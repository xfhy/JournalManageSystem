package liang.guo.diary.comparator;

import java.text.CollationKey;
import java.text.Collator;
import java.util.Comparator;

import liang.guo.diary.model.Diary;

/**
 * @author  XFHY
 * @date  2016年12月11日 下午1:09:26
 * @package liang.guo.diary.comparator
 * @function 日记类的  比较器  按照标题排序
 */
public class TitleComparator implements Comparator<Diary>{

	//关于Collator。
	private Collator collator = Collator.getInstance();//点击查看中文api详解
	
	@Override
	public int compare(Diary diary1, Diary diary2) {
		
		//把字符串转换为一系列比特，它们可以以比特形式与 CollationKeys 相比较
		CollationKey key1=collator.getCollationKey(diary1.getTitle().toLowerCase());//要想不区分大小写进行比较用o1.toString().toLowerCase()
		CollationKey key2=collator.getCollationKey(diary2.getTitle().toLowerCase());
		return key1.compareTo(key2);//返回的分别为1,0,-1 分别代表大于，等于，小于。要想按照字母降序排序的话 加个“-”号
	}
	
}
