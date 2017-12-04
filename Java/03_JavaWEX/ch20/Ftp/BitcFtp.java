import com.BitC.Controls.*;

import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import cz.dhl.io.*;
import cz.dhl.ftp.*;

//繼承JFrame類別實作Observer介面
//以便監看繼承Observable類別
public class BitcFtp extends JFrame implements Observer {

	private static String DISCONNECT = "尚未連線";
	//顯示連線訊息

	private File defPath = new File("C:\\"); //連線時, 客戶端的預設路徑
	private FtpAgent faConnect; //宣告處理程式FTP連線動作的代理物件
	private JLabel lbStatus = new JLabel(DISCONNECT);
	//顯示連線狀態的標籤物件

	private LocalFileDisplayPane 
		lfdpLocal = new LocalFileDisplayPane("客戶端", defPath);
	//顯示客戶端檔案系統的面版

	private RemoteFileDisplayPane 
		rfdpRemote = new RemoteFileDisplayPane("伺服端");
	//顯示伺服端檔案系統的面版

	private JMenuItem 
		miConnect = new JMenuItem("連線(C)", KeyEvent.VK_C),
		miDisconnect = new JMenuItem("斷線(D)", KeyEvent.VK_D),
		miExit = new JMenuItem("結束(E)", KeyEvent.VK_E);
	//宣告功能表內的選項

	private JButton btnUpload  = new JButton("上傳->"),
							  btnDownload  = new JButton("<-下載");
	//執行檔案上傳與下載的按鈕

	private ConnectActionListener 
		calConnect = new ConnectActionListener(this);
	//宣告執行連線的監聽器物件

	private DisconnectActionListener 
		dalDisconnect = new DisconnectActionListener(this);
	//宣告取消連線的監聽器物件
	
	public BitcFtp(){ //建構子

		Box bxButton = new Box(BoxLayout.Y_AXIS);
		bxButton.add(btnUpload);
		bxButton.add(Box.createVerticalStrut(50));
		bxButton.add(btnDownload);
		//建立容納執行上傳與下載動作的按鈕

		Box bxMain = new Box(BoxLayout.X_AXIS);
		bxMain.add(Box.createHorizontalStrut(10));
		bxMain.add(lfdpLocal);
		bxMain.add(Box.createHorizontalStrut(10));
		bxMain.add(bxButton);
		bxMain.add(Box.createHorizontalStrut(10));
		bxMain.add(rfdpRemote);
		bxMain.add(Box.createHorizontalStrut(10));
		//建立FTP程式的主面版

		Box bxStatus = new Box(BoxLayout.X_AXIS);
		bxStatus.add(Box.createHorizontalStrut(10));
		bxStatus.add(lbStatus);
		bxStatus.add(Box.createHorizontalStrut(10));
		//建立顯示連線狀態的標籤

		btnUpload.addActionListener(alUpload);
		btnDownload.addActionListener(alDownload);
		miConnect.addActionListener(calConnect);
		miDisconnect.addActionListener(dalDisconnect);
		miExit.addActionListener(alExit);
		//註冊回應各種按鈕與功能表選項

		miDisconnect.setEnabled(false);
		//設定取消連線選項狀態為無效

		JMenu mnApp = new JMenu("程式(P)");
		//宣告程式功能表
		
		mnApp.setMnemonic(KeyEvent.VK_P);

		mnApp.add(miConnect);
		mnApp.add(miDisconnect);
		mnApp.add(miExit);
		//將選項加入程式功能表

		JMenuBar jmb = new JMenuBar();
		jmb.add(mnApp);
		//將程式功能表加入功能表列

		setJMenuBar(jmb);	
		//設定視窗框架使用的功能表列

		Container cp = getContentPane();
		cp.add(bxMain);
		cp.add(bxStatus, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 630);
		setVisible(true);
	}

	//設定處理FTP連線的代理程式物件
	public void setFtpAgent(FtpAgent fa){
		faConnect = fa;
	}

	//取得處理FTP連線的代理程式物件
	public FtpAgent getFtpAgent(){
		return faConnect;
	}

	//取得功能表內執行FTP連線的選項
	public JMenuItem getConnectMenuItem(){
		return miConnect;
	}

	//取得功能表內取消FTP連線的選項
	public JMenuItem getDisconnectMenuItem(){
		return miDisconnect;
	}

	//取得FTP程式介面內顯示伺服端檔案列表的面版
	public RemoteFileDisplayPane getRemoteFileDisplayPane(){
		return rfdpRemote;
	}

	//取得FTP程式介面內顯示客戶端檔案列表的面版
	public LocalFileDisplayPane getLocalFileDisplayPane(){
		return lfdpLocal;
	}

	//依照FTP連線代理物件的狀態更新
	public void update(Observable o, Object arg){

		//取得連線代理物件的狀態, 並判斷
		switch(faConnect.getStatus()){
			case FtpAgent.DOWNLOAD : //下載
				lfdpLocal.update();
				//更新客戶端面版
				break;
			case FtpAgent.UPLOAD : //上傳
				rfdpRemote.update();
				//更新伺服端面版
				break;
			case FtpAgent.CONNECT : //連線
				miConnect.setEnabled(false);
				//設定連線選項為無效

				miDisconnect.setEnabled(true);
				//設定取消連線選項為無效

				rfdpRemote.setFtpConnection(faConnect.getConnect());
				//設定伺服端面版使用的FTP連線

				try{
					lbStatus.setText("已連線至" 
						+ faConnect.getConnect().host());
					//設定連線狀態標籤顯示的訊息
				}
				catch(IOException ioe){
					System.out.println(ioe.toString());
				}
				break;
			case FtpAgent.DISCONNECT : //取消連線

				miConnect.setEnabled(true);
				//設定連線選項的狀態為無效

				miDisconnect.setEnabled(false);
				//設定取消連線選項的狀態為無效

				rfdpRemote.setFtpConnection(null);
				//設定沒有連線物件

				rfdpRemote.reset(); //重設伺服端連線面版
				faConnect = null;
				lbStatus.setText(DISCONNECT);
				//設定狀態標籤顯示尚未連線的訊息
				break;
		}
	}

	//回應結束程式選項ActionEvent事件的監聽器物件
	private ActionListener alExit = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			System.exit(0);
		}
	};

	//回應執行上傳之按鈕ActionEvent事件的監聽器物件
	private ActionListener alUpload = new ActionListener(){

		public void actionPerformed(ActionEvent e){

			if(faConnect == null) return;
			//若連線物件為null則終止執行方法

			File source = (File) lfdpLocal.getFileList().getSelectedValue();
			//取得控制客戶端面版之顯示檔案列表之清單方塊的選項項目
			
			if(source.isDirectory()) return;
			//若選取資料夾則終止執行方法

			try{
				faConnect.upload(source); //上傳選取項目
			}
			catch(IOException ioe){
				System.out.println(ioe.toString());
			}
		}
	};

	//回應執行下載之按鈕ActionEvent事件的監聽器物件
	private ActionListener alDownload = new ActionListener(){

		public void actionPerformed(ActionEvent e){
			
			if(faConnect == null) return;
			//若沒有連線物件則終止執行方法

			FtpFile source  = 
				(FtpFile) rfdpRemote.getRemoteFileList().getSelectedValue();
			//取得伺服端面版顯示檔案列表的清單方塊之選項值

			File dest = new File(
				lfdpLocal.getFileList().getCurrentPath() + "\\" + source.getName());
			//宣告執行檔案下載的File物件

			try{
				faConnect.download(source, dest);
				//執行檔案下載
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