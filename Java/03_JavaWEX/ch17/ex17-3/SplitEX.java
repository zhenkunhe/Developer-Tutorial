import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

public class SplitEX extends JFrame {

	JLabel lbImage;	 //��ܹϤ����e��

	JScrollPane
		spTop = new JScrollPane(
				new JLabel(new ImageIcon("res\\butterfly.jpg"))),
		spBottom = new JScrollPane(
				new JLabel(new ImageIcon("res\\butterfly.jpg")));
	//�ŧi��ܹϤ������b����

	JSplitPane sltPane = new JSplitPane(
		JSplitPane.VERTICAL_SPLIT, spTop, spBottom);
	//�ŧi���έ���

	SplitEX(){

		spTop.setMinimumSize(new Dimension(100, 100));
		//�]�w������ܤ����b�������̤p�ؤo

		sltPane.setDividerSize(10); //�]�w���ξ����e��
		sltPane.setDividerLocation(150); //�]�w���ξ�����m
		sltPane.setOneTouchExpandable(true);
		//�]�w�䴩����i�}

		//�ŧi�^���]�w�������Τ覡��ܶs
		//ActionEvent�ƥ󪺺�ť������
		ActionListener alSplit = new ActionListener(){

			//�^��ActionEvent�ƥ󪺤�k
			public void actionPerformed(ActionEvent e){

				String strAction = (String) e.getActionCommand();
				//���o�ʧ@�R�O�r��

				//�P�_�ʧ@�R�O�r��ó]�w������������Τ覡
				if("��������".equals(strAction))
					sltPane.setOrientation(
						JSplitPane.VERTICAL_SPLIT);
				else if("��������".equals(strAction))
					sltPane.setOrientation(
						JSplitPane.HORIZONTAL_SPLIT);
			}
		};

		JRadioButton rbVer = new JRadioButton("��������", true),
								rbHor = new JRadioButton("��������");
		//�ŧi��������Τ覡����ܶs

		rbVer.addActionListener(alSplit);
		rbHor.addActionListener(alSplit);
		//���U��ť��ܶsActionEvent�ƥ󪺺�ť������

		//�N��������Τ覡����ܶs�ŧi���P�@�ӫ��s�s��
		ButtonGroup bg = new ButtonGroup();
		bg.add(rbVer);
		bg.add(rbHor);

		Box bxSplit = new Box(BoxLayout.Y_AXIS);
		bxSplit.add(Box.createVerticalStrut(5));
		bxSplit.add(rbVer);
		bxSplit.add(Box.createVerticalStrut(5));
		bxSplit.add(rbHor);
		//�N��ܶs�[�JBox�e����

		JButton btnReset = new JButton("���]");
		//�ŧi���O���s

		//���U�^��ActionEvent�ƥ󪺺�ť������
		btnReset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				sltPane.resetToPreferredSizes();
				//���]���έ������ؤo
			}
		});

		Container cp = getContentPane(); //���o���e����
		cp.add(sltPane); //�N�����[�J���e����
		cp.add(bxSplit, BorderLayout.EAST);
		cp.add(btnReset, BorderLayout.SOUTH);

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(415, 300);
		setVisible(true);
	}

	public static void main(String args[]) {
		new SplitEX(); //�ŧi�����ج[����
	}
}