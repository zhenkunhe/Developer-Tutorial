package com.BitC.Controls;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class FileListCellRenderer extends DefaultListCellRenderer {

	//���o�M�������ت���Ķ��
	public Component getListCellRendererComponent(
		JList list,	Object value, int index,
		boolean isSelected,	boolean cellHasFocus) {

		super.getListCellRendererComponent(
			list, value, index, isSelected, cellHasFocus);
		//�I�s��¦���O����k

		String text = ((File)value).getName();
		//���o�M�������ﶵ���x�s�Ȫ��ɮצW��

		setText(text); //�]�w�M�����ﶵ��ܪ��W��

		text = text.toLowerCase(); //�N��ܦW���ഫ���^��p�g

		//�P�_�ɮצW�٪�����, �]�w�M�����ﶵ��ܪ��ϥ�
		if(text.endsWith(".gif")){
			setIcon(FileIconResource.ICON_GIF); 
		}
		else if(text.endsWith(".jpg")){
			setIcon(FileIconResource.ICON_JPG);
		}

		setForeground(isSelected ? Color.darkGray : Color.black);
		setBackground(isSelected ? Color.lightGray : Color.white);
		//�̷ӲM�����ﶵ�O�_�Q����]�w�e/�I���C��

		if(isSelected)
			setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));
		//�]�w�M�����ﶵ���~��

		return this;
	}
}