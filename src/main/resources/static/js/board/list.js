import {boardFunc} from "/static/js/board/boardFunc.js";

$(function () {

    let page = 0;

    boardFunc.list.moreData(page);

    $(document).on("click", ".filter", function (e) {

        boardFunc.list.chgFilter(e.target);

        let param = {
            page: 1,
            search: document.querySelector("input[name=search]").value,
            filter: document.querySelector(".filter.on").dataset.filter,
        }

        axios.get("/board/list", {params: param}, {
            headers: {
                // "Content-Type": "multipart/form-data",
                // "Content-Type": "application/json",
            },
        }).then((res) => {
            console.log(res);
            if (res.data.result.code == 200) {
                boardFunc.list.drawList(res.data.data.list, true);
                page = 1;
            } else {
                alert_modal.on("저장", res.data.result.message);
            }
        }).catch((error) => {
            alert_modal.on("추가조회", "추가조회에 실패했습니다.");
            console.log(error)
        });

    });

    $(document).on("change", "input[name=search]", function (e) {

        let param = {
            page: 1,
            search: document.querySelector("input[name=search]").value,
            filter: document.querySelector(".filter.on").dataset.filter,
        }

        axios.get("/board/list", {params: param}, {
            headers: {
                // "Content-Type": "multipart/form-data",
                // "Content-Type": "application/json",
            },
        }).then((res) => {
            console.log(res);
            if (res.data.result.code == 200) {
                boardFunc.list.drawList(res.data.data.list, true);
                page = 1;
            } else {
                alert_modal.on("저장", res.data.result.message);
            }
        }).catch((error) => {
            alert_modal.on("추가조회", "추가조회에 실패했습니다.");
            console.log(error)
        });

    });

    $(document).on("click", ".list", function (e) {

        let bno = this.dataset.bno;
        location.href = "/board/" + bno;

    });

    window.addEventListener('scroll', () => {
        //스크롤을 할 때마다 로그로 현재 스크롤의 위치가 찍혀나온다.


        if (boardFunc.list.detectBottom()) {
            ++page;
            boardFunc.list.moreData(page);
        }
    });


});