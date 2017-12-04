import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.text.*;
//�]�t�B�z��r�������e���������P���O���M��

import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class DocumentEX extends JFrame {

	JTextComponent tcContent; //��ܤ�r���e����r����
	JTextArea taMsg = new JTextArea(); //��ܰT������r��
	JTree trDoc; //��ܤ�󵲺c���𪬵��c

	//�w�q��@CaretListener����
	//�^��CaretEvent�ƥ󪺺�ť������
	CaretListener cl = new CaretListener(){

		//�^����Ц�m����s�ʧ@
		public void caretUpdate(CaretEvent e){			

			int dot = e.getDot(), mark = e.getMark();
			//���o��Ц�m�P����d�򪺵�����m

			if(dot == mark){ //�P�_�O�_����d����
				taMsg.append("��Ц�m : " + dot + "\n");
				//��ܴ�Ц�m
			}
			else{
				//�Y��Ц�m�j�����d�򵲧�,
				//�h��̪��ƭȤ���
				if(dot > mark){
					int temp = mark;
					mark = dot;
					dot = temp;
				}

				taMsg.append("����d�� : [ " + dot + " �� " + mark + " ]\n");
				//��ܿ���d���T
			}
		}
	};

	//�w�q��@DocumentListener����
	//�^��DocumentEvent�ƥ󪺺�ť������
	DocumentListener dl = new DocumentListener(){

		Document document;

		//�^���s�W���e�ʧ@
		public void insertUpdate(DocumentEvent e) {
			document = (Document)e.getDocument();
			//���o�B�z��r���󤺮e��Document����

			displayEditInfo(e); //���DocumentEvent�ƥ󪺸�T
			trDoc.updateUI(); //��s��󤺮e�𪬵��c�����
		}

		//�^���R�����e�ʧ@
		public void removeUpdate(DocumentEvent e) {
			document = (Document)e.getDocument();
			displayEditInfo(e);
			trDoc.updateUI();
		}

		//�^�����e�]�w��s�ʧ@
		public void changedUpdate(DocumentEvent e) {
			document = (Document)e.getDocument();
			displayEditInfo(e);
			trDoc.updateUI();
		}

		//���DocumentEvent�ƥ󪺸�T
		private void displayEditInfo(DocumentEvent e) {

			int changeLength = e.getLength();
			//���o��r���󤺮e������

			taMsg.append(e.getType().toString() + " : " +
				changeLength + "�Ӧr��, �q" + e.getOffset() + 
				"��" + (e.getOffset()+changeLength) + "\n");
			//��X�޵o�ƥ󤧤��e�����׻P�_�l��m
		}
	};

	int start = -1, finish = -1;
	//�ŧi�x�s�W������d�򪺰_�l��m�P������m

	DefaultListModel dlmAtt = new DefaultListModel();
	//�ŧi�x�s��ܤ�󤸥��ݩʪ��M����

	DocumentEX(){

		tcContent = createTextArea(); //�إߤ�r��

		tcContent.addCaretListener(cl);
		//���U�^��CaretEvent�ƥ󪺺�ť��

		taMsg.setRows(6); //�]�w��r�Ϫ��C��
		taMsg.setLineWrap(true); //�]�w�۰��_��
        taMsg.setWrapStyleWord(true); //���b��r���_��		

		Document doc = tcContent.getDocument();
		//���o��r����Document����

		doc.addDocumentListener(dl);
		//���U�^��Document����DocumentEvent�ƥ󪺺�ť������

		trDoc = new JTree(
			new DocumentTreeModel(doc.getDefaultRootElement()));
		//�ŧi��ܤ�󵲺c���𪬵��c���

		//���U�B�w�q��ťMouseEvent�ƥ󪺺�ť������
		trDoc.addMouseListener(
										new MouseAdapter(){

			//�^�����U�ƹ�����ʧ@
			public void mouseClicked(MouseEvent e){
				
				JTree source = (JTree)e.getSource();
				//���o�޵o�ƥ󪺾𪬵��c���

				TreePath path = 
					source.getPathForLocation(e.getX(), e.getY());
				//���o�ƹ���Ц�m������������|

				if(path == null) return;
				//�Y������|��null�h�פ����

				Element node = 
					(Element) path.getLastPathComponent();
				//���o���|�̫������`�I, ���૬��Element����

				start = finish = -1; //�M���W��������d��

				tcContent.setSelectionStart(node.getStartOffset());
				tcContent.setSelectionEnd(node.getEndOffset());
				//�]�w���`�I������d��

				tcContent.grabFocus(); //�]�w��r�������o�J�I

				AttributeSet asNode = node.getAttributes();
				//���o��󤸯�����r�ݩ�
				
				dlmAtt.clear(); //�M���M�������ﶵ���e

				dlmAtt.addElement(
					"�ݩʭӼ� : " + asNode.getAttributeCount());
				//�N��r�ݩʪ��Ӽƥ[�J�M����

				//�Hfor�j����o�Ҧ���r�ݩʳ]�w���W��
				for (Enumeration elm = asNode.getAttributeNames();
							elm.hasMoreElements() ;) {

					String strAtt = elm.nextElement().toString();
					//���o�U�@�Ӥ�r�ݩʳ]�w

					//�H��r�ݩʳ]�w���W��, �I�sAttributeSet���O
					//getAttribute()��k���o�]�w���e
					if("size".equals(strAtt)){
						strAtt += " = " + 
							asNode.getAttribute(StyleConstants.FontSize);
						//���o�r���j�p�]�w
					}
					else if("foreground".equals(strAtt)){
						strAtt += " = " + 
							asNode.getAttribute(StyleConstants.Foreground);
						//���o��r�C��]�w
					}
					else if("family".equals(strAtt)){
						strAtt += " = " + 
							asNode.getAttribute(StyleConstants.Family);
						//���o�r���]�w
					}
					else if("bold".equals(strAtt)){
						strAtt += " = " + 
							asNode.getAttribute(StyleConstants.Bold);
						//���o�O�_�����骺�]�w
					}
					else if("italic".equals(strAtt)){
						strAtt += " = " + 
							asNode.getAttribute(StyleConstants.Italic);
						//���o�O�_�����骺�]�w
					}
					else if("underline".equals(strAtt)){
						strAtt += " = " + 
							asNode.getAttribute(StyleConstants.Underline);
						//���o�O�_�U�[���u���]�w
					}
					else if("resolver".equals(strAtt))
						strAtt = "�ݩʫݸѪR"; //�]�w����ݩʫݸѪR
					
					dlmAtt.addElement(strAtt);
					//�N�ݩ���ܷs�W�ܲM����
				}
			}
		});
			
		JList lstAtt = new JList(dlmAtt);
		//�ŧi����ݩʤ��e���M����

		lstAtt.setVisibleRowCount(7);  //�]�w�M�������i���C��

		JMenuBar mb = new JMenuBar(); //�ŧi�\���C
		mb.add(createStyleMenu()); //�N�\���[�J�\���C
		mb.add(createSizeMenu());
		mb.add(createColorMenu());

		setJMenuBar(mb); //�]�w�����ج[�ϥΪ��\���C

		JPanel jpTree = new JPanel(new BorderLayout(5, 5));
		jpTree.add(new JScrollPane(trDoc));
		//�[�J��ܤ�󵲺c���𪬵��c

		jpTree.add(new JScrollPane(lstAtt), BorderLayout.SOUTH);
		//�N����ݩʳ]�w���M�����[�J�e��

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new BorderLayout(5,  5));

		cp.add(tcContent); //�[�J��r����
		cp.add(jpTree, BorderLayout.EAST);
		//�[�J��ܤ�󤺮e���c������

		cp.add(createToolBar(), BorderLayout.NORTH);
		//�N�u��C�[�J�����ج[��

		cp.add(new JScrollPane(taMsg), BorderLayout.SOUTH);
		//�[�J��r��

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 550);
		setVisible(true);
	}

	private JToolBar createToolBar(){

		JRadioButton 
			rbPlain =	new JRadioButton("Plain Document (JTextArea)", true),
			rbSimpleStyle = 
				new JRadioButton("Style Document (²�n���e�BJTextPane)"),
			rbStyle = new JRadioButton("Style Document (JTextPane)");
		//�ŧi��������r����JRadioButton����

		//�ŧi��ťActionEvent�ƥ󪺺�ť������
		ActionListener al = new ActionListener(){
			public void actionPerformed(ActionEvent e){

				String strAction = (String) e.getActionCommand();
				//���o�ʧ@�R�O�r��
					
				tcContent.removeCaretListener(cl);
				//������ťCaretEvent�ƥ󪺺�ť������

				DocumentEX.this.getContentPane().remove(tcContent);
				//������r����

				//�P�_�ʧ@�R�O�r��إ߹�������r����
				if("Plain Document (JTextArea)".equals(strAction))
					tcContent = createTextArea();
				else if("Style Document (²�n���e�BJTextPane)".equals(strAction))
					tcContent = createSimpleTextPane();
				else if("Style Document (JTextPane)".equals(strAction))
					tcContent = createTextPane();
				
				taMsg.setText(null); //�M����r�Ϫ����e

				DocumentEX.this.getContentPane().add(tcContent);
				//���s�[�J��r����

				tcContent.addCaretListener(cl);
				//���U�^��CaretEvent�ƥ󪺺�ť��
	
				Document doc = tcContent.getDocument();
				//���o��r����Document����

				doc.addDocumentListener(dl);
				//���U�^��Document����DocumentEvent�ƥ󪺺�ť������

				trDoc.setModel(
					new DocumentTreeModel(
						doc.getDefaultRootElement()));
				//�ŧi��ܤ�󵲺c���𪬵��c���

				DocumentEX.this.getRootPane().updateUI();
				//��s�ڭ������e��
			}
		};

		rbPlain.addActionListener(al);
		rbSimpleStyle.addActionListener(al);
		rbStyle.addActionListener(al);
		//���U�^��ActionEvent�ƥ󪺺�ť������

		ButtonGroup bg = new ButtonGroup();
		bg.add(rbPlain);
		bg.add(rbSimpleStyle);
		bg.add(rbStyle);
		//�NJRadioButton����[�J���s�s��

		JToolBar tb = new JToolBar();
		tb.add(rbPlain);
		tb.add(rbSimpleStyle);
		tb.add(rbStyle);
		//�NJRadioButton����[�J�u��C

		return tb;
	}

	private JTextArea createTextArea(){

		JTextArea ta = new JTextArea();
		//�ŧi��r�Ϥ���

		String[] strInit = {
			"Java 2 �����{���]�p\n",
			"\n",
			"�줸��� �s��\n",
			"����T �X��"};
		//��r�Ϫ����e
		
		for(String elm: strInit)
			ta.append(elm);
			//�N�r��[�J��r��

		return ta;
	}

	private JTextPane createTextPane(){

		String[] strInit = {
			"Java 2 �����{���]�p", "\n\n",
			"�o���ѱj�եH�d������Swing�����{���]�p���[��, \n",
			"�Ӥ��O�Ūx�a���ЦU���O�P��k, \n",
			"Ū�̳z�L�o���Ѱ��F�i�H�A�Ѧp��B�ΦU���O�P��k, \n",
			"��i�ǲ߹�ڪ�����, \n", 
			"�Y�K�O��Ǫ̥�i�ֳt�Ƿ|�B��Java�}�o�����{��\n"};
		//��󪺤��e

		JTextPane tp = new JTextPane(); //�ŧi�˦���r����
		Document doc = tp.getDocument();
		//���o��r����Document����

		try {
			SimpleAttributeSet sas = new SimpleAttributeSet();
			//�]�w��r�ݩ�

			StyleConstants.setFontFamily(sas, "�رd�פ���");
			StyleConstants.setFontSize(sas, 14);
			StyleConstants.setForeground(sas, Color.black);
			//�]�w�r���B�r���j�p�P�e���C��

			//�N��r���e�P��r�ݩʷs�W�ܤ�r����
			for(int i = 0; i<strInit.length; i++)
				doc.insertString(doc.getLength(), 
					strInit[i], sas);

		} catch (BadLocationException ble) {
			System.err.println("�L�k���T�_�l���e");
		}

		return tp;
	}

	private JTextPane createSimpleTextPane(){

		String str1 = "Java 2 ",
				   str2 = "����",
				   str3 = "�{���]�p"; //�ŧi�r��

		JTextPane tp = new JTextPane(); //�ŧi�˦���r����
		Document doc = tp.getDocument();
		//���o��r����Document����

		try {
			SimpleAttributeSet sas = new SimpleAttributeSet();
			//�]�w��r�ݩ�

			StyleConstants.setFontFamily(sas, "�رd�פ���");
			StyleConstants.setFontSize(sas, 16);
			StyleConstants.setForeground(sas, Color.blue);
			//�]�w�r���B�r���j�p�P�e���C��

			doc.insertString(doc.getLength(), str1, sas);
			//�N��r���e�P��r�ݩʷs�W�ܤ�r����			
			
			StyleConstants.setForeground(sas, Color.red);
			//�]�w�r���B�r���j�p�P�e���C��

			doc.insertString(doc.getLength(), str2, sas);
			//�N��r���e�P��r�ݩʷs�W�ܤ�r����			

			StyleConstants.setForeground(sas, Color.green);
			//�]�w�r���B�r���j�p�P�e���C��

			doc.insertString(doc.getLength(), str3, sas);
			//�N��r���e�P��r�ݩʷs�W�ܤ�r����
		} catch (BadLocationException ble) {
			System.err.println("�L�k���T�_�l���e");
		}

		return tp;
	}


    //�إ߼˦��\���
	protected JMenu createStyleMenu() {

		JMenu mnStyle = new JMenu("��r�˦�");

		Action action = new StyledEditorKit.BoldAction();
		//�ŧi�]�w��r�����餧Action����

		action.putValue(Action.NAME, "Bold");
		//�N�]�w��r���骺Action����[�J�����

		mnStyle.add(action); //�N�ʧ@�[�J�\���

		action = new StyledEditorKit.ItalicAction();
		//�ŧi�]�w��r�����餧Action����

		action.putValue(Action.NAME, "Italic");
		//�N�]�w��r���骺Action����[�J�����

		mnStyle.add(action); //�N�ʧ@�[�J�\���

		return mnStyle;
	}

	//�إߦr��j�p�\���
	protected JMenu createSizeMenu() {

		JMenu mnSize = new JMenu("�r��j�p");

		//�N�˦��s��u�㪺�]�w��r�j�p��Action����[�J�\���
		mnSize.add(
			new StyledEditorKit.FontSizeAction("12", 12));
		mnSize.add(
			new StyledEditorKit.FontSizeAction("14", 14));
		mnSize.add(
			new StyledEditorKit.FontSizeAction("18", 18));

		return mnSize;
	}

	//�إ߫e���C��\���
	protected JMenu createColorMenu() {
		JMenu mnColor = new JMenu("�e���C��");

		//�N�˦��s��u�㪺�]�w��r�e���C�⪺Action����[�J�\���
        mnColor.add(
			new StyledEditorKit.ForegroundAction(
				"Red", Color.red));
		mnColor.add(
			new StyledEditorKit.ForegroundAction(
				"Green", Color.green));
		mnColor.add(
			new StyledEditorKit.ForegroundAction(
				"Blue", Color.blue));
		mnColor.add(
			new StyledEditorKit.ForegroundAction(
				"Black", Color.black));

		return mnColor;
	}

	public static void main(String args[]) {
		new DocumentEX(); //�ŧi�����ج[����
	}
}