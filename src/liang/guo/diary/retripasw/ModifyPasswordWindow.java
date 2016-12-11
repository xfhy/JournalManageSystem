package liang.guo.diary.retripasw;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import liang.guo.diary.model.User;
import liang.guo.diary.mylistener.BackButtonListener;
import liang.guo.diary.mylistener.MyWindowListener;
import liang.guo.diary.util.JFrameManager;

/**
 * @author  XFHY
 * @date  2016年12月4日 下午5:04:52
 * @package liang.guo.diary.retripasw
 * @function 修改密码窗口   
 */
public class ModifyPasswordWindow{

	/**
	 * 需要修改密码的那个User类
	 */
	private User user;
	
	JFrame mainFrame = new JFrame("修改密码");
	
	// 前面显示的文字
	JLabel passwordLabel = new JLabel("密码");
	JLabel confirmPasswordLabel = new JLabel("确认密码");
	
	// 后面需要用户输入的输入框
	JPasswordField passwordTextField = new JPasswordField(15); // 密码
	JPasswordField confirmPasswordField = new JPasswordField(15); // 确认密码
	
	// 按钮
	JButton submitButton = new JButton("提交");
	JButton backButton = new JButton("返回"); // 返回按钮
	
	// 布局
	GridBagConstraints gridBagConstraints = new GridBagConstraints();
	GridBagLayout modifyCenterBagLayout = new GridBagLayout();
	
	//提示信息
	private final static String PASSWORDPROMPTINFORMATION = "用户密码必须包含字母数字和特殊符号, 8~30 位,密码和确认密码必须相同";
	
	/**
	 * 构造函数
	 */
	public ModifyPasswordWindow(){
		init();
	}
	
	/**
	 * 构造函数   传入User类
	 * @param user
	 */
	public ModifyPasswordWindow(User user){
		this();
		this.user = user;
	}
	
	/**
	 * 初始化
	 */
	public void init(){
		
		    /*------设置按钮监听器--------*/
		submitButton.addActionListener(new SubmitActionListener());       //提交按钮监听器
		backButton.addActionListener(new BackButtonListener("修改密码界面",mainFrame));  //返回按钮监听器
		
		//鼠标放上去的提示信息
		passwordTextField.setToolTipText(PASSWORDPROMPTINFORMATION);
		confirmPasswordField.setToolTipText(PASSWORDPROMPTINFORMATION);
		
		mainFrame.add(createCenterPane(),BorderLayout.CENTER);
		mainFrame.addWindowListener(new ModifyPasswordWindowListener());
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
		JFrameManager.setModel("修改密码界面");
	}
	
