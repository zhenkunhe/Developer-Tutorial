import javax.swing.*; //�ޥ�swing�M��
import javax.swing.border.*; //�ޥ�swing�M��
import java.awt.*; //�ޥ�awt�M��

public class SpringLayoutEX2 extends JFrame {

	public SpringLayoutEX2(){

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new BoxLayout(cp,  BoxLayout.Y_AXIS));
		//�]�w���e�����ϥ�SpringLayout�t�m����

		SpringLayout slLayout = new SpringLayout();
		JPanel jpFirst = new JPanel(slLayout);

		for(int i=1; i<=6; i++){
			jpFirst.add(new JButton(Long.toString(i * 
				Math.round(Math.pow(10.0, i)))));	//�N���s�[�J�e��
		}

		SpringUtilities.makeGrid(jpFirst,
					2, 3, //�C, ��
                    10, 10,  //�_�lX�y��, �_�lY�y��
                    5, 5); //X�b��V���󪺶��j, Y�b��V���󪺶��j

		JPanel jpSecond = new JPanel(slLayout);

		for(int i=0; i<5; i++){
			for(int j=0; j<5; j++)
				jpSecond.add(new JButton(Long.toString(j * 
					Math.round(Math.pow(10.0, (j+i))) + i)));
				//�N���s�[�J�e��
		}

		SpringUtilities.makeCompactGrid(jpSecond,
					5, 5, //�C, ��
                    10, 10,  //�_�lX�y��, �_�lY�y��
                    5, 5); //X�b��V���󪺶��j, Y�b��V���󪺶��j

		cp.add(jpFirst);
		cp.add(jpSecond);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //�]�w���������N�w�]�����{��
		pack();
		setVisible(true); //��ܵ����ج[
	}

	public static void main(String[] args) {
		new SpringLayoutEX2();
	}
}

/*
SpringUtilities���O�ק��Java�������
http://java.sun.com/docs/books/tutorial/uiswing/layout/example-1dot4/SpringUtilities.java
*/
class SpringUtilities {

	/*
	�̷ӫ��w���_�lX, Y�y�лP���󶡪�X, Y�b���Z
	�Nparent�e����������ƦC���檬,
	�A���o�e�������󪺳̤j�e�׻P�̤j����,
	�ó]�w�Ҧ����󪺼e�׻P���ק����̤j���׻P�e��
	*/
	public static void makeGrid(
						Container parent, //���ƦC���󪺮e��
						int rows, int cols, //��, �C
						int initialX,  int initialY, //�_�l�y��
						int xPad, int yPad){ //X, Y�b�����Z

		SpringLayout layout;

		try {
			layout = (SpringLayout)parent.getLayout();
		}
		catch (ClassCastException exc) {
			System.err.println("�e�������ϥ�SpringLayout�����޲z��");
			return;
        }

		//�ŧi�]�wX, Y�y�а_�l�ȻP����X, Y�b��V�����j
		Spring initialXSpring = Spring.constant(initialX);
		Spring initialYSpring = Spring.constant(initialY);
		Spring xPadSpring = Spring.constant(xPad);
		Spring yPadSpring = Spring.constant(yPad);

		int max = rows * cols; //�p��e�Ǥ��󪺮��

		//���o�e����������̤j���e�׻P����
        Spring maxWidthSpring = 
			layout.getConstraints(parent.getComponent(0)).getWidth();
        Spring maxHeightSpring = 
			layout.getConstraints(parent.getComponent(0)).getWidth();

		for (int i = 1; i < max; i++) {
			SpringLayout.Constraints cons = 
				layout.getConstraints(parent.getComponent(i));

			maxWidthSpring = 
				Spring.max(maxWidthSpring, cons.getWidth());
			maxHeightSpring = 
				Spring.max(maxHeightSpring, cons.getHeight());
		}

		//�H�̤j�e�׻P���׳]�w�Ҧ�����
        for (int i = 0; i < max; i++) {
			SpringLayout.Constraints cons =
				layout.getConstraints(parent.getComponent(i));

			cons.setWidth(maxWidthSpring);
			cons.setHeight(maxHeightSpring);
		}

        //���o�[�J�e��������SpringLayout.Constraints����,
		//�íץ�����X, Y�y��
		SpringLayout.Constraints lastCons = null;
		SpringLayout.Constraints lastRowCons = null;

		for (int i = 0; i < max; i++) {
			SpringLayout.Constraints cons = 
					layout.getConstraints(parent.getComponent(i));
			//�̷ӥ[�J�e��������, ���o����

			//�̷ӥ[�J���ǳ]�w����X�y�Э�
			if(i % cols == 0) { //�إ߷s�C
				lastRowCons = lastCons;
				cons.setX(initialXSpring);
			}
			else { //����X�y�Ц�m, �N�ѫe�@����EAST��ɨM�w
				cons.setX(Spring.sum(
					lastCons.getConstraint(SpringLayout.EAST), 
					xPadSpring));				
			}

			//�̷ӥ[�J���ǳ]�w����Y�y�Э�
			if (i / cols == 0) { //�]�w�Ĥ@�C
				cons.setY(initialYSpring);
			}
			else { //����Y�y�Ц�m, �N�ѤW�@�C�M�w
				cons.setY(Spring.sum(
					lastRowCons.getConstraint(SpringLayout.SOUTH),
					yPadSpring));		
			}
			lastCons = cons; //���o�̫�@�Ӥ���
		}

		//�Q�γ̫�@�Ӥ��󭫷s�]�w�e�����j�p
		SpringLayout.Constraints pCons = layout.getConstraints(parent);
		pCons.setConstraint(SpringLayout.SOUTH,
			Spring.sum(Spring.constant(yPad),
				lastCons.getConstraint(SpringLayout.SOUTH)));
		pCons.setConstraint(SpringLayout.EAST,
			Spring.sum(Spring.constant(xPad),
				lastCons.getConstraint(SpringLayout.EAST)));
	}

