import javax.swing.*;  //�ޥήM��
import java.awt.*;

public class ColorFontEX extends JFrame {

	ColorFontEX() {

		JLabel lbRed = new JLabel("��");
		lbRed.setForeground(Color.RED);
		//�HColor���O���`�ƨ��o�N����⪺Color����,
		//�ó]�w�����Ҫ��e���C��

		JLabel lbGreen = new JLabel("��");
		lbGreen.setForeground(new Color(0, 255, 0));
		//�H��ƪ�RGB�ȫŧi�N���⪺Color����,
		//�ó]�w�����Ҫ��e���C��

		JLabel lbBlue = new JLabel("��");
		lbBlue.setForeground(new Color(0.0f, 0.0f, 1.0f));
		//�H�B�I�ƪ�RGB�ȫŧi�N���Ŧ⪺Colord����,
		//�ó]�w�����Ҫ��e���C��

		JLabel lbTimesBold15 = new JLabel("Times New Roman");
		lbTimesBold15.setFont(
					new Font("Times New Roman", Font.BOLD, 20));
		//�]�w���Ҥ���r�ϥΪ��r��
	
		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new GridLayout(2, 2, 10, 10)); //���o�G���޲z��

		cp.add(lbRed); //�N����[�J����
		cp.add(lbGreen);
		cp.add(lbBlue);
		cp.add(lbTimesBold15);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���������N�w�]�����{��
		pack(); //�]�w�H�A��j�p���
		setVisible(true); //��ܵ����ج[
	}

	public static void main(String args[]) {
		new ColorFontEX(); //�ŧi�����ج[����
	}
}