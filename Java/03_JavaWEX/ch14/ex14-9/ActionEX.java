import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class ActionEX extends JFrame {

	JLabel lbEdit = new JLabel(), //宣告標籤元件
	       lbLeft = new JLabel("靠左"),
	       lbCenter = new JLabel("置中"),
	       lbRight = new JLabel("靠右");

	//定義衍生自JButton類別的按鈕元件類別
	class tbButton extends JButton {

		tbButton(Action action){ //呼叫建構子時, 傳入Action物件

			super((Icon) action.getValue(Action.SMALL_ICON));
			//將傳入的ImageIcon物件傳入基礎類別的建構子

			setActionCommand((String) action.getValue(Action.NAME));
			//設定動作命令字串

			setToolTipText((String) action.getValue(Action.NAME));
			//設定命令提示字元

			addActionListener(action);
			//註冊由Action物件監聽ActionEvent事件
		}
	}

	//定義繼承AbstractAction類別執行文字對齊設定的Action物件
	class AlignmentAction extends AbstractAction {
		
		AlignmentAction(String name, Icon icon){
			super(name, icon);
			//將動作Action物件名稱與使用的圖示傳入基礎類別建構子

			putValue(Action.SHORT_DESCRIPTION, name);
			//設定Action物件的簡短描述文字
		}

		//回應ActionEvent事件的動作
		public void actionPerformed(ActionEvent e){

			String name = (String) getValue(Action.NAME);
			//取得Action物件名稱的設定值

			lbLeft.setEnabled(false); //設定標籤無效
			lbCenter.setEnabled(false);
			lbRight.setEnabled(false);

			//比對Action物件名稱設定對應的文字標籤為有效
			//以及相關選項為選取
			if(name.equals("靠左(L)")){
				lbLeft.setEnabled(true); //設定靠左標籤為有效
				tbnLeft.setSelected(true); //選取靠左對齊切換按鈕
				rbmiLeft.setSelected(true); //選取靠左選擇鈕選項
			}
			else if(name.equals("靠右(R)")){
				lbRight.setEnabled(true);
				tbnRight.setSelected(true);
				rbmiRight.setSelected(true);
			}
			else if(name.equals("置中(C)")){
				lbCenter.setEnabled(true);
				tbnCenter.setSelected(true);
				rbmiCenter.setSelected(true);
			}
		}
	}

	AlignmentAction	
		aaLeft = new AlignmentAction(
							"靠左(L)", new ImageIcon("images/left.gif")),
		aaCenter = new AlignmentAction(
							"置中(C)", new ImageIcon("images/center.gif")),
		aaRight = new AlignmentAction(
							"靠右(R)", new ImageIcon("images/right.gif"));
	//宣告執行文字對齊動作的Action物件

	JToggleButton
			tbnLeft = new JToggleButton(aaLeft),
			tbnCenter = new JToggleButton(aaCenter),
			tbnRight = new JToggleButton(aaRight);
	//以Action物件建立JToggleButton元件

	JRadioButtonMenuItem 
		rbmiLeft = 
			new JRadioButtonMenuItem(aaLeft),
		rbmiCenter = 
			new JRadioButtonMenuItem(aaCenter),
		rbmiRight = 
			new JRadioButtonMenuItem(aaRight);
	//以描述對齊設定動作的Action物件宣告選擇鈕選項

	//宣告執行編輯動作的Action物件
	AbstractAction 
		acCut = new AbstractAction("剪下", 
									new ImageIcon("images/cut.gif")){

				//定義回應ActionEvent事件的方法
				public void actionPerformed(ActionEvent e) {
					lbEdit.setText((String)getValue(Action.NAME));
					//將動作命令字串設定給狀態列的標籤
				}
			},
		acCopy = new AbstractAction("複製", 
									new ImageIcon("images/copy.gif")){
				public void actionPerformed(ActionEvent e) {
					lbEdit.setText((String)getValue(Action.NAME));
				}
			},
		acPaste = new AbstractAction("貼上", 
									new ImageIcon("images/paste.gif")){
				public void actionPerformed(ActionEvent e) {
					lbEdit.setText((String)getValue(Action.NAME));
				}
			};

	tbButton tbtnCut = new tbButton(acCut),
					tbtnCopy = new tbButton(acCopy),
					tbtnPaste = new tbButton(acPaste);
	//以描述編輯動作的Action物件宣告建立自訂按鈕的tbButton類別

	JMenuItem miCut = new JMenuItem(acCut),
						miCopy = new JMenuItem(acCopy),
						miPaste = new JMenuItem(acPaste);
	//以Action物件宣告功能表選項

	ActionEX(){

		acCut.putValue(Action.ACCELERATOR_KEY, 
			KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
		acCopy.putValue(Action.ACCELERATOR_KEY, 
			KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
		acPaste.putValue(Action.ACCELERATOR_KEY, 
			KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));
		//設定編輯動作使用的加速鍵

		JToolBar tbTop = new JToolBar("編輯");
		//宣告工具列物件
		
		tbTop.add(tbtnCut);
		tbTop.add(tbtnCopy);
		tbTop.add(tbtnPaste);
		//將自訂按鈕元件加入工具列

		miCut.setIcon(null); //功能表選項不使用圖示
		miCopy.setIcon(null);
		miPaste.setIcon(null);

		JMenu mnEdit = new JMenu("編輯");
		mnEdit.add(miCut);
		mnEdit.add(miCopy);
		mnEdit.add(miPaste);
		//將編輯選項加入功能表

		tbTop.addSeparator(); //加入分隔空間

		aaLeft.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		aaCenter.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		aaRight.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		//設定Action物件使用的記憶鍵

		tbnLeft.setSelected(true);
		//設定選取靠左對齊切換按鈕

		tbnLeft.setText(null); //設定切換按鈕不顯示文字
		tbnCenter.setText(null);
		tbnRight.setText(null);

		tbTop.add(tbnLeft); //將JToggleButton元件加入工具列
		tbTop.add(tbnCenter);
		tbTop.add(tbnRight);

		ButtonGroup bgToolBar = new ButtonGroup(); //建立按鈕群組

		bgToolBar.add(tbnLeft);
		bgToolBar.add(tbnCenter);
		bgToolBar.add(tbnRight);
		//將工具列的JToggleButton元件加入按鈕群組

		rbmiLeft.setSelected(true); //設定選取靠左選擇鈕選項

		rbmiLeft.setIcon(null); //設定不顯示圖示
		rbmiCenter.setIcon(null);
		rbmiRight.setIcon(null);

		ButtonGroup bgAlignMenu = new ButtonGroup(); //建立按鈕群組

		bgAlignMenu.add(rbmiLeft);
		bgAlignMenu.add(rbmiCenter);
		bgAlignMenu.add(rbmiRight);
		//將工具列的JToggleButton元件加入按鈕群組

		tbTop.addSeparator(); //加入分隔空間

		JCheckBox cbAlignEnabled = new JCheckBox("文字對齊有效");
		//控制文字對齊設定Action物件是否有效的核取方塊

		cbAlignEnabled.setSelected(true); //預設為選取
		tbTop.add(cbAlignEnabled); //加入工具列

		//以匿名類別的方式定義、註冊監聽ActionEvent事件的監聽器物件
		cbAlignEnabled.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				boolean enabled = 
					((JCheckBox)e.getSource()).isSelected();
				//取得核取方塊是否被選取

				aaLeft.setEnabled(enabled);
				aaCenter.setEnabled(enabled);
				aaRight.setEnabled(enabled);
				//設定對齊設定Action物件是否有效
			}
		});

		JMenu mnAlign = new JMenu("對齊");
		mnAlign.add(rbmiLeft); //將選擇鈕選項加入功能表
		mnAlign.add(rbmiCenter);
		mnAlign.add(rbmiRight);

		JMenuBar mb = new JMenuBar(); //宣告功能表列
		mb.add(mnEdit); //將功能表加入功能表列
		mb.add(mnAlign);

		setJMenuBar(mb); //設定視窗框架使用的功能表列

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
		setSize(430, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ActionEX(); //宣告視窗框架物件
	}
}