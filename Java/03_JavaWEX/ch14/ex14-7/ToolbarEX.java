import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class ToolbarEX extends JFrame{

	JLabel lbEdit = new JLabel(), //宣告標籤元件
	       lbLeft = new JLabel("靠左"),
	       lbCenter = new JLabel("置中"),
	       lbRight = new JLabel("靠右");

	//定義衍生自JButton類別的按鈕元件類別, 並實作ActionListener
	class tbButton extends JButton implements ActionListener {

		tbButton(String action,ImageIcon ii){
			super(ii);
			//將傳入的ImageIcon物件傳入基礎類別的建構子

			setActionCommand(action); //設定動作命令字串
			addActionListener(this);
			//設定回應ActionEvent事件的監聽器

			setToolTipText(action); //設定命令提示字元
		}

		//ActionListener介面定義回應ActionEvent事件的方法
		public void actionPerformed(ActionEvent e) {
			lbEdit.setText(getActionCommand());
			//將動作命令字串設定給狀態列的標籤
		}
	}

	ToolbarEX(){

		JToolBar tbTop = new JToolBar("編輯");
		//宣告工具列物件
		
		tbTop.add(new tbButton("剪下", 
			new ImageIcon("images/cut.gif")));
		tbTop.add(new tbButton("複製", 
			new ImageIcon("images/copy.gif")));
		tbTop.add(new tbButton("貼上", 
			new ImageIcon("images/paste.gif")));
		//以圖示建立按鈕元件, 並加入工具列內

		tbTop.addSeparator(); //加入分隔空間

		JToggleButton
			tbnLeft = new JToggleButton(
				new ImageIcon("images/left.gif"), true),
			tbnCenter = new JToggleButton(
				new ImageIcon("images/center.gif")),
			tbnRight = new JToggleButton(
				new ImageIcon("images/right.gif"));
		//以圖示建立JToggleButton元件, 且起始設定tbnLeft為選取

		tbnLeft.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				lbLeft.setEnabled(true);
				lbCenter.setEnabled(false);
				lbRight.setEnabled(false);
			}
		}); //設定回應ActionEvent事件的監聽器

		tbnCenter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				lbLeft.setEnabled(false);
				lbCenter.setEnabled(true);
				lbRight.setEnabled(false);
			}
		}); //設定回應ActionEvent事件的監聽器

		tbnRight.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				lbLeft.setEnabled(false);
				lbCenter.setEnabled(false);
				lbRight.setEnabled(true);
			}
		}); //設定回應ActionEvent事件的監聽器

		tbTop.add(tbnLeft); //將JToggleButton元件加入工具列
		tbTop.add(tbnCenter);
		tbTop.add(tbnRight);

		ButtonGroup bgToolBar = new ButtonGroup(); //建立按鈕群組

		bgToolBar.add(tbnLeft);
		bgToolBar.add(tbnCenter);
		bgToolBar.add(tbnRight);
		//將工具列的JToggleButton元件加入按鈕群組

		tbnLeft.setToolTipText("靠左"); //設定命令提示字元字串
		tbnCenter.setToolTipText("置中");
		tbnRight.setToolTipText("靠右");

		JPanel jpStatus = new JPanel(new GridLayout(1, 5));
		//宣告包含顯示狀態訊息的JPanel容器

		//將標籤加入容器
		jpStatus.add(new JLabel("編輯動作 : ", JLabel.RIGHT));
		jpStatus.add(lbEdit);
		jpStatus.add(lbLeft);
		jpStatus.add(lbCenter);
		jpStatus.add(lbRight);

		lbCenter.setEnabled(false); //設定標籤的狀態為無效
		lbRight.setEnabled(false);

		Container cp = getContentPane(); //取得內容面版
		cp.add(tbTop, BorderLayout.NORTH); //將工具列加入視窗框架
		cp.add(jpStatus, BorderLayout.SOUTH);
		
		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ToolbarEX(); //宣告視窗框架物件
	}
}