package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ContentCatgoryService {
	public List<EasyUITreeNode> getContentCatList(Long parentId);
	public TaotaoResult insertCatgory(Long parentId, String name);
}
