import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件
import java.util.*;

public class CheckBoxEX extends JFrame{

	JLabel lbTitle = new JLabel("選取項目 : ");

	//宣告核取方塊物件, 並設定標籤文字
	JCheckBox cbSpe1 = new JCheckBox("PHP"),
		  cbSpe2 = new JCheckBox("JSP"),
		  cbSpe3 = new JCheckBox("ASP"),
		  cbSpe4 = new JCheckBox("ASP.NET"),
		  cbSpe5 = new JCheckBox("Perl");

	//定義並宣告回應ItemEvent事件的監聽器
	ItemListener il = new ItemListener(){

		LinkedList<JCheckBox> llSel = new LinkedList<JCheckBox>();
		//宣告儲存狀態為選取的JCheckBox物件

		public void itemStateChanged(ItemEvent e) {

			JCheckBox cbSource = (JCheckBox) e.getSource();	//取得事件來源物件

			//判斷觸發的為選取事件還是取消選取事件
			if(e.getStateChange() == ItemEvent.SELECTED)
				llSel.add(cbSource);
			else if(e.getStateChange() == ItemEvent.DESELECTED)
				llSel.remove(cbSource);

			StringBuffer sbActionCommand = new StringBuffer("選取項目 : ");

			//運用for迴圈輸出被選取之JCheckBox物件的動作命令字串
			for(JCheckBox elm: llSel)
				sbActionCommand.append(elm.getActionCommand() + ", ");

			lbTitle.setText(sbActionCommand.toString());
			//設定被選取lbText的顯示內容
		}	
	};

	CheckBoxEX(){

		Box boxSpe = new Box(BoxLayout.X_AXIS); //建立放置核取方塊的Box

		boxSpe.add(cbSpe1); //將元件放入Box容器
		boxSpe.add(Box.createHorizontalStrut(10));
		boxSpe.add(cbSpe2);
		boxSpe.add(Box.createHorizontalStrut(10));
		boxSpe.add(cbSpe3);
		boxSpe.add(cbSpe4);
		boxSpe.add(Box.createHorizontalStrut(10));
		boxSpe.add(Box.createHorizontalStrut(10));
		boxSpe.add(cbSpe5);

		cbSpe1.addItemListener(il); //註冊回應ItemEvent事件的監聽器
		cbSpe2.addItemListener(il);
		cbSpe3.addItemListener(il);
		cbSpe4.addItemListener(il);
		cbSpe5.addItemListener(il);

		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new GridLayout(2, 1)); //設定運用GridLayout配置版面
		cp.add(lbTitle); //將元件加入內容面版
		cp.add(boxSpe);

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 100);
		setVisible(true);
	}

	public static void main(String args[]) {
		new CheckBoxEX(); //建立視窗框架物件
	}
}