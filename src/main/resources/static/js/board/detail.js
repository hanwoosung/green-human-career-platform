import {boardFunc} from "/static/js/board/boardFunc.js";

$(function () {

    $(document).on("click", ".comment-del-btn", function (){

        let commentEle = this.closest(".comment");

        boardFunc.detail.delComment(commentEle);
    });

    $(document).on("click", "#save-comment-btn", function (){

        boardFunc.detail.insertComment();

    });

    $(document).on("click", "#del-btn", function (){

        let bno = document.querySelector(".content-detail").dataset.bno;

        boardFunc.detail.delBoard(bno);

    });

    $(document).on("click", "#edit-btn", function (){

        let bno = document.querySelector(".content-detail").dataset.bno;

        location.href = "/board/edit/" + bno;

    });

});