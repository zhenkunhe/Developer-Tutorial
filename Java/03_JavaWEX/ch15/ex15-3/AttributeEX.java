import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;

public class AttributeEX extends JFrame {

	JTextPane tpStyle = new JTextPane(); //�˦���r����

	String[] strInit = {
			"Java 2 �����{���]�p\n","\n",
			"�o���ѱj�եH�d������Swing�����{���]�p���[��, \n",
			"�Ӥ��O�Ūx�a���ЦU���O�P��k, \n",
			"Ū�̳z�L�o���Ѱ��F�i�H�A�Ѧp��B�ΦU���O�P��k, \n",
			"��i�ǲ߹�ڪ�����, \n", 
			"�Y�K�O��Ǫ̥�i�ֳt�Ƿ|�B��Java�}�o�����{��\n"};
	//�˦���r���������e

	JMenuItem miFirst = new JMenuItem("�����Y��"),
						miRight = new JMenuItem("�k����Y��"),
						miLeft = new JMenuItem("������Y��");
	//�ŧi����q���Y�ƪ��ﶵ

	JMenuItem mi12 = new JMenuItem("12"),
						mi14 = new JMenuItem("14"),
						mi16 = new JMenuItem("16");
	//�ŧi����r��j�p���ﶵ

	JMenuItem 	miRed = new JMenuItem("����"),
						miGreen = new JMenuItem("���"),
						miBlue = new JMenuItem("�Ŧ�"),
						miBlack = new JMenuItem("�¦�");
	//�ŧi����e���C�⪺�ﶵ

	String strSize = "�r��j�p : ",
			   strColor = "�e���C�� : ";
	//��ܦr��j�p�P�e���C�⤧���Ҫ��w�]�r��

	JLabel lbFontSize = new JLabel(strSize),
				lbFontColor = new JLabel(strColor);
	//�ŧi��ܦr��j�p�P�e���C��]�w������

	//�w�q��@CaretListener����
	//�^��CaretEvent�ƥ󪺺�ť������
	CaretListener cl = new CaretListener(){

		//�^����Ц�m����s�ʧ@
		public void caretUpdate(CaretEvent e){

			AttributeSet asInput = tpStyle.getInputAttributes();
			//���o�ثe��J��r�ϥΪ��˦��ݩ�

			lbFontSize.setText(strSize + 
				asInput.getAttribute(StyleConstants.FontSize));
			//���o�r��j�p�˦��ݩʪ��]�w��

			lbFontColor.setText(strColor + 
				asInput.getAttribute(StyleConstants.Foreground));
			//���o�e���C��˦��ݩʪ��]�w��
		}
	};

