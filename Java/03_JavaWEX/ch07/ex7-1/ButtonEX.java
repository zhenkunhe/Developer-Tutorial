import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class ButtonEX extends JFrame{

	JButton enable = new JButton("�Ϲϥܫ��s����");
	JButton disable = new JButton();
	JButton iconBtn = new JButton(new ImageIcon(
		ButtonEX.class.getResource("images/bitc.gif"), "�줸���LOGO"));
	//�ŧi���O���s����

	//�w�q�ëŧi��ť������
	ActionListener al = new ActionListener(){
		public void actionPerformed(ActionEvent e){

			//���o�ʧ@�R�O�r��, �çP�_Ĳ�o�ƥ󪺫��s
			if(e.getActionCommand().equals("enable")){
				enable.setEnabled(false); //�̷ӻݨD�]�w���s�O�_����
				disable.setEnabled(true);
				iconBtn.setEnabled(true);
			}
			else{
				enable.setEnabled(true);
				disable.setEnabled(false);
				iconBtn.setEnabled(false);
			}
		}	
	};

	ButtonEX(){

		disable.setText("�Ϲϥܫ��s�L��"); //�]�w���s��ܪ���r

		enable.setActionCommand("enable"); //�]�w���s���ʧ@�R�O�r��
		disable.setActionCommand("disable");

		disable.setEnabled(false); //�N���s���_�l���A�]�w���L��
		iconBtn.setEnabled(false);

		enable.addActionListener(al); //���U��ť��
		disable.addActionListener(al);

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new FlowLayout()); //�]�w�HFlowLayout�t�m����

		cp.add(enable); //�N����[�J����
		cp.add(iconBtn);
		cp.add(disable);

		//�]�w���������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(600, 150);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ButtonEX(); //�ŧi�����ج[����
	}
}