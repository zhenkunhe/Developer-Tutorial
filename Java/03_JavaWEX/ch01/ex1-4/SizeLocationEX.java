import javax.swing.*;  //�ޥ�Swing�M��
import java.awt.*; //�ޥ�AWT�M��

public class SizeLocationEX extends JFrame {

	SizeLocationEX() {
		super("HelloSwing"); //�I�sJFrame���غc�l, �öǤJ�������D

		//STEP 2�B���o�i��m���󪺤��e����
		Container cp = getContentPane();

		//STEP 3�B�ŧi�[�J���������s����
		JButton button = new JButton("Hello Swing!");

		//STEP 4�B�N����[�J����
		cp.add(button);

		Dimension dim = getToolkit().getScreenSize(); //���o�ù��j�p

		setSize((int)dim.getWidth()/5, (int)dim.getHeight()/5); //�]�w�����e�����j�p

		setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);
		//�]�w������ܦb�ù�������

		//STEP 5�B�]�w���������ʧ@�A�վ�����j�p�A�����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���U�����k�W���������s�N���������õ������ε{��������
		
		setVisible(true); //��ܵ���
	}

	public static void main(String args[]) {

		//STEP 1�B�إߵ����ج[
		new SizeLocationEX();
	}
}