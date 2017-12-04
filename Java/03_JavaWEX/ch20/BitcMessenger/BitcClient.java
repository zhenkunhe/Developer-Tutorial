import java.net.*; //載入相關套件
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

//定義與其他使用者交談的聊天視窗
class TalkFrame extends JFrame implements Runnable {

	private JTextArea taMsg = new JTextArea(20, 40);
	private JTextField input = new JTextField(30);
	private JButton btnSend = new JButton("送出");

	private Socket talkSocket = null;
	private String name, talkTo;
	private Thread talkThread; //執行交談的執行緒

	DataInputStream in; //取得從連線讀取資料的InputStream物件
	DataOutputStream out; //取得輸出資料至連線的OutputStream物件

	//當由其他電腦主動傳訊過來進行交談時, 宣告交談視窗的建構子,
	//並以傳入代表連線的Socket物件與本身的帳號名稱建立視窗
	TalkFrame(Socket socket, String name){
		talkSocket = socket; //設定處理進行交談之連線的Socket物件		
		this.name = name;		

		try{
			in = new DataInputStream(
				new BufferedInputStream(talkSocket.getInputStream()));
			//取得從連線讀取資料的InputStream物件, 並建立為DataInputStream物件

			out = new DataOutputStream(
				new BufferedOutputStream(talkSocket.getOutputStream()));
			//取得輸出資料至連線的OutputStream物件, 並建立為DataOutputStream物件

			talkTo = in.readUTF();  //以UTF-8編碼讀取字串
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}

		setTitle(talkTo + " 想要與您交談"); //設定交談視窗標題

		initPane(); //起始交談視窗

		talkThread = new Thread(this); //建立執行交談的執行緒
		talkThread.start(); //啟動執行緒
	}

	//欲主動將訊息傳遞給其他電腦進行交談時, 宣告交談視窗的建構子,
	//將以交談對象名稱、位址與本身的帳號名稱名稱建立交談視窗
	TalkFrame(String talkTo, String add, String name, int talkPort){
		super("與 " + talkTo + " 交談");

		this.name = name; //設定本身的名稱
		this.talkTo = talkTo; //設定交談對象的名稱		

		try{
			talkSocket = new Socket(add, talkPort);
			//宣告處理執行交談之連線的Socket物件

			in = new DataInputStream(
				new BufferedInputStream(talkSocket.getInputStream()));
			//取得從連線讀取資料的InputStream物件, 並建立為DataInputStream物件

			out = new DataOutputStream(
				new BufferedOutputStream(talkSocket.getOutputStream()));
			//取得從連線讀取資料的OutputStream物件, 並建立為DataOutputStream物件

			out.writeUTF(name); //將自己的帳號名稱輸出給交談對象
			out.flush();
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}

		initPane(); //起始交談視窗

		talkThread = new Thread(this);  //建立執行交談的執行緒
		talkThread.start(); //啟動執行緒
	}

	private void initPane(){ //起始交談視窗

		Box bxInput = new Box(BoxLayout.X_AXIS);
		bxInput.add(input); //加入輸入交談訊息的文字方塊
		bxInput.add(Box.createHorizontalStrut(10));
		bxInput.add(btnSend); //加入傳出訊息的按鈕

		//定義回應ActionEvent事件的監聽器
		btnSend.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String send = input.getText();
				//取得從文字方塊取得交談訊息

				if(send.length() == 0)
					return;

				taMsg.append(name + " 說 : " + send + "\n");
				//將訊息加入文字區

				input.setText(null); //重設文字方塊的內容
				try{
					out.writeUTF(send); //將訊息傳至交談對象
					out.flush();
				}
				catch(Exception ex){
					System.out.println(ex.toString());
				}
			}
		});

		taMsg.setEditable(false);  //設定文字區不可執行編輯

		Container cp  = getContentPane();
		cp.add(taMsg); //將文字區加入交談視窗
		cp.add(bxInput, BorderLayout.SOUTH);
		//將輸入訊息的文字方塊加入交談視窗

		addWindowListener(wa);

		//設定視窗的位置、視窗大小, 並顯示視窗
		setLocation(100, 100);		
		setSize(400, 300);
		setVisible(true);
	}

	//定義並加入處理視窗訊息的監聽器
	WindowAdapter wa = new WindowAdapter() {

		public void windowClosing(WindowEvent e){ //回應視窗關閉訊息的方法
			try{
				talkThread.interrupt();	//強制中斷執行緒

				talkSocket.close(); //關閉連線
			}
			catch(Exception ex){
				System.out.println(ex.toString());
			}
		}
	};

	public void run(){ //處理交談訊息之執行緒
		try{
			while(talkSocket.isConnected()){ //判斷是否持續連線
				String msg = in.readUTF(); //以UTF-8編碼讀取訊息

				taMsg.append(talkTo + " 說 : " + msg + "\n");
				//將訊息加到文字區
			}
		}
		catch(SocketException se){
			System.out.println("主動中斷連線");
		}
		catch(EOFException ee){
			btnSend.setEnabled(false);
			taMsg.append("對方連線已經中斷......");
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}
	}
}

