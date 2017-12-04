import javax.swing.*;
import javax.swing.event.*; //引用包含CaretListener介面的套件
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class ListDataEventEX extends JFrame{

	String[] strTech = {"PHP", "JSP", "ASP", "ASP.NET",
								   "Perl", "Java", "C/C++", "C#"};
	//設定選項的字串陣列

	DefaultListModel dlmTech = new DefaultListModel();
	DefaultListModel dlmSelTech = new DefaultListModel();
	//宣告處理清單方塊內選項的Model物件

	JList ltTech = new JList(dlmTech);
	JList ltSelTech = new JList(dlmSelTech);
	//以Model物件宣告清單方塊

	JLabel lbTech = new JLabel();
	JLabel lbSelTech = new JLabel("回應事件的訊息");
	//宣告顯示清單方塊內選項個數的訊息

	JButton btnToLeft = new JButton("<");
	JButton btnToRight = new JButton(">");
	//宣告按鈕元件

	//將選項由左邊的清單方塊移至右邊
	private void toRight(){

		dlmSelTech.addElement(ltTech.getSelectedValue());
		//將左邊清單方塊選取的選項加入右邊清單方塊的Model物件

		//若右邊清單方塊未選取選項則設定選取第一個選項
		if(ltSelTech.getSelectedIndex() == -1)
			ltSelTech.setSelectedIndex(0); //設定選取第一個選項	

		int index = ltTech.getSelectedIndex(); //取得選取選項的索引
		dlmTech.remove(index); //從Model物件移除選項

		int size = dlmTech.getSize(); //取得Model物件的大小

		//移除選項後, 設定新的選取選項
		if(index != size){
			ltTech.setSelectedIndex(index);
			//設定左邊清單方塊選取原來索引值的選項
		}
		else{
			ltTech.setSelectedIndex(index-1);
			//設定左邊清單方塊選取原來索引值減1的選項
		}

		if(size == 0){ //size等於0表示左邊清單方塊內沒有選項
			btnToRight.setEnabled(false);
			//設定將選項移至右邊的按鈕為無效
		}

		if(!btnToLeft.isEnabled()){
		//判斷將選項移至左邊清單方塊的按鈕是否有效
			btnToLeft.setEnabled(true);	
			//設定將選項移至左邊的按鈕為有效
		}
	}

	//將選項由右邊的清單方塊移至左邊
	private void toLeft(){

		dlmTech.addElement(ltSelTech.getSelectedValue());
		//將右邊清單方塊選取的選項加入左邊清單方塊的Model物件

		//若左邊清單方塊未選取選項則設定選取第一個選項
		if(ltTech.getSelectedIndex() == -1)
			ltTech.setSelectedIndex(0); //設定選取第一個選項

		int index = ltSelTech.getSelectedIndex(); //取得選取選項的索引
		dlmSelTech.remove(index); //從Model物件移除選項

		int size = dlmSelTech.getSize(); //取得Model物件的大小

		//移除選項後, 設定新的選取選項
		if(index != size){
			ltSelTech.setSelectedIndex(index);
			//設定右邊清單方塊選取原來索引值的選項
		}
		else{
			ltSelTech.setSelectedIndex(index-1);
			//設定右邊清單方塊選取的選項
		}

		if(size == 0){ //size等於0表示右邊清單方塊內沒有選項
			btnToLeft.setEnabled(false);
			//設定將選項移至左邊的按鈕為無效
		}

		if(!btnToRight.isEnabled()){
		//判斷將選項移至右邊清單方塊的按鈕是否有效
			btnToRight.setEnabled(true);
			//設定將選項移至右邊的按鈕為有效
		}
	}

	//以匿名類別的方式實作ActionListener介面,
	//定義並宣告回應ActionEvent事件的監聽器物件
	ActionListener al = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(e.getActionCommand().equals("toRight")){
				toRight(); //將選取選項從左邊移至右邊
			}
			else{
				toLeft(); //將選取選項從右邊移至左邊
			}
		}
	};

	//以匿名類別的方式繼承MouseAdapter類別,
	//定義並宣告回應MouseEvent事件的監聽器物件
	MouseAdapter ma = new MouseAdapter(){
		public void mouseClicked(MouseEvent e){
			if(e.getClickCount() == 2){ //判斷滑鼠是否連按二下
				if((JList)e.getSource() == ltTech)
					toRight(); //將選取選項從左邊移至右邊
				else
					toLeft(); //將選取選項從右邊移至左邊
			}
		}
	};

	ListDataEventEX(){

		//宣告監聽ListDataEvent事件的監聽器物件
		dlmSelTech.addListDataListener(new ListDataListener(){

			//回應選項的加入事件
			public void intervalAdded(ListDataEvent e){
				lbSelTech.setText("新增第" + e.getIndex0() + "個選項");
			}

			//回應選項的移除事件
			public void intervalRemoved(ListDataEvent e){
				lbSelTech.setText("移除第" + e.getIndex0() + "個選項");
			}

			//回應選項內容修改事件
			public void contentsChanged(ListDataEvent e){ }
		});

		//將字串陣列內的字串加入Model物件
		for(String item: strTech)
			dlmTech.addElement(item);

		btnToLeft.setEnabled(false); //設定向左按鈕為無效

		ltTech.setVisibleRowCount(5); //設定左邊清單方塊的可視項目為5列
		ltTech.setSelectedIndex(0); //設定左邊清單方塊預設選取第1個選項

		ltSelTech.setVisibleRowCount(5);
		//設定右邊清單方塊的可是項目為5列

		btnToLeft.setActionCommand("toLeft"); //設定動作命令字串
		btnToRight.setActionCommand("toRight");

		btnToLeft.addActionListener(al);
		btnToRight.addActionListener(al);
		//註冊回應ActionEvent事件的監聽器物件

		ltTech.addMouseListener(ma);
		ltSelTech.addMouseListener(ma);
		//註冊回應MouseEvent事件的監聽器物件

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new GridBagLayout());
		//設定內容面版使用GridBagLayout管理佈局

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.BOTH;

		gbc.gridwidth = 2; gbc.gridheight = 1;
		gbc.gridx = 0; gbc.gridy = 0;
		cp.add(new JLabel("可選取的技術項目 : "), gbc);

		gbc.gridx = 3; gbc.gridy = 0;
		cp.add(new JLabel("被選取的技術項目 : "), gbc);

		gbc.gridwidth = 2; gbc.gridheight = 5;
		gbc.gridx = 0; gbc.gridy = 1;
		cp.add(new JScrollPane(ltTech), gbc); //加入左邊的清單方塊

		gbc.gridx = 3; gbc.gridy = 1;
		cp.add(new JScrollPane(ltSelTech), gbc); //加入右邊的清單方塊

		gbc.gridx = 3; gbc.gridy = 7;
		gbc.gridwidth = 1; gbc.gridheight = 1;
		cp.add(lbSelTech, gbc); //將標籤加入容器
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(10, 10, 10 ,10);

		gbc.gridx = 2; gbc.gridy = 2;
		cp.add(btnToRight, gbc); //加入將選項移至右邊的指令按鈕

		gbc.gridx = 2; gbc.gridy = 5;
		cp.add(btnToLeft, gbc); //加入將選項移至左邊的指令按鈕
	
		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(350, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ListDataEventEX();
	}
}