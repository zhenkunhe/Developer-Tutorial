import javax.swing.*;  //�ޥήM��
import java.awt.*;

public class GridLayoutEX extends JFrame {

	GridLayoutEX() {
		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new GridLayout(3, 4, 10, 10));
		//���w�����B��3�C4�檺�檬�G���޲z��, �����P�������Z���O��10

		for(int i=1; i<=7; i++)
			cp.add(new JButton("Button_" + i));
			//�N7�ӫ��s����[�J����

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 150);
		setVisible(true);
	}

	public static void main(String args[]) {
		new GridLayoutEX();
	}
}