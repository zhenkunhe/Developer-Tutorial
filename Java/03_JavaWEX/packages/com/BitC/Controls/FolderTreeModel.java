package com.BitC.Controls;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

//���o���w�ϺФ�����Ƨ�
public class FolderTreeModel implements TreeModel {

	private File root; //�ڸ`�I
	private Vector listeners = new Vector();
	//�x�s��ť�����󪺮e��

	//�w�q�P�ŧi�Ω�z���Ƨ���FileFilter����
	FileFilter ffDir = new FileFilter(){
		public boolean accept(File file){
			return file.isDirectory();
			//�P�_File����O�_�N���Ƨ�
		}
	};

	//�ǤJ���w���|���غc�l
	public FolderTreeModel(File rootDirectory){

		//�P�_�ڸ`�I�U����Ƨ��~�]�wroot�ݩ�
		if(rootDirectory.listFiles(ffDir) != null)
			root = rootDirectory;
	}

	public void setRoot(File rootDirectory){

		//�P�_�ڸ`�I�U�O�_����Ƨ�
		//�Y�S����Ƨ�, �h�]�w�ڸ`�I��null
		if(rootDirectory.listFiles(ffDir) != null)
			root = rootDirectory;
		else
			root = null;
	}
   
	//���o�ڸ`�I
	public Object getRoot() {
		return root;
	}

	//���o�Y�`�I�U���w���ޭȩҥN���l�`�I
	public Object getChild(Object parent, int index) {

		File directory = (File) parent; //���o�`�I

		File[] files = directory.listFiles(ffDir);
		//���o�`�I���ŦXffDir�z�磌��File����

		return new File(directory, files[index].getName());
		//�^�Ǩ��o��File����
	}

	//���o�`�I�ҥ]�t�l�`�I���Ӽ�
	public int getChildCount(Object parent){
		File[] files = ((File)parent).listFiles(ffDir);
		//���o�`�I���ŦXffDir�z�磌��File����
      
		return files.length; //�^�Ǥl�`�I�Ӽ�
	}

	//�P�_�`�I�O�_�����]�t�l�`�I�����`�I
	public boolean isLeaf(Object node) {

		File[] files = ((File)node).listFiles(ffDir);
		//���o�`�I���ŦXffDir�z�磌��File����

		//�Yfiles��null�Ϊ̨S���ɮ׫h�Ǧ^true
		if(files == null || files.length == 0)
			return true;

		return false;
	}

	//���o�`�I���Y�l�`�I�����ޭ�
	public int getIndexOfChild(Object parent, Object child) {

		File directory = (File)parent; //�ǤJ���`�I
		File file = (File)child; //�ǤJ���l�`�I

		File[] children = directory.listFiles(ffDir);
		//���o�`�I���ŦXffDir�z�磌��File����
     
		if(children == null) //�Y�L�l�`�I�Ǧ^-1
			return -1;

		//�B��for�j��M��l�`�I���ۦP���`�I
		//���h�Ǧ^���ޭ�
		for(int i = 0; i < children.length; i++ ) {
			if(file.equals(children[i])) { //�P�_�`�I�O�_�ۦP
				return i; //�Ǧ^���ޭ�
			}
		}
      
		return -1; //�S�����h�Ǧ^-1
	}
   
	//�����Ѹ`�I�ק�\��
	public void valueForPathChanged(TreePath path, Object value) {
	}
	
	//���U��ťTreeModelEvent�ƥ󪺺�ť������
	public void addTreeModelListener(TreeModelListener listener){

		if(listener == null)
			return;

		listeners.add(listener); //�N��ť������[�J�e��
	}
   
	//������ťTreeModelEvent�ƥ󪺺�ť������
	public void removeTreeModelListener(TreeModelListener listener){
		listeners.remove(listener); //�q�e��������ť������
	}
}