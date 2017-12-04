import javax.swing.*;  //�ޥ�Swing�M��
import java.awt.*;

public class ComponentEX extends JFrame {

	ComponentEX() {

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new GridLayout(2, 1));
		//�]�w���e�����ϥ�GridLayout�G���޲z��

		JPanel jpFirst = new JPanel(); //�ŧiJPanel�e��

		jpFirst.setLayout(null); //�]�w�e�����ϥΧG	���޲z��

		JButton btnA = new JButton("���sA"); //�ŧi���s����
		JButton btnB = new JButton("���sB");

		Rectangle rec = new Rectangle(50, 50, 100, 100);
		//�ŧi��ܦ�m�P�j�p��Rectangle����

		btnA.setBounds(rec); //�]�w���s���󪺦�m�P�j�p

		btnB.setSize(new Dimension(100,100)); //�]�w���s�j�p
		//btnB.setPreferredSize(new Dimension(100, 100));
		btnB.setLocation(200, 50); //�]�w�����m

		jpFirst.add(btnA); //�N����[�J�e��
		jpFirst.add(btnB);

		JPanel jpSecond = new JPanel();

		JButton btnC = new JButton("���sC");
		JButton btnD = new JButton("���sD");

		btnC.setMinimumSize(new Dimension(50, 50)); //�]�w�̤p�j�p
		btnC.setPreferredSize(new Dimension(75, 75)); //�]�w�ߦn�j�p
		btnC.setMaximumSize(new Dimension(100, 100)); //�]�w�̤j�j�p

		btnD.setSize(new Dimension(100,100)); //�]�w���󪺤j�p

		jpSecond.add(btnC); //�N����[�J�e��
		jpSecond.add(btnD);

		cp.add(jpFirst); //�N�e���[�J���e����
		cp.add(jpSecond);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���U�����k�W���������s�N���������õ������ε{��������
		setSize(350, 350); //�]�w�������j�p
		setVisible(true); //��ܵ���
	}

	public static void main(String args[]) {
		new ComponentEX(); //�إߵ����ج[����
	}
}