import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.event.*;

//瀏覽器的主要類別
public class JBrowser extends JFrame {

	static final String HOME_URL = "http://www.yahoo.com";
	//預設的瀏覽網頁路徑

	private URLLoader loader = null;
	//載入URL位址指定網頁的執行緒物件

	private JEditorPane content = new JEditorPane();
	//顯示網頁的文字編輯面版

	private JPanel jpStatus =
		new JPanel(new FlowLayout(FlowLayout.LEFT));
	//狀態列的面版

	private JLabel lbLoading =
		new JLabel("Ready", JLabel.LEFT);
	//狀態列面版內的標籤

	private URLComboBox urlcmb = new URLComboBox();
	//宣告可記憶選項清單之內容的網址組合方塊

	private JToolBar tbStandard = new JToolBar("標準"),
								 tbURL = new JToolBar("瀏覽");
	//宣告工具列

	private ArrayList<String> pageList = new ArrayList<String>();
	//宣告儲存已連結之超連結的ArrayList容器
	
	private	JButton
		btnPrevious = new JButton(new BrowserAction("上一頁",
							 new ImageIcon("image/previous.gif"))),
		btnNext = new JButton(new BrowserAction("下一頁",
							new ImageIcon("image/next.gif"))),
		btnHome = 	new JButton(new BrowserAction("首頁",
							new ImageIcon("image/home.gif"))),
		btnGo = new JButton(new BrowserAction("移至",
							new ImageIcon("image/go.gif")));
	//宣告工具列的按鈕

	private Thread loadMsgThread = null;
	//宣告顯示載入訊息的執行緒物件

