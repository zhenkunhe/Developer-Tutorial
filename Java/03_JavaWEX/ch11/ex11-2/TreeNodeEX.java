import javax.swing.*;  //�ޥήM��
import javax.swing.event.*;
//�ޥΥ]�tJTree���O�i�޵o�ƥ����O���M��

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.tree.*; //�]�t��UJTree���O�إ߾𪬵��c�����O���M��
import javax.swing.border.*; //�ޥΥ]�t�ؽu���O���M��

public class TreeNodeEX extends JFrame {

	JLabel lbNode = new JLabel(), lbType = new JLabel();
	//�ŧi��ܸ`�I�P�ƥ󫬺A��ƪ�����

	JLabel lbChildNodeNum = new JLabel(), 
				lbLeafNodeNum = new JLabel(),
				lbNodeDeep = new JLabel(), 
				lbNodeLevel  = new JLabel(),
				lbPreSibleNode = new JLabel(), 
				lbNextSibleNode = new JLabel();
	//�ŧi��ܸ`�I�T��������

	JTextField tfNode = new JTextField(); //��J�`�I�W�٪���r��

	JButton  btnAdd = new JButton("�s�W"),
		           btnRemove = new JButton("����");
	//�ŧi����s�W�B�����`�I�����O���s

	JButton btnNodeInfo = new JButton("�`�I��T");
	//�ŧi���o�`�I��T�����O���s

	JTree trBook; //�ŧi�إ߾𪬵��c�����JTree����

	JCheckBox cbAddChild = new JCheckBox("�i�s�W�l�`�I");
	//�]�w�O�_�s�W�l�`�I���֨����

	TreeNodeEX() {

		DefaultMutableTreeNode 
			dmtnOOP = 
				new DefaultMutableTreeNode("����ɦV�{���y��", true),
			dmtnC = new DefaultMutableTreeNode("C/C++", true),
			dmtnJava = new DefaultMutableTreeNode("Java", true),
			dmtnHtml = new DefaultMutableTreeNode("�ʺA����", true),
			dmtnRoot = new DefaultMutableTreeNode("�줸���", true);
		//�ŧi�`�I����

		//�H�U�ԭz�N��l�`�I�[�J�`�I
		dmtnC.add(new DefaultMutableTreeNode(
			"C/C++ �J���i��", false));
		dmtnC.add(	new DefaultMutableTreeNode(
			"Visual C++.Net �J���i��", false));
		dmtnC.add(	new DefaultMutableTreeNode(
			"Linux C/C++ �J���i��", false));

		dmtnJava.add(
			new DefaultMutableTreeNode("Java 2 �J���i��", false));
		dmtnJava.add(new DefaultMutableTreeNode(
			"Java 2 ����ɦV�{���y��", false));

		dmtnHtml.add(
			new DefaultMutableTreeNode("ASP�ʺA�����J�����", false));
		dmtnHtml.add(
			new DefaultMutableTreeNode("JSP�ʺA�����J�����", false));
		dmtnHtml.add(new DefaultMutableTreeNode(
			"DHTML�ʺA�����J�����", false));

		dmtnOOP.add(dmtnC);
		dmtnOOP.add(dmtnJava);

		dmtnRoot.add(dmtnOOP); //�N�`�I�[�J�ڸ`�I
		dmtnRoot.add(dmtnHtml);

		trBook = new JTree(dmtnRoot); //�ŧiJTree����

		//�B�ΰΦW���O�w�q�B�ŧi��ťTreeSelectionEvent�ƥ�
		//����ť������
		trBook.addTreeSelectionListener(new TreeSelectionListener(){

			public void valueChanged(TreeSelectionEvent e){
				lbNode.setText(e.getPath().toString());
				//�]�w��ܤ޵o�ƥ󤧸`�I�����|

				lbType.setText("���"); //��ܤ޵o�`�I����ƥ�

				updateNodeInfo(); //��s�`�I��ܸ�T
			}
		});

		Box bxNode = new Box(BoxLayout.X_AXIS);
		bxNode.add(new JLabel("�Q������`�I : ", JLabel.RIGHT));
		bxNode.add(Box.createHorizontalStrut(5));
		bxNode.add(lbNode); //��ܤ޵o�ƥ󪺸`�I

		Box bxEvent = new Box(BoxLayout.X_AXIS);
		bxEvent.add(new JLabel("�ƥ󫬺A : ", JLabel.RIGHT));
		bxEvent.add(Box.createHorizontalStrut(5));
		bxEvent.add(lbType); //��ܨƥ󫬺A���`�I

		JPanel jpEvent = new JPanel(new GridLayout(2, 1, 5, 5));
		jpEvent.add(bxNode); //��ܤ޵o�ƥ󪺸`�I
		jpEvent.add(bxEvent); //��ܤ޵o�ƥ󪺸`�I

		JPanel jpNodeInfo = new JPanel(new GridLayout(6, 2, 5, 5));
		jpNodeInfo.add(new JLabel("�l�`�I�Ӽ� : ", JLabel.RIGHT));
		jpNodeInfo.add(lbChildNodeNum);
		jpNodeInfo.add(new JLabel("���`�I�Ӽ� : ", JLabel.RIGHT));
		jpNodeInfo.add(lbLeafNodeNum);
		jpNodeInfo.add(
			new JLabel("�`�I�`��(�ܸ��`�I)	: ", JLabel.RIGHT));
		jpNodeInfo.add(lbNodeDeep);
		jpNodeInfo.add(
			new JLabel("�`�I�h��(�q�ڸ`�I��_) : ", JLabel.RIGHT));
		jpNodeInfo.add(lbNodeLevel);
		jpNodeInfo.add(new JLabel("�e�@�ӥS�̸`�I : ", JLabel.RIGHT));
		jpNodeInfo.add(lbPreSibleNode);
		jpNodeInfo.add(new JLabel("�U�@�ӥS�̸`�I : ", JLabel.RIGHT));
		jpNodeInfo.add(lbNextSibleNode);

		Box bxMsg = new Box(BoxLayout.Y_AXIS);
		bxMsg.add(Box.createVerticalStrut(20));
		bxMsg.add(jpNodeInfo);
		bxMsg.add(Box.createVerticalStrut(150));
		bxMsg.setBorder(new EmptyBorder(3, 3, 3,  3));

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new BorderLayout(10, 10));

