import javax.swing.*;
import java.io.*;

import javax.swing.filechooser.FileFilter;

//�~��JFileChooser���O�w�q��������ɪ��ɮ׿����
class MediaFileChooser extends JFileChooser {

	public MediaFileChooser(String path){ //�غc�l
		super(path);
		//�I�s��¦���O���غc�l�öǤJ���s���ɮת����|
	}

	//�ǤJ���s���ɮפ����|�P���z���ɮת����ɦW�P�y�z�r��
	public MediaFileChooser(String path, String[][] strExt){
		super(path); //�N���s�����|�ǤJ��¦���O���غc�l
		addChoosableFileFilter(strExt);
		//�[�J�L�o�i����ɮפ��ɮ׿z�磌��
	}

	//�[�J�ɮ׿z�磌��
	public void addChoosableFileFilter(String [][] strExt){

		removeChoosableFileFilter(getAcceptAllFileFilter());
		//�]�w��ܲ��w�]�ϥο���Ҧ��ɮת�FileFilter

		MediaFilter selFilter =
			new MediaFilter(strExt[0][0], strExt[0][1]);
		//�ŧi�w�]�ϥΪ��ɮ׿z�磌��

		for(int i=0; i<strExt.length; i++){
				addChoosableFileFilter(new MediaFilter(strExt[i][0], strExt[i][1]));
				//�N���z���ɮת����ɦW�P�y�z�r��ŧi���ɮ׿z�磌��,
				//�å[�J�ɮ׿����, �����H�z���ɮ�
		}
		setFileFilter(selFilter);
		//�]�w��ܲ��w�]�ϥο���Ҧ��ɮת�FileFilter
	}
}

//�إ߹L�o�ɮ׿�ܾ����ɮ�����������
class MediaFilter extends FileFilter {
	
	String extension, description;
	//�ŧi�ɮת����ɦW�P�y�z�r��

	MediaFilter(String ext, String des){ //�غc�l
		extension = ext.toLowerCase();
		//�N���ɦW�ഫ���^��p�g

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
			if(extension.equals(ext))
				return true;
		}

		return false;
	}

	//�Ǧ^�ɮ׿z�磌����z���ɮ��������y�z�r��
	public String getDescription(){ return description; }
}