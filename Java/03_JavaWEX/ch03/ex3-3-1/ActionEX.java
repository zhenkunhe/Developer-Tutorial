import javax.swing.*; //�ޥ�Swing�M��
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class ActionEX extends JFrame
	implements ActionListener { //��@ActionListener��������ť�����O

	JButton clickME = new JButton("�Ы���!"); //�إߤ���
	JLabel label = new JLabel("���s���Q���U");

	int clickCount = 0; //���s�Q���U������

	ActionEX(){
		Container cp = getContentPane(); //���o���e����

		cp.add(clickME, BorderLayout.CENTER); //�N����[�J����
		cp.add(label, BorderLayout.SOUTH);

		clickME.addActionListener(this); //�N�ƥ�e���������ج[��ť

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(150, 90);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e){
			clickCount ++; //�W�[���s�Q���U����
		
			//���o�o�X�ƥ󤧤��󪺰ʧ@�R�O�r��, �ó]�w���Ҫ����e
			label.setText("[" + e.getActionCommand()
				+ "] ���s�Q���F" + clickCount + "��");
	}

	public static void main(String args[]) {
		new ActionEX(); //���͵����ج[����
	}
}