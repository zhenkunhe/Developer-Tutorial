import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��
import java.io.*;

import javax.swing.filechooser.*;
//�ޥΩw�qFileView��H���O
//�PFileSystemView��H���O���M��

public class FileViewEX extends JFrame{

	private final static 
		String DEFAULT_FILE_PATH = "C:\\JavaWEX";
	//�w�]���ɮ��s�����|

	JLabel lbResult = new JLabel("����ɮ� : ");
	//�ŧi�Q����ɮת�����

	//�H�~�Ӥ覡�w�q�L�o�ɮ׿�ܾ��B�Ϊ��ɮ׿z�磌��
	class JavaFilter 
		extends javax.swing.filechooser.FileFilter {
		
		String extension, description;
		//�w�q�x�s�ɮװ��ɦW�P�ɮ״y�z��String����

		JavaFilter(String ext, String des){ //�غc�l
			extension = ext.toLowerCase(); //�N���ɦW�ഫ���^��p�g
			description = des; //�]�w�ɮת��y�z��r
		}

		//�P�_�ɮ׬O�_������������, �O�h�Ǧ^true
		public boolean accept(File f){

			if (f.isDirectory()) //�Y����Ƨ��Ǧ^true
				return true;

			String ext = null;
			String s = f.getName(); //���o�ɮצW��
			int i = s.lastIndexOf('.');
			//�M���ɮצW�٤���"."��

			if (i > 0 &&  i < s.length() - 1){
				ext = s.substring(i+1).toLowerCase();
				//�q�ɮצW�٤����o���ɦW�r��

				//�P�_���ɦW�O�_�P
				//�ɮ׿z�磌��extension�r��ۦP
				if(extension.equals(ext))
					return true;
			}

			return false;
		}

		//�Ǧ^�ɮ׿z�磌����z���ɮ��������y�z�r��
		public String getDescription(){ return description; }
	}

	FileViewEX(){

		JButton btnOpen = new JButton("�}������");
		//�ŧi�I�s�ɮ׹�ܲ������s

		//�w�q�B���U�^�����sActionEvent�ƥ󪺺�ť������
		btnOpen.addActionListener(new ActionListener(){

			//�^�����s���U�ƥ󪺤�k
			public void actionPerformed(ActionEvent e){

				JFileChooser fcObj = 
					new JFileChooser(DEFAULT_FILE_PATH);
				//�ŧi�ɮ׿�ܾ�����

				fcObj.setFileView(new JavaFileView());
				//�]�w�ɮ׶}�ҹ�ܲ��ϥΦۭq��JavaFileView����

				fcObj.setDialogTitle("�}������");
				//�]�w��ܲ������D

				fcObj.addChoosableFileFilter(
					new JavaFilter("class", "Java�줸�սX�� (.class)"));
				fcObj.addChoosableFileFilter(
					new JavaFilter("java", "Java��l�� (.java)"));
				//�s�W�ɮ׿z�磌��

				fcObj.setFileFilter(fcObj.getAcceptAllFileFilter());
				//�]�w�_�l��������Ҧ��ɮת��ɮ׿z�磌��

				int result = fcObj.showOpenDialog(FileViewEX.this);
				//����ɮ׶}�ҹ�ܲ�				

				if(result == JFileChooser.APPROVE_OPTION){
					File file = fcObj.getSelectedFile();
					//���o�ɮ׿�ܾ�������ɮ�

					lbResult.setText("����ɮ� : " + file.getName());
					//�]�w������ܶ}���ɮת��W��
				}
				else if(result == JFileChooser.CANCEL_OPTION){
					lbResult.setText("��������ɮ�");
					//�]�w������ܨ����ɮ׿�����T��
				}
			}		
		});

		JPanel plBtn = new JPanel(
				new FlowLayout(FlowLayout.CENTER, 10, 10));
		//�ŧi��m���s��JPanel�e��

		plBtn.add(btnOpen); //�N���s�[�J�e��

		Container cp = getContentPane(); //���o���e����
		cp.add(plBtn); //�N�]�t���s��JPanel�e���[�J���e����
		cp.add(lbResult, BorderLayout.SOUTH); //�[�J��ܰT��������
		
		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����|�P�N�ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(200, 110);
		setVisible(true);
	}

	public static void main(String args[]) {
		new FileViewEX(); //�ŧi�����ج[����
	}
}

//�~��FileView���O, �w�q�˵�Java�����ɮת�JavaFileView���O
class JavaFileView extends FileView {
	
	private static final ImageIcon 
		JAVA_ICON = new ImageIcon("icon\\Java.jpg"),
		CLASS_ICON = new ImageIcon("icon\\Class.jpg");
	//�w�q�N��U�عϥܪ��R�A�`���ݩ�

	public String getName(File f){ //���o�ɮצW��

		FileSystemView fsv = FileSystemView.getFileSystemView();
		//���o���ѥثe�t���ɮ׸�T��FileSystemView����

		return fsv.getSystemDisplayName(f);
		//�ǥX�ثe�t�Ϊ���ܦW��

		//String name = f.getName(); //���o�ɮשθ�Ƨ����W��
		//return ("".equals(name)? f.getPath() : name);
		//�P�_���o�W�٬O�_�Ŧr��, 
		//�O�h�����Ǧ^�ɮ׸��|, �Ϥ��Ǧ^���o���W��
	}

	//���o�ɮת��y�z��r
	public String getDescription(File f){
		return getTypeDescription(f);
	}

	//���o�ɮ��������y�z��r
	public String getTypeDescription(File f){

		String name = f.getName().toLowerCase();
		//���o�ɮצW��, ���ഫ���p�g�^��r��

		//�P�_���ɦW�O�_��.java��.class
		if(name.endsWith(".java")){
			return "Java �{����";
		}
		else if(name.endsWith(".class")){
			return "Java ���O��";
		}
		else{
			return name.substring(name.lastIndexOf(".")) + "��";
			//�����Ǧ^���ɦW
		}
	}

	public Icon getIcon(File f){ //���o�ɮ׹ϥ�

		String name = f.getName().toLowerCase();
		//���o�ɮצW��, ���ഫ���p�g�^��r��

		//�̷Ӱ��ɦW�Ǧ^�N���ɮת��ϥ�
		if(name.endsWith(".java")){
			return JAVA_ICON;
		}
		else if(name.endsWith(".class")){
			return CLASS_ICON;
		}

		return null;
	}

	//�P�_��Ƨ��O�_�i�Q
	public Boolean isTraversable(File f){
		return f.isDirectory();
	}
}