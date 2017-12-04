import javax.swing.*;  //�ޥήM��
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class LabelEX extends JFrame {

	LabelEX() {

		JLabel lbRight = new JLabel("�a�k", JLabel.RIGHT);
		JLabel lbLeft = new JLabel("�a��", JLabel.LEFT);
		JLabel lbCenter = new JLabel("�m��", JLabel.CENTER);

		JLabel lbLeadingTop = new JLabel("�a�e�t", JLabel.LEADING);
		lbLeadingTop.setVerticalAlignment(SwingConstants.TOP);
		//�]�w������V�a�W���

		JLabel lbTrailingBottom = new JLabel("�a��t", JLabel.TRAILING);
		lbTrailingBottom.setVerticalAlignment(SwingConstants.BOTTOM);
		//�]�w������V�a�U���
	
		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new GridLayout(2, 3, 10, 10)); //���o�G���޲z��

		cp.add(lbLeft); //�N����[�J����
		cp.add(lbCenter);
		cp.add(lbRight);
		cp.add(lbLeadingTop);
		cp.add(lbTrailingBottom);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���������N�w�]�����{��
		setSize(300, 250); //�]�w�������j�p��300x250
		setVisible(true); //��ܵ����ج[
	}

	public static void main(String args[]) {
		new LabelEX(); //�ŧi�����ج[����
	}
}