package liang.guo.diary.retripasw;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import liang.guo.diary.database.DatabaseTool;
import liang.guo.diary.model.User;
import liang.guo.diary.util.Utility;

/**
 * @author  XFHY
 * @date  2016年12月4日 下午2:13:48
 * @package liang.guo.diary.retripasw
 * @function 找回密码的  逻辑设计
 */
public class PasswordRecoveryLogic {
	
	/**
	 * 通过用户名获取用户类User
	 * @param userName 用户名
	 * @return 当找到该用户名所对应的用户时,则返回该User,如果未找到则返回null
	 */
	public User getUserByName(String userName){
		if(userName.length() == 0){
			System.err.println("通过用户名获取用户时,传入用户名为空");
			return null;
		}
		for (int i = 0; i < Utility.getAllUserInfo().size(); i++) {
			if(Utility.getAllUserInfo().get(i).getUserName().equals(userName)){
				return Utility.getAllUserInfo().get(i);
			}
		}
		return null;
	}
	
	/**
	 * 用户找回密码
	 * @param userNameTemp
	 * @param selectionProblem
	 * @param questionAnswerTemp
	 */
	public User userRetrievePassword(String userNameTemp,
			int selectionProblem,String questionAnswerTemp){
		
		//对话框   显示的图标
		Icon icon = new ImageIcon("image/dialog/错误.png");
		
		User user = getUserByName(userNameTemp);  //通过用户名获取用户类User
		
		//首先判断该用户是否存在
		if( user == null ){
			JOptionPane.showMessageDialog(null, "亲,该用户不存在~,请确保输入正确",   
					"亲,该用户不存在~", JOptionPane.ERROR_MESSAGE, icon);
			return null;
		} 
		
		 //判断用户选择的问题是否和之前注册时选择的问题索引一致
		boolean flagSelectionProblem = (user.getUserChooseProblem() == selectionProblem);   
		// 用户回答不正确
		if ( !(flagSelectionProblem && user.getUserQuestionAnswer()
				.equals(questionAnswerTemp)) ) {
			JOptionPane.showMessageDialog(null, "回答错误,密码找回失败~",   
					"回答错误,密码找回失败~", JOptionPane.ERROR_MESSAGE, icon);
			return null;
		}
		
		return user;
	}
	
	
	/**
	 * 用户找回密码时,回答是否正确
	 * @param userNameTemp  用户名
	 * @param selectionProblem  选择的问题
	 * @param questionAnswerTemp 问题答案
	 * @return
	 */
	public boolean isCurrentAnswer(String userName,
			int selectionProblem,String questionAnswer){
		Connection conn = DatabaseTool.getConnection();   // 获取数据库连接
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		//select * from `user` where `username`='1' and `ansindex`='2' and `answer`='1'; 
		String sql = "select * from user where username=? and ansindex=? and answer=?";
		try {
			preparedStatement = conn.prepareStatement(sql);  // 创建一个 PreparedStatement
															// 对象来将参数化的 SQL 语句发送到数据库
			// 设置上面sql语句占位符的内容
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, selectionProblem+"");
			preparedStatement.setString(3, questionAnswer);
			
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()){
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseTool.closeResultset(resultSet);
			DatabaseTool.closeStatement(preparedStatement);
			DatabaseTool.closeConnection(conn);
		}
		
		return false;
	}
	
	
	
}
