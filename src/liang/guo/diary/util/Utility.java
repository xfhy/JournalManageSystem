package liang.guo.diary.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import liang.guo.diary.Journal;
import liang.guo.diary.model.User;

/**
 * 
 * 2016年9月24日 下午9:50:56
 * @author XFHY
 * 
 * 工具类   程序中用到的共有的方法
 * 
 */
public class Utility {
	
	/**
	 * 当前用户
	 */
	public static User currentUser = new User();

	/**
	 * 用户个数信息  文件 的名称
	 */
	public final static String USERCOUNTSFILENAME = "userCounts.txt";
	
	public final static String USERINFOFILENAME = "User.xfhy";
	
	/**
	 * 所有用户的信息   
	 */
	private static List<User> allUserInfo = new ArrayList<>();
	
	/**
	 * 接收用户输入得菜单选项,并判断用户输入值是否正确,正确则返回
	 * 参数:最大可以选择几    1~maxChoice
	 */
	@SuppressWarnings("resource")
	public static int judgmentInput(int maxChoice){
		Scanner inputChoice = new Scanner(System.in);
		boolean inputFlag = true;
		int userSelection = -1;
		while(inputFlag){
			try {
				userSelection = inputChoice.nextInt();   //获得用户输入的整数
			} catch (Exception e) {
				System.err.println("捕获到异常:"+e.getClass());
				System.out.println("输入不合法,只能输入数字,请重新输入....");
				inputChoice = new Scanner(System.in);
				continue;
			}
			
			if(userSelection < 1 || userSelection > maxChoice){  //判断输入的整数是否合法
				System.out.println("输入不合法,未输入合法的数字选项,请重新输入....");
				inputChoice = new Scanner(System.in);
			} else {
				break;
			}
		}

		return userSelection;
	}
	
	/**
	 * 让用户输入int值
	 * @return
	 */
	@SuppressWarnings("resource")
	public static int judgmentInputInt(){
		Scanner inputInt = new Scanner(System.in);
		boolean inputFlag = true;
		int userInput = -1;
		while(inputFlag){
			/*---------------2016年10月27日20:10:48该加异常-------------------------*/
			try {
				userInput = inputInt.nextInt(); // 获得用户输入的整数
			} catch (Exception e) {
				System.err.println("捕获到异常:"+e.getClass());
				System.err.println("输入不合法,只能输入数字,请重新输入....");
				inputInt = new Scanner(System.in);
				continue;
			}
			
			inputFlag = false;
		}

		return userInput;
	}
	
	/**
	 * 获取所有的用户信息集合
	 * @return
	 */
	public static List<User> getAllUserInfo() {
		return allUserInfo;
	}
	
	/**
	 * 设置所有的用户信息的集合   这里一般不这么用,用addAllUserInfo
	 * @param allUserInfo    
	 */
	public static void setAllUserInfo(List<User> allUserInfo) {
		Utility.allUserInfo = allUserInfo;
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
	 * 保存用户的个数信息 到文件中
	 */
	public static boolean saveUserCountsToFile(){
		
		boolean saveSuccess = true;
		
		File file = new File(USERCOUNTSFILENAME);
		if( file.exists() ){    //如果已经存在该文件,则删除,重新写
			if(!file.delete()){
				return false;
			}
		}
		
		FileOutputStream userCountsFileOutputStream = null;
		ObjectOutputStream userCountsObjectOutputStream = null;
		try {
			//这里用   ObjectOutputStream   void writeInt(int val) 写入一个 32 位的 int 值。 
			userCountsFileOutputStream = new FileOutputStream(USERCOUNTSFILENAME);
			userCountsObjectOutputStream = new ObjectOutputStream(userCountsFileOutputStream);
			userCountsObjectOutputStream.writeInt(Utility.getAllUserInfo().size());  //用户信息个数
		} catch (FileNotFoundException e){
			System.err.println("异常信息可能是:该文件存在，但它是一个目录，而不是一个常规文件；"
					+ "或者该文件不存在，但无法创建它；抑或因为其他某些原因而无法打开它");
			e.printStackTrace();
			saveSuccess = false;
		} catch (IOException e1) {
			System.err.println("存储用户信息个数到文件时,产生IO异常");
			e1.printStackTrace();
			saveSuccess = false;
		} finally {
			try {
				userCountsObjectOutputStream.close();
			} catch (Exception e2) {
				System.err.println("关闭ObjectOutputStream时产生异常");
				e2.printStackTrace();
			}
			try {
				userCountsFileOutputStream.close();
			} catch (Exception e2) {
				System.err.println("关闭FileOutputStream时产生异常");
			}
		}
		return saveSuccess;
	}
	
	/**
	 * 通过文件读取  以前保存的用户的个数信息
	 */
	public static int loadUserCountsByFile(){
		
		File file = new File(USERCOUNTSFILENAME);
		if( !file.exists() ){    //如果用户第一次进入系统,那么用户个数的那个文件不存在,直接返回0
			return 0;
		}
		
		FileInputStream userCountsFileInputStream = null;
		ObjectInputStream userCountsObjectInputStream = null;
		try {
			userCountsFileInputStream = new FileInputStream(USERCOUNTSFILENAME);
			userCountsObjectInputStream = new ObjectInputStream(userCountsFileInputStream);
			return userCountsObjectInputStream.readInt();    //返回读取到的Int(用户个数)
		} catch (FileNotFoundException e) {
			System.err.println("异常信息可能是:该文件存在，但它是一个目录，而不是一个常规文件；"
					+ "或者该文件不存在，但无法创建它；抑或因为其他某些原因而无法打开它");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				userCountsObjectInputStream.close();
			} catch (Exception e2) {
				System.err.println("关闭ObjectOutputStream时产生异常");
				e2.printStackTrace();
			}
			try {
				userCountsFileInputStream.close();
			} catch (Exception e2) {
				System.err.println("关闭FileOutputStream时产生异常");
			}
		}
		return 0;   //如果文件不存在,则返回0     或者其他错误
		
	}
	
