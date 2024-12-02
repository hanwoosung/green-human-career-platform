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