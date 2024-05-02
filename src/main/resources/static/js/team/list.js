// 검색 초기화
$("#refresh_btn").click(function() {
    $("#name").val("");
});

// 검색
$("#name").keydown(function(e) {
    if (e.keyCode === 13) {
        $("#search_btn").click();
    }
});

$("#search_btn").click(function() {
    const name = $("#name").val();
    location.replace(`/team?name=${name}`);
});

// 등록
$("#regist_btn").click(function() {
    location.href = `/team/detail`;
});

// 수정/상세
$("#item_list tr").click(function() {
    const idx = $(this).data("idx");
    location.href = `/team/detail/${idx}`;
});