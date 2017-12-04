import javax.swing.*; //�ޥ�Swing�M��
import java.awt.*; //�ޥ�AWT�M��

public class BoxLayoutEX extends JFrame {

	BoxLayoutEX() {
		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));
		//�]�w�ϥΤ�����V�ƦC��BoxLayout�t�m����

		Box xBox = new Box(BoxLayout.X_AXIS);
		//�ŧiBox����, �ó]�w��X�b�ƦC����

		xBox.add(new JButton("���s 1"));
		xBox.add(Box.createHorizontalGlue()); //�b���󶡥[�J����
		xBox.add(new JButton("���s 2"));

		Box vBox = new Box(BoxLayout.Y_AXIS);
		//�ŧiBox����, �ó]�w��Y�b��A�C����

		vBox.add(new JButton("���s 3"));
		vBox.add(Box.createVerticalStrut(20));
		//�b���󶡥[�J���׬�20��������[

		vBox.add(new JButton("���s 4"));

		cp.add(xBox);
		cp.add(Box.createHorizontalStrut(40));
		cp.add(vBox);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���������ɡA�N�������ε{��

		setSize(400, 150); //�վ�����j�p
		setVisible(true); //��ܵ���
	}

	public static void main(String args[]) {
		new BoxLayoutEX();
	}
}