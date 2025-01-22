const alert_modal = {

    on: (title = "", detail = "") => {

        document.querySelector(".modal-alert").classList.add("on");

        document.querySelector("#alert_title").innerHTML = title;
        document.querySelector("#alert_detail").innerHTML = detail;
    },

    off: () => {
        document.querySelector(".modal-alert").classList.remove("on");
    }

}

const confirm_modal = {

    on: (title = "",
         detail = "",
         okCallback,
         ngCallback = confirm_modal.off,
         ok = "확인",
         ng = "취소") => {

        document.querySelector(".modal-confirm").classList.add("on");

        document.querySelector("#confirm_title").innerHTML = title;
        document.querySelector("#confirm_detail").innerHTML = detail;
        document.querySelector(".confirm-delete-ok").innerHTML = ok;
        document.querySelector(".confirm-delete-ng").innerHTML = ng;

        $(".modal-confirm .confirm-delete-ok").click(function(e){

            if(typeof okCallback != 'undefined' && okCallback){
                if(typeof okCallback == 'function'){
                    okCallback();
                }
            }

            confirm_modal.off();
        });

        $(".modal-confirm .confirm-delete-ng").click(function(e){

            if(typeof ngCallback != 'undefined' && ngCallback){
                if(typeof ngCallback == 'function'){
                    ngCallback();
                }
            }
            confirm_modal.off();
        });
    },

    off: () => {
        document.querySelector(".modal-confirm").classList.remove("on");
    }

}

