package liang.guo.diary.retripasw;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liang.guo.diary.database.DatabaseTool;

/**
 * @author  XFHY
 * @date  2016年12月4日 下午2:13:48
 * @package liang.guo.diary.retripasw
 * @function 找回密码的  逻辑设计
 */
public class PasswordRecoveryLogic {
	
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
