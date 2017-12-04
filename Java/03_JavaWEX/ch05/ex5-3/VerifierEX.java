import javax.swing.*;  //引用套件
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import java.text.*; //提供DateFormat的套件
import java.util.*; //提供Locale的套件
import javax.swing.text.*;  //提供DefaultFormatter, 及其子類別的套件

import java.beans.*; //提供PropertyChangeListener介面的套件

public class VerifierEX extends JFrame {

	//定義格式化資料輸入的樣式
	static final String ID_PATTERN = "?#########"; //身份證號碼的遮罩樣式
	static final String PHONE_PATTERN = "(##*)###*-###*"; //電話的遮罩樣式

	JFormattedTextField ftfName = new JFormattedTextField(new String("陳新璐")),
					   ftfID,
					   ftfBirthday = new JFormattedTextField(),
					   ftfPhone,
					   ftfSalary;
	//宣告JFormattedTextField物件

	JLabel lbMsg = new JLabel("顯示驗證訊息");

	VerifierEX(){

		try{
			ftfID = new JFormattedTextField(
						new DefaultFormatterFactory(new MaskFormatter(ID_PATTERN)),
						new String("A154688882"));
			//宣告JFormattedTextField物件,
			//並傳入建立MaskFormatter格式物件的
			//DefaultFormatterFactory物件與預設值

			ftfID.setInputVerifier(new IDVerifier()); //設定驗證身份證號碼的物件

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

		ftfPhone.setInputVerifier(new PhoneFormatVerifier(ID_PATTERN)); //設定驗證電話號碼的驗證物件
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

		ftfBirthday.setInputVerifier(new DateVerifier(new Date()));
		//設定日期資料的驗證物件

		NumberFormat nfMoney = NumberFormat.getCurrencyInstance(Locale.TAIWAN);
		//取得表達台灣區貨幣格是的Fromat物件

		ftfSalary = new JFormattedTextField(nfMoney);
		//宣告JFormattedTextField物件,
		//運用的格式物件將使用Format格式

		NumberFormatter nfrSalary = 
							(NumberFormatter)ftfSalary.getFormatter();
		//取得JFormattedTextField物件使用的格式物件

		nfrSalary.setMinimum(new Double(18500)); //設定允許的最小值
		nfrSalary.setMaximum(new Double(70000)); //設定允許的最大值

		ftfSalary.setValue(new Double(40000)); //設定value屬性

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

		JPanel jpLabel = new JPanel(new GridLayout(1, 1,  5, 5));
		jpLabel.add(lbMsg);

		Container cp = getContentPane(); //取得內容面版
		cp.add(jpMain); //將面版加入內容面版
		cp.add(jpLabel, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//設定關閉視窗將預設結束程式
		setSize(300, 250); //設定視窗框架的顯示大小
		setVisible(true); //顯示視窗框架
	}

	//驗證輸入的身份證號碼是否正確
	class IDVerifier extends InputVerifier {

		public boolean verify(JComponent input){
		
			//判斷欲執行驗證的元件是否為JFormattedTextField
			if(!(input instanceof JFormattedTextField))
				return true;

			JFormattedTextField ftf = (JFormattedTextField) input; //轉換型別

			String ID = (String) ftf.getText(); //取得欄位的屬性值

			if (ID.length() != 10) //判斷輸入字串的長度
				return false;
			
			ID = ID.toUpperCase(); //將英文字母均轉換為大寫
			byte s[] = ID.getBytes();
			//將所有字元轉換為byte型別, byte值即為字元的ASCII碼

			if (s[0] >= 65 && s[0] <= 90) { //判斷第一個字元是否英文字母

				int a[] = {10, 11, 12, 13, 14, 15, 16, 17, 34, 18, 19, 20, 21,
					          22, 35, 23, 24, 25, 26, 27, 28, 29, 32, 30, 31, 33};
				//儲存與英文字母對應的整數值

				int count = (a[ s[0] - 65] / 10)  + (a[ s[0] - 65] % 10) * 9;
				//將對應於第一個字元的整數值, 十位的數字加上個位的數字乘以9

				for (int i = 1; i <= 8; i++) {
					count += (s[i] - 48) * (9 - i); //將數字依序乘上1至8的數字, 然後累加
				}

				//以10減去累加結果的個位數字, 然後與檢查碼比對
				if ((10 - (count % 10)) == (s[9] - 48)) {
					lbMsg.setText(" [" + ID + "] 通過驗證! ");
					return true;
				}
			}

			lbMsg.setText(" [" + ID + "] 無法通過驗證! ");
			return false;
		}
	}

	//定義驗證電話號碼格式是否正確的類別
	class PhoneFormatVerifier extends InputVerifier {

		String pattern;

		PhoneFormatVerifier(String phonePattern) { //建構子
			pattern = phonePattern;
		}

		public boolean verify(JComponent input) {

			//判斷欲執行驗證的元件是否為JFormattedTextField
			if(!(input instanceof JFormattedTextField))
				return true;

			JFormattedTextField ftf = (JFormattedTextField) input; //轉換型別

			JFormattedTextField.AbstractFormatter
							formatter = ftf.getFormatter(); //取得格式物件

			if(formatter == null){
				lbMsg.setText("電話號碼格式通過驗證");
				return true;
			}

			try{
				formatter.stringToValue((String)ftf.getValue()); //轉換欄位內的值
				lbMsg.setText("電話號碼格式通過驗證");
				return true;
			}
			catch(ParseException pe){
				lbMsg.setText("格式錯誤, 正確的格式為" + pattern);
				return false;
			}
		}
	}

	//檢查輸入日期值是否正確的驗證類別
	class DateVerifier extends InputVerifier {

		Date d;
	
		public DateVerifier(Date d){
			this.d = d;
		}

		public boolean verify(JComponent input) {

			//判斷欲執行驗證的元件是否為JFormattedTextField
			if(!(input instanceof JFormattedTextField))
				return true;

			JFormattedTextField ftf = (JFormattedTextField) input; //轉換型別

			if(d.after((Date)ftf.getValue())){ //檢查輸入日期值是否在指定日期後
				lbMsg.setText("日期輸入值通過驗證");
				return true;
			}

			lbMsg.setText("日期晚於" + d + ", 輸入值不正確! ");
			return false;
		}
	}

	public static void main(String args[]) {
		new VerifierEX(); //宣告視窗框架物件
	}
}