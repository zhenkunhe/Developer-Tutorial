import javax.media.*; //引入多媒體套件

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.filechooser.FileFilter;
//引用檔案篩選類別

//定義播放器的主程式
class MPlayer extends JFrame {

	private Box bxScreen = null; //宣告Box容器
	private double ratio = 1.0; //設定畫面的比率
	private Container cp = null; //設定容器物件為null
	private Component visualPane = null, controlPane = null;
	//設定Component元件為null

	private Player player = null; //宣告播放器物件

	private JCheckBoxMenuItem 
		cbmRepeat = new JCheckBoxMenuItem("重複播放");
	//宣告控制是否重複播放視訊檔的核取方塊選項

	public MPlayer(){ //建構子

		JMenu mnFile = new JMenu("檔案(F)"),
					mnScreen = new JMenu("畫面(S)"),
					mnPlay = new JMenu("播放(P)");
		//宣告檔案與畫面功能表

		JMenuItem 
			miOpen = new JMenuItem("開啟(O)", KeyEvent.VK_O),
			miExit = new JMenuItem("結束(E)", KeyEvent.VK_E);
		//宣告開啟與結束選項

		JCheckBoxMenuItem 
			cbmi100 = new JCheckBoxMenuItem(new ScreenSize("100%")),
			cbmi75 = new JCheckBoxMenuItem(new ScreenSize("75%")),
			cbmi50 = new JCheckBoxMenuItem(new ScreenSize("50%")),
			cbmi125 = new JCheckBoxMenuItem(new ScreenSize("125%")),
			cbmi150 = new JCheckBoxMenuItem(new ScreenSize("150%")),
			cbmi200 = new JCheckBoxMenuItem(new ScreenSize("200%"));
		//宣告設定畫面大小的核取方塊選項

		mnFile.setMnemonic(KeyEvent.VK_F);
		mnScreen.setMnemonic(KeyEvent.VK_S);
		mnPlay.setMnemonic(KeyEvent.VK_P);
		//設定助憶鍵

		miOpen.addActionListener(alOpen);
		miExit.addActionListener(alExit);
		//註冊監聽功能表選項的ActionEvent事件

		mnFile.add(miOpen); //將選項加入功能表
		mnFile.add(miExit);

		ButtonGroup bg = new ButtonGroup();
		bg.add(cbmi100);
		bg.add(cbmi75);
		bg.add(cbmi50);
		bg.add(cbmi200);
		bg.add(cbmi150);
		bg.add(cbmi125);
		//將核取方塊選項加入按鈕群組
		
		mnScreen.add(cbmi100);
		mnScreen.add(cbmi75);
		mnScreen.add(cbmi50);
		mnScreen.add(cbmi200);
		mnScreen.add(cbmi150);
		mnScreen.add(cbmi125);
		//將核取方塊選項加入畫面功能表

		cbmi100.setSelected(true);
		//預設選取顯示比例為100%的螢幕畫面

		mnPlay.add(cbmRepeat);	//將選項加入功能表

		JMenuBar jmb = new JMenuBar();
		//宣告功能表列

		jmb.add(mnFile);
		jmb.add(mnScreen);
		jmb.add(mnPlay);
		//將檔案功能表與螢幕功能表加入功能表列

		setJMenuBar(jmb);	 //設定視窗框架使用的功能表列

		cp = getContentPane();

		addComponentListener(cma);
		//註冊監聽ComponentEvent事件的監聽器物件

		addWindowListener(wa);
		//註冊監聽視窗框架WindowEvent事件的監聽器物件

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 600);
		setVisible(true);

