import {common} from "/static/js/common.js";

$(document).ready(function () {
    // 스크랩 아이콘
    $(".scrap-icon").click(function (e) {

        e.stopPropagation();

        confirm_modal.on(
            "스크랩",
            "스크랩 상태를 변경 하시겠습니까?",
            () => likes.scrap(this)
        );

    });

    // 하트 아이콘
    $(".heart-icon").click(function () {

        confirm_modal.on(
            "북마크",
            "북마크 상태를 변경 하시겠습니까?",
            () => likes.bookmark(this)
        );

    });


    $(document).on("click", ".job-card img", function (e) {
        // 클릭한 요소가 <i> 태그인지 확인
        let jno = this.closest(".job-card").dataset.jno;
        console.log(jno);
        console.log(common.chk.empty(jno));
        if (common.chk.empty(jno)) {
            location.href = "/job-open/detail/" + jno;
        } else {
            alert_modal.on("경고", "공고가 존재하지 않습니다.");
        }
    });
});

const likes = {

    bookmark: (ele) => {

        let param = {
            cjNo: ele.closest(".job-card").dataset.id,
            flag: ele.classList.contains("bi-heart"),
            lgbnCd: "B"
        }

        axios.post("/likes", param, {
            headers: {
                // "Content-Type": "multipart/form-data",
                "Content-Type": "application/json",
            },
        }).then((res) => {
            console.log(res);
            if (res.data.result.code == 200) {
                this.classList.toggle("bi-heart");
                this.classList.toggle("bi-heart-fill");
            } else if (res.data.result.code == 455) {
                alert_modal.on("로그인", "로그인후 진행해주세요");
            }
        }).catch((error) => {
            alert("북마크 실패했습니다.");
            console.log(error)
        });

    },

    scrap: (ele) => {

        let param = {
            cjNo: ele.closest(".job-card").dataset.jno,
            flag: ele.classList.contains("bi-bookmark"),
            lgbnCd: "S"
        }

        axios.post("/likes", param, {
            headers: {
                // "Content-Type": "multipart/form-data",
                "Content-Type": "application/json",
            },
        }).then((res) => {
            console.log(res);
            if (res.data.result.code == 200) {
                location.reload();
            } else if (res.data.result.code == 455) {
                alert_modal.on("로그인", "로그인후 진행해주세요");
            }
        }).catch((error) => {
            alert("스크랩 실패했습니다.");
            console.log(error)
        });

    }

}

