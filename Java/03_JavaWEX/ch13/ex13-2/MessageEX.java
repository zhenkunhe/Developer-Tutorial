import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��
import java.util.*;

public class MessageEX extends JFrame{

	JRadioButton[] rbArray = { new JRadioButton("���~�T��", true),
												new JRadioButton("��T�T��"),
												new JRadioButton("ĵ�i�T��"),
												new JRadioButton("���D�T��"),
												new JRadioButton("�¤�r�T��")};
	//�ŧi����T����������ܶs

	MessageEX(){

		JButton btnMsg = new JButton("��ܰT����ܲ�");
		//�ŧi�I�s�T����ܲ������s

		//���U��ťActionEvent�ƥ󪺺�ť��
		btnMsg.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				int[] msgType = { JOptionPane.ERROR_MESSAGE,
							JOptionPane.INFORMATION_MESSAGE,
							JOptionPane.WARNING_MESSAGE,
							JOptionPane.QUESTION_MESSAGE,
							JOptionPane.PLAIN_MESSAGE};
				//�N���w�T��������JOptionPane���O�`�Ʋզ���ư}�C

				//����ܶs, �ó]�w�إ߹������T����ܲ�
				for(int i=0; i<rbArray.length; i++){

					if(rbArray[i].isSelected()){ //�P�_�Q�������ܶs
						String strMsg = rbArray[i].getActionCommand();
						//���o��ܶs���ʧ@�R�O�r��

						JOptionPane.showMessageDialog(	null,
							strMsg + "�����e", strMsg, msgType[i]);
							//�H�ʧ@�R�O�r��P�T������, 
							//�إߨ���ܰT����ܲ�
					}
				}
			}
		});

		JPanel jpMsg = new JPanel(new GridLayout(2, 3));
		ButtonGroup bg = new ButtonGroup();

		//�N��ܶs�[�JjpMsg�����P���s�s��
		for(JRadioButton elm : rbArray){
			jpMsg.add(elm);
			bg.add(elm);
		}

		Container cp = getContentPane(); //���o���e����

		cp.add(jpMsg);
		cp.add(btnMsg, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����|�P�N�ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 150);
		setVisible(true);
	}

	public static void main(String args[]) {
		new MessageEX(); //�ŧi�����ج[����
	}
}