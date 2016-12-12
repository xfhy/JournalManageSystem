package liang.guo.diary.register;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import liang.guo.diary.model.User;
import liang.guo.diary.mylistener.BackButtonListener;
import liang.guo.diary.mylistener.MyMouseListener;
import liang.guo.diary.mylistener.MyWindowListener;
import liang.guo.diary.util.BackgroundPanel;
import liang.guo.diary.util.JFrameManager;
import liang.guo.diary.util.MyRegExp;
import liang.guo.diary.util.Utility;

/**
 * @author XFHY
 * @date 2016年12月1日 下午6:41:42
 * @package liang.guo.diary.register
 * @function 这是账号注册界面
 */
public class RegisteredJournalSystem {

	JFrame mainFrame = new JFrame("账号注册");

	/**
	 * 窗口的内容窗口
	 */
	private JPanel imagePanel;

	GridBagConstraints gridBagConstraints = new GridBagConstraints();
	GridBagLayout regisCenterBagLayout = new GridBagLayout();

	// 前面显示的文字
	JLabel userNameLabel = new JLabel("用户名");
	JLabel displayNameLabel = new JLabel("显示名");
	JLabel passwordLabel = new JLabel("密码");
	JLabel confirmPasswordLabel = new JLabel("确认密码");
	JLabel mailboxLabel = new JLabel("邮箱");
	JLabel challengePhrLabel = new JLabel("密码提示问题");
	JLabel verificationLabel = new JLabel("验证码");
	JLabel simpleFourOperationsLabel; // 验证码图片

	/**
	 * 密码提示问题
	 */
	JComboBox<String> challengePhrComboBox; // 密码提示问题

	// 后面需要用户输入的输入框
	JTextField userNameTextField = new JTextField(20); // 用户名
	JTextField displayNameTextField = new JTextField(20); // 显示名
	JPasswordField passwordTextField = new JPasswordField(20); // 密码
	JPasswordField confirmPasswordField = new JPasswordField(20); // 确认密码
	JTextField mailboxTextField = new JTextField(20); // 邮箱
	JTextField challengePhrAnswerTextField = new JTextField(20); // 密码提示问题答案
	JTextField verifiProbAnswerTextField = new JTextField(10); // 算术验证问题答案

	//生成验证码的类
	Verification verification = new Verification();

	JButton registerButton = new JButton("注册");   //注册按钮
	JButton backButton = new JButton("返回");       //返回按钮

	//提示信息  
	private final static String USERNAMEPROMPTINFORMATION = "用户名只能包含字母、数字和下划线，并且首字母只能为字母,6~20位";
	private final static String DISPLAYNAMEPROMPTINFORMATION = "显示名可以包含任意字符,3~20位";
	private final static String PASSWORDPROMPTINFORMATION = "用户密码必须包含字母数字和特殊符号, 8~30 位,密码和确认密码必须相同";
	private final static String MAILBOXROMPTINFORMATION = "邮箱必须符合邮箱格式，最长不能超过 50 个字符";
	private final static String PASSWORDPROMPTPROBLEMINFORMATION = "请认真填写,找回密码时需要用到哦...";
	private final static String VERIFICATIONCODEINFORMATION = "计算结果请保留整数";
	
	// 用户名只能包含字母、数字和下划线，并且首字母只能为字母，用户名最短不能少于 6 个字符，最长不能超过 20 个字符。
	private final static String NAMEREGEX = "^[a-zA-Z][a-zA-Z_0-9]*$"; // 验证用户姓名的字符串
	
	/**
	 * 背景布局
	 */
	BackgroundPanel mainBackGround;

	public RegisteredJournalSystem() {

		init();
	}

	/**
	 * 初始化
	 */
	public void init() {
		challengePhrComboBox = new JComboBox<>(User.VERIFICATIONPROBLEM);
		challengePhrComboBox.setPreferredSize(new Dimension(250, 30));

		mainBackGround = new BackgroundPanel(new ImageIcon("image/register/注册界面背景.jpg").getImage());
		mainBackGround.setBounds(0, 0, 800, 600);

		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		imagePanel = (JPanel) mainFrame.getContentPane(); // this.getContentPane()的作用是初始化一个容器，用来在容器上添加一些控件
		imagePanel.setOpaque(false); // 是否透明

		initJLabelAndTextField();   //初始化所有的JLabel和JTextField
		
		registerButton.addActionListener(new RegisterBtnListener());   //注册按钮监听器
		backButton.addActionListener(new BackButtonListener("注册界面",mainFrame));  //返回按钮监听器

		// 内容窗格默认的布局管理器为BorderLayout
		imagePanel.add(createCenterGridBagLayout()); // 设置布局

		// 把背景图片添加到分层窗格的最底层作为背景
		mainFrame.getLayeredPane().add(mainBackGround, new Integer(Integer.MIN_VALUE));

		mainFrame.addWindowListener(new RegisteredWindowListener());
		mainFrame.setSize(800, 600);
	}

