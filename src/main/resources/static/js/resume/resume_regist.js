
//서버로갈 데이터준비
const temporaryDataStore = {
    name: '',
    id: '',
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

    // userInfoData를 JSON으로 파싱하여 temporaryDataStore에 저장
    const userInfoData = $('#userInfoData').val();
    console.log('userInfoData:', userInfoData);
    const userInfo = userInfoData ? JSON.parse(userInfoData) : {};


    temporaryDataStore.name = userInfo.name;
    temporaryDataStore.id = userInfo.id;
    temporaryDataStore.createdBy = userInfo.createdBy;
    temporaryDataStore.email = userInfo.email;
    temporaryDataStore.phone = userInfo.phone;
    temporaryDataStore.birth = userInfo.birth;
    temporaryDataStore.addr = userInfo.addr;
    temporaryDataStore.title = userInfo.title;
    temporaryDataStore.careerCode = userInfo.careerCode;
    temporaryDataStore.educations = userInfo.educations || [];
    temporaryDataStore.careers = userInfo.careers || [];
    temporaryDataStore.portfolios = userInfo.portfolios || [];
    temporaryDataStore.qualifications = userInfo.qualifications || [];
    temporaryDataStore.technicalStacks = userInfo.technicalStacks || [];
    temporaryDataStore.treats = userInfo.treats || [];
    temporaryDataStore.introduces = userInfo.introduces || [];
    temporaryDataStore.profilePhoto = userInfo.profilePhoto || [];

    // 각 섹션에 데이터 반영
    renderSection(temporaryDataStore.educations, '.user-educations-info .form-content-list', 'educations');
    renderSection(temporaryDataStore.careers, '.user-careers-info .form-content-list', 'careers');
    renderSection(temporaryDataStore.portfolios, '.user-portfolios-info .form-content-list', 'portfolios');
    renderSection(temporaryDataStore.qualifications, '.user-qualifications-info .form-content-list', 'qualifications');
    renderSection(temporaryDataStore.technicalStacks, '.user-technicalStacks-info .form-content-list', 'technicalStacks');
    renderSection(temporaryDataStore.treats, '.user-treats-info .form-content-list', 'treats');
    renderSection(temporaryDataStore.introduces, '.user-introduces-info .form-content-list', 'introduces');

    updateProgress();

    console.log(temporaryDataStore);

    const $modal = $('#modal');
    const $modalTitle = $('#modal-title');

    const $photoEditIcon = $('#photoEditIcon');
    const $photoUploadInput = $('#photoUploadInput');
    const $photoPreview = $('#resumePhotoPreview');
 


    // 모달 폼에서 우대사항 옵션 설정
    const treatOptions = [];
    $('#treatOptionsSelect option').each(function () {
        if ($(this).val()) {
            treatOptions.push({
                value: $(this).val(),
                label: $(this).text()
            });
        }
    });
    // 오른쪽 이력서 섹션 가이드에서 각 섹션 항목 클릭 이벤트 추가
    $('.section-item').on('click', function() {
        const formType = $(this).data('form-type'); // 클릭한 항목에서 폼 타입 가져오기
        if (formType) {
            $('#modal').data('form-type', formType);    // 모달에 formType 저장
            generateForm(formType, 'form-fields');      // 폼 생성
            $('#modal-title').text($(this).text() + ' 추가'); // 모달 제목 설정
            // 모달 열기
            $('#modal').show();
        }
    });

    // 폼 템플릿에 우대사항을 설정
    formTemplates.treats = [
        {
            name: "prf_cd",
            type: "select",
            options: treatOptions
        },
        { name: "rpr_content", type: "textarea", placeholder: "우대 세부사항을 입력하세요" }
    ];


    // 카테고리 버튼 클릭 이벤트
    $('.category-button').on('click', function () {
        $('.category-button').removeClass('active');
        $(this).addClass('active');

        const selectedCategory = $(this).data('category');

        // 모든 카테고리 콘텐츠 숨기기
        $('.category-content').hide();

        // 선택된 카테고리의 기술 스택만 표시
        $(`#category-${selectedCategory}`).show();

        // 이미 선택된 기술 스택들의 체크박스 상태 복원
        temporaryDataStore.technicalStacks.forEach(stack => {
            $(`.tech-stack-checkbox[value="${stack.stack_cd}"]`).prop('checked', true);
        });
    });

    // 기본적으로 첫 번째 카테고리 선택
    const $firstCategory = $('.category-button:first');
    if ($firstCategory.length) {
        $firstCategory.addClass('active');
        const firstCategoryCode = $firstCategory.data('category');
        $(`#category-${firstCategoryCode}`).show();

        // 저장된 기술 스택들의 체크박스 상태 복원
        temporaryDataStore.technicalStacks.forEach(stack => {
            $(`.tech-stack-checkbox[value="${stack.stack_cd}"]`).prop('checked', true);
        });
    }

    // 기술 스택 렌더링 초기화
    renderTechnicalStacks();

    // 아이콘 클릭 시 파일 업로드 창 열기
    $photoEditIcon.on('click', function () {
        console.log('photoEditIcon clicked');
        $photoUploadInput.click();
    });

    // 파일 선택 시 미리보기 업데이트 및 데이터 저장
    $photoUploadInput.on('change', function () {
        const file = this.files[0];
        console.log('Selected file:', file); // 파일 선택 확인

        if (file) {
            // 미리보기 업데이트
            const reader = new FileReader();
            reader.onload = function (e) {
                $photoPreview.attr('src', e.target.result);
                console.log('Preview updated:', e.target.result); // 미리보기 업데이트 확인
            };
            reader.readAsDataURL(file);

            // 파일 데이터 저장
            temporaryDataStore.profilePhoto = file;
        } else {
            console.log('파일이 선택되지 않음');
        }
    });

    //이력서 추가입력 버튼 클릭 이벤트
    $('.open-button').on('click', function (e) {
        e.preventDefault();
        $('#form-fields').empty(); // 기존 폼 필드 초기화

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


    $(document).on('click', '#modal-cancel', function(e) {
        e.preventDefault();
        e.stopPropagation();
        console.log('모달 닫기 버튼 클릭됨');
        $('#modal').hide();
        $('#modal').removeData('item-id'); // itemId 초기화
    });

});

const treatOptions = /*[[${treatOptions}]]*/ [];
// 폼 타입에 따른 입력 필드 정의
const formTemplates = {
    careers: [
        { name: "rc_company_nm", type: "text", placeholder: "회사명을 입력하세요" },
        { label: "입사일" , name: "rc_join_dt", type: "date" },
        { label: "퇴사일" ,name: "rc_out_dt", type: "date" },
        { name: "rc_dmpt", type: "text", placeholder: "부서명을 입력하세요" },
        { name: "rc_pstn", type: "text", placeholder: "직책을 입력하세요" },
        { name: "rc_duties", type: "textarea", placeholder: "담당업무를 입력하세요" }
    ],
    educations: [
        {
            name: "re_gbn_cd",
            type: "select",
            options: [
                { value: "H", label: "고등학교" },
                { value: "U", label: "대졸" },
                { value: "S", label: "초대졸" },
                { value: "D", label: "석사" },
                { value: "J", label: "박사" }
            ]
        },
        { name: "re_school_nm", type: "text", placeholder: "학교 이름을 입력하세요" },
        { name: "re_major", type: "text", placeholder: "전공을 입력하세요" },
        { name: "re_score", type: "number", placeholder: "성적을 입력하세요" },
        { label: "입학일", name: "re_indt", type: "date" },
        { label: "졸업일", name: "re_outdt", type: "date" },
        { label: "편입" ,  name: "re_transfer_yn", type: "checkbox" , labelWhenChecked: "편입" }
    ],
    portfolios: [
        { label: "시작날짜" , name: "rp_str_dt", type: "date" },
        { label: "종료날짜" , name: "rp_end_dt", type: "date" },
        { name: "rp_url", type: "text", placeholder: "URL을 입력하세요" },
        { name: "rp_cnt", type: "number", placeholder: "작업 인원을 입력하세요" },
        { name: "rp_content", type: "textarea", placeholder: "작업 내용을 입력하세요" },
        { name: "files", type: "file" }
    ],
    qualifications: [
        { name: "rq_nm", type: "text", placeholder: "자격증 이름을 입력하세요" },
        { label: "합격일" , name: "rq_dt", type: "date" },
        { name: "rq_place", type: "text", placeholder: "발급 기관을 입력하세요" },
        { name: "rq_gbn_cd", type: "select", options: [{ value: "P", label: "필기" }, { value: "S", label: "실기" }] }
    ],
    treats: [
        { name: "prf_cd", type: "text", placeholder: "우대사항 코드를 입력하세요" },
        { name: "rpr_content", type: "textarea", placeholder: "우대 세부사항을 입력하세요" }
    ],
    introduces: [
        { name: "rm_title", type: "text", placeholder: "자기소개 제목을 입력하세요" },
        { name: "rm_content", type: "textarea", placeholder: "자기소개 내용을 입력하세요" },
        { name: "files", type: "file" }
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

        if (field.label) {
            const label = $('<label>')
                .attr('for', field.name) // 라벨과 입력 필드 연결
                .text(field.label);
            fieldWrapper.append(label);
        }

        let input;

        // 동적 폼요소 생성
        if (field.type === "textarea") {
            input = $('<textarea>')
                .attr('name', field.name)
                .attr('id', field.name)
                .attr('placeholder', field.placeholder || '');
        } else if (field.type === "select") {
            input = $('<select>')
                .attr('name', field.name)
                .attr('id', field.name);
            field.options.forEach(option => {
                input.append($('<option>')
                    .attr('value', option.value)
                    .text(option.label));
            });
        } else if (field.type === "checkbox") {
            // 체크박스의 초기 값 설정
            input = $('<input>')
                .attr('type', 'checkbox')
                .attr('name', field.name)
                .attr('id', field.name);
            // 기본 값 설정
            input.val("N");
            console.log(input.val())
            // 체크박스를 클릭했을 때 값을 "Y" 또는 "N"으로 설정
            input.on('change', function () {
                this.value = this.checked ? "Y" : "N";
            });


        } else if (field.type === "file") {
            input = $('<input>')
                .attr('type', 'file')
                .attr('name', field.name)
                .attr('id', field.name);
        } else {
            input = $('<input>')
                .attr('type', field.type)
                .attr('name', field.name)
                .attr('id', field.name)
                .attr('placeholder', field.placeholder || '');
        }

        fieldWrapper.append(input);
        $container.append(fieldWrapper);
    });
}

