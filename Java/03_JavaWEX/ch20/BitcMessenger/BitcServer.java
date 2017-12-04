import java.net.*; //載入套件
import java.io.*;
import java.util.*;

//處理連線至BitcServer伺服器之使用者的執行緒類別
class UserThread extends Thread {
	private static HashMap<UserThread, String> 
		hmUser = new HashMap<UserThread, String>();
	//儲存執行緒類別與帳號名稱的Map容器

	private Socket socket; //儲存處理連線的Socket物件
	private DataInputStream in;
	private DataOutputStream out;
	//透過連線讀取或輸出訊息的串流物件

	public UserThread(Socket socket) throws IOException {

		this.socket = socket; //設定處理客戶端連線的Socket物件

		in = new DataInputStream(
			new BufferedInputStream(socket.getInputStream()));
		out = new DataOutputStream(
			new BufferedOutputStream(socket.getOutputStream()));
		//宣告透過連線處理訊息讀取與輸出的串流物件
	}

	public String toString(){
		return socket.getInetAddress().getHostAddress();
		//定義輸出UserThread時, 將傳出連線電腦的IP位址
	}

	public void run() { //執行緒的執行方法

		String address = socket.getInetAddress().getHostAddress();
		//以伺服器名稱取得IP位址

		String user = ""; //宣告儲存使用者名稱的字串

		try {
			user = in.readUTF(); //讀取使用UTF-8編碼的訊息
			hmUser.put(this, user);
			//將執行緒物件與對應的使用者帳號、IP與交談通訊埠放入容器

			System.out.println("目前登入BitcServer伺服器的帳號數: " + hmUser.size());
			//顯示連線個數

			Notify(hmUser.toString());
			//呼叫Notify()方法將登入帳號資料發送給所有連線帳號

			System.out.println(in.readUTF());
			//讀取客戶端傳送過來的訊息, 訊息將使用UTF-8編碼
			//如果有需要, 可以運用Client傳過來的訊息, 例如加入狀態判斷

			System.out.println(user + "離線");
			//顯示使用者離線訊息
		}
		catch (IOException e) {
			System.out.println(user + "的連線發生 [" + e.toString() + "] "
				 + "錯誤, 關閉連線!");
		}
		finally{
			hmUser.remove(this); //移除執行緒物件
			Notify(hmUser.toString());
			//呼叫Notify()方法將登入帳號資料發送給所有連線帳號

			System.out.println("目前登入BitcServer伺服器的帳號數: " + hmUser.size());
			try{
				socket.close();  //關閉Socket物件
			}
			catch(IOException e) { }
		}
	}

	//將帳號訊息傳送給所有連線至BitcServer伺服器的帳號
	public static void Notify(String userData){
		synchronized(hmUser){ //同步hmUser屬性

			Set<Map.Entry<UserThread, String>> stEntry = hmUser.entrySet();
			//呼叫entrySet()方法取得包含Map容器內元素的Set容器

			//以加強型for迴圈配合包含Map容器內元素的Set容器,
			//處理Map容器的所有元素
			for(Map.Entry<UserThread, String> elm: stEntry){
        			try{
					UserThread currentUser = (UserThread)elm.getKey();
					//呼叫getKey()方法從容器內取得處理登入BitcServer伺服器之使用者的執行緒類別

					synchronized(currentUser.out){ 
						currentUser.out.writeUTF(userData);
						//送出以UTF-8編碼的訊息
					}
					currentUser.out.flush();
				}
        			catch(IOException e) { }
			}
		}
	}
}

//定義BitcServer伺服器主程式類別
public class BitcServer {

	static final int port = 2004; //提供BitcServer客戶端程式連線的通訊埠

	public static void main(String args[]){
		try{
			ServerSocket server = new ServerSocket(port);
			//宣告監聽通訊埠的ServerSocket物件

			System.out.println("啟動BitcServer伺服器 (按下 Ctrl + C 結束)...");
			System.out.println("使用通訊埠: " + port);
			System.out.println("等待用戶端連線中......");

			while (true){  
				Socket client = server.accept(); //取得連線的客戶端Socket物件
				System.out.println("客戶端連線的IP位址: " 
						+ client.getInetAddress().getHostAddress());

				UserThread currentUser = new UserThread(client);
				//建立處理登入BitcServer伺服器之使用者訊息的執行緒物件

				currentUser.start();  //啟動執行緒
			}
		}
		catch (Exception e){
			e.printStackTrace();
			return;
		}
	}
}