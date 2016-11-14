package liang.guo.diary;

import liang.guo.diary.model.User;
import liang.guo.diary.util.Utility;

/**
 * 
 * 2016年9月24日 下午7:55:57
 * @author XFHY
 * 
 * 用于用户登录的类
 * 
 */
public class LoginMenu {
	
	private boolean exitFalg = false;  //退出?
	private String menuName = "登录主菜单";   //菜单名字     控制当前显示哪一级菜单
	private String loginName = null;      //记录用户的临时登录名
	private String loginPassword = null;  //记录用户的临时登录密码
	
	
	public LoginMenu(){
		//test();
	}
	
	/**
	 * 跑起来
	 * */
	public void run(){
		while(!exitFalg){  //判断用户是否退出   是否需要继续显示菜单
			if(menuName.equals(menuName)){  //根据菜单名字显示相应的菜单
				showLoginMainMenu();  //显示一级菜单
			} 
		}
	}
	
	/**
	 * 显示登录主菜单
	 */
	public void showLoginMainMenu(){
		System.out.println("1. 注册；");
		System.out.println("2. 登录；");
		System.out.println("3. 找回密码；");
		System.out.println("4. 返回上一层菜单；");
		int userSelection = Utility.judgmentInput(4);   //让用户输入选择
		
		switch (userSelection) {
		case 1:
			userRegister();
			break;
		case 2:
			userSignIn();
			break;
		case 3:
			User.userRetrievePassword();
			break;
		case 4:
			exitFalg = true;  
			break;
		default:
			System.out.println("噫,没有这个选项");
			break;
		}
	}
	
	/**
	 * 用户注册
	 */
	public void userRegister(){
		System.out.println("\t这是用户注册界面~");
		
		User userTemp = new User();
		
		userTemp.setUserName(User.inputUserName());                     //让用户输入用户名
		//System.out.println("输入的用户名正确");
		userTemp.setUserDisplayName(User.inputUserDisplayName());        //让用户输入显示名
		userTemp.setUserPassword(User.inputUserPassword());              //让用户输入密码
		userTemp.setUserMailBox(User.inputUserEmail());                  //让用户输入邮箱
		
		User.showRegistrationProblem();  //显示用户注册时需要回答的问题
		System.out.println("请输入您的选择:");
		userTemp.setUserChooseProblem(Utility.judgmentInput(5)-1);     //让用户选择 1-5,哪个问题
		userTemp.setUserQuestionAnswer(User.inputQuestionAnswer());       //让用户输入需要回答哪个密码提示问题,并让用户输入该问题的答案
		
		int numberOfInput = 5;  //允许用户输入的次数
		int errorTimes = 0;  //记录用户输入问题答案错误的次数
		for(int i=1; i <= numberOfInput; ++i){
			if(User.createSimpleFourOperations()){  //生成一个简单的 3 个整数的四则运算   判断回答是否正确
				break;   //回答正确就退出
			} else {
				++errorTimes;   //错误则错误次数+1
			}
		}
		if(errorTimes != 5 && !User.haveThisUser(userTemp.getUserName())){
			System.out.println("回答正确");
			
			System.out.println("\n\t恭喜!注册成功,您的信息如下:");
			System.out.println(userTemp);
			//showRegiSuccUser();      //显示用户注册的信息
			Journal.getAllUserInfo().add(userTemp);   
			Journal.howManyPeople++;   //注册成功,人数+1
			if(Journal.saveUserCountsToFile() && Journal.saveUserToFile()){
				System.out.println("用户信息保存成文件成功");
			}
		} else {
			System.err.println("\n亲,您已经5次回答问题错误啦,建议您需要回去狂补一下小学数学....\n");
			
			//既然用户注册失败,清空刚才用户输入的数据
			userTemp.resetData();
		}
		
	}
	

	
	/**
	 * 用户登录
	 */
	public void userSignIn(){
		System.out.println("\t这是用户登录界面~");
		System.out.println("请输入用户名:");
		loginName = User.getInputUserName();   //获取用户登录时输入的登录名
		System.out.println("请输入密码:");
		loginPassword = User.getInputUserPassword();  //获取用户登录时输入的密码
		Journal.isLogin = Journal.currentUser.loginJudgmen(loginName, loginPassword);   //判断登录是否成功   成功则标记用户成功登录
		
		if(Journal.isLogin){  //判断,如果登录成功,则退出当前登录菜单
			exitFalg = true;
			System.out.println("\n\t恭喜!登录成功!\n");
			int userIndex = User.getUserIndexByName(loginName);
			if(userIndex != -1){
				Journal.currentUser = Journal.getAllUserInfo().get(userIndex);
			}
		} else {   //登录失败
			System.err.println("登录失败~用户名或密码错误");
		}
	}
	
}