	public JBrowser(){

		JMenuItem miExit = 
			new JMenuItem("結束(E)", KeyEvent.VK_E);
		//宣告結束程式的選項

		miExit.addActionListener(alExit);

		JMenu mnFile = new JMenu("檔案");
		//宣告檔案功能表

		mnFile.add(miExit); //將結束選項加入功能表
		
		JMenuBar mbMain = new JMenuBar(); //宣告功能表列
		mbMain.add(mnFile); //將功能表加入功能表列

		setJMenuBar(mbMain); //設定視窗程式使用的功能表列

		btnNext.setEnabled(false); //設定下一頁按鈕為無效
		btnPrevious.setEnabled(false); //設定上一頁按鈕為無效

		btnHome.setText(""); //設定首頁按鈕無顯示文字

		tbStandard.add(btnPrevious);  //將按鈕加入工具列
		tbStandard.add(btnNext);
		tbStandard.add(btnHome);

		tbStandard.setFloatable(false); //設定工具列不浮動

		content.setContentType("text/html");
		//設定文字編輯面版內容使用的格式

		content.setEditable(false); //設定文字編輯面版不可執行編輯

		ComboBoxAgent cma = new ComboBoxAgent(urlcmb);
		//宣告監聽網址組合方塊代理物件

		content.addHyperlinkListener(hl);
		//註冊監聽HyperlinkEvent事件的監聽器物件

		urlcmb.setEditable(true); //設定網址組合方塊是否可編輯

		urlcmb.addItemListener(il);
		//註冊監聽ItemEvent事件的監聽器物件

		addWindowListener(wa);
		//註冊監聽WindowEvent事件的監聽器物件

		tbURL.setLayout(new BorderLayout());
		tbURL.add(new JLabel("網址: "),BorderLayout.WEST );
		tbURL.add(urlcmb);
		tbURL.add(btnGo,BorderLayout.EAST);
		//將標籤、網址組合方塊與按鈕加入工具列

		tbURL.setFloatable(false);
		//設定工具列不浮動

		JPanel jpToolbar = new JPanel(new BorderLayout());
		jpToolbar.add(tbStandard,  BorderLayout.NORTH);
		jpToolbar.add(tbURL,  BorderLayout.CENTER);
		//宣告容納工具列的JPanel容器

		jpStatus.add(lbLoading);
		//將顯示連線狀態的標籤加入狀態列面版

		Container cp = getContentPane();
		cp.add(jpToolbar,  BorderLayout.NORTH);
		cp.add(new JScrollPane(content));
		cp.add(jpStatus, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(800, 600);
		setVisible(true);
	}

	//設定文字編輯面版顯示的網頁
	public void showPage(String strUrl, boolean addToList)
														throws IOException {

		//判斷是否將URL路徑加入ArrayList容器
		if (addToList){

			int size = pageList.size(); //取得ArrayList容器的大小

			if(size > 0){

				JTextField editor = 
					(JTextField)urlcmb.getEditor().getEditorComponent();
				//取得網址組合方塊的編輯文字的欄位

				String currentUrl = editor.getText();
				//取得網址組合方塊文字編輯欄位的URL路徑

				int curIndex = pageList.indexOf(currentUrl);
				//取得目前瀏覽網頁位址的索引值

				//判斷索引值加1的大小是否等於size
				if((curIndex+1) < size){

					//移除目前索引值之後的所有清單選項
					for(int i=(size-1); i>curIndex; i--){
						pageList.remove(i);
						//移除選項
					}
				}
			}

			pageList.add(strUrl); //將URL路徑加入ArrayList物件
		}

		loader = new URLLoader(strUrl);
		//宣告載入URL路徑指定網頁的執行緒物件

		loader.start(); //啟動執行緒

		loadMsgThread = new URLLoadMsg();
		//宣告監看網頁載入動作的執行緒物件

		loadMsgThread.start();
		//啟動執行緒

		//判斷ArrayList物件內的URL路徑個數是否為小於2
		if(pageList.size() < 2 ){
			btnPrevious.setEnabled(false);
			btnNext.setEnabled(false);
			//設定前一頁/下一頁按鈕為無效
		}
		else{
			int newIndex = pageList.lastIndexOf(strUrl);
			//取得欲執行連結之URL路徑
			//在ArrayList容器內的索引值位置

			btnPrevious.setEnabled(newIndex > 0);
			//設定上一頁按鈕為有效, 以上敘述相當於
			/*if(newIndex > 1)
				btnPrevious.setEnabled(true);
			*/

			btnNext.setEnabled(newIndex <  (pageList.size()-1));
			//若ArrayList容器內元素的個數減 1, 
			//大於欲執行連結之URL路徑的索引值, 
			//則設定按鈕為有效
		}
	}

	//宣告監聽結束選項之ActionEvent事件的監聽器物件
	private ActionListener alExit = new ActionListener(){

		public void actionPerformed(ActionEvent e){

			JBrowser.this.processWindowEvent(
				new WindowEvent(
					JBrowser.this, WindowEvent.WINDOW_CLOSING));
			//觸發視窗關閉事件
		}
	};

	//宣告監聽HyperlinkEvent事件的監聽器物件
	private HyperlinkListener hl = new HyperlinkListener(){
		//回應更新超連結的方法
		public void hyperlinkUpdate(HyperlinkEvent event) {

			HyperlinkEvent.EventType eventType = event.getEventType();
			//取得超連結事件的類型

			//判斷超連結的類型是否為ACTIVATED
			if(eventType == HyperlinkEvent.EventType.ACTIVATED) {

				URL url = event.getURL(); //取得引發事件之超連結的URL位址

				//判斷事件的型態是否為HTMLFrameHyperlinkEvent
				if (event instanceof HTMLFrameHyperlinkEvent) {
					HTMLFrameHyperlinkEvent 
						linkEvent = (HTMLFrameHyperlinkEvent) event;
					//將型別轉換為HTMLFrameHyperlinkEvent

					HTMLDocument 
						document = (HTMLDocument) content.getDocument();
					//取得文字編輯面版使用的Document物件

					document.processHTMLFrameHyperlinkEvent(linkEvent);
					//執行超連結事件
				}
				else {
					try{
						String strUrl = url.toString();
						//將超連結的URL轉成字串
						showPage(strUrl, true);	//顯示網頁

						urlcmb.addItem(strUrl);
						//將超連結字串加入網址組合方塊

						urlcmb.setSelectedItem(strUrl);
						//設定網址組合方塊的選取項目
					}
					catch(IOException ioe){
						System.err.println(ioe.getMessage());
					}
				}
			}
			else if(eventType == HyperlinkEvent.EventType.ENTERED) {
			//判斷超連結的型態是否為ENTERED
				lbLoading.setText(	event.getURL().toString());
				//設定lbLoading標籤顯示超連結的字串
			}
			else if(eventType == HyperlinkEvent.EventType.EXITED) {
			//判斷超連結的型態是否為EXITED
				lbLoading.setText(	"Ready");
				//設定lbLoading標籤顯示"Ready"的字串
			}
		}
	};

	//宣告監聽視窗框架WindowEvent事件的監聽器物件
	private WindowAdapter wa = new WindowAdapter(){
		//回應視窗框架的開啟動作
		public void windowOpened(WindowEvent e){
			urlcmb.load("url.dat");
			//從檔案載入儲存網址組合方塊的選項清單
		}

		//回應視窗框架的關閉動作
		public void windowClosing(WindowEvent e){
			urlcmb.save("url.dat");
			//將網址組合方塊的選項清單儲存至檔案

			System.exit(0);
		}
	};

	//宣告監聽組合方塊ItemEvent事件的監聽器物件
	private ItemListener il = new ItemListener(){

		//回應項目改變動作的方法
		public void itemStateChanged(ItemEvent e) {

			URLComboBox source = (URLComboBox) e.getSource();
			//取得引發事件的組合方塊

			//判斷事件是否為項目選取事件
			if(e.getStateChange() == ItemEvent.SELECTED){

				String url = (String) source.getSelectedItem();
				//取得選取項目的內容

				try{
					Object selObj = e.getItem();
					//取得選取項目

					if (selObj == null) return;
					//判斷選取項目是否為null

					String selStr = (String)selObj;
					//將選取項目轉型為字串

					if("".equals(selStr)) return;
					//判斷選取項目字串是否為空字串

					source.addItem(selStr);
					//將項目加入組合方塊的清單

					//判斷選取項目的索引值是否為-1
					if(pageList.indexOf(selStr) != -1){
						showPage(selStr,  false);
						//設定欲顯示網頁的URL位址
					}
					else{
						showPage(selStr,  true);
						//設定欲顯示網頁的URL位址
					}
				}
				catch(IOException ioe){
					ioe.printStackTrace();
					System.err.println("資料讀取錯誤 : " + ioe.getMessage());
				}
			}
		}
	};

	//定義處理瀏覽動作的Action物件
	class BrowserAction extends AbstractAction {

		//建構子
		public BrowserAction(String name, Icon icon){
			super(name, icon);
		}

		//回應ActionEvent事件的方法
		public void actionPerformed(ActionEvent e){

			String name = (String)getValue(Action.NAME);
			//取得Action物件NAME性質之設定值

			String strUrl = (String)urlcmb.getSelectedItem();
			//取得目前網址組合方塊的選取項目之字串

			int curIndex = pageList.lastIndexOf(strUrl);
			//搜尋目前選取之URL的索引值

			try{
				//依照引發事件之元件名稱執行對應的動作
				if(name.equals("首頁")){
				//判斷引發ActionEvent事件之物件名稱是否為首頁

					showPage(HOME_URL, true);
					//顯示首頁網頁

					urlcmb.setSelectedItem(HOME_URL);
					//設定選取項目的路徑
				}
				else if(name.equals("上一頁")){
				//判斷引發ActionEvent事件之物件名稱是否為上一頁

					String prevUrl = (String)pageList.get(curIndex -1);
					//取得上一個連結路徑

					showPage(prevUrl ,false);
					//設定顯示指定URL路徑的網頁

					urlcmb.setSelectedItem(prevUrl);
					//設定網址組合方塊選取上一個連結路徑
				}
				else if(name.equals("下一頁")){
				//判斷引發ActionEvent事件之物件名稱是否為下一頁

					String nextUrl = (String)pageList.get(curIndex + 1);
					//取得下一個連結路徑

					showPage(nextUrl ,false);
					//設定顯示指定URL路徑的網頁

					urlcmb.setSelectedItem(nextUrl);
					//設定網址組合方塊選取下一個連結路徑
				}
				else if(name.equals("移至")){
				//判斷引發ActionEvent事件之物件名稱是否為移至

					JTextField editor = 
						(JTextField) urlcmb.getEditor().getEditorComponent();
					//取得網址網址組合方塊的文字欄

					String url = editor.getText();
					//取得文字欄的內容

					showPage(url , true); //設定顯示指定

					urlcmb.setSelectedItem(url); //網址網址組合方塊
				}
			}
			catch(MalformedURLException me){
				me.printStackTrace();
				System.err.println("位址格式錯誤 : " + me.getMessage());
			}
			catch(IOException ioe){
				ioe.printStackTrace();
				System.err.println("網頁瀏覽錯誤 : " + ioe.getMessage());
			}
		}
	}

	//執行載入URL指定之網頁的執行緒
	class URLLoader extends Thread {

		protected String strUrl;
		//儲存執行緒的路徑

		public URLLoader(String strUrl){ //建構子
			this.strUrl = strUrl; //設定欲載入網頁的URL路徑			
		}

		public void run(){ //載入網頁
			try{
				content.setPage(new URL(strUrl));
				//設定顯示載入URL指定的網頁
			}
			catch(Exception ex){
				ex.printStackTrace();
				System.err.println("位址瀏覽錯誤 : " +  ex.getMessage());
			}
		}
	}

	//顯示執行載入訊息的執行緒
	class URLLoadMsg extends Thread {

		public void run(){ //載入網頁

			setCursor(
				Cursor.getPredefinedCursor(
					Cursor.WAIT_CURSOR));
			//設定等待載入時, 使用的滑鼠游標

			int i = 0;

			//判斷載入URL路徑指定之網頁是否存在
			while(loader.isAlive()){

				StringBuffer msg = new StringBuffer("loading .");
				//載入動作的訊息

				//顯示載入動作的字串
				for(int j = 0; j < (i%5); j++){
					msg.append(".");
				}

				lbLoading.setText(msg.toString());
				//設定標籤顯示載入動作的字串

				i++;

				try{
					sleep(500); //休眠0.5秒
				}
				catch(Exception ex){
				}
			}

			lbLoading.setText("Ready");
			//設定完成載入後, 顯示Ready訊息

			setCursor(
				Cursor.getPredefinedCursor(
					Cursor.DEFAULT_CURSOR));
			//設定顯示預設的滑鼠游標
		}
	};

	public static void main(String args[]) {
		new JBrowser();
	}
}

//宣告供使用者輸入欲連結網址的組合方塊, 
//此組合方塊將可記憶已經輸入的網址
class URLComboBox extends JComboBox {

