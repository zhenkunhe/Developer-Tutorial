import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��
import java.util.*;

public class InputEX extends JFrame{

	JLabel lbInput = new JLabel("��J�� : ");
	//�ŧi��ܨϥΪ̿�J��ƪ�����

	JRadioButton rbTextField = new JRadioButton("��r��", true),
		rbComboBox = new JRadioButton("�զX���"),
		rbList = new JRadioButton("�M����(20�ӥH�W���ﶵ)");
	//�ѨϥΪ̿����ƿ�J��ܲ���J��ƪ����

	InputEX(){

		JButton btnInput = new JButton("��ܸ�ƿ�J��ܲ�");
		//�ŧi�I�s��ƿ�J��ܲ������s

		//���U��ťActionEvent�ƥ󪺺�ť��
		btnInput.addActionListener(new ActionListener(){
			private final ImageIcon ICON_BITC = 
				new ImageIcon("icon/Bitc_Logo_Only.gif");

			public void actionPerformed(ActionEvent e){

				String result; //�x�s��J�ܹ�ܲ������

				if(rbTextField.isSelected()){
					result = (String) JOptionPane.showInputDialog(
							null, "��J���y�W��", "�п�J���y�W��",
							JOptionPane.INFORMATION_MESSAGE, 
							ICON_BITC, null, "Java 2 �J���i��");
					//��ܸ�ƿ�J��ܲ�, �H��r���J���,
					//�w�]�Ȭ�Java 2 �J���i��, �è��o�ϥΪ̿�J�����
				}
				else if(rbComboBox.isSelected()) {
					String[] book = {"Java 2 �J���i�� �V �A��JDK5.0", 
											"Visual C++.NET �J���i��", 
											"Access 2003 ������s", 
											"Access 2003 �{���]�p", 
											"JSP �ʺA�����J�����",
											"ASP �ʺA�����J�����"};
					//��ƿ�J��ܲ����զX��ܲ�������ﶵ

					result = (String) JOptionPane.showInputDialog(
							null, "������y", "�п�����y", 
							JOptionPane.INFORMATION_MESSAGE, 
							ICON_BITC, book, book[0]);
					//��ܸ�ƿ�J��ܲ�, �H�զX��ܲ���J���,
					//�w�]����Ĥ@�ӿﶵ, �ñN���o������G
				}
				else{
					String[] item = new String[20];
					//��ƿ�J��ܲ����զX��ܲ�������ﶵ

					for(int i=0; i<item.length; i++)
						item[i] = new String("�ﶵ" + (i+1));

					result = (String) JOptionPane.showInputDialog(
							null, "������y", "�п�����y", 
							JOptionPane.INFORMATION_MESSAGE, 
							ICON_BITC, item, item[0]);
					//��ܸ�ƿ�J��ܲ�, �H�զX��ܲ���J���,
					//�w�]����Ĥ@�ӿﶵ, �ñN���o������G
				}

				//�Y���o��Ƥ���null, �B���פ���0, 
				//�h�]�wlbInput������ܿ�J���G
				if(result != null && result.length() > 0)
						lbInput.setText("��J�� : " + result);
			}
		});

		//�N��ܶs�զ������s�s��
		ButtonGroup bgInput = new ButtonGroup();
		bgInput.add(rbTextField);
		bgInput.add(rbComboBox);
		bgInput.add(rbList);

		Box bxInput = new Box(BoxLayout.X_AXIS);
		bxInput.add(new JLabel("��J��ƪ��覡 : "));
		bxInput.add(rbTextField); //�N��ܶs�[�JBox�e��
		bxInput.add(Box.createHorizontalStrut(5));
		bxInput.add(rbComboBox);
		bxInput.add(Box.createHorizontalStrut(5));
		bxInput.add(rbList);

		Container cp = getContentPane(); //���o���e����
		cp.add(bxInput); //�N����[�J���e����
		cp.add(btnInput, BorderLayout.EAST);
		cp.add(lbInput, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����|�P�N�ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 100);
		setVisible(true);
	}

	public static void main(String args[]) {
		new InputEX(); //�ŧi�����ج[����
	}
}