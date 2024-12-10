$(function () {

    skills.drawSkills();

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

        skills.drawSkills();
    });

    $(document).on("click", ".offer-btn", function (e) {

        e.stopPropagation(); // 이벤트 버블링 방지

        confirm_modal.on(
            "제안",
            "입사 제안 하시겠습니까?",
            () => skills.offer(this),
            confirm_modal.off,
            "제안",
            "취소"
        );

    });

    $(document).on("click", "#search-btn", function () {
        location.href = skills.getUrl();
    });

    $(document).on("click", ".page-item", function () {

        let page = this.querySelector(".page-link").dataset.page;

        location.href = skills.getUrl(page);
    });

    $(document).on("click", ".selected-skill .close", function () {
        let stackCd = this.parentElement.dataset.stackcd;

        let checkBoxs = skills.getAllByList();
        for (let checkBox of checkBoxs) {
            let cd = checkBox.ele.closest(".skill-item").dataset.cd;

            if (cd == stackCd) {
                checkBox.ele.checked = false;
            }
        }

        skills.drawSkills();
    });

    $(document).on("click", ".card", function () {

        let jno = this.dataset.jno;
        console.log(jno);
        location.href = "/resume/" + jno;

    });


});

let skills = {
    getChkAll: () => {

        let result = {
            cd: [],
            nm: []
        };
        let stacks = document.querySelectorAll(".skill-item");

        for (let stack of stacks) {
            let input = stack.querySelector("input");

            if (input.checked) {
                result['cd'].push(input.value);
                result['nm'].push(stack.innerText);
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

    getAllByList: () => {

        let result = [];

        let stacks = document.querySelectorAll(".skill-item");
        for (let stack of stacks) {

            let data = {
                cd: stack.dataset.cd,
                checked: stack.querySelector("input").checked,
                ele: stack.querySelector("input")
            };

            result.push(data);
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
    },

    drawSkills: () => {
        let stacks = skills.getChkAll();
        let html = "";

        for (let i = 0; i < stacks.cd.length; i++) {
            html +=
                `<div class="selected-skill" data-stackcd="${stacks.cd[i]}">    
                    ${stacks.nm[i]}
                    <span class="close">×</span>
                </div>`
        }

        console.log(html);

        document.querySelector(".selected-skills").innerHTML = html;
    },

    getUrl: (page = document.querySelector(".page-item.active").querySelector(".page-link").dataset.page) => {
        const baseURL = '/company/jbsk-mngm/job-stack-offer';
        const url = new URL(baseURL, window.location.origin);

        let stacks = skills.getChkAll();
        let search = document.querySelector("#search").value;

        url.searchParams.append("page", page);


        for (let i = 0; i < stacks.cd.length; i++) {
            url.searchParams.append("stacks", stacks['cd'][i]);
            // url.searchParams.append("stackNms", stacks['nm'][i]);

        }

        url.searchParams.append("search", search);

        return url.toString();
    },

    offer: (ele) => {

        let param = {
            uid: ele.closest(".card").dataset.id
        }

        console.log(param);

        axios.post("/company/jbsk-mngm/job-stack-offer", param, {
            headers: {
                // "Content-Type": "multipart/form-data",
                "Content-Type": "application/json",
            },
        }).then((res) => {

            console.log(res);

            if (res.data.result.code != "200") {
                alert_modal.on("오류", res.data.result.message);
            }
        }).catch((error) => {
            alert_modal.on("제안에 실패 하였습니다.");
            console.log(error)
        });

    },

    offCheck: () => {

    }
}