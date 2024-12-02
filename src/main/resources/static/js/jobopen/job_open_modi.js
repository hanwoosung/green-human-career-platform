let selectedFiles = []; // 새로 추가된 파일
let existingFiles = []; // 기존 파일
let filesToDelete = []; // 삭제 요청된 기존 파일 ID
let existingSkills = []; // 기존에 서버에서 받아온 스킬
let addedSkills = []; // 새로 추가된 스킬
let removedSkills = []; // 삭제 요청된 스킬

$(document).ready(function () {
    const maxFiles = 3;

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

        if (filePreview.find(".img-container").length + newFiles.length > maxFiles) {
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
        const imgContainers = filePreview.find(".img-container");
        const emptySlots = maxFiles - imgContainers.length;

        filePreview.empty();
        imgContainers.each((_, container) => filePreview.append(container));

        for (let i = 0; i < emptySlots; i++) {
            filePreview.append('<div class="default-img">이미지 없음</div>');
        }
    }

    // 기존 스킬 초기화
    $(".selected-skill-list .skill-tag").each(function () {
        const skill = $(this).data("skill");
        existingSkills.push(skill);
    });

    // 스킬 추가 처리
    $(".skill-button").on("click", function () {
        const skill = $(this).data("skill");
        if (!existingSkills.includes(skill) && !addedSkills.includes(skill)) {
            addedSkills.push(skill);
        }

        if (removedSkills.includes(skill)) {
            removedSkills = removedSkills.filter((s) => s !== skill);
        }

        if (!$(`.selected-skill-list .skill-tag[data-skill="${skill}"]`).length) {
            const skillTag = `
                <div class="skill-tag" data-skill="${skill}">
                    <span class="skill-text">${skill}</span>
                    <span class="remove-skill" data-skill="${skill}">x</span>
                </div>`;
            $(".selected-skill-list").append(skillTag);
        }
    });

    // 스킬 제거 처리
    $(document).on("click", ".remove-skill", function () {
        const skill = $(this).data("skill");

        if (existingSkills.includes(skill) && !removedSkills.includes(skill)) {
            removedSkills.push(skill);
        }

        if (addedSkills.includes(skill)) {
            addedSkills = addedSkills.filter((s) => s !== skill);
        }

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

    // 추가된 스킬
    if (addedSkills.length > 0) {
        addedSkills.forEach((skill) => {
            formData.append("addedSkills[]", skill);
            console.log("추가된 스킬:", skill);
        });
    } else {
        console.log("추가된 스킬 없음");
    }

    // 삭제된 스킬
    if (removedSkills.length > 0) {
        removedSkills.forEach((skill) => {
            formData.append("removedSkills[]", skill);
            console.log("제거된 스킬:", skill);
        });
    } else {
        console.log("삭제된 스킬 없음");
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

    // TODO: 고정값 수정해야함
    axios.put("/job-open/49", formData, {
        headers: {
            "Content-Type": "multipart/form-data",
        },
    })
        .then((response) => {
            if (response.data.result.code !== 500) {
                alert("공고 수정 완료.");
            }
        })
        .catch((error) => {
            console.error("에러 발생:", error);
        });
}

