let educationIndex = 0; // 학력 항목 인덱스
const tempEducationData = []; // 임시 학력 데이터 저장

// 학력 항목 추가
$('#add-edu-info').on('click', function () {
    const newEducationEntry = $('#template-form').clone();  // 템플릿 폼을 복제
    newEducationEntry.removeAttr('id');  // 복제된 항목의 id 제거
    newEducationEntry.attr('data-index', educationIndex);
    newEducationEntry.css('display', 'block');  // 화면에 보이도록 변경
    $('#form-content-list').append(newEducationEntry);  // 복제된 학력 폼을 목록에 추가
    educationIndex++;
    console.log('학력 항목 추가됨:', newEducationEntry);  // 추가된 항목을 콘솔에 출력하여 확인
});

// 이벤트 위임을 통해 동적으로 생성된 요소에 이벤트 등록
$('#form-content-list').on('change', '.education-level', function () {
    const educationLevel = $(this).val();
    const newEducationEntry = $(this).closest('.education-entry');

    // 성적 필드 초기화
    newEducationEntry.find('input[name="score"]').remove();

    if (educationLevel === 'H') {
        newEducationEntry.find('.highschool-fields').slideDown();
        newEducationEntry.find('.university-fields').hide();
    } else if (educationLevel === 'U') {
        newEducationEntry.find('.university-fields').slideDown();
        newEducationEntry.find('.highschool-fields').hide();
    } else {
        newEducationEntry.find('.highschool-fields, .university-fields').hide();
    }
});

$('#form-content-list').on('click', '.add-score', function () {
    const newEducationEntry = $(this).closest('.education-entry');
    const scoreInput = newEducationEntry.find('input[name="score"]');
    if (scoreInput.length === 0) {
        const inputHtml = `<input type="text" name="score" placeholder="성적" style="margin-top: 5px;" />`;
        $(this).after(inputHtml);
    } else {
        scoreInput.remove();
    }
});

// 확인 버튼 이벤트
$('#form-content-list').on('click', '.confirm-education', function () {
    const newEducationEntry = $(this).closest('.education-entry');
    const index = newEducationEntry.attr('data-index');
    const educationLevel = newEducationEntry.find('.education-level').val();

    const educationEntry = {
        educationLevel: educationLevel,
        schoolName: newEducationEntry.find('input[name="schoolName"]').val() || null,
        major: newEducationEntry.find('input[name="major"], select[name="major"]').val(),
        region: newEducationEntry.find('select[name="region"]').val(),
        admissionDate: newEducationEntry.find('input[name="admissionDate"]').val() + '-01',
        graduationDate: newEducationEntry.find('input[name="graduationDate"]').val() + '-01',
        transferYn: newEducationEntry.find('input[name="transferYn"]').is(':checked') ? 'Y' : 'N',
        degreeType: educationLevel === 'U' ? newEducationEntry.find('select[name="degreeType"]').val() : null,
        score: newEducationEntry.find('input[name="score"]').val() || null,
    };

    tempEducationData[index] = educationEntry;

    newEducationEntry.html(`
        <div class="education-summary">
            <p><strong>학력 구분:</strong> ${educationLevel === 'H' ? '고등학교' : '대학교 이상'}</p>
            <p><strong>학교명:</strong> ${educationEntry.schoolName || 'N/A'}</p>
            <p><strong>전공:</strong> ${educationEntry.major}</p>
            <p><strong>지역:</strong> ${educationEntry.region}</p>
            <p><strong>입학:</strong> ${educationEntry.admissionDate.slice(0, 7)}</p>
            <p><strong>졸업:</strong> ${educationEntry.graduationDate.slice(0, 7)}</p>
            <p><strong>성적:</strong> ${educationEntry.score || '미입력'}</p>
            ${educationLevel === 'U' ? `<p><strong>편입 여부:</strong> ${educationEntry.transferYn === 'Y' ? '편입' : '비편입'}</p>` : ''}
            <button type="button" class="edit-education">수정</button>
            <button type="button" class="delete-education">삭제</button>
        </div>
    `);
});

