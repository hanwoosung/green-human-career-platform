$(document).ready(function () {
    $('#id').on('input', function () {
        validateStatus.isIdDuplicateChecked = false;
        validateId();
    });
    $('#pw, #pwck').on('input', validatePassword);
    $('#email').on('input', validateEmail);
    $('#phone').on('input', validatePhone);
    $('#birthYear, #birthMonth, #birthDay').on('change', validateBirthSelection);
});


//-----------유효성검사 상태 관리--------------
let validateStatus= {
    isIdValid: false,
    isPwValid: false,
    isPwCkValid: false,
    isNameValid: false,
    isEmailValid: false,
    isPhoneValid: false,
    isAddrValid: false,
    isZipCdValid: false,
};

function updateValidationMessage(element, message, isValid) {
    element.text(message);
    element.css('color', isValid ? 'green' : 'red');
}

function validateId() {
    const id = $('#id').val();
    const containsSpace = /\s/.test(id);
    const isValid = /^[a-zA-Z0-9]{5,}$/.test(id) && !containsSpace;
    validateStatus.isIdValid = isValid;
    
    if (!isValid) {
        const message = containsSpace ? "아이디에는 공백이 포함될 수 없습니다." : "아이디는 최소 5자 이상, 영문 또는 숫자만 가능합니다.";
        updateValidationMessage($('#idValidationMessage'), message, false);
        validateStatus.isIdDuplicateChecked = false; // 중복 확인 리셋
    } else {
        updateValidationMessage($('#idValidationMessage'), "", true);
    }
    return isValid;
}

// $('#id-dupCk').on('click', function () {
//     const id = $('#id').val();
//     if (!validateStatus.isIdValid) {
//         updateValidationMessage($('#idValidationMessage'), "유효한 아이디를 입력한 후 중복 확인을 해주세요.", false);
//         return;
//     }
//
//     axios.get(`/regist/s/ckDpId/${id}`)
//         .then(function (response) {
//             const isDuplicate = response.data.data;
//             if (!isDuplicate) {
//                 updateValidationMessage($('#idValidationMessage'), "사용 가능한 아이디입니다.", true);
//                 validateStatus.isIdDuplicateChecked = true;
//             } else {
//                 updateValidationMessage($('#idValidationMessage'), "이미 사용 중인 아이디입니다.", false);
//                 validateStatus.isIdDuplicateChecked = false;
//             }
//         })
//         .catch(function (error) {
//             console.error('아이디 중복검사 오류:', error);
//             updateValidationMessage($('#idValidationMessage'), "중복 검사 중 오류가 발생했습니다. 나중에 다시 시도해주세요.", false);
//             validateStatus.isIdDuplicateChecked = false;
//         });
// });

function validatePassword() {
    const pw = $('#pw').val();
    const pwCk = $('#pwck').val();
    const containsSpace = /\s/.test(pw);
    const isPwValid = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*(),.?":{}|<>]).{8,}$/.test(pw) && !containsSpace; // 공백이 없어야 함
    const isPwCkValid = pw === pwCk && pwCk !== "";

    validateStatus.isPwValid = isPwValid;
    validateStatus.isPwCkValid = isPwCkValid;

    if (!isPwValid) {
        const message = containsSpace ? "비밀번호에는 공백이 포함될 수 없습니다." : "비밀번호는 8자 이상, 문자, 숫자, 특수문자를 포함해야 합니다.";
        updateValidationMessage($('#pwValidationMessage'), message, false);
    } else {
        updateValidationMessage($('#pwValidationMessage'), "사용 가능한 비밀번호입니다.", true);
    }

    if (!isPwCkValid) {
        updateValidationMessage($('#pwCkValidationMessage'), "비밀번호가 일치하지 않습니다.", false);
    } else {
        updateValidationMessage($('#pwCkValidationMessage'), "비밀번호가 일치합니다.", true);
    }

    return isPwValid && isPwCkValid;
}

function validateName() {
    const name = $('#name').val().trim();
    const isValid = name !== "";
    validateStatus.isNameValid = isValid;

    updateValidationMessage($('#nameValidationMessage'), isValid ? "" : "이름을 입력해주세요.", isValid);
    return isValid;
}

function validateEmail() {
    const email = $('#email').val().trim();
    const containsSpace = /\s/.test(email);
    console.log('containsSpace: ', containsSpace);
    const isValid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email) && !containsSpace;
    validateStatus.isEmailValid = isValid;

    if (!isValid) {
        const message = containsSpace ? "이메일에는 공백이 포함될 수 없습니다." : "이메일 형식이 올바르지 않습니다.";
        updateValidationMessage($('#emailValidationMessage'), message, false);
    } else {
        updateValidationMessage($('#emailValidationMessage'), "", true);
    }
    return isValid;
}

function validatePhone() {
    const phone = $('#phone').val();
    const containsSpace = /\s/.test(phone);
    const isValid = /^\d{11}$/.test(phone) && !containsSpace;
    validateStatus.isPhoneValid = isValid;

    if (!isValid) {
        const message = containsSpace ? "전화번호에는 공백이 포함될 수 없습니다." : "전화번호는 11자리 숫자로 입력해야 합니다.";
        updateValidationMessage($('#phoneValidationMessage'), message, false);
    } else {
        updateValidationMessage($('#phoneValidationMessage'), "", true);
    }
    return isValid;
}

