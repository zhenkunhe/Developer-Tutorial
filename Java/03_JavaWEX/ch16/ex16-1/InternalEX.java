import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class InternalEX extends JFrame{

	JDesktopPane dpPane = new JDesktopPane();
	//STEP 1、包含多文件視窗介面的虛擬桌面容器

	//宣告狀態列內顯示視窗狀態的標籤
	JLabel lbCount = new JLabel("0", JLabel.LEFT),
	       lbAlive = new JLabel("0", JLabel.LEFT),
	       lbCur = new JLabel("目前無顯示視窗");

	InternalEX(){

		JButton btnNew = 
			new JButton(new ImageIcon("images/new.gif"));
		//宣告欲加入工具列的按鈕元件, 並以new.gif為顯示圖片

		//宣告並定義回應按鈕MouseEvenet事件的監聽器
		btnNew.addMouseListener(new MouseAdapter(){

			int count = 0;

			public void mouseClicked(MouseEvent e){
	
				JInternalFrame ifObj =
					 new JInternalFrame(null, true, true, true, true);
				//STEP 2、建立可關閉、可變更大小、無標題
				//				   、可最大/小化的內部框架

				dpPane.add(ifObj);
				//STEP 3、將內部框架加入虛擬桌面

				count = dpPane.getAllFrames().length;
				//取得視窗框架的個數

				lbAlive.setText(String.valueOf(count));
				//設定目前開啟視窗的個數

				ifObj.setSize(200, 200); //設定內部框架的大小

				ifObj.setLocation(30*count, 30*count);
				//設定內部框架在虛擬桌面的位置

				ifObj.setTitle("第 " + count + " 個視窗");
				//設定內部框架的標題

				try{
					ifObj.setVisible(true);
					ifObj.setSelected(true);
					//STEP 4、顯示內部框架並設定為選取的
					
					lbCur.setText("[" + ifObj.getTitle() + "] 取得焦點");
					//設定狀態列顯示目前顯示之視窗的標題
				}
				catch(java.beans.PropertyVetoException pve){
					System.out.println(pve.toString());
				}

				//宣告並定義回應內部框架InternalFrameEvenet事件的監聽器
				ifObj.addInternalFrameListener(
					new InternalFrameAdapter(){

					//回應關閉內部框架事件
					public void
						internalFrameClosed(InternalFrameEvent e){

						int num = dpPane.getAllFrames().length;
						//取得內部框架的個數

						lbAlive.setText(String.valueOf(num));
						//設定目前開啟內部框架的個數

						if(num == 0)
							lbCur.setText("目前無顯示視窗");
						//設定狀態列顯示目前顯示之視窗的標題
					}

					//回應內部框架取得焦點的事件
					public void 
						internalFrameActivated(InternalFrameEvent e){

						lbCur.setText(
							"[" + e.getInternalFrame().getTitle() +  "] 取得焦點");
						//設定狀態列顯示目前顯示之視窗的標題
					}
				});
			}
		});

		JToolBar tbTop = new JToolBar(); //宣告工具列物件
		tbTop.add(btnNew); //將按鈕元件加入工具列

		Box bxStatus = new Box(BoxLayout.X_AXIS);
		//宣告包含顯示狀態訊息的JPanel容器

		//將顯示內部框架資訊的標籤加入Box容器
		bxStatus.add(Box.createHorizontalStrut(20));
		bxStatus.add(new JLabel("目前開啟個數 : ", JLabel.RIGHT));
		bxStatus.add(lbAlive);
		bxStatus.add(Box.createHorizontalGlue());
		bxStatus.add(lbCur);
		bxStatus.add(Box.createHorizontalStrut(20));

		Container cp = getContentPane(); //取得內容面版
		cp.add(tbTop, BorderLayout.NORTH); //將工具列加入視窗框架

		cp.add(dpPane);
		//STEP 5、將虛擬桌面加入內容面版

		cp.add(bxStatus, BorderLayout.SOUTH); //將狀態列加入內部框架
		
		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 400);
		setVisible(true);
	}

	public static void main(String args[]) {
		new InternalEX(); //宣告視窗框架物件
	}
}