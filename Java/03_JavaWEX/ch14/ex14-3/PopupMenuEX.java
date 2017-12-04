import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class PopupMenuEX extends JFrame{

	JPopupMenu pm = new JPopupMenu();
	//宣告蹦現功能表

	PopupMenuEX(){

		JMenuItem miExit = 
				new JMenuItem("結束(E)", KeyEvent.VK_E),
			miTextIcon = new JMenuItem(
				"圖示選項", new ImageIcon("icon/bitc.gif"));
		//宣告功能表選項

		JCheckBoxMenuItem cbmi =
			new JCheckBoxMenuItem("核取方塊選項");
		//宣告核取方塊選項

		//定義並宣告監聽ActionEvent事件的監聽器
		miExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});

		pm.add(miTextIcon); //將選項加入功能表
		pm.add(cbmi);
		pm.add(new JPopupMenu.Separator());
		pm.add(miExit);

		//註冊監聽MouseEvent事件的監聽器物件,
		//回應時, 將顯示蹦現功能表
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getButton() == MouseEvent.BUTTON3){
					pm.show(PopupMenuEX.this, e.getX(), e.getY());
					//在滑鼠游標位置顯示蹦現功能表
				}
			}
		});

		//定義、註冊回應蹦現功能表PopupMenuEvent事件的監聽器物件
		//並將訊息輸出至命令提示字元視窗
		pm.addPopupMenuListener(new PopupMenuListener(){

			//回應顯示蹦現功能表
			public void 
				popupMenuWillBecomeVisible(PopupMenuEvent e){

				System.out.println(
					"呼叫popupMenuWillBecomeVisible()方法");
			}

			//回應隱藏蹦現功能表
			public void 
				popupMenuWillBecomeInvisible(PopupMenuEvent e){

				System.out.println(
					"呼叫popupMenuWillBecomeInvisible()方法");
			}

			//回應取消蹦現功能表
			public void 
				popupMenuCanceled(PopupMenuEvent e){

				System.out.println(
					"呼叫popupMenuCanceled()方法");
			}
		});

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new PopupMenuEX(); //宣告視窗框架物件
	}
}