import javax.swing.*;  //�ޥήM��
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class LabelIconEX extends JFrame{

	LabelIconEX(){

		JLabel lbBitcCenter = new JLabel("��r�m��ϥܤ���",
			new ImageIcon(".\\Icon\\Bitc.gif"), SwingConstants.CENTER);
		//�P����ܤ�r�P�ϥܪ�����, �ϥܸm��

		lbBitcCenter.setHorizontalTextPosition(SwingConstants.CENTER);
		//�]�w��r��������V�۹��ϥܱĸm�����

		JLabel lbBitcTop = new JLabel("��r�m��ϥܤ����B�a�W", 
			new ImageIcon(".\\Icon\\Bitc.gif"), SwingConstants.CENTER);
		//�P����ܤ�r�P�ϥܪ�����, �ϥܸm��

		lbBitcTop.setHorizontalTextPosition(SwingConstants.CENTER);
		//�]�w��r��������V�۹��ϥܱĸm�����

		lbBitcTop.setVerticalTextPosition(SwingConstants.TOP);
		//�]�w��r��������V�۹��ϥܱĸm�W���

		JLabel lbBitcBottom = new JLabel("��r�m��ϥܤ����B�a�U", 
			new ImageIcon(".\\Icon\\Bitc.gif"), SwingConstants.CENTER);
		//�P����ܤ�r�P�ϥܪ�����, �ϥܸm��

		lbBitcBottom.setHorizontalTextPosition(SwingConstants.CENTER);
		//�]�w��r��������V�۹��ϥܱĸm�����

		lbBitcBottom.setVerticalTextPosition(SwingConstants.BOTTOM);
		//�]�w��r��������V�۹��ϥܱĸm�U���

		JLabel lbBitcLeft = new JLabel("��r�m��ϥܥ���B�a�U", 
			new ImageIcon(".\\Icon\\Bitc.gif"),
			SwingConstants.CENTER);
		//�P����ܤ�r�P�ϥܪ�����, �ϥܸm��

		lbBitcLeft.setHorizontalTextPosition(SwingConstants.LEFT);
		//�]�w��r��������V�۹��ϥܱĸm�����

		lbBitcLeft.setVerticalTextPosition(SwingConstants.BOTTOM);
		//�]�w��r��������V�۹��ϥܱĸm�U���

		JLabel lbBitcRight = new JLabel("��r�m��ϥܥk��B�a�W", 
			new ImageIcon(".\\Icon\\Bitc.gif"), SwingConstants.CENTER);
		//�P����ܤ�r�P�ϥܪ�����, �ϥܸm��

		lbBitcRight.setHorizontalTextPosition(SwingConstants.RIGHT);
		//�]�w��r��������V�۹��ϥܱĸm�k���

		lbBitcRight.setVerticalTextPosition(SwingConstants.TOP);
		//�]�w��r��������V�۹��ϥܱĸm�W���

		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new GridLayout(2, 3, 10, 10));
		//���o�G���޲z��

		cp.add(lbBitcCenter); //�N����[�J����
		cp.add(lbBitcTop);
		cp.add(lbBitcBottom);
		cp.add(lbBitcLeft);
		cp.add(lbBitcRight);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���������N�w�]�����{��
		pack(); //�H�A��j�p��ܵ���
		setVisible(true); //��ܵ����ج[
	}

	public static void main(String args[]) {
		new LabelIconEX(); //�ŧi�����ج[����
	}
}