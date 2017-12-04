import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class DefMneEX extends JFrame {

	JLabel[] lbImage = { //�ŧi��ܹϤ������Ҫ���
		new JLabel( new ImageIcon(
			DefMneEX.class.getResource("images/A4023.jpg"),
			"Access 2003 ������s")),
		new JLabel( new ImageIcon(
			DefMneEX.class.getResource("images/A4033.jpg"),
			"Access 2003 �{���]�p")),
		new JLabel( new ImageIcon(
			DefMneEX.class.getResource("images/A4043.jpg"),
			"Access 2003 ��������")),
		new JLabel( new ImageIcon(
			DefMneEX.class.getResource("images/W4075.jpg"),
			"ASP�ʺA�����J�����")),
		new JLabel( new ImageIcon(
			DefMneEX.class.getResource("images/W3135.jpg"),
			"JSP�ʺA�����J�����")) };

	//�ŧi����CardLayout������ܤ��e�����s
	JButton btnPre = new JButton("�W�@��(P)"),
		btnNext = new JButton("�U�@��(N)"),
		btnFirst = new JButton("�Ĥ@��(F)"),
		btnLast = new JButton("�̫�@��(L)"),
		btnName = new JButton("ASP�ʺA�����J�����");

	//�w�q��ťActionEvent�ƥ󪺺�ť�����O
	ActionListener alBtn = new ActionListener(){
		public void actionPerformed(ActionEvent e){

			String strCommand = e.getActionCommand(); //���o�ʧ@�R�O�r��

			if(e.getActionCommand().equals("�W�@��(P)"))
				cl.previous(jpImage); //���ܤW�@�i�d��
			else if(e.getActionCommand().equals("�U�@��(N)"))
				cl.next(jpImage); //���ܤU�@�i�d��
			else if(e.getActionCommand().equals("�Ĥ@��(F)"))
				cl.first(jpImage); //���ܲĤ@�i�d��
			else if(e.getActionCommand().equals("�̫�@��(L)"))
				cl.last(jpImage); //���̫ܳ�@�i�d��
			else if(e.getActionCommand().equals("ASP�ʺA�����J�����"))
				cl.show(jpImage, "ASP�ʺA�����J�����");
				//���ܴy�z���e��"ASP�ʺA�����J�����"���d��
		}
	};

	CardLayout cl = new CardLayout(10, 5);
	//�ŧi�t�m������CardLayout����

	JPanel jpImage = new JPanel(cl); //�ŧi�e�ǹϤ���JPanel�e��

	DefMneEX(){

		//�Hfor�j��N�]�t�Ϥ������Ҥ���P�ԭz�[�J�e��
		for(JLabel elm: lbImage)
			jpImage.add(elm
				, ((ImageIcon)elm.getIcon()).getDescription());

		JPanel jpBtn = new JPanel(new GridLayout(5, 1, 20, 10));
		//�ŧiJPanel����ó]�w�HGridLayout�t�m����

		jpBtn.add(btnFirst); //�N���s����[�JJPanel�e��
		jpBtn.add(btnPre);
		jpBtn.add(btnNext);
		jpBtn.add(btnLast);
		jpBtn.add(btnName);

		//���U�B�z�ƹ��ƥ󪺺�ť��
		btnFirst.addActionListener(alBtn);
		btnPre.addActionListener(alBtn);
		btnNext.addActionListener(alBtn);
		btnLast.addActionListener(alBtn);
		btnName.addActionListener(alBtn);

		btnFirst.setMnemonic('F'); //�]�w�U�вŸ�
		btnPre.setMnemonic('P');
		btnNext.setMnemonic('N');
		btnLast.setMnemonic('L');

		JRootPane rp = getRootPane(); //���o�e�����ڭ���
		rp.setDefaultButton(btnFirst); //�]�w�w�]���s

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new FlowLayout()); //�]�w�ϥ�FlowLayout�t�m����
		cp.add(jpBtn); //�N����[�J���e����
		cp.add(jpImage);

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(450, 300);
		setVisible(true);
	}

	public static void main(String args[]) {
		new DefMneEX(); //�ŧi�����ج[����
	}
}