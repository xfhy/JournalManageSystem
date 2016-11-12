package liang.guo.diary;

import liang.guo.diary.operation.KeepDiary;
import liang.guo.diary.util.Utility;

/**
 * 2016年9月10日12:49:24
 * 
 * 主菜单
 * 
 * @author 郭亮
 * 
 */
public class Menu {

	private boolean exitFalg = false;  //退出?
	private String menuName = "主菜单";   //菜单名字     控制当前显示哪一级菜单
	private String mainMenuName = "主菜单";   
	private String findDiaryMainMenu = "查找日记主菜单";
	
	/**
	 * 跑起来
	 */
	public void run(){
		while(!exitFalg){  //判断用户是否退出   是否需要继续显示菜单
			if(menuName.equals(mainMenuName)){  //根据菜单名字显示相应的菜单
				showFirstLevelMenu();  //显示一级菜单
			} else if(menuName.equals(findDiaryMainMenu)) {
				showFindDiary();  //查找日记菜单
			}
		}
	}
	
	/**
	 * 显示一级菜单 编号:1
	 */
	public void showFirstLevelMenu(){
		menuName = mainMenuName;
		String loginSystemMenu = "1. 登录系统；";
		if(Journal.isLogin){
			if(Journal.currentUser == null){
				loginSystemMenu = "1. 退出登录；";
			} else {
				loginSystemMenu = "1. ["+Journal.currentUser.getUserName()+"]退出登录；";
			}
			System.out.println(loginSystemMenu);
		} else {
			System.out.println(loginSystemMenu);
		}
		System.out.println("2. 系统设置；");
		System.out.println("3. 写日记；");
		System.out.println("4. 查找日记；");
		System.out.println("5. 退出系统；");
		System.out.println("请选择(1~5)：");
		int userSelection = Utility.judgmentInput(5);   //让用户输入选择
		
		//如果用户选择的是2-4选项,则判断他是否已经登录
		if(userSelection >= 2 && userSelection <= 4){
			//判断用户是否已经登录
			if(!Journal.isLogin){
				System.out.println("用户未登录，请先登录。");
				return ;
			}
		}
		
		switch(userSelection){  //执行用户选择的功能
		case 1:
			loginSystem();
			break;
		case 2:
			systemSettings();
			break;
		case 3:
			keepDiary();
			break;
		case 4:
			showFindDiary();
			break;
		case 5:
			exitSystem();
			break;
		default:
			System.out.println("噫,没有这个选项");
			break;
		}
	}
	
	/**
	 * 查找日记菜单
	 */
	public void showFindDiary() {
		menuName = findDiaryMainMenu;
		System.out.println("1. 按标题查找；");
		System.out.println("2. 按内容查找；");
		System.out.println("3. 按日期查找；");
		System.out.println("4. 按心情查找；");
		System.out.println("5. 按天气查找；");
		System.out.println("6. 返回上一层菜单；");
		System.out.println("请选择(1~6)：");
		int userSelection = Utility.judgmentInput(6);     //让用户输入选择
		switch (userSelection) {
		case 1:
			searchByTitle();
			break;
		case 2:
			searchByContent();
			break;
		case 3:
			searchByDate();
			break;
		case 4:
			searchByMood();
			break;
		case 5:
			searchByWeather();
			break;
		case 6:
			menuName = mainMenuName;
			break;
		default:
			System.out.println("噫,没有这个选项");
			break;
		}
	}
	
	/**
	 * 登录系统  or 退出系统
	 */
	public void loginSystem(){
		//判断是否已经登录
		if(Journal.isLogin){   
			Journal.isLogin = false;
			System.out.println("\t\n登出系统,谢谢使用~~\n");
			/*---------------------------2016年9月29日22:49:35  断点------------------------------------------------------*/
		} else {  //没登录的话,则去登录
			LoginMenu loginMenu = new LoginMenu();  //用于用户登录的类
			loginMenu.run();  //让用户去登录
		}
	}
	
	/**
	 * 系统设置
	 */
	public void systemSettings(){
		System.out.println("\t\t正在执行系统设置\t\t");
	}
	
	/**
	 * 写日记
	 */
	public void keepDiary(){
		System.out.println("\t\t正在执行写日记\t\t");
		KeepDiary writeDiary = new KeepDiary();
		writeDiary.writeDiary(Journal.currentUser.getUserName(),Journal.currentUser.getOwnDiaries());
	}
	
	/**
	 * 查找日记
	 */
	public void findDiary(){
		System.out.println("\t\t正在执行查找日记\t\t");
	}
	
	/**
	 * 退出系统
	 */
	public void exitSystem(){
		System.out.println("\t\t谢谢使用,再见!\t\t");
		exitFalg = true;
	}
	
	/**
	 * 按标题查找日记
	 */
	public void searchByTitle(){
		System.out.println("\t\t正在执行按照标题查找日记\t\t");
	}
	
	/**
	 * 按照内容查找日记
	 */
	public void searchByContent(){
		System.out.println("\t\t按照内容查找\t\t");
	}
	
	/**
	 * 按照日期查找日记
	 */
	public void searchByDate(){
		System.out.println("\t\t按照日期查找日记\t\t");
	}
	
	/**
	 * 按照心情查找日记
	 */
	public void searchByMood(){
		System.out.println("\t\t按照心情查找日记\t\t");
	}
	
	/**
	 * 按照天气查找日记
	 */
	public void searchByWeather(){
		System.out.println("\t\t按照天气查找日记\t\t");
	}
	
}
