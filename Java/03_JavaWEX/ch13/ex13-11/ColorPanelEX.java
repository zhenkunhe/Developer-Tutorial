import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.EmptyBorder;
import javax.swing.colorchooser.*;
//�ޥγB�z�C������ColorSelectionModel���O

import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��
import java.io.*;
import java.util.*;

public class ColorPanelEX extends JFrame{

	JLabel lbColor = new JLabel("�Ƕ��]�w", JLabel.CENTER);
	//�ŧi��ܦǦ��C�������G������

	JButton btnBGColor = new JButton("����Ƕ��C��");
	//�ŧi�I�s�C���ܲ������s

	ColorPanelEX(){

		lbColor.setOpaque(true); //�]�w���ҭI�����z��
		lbColor.setBackground(Color.white); //�]�w�I���C�⬰�զ�
		lbColor.setForeground(Color.blue); //�]�w�I���C�⬰�Ŧ�

		//���U�^�����sActionEvent�ƥ󪺺�ť��
		btnBGColor.addActionListener(new ActionListener(){

			JColorChooser ccUser;
			//�ŧi�C���ܾ�

			public void actionPerformed(ActionEvent e){

				ccUser = new JColorChooser();
				//�ŧi�C���ܾ�

				ccUser.getSelectionModel().setSelectedColor(
					lbColor.getBackground());
				//�]�w�_�l������Ҫ��I���C��

				AbstractColorChooserPanel[] 
					accpGray = { new GrayScalePanel() };
				ccUser.setChooserPanels(accpGray);
				//�]�w�C���ܾ��Ҧ��ϥΪ��C���ܭ���,
				//�H�W���ԭz�N���Ͳ����­���, 
				//�ȨϥΦۭq�Ƕ��C�����������ĪG

				JDialog dialog = JColorChooser.createDialog(
					ColorPanelEX.this, 
					"����Ƕ��C��",
					true, ccUser, 
					new ColorActionListener(), null);
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
						ccUser.getSelectionModel().getSelectedColor());
					//�q�C���ܾ����C���ܼҫ�����, 
					//���o������Ƕ��C��
				}
			}
		});

		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new BorderLayout(5, 5));

		cp.add(lbColor); //�N�]�t���s��JPanel�e���[�J���e����
		cp.add(btnBGColor, BorderLayout.SOUTH);
		//�[�J��ܰT��������
		
		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����|�P�N�ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(150, 120);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ColorPanelEX(); //�ŧi�����ج[����
	}
}

//�H�~��AbstractColorChooserPanel���O�ۭq�Ƕ��C���ܭ���
class GrayScalePanel extends AbstractColorChooserPanel {

	private JSlider slGray = new JSlider(0, 255);
	//�ŧi����Ƕ��C�⪺�԰ʱ�

	private JLabel lbRGB = new JLabel();
	//��ܿ���C�⤧RGB��

	GrayScalePanel(){
		super(); //�I�s��¦���O���غc�l

		slGray.setMajorTickSpacing(51); //�]�w�D�n��׶��Z
		slGray.setMinorTickSpacing(17); //�]�w���n����׶��Z
		slGray.setPaintTicks(true); //�]�w��ܨ��

		Hashtable labelTable = new Hashtable();
		//�ŧi�إߩ԰ʱ��׼��Ҫ������

		for(int i=0; i<6; i++){
			labelTable.put(new Integer(i*51), 
									 new JLabel(String.valueOf(i*51)));
			//�N��׹��������ҩ�J�����
		}

		slGray.setLabelTable(labelTable);
		//�]�w�԰ʱ�ϥΩ�m���Ҫ������

		slGray.setPaintLabels(true); //�]�w��ܨ�ת��Ʀr����

		//���U��ťChangeEvent�ƥ�
		slGray.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent ce){
				int selValue = ((JSlider)ce.getSource()).getValue();
				//���o�԰ʱ�ثe�����

				getColorSelectionModel().setSelectedColor(
							new Color(selValue, selValue,selValue));
				//�]�w�C���ܼҫ�������C��

				lbRGB.setText("(" + selValue + ", " 
					+ selValue + ", " + selValue + ")");
			}
		});
	}

	protected void buildChooser(){ //�إ��C���ܭ���

		setLayout(new BorderLayout(5, 5));
		//�]�w�����������t�m�޲z��

		add(slGray); //�N�԰ʱ�[�J����
		add(new JLabel("�զ�"), BorderLayout.EAST);
		add(new JLabel("�¦�"), BorderLayout.WEST);
		add(new JLabel("�Щ즲�Ա�վ�Ƕ��C��", JLabel.CENTER),
						BorderLayout.NORTH);
		add(lbRGB, BorderLayout.SOUTH);	

		setBorder(new EmptyBorder(20, 20, 20, 20));
		//�]�w�����ϥμe�׬�20���ťծؽu

		Color selColor = getColorFromModel();
		//���o�C�����ҫ����������C��

		slGray.setValue(selColor.getBlue());
		//�]�w�԰ʱ쪺��m, �ȥH�C�⪺�ť��Ȱ��]�w

		lbRGB.setText("(" + selColor.getBlue() + ", " 
									+ selColor.getBlue() + ", "
									+ selColor.getBlue() + ")");
		//��ܥثe�Ƕ��C�⪺�]�w��

		lbRGB.setHorizontalAlignment(JLabel.CENTER);
		//�]�w���Ҥ�r����������覡
	}

	public void updateChooser(){ //��s�C���ܭ�������

		Color selColor = getColorFromModel();
		//���o�C�����ҫ����������C��

		slGray.setValue(selColor.getBlue());
		//�]�w�԰ʱ쪺��m, �ȥH�C�⪺�ť��Ȱ��]�w
	}

	//���o�C���ܭ������W��
	public String getDisplayName(){
		return "�Ƕ����";
	}

	//���o�C���ܭ������ϥ�, ���ϥιϥܬG�Ǧ^null
	public Icon getLargeDisplayIcon(){ return null; }
	public Icon getSmallDisplayIcon(){ return null; }
}