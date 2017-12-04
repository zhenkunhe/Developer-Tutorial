import javax.swing.*;  //引用Swing套件
import java.awt.*;
import java.awt.event.*;

//繼承Canvas類別定義顯示圖片的畫布
class ImageCanvas extends Canvas {

	private Dimension d; //畫布大小
	private Image offImage, image; //圖片
	private Graphics offGraphics; //畫布
	private boolean loading, //判斷是否可載入
								firstTime; //判斷是否為第一次
	private int x, y; //圖片左上角座標
	private int lastX, lastY; //圖片左上角的舊座標
	private int w, h; //儲存圖片寬度
 	private Color background = Color.gray; //儲存圖片顯示的背景顏色
	private int lastmag, percent; //顯示比率
	private int maxZoom; //最大放大比率

	//建構子
	public ImageCanvas(int w, int h, int maxZoom){

		loading = true;
		firstTime = true;
		lastmag = 100;
		percent = 100;
		this.maxZoom = 1600;
		this.background = background;

		//判斷最大的放大比率是否大於等於1,
		//是則將放大比率乘以100
		if(maxZoom >= 1)
			this.maxZoom = maxZoom * 100;
		
		//若放大比率小於1, 則直接設定放大比率為100
		if(maxZoom < 1)
			this.maxZoom = 100;

		resize(w, h); //重新調整大小
	}

	//執行繪圖的起始設定
	public void setup(){

		d = size(); //取得畫布大小

		setBackground(Color.white); //設定背景圖

		if(offImage == null){
			offImage = createImage(d.width, d.height);
			//建立圖片

			offGraphics = offImage.getGraphics();
			//建立繪圖內容物件
		}

		offGraphics.setColor(Color.white);
		//設定畫布目前的使用顏色

		offGraphics.fillRect(0, 0, d.width, d.height);
		//在指定範圍填滿顏色

		offGraphics.setColor(Color.black);
		//設定畫布目前的使用顏色
	}

	public void paint(Graphics g){ //繪製畫布

		d = getSize();

		if(!loading){ //判斷是否已經載入

			offGraphics.setColor(background);
			//設定背景顏色

			offGraphics.fillRect(0, 0, d.width, d.height);
			//填滿矩形

			if(x > d.width) x = d.width;
			//判斷x座標是否大於畫布的寬度, 
			//是則設定x座標為寬度

			if(x < 0 - w) x = 0 - w;
			//計算圖片右上角的x座標

			if(y > d.height) y = d.height;
			//判斷y座標是否大於畫布的高度, 
			//是則設定y座標為畫布高度

			if(y < 0 - h) y = 0 - h;
			//計算圖片左上角的y座標

			offGraphics.drawImage(image, x, y, w, h, this);
			//繪製圖片
		}

		g.drawImage(offImage, 0, 0, this); //繪製圖片
	}

	//載入路徑指定的圖片
	public void loadImage(String theImage){

		d = getSize(); //取得畫布的大小

		String strTop = "Loading Image..."; //顯示載入圖片的訊息
		Font f = new Font("Arial", 0, 14); //宣告欲使用的字型

		FontMetrics fm = offGraphics.getFontMetrics(f);
		//取得繪圖使用的字體規格物件

		offGraphics.setFont(f); //設定繪圖使用的字型
		int fontwidth = fm.stringWidth(strTop); //設定字體的寬度
		int textwidth = (d.width - fontwidth) / 2; //設定字串的寬度
		offGraphics.drawString(strTop, textwidth, 50); //繪製字串
		f = new Font("Arial", 0, 12); //宣告欲使用的字型
		fm = offGraphics.getFontMetrics(f); //取得字體規格

		offGraphics.setFont(f); //設定繪圖使用的字型
		repaint(10L); //重繪畫面, 最多使用0.01秒

		image = getToolkit().getImage(theImage);
		//取得路徑所指向圖檔的路徑

		MediaTracker tracker = new MediaTracker(this);
		//宣告追蹤物件狀態的物件

		tracker.addImage(image, 0);
		//將image物件加入追蹤清單, 並設定ID

		try{
			tracker.waitForID(0); //設定等待圖片的ID
		}
		catch(InterruptedException interruptedexception) { }

		loading = false; //設定目前並非載入狀態
	}

	//更新畫面
	public void update(Graphics g){
		paint(g);
	}

	//縮小圖片
	public void zoomOut() {

		//依據縮小比率的大小計算縮小比率
		if(percent <= maxZoom && percent > 100){
			percent = (percent / 25) * 25;
			percent -= 25;
		}
		else if(percent <= 100 && percent > 10){
			percent = (percent / 5) * 5;
			percent -= 5;
		}

		if(percent < 10) percent = 10;
		//若顯示比率小於10, 則設定比率為10

		w = (percent * image.getWidth(this)) / 100;
		//計算圖片的寬度

		h = (percent * image.getHeight(this)) / 100;
		//計算圖片的高度

		magnify(); //執行縮放
		repaint(); //重繪畫面
	}

