import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
//�]�t�B�z��r�������e���������P���O���M��

import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class StyledEditorKitEX extends JFrame {

	JTextPane tpMsg = new JTextPane();
	//��ܤ�r���e����r����

	HashMap actions = new HashMap();
	//�ŧi�x�sAction���������

	JComboBox cmbFont = new JComboBox();
	//�ŧi�ѳ]�w�r�����զX���

	TextAction 
		ta12 = new StyledEditorKit.FontSizeAction("12", 12),
		ta14 = new StyledEditorKit.FontSizeAction("14", 14),
		//�ŧi�إߤu��C�W����r��j�p���O���s��FontSizeAction����

		ta16 = (TextAction) tpMsg.getActionMap().get("font-size-16");
		//�qActionMap���o������TextAction����

	TextAction 
		taRed = 
			new StyledEditorKit.ForegroundAction("Red", Color.red),
		taGreen = new StyledEditorKit.ForegroundAction(
				"Green", Color.green),
		taBlue = 
			new StyledEditorKit.ForegroundAction("Blue", Color.blue),
		taBlack =
			new StyledEditorKit.ForegroundAction(
													"Black", Color.black);
	//�ŧi�إߤu��C�W����e���C�⪺���O���s

	TextAction 
		taBold = new StyledEditorKit.BoldAction(),
		taItalic = new StyledEditorKit.ItalicAction(),
		taUnderline = new StyledEditorKit.UnderlineAction();
	//�ŧi�إߤu��C�W�����r����B����P�U�[���u�����O���s

	String[] strInit = {
		"Java 2 �����{���]�p\n","\n",
		"�o���ѱj�եH�d������Swing�����{���]�p���[��, \n",
		"�Ӥ��O�Ūx�a���ЦU���O�P��k, \n",
		"Ū�̳z�L�o���Ѱ��F�i�H�A�Ѧp��B�ΦU���O�P��k, \n",
		"��i�ǲ߹�ڪ�����, \n", 
		"�Y�K�O��Ǫ̥�i�ֳt�Ƿ|�B��Java�}�o�����{��\n"};
	//�˦���r���������e

	StyledEditorKitEX(){

		//�N��r���e�s�W�ܤ�r����
		for(int i = (strInit.length - 1); i>=0; i--)
			tpMsg.replaceSelection(strInit[i]);
			//�H�m�����覡�s�W���e

		taBold.putValue(Action.NAME, "Bold");
		taItalic.putValue(Action.NAME, "Italic");
		taUnderline.putValue(Action.NAME, "Underline");
		//�]�wAction���󪺦W�٩ʽ�

		createActionTable(tpMsg);
		//�إ߼˦���r�������ʧ@���󪺪��

		JMenuBar mb = new JMenuBar(); //�ŧi�\���C
		mb.add(createEditMenu()); //�N�\���[�J�\���C
		mb.add(createStyleMenu());
		mb.add(createSizeMenu());
		mb.add(createColorMenu());

		setJMenuBar(mb); //�]�w�����ج[�ϥΪ��\���C

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new BorderLayout(5,  5));

		cp.add(new JScrollPane(tpMsg)); //�[�J��r����

		cp.add(createFontToolBar(), BorderLayout.NORTH);
		//�[�J�u��C

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 500);
		setVisible(true);
	}

	private Action getActionByName(String name) {
		return (Action)(actions.get(name));
		//�H�W�٩ʽ���oAction����
	}

	//�B�δy�z��r���󤧹w�]�ʧ@��Action����P��W�٫إ������
	private void createActionTable(JTextComponent textComponent) {

		Action[] actionsArray = textComponent.getActions();
		//���o��r����Ҧ��w�]�إߴy�z�i����ʧ@��Action����

		//�HAction����P��W�٫إ������
		for (int i = 0; i < actionsArray.length; i++) {
			Action a = actionsArray[i];
			actions.put(a.getValue(Action.NAME), a);
			//�NAction����P��W�٩�J�����
		}
	}

	//�H��r����y�z�i����ʧ@��Aciton����إ߽s��\���
    protected JMenu createEditMenu() {

		JMenu menu = new JMenu("�s��");

		//�qDefaultEditorKit���O���o�y�z�s��ʧ@���w�]Action����
		//�H�U���O�N�y�z�ŤU�B�ƻs�P�K�W��Action����[�J�\���
		menu.add(getActionByName(DefaultEditorKit.cutAction));
		menu.add(getActionByName(DefaultEditorKit.copyAction));

		menu.add(
			tpMsg.getActionMap().get(DefaultEditorKit.pasteAction));
		//���o�˦���r������ActionMap����, 
		//�öǤJDefaultEditorKit.pasteAction,
		//���o����K�W�ʧ@��Action����

		menu.addSeparator(); //�s�W���j�Ÿ�

		menu.add(getActionByName(DefaultEditorKit.selectAllAction));
		//�N�y�z����ʧ@��Action����[�J�\���

		return menu;
    }

    //�إߦr���˦��\���
	protected JMenu createStyleMenu() {

		JMenu mnStyle = new JMenu("�r���˦�");
		//�ŧi"�r���˦�"�\���

		JMenuItem 
			miBold = new JMenuItem(taBold),
			miItalic = new JMenuItem(taItalic),
			miUnderline = new JMenuItem(taUnderline);
		//�HMyTextAction����ŧi�\����֨�����ﶵ

		mnStyle.add(miBold); //�N�ʧ@�[�J�\���
		mnStyle.add(miItalic);
		mnStyle.add(miUnderline);

		return mnStyle;
	}

	//�إߦr��j�p�\���
	protected JMenu createSizeMenu() {

		JMenu mnSize = new JMenu("�r��j�p");
		//�ŧi"�r��j�p"�\���

		JMenuItem
			mi12 = new JMenuItem(ta12),
			mi14 = new JMenuItem(ta14),
			mi16 = new JMenuItem(ta16);
		//�HMyTextAction����ŧi�\�����ܶs�ﶵ
	
		mnSize.add(mi12);
		mnSize.add(mi14);
		mnSize.add(mi16);
		//�N�˦��s��u�㪺�]�w��r�j�p��Action����[�J�\���

		return mnSize;
	}

	//�إ߫e���C��\���
	protected JMenu createColorMenu() {

		JMenu mnColor = new JMenu("�e���C��");
		//�ŧi"�e���C��"�\���

		JMenuItem 
			miRed = new JMenuItem(taRed),
			miGreen = new JMenuItem(taGreen),
			miBlue = new JMenuItem(taBlue),
			miBlack = new JMenuItem(taBlack);
		//�ŧi����e���C�⪺��ܶs�ﶵ

		mnColor.add(miRed);
		mnColor.add(miGreen);
		mnColor.add(miBlue);
		mnColor.add(miBlack);
		//�N�]�w�e���C�⪺Action����[�J�\���

		return mnColor;
	}

	//�إ߱���]�w��r�ݩʪ��u��C
	protected JToolBar createFontToolBar(){

		JToolBar tbFont = new JToolBar();
		//�ŧi�u��C����

		JButton 
			tbBold = new JButton(taBold),
			tbItalic = new JButton(taItalic),
			tbUnderline = new JButton(taUnderline);
		//�ŧi����r������B����P�U�[���u���������s

		tbFont.add(tbBold); //�NAction����[�J�u��C
		tbFont.add(tbItalic);
		tbFont.add(tbUnderline);

		tbFont.addSeparator(); //�[�J���j�Ÿ�

		JButton 
			tb12 = 	new JButton(ta12),
			tb14 = 	new JButton(ta14),
			tb16 = 	new JButton(ta16);
		//�ŧi����r��j�p���������s

		//�H�]�w��r�j�p��FontSizeAction����ŧiMyTextAction����,
		//�M��[�J�u��C
		tbFont.add(tb12);
		tbFont.add(tb14);
		tbFont.add(tb16);

		tbFont.addSeparator(); //�[�J���j�Ÿ�

		JButton 
			tbRed = new JButton(taRed),
			tbGreen = new JButton(taGreen),
			tbBlue = new JButton(taBlue),
			tbBlack = new JButton(taBlack);
		//�ŧi����e���C�⪺�������s

		tbFont.add(tbRed);
		tbFont.add(tbGreen);
		tbFont.add(tbBlue);
		tbFont.add(tbBlack);
		//�H�]�w��r�C�⪺ForegroundAction����ŧiMyTextAction����,
		//�M��[�J�u��C

		tbFont.addSeparator(); //�[�J���j�Ÿ�

		//�H��������o�t�δ��Ѫ��Ҧ��r��, 
		//�M��N�r���W�٥[�J�զX������ҫ�����
		Thread td = new Thread(){

			public void run(){

				String[] strFonts = 
					GraphicsEnvironment.getLocalGraphicsEnvironment(
					).getAvailableFontFamilyNames();
				//���o�t�δ��Ѫ��Ҧ��r�����W�٪��r��}�C
								
				DefaultComboBoxModel 
					dcbm = new DefaultComboBoxModel(strFonts);
				//�ŧi�x�s�ۦ����զX����ﶵ���e
				//��DefaultComboBoxModel����

				cmbFont.setModel(dcbm); //�]�w�զX��������e

				cmbFont.setSelectedItem("�s�ө���");
				//�]�w�զX���������r���W��
			}
		};

		td.start(); //�Ұʰ����
		
		//���U��ťActionEvent�ƥ󪺺�ť������
		cmbFont.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				(new StyledEditorKit.FontFamilyAction(
						(String)cmbFont.getSelectedItem(),
						(String)cmbFont.getSelectedItem())
														).actionPerformed(e);
				//�H�զX���������r���ŧiFontFamilyAction����,
				//�éI�sactionPerformed()��k�]�w�r��
			}
		});

		tbFont.add(cmbFont); //�N�զX����[�J�u��C

		return tbFont;		
	}

	public static void main(String args[]) {
		new StyledEditorKitEX(); //�ŧi�����ج[����
	}
}