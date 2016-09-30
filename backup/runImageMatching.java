void runImageMatching() {
		// startMatchingJB 클릭시 실행됨.
		// 이미지 비교 시작
		/** 경로 참고 */
		int result = ImageMatching.ifCompareFeature(imgPath, path1);

		/**
		 * 이미지가 같으면 1 반환, 이미지가 다르면 2를 반환한다.
		 */

		if (result == 1) {	// 이미지가 같으면
			// 리스트에 파일 이름 올린다.
			fileName[count] = path1.toString(); /** */
			count++; // 다음 배열 위치
		} else if(result == 0 ) {	// 이미지가 다르면
			// 아무것도 안 올려도 된다! 오예!
			System.out.println("pass. count : " + count);

		} else {
			// 두개 모두 아니면 오류 출력
			errorJD.setVisible(true);
		}
	}