function validateAddress() {
    const addr = $('#addr').val();
    const isValid = addr !== "";
    validateStatus.isAddrValid = isValid;
    updateValidationMessage($('#addrValidationMessage'), isValid ? "" : "주소를 입력해주세요.", isValid);
    return isValid;
}

function validateBirthSelection() {
    const isValid = $('#birthYear').val() && $('#birthMonth').val() && $('#birthDay').val();
    updateValidationMessage($('#birthValidationMessage'), isValid ? "" : "생년월일을 모두 선택해주세요.", isValid);
    return isValid;
}

//-----------폼 제출 유효성 검사-----------
$('#regist-s-form').on('submit', function (event) {
    event.preventDefault();

    const validationFunctions = [
        { validate: validateId, selector: '#id' },
        { validate: validatePassword, selector: '#pw' },
        { validate: validateEmail, selector: '#email' },
        { validate: validatePhone, selector: '#phone' },
        { validate: validateBirthSelection, selector: '#birthYear' }, // 연도로 포커스 이동
        { validate: validateAddress, selector: '#addr' },
    ];
    
    if (!validateStatus.isIdDuplicateChecked) {
        alert('아이디 중복검사를 완료해주세요.');
        $('#id').focus();
        return;
    }

    // 각 필드의 유효성 검사
    let allValid = true;
    //validate필드명() 함수가 **참(true)**이면 allValid의 현재 값은 유지
    // 유효성 검사 함수 배열의 모든 함수 실행
    for (const { validate, selector } of validationFunctions) {
        if (!validate()) {
            console.log('validate(): ', validate());
            $(selector).focus();
            allValid = false;
            return;
        }
    }

    
   if (!$('#agreeTerms').is(':checked')) {
        alert('개인정보 약관에 동의해야 합니다.');
        $('#agreeTerms').focus();
        return;
    }


    if (allValid) {
        const birthDate = `${$('#birthYear').val()}-${String($('#birthMonth').val()).padStart(2, '0')}-${String($('#birthDay').val()).padStart(2, '0')}`;
        const formData = {
            id: $('#id').val(),
            pw: $('#pw').val(),
            name: $('#name').val(),
            email: $('#email').val(),
            birth: birthDate,
            phone: $('#phone').val(),
            addr: $('#addr').val(),
            addr2: $('#addr2').val(),
            zipCd: $('#zipCd').val()
        };

        axios.post('/regist/s', formData)
            .then(function (response) {
                console.log('회원가입 성공:', response.data);
                window.location.href = '/login';
            })
            .catch(function (error) {
                if (error.response && error.response.status === 400) {
                    alert('잘못된 요청입니다. 입력값을 확인해주세요.');
                } else {
                    alert('서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
                }
            });
    }
});
//-----------주소 API 사용-----------
function openZipSearch() {
    new daum.Postcode({
        oncomplete: function (data) {
            let addr = data.userSelectedType === 'R' ? data.roadAddress : data.jibunAddress;
            let extraAddr = '';

            if (data.userSelectedType === 'R') {
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
            }

            $('#addr').val(addr + extraAddr);
            $('#zipCd').val(data.zonecode);
            validateStatus.isAddrValid = true;
            validateStatus.isZipCdValid = true;
            $('#addr2').focus();
        }
    }).open();
}

//---------날짜 select box 유효 날짜-------------

const $yearSelect = $('#birthYear');
const $monthSelect = $('#birthMonth');
const $daySelect = $('#birthDay');

// 현재 날짜 기준으로 최대 날짜 계산
const today = new Date();
const maxYear = today.getFullYear();
const maxMonth = today.getMonth() + 1;
const maxDay = today.getDate();
const minYear = 1900;

// 년도 추가
for (let year = maxYear; year >= minYear; year--) {
    $yearSelect.append($('<option>', { value: year, text: `${year}년` }));
}

// 월 추가
const populateMonths = () => {
    const selectedYear = parseInt($yearSelect.val(), 10);

    $monthSelect.empty().append($('<option>', { value: '', text: '월' }));

    const maxValidMonth = selectedYear === maxYear ? maxMonth : 12;

    for (let month = 1; month <= maxValidMonth; month++) {
        $monthSelect.append($('<option>', { value: month, text: `${month}월` }));
    }

    populateDays();
};

// 일 추가
const populateDays = () => {
    const selectedYear = parseInt($yearSelect.val(), 10);
    const selectedMonth = parseInt($monthSelect.val(), 10);

    $daySelect.empty().append($('<option>', { value: '', text: '일' }));

    if (!isNaN(selectedYear) && !isNaN(selectedMonth)) {
        const daysInMonth = new Date(selectedYear, selectedMonth, 0).getDate();
        const maxValidDay =
            selectedYear === maxYear && selectedMonth === maxMonth ? maxDay : daysInMonth;

        for (let day = 1; day <= maxValidDay; day++) {
            $daySelect.append($('<option>', { value: day, text: `${day}일` }));
        }
    }
};


$yearSelect.on('change', populateMonths);
$monthSelect.on('change', populateDays);
