import javax.swing.*;  //�ޥήM��
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.table.*;
//�ޥΥ]�t��UJTable���O�إߪ�檺�����P���O

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TableEventEX extends JFrame {

	Object[][] bookData = {
		{"Java 2 �J���i�� �V �A��JDK6.0", "����T", 590.0,
		 "P4137", "2005-5-1"},
		{"Visual C++.NET �J���i��", "����T", 750.0, 
		 "P3237", "2003-9-1"},
		{"Access 2003 ������s", "����T", 590.0, 
		 "A4023", "2004-3-1"},
		{"Access 2003 �{���]�p", "����T", 660.0, 
		 "A4033", "2004-5-1"},
		{"JSP �ʺA�����J�����", "����T", 720.0, 
		 "W3135", "2003-12-1"},
		{"ASP �ʺA�����J�����", "����T", 580.0, 
		 "W4075", "2004-7-1"}};
	//�ŧi�x�s���y��ƪ��G���}�C

	String[] colName = {"�ѦW", "�X����", "���", "�Ѹ�", "�X�����"};
	//�ŧi�x�s���W�٪��r��

	JLabel lbSelRow = new JLabel(), lbSelCol = new JLabel();
	//��ܿ���C�B����ޭȪ�����

	//��JTable���O�N�B�ΰΦW���O���覡,
	//�л\��@��������k, �H�^����B�C��ListSelectionEvent�ƥ�
	//�ŧi�����, �N�x�s���y��ƪ��G���}�C�P
	//������W�٦r��}�C�ŧi
	JTable tbBook = new JTable(bookData, colName){

		//�^���C����d����ܪ���k
		public void valueChanged(ListSelectionEvent e){

			super.valueChanged(e);
			//�I�s��¦���O��valueChanged()��k, 
			//�_�h�C����ʧ@�L�k���`�B�@

			//�P�_�O�_���\�C���, ���\�~��ܳQ����C�����ޭ�
			if(getRowSelectionAllowed()){
				lbSelRow.setText(Arrays.toString(getSelectedRows()));
				//�N����C�����ޭȳ]�w�����Ҥ���			
			}
			else{
				lbSelRow.setText(null);
			}
		}

		//�^�������d����ܪ���k
		public void columnSelectionChanged(ListSelectionEvent e){

			super.columnSelectionChanged(e);
			//�I�s��¦���O��columnSelectionChanged()��k, 
			//�_�h�����ʧ@�L�k���`�B�@

			//�P�_�O�_���\����, ���\�~��ܳQ����檺���ޭ�
			if(getColumnSelectionAllowed()){
				lbSelCol.setText(Arrays.toString(getSelectedColumns()));
				//�N����檺���ޭȳ]�w�����Ҥ���		
			}
			else{
				lbSelCol.setText(null);
			}
		}
	};

	JCheckBox cbCell = new JCheckBox(),
						cbRow = new JCheckBox(),
						cbCol = new JCheckBox();
	//�ŧi���������覡���֨�������
	
	public TableEventEX() {

		tbBook.setSelectionBackground(Color.gray);
		//�]�w����I���C��

		tbBook.setSelectionForeground(Color.lightGray);
		//�]�w����e���C��

		TableColumn colTitle = 
			tbBook.getColumnModel().getColumn(0);
		//���oJTable����ϥΪ�ColumnModel����,
		//�A���o����Ĥ@�檺TableColumn����

		colTitle.setPreferredWidth(200);
		//�]�w��쪺�ߦn�e�׬�200

		cbRow.setSelected(tbBook.getRowSelectionAllowed());
		//�]�w�N��C������֨���������

		//���U�^��ItemEvent�ƥ󪺺�ť������
		cbCell.addItemListener(new ItemListener(){

			//�^�����ت��A����
			public void itemStateChanged(ItemEvent e){

				tbBook.setCellSelectionEnabled(
					(e.getStateChange() ==  ItemEvent.SELECTED));
				//�]�w�x�s��O�_�i�Q���

				cbRow.setSelected(tbBook.getRowSelectionAllowed());
				cbCol.setSelected(tbBook.getColumnSelectionAllowed());
				//�]�w����C�B�������A���֨�����O�_�Q���
			}
		});

		//���U�^��ItemEvent�ƥ󪺺�ť������
		cbRow.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent e){

				boolean blSel = 
					(e.getStateChange() ==  ItemEvent.SELECTED);
				//�P�_������A�O�_�����

				tbBook.setRowSelectionAllowed(blSel);
				//�]�w�i����C���

				cbCell.setSelected(tbBook.getCellSelectionEnabled());
				//�]�w�O�_�i����x�s��
			}
		});

		//���U�^��ItemEvent�ƥ󪺺�ť������
		cbCol.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){

				boolean blSel = 
					(e.getStateChange() ==  ItemEvent.SELECTED);
				//�P�_������A�O�_�����

				tbBook.setColumnSelectionAllowed(blSel);
				//�]�w���\����

				cbCell.setSelected(tbBook.getCellSelectionEnabled());
				//�]�w�O�_�i����x�s��
			}
		});

		JButton btnSelAll = new JButton("�������"),
					  btnUnSel = new JButton("�������");
		//�ŧi�������Ψ�������ʧ@�����s
		
		//���U�^��ActionEvent�ƥ󪺺�ť������
		btnSelAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tbBook.selectAll(); //�]�w�������
			}
		});

		btnUnSel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tbBook.clearSelection(); //�]�w�������
			}
		});

		//�إߥ]�t�������覡�֨������Box�e��
		Box bxSel = new Box(BoxLayout.X_AXIS);
		bxSel.add(new JLabel("����覡�]�w : "));
		bxSel.add(cbCell);
		bxSel.add(new JLabel("�x�s��"));
		bxSel.add(Box.createHorizontalStrut(5));
		bxSel.add(cbRow);
		bxSel.add(new JLabel("�C"));
		bxSel.add(Box.createHorizontalStrut(5));
		bxSel.add(cbCol);
		bxSel.add(new JLabel("��"));
		bxSel.add(Box.createHorizontalStrut(20));
		bxSel.add(btnSelAll);
		bxSel.add(Box.createHorizontalStrut(5));
		bxSel.add(btnUnSel);

		Box bxSelRow = new Box(BoxLayout.X_AXIS);
		bxSelRow.add(new JLabel("����C : "));
		bxSelRow.add(Box.createHorizontalStrut(5));
		bxSelRow.add(lbSelRow); //�[�J��ܿ���C�����ޭȪ�����
		bxSelRow.add(Box.createHorizontalGlue());

		Box bxSelCol = new Box(BoxLayout.X_AXIS);
		bxSelCol.add(new JLabel("����� : "));
		bxSelCol.add(Box.createHorizontalStrut(5));
		bxSelCol.add(lbSelCol); //�[�J��ܿ���椧���ޭȪ�����
		bxSelCol.add(Box.createHorizontalGlue());

		Box bxSelStatus = new Box(BoxLayout.Y_AXIS);
		bxSelStatus.add(bxSelRow);
		bxSelStatus.add(bxSelCol);
		//�N��ܿ���d�򪺼��ҥ[�J�e��

		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new BorderLayout(10, 10));
		//�إߦU�ϰ�����B�������Z��10��BorderLayout����

		cp.add(bxSel, BorderLayout.NORTH);
		cp.add(new JScrollPane(tbBook));
		cp.add(bxSelStatus, BorderLayout.SOUTH);
		//�N�U�e������[�J����

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����|�P�N�ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 249);
		setVisible(true);
	}

	public static void main(String args[]) {
		new TableEventEX(); //�ŧi�����ج[����
	}
}