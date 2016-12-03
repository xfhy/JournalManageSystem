package liang.guo.diary.register;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author  XFHY
 * @date  2016年12月3日 上午10:12:50
 * @package liang.guo.diary.register
 * @function 验证码生成
 */
public class Verification {
/*验证码的框*/
    // 图像长度
    private int width = 100;
    // 图像宽度
    private int height = 40;
    // 验证码的长度
    private int number = 6;
  
    private String numberRandom = "123456789";
    private String operator = "+-*/"; 
    
    //验证码结果
    private int sum = -9999;
    private char[] operators = new char[2];   //操作符
    private int number_x; //那3个数
    private int number_y;
    private int number_z;
    
    /**
    * 获取验证码图像
    * 
    * @return 验证码图像
    */
    public BufferedImage getImage() {
        /*BufferedImage 子类描述具有可访问图像数据缓冲区的 Image。
        * BufferedImage(int width, int height, int imageType)构造一个类型为预定义图像类型之一的 BufferedImage。
        * 
        * */
        // 创建图像缓冲区
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        // 获取画笔
        /*public Graphics getGraphics()*/
        Graphics g = image.getGraphics();
        
        // 设置图像背景色，填充背景矩形
        /*public abstract void setColor(Color c)*/
        
        
        g.setColor(getRandomColor(200, 255));//???        
        /*public abstract void fillRect(int x,int y,int width,int height)*/
        g.fillRect(0, 0, width, height);

        // 画边框
        g.setColor(Color.blue);
        g.drawRect(0, 0, width - 1, height - 1);

        /* 生成随机验证码 */
        // 设置验证码字体 Font(String name, int style, int size)
        // HANGING_BASELINE 布置文本时，在 Devanigiri 和类似脚本中使用的基线。
        g.setFont(new Font("楷体", Font.HANGING_BASELINE, 20));

        // 循环生成验证码各字符
        Random random = new Random();
        for (int i = 1; i <= number; i++) {
        	String randStr = "";
        	if(i<6){
        		//奇数,则生成数字
            	if(i%2 != 0){
        		   randStr = String.valueOf(numberRandom.charAt(random.nextInt(numberRandom.length())));
            	} else {   //偶数,则生成运算符
            		randStr = String.valueOf(operator.charAt(random.nextInt(operator.length())));
            	}
        	} else {
        		randStr = "=";
        	}
        	
        	switch(i){
        	case 1:
        		number_x = Integer.parseInt(randStr);
        		break;
        	case 2:
        		operators[0] = randStr.charAt(0);
        		break;
        	case 3:
        		number_y = Integer.parseInt(randStr);
        		break;
        	case 4:
        		operators[1] = randStr.charAt(0);
        		break;
        	case 5:
        		number_z = Integer.parseInt(randStr);
        		break;
        	case 6:
        		sum = getResults(number_x,number_y,number_z,operators);
        		break;
        	}
        	
			 // 随机生成验证码中单个字符/*public int nextInt(int n)返回一个伪随机数，它是取自此随机数生成器序列的、在 0（包括）和指定值（不包括）之间均匀分布的 int 值*/
            // 单个字符绘制宽度
            int width = this.width / this.number;
            // 当前字符绘制原点  ????
            int x = width * (i-1);
            int y = this.height / 2 + random.nextInt(this.height / 5);
            /* 将该字符画到图像中 */// ???
            drawString(g, x, y, randStr);
		}

        // 画干扰线
        drawLine(g, 10);

        // 释放画笔
        g.dispose();
        return image;

    }

