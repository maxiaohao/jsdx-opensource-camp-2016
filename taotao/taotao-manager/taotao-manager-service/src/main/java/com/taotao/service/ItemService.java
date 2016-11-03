package com.taotao.service;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemService{
	public TbItem getItemById(Long itemId);
	public EasyUIDataGridResult getItemList(int page,int rows);
	TaotaoResult createItem(TbItem item, String itemDesc);
}