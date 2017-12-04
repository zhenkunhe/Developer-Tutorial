import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class CheckBoxIconEX extends JFrame{

	JCheckBox cbIcon = new JCheckBox(); //�ŧi�֨��������

	CheckBoxIconEX(){

		 cbIcon.setIcon(new ImageIcon(
			CheckBoxIconEX.class.getResource("images\\Normal.gif")));
		//�]�w�֨�����ϥ�

		cbIcon.setPressedIcon(new ImageIcon(
			CheckBoxIconEX.class.getResource("images\\Pressed.gif")));
		//�]�w���U�֨������ܪ��ϥ�

		cbIcon.setRolloverIcon(new ImageIcon(
			CheckBoxIconEX.class.getResource("images\\Rollover.gif")));
		//�]�w�ƹ���в��L�֨������ܪ��ϥ�

		cbIcon.setRolloverSelectedIcon(new ImageIcon(
			CheckBoxIconEX.class.getResource("images\\RolloverSelected.gif")));

		cbIcon.setDisabledSelectedIcon(new ImageIcon(
			CheckBoxIconEX.class.getResource("images\\DisabledSelected.gif")));
		//�]�w�֨�����Q��������L�Ī��A���ϥ�

		cbIcon.setSelectedIcon(new ImageIcon(
			CheckBoxIconEX.class.getResource("images\\Selected.gif")));
		//�]�w�֨�����Q�������ܪ��ϥ�
	
		cbIcon.setDisabledIcon(new ImageIcon(
			CheckBoxIconEX.class.getResource("images\\Disabled.gif")));
		//�]�w�֨�����L�Į���ܪ��ϥ�

		JButton btnDisabled = new JButton("�]�w���s���L��");
		//�N���s���A�]�w���L�Ī����s

		//���U�ëŧi��ťActionEvent�ƥ󪺺�ť������
		btnDisabled.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getActionCommand().equals("�]�w���s���L��")){
					cbIcon.setEnabled(false); //�N�֨�����]�w���L��
					((JButton)e.getSource()).setText("�]�w���s������");
					//�]�w���s��ܪ���r
				}
				else{
					cbIcon.setEnabled(true); //�N�֨�������A�]�w������
					((JButton)e.getSource()).setText("�]�w���s���L��");
					//�]�w���s��ܪ���r
				}
			}
		});

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

		cp.add(cbIcon); //�N����[�JBox�e��
		cp.add(Box.createVerticalStrut(20));
		cp.add(btnDisabled);

		//�]�w���������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		pack();
		setVisible(true);
	}

	public static void main(String args[]) {
		new CheckBoxIconEX(); //�ŧi�����ج[����
	}
}