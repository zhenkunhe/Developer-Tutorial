import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class ProgMonEX extends JFrame{

	private int 
		maxValue = 20, //�w�q�i�צC���̤j��
		popupValue = 1000;
		//�]�w��ܶi�׹�ܲ������ݮɶ����@���

	private String str = "�ާ@�w����";

	private ProgressMonitor pm; //�w�q�i�׺ʴ���

	private JButton btnStart = new JButton("�}�l");
	//�Ұʭp�ɾ������s

	private JTextField 
		tfTotal = new JTextField(String.valueOf(maxValue), 20),
		tfPopup = new JTextField(String.valueOf(popupValue), 20);

	//�w�q����p�ɪ�Timer����
	private Timer timer = 
		new Timer(1000, //�C�j1��o�XActionEvent�ƥ�
			new ActionListener(){ //�w�q�^���ƥ󪺺�ť������

				private int value = 0; //�_�l�p���ܼ�

				public void actionPerformed(ActionEvent e){
			
					//�P�_�ϥΪ̬O�_���U�i�׹�ܲ����������s
					if(pm.isCanceled()){
						pm.close(); //�����i�׺ʴ���
						timer.stop(); //�פ�p�ɾ�
						value = 0; //���]�p���ܼ�
					}
				
					if(value < maxValue){ //�P�_�p���ܼƬO�_�p��̤j��
						pm.setProgress(++value); //�]�w�i�׺ʴ�����ܪ��i��
						pm.setNote(str + ((value * 100) / maxValue) + " %");
						//�]�w�i�׹�ܲ����i�צC�W����ܪ��i�ת��A
					}
					else{
						timer.stop(); //�פ�p�ɾ�
						value = 0; //���]�p���ܼ�
					}
				}
			});

	ProgMonEX(){

		//�H�ΦW���O���覡�w�q�^��ActionEvent�ƥ󪺺�ť������
		btnStart.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){

				maxValue = Integer.valueOf(tfTotal.getText());
				//���o�ϥΪ̳]�w��������

				popupValue = Integer.valueOf(tfPopup.getText());
				//���o��ܶi�׹�ܲ������ݮɶ����@���

				pm = new ProgressMonitor(ProgMonEX.this,
					"�i�׺ʴ������B��", str + "0 %", 0, maxValue);
				//�ŧi�i�׺ʴ�����

				pm.setMillisToDecideToPopup(popupValue);
				//�]�w��ܶi�׹�ܲ������ݮɶ����@���

				pm.setProgress(0); //�]�w�i�׺ʴ����}�l�ʴ����i�׭�

				timer.start(); //�Ұʭp�ɾ�
			}
		});

		//�إߨѨϥΪ̿�J�]�w�Ȫ����һP��r��
		JPanel jpSetting = new JPanel(new GridLayout(2, 2, 5, 5));
		jpSetting.add(new JLabel("�`������", JLabel.RIGHT));
		jpSetting.add(tfTotal);
		jpSetting.add(
			new JLabel("��ܶi�׹�ܲ������ݲ@���", JLabel.RIGHT));
		jpSetting.add(tfPopup);

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new BorderLayout(10, 10));
		//�]�w���e���������P�������Z����10��
		//BorderLayout����޲z����

		add(jpSetting); //�N����[�J
		add(btnStart, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(4, 4, 4, 4));
		//�]�w�ڭ����N�ϥ�

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(400, 140);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ProgMonEX(); //�ŧi�����ج[����
	}
}