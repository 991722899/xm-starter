package com.xm.starter.request.log.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.xm.starter.request.log.enums.ContentType;
import com.xm.starter.request.log.enums.RequestLogStatusEnum;
import com.xm.starter.request.log.enums.RequestResponseType;
import com.xm.starter.request.log.model.RequestLogDetailOutputDTO;
import com.xm.starter.request.log.model.RequestLogOutputDTO;
import com.xm.starter.request.log.model.RequestLogProperties;
import com.xm.starter.request.log.service.RequestLogOutputService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

@Slf4j
public class RequestLogFilter implements Filter {
    private RequestLogOutputService requestLogOutputService;
    private RequestLogProperties requestLogProperties;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private UrlPathHelper urlPathHelper;

    public RequestLogFilter(RequestLogOutputService requestLogOutputService, RequestLogProperties requestLogProperties,UrlPathHelper urlPathHelper) {
        this.requestLogOutputService = requestLogOutputService;
        this.requestLogProperties = requestLogProperties;
        this.urlPathHelper = urlPathHelper;;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long start = System.currentTimeMillis();

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        //过滤不需要记录日志的地址
        if (CollUtil.isNotEmpty(requestLogProperties.getExcludeUrlPatterns())) {
            // 获取请求的实际路径
            String lookupPath = urlPathHelper.getLookupPathForRequest(httpServletRequest);
            for (String pattern : requestLogProperties.getExcludeUrlPatterns()) {
                if (pathMatcher.match(pattern, lookupPath)) {
                    chain.doFilter(httpServletRequest,httpServletResponse);
                    return;
                }
            }
        }


        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(httpServletRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpServletResponse);

        RequestLogOutputDTO requestLogOutputDTO = new RequestLogOutputDTO();
        requestLogOutputDTO.setStatus(RequestLogStatusEnum.NORMAL.getStatus());
        //处理请求前的逻辑
        try {
            parseRequest(requestWrapper,requestLogOutputDTO);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        chain.doFilter(requestWrapper,responseWrapper);

        try {
            parseException(requestWrapper,requestLogOutputDTO);
            parseResponse(responseWrapper,requestLogOutputDTO);
            requestLogOutputDTO.setTimeConsuming(System.currentTimeMillis()-start);
            requestLogOutputService.output(requestLogOutputDTO);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        // 将缓冲的内容写回到实际的响应对象中，避免数据丢失
        responseWrapper.copyBodyToResponse();
    }

    private void parseRequest(ContentCachingRequestWrapper requestWrapper,RequestLogOutputDTO requestLogOutputDTO){
        requestLogOutputDTO.setIp(requestWrapper.getRemoteAddr());
        requestLogOutputDTO.setRequestUrl(requestWrapper.getRequestURI());
        //后期可以扩展title取值，默认使用请求地址
        requestLogOutputDTO.setTitle(requestWrapper.getRequestURI());;
        requestLogOutputDTO.setRequestMethod(requestWrapper.getMethod());
        requestLogOutputDTO.setUserId(requestWrapper.getHeader("userId"));
        requestLogOutputDTO.setUserName(requestWrapper.getHeader("userName"));

        List<RequestLogDetailOutputDTO> detailList = new ArrayList<>();
        requestWrapper.getParameterMap().forEach((key,value)->{
            RequestLogDetailOutputDTO detailOutputDTO = new RequestLogDetailOutputDTO();
            detailOutputDTO.setContentKey(key);
            detailOutputDTO.setContentValue(StrUtil.join(",",value));
            detailOutputDTO.setContentType(ContentType.PARAMETER.getType());
            detailOutputDTO.setType(RequestResponseType.REQUEST.getType());
            detailList.add(detailOutputDTO);
        });

        Enumeration<String> enumeration =  requestWrapper.getHeaderNames();
        while (enumeration.hasMoreElements()){
            String headerName =enumeration.nextElement();
            RequestLogDetailOutputDTO detailOutputDTO = new RequestLogDetailOutputDTO();
            detailOutputDTO.setContentKey(headerName);
            detailOutputDTO.setContentValue(requestWrapper.getHeader(headerName));
            detailOutputDTO.setContentType(ContentType.HEADER.getType());
            detailOutputDTO.setType(RequestResponseType.REQUEST.getType());
            detailList.add(detailOutputDTO);
        }

        String contentType = requestWrapper.getContentType();
        if (contentType != null && !contentType.toLowerCase().startsWith("multipart/form-data")) {
            RequestLogDetailOutputDTO detailOutputDTO = new RequestLogDetailOutputDTO();
            detailOutputDTO.setContentKey("content");
            detailOutputDTO.setContentValue(new String(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8));
            detailOutputDTO.setContentType(ContentType.CONTENT.getType());
            detailOutputDTO.setType(RequestResponseType.REQUEST.getType());
            detailList.add(detailOutputDTO);
        }

        requestLogOutputDTO.getDetailList().addAll(detailList);
    }

    private void parseResponse(ContentCachingResponseWrapper responseWrapper,RequestLogOutputDTO requestLogOutputDTO){
        List<RequestLogDetailOutputDTO> detailList = new ArrayList<>();
        Collection<String>  headerNames =  responseWrapper.getHeaderNames();
        if(CollUtil.isNotEmpty(headerNames)){
            for (String headerName : headerNames) {
                RequestLogDetailOutputDTO detailOutputDTO = new RequestLogDetailOutputDTO();
                detailOutputDTO.setContentKey(headerName);
                detailOutputDTO.setContentValue(responseWrapper.getHeader(headerName));
                detailOutputDTO.setContentType(ContentType.HEADER.getType());
                detailOutputDTO.setType(RequestResponseType.RESPONSE.getType());
                detailList.add(detailOutputDTO);
            }
        }

        //不是文件响应才读取记录
        String contentType = responseWrapper.getContentType();
        if(contentType != null && !contentType.startsWith("image/") &&
                !contentType.startsWith("application/octet-stream") &&
                !contentType.startsWith("application/pdf") &&
                !contentType.startsWith("video/") &&
                !contentType.startsWith("audio/")){

            RequestLogDetailOutputDTO detailOutputDTO = new RequestLogDetailOutputDTO();
            detailOutputDTO.setContentKey("content");
            detailOutputDTO.setContentValue(new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8));
            detailOutputDTO.setContentType(ContentType.CONTENT.getType());
            detailOutputDTO.setType(RequestResponseType.REQUEST.getType());
            detailList.add(detailOutputDTO);
        }

        requestLogOutputDTO.getDetailList().addAll(detailList);
    }

    private void parseException(ContentCachingRequestWrapper requestWrapper,RequestLogOutputDTO requestLogOutputDTO){
        if(requestWrapper.getAttribute(DispatcherServlet.EXCEPTION_ATTRIBUTE)!=null){
            Throwable e = (Throwable) requestWrapper.getAttribute(DispatcherServlet.EXCEPTION_ATTRIBUTE);
            requestLogOutputDTO.setExceptionDetail(ExceptionUtil.stacktraceToString(e,15000));
            requestLogOutputDTO.setStatus(RequestLogStatusEnum.EXCEPTION.getStatus());
        }

    }
}
