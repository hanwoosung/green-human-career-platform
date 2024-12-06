let filecount = 1;

function addDate(event) {
    let historyYear = event.target.value;
    let date = historyYear + "-01";
    console.log(date);
    let hiddendate = event.target.parentElement.querySelector('input[name="historyYear"]');
    hiddendate.value = date;

}

function addSales(event) {
    let salesYear = event.target.value;
    let date = salesYear + "-12-01";
    let hiddendate = event.target.parentElement.querySelector('input[name="salesYear"]');
    hiddendate.value = date;
}

function addHistory() {
    let container = document.getElementById("historyContainer");
    let newGroup = document.createElement("div");
    newGroup.className = "dynamic-group";
    newGroup.innerHTML = `
            <input type="text" placeholder="연-월" oninput="addDate(event)" class="firstInput">
            <input type="hidden" name="historyYear">
            <input type="text" name="historyTxt" placeholder="연혁 내용" class="secondInput">
            <button type="button" onclick="removeHistory(this);"><span>삭제</span></button>
        `;
    container.appendChild(newGroup);
}

function addRevnue() {
    let container = document.getElementById("revenueContainer");
    let newGroup = document.createElement("div");
    newGroup.className = "dynamic-group";
    newGroup.innerHTML = `
            <input type="text" placeholder="연도" oninput="addSales(event)" class="firstInput">
            <input type="hidden" name="salesYear">
            <input type="text" name='salesTxt' placeholder="매출액" class="secondInput">
            <button type="button" onclick="removeSales(this);"><span>삭제</span></button>
        `;
    container.appendChild(newGroup);
}

function addImage() {
    if(filecount >= 3){
        alert("최대 3개의 이미지만 업로드할 수 있습니다.");
        return;
    }
    let fileDiv = document.createElement("div");
    fileDiv.innerHTML = `
                <div class="form-group">
                    <input type="file" name="sImage" id="sImage" onchange="ImageInfo(event)" accept="image/*">
                    <button type="button" onclick="removeImage(this);"><span>삭제</span></button>
                    <div class="previewContainer"></div>
                </div>
        `;
    document.querySelector(".sub_Image").appendChild(fileDiv);
    filecount++;
}

function removeImageP() {
    let fileInput = document.querySelector("#pImageGroup input[type=file]");
    fileInput.value = "";
    let previewContainer = document.querySelector("#pImageGroup .previewContainer");
    previewContainer.innerHTML = "";
}

function removeImageFirts() {
    let fileInput = document.querySelector("#firstImageGroup input[type=file]");
    fileInput.value = "";
    let previewContainer = document.querySelector("#firstImageGroup .previewContainer");
    previewContainer.innerHTML = "";
    filecount = 1;
}

function removeImage(element) {
    let parentDiv = element.parentElement;

    parentDiv.remove();
    filecount--;
}

function removeHistory(element) {
    let parentDiv = element.parentElement;
    parentDiv.remove();
}

function removeSales(element) {
    let parentDiv = element.parentElement;
    parentDiv.remove();
}

function ImageInfo(event) {

    let files = event.target.files;
    let previewContainer = event.target.parentElement.querySelector(".previewContainer");;
    previewContainer.innerHTML = "";

    Array.from(files).forEach((file, index) => {
        let reader = new FileReader();
        reader.onload = function (event) {
            let img = document.createElement("img");
            img.src = event.target.result;
            img.classList.add("preview");
            img.style.display = 'block';

            let info = document.createElement("div");
            info.classList.add("image-info");
            info.innerText = "파일명: " + file.name + " | 확장자: " + file.type;

            previewContainer.appendChild(img);
        };
        reader.readAsDataURL(file);
    });
}

function submitForm() {
    let isValid = true;

    let cno = document.getElementById("cno");
    let homepage = document.getElementById("homepage");
    let cDetail = document.getElementById("cDetail");
    let cCnt = document.getElementById("cCnt");
    let cBusiness = document.getElementById("cBusiness");

    if(cno.value.trim() == "" || cno.value.length != 10) {
        alert("사업자 등록번호는 필수 입니다.");
        cno.focus();
        isValid = false;
        return isValid;
    } else if(homepage.value.trim() == '') {
        alert("홈페이지는 필수 입니다.");
        homepage.focus();
        isValid = false;
        return isValid;
    } else if(cDetail.value.trim() == ''){
        alert("기업소개는 필수 입니다.");
        cDetail.focus();
        isValid = false;
        return isValid;
    } else if(cCnt.value.trim() == '') {
        alert("사원수는 필수 입니다.");
        cDetail.focus();
        isValid = false;
        return isValid;
    } else if(cBusiness.value.trim() == '') {
        alert("주요 업무는 필수 입니다.");
        cDetail.focus();
        isValid = false;
        return isValid
    } else {
        return isValid;
    }

}