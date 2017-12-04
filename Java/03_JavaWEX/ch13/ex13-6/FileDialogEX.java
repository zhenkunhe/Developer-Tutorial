import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��
import java.io.*;

public class FileDialogEX extends JFrame{

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

	FileDialogEX(){

		JButton btnOpen = new JButton("�}������"),
			btnSave = new JButton("�x�s�ɮ�"),
			btnMultiOpen = new JButton("�i�ƿ諸�ۭq�ɮ׹�ܲ�");
		//�ŧi�I�s�ɮ׹�ܲ������s

		//�w�q�B���U�^�����sActionEvent�ƥ󪺺�ť������
		btnOpen.addActionListener(new ActionListener(){

			//�^�����s���U�ƥ󪺤�k
			public void actionPerformed(ActionEvent e){

				JFileChooser fcObj = 
					new JFileChooser(DEFAULT_FILE_PATH);
				//�ŧi�ɮ׿�ܾ�����

				int result = -1;

				fcObj.setDialogTitle("�}������");
				//�]�w��ܲ������D

				JavaFilter jfClass =
					new JavaFilter("class", "Java�줸�սX�� (.class)");
				//�ŧi�z��줸�սX�ɪ��ɮ׿z�磌��

				fcObj.addChoosableFileFilter(jfClass);
				fcObj.addChoosableFileFilter(
					new JavaFilter("java", "Java��l�� (.java)"));
				//�s�W�ɮ׿z�磌��

				fcObj.removeChoosableFileFilter(
					fcObj.getAcceptAllFileFilter());
				//�����z��Ҧ��ɮת��z�磌��

				fcObj.setFileFilter(jfClass);
				//�]�w�_�l����z��줸�սX�ɪ��ɮ׿z�磌��

				result = fcObj.showOpenDialog(FileDialogEX.this);
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

		//�w�q�B���U�^�����sActionEvent�ƥ󪺺�ť������
		btnSave.addActionListener(new ActionListener(){

			//�^�����s���U�ƥ󪺤�k
			public void actionPerformed(ActionEvent e){

				JFileChooser fcObj = 
					new JFileChooser(DEFAULT_FILE_PATH);
				//�ŧi�ɮ׿�ܾ�����

				int result = -1;

				fcObj.setDialogTitle("�x�s�ɮ�");
				//�]�w��ܲ������D

				result = fcObj.showSaveDialog(FileDialogEX.this);
				//����ɮ��x�s��ܲ�			

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

		btnMultiOpen.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){

				JFileChooser fcObj = 
					new JFileChooser(DEFAULT_FILE_PATH);
				//�ŧi�ɮ׿�ܾ�����

				fcObj.setMultiSelectionEnabled(true);
				//�]�w�i�P�ɿ���ƭ��ɮ�

				fcObj.setApproveButtonMnemonic('T');
				//�]�w�T�{������s���U�вŸ�

				fcObj.setDialogTitle("������ǰe�ɮ�...");
				//�]�w��ܲ����D

				int result = fcObj.showDialog(FileDialogEX.this, "�ǰe(T)");
				//�]�w��ܦۭq��ܲ�����

				if(result == JFileChooser.APPROVE_OPTION){
					File[] aryFile = fcObj.getSelectedFiles();
					//���o�ɮ׿�ܾ�������ɮ�

					String strShow = "����ɮ� : ";

					for(File elm: aryFile)
						strShow = strShow + elm.getName() + ", ";
						//�N����ɮת��W�٦�s�b�@�_

					lbResult.setText(strShow);
					//�]�w������ܶ}���ɮת��W��
				}
			}
		});

		JPanel plBtn = new JPanel(
				new FlowLayout(FlowLayout.CENTER, 10, 10));
		//�ŧi��m���s��JPanel�e��

		plBtn.add(btnOpen); //�N���s�[�J�e��
		plBtn.add(btnSave);
		plBtn.add(btnMultiOpen);

		Container cp = getContentPane(); //���o���e����
		cp.add(plBtn); //�N�]�t���s��JPanel�e���[�J���e����
		cp.add(lbResult, BorderLayout.SOUTH); //�[�J��ܰT��������
		
		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����|�P�N�ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 110);
		setVisible(true);
	}

	public static void main(String args[]) {
		new FileDialogEX(); //�ŧi�����ج[����
	}
}