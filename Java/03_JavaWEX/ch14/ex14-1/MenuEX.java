import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class MenuEX extends JFrame{

	JTextArea taMsg = new JTextArea();
	//�ŧi��ܥ\���T������r��

	//�ŧi��ťActionEvent�ƥ󪺺�ť������
	ActionListener alMenuItem = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			taMsg.append(((JMenuItem)e.getSource()).getText() + "\n");
			//��X����ﶵ�W��
		}
	};

	MenuEX(){

		JMenu mnFile = new JMenu("�ɮ�(F)"); //�ŧi�\���
		JMenuItem miExit = 
			new JMenuItem("����(E)", KeyEvent.VK_E);
		//�ŧi�\���ﶵ

		mnFile.setMnemonic(KeyEvent.VK_F); //�]�w�O����

		//�w�q�ëŧi��ťActionEvent�ƥ󪺺�ť��
		miExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});

		miExit.setAccelerator(KeyStroke.getKeyStroke('E',
			 		java.awt.event.InputEvent.SHIFT_MASK, false));
		//�إ�Shift + E���������}�ﶵ���[�t��

		mnFile.add(miExit); //�N�ﶵ�[�J�\���

		//�ŧi�\���ﶵ	
		JMenuItem 
			miItemA = new JMenuItem("�ﶵA(A)", 'A'),
			//�ŧi�ﶵ�ë��w�O����
			miItemB = new JMenuItem(
				"�ϥܿﶵ", new ImageIcon("icon/bitc.gif")),
			//�ŧi��ܹϥܻP��r���ﶵ
			miItemC = new JMenuItem(new ImageIcon("icon/bitc.gif"));
			//�ŧi����ܹϥܪ��ﶵ

		JMenu mnIconMenu = new JMenu("�ϥܿﶵ");
		mnIconMenu.add(miItemA); //�N�ﶵ�[�J�l�\���
		mnIconMenu.add(miItemB);
		mnIconMenu.add(miItemC);

		JMenuItem miTextLeft = new JMenuItem("��b�ϥ�",
									new ImageIcon("icon/bitc.gif")),
							miTextRight = new JMenuItem("��b�ϥk",
									new ImageIcon("icon/bitc.gif"));
		//�ŧi�\���ﶵ

		miTextLeft.setHorizontalTextPosition(SwingConstants.LEFT);
		miTextRight.setHorizontalTextPosition(SwingConstants.RIGHT);
		//�]�w��r�P�ϥܪ���������覡

		JMenu mnTextAlignMenu = new JMenu("�Ϥ��m");
		mnTextAlignMenu.add(miTextLeft); //�N�ﶵ�[�J�l�\���
		mnTextAlignMenu.add(miTextRight);

		JMenuItem miItemText = new JMenuItem("�ﶵ�@");
		//�ŧi�\���ﶵ

		miItemText.addActionListener(alMenuItem);
		//���U�^���U�ﶵActionEvent�ƥ󪺺�ť������

		JMenu mnAlignMenu = new JMenu("��r�ﶵ");
		mnAlignMenu.add(miItemText); //�N�ﶵ�[�J�l�\���


		JMenu mnMenuA = new JMenu("�˦�A");
		mnMenuA.setMnemonic('A');
		//�]�w�\����O����

		mnMenuA.add(mnIconMenu); //�[�J�l�\���
		mnMenuA.addSeparator(); //�[�J���j�Ÿ�
		mnMenuA.add(mnTextAlignMenu); //�[�J�l�\���
		mnMenuA.add(mnAlignMenu); //�[�J�l�\���

		JMenuBar jmb = new JMenuBar(); //�ŧi�\���C����
		setJMenuBar(jmb); //�]�w�����ج[�ϥΪ��\���
		jmb.add(mnFile); //�N�\���[�J�\���C
		jmb.add(mnMenuA);

		Container cp = getContentPane(); //���o���e����

		cp.add(new JScrollPane(taMsg));

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new MenuEX(); //�ŧi�����ج[����
	}
}