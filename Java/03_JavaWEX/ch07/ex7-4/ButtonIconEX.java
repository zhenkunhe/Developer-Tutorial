import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class ButtonIconEX extends JFrame{

	JButton btnIcon = new JButton(); 	//�ŧi���O���s����

	ButtonIconEX(){

		btnIcon.setIcon(new ImageIcon(
			ButtonIconEX.class.getResource("images\\Normal.gif")));
		//�]�w���s�ϥ�

		btnIcon.setPressedIcon(new ImageIcon(
			ButtonIconEX.class.getResource("images\\Pressed.gif")));
		//�]�w���U���s��ܪ��ϥ�

		btnIcon.setDisabledIcon(new ImageIcon(
			ButtonIconEX.class.getResource("images\\Disabled.gif")));
		//�]�w���s�L�Į���ܪ��ϥ�

		btnIcon.setRolloverIcon(new ImageIcon(
			ButtonIconEX.class.getResource("images\\Rollover.gif")));
		//�]�w�ƹ���в��L���s��ܪ��ϥ�

		JButton btnDisabled = new JButton("�]�w���s���L��");
		//�N���s���A�]�w���L�Ī����s

		//���U�ëŧi��ťActionEvent�ƥ󪺺�ť������
		btnDisabled.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getActionCommand().equals("�]�w���s���L��")){
					btnIcon.setEnabled(false); //�N���s���A�]�w���L��
					((JButton)e.getSource()).setText("�]�w���s������");
					//�]�w���s��ܪ���r
				}
				else{
					btnIcon.setEnabled(true); //�N���s���A�]�w������
					((JButton)e.getSource()).setText("�]�w���s���L��");
					//�]�w���s��ܪ���r
				}
			}
		});

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

		cp.add(btnIcon); //�N����[�JBox�e��
		cp.add(Box.createVerticalStrut(20));
		cp.add(btnDisabled);

		//�]�w���������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		pack();
		setVisible(true);
	}

	public static void main(String args[]) {
		new ButtonIconEX(); //�ŧi�����ج[����
	}
}