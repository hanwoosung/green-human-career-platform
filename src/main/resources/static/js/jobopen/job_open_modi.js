let selectedFiles = []; // 새로 추가된 파일
let existingFiles = []; // 기존 파일
let filesToDelete = []; // 삭제 요청된 기존 파일 ID
let existingSkills = []; // 기존에 서버에서 받아온 스킬 코드
let addedSkillCodes = []; // 새로 추가된 스킬 코드
let removedSkillCodes = []; // 삭제 요청된 스킬 코드

$(document).ready(function () {
    const maxFiles = 3;
    updatePreviewOrder();
    // 서버에서 받아온 기존 파일 초기화
    $("#filePreview .img-container img").each(function () {
        const fileId = $(this).data("file-id");
        const fileName = $(this).data("file-name");

        existingFiles.push({
            id: fileId,
            name: fileName,
        });
    });

    // 파일 추가 처리
    $("#companyImage").on("change", function () {
        const filePreview = $("#filePreview");
        const newFiles = Array.from(this.files);
        const currentFileCount = filePreview.find(".img-container:not(.default-img)").length; // 실제 이미지 개수만 확인

        if (currentFileCount + newFiles.length > maxFiles) {
            alert(`이미지는 최대 ${maxFiles}개 업로드할 수 있습니다.`);
            this.value = "";
            return;
        }

        newFiles.forEach((file) => {
            const fileIndex = selectedFiles.findIndex((f) => f.name === file.name);

            if (fileIndex === -1) {
                selectedFiles.push(file);

                const reader = new FileReader();
                reader.onload = function (e) {
                    const slot = filePreview.find(".default-img").first();
                    const imgContainer = $('<div class="img-container"></div>');
                    const img = $("<img>")
                        .attr("src", e.target.result)
                        .attr("data-file-name", file.name);

                    const deleteBtn = $('<span class="delete-img">x</span>');
                    imgContainer.append(img).append(deleteBtn);
                    slot.replaceWith(imgContainer);
                };
                reader.readAsDataURL(file);
            }
        });

        this.value = "";
    });

    // 파일 삭제 처리
    $("#filePreview").on("click", ".delete-img", function () {
        const imgContainer = $(this).closest(".img-container");
        const fileId = imgContainer.find("img").data("file-id");
        const fileName = imgContainer.find("img").data("file-name");

        if (fileId) {
            // 기존 파일 삭제
            filesToDelete.push(fileId);
            existingFiles = existingFiles.filter((file) => file.id !== fileId);
        } else {
            // 새로 추가된 파일 삭제
            const fileIndex = selectedFiles.findIndex((file) => file.name === fileName);

            if (fileIndex !== -1) {
                selectedFiles.splice(fileIndex, 1);
            } else {
                console.error("파일 삭제 실패: 파일을 찾을 수 없습니다.");
            }
        }

        imgContainer.remove();
        updatePreviewOrder();
    });

    function updatePreviewOrder() {
        const filePreview = $("#filePreview");
        const imgContainers = filePreview.find(".img-container:not(.default-img)"); // 실제 이미지만 선택
        const emptySlots = maxFiles - imgContainers.length;

        // 기존 이미지 슬롯을 다시 추가
        filePreview.empty();
        imgContainers.each((_, container) => filePreview.append(container));

        // 빈 슬롯을 필요한 만큼 추가
        if (emptySlots > 0) {
            for (let i = 0; i < emptySlots; i++) {
                filePreview.append('<div class="img-container default-img">이미지 없음</div>');
            }
        }
    }


    $(".selected-skill-list .skill-tag").each(function () {
        const skillCd = $(this).data("skill");
        existingSkills.push(skillCd);
    });


    // 스킬 추가 처리
    $(".skill-button").on("click", function () {
        const skillCd = $(this).data("skillCd");
        const skillName = $(this).text().trim();

        if (!existingSkills.includes(skillCd) && !addedSkillCodes.includes(skillCd)) {
            addedSkillCodes.push(skillCd);
        }

        if (removedSkillCodes.includes(skillCd)) {
            removedSkillCodes = removedSkillCodes.filter((s) => s !== skillCd);
        }

        if (!$(`.selected-skill-list .skill-tag[data-skill="${skillCd}"]`).length) {
            const skillTag = `
            <div class="skill-tag" data-skill="${skillCd}">
                <span class="skill-text">${skillName}</span>
                <span class="remove-skill" data-skill="${skillCd}">x</span>
            </div>`;
            $(".selected-skill-list").append(skillTag);
        }
    });

    $(document).on("click", ".remove-skill", function () {
        const skillCd = $(this).data("skill");
        console.log("클릭된 스킬 코드:", skillCd);
        console.log("기존 스킬 코드 배열:", existingSkills);

        // 기존 스킬 중 제거된 스킬에 추가
        if (existingSkills.includes(skillCd) && !removedSkillCodes.includes(skillCd)) {
            removedSkillCodes.push(skillCd);
            console.log("기존 스킬이므로 removedSkillCodes에 추가:", skillCd);
        } else {
            console.log("기존 스킬이 아니므로 removedSkillCodes에 추가하지 않음.");
        }

        // 새로 추가된 스킬 코드 리스트에서 제거
        if (addedSkillCodes.includes(skillCd)) {
            addedSkillCodes = addedSkillCodes.filter((s) => s !== skillCd);
            console.log("새로 추가된 스킬에서 제거:", skillCd);
        }

        // 삭제된 스킬을 화면에서 제거
        $(this).closest(".skill-tag").remove();
    });

});


