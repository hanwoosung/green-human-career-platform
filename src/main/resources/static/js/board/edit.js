import {boardFunc} from "/static/js/board/boardFunc.js";
import {common} from "/static/js/common.js";

$(function () {

    let files = [];

    $(document).on("click", "#save-btn", function () {

        let param = {
            title: document.querySelector("input[name=title]").value,
            content: tinymce.activeEditor.getContent(),
            bgbnCd: "F",
            banswerYn: "N",
            delYn: "N",
            files: files
        }

        axios.post("/board", param, {
            headers: {
                // "Content-Type": "multipart/form-data",
                "Content-Type": "application/json",
            },
        }).then((res) => {
            location.href = "/board";
            console.log(res);
        }).catch((error) => {
            console.log(error)
        });

    });

    $(document).on("click", "#edit-btn", function () {

        let param = {
            bNo: document.querySelector("input[name=bNo]").value,
            title: document.querySelector("input[name=title]").value,
            content: tinymce.activeEditor.getContent(),
            banswerYn: "N",
            delYn: "N",
            files: files
        }

        axios.put("/board", param, {
            headers: {
                // "Content-Type": "multipart/form-data",
                "Content-Type": "application/json",
            },
        }).then((res) => {
            location.href = "/board";
            console.log(res);
        }).catch((error) => {
            console.log(error)
        });

    });

    function mobileUpload(formData, options) {


        axios.post("/board/file", formData, {
            headers: {
                "Content-Type": "multipart/form-data",
                // "Content-Type": "application/json",
            },
        }).then((res) => {
            if (typeof (options.callback) === 'function') options.callback(res.data, options);
            // success(res.data.data);
            console.log(res);
        }).catch((error) => {
            console.log(error)
        });

    }

});