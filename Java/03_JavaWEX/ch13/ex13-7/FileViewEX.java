import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件
import java.io.*;

import javax.swing.filechooser.*;
//引用定義FileView抽象類別
//與FileSystemView抽象類別的套件

public class FileViewEX extends JFrame{

	private final static 
		String DEFAULT_FILE_PATH = "C:\\JavaWEX";
	//預設的檔案瀏覽路徑

	JLabel lbResult = new JLabel("選取檔案 : ");
	//宣告被選取檔案的標籤

	//以繼承方式定義過濾檔案選擇器運用的檔案篩選物件
	class JavaFilter 
		extends javax.swing.filechooser.FileFilter {
		
		String extension, description;
		//定義儲存檔案副檔名與檔案描述的String物件

		JavaFilter(String ext, String des){ //建構子
			extension = ext.toLowerCase(); //將副檔名轉換為英文小寫
			description = des; //設定檔案的描述文字
		}

		//判斷檔案是否為接受的類型, 是則傳回true
		public boolean accept(File f){

			if (f.isDirectory()) //若為資料夾傳回true
				return true;

			String ext = null;
			String s = f.getName(); //取得檔案名稱
			int i = s.lastIndexOf('.');
			//尋找檔案名稱內的"."號

			if (i > 0 &&  i < s.length() - 1){
				ext = s.substring(i+1).toLowerCase();
				//從檔案名稱內取得副檔名字串

				//判斷副檔名是否與
				//檔案篩選物件的extension字串相同
				if(extension.equals(ext))
					return true;
			}

			return false;
		}

		//傳回檔案篩選物件欲篩選檔案類型的描述字串
		public String getDescription(){ return description; }
	}

	FileViewEX(){

		JButton btnOpen = new JButton("開啟舊檔");
		//宣告呼叫檔案對話盒的按鈕

		//定義、註冊回應按鈕ActionEvent事件的監聽器物件
		btnOpen.addActionListener(new ActionListener(){

			//回應按鈕按下事件的方法
			public void actionPerformed(ActionEvent e){

				JFileChooser fcObj = 
					new JFileChooser(DEFAULT_FILE_PATH);
				//宣告檔案選擇器物件

				fcObj.setFileView(new JavaFileView());
				//設定檔案開啟對話盒使用自訂的JavaFileView物件

				fcObj.setDialogTitle("開啟舊檔");
				//設定對話盒的標題

				fcObj.addChoosableFileFilter(
					new JavaFilter("class", "Java位元組碼檔 (.class)"));
				fcObj.addChoosableFileFilter(
					new JavaFilter("java", "Java原始檔 (.java)"));
				//新增檔案篩選物件

				fcObj.setFileFilter(fcObj.getAcceptAllFileFilter());
				//設定起始選取接受所有檔案的檔案篩選物件

				int result = fcObj.showOpenDialog(FileViewEX.this);
				//顯示檔案開啟對話盒				

				if(result == JFileChooser.APPROVE_OPTION){
					File file = fcObj.getSelectedFile();
					//取得檔案選擇器選取的檔案

					lbResult.setText("選取檔案 : " + file.getName());
					//設定標籤顯示開啟檔案的名稱
				}
				else if(result == JFileChooser.CANCEL_OPTION){
					lbResult.setText("取消選取檔案");
					//設定標籤顯示取消檔案選取的訊息
				}
			}		
		});

		JPanel plBtn = new JPanel(
				new FlowLayout(FlowLayout.CENTER, 10, 10));
		//宣告放置按鈕的JPanel容器

		plBtn.add(btnOpen); //將按鈕加入容器

		Container cp = getContentPane(); //取得內容面版
		cp.add(plBtn); //將包含按鈕的JPanel容器加入內容面版
		cp.add(lbResult, BorderLayout.SOUTH); //加入顯示訊息的標籤
		
		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//設定根面版四周將使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(200, 110);
		setVisible(true);
	}

	public static void main(String args[]) {
		new FileViewEX(); //宣告視窗框架物件
	}
}

//繼承FileView類別, 定義檢視Java相關檔案的JavaFileView類別
class JavaFileView extends FileView {
	
	private static final ImageIcon 
		JAVA_ICON = new ImageIcon("icon\\Java.jpg"),
		CLASS_ICON = new ImageIcon("icon\\Class.jpg");
	//定義代表各種圖示的靜態常數屬性

	public String getName(File f){ //取得檔案名稱

		FileSystemView fsv = FileSystemView.getFileSystemView();
		//取得提供目前系統檔案資訊的FileSystemView物件

		return fsv.getSystemDisplayName(f);
		//傳出目前系統的顯示名稱

		//String name = f.getName(); //取得檔案或資料夾的名稱
		//return ("".equals(name)? f.getPath() : name);
		//判斷取得名稱是否空字串, 
		//是則直接傳回檔案路徑, 反之傳回取得的名稱
	}

	//取得檔案的描述文字
	public String getDescription(File f){
		return getTypeDescription(f);
	}

	//取得檔案類型的描述文字
	public String getTypeDescription(File f){

		String name = f.getName().toLowerCase();
		//取得檔案名稱, 並轉換為小寫英文字母

		//判斷副檔名是否為.java或.class
		if(name.endsWith(".java")){
			return "Java 程式檔";
		}
		else if(name.endsWith(".class")){
			return "Java 類別檔";
		}
		else{
			return name.substring(name.lastIndexOf(".")) + "檔";
			//直接傳回副檔名
		}
	}

	public Icon getIcon(File f){ //取得檔案圖示

		String name = f.getName().toLowerCase();
		//取得檔案名稱, 並轉換為小寫英文字母

		//依照副檔名傳回代表檔案的圖示
		if(name.endsWith(".java")){
			return JAVA_ICON;
		}
		else if(name.endsWith(".class")){
			return CLASS_ICON;
		}

		return null;
	}

	//判斷資料夾是否可被
	public Boolean isTraversable(File f){
		return f.isDirectory();
	}
}