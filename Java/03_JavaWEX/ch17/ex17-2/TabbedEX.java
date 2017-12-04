import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

public class TabbedEX extends JFrame {

	JLabel lbImage; //宣告容納圖片的標籤物件

	JTabbedPane tpPanel = new JTabbedPane();
	//宣告頁籤面版

	String strSel = "目前選取 : ", strTotal = "頁次個數 : ";

	JLabel lbSelected = new JLabel(strSel),
				lbTotal = new JLabel(strTotal);
	//宣告顯示目前選取頁籤與總頁次個數的標籤

	TabbedEX(){

		lbImage = 
			new JLabel(new ImageIcon("res\\butterfly.jpg"));
		//宣告容納圖片的標籤
		
		tpPanel.addTab(
			"頁次 " + tpPanel.getTabCount(),	null, lbImage);
		//將頁次加入頁籤面版

		lbSelected.setText(
			strSel + tpPanel.getTitleAt(tpPanel.getSelectedIndex()));
		//設定標籤顯示被選取頁次的標題名稱

		lbTotal.setText(
			strTotal + tpPanel.getTabCount());
		//設定標籤顯示目前開啟的總頁次個數

		//註冊監聽頁籤面版ChangeEvent事件的監聽器物件
		tpPanel.addChangeListener(new ChangeListener(){

			//回應頁次狀態的變化
			public void stateChanged(ChangeEvent e){

				JTabbedPane tpSource = (JTabbedPane) e.getSource();
				//引發頁次ChangeEvent事件的來源物件

				int selIndex = tpSource.getSelectedIndex();
				//取得目前頁籤面版選取的頁次

				if(selIndex != -1){
					lbSelected.setText(
						strSel + tpSource.getTitleAt(selIndex));
					//取得目前選取的頁次之標題字串
				}
				else
					lbSelected.setText(strSel + "無");
					//設定目前無選取頁次
			}
		});

		//註冊回應頁籤面版回應ContainerEvent事件的監聽器物件
		tpPanel.addContainerListener(new ContainerListener(){

			//回應將元件加入面版的動作
			public void componentAdded(ContainerEvent e){
				lbTotal.setText(
					strTotal + tpPanel.getTabCount());
				//設定標籤顯示目前頁次的總個數
			}

			//回應自面版移除元件的動作
			public void componentRemoved(ContainerEvent e){
				lbTotal.setText(
					strTotal + tpPanel.getTabCount());
				//設定標籤顯示目前頁次的總個數
			}
		});

		JButton btnAdd = new JButton("新增頁次");
		//宣告新增頁次的指令按鈕

		//宣告、註冊回應ActionEvent事件的監聽器物件
		btnAdd.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){
				lbImage = new JLabel(
					new ImageIcon("res\\butterfly.jpg"));
				//宣告容納圖片的標籤

				tpPanel.addTab(
					"頁次 " + tpPanel.getTabCount(), null, lbImage);
				//將頁次加入頁籤面版內

				tpPanel.setSelectedIndex((tpPanel.getTabCount()-1));
				//設定頁籤面版選取最後一個頁次
			}
		});

		JButton btnRemove = new JButton("移除頁次");
		//宣告新增頁次的指令按鈕

		//宣告、註冊回應ActionEvent事件的監聽器物件
		btnRemove.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				if(tpPanel.getTabCount() == 0) return;
				//若目前頁籤面版內沒有任何頁次, 則終止執行方法

				tpPanel.remove(tpPanel.getSelectedIndex());
				//移除目前頁籤面版選取的頁次
			}
		});

		//宣告回應控制標籤位置選擇鈕
		//之AcitonEvent事件的監聽器物件
		ActionListener alPlace = new ActionListener(){

			public void actionPerformed(ActionEvent e){

				String strAction = (String) e.getActionCommand();
				//取得代表引發事件之動作命令字串

				//判斷動作命令字串, 並設定對應之標籤位置
				if("Top".equals(strAction))
					tpPanel.setTabPlacement(JTabbedPane.TOP);
				else if("Bottom".equals(strAction))
					tpPanel.setTabPlacement(JTabbedPane.BOTTOM);
				else if("Left".equals(strAction))
					tpPanel.setTabPlacement(JTabbedPane.LEFT);
				else if("Right".equals(strAction))
					tpPanel.setTabPlacement(JTabbedPane.RIGHT);
			}
		};

		JRadioButton rbTop = new JRadioButton("Top", true),
								rbBottom  = new JRadioButton("Bottom"),
								rbLeft = new JRadioButton("Left"),
								rbRight = new JRadioButton("Right");
		//宣告控制頁次放置位置的選擇鈕

		rbTop.addActionListener(alPlace);
		rbBottom.addActionListener(alPlace);
		rbLeft.addActionListener(alPlace);
		rbRight.addActionListener(alPlace);
		//註冊回應選擇鈕ActionEvent事件的監聽器物件

		ButtonGroup bgPlace = new ButtonGroup();
		bgPlace.add(rbTop);
		bgPlace.add(rbBottom);
		bgPlace.add(rbLeft);
		bgPlace.add(rbRight);
		//將選擇鈕將入按鈕群組

		//宣告回應控制標籤配置方式選擇鈕
		//之AcitonEvent事件的監聽器物件
		ActionListener alLayout = new ActionListener(){
			public void actionPerformed(ActionEvent e){

				String strAction = (String) e.getActionCommand();
				//取得引發AcitonEvent事件之動作命令字串

				//判斷動作命令字串並執行對應的標籤配置設定
				if("Wrap".equals(strAction))
					tpPanel.setTabLayoutPolicy(
						JTabbedPane.WRAP_TAB_LAYOUT);
				else if("Scroll".equals(strAction))
					tpPanel.setTabLayoutPolicy(
						JTabbedPane.SCROLL_TAB_LAYOUT );
			}
		};

		JRadioButton rbWrap = new JRadioButton("Wrap", true),
								rbScroll = new JRadioButton("Scroll");
		//宣告回應控制標籤配置方式選擇鈕

		rbWrap.addActionListener(alLayout);
		rbScroll.addActionListener(alLayout);
		//註冊監聽標籤配置方式選擇鈕的ActionEvent事件

		ButtonGroup bgLayout = new ButtonGroup();
		bgLayout.add(rbWrap);
		bgLayout.add(rbScroll);
		//將選擇鈕將入按鈕群組

		Box bxSetting = new Box(BoxLayout.Y_AXIS);
		bxSetting.add(Box.createVerticalStrut(15));
		bxSetting.add(rbTop);
		bxSetting.add(rbBottom);
		bxSetting.add(rbLeft);
		bxSetting.add(rbRight);
		bxSetting.add(Box.createVerticalStrut(15));
		bxSetting.add(rbWrap);
		bxSetting.add(rbScroll);
		//將選擇鈕將入Box容器內

		JPanel jpBtn = new JPanel(new GridLayout(1, 4, 5, 5));
		jpBtn.add(btnAdd);
		jpBtn.add(btnRemove);
		//將新增、移除頁次的指令按鈕加入面版

		JPanel jpLabel = new JPanel(new GridLayout(1, 2, 5, 5));
		jpLabel.add(lbSelected);
		jpLabel.add(lbTotal);
		//將顯示選取頁次與總頁次個數的標籤加入面版

		Container cp = getContentPane(); //取得內容面版
		cp.add(tpPanel); //將面版加入內容面版
		cp.add(jpBtn, BorderLayout.NORTH);
		cp.add(jpLabel, BorderLayout.SOUTH);
		cp.add(bxSetting, BorderLayout.EAST);

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//設定根面版使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
	}

	public static void main(String args[]) {
		new TabbedEX(); //宣告視窗框架物件
	}
}