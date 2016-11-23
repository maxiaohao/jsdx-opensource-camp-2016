package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.service.ContentService;

@Controller
public class ContentController {

    @Autowired
    private ContentService service;


    @RequestMapping("/content/query/list?categoryId=0&page=1&rows=20")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(
            @RequestParam(value = "categoryId", required = false) Long catId,
            @RequestParam(value = "page", required = false) Long page,
            @RequestParam(value = "rows", required = false) Long rows
            ) {
        if (null == catId) {
            catId = 0L;
        }
        List<EasyUITreeNode> result = null;
        return result;
    }
}
