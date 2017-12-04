import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件
import java.io.*;

import javax.swing.filechooser.*;
//引用定義FileView抽象類別
//與FileSystemView抽象類別的套件

public class UserFileDlgEX extends JFrame{

	private final static 
		String DEFAULT_FILE_PATH = "C:\\JavaWEX";
	//預設的檔案瀏覽路徑

	JLabel lbResult = new JLabel("選取檔案 : ");
	//宣告被選取檔案的標籤

	UserFileDlgEX(){

		JButton btnOpen = new JButton("開啟舊檔");
		//宣告呼叫檔案對話盒的按鈕

		//定義、註冊回應按鈕ActionEvent事件的監聽器物件
		btnOpen.addActionListener(new ActionListener(){

			//回應按鈕按下事件的方法
			public void actionPerformed(ActionEvent e){

				FileDetailDialog fcObj = 
					new FileDetailDialog(DEFAULT_FILE_PATH);
				//宣告檔案選擇器物件

				fcObj.setDialogTitle("開啟舊檔");
				//設定對話盒的標題

				fcObj.setPreferredSize(new Dimension(500, 500)); //設定對話盒的偏好大小

				int result = fcObj.showOpenDialog(UserFileDlgEX.this);
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
		new UserFileDlgEX(); //宣告視窗框架物件
	}
}

//以繼承JFileChooser的方式
//自訂可顯示檔案詳細資料的FileDetailDialog類別
class FileDetailDialog extends JFileChooser {

	JList ltFileDetail = new JList();
	//宣告顯示檔案詳細資料的JList元件

	FileDetailDialog(String path){ super(path); }
	//建構子

	//覆蓋建立對話盒的createDialog()方法
	protected JDialog createDialog(Component parent)
							throws HeadlessException {

		JDialog dialog = super.createDialog(parent);
		//呼叫基礎類別的createDialog()方法

		setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		//設定可以同時選取檔案與資料夾

		Container cpDlg = dialog.getContentPane();
		//取得對話盒的內容面版

		JScrollPane sp = new JScrollPane(ltFileDetail);
		//以JList元件宣告捲軸面版

		sp.setBorder(new javax.swing.border.CompoundBorder( 
			new javax.swing.border.EmptyBorder(5, 5, 5, 5),
			new javax.swing.plaf.metal.MetalBorders.ScrollPaneBorder()));
		//設定捲軸面版使用的組合外框, 外框為寬度為5的空白外框
		//內框為Swing的捲軸面版預設框線

		cpDlg.add(sp, BorderLayout.SOUTH);
		//將包含JList元件的捲軸面版加入內容面版的SOUTH位置

		//註冊監聽PropertyChangeEvent事件的監聽器物件
		addPropertyChangeListener(
			new java.beans.PropertyChangeListener(){

			//回應性質改變動作
			public void propertyChange(
				java.beans.PropertyChangeEvent e){

				//判斷引發事件性質名稱是否為
				//JFileChooser.SELECTED_FILE_CHANGED_PROPERTY
				if(e.getPropertyName() == 
					JFileChooser.SELECTED_FILE_CHANGED_PROPERTY){
					
					DefaultListModel dlm = new DefaultListModel();
					//宣告JList元件使用的內容模型物件

					File f = getSelectedFile();
					//取得對話盒選取的檔案

					//若選取檔案為null, 則終止執行方法
					if(f == null) return;

					FileSystemView fsv =
						FileSystemView.getFileSystemView();
					//取得提供目前系統內
					//檔案或資料夾之資料的FileSystemView物件
					
					//呼叫FileSystemView物件之方法取得檔案的資料
					dlm.addElement("磁碟裝置 : "
						+ (fsv.isDrive(f)? "是" : "否"));
					dlm.addElement("隱藏檔 : "
						+ (fsv.isHiddenFile(f)? "是" : "否"));
					dlm.addElement("系統根資料夾 : "
						+ (fsv.isFileSystemRoot(f)? "是" : "否"));
					dlm.addElement("軟碟機 : "
						+ (fsv.isFloppyDrive(f)? "是" : "否"));

					if(f.isDirectory()){ //判斷File物件是否為資料夾
						int count = f.listFiles().length;
						//取得資料夾內包含之檔案與資料夾的個數

						dlm.addElement("資料夾下檔案或資料夾的個數 : "
															+ count);
						//顯示資料夾內檔案的個數
					}

					ltFileDetail.setModel(dlm);
					//設定JList元件使用包含檔案資料的模型物件
				}
			}
		});

		return dialog; //傳回完成建立的對話盒
	}
}
