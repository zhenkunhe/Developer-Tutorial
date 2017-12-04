import javax.swing.*; //引用Swing套件
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class MouseEX extends JFrame {

	JButton clickME = new JButton("請按我!"); //建立元件
	JLabel lbMouse = new JLabel("滑鼠事件訊息"), //建立顯示訊息的標籤
				lbMouseButton = new JLabel("按下按鍵");

	//以匿名類別的方式實作MouseListener介面, 
	//宣告回應滑鼠事件的監聽器
	MouseListener mlBtn = new MouseListener() {

		private int clickCount = 0, //記錄按鈕被按下次數的屬性
						 doubleClickCount = 0; //記錄按鈕被按下次數的屬性

		//處理按一下滑鼠按鍵動作的方法
		public void mouseClicked(MouseEvent e) {

			if(e.getClickCount() == 2)
				doubleClickCount++;

			((JButton) e.getSource()).setText("按下一下 : " +
				(clickCount++) + "次 |  連按兩次 : " + doubleClickCount + "次");
		}

		//處理滑鼠游標進入元件的方法
		public void mouseEntered(MouseEvent e) {
			((JButton) e.getSource()).setText("滑鼠游標進入按鈕範圍");
		}

		//處理滑鼠游標離開元件的方法
		public void mouseExited(MouseEvent e) {
			((JButton) e.getSource()).setText("滑鼠游標離開按鈕範圍");
		}

		//處理按下滑鼠按鍵動作的方法
		public void mousePressed(MouseEvent e){

			switch(e.getButton()){ //取得滑鼠被按下的按鍵
				case MouseEvent.BUTTON1 :
					lbMouseButton.setText("滑鼠左鍵被按下");
					break;
				case MouseEvent.BUTTON2 :
					lbMouseButton.setText("滑鼠中間鍵被按下");
					break;
				case MouseEvent.BUTTON3 :
					lbMouseButton.setText("滑鼠右鍵被按下");
					break;
				case MouseEvent.NOBUTTON :
					lbMouseButton.setText("沒有滑鼠按鍵被按下");
					break;
			}
		}

		//處理放開滑鼠按鍵動作的方法
		//即使不處理事件但仍須定義空的回應方法
		public void mouseReleased(MouseEvent e){ }
	};

	//以匿名類別的方式實作MouseMotionListener介面, 
	//宣告回應滑鼠事件的監聽器
	MouseMotionListener mmlFrame = new MouseMotionListener(){
		public void mouseDragged(MouseEvent e){ //滑鼠拖曳
			lbMouse.setText("滑鼠在視窗範圍內 [拖曳] , 游標位置在 ( "
										+ e.getX()  + ", " + e.getY() + " )");
		}

		public void mouseMoved(MouseEvent e){ //滑鼠移動
			lbMouse.setText("滑鼠在視窗範圍內 [移動]  , 游標位置在 ( "
										+ e.getX()  + ", " + e.getY() + " )");
		}
	};

	MouseEX(){

		Box bxBtn = new Box(BoxLayout.X_AXIS);
		bxBtn.add(Box.createHorizontalGlue());
		bxBtn.add(clickME); //加入按鈕元件
		bxBtn.add(Box.createHorizontalGlue());

		Container cp = getContentPane(); //取得內容面版

		Box bxLabel = new Box(BoxLayout.Y_AXIS);

		bxLabel.add(lbMouse); //將標籤元件加入Box容器
		bxLabel.add(Box.createVerticalStrut(10));
		bxLabel.add(lbMouseButton);

		cp.add(bxBtn); //將包含按鈕元件的Box容器加入內容面版
		cp.add(bxLabel, BorderLayout.SOUTH);
		//將包含標籤元件的Box容器加入面版

		clickME.addMouseListener(mlBtn);
		//註冊由mlBtn監聽clickME元件的滑鼠事件

		addMouseMotionListener(mmlFrame);
		//註冊由mmlFrame監聽視窗的滑鼠移動事件

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 250);
		setVisible(true);
	}

	public static void main(String args[]) {
		new MouseEX(); //產生視窗框架物件
	}
}