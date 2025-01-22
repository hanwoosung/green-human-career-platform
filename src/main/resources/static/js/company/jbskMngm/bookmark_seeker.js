$(function (){

    $(document).on("click", ".page-link", function (){

        let href = this.dataset.href;

        location.href = href + "&search=" + document.querySelector("input[name=search]").value;

    });

    $(document).on("click", ".list", function (){

        let rno = this.dataset.rno;

        location.href = "/resume/" + rno;

    });


});