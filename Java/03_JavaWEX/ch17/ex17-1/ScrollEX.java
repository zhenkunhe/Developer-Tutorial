import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

public class ScrollEX extends JFrame {

	private JLabel
		lbImage = new JLabel(new ImageIcon("res\\butterfly.jpg"));
	//��ܹϤ����e��

	JLabel 
		lbHor = new JLabel("����Y", JLabel.CENTER),
		lbVer = new JLabel("�C���Y", JLabel.CENTER);

	JLabel lbUL = new JLabel(
					new ImageIcon("res\\UL.gif"), JLabel.RIGHT),
				lbLR = new JLabel(new ImageIcon("res\\LR.gif")),
				lbLL = new JLabel(
					new ImageIcon("res\\LL.gif"), JLabel.RIGHT),
				lbUR = new JLabel(new ImageIcon("res\\UR.gif"));

	JPanel jpImage = new JPanel();		
	JScrollPane spImage = new JScrollPane(jpImage);
	//�N�����[�J���b����

	JScrollBar sbHor = spImage.getHorizontalScrollBar(),
						sbVer = spImage.getVerticalScrollBar();
	//���o���b���������b�C

	ScrollEX(){

		//�ŧi�B���U�^��ActionEvent�ƥ󪺺�ť������
		lbUL.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e){
				sbHor.setValue(0);
				sbVer.setValue(0);
				//�N���ʶb���ʦܥ��W��
			}
		});

		//�ŧi�B���U�^��ActionEvent�ƥ󪺺�ť������
		lbUR.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e){
				sbHor.setValue(sbHor.getMaximum());
				sbVer.setValue(0);
				//�N���ʶb���ʦܥk�W��
			}
		});

		//�ŧi�B���U�^��ActionEvent�ƥ󪺺�ť������
		lbLR.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e){
				sbHor.setValue(sbHor.getMaximum());
				sbVer.setValue(sbVer.getMaximum());
				//�N���ʶb���ʦܥk�U��
			}
		});

		//�ŧi�B���U�^��ActionEvent�ƥ󪺺�ť������
		lbLL.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e) {
				sbHor.setValue(0);
				sbVer.setValue(sbVer.getMaximum());
				//�N���ʶb���ʦܥ��U��
			}
		});

		jpImage.add(lbImage); //�N���ҥ[�J����

		spImage.setHorizontalScrollBarPolicy(
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		spImage.setVerticalScrollBarPolicy(
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//�]�w���b������ܱ��b���覡

		spImage.setRowHeaderView(lbHor);
		spImage.setColumnHeaderView(lbVer);
		//�]�w���b�������C���Y�P����Y

		spImage.setViewportBorder(
			BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		//�]�w�˵��Ϫ��ؽu
	
		spImage.setCorner(
			JScrollPane.UPPER_LEFT_CORNER, lbUL);
		spImage.setCorner(
			JScrollPane.UPPER_RIGHT_CORNER, lbUR);
		spImage.setCorner(
			JScrollPane.LOWER_LEFT_CORNER, lbLL);
		spImage.setCorner(
			JScrollPane.LOWER_RIGHT_CORNER, lbLR);
		//�]�w���b�����|�Ө����ϥνվ��˵��d�򪺫��O���s

		Container cp = getContentPane(); //���o���e����
		cp.add(spImage); //�N�����[�J���e����

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ScrollEX(); //�ŧi�����ج[����
	}
}