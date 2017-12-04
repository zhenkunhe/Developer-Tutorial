import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ProgMonISEX extends JFrame{

	final static String FILE_NAME = "data\\java2.psd";
	//�ŧi�ܽd���J�ʧ@���ɮ�

	File file = new File(FILE_NAME);
	//�ŧi�����J�ɮת�File����

	JButton btnStart = new JButton("�}�lŪ���ɮ�");
	//�Ұʭp�ɾ������s

	ProgMonISEX(){

		//���U�^�����sActionEvent�ƥ󪺺�ť������
		btnStart.addActionListener(new ActionListener(){

			ProgressMonitorInputStream pmis;
			//��J��y�i�׺ʴ���

			ProgressMonitor pm; //�i�׺ʴ���
			FileInputStream fisInput; //�ɮ׿�J��y

			//�ŧi�����ɮ�Ū���������
			//�Y���ϥΰ����, �N�L�k��ܶi�צC
			Thread readFile = new Thread(){

				double size = 0; //�p��w���J���줸��

				public void run(){
					
					try{
						while(pmis.read() != -1){
							++size; //�p��w�g���J���줸�ռ�

							if((size%10000) == 0)
								pm.setNote("�w���� " + 
									String.valueOf((new Double(size *100 
									  / file.length())).intValue()) + "%");
								//����ɮ׸��J�i�ת��ʤ���
						}
					}
					catch(IOException ioe){
						ioe.printStackTrace();
					}
					finally{
						try{
							if(pmis != null) pmis.close();
							//�����i�׺ʵ���J��y
						}
						catch(IOException ioe){ }
					}
				}
			};

			//�^��ActionEvent�ƥ󪺤�k
			public void actionPerformed(ActionEvent e){

				if(file == null) return;
				//�P�_file����O�_��null, �O�h�פ����

				try{
					fisInput = new FileInputStream(file);
					//�ŧi�ɮ׿�J��y
				}
				catch(FileNotFoundException fnfe){
					fnfe.printStackTrace();
					return;
				}
				
				pmis = new ProgressMonitorInputStream(
						ProgMonISEX.this, "�ɮ�Ū����...", fisInput);
				//�ŧi�ʬ��ɮ׸��J����J��y�ʴ���

				pm = pmis.getProgressMonitor();
				//���o�i�׺ʴ���

				pm.setMillisToDecideToPopup(10);
				//�]�w�}�l�ʴ��{�����ɶ����@���

				readFile.start(); //�Ұʰ����
			}
		});
	
		Container cp = getContentPane();
		//���o���e����

		cp.setLayout(new BorderLayout(10, 10));
		//�]�w���e���������P�������Z����10��
		//BorderLayout����޲z����

		add(new JLabel("Ū�� [" + FILE_NAME + "]")); //�N����[�J
		add(btnStart, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(4, 4, 4, 4));
		//�]�w�ڭ����N�ϥ�

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(200, 100);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ProgMonISEX(); //�ŧi�����ج[����
	}
}