package liang.guo.diary.operation;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import liang.guo.diary.enumerate.MoodType;
import liang.guo.diary.enumerate.WeatherType;
import liang.guo.diary.model.Date;
import liang.guo.diary.model.Diary;
import liang.guo.diary.util.Utility;

/**
 * 用来写日记
 * 2016年10月18日 下午8:43:34
 * @author XFHY
 *
 */
public class KeepDiary {
	
	/**
	 * 开始写日记
	 */
	public void writeDiary(String userName,List<Diary> diaryList){
		WeatherType weather;  //天气
		MoodType mood;        //心情
		Date date;            //日期
		String title;         //标题
		String content;       //日记内容
		
		//先让用户选择天气和当时的心情
		weather = selectWeather();  
		mood = selectMood();
		date = new Date(inputDate());  //输入日期
		title = inputTitle();   //输入标题
		content = inputContent(); //输入日记内容
		
		Diary diary = new Diary();
		diary.setWeather(weather);
		diary.setMood(mood);
		diary.setDate(date);
		diary.setTitle(title);
		diary.setContent(content);
		
		if(diaryList.add(diary)){
			System.out.println("日记已记录成功!");
			System.out.println(diary.toString());
		}
	}
	
	
	/**
	 * 显示所有天气类型
	 */
	private void showAllWeatherType() {
		/*
		 * 晴 SUNNY, 阴天 OVERCAST, 多云 CLOUDY, 雨 RAIN, 雾 FOG, 雪 SNOW;
		 */
		System.out.println("1.晴 SUNNY");
		System.out.println("2.阴天 OVERCAST");
		System.out.println("3.多云 CLOUDY");
		System.out.println("4.雨 RAIN");
		System.out.println("5.雾 FOG");
		System.out.println("6.雪 SNOW");
	}
	
	
	/**
	 * 选择天气
	 * @return
	 */
	private WeatherType selectWeather(){
		showAllWeatherType();
		System.out.println("请选择今天的天气类型:");
		int choose = Utility.judgmentInput(6);      //让用户输入1~6的数字
		switch (choose) {
		case 1:
			return WeatherType.SUNNY;
		case 2:
			return WeatherType.OVERCAST;
		case 3:
			return WeatherType.CLOUDY;
		case 4:
			return WeatherType.RAIN;
		case 5:
			return WeatherType.FOG;
		case 6:
			return WeatherType.SNOW;
		default:
			System.err.println("选择错误,没有这个选项~");
			break;
		}
		return WeatherType.SUNNY;   //默认是晴天
	}
	
	
	/**
	 * 显示所有的心情类型
	 */
	private void showAllMoodType() {
		/*
		 * 高兴 HAPPY, 郁闷 DEPRESSED, 兴奋 EXCITEMENT, 悲伤 SADNESS, 恐惧 FEAR, 欣喜 DELIGHTED
		 */
		System.out.println("1.高兴 HAPPY");
		System.out.println("2.郁闷 DEPRESSED");
		System.out.println("3.兴奋 EXCITEMENT");
		System.out.println("4.悲伤 SADNESS");
		System.out.println("5.恐惧 FEAR");
		System.out.println("6.欣喜 DELIGHTED");
	}

	
	/**
	 * 选择心情
	 * @return
	 */
	private MoodType selectMood(){
		showAllMoodType();
		System.out.println("请选择您的心情:");
		int choose = Utility.judgmentInput(6);
		switch (choose) {
		case 1:
			return MoodType.HAPPY;
		case 2:
			return MoodType.DEPRESSED;
		case 3:
			return MoodType.EXCITEMENT;
		case 4:
			return MoodType.SADNESS;
		case 5:
			return MoodType.FEAR;
		case 6:
			return MoodType.DELIGHTED;
		default:
			System.err.println("亲,没有这个选项~");
			break;
		}
		return MoodType.HAPPY;     //默认是高兴
	}
	
	
	/**
	 * 让用户输入日期
	 * @return
	 */
	@SuppressWarnings("resource")
	private String inputDate(){
		System.out.println("请输入日期(格式为dddd-dd-dd eg: 1991-06-08):");
		Scanner inputDateScanner = new Scanner(System.in);
		String dateValue = "";
		boolean inputFlag = true;
		while(inputFlag){
			try{
				dateValue = inputDateScanner.nextLine();
				if(Date.inputIsDate(dateValue)){  //判断输入的日期是否正确
					inputFlag = false;
				} else {
					System.out.println("输入不合法,请输入正确的日期,请重新输入:");
					inputDateScanner = new Scanner(System.in);
				}
			} catch (NoSuchElementException e){
				System.err.println(e.getMessage());
				System.err.println("没有输入哦,请重新输入");
				inputDateScanner = new Scanner(System.in);
				
			} catch (Exception e2){
				e2.printStackTrace();
				System.err.println("输入时发生异常,请重新输入");
				inputDateScanner = new Scanner(System.in);
			}
		}
		return dateValue;
	}
	
	
	/**
	 * 让用户输入标题
	 * @return
	 */
	@SuppressWarnings("resource")
	private String inputTitle(){
		System.out.println("请输入标题(12字以内):");
		Scanner inputTitleScanner = new Scanner(System.in);
		String title = "";
		boolean inputFalg = true;
		while(inputFalg){
			try{
				title = inputTitleScanner.nextLine();
				if(title.length() <= 12 && title.length() > 0){
					inputFalg = false;
				} else {
					System.out.println("输入不合法,标题长度超过12字或为0,请重新输入:");
					inputTitleScanner = new Scanner(System.in);
				}
			} catch (NoSuchElementException e){
				System.err.println(e.getMessage());
				System.err.println("没有输入哦,请重新输入");
				inputTitleScanner = new Scanner(System.in);
				
			} catch (Exception e2){
				e2.printStackTrace();
				System.err.println("输入时发生异常,请重新输入");
				inputTitleScanner = new Scanner(System.in);
			}
		}
		return title;
	}
	
	
	/**
	 * 让用户输入日记内容
	 * @return
	 */
	@SuppressWarnings("resource")
	private String inputContent(){
		System.out.println("请输入日记内容:");
		Scanner inputContentScanner = new Scanner(System.in);
		String content = inputContentScanner.nextLine();
		return content;
	}
	
	/**
	 * 根据传入的用户名,将该用户的日记插入到集合中
	 * @return  插入是否成功
	 */
	@SuppressWarnings("unused")
	private boolean introduceAggregate(String userName,Diary diary){
		boolean successFlag = false;
		
		return successFlag;
	}
	
	
}
