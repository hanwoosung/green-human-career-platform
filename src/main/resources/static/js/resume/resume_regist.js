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
    // representativeYn: 'N',
    deleteYn: '',
    careers: [],
    educations: [],
    portfolios: [],
    qualifications: [],
    technicalStacks: [],
    treats: [],
    introduces: [],
    profilePhoto: null
};

// 서버에서 우대사항 옵션 불러오기
function loadTreatOptions() {
    axios.get('/resume/getTreatOptions')
        .then(response => {
            const treatOptions = response.data.data;
            console.log('Treat options loaded:', treatOptions);

            const formattedOptions = treatOptions.map(option => ({
                value: option.prf_cd,
                label: option.preference_name
            }));

            formTemplates.treats.forEach(treat => {
                treat.options = formattedOptions;
            });
        })
        .catch(error => {
            console.error('Error loading treat options:', error);
            alert('우대사항 옵션을 불러오는 데 실패했습니다.');
        });
}

const treatOptions = [];

// 폼 타입에 따른 입력 필드 정의
const formTemplates = {
    careers: [
        {name: "rc_company_nm", type: "text", placeholder: "회사명을 입력하세요"},
        {label: "입사일", name: "rc_join_dt", type: "date"},
        {label: "퇴사일", name: "rc_out_dt", type: "date"},
        {name: "rc_dmpt", type: "text", placeholder: "부서명을 입력하세요"},
        {name: "rc_pstn", type: "text", placeholder: "직책을 입력하세요"},
        {name: "rc_duties", type: "textarea", placeholder: "담당업무를 입력하세요"}
    ],
    educations: [
        {
            name: "re_gbn_cd",
            type: "select",
            options: [
                {value: "H", label: "고등학교"},
                {value: "U", label: "대졸"},
                {value: "S", label: "초대졸"},
                {value: "D", label: "석사"},
                {value: "J", label: "박사"}
            ]
        },
        {name: "re_school_nm", type: "text", placeholder: "학교 이름을 입력하세요"},
        {name: "re_major", type: "text", placeholder: "전공을 입력하세요"},
        {name: "re_score", type: "number", placeholder: "성적을 입력하세요"},
        {label: "입학일", name: "re_indt", type: "date"},
        {label: "졸업일", name: "re_outdt", type: "date"},
        {label: "편입", name: "re_transfer_yn", type: "checkbox", labelWhenChecked: "편입"}
    ],
    portfolios: [
        {label: "시작날짜", name: "rp_str_dt", type: "date"},
        {label: "종료날짜", name: "rp_end_dt", type: "date"},
        {name: "rp_url", type: "text", placeholder: "URL을 입력하세요"},
        {name: "rp_cnt", type: "number", placeholder: "작업 인원을 입력하세요"},
        {name: "rp_content", type: "textarea", placeholder: "작업 내용을 입력하세요"},
        {name: "files", type: "file"}
    ],
    qualifications: [
        {name: "rq_nm", type: "text", placeholder: "자격증 이름을 입력하세요"},
        {label: "합격일", name: "rq_dt", type: "date"},
        {name: "rq_place", type: "text", placeholder: "발급 기관을 입력하세요"},
        {name: "rq_gbn_cd", type: "select", options: [{value: "P", label: "필기"}, {value: "S", label: "실기"}]}
    ],
    treats: [
        {
            name: "prf_cd",
            type: "select",
            options: treatOptions
        },
        {
            name: "rpr_content",
            type: "textarea",
            placeholder: "우대 세부사항을 입력하세요ㄴㅇㄹㄴㅇㄹㄴㅇ"
        }
    ], 
    introduces: [
        {name: "rm_title", type: "text", placeholder: "자기소개 제목을 입력하세요"},
        {name: "rm_content", type: "textarea", placeholder: "자기소개 내용을 입력하세요"},
        {name: "files", type: "file"}
    ]
};

