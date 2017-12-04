import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��
import java.util.*;

public class ConfirmEX extends JFrame{

	JRadioButton[] rbArray = { new JRadioButton("�w�]", true),
												new JRadioButton("�O�B�_"),
												new JRadioButton("�O�B�_�B����"),
												new JRadioButton("�T�{�B����")};
	//�ŧi����ﶵ��������ܶs

	JLabel lbConfirm = new JLabel("�^���T�� : ");
	//�ŧi��ܨϥΪ̫��U���s���T��������

	ConfirmEX(){

		JButton btnConfirm = new JButton("��ܽT�{��ܲ�");
		//�ŧi�I�s�T�{��ܲ������s

		//���U��ťActionEvent�ƥ󪺺�ť��
		btnConfirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				int[] optionType = {
							JOptionPane.DEFAULT_OPTION, 
							JOptionPane.YES_NO_OPTION,
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.OK_CANCEL_OPTION};
				//�N���w�ﶵ������JOptionPane���O�`�Ʋզ���ư}�C

				int result = -1; //�x�s�T�{��ܲ����Ǧ^��
				
				//����ܶs, �ó]�w�إ߹������T�{��ܲ�
				for(int i=0; i<rbArray.length; i++){

					if(rbArray[i].isSelected()){ //�P�_�Q�������ܶs

						String strMsg = rbArray[i].getActionCommand();
						//���o��ܶs���ʧ@�R�O�r��

						result = JOptionPane.showConfirmDialog(
							null, "�Ч����T�{�ʧ@", "�T�{��ܲ�",
							optionType[i]);
						//��ܽT�{��ܲ�
					}
				}

				//�̷ӨϥΪ̫��U�T�{��ܲ������s�᪺�^�ǭ� 
				//�]�w������ܪ����e
				switch(result){
				case JOptionPane.YES_OPTION: //�POK_OPTION�ۦP
					lbConfirm.setText("�^���T�� :  ���U �O �� �T�{ ���s");
					break;
				case JOptionPane.NO_OPTION:
					lbConfirm.setText("�^���T�� :  ���U �_ ���s");
					break;
				case JOptionPane.CANCEL_OPTION:
					lbConfirm.setText("�^���T�� :  ���U ���� ���s");
					break;
				case JOptionPane.CLOSED_OPTION:
					lbConfirm.setText("�^���T�� :  ���������T�{��ܲ�");
					break;
				}
			}
		});
		
		Box bxConfirm = new Box(BoxLayout.X_AXIS);
		ButtonGroup bg = new ButtonGroup();

		//�N��ܶs�[�JjpConfirm�����P���s�s��
		for(JRadioButton elm : rbArray){
			bxConfirm.add(elm);
			bxConfirm.add(Box.createHorizontalStrut(5));
			bg.add(elm);
		}

		Container cp = getContentPane(); //���o���e����
		//cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
		//�]�w�B��GridLayout�t�m����

		cp.add(bxConfirm);
		cp.add(lbConfirm, BorderLayout.SOUTH);
		cp.add(btnConfirm, BorderLayout.EAST);

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����|�P�N�ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(480, 100);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ConfirmEX(); //�ŧi�����ج[����
	}
}