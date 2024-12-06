import {common} from "/static/js/common.js";

$(document).ready(function () {
    // 스크랩 아이콘
    $(".scrap-icon").click(function () {

        let param = {
            cjNo: this.closest(".job-card").dataset.jno,
            flag: this.classList.contains("bi-bookmark"),
            lgbnCd: "S"
        }

        console.log(param);

        axios.post("/likes", param, {
            headers: {
                // "Content-Type": "multipart/form-data",
                "Content-Type": "application/json",
            },
        }).then((res) => {
            console.log(res);
            if (res.data.result.code == 200) {
                // $(this).toggleClass("bi-bookmark");
                // $(this).toggleClass("bi-bookmark-fill");
                location.reload();
            }else if(res.data.result.code == 455){
                alert_modal.on("로그인", "로그인후 진행해주세요");
            }
        }).catch((error) => {
            alert("스크랩 실패했습니다.");
            console.log(error)
        });


    });

    // 하트 아이콘
    $(".heart-icon").click(function () {

        let param = {
            cjNo: this.closest(".job-card").dataset.id,
            flag: this.classList.contains("bi-heart"),
            lgbnCd: "B"
        }

        console.log(param);

        axios.post("/likes", param, {
            headers: {
                // "Content-Type": "multipart/form-data",
                "Content-Type": "application/json",
            },
        }).then((res) => {
            console.log(res);
            if (res.data.result.code == 200) {
                $(this).toggleClass("bi-heart");
                $(this).toggleClass("bi-heart-fill");
            }else if(res.data.result.code == 455){
                alert_modal.on("로그인", "로그인후 진행해주세요");
            }
        }).catch((error) => {
            alert("북마크 실패했습니다.");
            console.log(error)
        });


    });


    $(document).on("click", ".job-card img", function (e) {
        // 클릭한 요소가 <i> 태그인지 확인
        if ($(e.target).is('i.scrap-icon')) {
            e.stopPropagation(); // 이벤트 버블링 방지
            return; // 함수 종료
        }

        let jno = this.closest(".job-card").dataset.jno;

        if (common.chk.empty(jno)) {
            location.href = "/job-open/detail/" + jno;
        } else {
            alert_modal.on("경고", "공고가 존재하지 않습니다.");
        }
    });
});
