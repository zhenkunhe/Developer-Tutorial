import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class ToolbarEX extends JFrame{

	JLabel lbEdit = new JLabel(), //�ŧi���Ҥ���
	       lbLeft = new JLabel("�a��"),
	       lbCenter = new JLabel("�m��"),
	       lbRight = new JLabel("�a�k");

	//�w�q�l�ͦ�JButton���O�����s�������O, �ù�@ActionListener
	class tbButton extends JButton implements ActionListener {

		tbButton(String action,ImageIcon ii){
			super(ii);
			//�N�ǤJ��ImageIcon����ǤJ��¦���O���غc�l

			setActionCommand(action); //�]�w�ʧ@�R�O�r��
			addActionListener(this);
			//�]�w�^��ActionEvent�ƥ󪺺�ť��

			setToolTipText(action); //�]�w�R�O���ܦr��
		}

		//ActionListener�����w�q�^��ActionEvent�ƥ󪺤�k
		public void actionPerformed(ActionEvent e) {
			lbEdit.setText(getActionCommand());
			//�N�ʧ@�R�O�r��]�w�����A�C������
		}
	}

	ToolbarEX(){

		JToolBar tbTop = new JToolBar("�s��");
		//�ŧi�u��C����
		
		tbTop.add(new tbButton("�ŤU", 
			new ImageIcon("images/cut.gif")));
		tbTop.add(new tbButton("�ƻs", 
			new ImageIcon("images/copy.gif")));
		tbTop.add(new tbButton("�K�W", 
			new ImageIcon("images/paste.gif")));
		//�H�ϥܫإ߫��s����, �å[�J�u��C��

		tbTop.addSeparator(); //�[�J���j�Ŷ�

		JToggleButton
			tbnLeft = new JToggleButton(
				new ImageIcon("images/left.gif"), true),
			tbnCenter = new JToggleButton(
				new ImageIcon("images/center.gif")),
			tbnRight = new JToggleButton(
				new ImageIcon("images/right.gif"));
		//�H�ϥܫإ�JToggleButton����, �B�_�l�]�wtbnLeft�����

		tbnLeft.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				lbLeft.setEnabled(true);
				lbCenter.setEnabled(false);
				lbRight.setEnabled(false);
			}
		}); //�]�w�^��ActionEvent�ƥ󪺺�ť��

		tbnCenter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				lbLeft.setEnabled(false);
				lbCenter.setEnabled(true);
				lbRight.setEnabled(false);
			}
		}); //�]�w�^��ActionEvent�ƥ󪺺�ť��

		tbnRight.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				lbLeft.setEnabled(false);
				lbCenter.setEnabled(false);
				lbRight.setEnabled(true);
			}
		}); //�]�w�^��ActionEvent�ƥ󪺺�ť��

		tbTop.add(tbnLeft); //�NJToggleButton����[�J�u��C
		tbTop.add(tbnCenter);
		tbTop.add(tbnRight);

		ButtonGroup bgToolBar = new ButtonGroup(); //�إ߫��s�s��

		bgToolBar.add(tbnLeft);
		bgToolBar.add(tbnCenter);
		bgToolBar.add(tbnRight);
		//�N�u��C��JToggleButton����[�J���s�s��

		tbnLeft.setToolTipText("�a��"); //�]�w�R�O���ܦr���r��
		tbnCenter.setToolTipText("�m��");
		tbnRight.setToolTipText("�a�k");

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
		setSize(400, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ToolbarEX(); //�ŧi�����ج[����
	}
}