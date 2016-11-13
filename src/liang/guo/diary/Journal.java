package liang.guo.diary;

import java.util.ArrayList;
import java.util.List;

import liang.guo.diary.enumerate.MoodType;
import liang.guo.diary.enumerate.WeatherType;
import liang.guo.diary.model.Date;
import liang.guo.diary.model.Diary;
import liang.guo.diary.model.User;


/**
 *  日记
 *  
 *  这是主类
 * 
 * @author 郭亮
 * 
 * 2016年9月10日12:45:38 第一次  完成菜单显示功能
 * 
 * 2016年9月24日19:21:07 第二次改动  完成用户注册和登录功能
 * 
 * 2016年10月18日11:16:29 第三次 将属性封装到User中,并将相关属性的验证方法也放到User里面.
 * 
 * 2016年11月12日16:07:18 第四次 将数组改成集合,将Diary(日记类)加入User的属性
 * 
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
	/**
	 * 当前用户
	 */
	public static User currentUser = new User();

	/**
	 * 所有用户的信息   
	 */
	private static List<User> allUserInfo = new ArrayList<>();
	
	public static void main(String[] args) {
		test();
		Menu menu = new Menu(); // 实例化菜单类 
		menu.run(); //跑起
		
		showAllUserInfo();    //测试用
	}

	/**
	 * 获取所有的用户信息集合
	 * @return
	 */
	public static List<User> getAllUserInfo() {
		return allUserInfo;
	}

	
	/**
	 * 设置所有的用户信息的集合
	 * @param allUserInfo
	 */
	public static void setAllUserInfo(List<User> allUserInfo) {
		Journal.allUserInfo = allUserInfo;
	}

	/**
	 * 输出全部的用户信息
	 */
	public static void showAllUserInfo(){
		System.out.println("所有用户信息如下:");

		//遍历List(所有用户)集合
		for(User user : allUserInfo){
			System.out.println(user.toString()+user.getOwnDiaries().toString());
		}
	}
	
	/**
	 * 为了测试方便,加入了一个测试用户
	 */
	public static void test(){
		User userTemp = new User();
		userTemp.setUserName("xfhy666");
		userTemp.setUserDisplayName("xfhy");
		userTemp.setUserPassword("qwert;123");
		userTemp.setUserMailBox("123456789@qq.com");
		userTemp.setUserChooseProblem(0);
		userTemp.setUserQuestionAnswer("xfhy");
		
		Diary diary1 = new Diary();
		diary1.setMood(MoodType.DELIGHTED);
		diary1.setWeather(WeatherType.CLOUDY);
		diary1.setTitle("你好啊");
		diary1.setContent("这是内容");
		diary1.setDate(new Date("2012-12-12"));
		
		Diary diary2 = new Diary();
		diary2.setMood(MoodType.DEPRESSED);
		diary2.setWeather(WeatherType.FOG);
		diary2.setTitle("Hello");
		diary2.setContent("这是内容............................");
		diary2.setDate(new Date("2001-02-02"));
		
		Diary diary3 = new Diary();
		diary3.setMood(MoodType.EXCITEMENT);
		diary3.setWeather(WeatherType.OVERCAST);
		diary3.setTitle("World");
		diary3.setContent("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
		diary3.setDate(new Date("2007-07-06"));
		
		List<Diary> ownDiaries = new ArrayList<>();
		ownDiaries.add(diary1);
		ownDiaries.add(diary2);
		ownDiaries.add(diary3);
		
		userTemp.setOwnDiaries(ownDiaries);
		Journal.getAllUserInfo().add(userTemp);
		Journal.howManyPeople++;
	}
	
}
