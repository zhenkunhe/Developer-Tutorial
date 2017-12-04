import java.awt.event.*;

//實作ActionListener介面, 宣告監聽斷線動作的監聽器物件
class DisconnectActionListener implements ActionListener {

	BitcFtp mainFrame; //被監聽的FTP程式介面

	//建構子
	public DisconnectActionListener(BitcFtp mainFrame){
		this.mainFrame = mainFrame;
		//設定欲監聽的FTP連線程式視窗框架
	}

	public void actionPerformed(ActionEvent e){
		mainFrame.getFtpAgent().disconnect();
		//中斷FTP連線

		mainFrame.getConnectMenuItem().setEnabled(true);
		//設定執行連線之選項為有效

		mainFrame.getDisconnectMenuItem().setEnabled(false);
		//設定取消連線之選項為有效
	}
}
