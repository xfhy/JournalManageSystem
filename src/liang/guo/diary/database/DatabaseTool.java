package liang.guo.diary.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import liang.guo.diary.model.Diary;
import liang.guo.diary.model.User;

/**
 * @author XFHY
 * @date 2016年12月19日 下午1:48:27
 * @package liang.guo.diary.database
 * @function 数据库的一些操作 工具类
 */
public class DatabaseTool {

	/**
	 * 获取数据库连接
	 * 
	 * @return Connection
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			// 加载驱动
			Class.forName("com.mysql.jdbc.Driver");

			// 建立连接
			// 将用户名、密码和数据库名字存入配置文件是一个好的习惯
			Properties properties = new Properties();
			FileInputStream fis = null;
			try {
				fis = new FileInputStream("config.properties");
				properties.load(fis);

				String database = properties.getProperty("database");
				String user = properties.getProperty("jdbc.username");
				String password = properties.getProperty("jdbc.password");

				if (database != null && user != null && password != null) {
					String url = "jdbc:mysql://localhost:3306/" + database + "?useUnicode=true&characterEncoding=utf-8";
					DriverManager.setLoginTimeout(4); // 设置驱动程序试图连接到某一数据库时将等待的最长时间，以秒为单位
					conn = DriverManager.getConnection(url, user, password);
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭数据库连接(Connection)
	 * 
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭PreparedStatement对象的
	 * 
	 * @param stat
	 */
	public static void closeStatement(PreparedStatement stat) {
		if (stat != null) {
			try {
				// 立即释放此 Statement 对象的数据库和 JDBC 资源，而不是等待该对象自动关闭时发生此操作。
				// 一般来说，使用完后立即释放资源是一个好习惯，这样可以避免对数据库资源的占用。
				stat.close();
			} catch (Exception e2) {
				System.err.println("PreparedStatement关闭失败");
			}
		}
	}

	/**
	 * 关闭Resultset
	 * 
	 * @param results
	 */
	public static void closeResultset(ResultSet results) {
		if (results != null) {
			try {
				results.close(); // 立即释放此 ResultSet 对象的数据库和 JDBC
									// 资源，而不是等待该对象自动关闭时发生此操作。
			} catch (Exception e2) {
				System.err.println("ResultSet关闭失败");
			}
		}
	}

