package liang.guo.diary;

import liang.guo.diary.model.User;
import liang.guo.diary.util.Utility;

/**
 *  日记
 * 
 * @author 郭亮
 * 
 * 2016年9月10日12:45:38 第一次  完成菜单显示功能
 * 
 * 2016年9月24日19:21:07 第二次改动  完成用户注册和登录功能
 * 
 * 2016年10月18日11:16:29 第三次 将属性封装到User中,并将相关属性的验证方法也放到User里面.
 * 
 * 2016年10月18日20:15:01 
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
	private static User allUserInfo[] = new User[1000];
	
	public static void main(String[] args) {
		Menu menu = new Menu(); // 实例化菜单类 
		menu.run(); //跑起
		
		//showAllUserInfo();    //测试用
	}

	/**
	 * 获取所有的用户信息集合
	 * @return
	 */
	public static User[] getAllUserInfo() {
		return allUserInfo;
	}
	
	/**
	 * 返回当前用户数组中该索引处的用户信息
	 * @param index  索引
	 * @return  如果索引不合法,则返回null
	 */
	public static User getUserAt(int index) throws ArrayIndexOutOfBoundsException{
		try {
			return allUserInfo[index];
		} catch (ArrayIndexOutOfBoundsException e) {    //数组下标越界
			System.err.println("获取当前用户数组中该索引处的用户信息时出错,数组下标越界");
			throw new ArrayIndexOutOfBoundsException("获取当前用户数组中该索引处的用户信息时出错,数组下标越界");
		} catch (Exception e2){
			e2.printStackTrace();
		}
		return null;
		
		/*if(index >= 0 && index < howManyPeople){
			return allUserInfo[index];
		}*/
	}
	
	/**
	 * 设置所有的用户信息的集合
	 * @param allUserInfo
	 */
	public static void setAllUserInfo(User[] allUserInfo) {
		Journal.allUserInfo = allUserInfo;
	}

	/**
	 * 输出全部的用户信息
	 */
	public static void showAllUserInfo(){
		System.out.println("所有用户信息如下:");
		int length = -1;
		for(User user : Journal.allUserInfo){
			length++;
			if(length < Journal.howManyPeople){
				System.out.println(user);
			}
		}
	}
	
}
