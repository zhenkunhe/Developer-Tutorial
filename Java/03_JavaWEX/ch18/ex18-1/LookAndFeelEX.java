import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*; //�ޥδ���File���O���M��

import java.util.*;

public class LookAndFeelEX extends JFrame {

	String lookAndFeel = null;
	//���ϥΥ~���Pı���󪺦W��

	Hashtable htLF = new Hashtable();
	//�ŧi�x�s�~���Pı�W�ٻPLookAndFeelInfo���������

	//�H�ΦW���O���覡�w�q�ëŧi��ť������
	ActionListener al = new ActionListener(){

		public void actionPerformed(ActionEvent e){

			String strAction = (String) e.getActionCommand();
			//���o�ʧ@�R�O�r��
			
			try {
				String name = ((UIManager.LookAndFeelInfo) 
					htLF.get(strAction)).getClassName();
				//���o�y�z�~���Pı�����O�W��

				UIManager.setLookAndFeel(name);
				//�]�w�����ج[�ϥΪ��~���Pı

				SwingUtilities.updateComponentTreeUI(
					LookAndFeelEX.this);
				//��s����
			}
			catch (Exception ex) { }
		}
	};

	LookAndFeelEX(){

		JMenu mnLF = new JMenu("Look and Feel");	//�ŧi�\���

		JPanel jpLF = new JPanel(
			new FlowLayout(FlowLayout.CENTER, 10, 5));
		//�ŧi�e�Ǥ��󤺮e��JPanel

		ButtonGroup bgLF = new ButtonGroup(); //�ŧi���s�s��

		UIManager.LookAndFeelInfo[] 
			lafs = UIManager.getInstalledLookAndFeels();
		//���o�ثe�t�Φw�˪��~���Pı

		for(UIManager.LookAndFeelInfo elm : lafs){
			
			String strName = elm.getName();
			//���o�~���Pı���󪺦W��

			htLF.put(strName, elm);
			//�]�w�~���Pı���󪺦W�ٻPLookAndFeelInfo����

			JMenuItem mi = new JMenuItem(strName);
			//�\���ﶵ

			mi.addActionListener(al);
			//���U��ťActionEvent�ƥ󪺺�ť������

			mnLF.add(mi); //�N�ﶵ�[�J�\���

			JToggleButton tb = new JToggleButton(strName);
			//�ŧi�������s����

			tb.addActionListener(al);
			//���U��ťActionEvent�ƥ󪺺�ť������

			jpLF.add(tb); //�N�������s�[�J����
			bgLF.add(tb); //�N�������s�[�J���s�s��
		}

		JRadioButton
			rbOne = new JRadioButton("�ﶵ 1", true),
			rbTwo = new JRadioButton("�ﶵ 2"),
			rbThree = new JRadioButton("�ﶵ 3");
		//�ŧi��ܶs

		ButtonGroup bgRadio  = new ButtonGroup();
		bgRadio.add(rbOne);
		bgRadio.add(rbTwo);
		bgRadio.add(rbThree);
		//�ŧi���s�s��, �å[�J��ܶs

		Box bxRadio = new Box(BoxLayout.Y_AXIS);
		bxRadio.add(rbOne);
		bxRadio.add(rbTwo);
		bxRadio.add(rbThree);
		//�ŧi�e�ǿ�ܶs��Box�e��, �å[�J��ܶs

		JCheckBox
			cbOne = new JCheckBox("�ﶵ 1"),
			cbTwo = new JCheckBox("�ﶵ 2", true),
			cbThree = new JCheckBox("�ﶵ 3");
		//�ŧi�֨����

		Box bxCheck = new Box(BoxLayout.Y_AXIS);
		bxCheck.add(cbOne);
		bxCheck.add(cbTwo);
		bxCheck.add(cbThree);
		//�ŧi�e�Ǯ֨������Box�e��, �å[�J�֨����

		String[] strItem = {"�ﶵ 1", "�ﶵ 2", "�ﶵ 3"};
		//�ŧi�M�������ﶵ�r��

		JList lst = new JList(strItem);
		//�H�ﶵ�r��}�C�ŧiJList����

		JMenuBar mb = new JMenuBar();
		//�ŧi�\���C

		mb.add(mnLF); //�N�\���[�J�\���C

		setJMenuBar(mb); //�]�w�����ج[�ϥΪ��\���C

		//�N�e�Ǥ��󪺮e���[�J����
		JPanel jpPanel = new JPanel(new GridLayout(1, 3,  5, 5));
		jpPanel.add(new JScrollPane(lst));
		jpPanel.add(bxRadio);
		jpPanel.add(bxCheck);

		Container cp = getContentPane(); //���o���e����
		cp.add(jpLF, BorderLayout.NORTH); //�N�e�Ǥ��󪺭����[�J
		cp.add(jpPanel);

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(530, 220);
		setVisible(true);
	}

	public static void main(String args[]) {
		new LookAndFeelEX(); //�ŧi�����ج[����
	}
}