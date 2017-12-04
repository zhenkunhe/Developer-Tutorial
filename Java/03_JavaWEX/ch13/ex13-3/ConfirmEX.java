import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件
import java.util.*;

public class ConfirmEX extends JFrame{

	JRadioButton[] rbArray = { new JRadioButton("預設", true),
												new JRadioButton("是、否"),
												new JRadioButton("是、否、取消"),
												new JRadioButton("確認、取消")};
	//宣告控制選項類型的選擇鈕

	JLabel lbConfirm = new JLabel("回應訊息 : ");
	//宣告顯示使用者按下按鈕之訊息的標籤

	ConfirmEX(){

		JButton btnConfirm = new JButton("顯示確認對話盒");
		//宣告呼叫確認對話盒的按鈕

		//註冊監聽ActionEvent事件的監聽器
		btnConfirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				int[] optionType = {
							JOptionPane.DEFAULT_OPTION, 
							JOptionPane.YES_NO_OPTION,
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.OK_CANCEL_OPTION};
				//將指定選項類型的JOptionPane類別常數組成整數陣列

				int result = -1; //儲存確認對話盒的傳回值
				
				//比對選擇鈕, 並設定建立對應的確認對話盒
				for(int i=0; i<rbArray.length; i++){

					if(rbArray[i].isSelected()){ //判斷被選取的選擇鈕

						String strMsg = rbArray[i].getActionCommand();
						//取得選擇鈕的動作命令字串

						result = JOptionPane.showConfirmDialog(
							null, "請完成確認動作", "確認對話盒",
							optionType[i]);
						//顯示確認對話盒
					}
				}

				//依照使用者按下確認對話盒內按鈕後的回傳值 
				//設定標籤顯示的內容
				switch(result){
				case JOptionPane.YES_OPTION: //與OK_OPTION相同
					lbConfirm.setText("回應訊息 :  按下 是 或 確認 按鈕");
					break;
				case JOptionPane.NO_OPTION:
					lbConfirm.setText("回應訊息 :  按下 否 按鈕");
					break;
				case JOptionPane.CANCEL_OPTION:
					lbConfirm.setText("回應訊息 :  按下 取消 按鈕");
					break;
				case JOptionPane.CLOSED_OPTION:
					lbConfirm.setText("回應訊息 :  直接關閉確認對話盒");
					break;
				}
			}
		});
		
		Box bxConfirm = new Box(BoxLayout.X_AXIS);
		ButtonGroup bg = new ButtonGroup();

		//將選擇鈕加入jpConfirm面版與按鈕群組
		for(JRadioButton elm : rbArray){
			bxConfirm.add(elm);
			bxConfirm.add(Box.createHorizontalStrut(5));
			bg.add(elm);
		}

		Container cp = getContentPane(); //取得內容面版
		//cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
		//設定運用GridLayout配置版面

		cp.add(bxConfirm);
		cp.add(lbConfirm, BorderLayout.SOUTH);
		cp.add(btnConfirm, BorderLayout.EAST);

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//設定根面版四周將使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(480, 100);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ConfirmEX(); //宣告視窗框架物件
	}
}