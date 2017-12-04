import javax.swing.*; //�ޥ�Swing�M��
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class AdapterEX extends JFrame{

	JButton clickME = new JButton("�Ы���!"); //�إߤ���
	JLabel lbPos = new JLabel("��ܷƹ������T��");

	MouseListener mlFrame = new MouseListener(){
		public void mouseClicked(MouseEvent e){
			lbPos.setText("�b (" + e.getX() + ", " + e.getY() + ") ���U�ƹ�");
		}
		public void mouseEntered(MouseEvent e){
			lbPos.setText("�ƹ���жi�J����");
		}
		public void mouseExited(MouseEvent e){
			lbPos.setText("�ƹ�������}����");
		}

		//�Y�Ϥ��B�z�ƥ�������w�q�Ū��^����k
		public void mousePressed(MouseEvent e){ }
		public void mouseReleased(MouseEvent e){ }	
	};

	//�B�ΰΦW�������O, �H�~��MouseAdaptor���覡�w�q��ť�����O, �ç����ŧi
	MouseAdapter maBtn = new MouseAdapter(){

		int clickCount = 0; //�O�����s�Q���U���ƪ��ݩ�

		public void mouseClicked(MouseEvent e){
			((JButton) e.getSource()).setText(
				"���s�Q���F" + (clickCount++) + "��");
		}
		public void mouseEntered(MouseEvent e){
			((JButton) e.getSource()).setText("�ƹ���жi�J���s�d��");
		}
		public void mouseExited(MouseEvent e){
			((JButton) e.getSource()).setText("�ƹ�������}���s�d��");
		}	
	};

	AdapterEX(){
		Container cp = getContentPane(); //���o���e����

		Box bxBtn = new Box(BoxLayout.X_AXIS);

		bxBtn.add(Box.createHorizontalGlue());
		bxBtn.add(clickME); //�N����[�JBox�e��
		bxBtn.add(Box.createHorizontalGlue());

		cp.add(bxBtn); //�NBox�e���[�J���e����
		cp.add(lbPos, BorderLayout.SOUTH);

		clickME.addMouseListener(maBtn);
		//���U��maBtn��ťclickME���󪺷ƹ��ƥ�

		addMouseListener(mlFrame);
		//���U��mlFrame��ť�������ƹ��ƥ�

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(200, 150);
		setVisible(true);
	}

	public static void main(String args[]) {
		new AdapterEX(); //���͵����ج[����
	}
}