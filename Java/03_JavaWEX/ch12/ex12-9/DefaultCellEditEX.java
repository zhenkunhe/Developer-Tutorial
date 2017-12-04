import javax.swing.*;  //�ޥήM��
import javax.swing.border.*;
import javax.swing.table.*;
//�ޥΩw�q��UJTable����B�z��椧�����B���O���M��

import javax.swing.event.*;
//�ޥΥ]�tCellEditorEvent�PCellEditorListener���M��

import java.awt.*;
import java.awt.event.*;

public class DefaultCellEditEX extends JFrame {

	JTable tbBook = new JTable(new BookTableModel());
	//�HTableModel����ŧi�إߪ�檺JTable����

	public DefaultCellEditEX() {

		JComboBox cmbPublisher = new JComboBox();
		cmbPublisher.addItem(Publisher.KingsInfo);
		cmbPublisher.addItem(Publisher.BitC);
		cmbPublisher.addItem(Publisher.GKInfo);
		cmbPublisher.addItem(Publisher.Other);
		//�N�C�|�l�[�J�զX��������ﶵ

		tbBook.setDefaultEditor(
			Publisher.class, new DefaultCellEditor(cmbPublisher));	
		//�]�w��JPublisher�C�|���A�ϥΪ��զX���

		TableCellEditor tceDouble = tbBook.getDefaultEditor(Double.class);

		//�H�ΦW���O���覡
		//�w�q�B���U��ťChangeEvent�ƥ�CellEditorListener��ť������
		tceDouble.addCellEditorListener(new CellEditorListener(){

			//�^�������s��ʧ@
			public void editingStopped(ChangeEvent e){
				System.out.println("�����s����");
			}

			//�^�������s��ʧ@
			public void editingCanceled(ChangeEvent e){
				System.out.println("�����s����");
			}
		});

		TableColumn colTitle = tbBook.getColumnModel().getColumn(0);
		//���oJTable����ϥΪ�ColumnModel����,
		//�A���o�����1�檺TableColumn����

		colTitle.setPreferredWidth(200);
		//�]�w��쪺�ߦn�e�׬�200

		TableColumn colPublisher = 
			tbBook.getColumnModel().getColumn(1);
		//���oJTable����ϥΪ�ColumnModel����,
		//�A���o�����2�檺TableColumn����

		colPublisher.setPreferredWidth(95);
		//�]�w��쪺�ߦn�e�׬�95

		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new BorderLayout(10, 10));
		//�إߦU�ϰ�����B�������Z��10��BorderLayout����

		cp.add(new JScrollPane(tbBook));		
		//�N����[�J�����ϰ�

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����|�P�N�ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(570, 147);
		setVisible(true);
	}

	public static void main(String args[]) {
		new DefaultCellEditEX(); //�ŧi�����ج[����	 
	}
}

enum Publisher { //�w�q�B�z���y�������C�|���A

	KingsInfo("����T"), BitC("�줸���"),
	GKInfo("�l���T"), Other("��L");
	//�I�s�غc�l�w�q�U�C�|�l
	
	String name; //�x�s�C�|�l�N���X���ӦW�٪��r��

	Publisher(String name){ this.name = name; }
	//�C�|���A���غc�l, �N�]�w�C�|�l�N���X���ӦW�٦r��

	public String toString(){ return name;}
	//�N�C�|�l����
}

//�w�q�B�z���y��ƪ�Model����
class BookTableModel extends AbstractTableModel {

	Object[][] bookData = {
		{"Java 2 �J���i�� �V �A��JDK6.0", Publisher.KingsInfo, 590.0,
		 "P4137", "2005-5-1", true},
		{"Visual C++.NET �J���i��", Publisher.KingsInfo, 750.0, 
		 "P3237", "2003-9-1", true},
		{"Access 2003 ������s", Publisher.KingsInfo, 590.0, 
		 "A4023", "2004-3-1", true},
		{"Access 2003 �{���]�p", Publisher.KingsInfo, 660.0, 
		 "A4033", "2004-5-1", true},
		{"JSP �ʺA�����J�����", Publisher.BitC, 720.0, 
		 "W3135", "2003-12-1", true},
		{"ASP �ʺA�����J�����", Publisher.BitC, 580.0, 
		 "W4075", "2004-7-1", true},
		{"Access 2000 �{���]�p", Publisher.KingsInfo, 690.0,
		 "A9193", "2000-9-1", false}};
	//�ŧi�x�s���y��ƪ��G���}�C

	String[] colName = 
		{"�ѦW", "�X����", "���", "�Ѹ�", "�X�����", "�O�_�o��"};
	//�ŧi�x�s���W�٪��r��

	Class[] types = new Class[] { 
		String.class, 
		Publisher.class, //�C�|���A
		Double.class, String.class,
		String.class, //�Ъ`�N! Date�g�L�榡�ƫ�, ���O��String
		Boolean.class};
	//���t�XTableCellEditor����, 
	//�����ŧi�x�s�ɮ׸�Ƥ����O��Class�}�C

	public int getColumnCount(){ return colName.length; }
	//���o��쪺�Ӽ�

	public int getRowCount(){ return bookData.length; }
	//���o��ƪ��C��

	//���o���w��B�C��m���x�s����
	public Object getValueAt(int row, int col){
		return bookData[row][col];
	}

	//���o���W��, �i���w�q
	public String getColumnName(int column){
		return colName[column];
	}

	public Class getColumnClass(int col) { return types[col]; }
	//���o�ɮ׬Y����Ƥ����O��Class����,
	//����k�N��TableCellEditor����B�@�ϥ�

	//�Y���\������ƥ�����@����k
	public void setValueAt(Object value, int row, int col) {
		bookData[row][col] = value;
		fireTableCellUpdated(row, col);
	}

	//�Y���\�s�����ƥ�����@����k
	public boolean isCellEditable(int row, int col) {
		return true;        
    }
}