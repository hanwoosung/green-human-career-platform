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

});
