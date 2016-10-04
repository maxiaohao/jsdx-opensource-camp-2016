// JavaScript Document
function Delete(id)
{
	if(confirm("确定要删除吗？")) {
		location.href = "manager-crud-servlet?model=user&action=delete&userId=" + id;
	}
}