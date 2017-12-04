import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*; //�ޥδ���File���O���M��

public class ListModelEX extends JFrame {

	JList ltFile = new JList(); //�ŧi�M����

	DefaultListModel dlmFile = new DefaultListModel(),
								 dlmDir = new DefaultListModel(),
								 dlmAll = new DefaultListModel();
	//�ŧi�]�t�M�����ﶵ���e��Model����

	//�H�ΦW���O���覡�w�q�ëŧi��ť������
	ActionListener al = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			//�B�ΰʧ@�R�O�r��P�_���ϥΪ�Model����
			if(e.getActionCommand().equals("�ɮ�")){
				ltFile.setModel(dlmFile);
				//�]�w�ϥΥ]�t�ɮצW�٪�Model����
			}
			else if(e.getActionCommand().equals("��Ƨ�")){
				ltFile.setModel(dlmDir);
				//�]�w�ϥΥ]�t��Ƨ��W�٪�Model����
			}
			else{
				ltFile.setModel(dlmAll);
				//�]�w�ϥΥ]�t�ɮ׻P��Ƨ��W�٪�Model����
			}
		}
	};

	ListModelEX(){
		File flCur = new File("C:\\"); //���C�X�ɮת����|
		File[] files = flCur.listFiles();
		//���o���|�U�Ҧ��ɮת�File����

		//�B�Υ[�j��for�j��إߦU��Model����
		for(File elm : files){
			dlmAll.addElement(elm.getName());
			//�[�J�ɮשθ�Ƨ����W��

			if(elm.isDirectory()){
				dlmDir.addElement(elm.getName());
				//�[�J��Ƨ����W��
			}
			else{
				dlmFile.addElement(elm.getName());
				//�[�J�ɮת��W��
			}
		}

		ltFile.setModel(dlmFile); //�]�w�M��������ܤ��e
		ltFile.setLayoutOrientation(JList.VERTICAL_WRAP);
		//�]�w�ﶵ���]�w�覡

		ltFile.setVisibleRowCount(5); //�]�w�i���C�Ƭ�5�C

		JRadioButton rdFile = new JRadioButton("����ɮ�", true),
								rdDir = new JRadioButton("��ܸ�Ƨ�"),
								rdAll = new JRadioButton("�������");
		//�ŧi����M�������ﶵ�ƦC�覡����ܫ��s

		rdFile.setActionCommand("�ɮ�");
		rdDir.setActionCommand("��Ƨ�");
		rdAll.setActionCommand("����");
		//�]�w����M�������e���ʧ@�R�O�r��

		rdFile.addActionListener(al);
		rdDir.addActionListener(al);
		rdAll.addActionListener(al);
		//���U�^��ActionEvent�ƥ󪺺�ť��

		ButtonGroup bg = new ButtonGroup(); //�ŧi���s�s��
		bg.add(rdFile); //�N��ܫ��s�[�J���s�s��
		bg.add(rdDir);
		bg.add(rdAll);

		JScrollPane spList = new JScrollPane(ltFile);
		//�H�M�����ŧi���b����

		JPanel jpLayout = new JPanel(
			new FlowLayout(FlowLayout.CENTER, 10, 5));
		jpLayout.add(rdFile); //�N��ܫ��s�[�J����
		jpLayout.add(rdDir);
		jpLayout.add(rdAll);
		
		Container cp = getContentPane(); //���o���e����
		cp.add(spList); //�N����[�J���e����
		cp.add(jpLayout,BorderLayout.NORTH);

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(530, 220);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ListModelEX(); //�ŧi�����ج[����
	}
}