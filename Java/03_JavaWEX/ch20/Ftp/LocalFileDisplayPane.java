import com.BitC.Controls.*;

import java.io.*;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

//定義顯示客戶端檔案的面版
class LocalFileDisplayPane extends JPanel {

	private JComboBox cmbDevice; //宣告選取磁碟的組合方塊
	private JLabel lbPath; //宣告顯示客戶端目前路徑的標籤
	private FileList flFileList; //宣告顯示檔案列表的清單方塊
	private JButton btnUp = new JButton("上一層");
	//移至目前路徑的上一層資料夾

	private File defPath; //預設瀏覽的路徑

	//建構子
	public LocalFileDisplayPane(String systemName, File defPath) {
		
		this.defPath = defPath; //設定預設瀏覽的路徑

		JPanel jpPath = new JPanel(new BorderLayout(10, 10));
		jpPath.add(new JLabel("路徑 : "), BorderLayout.WEST);

		jpPath.add(getPathLabel()); //將路徑標籤加入容器
		lbPath.setBorder(LineBorder.createGrayLineBorder());
		jpPath.add(btnUp, BorderLayout.EAST);

		btnUp.addActionListener(alUp);
		//註冊監聽上傳按鈕ActionEvent事件的監聽器物件

		JPanel jpDevice = new JPanel(new BorderLayout(10, 10));
		jpDevice.add(new JLabel("磁碟 : "), BorderLayout.WEST);
		jpDevice.add(getDeviceComboBox());
		//將選取磁碟的組合方塊加入容器

		JPanel jpComboBox = new JPanel(new BorderLayout(10, 10));
		jpComboBox.add(jpPath);
		jpComboBox.add(jpDevice, BorderLayout.SOUTH);
		//建立容納選取與顯示磁碟路徑的容器

		setLayout(new BorderLayout(10, 10));
		//設定版面管理物件

		JScrollPane spFileList = new JScrollPane(getFileList());
		 spFileList.setPreferredSize(new Dimension(150, 50));

		add(spFileList);
		add(jpComboBox,  BorderLayout.NORTH);

		Border border = BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(Color.gray, 2),
			systemName, TitledBorder.LEFT, TitledBorder.TOP);
		//宣告標題框線

		setBorder(border); //設定使用標題框線
	}

	//取得供使用者選取磁碟的組合方塊
	public JComboBox getDeviceComboBox(){
		
		if(cmbDevice == null){

			cmbDevice = new JComboBox();
			//宣告組合方塊

			cmbDevice.setModel(
				new DefaultComboBoxModel(File.listRoots()));
			//設定組合方塊清單使用的Model物件

			cmbDevice.setSelectedItem(defPath);
			//設定選取的預設路徑

			cmbDevice.addItemListener(ilDevice);
			//註冊監聽ItemEvent事件的監聽器物件
		}

		return cmbDevice; //傳出組合方塊
	}

	//取得路徑標籤
	public JLabel getPathLabel(){

		if(lbPath == null){  //判斷路徑標籤是否為null
			lbPath = new JLabel(); //宣告標籤
			lbPath.setText(defPath.getPath());
			//設定標籤顯示預設路徑的字串
		}

		return lbPath; //傳出預設路徑
	}

	public FileList getFileList(){ //取得顯示檔案列表的清單方塊

		if(flFileList == null){
			flFileList = new FileList();
			//宣告顯示檔案列表的檔案清單方塊

			flFileList.setPath(defPath);
			//設定檔案清單方塊欲列出檔案的路徑

			flFileList.setCellRenderer(new DirListCellRenderer());
			//設定檔案清單方塊內選項的轉譯器

			flFileList.setLayoutOrientation(JList.VERTICAL);
			//設定檔案清單方塊的配置方式

			flFileList.setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
			//設定檔案清單方塊的選取模式為可不連續的範圍選取

			flFileList.addMouseListener(maFileList);
			//註冊監聽MouseEvent事件的監聽器物件
		}

		return flFileList;
	}

	public void update(){ //更新面版的畫面
		flFileList.setPath(flFileList.getCurrentPath());
		//設定檔案清單方塊目前列出檔案的資料夾路徑
	}

	//定義宣告監聽ActionEvent事件的監聽器物件
	ActionListener alUp = new ActionListener(){

		//回應ActionEvent事件的方法
		public void actionPerformed(ActionEvent e){

			File curPath = flFileList.getCurrentPath();
			//取得檔案清單方塊目前的路徑

			if(curPath == null) return;
			//判斷路徑是否為null, 是則終止執行方法

			String parent = curPath.getParent();
			//取得目前路徑的上一層路徑

			if(parent == null)	return;
			//若上一層路徑為null, 則終止執行方法
				
			lbPath.setText(parent);
			//設定路徑標籤顯示上一層路徑

			flFileList.setPath(new File(parent));
			//設定檔案清單方塊顯示上一層路徑內的檔案
		}
	};

	//回應組合方塊選取選項引發之ItemEvent事件的監聽器物件
	ItemListener ilDevice = new ItemListener(){

		File file = null;

		//回應組合方塊項目狀態的改變
		public void itemStateChanged(ItemEvent e){

			//判斷狀態是否改變為選取
			if(e.getStateChange() == ItemEvent.SELECTED){

				file = (File) e.getItem();
				//取得選取項目的儲存值, 並轉型為File物件

				lbPath.setText(file.getPath());
				//設定標籤顯示選取項目之儲存值的檔案路徑

				FileListLoader flLoader = new FileListLoader(file);
				//宣告設定清單方塊顯示路徑的執行緒

				flLoader.start();  //啟動執行緒
			}
		}
	};

	//監聽顯示檔案列表之MouseEvent事件的監聽器物件
	MouseAdapter maFileList = new MouseAdapter(){

		//回應滑鼠點選動作
		public void mouseClicked(MouseEvent e){

			//判斷滑鼠按下次數是否為2次
			if(e.getClickCount() == 2){
				FileList fl = (FileList) e.getSource();
				//取得引發事件的元件

				File file = (File)fl.getCurrentPath();
				//取得檔案清單方塊目前的路徑
			
				if(file.isDirectory()) //判斷目前路徑是否為資料夾
					lbPath.setText(file.getPath());
					//設定標籤顯示目前的路徑
			}
		}
	};

	//繼承Thread類別執行清單方塊檢視路徑的設定
	class FileListLoader extends Thread {

		File path; //宣告欲下載檔案的路徑

		FileListLoader(File path){ this.path = path; }
		//建構子

		public void run() {
			if(path != null)	flFileList.setPath(path);
			//設定檔案清單方塊顯示的指定路徑內的檔案列表
		}
	}
}