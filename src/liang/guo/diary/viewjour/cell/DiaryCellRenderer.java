package liang.guo.diary.viewjour.cell;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import liang.guo.diary.model.Diary;

/**
 * @author  XFHY
 * @date  2016年12月11日 上午8:40:54
 * @package liang.guo.diary.viewjour.cell
 * @function 查看日记列表界面中的JList的模型    暂时只显示Diary.toString()信息,未排版,日后有时间再美化
 */
public class DiaryCellRenderer extends JPanel implements ListCellRenderer<Diary>{

	private static final long serialVersionUID = -4970675349551914992L;

	/**
	 *  定义绘制单元格时的背景色
	 */
	private Color background;
	/**
	 *  定义绘制单元格时的前景色
	 */
	private Color foreground;
	
	private JLabel diaryText = new JLabel();
	
	/**
	    * This method finds the image and text corresponding to the selected
	    * value and returns the label, set up to display the text and image.
	    */
	@Override
	public Component getListCellRendererComponent(JList<? extends Diary> list, Diary value, int index,
			boolean isSelected, boolean cellHasFocus) {
		/*
		 * 然后然后this就是继承的JPanel了,对它可以设置属性了:
		    setIcon(icon);
		    setText(pet);
		最后把设置好的控件返回就可以了,
		return this;
		当然你也可以设置更复杂的控件,比如继承JButton可以设置成按钮的样式.
		 * */
		background = isSelected ? list.getSelectionBackground() : list.getBackground();
		foreground = isSelected ? list.getSelectionForeground() : list.getForeground();
		
		//this.setBackground(Color.blue);
		//kthis.setForeground(Color.gray);
		
		//System.out.println("背景颜色"+background);
		//System.out.println("前景颜色"+foreground+"\n");
		
		diaryText.setText(value.toString());
		//this.add(diaryText);
		// 返回该JPanel对象作为单元格绘制器
		return this;
	}

	/**
	 *  重写paintComponent方法，改变JPanel的外观
	 */
	public void paintComponent(Graphics g) {
		//int imageWidth = imageIcon.getImage().getWidth(null);
		//int imageHeight = imageIcon.getImage().getHeight(null);
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(foreground);
		
		//绘制图标
		//g.drawImage(imageIcon.getImage(), imageWidth/2, getHeight() / 2 - imageWidth / 2, null);
		
		g.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		//绘制String文字
		g.drawString(diaryText.getText(), 20 , 20);
	}
	
	/**
	 *  通过该方法来设置该ImageCellRenderer的最佳大小
	 */
	public Dimension getPreferredSize() {
		return new Dimension(500, 40);
	}
	
}
