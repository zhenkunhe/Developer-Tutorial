import javax.swing.*;  //�ޥήM��
import java.awt.*;

import javax.swing.border.*; //�w�q�ؽu���O���M��

public class TitledBorderEX extends JFrame{

	TitledBorderEX(){

		JLabel lbTitledTop = 
						new JLabel("���D(Titled) : �w�]", 
											JLabel.CENTER),
					lbTitledBottom = 
						new JLabel("���D(Titled) : �ꨤ�u���ؽu�B���ݮؽu���U", 
											JLabel.CENTER);
		//�ŧi����

		TitledBorder titledbordertop = new TitledBorder("���D��r");

		lbTitledTop.setBorder(titledbordertop); //�]�w���Ҩϥμ��D�ؽu

		LineBorder lineborderround = 
								new LineBorder(Color.GRAY, 1, true);
		//�ŧi�u���ؽu

		TitledBorder titledborderbottom = 
			BorderFactory.createTitledBorder(
					lineborderround, "���D��r",
					TitledBorder.CENTER, TitledBorder.BELOW_BOTTOM);
		//�HBorderFactory���O���R�A��k�إ߼��D�ؽu
	
		titledborderbottom.setTitleColor(Color.blue);
		//�]�w���D��r���C��

		lbTitledBottom.setBorder(titledborderbottom);
		//�]�w���Ҩϥμ��D�ؽu

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new GridLayout(1, 2, 10, 10));
		//�]�w���e�������G���޲z������

		cp.add(lbTitledTop); //�N���ҥ[�J����
		cp.add(lbTitledBottom);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���������N�w�]�����{��

		setSize(600, 120); //�H�̾A�j�p��ܵ���
		setVisible(true); //��ܵ����ج[
	}

	public static void main(String args[]) {
		new TitledBorderEX(); //�ŧi�����ج[����
	}
}