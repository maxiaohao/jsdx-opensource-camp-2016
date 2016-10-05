'use strict';

// 右上角登录信息+管理链接
function renderTopRightInfo() {
    $.ajax({
        url : 'public-json-crud-servlet',
        method : 'post',
        data : {
            model : 'user',
            action : 'get_current_user_name'
        },
        success : function(ret) {
            if (ret.success) {
                $("#onlineUserNameDesc").html(ret.data + '已登录');
                $("#onlineUserNameDesc").show();
                $("#logoutLink").show();
                // 退出并返回首页
                $("#logoutLink").click(function() {
                    $.ajax({
                        url : 'public-json-crud-servlet',
                        method : 'post',
                        data : {
                            model : 'user',
                            action : 'logout'
                        },
                        success : function(ret) {
                            if (ret.success) {
                                // reload index page
                                window.location.href = "index.html";
                            } else {
                                alert("退出登录时出错: " + ret.msg);
                            }
                        },
                        error : function() {
                            $.messager.alert('Internal Error', 'Failed get response from server!', 'error');
                        }
                    });
                });
            } else {
                $("#loginLink").show();
                $("#registerLink").show();
            }
        },
        error : function() {
            $.messager.alert('Internal Error', 'Failed get response from server!', 'error');
        }
    });

    $.ajax({
        url : 'public-json-crud-servlet',
        method : 'post',
        data : {
            model : 'user',
            action : 'is_current_user_admin'
        },
        success : function(ret) {
            if (ret.success && ret.data) {
                $("#adminLink").show();
            }
        },
        error : function() {
            $.messager.alert('Internal Error', 'Failed get response from server!', 'error');
        }
    });

};

// 列出左侧商品分类
function renderProdCats() {
    $
            .ajax({
                url : 'public-json-crud-servlet',
                method : 'post',
                data : {
                    model : 'prodCat',
                    action : 'getAll'
                },
                success : function(ret) {
                    if (ret.success) {
                        if (ret.data) {
                            for ( var i in ret.data) {
                                var pc = ret.data[i];
                                $("#prodCatDl").append(
                                        "<dd><a href='product-list.html?epcId=" + pc.epc_id + "'>" + pc.epc_name
                                                + "</a></dd>");
                            }
                        } else {
                            alert('商品分类数据为空,请检查后台数据库表配置');
                        }
                    } else {
                        alert('商品分类数据读取错误：' + ret.msg);
                    }
                },
                error : function() {
                    $.messager.alert('Internal Error', 'Failed get response from server!', 'error');
                }
            });
};

// index.html列出top商品(今日特价或热卖推荐)
function listTopProducts(containerSelector, topCount) {
    $.ajax({
        url : 'public-json-crud-servlet',
        method : 'post',
        data : {
            model : 'product',
            action : 'getTopProducts',
            topCount : topCount
        },
        success : function(ret) {
            if (ret.success) {
                if (ret.data) {
                    for ( var i in ret.data) {
                        var p = ret.data[i];
                        $(containerSelector).append(
                                "<li><dl><dt><a href='product-view.html?epId=" + p.ep_id
                                        + "' target='_blank'><img src='images/product/" + p.ep_file_name
                                        + "' /></a></dt><dd class='title'><a href='product-view.html=" + p.ep_id
                                        + "' target='_blank'>" + p.ep_name + "</a></dd><dd class='price'>￥"
                                        + p.ep_price + "</dd></dl></li>");
                    }
                } else {
                    alert('商品数据为空,请检查后台数据库表配置');
                }
            } else {
                alert('商品数据读取错误：' + ret.msg);
            }
        },
        error : function() {
            $.messager.alert('Internal Error', 'Failed get response from server!', 'error');
        }
    });
};

// 渲染product-list.html
function renderProductListByCat(epcId, curPage, pageSize) {
    $.ajax({
        url : 'public-json-crud-servlet',
        method : 'post',
        data : {
            model : 'product',
            action : 'getByEpcIdAndPage',
            epcId : epcId,
            curPage : curPage,
            pageSize : pageSize
        },
        success : function(ret) {
            if (ret.success) {
                if (ret.data) {
                    var pageCount = ret.data.pageCount;
                    $("#pager1").empty();
                    $("#pager2").empty();

                    var content = curPage <= 1 ? "<li>上一页</lib>" : "<li><a href='javascript:renderProductListByCat("
                            + epcId + "," + (curPage - 1) + "," + pageSize + ");'>上一页</a></li>";
                    $("#pager1").append(content);
                    $("#pager2").append(content);

                    for (var i = 1; i <= pageCount; i++) {
                        var content = "<li " + (i === curPage ? "class=\"current\" " : "")
                                + "><a href='javascript:renderProductListByCat(" + epcId + "," + i + "," + pageSize
                                + ");'>" + i + "</a></li>";
                        $("#pager1").append(content);
                        $("#pager2").append(content);
                    }

                    content = curPage >= pageCount ? "<li>下一页</lib>"
                            : "<li><a href='javascript:renderProductListByCat(" + epcId + "," + (curPage + 1) + ","
                                    + pageSize + ");'>下一页</a></li>";
                    $("#pager1").append(content);
                    $("#pager2").append(content);

                    $("#productsUl").empty();
                    for ( var i in ret.data.products) {
                        var p = ret.data.products[i];
                        $("#productsUl").append(
                                "<li><dl><dt><a href='product-view.html?epId=" + p.ep_id
                                        + "' target='_blank'><img src='images/product/" + p.ep_file_name
                                        + "' /></a></dt><dd class='title'><a href='product-view.html?epId=" + p.ep_id
                                        + "' target='_blank'>" + p.ep_name + "</a></dd><dd class='price'>￥"
                                        + p.ep_price + "</dd></dl></li>");
                    }
                } else {
                    alert('后台返回了无效数据，请检查服务端程序');
                }
            } else {
                alert('商品数据读取错误：' + ret.msg);
            }
        },
        error : function() {
            $.messager.alert('Internal Error', 'Failed get response from server!', 'error');
        }
    });
}

function renderProductDetail(epId) {
    $.ajax({
        url : 'public-json-crud-servlet',
        method : 'post',
        data : {
            model : 'product',
            action : 'getProductDetail',
            epId : epId
        },
        success : function(ret) {
            if (ret.success) {
                if (ret.data) {
                    $("#epName").append(ret.data.ep_name);
                    $("#epImg").attr("src", "images/product/" + ret.data.ep_file_name);
                    $("#epPrice").append("￥" + ret.data.ep_price);
                    $("#epStock").append("库存: " + ret.data.ep_stock);
                    $("#epDescription").append("描述: " + ret.data.ep_description);
                } else {
                    alert('后台返回了无效数据，请检查服务端程序');
                }
            } else {
                alert('商品数据读取错误：' + ret.msg);
            }
        },
        error : function() {
            $.messager.alert('Internal Error', 'Failed get response from server!', 'error');
        }
    });
}
