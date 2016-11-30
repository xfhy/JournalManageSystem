package liang.guo.diary;



import liang.guo.diary.util.Utility;


/**
 *  日记
 *  
 *  这是主类
 * 
 * @author XFHY
 * 
 * 2016年9月10日12:45:38 第一次  完成菜单显示功能
 * 
 * 2016年9月24日19:21:07 第二次改动  完成用户注册和登录功能
 * 
 * 2016年10月18日11:16:29 第三次 将属性封装到User中,并将相关属性的验证方法也放到User里面.
 * 
 * 2016年11月12日16:07:18 第四次 将数组改成集合,将Diary(日记类)加入User的属性
 * 
 * 2016年11月29日11:07:43 第五次 加入图形界面
 */
public class Journal {

	/**
	 * 是否已经登录
	 */
	public static boolean isLogin = false; 
	/**
	 * 当前用户是第几个
	 */
	public static int howManyPeople = 0;
	
	
	public static void main(String[] args) {
		
		//test();           //测试用
		//saveUserToFile();   //测试用
		//saveUserCountsToFile();  //测试用
		
		Utility.loadUserFileToProgram();    //装载之前用户保存成文件的信息
		
		Menu menu = new Menu(); // 实例化菜单类 
		menu.run(); //跑起 
		
		Utility.showAllUserInfo();    //测试用
	}

	
}
