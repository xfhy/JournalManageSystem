package liang.guo.diary.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

import liang.guo.diary.util.Utility;

/**
 * 
 * 2016年9月25日15:44:35
 * @author XFHY
 * 
 * 用户类
 * 用来存储登录用户的一些信息,用户名、显示名、密码、确认密码、邮箱等数据
 * 
 * 
1.2. 使用封装的方式将 User 类中相关属性的验证方法封装到 User 类中。
1.3. 在 User 类中添加方法用来判断用户登录是否成功。
1.4. 在主类中添加一个 static 属性用来判断用户是否登录，以及保存当前登录的用户信息
 * 
 */
public class User implements Cloneable,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6434352159756086863L;
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
	
	/**
	 * 判断用户登录是否成功
	 * 用户登录判断    判断用户登录时输入的用户名和密码是否存在,正确
	 * @return
	 */
	public boolean loginJudgmen(String loginName,String loginPassword){
		boolean isRealUsers = false;
		
		//遍历List(所有用户)集合
		for(User user : Utility.getAllUserInfo()){
			//如果用户名和密码都与库中的用户信息对的上,则说明登录成功
			if(user.getUserName().equals(loginName) && 
					user.getUserPassword().equals(loginPassword)){
				isRealUsers = true;
			}
		}

		return isRealUsers;
	}


	/**
	 * 设置当前登录用户的索引
	 * @param howManyPeopleLogin
	 */
	/*public void setHowManyPeopleLogin(int howManyPeopleLogin) throws ArrayIndexOutOfBoundsException{
		if(howManyPeopleLogin<0 || howManyPeopleLogin >= Journal.getAllUserInfo().length){
			throw new ArrayIndexOutOfBoundsException("设置当前登录用户的索引时数组下标越界");
		} else {
			this.howManyPeopleLogin = howManyPeopleLogin;
		}
	}*/

	
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
		temp.append("姓名: "+this.userName+"\n");
		temp.append("显示名: "+this.userDisplayName+"\n");
		temp.append("密码: "+this.userPassword+"\n");
		temp.append("邮箱: "+this.userMailBox+"\n");
		temp.append("用户选择的密码验证问题: "+User.VERIFICATIONPROBLEM[this.userChooseProblem]+"\n");
		temp.append("用户的密码验证问题答案: "+this.userQuestionAnswer+"\n");
		
		return temp.toString();
	}
	
	/**
	 * 获取用户登录时输入的登录名   只判断了长度为6~20
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String getInputUserName(){
		Scanner inputInt = new Scanner(System.in);
		boolean inputFlag = true;
		String userInput = "";
		while (inputFlag) {
			try{
				userInput = inputInt.next(); // 获得用户输入的整数
				if (userInput.length() >= 6 && userInput.length() <= 20) {
					inputFlag = false;
				} else {
					System.err.println("输入不合法,用户名的长度是6~20个字符");
					inputInt = new Scanner(System.in);
				}
			} catch (Exception e2){
				e2.printStackTrace();
				System.err.println("输入时发生异常,请重新输入");
				inputInt = new Scanner(System.in);
			}
		}
		return userInput;
	}
	
	/**
	 * 获取用户登录时输入的密码   只判断了长度为8~30
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String getInputUserPassword(){
		
		/*----------------2016年9月29日20:51:47   断点-----------------------------------------*/
		Scanner inputInt = new Scanner(System.in);
		boolean inputFlag = true;
		String userInput = "";
		while (inputFlag) {
			try{
				userInput = inputInt.next(); // 获得用户输入的整数
				if (userInput.length() >= 8 && userInput.length() <= 30) {
					inputFlag = false;
				} else {
					System.err.println("输入不合法,密码的长度是8~30个字符");
					inputInt = new Scanner(System.in);
				}
			} catch (Exception e2){
				e2.printStackTrace();
				System.err.println("输入时发生异常,请重新输入");
				inputInt = new Scanner(System.in);
			}
			
		}
		return userInput;
	}
	
	/**
	 * 判断该用户是否存在
	 * @param userName  需要判断是否存在的用户名
	 * @return 返回是否存在   boolean 存在:true,不存在:false
	 */
	public static boolean haveThisUser(String userName){
		boolean userExistence = false;

		//遍历List(所有用户)集合
		for(User user : Utility.getAllUserInfo()){
			//如果名字相同,则存在该用户
			if(user.getUserName().equals(userName)){
				userExistence = true;
			}
		}
		
		return userExistence;
	}
	
	/**
	 * 通过用户名获取用户该用户在集合(所有用户列表)中的索引
	 * @param userName 用户名
	 * @return 当找到该用户名所对应的用户时,则返回该索引,如果未找到则返回-1
	 */
	public static int getUserIndexByName(String userName){
		if(userName.length() == 0){
			System.err.println("通过用户名获取用户时,传入用户名为空");
			return -1;
		}
		
		int userIndex = -1;
		
		//遍历List(所有用户)集合
		for (int i = 0; i < Utility.getAllUserInfo().size(); i++) {
			if(Utility.getAllUserInfo().get(i).getUserName().equals(userName)){
				userIndex = i;
			}
		}
		
		return userIndex;
	}
	
	/**
	 * 通过用户名获取用户类User
	 * @param userName 用户名
	 * @return 当找到该用户名所对应的用户时,则返回该User,如果未找到则返回null
	 */
	public static User getUserByName(String userName){
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
	 * 2016年11月12日16:16:39  将数组改成集合时修改
	 */
	public static void userRetrievePassword(){
		System.out.println("\n\t这是用户找回密码界面~");
		System.out.println("请输入您的用户名:");
		String userNameTemp = User.getInputUserName();   //获取用户输入的用户名
		
		User user = getUserByName(userNameTemp);  //通过用户名获取用户类User
		
		//首先判断该用户是否存在
		if( user == null ){
			System.err.println("该用户不存在~");
			return ;
		} 
		
		System.out.println("请选择您注册时选的密码验证问题");
		showRegistrationProblem();    //显示用户注册时需要回答的密码验证问题
		System.out.println("请输入您的选择:");
		int selectionProblem = Utility.judgmentInput(5);  //获取用户正确的输入
		
		String questionAnswerTemp = inputQuestionAnswer();  //让用户输入密码验证问题的答案
		
		/*---------------------2016年9月30日19:57:41   断点----------------------------------------*/
		/*---------------------2016年9月30日22:37:36   继续----------------------------------------*/
		
		/*---------------------2016年11月12日16:10:14 将数组改成集合--------------------------------*/
		 //判断用户选择的问题是否和之前注册时选择的问题索引一致
		boolean flagSelectionProblem = (user.getUserChooseProblem() == (selectionProblem-1));   
		// 用户回答正确
		if (flagSelectionProblem && user.getUserQuestionAnswer()
				.equals(questionAnswerTemp)) {
			System.out.println("\t密码提示问题验证成功!");
			user.setUserPassword(inputUserPassword()); // 获取用户输入的密码(函数里面已包含确认密码)
			System.out.println("密码已成功更改~");
			System.out.println("您的密码已更新,请一定不要告诉别人喔:   "+ user.getUserPassword());
			System.out.println(user.toString());
		} else {
			System.err.println("回答错误,密码找回失败~");
		}
		
	}
	
	/**
	 * 显示用户注册时需要回答的密码验证问题
	 */
	public static void showRegistrationProblem(){
		System.out.println("请从下面 5 个密码提示问题中选择一个，并填写答案，答案不能为空");
		for (int i = 0; i < User.VERIFICATIONPROBLEM.length; i++) {
			System.out.println(User.VERIFICATIONPROBLEM[i]);
		}
	}
	
	/**
	 * 让用户输入密码验证问题的答案
	 */
	@SuppressWarnings("resource")
	public static String inputQuestionAnswer(){
		String userAnswer = "";
		boolean inputFalg = true;
		Scanner inputAnswer = new Scanner(System.in);
		System.out.println("请输入问题的答案");
		while(inputFalg){
			try{
				userAnswer = inputAnswer.nextLine();
				if(userAnswer.length() > 0){
					//存放用户回答的问题的答案
					inputFalg = false;
				} else {
					System.out.println("亲,请重新输入问题的答案,不要留空白~");
					inputAnswer = new Scanner(System.in);
				}
			} catch (NoSuchElementException e){
				System.err.println(e.getMessage());
				System.err.println("没有输入哦,请重新输入");
				inputAnswer = new Scanner(System.in);
				
			} catch (Exception e2){
				e2.printStackTrace();
				System.err.println("输入时发生异常,请重新输入");
				inputAnswer = new Scanner(System.in);
			}
			
		}
		return userAnswer;
	}
	
	/**
	 * 让用户输入密码
	 * @return  返回用户输入的符合要求的密码
	 */
	@SuppressWarnings("resource")
	public static String inputUserPassword(){
		//用户密码必须包含字母数字和特殊符号，密码最短为 8 位，最长不能超过 30 位；密码和确认密码必须相同
		String passwordTemp = null;      //密码
		String confirmPassword = null;   //确认密码
		Scanner input_password = new Scanner(System.in);
		boolean inputFlag = true;
		
		//正则表达式
		String regexLetter = ".*[a-zA-Z]+.*";  //字母
		String regexNumber = ".*[0-9]+.*";     //数字
		String regexSpecialChar = ".*[\\p{Punct}]+.*";     //特殊字符
		String regexChinese = ".*[\u4e00-\u9fa5]+.*";  //汉字
		System.out.println("请输入密码:");
		System.out.println("温馨提示 用户密码必须包含字母数字和特殊符号，不能包含汉字,密码最短为 8 位，最长不能超过 30 位");
		while(inputFlag){
			try{
				passwordTemp = input_password.nextLine();
				System.out.println("请输入确认密码:");
				input_password = new Scanner(System.in);
				confirmPassword = input_password.nextLine();
				if(passwordTemp.equals(confirmPassword)){
					if(passwordTemp.matches(regexLetter) 
							&& passwordTemp.matches(regexNumber)
							&& passwordTemp.matches(regexSpecialChar)
							&& !passwordTemp.matches(regexChinese)
							&& passwordTemp.length() >= 8
							&& passwordTemp.length() <= 30){
						System.out.println("密码格式符合要求~");
						inputFlag = false;
					} else {
						System.out.println("亲,您输入的密码格式错误,请按照规定格式输入哦~");
						System.out.println("下面,请重新输入密码:");
						input_password = new Scanner(System.in);
					}
				} else {
					System.out.println("提示:您前后2次输入的密码输入不一致,请重新输入,谢谢~");
					input_password = new Scanner(System.in);
				}
			} catch (NoSuchElementException e){
				System.err.println(e.getMessage());
				System.err.println("没有输入哦,请重新输入");
				input_password = new Scanner(System.in);
				
			} catch (Exception e2){
				e2.printStackTrace();
				System.err.println("输入时发生异常,请重新输入");
				input_password = new Scanner(System.in);
			}
		}
			
		return passwordTemp;
	}
	
	/**
	 * 让用户输入用户名
	 */
	@SuppressWarnings("resource")
	public static String inputUserName(){
		String userNameTemp = null;
		//用户名只能包含字母、数字和下划线，并且首字母只能为字母，用户名最短不能少于 6 个字符，最长不能超过 20 个字符。
		String nameRegex = "^[a-zA-Z][a-zA-Z_0-9]*$";  //验证用户姓名的字符串
		Scanner input_user_name = new Scanner(System.in);
		boolean inputFlag = true;
		
		System.out.println("请输入用户名");
		System.out.println("温馨提示: 用户名只能包含字母、数字和下划线，并且首字母只能为字母，"
				+ "用户名最短不能少于 6 个字符，最长不能超过 20 个字符");
		while(inputFlag){
			try{
				userNameTemp = input_user_name.nextLine();  //让用户输入一行
				if(userNameTemp.length() >= 6 && userNameTemp.length() <= 20 && userNameTemp.matches(nameRegex)){
					if(!haveThisUser(userNameTemp)){  //判断该用户是否存在
						inputFlag = false;
					} else {
						System.out.println("亲,该用户已存在,请重新输入用户名....");
						input_user_name = new Scanner(System.in);  //清空输入缓存区
					}
				} else {
					System.out.println("亲,输入不合法哦,请重新输入....");
					input_user_name = new Scanner(System.in);  //清空输入缓存区
				}
			} catch (NoSuchElementException e){
				System.err.println(e.getMessage());
				System.err.println("没有输入哦,请重新输入");
				input_user_name = new Scanner(System.in);
				
			} catch (Exception e2){
				e2.printStackTrace();
				System.err.println("输入时发生异常,请重新输入");
				input_user_name = new Scanner(System.in);
			}
			
		}
		return userNameTemp;
	}
	
	/**
	 * 让用户输入显示名
	 * @return 返回用户输入的正确的显示名
	 */
	@SuppressWarnings("resource")
	public static String inputUserDisplayName(){
		String userNameTemp = null;
		//[\s\S]*   可以匹配任意字符
		Scanner input_user_name = new Scanner(System.in);
		boolean inputFlag = true;
		
		System.out.println("请输入显示名");
		System.out.println("显示名可以包含任意字符，但是最小只能为 3 个字符，最长可以为 20 个字符");
		while(inputFlag){
			try{
				userNameTemp = input_user_name.nextLine();
				if(userNameTemp.length() >= 3 && userNameTemp.length() <= 20){
					inputFlag = false;
					System.out.println("输入显示名格式符合要求~~");
				} else {
					System.out.println("亲,输入不合法哦,请重新输入....");
					input_user_name = new Scanner(System.in);  //清空输入缓存区
				}
			} catch (NoSuchElementException e){
				System.err.println(e.getMessage());
				System.err.println("没有输入哦,请重新输入");
				input_user_name = new Scanner(System.in);
				
			} catch (Exception e2){
				e2.printStackTrace();
				System.err.println("输入时发生异常,请重新输入");
				input_user_name = new Scanner(System.in);
			}
			
		}
		return userNameTemp;
	}
	
	/**
	 * 让用户输入邮箱 
	 * @return 返回用户输入的符合要求的邮箱
	 */
	@SuppressWarnings("resource")
	public static String inputUserEmail() {
		// 邮箱必须符合邮箱格式，最长不能超过 50 个字符 \. 点字符 + >=1次
		String regexEmail = "[_a-zA-Z0-9]+@[0-9a-zA-Z]+(\\.[a-zA-Z]+)+";
		String userEmailTemp = null;
		System.out.println("请输入您的邮箱:");
		Scanner input_Email = new Scanner(System.in);
		boolean inputFlag = true;
		while (inputFlag) {
			try{
				userEmailTemp = input_Email.nextLine();
				if (userEmailTemp.matches(regexEmail)
						&& userEmailTemp.length() <= 50) {
					System.out.println("您输入的邮箱格式正确~");
					inputFlag = false;
				} else {
					System.out.println("亲,您输入的邮箱格式有误,请重新输入,谢谢~");
					input_Email = new Scanner(System.in);
				}
			} catch (NoSuchElementException e){
				System.err.println(e.getMessage());
				System.err.println("没有输入哦,请重新输入");
				input_Email = new Scanner(System.in);
				
			} catch (Exception e2){
				e2.printStackTrace();
				System.err.println("输入时发生异常,请重新输入");
				input_Email = new Scanner(System.in);
			}
			
		}

		return userEmailTemp;
	}
	
	/**
	 * 生成一个简单的 3 个整数的四则运算，例如[3*9-2=?]，并要求用户回答.如果用户回答正确，则用
		户注册过程成功。如果用户回答错误，则更新问题，并让用户重新输入，直到输入正确为止，或者用户输
		入错误超过 5 次。
	 * 2016年9月26日9:16:07
	 */
	public static boolean createSimpleFourOperations(){
		Random randomNumber = new Random();
		int number_x = randomNumber.nextInt(9)+1;  //生成1~9随机数
		int number_y = randomNumber.nextInt(9)+1;
		int number_z = randomNumber.nextInt(9)+1;
		char operator[] = new char[2];    //存放运算符的
		
		//随机生成运算符
		for (int i = 0; i < operator.length; i++) {
			int tempOperator = randomNumber.nextInt(4)+1;
			switch (tempOperator) {
			case 1:
				operator[i] = '+';
				break;
			case 2:
				operator[i] = '-';
				break;
			case 3:
				operator[i] = '*';
				break;
			case 4:
				operator[i] = '/';
				break;
			default:
				break;
			}
		}
		
		System.out.println("\n请回答一个验证问题");
		System.out.println("\n"+number_x+" "+operator[0]+" "+number_y+" "+operator[1]+" "+number_z+" = ?");
		//计算算式的正确结果
		int calculationResult = getResults(number_x,number_y,number_z,operator);
		//System.out.println("正确结果是"+calculationResult);
		System.out.println("请输入正确的结果(如果有除法,精确到整数即可)");
		int inputResult = Utility.judgmentInputInt();
		
		if(inputResult == calculationResult){  //判断用户输入答案的是否正确
			return true;
		} else {
			System.err.println("\n亲,您回答问题错误~~\n");    //syserr
			return false;
		}
	}
	
	/**
	 * 算出四则运算的结果
	 * 参数:待运算的3个数,2个运算符(数组里)
	 * @return 正确结果
	 */
	private static int getResults(int number_x,int number_y,int number_z,char operator[]){
		
		/*
		 * 从左向右依次读取符号，如果第i个符号为加减，判断第i+1个，如果为加减，则直接进行运算，
		 * 否则用temp将乘除法进行完全，再加入sum。如果第i个为乘除法，说明这是第一个符号（因为运算逻辑是将乘除法进行完全，
		 * 即下一个符号为加减号），那么将temp值直接赋值给sum，然后再进行运算。
		 * */
		
		int sum = 0;
		switch(operator[0]){
		case '+':
			switch(operator[1]){
			case '+':
				sum = number_x + number_y + number_z;
				break;
			case '-':
				sum = number_x + number_y - number_z;
				break;
			case '*':
				sum = number_x + number_y * number_z;
				break;
			case '/':
				sum = number_x + number_y / number_z;
				break;
			}
			break;
		case '-':
			switch(operator[1]){
			case '+':
				sum = number_x - number_y + number_z;
				break;
			case '-':
				sum = number_x - number_y - number_z;
				break;
			case '*':
				sum = number_x - number_y * number_z;
				break;
			case '/':
				sum = number_x - number_y / number_z;
				break;
			}
			break;
		case '*':
			switch(operator[1]){
			case '+':
				sum = number_x * number_y + number_z;
				break;
			case '-':
				sum = number_x * number_y - number_z;
				break;
			case '*':
				sum = number_x * number_y * number_z;
				break;
			case '/':
				sum = number_x * number_y / number_z;
				break;
			}
			break;
		case '/':
			switch(operator[1]){
			case '+':
				sum = number_x / number_y + number_z;
				break;
			case '-':
				sum = number_x / number_y - number_z;
				break;
			case '*':
				sum = number_x / number_y * number_z;
				break;
			case '/':
				sum = number_x / number_y / number_z;
				break;
			}
			break;
		}
		
		return sum;
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
