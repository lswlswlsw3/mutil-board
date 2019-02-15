<!-- 편집 버튼 눌렀을때, 확인 -->
function updateBefore() {
	var result = confirm("해당 게시물을 편집하겠습니까?");
	return result;
}

<!-- 삭제 버튼 눌렀을때, 확인 -->
function deleteBefore() {
	var result = confirm("해당 게시물을 삭제하겠습니까?");
	return result;
}

var sortType = 'asc';

<!-- 테이블의 제목을 눌렀을때 정렬 판단 -->
function sortTable(select){ // select는 정렬기준 제목 또는 날짜
	var table = document.getElementsByTagName('table');

    if(sortType === 'asc') {
        sortType = 'desc';
        sortTableDesc(table[0].rows, select);
    } else {
        sortType = 'asc';
        sortTableAsc(table[0].rows, select);
    }
}

<!-- 테이블의 제목을 눌렀을때 내림차순 정렬 -->
function sortTableDesc(rows, select) {
	var select = select; // select는 정렬기준 제목 또는 날짜
	var num = 0; // 정렬기준에 따라 열번호 저장
	var chkSort = true; // sorting할 데이터가 없을때 까지 반복을 위한 변수
	
	while(chkSort) { // sorting할 데이터가 없을때 까지 반복
		chkSort = false;
		// 1행 부터 시작하는 이유는 0번째 행이 필드명이기 때문에
		// 테이블 행의 크기 - 1 = 4 => 1~4까지 반복 
		for (var i = 1; i < (rows.length - 1); i++) {
			if(select == 'title') { // 정렬기준이 제목인 경우
				num = 1;
			} else { // 정렬기준이 날짜인 경우
				num = 3;
			}
			
			var fCell = rows[i].cells[num];		// 1행 0열
			var sCell = rows[i + 1].cells[num];	// 2행 0열
			if (fCell.innerHTML.toLowerCase() < sCell.innerHTML.toLowerCase()) { // 1행 0열의 값 > 2행 0열의 값 
				rows[i].parentNode.insertBefore(rows[i + 1], rows[i]); // 다음 행을 현재 행 앞으로 이동
				chkSort = true; // sorting할 데이터가 있다고 판단
			}
		}		
	}
}

<!-- 테이블의 제목을 눌렀을때 오름차순 정렬 -->
function sortTableAsc(rows, select) {
	var select = select; // select는 정렬기준 제목 또는 날짜
	var num = 0; // 정렬기준에 따라 열번호 저장
	var chkSort = true; // sorting할 데이터가 없을때 까지 반복을 위한 변수
	
	while(chkSort) { // sorting할 데이터가 없을때 까지 반복
		chkSort = false;
		// 1행 부터 시작하는 이유는 0번째 행이 필드명이기 때문에
		// 테이블 행의 크기 - 1 = 4 => 1~4까지 반복 
		for (var i = 1; i < (rows.length - 1); i++) {
			if(select == 'title') { // 정렬기준이 제목인 경우
				num = 1;
			} else { // 정렬기준이 날짜인 경우
				num = 3;
			}
			var fCell = rows[i].cells[num];		// 1행 0열
			var sCell = rows[i + 1].cells[num];	// 2행 0열
			if (fCell.innerHTML.toLowerCase() > sCell.innerHTML.toLowerCase()) { // 1행 0열의 값 > 2행 0열의 값 
				rows[i].parentNode.insertBefore(rows[i + 1], rows[i]); // 다음 행을 현재 행 앞으로 이동
				chkSort = true; // sorting할 데이터가 있다고 판단
			}
		}		
	}
}