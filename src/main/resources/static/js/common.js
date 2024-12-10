export let common = {

    get: {

        dataById: (ids = [], required = "required", point = "#") => {
            // ID 기반으로 데이터 가져오기 ids에 배열로 넣으면 됨

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

                if (date_id.indexOf(id) > -1) {
                    //날짜 id 체크
                    result[id] = new Date(ele.value);
                } else {
                    result[id] = ele.value;
                }

            }

            return result;
        },


    },

    format: {

        dateObject(date) { //date 객체로 변환

            if (date instanceof Date) {
                return date;
            }

            const parsedDate = new Date(date);
            if (isNaN(parsedDate.getTime())) {
                throw new Error("유효하지 않은 날짜 형식입니다.");
            }

            return parsedDate;
        },

        date: (inputDate) => { // 2000-12-12 형식으로 변환
            const date = common.format.dateObject(inputDate);
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
            return `${year}-${month}-${day}`;
        },


        dateTime: (inputDate) => { // 2000-12-12 12:12:12 형식으로 변환
            const date = common.format.dateObject(inputDate);
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
            const hours = String(date.getHours()).padStart(2, '0');
            const minutes = String(date.getMinutes()).padStart(2, '0');
            const seconds = String(date.getSeconds()).padStart(2, '0');
            return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
        },

        time: (inputDate) => { //12:12:12 형식으로 변환
            const date = common.format.dateObject(inputDate);
            const hours = String(date.getHours()).padStart(2, '0');
            const minutes = String(date.getMinutes()).padStart(2, '0');
            const seconds = String(date.getSeconds()).padStart(2, '0');
            return `${hours}:${minutes}:${seconds}`;
        },

        dateTimeMillSecond: (inputDate) => { // 20001212121212 형식으로 변환
            const date = common.format.dateObject(inputDate);
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
            const hours = String(date.getHours()).padStart(2, '0');
            const minutes = String(date.getMinutes()).padStart(2, '0');
            const seconds = String(date.getSeconds()).padStart(2, '0');
            const mill = String(date.getMilliseconds()).padStart(2, '0');
            return `${year}${month}${day}${hours}${minutes}${seconds}${mill}`;
        },

    },

    chk: {

        empty: (data) => {
            //빈값이면 false 반환
            let result = true;

            if (data == null || data == undefined || data == ""){
                result = false;
            }

            return result;
        }

    }




}