package com.BitC.Controls;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;

public class FileList extends JList {

	File curDir; //�ŧi�ثe�˵������|

	public FileList(){ //�غc�l
		initialize(); //�_�l�M����
	}

	//�غc�l, �]�w�C�X�ɮת��_�l���|
	public FileList(File directory){	
		setPath(directory);
		//�]�w�M�����˵��ɮצC����Ƨ����|

		initialize(); //�_�l�M����
	}

	//�غc�l, �]�w�C�X�ɮת��_�l���|�P�ɮצW�ٿz�磌��
	public FileList(File directory, FilenameFilter filter){		
		setPath(directory, filter);
		//�]�w�M�����˵��ɮצC����Ƨ����|�P�ɮצW�ٿz�磌��

		initialize(); //�_�l�M����
	}

	//�غc�l, �]�w�C�X�ɮת��_�l���|�P�ɮצW�ٿz�磌��
	public FileList(File directory, FileFilter filter){
		setPath(directory, filter);
		//�]�w�ɮצC��M������ܪ���Ƨ����|

		initialize(); //�_�l�M�������e
	}

	private void initialize(){ //�_�l�M����

		setCellRenderer(new FileListCellRenderer()); 
		//�]�w�M�����ϥΪ���Ķ������

		setLayoutOrientation(JList.VERTICAL_WRAP);
		//�]�w�M�����ﶵ���ƦC�覡

		setSelectionMode(
			ListSelectionModel.SINGLE_INTERVAL_SELECTION );
		//�]�w�M����������Ҧ�

		//���U��ťMouseEvent�ƥ󪺺�ť������
		addMouseListener(new MouseAdapter(){

			//�^���ƹ����@�U����k
			public void mouseClicked(MouseEvent e){

				if(e.getClickCount() == 2){
				//�P�_�ƹ����U���ƬO�_��2

					FileList fl = (FileList) e.getSource();
					//���o�޵o�ƥ��ɮײM����

					File file = (File)fl.getSelectedValue();
					//���o�����

					if(file == null) return;
					//�Y������ɮ׫h�פ�����k

					if(file.isDirectory())	setPath(file);
					//�P�_������ت��x�s�ȬO�_��File����
					//�O�h�]�w��ܦ����|�U���ɮצC��
				}
			}
		});
	}
	
	//�]�w�C�X�ɮצC����Ƨ����|
	public void setListFile(File directory){

		File[] files = directory.listFiles();
		//�C�X��Ƨ������Ҧ��ɮ�

		if(files !=  null){ //�Y�����o�ɮ�
			setListData(files);
			//�]�w�H�}�C�����ɮװ����M�������ﶵ
		}
		else{
			setModel(new DefaultListModel());
			//�M���M���������e
		}
	}

	//���w��Ƨ���m�P�ɮצW�ٿz�磌��]�w�M������ܪ��ɮצC��
	public void setListFile(File directory, FilenameFilter filter){

		File[] files = directory.listFiles(filter);
		//�H�ɮצW�ٿz�磌��z����w��Ƨ���m�U���ɮצC��

		if(files !=  null){ //�Y�����o�ɮ�
			setListData(files); //�]�w�M������ܿz��X���ɮ�
		}
		else{
			setModel(new DefaultListModel());
			//�M���M���������ﶵ
		}
	}

	//���w��Ƨ���m�P�ɮ׿z�磌��]�w�M������ܪ��ɮצC��
	public void setListFile(File directory, FileFilter filter){

		File[] files = directory.listFiles(filter);
		//�H�ɮ׿z�磌��z����w��Ƨ���m�U���ɮצC��

		if(files !=  null){ //�Y�����o�ɮ�
			setListData(files); //�]�w�M�����C�X���ɮ׶���
		}
		else{
			setModel(new DefaultListModel());
			//�M���M�������e
		}
	}

	public void setPath(File directory){ //�]�w�M�������C�X�ɮת����|
		curDir = directory; //�]�w�M�����ثe�C�X�ɮת����|
		setListFile(directory); //�]�w�M�����C�X�ɮת����|
	}

	//�]�w�M�����C�X�ɮת����|�P�ɮצW�ٿz�磌��
	public void setPath(File directory, FilenameFilter filter){
		curDir = directory; //�]�w�M�����ثe�C�X�ɮת����|
		setListFile(directory, filter);
		//�]�w�M�����C�X�ɮת����|�P�ɮצW�ٿz�磌��
	}

	public void setPath(File directory, FileFilter filter){
		curDir = directory; //�]�w�M�����ثe�C�X�ɮת����|
		setListFile(directory, filter);
		//�]�w�M�����C�X�ɮת����|�P�ɮ׿z�磌��
	}

	//���o�ثe�M�����s�������|
	public File getCurrentPath(){
		return curDir;
	}
}