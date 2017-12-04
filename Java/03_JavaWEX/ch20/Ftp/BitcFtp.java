import com.BitC.Controls.*;

import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import cz.dhl.io.*;
import cz.dhl.ftp.*;

//�~��JFrame���O��@Observer����
//�H�K�ʬ��~��Observable���O
public class BitcFtp extends JFrame implements Observer {

	private static String DISCONNECT = "�|���s�u";
	//��ܳs�u�T��

	private File defPath = new File("C:\\"); //�s�u��, �Ȥ�ݪ��w�]���|
	private FtpAgent faConnect; //�ŧi�B�z�{��FTP�s�u�ʧ@���N�z����
	private JLabel lbStatus = new JLabel(DISCONNECT);
	//��ܳs�u���A�����Ҫ���

	private LocalFileDisplayPane 
		lfdpLocal = new LocalFileDisplayPane("�Ȥ��", defPath);
	//��ܫȤ���ɮרt�Ϊ�����

	private RemoteFileDisplayPane 
		rfdpRemote = new RemoteFileDisplayPane("���A��");
	//��ܦ��A���ɮרt�Ϊ�����

	private JMenuItem 
		miConnect = new JMenuItem("�s�u(C)", KeyEvent.VK_C),
		miDisconnect = new JMenuItem("�_�u(D)", KeyEvent.VK_D),
		miExit = new JMenuItem("����(E)", KeyEvent.VK_E);
	//�ŧi�\������ﶵ

	private JButton btnUpload  = new JButton("�W��->"),
							  btnDownload  = new JButton("<-�U��");
	//�����ɮפW�ǻP�U�������s

	private ConnectActionListener 
		calConnect = new ConnectActionListener(this);
	//�ŧi����s�u����ť������

	private DisconnectActionListener 
		dalDisconnect = new DisconnectActionListener(this);
	//�ŧi�����s�u����ť������
	
	public BitcFtp(){ //�غc�l

		Box bxButton = new Box(BoxLayout.Y_AXIS);
		bxButton.add(btnUpload);
		bxButton.add(Box.createVerticalStrut(50));
		bxButton.add(btnDownload);
		//�إ߮e�ǰ���W�ǻP�U���ʧ@�����s

		Box bxMain = new Box(BoxLayout.X_AXIS);
		bxMain.add(Box.createHorizontalStrut(10));
		bxMain.add(lfdpLocal);
		bxMain.add(Box.createHorizontalStrut(10));
		bxMain.add(bxButton);
		bxMain.add(Box.createHorizontalStrut(10));
		bxMain.add(rfdpRemote);
		bxMain.add(Box.createHorizontalStrut(10));
		//�إ�FTP�{�����D����

		Box bxStatus = new Box(BoxLayout.X_AXIS);
		bxStatus.add(Box.createHorizontalStrut(10));
		bxStatus.add(lbStatus);
		bxStatus.add(Box.createHorizontalStrut(10));
		//�إ���ܳs�u���A������

		btnUpload.addActionListener(alUpload);
		btnDownload.addActionListener(alDownload);
		miConnect.addActionListener(calConnect);
		miDisconnect.addActionListener(dalDisconnect);
		miExit.addActionListener(alExit);
		//���U�^���U�ث��s�P�\���ﶵ

		miDisconnect.setEnabled(false);
		//�]�w�����s�u�ﶵ���A���L��

		JMenu mnApp = new JMenu("�{��(P)");
		//�ŧi�{���\���
		
		mnApp.setMnemonic(KeyEvent.VK_P);

		mnApp.add(miConnect);
		mnApp.add(miDisconnect);
		mnApp.add(miExit);
		//�N�ﶵ�[�J�{���\���

		JMenuBar jmb = new JMenuBar();
		jmb.add(mnApp);
		//�N�{���\���[�J�\���C

		setJMenuBar(jmb);	
		//�]�w�����ج[�ϥΪ��\���C

		Container cp = getContentPane();
		cp.add(bxMain);
		cp.add(bxStatus, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 630);
		setVisible(true);
	}

	//�]�w�B�zFTP�s�u���N�z�{������
	public void setFtpAgent(FtpAgent fa){
		faConnect = fa;
	}

