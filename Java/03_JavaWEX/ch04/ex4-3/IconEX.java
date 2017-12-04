import javax.swing.*;  //�ޥήM��
import java.awt.*;

public class IconEX extends JFrame{

	IconEX(){

		JLabel lbIcon = new JLabel(new BitcIcon(80, 80));
		//�H������ܹϥ�

		JLabel lbImageIcon = new JLabel(new ImageIcon(".\\Icon\\Bitc.gif"));
		//�H���ɫإ���ܩ���Ҫ��ϥ�

		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new GridLayout(1, 2, 10, 10));
		//���o�G���޲z��

		cp.add(lbIcon); //�N����[�J����
		cp.add(lbImageIcon);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���������N�w�]�����{��
		pack(); //�H�A��j�p��ܵ���
		setVisible(true); //��ܵ����ج[
	}

	public static void main(String args[]) {
		new IconEX(); //�ŧi�����ج[����
	}
}

class BitcIcon implements Icon { //�ŧi��@Icon�������ϥ����O

	private int width, height;

	public BitcIcon(int w, int h){ //�غc�l
		width = w;
		height = h;
	}

	//ø�s�ϥ�
	public void paintIcon(Component c, Graphics g, int x, int y){
		g.drawRect(5, 5, 70, 70); //ø�s�x��
		g.drawString("�줸���", 15, 45); //ø�s��r
	}

	public int getIconWidth(){ //���o�ϥܼe��
		return width;
	}

	public int getIconHeight(){ //���o�ϥܰ���
		return height;
	}
}