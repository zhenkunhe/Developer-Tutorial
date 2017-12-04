import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class ColorDialogEX extends JFrame{

	JLabel lbColor = new JLabel("������C��", JLabel.CENTER);
	//�ŧi�I���C��P�e���C��]�w������

	JButton btnBGColor = new JButton("����C��");
	//�ŧi�I�s�C���ܲ������s

	ColorDialogEX(){

		lbColor.setOpaque(true); //�]�w���ҭI�����O�z����

		//���U�^�����sActionEvent�ƥ󪺺�ť��
		btnBGColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				Color c = JColorChooser.showDialog(
										ColorDialogEX.this, 
										"����C��",
										lbColor.getBackground());
				//��ܿ���C�⪺�C���ܲ�,
				//�åHlbColor���Ҫ��I���C�⬰�w�]��,
				//�̫�öǦ^�ϥΪ̿�����C��

				lbColor.setBackground(c); 
				//�H��ܲ��Ǧ^���C�ⰵ��lbColor���Ҫ��I���C��
			}
		});

		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new BorderLayout(5, 5));

		cp.add(lbColor); //�N�]�t���s��JPanel�e���[�J���e����
		cp.add(btnBGColor, BorderLayout.SOUTH); //�[�J��ܰT��������
		
		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����|�P�N�ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(150, 120);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ColorDialogEX(); //�ŧi�����ج[����
	}
}