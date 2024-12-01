$(document).ready(function () {
    const urlParams = new URLSearchParams(window.location.search);
    const selectedSkills = urlParams.get('skills') ? urlParams.get('skills').split(",") : [];


    selectedSkills.forEach(function (skill) {
        $(`input[name='skills'][value='${skill}']`).prop("checked", true);
    });

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
    $("#select-all").change(function () {
        const isChecked = $(this).prop("checked");
        const activeCategory = $(".category-btn.active").data("category");
        $(`.skill-item[data-category="${activeCategory}"] input[type="checkbox"]`).prop("checked", isChecked);
        updateSelectedSkill();
    });

    // 선택된 스킬 목록 업데이트
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
        const totalCheckboxes = $(`.skill-item[data-category="${activeCategory}"] input[type="checkbox"]`).length;
        const checkedCheckboxes = $(`.skill-item[data-category="${activeCategory}"] input[type="checkbox"]:checked`).length;

        if (totalCheckboxes === checkedCheckboxes) {
            $("#select-all").prop("checked", true);
        } else {
            $("#select-all").prop("checked", false);
        }
    }

    // 초기화 버튼
    $("#reset-btn").click(function () {

        $("input[type='checkbox']").prop("checked", false);

        updateSelectedSkill();

        updateSelectAllCheckbox();

        $(".skill-item").not(".select-all").hide();
        $(".skill-item[data-category='frontend']").show();

        $(".category-btn").removeClass("active");
        $(".category-btn[data-category='frontend']").addClass("active");

        $("#search").val("");

        updateSelectAllCheckbox();
    });


    // 즐겨찾기 아이콘
    $(".scrap-icon").click(function () {
        $(this).toggleClass("bi-bookmark");
        $(this).toggleClass("bi-bookmark-fill");
    });

    // 하트 아이콘
    $(".heart-icon").click(function () {
        $(this).toggleClass("bi-heart");
        $(this).toggleClass("bi-heart-fill");
    });

    // 검색 버튼
    $("#search-btn").click(function () {
        const searchText = $("#search").val();
        const selectedSkills = $("input[name='skills']:checked").map(function () {
            return this.value;
        }).get();

        window.location.href = "/?search=" + encodeURIComponent(searchText) + "&skills=" + encodeURIComponent(selectedSkills.join(","));
    });
});
