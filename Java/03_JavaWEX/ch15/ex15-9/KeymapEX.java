import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;

public class KeymapEX extends JFrame {

	JTextPane tpStyle = new JTextPane(); //�˦���r����

	String[] strInit = {
			"Java 2 �����{���]�p\n","\n",
			"�o���ѱj�եH�d������Swing�����{���]�p���[��, \n",
			"�Ӥ��O�Ūx�a���ЦU���O�P��k, \n",
			"Ū�̳z�L�o���Ѱ��F�i�H�A�Ѧp��B�ΦU���O�P��k, \n",
			"��i�ǲ߹�ڪ�����, \n", 
			"�Y�K�O��Ǫ̥�i�ֳt�Ƿ|�B��Java�}�o�����{��\n"};
	//�˦���r���������e

	KeymapEX(){

		SimpleAttributeSet sasInput =
			(SimpleAttributeSet) tpStyle.getInputAttributes();
		//���o��ƿ�J�ϥΪ��˦�

		StyleConstants.setFontSize(sasInput, 14);
		//�]�w��ƿ�J�˦��ݩʪ��r���j�p��14

		StyleConstants.setForeground(sasInput, Color.black);
		//�]�w�e���C�⬰�¦�

		tpStyle.replaceSelection("Alt + E �]�w�M�αj�զr��\n");

		StyleConstants.setForeground(sasInput, Color.red);
		//�]�w�e���C�⬰����

		tpStyle.replaceSelection("Alt + R �]�w�e���C�⬰����\n\n");

		StyleConstants.setForeground(sasInput, Color.green);
		//�]�w�e���C�⬰���

		tpStyle.replaceSelection("Alt + G �]�w�e���C�⬰���\n");

		StyleConstants.setForeground(sasInput, Color.blue);
		//�]�w�e���C�⬰�Ŧ�

		tpStyle.replaceSelection("\nAlt + B �]�w�e���C�⬰�Ŧ�\n");

		StyleConstants.setForeground(sasInput, Color.black);
		//�]�w�e���C�⬰�¦�

		//�N��r���e�s�W�ܤ�r����
		for(int i = (strInit.length - 1); i>=0; i--)
			tpStyle.replaceSelection(strInit[i]);
			//�H�m�����覡�s�W���e

		ActionMap am = tpStyle.getActionMap();
		//���oJTextPane����ϥΪ�ActionMap����

		am.put("emphasis", new AbstractAction(){
			public void actionPerformed(ActionEvent e){
				new StyledEditorKit.BoldAction().actionPerformed(e);
				//�]�w�r�鬰����

				new StyledEditorKit.FontSizeAction(
					"16", 16).actionPerformed(e);
				//�]�w�r��j�p��16
			}
		});
		//�N�w�q��r���j�ծ榡������,
		//�r��j�p��16��Action����m�JActionMap����

		tpStyle.getInputMap().put(
			KeyStroke.getKeyStroke(
				'E', java.awt.event.InputEvent.ALT_MASK),
				"emphasis");
		//�w�q���UAlt + E�i�]�w�ϥαj�ռ˦�

		Keymap km = tpStyle.getKeymap();
		//���o�@�ɪ�Keymap����

		Keymap kmStyle = tpStyle.addKeymap("New Keymap", km);
		//�إߨѼ˦���r�����B�Ϊ�Keymap����

		kmStyle.addActionForKeyStroke(
			KeyStroke.getKeyStroke(
				'R', java.awt.event.InputEvent.ALT_MASK),
			new StyledEditorKit.ForegroundAction(
				"Red", Color.red));
		//�w�q���UAlt + R�i�]�w�e���C�⬰����

		kmStyle.addActionForKeyStroke(
			KeyStroke.getKeyStroke(
				'G', java.awt.event.InputEvent.ALT_MASK),
			new StyledEditorKit.ForegroundAction(
				"Green", Color.green));
		//�w�q���UAlt + G�i�]�w�e���C�⬰���

		kmStyle.addActionForKeyStroke(
			KeyStroke.getKeyStroke(
				'B', java.awt.event.InputEvent.ALT_MASK),
			new StyledEditorKit.ForegroundAction(
				"Blue", Color.blue));
		//�w�q���UAlt + B�i�]�w�e���C�⬰�Ŧ�

		Container cp = getContentPane(); //���o���e����

		cp.add(new JScrollPane(tpStyle));

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 350);
		setVisible(true);
	}

	public static void main(String args[]) {
		new KeymapEX(); //�ŧi�����ج[����
	}
}