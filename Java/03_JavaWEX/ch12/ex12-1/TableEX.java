import javax.swing.*;  //�ޥήM��
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.table.*;
//�ޥΥ]�t��UJTable���O�إߪ�檺�����P���O

import java.awt.*;
import java.awt.event.*;

public class TableEX extends JFrame {

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

	JTable tbBook = new JTable(bookData, colName);
	//�H�x�s���y��ƪ��G���}�C
	//�P������W�٦r��}�C�ŧiJTable����

	JLabel lbSel = new JLabel(),
				 lbRange = new JLabel();

	JCheckBox cbCell = new JCheckBox(),
						cbRow = new JCheckBox(),
						cbCol = new JCheckBox();
	//�ŧi���������覡���֨�������

	JCheckBox cbVer = new JCheckBox(),
						cbHor = new JCheckBox();
	//�ŧi����������u�O�_��ܪ��֨�������
	
	public TableEX() {

		tbBook.setSelectionBackground(Color.gray);
		//�]�w����I���C��

		tbBook.setSelectionForeground(Color.lightGray);
		//�]�w����e���C��

		TableColumn colTitle = tbBook.getColumnModel().getColumn(0);
		//���oJTable����ϥΪ�ColumnModel����,
		//�A���o����Ĥ@�檺TableColumn����

		colTitle.setPreferredWidth(200);
		//�]�w��쪺�ߦn�e�׬�200

		cbRow.setSelected(tbBook.getRowSelectionAllowed());
		//�]�w�N��C������֨���������

		cbVer.setSelected(tbBook.getShowVerticalLines());
		cbHor.setSelected(tbBook.getShowHorizontalLines());
		//�]�w�w�]�����

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

		//���U�^��ItemEvent�ƥ󪺺�ť������
		cbVer.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				tbBook.setShowVerticalLines(
					(e.getStateChange() ==  ItemEvent.SELECTED));
				//�]�w���O�_��ܫ����u
			}
		});

		//���U�^��ItemEvent�ƥ󪺺�ť������
		cbHor.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				tbBook.setShowHorizontalLines(
					(e.getStateChange() ==  ItemEvent.SELECTED));
				//�]�w���O�_��ܤ����u
			}
		});

		//���U�^��MouseMotionEvent�ƥ�
		tbBook.addMouseMotionListener(new MouseInputAdapter(){
			public void mouseDragged(MouseEvent e){
				updateMsg(); //��s����d��T��
			}
		});
		
		//���U�^��MouseEvent�ƥ�
		tbBook.addMouseListener(new MouseInputAdapter(){
			public void mouseClicked(MouseEvent e){
				updateMsg(); //��s����d��T��
			}
		});

		JButton btnSelAll = new JButton("�������"),
					  btnUnSel = new JButton("�������");
		//�ŧi�������Ψ�������ʧ@�����s
		
		//���U�^��ActionEvent�ƥ󪺺�ť������
		btnSelAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tbBook.selectAll(); //�]�w�������
				lbRange.setText("�������");
			}
		});

		btnUnSel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tbBook.clearSelection(); //�]�w�������
				lbRange.setText("�����");
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

		//�إߥ]�t����ؽuø�s�覡�֨������Box�e��
		Box bxLine = new Box(BoxLayout.X_AXIS);
		bxLine.add(new JLabel("�ؽu��ø�s : "));
		bxLine.add(cbVer);
		bxLine.add(new JLabel("�����u"));
		bxLine.add(Box.createHorizontalStrut(5));
		bxLine.add(cbHor);
		bxLine.add(new JLabel("�����u"));

		JPanel jpNorth = new JPanel(new GridLayout(2, 1));
		jpNorth.add(bxSel);
		jpNorth.add(bxLine);

		Box bxSelStatus = new Box(BoxLayout.X_AXIS);
		bxSelStatus.add(new JLabel("����d�� : "));
		bxSelStatus.add(lbRange);
		//�N��ܿ���d�򪺼��ҥ[�J�e��

		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new BorderLayout(10, 10));
		//�إߦU�ϰ�����B�������Z��10��BorderLayout����

		cp.add(jpNorth, BorderLayout.NORTH);
		cp.add(new JScrollPane(tbBook));
		cp.add(bxSelStatus, BorderLayout.SOUTH);
		//�N�U�e������[�J����

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����|�P�N�ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 259);
		setVisible(true);
	}

	private void updateMsg(){ //��s��ܰT��

		lbRange.setText(""); //�M����ܤ��e

		int [] selCols = tbBook.getSelectedColumns(),
				selRows = tbBook.getSelectedRows();
		//���o�Q�����B�C�����ޭȰ}�C

		//�̷ӳQ������֨�����]�w������ܪ��r��
		if(cbCell.isSelected()){
			String strLeftUp = "( " + selCols[0] + ", " + selRows[0]	+ " )",
					   strRightDown = "( " + selCols[selCols.length - 1] 
				+ ", " + selRows[selRows.length - 1] + " )";
			//�]�w��ܽd�򪺥��W���P�k�U��

			lbRange.setText(strLeftUp + " �� " + strRightDown);
			//�]�w��ܿ���d�򪺥��W���P�k�U��
		}
		else{
			if(cbRow.isSelected()){
				lbRange.setText("�� " + selRows[0] + " �� "
					+ selRows[selRows.length - 1] + " �C");
				//�C����d�򪺦r��
			}
			else if(cbCol.isSelected()){
				lbRange.setText("�� " + selCols[0] + " �� "
					+ selCols[selCols.length - 1] + " ��");
				//�����d�򪺦r��
			}					
		}
	}

	public static void main(String args[]) {
		new TableEX(); //�ŧi�����ج[����
	}
}