function getLabelForSelectValue(templateKey, fieldName, value) {
    const template = formTemplates[templateKey];
    if (!template) return value;

    const field = template.find(f => f.name === fieldName);
    if (!field) return value;

    // 선택 필드에 대한 라벨 처리
    if (field.type === 'select' && field.options) {
        const option = field.options.find(opt => opt.value === value);
        return option ? option.label : value;
    }

    // 체크박스 필드에 대한 라벨 처리
    if (field.type === 'checkbox' && value === 'Y' && field.labelWhenChecked) {
        return field.labelWhenChecked;
    }

    // 기본 반환값
    return value;
}


//모달창 입력한거 페이지에 렌더링
function renderSection(data, sectionSelector, templateKey) {
    // 섹션 DOM 요소 선택
    const $section = $(sectionSelector);

    console.log('this>>',$(this).data('item-id'));
    console.log('templateKey>>',templateKey);
    console.log('data>>',data);

    // 기존 섹션 데이터 초기화
    $section.empty();

    // 전달된 데이터를 기반으로 해당 섹션 렌더링
    data.forEach(item => {
        // 데이터 아이템을 하나의 행으로 표시할 컨테이너 div 생성
        const $item = $('<div>')
            .addClass('data-item')
            .data('item-id', item.id)
            .attr('data-form-type', templateKey); // 아이템에 폼 타입 추가

        // 주요 정보와 부속 정보를 렌더링하는 부분
        let mainInfo = '';
        let subInfo = [];
        let fileList = [];

        // templateKey를 사용해 해당 섹션에 맞는 필드 정보로 렌더링
        formTemplates[templateKey].forEach(field => {
            let fieldValue = item[field.name];
            if (fieldValue) {  // 필드 값이 있는 경우에만 출력
                // 선택 필드 또는 체크박스 필드인 경우 라벨 값으로 변환
                if (field.type === 'select' || field.type === 'checkbox') {
                    fieldValue = getLabelForSelectValue(templateKey, field.name, fieldValue);
                }

                if (field.name === 'rp_url' || field.name === 're_school_nm' || field.name === 'rc_company_nm') {
                    // 주요 정보로 표시 (예: 학교 이름, 회사명, URL 등)
                    mainInfo = fieldValue;
                } else if (field.type === 'file' && Array.isArray(fieldValue)) {
                    // 파일 목록 처리
                    fieldValue.forEach(file => {
                        fileList.push(file.name);
                    });
                } else {
                    // 나머지는 부속 정보로 간주
                    subInfo.push(`<span>${fieldValue}</span>`);
                }
            }
        });

        // 주요 정보 (큰 텍스트로 출력)
        if (mainInfo) {
            const $mainInfo = $('<div>').addClass('main-info').text(mainInfo);
            $item.append($mainInfo);
        }

        // 부속 정보 (작은 텍스트로 수평 정렬하여 출력)
        if (subInfo.length > 0) {
            const $subInfo = $('<div>').addClass('sub-info').html(subInfo.join(' '));
            $item.append($subInfo);
        }

        // 파일 목록 렌더링
        if (fileList.length > 0) {
            const $fileContainer = $('<div>').addClass('file-container');
            fileList.forEach(fileName => {
                const $fileItem = $('<div>').addClass('file-item').text(fileName);
                $fileContainer.append($fileItem);
            });
            $item.append($fileContainer);
        }

        // 수정 버튼 추가
        const $editButton = $('<button>')
            .addClass('edit-button')
            .data('item-id', item.id)
            .html('<i class="fas fa-edit"></i>'); // 아이콘을 사용해 수정 버튼을 시각적으로 표시
        $item.append($editButton);

        // 삭제 버튼 추가
        const $deleteButton = $('<button>')
            .addClass('delete-button')
            .data('item-id', item.id)
            .html('<i class="fas fa-trash"></i>'); // 아이콘을 사용해 삭제 버튼을 시각적으로 표시
        $item.append($deleteButton);

        // 섹션에 항목 추가
        $section.append($item);
    });

    // 섹션에 data-form-type 속성 추가
    $section.attr('data-form-type', templateKey);
}

