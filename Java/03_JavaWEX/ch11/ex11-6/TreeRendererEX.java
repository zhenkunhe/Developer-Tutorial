import javax.swing.*;  //�ޥήM��
import javax.swing.event.*;
//�ޥΥ]�tJTree���O�i�޵o�ƥ����O���M��
import javax.swing.tree.*; //�]�t��UJTree���O�إ߾𪬵��c�����O���M��
import javax.swing.border.*; //�ޥΥ]�t�ؽu���O���M��
import java.awt.*;
import java.awt.event.*;

public class TreeRendererEX extends JFrame {

	JTree trBook; //�ŧi�𪬵��c����

	TreeRendererEX() {

		DefaultMutableTreeNode 
			dmtnOOP = 
				new DefaultMutableTreeNode("����ɦV�{���y��", true),
			dmtnC = new DefaultMutableTreeNode("C/C++", true),
			dmtnJava = new DefaultMutableTreeNode("Java", true),
			dmtnHtml = new DefaultMutableTreeNode("�ʺA����", true),
			dmtnRoot = new DefaultMutableTreeNode("�줸���", true);
		//�ŧi�`�I����

		//�H�U�ԭz�N��l�`�I�[�J�`�I
		dmtnC.add(
			new DefaultMutableTreeNode("C/C++ �J���i��", false));
		dmtnC.add(	
			new DefaultMutableTreeNode("Visual C++.Net �J���i��", 
			false));
		dmtnC.add(
			new DefaultMutableTreeNode("Linux C/C++ �J���i��", 
			false));

		dmtnJava.add(
			new DefaultMutableTreeNode("Java 2 �J���i��", false));
		dmtnJava.add(
			new DefaultMutableTreeNode("Java 2 ����ɦV�{���y��", 
			false));

		dmtnHtml.add(
			new DefaultMutableTreeNode("ASP�ʺA�����J�����", 
			false));
		dmtnHtml.add(
			new DefaultMutableTreeNode("JSP�ʺA�����J�����", 
			false));
		dmtnHtml.add(
			new DefaultMutableTreeNode("DHTML�ʺA�����J�����",
			false));

		dmtnOOP.add(dmtnC);
		dmtnOOP.add(dmtnJava);

		dmtnRoot.add(dmtnOOP); //�N�`�I�[�J�ڸ`�I
		dmtnRoot.add(dmtnHtml);

		trBook = new JTree(dmtnRoot); //�ŧiJTree����

		trBook.setCellRenderer(new BookTreeCellRenderer());
		//�]�w�ϥΦۭq��Ķ������

		JRadioButton rbDef = new  JRadioButton("Java�w�]"),
			rbUser = new JRadioButton("�ۭq���", true);
		//�ŧi��ܶs

		ButtonGroup bg = new ButtonGroup(); //�ŧi���s�s��
		bg.add(rbDef); //�N��ܶs�[�J���s�s��
		bg.add(rbUser);

		//���U�^����ܶsActionEvent�ƥ󪺺�ť������
		rbDef.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				trBook.setCellRenderer(null);
				//�]�w�ϥιw�]����Ķ������
			}
		});	

		rbUser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				trBook.setCellRenderer(new BookTreeCellRenderer());
				//�]�w�ϥΦۭq��Ķ������
			}
		});	

		JPanel jpType = new JPanel();
		jpType.add(rbUser); //�[�J��ܶs
		jpType.add(rbDef);	

		Container cp = getContentPane(); //���o���e����
		cp.add(jpType, BorderLayout.NORTH);
		cp.add(trBook); //�N����[�J�����ϰ�

		getRootPane().setBorder(new EmptyBorder(3, 3, 3, 3));
		//�]�w�ڭ����ϥμe�׬�3���z���ؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 330);
		setVisible(true);
	}

	public static void main(String args[]) {
		new TreeRendererEX(); //�ŧi�����ج[����
	}
}

//�w�q�𪬵��c������ܵe���ϥΪ���Ķ��
class BookTreeCellRenderer 
	extends DefaultTreeCellRenderer {

	private final static ImageIcon ICON_FILE =
		new ImageIcon("icon/File.gif");
	private final static ImageIcon ICON_CLOSED_FOLDER = 
		new ImageIcon("icon/ClosedFolder.jpg");
	private final static ImageIcon ICON_OPEN_FOLDER = 
		new ImageIcon("icon/OpenFolder.jpg");
	//�w�q�N��ϥܪ��R�A�`��

	public Component getTreeCellRendererComponent(
		JTree tree, //�ϥ���Ķ�����󪺾𪬵��c����
		Object value, //��Ķ�����B�z���𪬵��c���`�I
		boolean isSelected, //�Q�B�z�`�I�O�_�Q���
		boolean expanded, //�Q�B�z�`�I�O�_�i�}
		boolean leaf, //�Q����`�I�O�_�����`�I
		int row, //�Q����`�I���C��
		boolean hasFocus) { //�Q����`�I�O�_�֦��J�I

		super.getTreeCellRendererComponent(
			tree, value, isSelected, expanded, leaf, row, hasFocus);
		//�I�s��¦���O��getTreeCellRendererComponent()��k

		DefaultMutableTreeNode item = (DefaultMutableTreeNode)value;
		//���o�Q����`�I������

		//�P�_�Q����`�I�O�_�e�\�[�J�l�`�I
		if(!item.getAllowsChildren()){ //�����\�[�J�l�`�I
			setIcon(ICON_FILE); //�]�w�ϥܬ�ICON_FILE
		}
		else{
			setIcon(expanded? ICON_OPEN_FOLDER: ICON_CLOSED_FOLDER);
			//�]�w�i�e�Ǥl�`�I���`�I���ϥ�
			//�Y�`�I�����A���i�}�h�ϥ�ICON_OPEN_FOLDER�ϥ�
			//�Y���A�����X�h�ϥ�ICON_CLOSED_FOLDER�ϥ�
		}

		setTextNonSelectionColor(Color.black);
		//�]�w�D����`�I����r�C��

		setTextSelectionColor(Color.darkGray);
		//�]�w����`�I����r�C��

		setBackgroundSelectionColor(Color.lightGray);
		//�]�w����`�I���I���C��

		setBorderSelectionColor(Color.darkGray);
		//�]�w����`�I���ؽu�C��

		return this;
	}
}