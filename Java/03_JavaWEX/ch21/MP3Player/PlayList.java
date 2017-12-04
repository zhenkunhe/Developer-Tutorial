import javax.media.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

import javax.swing.filechooser.FileFilter;

//宣告繼承JList類別顯示播放清單的播放清單方塊
class PlayList extends JList {

	protected DefaultListModel dlm = new DefaultListModel();
	//宣告儲存清單方塊選項內容的Model物件

	public PlayList(){	
		dlm.addElement("最喜愛的歌曲");
		//加入預設的播放清單
	}

	//儲存清單方塊的Model物件
	public void save(String fileName){
		try{
			FileOutputStream fos = new FileOutputStream(fileName);
			//宣告儲存Model物件的檔案串流

			ObjectOutputStream oos = new ObjectOutputStream(fos);
			//宣告輸出Model物件的物件串流

			oos.writeObject(getModel());
			//將清單方塊的Model物件寫入物件串流

			oos.flush(); //清除緩衝區
			oos.close(); //關閉物件串流
			fos.close(); //關閉檔案串流
		}
		catch(Exception ex){
				ex.printStackTrace();
				System.err.println(
					"瀏覽位址紀錄儲存錯誤 : " + ex.getMessage());
		}
	}

	public void load(String fileName){ //從檔案載入Model物件
		try{
			File flModel = new File(fileName);
			//宣告儲存Model物件的檔案

			if(!flModel.exists()){ //判斷檔案是否存在
				setModel(dlm); //設定清單方塊使用的Model物件
				return;
			}			

			FileInputStream fis = new FileInputStream(flModel);
			//宣告檔案輸入串流

			ObjectInputStream ois = new ObjectInputStream(fis);
			//宣告物件輸入串流

			Object obj = ois.readObject(); //從串流讀入物件

			//判斷物件型別是否為DefaultListModel
			if(obj instanceof DefaultListModel){
				setModel((DefaultListModel)obj);
				//設定清單方塊儲存選項的Model物件
			}
			ois.close(); //關閉物件串流
			fis.close(); //關閉檔案串流
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.err.println(
				"瀏覽位址紀錄讀取錯誤 : " +  ex.getMessage());
		}
	}
}

//繼承PlayList類別定義儲存播放歌曲的清單方塊
class SongList extends PlayList {

	private int curPlay = -1; //設定目前播放歌曲的索引值
	private Player player = null; //設定播放器物件
	private JPanel jpControl = null; //儲存播放器控制項的容器
	private Component controlPane = null; //宣告播放器的控制面版

	static final int ALL = 1,ONE = 2, AUTO = 3;
	//設定代表播放方式的常數

	private int playStatus = SongList.ALL; //設定播放狀態

