import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;

public class StyleEX extends JFrame {

	JTextPane tpStyle = new JTextPane(); //�˦���r����

	String[] strInit = {
			"Java 2 �����{���]�p\n","\n",
			"�o���ѱj�եH�d������Swing�����{���]�p���[��, \n",
			"�Ӥ��O�Ūx�a���ЦU���O�P��k, \n",
			"Ū�̳z�L�o���Ѱ��F�i�H�A�Ѧp��B�ΦU���O�P��k, \n",
			"��i�ǲ߹�ڪ�����, \n", 
			"�Y�K�O��Ǫ̥�i�ֳt�Ƿ|�B��Java�}�o�����{��\n"};
	//�˦���r���������e

	Style slContent = tpStyle.addStyle("����", null),
			  slHeading = tpStyle.addStyle("���D", slContent);
	//�إߥN����P���D�˦���Style����

	JRadioButtonMenuItem 
		rbmiHeading = new JRadioButtonMenuItem("���D"),
		rbmiContent = new JRadioButtonMenuItem("����"),
		rbmiNone = new JRadioButtonMenuItem("�L�˦�", true);
	//�ŧi����q���˦�����ܶs�ﶵ

	JLabel lbStyle = new JLabel();
	//��ܬq���M�Τ��޿�˦��W�٪�����

	//�w�q��@CaretListener����
	//�^��CaretEvent�ƥ󪺺�ť������
	CaretListener cl = new CaretListener(){

		//�^����Ц�m����s�ʧ@
		public void caretUpdate(CaretEvent e){			

			Style slPara = tpStyle.getLogicalStyle();
			//���o�ثe�q���M�Ϊ��޿�˦�

			//���ثe�q���ϥΪ��޿�˦�,
			//�ó]�w�������ﶵ�����
			if(slPara == slContent)
				rbmiContent.setSelected(true);
			else if(slPara == slHeading)
				rbmiHeading.setSelected(true);
			else
				rbmiNone.setSelected(true);

			lbStyle.setText(
				slPara == null? "�L�˦�": slPara.getName());
			//�]�w������ܬq���M�Ϊ��˦�
		}
	};

	StyleEX(){

		StyleConstants.setSpaceAbove(slContent, 3);
		StyleConstants.setSpaceBelow(slContent, 3);
		StyleConstants.setFontSize(slContent, 12);
		StyleConstants.setLeftIndent(slContent, 5.0f);
		//�w�qslContent�˦����˦��ݩ�
		
		StyleConstants.setBold(slHeading, true);
		StyleConstants.setFontSize(slHeading, 16);
		StyleConstants.setSpaceAbove(slHeading, 10);
		StyleConstants.setForeground(slHeading, Color.red);
		StyleConstants.setLeftIndent(slHeading, 0.0f);
		//�w�qslHeading�˦����˦��ݩ�

		tpStyle.addCaretListener(cl);
		//���U�^��CaretEvent�ƥ󪺺�ť��

		//�N��r���e�M�Ϋ��w���޿�˦��s�W�ܤ�r����
		for(int i = (strInit.length-1); i>=0; i--){
			tpStyle.replaceSelection(strInit[i]);
			tpStyle.setLogicalStyle(slContent);
		}

		//�ŧi��ť�q���˦�����ť������
		ActionListener alStyle = new ActionListener(){

			public void actionPerformed(ActionEvent e){

				String command = e.getActionCommand();
				//���o�ʧ@�R�O�r��

				if("�L�˦�".equals(command)){
					tpStyle.setLogicalStyle(null);
					//�]�w��r�q�����M�Υ����޿�˦�
				}
				else{
					tpStyle.setLogicalStyle(
						tpStyle.getStyle(command));
					//�H�ʧ@�R�O�r����o�q��
					//���M���޿�˦���Style����,
					//�ó]�w����r�q�����M�Ϊ��˦�
				}

				lbStyle.setText(command);
				//��ܬq���M�Ϊ��޿�˦��W��
			}
		};

		rbmiHeading.addActionListener(alStyle);
		rbmiContent.addActionListener(alStyle);
		rbmiNone.addActionListener(alStyle);
		//���U��ťActionEvent�ƥ󪺺�ť������

		//�ŧi�ëإ߱���q���˦����\���,
		//�å[�J��ܶs�ﶵ
		JMenu mnStyle = new JMenu("�˦�");
		mnStyle.add(rbmiHeading);
		mnStyle.add(rbmiContent);
		mnStyle.add(rbmiNone);

		//�ŧi���s�s��, �å[�J�]�w�q���˦�����ܶs�ﶵ
		ButtonGroup bgStyle = new ButtonGroup();
		bgStyle.add(rbmiHeading);
		bgStyle.add(rbmiContent);
		bgStyle.add(rbmiNone);

		Box bxMsg = new Box(BoxLayout.X_AXIS);
		bxMsg.add(new JLabel("�q���˦� : "));
		bxMsg.add(lbStyle);

		//�ŧi�\���C, �å[�J�\���
		JMenuBar mb = new JMenuBar();
		mb.add(mnStyle);

		setJMenuBar(mb); //�]�w�\���C

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
		new StyleEX(); //�ŧi�����ج[����
	}
}