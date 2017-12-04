import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextPaneEX extends JFrame {

	JTextPane tpPane = new JTextPane(); //�˦���r����

	TextPaneEX(){

		JButton  btnText = new JButton("���J��r");
		//�ŧi�N��r���J�˦���r���������O���s

		//���U�^��ActionEvent�ƥ󪺺�ť������,
		//�ñN��r���J�˦���r����
		btnText.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tpPane.replaceSelection("�줸���");
				//���J��r
			}
		});

		JButton  btnIcon = new JButton("���J�ϥ�");
		//�ŧi�N�ϥܴ��J�˦���r���������O���s

		//���U�^��ActionEvent�ƥ󪺺�ť������,
		//�ñN�ϥܴ��J�˦���r����
		btnIcon.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tpPane.insertIcon(new ImageIcon("icon\\bitc.gif"));
				//���J�ϥ�
			}
		});

		JButton  btnButton = new JButton("���J���s����");
		//�ŧi�N���s���󴡤J�˦���r���������O���s

		//���U�^��ActionEvent�ƥ󪺺�ť������,
		//�ñN���s���󴡤J�˦���r����
		btnButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JButton btn = new JButton("�ڬO���s");
				//�ŧi���s����

				//���U�^�����s����ActionEvent�ƥ󪺺�ť������
				btn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						JOptionPane.showMessageDialog(
							TextPaneEX.this, "�˦���r�����������s�Q���U");
						//��ܰT����ܲ�
					}
				});

				tpPane.insertComponent(btn);
				//�N���s���󴡤J�˦���r����
			}
		});

		Box bxButton = new Box(BoxLayout.X_AXIS);
		//�ŧi�e�ǫ��O���s��Box�e��

		bxButton.add(Box.createHorizontalGlue());
		bxButton.add(btnText); //�N���s�[�J�e��
		bxButton.add(Box.createHorizontalStrut(5));
		bxButton.add(btnIcon);
		bxButton.add(Box.createHorizontalStrut(5));
		bxButton.add(btnButton);
		bxButton.add(Box.createHorizontalGlue());


		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new BorderLayout(5,  5));
		//�]�w�ϥζ��j�e�׬�5��BorderLayout����t�m����

		cp.add(new JScrollPane(tpPane));
		cp.add(bxButton, BorderLayout.SOUTH);

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
	}

	public static void main(String args[]) {
		new TextPaneEX(); //�ŧi�����ج[����
	}
}