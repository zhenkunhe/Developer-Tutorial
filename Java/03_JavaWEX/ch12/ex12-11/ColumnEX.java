import javax.swing.*;  //�ޥήM��
import javax.swing.border.*;
import javax.swing.table.*;
//�ޥΩw�q��UJTable����B�z��椧�����B���O���M��

import javax.swing.event.*;
//�ޥΥ]�tCellEditorListener���M��

import javax.swing.text.NumberFormatter;
//�ޥ�NumberFormatter���O

import java.awt.*;
import java.awt.event.*;

import java.text.NumberFormat; //�ޥ�NumberFormat���O

public class ColumnEX extends JFrame {

	BookTableModel btmBook = new BookTableModel();
	//�ŧi�x�s�������ƪ�TableModel����

	JTable tbBook = new JTable(btmBook);
	//�HTableModel����ŧi�إߪ�檺JTable����

	public ColumnEX() {

		tbBook.setRowSelectionInterval(0, 0); //�]�w�����1�C�����

		TableColumnModel tcmBook = tbBook.getColumnModel();
		//���o�������ƪ�TableColumnModel����

		//���U��ťTableColumnModel����TableColumnModelEvent�ƥ�
		tcmBook.addColumnModelListener(
			new TableColumnModelListener(){

			//�s�W���
			public void columnAdded(TableColumnModelEvent e){
				System.out.println(
					"columnAdded(TableColumnModelEvent e) " +
					" formIndex = " + e.getFromIndex() + 
					" toIndex = " + e.getToIndex());
			}

			//�������
			public void columnRemoved(TableColumnModelEvent e){
				System.out.println(
					"columnRemoved(TableColumnModelEvent e)" +
					" formIndex = " + e.getFromIndex() + 
					" toIndex = " + e.getToIndex());
			}

			//�������
			public void columnMoved(TableColumnModelEvent e){
				System.out.println(
					"columnMoved(TableColumnModelEvent e)" +
					" formIndex = " + e.getFromIndex() + 
					" toIndex = " + e.getToIndex());
			}

			//����������
			public void columnMarginChanged(ChangeEvent e){
				System.out.println(
					"columnMarginChanged(ChangeEvent e)");
			}

			//���������
			public void columnSelectionChanged(
											ListSelectionEvent e){
				System.out.println(
					"void columnSelectionChanged(ListSelectionEvent e)");
			}
		});

		TableColumn colTitle = tcmBook.getColumn(0);
		//���oJTable����ϥΪ�ColumnModel����,
		//�A���o�����1�檺TableColumn����

		colTitle.setPreferredWidth(200);
		//�]�w��쪺�ߦn�e�׬�200

		colTitle.setCellRenderer(new BasicRenderer());
		//�]�w�ϥ�BasicRenderer��Ķ������

		JComboBox cmbPublisher = new JComboBox();
		cmbPublisher.addItem("����T");
		cmbPublisher.addItem("�줸���");
		cmbPublisher.addItem("�l���T");
		cmbPublisher.addItem("��L");
		//�إߨѥX������ϥΪ��զX���

		TableColumn colPublisher =  tcmBook.getColumn(1);
		//���o����X�����檺TableColumn
			
		colPublisher.setCellEditor(
			new DefaultCellEditor(cmbPublisher));
		//�]�w�X������ϥβզX��������s�边

		colPublisher.setCellRenderer(new BasicRenderer());
		//�]�w�ϥ�BasicRenderer��Ķ������

		colPublisher.setPreferredWidth(95);
		//�]�w��쪺�ߦn�e�׬�95

		JFormattedTextField ftfPrice = 
			new JFormattedTextField(
				NumberFormat.getNumberInstance());
		//�ŧi�ϥμƦr�榡���榡�Ƥ�r��

		NumberFormatter nfrPrice = 
			(NumberFormatter)ftfPrice.getFormatter();
		//���o�榡�Ƥ�r��ϥΪ��榡����

		nfrPrice.setMinimum(new Double(0.0));
		//�]�w���\��J���Ʀr�̤p��

		FormattedCellEditor fcePrice= 
			new FormattedCellEditor(ftfPrice);
		//�ŧi�榡�ƽs�边����

		TableColumn colPrice =  tcmBook.getColumn(2);
		//���o�������檺TableColumn

		colPrice.setCellEditor(fcePrice);
		//�]�w�����ϥή榡�ƪ��s�边����

		colPrice.setCellRenderer(new PriceRenderer());
		//�]�w�ϥ�BasicRenderer��Ķ������

		TableColumn colID =  tcmBook.getColumn(3);
		//���o����Ѹ��檺TableColumn

		colID.setCellRenderer(new BasicRenderer());
		//�]�w�ϥ�BasicRenderer��Ķ������

		TableColumn colPDate =  tcmBook.getColumn(4);
		//���o����Ѹ��檺TableColumn

		colPDate.setCellRenderer(new BasicRenderer());
		//�]�w�ϥ�BasicRenderer��Ķ������

		TableColumn colInPrint =  tcmBook.getColumn(5);
		//���o����O�_�o���檺TableColumn

		BooleanRenderer brInPrint = new BooleanRenderer();

		colInPrint.setCellRenderer(brInPrint);
		//�]�w�ϥ�BasicRenderer��Ķ������

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
		new ColumnEX(); //�ŧi�����ج[����	 
	}
}

