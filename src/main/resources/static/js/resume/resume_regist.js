//서버로갈 데이터준비
const temporaryDataStore = {
    createdBy: '',
    email: '',
    phone: '',
    birth: '',
    addr: '',
    title: '',
    careerCode: '',
    representativeYn: '',
    deleteYn: ''
    ,
    careers: [],
    educations: [],
    portfolios: [],
    qualifications: [],
    technicalStacks: [],
    treats: [],
    introduces: [],
    profilePhoto: null
};

//
$(document).ready(function (){
    // 사용자 기본 정보를 초기화
    temporaryDataStore.createdBy = $('#userId').val();
    temporaryDataStore.email = $('#userEmail').val();
    temporaryDataStore.phone = $('#userPhone').val();
    temporaryDataStore.birth = $('#userBirth').val();
    temporaryDataStore.addr = $('#userAddr').val();
    temporaryDataStore.careerCode = $('#careerCode').val();

    const $modal = $('#modal');
    const $modalTitle = $('#modal-title');

    const $photoEditIcon = $('#photoEditIcon');
    const $photoUploadInput = $('#photoUploadInput');
    const $photoPreview = $('#resumePhotoPreview');

    // 카테고리 버튼 클릭 이벤트
    $('.category-button').on('click', function () {
        // 모든 카테고리의 기술 스택을 숨기고, 선택한 카테고리만 보여주기
        $('.category-content').hide();
        const selectedCategory = $(this).data('category');
        $(`#category-${selectedCategory}`).show();

        // 모든 버튼을 비활성화하고 현재 클릭한 버튼을 활성화
        $('.category-button').removeClass('active');
        $(this).addClass('active');
    });
    // 기본적으로 첫 번째 카테고리를 선택한 상태로 시작
    $('.category-button:first').trigger('click');

    // 아이콘 클릭 시 파일 업로드 창 열기
    $photoEditIcon.on('click', function () {
        $photoUploadInput.click();
    });

    // 파일 선택 시 미리보기 업데이트 및 데이터 저장
    $photoUploadInput.on('change', function () {
        const file = this.files[0];

        if (file) {
            // 미리보기 업데이트
            const reader = new FileReader();
            reader.onload = function (e) {
                $photoPreview.attr('src', e.target.result);
            };
            reader.readAsDataURL(file);

            // 파일 데이터 저장
            temporaryDataStore.profilePhoto = file;
        }else {
            console.log('파일이 선택되지 않음');
        }
    });

    //이력서 추가입력 버튼 클릭 이벤트
    $('.open-button').on('click', function (e) {
        e.preventDefault();
        const formType = $(this).data('form-type'); // 버튼의 데이터 속성에서 타입 가져오기
        generateForm(formType, 'form-fields'); // 'form-fields' 컨테이너에 폼 생성

        // 모달 제목 업데이트
        const titleMap = {
            careers: '경력 추가',
            educations: '학력 추가',
            portfolios: '포트폴리오 추가',
            qualifications: '자격증 추가',
            technicalStacks: '기술 추가',
            treats: '우대사항 추가',
            introduces: '자기소개 추가'
        };
        $modalTitle.text(titleMap[formType] || '폼 추가');

        // 모달 열기
        $modal.show();
    });
    // 모달 닫기 이벤트
    $('#modal-cancel').on('click', function () {
        $modal.hide();
    });

});
// 폼 타입에 따른 입력 필드 정의
const formTemplates = {
    careers: [
        { label: "회사명", name: "rc_company_nm", type: "text", placeholder: "회사명을 입력하세요" },
        { label: "입사일", name: "rc_join_dt", type: "date" },
        { label: "퇴사일", name: "rc_out_dt", type: "date" },
        { label: "부서명", name: "rc_dmpt", type: "text", placeholder: "부서명을 입력하세요" },
        { label: "직책", name: "rc_pstn", type: "text", placeholder: "직책을 입력하세요" },
        { label: "담당업무", name: "rc_duties", type: "textarea", placeholder: "담당업무를 입력하세요" }
    ],
    educations: [
        {
            label: "학력선택",
            name: "re_gbn_cd",
            type: "select",
            options: [
                { value: "H", label: "고등학교"  },
                { value: "U", label: "대학교 이상" }
            ]
        },
        { label: "학교 이름", name: "re_school_nm", type: "text", placeholder: "학교 이름을 입력하세요" },
        { label: "전공", name: "re_major", type: "text", placeholder: "전공을 입력하세요" },
        { label: "성적", name: "re_score", type: "number", placeholder: "성적을 입력하세요" },
        { label: "입학 날짜", name: "re_indt", type: "date" },
        { label: "졸업 날짜", name: "re_outdt", type: "date" },
        { label: "편입 여부", name: "re_transfer_yn", type: "checkbox"},
    ],
    portfolios: [
        { label: "작업 시작 기간", name: "rp_str_dt", type: "date" },
        { label: "작업 종료 기간", name: "rp_end_dt", type: "date" },
        { label: "작업 URL", name: "rp_url", type: "text", placeholder: "URL을 입력하세요" },
        { label: "작업 인원", name: "rp_cnt", type: "number", placeholder: "작업 인원을 입력하세요" },
        { label: "작업 내용", name: "rp_content", type: "textarea", placeholder: "작업 내용을 입력하세요" },
        { label: "포트폴리오 첨부파일", name: "files", type: "file"}
    ],
    qualifications: [
        { label: "자격증 명", name: "rq_nm", type: "text", placeholder: "자격증 이름을 입력하세요" },
        { label: "취득일", name: "rq_dt", type: "date" },
        { label: "발급 기관", name: "rq_place", type: "text", placeholder: "발급 기관을 입력하세요" },
        { label: "합격 여부", name: "rq_gbn_cd", type: "select", options: [{ value: "P", label: "필기" }, { value: "S", label: "실기" }] },
    ],
    treats: [
        { label: "우대사항 코드", name: "prf_cd", type: "text", placeholder: "우대사항 코드를 입력하세요" },
        { label: "우대 세부사항", name: "rpr_content", type: "textarea", placeholder: "우대 세부사항을 입력하세요" }
    ],
    introduces: [
        { label: "자기소개 제목", name: "rm_title", type: "text", placeholder: "자기소개 제목을 입력하세요" },
        { label: "자기소개 내용", name: "rm_content", type: "textarea", placeholder: "자기소개 내용을 입력하세요" },
        { label: "자기소개 파일", name: "files", type: "file"}
    ]
};

