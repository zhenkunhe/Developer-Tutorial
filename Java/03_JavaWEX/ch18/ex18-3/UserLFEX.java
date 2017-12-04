import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*; //�ޥδ���File���O���M��

import java.util.*;

import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

public class UserLFEX extends JFrame {

	UserLFEX(){

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

		//�N�e�Ǥ��󪺮e���[�J����
		JPanel jpPanel = new JPanel(new GridLayout(1, 3,  5, 5));
		jpPanel.add(new JScrollPane(lst));
		jpPanel.add(bxRadio);
		jpPanel.add(bxCheck);

		Container cp = getContentPane(); //���o���e����
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

		try{
			LookAndFeel lfBitC = new BitCLF();
			//�ŧi�ۭq���~���Pı����

			UIManager.LookAndFeelInfo info =
				new UIManager.LookAndFeelInfo(lfBitC.getName(),
				lfBitC.getClass().getName());
			//�ŧi�y�z�ۭq�~���Pı����
			//UIManager.LookAndFeelInfo����

			UIManager.installLookAndFeel(info);
			//�w�˴y�z�~���Pı����UIManager.LookAndFeelInfo����

			UIManager.setLookAndFeel(lfBitC);
			//�]�w�ϥΪ��~���Pı����
		}
		catch (Exception ex){
			ex.printStackTrace();
		}

		new UserLFEX(); //�ŧi�����ج[����
	}
}

class BitCLF extends BasicLookAndFeel {

	public String getID() { return "BitCL&F"; }
	//���o�~���Pı����ID

	public String getName() { return "BitC"; }
	//���o�~���Pı���󪺦W��

	public String getDescription() {
		return "L&F from BitC";
	}
	//���o�~���Pı���y�z�r��

	public boolean isNativeLookAndFeel() { return false; }
	//�Y�~���Pı���������ثe�����x�h�Ǧ^true

	public boolean isSupportedLookAndFeel(){ return true; }
	//�Y�ثe�����x�䴩���~���Pı����h�Ǧ^true

	//�N�~���Pı���w�]�귽�]�w��UIDefaults����
	protected void initComponentDefaults(UIDefaults table) {

		super.initComponentDefaults(table);
		//�I�s��¦���O��initComponentDefaults()��k

		ColorUIResource commonBackground =
			new ColorUIResource(255, 255, 255);
		ColorUIResource commonForeground =
			new ColorUIResource(0, 0, 0);
		//�ŧi�w�]���I���C��P�e���C��

		ColorUIResource buttonBackground =
			new ColorUIResource(155, 155, 155);
		ColorUIResource buttonForeground =
			new ColorUIResource(210, 210, 0);
		//�ŧi���s�w�]���I���C��P�e���C��

		ColorUIResource menuBackground =
			new ColorUIResource(155, 155, 155);
		//�ŧi�\���w�]���I���C��

		FontUIResource commonFont = new
			FontUIResource("�s�ө���", Font.BOLD, 12 );
		//�ŧi�w�]�ϥΪ��r��

		Icon check = new ImageIcon("image/check.gif");
		Icon select = new ImageIcon("image/select.gif");
		//�ŧi�֨�����P��ܶs�Q����ɨϥΪ��ϥ�

		//�ŧiUI�귽���ݩʻP�������󪺰}�C
		Object[] defaults =	{
			"Button.font", commonFont,
			"Button.background", buttonBackground,
			"Button.foreground", buttonForeground,
			"Button.margin", new InsetsUIResource(8, 8, 8, 8),
			"Button.textIconGap", new Integer(4),
			"Button.textShiftOffset", new Integer(2),
			//�]�w���s����ϥΪ��귽

			"CheckBox.font", commonFont,
			"CheckBox.background", commonBackground,
			"CheckBox.foreground", commonForeground,
			"CheckBox.icon", new IconUIResource(check),
			//�]�w�֨�����ϥΪ��r���B�e���C��B�I���C��P�ϥ�

			"Panel.background", commonBackground,
			"Panel.foreground", commonForeground,
			//�]�w�������e���C��P�I���C��

			"RadioButton.font", commonFont,
			"RadioButton.background", commonBackground,
			"RadioButton.foreground", commonForeground,
			"RadioButton.icon", new IconUIResource(select),
			//�]�w��ܶs�ϥΪ��r���B�e���C��B�I���C��P�ϥ�

			"ScrollPane.margin", new InsetsUIResource(8, 8, 8, 8),
			"ScrollPane.background", commonBackground,
			//�]�w���ʭ����ϥΪ���ɻP�I���C��

			"ScrollBar.track", menuBackground,
			"ScrollBar.thumb", buttonBackground
			//�]�w���b�y�D�P�C�Τ�����I���C��
		};

		table.putDefaults(defaults);
		//�I�sputDefaults()��k�ǤJUI�귽���ݩ�
		//�P�������󤧰}�C
	}
}
