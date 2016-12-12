package liang.guo.diary.viewjour;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import liang.guo.diary.comparator.ContentComparator;
import liang.guo.diary.comparator.DateComparator;
import liang.guo.diary.comparator.TitleComparator;
import liang.guo.diary.enumerate.MoodType;
import liang.guo.diary.enumerate.WeatherType;
import liang.guo.diary.main.MainPage;
import liang.guo.diary.model.Date;
import liang.guo.diary.model.Diary;
import liang.guo.diary.mylistener.MyWindowListener;
import liang.guo.diary.operation.edit.EditDiaryWindow;
import liang.guo.diary.util.BackgroundPanel;
import liang.guo.diary.util.JFrameManager;
import liang.guo.diary.util.ShowDialog;
import liang.guo.diary.util.Utility;
import liang.guo.diary.viewjour.cell.DiaryCellRenderer;

/**
 * @author  XFHY
 * @date  2016年12月10日 下午10:48:06
 * @package liang.guo.diary.viewjour
 * @function 查看日记列表
 */
public class ViewJournalWindow extends JFrame {

	private static final long serialVersionUID = -3504004697393272679L;

	private JFrame mainFrame;
	
	//布局管理
	GridBagConstraints gridBagConstraints = new GridBagConstraints();
	GridBagLayout viewDiaryCenterBagLayout = new GridBagLayout();
	
	//前面显示的文字
	JLabel sortModeLabel = new JLabel("排序方式");
	
	/**
	 * 排序方式
	 */
	private final static String[] SORTMODEL = {"日期","标题","内容"};
	/**
	 * 查找方式
	 */
	private final static String[] FINDMODEL = {"按标题查找","按内容查找","按日期查找","按心情查找","按天气查找"};
	
	//下拉选择框
	JComboBox<String> sortModelComboBox = new JComboBox<>(SORTMODEL);
	JComboBox<String> findModelComboBox = new JComboBox<>(FINDMODEL);
	
	//查找的内容输入框
	JTextField findByTextField = new JTextField(10);
	
	//按钮
	JButton findDiaryBtn = new JButton("查找");
	JButton showAllDiaryBtn = new JButton("显示全部");
	JButton seeDiary = new JButton("查看");
	JButton backBtn = new JButton("返回");
	
	/**
	 * 滑动容器   用来放JList   数据是Diary列表
	 */
	JScrollPane jScrollPane;
	
	//中间的日记列表
	Vector<Diary> diaries = new Vector<>();    //原始数据
	Vector<Diary> diariesTemp = new Vector<>();    //中间数据(比如查找之后出现的)
	JList<Diary> diaryList;
	DiaryCellRenderer diaryCellRenderer = new DiaryCellRenderer();
	
	/**
	 * 背景布局
	 */
	BackgroundPanel mainBackGround;
	/**
	 * 窗口的内容窗口
	 */
	private JPanel imagePanel;
	
	/**
	 * 是否查看所有Diary信息
	 */
	private boolean isShowAllDiary = true;
	
	/**
	 * 构造函数
	 */
	public ViewJournalWindow(){
		mainFrame = this;
		//test();
		init();
	}
	
