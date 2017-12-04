import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class ButtonMenuEX extends JFrame {

	public ButtonMenuEX(){

		MenuButton mb = new MenuButton("����"); //�ŧiMenuButton����

		JPopupMenu pm  = mb.getPopupMenu();
		//���oMenuButton����ϥΪ��۲{�\���

		pm.add(new JMenuItem("�ﶵ�@")); //�N�ﶵ�[�J�۲{�\���
		pm.add(new JMenuItem("�ﶵ�G"));
		pm.add(new JMenuItem("�ﶵ�T"));

		Container cp = getContentPane();
		cp.setLayout(new FlowLayout());	
		cp.add(mb); //�NMenuButton����[�J�e��

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public static void main(String... args){
		new ButtonMenuEX();
	}
}

//�~��JComponent���O�w�q�֦��۲{�\����\�����s�������O
class MenuButton extends JComponent {

	final static int BTNRIGHT_WIDTH = 20;
	//�k����s���e��

	private JButton btnLeft = new JButton(), 
							  btnRight = new JButton("��");
	//�ŧi���k���䪺���O���s

	private JPopupMenu pm = new JPopupMenu();
	//�ŧi�۲{�\���

	public MenuButton(String text){ //�غc�l

		btnLeft.setText(text); //�]�wbtnLeft���s��ܪ��r��

		btnRight.setBorder(new CompoundBorder(
			((CompoundBorder)btnRight.getBorder()).getOutsideBorder(),
			new EmptyBorder(0, 0 ,0 ,0)));
		//�]�wbtnRight���s�ϥΪ��ؽu, 
		//�s���X���ؽu�N�������s����r�P�|�g�ؽu���ťհϰ�

		//���U�^���k����sActionEvent�ƥ󪺺�ť������,
		btnRight.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pm.show(btnLeft, 0, btnLeft.getSize().height);
				//�bbtnLeft���s�U������۲{�\���
			}
		});

		SpringLayout sl = new SpringLayout(); //�ŧi�G���޲z��
		setLayout(sl); //�]�w�t�m�������G���޲z��
		add(btnLeft); //�[�JbtnLeft���s

		SpringLayout.Constraints consBtnLeft = sl.getConstraints(btnLeft);
		//���o����btnLeft���s�t�m��SpringLayout.Constraints����

		add(btnRight, 
			new SpringLayout.Constraints(
				consBtnLeft.getConstraint(SpringLayout.EAST),
				Spring.constant(0),
				Spring.constant(BTNRIGHT_WIDTH ), 
				consBtnLeft.getHeight()));
		//�[�JbtnRight���s,
		//�æP�ɶǤJ����t�m��SpringLayout.Constraints����

		setPreferredSize(new Dimension(
			(new Double(btnLeft.getPreferredSize().getWidth()
													+ BTNRIGHT_WIDTH).intValue()),
			(new Double(btnLeft.getPreferredSize().getHeight()).intValue())));
		//�]�w��Ӥ��󪺰��n�j�p
	}

	//�]�w�۲{�\���
	public void setPopupMenu(JPopupMenu pm){
		this.pm = pm;
	}

	//�ǥX�۲{�\���
	public JPopupMenu getPopupMenu(){
		return pm;
	}

	//�]�w������s
	public void setLeftButton(JButton btn){
		this.btnLeft = btn;
	}

	//�ǥX������s
	public JButton getLeftButton(){
		return btnLeft;
	}
}