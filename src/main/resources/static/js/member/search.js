// 멤버 검색용 스크립트
if($("#member_search_list").length) {
    const member_search_list = $("#member_search_list");
    const member_list = [];

    function searchMember(keyword) {
        if(!keyword) {
            member_search_list.hide();
            return;
        }

        $.ajax({
            url: '/api/member/get_list',
            data: {name: keyword},
            method: 'get',
            success: function (data) {
                const result = data.result
                member_search_list.empty();

                if(result.length) {
                    result.forEach(item => {
                        if(!member_list.includes(item.idx))
                            member_search_list.append(`<li data-idx="${item.idx}" data-profile="${item.profileIdx ?? ""}">${item.name}</li>`);
                    });

                    if(member_search_list.find("li").length) {
                        member_search_list.show();
                    } else {
                        member_search_list.hide();
                    }
                } else {
                    member_search_list.hide();
                }
            }
        });
    }

    $("#member_search").keyup(function() {
        searchMember($(this).val());
    });

    $("body *:not(#member_search_list)").click(function () {
        $("#member_search").val("");
        member_search_list.hide();
    });

    // 유저 선택
    member_search_list.on("click", "li", function() {
        const idx = $(this).data("idx");
        const profileIdx = $(this).data("profile");
        const name = $(this).text();

        $("#member_search").val("");
        searchMember(null);

        let profile = "";
        if(profileIdx) {
            profile = `<div class="profile" style="background_image: url('/view/image/${profileIdx}');"></div>`;
        }

        member_list.push(idx);
        $("#member_list").append(`<div class="member" data-idx="" data-member="${idx}">
                                        ${profile}
                                        ${name}<i class="delete_item"></i>
                                  </div>`);
    });

    // 유저 삭제
    $("#member_list").on("click", ".member .delete_item", function() {
        const _dom = $(this).closest(".member");
        if(_dom.data("idx")) {
            delete_member_list.push(_dom.data("idx"));
        }

        member_list.splice(member_list.indexOf(_dom.data("member")), 1);
        _dom.remove();
    });

    // 선택 유저 목록
    function selectedMemberList() {
        const member_list = $("#member_list .member");
        let result_list = [];

        for(member of member_list) {
            if(!$(member).data("idx")) {
                result_list.push($(member).data("member"));
            }
        }

        return result_list;
    }

    // INIT
    for(item of $("#member_list .member")) {
        member_list.push($(item).data("member"));
    }
}