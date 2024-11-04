package com.xm.starter.business.log.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lombok.Getter;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.List;

public class JavascriptUtil {
    @Getter
    private static volatile ScriptEngine engine = null;

    static {
        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        engine = factory.getScriptEngine("Nashorn");
    }

    public static String eval(String script,Object ... params) throws ScriptException, JsonProcessingException {
        ObjectMapper objectMapper = SpringUtil.getBean(ObjectMapper.class);
        Bindings bindings = engine.createBindings();
        for (int i = 0; i < params.length; i++) {
            bindings.put("p"+i,params[i]);
        }
        Object o = engine.eval("(function(){"+script+"})()",bindings);

        if(o==null){
            return "[]";
        }
        if(o instanceof ScriptObjectMirror){
            ScriptObjectMirror scriptObjectMirror = (ScriptObjectMirror) o;
            if(scriptObjectMirror.isArray()){
                return objectMapper.writeValueAsString(scriptObjectMirror.values());
            }else{
                return objectMapper.writeValueAsString(o);
            }
        }
        return objectMapper.writeValueAsString(o);
    }
}