		Manager.setHint(
			Manager.LIGHTWEIGHT_RENDERER, new Boolean(true));
		//呼叫管理物件的setHint()方法, 設定播放器元件的轉譯物件
	}

	//建立螢幕容器
	private Box buildScreen(){

		Dimension 
			dmVisual = visualPane.getPreferredSize(),
			dmContent = cp.getSize(),
			dmControl = controlPane.getSize();
		//取得視覺面版、內容面版與控制面版的尺寸

		int strutWidth = 
			(int)(dmContent.width - dmVisual.width * ratio) / 2;
		//計算支架的寬度

		int strutHeight = 
			(int)(dmContent.height - 
			dmVisual.height * ratio - dmControl.height) / 2;
		//計算支架的高度

		Box bxInner = new Box(BoxLayout.X_AXIS);
		bxInner.add(Box.createHorizontalStrut(strutWidth));
		bxInner.add(visualPane);
		bxInner.add(Box.createHorizontalStrut(strutWidth));
		//宣告Box容器, 置入顯示影片檔的容器, 
		//並建立水平方向空白間距的支架

		Box bxOutter = new Box(BoxLayout.Y_AXIS);
		bxOutter.add(Box.createVerticalStrut(strutHeight));
		bxOutter.add(bxInner);
		bxOutter.add(Box.createVerticalStrut(strutHeight));
		//宣告Box容器, 置入顯示影片檔的容器,
		//並建立垂直方向空白間距的支架

		return bxOutter;
	}

	//註冊回應WindowEvent事件的監聽器物件
	WindowAdapter wa = new WindowAdapter(){

		//回應視窗關閉動作
		public void windowClosing(WindowEvent e){
			System.exit(0); //結束程式執行
		}
	};

	//宣告監聽功能表內開啟選項ActionEvent事件的監聽器物件
	ActionListener alOpen = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String[]  mpgFilter = {"mpg", "mpeg"},
						  aviFilter =	{"avi", "wmv"},
						  qtFilter = {"mov"};
			//宣告供使用者選取檔案類型的篩選物件

			MediaFileChooser mcObj = new MediaFileChooser(
					MPlayer.class.getResource("/").getPath() + "mpg");
			//宣告檔案選取器, 並設定起始檢視路徑

			mcObj.addChoosableFileFilter(
				mpgFilter, "電影檔(mpg) (*.mpg, *.mpeg)");
			mcObj.addChoosableFileFilter(
				aviFilter, "Windows 視訊檔(avi) (*.avi, *.wmv)");
			mcObj.addChoosableFileFilter(
				qtFilter, "QuickTime 影像檔 (*.mov)");
			//加入檔案篩選物件

			mcObj.setFileFilter(mcObj.getAcceptAllFileFilter());
			//設定對話盒預設使用選取所有檔案的FileFilter

			mcObj.setDialogTitle("開啟影片");
			//設定對話盒的標題

			int result = mcObj.showOpenDialog(MPlayer.this);
			//顯示對話盒, 並取得代表按下按鈕的常數

			//判斷按下的按鈕
			if(result == MediaFileChooser.APPROVE_OPTION){

				if(player != null){ //判斷播放物件是否為null
						if(controlPane != null) 
							cp.remove(controlPane); //移除控制面版

						if(bxScreen != null)						
							cp.remove(bxScreen); //移除顯示畫面

						player.stop(); //停止播放物件
						player.close(); //關閉播放物件					
				}

				File selFile = mcObj.getSelectedFile();
				//取得選取的檔案
		
				try{
					player = Manager.createPlayer(
						new URL(new String("file:" + selFile.getPath())));
					//呼叫管理物件取得播放物件
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(
						MPlayer.this, ex.getMessage(), 
						"錯誤", JOptionPane.ERROR_MESSAGE);
					//顯示錯誤訊息
				}

				if(player != null){ //判斷播放物件是否為null
					player.addControllerListener(ca);
					//註冊回應ControllerEvent事件的監聽器物件

					player.realize(); //建構播放物件
				}
			}
		}
	};

	//宣告監聽多媒體播放控制項事件的監聽器物件
	ControllerAdapter ca = new ControllerAdapter(){

		//控制項完全顯示
		public void realizeComplete(RealizeCompleteEvent e){

			Player player = (Player) e.getSource();
			//取得引發事件的播放器物件

			player.prefetch(); //準備載入
		}

		//控制項發生錯誤
		public void controllerError(ControllerErrorEvent e){
			JOptionPane.showMessageDialog(
				MPlayer.this, e.getMessage(), "錯誤",
				JOptionPane.ERROR_MESSAGE);
		}

		//多媒體檔完成載入準備
		public void prefetchComplete(PrefetchCompleteEvent e){

			Player player = (Player) e.getSource();
			//取得引發事件的播放器物件

			controlPane = player.getControlPanelComponent();
			//取得控制面版

			if(controlPane != null){ //判斷是否正確取得控制面版
				cp.add(controlPane, BorderLayout.SOUTH);
				//將控制項面版加入內容面版
			}

			visualPane = player.getVisualComponent();
			//取得畫面顯示面版

			if (visualPane != null) { //判斷是否正確取得畫面面版
				bxScreen = buildScreen(); //建立畫面
				cp.add(bxScreen, BorderLayout.CENTER);
				//將畫面加入內容面版
			}

			validate(); //進行驗證
			player.start(); //開始播放物件
		}

		//結束播放
		public void endOfMedia(EndOfMediaEvent e){

			//判斷重複播放核取方塊選項是否選取
			if(cbmRepeat.isSelected()){
				Player player = (Player) e.getSource();
				//取得引發事件的播放物件

				player.setMediaTime(new Time(0));
				//設定開始播放的時間

				player.start(); //開始播放
			}
		}
	};

	//宣告監聽結束選項ActionEvent事件的監聽器物件
	ActionListener alExit = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			System.exit(0); //結束程式執行
		}
	};

	//宣告監聽ComponentEvent事件的監聽器物件
	ComponentAdapter cma = new ComponentAdapter() {

		//重新調整元件大小
		public void componentResized(ComponentEvent e) {

			if(bxScreen != null){ //判斷是否完成視窗物件的建立
				cp.remove(bxScreen); //移除視窗物件
				bxScreen = buildScreen(); //建立視窗元件
				cp.add(bxScreen); //將視窗元件加入內容面版
				validate(); //執行確認
			}
		}
	};

		//定義設定畫面大小百分比的抽象動作類別
	class ScreenSize extends AbstractAction {

		public ScreenSize(String name){ //宣告螢幕大小

			super(name);
			//將動作Action物件名稱傳入基礎類別建構子

			putValue(Action.SHORT_DESCRIPTION, name);
			//設定Action物件的簡短描述文字
		}

		//回應ActionEvent事件, 並設定顯示畫面的大小
		public void actionPerformed(ActionEvent e){

			String name = (String) getValue(Action.NAME);
			//取得Action物件名稱的設定值

			//依照動作命令字串設定畫面大小的比率
			if(name.equals("100%"))	
				ratio = 1.0;
			else if(name.equals("75%"))
				ratio = 0.75;
			else if(name.equals("50%"))
				ratio = 0.5;
			else if(name.equals("200%"))
				ratio = 2.0;
			else if(name.equals("125%"))
				ratio = 1.25;
			else if(name.equals("150%"))
				ratio = 1.5;

			if(bxScreen != null){ //判斷容納畫面的Box容器是否完成建立
				cp.remove(bxScreen); //移除容納畫面的Box容器
				bxScreen = buildScreen(); //建立畫面
				cp.add(bxScreen); //將畫面加入內容面版
				validate(); //執行驗證
			}
		}
	}

	public static void main(String[] args) {
		new MPlayer(); //宣告視窗框架物件
	}
}