const userInfo = document.querySelector('.user-info');
const userType = userInfo.getAttribute('data-userType');
console.log('userType: ', userType);


function toggleProfileDropdown(event){
    event.stopPropagation();
    const dropdown = document.querySelector(".profile-dropdown-menu");
    if(userType === '비회원'){
        return;
    }
    if(dropdown.style.display === 'block'){
        dropdown.style.display = 'none';
    }else{
        dropdown.style.display = 'block';
    }
}
document.addEventListener('click',function(event){
    const dropdown = document.querySelector('.profile-dropdown-menu');
    const userInfo = document.querySelector('.user-info');
    if(!userInfo.contains(event.target)){
        dropdown.style.display = 'none';
    }
})

const dropdown = document.querySelector('.profile-dropdown-menu');
dropdown.addEventListener('click', function (event) {
    event.stopPropagation();
});

document.addEventListener('DOMContentLoaded', () => {
    const currentUrl = window.location.pathname; // 현재 URL 가져오기
    const menuLinks = document.querySelectorAll('.sidebar-item a'); // 모든 메뉴 링크 가져오기

    console.log('Current URL:', currentUrl);

    menuLinks.forEach(link => {
        const linkHref = link.getAttribute('href'); // 링크의 href 속성 값
        console.log('Checking link:', linkHref);

        // 현재 URL과 href가 정확히 일치하는 경우만 active 클래스 추가
        if (currentUrl === linkHref) {
            link.classList.add('active');
        }
    });
});

