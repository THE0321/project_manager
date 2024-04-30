// 로고 클릭
$(".logo").click(function() {
    location.href = "/";
});

// 팝업 이벤트 버블링 방지
$(".popup").on("scroll click", function(e) {
    e.stopPropagation();
});

// 팝업 닫기
$(".pop_close").click(function() {
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

// datepicker
function setDatepicker(id = "") {
    if(id) {
        id.replace("#", "");
        $(`#${id}`).datepicker({
            dateFormat: 'yy-mm-dd'
            ,monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
            ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
            ,dayNamesMin: ['일','월','화','수','목','금','토']
            ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일']
        }).prop("readonly", true);
    }
}

// alert
function alertMsg(msg, title = "") {
    $("#pop_title").text(title);
    $("#alert_message").text(msg);
    onPopup("alert_pop");
}