import javax.swing.*;
import javax.swing.border.*; //引用建立框線的Border物件
import javax.swing.event.*;
//引用包含ListSelectionListener介面的套件

import java.awt.*;
import java.awt.event.*;

public class ListEX extends JFrame{

	JList ltSS = new JList(); //宣告未起始選項的清單方塊

	String[] strItem = {"PHP", "JSP", "ASP", "ASP.NET", "Perl"};
	//宣告儲存清單方塊選項內容的字串陣列

	JList ltMIS = new JList(strItem); //以字串陣列起始宣告清單方塊
	JList ltSIS = new JList(strItem);

	JLabel lbSS = new JLabel(" ");
	JLabel lbMIS = new JLabel();
	JLabel lbSIS = new JLabel();
	//宣告顯示清單方塊被選取項目內容的標籤

	//定義並宣告回應ListSelectionEvent的監聽器
	ListSelectionListener lsl = new ListSelectionListener(){
		public void valueChanged(ListSelectionEvent e){

			if(e.getValueIsAdjusting() == true){
									//判斷是否於連續事件中引發
				System.out.println("連續事件");
				return;
			}
			else{
				System.out.println("非連續事件");
			}

			System.out.println("--------------------------------------\n");

			JList source = (JList)e.getSource();
			//取得事件的來源元件

			//判斷來源元件以便執行回應
			if(source == ltSS)
				lbSS.setText((String)source.getSelectedValue());
			else{
				String selItem = "";

				//取得清單方塊選取的選項, 並將選項內容串連成字串
				for(Object item: source.getSelectedValues())
					selItem = selItem + (String)item + ", ";

				if(source == ltMIS)
					lbMIS.setText(selItem);
				else
					lbSIS.setText(selItem);
			}
		}
	};

	ListEX(){

		ltSS.setListData(strItem); //設定選取項目

		ltSS.setVisibleRowCount(4); //設定清單方塊的大小
		ltMIS.setVisibleRowCount(4);
		ltSIS.setVisibleRowCount(4);

		//設定清單方塊的選取模式
		ltSS.setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		ltMIS.setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		ltSIS.setSelectionMode(
				ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		//設定回應清單方塊ListSelectionEvent事件的監聽器
		ltSS.addListSelectionListener(lsl);
		ltMIS.addListSelectionListener(lsl);
		ltSIS.addListSelectionListener(lsl);

		Box bxSS = new Box(BoxLayout.Y_AXIS);
		bxSS.add(new JLabel("單選型"));
		//將清單方塊名稱標籤加入Box容器內

		bxSS.add(new JScrollPane(ltSS));
		//以清單方塊建立JScrollPane物件, 並加入Box容器內

		Box bxMIS = new Box(BoxLayout.Y_AXIS);
		bxMIS.add(new JLabel("不連續範圍複選型"));
		bxMIS.add(new JScrollPane(ltMIS));

		Box bxSIS = new Box(BoxLayout.Y_AXIS);
		bxSIS.add(new JLabel("連續範圍複選型"));
		bxSIS.add(new JScrollPane(ltSIS));

		JPanel jpLS = new JPanel(new GridLayout(1, 3));
		//宣告包含Box容器的JPanel容器

		jpLS.add(bxSS); //將Box容器加入JPanel容器內
		jpLS.add(bxMIS);
		jpLS.add(bxSIS);

		JPanel jpResult = new JPanel(new GridLayout(1, 3));
		//宣告包含顯示清單方塊選取結果的標籤之JPanel容器

		jpResult.add(lbSS); //將顯示選取結果的標籤加入JPanel容器內
		jpResult.add(lbMIS);
		jpResult.add(lbSIS);

		Border border = BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(Color.gray, 2),
			"選取結果",
			TitledBorder.LEFT, TitledBorder.TOP);
		//宣告標題框線物件, 標題文字將靠左靠上對齊, 顏色為淺灰, 寬度為2

		jpResult.setBorder(border); //設定JPanel使用的框線
		
		Container cp = getContentPane(); //取得內容面版
		cp.add(jpLS); //將元件加入內容面版
		cp.add(jpResult,BorderLayout.SOUTH);

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(550, 190);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ListEX(); //宣告視窗框架物件
	}
}