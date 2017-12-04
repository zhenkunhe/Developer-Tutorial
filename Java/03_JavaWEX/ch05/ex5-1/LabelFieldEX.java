import javax.swing.*;  //�ޥήM��
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class LabelFieldEX extends JFrame{

	JLabel lbName = new JLabel("�b��(N) : ", JLabel.RIGHT);	
	JLabel lbPW = new JLabel("�K�X(P) : ", JLabel.RIGHT);	
	JTextField tfName = new JTextField(20);
	JPasswordField pfPW = new JPasswordField(20);

	JLabel lbEnter = new JLabel("[�K�X]����U Enter ���o����� : "),
				 lbCaret = new JLabel("[�b��]���Ъ���m : ");

	//�w�q��@CaretListener��������ť�����O
	class FieldCaretListener implements CaretListener {
		public void caretUpdate(CaretEvent e){ //�^���ƥ󪺤�k
			if( e.getDot() == e.getMark()){
				//�P�_getDot()��k�PgetMark()��k�����o�ȬO�_�ۦP

				lbCaret.setText("[�b��]���Ъ���m : " + e.getDot());
				//���o��Хثe����m
			}
			else
				lbCaret.setText("[�b��]���Ъ�����d�� :  [" 
								+ e.getDot() + "��" + e.getMark() + "]");
				//����d�򪺵�����m
		}
	}

	FieldCaretListener fcl =  new FieldCaretListener();
	//�ŧi��ťCaretEvent�ƥ󪺺�ť������

	//�H�ΦW�������O���覡�w�q�ëŧi��ť������
	ActionListener al = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			JPasswordField source = (JPasswordField) e.getSource();
			//���o�ƥ�ӷ�����

			//�^��ActionEvent�ƥ��, ��slbEnter���Ҫ����e
			lbEnter.setText("[�K�X]����U Enter ���o����� : [" 
			 + new String(pfPW.getPassword()) + "]");
		}
	};

	LabelFieldEX(){

		lbName.setDisplayedMnemonic('N');
		//�]�w�ϥ�N�f�tAlt�䰵���U����
		lbName.setLabelFor(tfName);
		//�]�wlbName���Ҭ�tfName��r��쪺�W��

		lbPW.setDisplayedMnemonic('P');
		//�]�w�ϥ�P�f�tAlt�䰵���O����
		lbPW.setLabelFor(pfPW); //�]�wlbPW���Ҭ�tfPW��r��쪺�W��

		pfPW.setEchoChar('@'); //�]�w�K�X��ϥΪ��B�n�r��

		tfName.addCaretListener(fcl); //���UCaretEvent�ƥ󪺺�ť��
		pfPW.addActionListener(al);
		//���U�^��ActionEvent�ƥ󪺺�ť��

		JPanel jpCenter = new JPanel(new GridLayout(2, 2,  5, 5));
		jpCenter.add(lbName); //�N����[�JJPanel�l�e��
		jpCenter.add(tfName);
		jpCenter.add(lbPW);
		jpCenter.add(pfPW);

		JPanel jpLabel = new JPanel(new GridLayout(2, 1, 5, 5));
		jpLabel.add(lbCaret);
		jpLabel.add(lbEnter);

		Container cp = getContentPane(); //���o���e����

		BorderLayout bl = (BorderLayout)cp.getLayout();
		//���o�G���޲z��
		bl.setVgap(10); //�]�w�������Z��10

		cp.add(jpCenter); //�N����[�J����
		cp.add(jpLabel, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���������N�w�]�����{��

		setSize(300, 150); //�]�w�����ج[�j�p
		setVisible(true); //��ܵ����ج[
	}

	public static void main(String args[]) {
		new LabelFieldEX(); //�ŧi�����ج[����
	}
}