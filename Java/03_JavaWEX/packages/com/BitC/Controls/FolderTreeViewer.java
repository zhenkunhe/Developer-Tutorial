package com.BitC.Controls;

import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;

//繼承JTree類別定義顯示資料夾樹狀結構
//的FolderTreeViewer類別
public class FolderTreeViewer extends JTree {

	FolderTreeModel dmtnComputer = null;
	FolderTreeCellRenderer renderer;

	public FolderTreeViewer(File rootpath){ //建構子

		dmtnComputer = new FolderTreeModel(rootpath);
		//宣告顯示指定路徑之資料夾樹狀結構的Model物件

		setModel(dmtnComputer); //設定儲存樹狀結構內容的Model物件

		renderer = new FolderTreeCellRenderer();
		//宣告樹狀結構使用的轉譯器物件

		setCellRenderer(renderer); //設定樹狀結構控制項的轉譯器物件
	}

	public File getTreeNode(TreePath path) {
		return (File)(path.getLastPathComponent());
		//取得指定樹狀結構路徑的最後一個節點
	}
}