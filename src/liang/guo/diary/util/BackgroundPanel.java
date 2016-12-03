package liang.guo.diary.util;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * @author  XFHY
 * @date  2016年12月1日 下午3:48:35
 * @package liang.guo.diary.util
 * @function 
 */
public class BackgroundPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1294250278874944659L;
	Image backGroundImage;  
    public BackgroundPanel(Image image)  
    {  
        this.backGroundImage=image;  
        this.setOpaque(true);  //设置透明
    }  
    //Draw the back ground.  
    public void paintComponent(Graphics graphics)  
    {  
        super.paintComponents(graphics);  
        //
        graphics.drawImage(backGroundImage,0,0,this.getWidth(),this.getHeight(),this);  
    }  
}
