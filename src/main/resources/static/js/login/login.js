const referer = document.referrer;
console.log('referer: ', referer);

document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();  // 폼 제출 기본 동작을 막음
    const id = document.getElementById('id').value;
    const pw = document.getElementById('pw').value;
    const userType = document.getElementById('userType').value;
    console.log('userType: ', userType);

    axios.post('/login', {
        id: id,
        pw: pw,
        userGbnCd:userType
    })
        .then(function(response) {
            console.log('서버 응답:', response);
            const responseMessage = document.getElementById('responseMessage');
            if (response.data.result.code === "200") {
                console.log('로그인 성공:', response.data.result);
                // window.location.href = '/';
                sessionStorage.setItem('userId', id);
                if(userType === "S" || userType === 'M') {
                    window.location.href = referer;
                }
                if(userType === "C") {
                    window.location.href = '/company';
                }

            } else {
                console.log('로그인 실패:', response.data.result);
                responseMessage.textContent = response.data.result.message;
            }
        })
        .catch(function(error) {
            console.error('로그인 실패:', error);

            const responseMessage = document.getElementById('responseMessage');
            responseMessage.style.color = 'red';

            if (error.response) {
                console.log('서버 응답 오류:', error.response);
                responseMessage.textContent = error.response.data.result.message || '알 수 없는 오류 발생';
            } else if (error.request) {
                console.log('서버에 요청을 보냈지만 응답이 없었습니다.', error.request);  // 요청은 했지만 응답이 없는 경우
                responseMessage.textContent = '서버에 요청을 보냈지만 응답이 없습니다. 나중에 다시 시도해 주세요.';
            } else {
                console.log('오류 발생:', error.message);  // 다른 오류들
                responseMessage.textContent = '서버와의 연결이 실패했습니다. 나중에 다시 시도해 주세요.';
            }
        });
});

document.addEventListener('DOMContentLoaded', () => {
    const jobseekerTab = document.getElementById('jobseeker-tab');
    const comTab = document.getElementById('com-tab');
    const userTypeInput = document.getElementById('userType');

    // 탭 클릭 이벤트 바인딩
    jobseekerTab.addEventListener('click', () => switchTab('S'));
    comTab.addEventListener('click', () => switchTab('C'));

    // 탭 전환 함수
    const switchTab = (type) => {
        if (type === 'S') {
            jobseekerTab.classList.add('active');
            comTab.classList.remove('active');
            userTypeInput.value = 'S';
        } else if (type === 'C') {
            comTab.classList.add('active');
            jobseekerTab.classList.remove('active');
            userTypeInput.value = 'C';
        }
    };
});