		cp.add(trBook); //�N����[�J�����ϰ�
		cp.add(bxMsg, BorderLayout.EAST);
		cp.add(jpEvent, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(3, 3, 3, 3));
		//�]�w�ڭ����ϥμe�׬�3���z���ؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(630, 350);
		setVisible(true);
	}

	private void updateNodeInfo(){

		TreePath tpSelNode = trBook.getSelectionPath();
		//���o�Q����`�I�����|

		if(tpSelNode != null){
			DefaultMutableTreeNode dmtnNode = 
				(DefaultMutableTreeNode) 
					tpSelNode.getLastPathComponent();
			//���o�`�I���|���̫�@�Ӹ`�I

			lbChildNodeNum.setText(
				String.valueOf(dmtnNode.getChildCount()));
			//�]�w������ܸ`�I���l�`�I�Ӽ�

			lbLeafNodeNum.setText(
				String.valueOf(dmtnNode.getLeafCount()));
			//�]�w������ܸ`�I�]�t�����`�I�Ӽ�

			lbNodeDeep.setText(
				String.valueOf(dmtnNode.getDepth()));
			//�]�w������ܸ`�I��̫�@�Ӹ`�I���`��

			lbNodeLevel.setText(
				String.valueOf(dmtnNode.getLevel()));
			//�]�w������ܸ`�I���h��

			lbPreSibleNode.setText(
				(dmtnNode.getPreviousSibling() == null ? 
				 "null"
				 : dmtnNode.getPreviousSibling().toString()));
			//�]�w������ܸ`�I���e�@�ӥS�̸`�I

			lbNextSibleNode.setText(
				(dmtnNode.getNextSibling() == null ? 
				 "null" 
				 : dmtnNode.getNextSibling().toString()));
			//�]�w������ܸ`�I���U�@�ӥS�̸`�I
		}
	}

	public static void main(String args[]) {
		new TreeNodeEX(); //�ŧi�����ج[����
	}
}