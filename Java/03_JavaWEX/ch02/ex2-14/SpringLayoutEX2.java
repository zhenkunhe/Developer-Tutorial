import javax.swing.*; //引用swing套件
import javax.swing.border.*; //引用swing套件
import java.awt.*; //引用awt套件

public class SpringLayoutEX2 extends JFrame {

	public SpringLayoutEX2(){

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new BoxLayout(cp,  BoxLayout.Y_AXIS));
		//設定內容面版使用SpringLayout配置版面

		SpringLayout slLayout = new SpringLayout();
		JPanel jpFirst = new JPanel(slLayout);

		for(int i=1; i<=6; i++){
			jpFirst.add(new JButton(Long.toString(i * 
				Math.round(Math.pow(10.0, i)))));	//將按鈕加入容器
		}

		SpringUtilities.makeGrid(jpFirst,
					2, 3, //列, 欄
                    10, 10,  //起始X座標, 起始Y座標
                    5, 5); //X軸方向元件的間隔, Y軸方向元件的間隔

		JPanel jpSecond = new JPanel(slLayout);

		for(int i=0; i<5; i++){
			for(int j=0; j<5; j++)
				jpSecond.add(new JButton(Long.toString(j * 
					Math.round(Math.pow(10.0, (j+i))) + i)));
				//將按鈕加入容器
		}

		SpringUtilities.makeCompactGrid(jpSecond,
					5, 5, //列, 欄
                    10, 10,  //起始X座標, 起始Y座標
                    5, 5); //X軸方向元件的間隔, Y軸方向元件的間隔

		cp.add(jpFirst);
		cp.add(jpSecond);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //設定關閉視窗將預設結束程式
		pack();
		setVisible(true); //顯示視窗框架
	}

	public static void main(String[] args) {
		new SpringLayoutEX2();
	}
}

/*
SpringUtilities類別修改自Java說明文件
http://java.sun.com/docs/books/tutorial/uiswing/layout/example-1dot4/SpringUtilities.java
*/
class SpringUtilities {

	/*
	依照指定的起始X, Y座標與元件間的X, Y軸間距
	將parent容器內的元件排列為格狀,
	再取得容器內元件的最大寬度與最大高度,
	並設定所有元件的寬度與高度均為最大高度與寬度
	*/
	public static void makeGrid(
						Container parent, //欲排列元件的容器
						int rows, int cols, //行, 列
						int initialX,  int initialY, //起始座標
						int xPad, int yPad){ //X, Y軸的間距

		SpringLayout layout;

		try {
			layout = (SpringLayout)parent.getLayout();
		}
		catch (ClassCastException exc) {
			System.err.println("容器必須使用SpringLayout版面管理員");
			return;
        }

		//宣告設定X, Y座標起始值與元件X, Y軸方向的間隔
		Spring initialXSpring = Spring.constant(initialX);
		Spring initialYSpring = Spring.constant(initialY);
		Spring xPadSpring = Spring.constant(xPad);
		Spring yPadSpring = Spring.constant(yPad);

		int max = rows * cols; //計算容納元件的格數

		//取得容器內之元件最大的寬度與高度
        Spring maxWidthSpring = 
			layout.getConstraints(parent.getComponent(0)).getWidth();
        Spring maxHeightSpring = 
			layout.getConstraints(parent.getComponent(0)).getWidth();

		for (int i = 1; i < max; i++) {
			SpringLayout.Constraints cons = 
				layout.getConstraints(parent.getComponent(i));

			maxWidthSpring = 
				Spring.max(maxWidthSpring, cons.getWidth());
			maxHeightSpring = 
				Spring.max(maxHeightSpring, cons.getHeight());
		}

		//以最大寬度與高度設定所有元件
        for (int i = 0; i < max; i++) {
			SpringLayout.Constraints cons =
				layout.getConstraints(parent.getComponent(i));

			cons.setWidth(maxWidthSpring);
			cons.setHeight(maxHeightSpring);
		}

        //取得加入容器之元件的SpringLayout.Constraints物件,
		//並修正元件的X, Y座標
		SpringLayout.Constraints lastCons = null;
		SpringLayout.Constraints lastRowCons = null;

		for (int i = 0; i < max; i++) {
			SpringLayout.Constraints cons = 
					layout.getConstraints(parent.getComponent(i));
			//依照加入容器之順序, 取得元件

			//依照加入順序設定元件的X座標值
			if(i % cols == 0) { //建立新列
				lastRowCons = lastCons;
				cons.setX(initialXSpring);
			}
			else { //元件的X座標位置, 將由前一元件EAST邊界決定
				cons.setX(Spring.sum(
					lastCons.getConstraint(SpringLayout.EAST), 
					xPadSpring));				
			}

			//依照加入順序設定元件的Y座標值
			if (i / cols == 0) { //設定第一列
				cons.setY(initialYSpring);
			}
			else { //元件的Y座標位置, 將由上一列決定
				cons.setY(Spring.sum(
					lastRowCons.getConstraint(SpringLayout.SOUTH),
					yPadSpring));		
			}
			lastCons = cons; //取得最後一個元件
		}

		//利用最後一個元件重新設定容器的大小
		SpringLayout.Constraints pCons = layout.getConstraints(parent);
		pCons.setConstraint(SpringLayout.SOUTH,
			Spring.sum(Spring.constant(yPad),
				lastCons.getConstraint(SpringLayout.SOUTH)));
		pCons.setConstraint(SpringLayout.EAST,
			Spring.sum(Spring.constant(xPad),
				lastCons.getConstraint(SpringLayout.EAST)));
	}

