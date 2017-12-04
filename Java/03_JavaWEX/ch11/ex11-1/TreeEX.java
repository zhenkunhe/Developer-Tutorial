import javax.swing.*;  //�ޥήM��
import javax.swing.event.*;
//�ޥΥ]�tJTree���O�i�޵o�ƥ����O���M��

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.tree.*; //�]�t��UJTree���O�إ߾𪬵��c�����O���M��
import javax.swing.border.*; //�ޥΥ]�t�ؽu���O���M��

public class TreeEX extends JFrame {

	JLabel lbNode = new JLabel(), lbType = new JLabel(),
				lbTotalRow = new JLabel(), lbRow = new JLabel(),
				lbCurRow = new JLabel(), lbMousePos = new JLabel(),
				lbMouse = new JLabel();
	//�ŧi��ܸ`�I�P�ƥ󫬺A��ƪ�����
	
	JButton btnExpand = new JButton("���X�ڸ`�I");
	//�ŧi����ڸ`�I�i�}�B���X�����O���s

	JTree trBook; //�ŧi��ܾ𪬵��c��JTree����

	TreeEX() {
		
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

		DefaultTreeModel dtm = new DefaultTreeModel(dmtnRoot, true);
		//�ŧi�B�zJTree������ܸ�ƪ�Model����

		trBook = new JTree(dtm); //�HModel����ŧiJTree����

		trBook.setEditable(true); //�]�w�`�I�i�s��
		trBook.setShowsRootHandles(true); //�]�w��ܮڸ`�I���i�}���s

		JRadioButton rbNone = new  JRadioButton("�L"),
			rbHor = new JRadioButton("��Ƨ������������j�u"),
			rbAng = new JRadioButton("�U�`�I���������s�u", true);
		//�ŧi��ܶs

		ButtonGroup bg = new ButtonGroup(); //�ŧi���s�s��
		bg.add(rbNone); //�N��ܶs�[�J���s�s��
		bg.add(rbHor);
		bg.add(rbAng);

		//���U�^����ܶsActionEvent�ƥ󪺺�ť������
		rbNone.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				trBook.putClientProperty("JTree.lineStyle", "None");
				//�]�wJTree���󤺸`�I���S���s���u

				trBook.updateUI(); //��sJTree������ܵe��
			}
		});	

		rbHor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				trBook.putClientProperty("JTree.lineStyle", "Horizontal");
				//�]�wJTree���󤺸�Ƨ��`�I�����������j�u

				trBook.updateUI(); //��sJTree������ܵe��
			}
		});	

		rbAng.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				trBook.putClientProperty("JTree.lineStyle", "Angled");
				//�]�wJTree���󤺸�Ƨ��`�I���������s���u

				trBook.updateUI(); //��sJTree������ܵe��
			}
		});	

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
					
					updateRow(); //��s�C�Ƹ�T
				}

				//�^���`�I���X�ʧ@
				public void treeCollapsed(
							TreeExpansionEvent event){
					lbNode.setText(event.getPath().toString());
					//�]�w��ܤ޵o�ƥ󤧸`�I�����|

					lbType.setText("���X"); //��ܤ޵o�`�I���X�ƥ�
					
					updateRow(); //��s�C�Ƹ�T
				}
			});

		//�B�ΰΦW���O�w�q�B�ŧi��ťTreeWillExpansionEvent�ƥ�
		//����ť������
		trBook.addTreeWillExpandListener(
			new TreeWillExpandListener(){

				//�^���`�I�Y�N�i�}���ʧ@
				public void treeWillExpand(TreeExpansionEvent event){
					System.out.println(
						event.getPath().toString() + " �`�I�Y�N�i�}");
					//�N�Y�N���X�Y�`�I���T����X�ܩR�O���ܦr������
				}

				//�^���`�I�Y�N���X���ʧ@
				public void treeWillCollapse(TreeExpansionEvent event){
					System.out.println(
						event.getPath().toString() + " �`�I�Y�N���X");
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
				updateRow(); //��s�C�Ƹ�T
			}
		});

		//���U�^��MouseEvent�ƥ��ť������
		trBook.addMouseListener(new MouseAdapter(){

			//�^����}�ƹ����s���ʧ@
			public void mouseReleased(MouseEvent e){

				lbMousePos.setText(
					" (" + e.getX() + ", " + e.getY() + ") ");
				//�]�w������ܷƹ��I���m���y��

				JTree trSource = (JTree) e.getSource();
				//���o�޵o�ƥ󪺤���

				TreePath tpNode = 
					trSource.getPathForLocation(e.getX(), e.getY());
				//�H�ƹ���Ц�m���oJTree����������`�I

				if(tpNode == null){ //�Y�����o�`�I�h�פ��k������
					TreePath tpClosestNode = 
						trSource.getClosestPathForLocation(e.getX(), e.getY());

					lbMouse.setText("������`�I, �̱���`�I�� " + 
						tpClosestNode.toString());
					//�]�w������ܷƹ����I�ﵲ�G

					return;
				}

				lbMouse.setText("��� "
							+ tpNode.toString() + " �`�I");
				//�]�w������ܷƹ����I�ﵲ�G
			}
		});

		//���U�^��ActionEvent�ƥ󪺺�ť������
		btnExpand.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				if(trBook.isCollapsed(0)){ //�P�_��0�C�`�I�O�_���X
					trBook.expandRow(0); //�i�}��0�C���`�I
					btnExpand.setText("���X�ڸ`�I");
				}
				else{
					trBook.collapseRow(0); //���X��0�C���`�I
					btnExpand.setText("�i�}�ڸ`�I");
				}

			}
		});

		Box bxStyle = new Box(BoxLayout.X_AXIS);
		bxStyle.add(rbNone); //�[�J��ܶs
		bxStyle.add(rbHor);
		bxStyle.add(rbAng);
		bxStyle.add(Box.createHorizontalGlue());
		bxStyle.add(btnExpand);
		bxStyle.add(Box.createHorizontalStrut(10));

		Box bxNode = new Box(BoxLayout.X_AXIS);
		bxNode.add(new JLabel("�޵o�ƥ󪺸`�I : ", JLabel.RIGHT));
		bxNode.add(Box.createHorizontalStrut(5));
		bxNode.add(lbNode); //��ܤ޵o�ƥ�`�I������
		bxNode.add(Box.createHorizontalStrut(15));
		bxNode.add(new JLabel("�ƥ󫬺A : ", JLabel.RIGHT));
		bxNode.add(Box.createHorizontalStrut(5));
		bxNode.add(lbType); //��ܤ޵o�ƥ󫬺A������

		Box bxRow = new Box(BoxLayout.X_AXIS);
		bxRow.add(new JLabel("�`�C�� : ", JLabel.RIGHT));
		bxRow.add(Box.createHorizontalStrut(5));
		bxRow.add(lbTotalRow);
		bxRow.add(Box.createHorizontalStrut(30));
		bxRow.add(new JLabel("�ثe��ܦC�� : ", JLabel.RIGHT));
		bxRow.add(Box.createHorizontalStrut(5));
		bxRow.add(lbCurRow);
		bxRow.add(Box.createHorizontalStrut(30));
		bxRow.add(new JLabel("����C : ", JLabel.RIGHT));
		bxRow.add(Box.createHorizontalStrut(5));
		bxRow.add(lbRow); //��ܤ޵o�ƥ󫬺A������

		Box bxMouse = new Box(BoxLayout.X_AXIS);
		bxMouse.add(new JLabel("�ƹ��y�� : ", JLabel.RIGHT));
		bxMouse.add(Box.createHorizontalStrut(5));
		bxMouse.add(lbMousePos); //��ܤ޵o�ƥ󫬺A������
		bxMouse.add(Box.createHorizontalStrut(15));
		bxMouse.add(new JLabel("�ƹ�������G : ", JLabel.RIGHT));
		bxMouse.add(Box.createHorizontalStrut(5));
		bxMouse.add(lbMouse); //��ܤ޵o�ƥ󫬺A������

		JPanel jpMsg = new JPanel(new GridLayout(3, 2, 5, 5));
		jpMsg.add(bxNode);
		jpMsg.add(bxRow);
		jpMsg.add(bxMouse);
		jpMsg.setBorder(new EmptyBorder(3, 3, 3,  3));

		Container cp = getContentPane(); //���o���e����
		cp.add(bxStyle, BorderLayout.NORTH);
		cp.add(trBook); //�N����[�J�����ϰ�
		cp.add(jpMsg, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(3, 3, 3, 3));
		//�]�w�ڭ����ϥμe�׬�3���z���ؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(690, 400);
		setVisible(true);
	}

	private void updateRow(){ //��s�C����ܸ�ƪ���k

		//�H�U�ԭz�N�]�w�������JTree�����`��ܦC�ơB
		//�ثe��ܦC�ƻP����`�I�Ҧb�C��
		lbTotalRow.setText(String.valueOf(trBook.getVisibleRowCount()));
		lbCurRow.setText(String.valueOf(trBook.getRowCount()));
		lbRow.setText(Arrays.toString(trBook.getSelectionRows()));
	}

	public static void main(String args[]) {
		new TreeEX(); //�ŧi�����ج[����	 
	}
}