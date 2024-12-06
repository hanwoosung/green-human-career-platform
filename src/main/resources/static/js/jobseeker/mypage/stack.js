import {common} from "/static/js/common.js";

$(document).ready(function () {
    const urlParams = new URLSearchParams(window.location.search);
    const selectedSkills = urlParams.get('skills') ? urlParams.get('skills').split(",") : [];

    selectedSkills.forEach(function (skill) {
        $(`input[name='skills'][value='${skill}']`).prop("checked", true);
    });

    // 페이지 로드 시 첫 번째 카테고리 활성화
    const firstCategory = $(".category-btn:first").data("category");
    $(".category-btn:first").addClass("active");

    // 모든 스킬 숨기기
    $(".skill-item").hide();
    $(".select-all").hide();

    // 첫 번째 카테고리 스킬만 표시
    $(`.skill-item[data-category="${firstCategory}"]`).show();
    $(`.select-all[data-category="${firstCategory}"]`).show();

    updateSelectedSkill();
    updateSelectAllCheckbox();

    $(".category-btn").click(function () {
        $(".category-btn").removeClass("active");
        $(this).addClass("active");

        const category = $(this).data("category");


        let selectedSkills = $("input[name='skills']:checked").map(function () {
            return $(this).val();
        }).get();

        $("input[type='checkbox']").prop("checked", false);
        $(".skill-item").hide();
        $(`.skill-item[data-category="${category}"]`).show();

        selectedSkills.forEach(function (skill) {
            $(`input[value="${skill}"]`).prop("checked", true);
        });

        if (category === "frontend" || category === "backend") {
            $(".select-all").show();
        }
        updateSelectAllCheckbox();
    });
    // 전체 선택 체크박스
    $(".select-all input[type='checkbox']").change(function () {
        const isChecked = $(this).prop("checked");
        const category = $(this).closest(".select-all").data("category");

        $(`.skill-item[data-category="${category}"] input[name='skills']`).prop("checked", isChecked);
        updateSelectedSkill();
    });

    // 체크박스 상태 변경 시 선택된 스킬 목록 업데이트
    $("input[name='skills']").change(function () {
        updateSelectedSkill();
        updateSelectAllCheckbox();
    });

    function updateSelectedSkill() {
        const selectedSkills = $("input[name='skills']:checked").map(function () {
            return this.value;
        }).get();

        // 선택된 스킬을 표시
        $(".selected-skills").empty();
        selectedSkills.forEach(function (skill) {
            $(".selected-skills").append(
                `<div class="selected-skill">${skill}<span class="close">&times;</span></div>`
            );
        });


        $(".selected-skill .close").click(function () {
            let skillText = $(this).parent().text().trim();
            skillText = skillText.replace('×', '').trim();

            const targetCheckbox = $(`input[name='skills'][value='${skillText}']`);

            if (targetCheckbox.length > 0) {

                targetCheckbox.prop("checked", false);

                updateSelectedSkill();
                updateSelectAllCheckbox();
            }
        });
    }

    // 체크박스 상태가 변경될 때마다 선택된 스킬 목록 업데이트
    $("input[name='skills']").change(function () {
        updateSelectedSkill();
        updateSelectAllCheckbox();
    });

    // 전체 선택 체크박스 상태 업데이트
    function updateSelectAllCheckbox() {
        const activeCategory = $(".category-btn.active").data("category");
        const totalCheckboxes = $(`.skill-item[data-category="${activeCategory}"] input[name='skills']`).length;
        const checkedCheckboxes = $(`.skill-item[data-category="${activeCategory}"] input[name='skills']:checked`).length;

        $(`#select-all-${activeCategory}`).prop("checked", totalCheckboxes === checkedCheckboxes);
    }


    // 초기화 버튼
    $("#reset-btn").click(function () {
        $("input[type='checkbox']").prop("checked", false);

        $(".skill-item").hide();
        $(".select-all").hide();

        const firstCategory = $(".category-btn:first").data("category");
        $(".category-btn").removeClass("active");
        $(".category-btn:first").addClass("active");

        $(`.skill-item[data-category="${firstCategory}"]`).show();
        $(`.select-all[data-category="${firstCategory}"]`).show();

        updateSelectedSkill();
        updateSelectAllCheckbox();
        $("#search").val("");
    });


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
                $(this).toggleClass("bi-bookmark");
                $(this).toggleClass("bi-bookmark-fill");
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

    // 검색 버튼
    $("#search-btn").click(function () {
        const searchText = $("#search").val();
        const selectedSkills = $("input[name='skills']:checked").map(function () {
            return this.value;
        }).get();

        window.location.href = "/job-seeker/my-page/stack?search=" + encodeURIComponent(searchText) + "&skills=" + encodeURIComponent(selectedSkills.join(","));
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
