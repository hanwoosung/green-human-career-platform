$(function () {

    $(document).on("click", ".category-btn", function () {
        let upSkill = this.dataset.cd;
        skills.chgStack(upSkill);
        skills.chkAll();
    });

    $(document).on("change", ".skill-item", function () {
        let cd = this.dataset.cd;
        if (cd == "") {
            skills.chkAll(true);
        } else {
            skills.chkAll();
        }
    });


    // TODO: 클릭시 들어가는 기능도 구현필요 현재 상세가 없어 안함.

    $(document).on("click", ".page-link", function () {

        let href = this.dataset.href;

        location.href = href + "&search=" + document.querySelector("input[name=search]").value;

    });


});

let skills = {
    getChkAll: () => {

        let result = [];
        let stacks = document.querySelectorAll(".skill-item.on");

        for (let stack of stacks) {
            let input = stack.querySelector("input");

            if (input.checked) {
                result.push(input.value);
            }
        }

        return result;
    },

    getAll: () => {

        let result = {};

        let categorys = document.querySelectorAll(".category-btn");
        for (let category of categorys) {
            result[category.dataset.cd] = [];
        }

        let stacks = document.querySelectorAll(".skill-item");
        for (let stack of stacks) {

            let data = {
                cd: stack.dataset.cd,
                checked: stack.querySelector("input").checked,
                ele: stack.querySelector("input")
            };

            result[stack.dataset.upcd].push(data);
        }

        return result;
    },

    chgStack: (upCd = "") => {

        let categorys = document.querySelectorAll(".category-btn");
        for (let category of categorys) {
            category.classList.remove("active");
            if (category.dataset.cd == upCd) {
                category.classList.add("active");
            }
        }

        let stacks = document.querySelectorAll(".skill-item");
        for (let stack of stacks) {

            if (!stack.classList.contains("select-all")) {
                if (upCd != "") {
                    stack.classList.add("off");
                    stack.classList.remove("on");
                }

                if (stack.dataset.upcd == upCd || upCd == "") {
                    stack.classList.remove("off");
                    stack.classList.add("on");
                }
            }
        }

    },

    chkAll: (flag = false) => {
        let stacks = document.querySelectorAll(".skill-item.on");
        let stackAll = document.querySelector(".skill-item.select-all").querySelector("input");

        if (!flag) {
            let actUpCd = document.querySelector(".category-btn.active");

            let chkStacks = document.querySelectorAll(".skill-item.on :checked");

            if (stacks.length == chkStacks.length) {
                stackAll.checked = true;
            } else {
                stackAll.checked = false;
            }
        } else {
            for (let stack of stacks) {
                let input = stack.querySelector("input");
                if (stackAll.checked) {
                    input.checked = true;
                } else {
                    input.checked = false;
                }
            }
        }
    }
}