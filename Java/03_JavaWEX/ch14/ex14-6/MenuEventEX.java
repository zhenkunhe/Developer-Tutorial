import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

import java.util.*;

public class MenuEventEX extends JFrame{

	JTextArea taMsg = new JTextArea();
	//�ŧi��ܥ\���T������r��

	MenuEventEX(){

		JMenu mnFile = new JMenu("�ɮ�(F)"); //�ŧi�\���
		JMenuItem miExit = 
			new JMenuItem("����(E)", KeyEvent.VK_E);
		//�ŧi�\���ﶵ

		mnFile.setMnemonic(KeyEvent.VK_F); //�]�w�O����

		//�w�q�ëŧi��ťActionEvent�ƥ󪺺�ť��
		miExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});

		miExit.setAccelerator( KeyStroke.getKeyStroke('E',
			 		java.awt.Event.SHIFT_MASK, false));
		//�إ�Shift + E���������}�ﶵ���ֳt��

		mnFile.add(miExit); //�N�ﶵ�[�J�\���

		JMenuItem 	miItemA = new JMenuItem("�ﶵA"),
							miItemB = new JMenuItem("�ﶵB"),
							miItemC = new JMenuItem("�ﶵC");
		//�ŧi�\���ﶵ	

		JMenu mnMenuEvent = new JMenu("MenuEvent�ƥ�");
		mnMenuEvent.add(miItemA); //�N�ﶵ�[�J�l�\���
		mnMenuEvent.add(miItemB);
		mnMenuEvent.add(miItemC);

		//�w�q�B���U�^��MenuEvent�ƥ󪺺�ť������
		mnMenuEvent.addMenuListener(new MenuListener(){
			public void menuSelected(MenuEvent e){
				taMsg.append("�I�smenuSelected()��k\n");
			}

			public void menuDeselected(MenuEvent e){
				taMsg.append("�I�smenuDeselected()��k\n");
			}

			public void menuCanceled(MenuEvent e){
				taMsg.append("�I�smenuCanceled()��k\n");
			}
		});

		JMenuItem 	miItemD = new JMenuItem("�ﶵD"),
							miItemE = new JMenuItem("�ﶵE"),
							miItemF = new JMenuItem("�ﶵF");
		//�ŧi�\���ﶵ	

		JMenu mnMenuDragMouseEvent = 
			new JMenu("MenuDragMouseEvent�ƥ�");
		mnMenuDragMouseEvent.add(miItemD); //�N�ﶵ�[�J�l�\���
		mnMenuDragMouseEvent.add(miItemE);
		mnMenuDragMouseEvent.add(miItemF);

		//�w�q�B���U�^��MenuDragMouseEvent�ƥ󪺺�ť������
		mnMenuDragMouseEvent.addMenuDragMouseListener(
		new MenuDragMouseListener(){
			public void menuDragMouseEntered(
									MenuDragMouseEvent e){
				taMsg.append("menuDragMouseEntered()��k" +
					" �\�����| : " + menuPathToString(e.getPath()) + "\n");
			}

			public void menuDragMouseExited(
										MenuDragMouseEvent e){
				taMsg.append("menuDragMouseExited()��k" +
					" �\�����| : " + menuPathToString(e.getPath()) + "\n");
			}

			public void menuDragMouseDragged(
										MenuDragMouseEvent e){
				taMsg.append("menuDragMouseDragged()��k" +
					" �\�����| : " + menuPathToString(e.getPath()) + "\n");
			}

			public void menuDragMouseReleased(
										MenuDragMouseEvent e){
				taMsg.append("menuDragMouseReleased()��k" +
					" �\�����| : " + menuPathToString(e.getPath()) + "\n");
			}

			//�N�\���ﶵ���|�ഫ���r��
			private String menuPathToString(
										MenuElement[] mePath){

				String[] strPath = new String[mePath.length];
				//�ŧi�x�s�\���ﶵ���|��String�}�C

				//���o�\���ﶵ���|�����󪺦W��
				for(int i=0; i<mePath.length; i++){

					Component elm = mePath[i].getComponent();
					//���o�\�����|������

					//�̷ӫ��O, ���o����W�ٻP���O
					if(elm.getClass() == JMenuBar.class)
						strPath[i] = "�\���C(JMenuBar)";
					else if(elm.getClass() == JPopupMenu.class)
						strPath[i] = ((JPopupMenu)mePath[i].getComponent()
											).getLabel()	+ "(JPopupMenu)";
					else
						strPath[i] = ((JMenuItem)mePath[i].getComponent()
											).getText() + "(JMenuItem)";
				}

				return Arrays.toString(strPath);
				//�ǥX�\���ﶵ�W�٤����|�r��
			}
		});

		JMenuItem 	miItemG = new JMenuItem("�ﶵG"),
							miItemH = new JMenuItem("�ﶵH"),
							miItemI = new JMenuItem("�ﶵI");
		//�ŧi�\���ﶵ	

		JMenu mnMenuKeyEvent = 
			new JMenu("�X�{���ﶵ�~�|Ĳ�oMenuKeyEvent�ƥ�");
		mnMenuKeyEvent.add(miItemG); //�N�ﶵ�[�J�l�\���
		mnMenuKeyEvent.add(miItemH);
		mnMenuKeyEvent.add(miItemI);

		//�w�q�B���U�^��MenuKeyEvent�ƥ󪺺�ť������
		mnMenuKeyEvent.addMenuKeyListener(new MenuKeyListener(){
			public void menuKeyTyped(MenuKeyEvent e){
				taMsg.append("menuKeyTyped()��k" + 
					" �\�����| : " + menuPathToString(e.getPath()) + "\n");
			}

			public void menuKeyPressed(MenuKeyEvent e){
				taMsg.append("menuKeyPressed()��k" + 
					" �\�����| : " + menuPathToString(e.getPath()) + "\n");
			}
			
			public void menuKeyReleased(MenuKeyEvent e){
				taMsg.append("menuKeyReleased()��k" +
					" �\�����| : " + menuPathToString(e.getPath()) + "\n");
			}

			//�N�\���ﶵ���|�ഫ���r��
			private String menuPathToString(MenuElement[] mePath){

				String[] strPath = new String[mePath.length];
				//�ŧi�x�s�\���ﶵ���|��String�}�C

				//���o�\���ﶵ���|�����󪺦W��
				for(int i=0; i<mePath.length; i++){

					Component elm = mePath[i].getComponent();
					//���o�\�����|������

					//�̷ӫ��O, ���o����W�ٻP���O
					if(elm.getClass() == JMenuBar.class)
						strPath[i] = "�\���C(JMenuBar)";
					else if(elm.getClass() == JPopupMenu.class)
						strPath[i] = ((JPopupMenu)mePath[i].getComponent()
											).getLabel()	+ "(JPopupMenu)";
					else
						strPath[i] = ((JMenuItem)mePath[i].getComponent()
											).getText() + "(JMenuItem)";
				}

				return Arrays.toString(strPath);
				//�ǥX�\���ﶵ�W�٤����|�r��
			}
		});

		JMenu mnMenuKeyEventMenu = new JMenu("MenuKeyEvent�ƥ�");
		mnMenuKeyEventMenu.add(mnMenuKeyEvent);

		JMenu mnEvent = new JMenu("�ƥ�^���ܽd");

		mnEvent.add(mnMenuEvent); //�[�J�l�\���
		mnEvent.add(mnMenuDragMouseEvent); //�[�J�l�\���
		mnEvent.add(mnMenuKeyEventMenu); //�[�J�l�\���

		JMenuBar jmb = new JMenuBar(); //�ŧi�\���C����
		setJMenuBar(jmb); //�]�w�����ج[�ϥΪ��\���
		jmb.add(mnFile); //�N�\���[�J�\���C
		jmb.add(mnEvent);

		Container cp = getContentPane();

		cp.add(new JScrollPane(taMsg));

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 300);
		setVisible(true);
	}

	public static void main(String args[]) {
		new MenuEventEX(); //�ŧi�����ج[����
	}
}