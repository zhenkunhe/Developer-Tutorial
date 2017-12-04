import javax.swing.*;  //引用套件
import java.awt.*;

public class TitleSeparatorEX extends JFrame{

	TitleSeparatorEX(){

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new GridLayout(2, 3, 10, 10));
		//設定內容面版的佈局管理員物件

		cp.add(new TitleSeparator(new JLabel("標題在左")));
		cp.add(new TitleSeparator(new JLabel("標題在上"), 
					100, TitleSeparator.TITLE_HORIZONTAL_TOP));
		cp.add(new TitleSeparator(new JLabel("標題在頂端"), 
					100, TitleSeparator.TITLE_VERTICAL_TOP));

		cp.add(new TitleSeparator(new JLabel("標題在右"),  
					TitleSeparator.TITLE_RIGHT));
		cp.add(new TitleSeparator(new JLabel("標題在下"), 
					100, TitleSeparator.TITLE_HORIZONTAL_BOTTOM));
		cp.add(new TitleSeparator(new JLabel("標題在底端"), 
					100, TitleSeparator.TITLE_VERTICAL_BOTTOM));
		//將分隔線加入內容面版

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//設定關閉視窗將預設結束程式

		pack(); //以最適大小顯示視窗
		setVisible(true); //顯示視窗框架
	}

	public static void main(String args[]) {
		new TitleSeparatorEX(); //宣告視窗框架物件
	}
}

class TitleSeparator extends JComponent {

	//宣告控制分隔線樣式與標籤位置的靜態常數
	public final static short TITLE_LEFT = 1, //預設值
							 TITLE_RIGHT = 2,
							 TITLE_HORIZONTAL_TOP = 3,
							 TITLE_HORIZONTAL_BOTTOM = 4,
							 TITLE_VERTICAL_TOP = 5,
							 TITLE_VERTICAL_BOTTOM = 6;	

	private JLabel lbTitle; //標題文字標籤
	private int length = 50; //分隔線的長度, 預設為50

	private	JSeparator spr = new JSeparator();
	//宣告水平分隔線

	Dimension dim = spr.getPreferredSize();
	//取得水平分隔線大小

	public TitleSeparator(JLabel title){ //建構子
		lbTitle = title;
		titleInSide(TITLE_LEFT); //預設分隔線為標題文字在左
	}
	
	public TitleSeparator(JLabel title, int l){
		lbTitle = title;
		length =  l;
		titleInSide(TITLE_LEFT);
	}

	public TitleSeparator(JLabel title, int l, short type){
		lbTitle = title;
		length =  l;

		switch(type){ //依照傳入型別建立適當的分隔線
			case TITLE_LEFT :
				titleInSide(TITLE_LEFT);
				break;
			case TITLE_RIGHT :
				titleInSide(TITLE_RIGHT);
				break;
			case TITLE_HORIZONTAL_TOP :
				titleHorizontal(TITLE_HORIZONTAL_TOP);
				break;
			case TITLE_HORIZONTAL_BOTTOM :
				titleHorizontal(TITLE_HORIZONTAL_BOTTOM);
				break;
			case TITLE_VERTICAL_TOP :
				titleVertical(TITLE_VERTICAL_TOP);
				break;
			case TITLE_VERTICAL_BOTTOM :
				titleVertical(TITLE_VERTICAL_BOTTOM);
				break;
			default :
				throw(new IllegalArgumentException("type"));
		}
	}

	public TitleSeparator(JLabel title, short type){
		lbTitle = title;

		switch(type){
			case TITLE_LEFT :
				titleInSide(TITLE_LEFT);
				break;
			case TITLE_RIGHT :
				titleInSide(TITLE_RIGHT);
				break;
			case TITLE_HORIZONTAL_TOP :
				titleHorizontal(TITLE_HORIZONTAL_TOP);
				break;
			case TITLE_HORIZONTAL_BOTTOM :
				titleHorizontal(TITLE_HORIZONTAL_BOTTOM);
				break;
			case TITLE_VERTICAL_TOP :
				titleVertical(TITLE_VERTICAL_TOP);
				break;
			case TITLE_VERTICAL_BOTTOM :
				titleVertical(TITLE_VERTICAL_BOTTOM);
				break;
			default :
				throw(new IllegalArgumentException("type"));
		}
	}

	private void titleInSide(short type){ //建立標題文字在左的分隔線

		setLayout(new BorderLayout(5, 5));
		//使用水平、垂直間距為5的BorderLayout

		lbTitle.setVerticalAlignment(JLabel.TOP); //設定標籤文字靠上對齊

		if(type == TITLE_LEFT)
			add(lbTitle, BorderLayout.WEST);
		else
			add(lbTitle, BorderLayout.EAST);

		spr.setPreferredSize(
			new Dimension(length, lbTitle.getPreferredSize().height));
		//設定水平分隔線的寬度

		Box bxSpr = new Box(BoxLayout.Y_AXIS);
		bxSpr.add(
			Box.createVerticalStrut(lbTitle.getPreferredSize().height/2) );
		//建立高度為標題標籤高度一半的支架
		bxSpr.add(spr);

		add(bxSpr);
	}

	private void titleHorizontal(short type){

		setLayout(new BorderLayout());
		//設定使用BorderLayout管理佈局

		spr.setPreferredSize(new Dimension(length, dim.height));
		//設定分隔線的偏好大小
	
		if(type == TITLE_HORIZONTAL_TOP){
			add(lbTitle, BorderLayout.NORTH); //將標題加入北邊區域
			add(spr); //將分隔線加入中間區域
		}
		else{
			lbTitle.setVerticalAlignment(JLabel.TOP);
			//設定標籤文字靠上對齊

			add(lbTitle); //將標籤加入中間區域
			add(spr, BorderLayout.NORTH); //將分隔線加入北邊區域
		}
	}

	private void titleVertical(short type){
		spr.setOrientation(JSeparator.VERTICAL);
		//設定分隔線的方向

		spr.setPreferredSize(
						new Dimension(dim.width, length));
		//設定分隔線的寬度
	
		Box bx = new Box(BoxLayout.X_AXIS);
		bx.add(Box.createHorizontalStrut(
							lbTitle.getPreferredSize().width / 2));
		//建立寬度為標籤寬度一半的支架

		bx.add(spr);

		setLayout(new BorderLayout());

		//依照常數設定標題文字標籤的位置
		if(type == TITLE_VERTICAL_TOP)
			add(lbTitle, BorderLayout.NORTH);
		else
			add(lbTitle, BorderLayout.SOUTH);

		add(bx);
	}
}