	/////////////测试用
	public void test(){
		Diary diary1 = new Diary();
		diary1.setMood(MoodType.DELIGHTED);
		diary1.setWeather(WeatherType.CLOUDY);
		diary1.setTitle("hhh");
		diary1.setContent("这是内容");
		diary1.setDate(new Date("2012-12-12"));
		
		Diary diary2 = new Diary();
		diary2.setMood(MoodType.DEPRESSED);
		diary2.setWeather(WeatherType.FOG);
		diary2.setTitle("AAA");
		diary2.setContent("这是内容........");
		diary2.setDate(new Date("2001-02-02"));
		
		Diary diary3 = new Diary();
		diary3.setMood(MoodType.EXCITEMENT);
		diary3.setWeather(WeatherType.OVERCAST);
		diary3.setTitle("ccc");
		diary3.setContent("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
		diary3.setDate(new Date("2007-07-06"));
		diaries.add(diary1);
		diaries.add(diary2);
		diaries.add(diary3);
	}
	
	/**
	 * 初始化
	 */
	public void init(){
		diaries.addAll(Utility.currentUser.getOwnDiaries());    //加载  当前用户的日记数据
		
		diaryList = new JList<>(diaries);
		diaryList.setCellRenderer(diaryCellRenderer);
		jScrollPane = new JScrollPane(diaryList);
		jScrollPane.setOpaque(false);
		//设置写日记的区域  是随时可以滑动的
		jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		//这是背景
		mainBackGround = new BackgroundPanel(new ImageIcon("image/viewjour/日记列表背景.jpg").getImage());
		mainBackGround.setBounds(0, 0, 800, 600);
		
		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		imagePanel = (JPanel) this.getContentPane(); // this.getContentPane()的作用是初始化一个容器，用来在容器上添加一些控件
		imagePanel.setOpaque(false); // 是否透明
		
		imagePanel.add(createCenterPanel());    //设置中心布局
		
		// 把背景图片添加到分层窗格的最底层作为背景
		this.getLayeredPane().add(mainBackGround, new Integer(Integer.MIN_VALUE));
		
		//设置下拉框监听器
		sortModelComboBox.addItemListener(new SortModelComboBoxListener());   //排序下拉框
		sortModelComboBox.setSelectedIndex(-1);                               //默认选择-1(没有选择)
		
		//设置按钮监听器
		findDiaryBtn.addActionListener(new findDiaryBtnListener());       //查找按钮
		seeDiary.addActionListener(new SeeDiaryBtnListener());            //查看日记
		showAllDiaryBtn.addActionListener(new ShowAllDiariesListener());  //显示所有日记
		backBtn.addActionListener(new BackBtnListener());                 //返回按钮
		
		//设置JList只能单选
		diaryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		this.addWindowListener(new ViewJournalWindowListener());
		this.setSize(800, 600);    
	}
	
	/**
	 * 显示UI
	 */
	public void showUI(){
		Image icon = Toolkit.getDefaultToolkit().getImage("image/viewjour/查看.png");   
		this.setIconImage(icon);   //设置窗口左上角的小图标
		
		this.setTitle("查看日记列表");
		this.setResizable(false); // 设置窗体大小不可改变
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
            //BeautyEyeLNFHelper.translucencyAtFrameInactive = false;  
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        } catch (Exception e) {  
            System.err.println("set skin fail!");  
        }  
          
    
		new ViewJournalWindow().showUI();
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
		viewDiaryCenterBagLayout.setConstraints(component, gridBagConstraints);
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
		
		//排序方式Label
		gridBagConstraints.insets = insets;
		setComponentStyle(0, 0, 0, 0, 1, 1, sortModeLabel, centerPanel);
		
		//排序方式下拉框选择
		insets.set(10, 10, 0, 0);
		setComponentStyle(1, 0, 0, 0, 1, 1, sortModelComboBox, centerPanel);
		
		//查找方式下拉框选择
		insets.set(10, 50, 0, 0);
		setComponentStyle(2, 0, 0, 0, 1, 1, findModelComboBox, centerPanel);
		
		//查找的内容输入框
		insets.set(10, 10, 0, 0);
		setComponentStyle(3, 0, 0, 0, 1, 1, findByTextField, centerPanel);
		
		//查找按钮
		setComponentStyle(4, 0, 0, 0, GridBagConstraints.REMAINDER, 1, findDiaryBtn, centerPanel);
		
		//中间的日记列表
		setComponentStyle(0, 1, 80, 40, GridBagConstraints.REMAINDER, 1, jScrollPane, centerPanel);
		
		//显示全部  按钮
		setComponentStyle(1, 2, 10, 0, 1, 1, showAllDiaryBtn, centerPanel);
		
		//查看按钮
		setComponentStyle(2, 2, 50, 0, 1, 1, seeDiary, centerPanel);
		
		//返回按钮
		setComponentStyle(3, 2, 10, 0, 1, 1, backBtn, centerPanel);
		
		// 设置布局
		centerPanel.setLayout(viewDiaryCenterBagLayout);
		return centerPanel;
	}
	
	
	/**
	 * 按照日期排序
	 */
	private void sortByDate(){
		if(isShowAllDiary){
			Collections.sort(diaries,new DateComparator());
		} else {
			Collections.sort(diariesTemp,new DateComparator());
		}
	}
	
