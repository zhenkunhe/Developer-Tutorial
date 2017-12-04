import javax.swing.*;
import java.io.*;

import javax.swing.filechooser.FileFilter;

//繼承檔案選擇器定義選取多媒體檔案的選擇器
class MediaFileChooser extends JFileChooser{

	//建構子, 將傳入欲瀏覽的路徑
	public MediaFileChooser(String path){
		super(path);
	}

	public MediaFileChooser(){ } //建構子

	//加入檔案篩選物件
	public void addChoosableFileFilter(
				String [] strExt, String strDes){

		addChoosableFileFilter(new MediaFilter(strExt, strDes));
		//加入篩選多媒體檔案的物件
	}

	public void addChoosableFileFilter(
		String strExt, String strDes){

		String[] ext = new String[1]; //定義副檔名字串

		ext[0] = strExt; //設定副檔名

		addChoosableFileFilter(ext, strDes);
		//將副檔名與檔案描述字串加入為檔案篩選
	}
}

//建立過濾檔案選擇對話盒內檔案類型的物件
class MediaFilter extends FileFilter {
	
	String[] extension = null;
	String description = null;

	MediaFilter(String[] ext, String des){ //建構子
		
		for(String elm: ext)	elm.toLowerCase();
		//將副檔名轉換為英文小寫

		extension = ext; //設定副檔名
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
			for(String elm: extension){
				if(elm.equals(ext)) //判斷副檔名是否相同
					return true;
			}
		}

		return false;
	}

	//傳回檔案篩選物件欲篩選檔案類型的描述字串
	public String getDescription(){ return description; }
}