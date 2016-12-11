package liang.guo.diary.comparator;

import java.util.Comparator;

import liang.guo.diary.model.Date;
import liang.guo.diary.model.Diary;

/**
 * @author  XFHY
 * @date  2016年11月13日 下午12:39:22
 * @package liang.guo.diary.comparator
 * @function 按日期排序  日记
 */
public class DateComparator implements Comparator<Diary> {

	/**
	 * 比较用来排序的两个参数。
	 * 根据第一个参数小于、等于或大于第二个参数分别返回负整数、零或正整数。
	 * 按照日期排序
	 */
	@Override
	public int compare(Diary diary1, Diary diary2) {
		Date date1 = diary1.getDate();
		Date date2 = diary2.getDate();
		return date2.compareTo(date1);
	}

}
