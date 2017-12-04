import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class RadioCheckEX extends JFrame{

	RadioCheckEX(){

		JMenu mnFile = new JMenu("檔案(F)"); //宣告功能表

		mnFile.setMnemonic(KeyEvent.VK_F); //設定記憶鍵

		JMenuItem miExit = new JMenuItem("結束(E)", KeyEvent.VK_E);
		//宣告功能表選項	

		//定義並宣告監聽ActionEvent事件的監聽器
		miExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});

		mnFile.add(miExit); //將選項加入功能表

		JCheckBoxMenuItem 
			cbmiItemA = new JCheckBoxMenuItem("選項 1", 
									new ImageIcon("icon\\bitc.gif")),
				//宣告同時顯示文字與圖示的核取方塊功能表選項				
			cbmiItemB = new JCheckBoxMenuItem(
									new ImageIcon("icon\\bitc.gif")),
				//宣告以顯示圖示的核取方塊功能表選項
			cbmiItemC = new JCheckBoxMenuItem("選項 3");
				//宣告顯示文字的核取方塊功能表選項

		JMenu mnCheckBox = new JMenu("核取方塊選項");
		mnCheckBox.add(cbmiItemA); //將核取方塊選項加入功能表
		mnCheckBox.add(cbmiItemB);
		mnCheckBox.add(cbmiItemC);

		JRadioButtonMenuItem 
			rbmiItemA = new JRadioButtonMenuItem("選項 1", 
									new ImageIcon("icon\\bitc.gif"), true),
				//宣告同時顯示文字與圖示的選擇鈕功能表選項
			rbmiItemB = new JRadioButtonMenuItem(
									new ImageIcon("icon\\bitc.gif")),
				//宣告顯示圖示的選擇鈕功能表選項
			rbmiItemC = new JRadioButtonMenuItem("選項 3");
				//宣告同時顯示文字的選擇鈕功能表選項

		JMenu mnRadioBtn = new JMenu("選擇鈕選項");
		mnRadioBtn.add(rbmiItemA); //將選擇鈕選項加入功能表
		mnRadioBtn.add(rbmiItemB);
		mnRadioBtn.add(rbmiItemC);

		ButtonGroup bgRbmi = new ButtonGroup(); //宣告按鈕群組物件
		bgRbmi.add(rbmiItemA); //將選擇鈕選項加入按鈕群組
		bgRbmi.add(rbmiItemB);
		bgRbmi.add(rbmiItemC);

		JMenuBar jmb = new JMenuBar(); //宣告功能表列物件
		setJMenuBar(jmb); //設定視窗框架使用的功能表
		jmb.add(mnFile); //將功能表加入功能表列
		jmb.add(mnCheckBox);
		jmb.add(mnRadioBtn);

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new RadioCheckEX(); //宣告視窗框架物件
	}
}