//수정버튼 실행
$(document).on('click', '.edit-button', function (event) {
    event.preventDefault();

    $('#form-fields').empty(); // 기존 필드 초기화

    const itemId = $(this).data('item-id');
    const formType = $(this).closest('.form-content-list').data('form-type');
    const itemData = temporaryDataStore[formType].find(item => item.id === itemId);

    if (itemData) {
        generateForm(formType, 'form-fields');
        const $formFields = $('#form-fields');
        formTemplates[formType].forEach(field => {
            if (field.type !== 'file') {
                // 파일 필드는 렌더링하지 않음
                $formFields.find(`[name=${field.name}]`).val(itemData[field.name] || '');
            }
        });

        // 파일 필드에 대한 별도 처리
        if (itemData.files && Array.isArray(itemData.files)) {
            const existingFilesContainer = $('<div>')
                .addClass('existing-files')
                .text('기존 파일이 있습니다. 새 파일을 업로드하려면 선택하세요.');
            $formFields.append(existingFilesContainer);
        }

        $('#modal').data('form-type', formType).data('item-id', itemId).show();
    }
});


//삭제버튼 실행
// 삭제 버튼 실행
$(document).on('click', '.delete-button', function (event) {
    event.preventDefault();
    // 아이템 ID 가져오기
    const itemId = $(this).data('item-id');

    // formType 가져오기 - 삭제 버튼의 부모 요소에서 data-form-type 찾기
    const formType = $(this).closest('[data-form-type]').data('form-type');

    if (!formType) {
        console.error("삭제 버튼에 해당하는 섹션의 formType을 찾을 수 없습니다.");
        return;
    }

    console.log('itemId:', itemId);
    console.log('formType:', formType);

    // 데이터스토어에서 아이템 삭제
    temporaryDataStore[formType] = temporaryDataStore[formType].filter(item => item.id !== itemId);

    // 삭제 후 페이지 업데이트
    renderSection(temporaryDataStore[formType], `.user-${formType}-info .form-content-list`, formType);

    updateProgress();
});



