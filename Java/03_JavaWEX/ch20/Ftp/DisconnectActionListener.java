import java.awt.event.*;

//��@ActionListener����, �ŧi��ť�_�u�ʧ@����ť������
class DisconnectActionListener implements ActionListener {

	BitcFtp mainFrame; //�Q��ť��FTP�{������

	//�غc�l
	public DisconnectActionListener(BitcFtp mainFrame){
		this.mainFrame = mainFrame;
		//�]�w����ť��FTP�s�u�{�������ج[
	}

	public void actionPerformed(ActionEvent e){
		mainFrame.getFtpAgent().disconnect();
		//���_FTP�s�u

		mainFrame.getConnectMenuItem().setEnabled(true);
		//�]�w����s�u���ﶵ������

		mainFrame.getDisconnectMenuItem().setEnabled(false);
		//�]�w�����s�u���ﶵ������
	}
}
