import javax.swing.*;
import javax.swing.border.*; //�ޥΫإ߮ؽu��Border����
import javax.swing.event.*;
//�ޥΥ]�tListSelectionListener�������M��

import java.awt.*;
import java.awt.event.*;

public class ListEX extends JFrame{

	JList ltSS = new JList(); //�ŧi���_�l�ﶵ���M����

	String[] strItem = {"PHP", "JSP", "ASP", "ASP.NET", "Perl"};
	//�ŧi�x�s�M�����ﶵ���e���r��}�C

	JList ltMIS = new JList(strItem); //�H�r��}�C�_�l�ŧi�M����
	JList ltSIS = new JList(strItem);

	JLabel lbSS = new JLabel(" ");
	JLabel lbMIS = new JLabel();
	JLabel lbSIS = new JLabel();
	//�ŧi��ܲM�����Q������ؤ��e������

	//�w�q�ëŧi�^��ListSelectionEvent����ť��
	ListSelectionListener lsl = new ListSelectionListener(){
		public void valueChanged(ListSelectionEvent e){

			if(e.getValueIsAdjusting() == true){
									//�P�_�O�_��s��ƥ󤤤޵o
				System.out.println("�s��ƥ�");
				return;
			}
			else{
				System.out.println("�D�s��ƥ�");
			}

			System.out.println("--------------------------------------\n");

			JList source = (JList)e.getSource();
			//���o�ƥ󪺨ӷ�����

			//�P�_�ӷ�����H�K����^��
			if(source == ltSS)
				lbSS.setText((String)source.getSelectedValue());
			else{
				String selItem = "";

				//���o�M����������ﶵ, �ñN�ﶵ���e��s���r��
				for(Object item: source.getSelectedValues())
					selItem = selItem + (String)item + ", ";

				if(source == ltMIS)
					lbMIS.setText(selItem);
				else
					lbSIS.setText(selItem);
			}
		}
	};

	ListEX(){

		ltSS.setListData(strItem); //�]�w�������

		ltSS.setVisibleRowCount(4); //�]�w�M�������j�p
		ltMIS.setVisibleRowCount(4);
		ltSIS.setVisibleRowCount(4);

		//�]�w�M����������Ҧ�
		ltSS.setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		ltMIS.setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		ltSIS.setSelectionMode(
				ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		//�]�w�^���M����ListSelectionEvent�ƥ󪺺�ť��
		ltSS.addListSelectionListener(lsl);
		ltMIS.addListSelectionListener(lsl);
		ltSIS.addListSelectionListener(lsl);

		Box bxSS = new Box(BoxLayout.Y_AXIS);
		bxSS.add(new JLabel("��﫬"));
		//�N�M�����W�ټ��ҥ[�JBox�e����

		bxSS.add(new JScrollPane(ltSS));
		//�H�M�����إ�JScrollPane����, �å[�JBox�e����

		Box bxMIS = new Box(BoxLayout.Y_AXIS);
		bxMIS.add(new JLabel("���s��d��ƿ﫬"));
		bxMIS.add(new JScrollPane(ltMIS));

		Box bxSIS = new Box(BoxLayout.Y_AXIS);
		bxSIS.add(new JLabel("�s��d��ƿ﫬"));
		bxSIS.add(new JScrollPane(ltSIS));

		JPanel jpLS = new JPanel(new GridLayout(1, 3));
		//�ŧi�]�tBox�e����JPanel�e��

		jpLS.add(bxSS); //�NBox�e���[�JJPanel�e����
		jpLS.add(bxMIS);
		jpLS.add(bxSIS);

		JPanel jpResult = new JPanel(new GridLayout(1, 3));
		//�ŧi�]�t��ܲM����������G�����Ҥ�JPanel�e��

		jpResult.add(lbSS); //�N��ܿ�����G�����ҥ[�JJPanel�e����
		jpResult.add(lbMIS);
		jpResult.add(lbSIS);

		Border border = BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(Color.gray, 2),
			"������G",
			TitledBorder.LEFT, TitledBorder.TOP);
		//�ŧi���D�ؽu����, ���D��r�N�a���a�W���, �C�⬰�L��, �e�׬�2

		jpResult.setBorder(border); //�]�wJPanel�ϥΪ��ؽu
		
		Container cp = getContentPane(); //���o���e����
		cp.add(jpLS); //�N����[�J���e����
		cp.add(jpResult,BorderLayout.SOUTH);

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(550, 190);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ListEX(); //�ŧi�����ج[����
	}
}