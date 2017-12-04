import javax.swing.*; //�ޥ�Swing�M��
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class MouseEX extends JFrame {

	JButton clickME = new JButton("�Ы���!"); //�إߤ���
	JLabel lbMouse = new JLabel("�ƹ��ƥ�T��"), //�إ���ܰT��������
				lbMouseButton = new JLabel("���U����");

	//�H�ΦW���O���覡��@MouseListener����, 
	//�ŧi�^���ƹ��ƥ󪺺�ť��
	MouseListener mlBtn = new MouseListener() {

		private int clickCount = 0, //�O�����s�Q���U���ƪ��ݩ�
						 doubleClickCount = 0; //�O�����s�Q���U���ƪ��ݩ�

		//�B�z���@�U�ƹ�����ʧ@����k
		public void mouseClicked(MouseEvent e) {

			if(e.getClickCount() == 2)
				doubleClickCount++;

			((JButton) e.getSource()).setText("���U�@�U : " +
				(clickCount++) + "�� |  �s���⦸ : " + doubleClickCount + "��");
		}

		//�B�z�ƹ���жi�J���󪺤�k
		public void mouseEntered(MouseEvent e) {
			((JButton) e.getSource()).setText("�ƹ���жi�J���s�d��");
		}

		//�B�z�ƹ�������}���󪺤�k
		public void mouseExited(MouseEvent e) {
			((JButton) e.getSource()).setText("�ƹ�������}���s�d��");
		}

		//�B�z���U�ƹ�����ʧ@����k
		public void mousePressed(MouseEvent e){

			switch(e.getButton()){ //���o�ƹ��Q���U������
				case MouseEvent.BUTTON1 :
					lbMouseButton.setText("�ƹ�����Q���U");
					break;
				case MouseEvent.BUTTON2 :
					lbMouseButton.setText("�ƹ�������Q���U");
					break;
				case MouseEvent.BUTTON3 :
					lbMouseButton.setText("�ƹ��k��Q���U");
					break;
				case MouseEvent.NOBUTTON :
					lbMouseButton.setText("�S���ƹ�����Q���U");
					break;
			}
		}

		//�B�z��}�ƹ�����ʧ@����k
		//�Y�Ϥ��B�z�ƥ�������w�q�Ū��^����k
		public void mouseReleased(MouseEvent e){ }
	};

	//�H�ΦW���O���覡��@MouseMotionListener����, 
	//�ŧi�^���ƹ��ƥ󪺺�ť��
	MouseMotionListener mmlFrame = new MouseMotionListener(){
		public void mouseDragged(MouseEvent e){ //�ƹ��즲
			lbMouse.setText("�ƹ��b�����d�� [�즲] , ��Ц�m�b ( "
										+ e.getX()  + ", " + e.getY() + " )");
		}

		public void mouseMoved(MouseEvent e){ //�ƹ�����
			lbMouse.setText("�ƹ��b�����d�� [����]  , ��Ц�m�b ( "
										+ e.getX()  + ", " + e.getY() + " )");
		}
	};

	MouseEX(){

		Box bxBtn = new Box(BoxLayout.X_AXIS);
		bxBtn.add(Box.createHorizontalGlue());
		bxBtn.add(clickME); //�[�J���s����
		bxBtn.add(Box.createHorizontalGlue());

		Container cp = getContentPane(); //���o���e����

		Box bxLabel = new Box(BoxLayout.Y_AXIS);

		bxLabel.add(lbMouse); //�N���Ҥ���[�JBox�e��
		bxLabel.add(Box.createVerticalStrut(10));
		bxLabel.add(lbMouseButton);

		cp.add(bxBtn); //�N�]�t���s����Box�e���[�J���e����
		cp.add(bxLabel, BorderLayout.SOUTH);
		//�N�]�t���Ҥ���Box�e���[�J����

		clickME.addMouseListener(mlBtn);
		//���U��mlBtn��ťclickME���󪺷ƹ��ƥ�

		addMouseMotionListener(mmlFrame);
		//���U��mmlFrame��ť�������ƹ����ʨƥ�

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 250);
		setVisible(true);
	}

	public static void main(String args[]) {
		new MouseEX(); //���͵����ج[����
	}
}