function generateForm(templateKey, containerId) {
    const template = formTemplates[templateKey];
    if (!template) {
        console.error("템플릿이 존재하지 않습니다:", templateKey);
        return;
    }

    const $container = $(`#${containerId}`); // 컨테이너 ID로 대상 지정
    $container.empty(); // 기존 내용 초기화

    template.forEach(field => {
        const fieldWrapper = $('<div>').addClass('form-group');
        const label = $('<label>').attr('for', field.name).text(field.label);
        let input;

        //동적 폼요소 생성
        if (field.type === "textarea") {
            input = $('<textarea>').attr('name', field.name).attr('id', field.name).attr('placeholder', field.placeholder || '');
        } else if (field.type === "select") {
            input = $('<select>').attr('name', field.name).attr('id', field.name);
            field.options.forEach(option => {
                input.append($('<option>').attr('value', option.value).text(option.label));
            });
        } else if (field.type === "checkbox") {
            // 체크박스의 초기 값 설정
            input = $('<input>')
                .attr('type', 'checkbox')
                .attr('name', field.name)
                .attr('id', field.name);

            // 체크박스를 클릭했을 때 값을 "Y" 또는 "N"으로 설정
            input.on('change', function () {
                this.value = this.checked ? "Y" : "N";
            });

            // 기본 값 설정
            input.val("N");

        } else if (field.type === "file") {
            input = $('<input>').attr('type', 'file').attr('name', field.name).attr('id', field.name);
        }  else {
            input = $('<input>').attr('type', field.type).attr('name', field.name).attr('id', field.name).attr('placeholder', field.placeholder || '');
        }

        fieldWrapper.append(label).append(input);
        $container.append(fieldWrapper);
    });
}

