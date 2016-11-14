package liang.guo.diary.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 日期类
 * 2016年10月18日 下午9:17:33
 * @author XFHY
 * 
 * 用来表示日期
 * 
 */
public class Date implements Comparable<Date>,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1635986082344938512L;
	/*
	 *该类应该包含一个通过字符串构造的日期函数Date(String date)，
            还应该包含一个 static 方法用来判断用户输入的日期是否正确，在构造日期之前先用该方法判断日期是否合法 
	 */
	private String date;
    
	public Date(String date){
		this.date = date;
	}
	
	/**
	 * 判断用户输入的日期是否正确
	 * @return  正确返回true,否则返回false
	 */
	public static boolean inputIsDate(String dateValue){
		String regexDate = "\\d{4}+[-]\\d{1,2}+[-]\\d{1,2}+";
		
		if(!dateValue.matches(regexDate)){
			return false;
		}
		
		//根据-号分隔年月日  判断月份是否1~12,再将每个月的天数存下来,再判断当前年份是否为闰年,闰年则2月为29天,平年28天
		String array[] = dateValue.split("-");
		
		int year = -1;
		int month = -1;
		int day = -1;
		try {
			year = Integer.valueOf(array[0]);
			month = Integer.valueOf(array[1]);
			day = Integer.valueOf(array[2]);
		} catch (NumberFormatException  e) {
			System.err.println(e.getMessage());
			System.err.println("将日期Date类的年月日(String)获取时,获取到String->Integer转换异常");
			return false;
		}
		
		if(month<1 || month>12){
			return false;
		}
		int[] monthLengths = new int[]{0, 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		if(isLeapYear(year)){
			monthLengths[2] = 29;
		} else {
			monthLengths[2] = 28;
		}
		
		//判断日期是否大于当月日期最大值,是否小于当月日期最小值
		int monthDays = monthLengths[month];
		if(monthDays<1 || day>monthDays){
			return false;
		}
		
		return true;
	}
	
	/**
	 * 判断是否是闰年
	 * @param year
	 * @return
	 */
	private static boolean isLeapYear(int year){
		return ( (year%4==0 && year%100!=0) || year%400==0 );
	}
	
	@Override
	public String toString() {
		return date;
	}

	/**
	 * 返回日期信息
	 * @return
	 */
	public String getDate() {
		return date;
	}

	/**
	 * 可以比较的   日期
	 * 比较此对象与指定对象的顺序。如果该对象小于、等于或大于指定对象，则分别返回负整数、零或正整数。 
	 * 还有个方法叫before():测试此日期是否在指定日期之前,但是在这里不适用,因为时间可能相等.
	 */
	@Override
	public int compareTo(Date when) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try{
			java.util.Date dt1 = df.parse(date);
			java.util.Date dt2 = df.parse(when.getDate());
			
			//比较时间
			if(dt1.getTime() < dt2.getTime()){  //返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。 
				return -1;
			} else if(dt1.getTime() > dt2.getTime()){
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e){
			System.err.println("日期比较时出错");
			e.printStackTrace();
		}
		return 0;
	}
	
}