	/**
	 * 主函数
	 * @param args
	 */
	public static void main(String[] args) {
		//InitGlobalFont(new Font(UICons.FONT_TYPE, Font.PLAIN, UICons.FONT_SIZE));  
		InitGlobalFont(new Font(Font.SANS_SERIF,Font.PLAIN,15));  
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
          
    
		new ModifyPasswordWindow().showUI();
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
	public JPanel createCenterPane(){
		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		
		// 控制外边距的
		Insets insets = new Insets(0, 0, 0, 0);
		insets.set(0, 0, 0, 0);
		
		// 密码
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.insets = insets;
		insets.set(10, 0, 0, 0);
		modifyCenterBagLayout.setConstraints(passwordLabel, gridBagConstraints);
		centerPanel.add(passwordLabel);

		// 密码输入框
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER; // 这一行的最后一个
		insets.set(10, 20, 0, 0);
		modifyCenterBagLayout.setConstraints(passwordTextField, gridBagConstraints);
		centerPanel.add(passwordTextField);

		// 确认密码
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 1;
		insets.set(10, 0, 0, 0);
		modifyCenterBagLayout.setConstraints(confirmPasswordLabel, gridBagConstraints);
		centerPanel.add(confirmPasswordLabel);

		// 确认密码输入框
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER; // 这一行的最后一个
		insets.set(10, 20, 0, 0);
		modifyCenterBagLayout.setConstraints(confirmPasswordField, gridBagConstraints);
		centerPanel.add(confirmPasswordField);
		
		// 提交按钮
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx = 25;    //把按钮撑大,横向
		gridBagConstraints.ipady = 10;    //把按钮撑大,纵向
		insets.set(60, 80, 0, 0);
		modifyCenterBagLayout.setConstraints(submitButton, gridBagConstraints);
		centerPanel.add(submitButton);

		// 返回按钮
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER; // 这一行的最后一个
		insets.set(60, 20, 0, 0);
		modifyCenterBagLayout.setConstraints(backButton, gridBagConstraints);
		centerPanel.add(backButton);
		
		centerPanel.setLayout(modifyCenterBagLayout);
		return centerPanel;
	}
	
	/**
	 * 提交按钮   监听器   
	 * @author XFHY
	 *
	 */
	class SubmitActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//获取用户输入的东西
			String password = new String(passwordTextField.getPassword());   //获取密码
			String confirmPassword = new String(confirmPasswordField.getPassword()); //获取确认密码
			
			//对话框   显示的图标
			Icon icon = new ImageIcon("image/dialog/错误.png");
			
			//判断是否输入一致
			if(!password.equals(confirmPassword)){
				JOptionPane.showMessageDialog(null, "亲,两次输入密码不一致,请重新输入",
						"亲,两次输入密码不一致", JOptionPane.ERROR_MESSAGE, icon);
				passwordTextField.setText("");
				confirmPasswordField.setText("");
				return ;
			}
			
			//判断输入长度
			if(password.length() < 8 || password.length() > 30){
				JOptionPane.showMessageDialog(null, "亲,密码长度过短,必须是8~30位",
						"亲,密码长度过短", JOptionPane.ERROR_MESSAGE, icon);
				return ;
			}
			
			//用户密码必须包含字母数字和特殊符号，密码最短为 8 位，最长不能超过 30 位；密码和确认密码必须相同
			//正则表达式
			String regexLetter = ".*[a-zA-Z]+.*";  //字母
			String regexNumber = ".*[0-9]+.*";     //数字
			String regexSpecialChar = ".*[\\p{Punct}]+.*";     //特殊字符
			String regexChinese = ".*[\u4e00-\u9fa5]+.*";  //汉字
			if( !(password.matches(regexLetter) 
					&& password.matches(regexNumber)
					&& password.matches(regexSpecialChar)
					&& !password.matches(regexChinese)) ){
				JOptionPane.showMessageDialog(null, 
						"亲,密码不符合要求.用户密码必须包含字母数字和特殊符号，密码最短为 8 位，最长不能超过 30 位",
						"亲,密码不符合要求", 
						JOptionPane.ERROR_MESSAGE, icon);
				return ;
			}
			
			//判断user是否为空
			if(user == null){
				JOptionPane.showMessageDialog(null, "亲,发送意外错误,密码更改失败,请重新更改...",
						"亲,发送意外错误", JOptionPane.ERROR_MESSAGE, icon);
				return ;
			}
			
			//上面的各种拦截都没拦截下来,说明这是符合要求的密码    下面   更改密码
			user.setUserPassword(password);
			
			icon = new ImageIcon("image/dialog/完成.png");
			JOptionPane.showMessageDialog(null, "密码修改成功",
					"密码修改成功", JOptionPane.INFORMATION_MESSAGE, icon);
		}
		
	}
	
	/**
	 * 窗口监听器  
	 * 监听窗口的关闭,打开等
	 */
	class ModifyPasswordWindowListener extends MyWindowListener{
		
		//用户试图从窗口的系统菜单中关闭窗口时调用。
		@Override
		public void windowClosing(WindowEvent e) {
			JFrameManager.removeJFrame("修改密码窗口");
		}

		//窗口首次变为可见时调用。
		@Override
		public void windowOpened(WindowEvent e) {
			super.windowOpened(e);
			JFrameManager.addJFrame("修改密码窗口", mainFrame);
		}
		
	}
	
}
