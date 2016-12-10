package liang.guo.diary.operation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * @author  XFHY
 * @date  2016年12月7日 下午10:19:43
 * @package liang.guo.diary.operation
 * @function 这是JComBoBox的模型.   适用于天气和心情
 */
public class ComboBoxRender extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1816886291303992766L;

	/**
	 * 图表
	 */
	protected ImageIcon imageIcon;
	/**
	 * 文字
	 */
	protected String name;
	/**
	 *  定义绘制单元格时的背景色
	 */
	protected Color background;
	/**
	 *  定义绘制单元格时的前景色
	 */
	protected Color foreground;
	
	/**
	 *  重写paintComponent方法，改变JPanel的外观
	 */
	public void paintComponent(Graphics g) {
		int imageWidth = imageIcon.getImage().getWidth(null);
		//int imageHeight = imageIcon.getImage().getHeight(null);
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(foreground);
		
		//绘制图标
		//g.drawImage(imageIcon.getImage(), getWidth() / 2 - imageWidth / 2, 10, null);
		g.drawImage(imageIcon.getImage(), imageWidth/2, getHeight() / 2 - imageWidth / 2, null);
		
		g.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		//绘制String文字
		//g.drawString(name, getWidth() / 2 - name.length() * 10, imageHeight + 30);
		g.drawString(name, getWidth() / 2 + name.length() * 5 , getHeight() / 2+5);
	}

	/**
	 *  通过该方法来设置该ImageCellRenderer的最佳大小
	 */
	public Dimension getPreferredSize() {
		return new Dimension(60, 40);
	}
	
}
