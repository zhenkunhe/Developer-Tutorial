import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
//�]�t�B�z��r�������e���������P���O���M��

import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class TextActionEX extends JFrame {

	JTextPane tpMsg = new JTextPane(); //��ܤ�r���e����r����

	HashMap actions = new HashMap();
	//�ŧi�x�sAction���������

	JComboBox cmbFont = new JComboBox();
	//�ŧi�ѳ]�w�r�����զX���

	//�w�q��@CaretListener����
	//�^��CaretEvent�ƥ󪺺�ť������
	CaretListener cl = new CaretListener(){

		//�^����Ц�m����s�ʧ@
		public void caretUpdate(CaretEvent e){

			AttributeSet asCur = tpMsg.getCharacterAttributes();
			//���o�ثe�]�w��AttributeSet����

			mtaItalic.setSelected(false);
			mtaUnderline.setSelected(false);
			mtaBold.setSelected(false);
			//���]�w���������B�U�[���u�P���骺�]�w

			//���o��r�]�w���ݩ�
			for (Enumeration obj = asCur.getAttributeNames();
							obj.hasMoreElements() ;) {

				String strAtt = obj.nextElement().toString();
					//���o�U�@�Ӥ�r�ݩʳ]�w

				if("size".equals(strAtt)){
					int size = (Integer)asCur.getAttribute(StyleConstants.FontSize);
					//���o�]�w���r��j�p

					if(size == 12)
						mta12.setSelected(true);
					else if(size == 14)
						mta14.setSelected(true);
					else if(size == 16)
						mta16.setSelected(true);
				}
				else	if("foreground".equals(strAtt)){
					Color color = (Color) asCur.getAttribute(
												StyleConstants.Foreground);
					//���o�]�w���I���C��

					//�P�_����]�w���C��
					if(Color.red.equals(color))
						mtaRed.setSelected(true);
					else if(Color.green.equals(color))
						mtaGreen.setSelected(true);
					else if(Color.blue.equals(color))
						mtaBlue.setSelected(true);
					else if(Color.black.equals(color))
						mtaBlack.setSelected(true);
				}
				else	if("italic".equals(strAtt)){
					mtaItalic.setSelected(
						(Boolean) asCur.getAttribute(StyleConstants.Italic));
					//���o�]�w������]�w
				}
				else	if("underline".equals(strAtt)){
					mtaUnderline.setSelected(
						(Boolean) asCur.getAttribute(StyleConstants.Underline));
					//���o�]�w���U�[���u�]�w
				}
				else	if("bold".equals(strAtt)){
					mtaBold.setSelected(
						(Boolean) asCur.getAttribute(StyleConstants.Bold));
					//���o�]�w������]�w
				}
				else	if("family".equals(strAtt)){
					cmbFont.setSelectedItem(
						asCur.getAttribute(StyleConstants.FontFamily));
					//�]�w�զX�����ܪ�����r��
				}
			}
		}
	};

	MyTextAction 
		mta12 = new MyTextAction(
							new StyledEditorKit.FontSizeAction("12", 12)),
		mta14 = new MyTextAction(
							new StyledEditorKit.FontSizeAction("14", 14), true),
		mta16 = new MyTextAction(
							new StyledEditorKit.FontSizeAction("16", 16));
	//�ŧi�إߤu��C�W����r��j�p�����O���s

	MyTextAction 
		mtaRed = new MyTextAction(
			new StyledEditorKit.ForegroundAction("Red", Color.red)),
		mtaGreen = new MyTextAction(
			new StyledEditorKit.ForegroundAction(
				"Green", Color.green)),
		mtaBlue = new MyTextAction(
			new StyledEditorKit.ForegroundAction("Blue", Color.blue)),
		mtaBlack = new MyTextAction(
			new StyledEditorKit.ForegroundAction(
				"Black", Color.black),	true);
	//�ŧi�إߤu��C�W����e���C�⪺���O���s

	MyTextAction 
		mtaBold = new MyTextAction(
							new StyledEditorKit.BoldAction()),
		mtaItalic = new MyTextAction(
							new StyledEditorKit.ItalicAction()),
		mtaUnderline = new MyTextAction(
							new StyledEditorKit.UnderlineAction());
	//�ŧi�إߤu��C�W�����r����B����P�U�[���u�����O���s

	String[] strInit = {
		"Java 2 �����{���]�p\n","\n",
		"�o���ѱj�եH�d������Swing�����{���]�p���[��, \n",
		"�Ӥ��O�Ūx�a���ЦU���O�P��k, \n",
		"Ū�̳z�L�o���Ѱ��F�i�H�A�Ѧp��B�ΦU���O�P��k, \n",
		"��i�ǲ߹�ڪ�����, \n", 
		"�Y�K�O��Ǫ̥�i�ֳt�Ƿ|�B��Java�}�o�����{��\n"};
	//�˦���r���������e

	TextActionEX(){

		tpMsg.addCaretListener(cl); //���U�^��CaretEvent�ƥ󪺺�ť��

		SimpleAttributeSet asInput = (SimpleAttributeSet)tpMsg.getInputAttributes();
		//���o��ƿ�J�ϥΪ��˦�

		StyleConstants.setFontSize(asInput, 14);
		StyleConstants.setFontFamily(asInput, "�s�ө���");
		StyleConstants.setForeground(asInput, Color.black);
		//�]�w����˦��ݩʪ��r�����ө���,
		//�r��j�p��14, �e���C�⬰�¦�

		//�N��r���e�s�W�ܤ�r����
		for(int i = (strInit.length - 1); i>=0; i--)
			tpMsg.replaceSelection(strInit[i]);
			//�H�m�����覡�s�W���e

		mtaBold.putValue(Action.NAME, "Bold");
		mtaItalic.putValue(Action.NAME, "Italic");
		mtaUnderline.putValue(Action.NAME, "Underline");
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
		setSize(750, 450);
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
		JMenu mnEdit = new JMenu("�s��");

		//�qDefaultEditorKit���O���o�y�z�s��ʧ@���w�]Action����
		//�H�U���O�N�y�z�ŤU�B�ƻs�P�K�W��Action����[�J�\���
		mnEdit.add(getActionByName(DefaultEditorKit.cutAction));
		mnEdit.add(getActionByName(DefaultEditorKit.copyAction));
		mnEdit.add(getActionByName(DefaultEditorKit.pasteAction));

		mnEdit.addSeparator(); //�s�W���j�Ÿ�

		mnEdit.add(getActionByName(DefaultEditorKit.selectAllAction));
		//�N�y�z����ʧ@��Action����[�J�\���

		return mnEdit;
    }

    //�إ߼˦��\���
	protected JMenu createStyleMenu() {

		JMenu mnStyle = new JMenu("�r���˦�");
		JCheckBoxMenuItem 
			cbmiBold = new JCheckBoxMenuItem(mtaBold),
			cbmiItalic = new JCheckBoxMenuItem(mtaItalic),
			cbmiUnderline = new JCheckBoxMenuItem(mtaUnderline);
		//�HMyTextAction����ŧi�\����֨�����ﶵ

		mtaBold.add(cbmiBold); //�N�֨�����ﶵ�[�J�ʧ@����
		mtaItalic.add(cbmiItalic);
		mtaUnderline.add(cbmiUnderline);

		mnStyle.add(cbmiBold); //�N�ʧ@�[�J�\���
		mnStyle.add(cbmiItalic);
		mnStyle.add(cbmiUnderline);

		return mnStyle;
	}

    //�إߦr��j�p�\���
	protected JMenu createSizeMenu() {

		JMenu mnSize = new JMenu("�r��j�p");

		JRadioButtonMenuItem
			rbmi12 = new JRadioButtonMenuItem(mta12),
			rbmi14 = new JRadioButtonMenuItem(mta14),
			rbmi16 = new JRadioButtonMenuItem(mta16);
		//�HMyTextAction����ŧi�\�����ܶs�ﶵ

		mta12.add(rbmi12); //�N��ܶs�ﶵ�[�J�ʧ@����
		mta14.add(rbmi14);
		mta16.add(rbmi16);
	
		ButtonGroup bgMnFontSize = new ButtonGroup();
		bgMnFontSize.add(rbmi12);
		bgMnFontSize.add(rbmi14);
		bgMnFontSize.add(rbmi16);
		//�N��ܶs�ﶵ�[�J���s�s��

		mnSize.add(rbmi12);
		mnSize.add(rbmi14);
		mnSize.add(rbmi16);
		//�N�˦��s��u�㪺�]�w��r�j�p��Action����[�J�\���

		return mnSize;
	}

    //�إ߫e���C��\���
	protected JMenu createColorMenu() {

		JMenu mnColor = new JMenu("�e���C��");

		JRadioButtonMenuItem 
			rbmiRed = new JRadioButtonMenuItem(mtaRed),
			rbmiGreen = new JRadioButtonMenuItem(mtaGreen),
			rbmiBlue = new JRadioButtonMenuItem(mtaBlue),
			rbmiBlack = new JRadioButtonMenuItem(mtaBlack);
		//�ŧi����e���C�⪺��ܶs�ﶵ

		mtaRed.add(rbmiRed);
		mtaGreen.add(rbmiGreen);
		mtaBlue.add(rbmiBlue);
		mtaBlack.add(rbmiBlack);
		//�N��ܶs�ﶵ�[�J�ʧ@����

		mnColor.add(rbmiRed);
		mnColor.add(rbmiGreen);
		mnColor.add(rbmiBlue);
		mnColor.add(rbmiBlack);
		//�N�]�w�e���C�⪺Action����[�J�\���

		ButtonGroup bgMnColor = new ButtonGroup();
        bgMnColor.add(rbmiRed);
		bgMnColor.add(rbmiGreen);
		bgMnColor.add(rbmiBlue);
		bgMnColor.add(rbmiBlack);
		//�N��ܶs�ﶵ�[�J���s�s��

		return mnColor;
	}

	//�إ߱���]�w��r�ݩʪ��u��C
	protected JToolBar createFontToolBar(){

		JToolBar tbFont = new JToolBar();
		//�ŧi�u��C����

		JToggleButton 
			tbBold = new JToggleButton(mtaBold),
			tbItalic = new JToggleButton(mtaItalic),
			tbUnderline = new JToggleButton(mtaUnderline);
		//�ŧi����r������B����P�U�[���u���������s

		mtaBold.add(tbBold);
		mtaItalic.add(tbItalic);
		mtaUnderline.add(tbUnderline);
		//�N�������s�[�JMyTextAction����

		tbFont.add(tbBold); //�NAction����[�J�u��C
		tbFont.add(tbItalic);
		tbFont.add(tbUnderline);

		tbFont.addSeparator(); //�[�J���j�Ÿ�

		JToggleButton 
			tb12 = 	new JToggleButton(mta12),
			tb14 = 	new JToggleButton(mta14),
			tb16 = 	new JToggleButton(mta16);
		//�ŧi����r��j�p���������s

		mta12.add(tb12);
		mta14.add(tb14);
		mta16.add(tb16);
		//�N�������s�[�J�u��C

		ButtonGroup bgFontSize = new ButtonGroup();
		bgFontSize.add(tb12);
		bgFontSize.add(tb14);
		bgFontSize.add(tb16);
		//�N����r��j�p���������s�[�J���s�s��

		//�H�]�w��r�j�p��FontSizeAction����ŧiMyTextAction����,
		//�M��[�J�u��C
		tbFont.add(tb12);
		tbFont.add(tb14);
		tbFont.add(tb16);

		tbFont.addSeparator(); //�[�J���j�Ÿ�

		JToggleButton 
			tbRed = new JToggleButton(mtaRed),
			tbGreen = new JToggleButton(mtaGreen),
			tbBlue = new JToggleButton(mtaBlue),
			tbBlack = new JToggleButton(mtaBlack);
		//�ŧi����e���C�⪺�������s

		mtaRed.add(tbRed);
		mtaGreen.add(tbGreen);
		mtaBlue.add(tbBlue);
		mtaBlack.add(tbBlack);
		//�N�������s�[�J�ʧ@����

		ButtonGroup bgColor = new ButtonGroup();
        bgColor.add(tbRed);
		bgColor.add(tbGreen);
		bgColor.add(tbBlue);
		bgColor.add(tbBlack);
		//�N����e���C�⪺�������s�[�J���s�s��

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

		td.start(); //�Ұʰ���
		
		//���U��ťActionEvent�ƥ󪺺�ť������
		cmbFont.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				new MyTextAction(new StyledEditorKit.FontFamilyAction(					
						(String)cmbFont.getSelectedItem(),
						(String)cmbFont.getSelectedItem())
														).actionPerformed(e);
				//�H�զX���������r���ŧiFontFamilyAction����,
				//�éI�sactionPerformed()��k�]�w�r��

				tpMsg.grabFocus();
				//�]�w�޵o�ʧ@����r������o�J�I
			}
		});

		tbFont.add(cmbFont); //�N�զX����[�J�u��C

		return tbFont;		
	}

	public static void main(String args[]) {
		new TextActionEX(); //�ŧi�����ج[����
	}
}

