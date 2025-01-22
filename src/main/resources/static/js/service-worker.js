document.addEventListener("DOMContentLoaded", function () {
    const userId = sessionStorage.getItem('userId');
    console.log("여기 호출됨 " + userId)
    if (userId) {
        console.log("페이지 로드 시 SSE 연결 시작: ", userId);
        setupSSE();
    } else {
        console.log("로그인 상태가 아니므로 SSE 연결하지 않음");
    }
});

function setupSSE() {
    const userId = sessionStorage.getItem('userId');
    if (userId) {
        console.log("SSE 연결 시작: ", userId);
        const eventSource = new EventSource(`/sse/${userId}`);

        eventSource.onmessage = function (event) {
            showNotification(event.data);
        };

        eventSource.onerror = function (error) {
            console.error('SSE 오류:', error);
            eventSource.close();
            if (userId) {
                setTimeout(setupSSE, 1000);
            }
        };
    } else {
        console.error('userId 없음 SSE 연결 못함');
    }
}

//TODO: 추후 로직 수정 바빠서 일단 이렇게 박아둠
function showNotification(message) {
    const currentPath = window.location.pathname;
    console.log("현재 경로: " + currentPath);
    if (message.includes("채용공고")) {
        confirm_modal.on(
            "★채용공고",
            message.split("!")[0],
            () => window.location.href = '/job-open/detail/' + message.split("!")[1],
            () => confirm_modal.off,
            '바로가기',
            '닫기'
        );
    } else {
        if (currentPath.includes('detail')) {
            console.log("현재 페이지 경로에 'detail'이 포함");
            confirm_modal.on(
                "★이력서 지원 도착★",
                message,
                () => window.location.reload(),
            );
        } else {
            alert_modal.on("★이력서 지원 도착★", message);
        }
    }
}