    //makeCompactGrid()��k�N�I�s����k
	//���o�e�������w����Constraints����
	private static SpringLayout.Constraints getConstraintsForCell(
			int row, int col, Container parent, int cols) {

		SpringLayout layout = (SpringLayout) parent.getLayout();
		Component c = parent.getComponent(row * cols + col);
		return layout.getConstraints(c);
	}

	/*
	�̷ӫ��w���_�lX, Y�y�лP���󶡪�X, Y�b���Z
	�Nparent�e����������ƦC���檬,
	�ó]�w�C�@�椸�󪺼e�׬�����̼e�����󪺼e��,
	�C�@�C���󪺰��׬��ӦC�̰������󪺰���,
	*/
	public static void makeCompactGrid(
						Container parent, //���ƦC���󪺮e��
						int rows, int cols, //��, �C
						int initialX,  int initialY, //�_�l�y��
						int xPad, int yPad){ //X, Y�b�����Z

		SpringLayout layout;

		try {
			layout = (SpringLayout)parent.getLayout();			
		}
		catch (ClassCastException exc) {
			System.err.println("�e�������ϥ�SpringLayout�����޲z��");
			return;
        }

		Spring x = Spring.constant(initialX);
		//�ŧi�N��X�y�а_�l��m��Spring����

        //���o�@�檺���󪺳̤j�e��,
		//�ñN�̤j�e�׳]�w�����椸�󪺼e��,
		//�B��������X�b�y�Ъ��]�w
		for (int c = 0; c < cols; c++) {
			Spring width = Spring.constant(0);
			//�_�l���o����̤j�e�ת�Spring����

			for (int r = 0; r < rows; r++) {
				SpringLayout.Constraints cons =
							getConstraintsForCell(r, c, parent, cols);
				//���o������m�P�j�p��Constraints����

				width = Spring.max(width, cons.getWidth());
				//������󪺼e�ר��o���j��
			}

			//�N�@�C�Ҧ����󪺼e�׳]�w���̤j�e��, �ó]�w����X�y��
			for (int r = 0; r < rows; r++) {
                SpringLayout.Constraints constraints =
                        getConstraintsForCell(r, c, parent, cols);
				//���o����󪺦�m�P�j�p��Constraints����

				constraints.setX(x); //�]�w����X�y��
				constraints.setWidth(width); //�]�w���󪺼e��
			}
			x = Spring.sum(x, Spring.sum(width, Spring.constant(xPad)));
			//�[�`X�y�Э�, �����U�Ӥ���X�y��
		}

		Spring y = Spring.constant(initialY);
		//�ŧi�N��Y�y�а_�l��m��Spring����

        //���o�@�C�����󪺳̤j����,
		//�ñN�̤j���׳]�w���ӦC���󪺰���,
		//�B��������Y�b�y�Ъ��]�w
		for (int r = 0; r < rows; r++) {
			Spring height = Spring.constant(0);

			//���o�Y�C���󪺳̰�����
			for (int c = 0; c < cols; c++) {
				SpringLayout.Constraints cons =
							getConstraintsForCell(r, c, parent, cols);
				//���o������m�P�j�p��Constraints����

				height = Spring.max(height, cons.getHeight());
				//������󪺰��ר��o���j��
			}

			//�N�@�C�Ҧ����󪺰��׳]�w���̤j����, �ó]�wY�y��
			for (int c = 0; c < cols; c++) {
				SpringLayout.Constraints constraints =
					getConstraintsForCell(r, c, parent, cols);
				//���o����󪺦�m�P�j�p��Constraints����

				constraints.setY(y); //�]�w����Y�y��
				constraints.setHeight(height); //�]�w���󪺰���
			}

			y = Spring.sum(y, Spring.sum(height, Spring.constant(yPad)));
			//�[�`Y�y�Э�, �����U�Ӥ���Y�y��
        }

		//�]�w�e�����j�p
		SpringLayout.Constraints pCons = layout.getConstraints(parent);
		pCons.setConstraint(SpringLayout.SOUTH, y);
		pCons.setConstraint(SpringLayout.EAST, x);
	}
}