document.addEventListener("DOMContentLoaded", function() {
	var searchBox = document.querySelector(".search-box")
	var clearIcons = document.querySelectorAll(".clear-icon"); // 수정된 부분
	var searchInputs = document.querySelectorAll(".search-input");
	var timer; // 타이머 전역 변수 선언
	const personIdx = document.getElementById('personIdx').value; // 현재 사용자 ID

	document.getElementById('btn-region').textContent = '지역 전국';

	// 경력 슬라이더의 현재 값에 따라 경력 텍스트를 초기화
	var experienceSliderValue = document.getElementById('experienceSlider').value;
	var experienceLabel = experienceSliderValue === '-1' ? '무관' : experienceSliderValue === '0' ? '신입' : experienceSliderValue === '11' ? '10년차 이상' : experienceSliderValue + '년차';
	document.getElementById('experienceValue').innerText = experienceLabel;
	document.getElementById('btn_experience').innerText = '경력 ' + experienceLabel;

	// 경력 슬라이더 이벤트 리스너
	document.getElementById('experienceSlider').addEventListener('input', function() {
		var value = this.value;
		var label = value === '-1' ? '무관' : value === '0' ? '신입' : value === '11' ? '10년차 이상' : value + '년차';
		document.getElementById('experienceValue').innerText = label;
		document.getElementById('btn_experience').innerText = '경력 ' + label;
	});

	// 지역 선택 이벤트 리스너
	var dropdownItems = document.querySelectorAll('.region-item');
	dropdownItems.forEach(function(item) {
		item.addEventListener('click', function() {
			var value = this.getAttribute('data-value');
			var dropdownButton = document.getElementById('btn-region');
			dropdownButton.textContent = '지역 ' + value;
		});
	});

	// 페이지 로드 시 기본값 설정
	document.getElementById('btn-region').textContent = '지역 전국';
	document.getElementById('btn_experience').innerText = '경력 무관';

	document.querySelectorAll('.dropdown-menu input[type="checkbox"]').forEach(item => {
		item.addEventListener('change', function() {
			let selectedItems = document.querySelectorAll('.dropdown-menu input[type="checkbox"]:checked');
			// 'item.value' 대신 'item.getAttribute('data-skill-name')'을 사용합니다.
			let selectedNames = Array.from(selectedItems).map(item => item.getAttribute('data-skill-name'));
			let textToShow = selectedNames.length > 1 ? selectedNames[0] + ` 외 ` + (selectedNames.length - 1) : selectedNames[0] || '기술스택';
			techStackButton.textContent = textToShow;
			selectedTechStacks.textContent = (selectedNames.join(', ') || '없음');
			console.log(selectedNames[0]); // 배열의 내용 확인
			console.log(selectedNames.length); // 배열의 길이 확인
		});
	});
	const searchInput = document.querySelector('.search-input.searchName');
	const searchPosting = document.getElementById('searchPosting');
	searchPosting.addEventListener('click', function() {
		sendData();
	});
	searchInput.addEventListener('keydown', function(event) {
		// 엔터 키가 눌렸을 때
		if (event.key === "Enter") {
			event.preventDefault(); // 폼이 자동으로 제출되는 것을 방지
			sendData(); // 검색 데이터 전송 함수 실행
		}
	});
	function sendData() {
		const region = document.getElementById('btn-region').textContent.replace('지역 ', '');
		const experience = document.getElementById('experienceValue').innerText;
		const selectedSkills = Array.from(document.querySelectorAll('.dropdown-menu input[type="checkbox"]:checked'))
			.map(item => item.value);
		const selectedJobs = Array.from(document.querySelectorAll('#selectedJobs .btn')).map(item => item.innerHTML);
		var companyandPostingName = document.querySelector(".searchName").value; // 여기를 수정했습니다.
		// 데이터를 객체로 구성
		const params = {
			region: region,
			experience: experience,
			selectedSkills: selectedSkills.join(','), // 배열을 콤마로 구분된 문자열로 변환
			selectedJobs: selectedJobs.join(','),
			keyword: companyandPostingName
		};

		// URLSearchParams를 사용하여 자동으로 쿼리 스트링 생성
		const queryString = new URLSearchParams(params).toString();
		const url = `/searchResult?` + queryString;

		fetch(url, {
			method: 'GET',
		})
			.then(response => response.text())
			.then(response => {
				document.querySelector("#result").innerHTML = response;
				window.updatescrapSvgs();
			})
			.catch(error => {
				console.error("Error: " + error);
				document.querySelector("#result").innerHTML = "검색 결과를 불러오는데 실패했습니다.";
			});
	}


	// 'X' 아이콘 클릭 이벤트
	document.querySelectorAll(".clear-icon").forEach(function(clearIcon) {
		clearIcon.addEventListener("click", function(event) {
			// 이벤트 전파를 막습니다.
			event.stopPropagation();

			// input 요소를 찾아서 내용을 지우고 포커스를 줍니다.
			var input = this.parentNode.querySelector("input");
			if (input) {
				input.value = "";
				input.focus();
			}
			// 'X' 아이콘 숨기고 돋보기 아이콘을 표시합니다.
			document.querySelectorAll(".clear-icon").forEach(el => el.style.display = "none");
			document.querySelectorAll(".search-icon").forEach(el => el.style.display = "block");

			// SearchJobType의 목록도 초기화합니다.
			var searchResultList = document.querySelector("#SearchJobType");
			if (searchResultList) {
				searchResultList.innerHTML = ''; // searchResult의 내용을 비웁니다.
			}
		});
	});


	// 입력란에 값 입력 시 'X' 아이콘 표시
	document.querySelectorAll(".search-box input").forEach(function(input) {
		input.addEventListener("input", function() {
			if (this.value == "") {
				// 입력란이 비었을 때 돋보기 아이콘 표시
				document.querySelectorAll(".clear-icon").forEach(el => el.style.display = "none");
				document.querySelectorAll(".search-icon").forEach(el => el.style.display = "block");
			} else {
				// 입력란에 텍스트가 있을 때 돋보기 아이콘 숨김
				document.querySelectorAll(".clear-icon").forEach(el => el.style.display = "block");
				document.querySelectorAll(".search-icon").forEach(el => el.style.display = "none");
			}
		});
	});

	searchInputs.forEach(function(input) { // 여러 입력란을 대상으로 합니다.
		input.addEventListener("input", function() {
			clearTimeout(timer);
			var keyword = this.value;
			var input = this;

			if (keyword.length === 0) {
				document.querySelector("#SearchJobType").style.display = "none";
				return;
			}

			timer = setTimeout(function() {
				fetch("/searchJobType?keyword=" + encodeURIComponent(keyword))
					.then(response => response.text())
					.then(response => {
						document.querySelector("#SearchJobType").innerHTML = response;
						document.querySelector("#SearchJobType").style.display = "block"; // 검색 결과를 다시 표시
						input.focus();
						searchBox.style.display = "block"; // 이 줄은 searchBox가 검색 결과와 같은 컨테이너를 가리키는 경우에만 필요합니다.
					})
					.catch(error => {
						console.error("Error: " + error);
						document.querySelector("#SearchJobType").innerHTML = "검색 결과를 불러오는데 실패했습니다.";
						// 실패 메시지도 보여줘야 하므로 여기에도 display를 설정할 수 있습니다.
						document.querySelector("#SearchJobType").style.display = "block";
						input.focus();
					});
			}, 300);

			document.body.addEventListener('click', function(e) {
				// 결과 텍스트 클릭 이벤트
				if (e.target.classList.contains('resultText')) {
					e.stopPropagation(); // 드랍다운이 닫히는 것을 방지
					const selectedText = e.target.textContent.trim();

					// 이미 선택된 직무인지 확인
					const existingButtons = document.querySelectorAll('#selectedJobs .btn');
					let isAlreadyAdded = false;
					existingButtons.forEach((btn) => {
						if (btn.textContent.trim() === selectedText) {
							isAlreadyAdded = true;
						}
					});

					// 중복되지 않는 경우에만 버튼 추가
					if (!isAlreadyAdded) {
						const newButton = `<button type="button" class="btn btn-primary m-1">${selectedText}</button>`;
						document.getElementById('selectedJobs').innerHTML += newButton;
					}
				}
			}, true); // 캡처 단계에서 이벤트 처리

			// selectedJobs 내의 버튼 클릭 이벤트 처리를 위한 이벤트 위임
			document.getElementById('selectedJobs').addEventListener('click', function(e) {
				if (e.target.tagName === 'BUTTON') {
					e.stopPropagation();
					e.target.remove(); // 클릭된 버튼 제거
				}
			});

			// 초기화 버튼 클릭 이벤트
			document.getElementById('resetButton').addEventListener('click', function(e) {
				e.preventDefault();
				e.stopPropagation();
				document.getElementById('selectedJobs').innerHTML = ''; // 선택된 직무 목록을 비움
			});

			// 적용 버튼 클릭 이벤트
			document.getElementById('applyButton').addEventListener('click', function(e) {
				e.preventDefault();
				const selectedButtons = document.querySelectorAll('#selectedJobs .btn');
				const jobTypeTxt = document.querySelector('#jobTypeText');
				let jobText = '';
				let count = 0;
				selectedButtons.forEach((btn, index) => {
					if (index === 0) {
						jobText += btn.textContent;
					}
					count++;
				});
				if (count > 0) { // 직무가 하나라도 선택되었는지 확인
					if (count > 1) {
						jobText += ` 외 ${count - 1}`;
					}
					// 직무 검색 버튼 텍스트 업데이트
					document.getElementById('searchButton').textContent = jobText;
					// 직무가 선택되었으므로 jobTypeTxt의 텍스트를 '직무'로 변경
					jobTypeTxt.textContent = '직무';
				} else {
					// 직무가 선택되지 않았을 경우, '직무 검색'으로 텍스트 변경
					document.getElementById('searchButton').textContent = '직무 검색';
					jobTypeTxt.textContent = '직무 전체';
				}
			});

		});

	});
});