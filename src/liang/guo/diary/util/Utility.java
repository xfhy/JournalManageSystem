package liang.guo.diary.util;

import java.util.Scanner;

/**
 * 
 * 2016年9月24日 下午9:50:56
 * @author XFHY
 * 
 * 工具类   程序中用到的共有的方法
 * 
 */
public class Utility {
	
	/**
	 * 接收用户输入得菜单选项,并判断用户输入值是否正确,正确则返回
	 * 参数:最大可以选择几    1~maxChoice
	 */
	@SuppressWarnings("resource")
	public static int judgmentInput(int maxChoice){
		Scanner inputChoice = new Scanner(System.in);
		boolean inputFlag = true;
		int userSelection = -1;
		while(inputFlag){
			try {
				userSelection = inputChoice.nextInt();   //获得用户输入的整数
			} catch (Exception e) {
				System.err.println("捕获到异常:"+e.getClass());
				System.out.println("输入不合法,只能输入数字,请重新输入....");
				inputChoice = new Scanner(System.in);
				continue;
			}
			
			if(userSelection < 1 || userSelection > maxChoice){  //判断输入的整数是否合法
				System.out.println("输入不合法,未输入合法的数字选项,请重新输入....");
				inputChoice = new Scanner(System.in);
			} else {
				break;
			}
		}

		return userSelection;
	}
	
	/**
	 * 让用户输入int值
	 * @return
	 */
	@SuppressWarnings("resource")
	public static int judgmentInputInt(){
		Scanner inputInt = new Scanner(System.in);
		boolean inputFlag = true;
		int userInput = -1;
		while(inputFlag){
			/*---------------2016年10月27日20:10:48该加异常-------------------------*/
			try {
				userInput = inputInt.nextInt(); // 获得用户输入的整数
			} catch (Exception e) {
				System.err.println("捕获到异常:"+e.getClass());
				System.err.println("输入不合法,只能输入数字,请重新输入....");
				inputInt = new Scanner(System.in);
				continue;
			}
			
			inputFlag = false;
		}

		return userInput;
	}
	
}