//모달열기
$('.open-button').on('click', function (e) {
    e.preventDefault();

    $('#form-fields').empty(); // 기존 폼 필드 초기화
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
    updateProgress();
    renderTechnicalStacks();
});

// 기술 스택 렌더링 함수
function renderTechnicalStacks() {
    const $selectedContainer = $(".selected-tech-stacks");
    $selectedContainer.empty();

    temporaryDataStore.technicalStacks.forEach(stack => {
        const stackName = $(`input[value='${stack.stack_cd}']`).siblings('span').text();
        const $techElement = $(`
            <div class="selected-tech-stack" data-stack-cd="${stack.stack_cd}">
                ${stackName}
                <span class="close">&times;</span>
            </div>
        `);

        $selectedContainer.append($techElement);
    });

    // 삭제 버튼 이벤트 핸들러
    $(".selected-tech-stack .close").on('click', function() {
        const stackCd = $(this).parent().data('stack-cd');
        removeSelectedTechStack(stackCd);
    });
}

// 선택된 기술 스택 제거 함수
function removeSelectedTechStack(skillCode) {
    // 선택된 기술 스택 체크박스 해제
    $(`.tech-stack-checkbox[value="${skillCode}"]`).prop('checked', false);

    // 선택된 기술 스택 데이터에서 제거
    temporaryDataStore.technicalStacks = temporaryDataStore.technicalStacks.filter(item => item.stack_cd !== skillCode);
    console.log("기술 스택이 제거되었습니다:", skillCode);

    // 기술 스택 렌더링 업데이트
    renderTechnicalStacks();
    updateProgress();
}



