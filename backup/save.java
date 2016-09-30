void save() {
		try {
			BufferedWriter save = new BufferedWriter(new FileWriter("result_list.txt"));
			/** for문으로 배열 값을 저장하면 되지 않을까? */
			save.write(listJL.getSelectedIndex()); // 선택된 jlist내용 넣기
			/** */
			save.close();
		} catch (Exception e) {

		}
	}
