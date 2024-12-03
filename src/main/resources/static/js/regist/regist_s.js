const yearSelect = document.getElementById('birthYear');
const monthSelect = document.getElementById('birthMonth');
const daySelect = document.getElementById('birthDay');

// 현재 날짜 기준으로 최대 년도 계산
const today = new Date();
const maxYear = today.getFullYear();
const minYear = 1900;

// 년도 추가
for (let year = maxYear; year >= minYear; year--) {
    const option = document.createElement('option');
    option.value = year;
    option.textContent = `${year}년`;
    yearSelect.appendChild(option);
}

// 월 추가
for (let month = 1; month <= 12; month++) {
    const option = document.createElement('option');
    option.value = month;
    option.textContent = `${month}월`;
    monthSelect.appendChild(option);
}

// 일 추가 함수
const populateDays = () => {
    const year = parseInt(yearSelect.value, 10);
    const month = parseInt(monthSelect.value, 10);
    const today = new Date();

    daySelect.innerHTML = '<option value="">일</option>';

    if (!isNaN(year) && !isNaN(month)) {
        const daysInMonth = new Date(year, month, 0).getDate();

        for (let day = 1; day <= daysInMonth; day++) {
            // 미래 날짜 제한
            if (
                year > today.getFullYear() || 
                (year === today.getFullYear() && month > today.getMonth() + 1) || 
                (year === today.getFullYear() && month === today.getMonth() + 1 && day > today.getDate())
            ) {
                break;
            }

            const option = document.createElement('option');
            option.value = day;
            option.textContent = `${day}일`;
            daySelect.appendChild(option);
        }
    }
};

// 월, 년도 선택 시 일 갱신
yearSelect.addEventListener('change', populateDays);
monthSelect.addEventListener('change', populateDays);

//-----------유효성검사--------------
let isIdValid = false;
let isPwValid = false;
let isPwCkValid = false;
let isEmailValid = false;
let isPhoneValid = false;
let isAddrValid = false;
let isZipCdValid = false;

