package liang.guo.diary.operation;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import liang.guo.diary.database.DatabaseTool;
import liang.guo.diary.enumerate.MoodType;
import liang.guo.diary.enumerate.WeatherType;
import liang.guo.diary.main.MainPage;
import liang.guo.diary.model.Date;
import liang.guo.diary.model.Diary;
import liang.guo.diary.model.User;
import liang.guo.diary.mylistener.MyComponentListener;
import liang.guo.diary.mylistener.MyWindowListener;
import liang.guo.diary.operation.cell.MoodComboBoxRenderer;
import liang.guo.diary.operation.cell.WeatherComboBoxRenderer;
import liang.guo.diary.operation.datechooser.DateChooserJButton;
import liang.guo.diary.util.BackgroundPanel;
import liang.guo.diary.util.JFrameManager;
import liang.guo.diary.util.ShowDialog;
import liang.guo.diary.util.Utility;

/**
 * @author  XFHY
 * @date  2016年12月6日 下午1:28:39
 * @package liang.guo.diary.operation
 * @function 写日记界面
 */
public class KeepDiaryWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8350067391023318512L;

	/**
	 * 主容器
	 */
	JFrame mainFrame;
	
	//布局管理
	GridBagConstraints gridBagConstraints = new GridBagConstraints();
	GridBagLayout keepDiaryCenterBagLayout = new GridBagLayout();
	
	//前面显示的文字
	JLabel weatherTypeLabel = new JLabel("天气");
	JLabel moodTypeLabel = new JLabel("心情");
	JLabel dateLabel = new JLabel("日期");
	JLabel titleLabel = new JLabel("标题");
	
	//下拉框
	JComboBox<WeatherType> weatherTypeComboBox;   //天气下拉选择框
	JComboBox<MoodType> moodTypeComboBox;         //心情下拉选择框
	
	/**
	 * 日期选择器
	 */
	DateChooserJButton dateChooserJButton = new DateChooserJButton();
	/**
	 * 标题输入框
	 */
	JTextField titleTextField = new JTextField();
	
	/**
	 * 天气JComboBox的模型
	 */
	WeatherComboBoxRenderer weatherRenderer = new WeatherComboBoxRenderer();
	/**
	 * 心情JComboBox的模型
	 */
	MoodComboBoxRenderer moodRenderer = new MoodComboBoxRenderer();
	
	/**
	 * 写日记的Text
	 */
	JTextArea diaryTextArea = new JTextArea(5,5);
	JScrollPane jScrollPane;
	
	JButton completeButton = new JButton("保存(S)");
	JButton cancelButton = new JButton("取消(W)");
	
	/**
	 * 背景布局
	 */
	BackgroundPanel mainBackGround;
	
	/**
	 * 窗口的内容窗口
	 */
	private JPanel imagePanel;
	
	//当前用户的信息
	private User user;
	
	/**
	 * 构造函数
	 */
	public KeepDiaryWindow(){
		user = Utility.currentUser;
		this.setTitle("写日记");
		mainFrame = this;
		init();
		System.out.println(Utility.currentUser.toString());
	}
	
	/**
	 * 初始化
	 */
	public void init(){
		diaryTextArea.setOpaque(false);   //设置中间写日记的地方是透明的
		diaryTextArea.setBorder(BorderFactory.createLineBorder(Color.gray,2));  //设置文本域边框
		diaryTextArea.setLineWrap(true);   //设置JTextArea自动换行
		jScrollPane = new JScrollPane(diaryTextArea);   //将文本域加入滑动JScrollPane
		jScrollPane.setOpaque(false);
		//设置写日记的区域  是随时可以滑动的
		jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		//这是背景
		mainBackGround = new BackgroundPanel(new ImageIcon("image/operation/写日记背景.jpg").getImage());
		mainBackGround.setBounds(0, 0, 800, 600);
		
		weatherTypeComboBox = new JComboBox<>(WeatherType.values());    //初始化天气JComboBox的值
		weatherTypeComboBox.setRenderer(weatherRenderer);               //设置天气JComboBox的模型
		moodTypeComboBox = new JComboBox<>(MoodType.values());          //初始化心情JComboBox的值
		moodTypeComboBox.setRenderer(moodRenderer);                     //设置心情JComboBox的模型
		
		/*--------设置按钮监听器------------*/
		completeButton.addActionListener(new SaveDiaryBtnListener());
		cancelButton.addActionListener(new CancelBtnListener());
		
		/*--------设置按钮快捷键------------*/
		completeButton.registerKeyboardAction(new SaveDiaryBtnListener(),
				KeyStroke.getKeyStroke( KeyEvent.VK_S, KeyEvent.CTRL_MASK),JComponent.WHEN_IN_FOCUSED_WINDOW);
		cancelButton.registerKeyboardAction(new CancelBtnListener(),
				KeyStroke.getKeyStroke( KeyEvent.VK_W, KeyEvent.CTRL_MASK),JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		imagePanel = (JPanel) this.getContentPane(); // this.getContentPane()的作用是初始化一个容器，用来在容器上添加一些控件
		imagePanel.setOpaque(false); // 是否透明
		
		imagePanel.add(createCenterPanel());    //设置中心布局
		
		// 把背景图片添加到分层窗格的最底层作为背景
		this.getLayeredPane().add(mainBackGround, new Integer(Integer.MIN_VALUE));
		
		this.setSize(800, 600);    //设置初始大小
		this.addComponentListener(new JFrameSizeChangeListener());   //监听窗口大小变化
		this.addWindowListener(new KeepDiaryWindowListener());       //监听窗口的事件
	}  
	
	/**
	 * 显示UI
	 */
	public void showUI(){
		Image icon = Toolkit.getDefaultToolkit().getImage("image/operation/写.png");   
		this.setIconImage(icon);   //设置窗口左上角的小图标
		
		//this.setResizable(false); // 设置窗体大小不可改变
		this.setLocationRelativeTo(null); // 设置JFrame居中
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);  //设置JFrame可见
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
        } catch (Exception e) {  
            System.err.println("set skin fail!");  
        }  
          
    
		new KeepDiaryWindow().showUI();
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
     * 设置组件的样式  
     * @param gridx
     * @param gridy
     * @param ipadx
     * @param ipady
     * @param gridwidth
     * @param gridheight
     * @param component
     * @param panel
     */
	private void setComponentStyle(int gridx,int gridy,
			int ipadx,int ipady,
			int gridwidth,int gridheight,
			JComponent component,JPanel panel){
		/*
		 * gridx,gridy默认值是RELATIVE
		 * ipadx,ipady默认值是0
		 * gridwidth,gridheight默认值是1
		 * */
		gridBagConstraints.gridx = gridx;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.ipadx = ipadx;
		gridBagConstraints.ipady = ipady;
		gridBagConstraints.gridwidth = gridwidth;
		gridBagConstraints.gridheight = gridheight;
		keepDiaryCenterBagLayout.setConstraints(component, gridBagConstraints);
		panel.add(component);
	}
	
	/**
	 * 生成页面中心的布局
	 * @return
	 */
	public JPanel createCenterPanel(){
		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(false);   //设置这个容器是透明的
		
		//控制外边距的
		Insets insets = new Insets(10, 50, 0, 0);
		
		//天气JLabel
		//gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = insets;
		//当组件的显示区域大于它所请求的显示区域的大小时使用此字段。它可以确定是否调整组件大小，以及在需要的时候如何进行调整。 
		gridBagConstraints.fill=GridBagConstraints.BOTH;  
		gridBagConstraints.weightx = 1.0;   //横向  占比重
		gridBagConstraints.weighty = 0.0;   //纵向  占比重
		setComponentStyle(0, 0, 0, 0, 1, 1, weatherTypeLabel, centerPanel);
		
		//天气下拉选择框
		insets.set(10, 0, 0, 0);
		setComponentStyle(1, 0, 0, 0, 1, 1, weatherTypeComboBox, centerPanel);
		
		//心情JLabel
		insets.set(10, 100, 0, 0);
		setComponentStyle(2, 0, 0, 0, 1, 1, moodTypeLabel, centerPanel);
		
		// 心情下拉选择框
		insets.set(10, 0, 0, 50);
		setComponentStyle(3, 0, 70, 0, GridBagConstraints.REMAINDER, 1, moodTypeComboBox, centerPanel);
		
		//日期选择器
		insets.set(10, 50, 10, 10);
		setComponentStyle(0, 1, 0, 0, 1, 1, dateLabel, centerPanel);
		
		//日期选择按钮
		insets.set(10, 10, 10, 10);
		setComponentStyle(1, 1, 0, 20, 1, 1, dateChooserJButton, centerPanel);
		
		//标题   JLabel
		insets.set(10, 100, 10, 0);
		setComponentStyle(2, 1, 0, 0, 1, 1, titleLabel, centerPanel);
		
		//标题   输入框
		insets.set(10, 0, 10, 50);
		setComponentStyle(3, 1, 60, 0, GridBagConstraints.REMAINDER, 1, titleTextField, centerPanel);
		
		//写日记的Text
		insets.set(10, 10, 10, 10);
		setComponentStyle(0, 2, 0, 200, GridBagConstraints.REMAINDER, 1, jScrollPane, centerPanel);
		
		//完成按钮
		insets.set(10, 20, 10, 10);
		setComponentStyle(0, 3, 0, 0, 1, 1, completeButton, centerPanel);
		
		//取消按钮
		setComponentStyle(1, 3, 0, 0, 1, 1, cancelButton, centerPanel);
		
		// 设置布局
		centerPanel.setLayout(keepDiaryCenterBagLayout);
		return centerPanel;
	}
	
	//重写这个方法  即可知道用户是否在点击关闭窗口按钮
    @Override  
    protected void processWindowEvent(WindowEvent e) {  
        if (e.getID() == WindowEvent.WINDOW_CLOSING)  
        {
        	Object[] options = {"确定","取消"};
			Icon icon = new ImageIcon("image/main/退出.png");
			int returnCode = JOptionPane.showOptionDialog(null, "亲,您确定要退出吗?", "Warning",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
					icon, options, options[0]);
			
			if(returnCode == 0){  //如果用户点击了确定按钮
				this.dispose();
			} 
        	return; //直接返回，阻止默认动作，阻止窗口关闭  
        }
        super.processWindowEvent(e); //该语句会执行窗口事件的默认动作(如：隐藏)  
    }  
	
	/**
	 * 窗口大小变化   监听器
	 * @author XFHY
	 *
	 */
	class JFrameSizeChangeListener extends MyComponentListener{
		
		@Override
		public void componentResized(ComponentEvent e) {
			//返回此窗体的 contentPane 对象
			Container contentPane = (JFrame)e.getComponent();
			Dimension size = contentPane.getSize();    //可视区域的大小
			//设置背景图片的大小
			mainBackGround.setBounds(0,0,(int)size.getWidth()+10,(int)size.getHeight()+40);
		}
	}
	
	/**
	 * 保存按钮  监听器
	 */
	class SaveDiaryBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			WeatherType weather;  //天气
			MoodType mood;        //心情
			Date date;            //日期
			String title = titleTextField.getText();         //标题
			String content = diaryTextArea.getText();       //日记内容
			
			//判断标题是否为空
			if(title.length() == 0){
				JOptionPane.showMessageDialog(null, "亲,请输入标题", "标题为空", JOptionPane.WARNING_MESSAGE);
				return ;
			}
			
			//判断日记文本框是否输入为空
			if(content.length() == 0){
				JOptionPane.showMessageDialog(null, "亲,请输入日记内容", "日记内容为空", JOptionPane.WARNING_MESSAGE);
				return ;
			}
			
			/*----------获取用户输入的数据-----------*/
			date = new Date(dateChooserJButton.getText());
			switch (weatherTypeComboBox.getSelectedIndex()) {
			case 0:
				weather = WeatherType.SUNNY;
				break;
			case 1:
				weather = WeatherType.OVERCAST;
				break;
			case 2:
				weather = WeatherType.CLOUDY;
				break;
			case 3:
				weather = WeatherType.RAIN;
				break;
			case 4:
				weather = WeatherType.FOG;
				break;
			case 5:
				weather = WeatherType.SNOW;
				break;
			default:
				weather = WeatherType.SUNNY;
				break;
			}
			
			switch (moodTypeComboBox.getSelectedIndex()) {
			case 0:
				mood = MoodType.HAPPY;
				break;
			case 1:
				mood = MoodType.DEPRESSED;
				break;
			case 2:
				mood = MoodType.EXCITEMENT;
				break;
			case 3:
				mood = MoodType.SADNESS;
				break;
			case 4:
				mood = MoodType.FEAR;
				break;
			case 5:
				mood = MoodType.DELIGHTED;
				break;
			default:
				mood = MoodType.HAPPY;
				break;
			}
			
			//创建一个日记类   保存用户的数据
			Diary diary = new Diary();
			diary.setWeather(weather);
			diary.setMood(mood);
			diary.setDate(date);
			diary.setTitle(title);
			diary.setContent(content);
			
			//将这个日记类放到当前用户的数据中去
			user.addDiary(diary);
