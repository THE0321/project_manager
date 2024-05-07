// 저장
$("#save_btn").click(function() {
    const title = $("#title").val();

    if(!title) {
        alertMsg("리스크 제목을 입력해주세요.");
        return;
    }

    const form_data = new FormData();
    form_data.append("idx", $("#idx").val());
    form_data.append("title", title);
    form_data.append("description", $('#description').val());
    form_data.append("action_item_list", selectedActionItemList());
    form_data.append("delete_action_item_list", delete_action_item_list);
    form_data.append("test_case_list", selectedTestCaseList());
    form_data.append("delete_test_case_list", delete_test_case_list);

    const selected_status = $("[name=status_idx]:checked");
    if(selected_status.length) form_data.append("status_idx", selected_status.val());

    save(form_data);
});

function save(form_data, retry = false) {
    $.ajax({
        url: '/api/risk/save',
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
                        location.replace(`/risk/detail/${alert_param}`);
                    }
                }
            }

            if(msg ?? null) alertMsg(msg, clicked);
        }, error: function () {
            if(!retry) save(form_data, true);
        }
    });
}