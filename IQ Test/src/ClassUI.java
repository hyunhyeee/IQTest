public class ClassUI {

	// 시작 화면
	public void startUI() {
		// 첫 게임 시작화면 구성
		// 시작하기 버튼 생성
	}

	
	// 메뉴 구성
	public void MenuUI() {
		// 메뉴 화면 구성
		// 배치관리차를 통해 테스트 문제 버튼 생성
	}
	
	
	// 게임 진행 화면
	public void gameUI() {
		// 게임 전체적인 진행 화면 구성
	}
	
	private void gameUI_DisplayPanel() {
		// 게임 진행 화면 구성시 디스플레이 화면
		// 구성에는 변경 없으니 private으로 설정
	}
	
	private void gameUI_ControlPanel() {
		// 게임 진행 화면 구성시 디스플레이 하단 입력과 보내기 버튼
		// 구성에는 변경 없으니 private으로 설정
	}
	
	public void game1_CalcRandom() {
		// 산술 문제시 랜덤 값 설정
	}
	
	public void game1_Calc() {
		// 산술 문제 게임 구성
	}
	
	
	
	// 게임 종료 화면
	public void gameoverUI() {
		// 게임 종료 시 최종점수 띄우는 화면 구성
	}
	
	private void gameoverUI_DisplayPanel() {
		// 게임 종료 화면 구성시 디스플레이 화면
		// 구성에는 변경 없으니 private으로 설정
	}
	
	private void gameoverUI_ControlPanel() {
		// 게임 종료 화면 구성시 디스플레이 하단 재시작, 종료 버튼
		// 구성에는 변경 없으니 private으로 설정
	}

	
	public static void main(String[] args) {
	    new MenuUI();
	}
}
