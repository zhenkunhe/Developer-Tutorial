import javax.swing.*;
import javax.swing.event.*; //引用包含ItemListener介面的套件
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class ComboBoxEX extends JFrame{

	String[] strPay = {"信用卡", "現金", "刷卡", "轉帳", "支票"};
	String[] strBank = {"郵局", "建華銀行", "中華商銀", "合作金庫"};
	//宣告建立組合方塊選項內容的字串

	JComboBox cmbPay = new JComboBox(strPay),
						  cmbBank = new JComboBox(strBank);
	//以包含選項內容的字串陣列建立組合方塊

	JLabel lbSelPay = new JLabel(), //顯示組合方塊選取結果的標籤
				lbSelBank = new JLabel();

	String strMsg = "選取或輸入銀行名稱";

	//定義並宣告回應在組合方塊內
	//按下 Enter 鍵觸發之ActionEvent的監聽器物件
	ActionListener al = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			boolean addItem = true;
			String strInp = 
				(String) ((JComboBox)e.getSource()).getSelectedItem();
			//取得被選取項目

			//若被選取的項目內容與組合方塊顯示的字串相同,
			//則終止事件回應
			if(strInp.equals(strMsg))
				return;

			//以for迴圈比對取得選項是否為組合方塊的選項
			for(int i=0; i<cmbBank.getItemCount(); i++){

				//比對選項字串				
				if(cmbBank.getItemAt(i).equals(strInp)){
					addItem = false; //設定欲將字串加入
					break;
				}
			}

			//若取得選項不是組合方塊的選項則將選項插入為選取項目
			if(addItem)
				cmbBank.insertItemAt(strInp, 0); //插入第一個選項
		}
	};

	ComboBoxEX(){
		cmbPay.setSelectedIndex(0); //設定選項的項目
		cmbBank.setSelectedIndex(-1); //設定預設不選取任何選項

		cmbBank.setEditable(true); //設定輸入內容可編輯

		ComboBoxEditor cmbeBank = cmbBank.getEditor();
		//取得組合方塊內容編輯元件

		cmbBank.configureEditor(cmbeBank, strMsg);
		//設定內容編輯元件的預設值	

		//定義並宣告回應ItemEvent事件的監聽器物件
		cmbPay.addItemListener(new ItemListener(){

			//回應改變選項狀態的動作
			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange() == ItemEvent.SELECTED)
					lbSelPay.setText(cmbPay.getSelectedItem()
						+ "[" + cmbPay.getSelectedIndex() + "]");
			}
		});

		cmbBank.addItemListener(new ItemListener(){

			//回應改變選項狀態的動作
			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange() == ItemEvent.SELECTED)
					lbSelBank.setText(cmbBank.getSelectedItem()
						+ "[" + cmbBank.getSelectedIndex() + "]");
			}
		});

		//註冊回應cmbBank元件ActionEvent事件的監聽器
		cmbBank.addActionListener(al);

		//建立包含各元件的Box容器, 並將元件加入
		Box bxPay = new Box(BoxLayout.X_AXIS);
		bxPay.add(Box.createHorizontalStrut(10));
		bxPay.add(new JLabel("付款方式 : ", JLabel.RIGHT));
		bxPay.add(cmbPay);
		bxPay.add(Box.createHorizontalStrut(10));
		bxPay.add(new JLabel("選取結果 : ", JLabel.RIGHT));
		bxPay.add(lbSelPay);
		bxPay.add(Box.createHorizontalGlue());

		//建立包含各元件的Box容器, 並將元件加入
		Box bxBank = new Box(BoxLayout.X_AXIS);
		bxBank.add(Box.createHorizontalStrut(10));
		bxBank.add(new JLabel("匯入銀行 : ", JLabel.RIGHT));
		bxBank.add(cmbBank);
		bxBank.add(Box.createHorizontalStrut(10));
		bxBank.add(new JLabel("選取結果 : ", JLabel.RIGHT));
		bxBank.add(lbSelBank);
		bxBank.add(Box.createHorizontalGlue());

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new GridLayout(2, 1, 10, 10));
		//設定內容面版使用GridLayout管理版面

		cp.add(bxPay); //將Box容器加入內容面版
		cp.add(bxBank);

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(400, 100);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ComboBoxEX(); //宣告視窗框架物件
	}
}