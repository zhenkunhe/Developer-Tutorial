import javax.swing.*; //�ޥ�Swing�M��
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class AnonymousEX extends JFrame{

	JButton clickME = new JButton("�Ы���!"); //�إߤ���
	JLabel label = new JLabel("���s���Q���U");

	int clickCount = 0; //���s�Q���U������

	//�ŧi�éw�q�^��ActionEvent�ƥ󪺰ΦW�������O
	ActionListener al = new ActionListener() {
		public void actionPerformed(ActionEvent e){
			clickCount ++; //�W�[���s�Q���U����
		
			//���o�o�X�ƥ󤧤��󪺰ʧ@�R�O�r��, �ó]�w���Ҫ����e
			label.setText("[" + e.getActionCommand()
				+ "] ���s�Q���F" + clickCount + "��");
		}	
	}; //�Ъ`�N����������

	AnonymousEX(){
		Container cp = getContentPane(); //���o���e����

		cp.add(clickME, BorderLayout.CENTER); //�N����[�J����
		cp.add(label, BorderLayout.SOUTH);

		clickME.addActionListener(al);
		//���U��al��ťclickME����ActionEvent�ƥ�

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public static void main(String args[]) {
		new AnonymousEX(); //���͵����ج[����
	}
}