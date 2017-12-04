import java.io.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

//��@ActionListener����, �ŧi��ť�s�u�ʧ@����ť������
class ConnectActionListener implements ActionListener {

	JComboBox cmbServer = new JComboBox();
	//�ŧi�զX���

	JTextField tfName = new JTextField("anonymous");
	//�ŧi��r��

	JPasswordField pfPassword = new JPasswordField();
	//�ŧi�K�X��

	BitcFtp mainFrame; //�ŧi�B�zFTP�s�u�������ج[
	JDialog dlgConnect; //����s�u���s�u��ܲ�

	public ConnectActionListener(BitcFtp mainFrame){ //�غc�l
		this.mainFrame = mainFrame;
		//�]�w�B�zFTP�s�u�������ج[
	}

	//�^��ActionEvent�ƥ󪺤�k
	public void actionPerformed(ActionEvent e){

		dlgConnect = new JDialog(mainFrame, "��J�s�u���", true);
		//�ŧiFTP�s�u��ܲ�

		Container cpConnect = dlgConnect.getContentPane();
		//���o��ܲ������e����
	
		cmbServer.addItem(new String("ftp.ntu.edu.tw"));
		cmbServer.addItem(new String("ftp.hinet.net"));
		//�s�W�զX������ﶵ�M��

		cmbServer.setEditable(true); //�]�w��r��O�_�i�s��

		cpConnect.setLayout(new GridLayout(4, 2, 10, 5));
		cpConnect.add(new JLabel("FTP���A�� :", JLabel.RIGHT));
		cpConnect.add(cmbServer);
		cpConnect.add(new JLabel("�b�� :", JLabel.RIGHT));
		cpConnect.add(tfName);
		cpConnect.add(new JLabel("�K�X :", JLabel.RIGHT));
		cpConnect.add(pfPassword);
		//�]�w��ܲ������e

		//�N�s�u���s�[�J��ܲ������e����
		cpConnect.add(new JButton(new AbstractAction("�s�u"){

			public void actionPerformed(ActionEvent e){

				String host = (String)cmbServer.getSelectedItem();
				String user = tfName.getText();
				String password = pfPassword.getText();
				//���o��ܲ��U��쪺�]�w��

				try{
					FtpAgent fa = new FtpAgent(host, user, password);
					//�ŧiFTP�s�u�N�z����

					fa.addObserver(mainFrame);
					//�N�����ج[�[�J�[��FTP�s�u�����[��̪���

					mainFrame.setFtpAgent(fa);
					//�]�w�����ج[�ϥΪ�FTP�s�u�N�z����

					fa.connect(); //�إ�FTP�s�u
	
					mainFrame.getRemoteFileDisplayPane().getRemoteFileList().setPath(fa.getCurrentPath());
					//���o��ܦ��A���ɮצC���M����, �ó]�w���A�ݳs�u����Ƨ���m

					mainFrame.getRemoteFileDisplayPane().setFtpConnection(fa.getConnect());
					//�]�w��ܦ��A���ɮצC���M�����ϥΪ�FTP�s�u
				}
				catch (IOException ex) {
					System.out.println(ex);
				}

				dlgConnect.dispose(); //������ܲ�
			}
		}));		

		cpConnect.add(new JButton(new AbstractAction("����"){
			public void actionPerformed(ActionEvent e){
				dlgConnect.dispose();
				//������ܳs�u��ܲ�
			}
		}));

		Dimension size = mainFrame.getSize();
		//���o�����ج[���j�p

		dlgConnect.setBounds((size.width-300)/2, (size.height-150)/2, 300, 150);
		//�]�w�s�u��ܲ����j�p
	
		dlgConnect.show(); //��ܳs�u��ܲ�
	}
}