/// 모달 폼 제출하기
$('#modal-submit').on('click', function (e) {
    e.preventDefault();

    // 현재 열려 있는 모달의 formType 가져오기
    const formType = $('#modal').data('form-type');
    const itemId = $('#modal').data('item-id'); // 수정 중인 ID (없으면 새로 추가)

    console.log('itemId>>>',itemId);

    // 폼 데이터를 배열로 직렬화
    const formData = $('#dynamic-form').serializeArray();

    // 필수 date 타입 필드 검증
    const requiredDateFields = formTemplates[formType].filter(field => field.type === 'date'); // 현재 폼에서 필수 date 타입 필드 가져오기
    for (const field of requiredDateFields) {
        const fieldValue = formData.find(item => item.name === field.name);
        if (!fieldValue || !fieldValue.value) {
            alert(`"${field.label || field.name}" 필드는 필수 항목입니다. 날짜를 입력해주세요.`);
            return; // 검증 실패 시 함수 종료
        }
    }

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
    }else {
        // 새로운 데이터 추가
        formObject.id = Date.now(); // 고유 ID 생성 (현재 시간 사용)
        if (!Array.isArray(temporaryDataStore[formType])) {
            temporaryDataStore[formType] = []; // 배열 초기화
        }
        temporaryDataStore[formType].push(formObject);
    }

    // 모달 닫기
    $('#modal').hide();
    $('#form-fields').empty(); // 폼 필드 초기화
    $('#modal').removeData('item-id'); // itemId 초기화

    // 진행도 업데이트
    updateProgress();

    // 섹션 데이터 렌더링
    renderSection(temporaryDataStore[formType], `.user-${formType}-info .form-content-list`, formType);

    // 디버깅용 로그
    console.log('폼 데이터 저장 완료:', temporaryDataStore[formType]);
    console.log('formType:', formType);
});



