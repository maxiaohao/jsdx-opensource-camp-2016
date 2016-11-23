package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;


    @Override
    public TaotaoResult insertContent(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        // 插入数据
        contentMapper.insert(content);

        return TaotaoResult.ok();
    }


    @Override
    public TaotaoResult deleteContent(TbContent content) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public TaotaoResult updateContent(TbContent content) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public EasyUIDataGridResult getContentList(int page, int rows) {
        // 分页处理
        PageHelper.startPage(page, rows);
        // 执行查询
        TbContentExample example = new TbContentExample();
        List<TbContent> list = contentMapper.selectByExample(example);
        // 取分页信息
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        // 返回处理结果
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);

        return result;
    }

}
