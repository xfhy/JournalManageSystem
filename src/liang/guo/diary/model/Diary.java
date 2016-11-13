package liang.guo.diary.model;

import liang.guo.diary.enumerate.MoodType;
import liang.guo.diary.enumerate.WeatherType;

/**
 * 用来保存用户日记的所有信息 
 * 2016年10月18日 下午9:13:33
 * @author XFHY
 * 
 *  Diary 类应该至少包含这些属性：日期、天气、心情、标题和内容
 * 
 */
public class Diary {
	/**
	 * 日期
	 */
	private Date date;
	/**
	 * 天气
	 */
	private WeatherType weather;
	/**
	 * 心情
	 */
	private MoodType mood;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 日记内容
	 */
	private String content;
	
	/**
	 * 返回日期对象
	 * @return
	 */
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) throws NullPointerException{
		if(date != null){
			this.date = date;
		} else {
			System.err.println("设置日记日期时,Date对象为null");
			throw new NullPointerException("Date类为null");
		}
	}

	public WeatherType getWeather() {
		return weather;
	}
	
	public void setWeather(WeatherType weather) {
		this.weather = weather;
	}
	
	public MoodType getMood() {
		return mood;
	}
	
	public void setMood(MoodType mood) {
		this.mood = mood;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) throws IllegalArgumentException {
		if(title.length() <= 12){
			this.title = title;
		} else {
			throw new IllegalArgumentException("标题必须是12字以内");
		}
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * 输出Diary对象内容
	 */
	@Override
	public String toString() {
		StringBuffer diaryInfo = new StringBuffer();
		diaryInfo.append("日期:"+date.toString()+"\n");
		
		diaryInfo.append("天气:");
		switch (weather) {
		case SUNNY:
			diaryInfo.append("晴");
			break;
		case OVERCAST:
			diaryInfo.append("阴天");
			break;
		case CLOUDY:
			diaryInfo.append("多云");
			break;
		case RAIN:
			diaryInfo.append("雨");
			break;
		case FOG:
			diaryInfo.append("雾");
			break;
		case SNOW:
			diaryInfo.append("雪");
			break;
		default:
			diaryInfo.append("null");
			break;
		}
		diaryInfo.append("\n");
		
		diaryInfo.append("心情:");
		switch(mood){
		case HAPPY:
			diaryInfo.append("高兴");
			break;
		case DEPRESSED:
			diaryInfo.append("郁闷");
			break;
		case EXCITEMENT:
			diaryInfo.append("兴奋");
			break;
		case SADNESS:
			diaryInfo.append("悲伤");
			break;
		case FEAR:
			diaryInfo.append("恐惧");
			break;
		case DELIGHTED:
			diaryInfo.append("欣喜");
			break;
		default:
			diaryInfo.append("null");
			break;
		}
		diaryInfo.append("\n");
		
		diaryInfo.append("标题:"+title+"\n");
		diaryInfo.append("内容:"+content+"\n");
		
		return diaryInfo.toString();
	}
	
}