// 수정 버튼 이벤트
$('#form-content-list').on('click', '.edit-education', function () {
    const entryElement = $(this).closest('.education-entry');
    const index = entryElement.attr('data-index');
    const educationData = tempEducationData[index];
    // 원래 데이터를 임시로 저장해둠 (취소 시 복구를 위해)
    const originalHtml = entryElement.html();
    entryElement.html(`
        <div class="education-form">
            <label>학력 구분:</label>
            <select class="education-level" name="educationLevel">
                <option value="H" ${educationData.educationLevel === 'H' ? 'selected' : ''}>고등학교</option>
                <option value="U" ${educationData.educationLevel === 'U' ? 'selected' : ''}>대학교 이상</option>
            </select>

            <label>학교 이름:</label>
            <input type="text" name="schoolName" value="${educationData.schoolName || ''}" />

            <label>전공:</label>
            <input type="text" name="major" value="${educationData.major || ''}" />

            <label>지역:</label>
            <select name="region">
                <option value="서울" ${educationData.region === '서울' ? 'selected' : ''}>서울</option>
                <option value="경기" ${educationData.region === '경기' ? 'selected' : ''}>경기</option>
                <option value="부산" ${educationData.region === '부산' ? 'selected' : ''}>부산</option>
                <option value="기타" ${educationData.region === '기타' ? 'selected' : ''}>기타</option>
            </select>

            <label>입학 연월:</label>
            <input type="month" name="admissionDate" value="${educationData.admissionDate.slice(0, 7)}" />

            <label>졸업 연월:</label>
            <input type="month" name="graduationDate" value="${educationData.graduationDate.slice(0, 7)}" />

            ${educationData.educationLevel === 'U' ? `
                <label>편입 여부:</label>
                <input type="checkbox" name="transferYn" ${educationData.transferYn === 'Y' ? 'checked' : ''} />

                <label>학위 구분:</label>
                <select name="degreeType">
                    <option value="4년제" ${educationData.degreeType === '4년제' ? 'selected' : ''}>4년제</option>
                    <option value="전문대" ${educationData.degreeType === '전문대' ? 'selected' : ''}>전문대</option>
                    <option value="석사" ${educationData.degreeType === '석사' ? 'selected' : ''}>석사</option>
                    <option value="박사" ${educationData.degreeType === '박사' ? 'selected' : ''}>박사</option>
                </select>
            ` : ''}

            <label>성적:</label>
            <input type="text" name="score" value="${educationData.score || ''}" />

            <div class="buttons">
                <button type="button" class="confirm-education">확인</button>
                <button type="button" class="cancel-education">취소</button>
            </div>
        </div>
    `);
    // 취소 버튼 이벤트
    entryElement.find('.cancel-education').on('click', function () {
        entryElement.html(originalHtml);
    });

});

// 삭제 버튼 이벤트
$('#form-content-list').on('click', '.delete-education', function () {
    const entryElement = $(this).closest('.education-entry');
    const index = entryElement.attr('data-index');
    delete tempEducationData[index];
    entryElement.remove();
});


// 이력서 제출 버튼 클릭 시 이벤트
// 이력서 제출 버튼 이벤트
$('#submit-resume').on('click', function (event) {
    event.preventDefault(); // 기본 제출 동작 방지

    // 학력 데이터를 수집
    const educations = [];
    $('#form-content-list .education-entry').each(function () {
        const educationLevel = $(this).find('.education-level').val();
        const schoolName = $(this).find('input[name="schoolName"]').val();
        const major = $(this).find('input[name="major"]').val() || $(this).find('select[name="major"]').val();
        const region = $(this).find('select[name="region"]').val();
        const admissionDate = $(this).find('input[name="admissionDate"]').val();
        const graduationDate = $(this).find('input[name="graduationDate"]').val();

        console.log( $(this))


        // 날짜 값이 비어 있을 경우 null로 설정
        const formattedAdmissionDate = admissionDate ? `${admissionDate}-01` : null;
        const formattedGraduationDate = graduationDate ? `${graduationDate}-01` : null;

        const transferYn = $(this).find('input[name="transferYn"]').is(':checked') ? 'Y' : 'N';
        const degreeType = educationLevel === 'U' ? $(this).find('select[name="degreeType"]').val() : null;
        const score = $(this).find('input[name="score"]').val();

        // 학력 데이터를 JSON 객체로 변환
        const education = {
            educationLevel,
            schoolName,
            major,
            region,
            admissionDate: formattedAdmissionDate,
            graduationDate: formattedGraduationDate,
            transferYn,
            degreeType,
            score: score ? parseFloat(score) : null // 성적을 숫자로 변환
        };

        educations.push(education); // 학력 데이터를 배열에 추가
    });

    // 기본 사용자 정보를 수집
    const resumeData = {
        resumeId: null, // 필요 시 ID를 추가 (신규 작성 시 null)
        name: $('#userName').val(),
        email: $('#userEmail').val(),
        phone: $('#userPhone').val(),
        birth: $('#userBirth').val(),
        addr: $('#userAddr').val(),
        title: "사용자가 지정한 제목",
        representativeYn: "N",
        careerCode: $('#resume-form select[name="careerCode"]').val(),
        deleteYn: "N",
        createdBy: "사용자 ID",
        createdDate: null,
        updatedBy: "사용자 ID",
        updatedDate: null,
        educations: educations,
        careers: [],
        qualifications: [],
        portfolios: [],
        technicalStacks: []
    };

    console.log('Submitting the following JSON data:');
    console.log(JSON.stringify(resumeData, null, 2));

    // Axios로 서버에 POST 요청
    axios.post('/resume/regist', resumeData, {
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then((response) => {
            console.log(response.data);
            alert('이력서가 성공적으로 제출되었습니다!');
        })
        .catch((error) => {
            console.error('이력서 제출 중 오류 발생:', error);
            alert('이력서 제출에 실패했습니다.');
        });
});
