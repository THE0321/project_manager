let upload_file = null;

// 파일 업로드
$("#upload_file").change(function() {
    const files = this.files;
    upload_file = files[0];

    $(this).val("");

    // 미리보기
    getBase64(upload_file, function(e) {
        $(".profile_img").attr("src", e.srcElement.result);
    });
});

// 저장
$("#save_btn").click(function() {
    const name = $("#name").val();
    const password = $("#password").val();
    const password_check = $("#password_check").val();

    if(!name) {
        alertMsg("이름을 입력해주세요.");
        return;
    }

    if(!password && password !== password_check) {
        alertMsg("비밀번호를 확인해주세요.");
        return;
    }

    const form_data = new FormData();
    form_data.append("upload_file", upload_file);
    form_data.append("name", name);
    form_data.append("password", password);

    save(form_data);
});

function save(form_data, retry = false) {
    $.ajax({
        url: '/api/save_info',
        method: 'post',
        data : form_data,
        contentType: false,
        processData: false,
        success: function (data) {
            const msg = data.msg ?? null;

            if(msg ?? null) alertMsg(msg);
        }, error: function () {
            if(!retry) save(form_data, true);
        }
    });
}

// 로그아웃
$("#login_btn").click(function() {
    logout();
});

function logout(retry = false) {
    $.ajax({
        url: '/api/logout',
        method: 'post',
        success: function (data) {
            const code = data.code ?? null;
            if(code === 200) {
                location.replace(`/login`)
            }
        }, error: function () {
            if(!retry) logout(true);
        }
    });
}