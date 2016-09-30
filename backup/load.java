void load() {
		try {
			File load = new File("result_list.txt");
			FileReader loadFR = new FileReader(load);
			BufferedReader loadBR = new BufferedReader(loadFR);
			String loading = loadBR.readLine();
			// String str[] = loadBR.split("@");

		} catch (Exception e) {
			failLoadJD.setVisible(true);
		}
	}
