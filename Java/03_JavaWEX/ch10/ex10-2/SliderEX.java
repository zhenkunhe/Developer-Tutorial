import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;

public class SliderEX extends JFrame{

	JLabel lbColor = new JLabel("RGB(0, 0, 0)", JLabel.CENTER);
	//顯示圖片的標籤

	JSlider slRed = new JSlider(JSlider.VERTICAL, 0, 255, 0),
				slGreen = new JSlider(JSlider.HORIZONTAL, 0, 255, 0),
				slBlue = new JSlider(JSlider.VERTICAL, 0, 255, 0);
	//宣告控制紅色光、綠色光與藍色光的滑動桿

	SliderEX(){

		slRed.setLabelTable(slRed.createStandardLabels(50));
		//設定使用標準的刻度數字標籤

		slRed.setPaintLabels(true); //設定顯示刻度的數字標籤
		slRed.setInverted(true); //設定滑動桿的方向是否顛倒
		
		slGreen.setMajorTickSpacing(50); //設定主要刻度間距
		slGreen.setMinorTickSpacing(5); //設定次要的刻度間距
		slGreen.setPaintLabels(true); //設定顯示刻度的數字標籤
		slGreen.setPaintTicks(true); //設定顯示刻度

		slBlue.setMajorTickSpacing(50); //設定主要刻度的間距
		slBlue.setMinorTickSpacing(5); //設定次要刻度間距
		slBlue.setPaintTicks(true); //設定顯示刻度
		slBlue.setSnapToTicks(true);
		//設定滑動桿移動後, 是否定位至最靠近的刻度

		lbColor.setOpaque(true); //設定為不透明
		lbColor.setBackground(Color.black); //設定背景顏色

		//以匿名類別的方式宣告回應ChangeEvent事件的監聽器物件
		ChangeListener cl = new ChangeListener(){

			public void stateChanged(ChangeEvent e){

				JSlider sl = (JSlider)e.getSource();
				//取得引發事件的捲軸

				Color bgColor = lbColor.getBackground();
				//取得標籤的背景顏色
				
				//判斷捲軸的方式, 以將捲動值設定至顯示的標籤
				if(sl == slRed){
					lbColor.setBackground(new Color(sl.getValue(), 
								bgColor.getGreen(), bgColor.getBlue()));
					//調整標籤背景顏色的紅色光強度
				}
				else if(sl == slGreen){
					lbColor.setBackground(new Color(bgColor.getRed(),
								sl.getValue(), bgColor.getBlue()));
					//調整標籤背景顏色的綠色光強度
				}
				else{
					lbColor.setBackground(new Color(bgColor.getRed(), 
								bgColor.getGreen(), sl.getValue()));
					//調整標籤背景顏色的藍色光強度
				}

				bgColor = lbColor.getBackground();
				//取得標籤更改後的背景顏色

				lbColor.setText("RGB(" + bgColor.getRed() + ", "
									+ bgColor.getGreen() 
									+ ", " + bgColor.getBlue() + ")");
					//設定標籤顯示背景顏色的RGB值
			}
		};

		slRed.addChangeListener(cl);
		slGreen.addChangeListener(cl);
		slBlue.addChangeListener(cl);
		//註冊監聽ChangeEvent事件的監聽器物件

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new BorderLayout(2, 2));
		//設定內容面版水平與垂直間距均為10的BorderLayout物件管理版面

		add(slRed, BorderLayout.WEST); //將元件加入
		add(lbColor);
		add(slGreen, BorderLayout.SOUTH);
		add(slBlue, BorderLayout.EAST);

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//設定根面版使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(400, 360);
		setVisible(true);

		slGreen.setBorder(
		new EmptyBorder(0, slRed.getWidth(), 0, slBlue.getWidth()));
		//設定水平捲軸使用空白框線, 
		//左方將出現與垂直捲軸寬度相同的空白間隙
		//請注意! 必須在呼叫setVisible()方法顯示視窗後執行
		//才能取得正確值
	}

	public static void main(String args[]) {
		new SliderEX(); //宣告視窗框架物件
	}
}