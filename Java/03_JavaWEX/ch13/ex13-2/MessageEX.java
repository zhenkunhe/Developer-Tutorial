import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件
import java.util.*;

public class MessageEX extends JFrame{

	JRadioButton[] rbArray = { new JRadioButton("錯誤訊息", true),
												new JRadioButton("資訊訊息"),
												new JRadioButton("警告訊息"),
												new JRadioButton("問題訊息"),
												new JRadioButton("純文字訊息")};
	//宣告控制訊息類型的選擇鈕

	MessageEX(){

		JButton btnMsg = new JButton("顯示訊息對話盒");
		//宣告呼叫訊息對話盒的按鈕

		//註冊監聽ActionEvent事件的監聽器
		btnMsg.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				int[] msgType = { JOptionPane.ERROR_MESSAGE,
							JOptionPane.INFORMATION_MESSAGE,
							JOptionPane.WARNING_MESSAGE,
							JOptionPane.QUESTION_MESSAGE,
							JOptionPane.PLAIN_MESSAGE};
				//將指定訊息類型的JOptionPane類別常數組成整數陣列

				//比對選擇鈕, 並設定建立對應的訊息對話盒
				for(int i=0; i<rbArray.length; i++){

					if(rbArray[i].isSelected()){ //判斷被選取的選擇鈕
						String strMsg = rbArray[i].getActionCommand();
						//取得選擇鈕的動作命令字串

						JOptionPane.showMessageDialog(	null,
							strMsg + "的內容", strMsg, msgType[i]);
							//以動作命令字串與訊息類型, 
							//建立並顯示訊息對話盒
					}
				}
			}
		});

		JPanel jpMsg = new JPanel(new GridLayout(2, 3));
		ButtonGroup bg = new ButtonGroup();

		//將選擇鈕加入jpMsg面版與按鈕群組
		for(JRadioButton elm : rbArray){
			jpMsg.add(elm);
			bg.add(elm);
		}

		Container cp = getContentPane(); //取得內容面版

		cp.add(jpMsg);
		cp.add(btnMsg, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//設定根面版四周將使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 150);
		setVisible(true);
	}

	public static void main(String args[]) {
		new MessageEX(); //宣告視窗框架物件
	}
}