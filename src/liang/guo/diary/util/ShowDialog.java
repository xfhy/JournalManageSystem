package liang.guo.diary.util;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * @author  XFHY
 * @date  2016年12月3日 下午2:35:23
 * @package liang.guo.diary.util
 * @function 专门用来显示对话框的
 */
public class ShowDialog {
	/*
	 * JOptionPane() 
          创建一个带有测试消息的 JOptionPane。 
JOptionPane(Object message) 
          创建一个显示消息的 JOptionPane 的实例，使其使用 UI 提供的普通消息消息类型和默认选项。 
JOptionPane(Object message, int messageType) 
          创建一个显示消息的 JOptionPane 的实例，使其具有指定的消息类型和默认选项。 
JOptionPane(Object message, int messageType, int optionType) 
          创建一个显示消息的 JOptionPane 的实例，使其具有指定的消息类型和选项。 
JOptionPane(Object message, int messageType, int optionType, Icon icon) 
          创建一个显示消息的 JOptionPane 的实例，使其具有指定的消息类型、选项和图标。 
JOptionPane(Object message, int messageType, int optionType, Icon icon, Object[] options) 
          创建一个显示消息的 JOptionPane 的实例，使其具有指定的消息类型、图标和选项。 
JOptionPane(Object message, int messageType, int optionType, Icon icon, Object[] options, Object initialValue) 
          在指定最初选择的选项的前提下，创建一个显示消息的 JOptionPane 的实例，使其具有指定的消息类型、图标和选项。 
	 * */
	
	/**
	 * 创建一个显示消息的 JOptionPane 的实例，使其具有指定的消息类型、选项和系统自定义的图标。
	 * @param message
	 * @param messageType
	 */
	public static void showMyDialog(final Object message, final String title,final int messageType){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Icon icon = null;
				if(messageType == JOptionPane.INFORMATION_MESSAGE){
					icon = new ImageIcon("image/dialog/消息.png");
				} else if(messageType == JOptionPane.WARNING_MESSAGE){
					icon = new ImageIcon("image/dialog/警告.png");
				} else if(messageType == JOptionPane.ERROR_MESSAGE){
					icon = new ImageIcon("image/dialog/错误.png");
				} 
				JOptionPane.showMessageDialog(null, message.toString(),
						title, messageType, icon);
			}
		}).start();
		
	}
	
	/**
	 * 创建一个显示消息的 JOptionPane 的实例，使其具有指定的消息类型、选项和系统自定义的图标。
	 * @param message
	 * @param messageType
	 */
	public static void showMyDialog(final Object message, final String title,
			final int messageType,final Icon icon){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				JOptionPane.showMessageDialog(null, message.toString(),
						title, messageType, icon);
			}
		}).start();
		
	}
	
}
