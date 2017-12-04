import javax.swing.*; //�ޥ�Swing�M��
import java.awt.*; //�ޥ�AWT�M��

public class GridBagLayoutEX05 extends JFrame {

	GridBagLayoutEX05() {
		Container cp = getContentPane(); //���o����

		cp.setLayout(new GridBagLayout());
		//�]�w�ϥ�GridBagLayout�����t�m

		GridBagConstraints gbc = new GridBagConstraints();
		//�ŧi����󪩭��t�m��GridBagConstraints����

		gbc.insets  = new Insets(5, 5, 5, 5); //�]�w����P�|�P���󪺶Z��
		gbc.ipadx = gbc.ipady = 10;
		//�]�w���󤺤�r�P�|�P��ɪ����Z����10

		/***�[�J�Ĥ@�C������***/
		gbc.gridx = 0; gbc.gridy = 0; //�]�w���󪺦�C��m
		gbc.gridwidth = 1; gbc.gridheight = 2; //�]�w�e�׬�1�氪�׬�2�C

		gbc.anchor = GridBagConstraints.SOUTH;
		//�]�w�����ܪŶ���SOUTH��

		cp.add(new JButton("Button 1"), gbc); //�N���s�[�쪩��

		gbc.anchor = GridBagConstraints.CENTER; //��_�w�]��

		gbc.gridx = 1; gbc.gridy = 0; //�]�w���󪺦�C��m
		gbc.gridwidth = 2; gbc.gridheight = 1; //�]�w�e�׬�2�氪�׬�1�C
		cp.add(new JButton("Button 2"), gbc);

		/***�[�J�ĤG�C������***/
		gbc.gridx = 1; gbc.gridy = 1; //�]�w���󪺦�C��m
		gbc.gridwidth = gbc.gridheight = 1;  //�]�w�e�׬�1�氪�ק���2�C
		cp.add(new JButton("Button 3"), gbc);

		gbc.gridx = 2; gbc.gridy = 1; //�]�w���󪺦�C��m
		cp.add(new JButton("Button 4"), gbc);

		/***�[�J�ĤT�C������***/
		gbc.gridx = 0; gbc.gridy = 2; //�]�w���󪺦�C��m
		cp.add(new JButton("Button 5"), gbc);

		gbc.gridx = 1; gbc.gridy = 2; //�]�w���󪺦�C��m
		gbc.gridwidth = gbc.gridheight = 2;  //�]�w�e�׬�2�氪�׬�2�C

		gbc.anchor = GridBagConstraints.SOUTHEAST;
		//�]�w�����ܪŶ���SOUTHEAST��

		cp.add(new JButton("Button 6"), gbc);

		gbc.anchor = GridBagConstraints.CENTER; //�٭�]�w

		/***�[�J�ĥ|�C������***/
		gbc.gridx = 0; gbc.gridy = 3; //�]�w���󪺦�C��m
		gbc.gridwidth = gbc.gridheight = 1;  //�]�w�e�׬�1�氪�׬�1�C
		cp.add(new JButton("Button 7"), gbc);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���������ɡA�N�������ε{��

		pack(); //�վ�����j�p
		setVisible(true); //��ܵ���
	}

	public static void main(String args[]) {
		new GridBagLayoutEX05(); //�ŧi�إߵ����e����frame����
	}
}