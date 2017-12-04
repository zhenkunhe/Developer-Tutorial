import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class TBControlEX extends JFrame{

	JTextArea taMsg = new JTextArea();
	//顯示控制項動作命令字串的文字區

	//定義宣告回應ActionEvent事件的監聽器物件
	ActionListener al = new ActionListener(){
		
		public void actionPerformed(ActionEvent e) {

			//判斷發出事件的控制項是否為JComboBox
			if(e.getSource() instanceof JComboBox){
				taMsg.append(
					((JComboBox)e.getSource()).getSelectedItem() + "\n");
				//將動作命令字串新增至文字區
				return;
			}

			taMsg.append(e.getActionCommand() + "\n");
			//將動作命令字串新增至文字區
		}
	};

	TBControlEX(){

		JToolBar tbTop = new JToolBar("控制項");
		//宣告工具列物件
		
		String[] strAry = {"組合方塊選項一",
									"組合方塊選項二",
									"組合方塊選項三"};
		//定義組合方塊的選項字串


		JComboBox cbItem = new JComboBox(strAry);
		//以選項字串宣告組合方塊

		JButton btnItem = new JButton("確定"); //宣告指令按鈕

		JCheckBox cbOne = new JCheckBox("核取方塊A"),
							cbTwo = new JCheckBox("核取方塊B"),
							cbThree = new JCheckBox("核取方塊C");
		//宣告欲加入工具列的核取方塊

		JRadioButton rbOne = new JRadioButton("選擇鈕A"),
								rbTwo = new JRadioButton("選擇鈕B"),
								rbThree = new JRadioButton("選擇鈕C");
		//宣告欲加入工具列的核取方塊

		ButtonGroup bg = new ButtonGroup();
		bg.add(rbOne);
		bg.add(rbTwo);
		bg.add(rbThree);
		//將選擇鈕加入同一個按鈕群組

		cbItem.addActionListener(al);
		btnItem.addActionListener(al);
		cbOne.addActionListener(al);
		cbTwo.addActionListener(al);
		cbThree.addActionListener(al);
		rbOne.addActionListener(al);
		rbTwo.addActionListener(al);
		rbThree.addActionListener(al);
		//註冊監聽工具列控制項ActionEvent事件的監聽器物件

		tbTop.add(cbItem);
		tbTop.addSeparator(); //加入分隔空間
		tbTop.add(btnItem);
		tbTop.addSeparator();
		tbTop.add(cbOne);
		tbTop.add(cbTwo);
		tbTop.add(cbThree);
		tbTop.addSeparator();
		tbTop.add(rbOne);
		tbTop.add(rbTwo);
		tbTop.add(rbThree);
		//以圖示建立按鈕元件, 並加入工具列內

		Container cp = getContentPane(); //取得內容面版
		cp.add(tbTop, BorderLayout.NORTH); //將工具列加入視窗框架
		cp.add(new JScrollPane(taMsg));
		
		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new TBControlEX(); //宣告視窗框架物件
	}
}