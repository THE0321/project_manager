// 저장
$("#save_btn").click(function() {
    const name = $("#name").val();

    if(!name) {
        alertMsg("직급명을 입력해주세요.");
        return;
    }

    const form_data = new FormData();
    form_data.append("idx", $("#idx").val());
    form_data.append("name", name);
    form_data.append("order_number", $("#order_number").val());

    save(form_data);
});

function save(form_data, retry = false) {
    $.ajax({
        url: '/api/position/save',
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
                        location.replace(`/position/detail/${alert_param}`);
                    }
                }
            }

            if(msg ?? null) alertMsg(msg, clicked);
        }, error: function () {
            if(!retry) save(form_data, true);
        }
    });
}