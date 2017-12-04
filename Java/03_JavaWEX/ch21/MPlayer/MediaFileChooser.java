import javax.swing.*;
import java.io.*;

import javax.swing.filechooser.FileFilter;

//�~���ɮ׿�ܾ��w�q����h�C���ɮת���ܾ�
class MediaFileChooser extends JFileChooser{

	//�غc�l, �N�ǤJ���s�������|
	public MediaFileChooser(String path){
		super(path);
	}

	public MediaFileChooser(){ } //�غc�l

	//�[�J�ɮ׿z�磌��
	public void addChoosableFileFilter(
				String [] strExt, String strDes){

		addChoosableFileFilter(new MediaFilter(strExt, strDes));
		//�[�J�z��h�C���ɮת�����
	}

	public void addChoosableFileFilter(
		String strExt, String strDes){

		String[] ext = new String[1]; //�w�q���ɦW�r��

		ext[0] = strExt; //�]�w���ɦW

		addChoosableFileFilter(ext, strDes);
		//�N���ɦW�P�ɮ״y�z�r��[�J���ɮ׿z��
	}
}

//�إ߹L�o�ɮ׿�ܹ�ܲ����ɮ�����������
class MediaFilter extends FileFilter {
	
	String[] extension = null;
	String description = null;

	MediaFilter(String[] ext, String des){ //�غc�l
		
		for(String elm: ext)	elm.toLowerCase();
		//�N���ɦW�ഫ���^��p�g

		extension = ext; //�]�w���ɦW
		description = des; //�]�w�ɮת��y�z�r��
	}

	//�P�_�ɮ׬O�_������������, �O�h�Ǧ^true
	public boolean accept(File f){

		if (f.isDirectory()) //�Y����Ƨ��Ǧ^true
			return true;

		String ext = null;
		String s = f.getName(); //���o�ɮצW��
		int i = s.lastIndexOf('.'); //�M���ɮצW�٤���"."��

		if (i > 0 &&  i < s.length() - 1){
			ext = s.substring(i+1).toLowerCase();
			//�q�ɮצW�٤����o���ɦW�r��

			//�P�_���ɦW�O�_�P�ɮ׿z�磌��extension�r��ۦP
			for(String elm: extension){
				if(elm.equals(ext)) //�P�_���ɦW�O�_�ۦP
					return true;
			}
		}

		return false;
	}

	//�Ǧ^�ɮ׿z�磌����z���ɮ��������y�z�r��
	public String getDescription(){ return description; }
}