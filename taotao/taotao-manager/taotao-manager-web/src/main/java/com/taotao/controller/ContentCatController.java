package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.service.ContentCatgoryService;

@Controller
public class ContentCatController {

    @Autowired
    private ContentCatgoryService service;


    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(value = "id", required = false) Long id) {
        if (null == id) {
            id = 0L;
        }
        List<EasyUITreeNode> result = service.getContentCatList(id);
        return result;
    }

}
