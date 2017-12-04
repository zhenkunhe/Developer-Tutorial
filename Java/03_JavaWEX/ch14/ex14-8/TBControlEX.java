import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class TBControlEX extends JFrame{

	JTextArea taMsg = new JTextArea();
	//��ܱ���ʧ@�R�O�r�ꪺ��r��

	//�w�q�ŧi�^��ActionEvent�ƥ󪺺�ť������
	ActionListener al = new ActionListener(){
		
		public void actionPerformed(ActionEvent e) {

			//�P�_�o�X�ƥ󪺱���O�_��JComboBox
			if(e.getSource() instanceof JComboBox){
				taMsg.append(
					((JComboBox)e.getSource()).getSelectedItem() + "\n");
				//�N�ʧ@�R�O�r��s�W�ܤ�r��
				return;
			}

			taMsg.append(e.getActionCommand() + "\n");
			//�N�ʧ@�R�O�r��s�W�ܤ�r��
		}
	};

	TBControlEX(){

		JToolBar tbTop = new JToolBar("���");
		//�ŧi�u��C����
		
		String[] strAry = {"�զX����ﶵ�@",
									"�զX����ﶵ�G",
									"�զX����ﶵ�T"};
		//�w�q�զX������ﶵ�r��


		JComboBox cbItem = new JComboBox(strAry);
		//�H�ﶵ�r��ŧi�զX���

		JButton btnItem = new JButton("�T�w"); //�ŧi���O���s

		JCheckBox cbOne = new JCheckBox("�֨����A"),
							cbTwo = new JCheckBox("�֨����B"),
							cbThree = new JCheckBox("�֨����C");
		//�ŧi���[�J�u��C���֨����

		JRadioButton rbOne = new JRadioButton("��ܶsA"),
								rbTwo = new JRadioButton("��ܶsB"),
								rbThree = new JRadioButton("��ܶsC");
		//�ŧi���[�J�u��C���֨����

		ButtonGroup bg = new ButtonGroup();
		bg.add(rbOne);
		bg.add(rbTwo);
		bg.add(rbThree);
		//�N��ܶs�[�J�P�@�ӫ��s�s��

		cbItem.addActionListener(al);
		btnItem.addActionListener(al);
		cbOne.addActionListener(al);
		cbTwo.addActionListener(al);
		cbThree.addActionListener(al);
		rbOne.addActionListener(al);
		rbTwo.addActionListener(al);
		rbThree.addActionListener(al);
		//���U��ť�u��C���ActionEvent�ƥ󪺺�ť������

		tbTop.add(cbItem);
		tbTop.addSeparator(); //�[�J���j�Ŷ�
		tbTop.add(btnItem);
		tbTop.addSeparator();
		tbTop.add(cbOne);
		tbTop.add(cbTwo);
		tbTop.add(cbThree);
		tbTop.addSeparator();
		tbTop.add(rbOne);
		tbTop.add(rbTwo);
		tbTop.add(rbThree);
		//�H�ϥܫإ߫��s����, �å[�J�u��C��

		Container cp = getContentPane(); //���o���e����
		cp.add(tbTop, BorderLayout.NORTH); //�N�u��C�[�J�����ج[
		cp.add(new JScrollPane(taMsg));
		
		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new TBControlEX(); //�ŧi�����ج[����
	}
}