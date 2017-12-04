import javax.swing.*; //�ޥ�Swing�M��
import java.awt.*; //�ޥ�AWT�M��

public class GridBagLayoutEX03 extends JFrame {

	GridBagLayoutEX03() {
		Container cp = getContentPane(); //���o����

		cp.setLayout(new GridBagLayout());
		//�]�w�ϥ�GridBagLayout�����t�m

		GridBagConstraints gbc = new GridBagConstraints();
		//�ŧi����󪩭��t�m��GridBagConstraints����

		gbc.insets  = new Insets(5, 5, 5, 5); //�]�w����P�|�P���󪺶Z��

		/***�[�J�Ĥ@�C������***/
		gbc.ipadx = gbc.ipady = 15;	//�]�w���󤺤�r�P�|�P��ɪ����Z����15
		gbc.gridx = 0; gbc.gridy = 0; //�]�w���󪺦�C��m
		gbc.weightx=0.8; //�]�wweightx���񭫬�0.8
		cp.add(new JButton("Button One"), gbc); //�N���s�[�쪩��

		gbc.gridx = 1; gbc.gridy = 0; //�]�w���󪺦�C��m
		gbc.weightx=0.2; //�]�wweightx���񭫬�0.2
		cp.add(new JButton("2"), gbc);

		gbc.weightx=0.0; //��_�w�]��

		/***�[�J�ĤG�C������***/
		gbc.ipadx = gbc.ipady = 0;
		//�]�w���󤺤�r�P�|�P��ɪ����Z����0

		gbc.anchor = GridBagConstraints.WEST; //�]�w�ĤG�C�����a��(��)���

		gbc.gridx = 0; gbc.gridy = 1; //�]�w���󪺦�C��m
		cp.add(new JButton("Button Three"), gbc);
		
		gbc.gridx = 1; gbc.gridy = 1; //�]�w���󪺦�C��m

		cp.add(new JButton("Button 4"), gbc);

		/***�[�J�ĤT�C������***/
		gbc.anchor = GridBagConstraints.EAST; //�]�w�ĤT�C���a�F(�k)���
		gbc.insets  = new Insets(0, 0, 0, 0); //�]�w����P�|�P���󪺶Z��

		gbc.gridx = 0; gbc.gridy = 2; //�]�w���󪺦�C��m
		cp.add(new JButton("Button 5"), gbc); //�N���s�[�쪩��

		gbc.gridx = 1; gbc.gridy = 2; //�]�w���󪺦�C��m
		cp.add(new JButton("Button 6"), gbc);

		/***�[�J�ĥ|�C������***/
		gbc.gridx = 0; gbc.gridy = 3; //�]�w���󪺦�C��m
		gbc.anchor = GridBagConstraints.SOUTH; //�a�n(�U)���
		cp.add(new JButton("Button 7"), gbc);

		gbc.gridx = 1; gbc.gridy = 3; //�]�w���󪺦�C��m
		gbc.weighty = 1.0; //�]�wweighty���񭫬�1.0
		gbc.anchor = GridBagConstraints.NORTH; //�a�_(�W)���
		cp.add(new JButton("Button 8"), gbc);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���������ɡA�N�������ε{��

		pack(); //�վ�����j�p
		setVisible(true); //��ܵ���
	}

	public static void main(String args[]) {
		new GridBagLayoutEX03(); //�ŧi�إߵ����e����frame����
	}
}