$(function (){

    document.querySelector(".list-wrap .list").addEventListener("click", function (){
        let id = this.dataset.id;
        
        //TODO: 상세페이지로 연결하는 코드 추후 추가 필요.
        location.href = "/" + id;
    });

});