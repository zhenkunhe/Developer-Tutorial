import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.EmptyBorder;
import javax.swing.colorchooser.*;
//引用處理顏色選取的ColorSelectionModel類別

import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件
import java.io.*;
import java.util.*;

public class ColorPanelEX extends JFrame{

	JLabel lbColor = new JLabel("灰階設定", JLabel.CENTER);
	//宣告顯示灰色顏色選取結果的標籤

	JButton btnBGColor = new JButton("選取灰階顏色");
	//宣告呼叫顏色對話盒的按鈕

	ColorPanelEX(){

		lbColor.setOpaque(true); //設定標籤背景不透明
		lbColor.setBackground(Color.white); //設定背景顏色為白色
		lbColor.setForeground(Color.blue); //設定背景顏色為藍色

		//註冊回應按鈕ActionEvent事件的監聽器
		btnBGColor.addActionListener(new ActionListener(){

			JColorChooser ccUser;
			//宣告顏色選擇器

			public void actionPerformed(ActionEvent e){

				ccUser = new JColorChooser();
				//宣告顏色選擇器

				ccUser.getSelectionModel().setSelectedColor(
					lbColor.getBackground());
				//設定起始選取標籤的背景顏色

				AbstractColorChooserPanel[] 
					accpGray = { new GrayScalePanel() };
				ccUser.setChooserPanels(accpGray);
				//設定顏色選擇器所有使用的顏色選擇面版,
				//以上兩行敘述將產生移除舊面版, 
				//僅使用自訂灰階顏色選取面版的效果

				JDialog dialog = JColorChooser.createDialog(
					ColorPanelEX.this, 
					"選取灰階顏色",
					true, ccUser, 
					new ColorActionListener(), null);
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
						ccUser.getSelectionModel().getSelectedColor());
					//從顏色選擇器的顏色選擇模型物件, 
					//取得選取的灰階顏色
				}
			}
		});

		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new BorderLayout(5, 5));

		cp.add(lbColor); //將包含按鈕的JPanel容器加入內容面版
		cp.add(btnBGColor, BorderLayout.SOUTH);
		//加入顯示訊息的標籤
		
		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//設定根面版四周將使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(150, 120);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ColorPanelEX(); //宣告視窗框架物件
	}
}

//以繼承AbstractColorChooserPanel類別自訂灰階顏色選擇面版
class GrayScalePanel extends AbstractColorChooserPanel {

	private JSlider slGray = new JSlider(0, 255);
	//宣告控制灰階顏色的拉動桿

	private JLabel lbRGB = new JLabel();
	//顯示選取顏色之RGB值

	GrayScalePanel(){
		super(); //呼叫基礎類別的建構子

		slGray.setMajorTickSpacing(51); //設定主要刻度間距
		slGray.setMinorTickSpacing(17); //設定次要的刻度間距
		slGray.setPaintTicks(true); //設定顯示刻度

		Hashtable labelTable = new Hashtable();
		//宣告建立拉動桿刻度標籤的雜湊表

		for(int i=0; i<6; i++){
			labelTable.put(new Integer(i*51), 
									 new JLabel(String.valueOf(i*51)));
			//將刻度對應的標籤放入雜湊表
		}

		slGray.setLabelTable(labelTable);
		//設定拉動桿使用放置標籤的雜湊表

		slGray.setPaintLabels(true); //設定顯示刻度的數字標籤

		//註冊監聽ChangeEvent事件的
		slGray.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent ce){
				int selValue = ((JSlider)ce.getSource()).getValue();
				//取得拉動桿目前的刻度

				getColorSelectionModel().setSelectedColor(
							new Color(selValue, selValue,selValue));
				//設定顏色選擇模型的選取顏色

				lbRGB.setText("(" + selValue + ", " 
					+ selValue + ", " + selValue + ")");
			}
		});
	}

	protected void buildChooser(){ //建立顏色選擇面版

		setLayout(new BorderLayout(5, 5));
		//設定面版的版面配置管理員

		add(slGray); //將拉動桿加入面版
		add(new JLabel("白色"), BorderLayout.EAST);
		add(new JLabel("黑色"), BorderLayout.WEST);
		add(new JLabel("請拖曳拉桿調整灰階顏色", JLabel.CENTER),
						BorderLayout.NORTH);
		add(lbRGB, BorderLayout.SOUTH);	

		setBorder(new EmptyBorder(20, 20, 20, 20));
		//設定面版使用寬度為20的空白框線

		Color selColor = getColorFromModel();
		//取得顏色選取模型物件選取的顏色

		slGray.setValue(selColor.getBlue());
		//設定拉動桿的位置, 僅以顏色的藍光值做設定

		lbRGB.setText("(" + selColor.getBlue() + ", " 
									+ selColor.getBlue() + ", "
									+ selColor.getBlue() + ")");
		//顯示目前灰階顏色的設定值

		lbRGB.setHorizontalAlignment(JLabel.CENTER);
		//設定標籤文字的水平對齊方式
	}

	public void updateChooser(){ //更新顏色選擇面版的值

		Color selColor = getColorFromModel();
		//取得顏色選取模型物件選取的顏色

		slGray.setValue(selColor.getBlue());
		//設定拉動桿的位置, 僅以顏色的藍光值做設定
	}

	//取得顏色選擇面版的名稱
	public String getDisplayName(){
		return "灰階選取";
	}

	//取得顏色選擇面版的圖示, 未使用圖示故傳回null
	public Icon getLargeDisplayIcon(){ return null; }
	public Icon getSmallDisplayIcon(){ return null; }
}