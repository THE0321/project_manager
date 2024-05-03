const delete_member_list = [];

// 팀 검색 스크립트
if($("#team_search_list").length) {
    const team_search_list = $("#team_search_list");
    const team_list = [];

    function searchTeam(keyword) {
        if(!keyword) {
            team_search_list.hide();
            return;
        }

        $.ajax({
            url: '/api/team/get_list',
            data: {name: keyword},
            method: 'get',
            success: function (data) {
                const result = data.result
                team_search_list.empty();

                if(result.length) {
                    result.forEach(item => {
                        if(!team_list.includes(item.idx))
                            team_search_list.append(`<li data-idx="${item.idx}">${item.name}</li>`);
                    });
                    team_search_list.show();
                } else {
                    team_search_list.hide();
                }
            }
        });
    }
    
    $("#team_search").keyup(function() {
        searchTeam($(this).val());
    });

    $("body *:not(#team_search_list)").click(function () {
        $("#team_search").val("");
        team_search_list.hide();
    });

    // 팀 선택
    team_search_list.on("click", "li", function() {
        const idx = $(this).data("idx");
        const name = $(this).text();

        $("#team_search").val("");
        searchTeam(null);

        team_list.push(idx);
        $("#team_list").append(`<div class="team" data-idx="" data-team="${idx}">${name}<i class="delete_item"></i></div>`);
    });

    // 팀 삭제
    $("#team_list").on("click", ".team .delete_item", function() {
        const _dom = $(this).closest(".team");
        if(_dom.data("idx")) {
            delete_member_list.push(_dom.data("idx"));
        }

        team_list.splice(team_list.indexOf(_dom.data("team")), 1);
        _dom.remove();
    });

    // 선택 팀 목록
    function selectedTeamList() {
        const team_list = $("#team_list .team");
        let result_list = [];

        for(team of team_list) {
            if(!$(team).data("idx")) {
                result_list.push($(team).data("team"));
            }
        }

        return result_list;
    }
}

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
                    member_search_list.show();
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

        $("#team_search").val("");
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

        member_list.splice(member_list.indexOf(_dom.data("team")), 1);
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
}