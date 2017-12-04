import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class ScrollEX extends JFrame{

	ImageIcon ii = new ImageIcon("image\\P4137All.jpg");
	//宣告容納圖片的ImageIcon物件

	JLabel lbImage = new JLabel(ii);	//顯示圖片的標籤

	DefaultBoundedRangeModel brmHor =
		new DefaultBoundedRangeModel(0, 0, 0, 0);
	//水平捲軸使用的Model物件

	JScrollBar sbHor = new  JScrollBar(JScrollBar.HORIZONTAL),
					  sbVer = new JScrollBar(JScrollBar.VERTICAL, 0, 0, 0, 0);
	//宣告水平方向與垂直方向的捲軸


	JPanel jpImage =  new JPanel(); //宣告容納圖片的容器物件
	JViewport vp = new JViewport(); //宣告顯示圖片的檢視區

	JLabel lbHor = new JLabel("水平捲軸 (value, min, max) : "),
				lbVer = new JLabel("垂直捲軸 (value, min, max) : "),
				lbHorPos = new JLabel("0", JLabel.LEFT),
				lbVerPos = new JLabel("0", JLabel.LEFT),
				lbVPP = new  JLabel("可視區原點 : "),
				lbVPPos = new JLabel(),
				lbVPS = new JLabel("可視區大小 : "),
				lbVPSize = new JLabel();
	//宣告顯示選標籤元件

	//更新視窗上方顯示的資訊
	private void updateMsg(){

		Point position = vp.getViewPosition();
		//取得檢視區原點在View物件的座標

		lbVerPos.setText(" ("  + sbVer.getValue() + " ," 
									+ sbVer.getMinimum() + " ," 
									+ sbVer.getMaximum() + ") ");
		//依序顯示垂直捲軸的值、最小值與最大值

		lbHorPos.setText(" ("  +  sbHor.getValue() + " ," 
									+ sbHor.getMinimum() + " ," 
									+ sbHor.getMaximum() + ") ");
		//依序顯示水平捲軸的值、最小值與最大值

		lbVPPos.setText(" (" + position.x + " ,"
									 +	position.y +  ") ");
		//顯示檢視區原點在View物件座標的位置

		lbVPPos.setText("(" + position.x + " ,"
									+ position.y +  ") ");
		//顯示檢視區原點在View物件座標的位置
	}

	ScrollEX(){

		sbHor.setModel(brmHor);
		//設定水平方向捲動軸使用的Model物件

		sbHor.setUnitIncrement(10); //設定按下箭頭按鈕的單位增量
		sbHor.setBlockIncrement(5); //設定按下捲軸區塊的捲動增量

		jpImage.add(lbImage); //將顯示圖片的標籤加入圖片容器物件
		vp.setView(jpImage); //設定檢視區顯示的View物件

		//註冊回應水平捲軸ChangeEvent事件的監聽器物件
		brmHor.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){

				DefaultBoundedRangeModel  source = 
							(DefaultBoundedRangeModel ) e.getSource();
				//取得引發事件的Model物件

				Point position = vp.getViewPosition();
				//取得目前檢視區左上角原點在View物件之座標的位置

				position.x = source.getValue();
				//取得引發事件之捲軸的設定值

				updateMsg(); //更新視窗上方顯示的資訊

				vp.setViewPosition(position); //設定檢視區原點的位置
				vp.repaint(); //重繪畫面
			}
		});

		//註冊監聽AdjustmentEvent事件的監聽器物件
		sbVer.addAdjustmentListener(new AdjustmentListener(){

			public void adjustmentValueChanged(AdjustmentEvent e){

				Point position = vp.getViewPosition();
				//取得撿視區原點在View物件的位置

				JScrollBar sb = (JScrollBar)e.getAdjustable();
				//取得引發事件的捲軸

				position.y = e.getValue();
				//將捲軸的值設定給檢視區原點的y座標

				vp.setViewPosition(position);
				//設定檢視區原點的在View物件的座標位置

				vp.repaint(); //重繪視窗

				updateMsg(); //更新視窗上方顯示的資訊
			}
		});

		addWindowListener(new WindowAdapter(){
			public void windowOpened(WindowEvent e){

				Dimension dm = vp.getExtentSize();
				//取得檢視區可視範圍的大小

				sbHor.setBorder(new EmptyBorder(0, 0, 0, sbVer.getWidth()));
				//設定水平捲軸使用空白框線, 
				//左方將出現與垂直捲軸寬度相同的空白間隙

				lbVPSize.setText(" [ " + dm.getWidth() 
												+ " x " + dm.getHeight() + "] ");
				//設定檢視區的大小
				
				int intHorMax = ii.getIconWidth() 
								- new Double(dm.getWidth()).intValue();
				//以圖片大小減去可視範圍大小計算水平捲軸的最大值

				sbHor.setMaximum(intHorMax > 0 ? intHorMax : 0);
				//設定水平捲軸的最大值為圖片寬度減去圖片容器目前的寬度

				int intVerMax = ii.getIconHeight() - new Double(dm.getHeight()).intValue();

				sbVer.setMaximum(intVerMax > 0 ? intVerMax : 0);
				//設定垂直捲軸的最大值為圖片高度減去圖片容器目前的高度
			}
		});

		//註冊回應ComponentEvent事件的監聽器物件
		addComponentListener(new ComponentAdapter(){	

			//回應元件大小調整
			public void componentResized(ComponentEvent e){

				Dimension dm = vp.getExtentSize();
				//取得檢視區可視範圍的大小

				lbVPSize.setText(" [ " + dm.getWidth() 
												+ " x " + dm.getHeight() + "] ");
				//設定檢視區的大小
				
				int intHorMax = ii.getIconWidth() 
								- new Double(dm.getWidth()).intValue();
				//以圖片大小減去可視範圍大小計算水平捲軸的最大值

				sbHor.setMaximum(intHorMax > 0 ? intHorMax : 0);
				//設定水平捲軸的最大值為圖片寬度減去圖片容器目前的寬度

				int intVerMax = ii.getIconHeight() - new Double(dm.getHeight()).intValue();

				sbVer.setMaximum(intVerMax > 0 ? intVerMax : 0);
				//設定垂直捲軸的最大值為圖片高度減去圖片容器目前的高度
			}
		});

		MouseInputAdapter mia = new MouseInputAdapter(){

			private boolean firstTime  = true; //判斷是否為第一次執行

			private Point lastPos = new Point();
			//紀錄滑鼠上一次的拖曳座標

			public void mouseReleased(MouseEvent e){

				firstTime = true; //重設firstTime值

				Point position = vp.getViewPosition();
				//取得撿視區原點在View物件的位置

				sbVer.setValue(position.y);
				sbHor.setValue(position.x);
				//取得引發事件的捲軸
			}

			public void mouseDragged(MouseEvent e){
				Point position = vp.getViewPosition();
				//取得撿視區原點在View物件的位置

				if(firstTime) {
					firstTime = false;
					lastPos.setLocation(e.getX(), e.getY());
				}
				else{
					double newX = position.getX() 
									- (e.getX() - lastPos.getX());
					//計算新的X軸座標

					//當X座標小於0, 則設定為0
					//若X座標大於捲軸最大值, 則設定為捲軸最大值
					if(newX < 0.0)
						newX = 0.0;
					else if(newX > sbHor.getMaximum())
						newX = sbHor.getMaximum();
	
					double newY = position.getY()
									- (e.getY() - lastPos.getY());
					//計算新的Y座標

					//當Y座標小於0, 則設定為0
					//若Y座標大於捲軸最大值, 則設定為捲軸最大值
					if(newY < 0.0)
						newY = 0.0;
					else if(newY > sbVer.getMaximum())
						newY = sbVer.getMaximum();

					lastPos.setLocation(e.getX(), e.getY());
					//將滑鼠座標設定給lastPos物件供下次使用

					updateMsg();

					vp.setViewPosition(new Point(
							(new Double(newX)).intValue() ,
							(new Double(newY)).intValue()));
					//設定檢視區原點在View物件的座標
					vp.repaint(); //重繪檢視區

				}
			}
		};

		//註冊回應ComponentEvent事件的監聽器物件
		vp.addMouseListener(mia);
		vp.addMouseMotionListener(mia);

		//建立顯示捲軸資訊的Box物件
		Box bxSBMsg = new Box(BoxLayout.X_AXIS);
		bxSBMsg.add(lbHor);
		bxSBMsg.add(lbHorPos);
		bxSBMsg.add(Box.createHorizontalStrut(10));
		bxSBMsg.add(lbVer);
		bxSBMsg.add(lbVerPos);

		//建立顯示檢視區資料的Box物件
		Box bxVPMsg = new Box(BoxLayout.X_AXIS);
		bxVPMsg.add(lbVPP);
		bxVPMsg.add(lbVPPos);
		bxVPMsg.add(Box.createHorizontalStrut(10));
		bxVPMsg.add(lbVPS);
		bxVPMsg.add(lbVPSize);

		JPanel jpMsg = new JPanel(new GridLayout(2, 1));
		jpMsg.add(bxSBMsg);
		jpMsg.add(bxVPMsg);

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new BorderLayout(2, 2));
		//設定內容面版水平與垂直間距均為10的BorderLayout物件管理版面

		add(jpMsg, BorderLayout.NORTH); //加入顯示資訊的面版物件
		add(vp); //加入顯示區
		add(sbHor, BorderLayout.SOUTH); //加入水平捲軸
		add(sbVer, BorderLayout.EAST); //加入垂直捲軸

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(600, 400);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ScrollEX(); //宣告視窗框架物件
	}
}