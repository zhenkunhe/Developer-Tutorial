import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class ProgressEX extends JFrame{

	final static int MAX_VALUE = 20; //�w�q�i�צC���̤j��

	JProgressBar pb = new JProgressBar(); //�ŧi�i�צC

	JButton btnStart = new JButton("�}�l"); //�Ұʭp�ɾ������s

	Timer timer = new Timer(500, //�C�j0.5��o�XActionEvent�ƥ�
		new ActionListener(){ //�w�q�^���ƥ󪺺�ť������

			private int value = 0; //�_�l�p���ܼ�

			public void actionPerformed(ActionEvent e){

				if(value <  MAX_VALUE){ //�P�_value�O�_�p��̤j��
					pb.setValue(++value);
					//�N��value�ȥ[1, �A�]�w�i�צC����
				}
				else{
					value = 0; //���]�p���ܼ�
					pb.setValue(value); //���]�i�צC
					btnStart.setText("�}�l"); //�]�w���O���s����r
					timer.stop(); //�פ�p�ɾ�
				}
			}
		});
	//�w�q����p�ɪ�Timer����

	ProgressEX(){

		pb.setOrientation(JProgressBar.HORIZONTAL);
		//�]�w�i�צC��������V

		pb.setMinimum(0); //�]�w�̤p�Ȭ�0
		pb.setMaximum(MAX_VALUE); //�]�w�̤j��
		pb.setValue(0); //�]�w�_�l��
		pb.setStringPainted(true); //�]�w�i�צC�O�_��ܶi�צr��
		
		btnStart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				if(e.getActionCommand().equals("�}�l")){
					((JButton) e.getSource()).setText("�פ�");
					//�N���s��ܪ���r��אּ"�פ�"

					timer.start(); //�Ұʭp�ɾ�
				}
				else{
					((JButton) e.getSource()).setText("�}�l");
					//�N���s��ܪ���r��אּ"�}�l"

					timer.stop(); //�פ�p�ɾ�
					pb.setValue(0); //���]�i�צC
				}
			}
		});
		//���U�^�����sActionEvent�ƥ󪺺�ť������

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));
		//�]�w���e���������P�������Z����10��BorderLayout����޲z����

		add(pb); //�N����[�J
		add(Box.createHorizontalStrut(10));
		add(btnStart);

		getRootPane().setBorder(new EmptyBorder(4, 4, 4, 4));
		//�]�w�ڭ����N�ϥ�

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		pack();
		setVisible(true);
	}

	public static void main(String args[]) {
		new ProgressEX(); //�ŧi�����ج[����
	}
}