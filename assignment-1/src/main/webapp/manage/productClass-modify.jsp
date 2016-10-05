<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.easybuy.control.Constants" %>
<%@ page import="com.easybuy.model.ProdCat" %>
<%@ page import="com.easybuy.control.biz.impl.ProdCatBizImpl" %>
<%@ page import="org.apache.commons.lang3.math.NumberUtils" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理 - 易买网</title>
<link type="text/css" rel="stylesheet" href="../css/style.css" />
<script type="text/javascript" src="../scripts/function-manage.js"></script>
</head>
<body>
<div id="header" class="wrap">
	<div id="logo"><img src="../images/logo.gif" /></div>
	<div class="help"><a href="../index.html">返回前台页面</a></div>
	<div class="navbar">
		<ul class="clearfix">
			<li><a href="index.html">首页</a></li>
			<li><a href="user.jsp">用户</a></li>
			<li class="current"><a href="product.jsp">商品</a></li>
			<li><a href="order.html">订单</a></li>
			<li><a href="guestbook.html">留言</a></li>
			<li><a href="news.html">新闻</a></li>
		</ul>
	</div>
</div>
<div id="childNav">
	<div class="welcome wrap">
		管理员pillys您好，今天是2012-12-21，欢迎回到管理后台。
	</div>
</div>
<div id="position" class="wrap">
	您现在的位置：<a href="index.html">易买网</a> &gt; 管理后台
</div>
<div id="main" class="wrap">
	<div id="menu-mng" class="lefter">
		<div class="box">
			<dl>
				<dt>用户管理</dt>
				<dd><em><a href="user-add.jsp">新增</a></em><a href="user.jsp">用户管理</a></dd>
				<dt>商品信息</dt>
				<dd><em><a href="productClass-add.jsp">新增</a></em><a href="productClass.jsp">分类管理</a></dd>
				<dd><em><a href="product-add.jsp">新增</a></em><a href="product.jsp">商品管理</a></dd>
				<dt>订单管理</dt>
				<dd><a href="order.html">订单管理</a></dd>
				<dt>留言管理</dt>
				<dd><a href="guestbook.html">留言管理</a></dd>
				<dt>新闻管理</dt>
				<dd><em><a href="news-add.html">新增</a></em><a href="news.html">新闻管理</a></dd>
			</dl>
		</div>
	</div>
	<div class="main">
		<h2>修改分类</h2>
		<div class="manage">
        
            <%
                long epcId=NumberUtils.toLong(request.getParameter("epcId"));
                ProdCat obj=(ProdCat)new ProdCatBizImpl().getProdCatById(epcId).getData();
                if(null==obj){
                    obj=new ProdCat();
                }
                request.setAttribute("obj", obj);
                
                List<ProdCat> cats=(List<ProdCat>)new ProdCatBizImpl().getAllProdCats().getData();
            %>
        
			<form action="admin-crud-servlet" method="post">
                <input type="hidden" name="model" value="prodCat" />
                <input type="hidden" name="action" value="update" />
                <input type="hidden" name="epcId" value="<%=epcId %>" />
				<table class="form">
					<tr>
						<td class="field">父分类：</td>
						<td>
							<select name="parentId">
                                <option value="0" <%=0==obj.getEpc_parent_id()?"selected=\"selected\"":"" %> >[根栏目]</option>
                                <%
                                    for(ProdCat cat: cats){
                                        out.println("<option value=\""+cat.getEpc_id()+"\" "+
                                    (obj.getEpc_parent_id()==cat.getEpc_id()?"selected=\"selected\"":"")+" >"+cat.getEpc_name()+"</option>");
                                    }
                                %>

							</select>
						</td>
					</tr>
					<tr>
						<td class="field">分类名称：</td>
						<td><input type="text" class="text" name="className" value="${obj.epc_name}" /></td>
					</tr>
					<tr>
						<td></td>
						<td><label class="ui-blue"><input type="submit" name="submit" value="更新" /></label></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div class="clear"></div>
</div>
<div id="footer">
	Copyright &copy; 2016 易买网 All Rights Reserved. 苏ICP证1000001号
</div>
</body>
</html>
