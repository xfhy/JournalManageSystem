package liang.guo.diary.mylistener;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * @author  XFHY
 * @date  2016年12月10日 上午10:14:36
 * @package liang.guo.diary.mylistener
 * @function 这是可以监听窗口的监听器,比如窗口关闭,打开,正在关闭等等事件
 * 在需要监听某窗口的这些变化时,可以继承自该类,实现部分方法即可
 */
public class MyWindowListener implements WindowListener {
	
	/**
	 * 将 Window 设置为活动 Window 时调用。
	 */
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 *  因对窗口调用 dispose 而将其关闭时调用。
	 */
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * 用户试图从窗口的系统菜单中关闭窗口时调用。
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * 当 Window 不再是活动 Window 时调用。
	 */
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * 窗口从最小化状态变为正常状态时调用。
	 */
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * 窗口从正常状态变为最小化状态时调用。
	 */
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * 窗口首次变为可见时调用。
	 */
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