// 학력 정보 불러오기
function loadEducations() {
    console.log('Loading educations for userId:', temporaryDataStore.id);
    axios.get('/resume/getEducations', {params: {userId: temporaryDataStore.id}})
        .then(response => {
            console.log('Educations response:', response.data);
            const educations = response.data;
            educations.forEach(edu => {
                edu.id = edu.id || Date.now() + Math.random(); // 고유 ID 할당
            });
            temporaryDataStore.educations = educations;
            renderSection(educations, '.user-educations-info .form-content-list', 'educations');
            updateProgress();
        })
        .catch(error => {
            console.error('Error loading educations:', error);
            alert('학력 정보를 불러오는 데 실패했습니다.');
        });
}

// 경력 정보 불러오기
function loadCareers() {
    console.log('Loading careers for userId:', temporaryDataStore.id);
    axios.get('/resume/getCareers', {params: {userId: temporaryDataStore.id}})
        .then(response => {
            console.log('Careers response:', response.data);
            const careers = response.data;
            careers.forEach(car => {
                car.id = car.id || Date.now() + Math.random(); // 고유 ID 할당
            });
            temporaryDataStore.careers = careers;
            renderSection(careers, '.user-careers-info .form-content-list', 'careers');
            updateProgress();
        })
        .catch(error => {
            console.error('Error loading careers:', error);
            alert('경력 정보를 불러오는 데 실패했습니다.');
        });
}

