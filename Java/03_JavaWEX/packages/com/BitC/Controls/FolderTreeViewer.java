package com.BitC.Controls;

import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;

//�~��JTree���O�w�q��ܸ�Ƨ��𪬵��c
//��FolderTreeViewer���O
public class FolderTreeViewer extends JTree {

	FolderTreeModel dmtnComputer = null;
	FolderTreeCellRenderer renderer;

	public FolderTreeViewer(File rootpath){ //�غc�l

		dmtnComputer = new FolderTreeModel(rootpath);
		//�ŧi��ܫ��w���|����Ƨ��𪬵��c��Model����

		setModel(dmtnComputer); //�]�w�x�s�𪬵��c���e��Model����

		renderer = new FolderTreeCellRenderer();
		//�ŧi�𪬵��c�ϥΪ���Ķ������

		setCellRenderer(renderer); //�]�w�𪬵��c�������Ķ������
	}

	public File getTreeNode(TreePath path) {
		return (File)(path.getLastPathComponent());
		//���o���w�𪬵��c���|���̫�@�Ӹ`�I
	}
}