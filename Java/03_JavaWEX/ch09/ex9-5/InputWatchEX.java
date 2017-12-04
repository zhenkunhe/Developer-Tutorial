import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*; //引用包含ItemListener介面的套件

import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

import java.io.*; //引用定義File類別的套件

public class InputWatchEX extends JFrame{

	final static String FILE_PATH = "data\\item.dat";
	//儲存組合方塊清單選項內容的檔案

	MemComboBox mcbBank = new MemComboBox(FILE_PATH);
	//以包含選項內容的字串建立組合方塊

	JLabel lbSelBank = new JLabel("顯示選取結果");
	//顯示組合方塊選取結果的標籤

	String strMsg = "選取或輸入銀行名稱";

	//定義並宣告回應在組合方塊內
	//按下 Enter 鍵觸發之ActionEvent的監聽器物件
	ActionListener al = new ActionListener(){
		public void actionPerformed(ActionEvent e){

			MemComboBox source = (MemComboBox)e.getSource();
			//取得引發事件的MemComboBox物件

			String strInp = (String) source.getSelectedItem();
			//取得被選取項目

			//若被選取的項目內容與組合方塊顯示的字串相同,
			//則終止事件回應
			if(strInp.equals(strMsg))
				return;

			source.addItem(strInp);
			//取得被選取項目
		}
	};

	ComboBoxInputWatch cbiw = new ComboBoxInputWatch(mcbBank);
	//宣告監看組合方塊輸入值的監看物件

	InputWatchEX(){

		mcbBank.setSelectedIndex(-1); //設定預設不選取任何選項

		ComboBoxEditor cmbeBank = mcbBank.getEditor();
		//取得組合方塊內容編輯元件

		mcbBank.configureEditor(cmbeBank, strMsg);
		//設定內容編輯元件的預設值

		mcbBank.addItemListener(new ItemListener(){

			//回應改變選項狀態的動作
			public void itemStateChanged(ItemEvent e){

				if(e.getStateChange() == ItemEvent.SELECTED)
					lbSelBank.setText(mcbBank.getSelectedItem()
						+ "[" + mcbBank.getSelectedIndex() + "]");
			}
		});

		//註冊回應mcbBank元件ActionEvent事件的監聽器
		mcbBank.addActionListener(al);

		//註冊監聽WindowEvent事件的監聽器物件,
		//並運用關閉視窗的機會儲存組合方塊的Model物件
		addWindowListener(new WindowAdapter(){

			//回應視窗關閉事件
			public void windowClosing(WindowEvent e){
				mcbBank.save(FILE_PATH);
				System.exit(0);
			}
		});

		//建立包含各元件的Box容器, 並將元件加入
		Box bxBank = new Box(BoxLayout.X_AXIS);
		bxBank.add(Box.createHorizontalStrut(10));
		bxBank.add(new JLabel("匯入銀行 : ", JLabel.RIGHT));
		bxBank.add(mcbBank);
		bxBank.add(Box.createHorizontalStrut(10));
		bxBank.add(new JLabel("選取結果 : ", JLabel.RIGHT));
		bxBank.add(lbSelBank);
		bxBank.add(Box.createHorizontalGlue());

		bxBank.setBorder(new EmptyBorder(5, 5,  5, 5));
		//設定寬度為5的空白框線

		getContentPane().add(bxBank); //將Box容器加入內容面版

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(450, 80);
		setVisible(true);
	}

	public static void main(String args[]) {
		new InputWatchEX(); //宣告視窗框架物件
	}
}

//定義可將選項資料儲存至檔案,
//與從檔案取得選項資料, 可記憶選項的組合方塊
class MemComboBox extends JComboBox {

	public MemComboBox(Object[] items){
		super(items); //呼叫基礎類別的建構子

		initial(); //起始組合方塊
	}

	public MemComboBox(){
		initial(); //起始組合方塊
	}

	public MemComboBox(String fileName){
		initial(); //起始組合方塊

		load(fileName); //載入儲存選項內容的檔案
	}

	private void initial(){
		setMaximumRowCount(20);
		//設定組合方塊之清單可列出選項的最大筆數

		setEditable(true); //設定文字方塊可執行編輯
	}

