import javax.swing.*;
import java.io.*;

import javax.swing.filechooser.FileFilter;

//繼承JFileChooser類別定義選取音效檔的檔案選取器
class MediaFileChooser extends JFileChooser {

	public MediaFileChooser(String path){ //建構子
		super(path);
		//呼叫基礎類別的建構子並傳入欲瀏覽檔案的路徑
	}

	//傳入欲瀏覽檔案之路徑與欲篩選檔案的副檔名與描述字串
	public MediaFileChooser(String path, String[][] strExt){
		super(path); //將欲瀏覽路徑傳入基礎類別的建構子
		addChoosableFileFilter(strExt);
		//加入過濾可選取檔案之檔案篩選物件
	}

	//加入檔案篩選物件
	public void addChoosableFileFilter(String [][] strExt){

		removeChoosableFileFilter(getAcceptAllFileFilter());
		//設定對話盒預設使用選取所有檔案的FileFilter

		MediaFilter selFilter =
			new MediaFilter(strExt[0][0], strExt[0][1]);
		//宣告預設使用的檔案篩選物件

		for(int i=0; i<strExt.length; i++){
				addChoosableFileFilter(new MediaFilter(strExt[i][0], strExt[i][1]));
				//將欲篩選檔案的副檔名與描述字串宣告為檔案篩選物件,
				//並加入檔案選取器, 做為以篩選檔案
		}
		setFileFilter(selFilter);
		//設定對話盒預設使用選取所有檔案的FileFilter
	}
}

//建立過濾檔案選擇器內檔案類型的物件
class MediaFilter extends FileFilter {
	
	String extension, description;
	//宣告檔案的副檔名與描述字串

	MediaFilter(String ext, String des){ //建構子
		extension = ext.toLowerCase();
		//將副檔名轉換為英文小寫

		description = des; //設定檔案的描述字串
	}

	//判斷檔案是否為接受的類型, 是則傳回true
	public boolean accept(File f){

		if (f.isDirectory()) //若為資料夾傳回true
			return true;

		String ext = null;
		String s = f.getName(); //取得檔案名稱
		int i = s.lastIndexOf('.'); //尋找檔案名稱內的"."號

		if (i > 0 &&  i < s.length() - 1){
			ext = s.substring(i+1).toLowerCase();
			//從檔案名稱內取得副檔名字串

			//判斷副檔名是否與檔案篩選物件的extension字串相同
			if(extension.equals(ext))
				return true;
		}

		return false;
	}

	//傳回檔案篩選物件欲篩選檔案類型的描述字串
	public String getDescription(){ return description; }
}