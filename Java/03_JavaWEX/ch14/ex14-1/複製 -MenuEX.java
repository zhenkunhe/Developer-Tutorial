import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class MenuEX extends JFrame{

	JTextArea taMsg = new JTextArea();
	//宣告顯示功能表訊息的文字區

	//宣告監聽ActionEvent事件的監聽器物件
	ActionListener alMenuItem = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			taMsg.append(((JMenuItem)e.getSource()).getText() + "\n");
			//輸出選取選項名稱
		}
	};

	MenuEX(){

		JMenu mnFile = new JMenu("檔案(F)"); //宣告功能表
		JMenuItem miExit = 
			new JMenuItem("結束(E)", KeyEvent.VK_E);
		//宣告功能表選項

		mnFile.setMnemonic(KeyEvent.VK_F); //設定記憶鍵

		//定義並宣告監聽ActionEvent事件的監聽器
		miExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});

		miExit.setAccelerator(KeyStroke.getKeyStroke('E',
			 		java.awt.event.InputEvent.SHIFT_MASK, false));
		//建立Shift + E為執行離開選項的加速鍵

		mnFile.add(miExit); //將選項加入功能表

		//宣告功能表選項	
		JMenuItem 
			miItemA = new JMenuItem("選項A(A)", 'A'),
			//宣告選項並指定記憶鍵
			miItemB = new JMenuItem(
				"圖示選項", new ImageIcon("icon/bitc.gif")),
			//宣告顯示圖示與文字的選項
			miItemC = new JMenuItem(new ImageIcon("icon/bitc.gif"));
			//宣告僅顯示圖示的選項

		JMenu mnIconMenu = new JMenu("圖示選項");
		mnIconMenu.add(miItemA); //將選項加入子功能表
		mnIconMenu.add(miItemB);
		mnIconMenu.add(miItemC);

		JMenuItem miTextLeft = new JMenuItem("文在圖左",
									new ImageIcon("icon/bitc.gif")),
							miTextRight = new JMenuItem("文在圖右",
									new ImageIcon("icon/bitc.gif"));
		//宣告功能表選項

		miTextLeft.setHorizontalTextPosition(SwingConstants.LEFT);
		miTextRight.setHorizontalTextPosition(SwingConstants.RIGHT);
		//設定文字與圖示的水平對齊方式

		JMenu mnTextAlignMenu = new JMenu("圖文位置");
		mnTextAlignMenu.add(miTextLeft); //將選項加入子功能表
		mnTextAlignMenu.add(miTextRight);

		JMenuItem miItemText = new JMenuItem("選項一");
		//宣告功能表選項

		miItemText.addActionListener(alMenuItem);
		//註冊回應各選項ActionEvent事件的監聽器物件

		JMenu mnAlignMenu = new JMenu("文字選項");
		mnAlignMenu.add(miItemText); //將選項加入子功能表


		JMenu mnMenuA = new JMenu("樣式A");
		mnMenuA.setMnemonic('A');
		//設定功能表的記憶鍵

		mnMenuA.add(mnIconMenu); //加入子功能表
		mnMenuA.addSeparator(); //加入分隔符號
		mnMenuA.add(mnTextAlignMenu); //加入子功能表
		mnMenuA.add(mnAlignMenu); //加入子功能表

		JMenuBar jmb = new JMenuBar(); //宣告功能表列物件
		setJMenuBar(jmb); //設定視窗框架使用的功能表
		jmb.add(mnFile); //將功能表加入功能表列
		jmb.add(mnMenuA);

		Container cp = getContentPane(); //取得內容面版

		cp.add(new JScrollPane(taMsg));

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new MenuEX(); //宣告視窗框架物件
	}
}