//아이디 중복체크
function checkDuplicateId() {
    const id = $('#id').val().trim();
    const $idValidationMessage = $('#idValidationMessage');
    const $dupCheckButton = $('#id-dupCk');

    if (!id) {
        $idValidationMessage.text('아이디를 입력해주세요.');
        $dupCheckButton.val('false');
        return;
    }else if (!isIdValid) {
        $dupCheckButton.val('false');
        return;
    }

    axios.get(`/regist/s/ckDpId/${id}`)
        .then(function (response) {
            const isDuplicate = response.data.data; 
            console.log('isDuplicate: ', isDuplicate);
            if (!isDuplicate) {
                $idValidationMessage.text('사용 가능한 아이디입니다.').css('color', 'green');
                $dupCheckButton.val('true');
               
                return;
            } else if(isDuplicate){
                $idValidationMessage.text('이미 사용 중인 아이디입니다.').css('color', 'red');
                $dupCheckButton.val('false');
                return;
            } else {
                $idValidationMessage.text('서버 응답이 올바르지 않습니다. 다시 시도해주세요.').css('color', 'orange');
                $dupCheckButton.val('false');
                return;
            }
        })
        .catch(function (error) {
            console.error('아이디 중복검사 오류:', error);
            $idValidationMessage.text('중복검사 중 오류가 발생했습니다. 나중에 다시 시도해주세요.');
            $dupCheckButton.val('false');
        });
}
//다음 주소 api
function openZipSearch() {
    new daum.Postcode({
        oncomplete: function (data) {
            let addr = '';
            let extraAddr = ''; 

            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;

                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
            } else if (data.userSelectedType === 'J') { 
                addr = data.jibunAddress;
            }

            document.getElementById("addr").value = addr + extraAddr;

            document.getElementById("zipCd").value = data.zonecode;

            isAddrValid = true;
            isZipCdValid = true;

            document.getElementById("addr2").focus();
        }
    }).open();
}
//실시간 유효성검사
$(document).ready(function () {

    const idMessage = $('#idValidationMessage');
    const pwMessage = $('#pwValidationMessage');
    const pwCkMessage = $('#pwCkValidationMessage');
    const emailMessage = $('#emailValidationMessage');
    const birthMessage = $('#birthValidationMessage');
    const phoneMessage = $('#phoneValidationMessage');

    $('#id').on('input', () => {
        $('#id-dupCk').val('false');
        const idValue = $('#id').val().trim();
        const idRegex = /^[a-zA-Z0-9]+$/;
        if (idValue === "" || idValue.length < 5 || !idRegex.test(idValue)) {
            isIdValid = false;
            $('#idValidationMessage').text("아이디는 최소 5자 이상, 영문 또는 숫자만 가능합니다.").css('color', 'red');
        } else {
            isIdValid = true;
            $('#idValidationMessage').text("");
        }
    });

    $('#pw, #pwck').on('input', function () {
        const pwValue = $('#pw').val().trim();
        const pwCkValue = $('#pwck').val().trim();
        const pwRegex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*(),.?":{}|<>]).{8,}$/;
    
        // 비밀번호 유효성 검사
        if (!pwRegex.test(pwValue)) {
            isPwValid = false;
            $('#pwValidationMessage').text("비밀번호는 8자 이상, 문자, 숫자, 특수문자를 포함해야 합니다.").css('color', 'red');
        } else {
            isPwValid = true;
            $('#pwValidationMessage').text("사용 가능한 비밀번호입니다.").css('color', 'green');
        }
    
        // 비밀번호 확인 검사
        if (pwCkValue === "" || pwValue !== pwCkValue) {
            isPwCkValid = false;
            $('#pwCkValidationMessage').text("비밀번호가 일치하지 않습니다.").css('color', 'red');
        } else {
            isPwCkValid = true;
            $('#pwCkValidationMessage').text("비밀번호가 일치합니다.").css('color', 'green');
        }
        
    });

    $('#email').on('input', () => {
        const emailValue = $('#email').val().trim();
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!emailRegex.test(emailValue)) {
            isEmailValid = false;
            $('#emailValidationMessage').text("이메일 형식이 올바르지 않습니다.").css('color', 'red');
        } else {
            isEmailValid = true;
            emailMessage.text("");
        }
    });

    $('#phone').on('input', () => {
        const phoneValue = $('#phone').val().trim();
        const phoneRegex = /^\d{11}$/; // 숫자만 11자리
    
        if (!phoneRegex.test(phoneValue)) {
            isPhoneValid = false;
            $('#phoneValidationMessage').text("전화번호는 11자리 숫자로 입력해야 합니다.").css('color', 'red');
        } else {
            isPhoneValid = true;
            phoneMessage.text("");
        }
    });

    const validateBirthSelection = () => {
        if (!$('#birthYear').val() || !$('#birthMonth').val() || !$('#birthDay').val()) {
            birthMessage.text("생년월일을 모두 선택해주세요.").css('color', 'red');
        } else {
            birthMessage.text("");
        }
    };

    $('#birthYear, #birthMonth, #birthDay').on('change', validateBirthSelection);
});
// 빈값 및 유효성 검사 함수
function validateField(fieldId, fieldName, messageId, isValid) {
    const $field = $(fieldId);
    const $message = $(messageId);

    if ((isValid !== undefined && !isValid)|| $('#id-dupCk').val() === 'false') {
        // $message.text(`${fieldName}이(가) 유효하지 않습니다.`).css('color', 'red');
        $field.focus(); 
        return false;
    }else if($field.val().trim() === '') {
        $message.text(`${fieldName}은(는) 필수 항목입니다.`).css('color', 'red');
        $field.focus();
        return false;
    }else {
        $message.text('');
        return true;
    }
}
//빈값 유효성 검사 및 axios로 post요청 
$('#regist-s-form').on('submit',function(event){
    event.preventDefault();
    let noEmptyField = true;
   const fields = [
        { id: '#id', name: '아이디', messageId: '#idValidationMessage', isValid: isIdValid },
        { id: '#pw', name: '비밀번호', messageId: '#pwValidationMessage', isValid: isPwValid },
        { id: '#pwck', name: '비밀번호 확인', messageId: '#pwCkValidationMessage', isValid: isPwCkValid },
        { id: '#email', name: '이메일', messageId: '#emailValidationMessage', isValid: isEmailValid },
        { id: '#phone', name: '전화번호', messageId: '#phoneValidationMessage', isValid: isPhoneValid },
        { id: '#addr', name: '주소', messageId: '#addrValidationMessage', isValid: isAddrValid},
        { id: '#zipCd', name: '우편번호', messageId: '#zipValidationMessage', isValid: isZipCdValid}
    ];

    for (let field of fields) {
        if (!validateField(field.id, field.name, field.messageId, field.isValid)) {
            noEmptyField = false;
            return;
        }
    }

    const $yearSelect = $('#birthYear');
    const $monthSelect = $('#birthMonth');
    const $daySelect = $('#birthDay');
    const $birthMessage = $('#birthValidationMessage');
    const $termsCheckbox = $('#agreeTerms');
    const $form = $('#regist-s-form');

    //생년월일 검사
    if(!$yearSelect.val()||!$monthSelect.val()||!$daySelect.val()){
        $birthMessage.text('생년월일을 모두 선택해주세요.').css('color', 'red');
        if(noEmptyField){
            $yearSelect.focus();    
            noEmptyField = false;
        }
    }else{
        $birthMessage.text('');
    }

    console.log(`'$('#id-dupCk').val(): '`, $('#id-dupCk').val());
    if($('#id-dupCk').val() === 'false'){
        alert('아이디 중복검사를 완료해주세요.');
    }else if(!$termsCheckbox.is(':checked')){
        alert('개인정보 약관에 동의해야 합니다.');
        $termsCheckbox.focus();
    }else {
        const birthDate = `${$yearSelect.val()}-${String($monthSelect.val()).padStart(2,'0')}-${String($daySelect.val()).padStart(2,'0')}`;
        $('<input>',{
            type: 'hidden',
            name: 'birth',
            value: birthDate
        }).appendTo($form);
        console.log('birthDate: ', birthDate);
        
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


