//�H�~��TextAction���O���覡�ŧiMyTextAction���O
class MyTextAction extends TextAction {

	TextAction action; //��ڰ���s��ʧ@��Action����
	ArrayList<AbstractButton> alBtn = new ArrayList<AbstractButton>();
	//�N���s����[�JArrayList����

	boolean selected = false; //����O�_��������L��

	MyTextAction(TextAction action){

		super((String)action.getValue(Action.NAME));
		//�I�s��¦���O���غc�l�öǤJAction����NAME�ʽ�

		this.action = action;
	}

	MyTextAction(TextAction action, boolean selected){

		super((String)action.getValue(Action.NAME));
		//�I�s��¦���O���غc�l�öǤJAction����NAME�ʽ�

		this.action = action; //�N�ʧ@����]�w��action�ݩ�

		this.selected = selected; //�]�w�ʧ@���󪺿���]�w
	}

	public void actionPerformed(ActionEvent e){

		JTextComponent tc = getTextComponent(e);
		//���o�޵o�ƥ󪺤�r����

		//�H�[�j��for�j��]�w�HTextAction����إߪ����󬰿��
		for(AbstractButton elm :  alBtn){
			Object source = e.getSource();
			//���o�޵o�ƥ󪺨ӷ�

			if(elm != source){
				elm.setSelected(((AbstractButton)source).isSelected());
				//�]�w���󬰿��
			}
		}

		if(tc == null) return; //�Y��r����null�h�פ�����k

		action.actionPerformed(e);
		//�����¦���O�^��ActionEvent�ƥ�actionPerformed()��k

		tc.grabFocus();
		//�]�w�޵o�ʧ@����r������o�J�I
	}

	//�N�HMyTextAction����إߪ�����[�JArrayList�e��
	public void add(AbstractButton ab){
		alBtn.add(ab); //�N����[�JArrayList�e��
		ab.setSelected(selected); //�]�w������_�l������A
	}

	//�]�wMyTextAction����إߪ��Ҧ������������A
	public void setSelected(boolean bl){
		for(AbstractButton elm : alBtn)
			elm.setSelected(bl);
	}
}