function modify(event) {
    event.preventDefault();

    const formData = new FormData();

    // 기본 정보 추가
    formData.append("id", "company1");
    formData.append("jTitle", document.querySelector("input[name='jobTitle']").value);
    formData.append("jStitle", document.querySelector("input[name='jobSubtitle']").value);
    formData.append("jContent", document.querySelector("textarea[name='jobDescription']").value);
    formData.append("jGbnCd", "O");
    formData.append("sDt", document.querySelector("input[name='startDate']").value);
    formData.append("eDt", document.querySelector("input[name='endDate']").value);
    formData.append("educatCd", document.querySelector("select[name='education']").value);
    formData.append("careerCd", document.querySelector("select[name='experience']").value);
    formData.append("preferences", document.querySelector("textarea[name='preferred']").value);
    formData.append("workPlace", document.querySelector("input[name='workLocation']").value);
    formData.append("workTime", document.querySelector("input[name='workTime']").value);
    formData.append("workType", document.querySelector("select[name='employmentType']").value);


    // 추가된 스킬 코드 전송
    if (addedSkillCodes.length > 0) {
        addedSkillCodes.forEach((skillCd) => {
            formData.append("addedSkills[]", skillCd);
            console.log("추가된 스킬 코드:", skillCd);
        });
    }

    // 삭제된 스킬 코드 전송
    if (removedSkillCodes.length > 0) {
        console.log("삭제된 스킬 코드 리스트:", removedSkillCodes);
        removedSkillCodes.forEach((skillCd) => {
            formData.append("removedSkills[]", skillCd);
            console.log("제거된 스킬 코드:", skillCd);
        });
    }


    // 새로 추가된 파일 추가
    selectedFiles.forEach((file) => {
        formData.append("companyImages", file);
        console.log("추가된 파일:", file.name);
    });

    // 삭제된 파일 ID 추가
    if (filesToDelete.length > 0) {
        filesToDelete.forEach((fileId) => {
            console.log("삭제된 파일 ID:", fileId);
            formData.append("filesToDelete[]", fileId);
        });
    } else {
        console.log("삭제할 파일이 없습니다.");
    }


    const jobJno = document.querySelector(".submit-button").getAttribute("data-job-jno");

    axios.put(`/job-open/${jobJno}`, formData, {
        headers: {
            "Content-Type": "multipart/form-data",
        },
    })
        .then((response) => {
            if (response.data.data != null) {
                alert("공고 수정이 완료되었습니다." + response.data.result.code);
                window.location.href = '/job-open/detail/' + jobJno;
            }
        })
        .catch((error) => {
            console.error("에러 발생:", error);
        });
}

