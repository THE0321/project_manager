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
    clearSelectedMember();

    onPopup("schedule_pop");
});

// 저장
$("#save_btn").click(function() {
    const title = $("#title").val();
    const schedule_date = $("#schedule_date").val();

    if(!title) {
        alert("일정명을 입력해주세요.");
        return;
    }

    if(!schedule_date) {
        alert("날짜를 선택해주세요.");
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

// 달력 조회
function getCalendar(retry = false) {
    $.ajax({
        url: '/api/schedule/get_calendar',
        method: 'get',
        success: function (data) {
            const code = data.code ?? null;

            if(code === 200) {
                const result_list = data.result;
                result_list.forEach(item => {
                    addEvent(item.year, item.month, item.day);
                });
            }
        }, error: function () {
            if(!retry) getCalendar(true);
        }
    });
}

// 일정 조회
$(".calendar tbody").on("click", ".date", function() {
    getList();
});

function getList(retry = false) {
    const list_dom = $("#item_list");
    $.ajax({
        url: '/api/schedule/get_list',
        method: 'get',
        data: {date: getCalendarDate()},
        success: function (data) {
            const code = data.code ?? null;
            list_dom.empty();

            if(code === 200) {
                const result_list = data.result;
                result_list.forEach(item => {
                    list_dom.append(`
                        <tr data-idx="${item.idx}">
                            <td>${item.title}</td>
                            <td class="center_t">${item.scheduleDate}</td>
                            <td class="center_t">${item.scheduleTimeStr}</td>
                            <td>${item.registerName}</td>
                            <td class="center_t">${item.registDateStr}</td>
                        </tr>`);
                    console.log(item);
                });
            }
        }, error: function () {
            if(!retry) getCalendar(true);
        }
    });
}

// 일정 상세
$("#item_list").on("click", "tr", function() {
    const idx = $(this).data("idx");
    getDetail(idx);
});

function getDetail(idx, retry = false) {
    $.ajax({
        url: '/api/schedule/get_detail',
        method: 'get',
        data: {idx: idx},
        success: function (data) {
            const code = data.code ?? null;

            if(code === 200) {
                const result = data.result;

                $("#idx").val(result.idx);
                $("#title").val(result.title);
                $("#schedule_date").val(result.scheduleDate);
                $("#schedule_time").val(result.scheduleTimeStr);
                $("#place").val(result.place);
                $("#member_search").val("");
                $("#member_list").html("");

                result.memberList.forEach(item => {
                    let profile = "";
                    if(item.memberProfileIdx) {
                        profile = `<div class="profile" style="background_image: url('/view/image/${item.memberProfileIdx}');"></div>`;
                    }

                    $("#member_list").append(`<div class="member" data-idx="${item.idx}" data-member="${item.memberIdx}">
                                                    ${profile}${item.memberName}<i class="delete_item"></i>
                                                </div>`);
                });

                onPopup("schedule_pop");
            }
        }, error: function () {
            if(!retry) getCalendar(true);
        }
    });
}

getCalendar();
getList();