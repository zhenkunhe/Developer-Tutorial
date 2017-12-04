import javax.swing.*;  //�ޥήM��
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
//�ޥΩw�q��UJTable����B�z��椧�����B���O���M��

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class TableHeaderEX extends JFrame {

	BookData[] bookData = {
		new BookData("Visual C++.NET �J���i��", "����T", 
			750.0, "P3237", "2003-9-1", true),
		new BookData("Access 2003 ������s", "����T", 
			590.0, "A4023", "2004-3-1", true),
		new BookData("Java 2 �J���i�� �V �A��JDK6.0", "����T",
			590.0, "P4137",	"2005-5-1", true),
		new BookData("Access 2003 �{���]�p", "����T", 
			660.0,  "A4033", "2004-5-1", true),
		new BookData("JSP �ʺA�����J�����", "����T", 
			720.0, "W3135", "2003-12-1", true),
		new BookData("ASP �ʺA�����J�����", "����T", 
			580.0, "W4075", "2004-7-1", true),
		new BookData("Access 2000 �{���]�p", "����T", 
			690.0, "A9193", "2000-9-1", false)};
	//�ŧi�x�s���y��ƪ��G���}�C

	BookObjectTableModel botm = new BookObjectTableModel();
	//�ŧi�B�z��椺���y��ƪ�BookObjectTableModel����

	JTable tbBook = new JTable(botm);
	//�HModel����ŧi�إߪ�檺JTable����

	TableColumnModel  tcmBook; //�ŧiTableColumnModel����

	public TableHeaderEX() {

		tcmBook = tbBook.getColumnModel();
		//���oJTable����TableColumnModel����

		//�H�ΦW���O���覡�w�q�~��JTableHeader���O�����D�C���O,
		//�����ǤJ���B�Ϊ�TableColumnModel����,
		//�_�h�L�k��ܼ��D�C���W��
		JTableHeader header = new JTableHeader(tcmBook){

			//�s�W���
			public void columnAdded(TableColumnModelEvent e){
				System.out.println(
					"columnAdded(TableColumnModelEvent e)");
				super.columnAdded(e);
				//�I�s��¦���O��columnAdded()��k,
				//�_�h���D�C�L�k���`����
			}

			//�������
			public void columnRemoved(TableColumnModelEvent e){
				System.out.println(
					"columnRemoved(TableColumnModelEvent e)");
				super.columnRemoved(e);
				//�I�s��¦���O��columnRemoved()��k,
				//�_�h���D�C�L�k���`����
			}

			//�������
			public void columnMoved(TableColumnModelEvent e){
				System.out.println(
					"columnMoved(TableColumnModelEvent e)");
				super.columnMoved(e);
				//�I�s��¦���O��columnMoved()��k,
				//�_�h���D�C�L�k���`����
			}

			//�����ɧ���
			public void columnMarginChanged(ChangeEvent e){
				System.out.println(
					"columnMarginChanged(ChangeEvent e)");
				super.columnMarginChanged(e);
				//�I�s��¦���O��columnMarginChanged()��k, 
				//�_�h���D�C�L�k���`����
			}

			//���������
			public void columnSelectionChanged(ListSelectionEvent e){
				System.out.println(
					"columnSelectionChanged(ListSelectionEvent e)");
				super.columnSelectionChanged(e);
				//�I�s��¦���O��columnSelectionChanged()��k,
				//�_�h���D�C�L�k���`����
			}
		};

		tbBook.setTableHeader(header);
		//���oJTable����ϥΪ����D�C

		tbBook.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//�]�w��椣�H�����j�p�۰ʽվ�

		for(BookData elm : bookData){ //BookData����s�W��TableModel����
			botm.addBookData(elm);
		}

		tbBook.setCellSelectionEnabled(true); //�]�w���\�x�s����

		//���U�^�����D�CMouseEvent�ƥ󪺺�ť������
		header.addMouseListener(new MouseAdapter(){

			//�^���ƹ����U�ʧ@
			public void mouseClicked(MouseEvent e){

				int viewIndex = tcmBook.getColumnIndexAtX(e.getX());
				//�N�ƹ���Ф� x �y���ഫ��JTable�������e�����ޭ�

				//�Y���O���U�ƹ�����2���h������, �òפ��k������
				if(e.getClickCount() != 2){
					tbBook.setColumnSelectionInterval(
						viewIndex, viewIndex);
					//�]�w����ثe�����

					tbBook.setRowSelectionInterval(
						0, tbBook.getRowCount()-1);

					return;
				}

				TableColumn col = tcmBook.getColumn(viewIndex);
				//�HJTable�������e�����ޭȨ��o�N����檺TableColumn����

				int modelIndex = col.getModelIndex();
				//���oTableColumn����bTableModel���󤺪����ޭ�

				if(modelIndex < 0) //�Y���ޭȤp��0, �h�פ��k������
					return;

				col.setPreferredWidth(botm.getColumnFitWidth(modelIndex));
				//�qTableModel������o���ޭȫ��w��쪺�̾A�e��,
				//�M��]�w����쪺�̾A�e��
			}
		});

		DefaultTableColumnModel dtcBook = 
			(DefaultTableColumnModel) tbBook.getColumnModel();
		//���oJTable����ϥΪ�ColumnModel����

		for(int i = 0; i < dtcBook.getColumnCount() ; i++){
			tcmBook.getColumn(i).setCellRenderer(new BasicRenderer());
			//�]�w���ϥΪ���Ķ������
		}

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
		new TableHeaderEX(); //�ŧi�����ج[����	 
	}
}

