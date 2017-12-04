import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class RadioEX extends JFrame{

	JLabel lbTitle = new JLabel("�]�w����覡");

	JRadioButton rbLeft = new JRadioButton("�a��",  true),
							rbCenter = new JRadioButton("�m��"),
							rbRight = new JRadioButton("�a�k");

	ButtonGroup bgHAlign = new ButtonGroup();
	//���s�s��

	//�w�q�ëŧi��ť��
	ActionListener al = new ActionListener(){

		public void actionPerformed(ActionEvent e){

			String command = e.getActionCommand();
			//���oĲ�o�ƥ󤧤��󪺰ʧ@�R�O�r��

			//�P�_�ʧ@�R�O�r��H�]�w���Ҥ���r������覡
			if(command.equals("Left"))
				lbTitle.setHorizontalAlignment(SwingConstants.LEFT);
			else if(command.equals("Center"))
				lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
			else if(command.equals("Right"))
				lbTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		}	
	};

	RadioEX(){

		lbTitle.setVerticalAlignment(SwingConstants.CENTER);
		//�]�w���Ҥ���r����������覡

		JPanel jpHAlign = 
			new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 5));
			//�إߩ�m�֨������JPanel

		jpHAlign.add(rbLeft); //�N��ܶs�[�JJPanel��
		jpHAlign.add(rbCenter);
		jpHAlign.add(rbRight);

		bgHAlign.add(rbLeft); //�N��ܶs�[�JButtonGroup
		bgHAlign.add(rbCenter);
		bgHAlign.add(rbRight);

		Border border = BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(Color.gray, 2),
			"�]�w��r����������覡"
			, TitledBorder.LEFT, TitledBorder.TOP);
		//�ŧi���D�ؽu����, ���D��r�N�a���a�W���, �C�⬰�L��, �e�׬�2

		jpHAlign.setBorder(border); //�]�wJPanel�ϥΪ��ؽu

		rbLeft.setActionCommand("Left"); //�]�w��ܶs���ʧ@�R�O�r��
		rbCenter.setActionCommand("Center");
		rbRight.setActionCommand("Right");

		rbLeft.addActionListener(al); //���U�^��ActionEvent�ƥ󪺺�ť��
		rbCenter.addActionListener(al);
		rbRight.addActionListener(al);

		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new GridLayout(2, 1)); //�]�w�ϥ�GridLayout�޲z����
		cp.add(lbTitle); //�N����[�J���e����
		cp.add(jpHAlign);

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new RadioEX(); //�ŧi�����ج[����
	}
}