import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;

import java.net.URL;

public class EditorPaneEX extends JFrame {

	JEditorPane epEditor = new JEditorPane(); //文件編輯面版
	JTextArea taMsg = new JTextArea(); //顯示訊息的文字區

	//以執行緒設定文件編輯面版顯示的文件
	class LoadThread extends Thread{

		private URL url; //文件的URL路徑
		private String contentType = null; //文件格式字串

		//建構子
		LoadThread(URL url, String contentType){
			this.url = url;
			this.contentType = contentType;
		}

		public void run(){	 //執行程式

			epEditor.setContentType("text/" + contentType);
			//設定文字編輯面版處理文件的格式

			try{
				epEditor.setPage(url); //設定文字編輯面版顯示的文件
			}
			catch(java.io.IOException ioe){
				System.err.println("無法正常開啟文件 "
															+ ioe.toString());
			}

			taMsg.append("使用Document物件的類別 : "
				+ epEditor.getDocument().getClass().getName() + "\n");
			//顯示文字編輯面版使用之Document物件的類別
		}
	};

	EditorPaneEX(){

		taMsg.setRows(6); //設定文字區的列數
		taMsg.setLineWrap(true); //設定自動斷行
		taMsg.setWrapStyleWord(true); //不在文字間斷行

		epEditor.setEditable(false);
		//設定無法執行編輯, 瀏覽HTML格式文件時, 
		//才能引發HyperlinkEvent事件

		//定義、註冊回應HyperlinkEvent事件的監聽器物件
		epEditor.addHyperlinkListener(new HyperlinkListener(){

			public void hyperlinkUpdate(HyperlinkEvent e){

				HyperlinkEvent.EventType eventType = e.getEventType();
				//取得HyperlinkEvent事件的類型
				
				//判斷HyperlinkEvent事件的類型是否為瀏覽
				if(eventType 
					== HyperlinkEvent.EventType.ACTIVATED){

					taMsg.append("[ACTIVATED] 瀏覽超連結位址為 ["
												+ e.getURL()  + "]\n");
					//顯示瀏覽位址

					LoadThread ltd = new LoadThread(e.getURL(), "html");
					//宣告設定文字編輯面版顯示文件的執行緒物件

					ltd.start();	 //啟動執行緒
				}
				else if(eventType 
					== HyperlinkEvent.EventType.ENTERED){
				//判斷HyperlinkEvent事件的類型
				//是否為滑鼠游標進入超連結範圍

					taMsg.append(
						"[ENTERED] 滑鼠游標進入超連結範圍\n");
				}
				else if(eventType == 
					HyperlinkEvent.EventType.EXITED){
				//判斷HyperlinkEvent事件的類型
				//是否為滑鼠游標離開超連結範圍

					taMsg.append("[EXITED] 滑鼠游標離開超連結範圍\n");
				}
			}
		});

		LoadThread ltd = new LoadThread(
			EditorPaneEX.class.getResource("res/bitc.htm"), "html");
		//宣告執行文字編輯面版載入文件動作的執行緒物件

		ltd.start(); //啟動執行緒

		JToggleButton
			btnHTML = new JToggleButton("顯示HTML文件", true),
			btnRTF = new JToggleButton("顯示RTF文件"),
			btnPlain = new JToggleButton("顯示純文字文件");
		//宣告工具列的JToggleButton按鈕

		ButtonGroup bg = new ButtonGroup(); //宣告按鈕群組
		bg.add(btnHTML); //將按鈕加入群組
		bg.add(btnRTF);
		bg.add(btnPlain);
		
		btnHTML.setActionCommand("html"); //設定動作命令字串
		btnRTF.setActionCommand("rtf");
		btnPlain.setActionCommand("plain");

		//宣告監聽ActionEvent事件的監聽器物件
		ActionListener alLoad = new ActionListener(){

			String strCommand; //儲存動作命令字串

			//回應ActionEvent事件的方法
			public void actionPerformed(ActionEvent e){

				URL url = null;

				strCommand = e.getActionCommand();
				//取得引發事件之按鈕的動作命令字串

				//判斷動作命令字串, 宣告欲載入文件的URL路徑
				if("html".equals(strCommand))
					url =
						EditorPaneEX.class.getResource("res/bitc.htm");
				else if("rtf".equals(strCommand))
					url =
						EditorPaneEX.class.getResource("res/bitc.rtf");
				else if("plain".equals(strCommand))
					url =
						EditorPaneEX.class.getResource("res/bitc.txt");

				LoadThread ltd = new LoadThread(url, strCommand);
				//宣告載入文件的執行緒物件

				ltd.start(); //啟動執行緒
			}
		};

		btnHTML.addActionListener(alLoad);
		btnRTF.addActionListener(alLoad);
		btnPlain.addActionListener(alLoad);
		//註冊回應ActionEvent事件的監聽器物件

		JToolBar tbShow = new JToolBar();
		tbShow.add(btnHTML);
		tbShow.addSeparator(); //加入分隔符號
		tbShow.add(btnRTF);
		tbShow.addSeparator();
		tbShow.add(btnPlain);
		//將按鈕加入工具列

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new BorderLayout(5,  5));
		//設定使用間隔寬度為5的BorderLayout物件配置版面

		cp.add(new JScrollPane(epEditor));
		cp.add(tbShow, BorderLayout.NORTH);
		cp.add(new JScrollPane(taMsg), BorderLayout.SOUTH);

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 400);
		setVisible(true);
	}

	public static void main(String args[]) {
		new EditorPaneEX(); //宣告視窗框架物件
	}
}