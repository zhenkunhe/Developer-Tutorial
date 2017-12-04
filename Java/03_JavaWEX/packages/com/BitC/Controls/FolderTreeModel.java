package com.BitC.Controls;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

//取得指定磁碟內的資料夾
public class FolderTreeModel implements TreeModel {

	private File root; //根節點
	private Vector listeners = new Vector();
	//儲存監聽器物件的容器

	//定義與宣告用於篩選資料夾的FileFilter物件
	FileFilter ffDir = new FileFilter(){
		public boolean accept(File file){
			return file.isDirectory();
			//判斷File物件是否代表資料夾
		}
	};

	//傳入指定路徑的建構子
	public FolderTreeModel(File rootDirectory){

		//判斷根節點下有資料夾才設定root屬性
		if(rootDirectory.listFiles(ffDir) != null)
			root = rootDirectory;
	}

	public void setRoot(File rootDirectory){

		//判斷根節點下是否有資料夾
		//若沒有資料夾, 則設定根節點為null
		if(rootDirectory.listFiles(ffDir) != null)
			root = rootDirectory;
		else
			root = null;
	}
   
	//取得根節點
	public Object getRoot() {
		return root;
	}

	//取得某節點下指定索引值所代表的子節點
	public Object getChild(Object parent, int index) {

		File directory = (File) parent; //取得節點

		File[] files = directory.listFiles(ffDir);
		//取得節點內符合ffDir篩選物件的File物件

		return new File(directory, files[index].getName());
		//回傳取得的File物件
	}

	//取得節點所包含子節點的個數
	public int getChildCount(Object parent){
		File[] files = ((File)parent).listFiles(ffDir);
		//取得節點內符合ffDir篩選物件的File物件
      
		return files.length; //回傳子節點個數
	}

	//判斷節點是否為不包含子節點的葉節點
	public boolean isLeaf(Object node) {

		File[] files = ((File)node).listFiles(ffDir);
		//取得節點內符合ffDir篩選物件的File物件

		//若files為null或者沒有檔案則傳回true
		if(files == null || files.length == 0)
			return true;

		return false;
	}

	//取得節點內某子節點的索引值
	public int getIndexOfChild(Object parent, Object child) {

		File directory = (File)parent; //傳入的節點
		File file = (File)child; //傳入的子節點

		File[] children = directory.listFiles(ffDir);
		//取得節點內符合ffDir篩選物件的File物件
     
		if(children == null) //若無子節點傳回-1
			return -1;

		//運用for迴圈尋找子節點內相同的節點
		//找到則傳回索引值
		for(int i = 0; i < children.length; i++ ) {
			if(file.equals(children[i])) { //判斷節點是否相同
				return i; //傳回索引值
			}
		}
      
		return -1; //沒有找到則傳回-1
	}
   
	//不提供節點修改功能
	public void valueForPathChanged(TreePath path, Object value) {
	}
	
	//註冊監聽TreeModelEvent事件的監聽器物件
	public void addTreeModelListener(TreeModelListener listener){

		if(listener == null)
			return;

		listeners.add(listener); //將監聽器物件加入容器
	}
   
	//移除監聽TreeModelEvent事件的監聽器物件
	public void removeTreeModelListener(TreeModelListener listener){
		listeners.remove(listener); //從容器移除監聽器物件
	}
}