    //makeCompactGrid()方法將呼叫此方法
	//取得容器內指定元件的Constraints物件
	private static SpringLayout.Constraints getConstraintsForCell(
			int row, int col, Container parent, int cols) {

		SpringLayout layout = (SpringLayout) parent.getLayout();
		Component c = parent.getComponent(row * cols + col);
		return layout.getConstraints(c);
	}

	/*
	依照指定的起始X, Y座標與元件間的X, Y軸間距
	將parent容器內的元件排列為格狀,
	並設定每一欄元件的寬度為該欄最寬之元件的寬度,
	每一列元件的高度為該列最高之元件的高度,
	*/
	public static void makeCompactGrid(
						Container parent, //欲排列元件的容器
						int rows, int cols, //行, 列
						int initialX,  int initialY, //起始座標
						int xPad, int yPad){ //X, Y軸的間距

		SpringLayout layout;

		try {
			layout = (SpringLayout)parent.getLayout();			
		}
		catch (ClassCastException exc) {
			System.err.println("容器必須使用SpringLayout版面管理員");
			return;
        }

		Spring x = Spring.constant(initialX);
		//宣告代表X座標起始位置的Spring物件

        //取得一欄的元件的最大寬度,
		//並將最大寬度設定為該欄元件的寬度,
		//且完成元件X軸座標的設定
		for (int c = 0; c < cols; c++) {
			Spring width = Spring.constant(0);
			//起始取得元件最大寬度的Spring物件

			for (int r = 0; r < rows; r++) {
				SpringLayout.Constraints cons =
							getConstraintsForCell(r, c, parent, cols);
				//取得控制元件位置與大小的Constraints物件

				width = Spring.max(width, cons.getWidth());
				//比較元件的寬度取得較大值
			}

			//將一列所有元件的寬度設定為最大寬度, 並設定元件的X座標
			for (int r = 0; r < rows; r++) {
                SpringLayout.Constraints constraints =
                        getConstraintsForCell(r, c, parent, cols);
				//取得控制元件的位置與大小的Constraints物件

				constraints.setX(x); //設定元件的X座標
				constraints.setWidth(width); //設定元件的寬度
			}
			x = Spring.sum(x, Spring.sum(width, Spring.constant(xPad)));
			//加總X座標值, 做為下個元件的X座標
		}

		Spring y = Spring.constant(initialY);
		//宣告代表Y座標起始位置的Spring物件

        //取得一列的元件的最大高度,
		//並將最大高度設定為該列元件的高度,
		//且完成元件Y軸座標的設定
		for (int r = 0; r < rows; r++) {
			Spring height = Spring.constant(0);

			//取得某列元件的最高高度
			for (int c = 0; c < cols; c++) {
				SpringLayout.Constraints cons =
							getConstraintsForCell(r, c, parent, cols);
				//取得控制元件位置與大小的Constraints物件

				height = Spring.max(height, cons.getHeight());
				//比較元件的高度取得較大值
			}

			//將一列所有元件的高度設定為最大高度, 並設定Y座標
			for (int c = 0; c < cols; c++) {
				SpringLayout.Constraints constraints =
					getConstraintsForCell(r, c, parent, cols);
				//取得控制元件的位置與大小的Constraints物件

				constraints.setY(y); //設定元件的Y座標
				constraints.setHeight(height); //設定元件的高度
			}

			y = Spring.sum(y, Spring.sum(height, Spring.constant(yPad)));
			//加總Y座標值, 做為下個元件的Y座標
        }

		//設定容器的大小
		SpringLayout.Constraints pCons = layout.getConstraints(parent);
		pCons.setConstraint(SpringLayout.SOUTH, y);
		pCons.setConstraint(SpringLayout.EAST, x);
	}
}