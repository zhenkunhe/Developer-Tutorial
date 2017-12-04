package com.BitC.Controls;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;

public class FileList extends JList {

	File curDir; //宣告目前檢視的路徑

	public FileList(){ //建構子
		initialize(); //起始清單方塊
	}

	//建構子, 設定列出檔案的起始路徑
	public FileList(File directory){	
		setPath(directory);
		//設定清單方塊檢視檔案列表的資料夾路徑

		initialize(); //起始清單方塊
	}

	//建構子, 設定列出檔案的起始路徑與檔案名稱篩選物件
	public FileList(File directory, FilenameFilter filter){		
		setPath(directory, filter);
		//設定清單方塊檢視檔案列表的資料夾路徑與檔案名稱篩選物件

		initialize(); //起始清單方塊
	}

	//建構子, 設定列出檔案的起始路徑與檔案名稱篩選物件
	public FileList(File directory, FileFilter filter){
		setPath(directory, filter);
		//設定檔案列表清單方塊顯示的資料夾路徑

		initialize(); //起始清單方塊內容
	}

	private void initialize(){ //起始清單方塊

		setCellRenderer(new FileListCellRenderer()); 
		//設定清單方塊使用的轉譯器物件

		setLayoutOrientation(JList.VERTICAL_WRAP);
		//設定清單方塊選項的排列方式

		setSelectionMode(
			ListSelectionModel.SINGLE_INTERVAL_SELECTION );
		//設定清單方塊的選取模式

		//註冊監聽MouseEvent事件的監聽器物件
		addMouseListener(new MouseAdapter(){

			//回應滑鼠按一下的方法
			public void mouseClicked(MouseEvent e){

				if(e.getClickCount() == 2){
				//判斷滑鼠按下次數是否為2

					FileList fl = (FileList) e.getSource();
					//取得引發事件之檔案清單方塊

					File file = (File)fl.getSelectedValue();
					//取得選取值

					if(file == null) return;
					//若未選取檔案則終止執行方法

					if(file.isDirectory())	setPath(file);
					//判斷選取項目的儲存值是否為File物件
					//是則設定顯示此路徑下的檔案列表
				}
			}
		});
	}
	
	//設定列出檔案列表的資料夾路徑
	public void setListFile(File directory){

		File[] files = directory.listFiles();
		//列出資料夾內的所有檔案

		if(files !=  null){ //若有取得檔案
			setListData(files);
			//設定以陣列內的檔案做為清單方塊的選項
		}
		else{
			setModel(new DefaultListModel());
			//清除清單方塊的內容
		}
	}

	//指定資料夾位置與檔案名稱篩選物件設定清單方塊顯示的檔案列表
	public void setListFile(File directory, FilenameFilter filter){

		File[] files = directory.listFiles(filter);
		//以檔案名稱篩選物件篩選指定資料夾位置下的檔案列表

		if(files !=  null){ //若有取得檔案
			setListData(files); //設定清單方塊顯示篩選出的檔案
		}
		else{
			setModel(new DefaultListModel());
			//清除清單方塊內的選項
		}
	}

	//指定資料夾位置與檔案篩選物件設定清單方塊顯示的檔案列表
	public void setListFile(File directory, FileFilter filter){

		File[] files = directory.listFiles(filter);
		//以檔案篩選物件篩選指定資料夾位置下的檔案列表

		if(files !=  null){ //若有取得檔案
			setListData(files); //設定清單方塊列出的檔案項目
		}
		else{
			setModel(new DefaultListModel());
			//清除清單方塊內容
		}
	}

	public void setPath(File directory){ //設定清單方塊欲列出檔案的路徑
		curDir = directory; //設定清單方塊目前列出檔案的路徑
		setListFile(directory); //設定清單方塊列出檔案的路徑
	}

	//設定清單方塊列出檔案的路徑與檔案名稱篩選物件
	public void setPath(File directory, FilenameFilter filter){
		curDir = directory; //設定清單方塊目前列出檔案的路徑
		setListFile(directory, filter);
		//設定清單方塊列出檔案的路徑與檔案名稱篩選物件
	}

	public void setPath(File directory, FileFilter filter){
		curDir = directory; //設定清單方塊目前列出檔案的路徑
		setListFile(directory, filter);
		//設定清單方塊列出檔案的路徑與檔案篩選物件
	}

	//取得目前清單方塊瀏覽的路徑
	public File getCurrentPath(){
		return curDir;
	}
}