// 액션 아이템 검색용 스크립트
if($("#action_item_search_list").length) {
    const action_item_search_list = $("#action_item_search_list");
    const action_item_list = [];

    function searchActionItem(keyword) {
        if(!keyword) {
            action_item_search_list.hide();
            return;
        }

        $.ajax({
            url: '/api/action/get_list',
            data: {name: keyword},
            method: 'get',
            success: function (data) {
                const result = data.result
                action_item_search_list.empty();

                if(result.length) {
                    result.forEach(item => {
                        if(!action_item_list.includes(item.idx))
                            action_item_search_list.append(`<li data-idx="${item.idx}">${item.title}</li>`);
                    });

                    if(action_item_search_list.find("li").length) {
                        action_item_search_list.show();
                    } else {
                        action_item_search_list.hide();
                    }
                } else {
                    action_item_search_list.hide();
                }
            }
        });
    }

    $("#action_item_search").keyup(function() {
        searchActionItem($(this).val());
    });

    $("body *:not(#action_item_search_list)").click(function () {
        $("#action_item_search").val("");
        action_item_search_list.hide();
    });

    // 유저 선택
    action_item_search_list.on("click", "li", function() {
        const idx = $(this).data("idx");
        const title = $(this).text();

        $("#action_item_search").val("");
        searchActionItem(null);

        action_item_list.push(idx);
        $("#action_item_list").append(`<div class="action_item" data-idx="" data-action_item="${idx}">${title}<i class="delete_item"></i></div>`);
    });

    // 액션 아이템 삭제
    $("#action_item_list").on("click", ".action_item .delete_item", function() {
        const _dom = $(this).closest(".action_item");
        if(_dom.data("idx")) {
            delete_action_item_list.push(_dom.data("idx"));
        }

        action_item_list.splice(action_item_list.indexOf(_dom.data("action_item")), 1);
        _dom.remove();
    });

    // 선택 액션 아이템 목록
    function selectedActionItemList() {
        const action_item_list = $("#action_item_list .action_item");
        let result_list = [];

        for(action_item of action_item_list) {
            if(!$(action_item).data("idx")) {
                result_list.push($(action_item).data("action_item"));
            }
        }

        return result_list;
    }

    // INIT
    for(item of $("#action_item_list .action_item")) {
        action_item_list.push($(item).data("action_item"));
    }
}