	/**
	 * 按照标题 排序
	 */
	private void sortByTitle(){
		if(isShowAllDiary){
			Collections.sort(diaries,new TitleComparator());
		} else {
			Collections.sort(diariesTemp,new TitleComparator());
		}
	}
	
	/**
	 * 按照内容  排序
	 */
	private void sortByContent(){
		if(isShowAllDiary){
			Collections.sort(diaries,new ContentComparator());
		} else {
			Collections.sort(diariesTemp,new ContentComparator());
		}
	}
	
	/**
	 * 按标题查找日记
	 */
	private void searchByTitle(){
		diariesTemp.clear();
		String findText = findByTextField.getText().toLowerCase();
		
		for (int i = diaries.size()-1; i >= 0; i--) {   //从原来的集合中找包含用户输入的数据的Diary类
			if(diaries.get(i).getTitle().toLowerCase().contains(findText)){
				diariesTemp.add(diaries.get(i));
			}
		}
		diaryList.setListData(diariesTemp);    //重新设置JList的数据
	}
	
	/**
	 * 按照内容查找日记
	 */
	private void searchByContent(){
		diariesTemp.clear();
		String findText = findByTextField.getText().toLowerCase();
		
		for (int i = diaries.size()-1; i >= 0; i--) {   //从原来的集合中找包含用户输入的数据的Diary类
			if(diaries.get(i).getContent().toLowerCase().contains(findText)){
				diariesTemp.add(diaries.get(i));
			}
		}
		diaryList.setListData(diariesTemp);    //重新设置JList的数据
	}
	
	/**
	 * 按照日期查找日记
	 */
	private void searchByDate(){
		diariesTemp.clear();
		String findText = findByTextField.getText();
		
		for (int i = diaries.size()-1; i >= 0; i--) {   //从原来的集合中找包含用户输入的数据的Diary类
			if(diaries.get(i).getDate().toString().contains(findText)){
				diariesTemp.add(diaries.get(i));
			}
		}
		diaryList.setListData(diariesTemp);    //重新设置JList的数据
	}
	
	/**
	 * 按照心情查找日记
	 */
	private void searchByMood(){
		diariesTemp.clear();
		String findText = findByTextField.getText();
		MoodType moodType = null;
		
		if(findText.equals("高兴")){
			moodType = MoodType.HAPPY;
		} else if(findText.equals("郁闷")){
			moodType = MoodType.DEPRESSED;
		}else if(findText.equals("兴奋")){
			moodType = MoodType.EXCITEMENT;
		} else if(findText.equals("悲伤")){
			moodType = MoodType.SADNESS;
		} else if(findText.equals("恐惧")){
			moodType = MoodType.FEAR;
		} else if(findText.equals("欣喜")){
			moodType = MoodType.DELIGHTED;
		} else {
			ShowDialog.showMyDialog("没有这种心情设定哦~", "未发现这种心情", 
					JOptionPane.WARNING_MESSAGE);
			return ;
		}
		
		for (int i = diaries.size()-1; i >= 0; i--) {   //从原来的集合中找包含用户输入的数据的Diary类
			if(diaries.get(i).getMood() == moodType){
				diariesTemp.add(diaries.get(i));
			}
		}
		diaryList.setListData(diariesTemp);    //重新设置JList的数据
	}
	
