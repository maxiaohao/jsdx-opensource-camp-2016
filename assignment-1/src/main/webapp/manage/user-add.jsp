<%@ page language="java" pageEncoding="utf-8"%>
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
			<li class="current"><a href="user.jsp">用户</a></li>
			<li><a href="product.jsp">商品</a></li>
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
		<h2>新增用户</h2>
		<div class="manage">
			<form action="admin-crud-servlet" method="post">
                <table class="form">
                    <input type="hidden" name="model" value="user" />
                    <input type="hidden" name="action" value="add" />
					<tr>
						<td class="field">用户名：</td>
						<td><input type="text" class="text" name="userName" value="" /></td>
					</tr>
					<tr>
						<td class="field">密码：</td>
						<td><input type="password" class="text" name="passWord" value="" /></td>
					</tr>
					<tr>
						<td class="field">性别：</td>
						<td><input type="radio" name="sex" value="男" />男 <input type="radio" name="sex" value="女" />女</td>
					</tr>
					<tr>
                        <td class="field">出生日期：</td>
                        <td>
                            <select name="birthyear">
                                <%
                                    for(int i=2016; i>=1900; i--){
                                        out.println("<option value=\""+i+"\" >"+i+"</option>");
                                    }
                                %>
                            </select>年
                            <select name="birthmonth">
                                <%
                                    for(int i=1; i<=12; i++){
                                        out.println("<option value=\""+i+"\" >"+i+"</option>");
                                    }
                                %>
                            </select>月
                            <select name="birthday">
                                <%
                                    for(int i=1; i<=31; i++){
                                        out.println("<option value=\""+i+"\" >"+i+"</option>");
                                    }
                                %>
                            </select>日
                        </td>
					</tr>
                    <tr>
                        <td class="field">身份证号：</td>
                        <td><input type="text" class="text" name="identity_code" value="${user.eu_identity_code}" /></td>
                    </tr>
                    <tr>
                        <td class="field">e-mail：</td>
                        <td><input type="text" class="text" name="email" value="${user.eu_email}" /></td>
                    </tr>
					<tr>
						<td class="field">手机号码：</td>
						<td><input type="text" class="text" name="mobile" value="" /></td>
					</tr>
					<tr>
						<td class="field">送货地址：</td>
						<td><input type="text" class="text" name="address" value="" /></td>
					</tr>
					<tr>
						<td class="field">头像：</td>
						<td><input type="file" class="text" name="photo" /></td>
					</tr>
                    <tr>
                        <td class="field">类型：</td>
                        <td>
                            <input type="radio" name="status" value="1" checked='checked' />普通用户 
                            <input type="radio" name="status" value="2" />管理员
                        </td>
                    </tr>
					<tr>
						<td></td>
						<td><label class="ui-blue"><input type="submit" name="submit" value="添加" /></label></td>
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