	AttributeEX(){

		tpStyle.addCaretListener(cl);
		//���U�^��CaretEvent�ƥ󪺺�ť��

		SimpleAttributeSet sasInput =
			(SimpleAttributeSet) tpStyle.getInputAttributes();
		//���o��ƿ�J�ϥΪ��˦�

		StyleConstants.setFontSize(sasInput, 14);
		StyleConstants.setForeground(sasInput, Color.black);
		//�]�w����˦��ݩʪ��r���j�p��14, �e���C�⬰�¦�

		//�N��r���e�s�W�ܤ�r����
		for(int i = (strInit.length - 1); i>=0; i--)
			tpStyle.replaceSelection(strInit[i]);
			//�H�m�����覡�s�W���e

		//�ŧi�^���]�w�r��j�p�ﶵActionEvent�ƥ󪺺�ť������
		ActionListener alFontSize = new ActionListener(){

			public void actionPerformed(ActionEvent e){

				int size = Integer.valueOf(e.getActionCommand());
				//���o�ﶵ�ҫ��w���j�p

				SimpleAttributeSet sas = new SimpleAttributeSet();
				//�ŧi�˦��ݩʪ���

				StyleConstants.setFontSize(sas, size);
				//�̷ӿﶵ���ʧ@�R�O�r��]�w�˦��ݩʪ��r��j�p

				tpStyle.setCharacterAttributes(sas, false);
				//�H�˦��ݩʼW�[�r�����˦��]�w

				lbFontSize.setText(strSize + size);
				//�]�w������ܫ��w���r���j�p
			}
		};

		mi12.addActionListener(alFontSize);
		mi14.addActionListener(alFontSize);
		mi16.addActionListener(alFontSize);
		//���U�^���r��j�p�]�w���ﶵ����ť������

		//�ŧi��ť�r���C�⪺��ť������
		ActionListener alFontColor = new ActionListener(){

			public void actionPerformed(ActionEvent e){

				Color clFont = null;
				String strCommand = e.getActionCommand();
				//���o�ʧ@�R�O�r��

				//�̷Ӱʧ@�R�O�r��]�w���]�w���r���C��
				if("����".equals(strCommand))
					clFont = Color.red;
				else if("���".equals(strCommand))
					clFont = Color.green;
				else if("�Ŧ�".equals(strCommand))
					clFont = Color.blue;
				else if("�¦�".equals(strCommand))
					clFont = Color.black;

				SimpleAttributeSet sas = new SimpleAttributeSet();
				//�ŧi�˦��ݩʪ���

				StyleConstants.setForeground(sas, clFont);
				//�]�w�˦��ݩʪ����C��

				tpStyle.setCharacterAttributes(sas, false);
				//�H�˦��ݩʼW�[�r�����˦��]�w

				lbFontColor.setText(strColor + clFont.toString());
				//�]�w������ܫe���C�⤧��
			}
		};

		miRed.addActionListener(alFontColor);
		miGreen.addActionListener(alFontColor);
		miBlue.addActionListener(alFontColor);
		miBlack.addActionListener(alFontColor);
		//���U��ťActionEvent�ƥ󪺺�ť������

		//�ŧi��ť�r���C�⪺��ť������
		ActionListener alParapraph = new ActionListener(){

			public void actionPerformed(ActionEvent e){

				Color clFont = null;
				String strCommand = e.getActionCommand();
				//���o�ʧ@�R�O�r��

				float indent = 0.0f; //�ŧi���]�w���Y�ƪŶ����j�p

				SimpleAttributeSet sas = new SimpleAttributeSet();
				//�ŧi�˦��ݩʪ���

				//�̷Ӱʧ@�R�O�r��P�_���]�w���q���Y��
				if("�����Y��".equals(strCommand)){

					Object paraIndent =
						tpStyle.getParagraphAttributes().getAttribute(
							StyleConstants.FirstLineIndent);
					//���o�����Y�ƪ���l�]�w

					float firstIndent = 
						(paraIndent == null ? 0.0f: (Float)paraIndent);
					//���o�����Y�Ƴ]�w���B�I�ƭ�

					Object objIndent = JOptionPane.showInputDialog(
						AttributeEX.this, "�]�w�����Y�Ƥj�p", "�q���Y��",
						JOptionPane.INFORMATION_MESSAGE,
						null, null, firstIndent);
					//���o�ϥΪ̿�J�������Y�Ƥj�p���]�w��

					indent = (objIndent == null ? 0.0f:
									Float.valueOf((String) objIndent));
					//�N�]�w���ഫ���B�I��

					StyleConstants.setFirstLineIndent(sas, indent);
					//�]�w�˦��ݩʪ��󪺭����Y�Ƥj�p
				}
				else if("������Y��".equals(strCommand)){

					Object paraIndent = 
						tpStyle.getParagraphAttributes().getAttribute(
							StyleConstants.LeftIndent);
					//���o������Y�ƪ���l�]�w

					float leftIndent =	
						(paraIndent == null ? 0: (Float)paraIndent);
					//���o������Y�Ƴ]�w���B�I�ƭ�

					Object objIndent = JOptionPane.showInputDialog(
						AttributeEX.this, "�]�w������Y�Ƥj�p", "�q���Y��",
						JOptionPane.INFORMATION_MESSAGE,
						null, null, leftIndent);
					//���o�ϥΪ̿�J��������Y�Ƥj�p���]�w��

					indent = (objIndent == null ? 0.0f :
									Float.valueOf((String) objIndent));
					//�N�]�w���ഫ���B�I��

					StyleConstants.setLeftIndent(sas, indent);
					//�]�w�˦��ݩʪ��󪺥�����Y�Ƥj�p			
				}
				else if("�k����Y��".equals(strCommand)){

					Object paraIndent =
						tpStyle.getParagraphAttributes().getAttribute(
							StyleConstants.RightIndent);
					//���o�k����Y�ƪ���l�]�w

					float rightIndent = 
						(paraIndent == null ? 0: (Float) paraIndent);
					//���o�k����Y�Ƴ]�w���B�I�ƭ�

					Object objIndent = JOptionPane.showInputDialog(
						AttributeEX.this, "�]�w�k����Y�Ƥj�p", "�q���Y��",
						JOptionPane.INFORMATION_MESSAGE,
						null, null, rightIndent);
					//���o�ϥΪ̿�J���k����Y�Ƥj�p���]�w��

					indent = (objIndent == null ? 0.0f :
									Float.valueOf((String) objIndent));
					//�N�]�w���ഫ���B�I��

					StyleConstants.setRightIndent(sas, indent);
					//�]�w�˦��ݩʪ��󪺥k����Y�Ƥj�p				
				}

				tpStyle.setParagraphAttributes(sas, false);
				//�H�˦��ݩʼW�[�r�����˦��]�w
			}
		};

		miFirst.addActionListener(alParapraph);
		miRight.addActionListener(alParapraph);
		miLeft.addActionListener(alParapraph);
		//���U�^���r��j�p�]�w���ﶵ����ť������

		//�ŧi�ëإ߱���r��j�p���\���,
		//�å[�J�ﶵ
		JMenu mnFontSize = new JMenu("�r��j�p");
		mnFontSize.add(mi12);
		mnFontSize.add(mi14);
		mnFontSize.add(mi16);

		//�ŧi�ëإ߱���e���C�⪺�\���,
		//�å[�J�ﶵ
		JMenu mnFontColor = new JMenu("�C��");
		mnFontColor.add(miRed);
		mnFontColor.add(miGreen);
		mnFontColor.add(miBlue);
		mnFontColor.add(miBlack);

		//�ŧi�ëإ߱���q���Y�ƪ��\���,
		//�å[�J�ﶵ
		JMenu mnParapraph = new JMenu("�q��");
		mnParapraph.add(miFirst);
		mnParapraph.add(miRight);
		mnParapraph.add(miLeft);

		//�ŧi�\���C, �å[�J�\���
		JMenuBar mb = new JMenuBar();
		mb.add(mnFontSize);
		mb.add(mnFontColor);
		mb.add(mnParapraph);

		setJMenuBar(mb); //�]�w�\���C

		//�N�e����ܦr���j�p�P�e���C����Ҫ�Box�e��
		Box bxMsg = new Box(BoxLayout.Y_AXIS);
		bxMsg.add(lbFontSize);
		bxMsg.add(lbFontColor);

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new BorderLayout(5,  5));
		//�]�w�ϥζ��j�e�׬�5��BorderLayout����t�m����

		cp.add(new JScrollPane(tpStyle));
		cp.add(bxMsg, BorderLayout.SOUTH);

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
	}

	public static void main(String args[]) {
		new AttributeEX(); //�ŧi�����ج[����
	}
}