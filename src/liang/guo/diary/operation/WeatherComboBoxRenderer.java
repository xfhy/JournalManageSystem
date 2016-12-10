package liang.guo.diary.operation;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import liang.guo.diary.enumerate.WeatherType;

/**
 * @author  XFHY
 * @date  2016年12月6日 下午10:24:37
 * @package liang.guo.diary.operation
 * @function 天气下拉框的模型   图片+文字
 */
public class WeatherComboBoxRenderer extends ComboBoxRender implements ListCellRenderer<WeatherType>{

	private static final long serialVersionUID = -6892520412287660352L;
	
	/**
	    * This method finds the image and text corresponding to the selected
	    * value and returns the label, set up to display the text and image.
	    */
	@Override
	public Component getListCellRendererComponent(JList<? extends WeatherType> list, WeatherType value, int index,
			boolean isSelected, boolean cellHasFocus) {
		/*
		 * 然后然后this就是继承的JLabel了,对它可以设置属性了:
		    setIcon(icon);
		    setText(pet);
		最后把设置好的控件返回就可以了,
		return this;
		当然你也可以设置更复杂的控件,比如继承JButton可以设置成按钮的样式.
		 * */
		
		
		switch (value) {
		case SUNNY:
			name = "晴";
			break;
		case OVERCAST:
			name = "阴天";
			break;
		case CLOUDY:
			name = "多云";
			break;
		case RAIN:
			name = "雨";
			break;
		case FOG:
			name = "雾";
			break;
		case SNOW:
			name = "雪";
			break;
		default:
			name = "晴";
			break;
		}
		
		imageIcon = new ImageIcon("image/weather/" + name + ".png");
		
		background = isSelected ? list.getSelectionBackground() : list.getBackground();
		foreground = isSelected ? list.getSelectionForeground() : list.getForeground();
		// 返回该JPanel对象作为单元格绘制器
		return this;
	}

	
	
}
