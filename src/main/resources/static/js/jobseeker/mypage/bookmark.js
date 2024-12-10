$(function () {

    document.querySelector(".list-wrap .list").addEventListener("click", function (e) {

        e.preventDefault();
        let id = this.dataset.id;

        location.href = "/companyInfo/" + id;
    });

    // 하트 아이콘
    $(".heart-icon").click(function (e) {

        e.stopPropagation();

        confirm_modal.on(
            "북마크",
            "북마크 상태를 변경하시겠습니까?",
            () => doLike(this)
        );


    });

});

function doLike(ele) {

    let param = {
        cjNo: ele.closest(".list").dataset.id,
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
            location.reload();
        } else if (res.data.result.code == 455) {
            alert_modal.on("로그인", "로그인후 진행해주세요");
        }
    }).catch((error) => {
        alert("북마크 실패했습니다.");
        console.log(error)
    });

}