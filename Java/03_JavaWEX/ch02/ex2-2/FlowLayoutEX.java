import javax.swing.*;  //�ޥήM��
import java.awt.*;

public class FlowLayoutEX extends JFrame {

	FlowLayoutEX() {
		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new FlowLayout(FlowLayout.RIGHT));
		//�]�w�ϥΪ����t�m�N�B�ξa�k�����FlowLayout����

		for(int i=1; i<=5; i++)
			cp.add(new JButton("���s " + i));
			//�N���s����[�J����

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 150); //�]�w�������e�׬�300, ���׬�150
		setVisible(true);
	}

	public static void main(String args[]) {
		new FlowLayoutEX();
	}
}