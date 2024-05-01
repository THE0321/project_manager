const delete_member_list = [];

$(function(){
    // DATE PICKER
    setDatepicker("start_date")
    setDatepicker("end_date")
});

// 저장
$("#save_btn").click(function() {
    const title = $("#title").val();

    const team_list = $("#team_list .team");
    const member_list = $("#member_list .member");

    if(!title) {
        alert("프로젝트명을 입력해주세요.");
        return;
    }

    const form_data = new FormData();
    form_data.append("idx", $("#idx").val());
    form_data.append("title", title);
    form_data.append("description", $('#description').val());
    form_data.append("start_date", $('#start_date').val());
    form_data.append("end_date", $('#end_date').val());

    const selected_status = $("[name=status_idx]:checked");
    if(selected_status.length) {
        form_data.append("status_idx", selected_status.val());
    }

    save(form_data);
});

function save(form_data, retry = false) {
    $.ajax({
        url: '/api/project/save',
        method: 'post',
        data : form_data,
        contentType: false,
        processData: false,
        success: function (data) {
            const msg = data.msg ?? null;
            const code = data.code ?? null;

            if(msg ?? null) {
                alert(msg);
            }

            if(code === "200") {
                const result = data.result ?? null;
                location.replace(`/detail/detail/${result}`);
            }
        }, error: function () {
            if(!retry) save(form_data, true);
        }
    });
}