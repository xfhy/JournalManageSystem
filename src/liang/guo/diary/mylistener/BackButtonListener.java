package liang.guo.diary.mylistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import liang.guo.diary.util.JFrameManager;

/**
 * @author  XFHY
 * @date  2016年12月3日 下午11:07:00
 * @package liang.guo.diary.mylistener
 * @function 专门用户   按钮  返回的  监听器
 */
public class BackButtonListener implements ActionListener {

	/**
	 * 界面名称
	 */
	private String frameName;
	/**
	 * 需要关闭的那个JFrame
	 */
	private JFrame jframe;
	
	/**
	 * 构造函数
	 */
	public BackButtonListener(String frameName,JFrame jframe){
		this.frameName = frameName;
		this.jframe = jframe;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFrameManager.removeJFrame(frameName);   //移除这个JFrame
		System.out.println(frameName+"已被销毁");
		jframe.dispose();   //关闭这个JFrame
		JFrameManager.recoveryModel();   //恢复所有窗口可用
	}

}
