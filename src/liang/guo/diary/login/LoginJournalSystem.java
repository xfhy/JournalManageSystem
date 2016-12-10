package liang.guo.diary.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import liang.guo.diary.main.MainPage;
import liang.guo.diary.model.User;
import liang.guo.diary.mylistener.MyKeyListener;
import liang.guo.diary.register.RegisteredJournalSystem;
import liang.guo.diary.retripasw.RetrievePasswordWindow;
import liang.guo.diary.util.JFrameManager;
import liang.guo.diary.util.Utility;

/**
 * @author XFHY
 * @date 2016年11月30日 上午10:05:38
 * @package liang.guo.diary.login
 * @function 这是登录界面
 */
public class LoginJournalSystem {
	private JFrame mainFrame = new JFrame("登录日记管理系统");

	/**
	 * 上方的背景
	 */
	private JLabel aboveBackground = new JLabel();
	
	/**
	 * 注册账号
	 */
	private JButton registeredAccountBtn = new JButton("注册账号");
	/**
	 * 找回密码
	 */
	private JButton retrievePasswordBtn = new JButton("找回密码");
	
	/**
	 * 账号输入框
	 */
	private JTextField accountNumberTextField = new JTextField(10);
	/**
	 * 密码输入框
	 */
	private JPasswordField passwordTextField = new JPasswordField(10);
	
	/**
	 * 记住密码
	 */
	private JCheckBox rememberPasswordCheckBox = new JCheckBox("记住密码");
	/**
	 * 自动登录
	 */
	private JCheckBox automaticLogonCheckBox = new JCheckBox("自动登录");
	/**
	 * 登录按钮
	 */
	private JButton loginBtn = new JButton("登录");
	
	/**
	 * 提示信息
	 */
	private final static String ACCOUNTINFO = "请输入 [账号]";  
	private final static String PASSWORDINFO = "请输入 <密码>";
	
	/**
	 * 构造方法
	 */
	public LoginJournalSystem() {
		init();
	}

	/**
	 * 初始化布局
	 */
	public void init() {
		
		//默认  设置上方的背景图片获得焦点
		aboveBackground.setFocusable(true);
		
		initAllBtn();    //初始化所有的按钮
		aboveBackground.setIcon(new ImageIcon("image/login/登录上方背景.jpg"));
		
		mainFrame.add(aboveBackground,BorderLayout.NORTH);
		mainFrame.add(createCenterPanel(),BorderLayout.CENTER);
		mainFrame.setSize(400, 400); 
		
		test();    //测试用
		
		Image icon = Toolkit.getDefaultToolkit().getImage("image/login/默认小图标.png");   
		mainFrame.setIconImage(icon);   //设置窗口左上角的小图标
		
		
		mainFrame.getContentPane().setBackground(Color.WHITE);    
	}

