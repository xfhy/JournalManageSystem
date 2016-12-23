package liang.guo.diary.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 2016年9月25日15:44:35
 * @author XFHY
 * 
 * 用户类
 * 用来存储登录用户的一些信息,用户名、显示名、密码、确认密码、邮箱等数据
 * 
 */
public class User implements Cloneable,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6434352159756086863L;
	/**
	 * id
	 */
	private int id;
	
	/**
	 * 存放用户姓名
	 */
	private String userName;
	/**
	 * 存放用户显示名
	 */
	private String userDisplayName;
	/**
	 * 存放用户密码
	 */
	private String userPassword;
	/**
	 * 存放用户邮箱
	 */
	private String userMailBox;
	/**
	 * 注册时需要用户选择回答的密码提示问题
	 */
	public final static String VERIFICATIONPROBLEM[] = {"1.你是谁?","2.你来自哪里?",
		"3.你想去哪里?","4.你想干什么?","5.你能干什么?"};
	/**
	 * 存放用户选择的问题索引
	 */
	private int userChooseProblem;
	/**
	 * 存放用户回答的密码提示问题的答案
	 */
	private String userQuestionAnswer;
	/**
	 * 该用户对应的日记列表
	 */
	private List<Diary> ownDiaries = new ArrayList<>();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * 获取用户名
	 * @return
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * 设置用户名
	 * @param userName
	 */
	public void setUserName(String userName) throws IllegalArgumentException{
		if(userName == null || userName.length()<6 || userName.length()>20){
			throw new IllegalArgumentException("设置用户名时传入参数非法");
		} else {
			this.userName = userName;
		}
	}
	
	/**
	 * 获取显示名
	 * @return
	 */
	public String getUserDisplayName() {
		return userDisplayName;
	}
	
	/**
	 * 设置显示名
	 * @param userDisplayName
	 */
	public void setUserDisplayName(String userDisplayName) throws IllegalArgumentException{
		if(userDisplayName == null){
			throw new IllegalArgumentException("设置显示名时传入参数非法(传入为null)");
		} 
		if(userDisplayName.length() < 3 || userDisplayName.length() > 20){
			throw new IllegalArgumentException("设置显示名时传入参数非法(传入显示名长度不合法)");
		}
		this.userDisplayName = userDisplayName;
	}
	
	/**
	 * 获取用户密码
	 * @return
	 */

	public String getUserPassword() {
		return userPassword;
	}
	
	/**
	 * 设置用户密码
	 * @param userPassword 用户密码,非空,长度为8~30
	 */
	public void setUserPassword(String userPassword) throws IllegalArgumentException{
		if(userPassword == null){
			throw new IllegalArgumentException("设置用户密码时传入参数非法(传入的为null)");
		}
		if(userPassword.length() < 8 || userPassword.length() > 30){
			throw new IllegalArgumentException("设置用户密码时传入参数非法(传入参数长度非法)");
		}
		this.userPassword = userPassword;
	}
	
	/**
	 * 获取用户邮箱
	 * @return
	 */ 
	public String getUserMailBox() {
		return userMailBox;
	}
	
	/**
	 * 设置用户邮箱
	 * @param userMailBox 用户邮箱,非空
	 */
	public void setUserMailBox(String userMailBox) {
		if(userMailBox == null){
			throw new IllegalArgumentException("设置用户邮箱时传入参数非法(传入为null)");
		}
		this.userMailBox = userMailBox;
	}
	
	/**
	 * 获取用户选择的密码验证问题索引
	 * @return
	 */
	public int getUserChooseProblem() {
		return userChooseProblem;
	}
	
	/**
	 * 设置用户选择的密码验证问题索引
	 * @param userChooseProblem
	 */
	public void setUserChooseProblem(int userChooseProblem) throws IllegalArgumentException{
		if(userChooseProblem < 0 || userChooseProblem >= User.VERIFICATIONPROBLEM.length){
			/*----------------------2016年10月30日11:01:01   断点------------------------------------*/
			System.err.println("设置用户选择的密码验证问题索引时传入参数非法(传入索引不在1~5)");
			throw new IllegalArgumentException("设置用户选择的密码验证问题索引时传入参数非法(传入索引不在1~5)");
		}
		this.userChooseProblem = userChooseProblem;
	}
	
	/**
	 * 获取用户密码验证问题答案
	 * @return
	 */
	public String getUserQuestionAnswer() {
		return userQuestionAnswer;
	}
	
	/**
	 * 设置用户密码验证问题答案
	 * @param userQuestionAnswer
	 */
	public void setUserQuestionAnswer(String userQuestionAnswer) throws IllegalArgumentException{
		if(userQuestionAnswer.length() == 0){
			System.err.println("设置用户密码验证问题答案时传入参数非法(传入参数长度为0)");
			throw new IllegalArgumentException("设置用户密码验证问题答案时传入参数非法(传入参数长度为0)");
		}
		this.userQuestionAnswer = userQuestionAnswer;
	}
	
	/**
	 * 重置本类中的数据
	 */
	public void resetData(){
		this.setUserName("");
		this.setUserDisplayName("");
		this.setUserPassword("");
		this.setUserMailBox("");
		this.setUserChooseProblem(-1);
		this.setUserQuestionAnswer("");
	}
	
	/**
	 * 克隆
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		User user = null;
		try {
			user = (User) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			System.err.println("在克隆User类时出现异常,当前类未实现Cloneable接口,所以克隆失败!");
		}
		return user;
	}
	
	
	/**
	 * 重写toString()方法,输出User的信息
	 */
	@Override
	public String toString() {
		StringBuffer temp = new StringBuffer();
		temp.append("id:"+this.id+"\n");
		temp.append("姓名: "+this.userName+"\n");
		temp.append("显示名: "+this.userDisplayName+"\n");
		temp.append("密码: "+this.userPassword+"\n");
		temp.append("邮箱: "+this.userMailBox+"\n");
		temp.append("用户选择的密码验证问题: "+User.VERIFICATIONPROBLEM[this.userChooseProblem]+"\n");
		temp.append("用户的密码验证问题答案: "+this.userQuestionAnswer+"\n");
		
		return temp.toString();
	}
	
	
	@Override
	public boolean equals(Object other) {
		if(this == other){
			return true;
		}
		if(other == null){
			return false;
		}
		if( !(other instanceof User) ){
			return false;
		}
		final User user = (User)other;
		if( !getUserName().equals(user.getUserName()) ){
			return false;
		}
		return true;
	}

	/**
	 * 获取该用户的日记列表
	 * @return
	 */
	public List<Diary> getOwnDiaries() {
		return ownDiaries;
	}
	
	/**
	 * 设置该用户的日记列表
	 * @param ownDiaries
	 */
	public void setOwnDiaries(List<Diary> ownDiaries) {
		this.ownDiaries = ownDiaries;
	}
	
	/**
	 * 添加日记
	 * @return
	 */
	public boolean addDiary(Diary diary){
		return ownDiaries.add(diary);
	}
	
	/**
	 * 判断此密码是否符合要求
	 * @param passwordTemp
	 * @return
	 */
	public static boolean isCurrentPassword(String passwordTemp) {
		// 正则表达式
		String regexLetter = ".*[a-zA-Z]+.*"; // 字母
		String regexNumber = ".*[0-9]+.*"; // 数字
		String regexSpecialChar = ".*[\\p{Punct}]+.*"; // 特殊字符
		String regexChinese = ".*[\u4e00-\u9fa5]+.*"; // 汉字
		if(passwordTemp.matches(regexLetter) 
				&& passwordTemp.matches(regexNumber)
				&& passwordTemp.matches(regexSpecialChar)
				&& !passwordTemp.matches(regexChinese)
				&& passwordTemp.length() >= 8
				&& passwordTemp.length() <= 30){
			return true;
		}
		return false;
	}

	/**
	 * 判断次邮箱是否符合要求
	 * @param emailTemp
	 * @return
	 */
	public static boolean isCurrentEmail(String emailTemp){
		// 邮箱必须符合邮箱格式，最长不能超过 50 个字符 \. 点字符 + >=1次
		String regexEmail = "[_a-zA-Z0-9]+@[0-9a-zA-Z]+(\\.[a-zA-Z]+)+";
		if(emailTemp.matches(regexEmail) && regexEmail.length()<=50){
			return true;
		} else {
			return false;
		}
	}
	
}
