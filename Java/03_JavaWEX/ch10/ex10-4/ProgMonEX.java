import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class ProgMonEX extends JFrame{

	private int 
		maxValue = 20, //定義進度列的最大值
		popupValue = 1000;
		//設定顯示進度對話盒之等待時間的毫秒數

	private String str = "操作已完成";

	private ProgressMonitor pm; //定義進度監測器

	private JButton btnStart = new JButton("開始");
	//啟動計時器的按鈕

	private JTextField 
		tfTotal = new JTextField(String.valueOf(maxValue), 20),
		tfPopup = new JTextField(String.valueOf(popupValue), 20);

	//定義執行計時的Timer物件
	private Timer timer = 
		new Timer(1000, //每隔1秒發出ActionEvent事件
			new ActionListener(){ //定義回應事件的監聽器物件

				private int value = 0; //起始計次變數

				public void actionPerformed(ActionEvent e){
			
					//判斷使用者是否按下進度對話盒的取消按鈕
					if(pm.isCanceled()){
						pm.close(); //關閉進度監測器
						timer.stop(); //終止計時器
						value = 0; //重設計次變數
					}
				
					if(value < maxValue){ //判斷計次變數是否小於最大值
						pm.setProgress(++value); //設定進度監測器顯示的進度
						pm.setNote(str + ((value * 100) / maxValue) + " %");
						//設定進度對話盒內進度列上方顯示的進度狀態
					}
					else{
						timer.stop(); //終止計時器
						value = 0; //重設計次變數
					}
				}
			});

	ProgMonEX(){

		//以匿名類別的方式定義回應ActionEvent事件的監聽器物件
		btnStart.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){

				maxValue = Integer.valueOf(tfTotal.getText());
				//取得使用者設定的執行秒數

				popupValue = Integer.valueOf(tfPopup.getText());
				//取得顯示進度對話盒之等待時間的毫秒數

				pm = new ProgressMonitor(ProgMonEX.this,
					"進度監測器的運用", str + "0 %", 0, maxValue);
				//宣告進度監測物件

				pm.setMillisToDecideToPopup(popupValue);
				//設定顯示進度對話盒之等待時間的毫秒數

				pm.setProgress(0); //設定進度監測器開始監測的進度值

				timer.start(); //啟動計時器
			}
		});

		//建立供使用者輸入設定值的標籤與文字欄
		JPanel jpSetting = new JPanel(new GridLayout(2, 2, 5, 5));
		jpSetting.add(new JLabel("總執行秒數", JLabel.RIGHT));
		jpSetting.add(tfTotal);
		jpSetting.add(
			new JLabel("顯示進度對話盒的等待毫秒數", JLabel.RIGHT));
		jpSetting.add(tfPopup);

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new BorderLayout(10, 10));
		//設定內容面版水平與垂直間距均為10的
		//BorderLayout物件管理版面

		add(jpSetting); //將元件加入
		add(btnStart, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(4, 4, 4, 4));
		//設定根面版將使用

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(400, 140);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ProgMonEX(); //宣告視窗框架物件
	}
}