	//���o�B�zFTP�s�u���N�z�{������
	public FtpAgent getFtpAgent(){
		return faConnect;
	}

	//���o�\�������FTP�s�u���ﶵ
	public JMenuItem getConnectMenuItem(){
		return miConnect;
	}

	//���o�\�������FTP�s�u���ﶵ
	public JMenuItem getDisconnectMenuItem(){
		return miDisconnect;
	}

	//���oFTP�{����������ܦ��A���ɮצC������
	public RemoteFileDisplayPane getRemoteFileDisplayPane(){
		return rfdpRemote;
	}

	//���oFTP�{����������ܫȤ���ɮצC������
	public LocalFileDisplayPane getLocalFileDisplayPane(){
		return lfdpLocal;
	}

	//�̷�FTP�s�u�N�z���󪺪��A��s
	public void update(Observable o, Object arg){

		//���o�s�u�N�z���󪺪��A, �çP�_
		switch(faConnect.getStatus()){
			case FtpAgent.DOWNLOAD : //�U��
				lfdpLocal.update();
				//��s�Ȥ�ݭ���
				break;
			case FtpAgent.UPLOAD : //�W��
				rfdpRemote.update();
				//��s���A�ݭ���
				break;
			case FtpAgent.CONNECT : //�s�u
				miConnect.setEnabled(false);
				//�]�w�s�u�ﶵ���L��

				miDisconnect.setEnabled(true);
				//�]�w�����s�u�ﶵ���L��

				rfdpRemote.setFtpConnection(faConnect.getConnect());
				//�]�w���A�ݭ����ϥΪ�FTP�s�u

				try{
					lbStatus.setText("�w�s�u��" 
						+ faConnect.getConnect().host());
					//�]�w�s�u���A������ܪ��T��
				}
				catch(IOException ioe){
					System.out.println(ioe.toString());
				}
				break;
			case FtpAgent.DISCONNECT : //�����s�u

				miConnect.setEnabled(true);
				//�]�w�s�u�ﶵ�����A���L��

				miDisconnect.setEnabled(false);
				//�]�w�����s�u�ﶵ�����A���L��

				rfdpRemote.setFtpConnection(null);
				//�]�w�S���s�u����

				rfdpRemote.reset(); //���]���A�ݳs�u����
				faConnect = null;
				lbStatus.setText(DISCONNECT);
				//�]�w���A������ܩ|���s�u���T��
				break;
		}
	}

	//�^�������{���ﶵActionEvent�ƥ󪺺�ť������
	private ActionListener alExit = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			System.exit(0);
		}
	};

	//�^������W�Ǥ����sActionEvent�ƥ󪺺�ť������
	private ActionListener alUpload = new ActionListener(){

		public void actionPerformed(ActionEvent e){

			if(faConnect == null) return;
			//�Y�s�u����null�h�פ�����k

			File source = (File) lfdpLocal.getFileList().getSelectedValue();
			//���o����Ȥ�ݭ���������ɮצC���M�������ﶵ����
			
			if(source.isDirectory()) return;
			//�Y�����Ƨ��h�פ�����k

			try{
				faConnect.upload(source); //�W�ǿ������
			}
			catch(IOException ioe){
				System.out.println(ioe.toString());
			}
		}
	};

	//�^������U�������sActionEvent�ƥ󪺺�ť������
	private ActionListener alDownload = new ActionListener(){

		public void actionPerformed(ActionEvent e){
			
			if(faConnect == null) return;
			//�Y�S���s�u����h�פ�����k

			FtpFile source  = 
				(FtpFile) rfdpRemote.getRemoteFileList().getSelectedValue();
			//���o���A�ݭ�������ɮצC���M�������ﶵ��

			File dest = new File(
				lfdpLocal.getFileList().getCurrentPath() + "\\" + source.getName());
			//�ŧi�����ɮפU����File����

			try{
				faConnect.download(source, dest);
				//�����ɮפU��
			}
			catch(IOException ioe){
				System.out.println(ioe.toString());
			}
		}
	};

	public static void main(String... args){
		new BitcFtp();
	}
}