// 자격증 정보 불러오기
function loadQualifications() {
    console.log('Loading qualifications for userId:', temporaryDataStore.id);
    axios.get('/resume/getQualifications', {params: {userId: temporaryDataStore.id}})
        .then(response => {
            console.log('Qualifications response:', response.data);
            const qualifications = response.data;
            qualifications.forEach(qual => {
                qual.id = qual.id || Date.now() + Math.random(); // 고유 ID 할당
            });
            temporaryDataStore.qualifications = qualifications;
            renderSection(qualifications, '.user-qualifications-info .form-content-list', 'qualifications');
            updateProgress();
        })
        .catch(error => {
            console.error('Error loading qualifications:', error);
            alert('자격증 정보를 불러오는 데 실패했습니다.');
        });
}
function formatDate(timestamp) {
    if (!timestamp) return '';
    const dateObj = new Date(timestamp);
    const year = dateObj.getFullYear();
    const month = String(dateObj.getMonth() + 1).padStart(2, '0');
    const day = String(dateObj.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}
$(document).ready(function () {
    const mode = $('#resume-form').data('mode');
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
    temporaryDataStore.profilePhoto = userInfo.profilePhoto || null;

    // 여기서 변환 로직 추가
    // 학력 정보 날짜 및 ID 변환
    temporaryDataStore.educations.forEach(edu => {
        edu.id = edu.educationId || Date.now() + Math.random(); // 고유 ID 할당
        if (edu.re_indt) {
            edu.re_indt = formatDate(edu.re_indt);
        }
        if (edu.re_outdt) {
            edu.re_outdt = formatDate(edu.re_outdt);
        }
    });

    // 경력 정보 날짜 및 ID 변환
    temporaryDataStore.careers.forEach(car => {
        car.id = car.careerId || Date.now() + Math.random(); // 고유 ID 할당
        if (car.rc_join_dt) {
            car.rc_join_dt = formatDate(car.rc_join_dt);
        }
        if (car.rc_out_dt) {
            car.rc_out_dt = formatDate(car.rc_out_dt);
        }
    });

    // 자격증 정보 날짜 및 ID 변환
    temporaryDataStore.qualifications.forEach(qual => {
        qual.id = qual.qualificationId || Date.now() + Math.random(); // 고유 ID 할당
        if (qual.rq_dt) {
            qual.rq_dt = formatDate(qual.rq_dt);
        }
    });

    // 포트폴리오 정보 날짜 변환 및 ID 할당
    temporaryDataStore.portfolios.forEach(port => {
        port.id = port.portfolioId || Date.now() + Math.random(); // 고유 ID 할당 (필요 시)
        if (port.rp_str_dt) {
            port.rp_str_dt = formatDate(port.rp_str_dt);
        }
        if (port.rp_end_dt) {
            port.rp_end_dt = formatDate(port.rp_end_dt);
        }
    });
















    // 기본적으로 첫 번째 카테고리 선택(수정 모드에서도 동일하게)
    const $firstCategory = $('.category-button:first');
    if ($firstCategory.length) {
        $firstCategory.addClass('active');
        const firstCategoryCode = $firstCategory.data('category');
        $(`#category-${firstCategoryCode}`).show();
    }

    // 기술 스택 렌더링
    renderTechnicalStacks();

    // 체크박스 상태 복원
    temporaryDataStore.technicalStacks.forEach(stack => {
        $(`.tech-stack-checkbox[value="${stack.stack_cd}"]`).prop('checked', true);
    });

    // mode가 'edit'가 아닐 경우에만 불러오기
    if (mode !== 'edit') {
        loadQualifications();
        loadCareers();
        loadEducations();
        loadTreatOptions();
    } else {

        loadTreatOptions();
    }

    // 각 섹션에 데이터 반영 (technicalStacks는 이미 하드코딩된 checkbox이므로 renderSection 불필요)
    renderSection(temporaryDataStore.educations, '.user-educations-info .form-content-list', 'educations');
    renderSection(temporaryDataStore.careers, '.user-careers-info .form-content-list', 'careers');
    renderSection(temporaryDataStore.portfolios, '.user-portfolios-info .form-content-list', 'portfolios');
    renderSection(temporaryDataStore.qualifications, '.user-qualifications-info .form-content-list', 'qualifications');
    // renderSection(temporaryDataStore.technicalStacks, '.user-technicalStacks-info .form-content-list', 'technicalStacks'); // 필요없으니 주석
    renderSection(temporaryDataStore.treats, '.user-treats-info .form-content-list', 'treats');
    renderSection(temporaryDataStore.introduces, '.user-introduces-info .form-content-list', 'introduces');





    updateProgress();

    console.log(temporaryDataStore);

    const $modal = $('#modal');
    const $modalTitle = $('#modal-title');
    const $photoEditIcon = $('#photoEditIcon');
    const $photoUploadInput = $('#photoUploadInput');
    const $photoPreview = $('#resumePhotoPreview');

    // 오른쪽 이력서 섹션 항목 클릭
    $('.section-item').on('click', function () {
        const formType = $(this).data('form-type');
        if (formType) {
            $('#modal').data('form-type', formType);
            generateForm(formType, 'form-fields');
            $('#modal-title').text($(this).text() + ' 추가');
            $('#modal').show();
        }
    });

    // 카테고리 버튼 클릭 이벤트
    $('.category-button').on('click', function () {
        $('.category-button').removeClass('active');
        $(this).addClass('active');

        const selectedCategory = $(this).data('category');
        $('.category-content').hide();
        $(`#category-${selectedCategory}`).show();

        // 이미 선택된 기술 스택들의 체크박스 상태 복원
        temporaryDataStore.technicalStacks.forEach(stack => {
            $(`.tech-stack-checkbox[value="${stack.stack_cd}"]`).prop('checked', true);
        });
    });

    // 기술 스택 체크박스 선택 이벤트
    $('.tech-stack-checkbox').on('change', function () {
        const stack_cd = $(this).val();
        if ($(this).is(':checked')) {
            temporaryDataStore.technicalStacks.push({stack_cd});
            console.log("기술 스택이 추가되었습니다:", stack_cd);
        } else {
            temporaryDataStore.technicalStacks = temporaryDataStore.technicalStacks.filter(item => item.stack_cd !== stack_cd);
            console.log("기술 스택이 제거되었습니다:", stack_cd);
        }
        console.log("현재 기술 스택 목록:", temporaryDataStore.technicalStacks);
        updateProgress();
        renderTechnicalStacks();
    });

    // 기술 스택 렌더링 함수
    function renderTechnicalStacks() {
        const $selectedContainer = $(".selected-tech-stacks");
        $selectedContainer.empty();

        temporaryDataStore.technicalStacks.forEach(stack => {
            const stackName = $(`.tech-stack-checkbox[value="${stack.stack_cd}"]`).siblings('span').text();
            const $techElement = $(`
                <div class="selected-tech-stack" data-stack-cd="${stack.stack_cd}">
                    ${stackName}
                    <span class="close">&times;</span>
                </div>
            `);

            $selectedContainer.append($techElement);
        });

        $(".selected-tech-stack .close").on('click', function () {
            const stackCd = $(this).parent().data('stack-cd');
            removeSelectedTechStack(stackCd);
        });
    }

    // 선택된 기술 스택 제거 함수
    function removeSelectedTechStack(skillCode) {
        $(`.tech-stack-checkbox[value="${skillCode}"]`).prop('checked', false);
        temporaryDataStore.technicalStacks = temporaryDataStore.technicalStacks.filter(item => item.stack_cd !== skillCode);
        console.log("기술 스택이 제거되었습니다:", skillCode);
        renderTechnicalStacks();
        updateProgress();
    }

    // 아이콘 클릭 시 파일 업로드 창 열기
    $photoEditIcon.on('click', function () {
        console.log('photoEditIcon clicked');
        $photoUploadInput.click();
    });

    // 파일 선택 시 미리보기 업데이트
    $photoUploadInput.on('change', function () {
        const file = this.files[0];
        console.log('Selected file:', file);
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                $photoPreview.attr('src', e.target.result);
                console.log('Preview updated:', e.target.result);
            };
            reader.readAsDataURL(file);
            temporaryDataStore.profilePhoto = file;
        } else {
            console.log('파일이 선택되지 않음');
        }
    });

    // 이력서 추가입력 버튼 클릭 이벤트
    $('.open-button').on('click', function (e) {
        e.preventDefault();
        $('#form-fields').empty();
        const formType = $(this).data('form-type');
        console.log(formType);
        $('#modal').data('form-type', formType);
        generateForm(formType, 'form-fields');
        const titleMap = {
            careers: '경력 추가',
            educations: '학력 추가',
            portfolios: '포트폴리오 추가',
            qualifications: '자격증 추가',
            technicalStacks: '기술 추가',
            treats: '우대사항 추가',
            introduces: '자기소개 추가'
        };
        $('#modal-title').text(titleMap[formType] || '폼 추가');
        $('#modal').show();
    });

    $(document).on('click', '#modal-cancel', function (e) {
        e.preventDefault();
        e.stopPropagation();
        console.log('모달 닫기 버튼 클릭됨');
        $('#modal').hide();
        $('#modal').removeData('item-id');
    });

});

