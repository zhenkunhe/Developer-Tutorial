import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class RadioCheckEX extends JFrame{

	RadioCheckEX(){

		JMenu mnFile = new JMenu("�ɮ�(F)"); //�ŧi�\���

		mnFile.setMnemonic(KeyEvent.VK_F); //�]�w�O����

		JMenuItem miExit = new JMenuItem("����(E)", KeyEvent.VK_E);
		//�ŧi�\���ﶵ	

		//�w�q�ëŧi��ťActionEvent�ƥ󪺺�ť��
		miExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});

		mnFile.add(miExit); //�N�ﶵ�[�J�\���

		JCheckBoxMenuItem 
			cbmiItemA = new JCheckBoxMenuItem("�ﶵ 1", 
									new ImageIcon("icon\\bitc.gif")),
				//�ŧi�P����ܤ�r�P�ϥܪ��֨�����\���ﶵ				
			cbmiItemB = new JCheckBoxMenuItem(
									new ImageIcon("icon\\bitc.gif")),
				//�ŧi�H��ܹϥܪ��֨�����\���ﶵ
			cbmiItemC = new JCheckBoxMenuItem("�ﶵ 3");
				//�ŧi��ܤ�r���֨�����\���ﶵ

		JMenu mnCheckBox = new JMenu("�֨�����ﶵ");
		mnCheckBox.add(cbmiItemA); //�N�֨�����ﶵ�[�J�\���
		mnCheckBox.add(cbmiItemB);
		mnCheckBox.add(cbmiItemC);

		JRadioButtonMenuItem 
			rbmiItemA = new JRadioButtonMenuItem("�ﶵ 1", 
									new ImageIcon("icon\\bitc.gif"), true),
				//�ŧi�P����ܤ�r�P�ϥܪ���ܶs�\���ﶵ
			rbmiItemB = new JRadioButtonMenuItem(
									new ImageIcon("icon\\bitc.gif")),
				//�ŧi��ܹϥܪ���ܶs�\���ﶵ
			rbmiItemC = new JRadioButtonMenuItem("�ﶵ 3");
				//�ŧi�P����ܤ�r����ܶs�\���ﶵ

		JMenu mnRadioBtn = new JMenu("��ܶs�ﶵ");
		mnRadioBtn.add(rbmiItemA); //�N��ܶs�ﶵ�[�J�\���
		mnRadioBtn.add(rbmiItemB);
		mnRadioBtn.add(rbmiItemC);

		ButtonGroup bgRbmi = new ButtonGroup(); //�ŧi���s�s�ժ���
		bgRbmi.add(rbmiItemA); //�N��ܶs�ﶵ�[�J���s�s��
		bgRbmi.add(rbmiItemB);
		bgRbmi.add(rbmiItemC);

		JMenuBar jmb = new JMenuBar(); //�ŧi�\���C����
		setJMenuBar(jmb); //�]�w�����ج[�ϥΪ��\���
		jmb.add(mnFile); //�N�\���[�J�\���C
		jmb.add(mnCheckBox);
		jmb.add(mnRadioBtn);

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new RadioCheckEX(); //�ŧi�����ج[����
	}
}