import javax.swing.*; //�ޥ�Swing�M��
import java.awt.*; //�ޥ�AWT�M��

public class WeightEX extends JFrame {

	WeightEX() {
		Container cp = getContentPane(); //���o����

		cp.setLayout(new GridBagLayout());
		//�]�w�ϥ�GridBagLayout�����t�m

		GridBagConstraints gbc = new GridBagConstraints();
		//�ŧi����󪩭��t�m��GridBagConstraints����

		gbc.insets  = new Insets(5, 5, 5, 5); //�]�w����P�|�P���󪺶Z��

		gbc.weightx = gbc.weighty = 1.0; //�]�w�C�Ӥ�����t�h�l�Ŷ����v��

		/***�[�J�Ĥ@�C������***/
		gbc.ipadx = gbc.ipady = 15;	//�]�w���󤺤�r�P�|�P��ɪ����Z����15
		gbc.gridx = 0; gbc.gridy = 0; //�]�w���󪺦�C��m
		cp.add(new JButton("Button One"), gbc); //�N���s�[�쪩��

		gbc.gridx = 1; gbc.gridy = 0; //�]�w���󪺦�C��m
		cp.add(new JButton("2"), gbc);

		/***�[�J�ĤG�C������***/
		gbc.ipadx = gbc.ipady = 0;
		//�]�w���󤺤�r�P�|�P��ɪ����Z����0

		gbc.gridx = 0; gbc.gridy = 1; //�]�w���󪺦�C��m
		cp.add(new JButton("Button Three"), gbc);
		
		gbc.gridx = 1; gbc.gridy = 1; //�]�w���󪺦�C��m
		cp.add(new JButton("Button 4"), gbc);

		/***�[�J�ĤT�C������***/
		gbc.insets  = new Insets(0, 0, 0, 0); //�]�w����P�|�P���󪺶Z��
		gbc.gridx = 0; gbc.gridy = 2; //�]�w���󪺦�C��m
		cp.add(new JButton("Button 5"), gbc); //�N���s�[�쪩��

		gbc.gridx = 1; gbc.gridy = 2; //�]�w���󪺦�C��m
		cp.add(new JButton("Button 6"), gbc);

		/***�[�J�ĥ|�C������***/
		gbc.gridx = 0; gbc.gridy = 3; //�]�w���󪺦�C��m
		cp.add(new JButton("Button 7"), gbc);
		
		gbc.gridx = 1; gbc.gridy = 3; //�]�w���󪺦�C��m
		cp.add(new JButton("Button 8"), gbc);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���������ɡA�N�������ε{��

		pack(); //�վ�����j�p
		setVisible(true); //��ܵ���
	}

	public static void main(String args[]) {
		new WeightEX(); //�ŧi�إߵ����e����frame����
	}
}