	/**
	 * 添加用户类到数据库
	 * 
	 * @param conn
	 *            数据库连接
	 * @param user
	 *            用户类
	 * @return 添加是否成功 true|false
	 */
	public static boolean addUserToDatabase(User user) {
		Connection conn = getConnection();
		PreparedStatement preState = null;
		String sql = "insert into user(username,password,disname,email,ansindex,answer) values (?,?,?,?,?,?)";
		int changeRows = 0;

		try {
			preState = conn.prepareStatement(sql); // 创建一个 PreparedStatement
													// 对象来将参数化的 SQL 语句发送到数据库

			// 设置上面sql语句占位符的内容
			preState.setString(1, user.getUserName());
			preState.setString(2, user.getUserPassword());
			preState.setString(3, user.getUserDisplayName());
			preState.setString(4, user.getUserMailBox());
			preState.setString(5, String.valueOf(user.getUserChooseProblem()+1));
			preState.setString(6, user.getUserQuestionAnswer());

			changeRows = preState.executeUpdate(); // 执行sql语句
			if (changeRows > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeStatement(preState);
		}

		return false;
	}

	/**
	 * 添加日记到数据库
	 * 
	 * @param conn
	 *            数据库连接
	 * @param diary
	 *            日记类
	 * @return 返回是否添加成功 true|false
	 */
	public static boolean addDiaryToDatabase(Diary diary) {
		Connection conn = getConnection();
		PreparedStatement preState = null;
		String sql = "insert into diary (weather,mood,title,content,mydate) values (?,?,?,?,?)";
		int changeRows = 0;

		// 获取天气
		String weather = null;
		switch (diary.getWeather()) {
		case SUNNY:
			weather = "晴";
			break;
		case OVERCAST:
			weather = "阴天";
			break;
		case CLOUDY:
			weather = "多云";
			break;
		case RAIN:
			weather = "雨";
			break;
		case FOG:
			weather = "雾";
			break;
		case SNOW:
			weather = "雪";
			break;
		default:
			weather = "晴";
			break;
		}

		String mood = null;
		// '高兴','郁闷','兴奋','悲伤','恐惧','欣喜'
		switch (diary.getMood()) {
		case HAPPY:
			mood = "高兴";
			break;
		case DEPRESSED:
			mood = "郁闷";
			break;
		case EXCITEMENT:
			mood = "兴奋";
			break;
		case SADNESS:
			mood = "悲伤";
			break;
		case FEAR:
			mood = "恐惧";
			break;
		case DELIGHTED:
			mood = "欣喜";
			break;
		default:
			mood = "高兴";
			break;
		}

		try {
			preState = conn.prepareStatement(sql); // 创建一个 PreparedStatement
													// 对象来将参数化的 SQL 语句发送到数据库

			// 设置上面sql语句占位符的内容
			// insert into diary (weather,mood,title,content,mydate) values
			// (?,?,?,?,?)
			preState.setString(1, weather);
			preState.setString(2, mood);
			preState.setString(3, diary.getTitle());
			preState.setString(4, diary.getContent());
			preState.setString(5, diary.getDate().toString());

			changeRows = preState.executeUpdate(); // 执行sql语句
			if (changeRows > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeStatement(preState);
			closeConnection(conn);
		}
		return false;
	}

	/**
	 * 添加联系 用户与日记之间的联系 到数据库中
	 * 
	 * @param conn
	 * @param diary
	 * @return
	 */
	public static boolean addContactToDatabase(int id, int num) {
		Connection conn = getConnection();
		PreparedStatement preState = null;
		// insert into `contact` (`id`,`num`) values (1,1001);
		String sql = "insert into contact(id,num) values (?,?)";
		int changeRows = 0;
		try {
			preState = conn.prepareStatement(sql); // 创建一个 PreparedStatement
													// 对象来将参数化的 SQL 语句发送到数据库

			// 设置上面sql语句占位符的内容
			preState.setInt(1, id);
			preState.setInt(2, num);

			changeRows = preState.executeUpdate(); // 执行sql语句
			if (changeRows > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeStatement(preState);
			closeConnection(conn);
		}
		return false;
	}

	/**
	 * 通过用户名获取该用户的id,通过数据库查询
	 * @param conn 数据库连接
	 * @param userName 用户名
	 * @return 用户id
	 */
	public static int getIdByUserName(String userName){
		int id = -1;
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		//select `id` from `user` where `username`='1';     #通过用户名获取用户id
		String sql = "select id from user where username=?";
		try {
			conn = DatabaseTool.getConnection();    //获取数据库连接
			preparedStatement = conn.prepareStatement(sql);  //准备
			preparedStatement.setString(1, userName);
			
			resultSet = preparedStatement.executeQuery();  //执行查询
			
			//判断是否有下一行
			if(resultSet.next()){
				id = resultSet.getInt("id");   //获取id
				return id;
			} else {    //未找到该用户名  所在 用户
				return -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseTool.closeResultset(resultSet);
			DatabaseTool.closeStatement(preparedStatement);
			DatabaseTool.closeConnection(conn);
		}
		
		return id;
	}
	
	/**
	 * 通过id修改用户密码
	 * @param id   			用户id
	 * @param password      用户密码
	 * @return  返回是否修改成功
	 */
	public static boolean modifyPassById(int id,String password){
		//update `user` set `password`='qwert;123' where `id`='8';
		String sql = "update user set password=? where id=?";
		Connection conn = DatabaseTool.getConnection();
		PreparedStatement preparedStatement = null;
		int changeRows = -1;
		try {
			preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, password);
			preparedStatement.setInt(2, id);
			
			changeRows = preparedStatement.executeUpdate();
			if(changeRows > 0){
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseTool.closeStatement(preparedStatement);
			DatabaseTool.closeConnection(conn);
		}
		return false;
	}
	
	/**
	 * 获取数据库中最大的日记类的id
	 * @return  id
	 */
	public static int getMaxDiaryIdFromDb(){
		int num = -1;
		//select max(`num`) from `diary`;    #获取diary中编号最大的那一个,(我觉得是最后添加的那一个)
		String sql = "select max(num) as num from diary";   //查询结果的列名是num
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			conn = getConnection();
			preparedStatement = conn.prepareStatement(sql);
			
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				num = resultSet.getInt("num");
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResultset(resultSet);
			closeStatement(preparedStatement);
			closeConnection(conn);
		}
		
		return num;
	}
	
}
