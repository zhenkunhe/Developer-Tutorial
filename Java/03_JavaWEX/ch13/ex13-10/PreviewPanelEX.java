import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.colorchooser.*;
//引用處理顏色選取的ColorSelectionModel類別

import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件
import java.io.*;

public class PreviewPanelEX extends JFrame{

	JLabel lbColor = 
		new JLabel("設定背景/前景顏色", JLabel.CENTER);
	//宣告背景顏色與前景顏色設定的標籤

	JButton btnUserColor = new JButton("設定前景/背景顏色");
	//宣告呼叫顏色對話盒的按鈕

	PreviewPanelEX(){

		lbColor.setOpaque(true); //設定標籤背景不是透明的

		//註冊回應按鈕ActionEvent事件的監聽器
		btnUserColor.addActionListener(new ActionListener(){

			PreviewPanel ppPreview; //自訂的顏色預覽面版

			//回應ActionEvent事件的方法
			public void actionPerformed(ActionEvent e){

				JColorChooser ccUser = new JColorChooser();
				//宣告顏色選擇器

				ppPreview = new PreviewPanel(ccUser, 
											lbColor.getBackground(),
											lbColor.getForeground());
				//宣告自訂的顏色預覽面版, 
				//傳入使用的顏色選擇器,
				//且傳入lbColor標籤的前景顏色與背景顏色做為預設值

				ccUser.setPreviewPanel(ppPreview);
				//設定顏色選擇器使用的顏色預覽面版

				ccUser.getSelectionModel().setSelectedColor(
									lbColor.getBackground());
				//設定起始選取標籤的背景顏色

				JDialog dialog = JColorChooser.createDialog(
					PreviewPanelEX.this, "取得標籤背景/前景顏色",
					true, ccUser, new ColorActionListener(), null);
				//建立顏色對話盒,
				//並以ccUser做為對話盒的內容,
				//且由ColorActionListener物件回應對話盒的 確定 按鈕			

				dialog.setVisible(true); //顯示對話盒
			}

			//定義回應顏色對話盒內 確定 按鈕之
			//ActionEvent事件的ColorActionListener監聽器物件
			class ColorActionListener implements ActionListener {
				public void actionPerformed(ActionEvent e){			
					lbColor.setBackground(
						ppPreview.getSelectedBackground());
					lbColor.setForeground(
						ppPreview.getSelectedForeground());
					//從自訂預覽面版取得前景顏色與背景顏色的設定值
				}
			}
		});	

		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new BorderLayout(5, 5));

		cp.add(lbColor); //將包含按鈕的JPanel容器加入內容面版
		cp.add(btnUserColor, BorderLayout.SOUTH); //加入顯示訊息的標籤
		
		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//設定根面版四周將使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(150, 120);
		setVisible(true);
	}

	public static void main(String args[]) {
		new PreviewPanelEX(); //宣告視窗框架物件
	}
}

//定義顏色對話盒的自訂預覽面版
class PreviewPanel extends JPanel {

	private JLabel lbPreview = 
						new JLabel("預覽顏色設定", JLabel.CENTER),
						//顯示顏色設定的標籤
					lbOrgin = 
						new JLabel("顏色原始設定", JLabel.CENTER);
						//顯示原始顏色設定的標籤	

	private JToggleButton
		tbBack = new JToggleButton("背景", true),
		tbFore = new JToggleButton("前景");
	//控制設定前景顏色或背景顏色的切換按鈕

	PreviewPanel(JColorChooser chooser, 
		Color background, Color foreground){ //建構子

		//取得顏色選擇器的ColorSelectionModel物件,
		//並註冊監聽ChangeEvent事件的監聽器物件
		chooser.getSelectionModel().addChangeListener(
			new ChangeListener(){

				//回應選取顏色改變行為的方法
				public void stateChanged(ChangeEvent e){

					Color selColor = ((ColorSelectionModel)
								e.getSource()).getSelectedColor();
					//取得使用者選取的顏色

					//判斷背景切換按鈕是否被選取
					if(tbBack.isSelected()){
						lbPreview.setBackground(selColor);
						//將選取顏色設定為標籤的背景顏色
					}
					else{
						lbPreview.setForeground(selColor);
						//將選取顏色設定為標籤的前景顏色
					}
				}
			}
		);

		lbPreview.setOpaque(true); //設定標籤不透明
		lbPreview.setBackground(background); //設定背景顏色
		lbPreview.setForeground(foreground); //設定前景顏色
		lbPreview.setBorder(new CompoundBorder(
			new javax.swing.plaf.metal.MetalBorders.TextFieldBorder(),
			new EmptyBorder(0, 5, 0, 5)));
		//設定標籤使用組合框線, 外框為Swing預設的文字欄框線,
		//內框為空白框線

		lbOrgin.setOpaque(true); //設定標籤不透明
		lbOrgin.setBackground(background); //設定背景顏色
		lbOrgin.setForeground(foreground); //設定前景顏色
		lbOrgin.setBorder(new CompoundBorder(
			new javax.swing.plaf.metal.MetalBorders.TextFieldBorder(),
			new EmptyBorder(0, 5, 0, 5)));
		//設定標籤使用組合框線, 外框為Swing預設的文字欄框線,
		//內框為空白框線

		ButtonGroup bg = new ButtonGroup(); //宣告按鈕群組
		bg.add(tbBack); //將按鈕加入按鈕群組
		bg.add(tbFore);

		//宣告容納切換按鈕的Box容器
		Box bxTButton = new Box(BoxLayout.Y_AXIS);
		bxTButton.add(tbBack); //加入切換按鈕
		bxTButton.add(Box.createVerticalStrut(5));
		bxTButton.add(tbFore);

		JPanel jpColor = new JPanel(new GridLayout(1, 2, 2, 2));
		jpColor.add(lbOrgin); //加入顯示顏色的標籤
		jpColor.add(lbPreview);

		setLayout(new BorderLayout(5, 5));
		//設定面版使用配置管理員

		add(bxTButton, BorderLayout.EAST); //將元件加入面版
		add(jpColor);

		setBorder(new EmptyBorder(2, 2, 2, 2));
		//設定面版使用空白框線
		//請注意! 若未設定, 將無法顯示自訂預覽面版
	}

	//取得預覽顏色面版顯示的背景顏色
	public Color getSelectedBackground(){
		return lbPreview.getBackground();
	}

	//取得預覽顏色面版顯示的前景顏色
	public Color getSelectedForeground(){
		return lbPreview.getForeground();
	}
}