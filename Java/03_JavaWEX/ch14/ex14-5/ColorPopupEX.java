import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class ColorPopupEX extends JFrame{

	JPopupMenu pm = new JPopupMenu();
	//�ŧi�۲{�\���

	JButton btnColor = new JButton("�C���۲{�\���");
	//�ŧi����C�⪺�۲{�\���

	JLabel lbColor = new JLabel(); //����C��]�w����
	ColorMenuItem cmi; //�\���ﶵ

	ColorPopupEX(){

		lbColor.setHorizontalAlignment(JLabel.CENTER);
		//�]�w��������覡

		lbColor.setOpaque(true); //�]�w���ҭI�����z��
		lbColor.setText(lbColor.getBackground().toString());
		//�]�w��ܼ��ҭI���C�⪺RGB��

		cmi = new ColorMenuItem(lbColor.getBackground());
		//�C��\���ﶵ

		pm.add(cmi);
		//�N�ﶵ�[�J�\���

		JLabel lbOK = cmi.getOKLabel(); //���o �T�w ����

		//���U�^��MouseEvent�ƥ󪺺�ť������
		lbOK.addMouseListener(new MouseAdapter(){

			//�^�����U�ƹ�����ʧ@
			public void mouseClicked(MouseEvent e){
				Color selColor = cmi.getSelectedColor();
				//���o�զ�O������C��

				lbColor.setBackground(selColor);
				//�]�w���Ҫ��I���C��

				lbColor.setText(selColor.toString());
				//�]�w��ܼ��ҭI���C�⪺RGB��
			}
		});

		//���U��ťActionEvent�ƥ󪺺�ť������,
		//�^����, �N����۲{�\���
		btnColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JButton btnSource = (JButton) e.getSource();

				pm.show(btnSource, 0, btnSource.getSize().height);
				//�b���s�U������۲{�\���
			}
		});

		Container cp = getContentPane();
		cp.setLayout(new BorderLayout(5, 5));
		cp.add(lbColor);
		cp.add(btnColor, BorderLayout.SOUTH); //�N���s�[�J

		getRootPane().setBorder(new EmptyBorder(5, 5 , 5 , 5));

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 150);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ColorPopupEX(); //�ŧi�����ج[����
	}
}

//�~��JPanel���O��@MenuElement����, 
//�w�q���ѽզ�O����C�⪺�\���
class ColorMenuItem extends JPanel 
						implements MenuElement {

	CompoundBorder
		bdBtnRasied = new CompoundBorder(
			new BevelBorder(BevelBorder.RAISED),
			new EmptyBorder(2, 4, 2, 4)),
			//�e�{�Y�_�ĪG���ؽu
		bdBtnLowered = new CompoundBorder(
			new BevelBorder(BevelBorder.LOWERED),
			new EmptyBorder(2, 4, 2, 4));
			//�e�{�W���ĪG���ؽu

	AbstractColorChooserPanel accpPanel;
	//�ŧi�C��������

	JLabel lbBtnOK = new JLabel("�T�w");
	//�ŧi���ѫ��s�\�઺����

	public ColorMenuItem(Color defColor){ //�غc�l

		lbBtnOK.setOpaque(true);
		// �T�w ���ҭI�����z��

		lbBtnOK.setBorder(bdBtnRasied); //�]�w���ҨϥΥY�_�ؽu

		JColorChooser cc = new JColorChooser();
		//�ŧi�C������

		//���o�C���������զ�O����
		for(AbstractColorChooserPanel elm:
							cc.getChooserPanels()){

			//����C���������W�٬O�_��"�զ�O(S)"
			if((elm.getDisplayName()).equals("�զ�O(S)")){
				accpPanel = elm;
				//�N�ŦX�����C���������]�w��accpPanel�ݩ�

				add(elm); //�N�զ�O�[�J���e����
			}
		}

		accpPanel.getColorSelectionModel().setSelectedColor(defColor);
		//�]�w�w�]����C��

		//���U��ť �T�w ����MouseEvent�ƥ󪺺�ť������
		lbBtnOK.addMouseListener(new MouseAdapter(){

			private Color oldBackground;
			//�x�s �T�w ���ҭ�l���I���C��

			//�^�����U�ƹ����䪺�ʧ@
			public void mouseClicked(MouseEvent e){

				MenuSelectionManager manager = 
							MenuSelectionManager.defaultManager();
				//���o�޲z�\��������޲z������

				manager.clearSelectedPath(); //�M��������|

				lbBtnOK.setBackground(oldBackground);
				//��_���Ҫ��I���C��

				lbBtnOK.setBorder(bdBtnRasied);
				//�]�w���ҨϥΥY�_�ĪG�ؽu
			}

			//�^���ƹ��i�J���ҽd�򪺰ʧ@
			public void mouseEntered(MouseEvent e){	

				oldBackground = lbBtnOK.getBackground();
				//���o���Ҫ��I���C��

				lbBtnOK.setBackground(new Color(163, 184, 204));
				//�]�w�ƹ���жi�J���ҽd�򪺭I���C��
			}

			//�^���ƹ����}���ҽd�򪺰ʧ@
			public void mouseExited(MouseEvent e){

				lbBtnOK.setBackground(oldBackground);
				//��_���ҭI���C�⪺�]�w

				lbBtnOK.setBorder(bdBtnRasied);
				//�]�w���ҨϥΥY�_�ĪG�ؽu
			}

			//�^�����U�ƹ����䪺�ʧ@
			public void mousePressed(MouseEvent e){	
				lbBtnOK.setBorder(bdBtnLowered);
				//�]�w���ҨϥΥW���ĪG���ؽu
			}
		});

		add(lbBtnOK); //�N �T�w ���ҥ[�J���e����
	}

	//���o�N�����s�� �T�w ����
	public JLabel getOKLabel(){ return lbBtnOK; }

	//�]�w�զ�O������C��
	public void setSelectedColor(Color c){
		accpPanel.getColorSelectionModel().setSelectedColor(c);
	}

	//���o�զ�O������C��
	public Color getSelectedColor(){
		return accpPanel.getColorSelectionModel().getSelectedColor();
	}

	//�H�U����@MenuElement�����w�q����k
	public void processMouseEvent(MouseEvent event,
                       MenuElement[] path,
                       MenuSelectionManager manager){}

	public void processKeyEvent(KeyEvent event,
                     MenuElement[] path,
                     MenuSelectionManager manager){}

	public void menuSelectionChanged(boolean isIncluded){}

	public MenuElement[] getSubElements(){
		return new MenuElement[0];
	}

	public Component getComponent(){ return this; }
}