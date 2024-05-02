// 검색 초기화
$("#refresh_btn").click(function() {
    $("#name").val("");
    $("#account").val("");
    $("#position_idx option[value='']").click();
    $("#role_idx option[value='']").click();
});

// 검색
$("#name").keydown(function(e) {
    if (e.keyCode === 13) {
        $("#search_btn").click();
    }
});

$("#search_btn").click(function() {
    const name = $("#name").val();
    const account = $("#account").val();
    const position_idx = $("#position_idx").val();
    const role_idx = $("#role_idx").val();

    let search_param = {};
    name ? search_param["name"] = name : null;
    account ? search_param["account"] = account : null;
    position_idx ? search_param["position_idx"] = position_idx : null;
    role_idx ? search_param["role_idx"] = role_idx : null;

    const query_string = new URLSearchParams(search_param).toString();
    location.replace(`/member?${query_string}`);
});

// 등록
$("#regist_btn").click(function() {
    location.href = `/member/detail`;
});

// 수정/상세
$("#item_list tr").click(function() {
    const idx = $(this).data("idx");
    location.href = `/member/detail/${idx}`;
});

$("#item_list tr .position_select, #item_list tr .role_select, #item_list tr .disable_select").click(function(e) {
    e.stopPropagation();
    return false;
});

// 직급 변경
$("#item_list tr .position_select").change(function() {
    const form_data = new FormData();
    form_data.append("idx", $(this).closest("tr").data("idx"));
    form_data.append("position_idx", $(this).val());

    savePosition(form_data);
});

function savePosition(form_data, retry = false) {
    $.ajax({
        url: '/api/member/change_position',
        method: 'post',
        data : form_data,
        contentType: false,
        processData: false,
        success: function (data) {
            const msg = data.msg ?? null;
            if(msg ?? null) alertMsg(msg);
        }, error: function () {
            if(!retry) savePosition(form_data, true);
        }
    });
}

// 직급 변경
$("#item_list tr .role_select").change(function() {
    const form_data = new FormData();
    form_data.append("idx", $(this).closest("tr").data("idx"));
    form_data.append("role_idx", $(this).val());

    saveRole(form_data);
});

function saveRole(form_data, retry = false) {
    $.ajax({
        url: '/api/member/change_role',
        method: 'post',
        data : form_data,
        contentType: false,
        processData: false,
        success: function (data) {
            const msg = data.msg ?? null;
            if(msg ?? null) alertMsg(msg);
        }, error: function () {
            if(!retry) saveRole(form_data, true);
        }
    });
}

// 직급 변경
$("#item_list tr .disable_select").change(function() {
    const form_data = new FormData();
    form_data.append("idx", $(this).closest("tr").data("idx"));
    form_data.append("disable_yn", $(this).val());

    saveDisableYn(form_data);
});

function saveDisableYn(form_data, retry = false) {
    $.ajax({
        url: '/api/member/change_disable_yn',
        method: 'post',
        data : form_data,
        contentType: false,
        processData: false,
        success: function (data) {
            const msg = data.msg ?? null;
            if(msg ?? null) alertMsg(msg);
        }, error: function () {
            if(!retry) saveDisableYn(form_data, true);
        }
    });
}