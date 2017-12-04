import javax.swing.*;  //�ޥήM��
import javax.swing.event.*;
//�ޥΥ]�tJTree���O�i�޵o�ƥ����O���M��

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.tree.*; //�]�t��UJTree���O�إ߾𪬵��c�����O���M��
import javax.swing.border.*; //�ޥΥ]�t�ؽu���O���M��

public class TreeSelectionEX extends JFrame {

	JLabel lbNode = new JLabel(), lbType = new JLabel();
	//�ŧi��ܸ`�I�P�ƥ󫬺A��ƪ�����

	JTree trBook; //�w�qJTree����
	DefaultTreeSelectionModel dtsm;
	//�B�z�`�I�����TreeSelectionModel����

	TreeSelectionEX() {

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

		dtsm = (DefaultTreeSelectionModel)trBook.getSelectionModel();

		JRadioButton rbSTS = new  JRadioButton("SINGLE_TREE_SELECTION"),
			rbCTS = new JRadioButton("CONTIGUOUS_TREE_SELECTION"),
			rbDTS = new JRadioButton(
						"DISCONTIGUOUS_TREE_SELECTION", true);
		//�ŧi��ܶs

		ButtonGroup bg = new ButtonGroup(); //�ŧi���s�s��
		bg.add(rbDTS); //�N��ܶs�[�J���s�s��
		bg.add(rbCTS);
		bg.add(rbSTS);

		//���U�^����ܶsActionEvent�ƥ󪺺�ť������
		rbSTS.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dtsm.clearSelection(); //�M�����
				dtsm.setSelectionMode(
					DefaultTreeSelectionModel.SINGLE_TREE_SELECTION);
				//�]�w�����Ҧ�
			}
		});	

		rbCTS.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dtsm.clearSelection(); //�M�����
				dtsm.setSelectionMode(
					DefaultTreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
				//�]�w���s��ƿ�Ҧ�
			}
		});	

		rbDTS.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dtsm.clearSelection(); //�M�����
				dtsm.setSelectionMode(
					DefaultTreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
				//�]�w�����s��ƿ�Ҧ�
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
				}

				//�^���`�I���X�ʧ@
				public void treeCollapsed(
							TreeExpansionEvent event){
					lbNode.setText(event.getPath().toString());
					//�]�w��ܤ޵o�ƥ󤧸`�I�����|

					lbType.setText("���X"); //��ܤ޵o�`�I���X�ƥ�
				}
			});

		//�B�ΰΦW���O�w�q�B�ŧi��ťTreeSelectionEvent�ƥ�
		//����ť������
		trBook.addTreeSelectionListener(new TreeSelectionListener(){

			public void valueChanged(TreeSelectionEvent e){

				TreePath[] tp = trBook.getSelectionPaths();

				if(tp == null)
					return;

				lbNode.setText(Arrays.toString(tp));
				//�]�w��ܤ޵o�ƥ󤧸`�I�����|

				for(TreePath elm : tp)
					System.out.println(elm.toString());

				System.out.println("-------------------------------------------\n");

				lbType.setText("���"); //��ܤ޵o�`�I����ƥ�
			}
		});

		JPanel jpType = new JPanel();
		jpType.add(rbDTS); //�[�J��ܶs
		jpType.add(rbCTS);
		jpType.add(rbSTS);		

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
		cp.add(trBook); //�N����[�J�����ϰ�
		cp.add(jpMsg, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(3, 3, 3, 3));
		//�]�w�ڭ����ϥμe�׬�3���z���ؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 380);
		setVisible(true);
	}

	public static void main(String args[]) {
		new TreeSelectionEX(); //�ŧi�����ج[����
	}
}