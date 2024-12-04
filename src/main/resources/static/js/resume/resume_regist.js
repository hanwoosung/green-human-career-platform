// 학력 데이터 저장용 배열
const tempEducationData = [];

// 모달 열기/닫기
const modal = document.getElementById('education-modal');
const closeModal = document.getElementById('close-modal');
const addEduInfoButton = document.getElementById('add-edu-info');

addEduInfoButton.addEventListener('click', () => {
    modal.style.display = 'block';
});

closeModal.addEventListener('click', () => {
    modal.style.display = 'none';
});

// 학력 저장
document.getElementById('save-education').addEventListener('click', () => {
    const educationData = {
        educationLevel: document.getElementById('educationLevel').value,
        schoolName: document.getElementById('schoolName').value,
        major: document.getElementById('major').value,
        region: document.getElementById('region').value,
        admissionDate: document.getElementById('admissionDate').value + '-01',
        graduationDate: document.getElementById('graduationDate').value + '-01',
        score: parseFloat(document.getElementById('score').value) || null,
        transferYn: document.getElementById('transferYn').checked ? 'Y' : 'N',
        degreeType: document.getElementById('degreeType').value || null,
    };

    tempEducationData.push(educationData);

    // 학력 리스트에 추가
    const educationList = document.getElementById('form-content-list');
    const educationEntry = document.createElement('div');
    educationEntry.classList.add('education-entry');
    educationEntry.innerHTML = `
        <p><strong>학력 구분:</strong> ${educationData.educationLevel === 'H' ? '고등학교' : '대학교 이상'}</p>
        <p><strong>학교 이름:</strong> ${educationData.schoolName}</p>
        <p><strong>전공:</strong> ${educationData.major}</p>
        <p><strong>지역:</strong> ${educationData.region}</p>
        <p><strong>입학:</strong> ${educationData.admissionDate.slice(0, 7)}</p>
        <p><strong>졸업:</strong> ${educationData.graduationDate.slice(0, 7)}</p>
        <p><strong>성적:</strong> ${educationData.score || '미입력'}</p>
        <p><strong>편입 여부:</strong> ${educationData.transferYn === 'Y' ? '편입' : '비편입'}</p>
        <button class="delete-education">삭제</button>
    `;
    educationList.appendChild(educationEntry);

    // 삭제 버튼 이벤트 추가
    educationEntry.querySelector('.delete-education').addEventListener('click', () => {
        educationList.removeChild(educationEntry);
    });

    modal.style.display = 'none'; // 모달 닫기
});

// 이력서 제출
document.getElementById('submit-resume').addEventListener('click', (event) => {
    event.preventDefault();

    const resumeData = {
        name: document.getElementById('userName').value,
        email: document.getElementById('userEmail').value,
        phone: document.getElementById('userPhone').value,
        birth: document.getElementById('userBirth').value,
        addr: document.getElementById('userAddr').value,
        educations: tempEducationData,
    };

    console.log('Submitting resume data:', JSON.stringify(resumeData, null, 2));

    // 서버로 전송
    axios.post('/resume/regist', resumeData, {
        headers: { 'Content-Type': 'application/json' },
    })
        .then((response) => {
            alert('이력서가 성공적으로 제출되었습니다!');
        })
        .catch((error) => {
            console.error('Error submitting resume:', error);
            alert('이력서 제출 중 오류가 발생했습니다.');
        });
});
