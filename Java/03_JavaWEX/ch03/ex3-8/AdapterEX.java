import javax.swing.*; //引用Swing套件
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class AdapterEX extends JFrame{

	JButton clickME = new JButton("請按我!"); //建立元件
	JLabel lbPos = new JLabel("顯示滑鼠相關訊息");

	MouseListener mlFrame = new MouseListener(){
		public void mouseClicked(MouseEvent e){
			lbPos.setText("在 (" + e.getX() + ", " + e.getY() + ") 按下滑鼠");
		}
		public void mouseEntered(MouseEvent e){
			lbPos.setText("滑鼠游標進入視窗");
		}
		public void mouseExited(MouseEvent e){
			lbPos.setText("滑鼠游標離開視窗");
		}

		//即使不處理事件但仍須定義空的回應方法
		public void mousePressed(MouseEvent e){ }
		public void mouseReleased(MouseEvent e){ }	
	};

	//運用匿名內部類別, 以繼承MouseAdaptor的方式定義監聽器類別, 並完成宣告
	MouseAdapter maBtn = new MouseAdapter(){

		int clickCount = 0; //記錄按鈕被按下次數的屬性

		public void mouseClicked(MouseEvent e){
			((JButton) e.getSource()).setText(
				"按鈕被按了" + (clickCount++) + "次");
		}
		public void mouseEntered(MouseEvent e){
			((JButton) e.getSource()).setText("滑鼠游標進入按鈕範圍");
		}
		public void mouseExited(MouseEvent e){
			((JButton) e.getSource()).setText("滑鼠游標離開按鈕範圍");
		}	
	};

	AdapterEX(){
		Container cp = getContentPane(); //取得內容面版

		Box bxBtn = new Box(BoxLayout.X_AXIS);

		bxBtn.add(Box.createHorizontalGlue());
		bxBtn.add(clickME); //將元件加入Box容器
		bxBtn.add(Box.createHorizontalGlue());

		cp.add(bxBtn); //將Box容器加入內容面版
		cp.add(lbPos, BorderLayout.SOUTH);

		clickME.addMouseListener(maBtn);
		//註冊由maBtn監聽clickME元件的滑鼠事件

		addMouseListener(mlFrame);
		//註冊由mlFrame監聽視窗的滑鼠事件

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(200, 150);
		setVisible(true);
	}

	public static void main(String args[]) {
		new AdapterEX(); //產生視窗框架物件
	}
}