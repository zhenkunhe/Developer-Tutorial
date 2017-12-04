import javax.swing.*;
import javax.swing.event.*; //引用包含ItemListener介面的套件
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class ComboRendererEX extends JFrame{

	JComboBox cmbBook = new JComboBox();
	//以包含選項內容的字串建立組合方塊

	//定義顯示書籍資料的標籤
	JLabel lbImage = new JLabel(), //顯示書籍封面圖檔
				lbPublisher = new JLabel(), //顯示出版商
				lbPDate = new JLabel(), //顯示日期
				lbPrice = new JLabel(); //顯示書籍價格

	Object [] listData = new Object[9]; //儲存組合方塊選項物件的陣列

	//以匿名類別的方式繼承DefaultComboBoxModel類別
	DefaultComboBoxModel cmbModel = new DefaultComboBoxModel(){

		public void setSelectedItem(Object anItem){ //設定選取的選項

			//判斷是否為String, 是則直接終止方法
			if(anItem instanceof String)
				return; //終止方法取消選取

			super.setSelectedItem(anItem);
			//呼叫基礎類別的setSelectedItem()方法
		}
	};

	ComboRendererEX(){

		listData[0] = new String("程式設計");
		listData[1] = new Book("Java 2 入門進階 – 適用JDK6.0",
			"文魁資訊", 590.0, new ImageIcon("image\\P4137.jpg"),
			"2005-5-1");
		listData[2] = new Book("Visual C++.NET 入門進階", 
			"文魁資訊", 750.0, new ImageIcon("image\\P3237.jpg"), 
			"2003-9-1");
		listData[3] = new String("資料庫");
		listData[4] = new Book("Access 2003 徹底研究", 
			"文魁資訊", 590.0, new ImageIcon("image\\A4023.jpg"), 
			"2004-3-1");
		listData[5] = new Book("Access 2003 程式設計", 
			"文魁資訊", 660.0, new ImageIcon("image\\A4033.jpg"),
			"2004-5-1");
		listData[6] = new String("動態網頁");
		listData[7] = new Book("JSP 動態網頁入門實務", 
			"文魁資訊", 720.0, new ImageIcon("image\\W3135.jpg"), 
			"2003-12-1");
		listData[8] = new Book("ASP 動態網頁入門實務", 
			"文魁資訊", 580.0, new ImageIcon("image\\W4075.jpg"), 
			"2004-7-1");
		//宣告書籍資料物件

		cmbBook.setRenderer(new BookCellRenderer());
		//設定組合方塊選項使用的轉譯器

		cmbBook.setModel(cmbModel); //設定使用的Model物件

		//將書籍資料加入組合方塊做為選項
		for(Object elm : listData)
			cmbModel.addElement(elm); //將選項加入Model物件

		cmbBook.setSelectedIndex(1); //設定選項的項目

		lbPublisher.setHorizontalAlignment(JLabel.RIGHT);
		lbPDate.setHorizontalAlignment(JLabel.RIGHT);
		lbPrice.setHorizontalAlignment(JLabel.RIGHT);
		lbImage.setHorizontalAlignment(JLabel.CENTER);
		//設定標籤內文字的水平對齊方式

		lbPublisher.setText(((Book)listData[1]).getPublisher());
		lbPDate.setText(((Book)listData[1]).getPublishDate());
		lbPrice.setText(
					String.valueOf(((Book)listData[1]).getPrice()));
		lbImage.setIcon(((Book)listData[1]).getCoverImage());
		//設定標籤顯示書籍資料

		//定義並宣告回應ItemEvent事件的監聽器物件
		cmbBook.addItemListener(new ItemListener(){

			//回應改變選項狀態的動作
			public void itemStateChanged(ItemEvent e){

				Book item = (Book)e.getItem(); //取得被選取選項的物件

				lbPublisher.setText(item.getPublisher()); //設定書籍資料
				lbPDate.setText(item.getPublishDate());
				lbPrice.setText(String.valueOf(item.getPrice()));				
				lbImage.setIcon(item.getCoverImage()); //設定顯示書籍封面
			}
		});

		//建立包含各元件的Box容器, 並將元件加入
		Box bxBook = new Box(BoxLayout.X_AXIS);
		bxBook.add(new JLabel("書籍 : ", JLabel.RIGHT));
		bxBook.add(cmbBook); //加入組合方塊

		JPanel jpDetail = new JPanel(new GridLayout(3, 2, 10, 5));
		jpDetail.add(new JLabel("出版商 : ", JLabel.RIGHT));
		jpDetail.add(lbPublisher); //加入顯示出版商的標籤
		jpDetail.add(new JLabel("出版日期 : ", JLabel.RIGHT));
		jpDetail.add(lbPDate); //加入顯示出版日期的標籤
		jpDetail.add(new JLabel("價格 : ", JLabel.RIGHT));
		jpDetail.add(lbPrice); //加入顯示書籍價格的標籤

		JPanel jpMain = new JPanel(new BorderLayout(10, 10));
		jpMain.add(bxBook, BorderLayout.NORTH); //將Box容器加入主面版
		jpMain.add(lbImage); //加入顯示書籍封面圖片的標籤
		jpMain.add(jpDetail,  BorderLayout.EAST);
		//加入顯示詳細資料的面版

		jpMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		//設定顯示資料的主要面版使用寬度為5的空白框線

		Container cp = getContentPane(); //取得內容面版

		cp.add(jpMain); //將Box容器加入內容面版

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		pack();
		setVisible(true);
	}

	public static void main(String args[]) {
		new ComboRendererEX(); //宣告視窗框架物件
	}
}

class Book { //定義儲存書籍資料的Book物件

	private String title, publisher;
	private double price;
	private ImageIcon coverImage;
	private String p_date;

	//Book物件的建構子
	public Book(String title, String publisher, double price, 
									ImageIcon coverImage, String p_date){
		this.title = title;
		this.publisher = publisher;
		this.price = price;
		this.coverImage = coverImage;
		this.p_date = p_date;
	}

	public String toString(){ //將Book物件轉換為String物件
		return title;
	}

	public String getTitle(){ //取得書籍的標題
		return title;
	}

	public String getPublisher(){ //取得書籍的出版商
		return publisher;
	}

	public double getPrice(){ //取得書籍的價格
		return price;
	}

	public ImageIcon getCoverImage(){ //取得書籍封面的圖片
		return coverImage;
	}

	public String getPublishDate(){ //取得出版日期
		return p_date;
	}
}

//以繼承DefaultListCellRenderer的方式
//定義繪製組合方塊清單選項的BookCellRenderer類別
class BookCellRenderer extends DefaultListCellRenderer {

	public Component getListCellRendererComponent(
										JList list,
										Object value,
										int index,
										boolean isSelected,
										boolean cellHasFocus){

		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		//先呼叫基礎類別的getListCellRendererComponent()方法

		if(value instanceof String){ //判斷選項物件是否為字串
			setText((String) value); //設定選項顯示的內容
			setBackground(Color.gray); //設定選項的背景顏色
			setIcon(new ImageIcon("image\\icon.jpg"));
			//設定選項使用的圖示
		}
		else{
			setText(((Book)value).getTitle()); //設定以書籍名稱為選項文字
			setBorder(new EmptyBorder(0, 10, 0, 0));
			//運用空白框線設定選項左方出現空白
		}

		return this;
	}
}