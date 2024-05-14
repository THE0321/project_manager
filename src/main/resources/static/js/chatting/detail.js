const socket = io('ws://localhost:8081');
const chatting_list = $("#chatting_list");

let upload_file = null;
let page = 1;
let more_page = true;

socket.on('chatting', data => {
    getChatting(data);
});
socket.emit('join', $("#login_idx").val());

// 채팅방 선택
$("#room_list li").click(function () {
    const code = $(this).data("code");
    location.replace(`/chat/${code}`);
});

// 등록
$("#regist_room").click(function() {
    $("#title").val("");
    clearSelectedMember();

    onPopup("chatting_room_pop");
});

// 채팅방 저장
$("#save_btn").click(function() {
    const title = $("#title").val();

    if(!title) {
        alert("채팅방 제목을 입력해주세요.");
        return;
    }

    const form_data = new FormData();
    form_data.append("idx", $("#idx").val());
    form_data.append("title", title);
    form_data.append("member_list", selectedMemberList());
    form_data.append("delete_list", delete_member_list);

    saveRoom(form_data);
});

function saveRoom(form_data, retry = false) {
    $.ajax({
        url: '/api/chat/save_room',
        method: 'post',
        data : form_data,
        contentType: false,
        processData: false,
        success: function (data) {
            const msg = data.msg ?? null;
            const code = data.code ?? null;

            if(msg ?? null) alert(msg);

            if(code === 200) {
                location.replace(`/chat/${data.result}`);
            }
        }, error: function () {
            if(!retry) saveRoom(form_data, true);
        }
    });
}

// 파일 업로드
$("#upload_file").change(function() {
    const files = this.files;
    upload_file = files[0];

    $(this).val("");

    // 미리보기
    getBase64(upload_file, function(e) {
        $("#upload_image").attr("src", e.srcElement.result);
    });
});

// 채팅 저장
$("#mssage").keydown(function (e) {
    if (e.keyCode === 13) {
        $("#save_chatting").click();
    }
});
$("#save_chatting").click(function() {
    const message = $("#message").val();

    if(!upload_file && !message) {
        alertMsg("내용을 입력해주세요.");
        return;
    }

    const form_data = new FormData();
    form_data.append("room_idx", $("#idx").val());
    form_data.append("upload_file", upload_file);
    form_data.append("message", message);

    saveChatting(form_data);
});

function saveChatting(form_data, retry = false) {
    $.ajax({
        url: '/api/chat/save',
        method: 'post',
        data : form_data,
        contentType: false,
        processData: false,
        success: function (data) {
            const msg = data.msg ?? null;
            const code = data.code ?? null;

            if(code === 200) {
                $("#message").val("");
                $("#upload_image").attr("src", null);
                upload_file = null;

                socket.emit('chatting', data.result);
            } else if(msg) alertMsg(msg);
        }, error: function () {
            if(!retry) saveChatting(form_data, true);
        }
    });
}

// 채팅 불러오기
$("#chatting_list").scroll(function() {
    const scroll = $(this).scrollTop() + $(this).innerHeight();

    if(scroll <= 400){
        page++;
        getChattingList();
    }
});

function getChattingList(first = false, retry = false) {
    if(more_page) {
        $.ajax({
            url: '/api/chat/get_list',
            method: 'get',
            data : {room_idx: $("#idx").val(), page: page},
            success: function (data) {
                const result_list = data.result ?? [];
                if(result_list.length) {
                    result_list.forEach(item => {
                        let file = "";
                        if(item.fileIdx) {
                            if(item.fileExtension.includes("image/")) {
                                file = `<div><img height="300px" src="/api/upload/view/image/${item.fileIdx}"/></div>`;
                            } else {
                                file = `<div><a class="file_download" data-idx="${item.fileIdx}">${item.fileName}</a></div>`;
                            }
                        }

                        chatting_list.prepend(`<div class="chatting">
                                                ${item.message}
                                                ${file}
                                              </div>`);
                    });

                    if(first) chatting_list.scrollTop(chatting_list[0].scrollHeight);
                } else {
                    more_page = false;
                }
            }, error: function () {
                if(!retry) getChattingList(first, true);
            }
        });
    }
}

function getChatting(idx, retry = false) {
    $.ajax({
        url: '/api/chat/get',
        method: 'get',
        data : {idx: idx},
        success: function (data) {
            const result = data.result;
            if(result != null) {
                let file = "";
                if(result.fileIdx) {
                    if(result.fileExtension.includes("image/")) {
                        file = `<div><img height="300px" src="/api/upload/view/image/${result.fileIdx}"/></div>`;
                    } else {
                        file = `<div><a class="file_download" data-idx="${result.fileIdx}">${result.fileName}</a></div>`;
                    }
                }

                chatting_list.append(`<div class="chatting">
                                           ${result.message}
                                           ${file}
                                       </div>`);

                chatting_list.scrollTop(chatting_list[0].scrollHeight);
            }
        }, error: function () {
            if(!retry) getChatting(true);
        }
    });
}

getChattingList(true);