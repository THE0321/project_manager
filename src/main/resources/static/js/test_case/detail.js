$(function(){
    // DATE PICKER
    setDatepicker("start_date")
    setDatepicker("end_date")
});

// 저장
$("#save_btn").click(function() {
    const directory_idx = $("#directory_idx").val();
    const title = $("#title").val();

    if(!directory_idx) {
        alertMsg("디렉토리를 선택해주세요.");
        return;
    }

    if(!title) {
        alertMsg("테스트 케이스 제목을 입력해주세요.");
        return;
    }

    const form_data = new FormData();
    form_data.append("idx", $("#idx").val());
    form_data.append("directory_idx", directory_idx);
    form_data.append("title", title);
    form_data.append("description", $('#description').val());
    form_data.append("start_date", $('#start_date').val());
    form_data.append("end_date", $('#end_date').val());
    form_data.append("member_list", selectedMemberList());
    form_data.append("delete_member_list", delete_member_list);
    form_data.append("action_item_list", selectedActionItemList());
    form_data.append("delete_action_item_list", delete_action_item_list);

    const selected_status = $("[name=status_idx]:checked");
    if(selected_status.length) form_data.append("status_idx", selected_status.val());

    save(form_data);
});

function save(form_data, retry = false) {
    $.ajax({
        url: '/api/testcase/save',
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
                        location.replace(`/testcase/detail/${alert_param}`);
                    }
                }
            }

            if(msg ?? null) alertMsg(msg, clicked);
        }, error: function () {
            if(!retry) save(form_data, true);
        }
    });
}