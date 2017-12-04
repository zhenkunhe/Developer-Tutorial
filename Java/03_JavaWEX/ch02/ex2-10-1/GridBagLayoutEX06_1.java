import javax.swing.*; //�ޥ�Swing�M��
import java.awt.*; //�ޥ�AWT�M��

public class GridBagLayoutEX06_1 extends JFrame {

	GridBagLayoutEX06_1() {
		Container cp = getContentPane(); //���o����

		cp.setLayout(new GridBagLayout());
		//�]�w�ϥ�GridBagLayout�����t�m

		GridBagConstraints gbc = new GridBagConstraints();
		//�ŧi����󪩭��t�m��GridBagConstraints����

		gbc.insets  = new Insets(5, 5, 5, 5); //�]�w����P�|�P���󪺶Z��
		gbc.ipadx = gbc.ipady = 10;
		//�]�w���󤺤�r�P�|�P��ɪ����Z����10

		gbc.fill = GridBagConstraints.BOTH;
		//�]�w�񺡫����P������V����ܪŶ�

		/***�[�J�Ĥ@�C������***/
		gbc.gridx = 0; gbc.gridy = 0; //�]�w���󪺦�C��m
		gbc.gridheight = GridBagConstraints.RELATIVE;
		//�]�w��ܪŶ������ש����ܸ���U�@�Ӥ���

		cp.add(new JButton("Button 1"), gbc); //�N���s�[�쪩��

		gbc.gridheight = 1; //��_�w�]��

		gbc.gridx = 1; gbc.gridy = 0; //�]�w���󪺦�C��m
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		//�]�w��ܪŶ����e�ש����ܸ���U�@�Ӥ���

		cp.add(new JButton("Button 2"), gbc);

		gbc.gridwidth = 1; //��_�w�]��

		/***�[�J�ĤG�C������***/
		gbc.gridx = 1; gbc.gridy = 1; //�]�w���󪺦�C��m
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		//�]�w��ܪŶ����e�ש����ܸ���U�@�Ӥ���

		cp.add(new JButton("Button 3"), gbc);

		gbc.gridwidth = 1; //��_�w�]��

		gbc.gridx = 3; gbc.gridy = 1; //�]�w���󪺦�C��m
		cp.add(new JButton("Button 4"), gbc);

		/***�[�J�ĤT�C������***/
		gbc.gridx = 1; gbc.gridy = 2; //�]�w���󪺦�C��m
		cp.add(new JButton("Button 5"), gbc);

		gbc.gridx = 2; gbc.gridy = 2; //�]�w���󪺦�C��m
		gbc.gridheight = GridBagConstraints.REMAINDER;
		//�]�w��ܪŶ������ש����ܸ��檺�̫�

		cp.add(new JButton("Button 6"), gbc);

		gbc.gridheight = 1; //��_�w�]��

		/***�[�J�ĥ|�C������***/
		gbc.gridx = 0; gbc.gridy = 3; //�]�w���󪺦�C��m
		cp.add(new JButton("Button 7"), gbc);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���������ɡA�N�������ε{��

		pack(); //�վ�����j�p
		setVisible(true); //��ܵ���
	}

	public static void main(String args[]) {
		new GridBagLayoutEX06_1(); //�ŧi�إߵ����e����frame����
	}
}