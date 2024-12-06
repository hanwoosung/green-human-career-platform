$(function (){
    // TODO: 클릭시 들어가는 기능도 구현필요 현재 상세가 없어 안함.
    $(document).on("click", ".page-link", function (){

        let href = this.dataset.href;

        location.href = href + "&search=" + document.querySelector("input[name=search]").value;

    });


    $(document).on("click", ".list", function (){

        let rno = this.dataset.rno;

        location.href = "/resume/" + rno;

    })

});