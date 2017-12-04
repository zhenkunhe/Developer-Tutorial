import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件
import java.io.*;

public class FileDialogEX extends JFrame{

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

	FileDialogEX(){

		JButton btnOpen = new JButton("開啟舊檔"),
			btnSave = new JButton("儲存檔案"),
			btnMultiOpen = new JButton("可複選的自訂檔案對話盒");
		//宣告呼叫檔案對話盒的按鈕

		//定義、註冊回應按鈕ActionEvent事件的監聽器物件
		btnOpen.addActionListener(new ActionListener(){

			//回應按鈕按下事件的方法
			public void actionPerformed(ActionEvent e){

				JFileChooser fcObj = 
					new JFileChooser(DEFAULT_FILE_PATH);
				//宣告檔案選擇器物件

				int result = -1;

				fcObj.setDialogTitle("開啟舊檔");
				//設定對話盒的標題

				JavaFilter jfClass =
					new JavaFilter("class", "Java位元組碼檔 (.class)");
				//宣告篩選位元組碼檔的檔案篩選物件

				fcObj.addChoosableFileFilter(jfClass);
				fcObj.addChoosableFileFilter(
					new JavaFilter("java", "Java原始檔 (.java)"));
				//新增檔案篩選物件

				fcObj.removeChoosableFileFilter(
					fcObj.getAcceptAllFileFilter());
				//移除篩選所有檔案的篩選物件

				fcObj.setFileFilter(jfClass);
				//設定起始選取篩選位元組碼檔的檔案篩選物件

				result = fcObj.showOpenDialog(FileDialogEX.this);
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

		//定義、註冊回應按鈕ActionEvent事件的監聽器物件
		btnSave.addActionListener(new ActionListener(){

			//回應按鈕按下事件的方法
			public void actionPerformed(ActionEvent e){

				JFileChooser fcObj = 
					new JFileChooser(DEFAULT_FILE_PATH);
				//宣告檔案選擇器物件

				int result = -1;

				fcObj.setDialogTitle("儲存檔案");
				//設定對話盒的標題

				result = fcObj.showSaveDialog(FileDialogEX.this);
				//顯示檔案儲存對話盒			

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

		btnMultiOpen.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){

				JFileChooser fcObj = 
					new JFileChooser(DEFAULT_FILE_PATH);
				//宣告檔案選擇器物件

				fcObj.setMultiSelectionEnabled(true);
				//設定可同時選取數個檔案

				fcObj.setApproveButtonMnemonic('T');
				//設定確認執行按鈕的助憶符號

				fcObj.setDialogTitle("選取欲傳送檔案...");
				//設定對話盒標題

				int result = fcObj.showDialog(FileDialogEX.this, "傳送(T)");
				//設定顯示自訂對話盒物件

				if(result == JFileChooser.APPROVE_OPTION){
					File[] aryFile = fcObj.getSelectedFiles();
					//取得檔案選擇器選取的檔案

					String strShow = "選取檔案 : ";

					for(File elm: aryFile)
						strShow = strShow + elm.getName() + ", ";
						//將選取檔案的名稱串連在一起

					lbResult.setText(strShow);
					//設定標籤顯示開啟檔案的名稱
				}
			}
		});

		JPanel plBtn = new JPanel(
				new FlowLayout(FlowLayout.CENTER, 10, 10));
		//宣告放置按鈕的JPanel容器

		plBtn.add(btnOpen); //將按鈕加入容器
		plBtn.add(btnSave);
		plBtn.add(btnMultiOpen);

		Container cp = getContentPane(); //取得內容面版
		cp.add(plBtn); //將包含按鈕的JPanel容器加入內容面版
		cp.add(lbResult, BorderLayout.SOUTH); //加入顯示訊息的標籤
		
		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//設定根面版四周將使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 110);
		setVisible(true);
	}

	public static void main(String args[]) {
		new FileDialogEX(); //宣告視窗框架物件
	}
}