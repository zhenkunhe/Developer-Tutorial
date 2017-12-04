import javax.swing.*;  //引用套件
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import java.text.*; //提供DateFormat的套件
import java.util.*; //提供Locale的套件
import javax.swing.text.*;  //提供DefaultFormatter, 及其子類別的套件

import java.beans.*; //提供PropertyChangeListener介面的套件

public class FormattedEX extends JFrame {

	//定義格式化資料輸入的樣式
	static final String ID_PATTERN = "?#########"; //身份證號碼的遮罩樣式
	static final String PHONE_PATTERN = "(##*)###*-###*"; //電話的遮罩樣式

	JFormattedTextField ftfName = new JFormattedTextField(new String("陳新璐")),
					   ftfID,
					   ftfBirthday = new JFormattedTextField(),
					   ftfPhone,
					   ftfSalary;
	//宣告JFormattedTextField物件

	JLabel lbName = new JLabel("姓名欄位的text屬性 :       value屬性 : ");
	JLabel lbMsg = new JLabel("取得的數值 : ");

	FormattedEX(){

		//監聽游標移動事件, 並顯示text屬性與value屬性之值的變化
		ftfName.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e){
				JFormattedTextField ftfSource = (JFormattedTextField) e.getSource();

				lbName.setText("姓名欄位的text屬性 : " 
											+ ftfSource.getText()
											+ "  value屬性 : " + ftfSource.getValue());
			}
		});

		//監聽Action事件, 並顯示text屬性與value屬性之變化
		ftfName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				JFormattedTextField ftfSource = (JFormattedTextField) e.getSource();

				lbName.setText("姓名欄位的text屬性 : " 
											+ ftfSource.getText()
											+ "  value屬性 : " + ftfSource.getValue());
			}
		});

		try{
			ftfID = new JFormattedTextField(
						new DefaultFormatterFactory(new MaskFormatter(ID_PATTERN)),
						new String("A154688887"));
			//宣告JFormattedTextField物件,
			//並傳入建立MaskFormatter格式物件的
			//DefaultFormatterFactory物件與預設值

			MaskFormatter mfPhone = new MaskFormatter(PHONE_PATTERN);
			//宣告MaskFormatter物件

			mfPhone.setPlaceholderCharacter('_'); //設定欄位顯示輸入位置的標示
			mfPhone.setValidCharacters("0123456789 ");
			//設定可接受的字元為0至9的數字與空白

			ftfPhone = new JFormattedTextField(mfPhone);
			//宣告JFormattedTextField物件,
			//直接傳入欲使用的MaskFormatter格式物件
		}
		catch(ParseException pe){
			pe.printStackTrace();
		}

		ftfPhone.setValue(new String("(07 )557 -5586"));
		//設定value屬性

		DateFormat dfDate = 
						new SimpleDateFormat("yyyy/MM/dd", Locale.US);
		//宣告日期格式

		ftfBirthday.setFormatterFactory(
						new DefaultFormatterFactory(new DateFormatter(dfDate)));
		//設定產生格式物件的建立器

		Calendar date = Calendar.getInstance(Locale.TAIWAN);
		//取得表示日期Calendar物件

		date.set(1976, 5, 16);
		//設定日期值為1976/6/16, 月份值從0開始, 故以5代表6月

		ftfBirthday.setValue(date.getTime()); //設定value屬性

		NumberFormat nfMoney = NumberFormat.getCurrencyInstance(Locale.TAIWAN);
		//取得表達台灣區貨幣格是的Fromat物件

		ftfSalary = new JFormattedTextField(nfMoney);
		//宣告JFormattedTextField物件,
		//運用的格式物件將使用Format格式

		NumberFormatter nfrSalary = (NumberFormatter)ftfSalary.getFormatter();
		//取得JFormattedTextField物件使用的格式物件

		nfrSalary.setMinimum(new Double(18500)); //設定允許的最小值
		nfrSalary.setMaximum(new Double(70000)); //設定允許的最大值

		ftfSalary.setValue(new Double(40000)); //設定value屬性

		ftfSalary.addPropertyChangeListener("value", new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent evt){

				//Double value = (Double) evt.getNewValue();
				//因為呼叫過setMinimum()方法與setMaximum()方法,
				//故上一行敘述亦可正常執行

				Double value = ((Double)evt.getNewValue()).doubleValue();
				//取得的資料型別為Number, 若直接轉型為Double將丟出例外

				lbMsg.setText("取得的數值 : " + Double.toString(value));
			}
		});

		JPanel jpMain = new JPanel(new GridLayout(5, 2,  5, 5));
		jpMain.add(new JLabel("姓名 : ", JLabel.RIGHT)); //將元件加入JPanel子容器
		jpMain.add(ftfName);
		jpMain.add(new JLabel("身份證字號 : ", JLabel.RIGHT));
		jpMain.add(ftfID);
		jpMain.add(new JLabel("生日 : ", JLabel.RIGHT));
		jpMain.add(ftfBirthday);
		jpMain.add(new JLabel("聯絡電話 : ", JLabel.RIGHT));
		jpMain.add(ftfPhone);
		jpMain.add(new JLabel("薪資 : ", JLabel.RIGHT));
		jpMain.add(ftfSalary);

		JPanel jpLabel = new JPanel(new GridLayout(2, 1,  5, 5));
		jpLabel.add(lbName);
		jpLabel.add(lbMsg);

		Container cp = getContentPane(); //取得內容面版
		cp.add(jpMain); //將面版加入內容面版
		cp.add(jpLabel, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//設定關閉視窗將預設結束程式
		setSize(300, 250); //設定視窗框架的顯示大小
		setVisible(true); //顯示視窗框架
	}

	public static void main(String args[]) {
		new FormattedEX(); //宣告視窗框架物件
	}
}