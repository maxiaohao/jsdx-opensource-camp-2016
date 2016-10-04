'use strict';

$(function() {

    $("#refreshCaptchaBtn").click(function() {
        $("#veryCode").attr("src", 'generate-captcha?r=' + Math.random());
    });

    $("#submitBtn").click(function() {
        $.ajax({
            url : 'public-json-crud-servlet',
            method: 'post',
            data : {
                model : 'user',
                action : 'register',
                userName : $("#userName").val(),
                passWord : $("#passWord").val(),
                rePassWord: $("#rePassWord").val(),
                answer : $("#answer").val()
            },
            success : function(ret) {
                if (ret.success) {
                    // redirect to reg-result page
                    window.location.href = "reg-result.html";
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
