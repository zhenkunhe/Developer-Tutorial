import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//繼承BasicCheckBoxUI類別並實作監聽ItemEvent事件的監聽器物件
public class BitCCheckBoxUI 
	extends javax.swing.plaf.basic.BasicCheckBoxUI
	implements ItemListener {

	Icon check = new ImageIcon("image/check.gif"),
			uncheck = new ImageIcon("image/uncheck.gif");
	//宣告顯示核取方塊選取與取消選取的狀態圖示
	
	private static BitCCheckBoxUI instance = new BitCCheckBoxUI();
	//宣告處理核取方塊外觀的UI代理元件

	public BitCCheckBoxUI(){} //建構子

	//建立UI代理元件的公開靜態方法
	public static 
		javax.swing.plaf.ComponentUI createUI(JComponent c){

		return instance;
	}

	//安裝UI代理元件
	public void installUI(JComponent c){

		super.installUI(c); //呼叫基礎類別的installUI()方法

		JCheckBox cb = (JCheckBox) c;
		//取得欲安裝UI代理元件的核取方塊
		
		cb.setIcon(cb.isSelected()? check: uncheck);
		//依照核取方塊是否被選取設定顯示選取狀態的圖示

		cb.addItemListener(this);
		//註冊回應ItemEvent事件的監聽器物件
	}

	//移除UI代理元件
	public void uninstallUI(JComponent c){

		super.uninstallUI(c); //呼叫基礎類別的uninstallUI()方法

		JCheckBox cb = (JCheckBox) c;
		//取得欲安裝UI代理元件的核取方塊

		cb.removeItemListener(this);
		//移除回應ItemEvent事件的監聽器物件
	}

	//繪製元件
	public void paint(Graphics g, JComponent c){
		super.paint(g, c);
		//未執行繪製動作故必須呼叫基礎類別的paint()方法
	}

	//更新元件
	public void update(Graphics g, JComponent c){
		super.update(g, c);
		//未執行重繪動作故必須呼叫基礎類別的update()方法
	}

	//回應核取方塊選取狀態改變的方法
	public void itemStateChanged(ItemEvent e){

		JCheckBox cb = (JCheckBox) e.getSource();
		//取得引發事件的核取方塊

		cb.setIcon(
			e.getStateChange() == ItemEvent.SELECTED ?
			check: uncheck);
		//依照核取方塊是否被選取設定顯示選取狀態的圖示
	}
}