// 폼 생성 함수
function generateForm(templateKey, containerId) {
    const template = formTemplates[templateKey];
    if (!template) {
        console.error("템플릿이 존재하지 않습니다:", templateKey);
        return;
    }

    const $container = $(`#${containerId}`);
    $container.empty();

    template.forEach(field => {
        const fieldWrapper = $('<div>').addClass('form-group');
        if (field.label) {
            const label = $('<label>')
                .attr('for', field.name)
                .text(field.label);
            fieldWrapper.append(label);
        }

        let input;
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
            input = $('<input>')
                .attr('type', 'checkbox')
                .attr('name', field.name)
                .attr('id', field.name);
            input.val("N");
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

    if (field.type === 'select' && field.options) {
        const option = field.options.find(opt => opt.value === value);
        return option ? option.label : value;
    }

    if (field.type === 'checkbox' && value === 'Y' && field.labelWhenChecked) {
        return field.labelWhenChecked;
    }
    return value;
}

// 섹션 렌더링 함수
function renderSection(data, sectionSelector, templateKey) {
    const $section = $(sectionSelector);
    $section.empty();

    data.forEach(item => {
        const $item = $('<div>')
            .addClass('data-item')
            .data('item-id', item.id)
            .attr('data-form-type', templateKey);

        let mainInfo = '';
        let subInfo = [];
        let fileList = [];

        if (templateKey === 'technicalStacks') {
            // technicalStacks는 이미 하드코딩 + renderTechnicalStacks()로 처리 중이므로 여기는 비워둡니다.
            // 필요하다면 해당 부분 조정
        }

        formTemplates[templateKey].forEach(field => {
            let fieldValue = item[field.name];
            if (fieldValue) {
                if (field.type === 'select' || field.type === 'checkbox') {
                    fieldValue = getLabelForSelectValue(templateKey, field.name, fieldValue);
                }
                if (field.name === 'rp_url' || field.name === 're_school_nm' || field.name === 'rc_company_nm' || 
                    field.name === 'rm_title'||field.name === 'rq_nm' || field.name === 'prf_cd' ) {
                    mainInfo = fieldValue;
                } else if (field.type === 'file' && Array.isArray(fieldValue)) {
                    fieldValue.forEach(file => {
                        fileList.push(file.name);
                    });
                }else if (field.name === 're_transfer_yn' && fieldValue === 'N') {
                    return;
                } else {
                    subInfo.push(`<span>${fieldValue}</span>`);

                }

            }
        });

        if (mainInfo) {
            const $mainInfo = $('<div>').addClass('main-info').text(mainInfo);
            $item.append($mainInfo);
        }

        if (subInfo.length > 0) {
            const $subInfo = $('<div>').addClass('sub-info').html(subInfo.join(' '));
            $item.append($subInfo);
        }

        if (fileList.length > 0) {
            const $fileContainer = $('<div>').addClass('file-container');
            fileList.forEach(fileName => {
                const $fileItem = $('<div>').addClass('file-item').text(fileName);
                $fileContainer.append($fileItem);
            });
            $item.append($fileContainer);
        }

        const $editButton = $('<button>')
            .addClass('edit-button')
            .data('item-id', item.id)
            .html('<i class="fas fa-edit"></i>');
        $item.append($editButton);

        const $deleteButton = $('<button>')
            .addClass('delete-button')
            .data('item-id', item.id)
            .html('<i class="fas fa-trash"></i>');
        $item.append($deleteButton);

        $section.append($item);
    });

    $section.attr('data-form-type', templateKey);
}

$(document).on('click', '.edit-button', function (event) {
    event.preventDefault();
    $('#form-fields').empty();
    const itemId = $(this).data('item-id');
    const formType = $(this).closest('.form-content-list').data('form-type');
    console.log('formType>>>>>>>>>>>>',formType);
    const itemData = temporaryDataStore[formType].find(item => item.id === itemId);

    if (itemData) {
        generateForm(formType, 'form-fields');
        const $formFields = $('#form-fields');
        formTemplates[formType].forEach(field => {
            if (field.type !== 'file') {
                $formFields.find(`[name=${field.name}]`).val(itemData[field.name] || '');
            }
        });

        if (itemData.files && Array.isArray(itemData.files)) {
            const existingFilesContainer = $('<div>')
                .addClass('existing-files')
                .text('기존 파일이 있습니다. 새 파일을 업로드하려면 선택하세요.');
            $formFields.append(existingFilesContainer);
        }

        $('#modal').data('form-type', formType).data('item-id', itemId).show();
    }
});

$(document).on('click', '.delete-button', function (event) {
    event.preventDefault();
    const itemId = $(this).data('item-id');
    const formType = $(this).closest('[data-form-type]').data('form-type');
    if (!formType) {
        console.error("삭제 버튼에 해당하는 섹션의 formType을 찾을 수 없습니다.");
        return;
    }
    temporaryDataStore[formType] = temporaryDataStore[formType].filter(item => item.id !== itemId);
    renderSection(temporaryDataStore[formType], `.user-${formType}-info .form-content-list`, formType);
    updateProgress();
});

$('.open-button').on('click', function (e) {
    e.preventDefault();
    $('#form-fields').empty();
    const formType = $(this).data('form-type');
    console.log(formType);
    $('#modal').data('form-type', formType);
    generateForm(formType, 'form-fields');
    const titleMap = {
        careers: '경력 추가',
        educations: '학력 추가',
        portfolios: '포트폴리오 추가',
        qualifications: '자격증 추가',
        technicalStacks: '기술 추가',
        treats: '우대사항 추가',
        introduces: '자기소개 추가'
    };
    $('#modal-title').text(titleMap[formType] || '폼 추가');
    $('#modal').show();
});

$('#modal-submit').on('click', function (e) {
    e.preventDefault();
    const formType = $('#modal').data('form-type');
    const itemId = $('#modal').data('item-id');

    const formData = $('#dynamic-form').serializeArray();
    const requiredDateFields = formTemplates[formType].filter(field => field.type === 'date');

    for (const field of requiredDateFields) {
        const fieldValue = formData.find(item => item.name === field.name);
        if (!fieldValue || !fieldValue.value) {
            alert(`"${field.label || field.name}" 필드는 필수 항목입니다. 날짜를 입력해주세요.`);
            return;
        }
    }

    const formObject = {};
    formData.forEach(field => {
        formObject[field.name] = field.value;
    });

    formTemplates[formType].forEach(field => {
        if (field.type === "file") {
            const fileInput = $(`[name="${field.name}"]`)[0];
            if (fileInput && fileInput.files.length > 0) {
                formObject[field.name] = Array.from(fileInput.files);
            }
        }
    });

    // 체크박스 필드인 re_transfer_yn이 없으면 "N" 기본값 설정
    // (re_transfer_yn이 checkbox 필드이며, 체크 안하면 formData에 안 들어올 수 있음)
    if (formTemplates[formType].some(f => f.name === 're_transfer_yn') && formObject['re_transfer_yn'] === undefined) {
        formObject['re_transfer_yn'] = 'N';
    }



    if (itemId) {
        const itemIndex = temporaryDataStore[formType].findIndex(item => item.id === itemId);
        if (itemIndex !== -1) {
            temporaryDataStore[formType][itemIndex] = {...temporaryDataStore[formType][itemIndex], ...formObject};
        }
    } else {
        formObject.id = Date.now();
        if (!Array.isArray(temporaryDataStore[formType])) {
            temporaryDataStore[formType] = [];
        }
        temporaryDataStore[formType].push(formObject);
    }

    $('#modal').hide();
    $('#form-fields').empty();
    $('#modal').removeData('item-id');
    updateProgress();
    renderSection(temporaryDataStore[formType], `.user-${formType}-info .form-content-list`, formType);
    console.log('폼 데이터 저장 완료:', temporaryDataStore[formType]);
    console.log('formType:', formType);
});

function updateProgress() {
    const totalSections = 7;
    let filledSections = 0;

    if (temporaryDataStore.educations.length > 0) filledSections++;
    if (temporaryDataStore.careers.length > 0) filledSections++;
    if (temporaryDataStore.technicalStacks.length > 0) filledSections++;
    if (temporaryDataStore.portfolios.length > 0) filledSections++;
    if (temporaryDataStore.qualifications.length > 0) filledSections++;
    if (temporaryDataStore.introduces.length > 0) filledSections++;
    if (temporaryDataStore.treats.length > 0) filledSections++;

    const progressPercentage = Math.round((filledSections / totalSections) * 100);
    $('#progress-bar-inner')
        .css('width', `${progressPercentage}%`)
        .text(`${progressPercentage}%`);

    if (progressPercentage === 100) {
        $('#resume-status').text('이력서 모든 폼이 작성되었습니다!');
    } else {
        $('#resume-status').text('이력서가 진행 중입니다!');
    }
    console.log('진행도 업데이트:', progressPercentage, '% 완료');
}

$('.submit-resume').on('click', function (e) {
    e.preventDefault();
    const formData = new FormData();
    const mode = $('#resume-form').data('mode');
    const resumeId = $('#resume-form').data('resume-id');

    temporaryDataStore.title = $('#resume-title').val();
    temporaryDataStore.careerCode = $('#careerCode').val();

    if (mode === 'edit') {
        temporaryDataStore.resumeId = resumeId;
    }

    const cleanedData = {
        ...temporaryDataStore,
        careers: temporaryDataStore.careers.map(({id, ...rest}) => rest),
        educations: temporaryDataStore.educations.map(({id, ...rest}) => rest),
        portfolios: temporaryDataStore.portfolios.map(({id, files, ...rest}) => rest),
        qualifications: temporaryDataStore.qualifications.map(({id, ...rest}) => rest),
        treats: temporaryDataStore.treats.map(({id, ...rest}) => rest),
        introduces: temporaryDataStore.introduces.map(({id, files, ...rest}) => rest),
    };

    formData.append('resumeData', JSON.stringify(cleanedData));

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
        .then(function (response) {
            alert(mode === 'edit' ? '이력서가 수정되었습니다.' : '이력서가 저장되었습니다.');
            window.location.href = '/resume';
        })
        .catch(function (error) {
            console.error('저장 실패:', error);
            alert(mode === 'edit' ? '이력서 수정에 실패했습니다.' : '이력서 저장에 실패했습니다.');
        });
});

