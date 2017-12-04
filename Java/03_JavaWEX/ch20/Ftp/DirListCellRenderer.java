import com.BitC.Controls.*;

import java.io.*;

import javax.swing.*;
import java.awt.*;

import cz.dhl.ftp.*;

//�w�q�~��DefaultListCellRenderer���O��ܸ�Ƨ����M��������Ķ������
class DirListCellRenderer extends DefaultListCellRenderer {

	//���o�e�{�M�������N���Ƨ����ﶵ����Ķ������
	public Component getListCellRendererComponent(
					JList list,
					Object value,
					int index,
					boolean isSelected,
					boolean cellHasFocus) {

		super.getListCellRendererComponent(
			list, value, index, isSelected, cellHasFocus);
		//�I�s��¦���O���غc�l

		String fileName = null; //�w�q�x�s�ɮצW�٪��r��
		boolean isDir = false; //�P�_�O�_����Ƨ���boolean��

		if(value.getClass() == File.class){
		//���o�M�����ﶵ���x�s�ȬO�_��File���O

			File file = (File)value; //���o�M�����ﶵ���x�s�Ȩ��૬��File����
			fileName = file.getName(); //���oFile���󪺦W�� 
			isDir = file.isDirectory(); //�P�_File����O�_����Ƨ�
		}
		else if(value.getClass() == FtpFile.class){
		//���o�M�����ﶵ���x�s�ȬO�_��FtpFile���O

			FtpFile file = (FtpFile)value;
			//���o�M�����ﶵ���x�s�Ȩ��૬��FtpFile����

			fileName = file.getName(); //���oFtpFile���󪺦W��
			isDir = file.isDirectory(); //�P�_File����O�_����Ƨ�
		}
		else
			return this; //�Ǧ^���󥻨�

		setText(fileName); //�H�ɮצW�ٳ]�w�`�I����ܦW��

		if(isDir){ //�P�_����ܤ��`�I�O�_�N���Ƨ�
			setIcon(isSelected 
				? FileIconResource.ICON_OpenedFolder
				: FileIconResource.ICON_ClosedFolder);
			//�̷Ӹ`�I�O�_�Q����]�w�������ϥ�
		}
		else{
			setIcon(FileIconResource.ICON_File);
			//�Y�`�I�ä��N���Ƨ��h�]�w����ɮ׹ϥ�
		}

		setBackground(isSelected? Color.lightGray: Color.white);
		setForeground(isSelected? Color.darkGray: Color.black);
		//�]�w�`�I���e���C��P�I���C��

		if(isSelected) //�P�_�`�I�O�_�Q���
			setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));
			//����`�I��, �N�]�w�ؽu

		return this;
	}
}
