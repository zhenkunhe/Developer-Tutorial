import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件
import java.util.*;

public class InputEX extends JFrame{

	JLabel lbInput = new JLabel("輸入值 : ");
	//宣告顯示使用者輸入資料的標籤

	JRadioButton rbTextField = new JRadioButton("文字欄", true),
		rbComboBox = new JRadioButton("組合方塊"),
		rbList = new JRadioButton("清單方塊(20個以上的選項)");
	//供使用者選取資料輸入對話盒輸入資料的控制項

	InputEX(){

		JButton btnInput = new JButton("顯示資料輸入對話盒");
		//宣告呼叫資料輸入對話盒的按鈕

		//註冊監聽ActionEvent事件的監聽器
		btnInput.addActionListener(new ActionListener(){
			private final ImageIcon ICON_BITC = 
				new ImageIcon("icon/Bitc_Logo_Only.gif");

			public void actionPerformed(ActionEvent e){

				String result; //儲存輸入至對話盒的資料

				if(rbTextField.isSelected()){
					result = (String) JOptionPane.showInputDialog(
							null, "輸入書籍名稱", "請輸入書籍名稱",
							JOptionPane.INFORMATION_MESSAGE, 
							ICON_BITC, null, "Java 2 入門進階");
					//顯示資料輸入對話盒, 以文字欄輸入資料,
					//預設值為Java 2 入門進階, 並取得使用者輸入的資料
				}
				else if(rbComboBox.isSelected()) {
					String[] book = {"Java 2 入門進階 – 適用JDK5.0", 
											"Visual C++.NET 入門進階", 
											"Access 2003 徹底研究", 
											"Access 2003 程式設計", 
											"JSP 動態網頁入門實務",
											"ASP 動態網頁入門實務"};
					//資料輸入對話盒之組合對話盒的選取選項

					result = (String) JOptionPane.showInputDialog(
							null, "選取書籍", "請選取書籍", 
							JOptionPane.INFORMATION_MESSAGE, 
							ICON_BITC, book, book[0]);
					//顯示資料輸入對話盒, 以組合對話盒輸入資料,
					//預設選取第一個選項, 並將取得選取結果
				}
				else{
					String[] item = new String[20];
					//資料輸入對話盒之組合對話盒的選取選項

					for(int i=0; i<item.length; i++)
						item[i] = new String("選項" + (i+1));

					result = (String) JOptionPane.showInputDialog(
							null, "選取書籍", "請選取書籍", 
							JOptionPane.INFORMATION_MESSAGE, 
							ICON_BITC, item, item[0]);
					//顯示資料輸入對話盒, 以組合對話盒輸入資料,
					//預設選取第一個選項, 並將取得選取結果
				}

				//若取得資料不為null, 且長度不為0, 
				//則設定lbInput標籤顯示輸入結果
				if(result != null && result.length() > 0)
						lbInput.setText("輸入值 : " + result);
			}
		});

		//將選擇鈕組成為按鈕群組
		ButtonGroup bgInput = new ButtonGroup();
		bgInput.add(rbTextField);
		bgInput.add(rbComboBox);
		bgInput.add(rbList);

		Box bxInput = new Box(BoxLayout.X_AXIS);
		bxInput.add(new JLabel("輸入資料的方式 : "));
		bxInput.add(rbTextField); //將選擇鈕加入Box容器
		bxInput.add(Box.createHorizontalStrut(5));
		bxInput.add(rbComboBox);
		bxInput.add(Box.createHorizontalStrut(5));
		bxInput.add(rbList);

		Container cp = getContentPane(); //取得內容面版
		cp.add(bxInput); //將元件加入內容面版
		cp.add(btnInput, BorderLayout.EAST);
		cp.add(lbInput, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//設定根面版四周將使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 100);
		setVisible(true);
	}

	public static void main(String args[]) {
		new InputEX(); //宣告視窗框架物件
	}
}