class BookData { //�w�q�x�s���y��ƪ�����

	//�w�q���y��ƪ��ݩ�
	private String title, //���y���D
						   publisher, //�X����
						   ID, //�Ѹ�
						   p_Date; //�X�����
	private Double price; //���
	private boolean inPrint; //�O�_����

	//�غc�l
	BookData(String title, String publisher, Double price, 
		String ID, String p_Date, boolean inPrint){

		this.title = title;
		this.publisher = publisher;
		this.price = price;
		this.ID = ID;
		this.p_Date =  p_Date;
		this.inPrint = inPrint;
		//�]�w���y���
	}

	//�w�q���o���y��ƪ���k
	public String getTitle(){ return title; }
	public String getPublisher(){ return publisher; }
	public Double getPrice(){ return price; }
	public String getID(){ return ID; }
	public String getPDate(){ return p_Date; }
	public boolean getInPrint(){ return inPrint; }
}

//�w�q�B�z���y��ƪ�TableModel����
class BookObjectTableModel extends AbstractTableModel {

	private Vector bookData =  new Vector();
	//�w�q�x�s���y��ƪ�Vector�e��

	String[] colName = 
		{"�ѦW", "�X����", "���", "�Ѹ�", "�X�����", "�O�_�o��"};
	//�ŧi�x�s���W�٪��r��

	private int[] fitWidth = {50, 50, 50, 50, 50, 50};
	//�x�s��쪺�̾A�e��

	private int[] HAlign = {JLabel.LEFT, JLabel.LEFT, JLabel.RIGHT,
										JLabel.LEFT, JLabel.RIGHT, JLabel.RIGHT};
	//����ƪ�����覡

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
		
		//�P�_�C���ޭȬO�_���T
		if(row < 0 || row >= getRowCount())
			return "";

		//�P�_����ޭȬO�_���T
		if(col < 0 || col >= getColumnCount())
			return "";

		BookData bdRow = (BookData) bookData.elementAt(row);
		//�H�C�ư������ޭȨ��o�e�������y��ƪ���

		switch(col){ //�H����ާP�_�����o�����y���
			case 0:
				return bdRow.getTitle(); //���o���y�W��
			case 1:
				return bdRow.getPublisher(); //���o�X����
			case 2:
				return bdRow.getPrice(); //���o���
			case 3:
				return bdRow.getID(); //���o�Ѹ�
			case 4:
				return bdRow.getPDate(); //���o�X�����
			case 5:
				return bdRow.getInPrint();
		}

		return "";
	}

	//���o���W��, �i���w�q
	public String getColumnName(int column){
		return colName[column];
	}

	//���o��쪺�̾A�e��
	public int getColumnFitWidth(int column){
		return fitWidth[column];
	}

	//�]�w��쪺�̾A�e��
	public void setColumnFitWidth(int column, int width){
		fitWidth[column] = width;
	}

	//���o����r������覡
	public int getTextAlignment(int column){
		return HAlign[column];
	}
}

//�H�~��DefaultTableCellRenderer���O���覡, 
//�ۭq�B�z�x�s����ܪ���Ķ������
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

		//�̷ӿ�����A�]�w�x�s�檺�I���P�e���C��
		if(isSelected){
			setBackground(Color.lightGray);
			setForeground(Color.darkGray);
		}
		else{
			setBackground(Color.white);
			setForeground(Color.black);
		}

		if(hasFocus) //�]�w�֦��J�I�ɪ��I���C��
			setBackground(Color.pink);

		int modelIndex = table.convertColumnIndexToModel(column);
		//�N���e�������ഫ�����ҫ�����

		BookObjectTableModel  botm = 
			(BookObjectTableModel) table.getModel();
		//���o�x�sJTable������ܤ����y��ƪ�TableModel����

		setHorizontalAlignment(botm.getTextAlignment(modelIndex));
		//�qTableModel������o���w��쪺��������覡

		int colWidth = botm.getColumnFitWidth(modelIndex);
		//�qTableModel������o�ثe���]�w���̾A�e��

		int lbWidth = new Double(getPreferredSize().getWidth()).intValue() + 5;
		//���o�ثe��ܪ����e��

		//�Y������e�פj��]�w���̾A�e��, 
		//�h�N���e�׳]�w���s���̾A�e��
		if(lbWidth> colWidth){
			botm.setColumnFitWidth(modelIndex, lbWidth);
		}

		return this;
	}
}