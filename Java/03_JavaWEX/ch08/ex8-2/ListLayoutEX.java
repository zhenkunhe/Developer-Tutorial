import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*; //�ޥδ���File���O���M��

public class ListLayoutEX extends JFrame {

	JList ltFile = new JList(); //�ŧi�M����

	//�H�ΦW���O���覡�w�q�ëŧi��ť������
	ActionListener al = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			ltFile.setLayoutOrientation(
				Integer.parseInt(e.getActionCommand()));
			//�N�ʧ@�R�O�r���ഫ������M�����ﶵ�ƦC�覡����ƭ�
		}
	};

	ListLayoutEX(){
		File flCur = new File("C:\\"); //���C�X�ɮת����|
		File[] files = flCur.listFiles();
		//���o���|�U�Ҧ��ɮת�File����

		ltFile.setListData(files); //�]�w�M��������ܤ��e
		ltFile.setVisibleRowCount(5);

		JRadioButton rdVertical = new JRadioButton("�����ƦC", true),
				rdHorizontalWrap = new JRadioButton("�����ƦC���_��"),
				rdVerticalWrap = new JRadioButton("�����ƦC���_��");
		//�ŧi����M�������ﶵ�ƦC�覡����ܫ��s

		rdVertical.setActionCommand(String.valueOf(JList.VERTICAL));
		rdHorizontalWrap.setActionCommand(
							String.valueOf(JList.HORIZONTAL_WRAP));
		rdVerticalWrap.setActionCommand(
			String.valueOf(JList.VERTICAL_WRAP));
		//�N����M�����ﶵ�ƦC�覡���`���ഫ���r��, 
		//�ó]�w���ʧ@�R�O�r��

		rdVertical.addActionListener(al);
		rdHorizontalWrap.addActionListener(al);
		rdVerticalWrap.addActionListener(al);
		//���U�^��ActionEvent�ƥ󪺺�ť��

		ButtonGroup bg = new ButtonGroup(); //�ŧi���s�s��
		bg.add(rdVertical); //�N��ܫ��s�[�J���s�s��
		bg.add(rdHorizontalWrap);
		bg.add(rdVerticalWrap);

		JScrollPane spList = new JScrollPane(ltFile);
		//�H�M�����ŧi���b����

		JPanel jpLayout = new JPanel(
			new FlowLayout(FlowLayout.CENTER, 10, 5));
		jpLayout.add(rdVertical); //�N��ܫ��s�[�J����
		jpLayout.add(rdHorizontalWrap);
		jpLayout.add(rdVerticalWrap);
		
		Container cp = getContentPane(); //���o���e����
		cp.add(spList); //�N����[�J���e����
		cp.add(jpLayout,BorderLayout.NORTH);

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(530, 220);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ListLayoutEX(); //�ŧi�����ج[����
	}
}