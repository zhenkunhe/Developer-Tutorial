import javax.media.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

//宣告繼承JFrame類別MP3播放器的視窗框架程式
class MP3Player extends JFrame {

	String[][] strFilter = 
		{{"mp3", "MPEG-3 音效檔 (*.mp3)"},
		 {"swf", "Macromedia Flash (*.swf)"},
		 {"wav", "Windows Waveform 音效檔 (*.wav)"},
		 {"mid", "Musical Instrument Digital Interface  (*.mid)"}};
	//設定篩選檔案的副檔名與描述字串

	PlayList ltList = null; //顯示播放清單的清單方塊
	SongList ltDet = null; //顯示歌曲清單的清單方塊

	public MP3Player(){

		JPanel controlPane = new JPanel(); //容納播放器控制項的容器
		JToolBar tbControl = new JToolBar("播放"); //宣告工具列

		Player player = null; //宣告播放器物件為null

		JRadioButton 
			rbAll =  new JRadioButton("播放整個清單", true),
			rbOne =  new JRadioButton("重複播放一首"),
			rbAuto =  new JRadioButton("重複播放整個清單");
		//宣告設定清單播放方式的選擇鈕

		Manager.setHint(
			Manager.LIGHTWEIGHT_RENDERER, 
			new Boolean(true));
		//設定播放器控制項使用的外觀

		Container cp = getContentPane(); //取得內容面版

		rbAll.addActionListener(alPlay);
		rbOne.addActionListener(alPlay);
		rbAuto.addActionListener(alPlay);
		//註冊監聽ActionEvent事件的監聽器物件

		ButtonGroup bgRepeat = new ButtonGroup();
		//宣告按鈕群組

		bgRepeat.add(rbAll);
		bgRepeat.add(rbAuto);
		bgRepeat.add(rbOne);
		//將選擇鈕加入按鈕群組

		tbControl.add(rbAll);
		tbControl.add(rbAuto);
		tbControl.add(rbOne);
		tbControl.add(controlPane);
		//將選擇鈕加入工具列

		cp.add(tbControl, BorderLayout.NORTH);
		//將工具列加入內容面版

		ltList = new PlayList(); //顯示播放清單的清單方塊
		ltList.setFixedCellWidth(150); //設定清單方塊的固定寬度
		ltList.setSelectionMode(
			ListSelectionModel.SINGLE_SELECTION);
		//設定清單方塊的選取模式為單選

		//監聽顯示播放清單清單方塊ListSelectionEvent事件的監聽器物件
		ltList.addListSelectionListener(lslList);

		ltDet = new SongList(controlPane);
		//宣告顯示播放歌曲的清單方塊

		//註冊回應MouseEvent事件的監聽器物件
		ltDet.addMouseListener(new MouseAdapter(){

			//回應滑鼠按下事件
			public void mouseClicked(MouseEvent e) {

				if(e.getClickCount() != 2) return;
				//判斷滑鼠按下次數是否不等於2

				SongList sl = (SongList) e.getSource();
				//取得歌曲播放清單

				int selIndex = sl.getSelectedIndex();
				//取得選取項目的索引值

				if(selIndex == -1) return;
				//若未選取任何選項則終止執行方法

				sl.setCurrentPlay(selIndex);
				//設定目前播放的歌曲

				sl.play(); //播放歌曲
			}
		});	

		JMenu mnFile = new JMenu("檔案(F)"), 
					mnEdit = new JMenu("編輯清單(E)"),
					mnList = new JMenu("播放清單(L)");
		//宣告功能表

		JMenuItem
			miExit = new JMenuItem("結束(E)", KeyEvent.VK_E),
			miNew = new JMenuItem("新增(N)", KeyEvent.VK_N),
			miDel = new JMenuItem("刪除(D)", KeyEvent.VK_D),
			miMod = new JMenuItem("修改(M)", KeyEvent.VK_M),
			miNewItem  = new JMenuItem("新增歌曲(N)", KeyEvent.VK_N),
			miDelItem  = new JMenuItem("刪除歌曲(D)", KeyEvent.VK_D);
		//宣告功能表選項

		mnFile.add(miExit); //新增功能表選項

		mnList.add(miNew);
		mnList.add(miMod);
		mnList.add(miDel);

		mnEdit.add(miNewItem);
		mnEdit.add(miDelItem);

		JMenuBar jmb = new JMenuBar();
		jmb.add(mnFile);
		jmb.add(mnList);
		jmb.add(mnEdit);
		//建立視窗框架的功能表列

		miExit.addActionListener(alExit);
		miNew.addActionListener(alNew);
		miDel.addActionListener(alDel);
		miMod.addActionListener(alMod);
		miNewItem.addActionListener(alNewItem);
		miDelItem.addActionListener(alDelItem);
		//註冊監聽ActionEvent事件的監聽器物件

		addWindowListener(waWindow);
		//註冊監聽WindowEvent事件的監聽器物件

		setJMenuBar(jmb);	 //設定視窗框架的功能表列

		cp.add(new JScrollPane(ltList), BorderLayout.WEST);
		cp.add(new JScrollPane(ltDet));
		//將清單方塊加入內容面版

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 600);
		setVisible(true);
	}

	//宣告監聽WindowEvent事件的監聽器物件
	WindowAdapter waWindow = new WindowAdapter(){

		//回應視窗開啟動作的方法
		public void windowOpened(WindowEvent e){
			ltList.load("PlayList/main.dat");
			//從檔案載入清單方塊的選項內容

			ltList.setSelectedIndex(0);
			//設定選取第一個項目
		}

		//回應視窗開啟動作的方法
		public void windowClosing(WindowEvent e){
			ltList.save("PlayList/main.dat");
			//儲存清單方塊的選項內容

			System.exit(0); //結束執行系統
		}
	};

	//註冊回應結束選項的ActionEvent事件的監聽器物件
	ActionListener alExit = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			System.exit(0); //結束系統執行
		}
	};

	//新增播放清單
	ActionListener alNew = new ActionListener(){

		//回應動作的方法
		public void actionPerformed(ActionEvent e){

			String listName = (String)JOptionPane.showInputDialog(
				MP3Player.this, "請輸入播放清單名稱", "資料輸入",
				JOptionPane.QUESTION_MESSAGE, null, null, null);
			//顯示輸入播放清單方塊名稱的對話盒

			if("".equals(listName)){
				JOptionPane.showMessageDialog(
					MP3Player.this, "不可輸入空白字串", "錯誤",
					JOptionPane.ERROR_MESSAGE);
				//顯示訊息對話盒

				return;
			}

			DefaultListModel dlmList = 
				(DefaultListModel) ltList.getModel();
			//宣告儲存播放清單內容的Model物件

			//判斷輸入的播放清單名稱是否已存在於清單方塊內
			if(dlmList.contains(listName)){
				JOptionPane.showMessageDialog(
					MP3Player.this, "已存在同名的清單", "錯誤",
					JOptionPane.ERROR_MESSAGE);
				//顯示訊息對話盒
				return;
			}

			int curIndex = ltList.getSelectedIndex();
			//取得清單方塊選取項目的索引值

			dlmList.add(
				(curIndex==0 ? curIndex=1: curIndex), listName);
			//將新增播放清單的名稱加入清單方塊

			ltList.setSelectedIndex(curIndex); //設定選取項目
		}
	};

	//刪除播放清單
	ActionListener alDel = new ActionListener(){

		//回應動作的方法
		public void actionPerformed(ActionEvent e){

			DefaultListModel dlmList = 
				(DefaultListModel) ltList.getModel();
			//取得儲存清單方塊之選項內容的Model物件

			File fileDel = 
				new File("PlayList/" + 
				(String)ltList.getSelectedValue() + ".dat");
			//宣告代表欲刪除檔案的File物件

			int curIndex = ltList.getSelectedIndex();
			//取得清單方塊選取選項的索引值

			if(curIndex == 0){
				JOptionPane.showMessageDialog(MP3Player.this, 
					"不可刪除'最喜愛的歌曲'清單", "錯誤",
					JOptionPane.ERROR_MESSAGE);
				return;
			}

			fileDel.delete(); //刪除檔案

			dlmList.remove(curIndex);
			//移除清單方塊目前選取的播放清單

			ltList.setSelectedIndex(
				(curIndex<dlmList.getSize() ? curIndex : curIndex-1));
			//設定新的選取項目
		}
	};

	//回應修改播放清單名稱的監聽器物件
	ActionListener alMod = new ActionListener(){

		//回應動作的方法
		public void actionPerformed(ActionEvent e){

			int index = ltList.getSelectedIndex();
			//取得選取項目的索引值

			if(index == -1){
				JOptionPane.showMessageDialog(
					MP3Player.this, "請選取欲修改名稱的清單 !");
				//顯示訊息的對話盒
				return;
			}
	
			String listName = (String)JOptionPane.showInputDialog(
				MP3Player.this, "請修改播放清單名稱", "資料修改",
				JOptionPane.QUESTION_MESSAGE, null, null,
				(String) ltList.getSelectedValue());
			//顯示輸入清單方塊名稱的對話盒

			if("".equals(listName)){ //判斷是否輸入空白字串
				JOptionPane.showMessageDialog(
					MP3Player.this, "不可輸入空白字串", 
					"錯誤", JOptionPane.ERROR_MESSAGE);
				//顯示錯誤訊息

				return;
			}

			DefaultListModel dlmList = 
				(DefaultListModel) ltList.getModel();
			//取得儲存顯示播放清單列表之清單方塊的Model物件

			//判斷輸入的播放清單名稱是否存在於Model物件
			if(dlmList.contains(listName)){

				JOptionPane.showMessageDialog(
					MP3Player.this, "已存在同名的清單", "錯誤", 
					JOptionPane.ERROR_MESSAGE);
				//顯示錯誤訊息

				return;
			}

			dlmList.setElementAt(listName, index);
			//設定清單方塊選取的選項
		}
	};

	//將歌曲新增至播放清單
	ActionListener alNewItem = new ActionListener(){

		//回應動作的方法
		public void actionPerformed(ActionEvent e){
			
			MediaFileChooser mcObj = new MediaFileChooser(
				MP3Player.class.getResource("/").getPath() + "mp3",
				strFilter);
			//宣告選取多媒體檔案選擇器

			mcObj.setMultiSelectionEnabled(true);
			//設定允許多重選取

			mcObj.setDialogTitle("開啟舊檔");
			//設定檔案選擇器對話盒的標題名稱

			int result = mcObj.showOpenDialog(MP3Player.this);
			//取得開啟舊檔對話盒按下按鈕的常數

			//判斷是否為按下 確定 按鈕
			if(result == MediaFileChooser.APPROVE_OPTION){

				DefaultListModel dlmDet = 
					(DefaultListModel) ltDet.getModel();
				//取得歌曲清單之清單方塊的Model物件

				File[] selFiles = mcObj.getSelectedFiles();
				//檔案對話盒選取的所有檔案

				for (File elm : selFiles) dlmDet.addElement(elm);
				//將選取檔案加入歌曲播放清單方塊的Model物件

				ltDet.save(
					"PlayList/" + ((String)ltList.getSelectedValue()) + ".dat");
				//儲存歌曲播放清單方塊的內容
			}
		}	
	};

	//刪除清單方塊的歌曲項目
	ActionListener alDelItem = new ActionListener(){

		//回應動作的方法
		public void actionPerformed(ActionEvent e){

			DefaultListModel dlmDet =
				(DefaultListModel) ltDet.getModel();
			//取得儲存播放清單內容的Model物件

			int[] selIndex = ltDet.getSelectedIndices();
			//取得被選取選項的索引值

			if(selIndex == null | selIndex.length == 0) return;

			int newIndex = 
				selIndex[selIndex.length-1] - selIndex.length + 1;
			//取得最後一個選取選項的索引值

			//移除播放清單內選取的項目
			for(int i=(selIndex.length-1); i>=0; i--){
				dlmDet.remove(selIndex[i]);
			}

			int size = dlmDet.getSize(); //取得清單選項的個數

			ltDet.setSelectedIndex(
				(newIndex<size? newIndex: size-1));
			//設定新的選取項目

			ltDet.save("PlayList/" + 
				((String)ltList.getSelectedValue()) + ".dat");
			//儲存清單方塊內容
		}
	};

	//宣告監聽清單方塊選取事件的監聽器物件
	ListSelectionListener lslList = new ListSelectionListener(){

		//回應選項發生變化的方法
		public void valueChanged(ListSelectionEvent e){

			if(e.getValueIsAdjusting()) return;
			//判斷是否為連續事件, 是則終止執行方法

			PlayList pl = (PlayList) e.getSource();
			//取得引發事件之來源元件

			ltDet.load("PlayList/" + pl.getSelectedValue() + ".dat");
			//載入清單方塊被選取的播放清單之內容
		}
	};

	//宣告監聽設定播放方式之選擇鈕ActionEvent事件的監聽器物件
	ActionListener alPlay = new ActionListener(){

		//回應事件的方法
		public void actionPerformed(ActionEvent e){

			String command = e.getActionCommand();
			//取得動作命令字串

			//依照動作命令字串設定播放方式
			if("播放整個清單".equals(command))
				ltDet.setPlayStatus(SongList.ALL); //設定播放整個清單
			else if("重複播放一首".equals(command))
				ltDet.setPlayStatus(SongList.ONE); //設定播放一首歌
			else if("重複播放整個清單".equals(command))
				ltDet.setPlayStatus(SongList.AUTO);
				//設定重複播放整個清單
		}
	};

	public static void main(String[] args) {
		new MP3Player(); //宣告視窗框架
	}
}