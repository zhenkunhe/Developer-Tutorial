import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.*;

import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

public class UserUIEX extends JFrame {

	UserUIEX(){

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

		//�N�e�Ǥ��󪺮e���[�J����
		JPanel jpPanel = new JPanel(new GridLayout(1, 3,  5, 5));
		jpPanel.add(bxRadio);
		jpPanel.add(bxCheck);

		Container cp = getContentPane(); //���o���e����
		cp.add(jpPanel);

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(330, 180);
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

			UIManager.getDefaults().put(
				"RadioButtonUI", "BitCRadioButtonUI");
			//�����]�w������RadioButtonUI
			//��UI�N�z���󤧧������O�W��
		}
		catch (Exception ex){
			ex.printStackTrace();
		}

		new UserUIEX(); //�ŧi�����ج[����
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

	//�_�lUIDefaults���󤺥~���Pı�Ҥ䴩����ID�P���O�W��
	protected void initClassDefaults(UIDefaults table) {

		super.initClassDefaults(table);
		//�I�s��¦���O��initClassDefaults()��k

		try{
			table.put("CheckBoxUI", "BitCCheckBoxUI");
			//�]�w������CheckBoxUI��UI�N�z���󤧧������O�W��
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	//�N�~���Pı���w�]�귽�]�w��UIDefaults����
	protected void initComponentDefaults(UIDefaults table) {

		super.initComponentDefaults(table);
		//�I�s��¦���O��initComponentDefaults()��k

		ColorUIResource commonBackground =
			new ColorUIResource(255, 255, 255);
		ColorUIResource commonForeground =
			new ColorUIResource(0, 0, 0);
		//�ŧi�w�]���I���C��P�e���C��

		FontUIResource commonFont = 
			new FontUIResource("�s�ө���", Font.BOLD, 12 );
		//�ŧi�w�]�ϥΪ��r��

		//�ŧiUI�귽���ݩʻP�������󪺰}�C
		Object[] defaults =	{
			"Panel.background", commonBackground,
			"Panel.foreground", commonForeground,
			//�]�w�������e���C��P�I���C��

			"CheckBox.font", commonFont,
			"CheckBox.background", commonBackground,
			"CheckBox.foreground", commonForeground,
			//�]�w�֨�����ϥΪ��r���B�e���C��P�I���C��

			"RadioButton.font", commonFont,
			"RadioButton.background", commonBackground,
			"RadioButton.foreground", commonForeground,
			//�]�w��ܶs�ϥΪ��r���B�e���C��P�I���C��
		};

		table.putDefaults(defaults);
		//�I�sputDefaults()��k�ǤJUI�귽���ݩ�
		//�P�������󤧰}�C
	}
}