	public URLComboBox(){	//建構子
		setMaximumRowCount(20);
		//設定清單可顯示的選項個數
	}

	//將URL路徑新增至組合方塊的清單
	public void addItem(String strUrl){

		//取得清單選項的個數
		if(getItemCount() == 0){
			super.addItem(strUrl);
			//將選項加入清單

			return;
		}

		for(int i=0; i<getItemCount(); i++)	{
			if(((String)getItemAt(i)).equals(strUrl)) return;
			//運用迴圈判斷欲加入URL路徑
			//是否已存在於組合方塊的清單內
			//若存在則中止執行迴圈
		}

		super.addItem(strUrl);
		//將URL路徑加入組合方塊的清單內
	}

	public void save(String fileName){

		try{
			FileOutputStream fos = new FileOutputStream(fileName);
			//建立處理將資料輸出至指定檔案的FileOutputStream物件

			ObjectOutputStream oos = new ObjectOutputStream(fos);
			//建立執行物件輸出的ObjectOutputStream物件

			oos.writeObject(getModel());
			//將組合方塊的Model物件輸出至檔案

			oos.flush(); //清除ObjectOutputStream物件串流
			oos.close(); //關閉物件輸出串流
			fos.close(); //關閉檔案輸出串流
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.err.println(
				"瀏覽位址紀錄儲存錯誤 : " + ex.getMessage());
		}
	}