	/**
	 * 按照天气查找日记
	 */
	private void searchByWeather(){
		diariesTemp.clear();
		String findText = findByTextField.getText();
		WeatherType weatherType = null;
		
		if(findText.equals("晴")){
			weatherType = WeatherType.SUNNY;
		} else if(findText.equals("阴天")){
			weatherType = WeatherType.OVERCAST;
		}else if(findText.equals("多云")){
			weatherType = WeatherType.CLOUDY;
		} else if(findText.equals("雨")){
			weatherType = WeatherType.RAIN;
		} else if(findText.equals("雾")){
			weatherType = WeatherType.FOG;
		} else if(findText.equals("雪")){
			weatherType = WeatherType.SNOW;
		} else {
			ShowDialog.showMyDialog("没有这种天气设定哦~", "未发现这种天气", 
					JOptionPane.WARNING_MESSAGE);
			return ;
		}
		
		for (int i = diaries.size()-1; i >= 0; i--) {   //从原来的集合中找包含用户输入的数据的Diary类
			if(diaries.get(i).getWeather() == weatherType){
				diariesTemp.add(diaries.get(i));
			}
		}
		diaryList.setListData(diariesTemp);    //重新设置JList的数据
	}
	
	/**
	 * 排序方式下拉列表 监听器     当所选项更改时，aListener 将接收一个或两个 ItemEvent
	 */
	class SortModelComboBoxListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent event) {
			
			if(event.getStateChange() == ItemEvent.SELECTED)
            {
                switch(sortModelComboBox.getSelectedIndex()){
                case 0:
                	sortByDate();
                	break;
                case 1:
                	sortByTitle();
                	break;
                case 2:
                	sortByContent();
                	break;
                default:
                		break;
                }
                
                diaryList.updateUI();     //更新JList UI
            }
		}
	}
	
	/**
	 * 查找日记按钮  监听器
	 */
	class findDiaryBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			//判断用户是否输入
			String inputFindText = findByTextField.getText();
			if(inputFindText.length() == 0){
				ShowDialog.showMyDialog("请输入查找内容再进行查找", "未输入查找内容", 
						JOptionPane.WARNING_MESSAGE);
				return ;
			}
			
			isShowAllDiary = false;   //将是否查看所有日记类    这个标记   设为false    
			switch (findModelComboBox.getSelectedIndex()) {
			case 0:
				searchByTitle();
				break;
			case 1:
				searchByContent();
				break;
			case 2:
				searchByDate();
				break;
			case 3:
				searchByMood();
				break;
			case 4:
				searchByWeather();
				break;
			default:
				break;
			}
			diaryList.updateUI();     //更新UI
		}
		
	}
	
	/**
	 * 查看当前选中日记  按钮  监听器
	 * @author XFHY
	 *
	 */
	class SeeDiaryBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(diaryList.getSelectedIndex() < 0){
				return ;
			}
			Diary diary = diaryList.getSelectedValue();
			//System.out.println(diary.toString());
			new EditDiaryWindow(diary).showUI();
			mainFrame.dispose();
		}
		
	}
	
	/**
	 * 显示所有日记列表  按钮  监听器
	 */
	class ShowAllDiariesListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			isShowAllDiary = true;             //将是否查看所有日记类    这个标记   设为true
			diaryList.setListData(diaries);    //重新设置JList的数据
			diaryList.updateUI();              //更新UI
		}
		
	}
	
	/**
	 * 返回按钮  监听器
	 * @author XFHY
	 *
	 */
	class BackBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			new MainPage().showUI();    //返回    主界面
			mainFrame.dispose();
		}
		
	}
	
	/**
	 * 窗口监听器  
	 * 监听窗口的关闭,打开等
	 */
	class ViewJournalWindowListener extends MyWindowListener{
		
		// 因对窗口调用 dispose 而将其关闭时调用。
		@Override
		public void windowClosed(WindowEvent e) {
			super.windowClosed(e);
			JFrameManager.removeJFrame("查看日记列表窗口");
		}
		
		//用户试图从窗口的系统菜单中关闭窗口时调用。
		@Override
		public void windowClosing(WindowEvent e) {
			JFrameManager.removeJFrame("查看日记列表窗口");
		}

		//窗口首次变为可见时调用。
		@Override
		public void windowOpened(WindowEvent e) {
			super.windowOpened(e);
			JFrameManager.addJFrame("查看日记列表窗口", mainFrame);
		}
		
	}
	
}
