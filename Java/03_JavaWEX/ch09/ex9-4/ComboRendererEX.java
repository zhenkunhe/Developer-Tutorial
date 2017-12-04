import javax.swing.*;
import javax.swing.event.*; //�ޥΥ]�tItemListener�������M��
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class ComboRendererEX extends JFrame{

	JComboBox cmbBook = new JComboBox();
	//�H�]�t�ﶵ���e���r��إ߲զX���

	//�w�q��ܮ��y��ƪ�����
	JLabel lbImage = new JLabel(), //��ܮ��y�ʭ�����
				lbPublisher = new JLabel(), //��ܥX����
				lbPDate = new JLabel(), //��ܤ��
				lbPrice = new JLabel(); //��ܮ��y����

	Object [] listData = new Object[9]; //�x�s�զX����ﶵ���󪺰}�C

	//�H�ΦW���O���覡�~��DefaultComboBoxModel���O
	DefaultComboBoxModel cmbModel = new DefaultComboBoxModel(){

		public void setSelectedItem(Object anItem){ //�]�w������ﶵ

			//�P�_�O�_��String, �O�h�����פ��k
			if(anItem instanceof String)
				return; //�פ��k�������

			super.setSelectedItem(anItem);
			//�I�s��¦���O��setSelectedItem()��k
		}
	};

	ComboRendererEX(){

		listData[0] = new String("�{���]�p");
		listData[1] = new Book("Java 2 �J���i�� �V �A��JDK6.0",
			"����T", 590.0, new ImageIcon("image\\P4137.jpg"),
			"2005-5-1");
		listData[2] = new Book("Visual C++.NET �J���i��", 
			"����T", 750.0, new ImageIcon("image\\P3237.jpg"), 
			"2003-9-1");
		listData[3] = new String("��Ʈw");
		listData[4] = new Book("Access 2003 ������s", 
			"����T", 590.0, new ImageIcon("image\\A4023.jpg"), 
			"2004-3-1");
		listData[5] = new Book("Access 2003 �{���]�p", 
			"����T", 660.0, new ImageIcon("image\\A4033.jpg"),
			"2004-5-1");
		listData[6] = new String("�ʺA����");
		listData[7] = new Book("JSP �ʺA�����J�����", 
			"����T", 720.0, new ImageIcon("image\\W3135.jpg"), 
			"2003-12-1");
		listData[8] = new Book("ASP �ʺA�����J�����", 
			"����T", 580.0, new ImageIcon("image\\W4075.jpg"), 
			"2004-7-1");
		//�ŧi���y��ƪ���

		cmbBook.setRenderer(new BookCellRenderer());
		//�]�w�զX����ﶵ�ϥΪ���Ķ��

		cmbBook.setModel(cmbModel); //�]�w�ϥΪ�Model����

		//�N���y��ƥ[�J�զX��������ﶵ
		for(Object elm : listData)
			cmbModel.addElement(elm); //�N�ﶵ�[�JModel����

		cmbBook.setSelectedIndex(1); //�]�w�ﶵ������

		lbPublisher.setHorizontalAlignment(JLabel.RIGHT);
		lbPDate.setHorizontalAlignment(JLabel.RIGHT);
		lbPrice.setHorizontalAlignment(JLabel.RIGHT);
		lbImage.setHorizontalAlignment(JLabel.CENTER);
		//�]�w���Ҥ���r����������覡

		lbPublisher.setText(((Book)listData[1]).getPublisher());
		lbPDate.setText(((Book)listData[1]).getPublishDate());
		lbPrice.setText(
					String.valueOf(((Book)listData[1]).getPrice()));
		lbImage.setIcon(((Book)listData[1]).getCoverImage());
		//�]�w������ܮ��y���

		//�w�q�ëŧi�^��ItemEvent�ƥ󪺺�ť������
		cmbBook.addItemListener(new ItemListener(){

			//�^�����ܿﶵ���A���ʧ@
			public void itemStateChanged(ItemEvent e){

				Book item = (Book)e.getItem(); //���o�Q����ﶵ������

				lbPublisher.setText(item.getPublisher()); //�]�w���y���
				lbPDate.setText(item.getPublishDate());
				lbPrice.setText(String.valueOf(item.getPrice()));				
				lbImage.setIcon(item.getCoverImage()); //�]�w��ܮ��y�ʭ�
			}
		});

		//�إߥ]�t�U����Box�e��, �ñN����[�J
		Box bxBook = new Box(BoxLayout.X_AXIS);
		bxBook.add(new JLabel("���y : ", JLabel.RIGHT));
		bxBook.add(cmbBook); //�[�J�զX���

		JPanel jpDetail = new JPanel(new GridLayout(3, 2, 10, 5));
		jpDetail.add(new JLabel("�X���� : ", JLabel.RIGHT));
		jpDetail.add(lbPublisher); //�[�J��ܥX���Ӫ�����
		jpDetail.add(new JLabel("�X����� : ", JLabel.RIGHT));
		jpDetail.add(lbPDate); //�[�J��ܥX�����������
		jpDetail.add(new JLabel("���� : ", JLabel.RIGHT));
		jpDetail.add(lbPrice); //�[�J��ܮ��y���檺����

		JPanel jpMain = new JPanel(new BorderLayout(10, 10));
		jpMain.add(bxBook, BorderLayout.NORTH); //�NBox�e���[�J�D����
		jpMain.add(lbImage); //�[�J��ܮ��y�ʭ��Ϥ�������
		jpMain.add(jpDetail,  BorderLayout.EAST);
		//�[�J��ܸԲӸ�ƪ�����

		jpMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		//�]�w��ܸ�ƪ��D�n�����ϥμe�׬�5���ťծؽu

		Container cp = getContentPane(); //���o���e����

		cp.add(jpMain); //�NBox�e���[�J���e����

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		pack();
		setVisible(true);
	}

	public static void main(String args[]) {
		new ComboRendererEX(); //�ŧi�����ج[����
	}
}