	public void load(String fileName){

		try{
			File urlFile = new File(fileName);
			//宣告指定檔案的File物件

			if(!urlFile.exists()) return;
			//若檔案不存在則終止方法的執行

			FileInputStream fis = new FileInputStream(urlFile);
			//建立將資料輸入至指定檔案的FileInputStream物件

			ObjectInputStream ois = new ObjectInputStream(fis);
			//建立輸入物件的ObjectInputStream物件

			Object obj = ois.readObject();
			//從物件輸入串流讀取物件

			//判斷組合方塊物件的Model物件
			if(obj instanceof ComboBoxModel){
				removeAllItems(); //移除所有項目

				setModel((ComboBoxModel)obj);
				//設定組合方塊使用的Model物件

				setSelectedItem(null);
				//設定組合方塊不選取
			}

			ois.close(); //關閉物件輸入串流
			fis.close(); //關閉檔案輸入串流
		}
		catch(Exception ex){
				ex.printStackTrace();
				System.err.println(
					"瀏覽位址紀錄讀取錯誤 : " +  ex.getMessage());
		}
	}
}

//宣告監聽KeyEvent事件的監聽器物件
class ComboBoxAgent extends KeyAdapter {

	protected JComboBox cmb; //被監聽的組合方塊物件
	protected JTextField  editor; //組合方塊物件使用的文字欄

