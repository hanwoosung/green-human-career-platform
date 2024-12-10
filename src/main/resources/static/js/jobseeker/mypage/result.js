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
                "Content-Type": "application/json",
            },
        }).then((res) => {
            console.log(res);
            if (res.data.result.code == 200) {
                $(this).toggleClass("bi-heart");
                $(this).toggleClass("bi-heart-fill");
                window.location.reload();
            } else if (res.data.result.code == 455) {
                alert_modal.on("로그인", "로그인후 진행해주세요");
            }
        }).catch((error) => {
            alert("북마크 실패했습니다.");
            console.log(error)
        });


    });

});

const inputRange = document.querySelector(".rate_range");
const activeRate = document.querySelector(".active_rate");
const rateValue = document.querySelector("#rate_value");
$(document).ready(function () {
    $(".rating-button").click(function () {
        document.querySelector("#rating-popup").classList.add("on");
    });

    const ratingInput = document.querySelector('.rating input');
    const ratingStar = document.querySelector('.rating_star');

    ratingInput.addEventListener('input', () => {
        ratingStar.style.width = `${ratingInput.value * 20}%`;
    });

    $(".rating_star").on('click', function (e) {
        const starWidth = e.offsetX / this.offsetWidth;
        ratingInput.value = Math.ceil(starWidth * 5);
        ratingStar.style.width = `${ratingInput.value * 20}%`;
    });

});

$("#confirm-rating").click(function () {
    const popup = document.querySelector("#rating-popup");
    const jobCard = document.querySelector(".job-card");
    const jno = jobCard ? jobCard.dataset.jno : null;
    const rNo = jobCard ? jobCard.dataset.rNo : null;
    const ratingValue = document.querySelector('.rate_range').value;
    const finalRating = ratingValue / 2;

    console.log('선택한 :', finalRating);

    axios.post("/job-seeker/my-page/rating", {
        rating: finalRating,
        jno: jno,
        rNo: rNo
    })
        .then((res) => {
            console.log(res.data);
            if (res.data.result.code == 455) {
                alert("로그인하세요");
            } else {
                alert("별점이 제출되었습니다.");
            }
            popup.classList.remove("on");
        })
        .catch((error) => {
            console.error("별점 제출 실패:", error);
            alert("이미 별점을 등록하셨습니다."); //TODO: 이거말도 안됨 나중에 수정해야함 서비스단에서 디비 중복이나 이런 캐치들이 안먹힘 찾아볼예정
            popup.classList.remove("on");
        });
});


inputRange.addEventListener("input", (event) => {
    const target = event.target;
    const range = target.value;
    const rate = range / 2;

    activeRate.style.width = `${range * 10}%`;
    rateValue.value = rate;
    console.log("#rate_value value : ", rateValue.value);
    document.querySelector(".range_text").innerHTML = rate + "점";
})