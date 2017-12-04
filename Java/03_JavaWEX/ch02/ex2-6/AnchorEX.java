import javax.swing.*; //�ޥ�Swing�M��
import java.awt.*; //�ޥ�AWT�M��

public class AnchorEX extends JFrame {

	AnchorEX() {
		Container cp = getContentPane(); //���o����

		cp.setLayout(new GridBagLayout());
		//�]�w�ϥ�GridBagLayout�����t�m

		GridBagConstraints gbc = new GridBagConstraints();
		//�ŧi����󪩭��t�m��GridBagConstraints����

		gbc.gridx =  gbc.gridy =  0; //���w����[�J����m
		gbc.weightx  = gbc.weighty = 1.0;
		//�]�w���h�l�Ŷ���, ����N���t�������Ŷ�

		cp.add(new JButton("CENTER"), gbc); //�N���s�[�쪩��

		gbc.anchor = GridBagConstraints.NORTH;
		cp.add(new JButton("NORTH"), gbc); //�N���s�[�쪩��

		gbc.anchor = GridBagConstraints.SOUTH;
		cp.add(new JButton("SOUTH"), gbc); //�N���s�[�쪩��

		gbc.anchor = GridBagConstraints.EAST;
		cp.add(new JButton("EAST"), gbc); //�N���s�[�쪩��

		gbc.anchor = GridBagConstraints.WEST;
		cp.add(new JButton("WEST"), gbc); //�N���s�[�쪩��

		gbc.anchor = GridBagConstraints.NORTHEAST;
		cp.add(new JButton("NORTHEAST"), gbc); //�N���s�[�쪩��

		gbc.anchor = GridBagConstraints.NORTHWEST;
		cp.add(new JButton("NORTHEAST"), gbc); //�N���s�[�쪩��

		gbc.anchor = GridBagConstraints.SOUTHEAST;
		cp.add(new JButton("SOUTHEAST"), gbc); //�N���s�[�쪩��

		gbc.anchor = GridBagConstraints.SOUTHWEST;
		cp.add(new JButton("SOUTHWEST"), gbc); //�N���s�[�쪩��

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���������ɡA�N�������ε{��

		setSize(350, 250); //�]�w�����j�p
		setVisible(true); //��ܵ���
	}

	public static void main(String args[]) {
		new AnchorEX(); //�ŧi�إߵ����e����frame����
	}
}