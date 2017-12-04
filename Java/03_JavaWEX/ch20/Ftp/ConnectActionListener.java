import java.io.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

//實作ActionListener介面, 宣告監聽連線動作的監聽器物件
class ConnectActionListener implements ActionListener {

	JComboBox cmbServer = new JComboBox();
	//宣告組合方塊

	JTextField tfName = new JTextField("anonymous");
	//宣告文字欄

	JPasswordField pfPassword = new JPasswordField();
	//宣告密碼欄

	BitcFtp mainFrame; //宣告處理FTP連線的視窗框架
	JDialog dlgConnect; //執行連線的連線對話盒

	public ConnectActionListener(BitcFtp mainFrame){ //建構子
		this.mainFrame = mainFrame;
		//設定處理FTP連線的視窗框架
	}

	//回應ActionEvent事件的方法
	public void actionPerformed(ActionEvent e){

		dlgConnect = new JDialog(mainFrame, "輸入連線資料", true);
		//宣告FTP連線對話盒

		Container cpConnect = dlgConnect.getContentPane();
		//取得對話盒的內容面版
	
		cmbServer.addItem(new String("ftp.ntu.edu.tw"));
		cmbServer.addItem(new String("ftp.hinet.net"));
		//新增組合方塊的選項清單

		cmbServer.setEditable(true); //設定文字欄是否可編輯

		cpConnect.setLayout(new GridLayout(4, 2, 10, 5));
		cpConnect.add(new JLabel("FTP伺服器 :", JLabel.RIGHT));
		cpConnect.add(cmbServer);
		cpConnect.add(new JLabel("帳號 :", JLabel.RIGHT));
		cpConnect.add(tfName);
		cpConnect.add(new JLabel("密碼 :", JLabel.RIGHT));
		cpConnect.add(pfPassword);
		//設定對話盒的內容

		//將連線按鈕加入對話盒的內容面版
		cpConnect.add(new JButton(new AbstractAction("連線"){

			public void actionPerformed(ActionEvent e){

				String host = (String)cmbServer.getSelectedItem();
				String user = tfName.getText();
				String password = pfPassword.getText();
				//取得對話盒各欄位的設定值

				try{
					FtpAgent fa = new FtpAgent(host, user, password);
					//宣告FTP連線代理物件

					fa.addObserver(mainFrame);
					//將視窗框架加入觀察FTP連線物件的觀察者物件

					mainFrame.setFtpAgent(fa);
					//設定視窗框架使用的FTP連線代理物件

					fa.connect(); //建立FTP連線
	
					mainFrame.getRemoteFileDisplayPane().getRemoteFileList().setPath(fa.getCurrentPath());
					//取得顯示伺服端檔案列表的清單方塊, 並設定伺服端連線的資料夾位置

					mainFrame.getRemoteFileDisplayPane().setFtpConnection(fa.getConnect());
					//設定顯示伺服端檔案列表的清單方塊使用的FTP連線
				}
				catch (IOException ex) {
					System.out.println(ex);
				}

				dlgConnect.dispose(); //取消對話盒
			}
		}));		

		cpConnect.add(new JButton(new AbstractAction("取消"){
			public void actionPerformed(ActionEvent e){
				dlgConnect.dispose();
				//取消顯示連線對話盒
			}
		}));

		Dimension size = mainFrame.getSize();
		//取得視窗框架的大小

		dlgConnect.setBounds((size.width-300)/2, (size.height-150)/2, 300, 150);
		//設定連線對話盒的大小
	
		dlgConnect.show(); //顯示連線對話盒
	}
}
