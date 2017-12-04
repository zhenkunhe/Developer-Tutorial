import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.colorchooser.*;
//�ޥγB�z�C������ColorSelectionModel���O

import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��
import java.io.*;

public class PreviewPanelEX extends JFrame{

	JLabel lbColor = 
		new JLabel("�]�w�I��/�e���C��", JLabel.CENTER);
	//�ŧi�I���C��P�e���C��]�w������

	JButton btnUserColor = new JButton("�]�w�e��/�I���C��");
	//�ŧi�I�s�C���ܲ������s

	PreviewPanelEX(){

		lbColor.setOpaque(true); //�]�w���ҭI�����O�z����

		//���U�^�����sActionEvent�ƥ󪺺�ť��
		btnUserColor.addActionListener(new ActionListener(){

			PreviewPanel ppPreview; //�ۭq���C��w������

			//�^��ActionEvent�ƥ󪺤�k
			public void actionPerformed(ActionEvent e){

				JColorChooser ccUser = new JColorChooser();
				//�ŧi�C���ܾ�

				ppPreview = new PreviewPanel(ccUser, 
											lbColor.getBackground(),
											lbColor.getForeground());
				//�ŧi�ۭq���C��w������, 
				//�ǤJ�ϥΪ��C���ܾ�,
				//�B�ǤJlbColor���Ҫ��e���C��P�I���C�ⰵ���w�]��

				ccUser.setPreviewPanel(ppPreview);
				//�]�w�C���ܾ��ϥΪ��C��w������

				ccUser.getSelectionModel().setSelectedColor(
									lbColor.getBackground());
				//�]�w�_�l������Ҫ��I���C��

				JDialog dialog = JColorChooser.createDialog(
					PreviewPanelEX.this, "���o���ҭI��/�e���C��",
					true, ccUser, new ColorActionListener(), null);
				//�إ��C���ܲ�,
				//�åHccUser������ܲ������e,
				//�B��ColorActionListener����^����ܲ��� �T�w ���s			

				dialog.setVisible(true); //��ܹ�ܲ�
			}

			//�w�q�^���C���ܲ��� �T�w ���s��
			//ActionEvent�ƥ�ColorActionListener��ť������
			class ColorActionListener implements ActionListener {
				public void actionPerformed(ActionEvent e){			
					lbColor.setBackground(
						ppPreview.getSelectedBackground());
					lbColor.setForeground(
						ppPreview.getSelectedForeground());
					//�q�ۭq�w���������o�e���C��P�I���C�⪺�]�w��
				}
			}
		});	

		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new BorderLayout(5, 5));

		cp.add(lbColor); //�N�]�t���s��JPanel�e���[�J���e����
		cp.add(btnUserColor, BorderLayout.SOUTH); //�[�J��ܰT��������
		
		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����|�P�N�ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(150, 120);
		setVisible(true);
	}

	public static void main(String args[]) {
		new PreviewPanelEX(); //�ŧi�����ج[����
	}
}

//�w�q�C���ܲ����ۭq�w������
class PreviewPanel extends JPanel {

	private JLabel lbPreview = 
						new JLabel("�w���C��]�w", JLabel.CENTER),
						//����C��]�w������
					lbOrgin = 
						new JLabel("�C���l�]�w", JLabel.CENTER);
						//��ܭ�l�C��]�w������	

	private JToggleButton
		tbBack = new JToggleButton("�I��", true),
		tbFore = new JToggleButton("�e��");
	//����]�w�e���C��έI���C�⪺�������s

	PreviewPanel(JColorChooser chooser, 
		Color background, Color foreground){ //�غc�l

		//���o�C���ܾ���ColorSelectionModel����,
		//�õ��U��ťChangeEvent�ƥ󪺺�ť������
		chooser.getSelectionModel().addChangeListener(
			new ChangeListener(){

				//�^������C����ܦ欰����k
				public void stateChanged(ChangeEvent e){

					Color selColor = ((ColorSelectionModel)
								e.getSource()).getSelectedColor();
					//���o�ϥΪ̿�����C��

					//�P�_�I���������s�O�_�Q���
					if(tbBack.isSelected()){
						lbPreview.setBackground(selColor);
						//�N����C��]�w�����Ҫ��I���C��
					}
					else{
						lbPreview.setForeground(selColor);
						//�N����C��]�w�����Ҫ��e���C��
					}
				}
			}
		);

		lbPreview.setOpaque(true); //�]�w���Ҥ��z��
		lbPreview.setBackground(background); //�]�w�I���C��
		lbPreview.setForeground(foreground); //�]�w�e���C��
		lbPreview.setBorder(new CompoundBorder(
			new javax.swing.plaf.metal.MetalBorders.TextFieldBorder(),
			new EmptyBorder(0, 5, 0, 5)));
		//�]�w���ҨϥβզX�ؽu, �~�ج�Swing�w�]����r��ؽu,
		//���ج��ťծؽu

		lbOrgin.setOpaque(true); //�]�w���Ҥ��z��
		lbOrgin.setBackground(background); //�]�w�I���C��
		lbOrgin.setForeground(foreground); //�]�w�e���C��
		lbOrgin.setBorder(new CompoundBorder(
			new javax.swing.plaf.metal.MetalBorders.TextFieldBorder(),
			new EmptyBorder(0, 5, 0, 5)));
		//�]�w���ҨϥβզX�ؽu, �~�ج�Swing�w�]����r��ؽu,
		//���ج��ťծؽu

		ButtonGroup bg = new ButtonGroup(); //�ŧi���s�s��
		bg.add(tbBack); //�N���s�[�J���s�s��
		bg.add(tbFore);

		//�ŧi�e�Ǥ������s��Box�e��
		Box bxTButton = new Box(BoxLayout.Y_AXIS);
		bxTButton.add(tbBack); //�[�J�������s
		bxTButton.add(Box.createVerticalStrut(5));
		bxTButton.add(tbFore);

		JPanel jpColor = new JPanel(new GridLayout(1, 2, 2, 2));
		jpColor.add(lbOrgin); //�[�J����C�⪺����
		jpColor.add(lbPreview);

		setLayout(new BorderLayout(5, 5));
		//�]�w�����ϥΰt�m�޲z��

		add(bxTButton, BorderLayout.EAST); //�N����[�J����
		add(jpColor);

		setBorder(new EmptyBorder(2, 2, 2, 2));
		//�]�w�����ϥΪťծؽu
		//�Ъ`�N! �Y���]�w, �N�L�k��ܦۭq�w������
	}

	//���o�w���C�⭱����ܪ��I���C��
	public Color getSelectedBackground(){
		return lbPreview.getBackground();
	}

	//���o�w���C�⭱����ܪ��e���C��
	public Color getSelectedForeground(){
		return lbPreview.getForeground();
	}
}