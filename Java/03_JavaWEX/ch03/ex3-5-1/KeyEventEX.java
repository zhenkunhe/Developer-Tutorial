import javax.swing.*; //�ޥ�Swing�M��
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class KeyEventEX extends JFrame {

	JLabel lbKey = new JLabel("��L�ƥ�"),
				 lbInput = new JLabel("��J�r��");

	//�H�ΦW���O���覡��@KeyListener����, 
	//�ŧi�^���ƹ��ƥ󪺺�ť��
	KeyListener klFrame = new KeyListener() {

		//�B�z��L����Q���U����k
		public void keyPressed(KeyEvent e) {
			lbKey.setText(" [" + KeyEvent.getKeyText(e.getKeyCode()) + "] ��Q���U");
		}

		//�B�z��L����Q��}����k
		public void keyReleased(KeyEvent e) {
			lbKey.setText(" [" + KeyEvent.getKeyText(e.getKeyCode()) + "] ��Q��}");
		}

		//�B�z����L��J�r������k
		public void keyTyped(KeyEvent e) {
			lbInput.setText("��J '" + e.getKeyChar() + "'");
		}
	};

	KeyEventEX(){

		Container cp = getContentPane(); //���o���e����

		cp.add(lbKey); //�N��ܰT�������ҥ[�J���e����
		cp.add(lbInput, BorderLayout.SOUTH);

		addKeyListener(klFrame);
		//���U��klFrame��ť���������쪺��L�ƥ�

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new KeyEventEX(); //���͵����ج[����
	}
}