class Book { //�w�q�x�s���y��ƪ�Book����

	private String title, publisher;
	private double price;
	private ImageIcon coverImage;
	private String p_date;

	//Book���󪺫غc�l
	public Book(String title, String publisher, double price, 
									ImageIcon coverImage, String p_date){
		this.title = title;
		this.publisher = publisher;
		this.price = price;
		this.coverImage = coverImage;
		this.p_date = p_date;
	}

	public String toString(){ //�NBook�����ഫ��String����
		return title;
	}

	public String getTitle(){ //���o���y�����D
		return title;
	}

	public String getPublisher(){ //���o���y���X����
		return publisher;
	}

	public double getPrice(){ //���o���y������
		return price;
	}

	public ImageIcon getCoverImage(){ //���o���y�ʭ����Ϥ�
		return coverImage;
	}

	public String getPublishDate(){ //���o�X�����
		return p_date;
	}
}

//�H�~��DefaultListCellRenderer���覡
//�w�qø�s�զX����M��ﶵ��BookCellRenderer���O
class BookCellRenderer extends DefaultListCellRenderer {

	public Component getListCellRendererComponent(
										JList list,
										Object value,
										int index,
										boolean isSelected,
										boolean cellHasFocus){

		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		//���I�s��¦���O��getListCellRendererComponent()��k

		if(value instanceof String){ //�P�_�ﶵ����O�_���r��
			setText((String) value); //�]�w�ﶵ��ܪ����e
			setBackground(Color.gray); //�]�w�ﶵ���I���C��
			setIcon(new ImageIcon("image\\icon.jpg"));
			//�]�w�ﶵ�ϥΪ��ϥ�
		}
		else{
			setText(((Book)value).getTitle()); //�]�w�H���y�W�٬��ﶵ��r
			setBorder(new EmptyBorder(0, 10, 0, 0));
			//�B�Ϊťծؽu�]�w�ﶵ����X�{�ť�
		}

		return this;
	}
}