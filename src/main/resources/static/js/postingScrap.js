document.addEventListener("DOMContentLoaded", function() {
	const personIdx = document.getElementById('personIdx').value; // 현재 사용자 ID

	function updatescrapSvgs() {
		const scrapSvgs = document.querySelectorAll('.scrapSvg');
		scrapSvgs.forEach(function(button) {
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

	updatescrapSvgs(); // 페이지 로드 시 스크랩 버튼 상태 갱신

	document.addEventListener('click', async function(e) {
		const button = e.target.closest('.scrapBtn'); // 상위 요소인 버튼을 찾기
		if (button) {
			e.preventDefault(); // 기본 동작 방지
			e.stopPropagation(); // 이벤트 전파 방지
			const scrapSvg = button.querySelector('.scrapSvg');
			const postingIdx = scrapSvg.getAttribute('data-posting-idx');
			const personIdx = document.getElementById('personIdx').value;
			const isScraped = scrapSvg.getAttribute('data-scraped') === 'true';

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
					updatescrapSvgs(); // 모든 스크랩 버튼 상태 갱신
				} else {
					throw new Error('네크워크 에러');
				}
			} catch (error) {
				console.error('Error:', error);
				alert('오류가 발생했습니다. 다시 시도해주세요.');
			}
			return; // 여기서 함수 실행 종료
		}
		// detail-div 클릭 시 페이지 이동 로직을 scrapSvg 로직과 별도로 처리
		if (e.target && e.target.closest('.detail-div')) {
			const detailDiv = e.target.closest('.detail-div');
			const postingIdx = detailDiv.getAttribute('data-posting-idx');
			window.location.href = '/mainPosting/' + postingIdx;
		
		}
	});
});