'use strict';

$(function() {

    $.ajax({
        url : 'public-json-crud-servlet',
        method: 'post',
        data : {
            model : 'user',
            action : 'get_current_user_name'
        },
        success : function(ret) {
            if (ret.success) {
                $("#onlineUserNameDesc").html(ret.data + '已登录');
                $("#onlineUserNameDesc").show();
                $("#logoutLink").show();
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
        method: 'post',
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

    $("#logoutLink").click(function() {
        $.ajax({
            url : 'public-json-crud-servlet',
            method: 'post',
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

});
