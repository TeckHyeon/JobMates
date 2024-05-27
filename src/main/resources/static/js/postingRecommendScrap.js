document.addEventListener("DOMContentLoaded", function() {
	const personIdx = document.getElementById('personIdx').value; // 현재 사용자 ID

	function updateScrapButtons() {
		const scrapButtons = document.querySelectorAll('.scrapBtn');
		scrapButtons.forEach(function(button) {
			const postingIdx = button.getAttribute('data-posting-idx');

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

	updateScrapButtons(); // 페이지 로드 시 스크랩 버튼 상태 갱신

	document.addEventListener('click', async function(e) {
    if (e.target && e.target.classList.contains('.scrapBtn')) {
        const button = e.target;
        const postingIdx = button.getAttribute('data-posting-idx');
        const isScraped = button.getAttribute('data-scraped') === 'true';

        try {
            let response;
            if (isScraped) {
                // 스크랩 삭제 요청
                response = await fetch(`/scrapDelete`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ postingIdx, personIdx }),
                });
            } else {
                // 스크랩 추가 요청
                response = await fetch('/scrapAdd', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ postingIdx, personIdx }),
                });
            }

            if (response.ok) {
                const message = isScraped ? '스크랩이 해제되었습니다.' : '스크랩되었습니다.';
                alert(message);
                updateScrapButtons(); // 모든 스크랩 버튼 상태 갱신
            } else {
                throw new Error('Network response was not ok.');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('오류가 발생했습니다. 다시 시도해주세요.');
        }
    } else if (e.target && e.target.closest('.detail-div') && !e.target.closest('.scrapBtn')) {
        const detailDiv = e.target.closest('.detail-div');
        const postingIdx = detailDiv.getAttribute('data-posting-idx');
        window.location.href = '/MainPosting/' + postingIdx;
    }
});

});