package liang.guo.diary.util;

import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFrame;

/**
 * @author  XFHY
 * @date  2016年12月5日 上午9:23:07
 * @package liang.guo.diary.util
 * @function JFrame的管理器     所有创建了的JFrame都交到这里来管理
 */
public class JFrameManager {

	/**
	 * 所有的JFrame
	 */
	private static HashMap<String, JFrame> allJFrame = new HashMap<>();
	
	/**
	 * 创建了一个  JFrame的话,需要添加到这里面来
	 * @param name     界面名称
	 * @param jframe   JFrame
	 * @return
	 */
	public static void addJFrame(String name,JFrame jframe){
		allJFrame.put(name,jframe);
	}
	
	/**
	 * 移除一个JFarme   当一个JFrame不需要的时候,需要移除
	 * @param name
	 */
	public static void removeJFrame(String name){
		allJFrame.remove(name);
	}
	
	/**
	 * 销毁所有的JFrame
	 * 当需要退出时,可能会用到
	 */
	public static void destroyAllJFrame(){
		
		Iterator<String> iterator = allJFrame.keySet().iterator();
		while(iterator.hasNext()){
			String name = iterator.next();
			JFrame frame = allJFrame.get(name);
			frame.dispose();
		}
		allJFrame.clear();
	}
	
	/**
	 * 设置该窗口为"模式窗口"
	 * 这里是模拟
	 * @param frameName
	 */
	public static void setModel(String frameName){
		Iterator<String> iterator = allJFrame.keySet().iterator();
		while(iterator.hasNext()){
			String name = iterator.next();
			
			//如果不是该窗口,则设置为不可用setEnabled(false);
			if(!name.equals(frameName)){
				JFrame frame = allJFrame.get(name);
				frame.setEnabled(false);
			}
		}
	}
	
	/**
	 * 恢复那些不可用的窗口可用
	 */
	public static void recoveryModel(){
		Iterator<String> iterator = allJFrame.keySet().iterator();
		while(iterator.hasNext()){
			String name = iterator.next();
			JFrame frame = allJFrame.get(name);
			frame.setEnabled(true);
			frame.requestFocus();
		}
	}
	
}
