import {adminFunc} from "/static/js/admin/adminFunc.js";

$(function () {

    document.querySelector(".pwd-btn").addEventListener("click", function () {

        if (adminFunc.main.getChkUser().length <= 0){
            alert_modal.on("오류", "사용자를 선택 해주세요.");
            return;
        }

        confirm_modal.on(
            "비밀번호",
            "비밀번호를 초기화 하시겠습니까?",
            () => adminFunc.main.updtStatus("P"),
            confirm_modal.off,
            "초기화",
            "취소"
        );

    });

    document.querySelector(".use-btn").addEventListener("click", function () {

        if (adminFunc.main.getChkUser().length <= 0){
            alert_modal.on("오류", "사용자를 선택 해주세요.");
            return;
        }

        confirm_modal.on(
            "사용여부",
            "사용여부를 변경하시겠습니까?",
            () => adminFunc.main.updtStatus("U"),
            confirm_modal.off,
            "변경",
            "취소"
        );

    });

    $('.page-item').on("click", function () {
        let page = this.querySelector(".page-link").dataset.page;

        location.href = adminFunc.main.getUrl(page);
    })

    $('.toggles').on("click", function () {

        adminFunc.main.chgToggle(this);

        location.href = adminFunc.main.getUrl();
    })

    $('.user-table-row').on("click", function (e) {
        // 클릭된 요소가 체크박스인 경우, 버블링을 막는다
        if ($(e.target).is('input[type=checkbox]')) {
            e.stopPropagation();  // 이벤트 버블링 방지
            return;  // 더 이상 실행하지 않도록 종료
        }

        // 체크박스를 찾고 상태를 토글
        let checkBox = this.querySelector("input[type=checkbox]");
        checkBox.checked = !checkBox.checked;

        adminFunc.main.chkAll();
    });


    $(document).on("change", "input[name=userChk]", function (){

        adminFunc.main.chkAll();

    });

    $(document).on("change", "input[name=userChkAll]", function (){

        let checkBoxs = document.querySelectorAll("input[name=userChk]");
        let checkBoxAll = document.querySelector("input[name=userChkAll]");


        for (let box of checkBoxs){
            box.checked = checkBoxAll.checked;
        }

    });

});