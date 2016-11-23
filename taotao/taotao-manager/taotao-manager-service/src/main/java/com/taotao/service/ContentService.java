package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {

    public TaotaoResult insertContent(TbContent content);


    public TaotaoResult deleteContent(TbContent content);


    public TaotaoResult updateContent(TbContent content);


    public EasyUIDataGridResult getContentList(int page, int rows);
}
