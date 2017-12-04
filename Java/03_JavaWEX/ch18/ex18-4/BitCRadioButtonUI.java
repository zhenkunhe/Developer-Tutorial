import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//繼承BasicRadioButtonUI類別並實作監聽ItemEvent事件的監聽器物件
public class BitCRadioButtonUI 
	extends javax.swing.plaf.basic.BasicRadioButtonUI
	implements ItemListener {

	Icon check = new ImageIcon("image/select.gif"),
			uncheck = new ImageIcon("image/unselect.gif");
	//宣告顯示選擇鈕選取與取消選取的狀態圖示
	
	private static BitCRadioButtonUI instance = new BitCRadioButtonUI();
	//宣告處理選擇鈕外觀的UI代理元件

	public BitCRadioButtonUI(){} //建構子

	//建立UI代理元件的公開靜態方法
	public static 
		javax.swing.plaf.ComponentUI createUI(JComponent c){

		return instance;
	}

	//安裝UI代理元件
	public void installUI(JComponent c){

		super.installUI(c); //呼叫基礎類別的installUI()方法

		JRadioButton cb = (JRadioButton) c;
		//取得欲安裝UI代理元件的選擇鈕
		
		cb.setIcon(cb.isSelected()? check: uncheck);
		//依照選擇鈕是否被選取設定顯示選取狀態的圖示

		cb.addItemListener(this);
		//註冊回應ItemEvent事件的監聽器物件
	}

	//移除UI代理元件
	public void uninstallUI(JComponent c){

		super.uninstallUI(c); //呼叫基礎類別的uninstallUI()方法

		JRadioButton cb = (JRadioButton) c;
		//取得欲安裝UI代理元件的選擇鈕

		cb.removeItemListener(this);
		//移除回應ItemEvent事件的監聽器物件
	}

	//回應選擇鈕選取狀態改變的方法
	public void itemStateChanged(ItemEvent e){

		JRadioButton cb = (JRadioButton) e.getSource();
		//取得引發事件的選擇鈕

		cb.setIcon(
			e.getStateChange() == ItemEvent.SELECTED ?
			check: uncheck);
		//依照選擇鈕是否被選取設定顯示選取狀態的圖示
	}
}