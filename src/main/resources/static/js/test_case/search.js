// 테스트 케이스 검색용 스크립트
if($("#test_case_search_list").length) {
    const test_case_search_list = $("#test_case_search_list");
    const test_case_list = [];

    function searchTestCase(keyword) {
        if(!keyword) {
            test_case_search_list.hide();
            return;
        }

        $.ajax({
            url: '/api/testcase/get_list',
            data: {name: keyword},
            method: 'get',
            success: function (data) {
                const result = data.result
                test_case_search_list.empty();

                if(result.length) {
                    result.forEach(item => {
                        if(!test_case_list.includes(item.idx))
                            test_case_search_list.append(`<li data-idx="${item.idx}">${item.title}</li>`);
                    });

                    if(test_case_search_list.find("li").length) {
                        test_case_search_list.show();
                    } else {
                        test_case_search_list.hide();
                    }
                } else {
                    test_case_search_list.hide();
                }
            }
        });
    }

    $("#test_case_search").keyup(function() {
        searchTestCase($(this).val());
    });

    $("body *:not(#test_case_search_list)").click(function () {
        $("#test_case_search").val("");
        test_case_search_list.hide();
    });

    // 테스트 케이스 선택
    test_case_search_list.on("click", "li", function() {
        const idx = $(this).data("idx");
        const title = $(this).text();

        $("#test_case_search").val("");
        searchTestCase(null);

        test_case_list.push(idx);
        $("#test_case_list").append(`<div class="test_case" data-idx="" data-test_case="${idx}">${title}<i class="delete_item"></i></div>`);
    });

    // 테스트 케이스 삭제
    $("#test_case_list").on("click", ".test_case .delete_item", function() {
        const _dom = $(this).closest(".test_case");
        if(_dom.data("idx")) {
            delete_test_case_list.push(_dom.data("idx"));
        }

        test_case_list.splice(test_case_list.indexOf(_dom.data("test_case")), 1);
        _dom.remove();
    });

    // 선택 테스트 케이스 목록
    function selectedTestCaseList() {
        const test_case_list = $("#test_case_list .test_case");
        let result_list = [];

        for(test_case of test_case_list) {
            if(!$(test_case).data("idx")) {
                result_list.push($(test_case).data("test_case"));
            }
        }

        return result_list;
    }

    // INIT
    for(item of $("#test_case_list .test_case")) {
        test_case_list.push($(item).data("test_case"));
    }

    // 선택 테스트 케이스 목록 비우기
    function clearSelectedTestCase() {
        $("#test_case_search").val("");
        $("#test_case_list").empty();
        searchTestCase(null);
        while(test_case_list.length) test_case_list.pop();
        while(delete_test_case_list.length) delete_test_case_list.pop();
    }
}