	public ComboBoxAgent(JComboBox cmbObj){ //建構子
		cmb = cmbObj; //設定欲監聽KeyEvent事件的組合方塊

		editor = (JTextField)cmb.getEditor().getEditorComponent();
		//取得組合方塊的編輯元件

		editor.addKeyListener(this);
		//註冊由ComboBoxAgent物件監聽組合方塊的KeyEvent事件
	}

	//回應鍵盤釋放動作的方法
	public void keyReleased(KeyEvent e){

		char ch = e.getKeyChar(); //取得按鍵的字元

		if(ch == KeyEvent.VK_ENTER){ //判斷是否由 Enter  鍵引發事件

			String url = (String) cmb.getSelectedItem();
			//判斷組合方塊選取項目的URL字串

			editor.setCaretPosition(url.length());
			//設定游標位置

			return;
		}

		if(ch == KeyEvent.CHAR_UNDEFINED 
			|| Character.isISOControl(ch))
			return;
		//判斷引發事件的按鍵之字元是否未定義或無ISO控制字元, 
		//是則中止執行方法

		int  pos = editor.getCaretPosition();
		//取得游標在編輯元件的位置

		String input = editor.getText(); //取得編輯元件的內容

		if(input.length() == 0) return;
		//若編輯元件的內容長度為0, 則終止執行方法

		//運用for迴圈以輸入路徑比對清單內的選項
		for(int k=0; k<cmb.getItemCount(); k++){

			String item = cmb.getItemAt(k).toString();
			//取得組合方塊的清單選項, 並轉換為字串

			//以輸入的字串比對清單的選項
			if(item.startsWith(input)){
				editor.setText(item); //設定編輯元件的內容
				editor.setCaretPosition(item.length());
				//設定游標在編輯元件的位置

				editor.moveCaretPosition(pos);
				//將游標移至指定位置

				break;
			}
		}
	}
}