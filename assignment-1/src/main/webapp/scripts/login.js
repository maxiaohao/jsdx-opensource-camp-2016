'use strict';

$(function() {

    $("#refreshCaptchaBtn").click(function() {
        $("#captcha").attr("src", 'generate-captcha?r=' + Math.random());
    });

    $("#submitBtn").click(function() {
        $.ajax({
            url : 'login-servlet',
            data : {
                userName : $("#userName").val(),
                passWord : $("#passWord").val(),
                veryCode : $("#veryCode").val()
            },
            success : function(ret) {
                if (ret.success) {
                    // redirect to index.html
                    window.location.href = "index.html";
                } else {
                    alert(">> "+ret.msg);
                }
            },
            error : function() {
                $.messager.alert('Internal Error', 'Failed get response from server!', 'error');
            }
        });
    });

});
