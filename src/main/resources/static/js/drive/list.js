let upload_file = null;

// 등록
$("#regist_btn").click(function() {
    upload_file = null;

    $("#upload_file").val("");
    $("#file_name").text("파일을 업로드해주세요.");

    onPopup("upload_pop");
});

// 파일 업로드
$("#upload_file").change(function() {
    const files = $(this)[0].files;
    if(!files.length) {
        alertMsg("파일을 업로드해주세요.");
        $(this).val("");
        return;
    }

    upload_file = files[0];
    $("#file_name").text(upload_file.name);
});

// 저장
$("#upload_btn").click(function() {
    if(upload_file == null) {
        alertMsg("파일을 업로드해주세요.");
        return;
    }

    let form_data = new FormData;
    form_data.append("file", upload_file);

    saveFile(form_data);
});

function saveFile(form_data, retry = false) {
    $.ajax({
        url: '/api/upload/drive',
        method: 'post',
        data : form_data,
        contentType: false,
        processData: false,
        success: function (data) {
            const code = data.code ?? null;
            const msg = data.msg ?? null;

            if(code === 200) {
                clicked = function() {
                    location.reload();
                }
            }

            if(msg ?? null) alertMsg(msg, clicked);
        }, error: function () {
            if(!retry) saveFile(form_data, true);
        }
    });
}

// 다운로드
$("#item_list tr").click(function() {
    const idx = $(this).data("idx");
    location.href = `/api/upload/download/${idx}`;
});