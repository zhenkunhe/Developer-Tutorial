import javax.swing.*; //�ޥ�swing�M��
import java.awt.*; //�ޥ�awt�M��

public class SpringLayoutEX extends JFrame {

	public SpringLayoutEX(){

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new GridLayout(4, 1, 10, 10));
		//�]�w���e�����ϥ�GridLayout�t�m����

		JLabel lbName01 = new JLabel("�m�W : "); //�ŧi���Ҥ���
		JTextField tfName01 = new JTextField(); //�ŧi��r��줸��

		JPanel jpFirst = new JPanel(new SpringLayout());
		//�ŧiJPanel�e��, �ó]�w�ϥ�SpringLayout�޲z����

		jpFirst.add(lbName01); //�N���Ҥ���[�J�e��
		jpFirst.add(tfName01); //�N��r���[�J�e��

		JLabel lbName02 = new JLabel("�m�W : ");
		JTextField tfName02 = new JTextField();

		SpringLayout slLayout02 = new SpringLayout();
		//�ŧiSpringLayout�����޲z����

		JPanel jpSecond = new JPanel(slLayout02);
	
		//��k�@
		Spring width = Spring.constant(50);
		Spring height = Spring.constant(25, 35, 45);
		//�I�sconstant()��k�ŧiSpring����

		SpringLayout.Constraints consLbName02 = 
				new SpringLayout.Constraints(
							Spring.constant(10), Spring.constant(10),
							width, height);
		//�ŧiSpringLayout.Constraints����

		jpSecond.add(lbName02, consLbName02);
		//�N���Ҥ���[�J�e��, 
		//�ùB��SpringLayout.Constraints������w��m�P���B�e
		
		jpSecond.add(tfName02, 
				new SpringLayout.Constraints(
					Spring.constant(55), Spring.constant(10),
					Spring.constant(100), Spring.constant(30)));
		//�N��r���[�J�e��, �P�ɥ[�JSpringLayout.Constraints����

		JLabel lbName03 = new JLabel("�m�W : ");
		JTextField tfName03 = new JTextField(15);

		SpringLayout slLayout03 = new SpringLayout();
		JPanel jpThird = new JPanel(slLayout03);

		jpThird.add(lbName03); //�N����[�J�e��
		jpThird.add(tfName03);

		//��k�G
		SpringLayout.Constraints consLbName03 = 
								slLayout03.getConstraints(lbName03);
		//���o������Ҥ��󤧦�m�P�j�p��SpringLayout.Constraints����

		//�I�sSpringLayout.Constraints���󪺤�k, 
		//�]�w������m�P���B�e����
		consLbName03.setX(Spring.constant(10)); //�]�wX�b��m
		consLbName03.setY(Spring.constant(10)); //�]�wY�b��m
		consLbName03.setHeight(Spring.constant(25)); //�]�w���󰪫�
		consLbName03.setWidth(Spring.constant(35)); //�]�w����e��

		SpringLayout.Constraints 
				consTfName03 = slLayout03.getConstraints(tfName03);
		//���o�y�ztfName03����SpringLayout.Constraints����

		consTfName03.setConstraint(SpringLayout.WEST,
						Spring.sum(Spring.constant(10),
											 consLbName03.getConstraint(SpringLayout.EAST)));
		//�]�wtfName03����WEST��ɶZ���e��WEST��ɪ��Z��,
		//���o�Z�����覡���HlbName����EAST��ɪ���m�[�W10����
		consTfName03.setConstraint(SpringLayout.NORTH,
														   Spring.constant(10));

		JLabel lbName04 = new JLabel("�m�W : ");
		JTextField tfName04 = new JTextField();

		SpringLayout slLayout04 = new SpringLayout();
		JPanel jpFourth = new JPanel(slLayout04);

		//��k�@
		jpFourth.add(lbName04, new SpringLayout.Constraints(
									Spring.constant(10), Spring.constant(10)));
		//�N����[�J�e��, �ùB��SpringLayout.Constraints����
		//���w�����m��X, Y�y��

		jpFourth.add(tfName04);

		//��k�T
		slLayout04.putConstraint(SpringLayout.WEST, tfName04, 
							10,
							SpringLayout.EAST, lbName04);
		//�]�wlbName04����WEST��ɻPlbName����EAST��ɶZ��10����

		slLayout04.putConstraint(SpringLayout.EAST, jpFourth, 
							10,
							SpringLayout.EAST, tfName04);
		slLayout04.putConstraint(SpringLayout.SOUTH, jpFourth,
										10,
										SpringLayout.SOUTH, tfName04);
		
		slLayout04.putConstraint(SpringLayout.NORTH, tfName04, 
										10,
										SpringLayout.NORTH, jpFourth);
		//�]�wtfName04������ɻPjpFourth�e����
		//EAST�BSOUTH�BNORTH��ɶZ��10����

		cp.add(jpFirst); //�NJPanel�e���[�J���e����
		cp.add(jpSecond);
		cp.add(jpThird);
		cp.add(jpFourth);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���������N�w�]�����{��

		setSize(300, 300); //�]�w�����ج[�j�p
		setVisible(true); //��ܵ����ج[
	}

	public static void main(String[] args) {
		new SpringLayoutEX();
	}
}