	//放大圖片
	public void zoomIn(){

		//依據放大比率的大小計算放大比率
		if(percent < maxZoom && percent >= 100){
			percent = (percent / 25) * 25;
			percent += 25;
		}
		else if(percent < 100 && percent >= 10){
			percent = (percent / 5) * 5;
			percent += 5;
		}

		if(percent > maxZoom) percent = maxZoom;
		//判斷放大比率是否大於最大放大比率

		w = (percent * image.getWidth(this)) / 100;
		h = (percent * image.getHeight(this)) / 100;
		//計算圖片的寬度與高度

		magnify(); //執行縮放
		repaint(); //重繪畫面
	}

	//設定顯示圖片的高度與視窗高度相同
	public void fitHeight(){

		d = getSize(); //取得畫布大小
		int xsize = image.getWidth(this),
			ysize = image.getHeight(this);
		//取得圖片的寬、高

		float hratio = (float)d.height / (float)ysize;
		//計算垂直縮放比率

		w = (int)(hratio * (float)xsize);
		h = (int)(hratio * (float)ysize);
		//以縮放比率計算圖片的寬、高

		percent = lastmag = (int)(hratio * 100F);
		//計算縮放比率

		x = (d.width - w) / 2;
		y = (d.height - h) / 2;
		//計算圖片左上角的位置

		repaint(); //重繪圖片
	}

	public void imagecenter(){ //置中顯示圖片

		x = (d.width - w) / 2;
		y = (d.height - h) / 2;
		//計算圖片左上角的位置

		repaint(); //重繪畫面
	}

	public void fitWindow(){ //設定可在視窗內完整顯示圖片

		d = getSize(); //取得畫布的尺寸

		int xsize = image.getWidth(this);
		int ysize = image.getHeight(this);
		//取得圖片的寬、高

		float wratio = (float)d.width / (float)xsize;
		float hratio = (float)d.height / (float)ysize;
		//計算寬與高的放大比率

		//判斷寬與高之縮放比率的大小
		if(wratio <= hratio){ //高度縮放比率大於寬度
			w = (int)(wratio * (float)xsize);
			h = (int)(wratio * (float)ysize);
			//以縮放比率計算圖片的寬度與高度

			percent = lastmag = (int)(wratio * 100F);
			//計算縮放比率
		}
		if(wratio > hratio){ //寬度放大比率大於高度
			w = (int)(hratio * (float)xsize);
			h = (int)(hratio * (float)ysize);
			//以縮放比率計算圖片的寬度與高度

			percent = lastmag = (int)(hratio * 100F);
			//計算縮放比率
		}

		x = (d.width - w) / 2;
		y = (d.height - h) / 2;
		//計算圖片左上角的位置

		repaint(); //重繪視窗
	}

	public void normal(){ //設定圖片以正常大小顯示

		d = getSize(); //取得畫布大小

		percent = 100;
		lastmag = 100;

		w = (percent * image.getWidth(this)) / 100;
		h = (percent * image.getHeight(this)) / 100;
		//依照縮放比率計算圖片寬、高

		x = (d.width - image.getWidth(this)) / 2;
		y = (d.height - image.getHeight(this)) / 2;
		//計算圖片的左上角座標

		repaint(); //重繪
	}

	public void fitWidth(){ //設定完全以圖片寬度顯示

		d = getSize(); //取得畫布大小

		int xsize = image.getWidth(this);
		int ysize = image.getHeight(this);
		//取得圖片的寬、高

		float wratio = (float)d.width / (float)xsize;
		//計算寬度的縮放比率

		w = (int)(wratio * (float)xsize);
		h = (int)(wratio * (float)ysize);
		//以縮放比率計算寬、高

		percent = lastmag = (int)(wratio * 100F);
		//設定縮放比率

		x = (d.width - w) / 2;
		y = (d.height - h) / 2;
		//計算圖片左上角的座標

		repaint(); //重繪畫面
    }

    public void magnify(){ //執行圖片的縮放

		float magnify = percent; //設定縮放比率
		magnify /= lastmag;
		magnify--;
		x = (int)((float)x + magnify * (float)(x - d.width / 2));
		y = (int)((float)y + magnify * (float)(y - d.height / 2));
		//計算圖片左上角座標

		lastmag = percent; //設定圖片的縮放比率
    }

	//釋放滑鼠按鍵
   public boolean mouseUp(Event E, int x, int y){	
		firstTime = true; //設定為true
		return true;
	}

	//滑鼠拖曳圖片
	public boolean mouseDrag(Event e, int x, int y){
		move(x, y); //移動圖片位置
		return true;
	}

	//移動圖片
	public void move(int xLocation, int yLocation) {

		if(firstTime) { //判斷是否為第一次
			firstTime = false; //設定不是第一次
			lastX = xLocation; //設定圖片的上一次位置
			lastY = yLocation;
			return;
		}
		else {
			x += xLocation - lastX; //設定圖片新的左上角座標
			y += yLocation - lastY;

			lastX = xLocation; //上次圖片左上角的位置
			lastY = yLocation;

			repaint(); //重繪圖片
			return;
		}
	}
}