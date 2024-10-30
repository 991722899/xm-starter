package com.xm.starter.log.visual.controller;

import com.xm.starter.log.visual.model.LogVisualReadVO;
import com.xm.starter.log.visual.model.LogVisualReadDTO;
import com.xm.starter.log.visual.service.LogVisualService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "logVisual", description = "日志可视化")
@Controller
@RequestMapping("/logVisual")
public class LogVisualController {
    private @Autowired LogVisualService logVisualService;

    @ResponseBody
    @RequestMapping("/read")
    public LogVisualReadVO read(@RequestBody LogVisualReadDTO query) throws IOException {
        return logVisualService.read(query);
    }

    @RequestMapping("/view")
    public String view(){
        return "logView";
    }

}
