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

                    if(team_search_list.find("li").length) {
                        team_search_list.show();
                    } else {
                        team_search_list.hide();
                    }
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

    // INIT
    for(item of $("#team_list .member")) {
        team_list.push($(item).data("team"));
    }

    // 선택 팀 목록 비우기
    function clearSelectedTeam() {
        $("#team_search").val("");
        $("#team_list").empty();
        searchTeam(null);
        while(team_list.length) team_list.pop();
        while(delete_member_list.length) delete_member_list.pop();
    }
}