import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*; //�ޥΥ]�tChangeListener�������M��

import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class ToggleEX extends JFrame{

	JLabel lbTitle = new JLabel("�]�w����覡");

	JToggleButton tbLeft = new JToggleButton("�a�W"),
							tbCenter = new JToggleButton("�m��",  true),
							tbRight = new JToggleButton("�a�U");
	//�ŧi�������s

	ButtonGroup bgVAlign = new ButtonGroup();
	//���s�s��

	//�w�q�ëŧi�^��ChangeEvent�ƥ󪺺�ť��
	ChangeListener cl = new ChangeListener(){

		public void stateChanged(ChangeEvent e){

			JToggleButton tb = (JToggleButton) e.getSource();
			//���oĲ�o�ƥ󪺤������s

			String command = tb.getActionCommand();
			//���oĲ�o�ƥ󤧤������s���ʧ@�R�O�r��

			//�P�_�ʧ@�R�O�r��H�]�w���Ҥ���r������覡
			if(command.equals("Top"))
				lbTitle.setVerticalAlignment(SwingConstants.TOP);
			else if(command.equals("Center"))
				lbTitle.setVerticalAlignment(SwingConstants.CENTER);
			else if(command.equals("Bottom"))
				lbTitle.setVerticalAlignment(SwingConstants.BOTTOM);
		}	
	};

	ToggleEX(){

		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		//�]�w���Ҥ���r��������V����覡

		JPanel jpVAlign = new JPanel(new GridLayout(3, 1, 5, 5));
		//�ŧi��m�������s��JPanel�e��

		jpVAlign.add(tbLeft); //�N�������s�[�JJPanel��
		jpVAlign.add(tbCenter);
		jpVAlign.add(tbRight);

		bgVAlign.add(tbLeft); //�N�������s�[�JButtonGroup
		bgVAlign.add(tbCenter);
		bgVAlign.add(tbRight);

		Border border = BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(Color.gray, 2),
			"�]�w��r����������覡"
			, TitledBorder.LEFT, TitledBorder.TOP);
		//�ŧi���D�ؽu����, ���D��r�N�a���a�W���, �C�⬰�L��, �e�׬�2

		jpVAlign.setBorder(border); //�]�wJPanel�ϥΪ��ؽu

		tbLeft.setActionCommand("Top"); //�]�w�������s���ʧ@�R�O�r��
		tbCenter.setActionCommand("Center");
		tbRight.setActionCommand("Bottom");

		tbLeft.addChangeListener(cl); //���U�^��ActionEvent�ƥ󪺺�ť��
		tbCenter.addChangeListener(cl);
		tbRight.addChangeListener(cl);
		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new GridLayout(1, 2));
		//�]�w�ϥ�GridLayout�޲z����
		cp.add(lbTitle); //�N����[�J���e����
		cp.add(jpVAlign);

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ToggleEX(); //�ŧi�����ج[����
	}
}