import javax.swing.*;  //�ޥήM��
import java.awt.*;

public class SeparatorEX extends JFrame{

	SeparatorEX(){

		JLabel lbLeft = 
						new JLabel("�줸���",  JLabel.CENTER),
					lbRight= 
						new JLabel("�줸���",  JLabel.CENTER),
					lbTop = 
						new JLabel("�줸���",  JLabel.CENTER),
					lbBottom = 
						new JLabel("�줸���",  JLabel.CENTER)	;
		//�ŧi����

		JSeparator sprHorizontal = new JSeparator(); //�ŧi�������j�u
		Box bxVertical = new Box(BoxLayout.Y_AXIS);

		bxVertical.add(lbTop);
		bxVertical.add(sprHorizontal); //�[�J�������j�u
		bxVertical.add(lbBottom);

		JSeparator sprVertical = new JSeparator(JSeparator.VERTICAL);
		 //�ŧi�������j�u

		Box bxHorizontal = new Box(BoxLayout.X_AXIS);

		bxHorizontal.add(lbLeft);
		bxHorizontal.add(sprVertical); //�[�J�������j�u
		bxHorizontal.add(lbRight);

		/**** �إ߲Ĥ@�ؼ˦������j�u ***/		
		JSeparator sprSytle01 = new JSeparator(JSeparator.HORIZONTAL);
		//�ŧi�������j�u

		Dimension dimStyle01 = sprSytle01.getPreferredSize();
		//���o�������j�u�j�p

		sprSytle01.setPreferredSize(
							new Dimension(100, dimStyle01.height));
		//�]�w�������j�u���e�׬�100
	
		JPanel jpStyle01 = new JPanel();
		jpStyle01.add(new JLabel("�˦��@"));
		jpStyle01.add(sprSytle01);

		/**** �إ߲ĤG�ؼ˦������j�u ***/		
		JSeparator sprSytle02 = new JSeparator();
		Dimension dimStyle02 = sprSytle02.getPreferredSize();
		sprSytle02.setPreferredSize(
							new Dimension(100, dimStyle02.height));
	
		JPanel jpStyle02 = new JPanel(new GridLayout(2, 1));

		jpStyle02.add(new JLabel("�˦��G"));
		jpStyle02.add(sprSytle02);

		/**** �إ߲ĤT�ؼ˦������j�u ***/		
		JLabel lbStyle03 = new JLabel("�˦��T");
		JSeparator sprSytle03 = new JSeparator(JSeparator.VERTICAL);
		//�ŧi�������j�u

		Dimension dimStyle03 = sprSytle03.getPreferredSize();
		sprSytle03.setPreferredSize(
						new Dimension(dimStyle03.width, 100));
		//�]�w���j�u���e��
	
		Box bxStyle03 = new Box(BoxLayout.X_AXIS);
		bxStyle03.add(Box.createHorizontalStrut(
							lbStyle03.getPreferredSize().width / 2));
		//�إ߼e�׬����Ҽe�פ@�b����[

		bxStyle03.add(sprSytle03);

		JPanel jpStyle03 = new JPanel(new BorderLayout());

		jpStyle03.add(new JLabel("�˦��T", JLabel.LEFT), 
								BorderLayout.NORTH);
		jpStyle03.add(bxStyle03);

		JPanel jpTop = new JPanel();
		jpTop.add(bxVertical);
		jpTop.add(bxHorizontal);

		JPanel jpBottom = new JPanel();
		jpBottom.add(jpStyle01);
		jpBottom.add(jpStyle02);
		jpBottom.add(jpStyle03);

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
		//�]�w���e�������G���޲z������

		cp.add(jpTop);
		cp.add(jpBottom);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���������N�w�]�����{��

		pack(); //�H�̾A�j�p��ܵ���
		setVisible(true); //��ܵ����ج[
	}

	public static void main(String args[]) {
		new SeparatorEX(); //�ŧi�����ج[����
	}
}