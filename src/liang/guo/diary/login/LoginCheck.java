package liang.guo.diary.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import liang.guo.diary.database.DatabaseTool;
import liang.guo.diary.util.ShowDialog;
import liang.guo.diary.util.config.Config;

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
		//用户名长度在6~20   
		if(name.length() < 6 || name.length() > 20){
			ShowDialog.showMyDialog("用户名长度为6~20位,请检查是否符合规范", "登录失败", 
					JOptionPane.WARNING_MESSAGE);
			return NOTLEGITIMATEUSERS;
		}
		
		//密码长度在8~30
		if(passwrd.length() < 8 || passwrd.length() > 30 ){
			ShowDialog.showMyDialog("密码长度为8~30位,请检查是否符合规范", "登录失败", 
					JOptionPane.WARNING_MESSAGE);
			return NOTLEGITIMATEUSERS;
		}
		
		//访问数据库,判断是否正确
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		//select * from `user` where `username`='1' and `password`='1';
		String sql = "select * from user where username=? and password=?";
		conn = DatabaseTool.getConnection();
		ResultSet resultSet = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, passwrd);
			
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){   //判断是否存在下一个,即有这个用户么?
				Config.currentUser.setId(resultSet.getInt("id"));
				Config.currentUser.setUserName(resultSet.getString("username"));
				Config.currentUser.setUserDisplayName(resultSet.getString("disname"));
				Config.currentUser.setUserPassword(resultSet.getString("password"));
				Config.currentUser.setUserMailBox(resultSet.getString("email"));
				Config.currentUser.setUserChooseProblem(Integer.parseInt(resultSet.getString("ansindex")));
				Config.currentUser.setUserQuestionAnswer(resultSet.getString("answer"));
				//System.out.println(Config.currentUser.toString());
				return LOGINSYSTEMSUCCESS;
			} else {
				return NOTLEGITIMATEUSERS;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return CONNECTTODATABASEFAILED;
		} finally {
			DatabaseTool.closeResultset(resultSet);
			DatabaseTool.closeStatement(preparedStatement);
			DatabaseTool.closeConnection(conn);
		}
		
	}
	
}
