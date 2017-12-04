import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

public class TabbedEX extends JFrame {

	JLabel lbImage; //�ŧi�e�ǹϤ������Ҫ���

	JTabbedPane tpPanel = new JTabbedPane();
	//�ŧi���ҭ���

	String strSel = "�ثe��� : ", strTotal = "�����Ӽ� : ";

	JLabel lbSelected = new JLabel(strSel),
				lbTotal = new JLabel(strTotal);
	//�ŧi��ܥثe������һP�`�����Ӽƪ�����

	TabbedEX(){

		lbImage = 
			new JLabel(new ImageIcon("res\\butterfly.jpg"));
		//�ŧi�e�ǹϤ�������
		
		tpPanel.addTab(
			"���� " + tpPanel.getTabCount(),	null, lbImage);
		//�N�����[�J���ҭ���

		lbSelected.setText(
			strSel + tpPanel.getTitleAt(tpPanel.getSelectedIndex()));
		//�]�w������ܳQ������������D�W��

		lbTotal.setText(
			strTotal + tpPanel.getTabCount());
		//�]�w������ܥثe�}�Ҫ��`�����Ӽ�

		//���U��ť���ҭ���ChangeEvent�ƥ󪺺�ť������
		tpPanel.addChangeListener(new ChangeListener(){

			//�^���������A���ܤ�
			public void stateChanged(ChangeEvent e){

				JTabbedPane tpSource = (JTabbedPane) e.getSource();
				//�޵o����ChangeEvent�ƥ󪺨ӷ�����

				int selIndex = tpSource.getSelectedIndex();
				//���o�ثe���ҭ������������

				if(selIndex != -1){
					lbSelected.setText(
						strSel + tpSource.getTitleAt(selIndex));
					//���o�ثe��������������D�r��
				}
				else
					lbSelected.setText(strSel + "�L");
					//�]�w�ثe�L�������
			}
		});

		//���U�^�����ҭ����^��ContainerEvent�ƥ󪺺�ť������
		tpPanel.addContainerListener(new ContainerListener(){

			//�^���N����[�J�������ʧ@
			public void componentAdded(ContainerEvent e){
				lbTotal.setText(
					strTotal + tpPanel.getTabCount());
				//�]�w������ܥثe�������`�Ӽ�
			}

			//�^���ۭ����������󪺰ʧ@
			public void componentRemoved(ContainerEvent e){
				lbTotal.setText(
					strTotal + tpPanel.getTabCount());
				//�]�w������ܥثe�������`�Ӽ�
			}
		});

		JButton btnAdd = new JButton("�s�W����");
		//�ŧi�s�W���������O���s

		//�ŧi�B���U�^��ActionEvent�ƥ󪺺�ť������
		btnAdd.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){
				lbImage = new JLabel(
					new ImageIcon("res\\butterfly.jpg"));
				//�ŧi�e�ǹϤ�������

				tpPanel.addTab(
					"���� " + tpPanel.getTabCount(), null, lbImage);
				//�N�����[�J���ҭ�����

				tpPanel.setSelectedIndex((tpPanel.getTabCount()-1));
				//�]�w���ҭ�������̫�@�ӭ���
			}
		});

		JButton btnRemove = new JButton("��������");
		//�ŧi�s�W���������O���s

		//�ŧi�B���U�^��ActionEvent�ƥ󪺺�ť������
		btnRemove.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				if(tpPanel.getTabCount() == 0) return;
				//�Y�ثe���ҭ������S�����󭶦�, �h�פ�����k

				tpPanel.remove(tpPanel.getSelectedIndex());
				//�����ثe���ҭ������������
			}
		});

		//�ŧi�^��������Ҧ�m��ܶs
		//��AcitonEvent�ƥ󪺺�ť������
		ActionListener alPlace = new ActionListener(){

			public void actionPerformed(ActionEvent e){

				String strAction = (String) e.getActionCommand();
				//���o�N��޵o�ƥ󤧰ʧ@�R�O�r��

				//�P�_�ʧ@�R�O�r��, �ó]�w���������Ҧ�m
				if("Top".equals(strAction))
					tpPanel.setTabPlacement(JTabbedPane.TOP);
				else if("Bottom".equals(strAction))
					tpPanel.setTabPlacement(JTabbedPane.BOTTOM);
				else if("Left".equals(strAction))
					tpPanel.setTabPlacement(JTabbedPane.LEFT);
				else if("Right".equals(strAction))
					tpPanel.setTabPlacement(JTabbedPane.RIGHT);
			}
		};

		JRadioButton rbTop = new JRadioButton("Top", true),
								rbBottom  = new JRadioButton("Bottom"),
								rbLeft = new JRadioButton("Left"),
								rbRight = new JRadioButton("Right");
		//�ŧi�������m��m����ܶs

		rbTop.addActionListener(alPlace);
		rbBottom.addActionListener(alPlace);
		rbLeft.addActionListener(alPlace);
		rbRight.addActionListener(alPlace);
		//���U�^����ܶsActionEvent�ƥ󪺺�ť������

		ButtonGroup bgPlace = new ButtonGroup();
		bgPlace.add(rbTop);
		bgPlace.add(rbBottom);
		bgPlace.add(rbLeft);
		bgPlace.add(rbRight);
		//�N��ܶs�N�J���s�s��

		//�ŧi�^��������Ұt�m�覡��ܶs
		//��AcitonEvent�ƥ󪺺�ť������
		ActionListener alLayout = new ActionListener(){
			public void actionPerformed(ActionEvent e){

				String strAction = (String) e.getActionCommand();
				//���o�޵oAcitonEvent�ƥ󤧰ʧ@�R�O�r��

				//�P�_�ʧ@�R�O�r��ð�����������Ұt�m�]�w
				if("Wrap".equals(strAction))
					tpPanel.setTabLayoutPolicy(
						JTabbedPane.WRAP_TAB_LAYOUT);
				else if("Scroll".equals(strAction))
					tpPanel.setTabLayoutPolicy(
						JTabbedPane.SCROLL_TAB_LAYOUT );
			}
		};

		JRadioButton rbWrap = new JRadioButton("Wrap", true),
								rbScroll = new JRadioButton("Scroll");
		//�ŧi�^��������Ұt�m�覡��ܶs

		rbWrap.addActionListener(alLayout);
		rbScroll.addActionListener(alLayout);
		//���U��ť���Ұt�m�覡��ܶs��ActionEvent�ƥ�

		ButtonGroup bgLayout = new ButtonGroup();
		bgLayout.add(rbWrap);
		bgLayout.add(rbScroll);
		//�N��ܶs�N�J���s�s��

		Box bxSetting = new Box(BoxLayout.Y_AXIS);
		bxSetting.add(Box.createVerticalStrut(15));
		bxSetting.add(rbTop);
		bxSetting.add(rbBottom);
		bxSetting.add(rbLeft);
		bxSetting.add(rbRight);
		bxSetting.add(Box.createVerticalStrut(15));
		bxSetting.add(rbWrap);
		bxSetting.add(rbScroll);
		//�N��ܶs�N�JBox�e����

		JPanel jpBtn = new JPanel(new GridLayout(1, 4, 5, 5));
		jpBtn.add(btnAdd);
		jpBtn.add(btnRemove);
		//�N�s�W�B�������������O���s�[�J����

		JPanel jpLabel = new JPanel(new GridLayout(1, 2, 5, 5));
		jpLabel.add(lbSelected);
		jpLabel.add(lbTotal);
		//�N��ܿ�������P�`�����Ӽƪ����ҥ[�J����

		Container cp = getContentPane(); //���o���e����
		cp.add(tpPanel); //�N�����[�J���e����
		cp.add(jpBtn, BorderLayout.NORTH);
		cp.add(jpLabel, BorderLayout.SOUTH);
		cp.add(bxSetting, BorderLayout.EAST);

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
	}

	public static void main(String args[]) {
		new TabbedEX(); //�ŧi�����ج[����
	}
}