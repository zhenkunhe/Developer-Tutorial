import javax.swing.*; //�ޥ�Swing�M��
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class WindowEventEX extends JFrame {

	JLabel lbMsg = new JLabel("WindowEvent�ƥ󪺦^��",
													JLabel.CENTER);

	//�H�ΦW���O��@WindowListener����, 
	//�ëŧi��ťWindowEvent�ƥ󪺺�ť������
	WindowListener wlFrame = new WindowListener(){

		//�^�������i�J���Ī��A(���o�J�I)����k
		public void windowActivated(WindowEvent e){
			System.out.println("WindowListener : �������o�J�I");
		}
		//�^�����������欰����k
		public void windowClosed(WindowEvent e){
			System.out.println("WindowListener : �����w�g����");
		}
		//�^���������b����������k
		public void windowClosing(WindowEvent e){
			System.out.println("WindowListener : �������b������");
		}
		//�^���������h�J�I����k
		public void windowDeactivated(WindowEvent e){
			System.out.println("WindowListener : ��������");
		}
		//�^�������q�ϥܤ��٭쪺��k
		public void windowDeiconified(	WindowEvent e){
			System.out.println("WindowListener : �����q�ϥܤ��٭�");
		}
		//�^�������ϥܤƪ���k
		public void windowIconified(WindowEvent e){
			System.out.println("WindowListener : �����ϥܤ�");
		}
		//�^���}�ҵ�������k
		public void windowOpened(WindowEvent e){
			System.out.println("WindowListener : �����w�g�}��");
		}
	};

	//�H�ΦW���O��@WindowFocusListener����, �ëŧi��ť������
	WindowFocusListener wflFrame = new WindowFocusListener(){
		//�^���������o�J�I
		public void windowGainedFocus(WindowEvent e){
			System.out.println("WindowFocusListener : �������o�J�I");
		}
		//�^���������h�J�I
		public void windowLostFocus(WindowEvent e){
			System.out.println("WindowFocusListener : �������o�J�I");
		}
	};

	//�H�ΦW���O��@WindowStateListener����, �ëŧi��ť������
	WindowStateListener wslFrame = new WindowStateListener(){
		//�^���������A����
		public void windowStateChanged(WindowEvent e){
			System.out.println("WindowStateListener : �������A����");
		}
	};

	WindowEventEX(){

		addWindowListener(wlFrame);
		//���U��wlFrame��ť������WindowEvent

		addWindowFocusListener(wflFrame);
		//���U��wflFrame��ť������WindowEvent

		addWindowStateListener(wslFrame);
		//���U��wslFrame��ť������WindowEvent

		add(lbMsg); //�N��ܰT�������ҥ[�J���e����

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250, 150);
		setVisible(true);
	}

	public static void main(String args[]) {
		new WindowEventEX(); //���͵����ج[����
	}
}