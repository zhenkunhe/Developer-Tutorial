import java.io.*;
import java.util.*;

import cz.dhl.io.*;
import cz.dhl.ftp.*;

//繼承Observable類別將用於處理FTP連線
//此類別將可被實作Observer介面的類別監聽
class FtpAgent extends Observable {

	public static final int DOWNLOAD = 0;
	public static final int UPLOAD = 1;
	public static final int CONNECT = 2;
	public static final int DISCONNECT = 3;
	//宣告代表連線狀態的常數

	private int status = DISCONNECT;
	//宣告儲存連線狀態的屬性, 預設值為取消連線

	private Ftp cl; //宣告代表FTP連線的屬性
	private FtpFile curPath; //宣告代表連線路徑的屬性
	private String host, user, password;
	//宣告FTP連線的伺服器位址、使用者帳號與密碼

	//建構子
	public FtpAgent(String host, String user, String password){
		this.host = host;
		this.user = user;
		this.password  = password;
		//設定連線的伺服器位址、使用者帳號與密碼
	}

	public void connect() throws IOException {

		FtpConnect cn = new FtpConnect();
		//宣告儲存FTP連線資料的物件

		cn.setHostName(host); //設定執行連線的FTP伺服器位址
		cn.setUserName(user); //設定FTP連線的使用者帳號
		cn.setPassWord(password); //設定FTP連線的密碼

		cl = new Ftp(); //宣告FTP連線處理物件
		cl.connect(cn); //建立連線並登入指定的FTP伺服器

		curPath = new FtpFile(cl.pwd(), cl);
		//取得目前的FTP連線伺服器的檔案路徑

		status = CONNECT; //設定狀態為連線
		stateChanged(); //更新連線狀態
	}

	public void disconnect(){ //取得連線
		cl.disconnect(); //終止FTP連線
		cl = null; //設定FTP連線為null
		status = DISCONNECT; //設定連線狀態為斷線
		stateChanged(); //更新連線狀態
	}

	public int getStatus(){ return status; }
	//取得連線狀態

	public Ftp getConnect(){ return cl; }
	//取得FTP連線

	public FtpFile getCurrentPath(){ return curPath; }
	//取得目前的連線路徑

	public String getHost(){ return host; }
	//取得FTP伺服器的位址

	public String getUser(){ 	return user; }
	//取得使用者帳號

	public String  getPassword(){ return password; }
	//取得使用者帳號的密碼

	public void upload(File from) throws IOException {
	
		CoFile source = new LocalFile(from.getPath());
		//客戶端欲執行上傳的檔案

		CoFile to = new FtpFile(cl.pwd() + from.getName(), cl);
		//伺服端代表由客戶端上傳的檔案

		CoLoad.copy(to, source);
		//將客戶端檔案複製至伺服端

		status = UPLOAD; //設定連線狀態為上傳

		stateChanged(); //更新連線狀態
	}

	public void download(FtpFile from, File dest)
		throws IOException {

		CoFile to = new LocalFile(dest.getPath());
		//儲存從伺服端下載檔案之客戶端檔案

	   CoLoad.copy(to, from); //從伺服端下載檔案至客戶端

	   status = DOWNLOAD; //設定連線狀態

	   stateChanged(); //更新連線狀態
	}

	private void stateChanged(){ //更新連線狀態
		setChanged(); //設定物件已經產生改變
		notifyObservers(); //通知已產生物件的改變
	}
}