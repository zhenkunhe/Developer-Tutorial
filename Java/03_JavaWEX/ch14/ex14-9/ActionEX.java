import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class ActionEX extends JFrame {

	JLabel lbEdit = new JLabel(), //�ŧi���Ҥ���
	       lbLeft = new JLabel("�a��"),
	       lbCenter = new JLabel("�m��"),
	       lbRight = new JLabel("�a�k");

	//�w�q�l�ͦ�JButton���O�����s�������O
	class tbButton extends JButton {

		tbButton(Action action){ //�I�s�غc�l��, �ǤJAction����

			super((Icon) action.getValue(Action.SMALL_ICON));
			//�N�ǤJ��ImageIcon����ǤJ��¦���O���غc�l

			setActionCommand((String) action.getValue(Action.NAME));
			//�]�w�ʧ@�R�O�r��

			setToolTipText((String) action.getValue(Action.NAME));
			//�]�w�R�O���ܦr��

			addActionListener(action);
			//���U��Action�����ťActionEvent�ƥ�
		}
	}

	//�w�q�~��AbstractAction���O�����r����]�w��Action����
	class AlignmentAction extends AbstractAction {
		
		AlignmentAction(String name, Icon icon){
			super(name, icon);
			//�N�ʧ@Action����W�ٻP�ϥΪ��ϥܶǤJ��¦���O�غc�l

			putValue(Action.SHORT_DESCRIPTION, name);
			//�]�wAction����²�u�y�z��r
		}

		//�^��ActionEvent�ƥ󪺰ʧ@
		public void actionPerformed(ActionEvent e){

			String name = (String) getValue(Action.NAME);
			//���oAction����W�٪��]�w��

			lbLeft.setEnabled(false); //�]�w���ҵL��
			lbCenter.setEnabled(false);
			lbRight.setEnabled(false);

			//���Action����W�ٳ]�w��������r���Ҭ�����
			//�H�ά����ﶵ�����
			if(name.equals("�a��(L)")){
				lbLeft.setEnabled(true); //�]�w�a�����Ҭ�����
				tbnLeft.setSelected(true); //����a������������s
				rbmiLeft.setSelected(true); //����a����ܶs�ﶵ
			}
			else if(name.equals("�a�k(R)")){
				lbRight.setEnabled(true);
				tbnRight.setSelected(true);
				rbmiRight.setSelected(true);
			}
			else if(name.equals("�m��(C)")){
				lbCenter.setEnabled(true);
				tbnCenter.setSelected(true);
				rbmiCenter.setSelected(true);
			}
		}
	}

	AlignmentAction	
		aaLeft = new AlignmentAction(
							"�a��(L)", new ImageIcon("images/left.gif")),
		aaCenter = new AlignmentAction(
							"�m��(C)", new ImageIcon("images/center.gif")),
		aaRight = new AlignmentAction(
							"�a�k(R)", new ImageIcon("images/right.gif"));
	//�ŧi�����r����ʧ@��Action����

	JToggleButton
			tbnLeft = new JToggleButton(aaLeft),
			tbnCenter = new JToggleButton(aaCenter),
			tbnRight = new JToggleButton(aaRight);
	//�HAction����إ�JToggleButton����

	JRadioButtonMenuItem 
		rbmiLeft = 
			new JRadioButtonMenuItem(aaLeft),
		rbmiCenter = 
			new JRadioButtonMenuItem(aaCenter),
		rbmiRight = 
			new JRadioButtonMenuItem(aaRight);
	//�H�y�z����]�w�ʧ@��Action����ŧi��ܶs�ﶵ

	//�ŧi����s��ʧ@��Action����
	AbstractAction 
		acCut = new AbstractAction("�ŤU", 
									new ImageIcon("images/cut.gif")){

				//�w�q�^��ActionEvent�ƥ󪺤�k
				public void actionPerformed(ActionEvent e) {
					lbEdit.setText((String)getValue(Action.NAME));
					//�N�ʧ@�R�O�r��]�w�����A�C������
				}
			},
		acCopy = new AbstractAction("�ƻs", 
									new ImageIcon("images/copy.gif")){
				public void actionPerformed(ActionEvent e) {
					lbEdit.setText((String)getValue(Action.NAME));
				}
			},
		acPaste = new AbstractAction("�K�W", 
									new ImageIcon("images/paste.gif")){
				public void actionPerformed(ActionEvent e) {
					lbEdit.setText((String)getValue(Action.NAME));
				}
			};

	tbButton tbtnCut = new tbButton(acCut),
					tbtnCopy = new tbButton(acCopy),
					tbtnPaste = new tbButton(acPaste);
	//�H�y�z�s��ʧ@��Action����ŧi�إߦۭq���s��tbButton���O

	JMenuItem miCut = new JMenuItem(acCut),
						miCopy = new JMenuItem(acCopy),
						miPaste = new JMenuItem(acPaste);
	//�HAction����ŧi�\���ﶵ

	ActionEX(){

		acCut.putValue(Action.ACCELERATOR_KEY, 
			KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
		acCopy.putValue(Action.ACCELERATOR_KEY, 
			KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
		acPaste.putValue(Action.ACCELERATOR_KEY, 
			KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));
		//�]�w�s��ʧ@�ϥΪ��[�t��

		JToolBar tbTop = new JToolBar("�s��");
		//�ŧi�u��C����
		
		tbTop.add(tbtnCut);
		tbTop.add(tbtnCopy);
		tbTop.add(tbtnPaste);
		//�N�ۭq���s����[�J�u��C

		miCut.setIcon(null); //�\���ﶵ���ϥιϥ�
		miCopy.setIcon(null);
		miPaste.setIcon(null);

		JMenu mnEdit = new JMenu("�s��");
		mnEdit.add(miCut);
		mnEdit.add(miCopy);
		mnEdit.add(miPaste);
		//�N�s��ﶵ�[�J�\���

		tbTop.addSeparator(); //�[�J���j�Ŷ�

		aaLeft.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		aaCenter.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		aaRight.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		//�]�wAction����ϥΪ��O����

		tbnLeft.setSelected(true);
		//�]�w����a������������s

		tbnLeft.setText(null); //�]�w�������s����ܤ�r
		tbnCenter.setText(null);
		tbnRight.setText(null);

		tbTop.add(tbnLeft); //�NJToggleButton����[�J�u��C
		tbTop.add(tbnCenter);
		tbTop.add(tbnRight);

		ButtonGroup bgToolBar = new ButtonGroup(); //�إ߫��s�s��

		bgToolBar.add(tbnLeft);
		bgToolBar.add(tbnCenter);
		bgToolBar.add(tbnRight);
		//�N�u��C��JToggleButton����[�J���s�s��

		rbmiLeft.setSelected(true); //�]�w����a����ܶs�ﶵ

		rbmiLeft.setIcon(null); //�]�w����ܹϥ�
		rbmiCenter.setIcon(null);
		rbmiRight.setIcon(null);

		ButtonGroup bgAlignMenu = new ButtonGroup(); //�إ߫��s�s��

		bgAlignMenu.add(rbmiLeft);
		bgAlignMenu.add(rbmiCenter);
		bgAlignMenu.add(rbmiRight);
		//�N�u��C��JToggleButton����[�J���s�s��

		tbTop.addSeparator(); //�[�J���j�Ŷ�

		JCheckBox cbAlignEnabled = new JCheckBox("��r�������");
		//�����r����]�wAction����O�_���Ī��֨����

		cbAlignEnabled.setSelected(true); //�w�]�����
		tbTop.add(cbAlignEnabled); //�[�J�u��C

		//�H�ΦW���O���覡�w�q�B���U��ťActionEvent�ƥ󪺺�ť������
		cbAlignEnabled.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				boolean enabled = 
					((JCheckBox)e.getSource()).isSelected();
				//���o�֨�����O�_�Q���

				aaLeft.setEnabled(enabled);
				aaCenter.setEnabled(enabled);
				aaRight.setEnabled(enabled);
				//�]�w����]�wAction����O�_����
			}
		});

		JMenu mnAlign = new JMenu("���");
		mnAlign.add(rbmiLeft); //�N��ܶs�ﶵ�[�J�\���
		mnAlign.add(rbmiCenter);
		mnAlign.add(rbmiRight);

		JMenuBar mb = new JMenuBar(); //�ŧi�\���C
		mb.add(mnEdit); //�N�\���[�J�\���C
		mb.add(mnAlign);

		setJMenuBar(mb); //�]�w�����ج[�ϥΪ��\���C

		JPanel jpStatus = new JPanel(new GridLayout(1, 5));
		//�ŧi�]�t��ܪ��A�T����JPanel�e��

		//�N���ҥ[�J�e��
		jpStatus.add(new JLabel("�s��ʧ@ : ", JLabel.RIGHT));
		jpStatus.add(lbEdit);
		jpStatus.add(lbLeft);
		jpStatus.add(lbCenter);
		jpStatus.add(lbRight);

		lbCenter.setEnabled(false); //�]�w���Ҫ����A���L��
		lbRight.setEnabled(false);

		Container cp = getContentPane(); //���o���e����
		cp.add(tbTop, BorderLayout.NORTH); //�N�u��C�[�J�����ج[
		cp.add(jpStatus, BorderLayout.SOUTH);
		
		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(430, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ActionEX(); //�ŧi�����ج[����
	}
}