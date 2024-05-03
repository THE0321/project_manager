// 저장
$("#save_btn").click(function() {
    const name = $("#name").val();

    if(!name) {
        alertMsg("팀명을 입력해주세요.");
        return;
    }

    const form_data = new FormData();
    form_data.append("idx", $("#idx").val());
    form_data.append("name", name);
    form_data.append("member_list", selectedMemberList());
    form_data.append("delete_list", delete_member_list);

    save(form_data);
});

function save(form_data, retry = false) {
    $.ajax({
        url: '/api/team/save',
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
                        location.replace(`/team/detail/${alert_param}`);
                    }
                }
            }

            if(msg ?? null) alertMsg(msg, clicked);
        }, error: function () {
            if(!retry) save(form_data, true);
        }
    });
}