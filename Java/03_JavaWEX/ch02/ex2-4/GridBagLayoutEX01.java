import javax.swing.*; //�ޥ�Swing�M��
import java.awt.*; //�ޥ�AWT�M��

public class GridBagLayoutEX01 extends JFrame {

	GridBagLayoutEX01() {
		Container cp = getContentPane(); //���o����

		cp.setLayout(new GridBagLayout());
		//�]�w�ϥ�GridBagLayout�����t�m

		JButton btnOne = new JButton("Button One");
		
		btnOne.setPreferredSize(new Dimension(120, 40)); //�]�w���󪺳ߦn�j�p

		cp.add(btnOne); //�N���s�[�쪩��
		cp.add(new JButton("2"));
		cp.add(new JButton("Button Three"));
		cp.add(new JButton("Button 4"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���������ɡA�N�������ε{��

		setSize(400, 80); //�վ�����j�p
		setVisible(true); //��ܵ���
	}

	public static void main(String args[]) {
		new GridBagLayoutEX01(); //�ŧi�إߵ����e����frame����
	}
}