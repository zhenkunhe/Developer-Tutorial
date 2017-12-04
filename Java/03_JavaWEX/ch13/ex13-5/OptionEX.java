import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class OptionEX extends JFrame{

	JLabel lbUser = new JLabel("������G : ");
	//�ŧi��ܵ��G������

	OptionEX(){

		JButton btnOption1 = new JButton("�ۭq��ܲ�(�ۭq�Φ��@)");
		//�ŧi�I�s�Ĥ@�ؤ覡�إߤ��ۭq��ܲ������s

		//���U�^��ActionEvent�ƥ󪺺�ť������
		btnOption1.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){

				String strMsg = "�H�U�ҦC�����q��,\n" + 
						  "���@�ӳ̦���O�����N��x�W�����~?",
						   strTitle = "�ۭq��ܲ�";
				//��ܩ�ۭq��ܲ����T��

				JOptionPane pane = new JOptionPane(strMsg);
				//�ŧi�ۭq��ܲ�

				String[] options = 
					new String[] {"�x�n�q", "�E��", "�غ�", "����"};
				//�ŧi�ۭq��ܲ������s����

				pane.setOptions(options); //�]�w�ۭq��ܲ����ﶵ
				pane.setMessageType(JOptionPane.QUESTION_MESSAGE);
				//�]�w�ۭq��ܲ����T������

				JDialog dialog = pane.createDialog(
										OptionEX.this, strTitle);
				//�إߦۭq��ܲ�����ܲ�����

				dialog.setVisible(true); //��ܹ�ܲ�

				lbUser.setText("������G : " + (String)pane.getValue());
				//��ܨϥΪ̫��U�����s
			}
		});

		JButton btnOption2 = new JButton("�����ܲ�(�ۭq�Φ��G)");
		//�ŧi�I�s�ĤG�ؤ覡�إߤ��ۭq��ܲ������s

		//���U�^��ActionEvent�ƥ󪺺�ť������
		btnOption2.addActionListener(new ActionListener(){

			private final ImageIcon ICON_BITC = 
				new ImageIcon("icon/Bitc_Logo_Only.gif");
			//�ŧi�ޥιϥܪ��R�A�`��

			public void actionPerformed(ActionEvent e){

				JOptionPane.showOptionDialog(null, 
					"Java 2 �����{���]�p �d��13-5 �Ĥ@��\n" +
					"�줸���(R) ���|�g ���v�Ҧ� (c) \n" + 
					"Copyright (c) 1998-2005 BitCultrue Inc. " +
					"All Rights Reserved.", 
					"����", JOptionPane.DEFAULT_OPTION, 
					JOptionPane.INFORMATION_MESSAGE,  ICON_BITC,
					null, null);
				//��ܦۭq�������ܲ�
			}
		});

		Box bxOption = new Box(BoxLayout.X_AXIS);
		bxOption.add(btnOption1); //�N���O���s�[�JBox�e��
		bxOption.add(Box.createHorizontalGlue());
		bxOption.add(btnOption2);

		Container cp = getContentPane(); //���o���e����
		cp.add(bxOption); //�NBox�e���[�J���e����
		cp.add(lbUser, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����|�P�N�ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(370, 100);
		setVisible(true);
	}

	public static void main(String args[]) {
		new OptionEX(); //�ŧi�����ج[����
	}
}