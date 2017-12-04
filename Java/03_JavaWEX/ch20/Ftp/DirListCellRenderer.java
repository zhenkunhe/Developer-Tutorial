import com.BitC.Controls.*;

import java.io.*;

import javax.swing.*;
import java.awt.*;

import cz.dhl.ftp.*;

//定義繼承DefaultListCellRenderer類別顯示資料夾之清單方塊的轉譯器物件
class DirListCellRenderer extends DefaultListCellRenderer {

	//取得呈現清單方塊內代表資料夾之選項的轉譯器物件
	public Component getListCellRendererComponent(
					JList list,
					Object value,
					int index,
					boolean isSelected,
					boolean cellHasFocus) {

		super.getListCellRendererComponent(
			list, value, index, isSelected, cellHasFocus);
		//呼叫基礎類別的建構子

		String fileName = null; //定義儲存檔案名稱的字串
		boolean isDir = false; //判斷是否為資料夾的boolean值

		if(value.getClass() == File.class){
		//取得清單方塊選項的儲存值是否為File類別

			File file = (File)value; //取得清單方塊選項之儲存值並轉型為File物件
			fileName = file.getName(); //取得File物件的名稱 
			isDir = file.isDirectory(); //判斷File物件是否為資料夾
		}
		else if(value.getClass() == FtpFile.class){
		//取得清單方塊選項的儲存值是否為FtpFile類別

			FtpFile file = (FtpFile)value;
			//取得清單方塊選項之儲存值並轉型為FtpFile物件

			fileName = file.getName(); //取得FtpFile物件的名稱
			isDir = file.isDirectory(); //判斷File物件是否為資料夾
		}
		else
			return this; //傳回物件本身

		setText(fileName); //以檔案名稱設定節點的顯示名稱

		if(isDir){ //判斷欲顯示之節點是否代表資料夾
			setIcon(isSelected 
				? FileIconResource.ICON_OpenedFolder
				: FileIconResource.ICON_ClosedFolder);
			//依照節點是否被選取設定對應的圖示
		}
		else{
			setIcon(FileIconResource.ICON_File);
			//若節點並不代表資料夾則設定顯示檔案圖示
		}

		setBackground(isSelected? Color.lightGray: Color.white);
		setForeground(isSelected? Color.darkGray: Color.black);
		//設定節點的前景顏色與背景顏色

		if(isSelected) //判斷節點是否被選取
			setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));
			//選取節點時, 將設定框線

		return this;
	}
}
