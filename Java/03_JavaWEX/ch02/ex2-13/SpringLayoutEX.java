import javax.swing.*; //引用swing套件
import java.awt.*; //引用awt套件

public class SpringLayoutEX extends JFrame {

	public SpringLayoutEX(){

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new GridLayout(4, 1, 10, 10));
		//設定內容面版使用GridLayout配置版面

		JLabel lbName01 = new JLabel("姓名 : "); //宣告標籤元件
		JTextField tfName01 = new JTextField(); //宣告文字欄位元件

		JPanel jpFirst = new JPanel(new SpringLayout());
		//宣告JPanel容器, 並設定使用SpringLayout管理版面

		jpFirst.add(lbName01); //將標籤元件加入容器
		jpFirst.add(tfName01); //將文字欄位加入容器

		JLabel lbName02 = new JLabel("姓名 : ");
		JTextField tfName02 = new JTextField();

		SpringLayout slLayout02 = new SpringLayout();
		//宣告SpringLayout版面管理物件

		JPanel jpSecond = new JPanel(slLayout02);
	
		//方法一
		Spring width = Spring.constant(50);
		Spring height = Spring.constant(25, 35, 45);
		//呼叫constant()方法宣告Spring物件

		SpringLayout.Constraints consLbName02 = 
				new SpringLayout.Constraints(
							Spring.constant(10), Spring.constant(10),
							width, height);
		//宣告SpringLayout.Constraints物件

		jpSecond.add(lbName02, consLbName02);
		//將標籤元件加入容器, 
		//並運用SpringLayout.Constraints物件指定位置與高、寬
		
		jpSecond.add(tfName02, 
				new SpringLayout.Constraints(
					Spring.constant(55), Spring.constant(10),
					Spring.constant(100), Spring.constant(30)));
		//將文字欄位加入容器, 同時加入SpringLayout.Constraints物件

		JLabel lbName03 = new JLabel("姓名 : ");
		JTextField tfName03 = new JTextField(15);

		SpringLayout slLayout03 = new SpringLayout();
		JPanel jpThird = new JPanel(slLayout03);

		jpThird.add(lbName03); //將元件加入容器
		jpThird.add(tfName03);

		//方法二
		SpringLayout.Constraints consLbName03 = 
								slLayout03.getConstraints(lbName03);
		//取得控制標籤元件之位置與大小的SpringLayout.Constraints物件

		//呼叫SpringLayout.Constraints物件的方法, 
		//設定控制元件位置與高、寬之值
		consLbName03.setX(Spring.constant(10)); //設定X軸位置
		consLbName03.setY(Spring.constant(10)); //設定Y軸位置
		consLbName03.setHeight(Spring.constant(25)); //設定元件高度
		consLbName03.setWidth(Spring.constant(35)); //設定元件寬度

		SpringLayout.Constraints 
				consTfName03 = slLayout03.getConstraints(tfName03);
		//取得描述tfName03元件的SpringLayout.Constraints物件

		consTfName03.setConstraint(SpringLayout.WEST,
						Spring.sum(Spring.constant(10),
											 consLbName03.getConstraint(SpringLayout.EAST)));
		//設定tfName03元件的WEST邊界距離容器WEST邊界的距離,
		//取得距離的方式為以lbName元件的EAST邊界的位置加上10像素
		consTfName03.setConstraint(SpringLayout.NORTH,
														   Spring.constant(10));

		JLabel lbName04 = new JLabel("姓名 : ");
		JTextField tfName04 = new JTextField();

		SpringLayout slLayout04 = new SpringLayout();
		JPanel jpFourth = new JPanel(slLayout04);

		//方法一
		jpFourth.add(lbName04, new SpringLayout.Constraints(
									Spring.constant(10), Spring.constant(10)));
		//將元件加入容器, 並運用SpringLayout.Constraints物件
		//指定元件位置的X, Y座標

		jpFourth.add(tfName04);

		//方法三
		slLayout04.putConstraint(SpringLayout.WEST, tfName04, 
							10,
							SpringLayout.EAST, lbName04);
		//設定lbName04元件的WEST邊界與lbName元件的EAST邊界距離10像素

		slLayout04.putConstraint(SpringLayout.EAST, jpFourth, 
							10,
							SpringLayout.EAST, tfName04);
		slLayout04.putConstraint(SpringLayout.SOUTH, jpFourth,
										10,
										SpringLayout.SOUTH, tfName04);
		
		slLayout04.putConstraint(SpringLayout.NORTH, tfName04, 
										10,
										SpringLayout.NORTH, jpFourth);
		//設定tfName04元件的邊界與jpFourth容器的
		//EAST、SOUTH、NORTH邊界距離10像素

		cp.add(jpFirst); //將JPanel容器加入內容面版
		cp.add(jpSecond);
		cp.add(jpThird);
		cp.add(jpFourth);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//設定關閉視窗將預設結束程式

		setSize(300, 300); //設定視窗框架大小
		setVisible(true); //顯示視窗框架
	}

	public static void main(String[] args) {
		new SpringLayoutEX();
	}
}