import javax.swing.*;  //�ޥήM��
import java.awt.*;

public class RootPaneEX extends JFrame {

	RootPaneEX() {

		int gap = 5; //�ťծؽu���e��

		getRootPane().setBorder(
			BorderFactory.createEmptyBorder(gap, gap, gap, gap));
		//�ŧi�ڭ����|�g�ϥμe�׬�5���ťծؽu

		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new BorderLayout(10, 10));
		//�إߦU�ϰ�����B�������Z��10��BorderLayout����

		//�N�U���s����A�[�J���������w��m
		cp.add(new JButton("EAST"), BorderLayout.EAST);		
		cp.add(new JButton("WEST"), BorderLayout.WEST);
		cp.add(new JButton("SOUTH"), BorderLayout.SOUTH);
		cp.add(new JButton("NORTH"), BorderLayout.NORTH);
		cp.add(new JButton("CENTER"));
		//�N����[�J�����ϰ�,
		//�۷��cp.add(new JButton("CENTER", BorderLayout.CENTER));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public static void main(String args[]) {
		new RootPaneEX();
	}
}