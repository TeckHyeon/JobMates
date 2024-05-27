document.addEventListener("DOMContentLoaded", function() {
    window.updatescrapSvgs = function() {
        const scrapSvgs = document.querySelectorAll('.scrapSvg');
        scrapSvgs.forEach(function(button) {
            const postingIdx = button.getAttribute('data-posting-idx');
            const personIdx = document.getElementById('personIdx').value;

            // 스크랩 상태 확인 요청
            fetch(`/checkScrap?postingIdx=` + postingIdx + `&personIdx=` + personIdx, {
                method: 'GET',
            })
                .then(response => response.json())
                .then(isScraped => {
                    button.setAttribute('data-scraped', isScraped);
                    if (isScraped) {
                        button.setAttribute('fill', 'yellow');
                    } else {
                        button.setAttribute('fill', 'none');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        });
    }
});
