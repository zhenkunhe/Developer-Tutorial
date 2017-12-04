import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��
import java.util.*;

public class CheckBoxEX extends JFrame{

	JLabel lbTitle = new JLabel("������� : ");

	//�ŧi�֨��������, �ó]�w���Ҥ�r
	JCheckBox cbSpe1 = new JCheckBox("PHP"),
		  cbSpe2 = new JCheckBox("JSP"),
		  cbSpe3 = new JCheckBox("ASP"),
		  cbSpe4 = new JCheckBox("ASP.NET"),
		  cbSpe5 = new JCheckBox("Perl");

	//�w�q�ëŧi�^��ItemEvent�ƥ󪺺�ť��
	ItemListener il = new ItemListener(){

		LinkedList<JCheckBox> llSel = new LinkedList<JCheckBox>();
		//�ŧi�x�s���A�������JCheckBox����

		public void itemStateChanged(ItemEvent e) {

			JCheckBox cbSource = (JCheckBox) e.getSource();	//���o�ƥ�ӷ�����

			//�P�_Ĳ�o��������ƥ��٬O��������ƥ�
			if(e.getStateChange() == ItemEvent.SELECTED)
				llSel.add(cbSource);
			else if(e.getStateChange() == ItemEvent.DESELECTED)
				llSel.remove(cbSource);

			StringBuffer sbActionCommand = new StringBuffer("������� : ");

			//�B��for�j���X�Q�����JCheckBox���󪺰ʧ@�R�O�r��
			for(JCheckBox elm: llSel)
				sbActionCommand.append(elm.getActionCommand() + ", ");

			lbTitle.setText(sbActionCommand.toString());
			//�]�w�Q���lbText����ܤ��e
		}	
	};

	CheckBoxEX(){

		Box boxSpe = new Box(BoxLayout.X_AXIS); //�إߩ�m�֨������Box

		boxSpe.add(cbSpe1); //�N�����JBox�e��
		boxSpe.add(Box.createHorizontalStrut(10));
		boxSpe.add(cbSpe2);
		boxSpe.add(Box.createHorizontalStrut(10));
		boxSpe.add(cbSpe3);
		boxSpe.add(cbSpe4);
		boxSpe.add(Box.createHorizontalStrut(10));
		boxSpe.add(Box.createHorizontalStrut(10));
		boxSpe.add(cbSpe5);

		cbSpe1.addItemListener(il); //���U�^��ItemEvent�ƥ󪺺�ť��
		cbSpe2.addItemListener(il);
		cbSpe3.addItemListener(il);
		cbSpe4.addItemListener(il);
		cbSpe5.addItemListener(il);

		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new GridLayout(2, 1)); //�]�w�B��GridLayout�t�m����
		cp.add(lbTitle); //�N����[�J���e����
		cp.add(boxSpe);

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 100);
		setVisible(true);
	}

	public static void main(String args[]) {
		new CheckBoxEX(); //�إߵ����ج[����
	}
}