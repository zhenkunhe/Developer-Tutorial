import javax.swing.*;  //�ޥήM��
import java.awt.*;

public class TitleSeparatorEX extends JFrame{

	TitleSeparatorEX(){

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new GridLayout(2, 3, 10, 10));
		//�]�w���e�������G���޲z������

		cp.add(new TitleSeparator(new JLabel("���D�b��")));
		cp.add(new TitleSeparator(new JLabel("���D�b�W"), 
					100, TitleSeparator.TITLE_HORIZONTAL_TOP));
		cp.add(new TitleSeparator(new JLabel("���D�b����"), 
					100, TitleSeparator.TITLE_VERTICAL_TOP));

		cp.add(new TitleSeparator(new JLabel("���D�b�k"),  
					TitleSeparator.TITLE_RIGHT));
		cp.add(new TitleSeparator(new JLabel("���D�b�U"), 
					100, TitleSeparator.TITLE_HORIZONTAL_BOTTOM));
		cp.add(new TitleSeparator(new JLabel("���D�b����"), 
					100, TitleSeparator.TITLE_VERTICAL_BOTTOM));
		//�N���j�u�[�J���e����

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���������N�w�]�����{��

		pack(); //�H�̾A�j�p��ܵ���
		setVisible(true); //��ܵ����ج[
	}

	public static void main(String args[]) {
		new TitleSeparatorEX(); //�ŧi�����ج[����
	}
}

class TitleSeparator extends JComponent {

	//�ŧi������j�u�˦��P���Ҧ�m���R�A�`��
	public final static short TITLE_LEFT = 1, //�w�]��
							 TITLE_RIGHT = 2,
							 TITLE_HORIZONTAL_TOP = 3,
							 TITLE_HORIZONTAL_BOTTOM = 4,
							 TITLE_VERTICAL_TOP = 5,
							 TITLE_VERTICAL_BOTTOM = 6;	

	private JLabel lbTitle; //���D��r����
	private int length = 50; //���j�u������, �w�]��50

	private	JSeparator spr = new JSeparator();
	//�ŧi�������j�u

	Dimension dim = spr.getPreferredSize();
	//���o�������j�u�j�p

	public TitleSeparator(JLabel title){ //�غc�l
		lbTitle = title;
		titleInSide(TITLE_LEFT); //�w�]���j�u�����D��r�b��
	}
	
	public TitleSeparator(JLabel title, int l){
		lbTitle = title;
		length =  l;
		titleInSide(TITLE_LEFT);
	}

	public TitleSeparator(JLabel title, int l, short type){
		lbTitle = title;
		length =  l;

		switch(type){ //�̷ӶǤJ���O�إ߾A�����j�u
			case TITLE_LEFT :
				titleInSide(TITLE_LEFT);
				break;
			case TITLE_RIGHT :
				titleInSide(TITLE_RIGHT);
				break;
			case TITLE_HORIZONTAL_TOP :
				titleHorizontal(TITLE_HORIZONTAL_TOP);
				break;
			case TITLE_HORIZONTAL_BOTTOM :
				titleHorizontal(TITLE_HORIZONTAL_BOTTOM);
				break;
			case TITLE_VERTICAL_TOP :
				titleVertical(TITLE_VERTICAL_TOP);
				break;
			case TITLE_VERTICAL_BOTTOM :
				titleVertical(TITLE_VERTICAL_BOTTOM);
				break;
			default :
				throw(new IllegalArgumentException("type"));
		}
	}

	public TitleSeparator(JLabel title, short type){
		lbTitle = title;

		switch(type){
			case TITLE_LEFT :
				titleInSide(TITLE_LEFT);
				break;
			case TITLE_RIGHT :
				titleInSide(TITLE_RIGHT);
				break;
			case TITLE_HORIZONTAL_TOP :
				titleHorizontal(TITLE_HORIZONTAL_TOP);
				break;
			case TITLE_HORIZONTAL_BOTTOM :
				titleHorizontal(TITLE_HORIZONTAL_BOTTOM);
				break;
			case TITLE_VERTICAL_TOP :
				titleVertical(TITLE_VERTICAL_TOP);
				break;
			case TITLE_VERTICAL_BOTTOM :
				titleVertical(TITLE_VERTICAL_BOTTOM);
				break;
			default :
				throw(new IllegalArgumentException("type"));
		}
	}

	private void titleInSide(short type){ //�إ߼��D��r�b�������j�u

		setLayout(new BorderLayout(5, 5));
		//�ϥΤ����B�������Z��5��BorderLayout

		lbTitle.setVerticalAlignment(JLabel.TOP); //�]�w���Ҥ�r�a�W���

		if(type == TITLE_LEFT)
			add(lbTitle, BorderLayout.WEST);
		else
			add(lbTitle, BorderLayout.EAST);

		spr.setPreferredSize(
			new Dimension(length, lbTitle.getPreferredSize().height));
		//�]�w�������j�u���e��

		Box bxSpr = new Box(BoxLayout.Y_AXIS);
		bxSpr.add(
			Box.createVerticalStrut(lbTitle.getPreferredSize().height/2) );
		//�إ߰��׬����D���Ұ��פ@�b����[
		bxSpr.add(spr);

		add(bxSpr);
	}

	private void titleHorizontal(short type){

		setLayout(new BorderLayout());
		//�]�w�ϥ�BorderLayout�޲z�G��

		spr.setPreferredSize(new Dimension(length, dim.height));
		//�]�w���j�u�����n�j�p
	
		if(type == TITLE_HORIZONTAL_TOP){
			add(lbTitle, BorderLayout.NORTH); //�N���D�[�J�_��ϰ�
			add(spr); //�N���j�u�[�J�����ϰ�
		}
		else{
			lbTitle.setVerticalAlignment(JLabel.TOP);
			//�]�w���Ҥ�r�a�W���

			add(lbTitle); //�N���ҥ[�J�����ϰ�
			add(spr, BorderLayout.NORTH); //�N���j�u�[�J�_��ϰ�
		}
	}

	private void titleVertical(short type){
		spr.setOrientation(JSeparator.VERTICAL);
		//�]�w���j�u����V

		spr.setPreferredSize(
						new Dimension(dim.width, length));
		//�]�w���j�u���e��
	
		Box bx = new Box(BoxLayout.X_AXIS);
		bx.add(Box.createHorizontalStrut(
							lbTitle.getPreferredSize().width / 2));
		//�إ߼e�׬����Ҽe�פ@�b����[

		bx.add(spr);

		setLayout(new BorderLayout());

		//�̷ӱ`�Ƴ]�w���D��r���Ҫ���m
		if(type == TITLE_VERTICAL_TOP)
			add(lbTitle, BorderLayout.NORTH);
		else
			add(lbTitle, BorderLayout.SOUTH);

		add(bx);
	}
}