	//判斷清單內是否有重複的選項,
	//若無則新增選項
	public void addItem(Object item){

		//若清單內沒有選項則直接將選項加入
		if(getItemCount() == 0){
			super.addItem(item);
			return;
		}

		//判斷清單內是否已經存在相同的選項,
		//是則停止執行
		for(int i=0; i<getItemCount(); i++)	{
			if((getItemAt(i)).equals(item)){
				return;
			}
		}

		super.addItem(item); //將清單內沒有的選項加入清單
	}

	//將儲存組合方塊之清單選項的Model物件寫入指定檔案
	public void save(String fileName){
		try{			
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			//宣告將Model物件輸出至指定檔案的輸出串流物件

			oos.writeObject(getModel());
			//取得組合方塊使用的Model物件, 並寫入檔案

			oos.flush(); //清空輸出串流, 並將資料寫入緩衝區
			oos.close(); //關閉物件輸出串流
			fos.close(); //關閉檔案輸出串流
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	//從指定檔案載入儲存組合方塊清單內容的Model物件
	public void load(String fileName){
		try {
			File file = new File(fileName);
			//宣告代表儲存Model物件之檔案的File

			if(!file.exists()){ //判斷是否可正確取得檔案
				String[] strBank = {"郵局", "建華銀行", "中華商銀", "合作金庫"};
				//宣告建立組合方塊選項內容的字串陣列

				setModel(new DefaultComboBoxModel(strBank));
				//以String物件陣列宣告Model物件

				return;
			}

			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			//宣告讀取Model物件的輸入串流物件

			Object obj = ois.readObject(); //從串流讀取物件

			//判斷取得物件之型別是否為ComboBoxModel
			if(obj instanceof ComboBoxModel){
				removeAllItems(); //移除所有選項
				setModel((ComboBoxModel)obj);
				//設定組合方塊使用Model物件的內容做為選項
			}
			ois.close(); //關閉物件輸入串流
			fis.close();  //關閉檔案輸入串流
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}

//繼承KeyAdapter類別
//宣告回應KeyEvent事件監看組合方塊內文字方塊的輸入監看類別
class ComboBoxInputWatch extends KeyAdapter {
	private JComboBox cmb; //監看的組合方塊
	private JTextField editor; //組合方塊的文字方塊

	public ComboBoxInputWatch(JComboBox cmbObj){
		cmb = cmbObj;
		editor = (JTextField)cmb.getEditor().getEditorComponent();
		//取得組合方塊的文字方塊

		editor.addKeyListener(this);
		//註冊輸入監看物件將監看組合方塊的KeyEvent事件
	}

	//回應放開鍵盤按鍵的事件
	public void keyReleased(KeyEvent e){

		char ch = e.getKeyChar(); //取得代表引發事件之按鍵的字元

		String input = (String) editor.getText();
		//取得組合方塊的選取值

		if(input.length() == 0){ //判斷字串長度是否為0
			return;
		}

		//判斷引發事件之按鍵是否為 Enter ,
		//此動作主要用於處理中文字的輸入,
		//輸入一個中文時, 將伴隨 Enter
		if(ch == KeyEvent.VK_ENTER){
			editor.setCaretPosition(input.length());
			//設定文字方塊內游標的位置將在輸入值的最後
		}
		else if(Character.isISOControl(ch)){			
			return; //若輸入的字元是ISO控制字元, 則中斷方法
		}

		int  pos = editor.getCaretPosition(); //取得游標位置

		//運用迴圈比對組合方塊內所有清單
		for(int k=0; k<cmb.getItemCount(); k++){
			String item = cmb.getItemAt(k).toString();
			//取得組合方塊內清單的選項, 將選項轉換為字串

			//比對選項字串內是否包含輸入字串, 是則設定游標位置
			if(item.startsWith(input)){
				editor.setText(item); //設定文字方塊內顯示選項字串
				editor.setCaretPosition(item.length()); //設定游標位置
				editor.moveCaretPosition(pos); //設定游標的選取範圍
				break;
			}
		}
	}
}