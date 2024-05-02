// 저장
$("#save_btn").click(function() {
    const name = $("#name").val();
    const account = $("#account").val();
    const password = $("#password").val();
    const password_check = $("#password_check").val();
    const position_idx = $("#position_idx").val();
    const role_idx = $("#role_idx").val();
    const disable_yn = $("[name=disable_yn]:checked");

    const team_list = $("#team_list .team");

    if(!name) {
        alertMsg("이름을 입력해주세요.");
        return;
    }

    if(!account) {
        alertMsg("계정을 입력해주세요.");
        return;
    }

    if(!password) {
        alertMsg("비밀번호를 입력해주세요.");
        return;
    }

    if(password !== password_check) {
        alertMsg("비밀번호를 확인해주세요.");
        return;
    }

    const form_data = new FormData();
    form_data.append("idx", $("#idx").val());
    form_data.append("name", name);
    form_data.append("account", account);
    form_data.append("password", password);
    form_data.append("position_idx", position_idx ? position_idx : "");
    form_data.append("role_idx", role_idx ? role_idx : "");
    form_data.append("disable_yn", disable_yn.length ? disable_yn.val() : "N");
    form_data.append("note", $("#note").val());

    save(form_data);
});

function save(form_data, retry = false) {
    $.ajax({
        url: '/api/member/save',
        method: 'post',
        data : form_data,
        contentType: false,
        processData: false,
        success: function (data) {
            const msg = data.msg ?? null;
            const code = data.code ?? null;

            let clicked = function(){};
            if(code === 200) {
                alert_param = data.result;
                clicked = function() {
                    if(alert_param) {
                        location.replace(`/member/detail/${alert_param}`);
                    }
                }
            }

            if(msg ?? null) alertMsg(msg, clicked);
        }, error: function () {
            if(!retry) save(form_data, true);
        }
    });
}