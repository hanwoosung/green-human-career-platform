export let common = {

    get: {

        dataById: (ids = [], required = "required", point = "#") => {

            let result = {
                result: true
            };

            let date_id = ["inst_dt", "updt_dt"];

            for (let id of ids) {
                let ele = document.querySelector(`${point}${id}`);

                if (ele == null) {

                    alert(`존재하지 않는 ${point}의 Element 입니다.`);
                    return result = {
                        result: false,
                    }
                }

                if (ele.classList.contains(required)) {
                    if (ele.value == "") {

                        ele?.focus();
                        alert(ele?.placeholder);

                        return result = {
                            result: false,
                            ele: ele
                        }
                    }
                }

                if (date_id.indexOf(id) > -1){
                    //날짜 id 체크
                    result[id] = new Date(ele.value);
                }else {
                    result[id] = ele.value;
                }

            }

            return result;
        },


    },


}