// 로고 클릭
$(".logo").click(function() {
    location.href = "/";
});

// 팝업 이벤트 버블링 방지
$(".popup").on("scroll click", function(e) {
    e.stopPropagation();
});

// 팝업 닫기
$(document).on("click", ".pop_close", function() {
    $(".popup, .popup section").hide();
});

// 팝업 열기
function onPopup(id = "") {
    $(".popup").show();

    if(id) {
        id.replace("#", "");
        $(`#${id}`).show();
    }
}

// 이미지 base64 변환
function getBase64(file, onloaded) {
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = onloaded;
    reader.onerror = function (error) {
        console.log('Error: ', error);
    };
}

function alertMsg(msg, title = "") {
    $("#pop_title").text(title);
    $("#alert_message").text(msg);
    onPopup("alert_pop");
}