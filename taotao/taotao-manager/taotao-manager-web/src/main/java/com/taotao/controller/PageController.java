package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * 通用的页面跳转逻辑
 * 
 */

@Controller
public class PageController {

    @RequestMapping("/")
    public String showIndex() {
        return "index";
    }
    
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page) {
        return page;
    }

}
