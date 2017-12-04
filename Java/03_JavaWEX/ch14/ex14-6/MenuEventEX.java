import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

import java.util.*;

public class MenuEventEX extends JFrame{

	JTextArea taMsg = new JTextArea();
	//宣告顯示功能表訊息的文字區

	MenuEventEX(){

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

		miExit.setAccelerator( KeyStroke.getKeyStroke('E',
			 		java.awt.Event.SHIFT_MASK, false));
		//建立Shift + E為執行離開選項的快速鍵

		mnFile.add(miExit); //將選項加入功能表

		JMenuItem 	miItemA = new JMenuItem("選項A"),
							miItemB = new JMenuItem("選項B"),
							miItemC = new JMenuItem("選項C");
		//宣告功能表選項	

		JMenu mnMenuEvent = new JMenu("MenuEvent事件");
		mnMenuEvent.add(miItemA); //將選項加入子功能表
		mnMenuEvent.add(miItemB);
		mnMenuEvent.add(miItemC);

		//定義、註冊回應MenuEvent事件的監聽器物件
		mnMenuEvent.addMenuListener(new MenuListener(){
			public void menuSelected(MenuEvent e){
				taMsg.append("呼叫menuSelected()方法\n");
			}

			public void menuDeselected(MenuEvent e){
				taMsg.append("呼叫menuDeselected()方法\n");
			}

			public void menuCanceled(MenuEvent e){
				taMsg.append("呼叫menuCanceled()方法\n");
			}
		});

		JMenuItem 	miItemD = new JMenuItem("選項D"),
							miItemE = new JMenuItem("選項E"),
							miItemF = new JMenuItem("選項F");
		//宣告功能表選項	

		JMenu mnMenuDragMouseEvent = 
			new JMenu("MenuDragMouseEvent事件");
		mnMenuDragMouseEvent.add(miItemD); //將選項加入子功能表
		mnMenuDragMouseEvent.add(miItemE);
		mnMenuDragMouseEvent.add(miItemF);

		//定義、註冊回應MenuDragMouseEvent事件的監聽器物件
		mnMenuDragMouseEvent.addMenuDragMouseListener(
		new MenuDragMouseListener(){
			public void menuDragMouseEntered(
									MenuDragMouseEvent e){
				taMsg.append("menuDragMouseEntered()方法" +
					" 功能表路徑 : " + menuPathToString(e.getPath()) + "\n");
			}

			public void menuDragMouseExited(
										MenuDragMouseEvent e){
				taMsg.append("menuDragMouseExited()方法" +
					" 功能表路徑 : " + menuPathToString(e.getPath()) + "\n");
			}

			public void menuDragMouseDragged(
										MenuDragMouseEvent e){
				taMsg.append("menuDragMouseDragged()方法" +
					" 功能表路徑 : " + menuPathToString(e.getPath()) + "\n");
			}

			public void menuDragMouseReleased(
										MenuDragMouseEvent e){
				taMsg.append("menuDragMouseReleased()方法" +
					" 功能表路徑 : " + menuPathToString(e.getPath()) + "\n");
			}

			//將功能表選項路徑轉換為字串
			private String menuPathToString(
										MenuElement[] mePath){

				String[] strPath = new String[mePath.length];
				//宣告儲存功能表選項路徑的String陣列

				//取得功能表選項路徑之物件的名稱
				for(int i=0; i<mePath.length; i++){

					Component elm = mePath[i].getComponent();
					//取得功能表路徑的物件

					//依照型別, 取得物件名稱與型別
					if(elm.getClass() == JMenuBar.class)
						strPath[i] = "功能表列(JMenuBar)";
					else if(elm.getClass() == JPopupMenu.class)
						strPath[i] = ((JPopupMenu)mePath[i].getComponent()
											).getLabel()	+ "(JPopupMenu)";
					else
						strPath[i] = ((JMenuItem)mePath[i].getComponent()
											).getText() + "(JMenuItem)";
				}

				return Arrays.toString(strPath);
				//傳出功能表選項名稱之路徑字串
			}
		});

		JMenuItem 	miItemG = new JMenuItem("選項G"),
							miItemH = new JMenuItem("選項H"),
							miItemI = new JMenuItem("選項I");
		//宣告功能表選項	

		JMenu mnMenuKeyEvent = 
			new JMenu("出現此選項才會觸發MenuKeyEvent事件");
		mnMenuKeyEvent.add(miItemG); //將選項加入子功能表
		mnMenuKeyEvent.add(miItemH);
		mnMenuKeyEvent.add(miItemI);

		//定義、註冊回應MenuKeyEvent事件的監聽器物件
		mnMenuKeyEvent.addMenuKeyListener(new MenuKeyListener(){
			public void menuKeyTyped(MenuKeyEvent e){
				taMsg.append("menuKeyTyped()方法" + 
					" 功能表路徑 : " + menuPathToString(e.getPath()) + "\n");
			}

			public void menuKeyPressed(MenuKeyEvent e){
				taMsg.append("menuKeyPressed()方法" + 
					" 功能表路徑 : " + menuPathToString(e.getPath()) + "\n");
			}
			
			public void menuKeyReleased(MenuKeyEvent e){
				taMsg.append("menuKeyReleased()方法" +
					" 功能表路徑 : " + menuPathToString(e.getPath()) + "\n");
			}

			//將功能表選項路徑轉換為字串
			private String menuPathToString(MenuElement[] mePath){

				String[] strPath = new String[mePath.length];
				//宣告儲存功能表選項路徑的String陣列

				//取得功能表選項路徑之物件的名稱
				for(int i=0; i<mePath.length; i++){

					Component elm = mePath[i].getComponent();
					//取得功能表路徑的物件

					//依照型別, 取得物件名稱與型別
					if(elm.getClass() == JMenuBar.class)
						strPath[i] = "功能表列(JMenuBar)";
					else if(elm.getClass() == JPopupMenu.class)
						strPath[i] = ((JPopupMenu)mePath[i].getComponent()
											).getLabel()	+ "(JPopupMenu)";
					else
						strPath[i] = ((JMenuItem)mePath[i].getComponent()
											).getText() + "(JMenuItem)";
				}

				return Arrays.toString(strPath);
				//傳出功能表選項名稱之路徑字串
			}
		});

		JMenu mnMenuKeyEventMenu = new JMenu("MenuKeyEvent事件");
		mnMenuKeyEventMenu.add(mnMenuKeyEvent);

		JMenu mnEvent = new JMenu("事件回應示範");

		mnEvent.add(mnMenuEvent); //加入子功能表
		mnEvent.add(mnMenuDragMouseEvent); //加入子功能表
		mnEvent.add(mnMenuKeyEventMenu); //加入子功能表

		JMenuBar jmb = new JMenuBar(); //宣告功能表列物件
		setJMenuBar(jmb); //設定視窗框架使用的功能表
		jmb.add(mnFile); //將功能表加入功能表列
		jmb.add(mnEvent);

		Container cp = getContentPane();

		cp.add(new JScrollPane(taMsg));

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 300);
		setVisible(true);
	}

	public static void main(String args[]) {
		new MenuEventEX(); //宣告視窗框架物件
	}
}