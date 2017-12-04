import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.*;
import javax.swing.text.*;
//�]�t�B�z��r�������e���������P���O���M��

import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class UndoRedoEX extends JFrame {

	JTextPane tpMsg = new JTextPane(); //��ܤ�r���e����r����

	HashMap actions = new HashMap();
	//�ŧi�x�sAction���������

	String[] strInit = {
		"Java 2 �����{���]�p\n","\n",
		"�o���ѱj�եH�d������Swing�����{���]�p���[��, \n",
		"�Ӥ��O�Ūx�a���ЦU���O�P��k, \n",
		"Ū�̳z�L�o���Ѱ��F�i�H�A�Ѧp��B�ΦU���O�P��k, \n",
		"��i�ǲ߹�ڪ�����, \n", 
		"�Y�K�O��Ǫ̥�i�ֳt�Ƿ|�B��Java�}�o�����{��\n"};
	//�˦���r���������e

	UndoManager undoManager = new UndoManager();
	//�ŧi�޲z����/�٭�ʧ@��UndoManager����

	UndoAction undoAction; //�����٭�ʧ@��Action����
    RedoAction redoAction; //���歫���ʧ@��Action����

	//�H�ŧi�ΦW���O���覡,
	//�w�q��ťUndoableEditEvent�ƥ󪺺�ť������
	UndoableEditListener uel = new UndoableEditListener(){

		//�^���o�ͥi�٭�s��ʧ@����k
		public void
			undoableEditHappened(UndoableEditEvent e) {

			undoManager.addEdit(e.getEdit());
			//�N�޵o�i�٭�s��ʧ@����k�[�JUndoManager����

			undoAction.updateUndoState();
			redoAction.updateRedoState();
			//��sAction����إߤ��ﶵ����ܤ��e
		}
	};

	UndoRedoEX(){

		//�N��r���e�s�W�ܤ�r����
		for(int i = (strInit.length - 1); i>=0; i--)
			tpMsg.replaceSelection(strInit[i]);
			//�H�m�����覡�s�W���e

		tpMsg.getDocument().addUndoableEditListener(uel);
		//���U��ťUndoableEditEvent�ƥ󪺺�ť������

		createActionTable(tpMsg);
		//�إ߼˦���r�������ʧ@���󪺪��

		JMenuBar mb = new JMenuBar(); //�ŧi�\���C
		mb.add(createEditMenu()); //�N�\���[�J�\���C
		mb.add(createUndoMenu()); //�N�\���[�J�\���C

		setJMenuBar(mb); //�]�w�����ج[�ϥΪ��\���C

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new BorderLayout(5,  5));

		cp.add(new JScrollPane(tpMsg)); //�[�J��r����

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 250);
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

		menu.add(getActionByName(DefaultEditorKit.cutAction));
		menu.add(getActionByName(DefaultEditorKit.copyAction));
		//�qDefaultEditorKit���O���o�y�z
		//�ŤU�B�ƻs�s��ʧ@���w�]Action����

		menu.add((TextAction)
			tpMsg.getActionMap().get(DefaultEditorKit.pasteAction));
		//�q��r����ActionMap�����o�y�z�K�W�ʧ@��Action����

		menu.addSeparator(); //�s�W���j�Ÿ�

		Action acSelectAll =
			getActionByName(DefaultEditorKit.selectAllAction);
		//���o�������ʧ@���\���ﶵ

		acSelectAll.putValue(Action.NAME, "����");
		//������ʧ@���r��

		menu.add(acSelectAll);
		//�N�y�z����ʧ@��Action����[�J�\���

		return menu;
    }

	//�H��r����y�z�i����ʧ@��Aciton����إ߽s��\���
    protected JMenu createUndoMenu() {

		undoAction = new UndoAction();
		//�ŧi���歫���ʧ@��Action����

		redoAction = new RedoAction();
		//�ŧi�����٭�ʧ@��Action����

		JMenu menu = new JMenu("�٭�/����");
		//�ŧi�٭�/�����\���

		menu.add(undoAction); //�NAction����[�J�\���
		menu.add(redoAction);

		return menu;
    }

	//�w�q�����٭�ʧ@��Action���O
	class UndoAction extends AbstractAction {

		public UndoAction() {
			super("�٭�"); //�I�s��¦���O���غc�l
			setEnabled(false);
			//�]�w�����٭�ʧ@��Action���󪺪��A���L��
		}

		public void actionPerformed(ActionEvent e) {

			try {
				undoManager.undo(); //�����٭�ʧ@
			}
			catch (CannotUndoException ex) {
				System.out.println("�L�k�٭� : " + ex);
				ex.printStackTrace();
			}

			updateUndoState();
			//��s�٭�ﶵ����ܪ��A

			redoAction.updateRedoState();
			//��s�����ﶵ����ܪ��A
		}

		//��s�����٭�ʧ@��Action������ܪ��A
		protected void updateUndoState() {

			//�P�_�O�_�i�H�٭�
			if (undoManager.canUndo()) {
				setEnabled(true);
				//�]�w�٭�ﶵ���A������

				putValue(Action.NAME, 
					undoManager.getUndoPresentationName());
				//���o�ثe�i�����٭�ʧ@���W��
			}
			else {
				setEnabled(false); //�]�w���L�Ī��A
				putValue(Action.NAME, "�٭�");
				//�]�wAction����NAME�ʽ謰�٭�
			}
		}
	}

	//�w�q���歫���ʧ@��Action���O
	class RedoAction extends AbstractAction {

		public RedoAction() {
			super("����"); //�I�s��¦���O���غc�l
			setEnabled(false);
			//�]�w���歫���ʧ@��Action���󪺪��A���L��
		}

		public void actionPerformed(ActionEvent e) {

			try {
				undoManager.redo(); //���歫���ʧ@
			}
			catch(CannotRedoException ex) {
				System.out.println("�L�k���� : " + ex);
				ex.printStackTrace();
			}

			updateRedoState();
			//��s�����ﶵ����ܪ��A

			undoAction.updateUndoState();
			//��s�٭�ﶵ����ܪ��A
		}

		//��s���歫���ʧ@��Action������ܪ��A
		protected void updateRedoState() {

			if (undoManager.canRedo()) {
				setEnabled(true);
				//�]�w�٭�ﶵ���A������

				putValue(Action.NAME, 
					undoManager.getRedoPresentationName());
				//���o�ثe�i���歫���ʧ@���W��
			}
			else {
				setEnabled(false); //�]�w���L�Ī��A
				putValue(Action.NAME, "����");
				//�]�wAction����NAME�ʽ謰����
			}
		}
	}

	public static void main(String args[]) {
		new UndoRedoEX(); //�ŧi�����ج[����
	}
}