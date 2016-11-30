package liang.guo.diary.operation;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import liang.guo.diary.comparator.DateComparator;
import liang.guo.diary.model.Diary;
import liang.guo.diary.util.Utility;

/**
 * @author  XFHY
 * @date  2016年11月13日 下午12:56:52
 * @package liang.guo.diary.operation
 * @function   查看日记
 */
public class SeeDiary {
	
	private int diarySerialNumber = 0;
	
	/**
	 * 查看日记列表  按日期排序列出所有日记的标题和日期
	 */
	public void viewJournalsListByDate(){
		Set<Diary> diarySet = new TreeSet<>(new DateComparator());
		diarySet.addAll(Utility.currentUser.getOwnDiaries());   //将当前用户所有的日记添加到treeSet中,(按照日期排序)
		
		Utility.currentUser.getOwnDiaries().clear();
		Utility.currentUser.getOwnDiaries().addAll(diarySet);
		
		//列出所有日记的标题和日期
		Iterator<Diary> it = diarySet.iterator();
		while(it.hasNext()){
			Diary diary = it.next();
			String title = diary.getTitle();
			String date = diary.getDate().getDate();
			System.out.println((diarySerialNumber+1)+". "+date+"    ----------->      "+title);
			diarySerialNumber++;
		}
		diarySerialNumber--;    //上面最后那里,多加了一次,so这里要减掉
	}
	
	/**
	 * 请用户选择要查看的日记
	 * 当用户选择日记的序号后，输出该日记的内容。
	 */
	public void showDiaryByIndex(){
		System.out.println("请输入需要查看的日记的序号(1~"+(diarySerialNumber+1)+")");
		int userSelection = Utility.judgmentInput(diarySerialNumber+1);
		try {
			System.out.println(
					Utility.currentUser.getOwnDiaries().get(userSelection-1).toString());
		} catch (Exception e) {
			System.err.println("用户选择日记的序号后,数组下标越界");
			e.printStackTrace();
		}
	}
	
}
