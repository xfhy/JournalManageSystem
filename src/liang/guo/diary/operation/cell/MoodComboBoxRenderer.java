package liang.guo.diary.operation.cell;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import liang.guo.diary.enumerate.MoodType;

/**
 * @author  XFHY
 * @date  2016年12月7日 下午10:15:06
 * @package liang.guo.diary.operation
 * @function 心情下拉框的模型  图片+文字
 */
public class MoodComboBoxRenderer extends ComboBoxRender implements ListCellRenderer<MoodType>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4684985955193536858L;

	@Override
	public Component getListCellRendererComponent(JList<? extends MoodType> list, MoodType value, int index,
			boolean isSelected, boolean cellHasFocus) {
		switch (value) {
		case HAPPY:
			name = "高兴";
			break;
		case DEPRESSED:
			name = "郁闷";
			break;
		case EXCITEMENT:
			name = "兴奋";
			break;
		case SADNESS:
			name = "悲伤";
			break;
		case FEAR:
			name = "恐惧";
			break;
		case DELIGHTED:
			name = "欣喜";
			break;
		default:
			name = "高兴";
			break;
		}
		
		imageIcon = new ImageIcon("image/mood/" + name + ".png");
		
		background = isSelected ? list.getSelectionBackground() : list.getBackground();
		foreground = isSelected ? list.getSelectionForeground() : list.getForeground();
		// 返回该JPanel对象作为单元格绘制器
		return this;
	}

}
