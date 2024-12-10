import {common} from "/static/js/common.js";

export const boardFunc = {

    list: {

        chgFilter: (ele) => {
            let filters = document.querySelectorAll(".filter");


            for (let filter of filters) {
                filter.classList.remove("on");
            }

            ele.classList.add("on");
        },

        detectBottom: () => {
            let scrollTop = $(window).scrollTop();
            let innerHeight = $(window).innerHeight();
            let scrollHeight = $('body').prop('scrollHeight');

            if (scrollTop + innerHeight >= scrollHeight) {
                return true;
            } else {
                return false;
            }

        },

        moreData: (page) => {

            let param = {
                page: ++page,
                search: document.querySelector("input[name=search]").value,
                filter: document.querySelector(".filter.on").dataset.filter,
                userGbnCd: "C",
            }

            axios.get("/board/list", {params: param}, {
                headers: {
                    // "Content-Type": "multipart/form-data",
                    // "Content-Type": "application/json",
                },
            }).then((res) => {
                console.log(res);
                if (res.data.result.code == 200) {
                    boardFunc.list.drawList(res.data.data.list);
                } else {
                    alert_modal.on("저장", res.data.result.message);
                }
            }).catch((error) => {
                alert_modal.on("추가조회", "추가조회에 실패했습니다.");
                console.log(error)
            });

        },

        drawList: (list, result = false) => {

            let html = "";

            for (let board of list) {

                html +=
                    `<div class="list" data-bno="${board.bno}">
                                <a class="list-body">
                                    <div class="list-tag list-line">
                                        <div class="tags-wrap">
                                            <span class="tags">${board.bgbnNm}</span>
                                        </div>
                                    </div>
                                    <div class="list-title list-line">
                                        <span>${board.title}</span>
                                    </div>
                                    <div class="list-line list-text">
                                        <span>${board.content}</span>
                                    </div>
                                    <div class="list-line list-writer">
                                        <span>${board.name}</span>
                                    </div>
                                    <div class="list-line list-extra-info">
                                        <div class="list-extra-info-counts">
                                        <span>
                                            <img src="/static/images/message.png">
                                            <span>${board.comments.length}</span>
                                        </span>
                                            <span>
                                            <img src="/static/images/eye.png">
                                            <span>${board.vcnt}</span>
                                        </span>
                                        </div>
                                        <div class="list-date">
                                            <span>${common.format.date(board.instDt)}</span>
                                        </div>
                                    </div>
                                </a>
                            </div>`

            }
            if (result){
                document.querySelector(".list-content").innerHTML = html;
            }else{
                document.querySelector(".list-content").innerHTML += html;
            }


        }
    },

    detail: {

        delBoard: (bno) => {


            let param = {};

            axios.delete("/board/" + bno,
                param, {
                    headers: {
                        // "Content-Type": "multipart/form-data",
                        // "Content-Type": "application/json",
                    },
                }).then((res) => {
                if (res.data.result.code == 200) {
                    location.href = "/board";
                } else {
                    alert_modal.on("게시글", res.data.result.message);
                }
            }).catch((error) => {
                alert_modal.on("게시글", "댓글삭제에 실패했습니다.");
                console.log(error)
            });


        },

        delComment: (ele) => {

            let param = {};

            axios.delete("/board/comment/" + ele.dataset.cmno,
                param, {
                    headers: {
                        // "Content-Type": "multipart/form-data",
                        // "Content-Type": "application/json",
                    },
                }).then((res) => {
                if (res.data.result.code == 200) {
                    ele.remove();
                } else {
                    alert_modal.on("댓글", res.data.result.message);
                }
            }).catch((error) => {
                alert_modal.on("댓글", "댓글삭제에 실패했습니다.");
                console.log(error)
            });

        },

        insertComment: () => {

            let param = {
                cmContent: document.querySelector("textarea").value,
                bno: document.querySelector(".content-detail").dataset.bno,
                delYn: 'N'
            };

            if (param.cmContent == ''){
                alert_modal.on("빈값체크", "댓글을 입력하세요");
                return
            }

            axios.post("/board/comment",
                param, {
                    headers: {
                        // "Content-Type": "multipart/form-data",
                        "Content-Type": "application/json",
                    },
                }).then((res) => {
                if (res.data.result.code == 200) {
                    location.reload();
                } else {
                    alert_modal.on("댓글", res.data.result.message);
                }
                console.log(res);

            }).catch((error) => {
                alert_modal.on("댓글", "댓글작성에 실패했습니다.");
                console.log(error)
            });
        },

    }


}