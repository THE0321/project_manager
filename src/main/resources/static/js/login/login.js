// 로그인
$("#account").keydown(function(e) {
    if (e.keyCode === 13) {
        $("#password").focus();
    }
});

$("#password").keydown(function(e) {
    if (e.keyCode === 13) {
        $("#login_btn").click();
    }
});

$("#login_btn").click(function() {
    const account = $("#account").val();
    const password = $("#password").val();
    
    if(!account) {
        alertMsg("계정을 입력해주세요.");
        return;
    }
    
    if(!password) {
        alertMsg("비밀번호를 입력해주세요.");
        return;
    }

    const form_data = new FormData();
    form_data.append("account", account);
    form_data.append("password", password);

    login(form_data);
});

function login(form_data, retry = false) {
    $.ajax({
        url: '/api/login',
        method: 'post',
        data : form_data,
        contentType: false,
        processData: false,
        success: function (data) {
            const code = data.code ?? null;

            if(code === 200) {
                location.replace(`/`);
            } else {
                alertMsg(data.msg);
            }
        }, error: function () {
            if(!retry) login(form_data, true);
        }
    });
}