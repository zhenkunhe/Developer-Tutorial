import javax.swing.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;

public class StyleContextEX extends JFrame {

	JTextPane tpStyleA = new JTextPane(),
					   tpStyleB = new JTextPane(); //�˦���r����

	class ThreadA extends Thread {
		public void run(){
			//�N��r���e�P��r�ݩʷs�W�ܤ�r����
			for(int i = 0; i<100; i++){
				SimpleAttributeSet sasContent = 
					new SimpleAttributeSet();
				//�ŧi���媺�˦��ݩ�

				StyleConstants.setFontSize(sasContent, 14);
				//�]�w����˦��ݩʪ��r���j�p��14

				int len = tpStyleA.getText().length();
				//���o�˦���r�������e������

				tpStyleA.setCaretPosition(len);
				//�N��в��̫ܳ�, �N�������

				tpStyleA.setCharacterAttributes(sasContent, false);
				//�]�w��r���˦��ݩ�

				tpStyleA.replaceSelection("Java 2 �����{���]�p");
				//�������r, �G�ĪG�۷�󴡤J��r
			}
		}
	};

	class ThreadB extends Thread {
		public void run(){
			//�N��r���e�P��r�ݩʷs�W�ܤ�r����
			for(int i = 0; i<100; i++){
				StyleContext sc = StyleContext.getDefaultStyleContext();
				//���o�w�]��StyleContext����
	
				AttributeSet sasContent =
					sc.addAttribute(SimpleAttributeSet.EMPTY,
									StyleConstants.FontSize, 14);
				//�N�r���j�p��14���˦��ݩʥ[�JStyleContext����, �è��o�]�w�����[�J��AttributeSet����

				int len = tpStyleB.getText().length();
				//���o�˦���r�������e������

				tpStyleB.setCaretPosition(len);
				//�N��в��̫ܳ�, �N�������

				tpStyleB.setCharacterAttributes(sasContent, false);
				//�]�w��r���˦��ݩ�

				tpStyleB.replaceSelection("Java 2 �����{���]�p");
				//�������r, �G�N���J��r
			}
		}
	};

	StyleContextEX(){

		JButton btnLoad = new JButton("���J���e");

		btnLoad.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				(new ThreadA()).start(); //�Ұʰ����
				(new ThreadB()).start();
			}
		});

		JPanel jpPane = new JPanel(new GridLayout(1, 2, 5,  5));
		jpPane.add(new JScrollPane(tpStyleA));
		jpPane.add(new JScrollPane(tpStyleB));

		Container cp = getContentPane(); //���o���e����
		cp.add(jpPane);
		cp.add(btnLoad, BorderLayout.SOUTH);

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
	}

	public static void main(String args[]) {
		new StyleContextEX(); //�ŧi�����ج[����
	}
}