//�w�q�B�z���y��ƪ�TableModel����
class BookTableModel extends AbstractTableModel {

	Object[][] bookData = {
		{"Java 2 �J���i�� �V �A��JDK5.0", "����T", 590.0,
		 "P4137", "2005-5-1", true},
		{"Visual C++.NET �J���i��", "�줸���", 750.0, 
		 "P3237", "2003-9-1", true},
		{"Access 2003 ������s", "����T", 590.0, 
		 "A4023", "2004-3-1", true},
		{"Access 2003 �{���]�p", "����T", 660.0, 
		 "A4033", "2004-5-1", true},
		{"JSP �ʺA�����J�����", "����T", 720.0, 
		 "W3135", "2003-12-1", true},
		{"ASP �ʺA�����J�����", "����T", 580.0, 
		 "W4075", "2004-7-1", true},
		{"Access 2000 �{���]�p", "����T", 690.0,
		 "A9193", "2000-9-1", false}};
	//�ŧi�x�s���y��ƪ��G���}�C

	String[] colName = 
		{"�ѦW", "�X����", "���", "�Ѹ�", "�X�����", "�O�_�o��"};
	//�ŧi�x�s���W�٪��r��

	Class[] types = new Class[] { 
		String.class, 
		String.class, //�C�|���A
		Double.class, String.class,
		String.class, //�Ъ`�N! Date�g�L�榡�ƫ�, ���O��String
		Boolean.class};
	//���t�XTableCellEditor����, 
	//�����ŧi�x�s�ɮ׸�Ƥ����O��Class�}�C

	private int[] HAlign = {JLabel.LEFT, JLabel.LEFT, JLabel.RIGHT,
									   JLabel.LEFT, JLabel.RIGHT, JLabel.CENTER};
	//����ƪ�����覡

	private int[] colWidth = {200, 100, 50, 50, 50, 50};
	//�U��쪺�e��

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

	//���o����r������覡
	public int getTextAlignment(int column){
		return HAlign[column];
	}

	//���o��쪺�̾A�e��
	public int getColumnWidth(int column){
		return colWidth[column];
	}
}

//�H�~��DefaultCellEditor���O���覡, �w�q
class FormattedCellEditor extends DefaultCellEditor {
	FormattedCellEditor(final JFormattedTextField formattedTextField){ //�غc�l

		super(formattedTextField); //�I�s��¦���O���غc�l

		formattedTextField.removeActionListener(delegate);
		//������ťActionEvent�ƥ�delegate��ť������

		//�H�ΦW���O���覡�w�q�B�ŧi�s��EditorDelegate����
		delegate = new EditorDelegate(){

			//�л\�]�wdelegate����Ȫ�setValue()��k
			public void setValue(Object value){
				formattedTextField.setValue(value);
			}

			//�л\���odelegate����s��Ȫ�getCellEditorValue()��k
			public Object getCellEditorValue(){
				return formattedTextField.getValue();
			}
		};

		formattedTextField.addActionListener(delegate);
		//���U��ťActionEvent�ƥ�delegate��ť������

		formattedTextField.setBorder(null);
		//�]�w���ϥήؽu
	}
}

