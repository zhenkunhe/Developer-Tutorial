import javax.swing.*;
import javax.swing.border.*;
import javax.swing.colorchooser.*;

import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class ColorMenuEX extends JFrame{

	JLabel lbColor = new JLabel();
	//�ŧi��ܿ���C�⪺����

	ColorMenuItem cmi; //�ŧi�ۭq���զ�O�\���ﶵ
	ColorMenu cmUser; //�ŧi�ۭq���զ�O�\���

	ColorMenuEX(){

		lbColor.setOpaque(true); //�]�w���ҭI�����z��
		lbColor.setHorizontalAlignment(JLabel.CENTER);
		//�]�w���Ҥ���r�m�����

		lbColor.setText(lbColor.getBackground().toString());
		//�]�w��ܭI���C�⪺RGB��

		JMenu mnFile = new JMenu("�ɮ�(F)");
		//�ŧi�\���

		mnFile.setMnemonic(KeyEvent.VK_F);
		//�]�w�\����U�вŸ�

		JMenuItem miExit = 
			new JMenuItem("����(E)", KeyEvent.VK_E);
		//�ŧi�\���ﶵ

		//�w�q�ëŧi��ťActionEvent�ƥ󪺺�ť��
		miExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
				//�����{��������
			}
		});

		mnFile.add(miExit); //�N�ﶵ�[�J�\���

		cmUser = new ColorMenu(
			"����C��\���", lbColor.getBackground());
		//�ŧi�ۭq���զ�O�\���
		
		//���U�^�� �T�w ����MouseEvent�ƥ󪺺�ť������
		cmUser.getOKLabel().addMouseListener(new MouseAdapter(){

			//�^�����U�ƹ�����ʧ@
			public void mouseClicked(MouseEvent e){
				Color selColor = cmUser.getSelectedColor();
				//���o�զ�O������C��

				lbColor.setText(selColor.toString());
				//�]�w������ܿ���C�⪺��

				lbColor.setBackground(selColor);
				//�]�w���Ҫ��I���C��
			}
		});

		cmi = new ColorMenuItem(lbColor.getBackground());
		//�ŧi���ѽզ�O���\���ﶵ
		
		//���U�^�� �T�w ����MouseEvent�ƥ󪺺�ť������
		cmi.getOKLabel().addMouseListener(new MouseAdapter(){

			//�^�����U�ƹ�����ʧ@
			public void mouseClicked(MouseEvent e){
				Color selColor = cmi.getSelectedColor();
				//���o�զ�O������C��

				lbColor.setText(selColor.toString());
				//�]�w������ܿ���C�⪺��

				lbColor.setBackground(selColor);
				//�]�w���Ҫ��I���C��
			}
		});

		JMenu mnColorMenu = new JMenu("�C�����ﶵ");
		mnColorMenu.add(cmi); //�[�J�C��ﶵ

		JMenu mnColorItem = new JMenu("�C����");
		//�ŧi�\���

		mnColorItem.add(mnColorMenu); //�[�J�l�\���
		mnColorItem.add(cmUser); //�[�J�l�\���

		JMenuBar jmb = new JMenuBar(); //�ŧi�\���C����
		setJMenuBar(jmb); //�]�w�����ج[�ϥΪ��\���
		jmb.add(mnFile); //�N�\���[�J�\���C
		jmb.add(mnColorItem);

		Container cp = getContentPane(); //���o���e����

		cp.add(lbColor); //�N��r�ϥ[�J���e����

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ColorMenuEX(); //�ŧi�����ج[����
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

//�H�~��JMenu���O���覡�w�q���ѽզ�O����C�⪺�\���
class ColorMenu extends JMenu {

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

	JPanel jpColor = new JPanel();
	//�ŧi�e�ǽզ�O�P �T�w ���Ҫ��e��

	public ColorMenu(String name, Color defColor){ //�غc�l

		super(name); //�I�s��¦���O���غc�l

		lbBtnOK.setOpaque(true); // �T�w ���ҭI�����z��
		lbBtnOK.setBorder(bdBtnRasied); //�]�w���ҨϥΥY�_�ؽu

		JColorChooser cc = new JColorChooser();
		//�ŧi�C���ܾ�

		//���o�C���������զ�O����
		for(AbstractColorChooserPanel elm:
							cc.getChooserPanels()){

			//����C���������W�٬O�_��"�զ�O(S)"
			if((elm.getDisplayName()).equals("�զ�O(S)")){
				accpPanel = elm;
				//�N�ŦX�����C���������]�w��accpPanel�ݩ�

				jpColor.add(elm); //�N�զ�O�[�J�����e��
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

		jpColor.add(lbBtnOK); //�N �T�w ���ҥ[�J�e��
		add(jpColor); //�NjpColor�e���[�J���e����
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
}