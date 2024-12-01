export let common = {

    get: {

        dataAllByName: (required = "required", formData = false) => {
            // name 속성을 기반으로 데이터 가져오기

            let output = {
                result: true, // 성공 여부
                value: {} // 수집된 데이터
            };

            let elements = document.querySelectorAll(`[name]`); // name 속성을 가진 모든 요소 선택
            let formDataObj = formData ? new FormData() : null; // formData가 true일 경우 FormData 객체 초기화

            for (let ele of elements) {
                let name = ele.getAttribute('name'); // name 속성 값 가져오기

                if (!output.value[name]) {
                    output.value[name] = ele.type === "checkbox" || ele.type === "radio" ? [] : null; // 초기화
                }

                // 필수 값 검증
                if (ele.classList.contains(required)) {
                    if (ele.value === "") {
                        // 값이 비어 있고 클래스에 ${required} 가 있을경우
                        ele?.focus();
                        alert(ele?.placeholder || `${name} 값이 필요합니다.`);

                        return {
                            result: false, // 실패 처리
                            ele: ele // 실패한 요소 추가
                        };
                    }
                }

                if (ele.type === "checkbox") {
                    // 체크박스인 경우 선택된 값만 처리
                    if (ele.checked) {
                        output.value[name].push(ele.value);
                        if (formDataObj) {
                            formDataObj.append(name, ele.value);
                        }
                    }
                } else if (ele.tagName === "SELECT" && ele.multiple) {
                    // 멀티 셀렉트 박스인 경우 선택된 값 배열 처리
                    let selectedValues = Array.from(ele.selectedOptions).map(option => option.value);
                    output.value[name] = selectedValues;
                    if (formDataObj) {
                        for (let val of selectedValues) {
                            formDataObj.append(name, val);
                        }
                    }
                } else if (ele.type === "date") {
                    // 날짜 필드 처리
                    let dateValue = new Date(ele.value);
                    if (Array.isArray(output.value[name])) {
                        output.value[name].push(dateValue); // 배열로 추가
                    } else if (output.value[name] !== null) {
                        output.value[name] = [output.value[name], dateValue]; // 배열로 변환
                    } else {
                        output.value[name] = dateValue;
                    }
                    if (formDataObj) {
                        formDataObj.append(name, ele.value);
                    }
                } else {
                    // 일반적인 경우 (단일 값 처리)
                    if (output.value[name] !== null) {
                        // 기존 값이 배열인 경우 처리
                        if (!Array.isArray(output.value[name])) output.value[name] = [output.value[name]];
                        output.value[name].push(ele.value);
                    } else {
                        output.value[name] = ele.value;
                    }
                    if (formDataObj) {
                        formDataObj.append(name, ele.value);
                    }
                }
            }

            // 체크박스/라디오에서 아무 값도 선택되지 않은 경우 빈 배열 제거
            for (let key in output.value) {
                if (Array.isArray(output.value[key]) && output.value[key].length === 0) {
                    output.value[key] = null; // 빈 값으로 처리
                }
            }

            // FormData가 활성화된 경우 추가
            if (formData) {
                output.formData = formDataObj;
            }

            return output;
        },

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
        }

    },


}