//�H�~��JCheckBox���O�P��@TableCellRenderer�������覡
//�w�q���Boolean���O��ƪ���Ķ������
class BooleanRenderer extends JCheckBox
							implements TableCellRenderer {

	boolean repaint = false; //�Ω�P�_�O�_���歫ø�ʧ@���ݩ�

	public Component getTableCellRendererComponent(
							JTable table, //�ϥ���Ķ�����󪺪�椸��
							Object value, //��Ķ�����B�z������x�s�檺��
							boolean isSelected, //�Q�B�z�x�s��O�_�Q���
							boolean hasFocus, //�Q����x�s��O�_�֦��J�I
							int row, //�Q����x�s��Ҧb��m���C��
							int column){ //�Q����x�s��Ҧb��m�����
	
		DefaultTableColumnModel tcmBook = 
			(DefaultTableColumnModel) table.getColumnModel();
		//���oJTable����ϥΪ�ColumnModel����

		TableColumn col = tcmBook.getColumn(column);
		//���oJTable������w���ޭȩҥN���椧TableColumn����

		int modelIndex = col.getModelIndex();
		//���oTableColumn����bModel�������ҫ�����

		BookTableModel  botm = 
			(BookTableModel) table.getModel();
		//���o�x�sJTable������ܤ����y��ƪ�Model����

		setHorizontalAlignment(botm.getTextAlignment(modelIndex));
		//�qModel������o���w��쪺��������覡

		setSelected((Boolean)value);
		//�]�w�֨�����O�_���

		//�P�_�O�_�֦��J�I, 
		//�O�h�]�w�I���C�⬰������
		//��ø�P�C����L�x�s��
		if(hasFocus){
			setBackground(Color.pink);
			 //�]�w�֦��J�I�ɥH�����⬰�I���C��

			if(!repaint){
				table.repaint(); //��ø���
				repaint = true; //�]�w�N���歫ø
			}
			else
				repaint = false; //���]��ø����

			return this;
		}

		if(!(Boolean)value){			
			setBackground(Color.blue);
			//�Y�]�w�Ȭ�true, �h�]�w�I���C�⬰�Ŧ�
		}
		else{

			//�̷ӿ�����A�]�w�x�s�檺�I���P�e���C��
			if(isSelected){
				setBackground(Color.lightGray);
				setForeground(Color.darkGray);
			}
			else{
				setBackground(Color.white);
				setForeground(Color.black);
			}
		}

		return this;
	}
}

//�H�~��DefaultTableCellRenderer���O���覡
//�w�q�B�z�U������ƪ�BasicRenderer��Ķ�����O
class BasicRenderer extends DefaultTableCellRenderer {

	public Component getTableCellRendererComponent(
							JTable table, //�ϥ���Ķ�����󪺪�椸��
							Object value, //��Ķ�����B�z������x�s�檺��
							boolean isSelected, //�Q�B�z�x�s��O�_�Q���
							boolean hasFocus, //�Q����x�s��O�_�֦��J�I
							int row, //�Q����x�s��Ҧb��m���C��
							int column){ //�Q����x�s��Ҧb��m�����

		super.getTableCellRendererComponent(
			table, value, isSelected, hasFocus, row, column);
		//�I�s��¦���O��getTreeCellRendererComponent()��k

		DefaultTableColumnModel tcmBook = 
			(DefaultTableColumnModel) table.getColumnModel();
		//���oJTable����ϥΪ�ColumnModel����

		TableColumn col = tcmBook.getColumn(column);
		//���oJTable������w���ޭȩҥN���椧TableColumn����

		int modelIndex = col.getModelIndex();
		//���oTableColumn����bModel���󪺯��ޭ�

		BookTableModel  botm = 
			(BookTableModel) table.getModel();
		//���o�x�sJTable������ܤ����y��ƪ�Model����

		setHorizontalAlignment(botm.getTextAlignment(modelIndex));
		//�qTableModel������o���w��쪺��������覡

		//�]�w�֦��J�I�ɪ��I���C��, �òפ��k������
		if(hasFocus){
			setBackground(Color.pink);
			return this;
		}

		if(!(Boolean)botm.getValueAt(row, 5)){
			setBackground(Color.blue);
			//�Y�O�_�o�����Q���, �h�]�w�I���C�⬰�Ŧ�
		}
		else{

			//�̷ӿ�����A�]�w�x�s�檺�I���P�e���C��
			if(isSelected){
				setBackground(Color.lightGray);
				setForeground(Color.darkGray);
			}
			else{
				setBackground(Color.white);
				setForeground(Color.black);
			}
		}

		return this;
	}
}

//�w�qPriceRenderer���O�~�Ӧ�BasicRenderer���O
class PriceRenderer extends BasicRenderer {

	public Component getTableCellRendererComponent(
							JTable table, //�ϥ���Ķ�����󪺪�椸��
							Object value, //��Ķ�����B�z������x�s�檺��
							boolean isSelected, //�Q�B�z�x�s��O�_�Q���
							boolean hasFocus, //�Q����x�s��O�_�֦��J�I
							int row, //�Q����x�s��Ҧb��m���C��
							int column){ //�Q����x�s��Ҧb��m�����

		super.getTableCellRendererComponent(
			table, value, isSelected, hasFocus, row, column);
		//�I�s��¦���O��getTreeCellRendererComponent()��k

		Double price = (Double) value; //���o���

		setForeground((price > 700.0 ? Color.red : Color.black));
		//�P�_�O�_�j��700, �O�h�]�w�I���C�⬰����, 
		//�Ϥ��]�w���¦�

		return this;
	}
}