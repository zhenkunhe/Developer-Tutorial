import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*; //引用提供File類別的套件

import java.util.*;

import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

public class UserLFEX extends JFrame {

	UserLFEX(){

		JRadioButton
			rbOne = new JRadioButton("選項 1", true),
			rbTwo = new JRadioButton("選項 2"),
			rbThree = new JRadioButton("選項 3");
		//宣告選擇鈕

		ButtonGroup bgRadio  = new ButtonGroup();
		bgRadio.add(rbOne);
		bgRadio.add(rbTwo);
		bgRadio.add(rbThree);
		//宣告按鈕群組, 並加入選擇鈕

		Box bxRadio = new Box(BoxLayout.Y_AXIS);
		bxRadio.add(rbOne);
		bxRadio.add(rbTwo);
		bxRadio.add(rbThree);
		//宣告容納選擇鈕的Box容器, 並加入選擇鈕

		JCheckBox
			cbOne = new JCheckBox("選項 1"),
			cbTwo = new JCheckBox("選項 2", true),
			cbThree = new JCheckBox("選項 3");
		//宣告核取方塊

		Box bxCheck = new Box(BoxLayout.Y_AXIS);
		bxCheck.add(cbOne);
		bxCheck.add(cbTwo);
		bxCheck.add(cbThree);
		//宣告容納核取方塊的Box容器, 並加入核取方塊

		String[] strItem = {"選項 1", "選項 2", "選項 3"};
		//宣告清單方塊的選項字串

		JList lst = new JList(strItem);
		//以選項字串陣列宣告JList物件

		//將容納元件的容器加入面版
		JPanel jpPanel = new JPanel(new GridLayout(1, 3,  5, 5));
		jpPanel.add(new JScrollPane(lst));
		jpPanel.add(bxRadio);
		jpPanel.add(bxCheck);

		Container cp = getContentPane(); //取得內容面版
		cp.add(jpPanel);

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//設定根面版使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(530, 220);
		setVisible(true);
	}

	public static void main(String args[]) {

		try{
			LookAndFeel lfBitC = new BitCLF();
			//宣告自訂的外視感覺物件

			UIManager.LookAndFeelInfo info =
				new UIManager.LookAndFeelInfo(lfBitC.getName(),
				lfBitC.getClass().getName());
			//宣告描述自訂外視感覺物件的
			//UIManager.LookAndFeelInfo物件

			UIManager.installLookAndFeel(info);
			//安裝描述外視感覺物件的UIManager.LookAndFeelInfo物件

			UIManager.setLookAndFeel(lfBitC);
			//設定使用的外視感覺物件
		}
		catch (Exception ex){
			ex.printStackTrace();
		}

		new UserLFEX(); //宣告視窗框架物件
	}
}

class BitCLF extends BasicLookAndFeel {

	public String getID() { return "BitCL&F"; }
	//取得外視感覺物件的ID

	public String getName() { return "BitC"; }
	//取得外視感覺物件的名稱

	public String getDescription() {
		return "L&F from BitC";
	}
	//取得外視感覺的描述字串

	public boolean isNativeLookAndFeel() { return false; }
	//若外視感覺物件對應到目前的平台則傳回true

	public boolean isSupportedLookAndFeel(){ return true; }
	//若目前的平台支援此外視感覺物件則傳回true

	//將外視感覺的預設資源設定給UIDefaults物件
	protected void initComponentDefaults(UIDefaults table) {

		super.initComponentDefaults(table);
		//呼叫基礎類別的initComponentDefaults()方法

		ColorUIResource commonBackground =
			new ColorUIResource(255, 255, 255);
		ColorUIResource commonForeground =
			new ColorUIResource(0, 0, 0);
		//宣告預設的背景顏色與前景顏色

		ColorUIResource buttonBackground =
			new ColorUIResource(155, 155, 155);
		ColorUIResource buttonForeground =
			new ColorUIResource(210, 210, 0);
		//宣告按鈕預設的背景顏色與前景顏色

		ColorUIResource menuBackground =
			new ColorUIResource(155, 155, 155);
		//宣告功能表預設的背景顏色

		FontUIResource commonFont = new
			FontUIResource("新細明體", Font.BOLD, 12 );
		//宣告預設使用的字型

		Icon check = new ImageIcon("image/check.gif");
		Icon select = new ImageIcon("image/select.gif");
		//宣告核取方塊與選擇鈕被選取時使用的圖示

		//宣告UI資源的屬性與對應物件的陣列
		Object[] defaults =	{
			"Button.font", commonFont,
			"Button.background", buttonBackground,
			"Button.foreground", buttonForeground,
			"Button.margin", new InsetsUIResource(8, 8, 8, 8),
			"Button.textIconGap", new Integer(4),
			"Button.textShiftOffset", new Integer(2),
			//設定按鈕元件使用的資源

			"CheckBox.font", commonFont,
			"CheckBox.background", commonBackground,
			"CheckBox.foreground", commonForeground,
			"CheckBox.icon", new IconUIResource(check),
			//設定核取方塊使用的字型、前景顏色、背景顏色與圖示

			"Panel.background", commonBackground,
			"Panel.foreground", commonForeground,
			//設定面版的前景顏色與背景顏色

			"RadioButton.font", commonFont,
			"RadioButton.background", commonBackground,
			"RadioButton.foreground", commonForeground,
			"RadioButton.icon", new IconUIResource(select),
			//設定選擇鈕使用的字型、前景顏色、背景顏色與圖示

			"ScrollPane.margin", new InsetsUIResource(8, 8, 8, 8),
			"ScrollPane.background", commonBackground,
			//設定捲動面版使用的邊界與背景顏色

			"ScrollBar.track", menuBackground,
			"ScrollBar.thumb", buttonBackground
			//設定捲軸軌道與饅形方塊的背景顏色
		};

		table.putDefaults(defaults);
		//呼叫putDefaults()方法傳入UI資源的屬性
		//與對應物件之陣列
	}
}
