import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.colorchooser.*;
//引用處理顏色選取的ColorSelectionModel類別

import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件
import java.io.*;

public class UserColorDlgEX extends JFrame{

	JLabel lbColor = new JLabel("設定結果", JLabel.CENTER);
	//宣告背景顏色與前景顏色設定的標籤

	JButton btnUserColor = new JButton("設定字型、前景/背景顏色");
	//宣告呼叫顏色對話盒的按鈕

	UserColorDlgEX(){

		lbColor.setFont(new Font("華康細圓體", Font.PLAIN, 12));
		//設定標籤使用的字型

		lbColor.setOpaque(true); //設定標籤背景不是透明的

		//註冊回應按鈕ActionEvent事件的監聽器
		btnUserColor.addActionListener(
			new ActionListener(){

			FontColorDialog fcd;
			//宣告自訂的字型、背景/前景顏色設定對話盒

			//回應ActionEvent事件的方法
			public void actionPerformed(ActionEvent e){

				fcd = new FontColorDialog(
													UserColorDlgEX.this,
													lbColor.getFont(),
													lbColor.getBackground(),
													lbColor.getForeground());
				//以lbColor標籤的字型、前景/背景顏色建立對話盒


				fcd.addOKActionListener(new ColorActionListener());
				//註冊監聽對話盒內 確定 按鈕ActionEvent事件的監聽器物件

				fcd.setVisible(true); //設定顯示對話盒

			}

			//定義回應顏色對話盒內 確定 按鈕之
			//ActionEvent事件的ColorActionListener監聽器物件
			class ColorActionListener implements ActionListener {
				public void actionPerformed(ActionEvent e){

					lbColor.setBackground(
						fcd.getSelectedBackground());
					lbColor.setForeground(
						fcd.getSelectedForeground());
					//以使用者選取的前景顏色與背景顏色設定lbColor標籤

					lbColor.setFont(fcd.getFont());
					//以使用者選取的字型設定lbColor標籤
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
		setSize(200, 120);
		setVisible(true);
	}

	public static void main(String args[]) {
		new UserColorDlgEX(); //宣告視窗框架物件
	}
}

//以繼承JDialog類別的方式
//定義可設定字型與前景/背景顏色的FontColorDialog類別
class FontColorDialog extends JDialog {

	Integer[] fontSize = 
		{8, 9 ,10, 11, 12, 13, 14, 15, 16, 17,  18, 20};
	//宣告建立字型大小對話盒選項的整數陣列

	JComboBox cmbFont, cmbSize = new JComboBox(fontSize);
	//宣告選取字型與字型大小的組合方塊

	PreviewPanel pp; //宣告自訂的預覽設定面版

	JButton btnOK = new JButton("確定"),
				  btnCancel = new JButton("取消"),
				  btnReset = new JButton("重設");
	//宣告對話盒的 確定 與 取消 按鈕

	Font oldFont; //字型的原設定值
	Color oldBackground, oldForeground;
	//前景/背景顏色的原設定值

	public FontColorDialog(Frame parent, Font font, 
											Color background, Color foreground){

		super(parent, "選取字型/前景顏色/背景顏色", true);
		//呼叫基礎類別的建構子

		oldFont = font;
		oldBackground = background;
		oldForeground = foreground;
		//儲存字型、前景/背景顏色的原設定值

		Font[] fonts = 
			GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		//取得系統提供的所有字型

		DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
		//宣告儲存組合方塊選項內容的DefaultComboBoxModel物件

		//將系統字型的名稱加入組合方塊
		for(Font elm : fonts)
			dcbm.addElement(elm.getName());

		cmbFont = new JComboBox(dcbm);
		//以儲存選項內容的DefaultComboBoxModel物件建立組合方塊

		JColorChooser cc = new JColorChooser();
		//宣告顏色選擇器

		pp = new PreviewPanel(cc, font, background, foreground);
		//宣告自訂的預覽設定面版

		cc.setPreviewPanel(pp); //設定顏色選擇器的預覽設定面版

		Font fnCur = pp.lbPreview.getFont();
		//取得預覽設定面版內lbPreview標籤使用的字型

		cmbFont.setSelectedItem(fnCur.getName());
		cmbSize.setSelectedItem(fnCur.getSize());
		//依照目前設定的字型, 設定組合方塊的選取值

		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new BorderLayout(5, 5));
		//設定內容面版使用BorderLayout配置版面

		JPanel jpFont = new JPanel(new GridLayout(1, 2, 10, 10));
		//宣告容納字型與字型大小設定之組合方塊的面版

		jpFont.add(cmbFont); //將組合方塊加入面版
		jpFont.add(cmbSize);
		jpFont.setBorder(new EmptyBorder(5, 5, 5, 5));

		//宣告容納指令按鈕的Box容器, 並加入指令按鈕
		Box bxButton = new Box(BoxLayout.X_AXIS);
		bxButton.add(Box.createHorizontalGlue());
		bxButton.add(btnOK);
		bxButton.add(Box.createHorizontalStrut(15));
		bxButton.add(btnCancel);
		bxButton.add(Box.createHorizontalStrut(15));
		bxButton.add(btnReset);
		bxButton.add(Box.createHorizontalGlue());

		bxButton.setBorder(new EmptyBorder(5, 5, 5, 5));

		//將各容器與選擇器加入對話盒的內容面版
		cp.add(jpFont, BorderLayout.NORTH);
		cp.add(cc);
		cp.add(bxButton, BorderLayout.SOUTH);

		setSize(500, 450); //設定對話盒的大小

		//註冊回應字型組合方塊ItemEvent事件的監聽器
		cmbFont.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent e){

				//判斷發出事件之選項的狀態是否為被選取
				if(e.getStateChange() == ItemEvent.SELECTED){
					String strFont = (String) e.getItem();
					//取得引發事件的選項

					Font fnNew = new Font(strFont, Font.PLAIN,
						(Integer)cmbSize.getModel().getSelectedItem());
					//以字型名稱與字型大小組合方塊的選取值宣告字型物件

					pp.lbPreview.setFont(fnNew);
					//設定預覽設定面版內lbPreview標籤使用的字型
				}
			}
		});

		//註冊回應字型大小組合方塊ItemEvent事件的監聽器
		cmbSize.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent e){

				//判斷發出事件之選項的狀態是否為被選取
				if(e.getStateChange() == ItemEvent.SELECTED){
					String strFont = (String) cmbFont.getModel().getSelectedItem();
					//取得字型對話盒目前選取的字型名稱

					Font fnNew = 
						new Font(strFont, Font.PLAIN,	(Integer)e.getItem());
					//以字型名稱與引發事件之
					//字型大小組合方塊的選項建立字型

					pp.lbPreview.setFont(fnNew);
					//設定預覽設定面版內lbPreview標籤使用的字型
				}
			}
		});

		//註冊回應 確定 指令按鈕ActionEvent事件的監聽器物件
		btnOK.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setVisible(false); //設定不顯示對話盒
			}
		});

		//註冊回應 取消 指令按鈕ActionEvent事件的監聽器物件
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pp.lbPreview.setFont(oldFont);
				pp.lbPreview.setBackground(oldBackground);
				pp.lbPreview.setForeground(oldForeground);
				//恢復預覽設定面版內lbPreview標籤
				//使用的字型與前景/背景顏色

				setVisible(false); //設定不顯示對話盒
			}
		});

		//註冊回應 重設 指令按鈕ActionEvent事件的監聽器物件
		btnReset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pp.lbPreview.setFont(oldFont);
				pp.lbPreview.setBackground(oldBackground);
				pp.lbPreview.setForeground(oldForeground);
				//恢復預覽設定面版內lbPreview標籤
				//使用的字型與前景/背景顏色

				cmbFont.setSelectedItem(oldFont.getName());
				cmbSize.setSelectedItem(oldFont.getSize());
				//恢復字型與字型大小組合方塊的選取值
			}
		});
	}

	//註冊監聽 確定 按鈕ActionEvent事件的監聽器物件
	public void addOKActionListener(ActionListener al){
		btnOK.addActionListener(al);
		//註冊監聽ActionEvent事件的監聽器物件
	}

	//取得預覽設定面版顯示的背景顏色
	public Color getSelectedBackground(){
		return pp.lbPreview.getBackground();
	}

	//取得預覽設定面版顯示的前景顏色
	public Color getSelectedForeground(){
		return pp.lbPreview.getForeground();
	}

	//取得預覽設定面版顯示的字型
	public Font getFont(){
		return pp.lbPreview.getFont();
	}

	//定義顏色對話盒的自訂預覽設定面版
	class PreviewPanel extends JPanel {

		private JLabel lbPreview = 
							new JLabel("設定值", JLabel.CENTER),
							//顯示顏色設定的標籤
						lbOrgin = 
							new JLabel("原始值", JLabel.CENTER);
							//顯示原始顏色設定的標籤	

		private JToggleButton
			tbBack = new JToggleButton("背景", true),
			tbFore = new JToggleButton("前景");
		//控制設定前景顏色或背景顏色的切換按鈕

		PreviewPanel(JColorChooser chooser,  Font font,
			Color background, Color foreground){ //建構子

			lbPreview.setOpaque(true); //設定標籤不透明
			lbPreview.setBackground(background); //設定背景顏色
			lbPreview.setForeground(foreground); //設定前景顏色
			lbPreview.setFont(font); //設定使用字型
			lbPreview.setBorder(new CompoundBorder(
				new javax.swing.plaf.metal.MetalBorders.TextFieldBorder(),
				new EmptyBorder(0, 5, 0, 5)));
			//設定標籤使用組合框線, 外框為Swing預設的文字欄框線,
			//內框為空白框線

			lbPreview.setPreferredSize(new Dimension(150, 70));
			//設定顯示選取設定之標籤的偏好大小

			lbOrgin.setOpaque(true); //設定標籤不透明
			lbOrgin.setBackground(background); //設定背景顏色
			lbOrgin.setForeground(foreground); //設定前景顏色
			lbOrgin.setFont(font); //設定使用字型

			lbOrgin.setBorder(new CompoundBorder(
				new javax.swing.plaf.metal.MetalBorders.TextFieldBorder(),
				new EmptyBorder(0, 5, 0, 5)));
			//設定標籤使用組合框線, 外框為Swing預設的文字欄框線,
			//內框為空白框線

			lbOrgin.setPreferredSize(new Dimension(150, 70));
			//設定顯示原始設定之標籤的偏好大小

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
		}
	}
}