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

