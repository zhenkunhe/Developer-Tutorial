import javax.swing.*; //�ޥ�Swing�M��
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class InnerEX extends JFrame{

	JButton clickME = new JButton("�Ы���!"); //�إߤ���
	JLabel label = new JLabel("���s���Q���U");

	int clickCount = 0; //���s�Q���U������

	class alClickME implements ActionListener {
		public void actionPerformed(ActionEvent e){
			clickCount ++; //�W�[���s�Q���U����

			//���o�o�X�ƥ󤧤��󪺰ʧ@�R�O�r��, �ó]�w���Ҫ����e
			label.setText("[" + e.getActionCommand()
				+ "] ���s�Q���F" + clickCount + "��");
		}	
	}

	InnerEX(){
		Container cp = getContentPane(); //���o���e����

		cp.add(clickME, BorderLayout.CENTER); //�N����[�J����
		cp.add(label, BorderLayout.SOUTH);

		alClickME al = new alClickME(); //�ŧi��ť�����O����
		clickME.addActionListener(al);
		//���U��al��ťclickME����ActionEvent�ƥ�

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public static void main(String args[]) {
		new InnerEX(); //���͵����ج[����
	}
}