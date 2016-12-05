package liang.guo.diary.retripasw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import liang.guo.diary.model.User;
import liang.guo.diary.mylistener.BackButtonListener;
import liang.guo.diary.util.JFrameManager;
import liang.guo.diary.util.MyRegExp;

/**
 * @author  XFHY
 * @date  2016年12月4日 上午10:19:23
 * @package liang.guo.diary.retripasw
 * @function 找回密码界面
 */
public class RetrievePasswordWindow {
	
	JFrame mainFrame = new JFrame("找回密码");
	
	//布局
	GridBagConstraints gridBagConstraints = new GridBagConstraints();
	GridBagLayout retrieveCenterBagLayout = new GridBagLayout();
	
	// 前面显示的文字
	JLabel userNameLabel = new JLabel("用户名");
	JLabel challengePhrLabel = new JLabel("密码提示问题");
	
	/**
	 * 密码提示问题
	 */
	JComboBox<String> challengePhrComboBox; // 密码提示问题
	
	// 后面需要用户输入的输入框
	JTextField userNameTextField = new JTextField(20); // 用户名
	JTextField challengePhrAnswerTextField = new JTextField(20); // 密码提示问题答案
	
	//按钮
	JButton submitButton = new JButton("提交");
	JButton backButton = new JButton("返回");       //返回按钮
	
	// 用户名只能包含字母、数字和下划线，并且首字母只能为字母，用户名最短不能少于 6 个字符，最长不能超过 20 个字符。
	private final static String NAMEREGEX = "^[a-zA-Z][a-zA-Z_0-9]*$"; // 验证用户姓名的字符串
	
	//鼠标放上去之后的提示信息
	private final static String VERIFYPROBLEMPROMPTINFORMATION = "请如实按照注册时的信息填写";
	
	//找回密码的逻辑设计
	private PasswordRecoveryLogic passwordRecoveryLogic = new PasswordRecoveryLogic();
	
	public RetrievePasswordWindow(){
		init();
	}
	
	/**
	 * 初始化
	 */
	public void init(){
		//初始化密码验证问题下拉框
		challengePhrComboBox = new JComboBox<>(User.VERIFICATIONPROBLEM);
		challengePhrComboBox.setPreferredSize(new Dimension(250, 30));
		
		//设置用户的一些输入限制
		// 用正则表达式限制swing(JTextField等)的输入 6-20
		userNameTextField.setDocument(new MyRegExp(NAMEREGEX, 20)); 
		
		//设置按钮监听器
		submitButton.addActionListener(new SubmitActionListener());
		backButton.addActionListener(new BackButtonListener("找回密码界面",mainFrame));  //返回按钮监听器
		
		//鼠标放上去之后的提示信息
		userNameTextField.setToolTipText(VERIFYPROBLEMPROMPTINFORMATION);
		challengePhrAnswerTextField.setToolTipText(VERIFYPROBLEMPROMPTINFORMATION);
		
		mainFrame.getContentPane().setBackground(new Color(215, 215, 220));  //设置背景
		mainFrame.add(createCenterJPanel(),BorderLayout.CENTER);
		mainFrame.setSize(600, 400);   
	}

	/**
	 * 显示UI
	 */
	public void showUI(){
		mainFrame.setResizable(false);     //设置窗体大小不可改变
		mainFrame.setLocationRelativeTo(null);    //设置JFrame居中
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);  //设置JFrame可见
		JFrameManager.addJFrame("找回密码界面", mainFrame);
		JFrameManager.setModel("找回密码界面");
	}
	
	/**
	 * 主函数
	 * @param args
	 */
	public static void main(String[] args) {
		InitGlobalFont(new Font(Font.SANS_SERIF,Font.BOLD,15));  
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
            System.err.println("set skin fail!");  
        }  
          
    
		new RetrievePasswordWindow().showUI();
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
	 * 创建中间的布局
	 * @return
	 */
	public JPanel createCenterJPanel(){
		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(false);    //设置是透明(false)的 
		
		// 控制外边距的
		Insets insets = new Insets(0, 0, 0, 0);
		insets.set(0, 0, 0, 0);
		
		
		// 用户名JLabel
		gridBagConstraints.gridx = 0;
		gridBagConstraints.insets = insets; // 外边距
		retrieveCenterBagLayout.setConstraints(userNameLabel, gridBagConstraints);
		centerPanel.add(userNameLabel);

		
		// 用户名输入框
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER; // 这一行的最后一个
		insets.set(0, 30, 0, 0);
		retrieveCenterBagLayout.setConstraints(userNameTextField, gridBagConstraints);
		centerPanel.add(userNameTextField);
		
		// 密码提示问题的Jlabel
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 1;
		insets.set(10, 0, 0, 0);
		retrieveCenterBagLayout.setConstraints(challengePhrLabel, gridBagConstraints);
		centerPanel.add(challengePhrLabel);

		// 密码提示问题
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER; // 这一行的最后一个
		insets.set(10, 30, 0, 0);
		retrieveCenterBagLayout.setConstraints(challengePhrComboBox, gridBagConstraints);
		centerPanel.add(challengePhrComboBox);

		// 密码提示问题答案输入框
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER; // 这一行的最后一个
		insets.set(10, 0, 0, 0);
		retrieveCenterBagLayout.setConstraints(challengePhrAnswerTextField, gridBagConstraints);
		centerPanel.add(challengePhrAnswerTextField);
		
		//提交按钮
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx = 30;
		gridBagConstraints.ipady = 10;
		insets.set(40, 100, 0, 0);
		retrieveCenterBagLayout.setConstraints(submitButton, gridBagConstraints);
		centerPanel.add(submitButton);
		
		//返回按钮
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER; // 这一行的最后一个
		insets.set(40, 0, 0, 100);
		retrieveCenterBagLayout.setConstraints(backButton, gridBagConstraints);
		centerPanel.add(backButton);
		
		centerPanel.setLayout(retrieveCenterBagLayout);
		return centerPanel;
	}
	
	/**
	 * 提交按钮   监听器
	 * @author XFHY
	 *
	 */
	class SubmitActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String userName = userNameTextField.getText();                  //用户名
			int selectIndex = challengePhrComboBox.getSelectedIndex();      //获取选择
			String problemAnswer = challengePhrAnswerTextField.getText();   //问题的答案
			
			//对话框   显示的图标
			Icon icon = new ImageIcon("image/dialog/错误.png");
			
			//判断用户名是否过短
			if(userName.length() < 6){
				JOptionPane.showMessageDialog(null, "亲,用户名长度过短,最起码也要6位",
						"亲,用户名长度过短,最起码也要6位", JOptionPane.ERROR_MESSAGE, icon);
				return ;
			}
			
			//判断用户是否填了问题的答案
			if(problemAnswer.length() == 0){
				JOptionPane.showMessageDialog(null, "亲,您还未输入问题的答案",
						"亲,您还未输入问题的答案", JOptionPane.ERROR_MESSAGE, icon);
				return ;
			}
			
			//用户找回密码
			User user = passwordRecoveryLogic.userRetrievePassword(userName,selectIndex,problemAnswer);
			if(user != null){
				new ModifyPasswordWindow(user).showUI();    //启动修改密码界面
				
			}
		}
		
	}
	
}