//定義顯示登入BitcServer伺服器, 可透過網路交談的使用者
public class BitcClient extends JFrame implements Runnable{

	static final int port = 2004; //BitcServer提供連線之通訊埠編號
	static int talkPort; //執行交談時運用的通訊埠編號
	String serverName; //登入伺服器的名稱

	private DataInputStream in; //從連線讀取資料的InputStream物件
	private DataOutputStream out; //輸出資料至連線的OutputStream物件
	private String userAddPort; //以使用者名稱與位址建立的帳號名稱
	private String userName; //使用者名稱
	private Thread client; //與伺服器連線的執行緒

	DefaultListModel dlmUser = new DefaultListModel();
	//處理清單方塊顯示內容的DefaultListModel物件

	JList lsUser = new JList(dlmUser);
	JButton btnLogin = new JButton("登入");
	JButton btnLogout = new JButton("登出");

	Socket socket = null; //處理與BitcServer伺服器連線的Socket物件

	public BitcClient(String server, String user){
	//宣告BitcServer即時連線客戶端程式的建構子

		super("Bitc Messenger : 我是" + user);

		userName = user;
		Random rand = new Random(); //宣告產生亂數的Random物件
		talkPort = rand.nextInt(100) + 1024;
		//以亂數方式指定交談使用的通訊埠

		try{
			this.userAddPort = user + "@" 
				+ InetAddress.getLocalHost().getHostAddress() 
				+ ":" + talkPort;  //帳號名稱
		}
		catch(UnknownHostException uhe){ System.out.println(uhe.toString());}

		serverName = server;  //設定登入的伺服器

		lsUser.addMouseListener(ma);
		//註冊回應清單方塊之MouseEvent事件的監聽器

		btnLogin.addActionListener(alLogin);
		//註冊回應 登入 按鈕之ActionEvent事件的監聽器

		btnLogout.addActionListener(alLogout);
		//註冊回應 登出 按鈕之ActionEvent事件的監聽器

		btnLogout.setEnabled(false); //設定 登出 按鈕無效

		Box bxBtn = new Box(BoxLayout.X_AXIS);
		bxBtn.add(btnLogin); //將 登入 按鈕加入Box容器內
		bxBtn.add(Box.createHorizontalStrut(10));
		bxBtn.add(btnLogout); //將 登出 按鈕加入Box容器內

		Container cp = getContentPane(); //取得內容面版
		cp.add(lsUser); //將使用者清單方塊加入內容面版

		cp.add(bxBtn, BorderLayout.SOUTH);
		//將包含 登入 與 登出 按鈕的Box容器加入內容面版

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
	}