	/**
	 * 保存用户信息(User)到文件(User.xfhy)中
	 */
	public static boolean saveUserToFile(){
		boolean saveSuccess = true;
		
		File file = new File(USERINFOFILENAME);
		if( file.exists() ){    //如果已经存在该文件,则删除,重新写
			if(!file.delete()){
				return false;
			}
		}
		
		FileOutputStream userFileOutputStream = null;
		ObjectOutputStream userObjectOutputStream = null;
		try {
			userFileOutputStream = new FileOutputStream(USERINFOFILENAME);
			userObjectOutputStream = new ObjectOutputStream(userFileOutputStream);
			
			for(User user : Utility.getAllUserInfo()){
				userObjectOutputStream.writeObject(user);
			}
			
		} catch (FileNotFoundException e) {
			System.err.println("异常信息可能是:该文件存在，但它是一个目录，而不是一个常规文件；"
					+ "或者该文件不存在，但无法创建它；抑或因为其他某些原因而无法打开它");
			e.printStackTrace();
			saveSuccess = false;
		} catch (IOException e) {
			System.err.println("存储用户信息到文件时,产生IO异常");
			e.printStackTrace();
			saveSuccess = false;
		} finally {
			try {
				userObjectOutputStream.close();
			} catch (Exception e2) {
				System.err.println("关闭ObjectOutputStream时产生异常");
				e2.printStackTrace();
			}
			try {
				userFileOutputStream.close();
			} catch (Exception e2) {
				System.err.println("关闭FileOutputStream时产生异常");
			}
		}
		return saveSuccess;
	}
	
	/**
	 * 从文件(User.xfhy)中读取
	 */
	public static void loadUserFileToProgram(){
		
		File file = new File(USERINFOFILENAME);
		if( !file.exists() ){    //如果用户第一次进入系统,那么用户个数的那个文件不存在,直接返回0
			return ;
		}
		
		FileInputStream userFileInputStream = null;
		ObjectInputStream userObjectInputStream = null;
		try {
			userFileInputStream = new FileInputStream(USERINFOFILENAME);
			userObjectInputStream = new ObjectInputStream(userFileInputStream);
			int counts = loadUserCountsByFile();    //从文件中读取到User的个数
			for(int i=0; i < counts; i++){
				User user = (User) userObjectInputStream.readObject();
				Utility.getAllUserInfo().add(user);   //添加这个User类到用户列表中
			}
		} catch (FileNotFoundException e) {
			System.err.println("从文件中读取用户信息时出现异常,文件未找到,但是又由于某些原因无法创建");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("读取用户信息到文件时,产生IO异常");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("类转化异常,源文件可能已被修改");
			e.printStackTrace();
		} finally {
			try {
				userObjectInputStream.close();
			} catch (Exception e2) {
				System.err.println("关闭ObjectOutputStream时产生异常");
				e2.printStackTrace();
			}
			try {
				userFileInputStream.close();
			} catch (Exception e2) {
				System.err.println("关闭FileOutputStream时产生异常");
			}
		}
	}
	
	/**
	 * 为了测试方便,加入了一个测试用户
	 */
	public static void test(){
		/*User userTemp = new User();
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
		saveUserToFile();
		saveUserCountsToFile();*/
		
		Journal.howManyPeople++;
	}
	
	
}
