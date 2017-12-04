import javax.swing.*; //�ޥ�Swing�M��
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class FocusKeyEX extends JFrame {

	JLabel lbKey = new JLabel("��L�ƥ� : "), //�ŧi���Ҥ���
				 lbInput = new JLabel("��J�r�� : ");

	JButton btnOne = new JButton("���s�@"), //�ŧi���s����
				  btnTwo = new JButton("���s�G");

	//��@FocusListener��������ť�����O
	class BtnFocusListener implements FocusListener {

		private String btnName;

		public BtnFocusListener(String name){ //�غc�l
			btnName = name;
		}

		public void focusGained(FocusEvent e){ //�B�z���o�J�I����k
			((JButton) e.getSource()).setText(" [" 
				+ btnName + "] ���o�J�I");
		}

		public void focusLost(FocusEvent e){ //�B�z���h�J�I����k
			((JButton) e.getSource()).setText(" [" 
				+ btnName + "] ���h�J�I");
		}
	};

	//�H�ΦW���O���覡��@KeyListener����, 
	//�ŧi�^���ƹ��ƥ󪺺�ť��
	KeyListener klButton = new KeyListener() {

		//�B�z��L����Q���U����k
		public void keyPressed(KeyEvent e) {
			lbKey.setText("��L�ƥ� : [" + 
				KeyEvent.getKeyText(e.getKeyCode()) + "] ��Q���U");
		}

		//�B�z��L����Q��}����k
		public void keyReleased(KeyEvent e) {
			lbKey.setText("��L�ƥ� : [" + 
				KeyEvent.getKeyText(e.getKeyCode()) + "] ��Q��}");
		}

		//�B�z����L��J�r������k
		public void keyTyped(KeyEvent e) {
			lbInput.setText("��J�r�� : '" + e.getKeyChar() + "'");
		}
	};

	FocusKeyEX(){

		btnOne.addFocusListener(new BtnFocusListener("���s�@"));
		btnTwo.addFocusListener(new BtnFocusListener("���s�G"));
		//���U��ť���s�J�I�ƥ󪺺�ť������

		btnTwo.addKeyListener(klButton);
		//���U��klButton��ť���������쪺��L�ƥ�

		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new GridLayout(2, 2, 5, 5));
		//�]�w�ϥ�GridLayout�޲z�����G��

		cp.add(btnOne); //�N���s����[�J���e����
		cp.add(btnTwo);

		cp.add(lbKey); //�N��ܰT�������ҥ[�J���e����
		cp.add(lbInput);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 130);
		setVisible(true);
	}

	public static void main(String args[]) {
		new FocusKeyEX(); //���͵����ج[����
	}
}