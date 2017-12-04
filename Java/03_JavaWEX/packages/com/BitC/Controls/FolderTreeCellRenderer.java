package com.BitC.Controls;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.io.*;

//�w�q�𪬵��c������ܵe���ϥΪ���Ķ��
class FolderTreeCellRenderer 
	extends DefaultTreeCellRenderer {

	private String toolTip; //�x�s�u�㴣�ܤ�r���ݩ�

	public Component getTreeCellRendererComponent(
		JTree tree, //�ϥ���Ķ�����󪺾𪬵��c����
		Object value, //��Ķ�����B�z���𪬵��c���`�I
		boolean isSelected, //�Q�B�z�`�I�O�_�Q���
		boolean expanded, //�Q�B�z�`�I�O�_�i�}
		boolean leaf, //�Q����`�I�O�_�����`�I
		int row, //�Q����`�I���C��
		boolean hasFocus) { //�Q����`�I�O�_�֦��J�I


		super.getTreeCellRendererComponent(
			tree, value, isSelected, expanded, leaf, row, hasFocus);
		//�I�s��¦���O��getTreeCellRendererComponent()��k

		File item = (File)value;
		//���o�Q����`�I������

		toolTip = item.getPath();

		if(((File)tree.getModel().getRoot()).equals(item)){
			//�P�_�O�_���ڸ`�I

			setText(item.getPath());
			//�O�h�]�w��ܤ�r��File���󪺸��|
		}
		else{
			setText(item.getName());
			//�]�w�`�I��ܪ���r��File����ҥN���ɮת��W��
		}

		setIcon(
			expanded ? 
			FileIconResource.ICON_OpenedFolder :
			FileIconResource.ICON_ClosedFolder);
		//�̷Ӹ`�I�O�_�i�}�]�w�ϥΪ��ϥ�

		setTextNonSelectionColor(Color.black);
		//�]�w�D����`�I����r�C��

		setTextSelectionColor(Color.darkGray);
		//�]�w����`�I����r�C��

		setBackgroundSelectionColor(Color.lightGray);
		//�]�w����`�I���I���C��

		setBorderSelectionColor(Color.darkGray);
		//�]�w����`�I���ؽu�C��

		return this;
	}

	public String getToolTipText(){ //�w�q�`�I��ܪ��u�㴣�ܤ�r
		return toolTip;
	}
}