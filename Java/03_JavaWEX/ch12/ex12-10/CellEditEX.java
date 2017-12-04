import javax.swing.*;  //�ޥήM��
import javax.swing.border.*;
import javax.swing.table.*;
//�ޥΩw�q��UJTable����B�z��椧�����B���O���M��

import javax.swing.event.*;
//�ޥΥ]�tCellEditorEvent�PCellEditorListener���M��

import java.util.EventObject; //�ޥΥN��ƥ�EventObject����
import java.util.Locale; //�ޥ�Locale���O
import javax.swing.text.*;  //����DefaultFormatter, �Ψ�l���O���M��
import java.text.NumberFormat; //�ޥ�NumberFormat���O
import java.awt.*;
import java.awt.event.*;

public class CellEditEX extends JFrame {

	JTable tbBook = new JTable(new BookTableModel());
	//�HTableModel����ŧi�إߪ�檺JTable����

	public CellEditEX() {

		JFormattedTextField ftfPrice = 
			new JFormattedTextField(NumberFormat.getNumberInstance());
		//�ŧi�ϥμƦr�榡���榡�Ƥ�r��

		NumberFormatter nfrPrice = 
			(NumberFormatter)ftfPrice.getFormatter();
		//���o�榡�Ƥ�r��ϥΪ��榡����

		nfrPrice.setMinimum(new Double(0.0));
		//�]�w���\��J���Ʀr�̤p��

		FormattedCellEditor fcePrice= new FormattedCellEditor(ftfPrice);
		//�ŧi�榡�ƽs�边����

		tbBook.setDefaultEditor(Double.class, fcePrice);
		//�]�wDouble���O�ϥή榡�ƪ��s�边����

		tbBook.setDefaultEditor(
			String.class, new PublisherCellEditor());
		//�]�w�B�zString���O��ƨϥΪ���Ķ������

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
		setSize(580, 147);
		setVisible(true);
	}

	public static void main(String args[]) {
		new CellEditEX(); //�ŧi�����ج[����	 
	}
}

//�w�q�B�z���y��ƪ�Model����
class BookTableModel extends AbstractTableModel {

	Object[][] bookData = {
		{"Java 2 �J���i�� �V �A��JDK6.0", "����T", 590.0,
		 "P4137",	"2005-5-1", true},
		{"Visual C++.NET �J���i��", "����T", 750.0, 
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
		String.class, String.class, Double.class, String.class,
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

//�H�~��AbstractCellEditor���O��@TableCellEditor�������覡
//�ŧi�s�边���O
class PublisherCellEditor extends AbstractCellEditor
											implements TableCellEditor {

	String[] publishers = {"����T", "�줸���", "�l���T"};
	//�ŧi�զX������ﶵ

	JComboBox cmbEditor = new JComboBox(publishers);
	//�ŧi�զX���

	JTextField tfEditor = new JTextField(); //�ŧi��r��

	Component curComponent; //�ثe�ϥΪ�����

	//�P�_�O�_�i�s��
	public boolean isCellEditable(EventObject anEvent){

		boolean blRet = false;

		//�Y�L�޵o�ƥ�Τ޵o�ƥ󪺫��A���ƹ��ƥ�, 
		//�B���U�ƹ������U, �h�s�边�i�s��
		if((anEvent == null) || 
			(anEvent instanceof MouseEvent) && 
				(((MouseEvent)anEvent).getClickCount() == 2))
			blRet = true;

		System.out.println("isCellEditable() �Ǧ^�� : " + blRet);

		return blRet;
	}

	//���o�ثe�s�边���󪺭�
	public Object getCellEditorValue() {

		Object currentValue; //�ثe��J���󪺸��

		//�̷ӥثe�ϥΪ��s�褸����o�]�w��
		if(curComponent == cmbEditor){
			currentValue = cmbEditor.getSelectedItem();
			//���o�ثe�զX����������
		}
		else{
			currentValue = tfEditor.getText();
			//���o�ثe��r�檺��J��
		}

		System.out.println("getCellEditorValue() �Ǧ^�� : " 
			+ currentValue);

		return currentValue;
	}
	
	public Component getTableCellEditorComponent(
										JTable table, //�B�νs�边���󪺪�檫��
										Object value, //�x�s�檺��
										boolean isSelected, //�x�s��O�_�Q���
										int row, //�x�s�檺�C����
										int column){ //�x�s�檺�����		

		//�P�_���W�٬O�_��"�X����", �O�h�ϥβզX������s�边
		if(table.getColumnName(column).equals("�X����")){
			cmbEditor.setSelectedItem(value); //�]�w�զX����������
			cmbEditor.setBorder(null); //�]�w���ϥήؽu
			curComponent = cmbEditor; //�]�w�ثe�ϥνs�边���զX���
		}
		else{
			tfEditor.setText(value.toString()); //�]�w��r�檺���e
			tfEditor.setBorder(null); //�]�w���ϥήؽu
			curComponent = tfEditor; //�]�w�ثe�ϥνs�边����r��
		}
		System.out.println("getTableCellEditorComponent() �Ǧ^�� : " 
			+	curComponent.getClass().getName());

		return curComponent; //�Ǧ^�s�边
	}

	public boolean stopCellEditing(){ //�л\����s��ʧ@����k

		boolean blRet = super.stopCellEditing();
		//�I�s��¦���O��stopCellEditing()��k

		System.out.println("stopCellEditing() �Ǧ^�� : " + blRet);

		return blRet;
		//�I�s��¦���O��stopCellEditing()��k, �öǦ^��
	}
}