    /**
    * 画验证码字符串单个字符
    * 
    * @param g
    *            图像上下文
    * @param x
    *            字符 所占宽度
    * @param y
    *            字符所占高度
    * @param randStr
    *            待绘制字符串
    * 
    */
    private void drawString(Graphics g,int width,int height,String randStr){
    //private void drawString(Graphics g, int x, int y, String randStr) {
        Random rand = new Random();
        // 随机生成字符旋转（-15-15度）/*/*public int nextInt(int n)返回一个伪随机数，它是取自此随机数生成器序列的、在 0（包括）和指定值（不包括）之间均匀分布的 int 值*/*/
        int degree = rand.nextInt(30);
        if (degree > 15) {
            degree = 15-degree;
        }
        // 设置字体颜色
        g.setColor(getRandomColor(0, 50));
        // 转换Graphics2D
        Graphics2D g2 = (Graphics2D) g.create();
        // 平移原点到图形环境的中心，这个方法的作用实际上就是将字符串移到某一位置/*public abstract void translate(int x,int y)将 Graphics2D 上下文的原点平移到当前坐标系中的点 (x, y)。*/
       // g2.translate(width + rand.nextInt(10), height + rand.nextInt(10));
        g2.translate(width, height );
        // 旋转文本  ( 单位是弧度)
        g2.rotate(degree * Math.PI / 180);
        // 画文本，特别需要注意的是，这里的笔画已经具有上次指定的一个位置，所以这里指定的位置是一个相对的位置
        g2.drawString(randStr, 0, 0);
    }

    /**
    * 
    * 画 随机干扰线
    * 
    * @param g
    *            图形上下文（画笔）
    * 
    * @param count
    *            干扰线条数
    */
    private void drawLine(Graphics g,int count){
    
        Random random = new Random();
        // 循环绘制每条干扰线
        for (int j = 0; j < count; j++) {
            // 设置线条随机颜色
            g.setColor(getRandomColor(180, 200));
            
            // 生成随机线条起点终点，坐标点
            int x1 = random.nextInt(this.width);
            int y1 = random.nextInt(this.height);
            int x2 = random.nextInt(this.width);
            int y2 = random.nextInt(this.height);
            // 画线条
            g.drawLine(x1, y1, x2, y2);
        }
    }

    /**
    * 获取随机颜色
    * 
    * @param i
    *            颜色下限值
    * @param j
    *            颜色上限值
    * @return 随机颜色对象
    */
    private Color getRandomColor(int i, int j) {
        if (i > j) {
            int tmp = i;
            i = j;
            j = tmp;
        }
        if (j > 225) {
            j = 225;
        }
        if (i < 0) {
            i = 0;
        }
        int r = i + (int) (Math.random() * (j - i));
        int g = i + (int) (Math.random() * (j - i));
        int b = i + (int) (Math.random() * (j - i));

        return new Color(r, g, b);
        
//        values in the range (0 - 255). red green blue
    }

    /**
	 * 算出四则运算的结果
	 * 参数:待运算的3个数,2个运算符(数组里)
	 * @return 正确结果
	 */
	private int getResults(int number_x,int number_y,int number_z,char operator[]){
		
		/*
		 * 从左向右依次读取符号，如果第i个符号为加减，判断第i+1个，如果为加减，则直接进行运算，
		 * 否则用temp将乘除法进行完全，再加入sum。如果第i个为乘除法，说明这是第一个符号（因为运算逻辑是将乘除法进行完全，
		 * 即下一个符号为加减号），那么将temp值直接赋值给sum，然后再进行运算。
		 * */
		
		int sum = 0;
		switch(operator[0]){
		case '+':
			switch(operator[1]){
			case '+':
				sum = number_x + number_y + number_z;
				break;
			case '-':
				sum = number_x + number_y - number_z;
				break;
			case '*':
				sum = number_x + number_y * number_z;
				break;
			case '/':
				sum = number_x + number_y / number_z;
				break;
			}
			break;
		case '-':
			switch(operator[1]){
			case '+':
				sum = number_x - number_y + number_z;
				break;
			case '-':
				sum = number_x - number_y - number_z;
				break;
			case '*':
				sum = number_x - number_y * number_z;
				break;
			case '/':
				sum = number_x - number_y / number_z;
				break;
			}
			break;
		case '*':
			switch(operator[1]){
			case '+':
				sum = number_x * number_y + number_z;
				break;
			case '-':
				sum = number_x * number_y - number_z;
				break;
			case '*':
				sum = number_x * number_y * number_z;
				break;
			case '/':
				sum = number_x * number_y / number_z;
				break;
			}
			break;
		case '/':
			switch(operator[1]){
			case '+':
				sum = number_x / number_y + number_z;
				break;
			case '-':
				sum = number_x / number_y - number_z;
				break;
			case '*':
				sum = number_x / number_y * number_z;
				break;
			case '/':
				sum = number_x / number_y / number_z;
				break;
			}
			break;
		}
		
		return sum;
	}

	
	public int getSum() {
		return sum;
	}
    
	
	
}