	/**
	 * 显示UI
	 */
	public void showUI() {
		mainFrame.setResizable(false);     //设置窗体大小不可改变
		mainFrame.setLocationRelativeTo(null);    //设置JFrame居中
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true); // 设置JFrame可见
		JFrameManager.addJFrame("登录界面",mainFrame);
	}

	/**
	 * 测试用
	 */
	public void test(){
		accountNumberTextField.setText("xfhy8888");
		passwordTextField.setText("qwert;123");
		passwordTextField.setEchoChar('●');
	}
	
	/**
	 * 初始化所有的按钮
	 */
	public void initAllBtn(){
		registeredAccountBtn.setContentAreaFilled(false);   //去掉外面那层样式,现在这个按钮就像文本一样
		retrievePasswordBtn.setContentAreaFilled(false);   //去掉外面那层样式,现在这个按钮就像文本一样
		rememberPasswordCheckBox.setContentAreaFilled(false);
		automaticLogonCheckBox.setContentAreaFilled(false);
		accountNumberTextField.setText(ACCOUNTINFO);       //设置账号输入框的提示文字
		passwordTextField.setText(PASSWORDINFO);           //设置密码输入框的提示文字
		passwordTextField.setEchoChar('\0');     //设置明文显示文字
		
		//设置注册账号,找回密码    按钮  颜色为蓝色
		registeredAccountBtn.setForeground(Color.BLUE);
		retrievePasswordBtn.setForeground(Color.BLUE);
		
		//设置注册账号,找回密码  按钮  监听器
		registeredAccountBtn.addActionListener(new RegisteredAccountBtnListener());
		retrievePasswordBtn.addActionListener(new RetrievePasswordBtnListener());
		
		//设置输入框的焦点  监听器
		accountNumberTextField.addFocusListener(new InputAccountFocusListener());
		passwordTextField.addFocusListener(new InputPasswordFocusListener());
		
		//设置登录按钮监听器
		loginBtn.addActionListener(new LoginBtnActionListener());  
		
		/*-----------设置enter键监听-----------*/
		//按下enter即登录
		EnterKeyListener enterKeyListener = new EnterKeyListener();
		mainFrame.addKeyListener(enterKeyListener);
		aboveBackground.addKeyListener(enterKeyListener);
		accountNumberTextField.addKeyListener(enterKeyListener);
		passwordTextField.addKeyListener(enterKeyListener);
		rememberPasswordCheckBox.addKeyListener(enterKeyListener);
		automaticLogonCheckBox.addKeyListener(enterKeyListener);
		loginBtn.addKeyListener(enterKeyListener);
	}
	
	/**
	 * 创建中心布局
	 * 账号,密码,记住密码,自动登录,登录
	 * @return
	 */
	public JPanel createCenterPanel(){
		JPanel centerPanel = new JPanel();
		GridBagConstraints gbc = new GridBagConstraints();
		GridBagLayout loginCenterBagLayout = new GridBagLayout();
		
		//账号输入框  
		gbc.gridx = 0;
		gbc.insets = new Insets(0, 0, 0, 0);    //外边距
		loginCenterBagLayout.setConstraints(accountNumberTextField, gbc);
		centerPanel.add(accountNumberTextField);
		
		//注册账号   按钮
		gbc.gridx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;  //这一行的最后一个
		gbc.insets = new Insets(0, 10, 0, 0);    //外边距
		loginCenterBagLayout.setConstraints(registeredAccountBtn, gbc);
		centerPanel.add(registeredAccountBtn);
		
		//密码输入框
		gbc.insets = new Insets(10, 0, 0, 0);    //外边距
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		loginCenterBagLayout.setConstraints(passwordTextField, gbc);
		centerPanel.add(passwordTextField);
		
		// 找回密码 按钮
		gbc.gridx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER; // 这一行的最后一个
		gbc.insets = new Insets(10, 10, 0, 0); // 外边距
		loginCenterBagLayout.setConstraints(retrievePasswordBtn, gbc);
		centerPanel.add(retrievePasswordBtn);
		
		//记住密码   
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.insets = new Insets(10, 0, 0, 0);    //外边距
		loginCenterBagLayout.setConstraints(rememberPasswordCheckBox, gbc);
		centerPanel.add(rememberPasswordCheckBox);
		
		//自动登录
		gbc.gridx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;  //这一行的最后一个
		loginCenterBagLayout.setConstraints(automaticLogonCheckBox, gbc);
		centerPanel.add(automaticLogonCheckBox);
		
		//登录按钮
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.ipadx = 150;
		gbc.insets = new Insets(10, 0, 0, 0);    //外边距
		loginCenterBagLayout.setConstraints(loginBtn, gbc);
		centerPanel.add(loginBtn);
		
		centerPanel.setLayout(loginCenterBagLayout);
		return centerPanel;
	}
	
	/**
	 * 主函数
	 * @param args
	 */
	public static void main(String[] args) {
		InitGlobalFont(new Font(Font.SANS_SERIF,Font.BOLD,18));  
        try {  
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();  
            
            //这句是更换主题
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.
            		FrameBorderStyle.generalNoTranslucencyShadow;  
            
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();  
            UIManager.put("RootPane.setupButtonVisible", false);  
            //BeautyEyeLNFHelper.translucencyAtFrameInactive = false;  
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        } catch (Exception e) {  
            JOptionPane.showMessageDialog(null, "set skin fail!", "通知", JOptionPane.WARNING_MESSAGE);
        }  
        
        Utility.loadUserFileToProgram();    //装载之前用户保存成文件的信息
        
        new LoginJournalSystem().showUI();
	}

	/**
	 * 初始化字体
	 * @param font
	 */
	private static void InitGlobalFont(Font font) {  
        FontUIResource fontRes = new FontUIResource(font);  
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {  
            Object key = keys.nextElement();  
            Object value = UIManager.get(key);  
            if (value instanceof FontUIResource) {  
                UIManager.put(key, fontRes);  
            }  
        }  
    }  
	
	/**
	 * 监听 账号输入框是否获取焦点
	 * @author XFHY
	 *
	 */
	class InputAccountFocusListener implements FocusListener{

		String account;
		@Override
		public void focusGained(FocusEvent arg0) {  //获得焦点的时候,清空提示文字  
			account = accountNumberTextField.getText();
			if(account.equals(ACCOUNTINFO)){
				accountNumberTextField.setText("");
			}
		}

		@Override
		public void focusLost(FocusEvent arg0) {    //失去焦点的时候,判断如果为空,就显示提示文字 
			account = accountNumberTextField.getText();
			if(account.length() == 0){
				accountNumberTextField.setText(ACCOUNTINFO);
			}
		}
		
	}
	
	/**
	 * 监听 密码输入框是否获取焦点
	 * @author XFHY
	 *
	 */
	class InputPasswordFocusListener implements FocusListener{

		String password;   //用户输入的密码
		@Override
		public void focusGained(FocusEvent arg0) {  //获得焦点的时候,清空提示文字 
			password = new String(passwordTextField.getPassword());
			if(password.equals(PASSWORDINFO)){
				passwordTextField.setText("");
				passwordTextField.setEchoChar('●');   //当用户点击输入框,则输入密码,显示●
			}
		}

		@Override
		public void focusLost(FocusEvent arg0) {    //失去焦点的时候,判断如果为空,就显示提示文字 
			password = new String(passwordTextField.getPassword());
			if(password.length() == 0){
				passwordTextField.setText(PASSWORDINFO);
				passwordTextField.setEchoChar('\0');     //设置明文显示文字
			}
			//System.out.println(password);
		}
		
	}
	
	/**
	 * 登录 按钮  监听器
	 * @author XFHY
	 */
	class LoginBtnActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			loginProcess();  //判读用户是否为正确的用户
		}
		
	}
	
	
	/**
	 * 判读用户是否为正确的用户
	 * 用户点击了登录按钮或者按下enter键   则执行下面的
	 */
	public void loginProcess() {
		//获取用户输入的数据
		String name = accountNumberTextField.getText();
		String password = new String(passwordTextField.getPassword());
		
		     /*----------------检查是否登录成功---------------*/
		int isLoginSuccess = LoginCheck.CONNECTTODATABASEFAILED;    //是否登录成功
		if(!name.equals(ACCOUNTINFO) && !password.equals(PASSWORDINFO)){
			isLoginSuccess = LoginCheck.isSucceed(name, password);   //去判断是否成功成功
		} else {
			Icon icon = new ImageIcon("image/dialog/警告.png");
			JOptionPane.showMessageDialog(null, "请输入账号和密码再进行登录", "登录失败",
					JOptionPane.WARNING_MESSAGE, icon);
		}
		
		//登录成功
		if(isLoginSuccess == LoginCheck.LOGINSYSTEMSUCCESS){
			// TODO Auto-generated method stub
			new MainPage().showUI();
			JFrameManager.removeJFrame("登录界面");
			
			Utility.currentUser = User.getUserByName(name);    //得到当前用户  的信息 
			
			mainFrame.dispose();
		} else if (isLoginSuccess == LoginCheck.NOTLEGITIMATEUSERS){   //不是合法用户
			// TODO Auto-generated method stub
			System.out.println("不是合法用户");
		} else if (isLoginSuccess == LoginCheck.CONNECTTODATABASEFAILED){  //连接数据库都没有成功
			// TODO Auto-generated method stub
		}
	}
	
	/**
	 * enter按键  监听器
	 * @author XFHY
	 *
	 */
	class EnterKeyListener extends MyKeyListener{
		@Override
		public void keyPressed(KeyEvent event) {
			//如果用户按下的是enter键,则执行登录操作
			if(event.getKeyCode() == KeyEvent.VK_ENTER){
				loginProcess();  //判断用户是否为正确的用户
			}
		}
	}
	
	/**
	 * 注册账号 按钮  监听器
	 * @author XFHY
	 *
	 */
	class RegisteredAccountBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			new RegisteredJournalSystem().showUI();
			//mainFrame.dispose();   //关闭当前窗口
		}
		
	}
	
	/**
	 * 找回密码  按钮  监听器
	 * @author XFHY
	 *
	 */
	class RetrievePasswordBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			new RetrievePasswordWindow().showUI();
		}
		
	}
	
}
