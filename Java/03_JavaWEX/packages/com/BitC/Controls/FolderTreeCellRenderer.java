package com.BitC.Controls;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.io.*;

//定義樹狀結構元件顯示畫面使用的轉譯器
class FolderTreeCellRenderer 
	extends DefaultTreeCellRenderer {

	private String toolTip; //儲存工具提示文字的屬性

	public Component getTreeCellRendererComponent(
		JTree tree, //使用轉譯器物件的樹狀結構元件
		Object value, //轉譯器欲處理的樹狀結構之節點
		boolean isSelected, //被處理節點是否被選取
		boolean expanded, //被處理節點是否展開
		boolean leaf, //被選取節點是否為葉節點
		int row, //被選取節點的列數
		boolean hasFocus) { //被選取節點是否擁有焦點


		super.getTreeCellRendererComponent(
			tree, value, isSelected, expanded, leaf, row, hasFocus);
		//呼叫基礎類別的getTreeCellRendererComponent()方法

		File item = (File)value;
		//取得被選取節點的物件

		toolTip = item.getPath();

		if(((File)tree.getModel().getRoot()).equals(item)){
			//判斷是否為根節點

			setText(item.getPath());
			//是則設定顯示文字為File物件的路徑
		}
		else{
			setText(item.getName());
			//設定節點顯示的文字為File物件所代表檔案的名稱
		}

		setIcon(
			expanded ? 
			FileIconResource.ICON_OpenedFolder :
			FileIconResource.ICON_ClosedFolder);
		//依照節點是否展開設定使用的圖示

		setTextNonSelectionColor(Color.black);
		//設定非選取節點的文字顏色

		setTextSelectionColor(Color.darkGray);
		//設定選取節點的文字顏色

		setBackgroundSelectionColor(Color.lightGray);
		//設定選取節點的背景顏色

		setBorderSelectionColor(Color.darkGray);
		//設定選取節點的框線顏色

		return this;
	}

	public String getToolTipText(){ //定義節點顯示的工具提示文字
		return toolTip;
	}
}