import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

import cz.dhl.io.*;
import cz.dhl.ftp.*;

//定義顯示伺服端檔案的面版
public class RemoteFileDisplayPane extends JPanel {

	private static String UNCONNECT = "尚未連線"; //顯示尚未連線的訊息
	private Ftp cl; //處理連線的FTP物件
	private JLabel lbPath; //顯示連線路徑的標籤
	private RemoteFileList rflFileList; //伺服端檔案列表的清單方塊
	private JButton btnUp = new JButton("上一層");
	//移至上一層資料夾的按鈕

	public RemoteFileDisplayPane(String systemName){ //建構子
		
		JPanel jpComboBox = new JPanel();
		//容納組合方塊的面版

		jpComboBox.setLayout(new BorderLayout(10, 10));
		//設定使用的版面管理員

		jpComboBox.add(new JLabel("路徑 : "), BorderLayout.WEST);
		jpComboBox.add(getPathLabel()); //取得顯示路徑的標籤
		jpComboBox.add(btnUp, BorderLayout.EAST);		
		//將元件加入面版

		lbPath.setBorder(LineBorder.createGrayLineBorder());
		//設定路徑標籤使用的框線

		setLayout(new BorderLayout(10, 10));

		add(jpComboBox,  BorderLayout.NORTH);
		//將放置組合方塊的Box容器加入面版

		JScrollPane spFileList = new JScrollPane(getRemoteFileList());	
		spFileList.setPreferredSize(new Dimension(150, 50));
		//建立捲軸面版, 並設定適當大小

		add(spFileList); //加入捲軸面版

		Border border = BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(Color.gray, 2),
			systemName, TitledBorder.LEFT, TitledBorder.TOP);
		//宣告標籤框線

		setBorder(border); //設定面版使用標籤框線

		btnUp.addActionListener(alUp);
		//註冊監聽上一層按鈕ActionEvent事件的監聽器物件

		rflFileList.setCellRenderer(new DirListCellRenderer());
		//設定列出檔案之清單方塊使用的轉譯器物件

		rflFileList.addMouseListener(maFileList);
		//註冊監聽MouseEvent事件的監聽器物件
	}

	public void setFtpConnection(Ftp ftp){ cl = ftp; }
	//設定處理FTP連線的物件
	
	public JLabel getPathLabel(){ //取得顯示路徑的標籤
		if(lbPath == null){
			lbPath = new JLabel(); //宣告顯示路徑的標籤
		}

		return lbPath;
	}

	//取得顯示伺服端檔案列表的清單方塊
	public RemoteFileList getRemoteFileList(){

		if(rflFileList == null){
			rflFileList = new RemoteFileList();
			//宣告顯示伺服端檔案列表的清單方塊
		}

		return rflFileList;
	}

	public void update(){ rflFileList.update();}
	//更新顯示伺服端的檔案清單

	public void reset(){ //重設伺服端面版的顯示內容
		lbPath.setText(null);
		rflFileList.clear();
	}

	//宣告監聽執行上傳動作之按鈕ActionEvent事件的監聽器物件
	private ActionListener alUp = new ActionListener(){

		public void actionPerformed(ActionEvent e){

			if(cl ==  null) return; //若未連線則終止方法的執行

			FtpFile curPath  = rflFileList.getCurrentPath();
			//取得伺服端目前的路徑

			if(curPath == null) return; //若無法取得路徑則終止執行方法

			String parent = curPath.getParent(); //取得上一層資料夾

			if(parent == null)	return;
			//若無法取得上一層資料夾則終止執行方法
			
			lbPath.setText(parent); //設定顯示上一層路徑

			rflFileList.setPath(new FtpFile(parent, cl));
			//設定顯示伺服端檔案清單之清單方塊的路徑
		}
	};

	//宣告回應清單方塊之MouseEvent事件的監聽器物件
	private MouseAdapter maFileList = new MouseAdapter(){

		public void mouseClicked(MouseEvent e){ //回應滑鼠按下事件

			if(e.getClickCount() == 2){ //判斷滑鼠按下次數是否為2次

				RemoteFileList fl = (RemoteFileList) e.getSource();
				//取得引發事件的清單方塊

				FtpFile file = (FtpFile)fl.getSelectedValue();
				//取得清單方塊的選取項目

				if(file.isDirectory()){ //判斷取得的項目是否為資料夾
					lbPath.setText(file.toString());
					//設定標籤顯示伺服端的路徑

					fl.setPath(file); //設定顯示的路徑
				}
			}
		}
	};
}

//定義繼承JList類別顯示伺服端檔案列表的清單方塊
class RemoteFileList extends JList {

	private FtpFile curPath; 	//儲存目前顯示的路徑

	public RemoteFileList(){ initialize(); } //建構子

	public void clear(){
		setModel(new DefaultListModel());
		//清除清單方塊顯示內容的Model物件

		curPath = null; //清除目前顯示檔案列表的路徑
	}

	private void initialize(){ //起始清單方塊
		setModel(new DefaultListModel());
		//設定清單方塊顯示的Model物件

		setSelectionMode(
			ListSelectionModel.SINGLE_INTERVAL_SELECTION );
		//設定清單方塊的選取模式
	}

	//設定清單方塊顯示的路徑
	public void setPath(FtpFile directory){

		curPath = directory; //設定伺服端欲瀏覽的路徑

		CoFile[] files = directory.listCoFiles();
		//列出伺服端的檔案

		if(files !=  null){ //判斷是否取得檔案
			setListData(files); //設定清單方塊內顯示的選項
		}
		else{
			setModel(new DefaultListModel());
			//清除清單方塊內容
		}
	}
	
	public FtpFile getCurrentPath(){ return curPath; }
	//取得目前伺服端的路徑

	public void update(){ setPath(curPath);}
	//更新目前顯示之伺服端路徑
}