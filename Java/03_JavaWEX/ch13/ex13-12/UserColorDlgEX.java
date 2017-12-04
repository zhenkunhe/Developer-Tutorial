import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.colorchooser.*;
//�ޥγB�z�C������ColorSelectionModel���O

import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��
import java.io.*;

public class UserColorDlgEX extends JFrame{

	JLabel lbColor = new JLabel("�]�w���G", JLabel.CENTER);
	//�ŧi�I���C��P�e���C��]�w������

	JButton btnUserColor = new JButton("�]�w�r���B�e��/�I���C��");
	//�ŧi�I�s�C���ܲ������s

	UserColorDlgEX(){

		lbColor.setFont(new Font("�رd�Ӷ���", Font.PLAIN, 12));
		//�]�w���ҨϥΪ��r��

		lbColor.setOpaque(true); //�]�w���ҭI�����O�z����

		//���U�^�����sActionEvent�ƥ󪺺�ť��
		btnUserColor.addActionListener(
			new ActionListener(){

			FontColorDialog fcd;
			//�ŧi�ۭq���r���B�I��/�e���C��]�w��ܲ�

			//�^��ActionEvent�ƥ󪺤�k
			public void actionPerformed(ActionEvent e){

				fcd = new FontColorDialog(
													UserColorDlgEX.this,
													lbColor.getFont(),
													lbColor.getBackground(),
													lbColor.getForeground());
				//�HlbColor���Ҫ��r���B�e��/�I���C��إ߹�ܲ�


				fcd.addOKActionListener(new ColorActionListener());
				//���U��ť��ܲ��� �T�w ���sActionEvent�ƥ󪺺�ť������

				fcd.setVisible(true); //�]�w��ܹ�ܲ�

			}

			//�w�q�^���C���ܲ��� �T�w ���s��
			//ActionEvent�ƥ�ColorActionListener��ť������
			class ColorActionListener implements ActionListener {
				public void actionPerformed(ActionEvent e){

					lbColor.setBackground(
						fcd.getSelectedBackground());
					lbColor.setForeground(
						fcd.getSelectedForeground());
					//�H�ϥΪ̿�����e���C��P�I���C��]�wlbColor����

					lbColor.setFont(fcd.getFont());
					//�H�ϥΪ̿�����r���]�wlbColor����
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
		setSize(200, 120);
		setVisible(true);
	}

	public static void main(String args[]) {
		new UserColorDlgEX(); //�ŧi�����ج[����
	}
}

//�H�~��JDialog���O���覡
//�w�q�i�]�w�r���P�e��/�I���C�⪺FontColorDialog���O
class FontColorDialog extends JDialog {

	Integer[] fontSize = 
		{8, 9 ,10, 11, 12, 13, 14, 15, 16, 17,  18, 20};
	//�ŧi�إߦr���j�p��ܲ��ﶵ����ư}�C

	JComboBox cmbFont, cmbSize = new JComboBox(fontSize);
	//�ŧi����r���P�r���j�p���զX���

	PreviewPanel pp; //�ŧi�ۭq���w���]�w����

	JButton btnOK = new JButton("�T�w"),
				  btnCancel = new JButton("����"),
				  btnReset = new JButton("���]");
	//�ŧi��ܲ��� �T�w �P ���� ���s

	Font oldFont; //�r������]�w��
	Color oldBackground, oldForeground;
	//�e��/�I���C�⪺��]�w��

	public FontColorDialog(Frame parent, Font font, 
											Color background, Color foreground){

		super(parent, "����r��/�e���C��/�I���C��", true);
		//�I�s��¦���O���غc�l

		oldFont = font;
		oldBackground = background;
		oldForeground = foreground;
		//�x�s�r���B�e��/�I���C�⪺��]�w��

		Font[] fonts = 
			GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		//���o�t�δ��Ѫ��Ҧ��r��

		DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
		//�ŧi�x�s�զX����ﶵ���e��DefaultComboBoxModel����

		//�N�t�Φr�����W�٥[�J�զX���
		for(Font elm : fonts)
			dcbm.addElement(elm.getName());

		cmbFont = new JComboBox(dcbm);
		//�H�x�s�ﶵ���e��DefaultComboBoxModel����إ߲զX���

		JColorChooser cc = new JColorChooser();
		//�ŧi�C���ܾ�

		pp = new PreviewPanel(cc, font, background, foreground);
		//�ŧi�ۭq���w���]�w����

		cc.setPreviewPanel(pp); //�]�w�C���ܾ����w���]�w����

		Font fnCur = pp.lbPreview.getFont();
		//���o�w���]�w������lbPreview���ҨϥΪ��r��

		cmbFont.setSelectedItem(fnCur.getName());
		cmbSize.setSelectedItem(fnCur.getSize());
		//�̷ӥثe�]�w���r��, �]�w�զX����������

		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new BorderLayout(5, 5));
		//�]�w���e�����ϥ�BorderLayout�t�m����

		JPanel jpFont = new JPanel(new GridLayout(1, 2, 10, 10));
		//�ŧi�e�Ǧr���P�r���j�p�]�w���զX���������

		jpFont.add(cmbFont); //�N�զX����[�J����
		jpFont.add(cmbSize);
		jpFont.setBorder(new EmptyBorder(5, 5, 5, 5));

		//�ŧi�e�ǫ��O���s��Box�e��, �å[�J���O���s
		Box bxButton = new Box(BoxLayout.X_AXIS);
		bxButton.add(Box.createHorizontalGlue());
		bxButton.add(btnOK);
		bxButton.add(Box.createHorizontalStrut(15));
		bxButton.add(btnCancel);
		bxButton.add(Box.createHorizontalStrut(15));
		bxButton.add(btnReset);
		bxButton.add(Box.createHorizontalGlue());

		bxButton.setBorder(new EmptyBorder(5, 5, 5, 5));

		//�N�U�e���P��ܾ��[�J��ܲ������e����
		cp.add(jpFont, BorderLayout.NORTH);
		cp.add(cc);
		cp.add(bxButton, BorderLayout.SOUTH);

		setSize(500, 450); //�]�w��ܲ����j�p

		//���U�^���r���զX���ItemEvent�ƥ󪺺�ť��
		cmbFont.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent e){

				//�P�_�o�X�ƥ󤧿ﶵ�����A�O�_���Q���
				if(e.getStateChange() == ItemEvent.SELECTED){
					String strFont = (String) e.getItem();
					//���o�޵o�ƥ󪺿ﶵ

					Font fnNew = new Font(strFont, Font.PLAIN,
						(Integer)cmbSize.getModel().getSelectedItem());
					//�H�r���W�ٻP�r���j�p�զX���������ȫŧi�r������

					pp.lbPreview.setFont(fnNew);
					//�]�w�w���]�w������lbPreview���ҨϥΪ��r��
				}
			}
		});

		//���U�^���r���j�p�զX���ItemEvent�ƥ󪺺�ť��
		cmbSize.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent e){

				//�P�_�o�X�ƥ󤧿ﶵ�����A�O�_���Q���
				if(e.getStateChange() == ItemEvent.SELECTED){
					String strFont = (String) cmbFont.getModel().getSelectedItem();
					//���o�r����ܲ��ثe������r���W��

					Font fnNew = 
						new Font(strFont, Font.PLAIN,	(Integer)e.getItem());
					//�H�r���W�ٻP�޵o�ƥ�
					//�r���j�p�զX������ﶵ�إߦr��

					pp.lbPreview.setFont(fnNew);
					//�]�w�w���]�w������lbPreview���ҨϥΪ��r��
				}
			}
		});

		//���U�^�� �T�w ���O���sActionEvent�ƥ󪺺�ť������
		btnOK.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setVisible(false); //�]�w����ܹ�ܲ�
			}
		});

		//���U�^�� ���� ���O���sActionEvent�ƥ󪺺�ť������
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pp.lbPreview.setFont(oldFont);
				pp.lbPreview.setBackground(oldBackground);
				pp.lbPreview.setForeground(oldForeground);
				//��_�w���]�w������lbPreview����
				//�ϥΪ��r���P�e��/�I���C��

				setVisible(false); //�]�w����ܹ�ܲ�
			}
		});

		//���U�^�� ���] ���O���sActionEvent�ƥ󪺺�ť������
		btnReset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pp.lbPreview.setFont(oldFont);
				pp.lbPreview.setBackground(oldBackground);
				pp.lbPreview.setForeground(oldForeground);
				//��_�w���]�w������lbPreview����
				//�ϥΪ��r���P�e��/�I���C��

				cmbFont.setSelectedItem(oldFont.getName());
				cmbSize.setSelectedItem(oldFont.getSize());
				//��_�r���P�r���j�p�զX����������
			}
		});
	}

	//���U��ť �T�w ���sActionEvent�ƥ󪺺�ť������
	public void addOKActionListener(ActionListener al){
		btnOK.addActionListener(al);
		//���U��ťActionEvent�ƥ󪺺�ť������
	}

	//���o�w���]�w������ܪ��I���C��
	public Color getSelectedBackground(){
		return pp.lbPreview.getBackground();
	}

	//���o�w���]�w������ܪ��e���C��
	public Color getSelectedForeground(){
		return pp.lbPreview.getForeground();
	}

	//���o�w���]�w������ܪ��r��
	public Font getFont(){
		return pp.lbPreview.getFont();
	}

	//�w�q�C���ܲ����ۭq�w���]�w����
	class PreviewPanel extends JPanel {

		private JLabel lbPreview = 
							new JLabel("�]�w��", JLabel.CENTER),
							//����C��]�w������
						lbOrgin = 
							new JLabel("��l��", JLabel.CENTER);
							//��ܭ�l�C��]�w������	

		private JToggleButton
			tbBack = new JToggleButton("�I��", true),
			tbFore = new JToggleButton("�e��");
		//����]�w�e���C��έI���C�⪺�������s

		PreviewPanel(JColorChooser chooser,  Font font,
			Color background, Color foreground){ //�غc�l

			lbPreview.setOpaque(true); //�]�w���Ҥ��z��
			lbPreview.setBackground(background); //�]�w�I���C��
			lbPreview.setForeground(foreground); //�]�w�e���C��
			lbPreview.setFont(font); //�]�w�ϥΦr��
			lbPreview.setBorder(new CompoundBorder(
				new javax.swing.plaf.metal.MetalBorders.TextFieldBorder(),
				new EmptyBorder(0, 5, 0, 5)));
			//�]�w���ҨϥβզX�ؽu, �~�ج�Swing�w�]����r��ؽu,
			//���ج��ťծؽu

			lbPreview.setPreferredSize(new Dimension(150, 70));
			//�]�w��ܿ���]�w�����Ҫ����n�j�p

			lbOrgin.setOpaque(true); //�]�w���Ҥ��z��
			lbOrgin.setBackground(background); //�]�w�I���C��
			lbOrgin.setForeground(foreground); //�]�w�e���C��
			lbOrgin.setFont(font); //�]�w�ϥΦr��

			lbOrgin.setBorder(new CompoundBorder(
				new javax.swing.plaf.metal.MetalBorders.TextFieldBorder(),
				new EmptyBorder(0, 5, 0, 5)));
			//�]�w���ҨϥβզX�ؽu, �~�ج�Swing�w�]����r��ؽu,
			//���ج��ťծؽu

			lbOrgin.setPreferredSize(new Dimension(150, 70));
			//�]�w��ܭ�l�]�w�����Ҫ����n�j�p

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
		}
	}
}