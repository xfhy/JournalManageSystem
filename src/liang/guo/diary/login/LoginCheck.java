package liang.guo.diary.login;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import liang.guo.diary.model.User;
import liang.guo.diary.util.Utility;

/**
 * @author  XFHY
 * @date  2016年11月30日 下午8:59:52
 * @package liang.guo.diary.login
 * @function 用来检查用户登录是否成功   专门为LoginJournalSystem类服务
 */
public class LoginCheck {
	
	/**
	 * 连数据库都没有连接成功
	 */
	public final static int CONNECTTODATABASEFAILED = -1;
	/**
	 * 不是合法用户
	 */
	public final static int NOTLEGITIMATEUSERS = 0;
	/**
	 * 登录成功,找到了合法的用户
	 */
	public final static int LOGINSYSTEMSUCCESS = 1;
	
	
	/**
	 * 判断登录是否成功
	 * @param name 用户名
	 * @param passwrd 密码
	 * @return 返回3种状态 1:登录成功,找到了合法的用户     0:不是合法用户      -1:连数据库都没有连接成功
	 */
	public static int isSucceed(String name,String passwrd){
		Icon icon;
		
		//用户名长度在6~20   
		if(name.length() < 6 || name.length() > 20){
			icon = new ImageIcon("image/dialog/警告.png");
			JOptionPane.showMessageDialog(null, "用户名长度为6~20位,请检查是否符合规范", "登录失败",
					JOptionPane.WARNING_MESSAGE, icon);
			
			return 0;
		}
		
		//密码长度在8~30
		if(passwrd.length() < 8 || passwrd.length() > 30 ){
			icon = new ImageIcon("image/dialog/警告.png");
			JOptionPane.showMessageDialog(null, "密码长度为8~30位,请检查是否符合规范", "登录失败",
					JOptionPane.WARNING_MESSAGE, icon);
			return 0;
		}
		
		//从所有用户中找这个用户   试试能不能找到
		for(User u : Utility.getAllUserInfo()){
			if(u.getUserName().equals(name) && u.getUserPassword().equals(passwrd)){
				
				JOptionPane.showMessageDialog(null, "登录成功!您的信息如下:\n"+u.toString());    //测试用  输出登录成功用户的信息
				
				return LOGINSYSTEMSUCCESS;   //登录成功
			}
		}
		return NOTLEGITIMATEUSERS;
	}
	
}
