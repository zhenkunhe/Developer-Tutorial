import javax.swing.*;  //�ޥήM��
import javax.swing.border.*;
import javax.swing.table.*;
//�ޥΩw�q��UJTable����B�z��椧�����B���O���M��

import java.awt.*;

public class BookModelEX extends JFrame {

	public BookModelEX() {

		JTable tbBook = new JTable(new BookTableModel());
		//�HModel����ŧi�إߪ�檺JTable����

		TableColumn colTitle = tbBook.getColumnModel().getColumn(0);
		//���oJTable����ϥΪ�ColumnModel����,
		//�A���o����Ĥ@�檺TableColumn����

		colTitle.setPreferredWidth(200); //�]�w��쪺�ߦn�e�׬�150

		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new BorderLayout(10, 10));
		//�إߦU�ϰ�����B�������Z��10��BorderLayout����

		cp.add(new JScrollPane(tbBook));		
		//�N����[�J�����ϰ�

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����|�P�N�ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 147);
		setVisible(true);
	}

	public static void main(String args[]) {
		new BookModelEX(); //�ŧi�����ج[����	 
	}
}

//�w�q�B�z���y��ƪ�Model����
class BookTableModel extends AbstractTableModel {

	Object[][] bookData = {
		{"Java 2 �J���i�� �V �A��JDK6.0", "����T", 590.0,
		 "P4137",	"2005-5-1"},
		{"Visual C++.NET �J���i��", "����T", 750.0, 
		 "P3237", "2003-9-1"},
		{"Access 2003 ������s", "����T", 590.0, 
		 "A4023", "2004-3-1"},
		{"Access 2003 �{���]�p", "����T", 660.0, 
		 "A4033",	"2004-5-1"},
		{"JSP �ʺA�����J�����", "����T", 720.0, 
		 "W3135", "2003-12-1"},
		{"ASP �ʺA�����J�����", "����T", 580.0, 
		 "W4075", "2004-7-1"}};
	//�ŧi�x�s���y��ƪ��G���}�C

	String[] colName = {"�ѦW", "�X����", "���", "�Ѹ�", "�X�����"};
	//�ŧi�x�s���W�٪��r��

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
}