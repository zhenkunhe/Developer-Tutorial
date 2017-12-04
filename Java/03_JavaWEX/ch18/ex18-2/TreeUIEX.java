import javax.swing.*;  //�ޥήM��
import javax.swing.event.*;
//�ޥΥ]�tJTree���O�i�޵o�ƥ����O���M��

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.tree.*;
//�]�t��UJTree���O�إ߾𪬵��c�����O���M��

import javax.swing.border.*; //�ޥΥ]�t�ؽu���O���M��

public class TreeUIEX extends JFrame {

	JLabel lbNode = new JLabel(), lbType = new JLabel();
	//�ŧi��ܸ`�I�P�ƥ󫬺A��ƪ�����

	JTree trBook; //�ŧi�𪬵��c����

	TreeUIEX() {

		DefaultMutableTreeNode 
			dmtnOOP = 
				new DefaultMutableTreeNode("����ɦV�{���y��", true),
			dmtnC = new DefaultMutableTreeNode("C/C++", true),
			dmtnJava = new DefaultMutableTreeNode("Java", true),
			dmtnHtml = new DefaultMutableTreeNode("�ʺA����", true),
			dmtnRoot = new DefaultMutableTreeNode("�줸���", true);
		//�ŧi�`�I����

		//�H�U�ԭz�N��l�`�I�[�J�`�I
		dmtnC.add(	new DefaultMutableTreeNode(
			"C/C++ �J���i��", false));
		dmtnC.add(	new DefaultMutableTreeNode(
			"Visual C++.Net �J���i��", false));
		dmtnC.add(	new DefaultMutableTreeNode(
			"Linux C/C++ �J���i��", false));

		dmtnJava.add(new DefaultMutableTreeNode(
			"Java 2 �J���i��", false));
		dmtnJava.add(new DefaultMutableTreeNode(
			"Java 2 ����ɦV�{���y��", false));

		dmtnHtml.add(new DefaultMutableTreeNode(
			"ASP�ʺA�����J�����", false));
		dmtnHtml.add(new DefaultMutableTreeNode(
			"JSP�ʺA�����J�����", false));
		dmtnHtml.add(new DefaultMutableTreeNode(
			"DHTML�ʺA�����J�����", false));

		dmtnOOP.add(dmtnC);
		dmtnOOP.add(dmtnJava);

		dmtnRoot.add(dmtnOOP); //�N�`�I�[�J�ڸ`�I
		dmtnRoot.add(dmtnHtml);

		trBook = new JTree(dmtnRoot); //�ŧiJTree����

		JRadioButton rbDef = new  JRadioButton("Java�w�]", true),
			rbUser = new JRadioButton("�ۭq���");
		//�ŧi��ܶs

		ButtonGroup bg = new ButtonGroup(); //�ŧi���s�s��
		bg.add(rbDef); //�N��ܶs�[�J���s�s��
		bg.add(rbUser);

		//�H�ΦW���O�w�q�B�ŧi�^��ActionEvent�ƥ󪺺�ť������
		ActionListener al = new ActionListener(){

			Object leafIcon , openIcon, closedIcon,
				        expandedIcon, collapsedIcon;

			//�B�ι����l�ư���غc�l���u�@
			{
				UIDefaults uid  = UIManager.getDefaults();
				//���o�w�]�~���Pı���w�]��

				leafIcon =  uid.get("Tree.leafIcon");
				openIcon = uid.get("Tree.openIcon");
				closedIcon = uid.get("Tree.closedIcon");
				expandedIcon = uid.get("Tree.expandedIcon");
				collapsedIcon = uid.get("Tree.collapsedIcon");
				//���o�𪬵��c�w�]�ϥΪ��ϥܳ]�w��
			}

			public void actionPerformed(ActionEvent e){

				if(e.getActionCommand().equals("Java�w�]")){
					UIManager.put("Tree.leafIcon", leafIcon);
					UIManager.put("Tree.openIcon", openIcon);
					UIManager.put("Tree.closedIcon", closedIcon);
					UIManager.put("Tree.expandedIcon", expandedIcon);
					UIManager.put("Tree.collapsedIcon", collapsedIcon);
					//�٭�t�ξ𪬵��c�w�]�ϥΪ��ϥ�
				}
				else {
					UIManager.put("Tree.leafIcon", 
						new ImageIcon("icon/File.gif"));
					UIManager.put("Tree.openIcon", 
						new ImageIcon("icon/OpenFolder.jpg"));
					UIManager.put("Tree.closedIcon", 
						new ImageIcon("icon/ClosedFolder.jpg"));
					UIManager.put("Tree.expandedIcon", 
						new ImageIcon("icon/unlocked.jpg"));
					UIManager.put("Tree.collapsedIcon", 
						new ImageIcon("icon/locked.jpg"));
					//���t�γ]�w�𪬵��c�ϥΪ��ϥ�
				}

				trBook.updateUI(); //��s�𪬵��c����
			}
		};

		rbDef.addActionListener(al);	
		rbUser.addActionListener(al);
		//���U�^����ܶsActionEvent�ƥ󪺺�ť������

		//�B�ΰΦW���O�w�q�B�ŧi��ťTreeExpansionEvent�ƥ�
		//����ť������
		trBook.addTreeExpansionListener(
			new TreeExpansionListener(){

				//�^���`�I�i�}�ʧ@
				public void treeExpanded(
								TreeExpansionEvent event){
					lbNode.setText(event.getPath().toString());
					//�]�w��ܤ޵o�ƥ󤧸`�I�����|

					lbType.setText("�i�}"); //��ܤ޵o�`�I�i�}�ƥ�
				}

				//�^���`�I���X�ʧ@
				public void treeCollapsed(
							TreeExpansionEvent event){
					lbNode.setText(event.getPath().toString());
					//�]�w��ܤ޵o�ƥ󤧸`�I�����|

					lbType.setText("���X"); //��ܤ޵o�`�I���X�ƥ�
				}
			});

		//�B�ΰΦW���O�w�q�B�ŧi��ťTreeWillExpansionEvent�ƥ�
		//����ť������
		trBook.addTreeWillExpandListener(
			new TreeWillExpandListener(){

				//�^���`�I�Y�N�i�}���ʧ@
				public void 
					treeWillExpand(TreeExpansionEvent event){

					System.out.println(
						event.getPath().toString() + "�`�I�Y�N�i�}");
					//�N�Y�N���X�Y�`�I���T����X�ܩR�O���ܦr������
				}

				//�^���`�I�Y�N���X���ʧ@
				public void 
					treeWillCollapse(TreeExpansionEvent event){
					System.out.println(
						event.getPath().toString() + "�`�I�Y�N���X");
					//�N�Y�N���X�Y�`�I���T����X�ܩR�O���ܦr������
				}
			});

		//�B�ΰΦW���O�w�q�B�ŧi��ťTreeSelectionEvent�ƥ�
		//����ť������
		trBook.addTreeSelectionListener(new TreeSelectionListener(){

			public void valueChanged(TreeSelectionEvent e){
				lbNode.setText(e.getPath().toString());
				//�]�w��ܤ޵o�ƥ󤧸`�I�����|

				lbType.setText("���"); //��ܤ޵o�`�I����ƥ�
			}
		});

		//���U�^��MouseEvent�ƥ��ť������
		trBook.addMouseListener(new MouseAdapter(){

			//�^����}�ƹ����s���ʧ@
			public void mouseReleased(MouseEvent e){

				JTree tbSource = (JTree) e.getSource();
				//���o�޵o�ƥ󪺤���

				TreePath tpNode = 
					tbSource.getPathForLocation(e.getX(), e.getY());
				//�H�ƹ���Ц�m���oJTree�������������

				if(tpNode == null) //�Y�����o���ثh�פ��k������
					return;

				lbNode.setText(tpNode.toString());
				//��ܤ޵o�ƥ󪺸`�I

				lbType.setText(lbType.getText() + "; �ƹ��I��");
				//��ܤ޵o���ƥ�
			}
		});

		JPanel jpType = new JPanel();
		jpType.add(rbUser); //�[�J��ܶs
		jpType.add(rbDef);	

		Box bxNode = new Box(BoxLayout.X_AXIS);
		bxNode.add(new JLabel("�޵o�ƥ󪺸`�I : ", JLabel.RIGHT));
		bxNode.add(Box.createHorizontalStrut(5));
		bxNode.add(lbNode); //��ܤ޵o�ƥ�`�I������

		Box bxType = new Box(BoxLayout.X_AXIS);
		bxType.add(new JLabel("�ƥ󫬺A : ", JLabel.RIGHT));
		bxType.add(Box.createHorizontalStrut(5));
		bxType.add(lbType); //��ܤ޵o�ƥ󫬺A������

		JPanel jpMsg = new JPanel(new GridLayout(2, 2, 5, 5));
		jpMsg.add(bxNode);
		jpMsg.add(bxType);
		jpMsg.setBorder(new EmptyBorder(3, 3, 3,  3));

		Container cp = getContentPane(); //���o���e����
		cp.add(jpType, BorderLayout.NORTH);
		cp.add(new JScrollPane(trBook)); //�N����[�J�����ϰ�
		cp.add(jpMsg, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(3, 3, 3, 3));
		//�]�w�ڭ����ϥμe�׬�3���z���ؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 380);
		setVisible(true);
	}

	public static void main(String args[]) {
		new TreeUIEX(); //�ŧi�����ج[����
	}
}