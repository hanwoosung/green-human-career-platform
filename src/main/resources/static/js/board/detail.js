import {boardFunc} from "/static/js/board/boardFunc.js";

$(function () {

    $(document).on("click", ".comment-del-btn", function (){

        let commentEle = this.closest(".comment");

        confirm_modal.on(
            "댓글",
            "댓글을 삭제 하시겠습니까?",
            () => boardFunc.detail.delComment(commentEle),
            confirm_modal.off,
            "삭제",
            "취소"
        );

    });

    $(document).on("click", "#save-comment-btn", function (){

        boardFunc.detail.insertComment();

    });

    $(document).on("click", "#del-btn", function (){

        let bno = document.querySelector(".content-detail").dataset.bno;

        confirm_modal.on(
            "게시글",
            "게시글을 삭제 하시겠습니까?",
            () => boardFunc.detail.delBoard(bno),
            confirm_modal.off,
            "삭제",
            "취소"
        );

    });

    $(document).on("click", "#edit-btn", function (){

        let bno = document.querySelector(".content-detail").dataset.bno;

        location.href = "/board/edit/" + bno;

    });

});