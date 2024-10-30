package com.xm.starter.base.model;

import lombok.Data;

/**
* @description 前端响应默认数据结构
* @author：陈超超
* @time：2024/6/6 14:15
*/
@Data
public class RVO {
    private String code;
    private String msg;
    private Object data;



    public RVO(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static RVO success(Object data){
       return new RVO(ResCode.SUCCESS,"",data);
    }

    public static RVO fail(){
        return new RVO(ResCode.FAIL,"","");
    }
    public static RVO fail(String msg, Object data){
        return new RVO(ResCode.FAIL,msg,data);
    }

    public static RVO fail(String code, String msg, Object data){
        return new RVO(code,msg,data);
    }

    public static RVO fail(String msg){
        return new RVO(ResCode.FAIL,msg,"");
    }


}
