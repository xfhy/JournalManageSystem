package liang.guo.diary.util.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import liang.guo.diary.model.User;

/**
 * @author XFHY
 * @date 2016年12月12日 下午10:35:08
 * @package liang.guo.diary.util.config
 * @function 系统的配置 比如 记住密码 自动登录 数据库名称 数据库用户名,密码等
 *           加密算法引用:https://zhidao.baidu.com/question/496797552.html
 */
public class Config {

	/**
	 * 当前用户
	 */
	public static User currentUser = new User();
	
	/**
	 * 配置文件名称
	 */
	private final String CONFIGFILENAME = "config.properties";

	/**
	 * 记住密码
	 */
	private boolean rememberPassword = false;
	/**
	 * 自动登录
	 */
	private boolean automaticLogon = false;

	/**
	 * 用户名
	 */
	private String user_name = "";
	/**
	 * 密码
	 */
	private String user_password = "";

	/**
	 * 构造函数
	 * 需要读取上次的配置文件信息
	 */
	public Config() {
		loadLoginConfig();    //读取登录界面的配置信息
	}

	/**
	 * 构造函数    不需要读取上次的配置文件信息
	 * @param noLoad
	 */
	public Config(boolean noLoad){
		
	}
	
	
	/**
	 * 获取是否记住密码
	 * 
	 * @return
	 */
	public boolean isRememberPassword() {
		return rememberPassword;
	}

	/**
	 * 获取是否自动登录
	 * 
	 * @return
	 */
	public boolean isAutomaticLogon() {
		return automaticLogon;
	}

	/**
	 * 获取用户名
	 * 
	 * @return
	 */
	public String getUser_name() {
		return user_name;
	}

	/**
	 * 获取密码
	 * 
	 * @return
	 */
	public String getUser_password() {
		return user_password;
	}



	/**
	 * 从文件获取 登录 界面 的配置
	 */
	public void loadLoginConfig() {
		/*
		 * Properties 类表示了一个持久的属性集。Properties 可保存在流中或从流中加载。
		 * 属性列表中每个键及其对应值都是一个字符串。
		 */
		Properties props = new Properties();
		File configFile = new File(CONFIGFILENAME);
		// FileInputStream:从文件系统中的某个文件中获得输入字节 将配置文件加载到输入流中
		FileInputStream fileInputStream = null;
		InputStreamReader inputStreamReader = null;
		try {
			fileInputStream = new FileInputStream(configFile);
			inputStreamReader = new InputStreamReader(fileInputStream,"utf-8");
			props.load(inputStreamReader); // 从输入流中读取属性列表（键和元素对）。

			/*------------从配置文件中读取是否记住密码了的(之前)-------------*/
			// getProperty()用指定的键在此属性列表中搜索属性
			rememberPassword = props.getProperty("remember_password").equals("true");
			automaticLogon = props.getProperty("automatic_logon").equals("true");
			user_name = props.getProperty("user_name");
			user_password = props.getProperty("user_password");
		} catch (FileNotFoundException e) {
			try {
				configFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close(); // 关闭输入流
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	/**
	 * 设置配置文件中 记住密码 那一项是true还是false
	 * 
	 * @param isRememberPassword
	 *            是否记住密码
	 * @return
	 */
	public boolean setRemePassToFile(boolean isRememberPassword) {
		/*
		 * Properties 类表示了一个持久的属性集。Properties 可保存在流中或从流中加载。
		 * 属性列表中每个键及其对应值都是一个字符串。
		 */
		Properties props = new Properties();

		FileReader fileReader = null; // 用来读取字符文件的便捷类
		FileWriter fileWriter = null;
		try {
			fileReader = new FileReader(CONFIGFILENAME);
			props.load(fileReader);

			if(isRememberPassword){
				props.setProperty("remember_password", "true"); // 设置记住密码 键值对 值是true
			} else {
				props.setProperty("remember_password", "false"); // 设置记住密码 键值对 值是false
			}
			fileWriter = new FileWriter(CONFIGFILENAME);
			props.store(fileWriter, CONFIGFILENAME); // 存储为文件
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileReader != null) {
				try {
					fileReader.close(); // 关闭输入流
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileWriter != null) {
				try {
					fileWriter.close(); // 关闭输入流
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 设置配置文件中 自动登录 那一项是true还是false
	 * 
	 * @param isAutomaticLogon
	 *            是否自动登录
	 * @return
	 */
	public boolean setAutomaticLogonToFile(boolean isAutomaticLogon) {
		/*
		 * Properties 类表示了一个持久的属性集。Properties 可保存在流中或从流中加载。
		 * 属性列表中每个键及其对应值都是一个字符串。
		 */
		Properties props = new Properties();

		FileReader fileReader = null; // 用来读取字符文件的便捷类
		FileWriter fileWriter = null;
		try {
			fileReader = new FileReader(CONFIGFILENAME);
			props.load(fileReader);

			if(isAutomaticLogon){
				props.setProperty("automatic_logon", "true"); // 设置自动登录 键值对 值是true
			} else {
				props.setProperty("automatic_logon", "false"); // 设置自动登录 键值对 值是false
			}
			fileWriter = new FileWriter(CONFIGFILENAME);
			props.store(fileWriter, CONFIGFILENAME); // 存储为文件
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileReader != null) {
				try {
					fileReader.close(); // 关闭输入流
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileWriter != null) {
				try {
					fileWriter.close(); // 关闭输入流
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 设置配置文件中 用户名 那一项的值
	 * 
	 * @param userName
	 */
	public void setUserNameToFile(String userName) {
		/*
		 * Properties 类表示了一个持久的属性集。Properties 可保存在流中或从流中加载。
		 * 属性列表中每个键及其对应值都是一个字符串。
		 */
		Properties props = new Properties();

		FileReader fileReader = null; // 用来读取字符文件的便捷类
		FileWriter fileWriter = null;
		try {
			fileReader = new FileReader(CONFIGFILENAME);
			props.load(fileReader);

			props.setProperty("user_name", userName); // 设置用户名 键值对 值是true
			fileWriter = new FileWriter(CONFIGFILENAME);
			props.store(fileWriter, CONFIGFILENAME); // 存储为文件
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileReader != null) {
				try {
					fileReader.close(); // 关闭输入流
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileWriter != null) {
				try {
					fileWriter.close(); // 关闭输入流
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 设置配置文件中 密码 那一项的值
	 * 
	 * @param password
	 */
	public void setUserPassToFile(String password) {
		/*
		 * Properties 类表示了一个持久的属性集。Properties 可保存在流中或从流中加载。
		 * 属性列表中每个键及其对应值都是一个字符串。
		 */
		Properties props = new Properties();

		FileReader fileReader = null; // 用来读取字符文件的便捷类
		FileWriter fileWriter = null;
		try {
			fileReader = new FileReader(CONFIGFILENAME);
			props.load(fileReader);

			props.setProperty("user_password", password); // 设置用户名 键值对 值是true
			fileWriter = new FileWriter(CONFIGFILENAME);
			props.store(fileWriter, CONFIGFILENAME); // 存储为文件
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileReader != null) {
				try {
					fileReader.close(); // 关闭输入流
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileWriter != null) {
				try {
					fileWriter.close(); // 关闭输入流
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
