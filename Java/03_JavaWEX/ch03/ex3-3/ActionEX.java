import javax.swing.*; //�ޥ�Swing�M��
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class ActionEX extends JFrame { 

	class ButtonListener extends JButton //�~��JButton���O
	 implements ActionListener { //��@ActionListener��������ť�����O

		ButtonListener(String name) {
			super(name);

			addActionListener(this); //�N�ƥ�e�����ۤv��ť
		}
		public void actionPerformed(ActionEvent e){
			clickCount ++; //�W�[���s�Q���U����
		
			//���o�o�X�ƥ󤧤��󪺰ʧ@�R�O�r��, �ó]�w���Ҫ����e
			label.setText("[" + e.getActionCommand()
				+ "] ���s�Q���F" + clickCount + "��");
		}
	}

	ButtonListener clickME = new ButtonListener("�Ы���!"); //�إߤ���
	JLabel label = new JLabel("���s���Q���U");

	int clickCount = 0; //���s�Q���U������

	ActionEX(){
		Container cp = getContentPane(); //���o���e����

		cp.add(clickME, BorderLayout.CENTER); //�N����[�J����
		cp.add(label, BorderLayout.SOUTH);		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(150, 90);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ActionEX(); //���͵����ج[����
	}
}