	public SongList(JPanel jpControl){ //建構子

		dlm.clear();

		setCellRenderer(new SongListCellRenderer());
		//設定清單方塊使用的轉譯器物件

		setSelectionMode(
			ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
		//設定清單方塊的選取模式

		this.jpControl = jpControl; //設定容納播放器控制項的面版
	}

	public void setPlayStatus(int status){ //設定歌曲清單的播放方式
		playStatus = status;
	}

	public int getCurrentPlay(){ //取得目前播放歌曲的索引值
		return curPlay;
	}

	//設定目前播放歌曲的索引值
	public void setCurrentPlay(int index){
		curPlay = index;
	}

	public void play(){ //播放mp3
		try{
			if(player != null){ //判斷播放器物件是否為null
				jpControl.remove(controlPane); //移除播放氣得控制面版
				player.removeControllerListener(ca);
				//移除監聽ControllerEvent事件的監聽器物件

				player.stop(); //停止播放器
				player.close(); //關閉播放器
				player = null; //重設物件參考
			}

			Object selValue = 
				((DefaultListModel)getModel()).elementAt(curPlay);
			//取得目前播放之歌曲索引值

			player = Manager.createPlayer(
				new URL(new String("file:" + ((File)selValue).getPath())));
			//建立播放器物件
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}

		player.addControllerListener(ca);
		//註冊監聽ControllerEvent事件的監聽器物件

		player.realize(); //將控制項的狀態設定為實現
	}

	//註冊監聽播放器控制項的監聽器物件
	ControllerAdapter ca = new ControllerAdapter(){

		Player player = null;

		//回應已完成載入的方法
		public void realizeComplete(RealizeCompleteEvent e){
			player = (Player) e.getSource(); //取得引發事件的播放器物件
			player.prefetch();
		}

		//回應完成準備載入動作的方法
		public void prefetchComplete(PrefetchCompleteEvent e){

			player = (Player) e.getSource(); //取得引發事件的播放器物件

			controlPane = player.getControlPanelComponent();
			//取得播放器物件的控制項

			controlPane.setSize(new Dimension(300, 40));
			//設定控制項的大小

			jpControl.add(controlPane);
			//將播放器控制項加入容納的容器

			validate(); //驗證
			updateUI(); //更新畫面

			player.start(); //啟動播放器
		}

		//結束媒體播放的方法
		public void endOfMedia(EndOfMediaEvent e){

			player = (Player) e.getSource();
			//取得引發事件的播放器物件

			//依照播放狀態執行播放動作
			if(playStatus == SongList.ONE){ //重複播放單一歌曲			
				player.setMediaTime(new Time(0));
				//設定開始播放的位置

				player.start(); //開始播放
			}
			else{

				DefaultListModel dlm = (DefaultListModel) getModel();
				//取得儲存歌曲清單方塊的Model物件

				int size = dlm.getSize(); //取得清單內選項的個數
				int index = getCurrentPlay();	//取得目前播放歌曲的索引值
				
				if(playStatus == SongList.ALL){
				//判斷是否播放清單內的所有歌曲

					if(index < (size-1)){
					//判斷播放歌曲的索引是否小於選項個數減1
						setCurrentPlay(index + 1);
						//設定下一首為播放歌曲
					}
					else
						return;
				}
				else if(playStatus == SongList.AUTO){
				//判斷是否重複播放歌曲清單的所有歌曲

					if(index < (size-1)){
					//判斷播放歌曲的索引是否小於選項個數減1

						setCurrentPlay(index + 1);
						//設定下一首為播放歌曲
					}
					else{
						setCurrentPlay(0);
						//設定播放第一首歌曲
					}
				}

				play(); //播放歌曲
			}
		}
	};
}


//繼承DefaultListCellRenderer類別
//宣告處理歌曲清單選項外觀的轉譯器物件
class SongListCellRenderer extends DefaultListCellRenderer {

	//取得選項的轉譯器物件
	public Component getListCellRendererComponent(
					JList list,
					Object value,
					int index,
					boolean isSelected,
					boolean cellHasFocus) {

		super.getListCellRendererComponent(
			list, value, index, isSelected, cellHasFocus);
		//呼叫基礎類別的方法, 取得轉譯器物件

		//若未選取值則終止執行方法
		if(value == null) return this;

		String text = ((File)value).getName();
		//取得選項儲存物件的檔案名稱

		setText(text); //設定選項的名稱

		//判斷目前播放歌曲索引值
		//是否等於取得轉譯器之選項的索引值
		if(((SongList)list).getCurrentPlay() == index)
			setIcon(new ImageIcon("image/play.gif"));
			//設定項目使用的圖示

		text = text.toLowerCase();	 //轉換為小寫英文字母

		setBackground(
			isSelected ? Color.lightGray : Color.white);
		//依照選取狀態設定項目的背景顏色

		setForeground(
			isSelected ? Color.darkGray : Color.black);
		//依照選取狀態設定項目的前景顏色

		if(isSelected)
			setBorder(
				BorderFactory.createLineBorder(Color.darkGray, 1));
			//依照選取狀態設定選項的框線

		return this;
	}
}