	public void run(){ //處理從伺服器傳來登入帳號
		try{
			out.writeUTF(userAddPort);
			//以UTF-8編碼送出使用者名稱字串

			out.flush();

			while(true){  //以while迴圈等待讀取訊息
				String temp = in.readUTF();
				//讀取BitcServer伺服器傳送過來包含登入使用者帳號的訊息
				//訊息將使用UTF-8編碼

				dlmUser.clear(); //清除顯示登入使用者的清單方塊
				String userData = temp.substring(1, temp.length()-1);
				//將讀取到的訊息去掉頭尾的無用字元

				String[] aryTempItem = userData.split(", ");
				//以", "為分隔字串將訊息切割為字串陣列

				for(String tempItem: aryTempItem){
					String[] aryTemp = tempItem.split("=");
					//以"="為分隔字串將訊息切割為字串陣列

					//判斷字串並不是代表自己的帳號, 然後才將字串加入清單方塊
					if(!userAddPort.equals(aryTemp[1])){
						dlmUser.addElement(aryTemp[1]);
						//將顯示帳號的字串加入清單方塊
					}
				}

				dlmUser.addElement("目前線上有 " + dlmUser.getSize() + " 位朋友");
			}
		}
		catch(SocketException se){ }
		catch(IOException e){
			System.out.println(e.toString());			
		}
		finally{
			try{
				in.close(); //關閉串流
				out.close(); 
			}
			catch (IOException e2) { }
		}
	}

	MouseAdapter ma = new MouseAdapter(){ //回應MouseEvent事件的監聽器
		public void mouseClicked(MouseEvent e){
			if (e.getClickCount() == 2) { //判斷滑鼠左鍵按下的次數
				JList source = (JList)e.getSource();
				//取得清單方塊被點選的項目

				String[] selUser = 
					((String)source.getSelectedValue()).split("@");
				//將被點選項目的內容, 依照 @ 字元分隔成兩個部分

				String[] IP_Port = selUser[1].split(":");

				new TalkFrame(selUser[0], IP_Port[0], 
											userName, Integer.parseInt(IP_Port[1]));
				//宣告執行交談的視窗
			}
		}
	};

	//定義並宣告回應 登入 按鈕之ActionEvent事件的監聽器
	ActionListener alLogin = new ActionListener(){
		public void actionPerformed(ActionEvent e){

			try{
				socket = new Socket(serverName, port);
				//建立與BitcServer伺服器連線的Socket物件

				in = new DataInputStream(
					new BufferedInputStream(socket.getInputStream()));
				out = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));
				//建立處理連線資料輸出與輸入的串流物件

				btnLogin.setEnabled(false); //設定 登入 按鈕失效
				btnLogout.setEnabled(true); //設定 登出 按鈕有效

				client = new Thread(BitcClient.this);
				//宣告與BitcServer伺服器連線的執行緒

				client.start(); //啟動執行緒

				(new WaitTalk()).start();
				//宣告啟動等待處理訊息交談的執行緒
			}
			catch(Exception ex){
				System.out.println(ex.toString());
			}
		}
	};

	//定義並宣告回應 登出 按鈕之ActionEvent事件的監聽器
	ActionListener alLogout = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			try{
				out.writeUTF("Bye!"); //輸出結束訊息至BitcServer伺服器
				out.flush();
				client.interrupt(); //強制中斷執行緒
				socket.close(); //關閉連線

				dlmUser.clear(); //清除顯示登入使用者帳號的清單方塊

				btnLogin.setEnabled(true); //設定 登入 按鈕有效
				btnLogout.setEnabled(false); //設定 登出 按鈕失效
			}
			catch(Exception ex){
				System.out.println(ex.toString());
			}
		}
	};

	//等待其他使用者連線交談的執行緒
	class WaitTalk extends Thread {

		public void run(){			
			try{
				ServerSocket server = new ServerSocket(talkPort);
				//以指定的通訊埠宣告等待連線的ServerSocket物件

				System.out.println("監聽 " + talkPort + " 通訊埠等待連線...");

				while(true){
					Socket client = server.accept(); //取得連線的客戶端Socket物件
					new TalkFrame(client, userName); //宣告交談視窗
				}
			}
			catch(IOException e){
				System.out.println(e.toString());			
			}
		}
	}

	public static void main (String... args) throws Exception {
		if (args.length != 2) {
			System.out.println("使用語法: BitcClient <伺服器名稱> <使用者帳號>");
			return;
		}

		new BitcClient(args[0], args[1]); //宣告BitcServer即時連線客戶端物件
	}
}