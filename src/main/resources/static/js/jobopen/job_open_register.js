let selectedFiles = [];

$(document).ready(function () {
    updatePreviewOrder();
    const maxFiles = 3;

    const now = new Date();
    const isoString = new Date(now.getTime() - now.getTimezoneOffset() * 60000).toISOString().slice(0, 16);
    $("#startDate").val(isoString);

    $("#companyImage").on("change", function () {
        const filePreview = $("#filePreview");
        const newFiles = Array.from(this.files);

        if (filePreview.find(".img-container").length + newFiles.length > maxFiles) {
            alert(`이미지는 최대 ${maxFiles}개`);
            this.value = "";
            return;
        }

        newFiles.forEach((file) => {
            const fileIndex = selectedFiles.findIndex(
                (f) => f.name === file.name && f.size === file.size
            );
            if (fileIndex === -1) {
                selectedFiles.push(file);

                const reader = new FileReader();
                reader.onload = function (e) {
                    const slot = filePreview.find(".default-img").first();
                    const imgContainer = $('<div class="img-container"></div>');
                    const img = $("<img>")
                        .attr("src", e.target.result)
                        .attr("data-file-name", file.name)
                        .attr("data-file-size", file.size);

                    const deleteBtn = $('<span class="delete-img">x</span>');
                    imgContainer.append(img).append(deleteBtn);
                    slot.replaceWith(imgContainer);
                };
                reader.readAsDataURL(file);
            }
        });

        this.value = "";
    });


    $("#filePreview").on("click", ".delete-img", function () {
        const imgContainer = $(this).closest(".img-container");
        const imgFileName = imgContainer.find("img").attr("data-file-name");
        const imgFileSize = imgContainer.find("img").attr("data-file-size");

        console.log("삭제 시도 중인 파일 이름:", imgFileName, "파일 크기:", imgFileSize);
        console.log("현재 selectedFiles 상태:", selectedFiles);

        const fileIndex = selectedFiles.findIndex(
            (file) => file.name === imgFileName && file.size.toString() === imgFileSize
        );

        if (fileIndex !== -1) {
            console.log("삭제할 파일 인덱스:", fileIndex);
            console.log("삭제 파일 정보:", selectedFiles[fileIndex]);

            selectedFiles.splice(fileIndex, 1);
            imgContainer.remove();
            updatePreviewOrder();

            console.log("파일 삭제 완료.");
            console.log("삭제 후 updated selectedFiles 상태:", selectedFiles);
        } else {
            console.error("파일 삭제 실패: 파일을 찾을 수 없습니다.");
        }
    });

    function updatePreviewOrder() {
        const filePreview = $("#filePreview");
        const imgContainers = filePreview.find(".img-container");
        filePreview.find(".default-img").remove();

        const imageCount = imgContainers.length;
        const emptySlotsNeeded = 3 - imageCount;

        for (let i = 0; i < emptySlotsNeeded; i++) {
            filePreview.append('<div class="default-img">이미지 없음</div>');
        }
    }


    $(".skill-button").on("click", function () {
        const skillCd = $(this).data("skill");
        const skillName = $(this).text();

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
        $(this).closest(".skill-tag").remove();
    });
});


function regist(event) {
    event.preventDefault();

    const formData = new FormData();

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

    const skills = Array.from(document.querySelectorAll(".selected-skill-list .skill-tag")).map((tag, index) => {
        const skillCd = tag.getAttribute("data-skill");
        return skillCd;
    });
    skills.forEach((skillCd, index) => formData.append(`skillList[${index}]`, skillCd));

    selectedFiles.forEach((file) => formData.append("companyImages", file));

    axios.post("/job-open", formData, {
        headers: {
            "Content-Type": "multipart/form-data",
        },
    })
        .then((response) => {
            console.log(response.data)
            if (response.data.data != null) {
                alert("공고 등록이 완료되었습니다." + response.data.result.code);
                setTimeout(window.location.href = '/job-open/detail/' + response.data.data,1000);
            }
        })
        .catch((error) => {
            console.log(error)
            alert("공고 등록에 실패했습니다.");
        });
}