import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��
import java.io.*;

import javax.swing.filechooser.*;
//�ޥΩw�qFileView��H���O
//�PFileSystemView��H���O���M��

public class UserFileDlgEX extends JFrame{

	private final static 
		String DEFAULT_FILE_PATH = "C:\\JavaWEX";
	//�w�]���ɮ��s�����|

	JLabel lbResult = new JLabel("����ɮ� : ");
	//�ŧi�Q����ɮת�����

	UserFileDlgEX(){

		JButton btnOpen = new JButton("�}������");
		//�ŧi�I�s�ɮ׹�ܲ������s

		//�w�q�B���U�^�����sActionEvent�ƥ󪺺�ť������
		btnOpen.addActionListener(new ActionListener(){

			//�^�����s���U�ƥ󪺤�k
			public void actionPerformed(ActionEvent e){

				FileDetailDialog fcObj = 
					new FileDetailDialog(DEFAULT_FILE_PATH);
				//�ŧi�ɮ׿�ܾ�����

				fcObj.setDialogTitle("�}������");
				//�]�w��ܲ������D

				fcObj.setPreferredSize(new Dimension(500, 500)); //�]�w��ܲ������n�j�p

				int result = fcObj.showOpenDialog(UserFileDlgEX.this);
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
		new UserFileDlgEX(); //�ŧi�����ج[����
	}
}

//�H�~��JFileChooser���覡
//�ۭq�i����ɮ׸ԲӸ�ƪ�FileDetailDialog���O
class FileDetailDialog extends JFileChooser {

	JList ltFileDetail = new JList();
	//�ŧi����ɮ׸ԲӸ�ƪ�JList����

	FileDetailDialog(String path){ super(path); }
	//�غc�l

	//�л\�إ߹�ܲ���createDialog()��k
	protected JDialog createDialog(Component parent)
							throws HeadlessException {

		JDialog dialog = super.createDialog(parent);
		//�I�s��¦���O��createDialog()��k

		setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		//�]�w�i�H�P�ɿ���ɮ׻P��Ƨ�

		Container cpDlg = dialog.getContentPane();
		//���o��ܲ������e����

		JScrollPane sp = new JScrollPane(ltFileDetail);
		//�HJList����ŧi���b����

		sp.setBorder(new javax.swing.border.CompoundBorder( 
			new javax.swing.border.EmptyBorder(5, 5, 5, 5),
			new javax.swing.plaf.metal.MetalBorders.ScrollPaneBorder()));
		//�]�w���b�����ϥΪ��զX�~��, �~�ج��e�׬�5���ťե~��
		//���ج�Swing�����b�����w�]�ؽu

		cpDlg.add(sp, BorderLayout.SOUTH);
		//�N�]�tJList���󪺱��b�����[�J���e������SOUTH��m

		//���U��ťPropertyChangeEvent�ƥ󪺺�ť������
		addPropertyChangeListener(
			new java.beans.PropertyChangeListener(){

			//�^���ʽ���ܰʧ@
			public void propertyChange(
				java.beans.PropertyChangeEvent e){

				//�P�_�޵o�ƥ�ʽ�W�٬O�_��
				//JFileChooser.SELECTED_FILE_CHANGED_PROPERTY
				if(e.getPropertyName() == 
					JFileChooser.SELECTED_FILE_CHANGED_PROPERTY){
					
					DefaultListModel dlm = new DefaultListModel();
					//�ŧiJList����ϥΪ����e�ҫ�����

					File f = getSelectedFile();
					//���o��ܲ�������ɮ�

					//�Y����ɮ׬�null, �h�פ�����k
					if(f == null) return;

					FileSystemView fsv =
						FileSystemView.getFileSystemView();
					//���o���ѥثe�t�Τ�
					//�ɮשθ�Ƨ�����ƪ�FileSystemView����
					
					//�I�sFileSystemView���󤧤�k���o�ɮת����
					dlm.addElement("�Ϻи˸m : "
						+ (fsv.isDrive(f)? "�O" : "�_"));
					dlm.addElement("������ : "
						+ (fsv.isHiddenFile(f)? "�O" : "�_"));
					dlm.addElement("�t�ήڸ�Ƨ� : "
						+ (fsv.isFileSystemRoot(f)? "�O" : "�_"));
					dlm.addElement("�n�о� : "
						+ (fsv.isFloppyDrive(f)? "�O" : "�_"));

					if(f.isDirectory()){ //�P�_File����O�_����Ƨ�
						int count = f.listFiles().length;
						//���o��Ƨ����]�t���ɮ׻P��Ƨ����Ӽ�

						dlm.addElement("��Ƨ��U�ɮשθ�Ƨ����Ӽ� : "
															+ count);
						//��ܸ�Ƨ����ɮת��Ӽ�
					}

					ltFileDetail.setModel(dlm);
					//�]�wJList����ϥΥ]�t�ɮ׸�ƪ��ҫ�����
				}
			}
		});

		return dialog; //�Ǧ^�����إߪ���ܲ�
	}
}