//모달창 입력한거 페이지에 렌더링
function renderSection(data, sectionSelector, templateKey) {
    // 섹션 DOM 요소 선택
    const $section = $(sectionSelector);

    // 기존 섹션 데이터 초기화
    $section.empty();

    console.log(data);
    // 전달된 데이터를 기반으로 해당 섹션 렌더링
    data.forEach(item => {
        // 섹션별로 데이터 아이템 추가
        const $item = $('<div>').addClass('content-item').data('item-id', item.id);

        // templateKey를 사용해 해당 섹션에 맞는 필드 정보로 렌더링
        formTemplates[templateKey].forEach(field => {
            const fieldValue = item[field.name] || '';
            const $field = $('<p>').text(`${field.label}: ${fieldValue}`);
            $item.append($field);
        });

        // 수정 및 삭제 버튼 추가
        const $editButton = $('<button>').text('수정').addClass('edit-button').data('item-id', item.id);
        const $deleteButton = $('<button>').text('삭제').addClass('delete-button').data('item-id', item.id);

        $item.append($editButton, $deleteButton);
        $section.append($item);
    });
}

//수정버튼 실행
$(document).on('click', '.edit-button', function (event) {
    event.preventDefault();
    console.log('this>>',$(this).data('item-id'));
    const itemId = $(this).data('item-id');

    const formType = $(this).closest('.form-section').data('form-type');


    console.log(temporaryDataStore[formType]);
    const itemData = temporaryDataStore[formType].find(item => item.id === itemId);


    if (itemData) {
        // 모달 열기 및 데이터 채우기
        generateForm(formType, 'form-fields');
        const $formFields = $('#form-fields');
        formTemplates[formType].forEach(field => {
            $formFields.find(`[name=${field.name}]`).val(itemData[field.name] || '');
        });

        $('#modal').data('form-type', formType).data('item-id', itemId);
        $('#modal-title').text('수정');
        $('#modal').show();
    }
});
//삭제버튼 실행
$(document).on('click', '.delete-button', function (event) {
    event.preventDefault();
    console.log('this>>',$(this).data('item-id'));
    const itemId = $(this).data('item-id');
    const formType = $(this).closest('.form-section').data('form-type');
    console.log(formType);

    temporaryDataStore[formType] = temporaryDataStore[formType].filter(item => item.id !== itemId);

    renderSection(temporaryDataStore[formType], `.user-${formType}-info .form-content-list`, formType);
});
//모달열기
$('.open-button').on('click', function (e) {
    e.preventDefault();
    const formType = $(this).data('form-type'); // 버튼에서 formType 가져오기
    console.log(formType);
    $('#modal').data('form-type', formType);    // 모달에 formType 저장
    generateForm(formType, 'form-fields');      // 폼 생성
    // 모달 열기
    $('#modal').show();
});



// 기술 스택 체크박스 선택 이벤트 처리
$('.tech-stack-checkbox').on('change', function () {
    const stack_cd = $(this).val();

    if ($(this).is(':checked')) {
        // 선택된 기술 스택을 추가
        temporaryDataStore.technicalStacks.push({ stack_cd });
        console.log("기술 스택이 추가되었습니다:", stack_cd);
    } else {
        // 선택 해제된 기술 스택 제거
        temporaryDataStore.technicalStacks = temporaryDataStore.technicalStacks.filter(item => item.stack_cd !== stack_cd);
        console.log("기술 스택이 제거되었습니다:", stack_cd);
    }

    // 기술 스택 배열 출력 (디버깅 용도)
    console.log("현재 기술 스택 목록:", temporaryDataStore.technicalStacks);
});



