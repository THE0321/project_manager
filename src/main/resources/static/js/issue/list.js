// 검색 초기화
$("#refresh_btn").click(function() {
    $("#title").val("");
    $("[name=status_idx]").prop("checked", false);
});

// 검색
$("#title").keydown(function(e) {
    if(e.keyCode === 13) {
        $("#search_btn").click();
    }
});

$("#search_btn").click(function() {
    const title = $("#title").val();
    const status_idx = $("[name=status_idx]:checked")?.val();

    let search_param = {};
    title ? search_param["title"] = title : null;
    status_idx ? search_param["status_idx"] = status_idx : null;

    const query_string = new URLSearchParams(search_param).toString();
    location.replace(`/issue?${query_string}`);
});

// 등록
$("#regist_btn").click(function() {
    location.href = `/issue/detail`;
});

// 수정/상세
$("#item_list tr").click(function() {
    const idx = $(this).data("idx");
    location.href = `/issue/detail/${idx}`;
});

// 상태 변경
$("#item_list tr .status_select").change(function() {
    const form_data = new FormData();
    form_data.append("idx", $(this).closest("tr").data("idx"));
    form_data.append("status_idx", $(this).val());

    saveStatus(form_data);
}).click(function(e) {
    e.stopPropagation();
    return false;
});

function saveStatus(form_data, retry = false) {
    $.ajax({
        url: '/api/issue/change_status',
        method: 'post',
        data : form_data,
        contentType: false,
        processData: false,
        success: function (data) {
            const msg = data.msg ?? null;
            if(msg ?? null) alertMsg(msg);
        }, error: function () {
            if(!retry) saveStatus(form_data, true);
        }
    });
}