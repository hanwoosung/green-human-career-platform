import {boardFunc} from "/static/js/board/boardFunc.js";
import {common} from "/static/js/common.js";

$(function () {

    let files = [];

    tinymce.init({
        selector: "#textarea", // TinyMCE를 적용할 textarea 요소의 선택자를 지정
        plugins: "paste image", // 'paste', 'image', 'imagetools' 플러그인 추가
        height: 500,
        width: '100%',
        menubar: false,
        toolbar: "undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | outdent indent | image", // 'image' 버튼 툴바에 추가
        // images_reuse_filename: true,
        // paste_data_images: true, // 이미지 붙여넣기 설정 활성화
        file_picker_types: 'file', // TinyMCE에서 이미지를 선택할 때, 이미지 파일만 선택 (옵션 : media, file 등)

        images_upload_handler(blobInfo, success, failure) { // 이미지를 업로드하는 핸들러 함수
            // blobInfo : TinyMCE에서 이미지 업로드 시 사용되는 정보를 담고 있는 객체

            const file = new File([blobInfo.blob()], blobInfo.filename());
            const fileName = blobInfo.filename();

            console.log(file);
            console.log(fileName);

            let formData = new FormData();
            formData.set("file", file);

            mobileUpload(formData, {
                callback: function (data) {
                    if (data.result.code == 200) {

                        files.push(data.data);
                        success(data.data);  // TinyMCE에 성공적으로 이미지 URL 전달

                    }
                }
            });


        }
    });

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