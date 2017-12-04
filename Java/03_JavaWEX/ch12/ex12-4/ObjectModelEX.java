import javax.swing.*;  //�ޥήM��
import javax.swing.border.*;
import javax.swing.table.*;
//�ޥΩw�q��UJTable����B�z��椧�����B���O���M��

import java.awt.*;
import java.util.*;

public class ObjectModelEX extends JFrame {

	BookData[] bookData = {
		new BookData("Java 2 �J���i�� �V �A��JDK6.0", "����T",
			590.0, "P4137",	"2005-5-1", false),
		new BookData("Visual C++.NET �J���i��", "����T", 
			750.0, "P3237", "2003-9-1", false),
		new BookData("Access 2003 ������s", "����T", 
			590.0, "A4023", "2004-3-1", false),
		new BookData("Access 2003 �{���]�p", "����T", 
			660.0,  "A4033", "2004-5-1", false),
		new BookData("JSP �ʺA�����J�����", "����T", 
			720.0, "W3135", "2003-12-1", false),
		new BookData("ASP �ʺA�����J�����", "����T", 
			580.0, "W4075", "2004-7-1", false),
		new BookData("Access 2000 �{���]�p", "����T", 
			690.0, "A9193", "2000-9-1", true)};
	//�ŧi�x�s���y��ƪ��G���}�C

	String[] colName = {"�ѦW", "�X����", "���", "�Ѹ�", "�X�����"};
	//�ŧi�x�s���W�٪��r��

	public ObjectModelEX() {

		BookObjectTableModel botm = new BookObjectTableModel(colName);
		//�H���W�٫ŧiBookObjectTableModel����
		
		for(BookData elm : bookData)
			botm.addBookData(elm);

		JTable tbBook = new JTable(botm);
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
		setSize(550, 147);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ObjectModelEX(); //�ŧi�����ج[����	 
	}
}

class BookData { //�w�q�x�s���y��ƪ�����

	//�w�q���y��ƪ��ݩ�
	private String title, //���y���D
						   publisher, //�X����
						   ID, //�Ѹ�
						   p_Date; //�X�����
	private Double price; //���
	private boolean out_of_print; //�O�_����

	//�غc�l
	BookData(String title, String publisher, Double price, 
		String ID, String p_Date, boolean out_of_print){

		this.title = title;
		this.publisher = publisher;
		this.price = price;
		this.ID = ID;
		this.p_Date =  p_Date;
		this.out_of_print = out_of_print;
		//�]�w���y���
	}

	//�w�q���o���y��ƪ���k
	public String getTitle(){ return title; }
	public String getPublisher(){ return publisher; }
	public Double getPrice(){ return price; }
	public String getID(){ return ID; }
	public String getPDate(){ return p_Date; }
	public boolean getOutOfPrint(){ return out_of_print; }
}

//�w�q�B�z���y��ƪ�Model����
class BookObjectTableModel extends AbstractTableModel {

	private Vector bookData =  new Vector();
	//�w�q�x�s���y��ƪ�Vector�e��

	String[] colName;
	//�ŧi�x�s���W�٪��r��}�C

	BookObjectTableModel(String[] cols){ //�غc�l, �N�ǤJ�����D�r��
		colName = cols;
	}

	//�[�J���y��ƪ���
	public void addBookData(BookData bd){
		bookData.add(bd); //�N����[�JVector�e��
	}

	public int getColumnCount(){ return colName.length; }
	//���o��쪺�Ӽ�

	public int getRowCount(){ return bookData.size(); }
	//���o��ƪ��C��

	//���o���w��B�C��m���x�s����
	public Object getValueAt(int row, int col){
		
			//�P�_�C���ޭȬO�_���T, �����T�h�Ǧ^�Ŧr��
			if(row < 0 || row >= getRowCount())
				return "";
	
			//�P�_����ޭȬO�_���T, �����T�h�Ǧ^�Ŧr��
			if(col < 0 || col >= getColumnCount())
				return "";
	
			BookData bdRow = (BookData) bookData.elementAt(row);
			//�H�C�ư������ޭȨ��o�e�������y��ƪ���
	
			switch(col){ //�H����ާP�_�����o�����y���
			case 0: //��1��
				return bdRow.getTitle(); //���o���D
			case 1: //��2��
				return bdRow.getPublisher(); //���o�X����
			case 2:  //��3��
				return bdRow.getPrice(); //���o���
			case 3: //��4��
				return bdRow.getID(); //���o�Ѹ�
			case 4: //��5��
				return bdRow.getPDate(); //���o�X�����
			case 5: //��6��
				return bdRow.getOutOfPrint(); //���o�O�_����
		}

		return ""; //�Yswitch�ԭz�L�k���o���w��쪺��, �N�Ǧ^�Ŧr��
	}


	//���o���W��, �i���w�q
	public String getColumnName(int column){
		return colName[column];
	}
}