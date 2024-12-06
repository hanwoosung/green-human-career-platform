export const adminFunc = {

    main: {

        updtStatus: (status = "P") => {

            let param = {
                status: status,
                id: adminFunc.main.getChkUser()
            }

            axios.post("/admin/status", param, {
                headers: {
                    "Content-Type": "application/json",
                },
            }).then((res) => {
                console.log(res);
                if (res.data.result.code == 200) {
                    if (param.status == "P"){
                        alert_modal.on("성공", "비밀번호 초기화 하였습니다.");
                    }else{
                        alert_modal.on("성공", "사용여부를 변경하였습니다.");
                    }

                    location.reload();
                } else if (res.data.result.code == 455) {
                    alert_modal.on("로그인", "로그인 후 진행해주세요");
                }else{
                    alert_modal.on("로그인", res.data.result.message);
                }
            }).catch((error) => {
                alert_modal.on("업데이트중 오류가 발생하였습니다.")
                console.log(error);
            });

        },

        getChkUser: () => {
            let result = [];

            let chkBoxs = document.querySelectorAll("input[type=checkbox]:checked");

            for (let chkBox of chkBoxs){
                result.push(chkBox.value);
            }

            return result
        },

        getUrl: (page = document.querySelector(".page-item.active").querySelector(".page-link").dataset.page) => {
            const baseURL = '/admin';
            const url = new URL(baseURL, window.location.origin);

            let toggle = document.querySelector(".toggles.on").dataset.usergbncd;
            console.log(toggle);
            // let search = document.querySelector("#search").value;

            url.searchParams.append("toggle", toggle);
            url.searchParams.append("page", page);

            // url.searchParams.append("search", search);

            return url.toString();
        },

        chgToggle: (ele) => {
            let toggles = document.querySelectorAll(".toggles");

            for (let toggle of toggles){
                toggle.classList.remove("on");
            }

            ele.classList.add("on");
        },

        chkAll: () => {

            let checkBoxs = document.querySelectorAll("input[name=userChk]");
            let checkedBoxs = document.querySelectorAll("input[name=userChk]:checked");

            let checkBoxAll = document.querySelector("input[name=userChkAll]");

            if (checkBoxs.length == checkedBoxs.length){
                checkBoxAll.checked = true;
            }else{
                checkBoxAll.checked = false;
            }

        }

    }

}