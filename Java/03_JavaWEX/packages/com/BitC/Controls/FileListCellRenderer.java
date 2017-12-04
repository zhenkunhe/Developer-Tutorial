package com.BitC.Controls;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class FileListCellRenderer extends DefaultListCellRenderer {

	//取得清單方塊項目的轉譯器
	public Component getListCellRendererComponent(
		JList list,	Object value, int index,
		boolean isSelected,	boolean cellHasFocus) {

		super.getListCellRendererComponent(
			list, value, index, isSelected, cellHasFocus);
		//呼叫基礎類別的方法

		String text = ((File)value).getName();
		//取得清單方塊內選項之儲存值的檔案名稱

		setText(text); //設定清單方塊選項顯示的名稱

		text = text.toLowerCase(); //將顯示名稱轉換為英文小寫

		//判斷檔案名稱的結尾, 設定清單方塊選項顯示的圖示
		if(text.endsWith(".gif")){
			setIcon(FileIconResource.ICON_GIF); 
		}
		else if(text.endsWith(".jpg")){
			setIcon(FileIconResource.ICON_JPG);
		}

		setForeground(isSelected ? Color.darkGray : Color.black);
		setBackground(isSelected ? Color.lightGray : Color.white);
		//依照清單方塊選項是否被選取設定前/背景顏色

		if(isSelected)
			setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));
		//設定清單方塊選項的外框

		return this;
	}
}