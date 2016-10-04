'use strict';

$(function() {

    $("#refreshCaptchaBtn").click(function() {
        $("#veryCode").attr("src", 'generate-captcha?r=' + Math.random());
    });

    $("#submitBtn").click(function() {
        $.ajax({
            url : 'public-json-crud-servlet',
            data : {
                model : 'user',
                action : 'login',
                userName : $("#userName").val(),
                passWord : $("#passWord").val(),
                answer : $("#answer").val()
            },
            success : function(ret) {
                if (ret.success) {
                    // redirect to index page
                    window.location.href = "index.html";
                } else {
                    alert(ret.msg);
                }
            },
            error : function() {
                $.messager.alert('Internal Error', 'Failed get response from server!', 'error');
            }
        });
    });

});
