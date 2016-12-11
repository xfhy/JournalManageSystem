package liang.guo.diary.main;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import liang.guo.diary.mylistener.MyWindowListener;
import liang.guo.diary.operation.KeepDiaryWindow;
import liang.guo.diary.util.BackgroundPanel;
import liang.guo.diary.util.JFrameManager;
import liang.guo.diary.viewjour.ViewJournalWindow;


/**
 * @author  XFHY
 * @date  2016年11月30日 下午10:31:06
 * @package liang.guo.diary.main
 * @function 主界面    当用户登录成功就显示这个界面
 * 可以写日记,系统设置,查找日记等等
 */
public class MainPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8742469121530688625L;

	/**
	 * 主窗口
	 */
	JFrame mainFrame;// = new JFrame();
	
	/**
	 * 主菜单栏
	 */
	JMenuBar mainMenuBar = new JMenuBar();   
	JMenu systemSettingsMenu = new JMenu("系统设置");
	JMenu diaryManagementMenu = new JMenu("日记管理");
	JMenu otherMenu = new JMenu("其他");
	JMenuItem keepDiaryMenuItem = new JMenuItem("写日记");
	JMenu seeDiaryMenuItem = new JMenu("查找日记");
	JMenuItem exitMenuItem = new JMenuItem("退出");
	JMenuItem logoffMenuItem = new JMenuItem("注销");
	JMenuItem  viewAListOfJourMenu = new JMenuItem("查看日记列表");
	
	/**
	 * 背景
	 */
	BackgroundPanel mainBackGround;
	
	/**
	 * 标题
	 */
	private final static String JFRAMETITLE = "日记管理系统主界面";
	
	/**
	 * 构造函数
	 */
	public MainPage(){
		mainFrame = this;
		this.setTitle(JFRAMETITLE);
		init();
	}
	
	/**
	 * 初始化
	 */
	public void init(){
		initAllMenu();   //初始化所有菜单
		
		//"image/main/主界面背景.jpg"
		mainBackGround = new BackgroundPanel(new ImageIcon("image/main/主界面背景.jpg").getImage());
		this.getContentPane().add(mainBackGround);   //添加背景 布局
		
		this.setJMenuBar(mainMenuBar);   //设置菜单栏
		this.addWindowListener(new MainPageWindowListener());
		this.setSize(751, 481);    
	}
	
	/**
	 * 初始化所有菜单
	 */
	public void initAllMenu(){
		/*-------设置菜单图标--------*/
		keepDiaryMenuItem.setIcon(new ImageIcon("image/main/写日记.png"));
		seeDiaryMenuItem.setIcon(new ImageIcon("image/main/查找.png"));
		exitMenuItem.setIcon(new ImageIcon("image/main/退出.png"));
		logoffMenuItem.setIcon(new ImageIcon("image/main/注销.png"));
		
		/*------------设置菜单监听器------------*/
		keepDiaryMenuItem.addActionListener(new KeepDiaryMenuItemListener());     //写日记
		viewAListOfJourMenu.addActionListener(new SeeDiaryListMenuItemListener());   //查看日记列表
		
		//日记管理菜单
		diaryManagementMenu.add(keepDiaryMenuItem);
		diaryManagementMenu.add(seeDiaryMenuItem);
		seeDiaryMenuItem.add(viewAListOfJourMenu);
		
		//其他菜单
		otherMenu.add(exitMenuItem);
		otherMenu.add(logoffMenuItem);
		
		//主菜单
		mainMenuBar.add(systemSettingsMenu);
		mainMenuBar.add(diaryManagementMenu);
		mainMenuBar.add(otherMenu);
	}
	
	/**
	 * 显示UI
	 */
	public void showUI(){
		this.setLocationRelativeTo(null);    //设置JFrame居中
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);  //设置JFrame可见
		JFrameManager.addJFrame(JFRAMETITLE, this);
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
            System.err.println("set skin fail!");  
        }  
		
		new MainPage().showUI();
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
	 * 写日记菜单  监听器
	 */
	class KeepDiaryMenuItemListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			new KeepDiaryWindow().showUI();
			mainFrame.dispose();
		}
		
	}
	
	/**
	 * 查看日记列表  菜单  监听器
	 * @author XFHY
	 *
	 */
	class SeeDiaryListMenuItemListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			new ViewJournalWindow().showUI();
			mainFrame.dispose();
		}
		
	}
	
	/**
	 * 窗口监听器  
	 * 监听窗口的关闭,打开等
	 */
	class MainPageWindowListener extends MyWindowListener{
		
		//用户试图从窗口的系统菜单中关闭窗口时调用。
		@Override
		public void windowClosing(WindowEvent e) {
			JFrameManager.removeJFrame("日记主界面窗口");
		}

		//窗口首次变为可见时调用。
		@Override
		public void windowOpened(WindowEvent e) {
			super.windowOpened(e);
			JFrameManager.addJFrame("日记主界面窗口", mainFrame);
		}
		
	}
	
}
