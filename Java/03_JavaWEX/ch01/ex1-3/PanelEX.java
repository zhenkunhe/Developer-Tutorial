import javax.swing.*;  //�ޥ�Swing�M��
import java.awt.*;

public class PanelEX extends JFrame {

	PanelEX() {
		JPanel jpBottom = new JPanel(); //�إ߸s�ձ����JPanel���O
		JPanel jpLeft = new JPanel();
		JPanel jpRight = new JPanel();

		//�B��awt�M�󤺪�Color���O��RED�ݩʳ]�w�U�������O���I���C��
		jpLeft.setBackground(Color.RED);
		jpBottom.setBackground(Color.YELLOW);
		jpRight.setBackground(Color.BLUE);

		jpLeft.add(new JButton("���s 1")); //�N���s�[�J����
		jpLeft.add(new JButton("���s 2"));

		jpRight.add(new JButton("���s 3"));
		jpRight.add(new JButton("���s 4"));
		jpRight.add(new JButton("���s 5"));

		jpBottom.add(jpLeft); //�N�����[�J���h����
		jpBottom.add(jpRight);

		getContentPane().add(jpBottom); //�N�����[�J���e����

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public static void main(String args[]) {
		new PanelEX(); //�إߵ����ج[����
	}
}