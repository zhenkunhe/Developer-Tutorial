import javax.swing.*;  //�ޥ�Swing�M��
import java.awt.*; //�ޥ�AWT�M��

public class HelloSwing {

	public static void main(String args[]) {

		//STEP 1�B�إߵ����ج[
		JFrame frame = new JFrame("HelloSwing");

		//STEP 2�B���o�i��m���󪺤��e����
		Container cp = frame.getContentPane();

		//STEP 3�B�ŧi�[�J���������s����
		JButton button = new JButton("Hello Swing!");

		//STEP 4�B�N����[�J����
		cp.add(button);

		//STEP 5�B�]�w���������ʧ@�A�վ�����j�p�A����ܵ���
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���U�����k�W���������s�N���������õ������ε{��������

		frame.pack(); //�վ�����j�p, �_�h�N����ܵ��������D�C
		frame.setVisible(true); //��ܵ���
	}
}