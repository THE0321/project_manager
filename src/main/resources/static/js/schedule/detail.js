$(function(){
    // DATE PICKER
    setDatepicker("schedule_date");

    // TIME PICKER
    setTimepicker("schedule_time");
});

// 등록
$("#regist_btn").click(function() {
    $("#idx").val("");
    $("#title").val("");
    $("#schedule_date").val("");
    $("#schedule_time").val("");
    $("#place").val("");
    $("#member_search").val("");
    $("#member_list").html("");

    onPopup("schedule_pop");
});

// 저장
$("#save_btn").click(function() {
    const title = $("#title").val();
    const schedule_date = $("#schedule_date").val();

    if(!title) {
        alertMsg("일정명을 입력해주세요.");
        return;
    }

    if(!schedule_date) {
        alertMsg("날짜를 선택해주세요.");
        return;
    }

    const form_data = new FormData();
    form_data.append("idx", $("#idx").val());
    form_data.append("title", title);
    form_data.append("schedule_date", schedule_date);
    form_data.append("schedule_time", $("#schedule_time").val());
    form_data.append("place", $("#place").val());
    form_data.append("member_list", selectedMemberList());
    form_data.append("delete_list", delete_member_list);

    save(form_data);
});

function save(form_data, retry = false) {
    $.ajax({
        url: '/api/schedule/save',
        method: 'post',
        data : form_data,
        contentType: false,
        processData: false,
        success: function (data) {
            const msg = data.msg ?? null;
            const code = data.code ?? null;

            if(code === 200) {
            }

            if(msg ?? null) alertMsg(msg);
        }, error: function () {
            if(!retry) save(form_data, true);
        }
    });
}