//모달 폼 제출하기
$('#modal-submit').on('click', function (e) {
    e.preventDefault();

    const formType = $('#modal').data('form-type'); // 폼 타입 가져오기
    const formData = $('#dynamic-form').serializeArray(); // 폼 데이터를 배열로 직렬화
    const itemId = $('#modal').data('item-id'); // 수정 중인 ID (없으면 새로 추가)

    // 폼 데이터를 객체로 변환
    const formObject = {};
    formData.forEach(field => {
        formObject[field.name] = field.value;
    });

    // 파일 필드 처리
    formTemplates[formType].forEach(field => {
        if (field.type === "file") {
            const fileInput = $(`[name="${field.name}"]`)[0];
            if (fileInput && fileInput.files.length > 0) {
                formObject[field.name] = Array.from(fileInput.files); // 파일 데이터를 배열로 추가
            }
        }
    });

    if (itemId) {
        // 기존 데이터 업데이트
        const itemIndex = temporaryDataStore[formType].findIndex(item => item.id === itemId);
        if (itemIndex !== -1) {
            temporaryDataStore[formType][itemIndex] = { ...temporaryDataStore[formType][itemIndex], ...formObject };
        }
    } else {
        // 새로운 데이터 추가
        formObject.id = Date.now(); // 고유 ID 생성 (현재 시간 사용)
        if (!Array.isArray(temporaryDataStore[formType])) {
            temporaryDataStore[formType] = []; // 배열 초기화
        }
        temporaryDataStore[formType].push(formObject);
    }

    $('#modal').hide(); // 모달 닫기

    console.log('formType>>',formType)

    renderSection(temporaryDataStore[formType], `.user-${formType}-info .form-content-list`, formType); // 섹션 데이터 렌더링
});

$('.submit-resume').on('click', function (e) {
    e.preventDefault();
    const formData = new FormData();
    temporaryDataStore.title = $('#resume-title').val();

    // 사용자 정보를 제출 전에 갱신
    temporaryDataStore.careerCode = $('#careerCode').val();

    // 사용자 정보와 섹션 데이터를 포함한 전체 데이터를 JSON으로 변환
    const cleanedData = {
        ...temporaryDataStore,
        careers: temporaryDataStore.careers.map(({ id, ...rest }) => rest),
        educations: temporaryDataStore.educations.map(({ id, ...rest }) => rest),
        portfolios: temporaryDataStore.portfolios.map(({ id, files, ...rest }) => rest),
        qualifications: temporaryDataStore.qualifications.map(({ id, ...rest }) => rest),
        treats: temporaryDataStore.treats.map(({ id, ...rest }) => rest),
        introduces: temporaryDataStore.introduces.map(({ id, files, ...rest }) => rest),
    };

    // JSON 데이터를 `resumeData`로 추가
    formData.append('resumeData', JSON.stringify(cleanedData));

    // 프로필 사진 추가
    if (temporaryDataStore.profilePhoto) {
        formData.append('profilePhoto', temporaryDataStore.profilePhoto);
    }

    // 포트폴리오 및 자기소개서 파일 추가
    temporaryDataStore.portfolios.forEach((portfolio) => {
        if (portfolio.files && Array.isArray(portfolio.files)) {
            portfolio.files.forEach((file) => {
                formData.append('portfolioFiles', file);
            });
        }
    });

    temporaryDataStore.introduces.forEach((introduce) => {
        if (introduce.files && Array.isArray(introduce.files)) {
            introduce.files.forEach((file) => {
                formData.append('introduceMeFiles', file);
            });
        }
    });


    // Axios 요청
    axios.post('/resume/regist', formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    })
        .then(function (response) {
            console.log(cleanedData);
            alert('이력서가 저장되었습니다.');
            console.log('응답 데이터:', response.data);
        })
        .catch(function (error) {
            console.error('저장 실패:', error);
        });
});




















