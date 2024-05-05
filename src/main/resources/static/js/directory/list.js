// 디렉토리 이동
$(".side-menu li").click(function() {
    const idx = $(this).data("idx");

    if(search != null) {
        search(idx);
    }
});

// 디렉토리 생성
$("#regist_directory").click(function() {
    location.href = `/directory/detail`;
});