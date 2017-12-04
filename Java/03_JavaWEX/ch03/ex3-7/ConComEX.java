import javax.swing.*; //引用Swing套件
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class ConComEX extends JFrame {

	JLabel lbMsg = new JLabel("ComponentEvent事件的回應示範",
													JLabel.CENTER);

	JButton btnAction = new JButton("移除元件");
	
	//以匿名類別實作ComponentListener介面, 
	//並宣告監聽ComponentEvent事件的監聽器物件
	ComponentListener clFrame = new ComponentListener(){
		//回應隱藏元件行為的方法
		public void componentHidden(ComponentEvent e){
			lbMsg.setText("ComponentEvent : 隱藏元件");
		}
		//回應移動元件行為的方法
		public void componentMoved(ComponentEvent e){
			lbMsg.setText("ComponentEvent : 移動元件");
		}
		//回應調整元件大小行為的方法
		public void componentResized(ComponentEvent e){
			lbMsg.setText("ComponentEvent : 調整元件大小");
		}
		//回應隱藏元件行為的方法
		public void componentShown(ComponentEvent e){
			lbMsg.setText("ComponentEvent : 顯示元件");
		}
	};

	//以匿名類別實作ContainerListener介面, 並宣告監聽器物件
	ContainerListener conlFrame = new ContainerListener(){
		//回應元件加入行為的方法
		public void componentAdded(ContainerEvent e){
			 btnAction.setText("移除元件"); //設定按鈕顯示的文字
		}
		//回應元件移除行為的方法
		public void componentRemoved(ContainerEvent e){
			 btnAction.setText("加入元件");
		}
	};

	ConComEX(){

		//以匿名類別的方式宣告回應ActionEvent事件的監聽器,
		//並呼叫addActionListener()方法註冊監聽器物件
		btnAction.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){
				JButton source = (JButton) e.getSource();
				//取得事件來源的按鈕元件

				if(source.getText().equals("加入元件")){
					ConComEX.this.add(lbMsg);
					//呼叫外部類別的add()方法加入標籤元件
				}
				else{
					ConComEX.this.remove(lbMsg);
					//呼叫外部類別的add()方法移除標籤元件
				}

				ConComEX.this.repaint();
				//呼叫外部類別的repaint()方法重繪視窗畫面
			}
		});

		add(lbMsg); //將顯示訊息的標籤加入內容面版
		add(btnAction, BorderLayout.SOUTH); //將按鈕加入內容面版
		
		addComponentListener(clFrame);

		getContentPane().addContainerListener(conlFrame);
		//註冊由conlFrame監聽視窗的ContainerEvent

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250, 150);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ConComEX(); //產生視窗框架物件
	}
}