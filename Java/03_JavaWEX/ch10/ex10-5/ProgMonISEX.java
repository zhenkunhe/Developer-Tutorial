import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ProgMonISEX extends JFrame{

	final static String FILE_NAME = "data\\java2.psd";
	//宣告示範載入動作的檔案

	File file = new File(FILE_NAME);
	//宣告欲載入檔案的File物件

	JButton btnStart = new JButton("開始讀取檔案");
	//啟動計時器的按鈕

	ProgMonISEX(){

		//註冊回應按鈕ActionEvent事件的監聽器物件
		btnStart.addActionListener(new ActionListener(){

			ProgressMonitorInputStream pmis;
			//輸入串流進度監測器

			ProgressMonitor pm; //進度監測器
			FileInputStream fisInput; //檔案輸入串流

			//宣告執行檔案讀取的執行緒
			//若不使用執行緒, 將無法顯示進度列
			Thread readFile = new Thread(){

				double size = 0; //計算已載入的位元數

				public void run(){
					
					try{
						while(pmis.read() != -1){
							++size; //計算已經載入的位元組數

							if((size%10000) == 0)
								pm.setNote("已完成 " + 
									String.valueOf((new Double(size *100 
									  / file.length())).intValue()) + "%");
								//顯示檔案載入進度的百分比
						}
					}
					catch(IOException ioe){
						ioe.printStackTrace();
					}
					finally{
						try{
							if(pmis != null) pmis.close();
							//關閉進度監視輸入串流
						}
						catch(IOException ioe){ }
					}
				}
			};

			//回應ActionEvent事件的方法
			public void actionPerformed(ActionEvent e){

				if(file == null) return;
				//判斷file物件是否為null, 是則終止執行

				try{
					fisInput = new FileInputStream(file);
					//宣告檔案輸入串流
				}
				catch(FileNotFoundException fnfe){
					fnfe.printStackTrace();
					return;
				}
				
				pmis = new ProgressMonitorInputStream(
						ProgMonISEX.this, "檔案讀取中...", fisInput);
				//宣告監看檔案載入的輸入串流監測器

				pm = pmis.getProgressMonitor();
				//取得進度監測器

				pm.setMillisToDecideToPopup(10);
				//設定開始監測程式之時間的毫秒數

				readFile.start(); //啟動執行緒
			}
		});
	
		Container cp = getContentPane();
		//取得內容面版

		cp.setLayout(new BorderLayout(10, 10));
		//設定內容面版水平與垂直間距均為10的
		//BorderLayout物件管理版面

		add(new JLabel("讀取 [" + FILE_NAME + "]")); //將元件加入
		add(btnStart, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(4, 4, 4, 4));
		//設定根面版將使用

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(200, 100);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ProgMonISEX(); //宣告視窗框架物件
	}
}