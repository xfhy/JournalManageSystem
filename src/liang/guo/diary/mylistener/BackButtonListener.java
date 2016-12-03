package liang.guo.diary.mylistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

/**
 * @author  XFHY
 * @date  2016年12月3日 下午11:07:00
 * @package liang.guo.diary.mylistener
 * @function 专门用户   按钮  返回的  监听器
 */
public class BackButtonListener implements ActionListener {

	private JFrame jframe;
	
	/**
	 * 构造函数
	 */
	public BackButtonListener(JFrame jframe){
		this.jframe = jframe;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		jframe.dispose();   //关闭这个JFrame
	}

}