//			if(user.addDiary(diary)){
//				if(Utility.saveUserToFile()){        //将用户数据保存到文件中
//					
//					Icon icon = new ImageIcon("image/dialog/完成.png");
//					ShowDialog.showMyDialog("保存成功~", "保存成功", JOptionPane.DEFAULT_OPTION, icon);
//				}
//			}
			
			//保存日记类到数据库   保存该日记类与用户的联系到数据库
			if(DatabaseTool.addDiaryToDatabase(diary)){
				//现在需要获取到刚刚插入的日记的编号
				int num = DatabaseTool.getMaxDiaryIdFromDb();
				
				if(num == -1){
					Icon icon = new ImageIcon("image/dialog/错误.png");
					ShowDialog.showMyDialog("保存失败..", "保存失败", JOptionPane.ERROR_MESSAGE, icon);
					return ;
				}
				
				//添加日记与用户的联系到数据库中
				if(DatabaseTool.addContactToDatabase(Utility.currentUser.getId(), num)){
					Icon icon = new ImageIcon("image/dialog/完成.png");
					ShowDialog.showMyDialog("保存成功~", "保存成功", JOptionPane.DEFAULT_OPTION, icon);
				}
			}  
			
		}
		
	}
	
	/**
	 * 取消按钮   监听器
	 */
	class CancelBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			mainFrame.dispose();
			new MainPage().showUI();
		}
		
	}
	
	/**
	 * 窗口监听器  
	 * 监听窗口的关闭,打开等
	 */
	class KeepDiaryWindowListener extends MyWindowListener{
		
		// 因对窗口调用 dispose 而将其关闭时调用。
		@Override
		public void windowClosed(WindowEvent e) {
			super.windowClosed(e);
			JFrameManager.removeJFrame("写日记窗口");
		}
		
		//用户试图从窗口的系统菜单中关闭窗口时调用。
		@Override
		public void windowClosing(WindowEvent e) {
			JFrameManager.removeJFrame("写日记窗口");
		}

		//窗口首次变为可见时调用。
		@Override
		public void windowOpened(WindowEvent e) {
			super.windowOpened(e);
			JFrameManager.addJFrame("写日记窗口", mainFrame);
			diaryTextArea.requestFocus();    //设置文本域获取焦点
		}
		
	}
	
}