	/**
	 * 显示UI
	 */
	public void showUI() {
		mainFrame.setResizable(false); // 设置窗体大小不可改变
		mainFrame.setLocationRelativeTo(null); // 设置JFrame居中
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true); // 设置JFrame可见
	}

	/**
	 * 主函数
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		InitGlobalFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();

			// 这句是更换主题
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;

			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("RootPane.setupButtonVisible", false);
			// BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println("set skin fail!");
		}

		new RegisteredJournalSystem().showUI();
	}

	/**
	 * 初始化字体
	 * 
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
	 * 初始化所有的JLabel和输入框
	 */
	public void initJLabelAndTextField() {
		// 设置Jlabel字体颜色
		userNameLabel.setForeground(Color.white);
		displayNameLabel.setForeground(Color.white);
		passwordLabel.setForeground(Color.white);
		confirmPasswordLabel.setForeground(Color.white);
		mailboxLabel.setForeground(Color.white);
		verificationLabel.setForeground(Color.white);
		challengePhrLabel.setForeground(Color.white);
		
		   /*----------设置用户提示信息--------*/
		userNameLabel.setToolTipText(USERNAMEPROMPTINFORMATION);
		userNameTextField.setToolTipText(USERNAMEPROMPTINFORMATION);
		displayNameLabel.setToolTipText(DISPLAYNAMEPROMPTINFORMATION);
		displayNameTextField.setToolTipText(DISPLAYNAMEPROMPTINFORMATION);
		passwordLabel.setToolTipText(PASSWORDPROMPTINFORMATION);
		passwordTextField.setToolTipText(PASSWORDPROMPTINFORMATION);
		confirmPasswordLabel.setToolTipText(PASSWORDPROMPTINFORMATION);
		confirmPasswordField.setToolTipText(PASSWORDPROMPTINFORMATION);
		mailboxLabel.setToolTipText(MAILBOXROMPTINFORMATION);
		mailboxTextField.setToolTipText(MAILBOXROMPTINFORMATION);
		challengePhrLabel.setToolTipText(PASSWORDPROMPTPROBLEMINFORMATION);
		challengePhrComboBox.setToolTipText(PASSWORDPROMPTPROBLEMINFORMATION);
		challengePhrAnswerTextField.setToolTipText(PASSWORDPROMPTPROBLEMINFORMATION);
		verificationLabel.setToolTipText(VERIFICATIONCODEINFORMATION);
		verificationLabel.setToolTipText(VERIFICATIONCODEINFORMATION);
		verifiProbAnswerTextField.setToolTipText(VERIFICATIONCODEINFORMATION);
		
		//这是验证码
		simpleFourOperationsLabel = new JLabel();
		simpleFourOperationsLabel.setIcon(new ImageIcon(verification.getImage())); // 设置验证码图片
		// 设置验证码图片 点击 监听器
		simpleFourOperationsLabel.addMouseListener(new RandomCreateVerificationListener());
		registerButton.setPreferredSize(new Dimension(180, 50));

		   //设置用户的一些输入限制
		userNameTextField.setDocument(new MyRegExp(NAMEREGEX, 20)); // 用正则表达式限制swing(JTextField等)的输入 6-20
		displayNameTextField.setDocument(new MyRegExp("[\\s\\S]*", 20)); // 3-20位
		mailboxTextField.setDocument(new MyRegExp("[\\s\\S]*", 20));
	}

	/**
	 * 初始化布局管理
	 */
	public JPanel createCenterGridBagLayout() {
		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		
		//控制外边距的
		Insets insets = new Insets(0, 0, 0, 0);
		insets.set(0, 0, 0, 0);

		// 用户名JLabel
		gridBagConstraints.gridx = 0;
		gridBagConstraints.insets = insets; // 外边距
		regisCenterBagLayout.setConstraints(userNameLabel, gridBagConstraints);
		centerPanel.add(userNameLabel);

		// 用户名输入框
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER; // 这一行的最后一个
		insets.set(0, 30, 0, 0);
		regisCenterBagLayout.setConstraints(userNameTextField, gridBagConstraints);
		centerPanel.add(userNameTextField);

		// 显示名
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 1;
		insets.set(10, 0, 0, 0);
		regisCenterBagLayout.setConstraints(displayNameLabel, gridBagConstraints);
		centerPanel.add(displayNameLabel);

		// 显示名输入框
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER; // 这一行的最后一个
		insets.set(10, 30, 0, 0);
		regisCenterBagLayout.setConstraints(displayNameTextField, gridBagConstraints);
		centerPanel.add(displayNameTextField);

		// 密码
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 1;
		insets.set(10, 0, 0, 0);
		regisCenterBagLayout.setConstraints(passwordLabel, gridBagConstraints);
		centerPanel.add(passwordLabel);

		// 密码输入框
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER; // 这一行的最后一个
		insets.set(10, 30, 0, 0);
		regisCenterBagLayout.setConstraints(passwordTextField, gridBagConstraints);
		centerPanel.add(passwordTextField);

		// 确认密码
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 1;
		insets.set(10, 0, 0, 0);
		regisCenterBagLayout.setConstraints(confirmPasswordLabel, gridBagConstraints);
		centerPanel.add(confirmPasswordLabel);

		// 确认密码输入框
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER; // 这一行的最后一个
		insets.set(10, 30, 0, 0);
		regisCenterBagLayout.setConstraints(confirmPasswordField, gridBagConstraints);
		centerPanel.add(confirmPasswordField);

		// 邮箱
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 1;
		insets.set(10, 0, 0, 0);
		regisCenterBagLayout.setConstraints(mailboxLabel, gridBagConstraints);
		centerPanel.add(mailboxLabel);

		// 邮箱输入框
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER; // 这一行的最后一个
		insets.set(10, 30, 0, 0);
		regisCenterBagLayout.setConstraints(mailboxTextField, gridBagConstraints);
		centerPanel.add(mailboxTextField);

		// 密码提示问题的Jlabel
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 1;
		insets.set(10, 0, 0, 0);
		regisCenterBagLayout.setConstraints(challengePhrLabel, gridBagConstraints);
		centerPanel.add(challengePhrLabel);

		// 密码提示问题
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		insets.set(10, 30, 0, 0);
		regisCenterBagLayout.setConstraints(challengePhrComboBox, gridBagConstraints);
		centerPanel.add(challengePhrComboBox);

		// 密码提示问题答案输入框
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER; // 这一行的最后一个
		insets.set(10, 124, 0, 0);
		regisCenterBagLayout.setConstraints(challengePhrAnswerTextField, gridBagConstraints);
		centerPanel.add(challengePhrAnswerTextField);

		// 验证码JLabel
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 1;
		insets.set(10, 0, 0, 0);
		regisCenterBagLayout.setConstraints(verificationLabel, gridBagConstraints);
		centerPanel.add(verificationLabel);

		// 验证码 图片
		gridBagConstraints.gridx = 1;
		insets.set(10, 30, 0, 0);
		regisCenterBagLayout.setConstraints(simpleFourOperationsLabel, gridBagConstraints);
		centerPanel.add(simpleFourOperationsLabel);

		// 验证码 输入框
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		insets.set(10, 30, 0, 0);
		regisCenterBagLayout.setConstraints(verifiProbAnswerTextField, gridBagConstraints);
		centerPanel.add(verifiProbAnswerTextField);

		// 注册按钮
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		insets.set(30, 10, 0, 0);
		regisCenterBagLayout.setConstraints(registerButton, gridBagConstraints);
		centerPanel.add(registerButton);

		//返回按钮
		gridBagConstraints.gridx = 0;
		insets.set(60, 380, 0, 0);
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		regisCenterBagLayout.setConstraints(backButton, gridBagConstraints);
		centerPanel.add(backButton);
		
		// 设置布局
		centerPanel.setLayout(regisCenterBagLayout);
		return centerPanel;
	}

	/**
	 * 
	 * @author XFHY 随机生成 验证码 监听器
	 */
	class RandomCreateVerificationListener extends MyMouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			simpleFourOperationsLabel.setIcon(new ImageIcon(verification.getImage()));
		}
	}

	/**
	 * 注册按钮监听器
	 * @author XFHY
	 *
	 */
	class RegisterBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Icon icon = new ImageIcon("image/dialog/错误.png");
			
			//判断用户名是否输入正确
			if(!isCorrectUserName()){
				JOptionPane.showMessageDialog(null, USERNAMEPROMPTINFORMATION,
						"亲,您输入的用户名不符合要求", JOptionPane.ERROR_MESSAGE, icon);
				return ;
			}
			
			//判断显示名是否输入正确
			if(!isCorrectDisplayName()){
				JOptionPane.showMessageDialog(null, DISPLAYNAMEPROMPTINFORMATION,
						"亲,您输入的用户名不符合要求", JOptionPane.ERROR_MESSAGE, icon);
				return ;
			}
			
			//判断是否密码输入一致
			if(new String(passwordTextField.getPassword()).
					equals(new String(confirmPasswordField.getPassword()))){
				//判断密码是否符合要求
				if(!User.isCurrentPassword(new String(passwordTextField.getPassword()))){
					JOptionPane.showMessageDialog(null, PASSWORDPROMPTINFORMATION,
							"亲,您输入的密码不符合要求", JOptionPane.ERROR_MESSAGE, icon);
					return ;
				}
			} else {
				JOptionPane.showMessageDialog(null, PASSWORDPROMPTINFORMATION,
						"亲,您输入的密码不一致", JOptionPane.ERROR_MESSAGE, icon);
				return ;
			}
			
			if(!User.isCurrentEmail(mailboxTextField.getText())){
				JOptionPane.showMessageDialog(null, MAILBOXROMPTINFORMATION,
						"亲,您输入的邮箱不符合要求", JOptionPane.ERROR_MESSAGE, icon);
				return ;
			}
			
			if(challengePhrAnswerTextField.getText().length() == 0){
				JOptionPane.showMessageDialog(null, "亲,密码提示问题不能留空哦",
						"亲,密码提示问题不能留空哦", JOptionPane.ERROR_MESSAGE, icon);
				return ;
			}
			
			//int selectProblem = challengePhrComboBox.getSelectedIndex();   
			//String problemAnswer = challengePhrAnswerTextField.getText();
			
			//判断用户输入的验证码是否为空
			if(verifiProbAnswerTextField.getText().length() == 0){
				JOptionPane.showMessageDialog(null, "亲,您未输入验证码",
						"亲,您未输入验证码", JOptionPane.ERROR_MESSAGE, icon);
				return ;
			}
			
			//判断用户输入的验证码结果是否正确
			if(!verifiProbAnswerTextField.getText().equals(String.valueOf(verification.getSum()))){
				JOptionPane.showMessageDialog(null, "亲,您输入的验证码结果不正确",
						"亲,您输入的验证码结果不正确", JOptionPane.ERROR_MESSAGE, icon);
				return ;
			}
			
			//如果能到达这里,说明上面的数据都已按要求输入
			if(saveUserToFile()){  //保存用户信息到文件中
				icon = new ImageIcon("image/dialog/信息.png");
				JOptionPane.showMessageDialog(null, "恭喜!注册成功!",
						"恭喜!注册成功!", JOptionPane.INFORMATION_MESSAGE, icon);
				mainFrame.dispose();
			}   
		}
		
	}
	
	/**
	 * 判断是否是正确的用户名
	 * @return
	 */
	private boolean isCorrectUserName(){
		//上面已经判断了正则表达式,确保不是其他字符,这里只需要判断长度6~20位即可
		String userName = userNameTextField.getText();    
		//6~20位    且不含有该用户名
		if(userName.length() >=6 && userName.length() <= 20 && !User.haveThisUser(userName)){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断是否是正确的显示名
	 * @return
	 */
	private boolean isCorrectDisplayName(){
		String displayName = displayNameTextField.getText();
		if(displayName.length() >= 3 && displayName.length() <= 20){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 保存当前  注册成功的  用户到文件中
	 */
	private boolean saveUserToFile(){
		User userTemp = new User();
		
		userTemp.setUserName(userNameTextField.getText());               //设置用户名
		userTemp.setUserDisplayName(displayNameTextField.getText());     //设置显示名
		userTemp.setUserPassword(new String(passwordTextField.getPassword()));//设置密码
		userTemp.setUserMailBox(mailboxTextField.getText());                  //设置邮箱
		
		//让用户选择 1-5,哪个问题
		userTemp.setUserChooseProblem(challengePhrComboBox.getSelectedIndex());   
		//让用户选择需要回答哪个密码提示问题,并获取用户输入的该问题的答案
		userTemp.setUserQuestionAnswer(challengePhrAnswerTextField.getText());       
		
		boolean addSuccess = Utility.addAllUserInfo(userTemp);   //添加用户到所有用户集合中
		Utility.saveUserCountsToFile();     //添加用户数目到文件中
		Utility.saveUserToFile();           //添加用户数据到文件中
		return addSuccess;
	}
	
	/**
	 * 窗口监听器  
	 * 监听窗口的关闭,打开等
	 */
	class RegisteredWindowListener extends MyWindowListener{
		
		// 因对窗口调用 dispose 而将其关闭时调用。
		@Override
		public void windowClosed(WindowEvent e) {
			super.windowClosed(e);
			JFrameManager.removeJFrame("账号注册界面窗口");
		}
		
		//用户试图从窗口的系统菜单中关闭窗口时调用。
		@Override
		public void windowClosing(WindowEvent e) {
			JFrameManager.removeJFrame("账号注册界面窗口");
		}

		//窗口首次变为可见时调用。
		@Override
		public void windowOpened(WindowEvent e) {
			super.windowOpened(e);
			JFrameManager.addJFrame("账号注册界面窗口", mainFrame);
		}
		
	}
	
}