// 이력서 진행도 업데이트 함수
function updateProgress() {
    const totalSections = 7;  // 총 섹션 수
    let filledSections = 0;

    // 각 섹션이 채워졌는지 확인
    if (temporaryDataStore.educations.length > 0) filledSections++;
    if (temporaryDataStore.careers.length > 0) filledSections++;
    if (temporaryDataStore.technicalStacks.length > 0) filledSections++;
    if (temporaryDataStore.portfolios.length > 0) filledSections++;
    if (temporaryDataStore.qualifications.length > 0) filledSections++;
    if (temporaryDataStore.introduces.length > 0) filledSections++;
    if (temporaryDataStore.treats.length > 0) filledSections++;

    // 진행 퍼센트 계산
    const progressPercentage = Math.round((filledSections / totalSections) * 100);

    // 진행도 업데이트
    $('#progress-bar-inner')
        .css('width', `${progressPercentage}%`)
        .text(`${progressPercentage}%`);

    // 상태 메시지 업데이트
    if (progressPercentage === 100) {
        $('#resume-status').text('이력서 모든 폼이 작성되었습니다!');
    } else {
        $('#resume-status').text('이력서가 진행 중입니다!');
    }

    // 디버깅용 로그 추가
    console.log('진행도 업데이트:', progressPercentage, '% 완료');
}




$('.submit-resume').on('click', function (e) {
    e.preventDefault();
    const formData = new FormData();
    const mode = $('#resume-form').data('mode');
    const resumeId = $('#resume-form').data('resume-id');

    // 기존 데이터 유지하면서 수정된 내용 반영
    temporaryDataStore.title = $('#resume-title').val();
    temporaryDataStore.careerCode = $('#careerCode').val();

    if (mode === 'edit') {
        // 수정 모드일 때는 resumeId도 포함
        temporaryDataStore.resumeId = resumeId;
    }

    const cleanedData = {
        ...temporaryDataStore,
        careers: temporaryDataStore.careers.map(({ id, ...rest }) => rest),
        educations: temporaryDataStore.educations.map(({ id, ...rest }) => rest),
        portfolios: temporaryDataStore.portfolios.map(({ id, files, ...rest }) => rest),
        qualifications: temporaryDataStore.qualifications.map(({ id, ...rest }) => rest),
        treats: temporaryDataStore.treats.map(({ id, ...rest }) => rest),
        introduces: temporaryDataStore.introduces.map(({ id, files, ...rest }) => rest),
    };

    formData.append('resumeData', JSON.stringify(cleanedData));

    // 파일 처리
    if (temporaryDataStore.profilePhoto) {
        formData.append('profilePhoto', temporaryDataStore.profilePhoto);
    }

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

    const url = mode === 'edit' ? `/resume/${resumeId}` : '/resume/regist';
    const method = mode === 'edit' ? 'PUT' : 'POST';

    console.log('전송 데이터:', cleanedData);
    console.log('mode:', mode);
    console.log('method:', method);
    console.log('url:', url);

    axios({
        url: url,
        method: method,
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
        .then(function(response) {
            alert(mode === 'edit' ? '이력서가 수정되었습니다.' : '이력서가 저장되었습니다.');
            window.location.href = '/resume';
        })
        .catch(function(error) {
            console.error('저장 실패:', error);
            alert(mode === 'edit' ? '이력서 수정에 실패했습니다.' : '이력서 저장에 실패했습니다.');
        });
});