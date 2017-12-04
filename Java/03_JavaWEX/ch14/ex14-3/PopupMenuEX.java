import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class PopupMenuEX extends JFrame{

	JPopupMenu pm = new JPopupMenu();
	//�ŧi�۲{�\���

	PopupMenuEX(){

		JMenuItem miExit = 
				new JMenuItem("����(E)", KeyEvent.VK_E),
			miTextIcon = new JMenuItem(
				"�ϥܿﶵ", new ImageIcon("icon/bitc.gif"));
		//�ŧi�\���ﶵ

		JCheckBoxMenuItem cbmi =
			new JCheckBoxMenuItem("�֨�����ﶵ");
		//�ŧi�֨�����ﶵ

		//�w�q�ëŧi��ťActionEvent�ƥ󪺺�ť��
		miExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});

		pm.add(miTextIcon); //�N�ﶵ�[�J�\���
		pm.add(cbmi);
		pm.add(new JPopupMenu.Separator());
		pm.add(miExit);

		//���U��ťMouseEvent�ƥ󪺺�ť������,
		//�^����, �N����۲{�\���
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getButton() == MouseEvent.BUTTON3){
					pm.show(PopupMenuEX.this, e.getX(), e.getY());
					//�b�ƹ���Ц�m����۲{�\���
				}
			}
		});

		//�w�q�B���U�^���۲{�\���PopupMenuEvent�ƥ󪺺�ť������
		//�ñN�T����X�ܩR�O���ܦr������
		pm.addPopupMenuListener(new PopupMenuListener(){

			//�^������۲{�\���
			public void 
				popupMenuWillBecomeVisible(PopupMenuEvent e){

				System.out.println(
					"�I�spopupMenuWillBecomeVisible()��k");
			}

			//�^�������۲{�\���
			public void 
				popupMenuWillBecomeInvisible(PopupMenuEvent e){

				System.out.println(
					"�I�spopupMenuWillBecomeInvisible()��k");
			}

			//�^�������۲{�\���
			public void 
				popupMenuCanceled(PopupMenuEvent e){

				System.out.println(
					"�I�spopupMenuCanceled()��k");
			}
		});

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new PopupMenuEX(); //�ŧi�����ج[����
	}
}