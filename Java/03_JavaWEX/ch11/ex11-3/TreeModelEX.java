import javax.swing.*;  //�ޥήM��
import javax.swing.event.*;
//�ޥΥ]�tJTree���O�i�޵o�ƥ����O���M��

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.tree.*; //�]�t��UJTree���O�إ߾𪬵��c�����O���M��
import javax.swing.border.*; //�ޥΥ]�t�ؽu���O���M��

public class TreeModelEX extends JFrame {

	JLabel lbChildNodeNum = new JLabel(), 
				lbLeafNode = new JLabel(),
				lbNodeLevel = new JLabel(), 
				lbNodePath  = new JLabel(),
				lbPreSibleNode = new JLabel(), 
				lbNextSibleNode = new JLabel();
	//�ŧi��ܸ`�I��ƪ�����

	JTextField tfNode = new JTextField(); //��J�`�I�W�٪���r��

	JButton  btnAdd = new JButton("�s�W"),
		           btnRemove = new JButton("����"),
				   btnRemoveAllChildren = new JButton("�����Ҧ��`�I");
	//�ŧi����s�W�B�����`�I�����O���s

	JButton btnNodeInfo = new JButton("�`�I��T");
	//�ŧi���o�`�I��T�����O���s

	JTree trBook = new JTree(); //�ŧi�إ߾𪬵��c�����JTree����

	DefaultTreeModel dtmBook;
	//JTree����ϥΪ�Model����

	JCheckBox cbAddChild = new JCheckBox("�i�s�W�l�`�I");
	//�]�w�O�_�s�W�l�`�I���֨����

	TreeModelEX() {

		DefaultMutableTreeNode 
			dmtnOOP = 
				new DefaultMutableTreeNode("����ɦV�{���y��", true),
			dmtnC = new DefaultMutableTreeNode("C/C++", true),
			dmtnJava = new DefaultMutableTreeNode("Java", true),
			dmtnHtml = new DefaultMutableTreeNode("�ʺA����", true),
			dmtnRoot = new DefaultMutableTreeNode("�줸���", true);
		//�ŧi�`�I����

		//�H�U�ԭz�N��l�`�I�[�J�`�I
		dmtnC.add(new DefaultMutableTreeNode("C/C++ �J���i��", false));
		dmtnC.add(	new DefaultMutableTreeNode(
			"Visual C++.Net �J���i��", false));
		dmtnC.add(
			new DefaultMutableTreeNode("Linux C/C++ �J���i��", false));

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

		dtmBook = new DefaultTreeModel(dmtnRoot);
		//���oJTree����ϥΪ�Model����

		trBook.setModel(dtmBook);
		//�]�wJTree����ϥ�dtmBook����Model����
		
		//�B�ΰΦW���O�w�q�B�ŧi��ťTreeSelectionEvent�ƥ�
		//����ť������
		trBook.addTreeSelectionListener(new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent e){
				updateNodeInfo(); //��s�`�I��ܸ�T
			}
		});

		//�H�ΦW���O�w�q�B�ŧi��ťActionEvent�ƥ󪺺�ť������
		btnAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				String strNode = tfNode.getText();
				//���o��r�椺���`�I�W��

				if("".equals(strNode)) //���i�H�אּ strNode.equals("")
					return;

				TreePath tpSelNode = trBook.getSelectionPath();
				//���o�Q����`�I�����|

				DefaultMutableTreeNode dmtnParent = 
					(DefaultMutableTreeNode) tpSelNode.getLastPathComponent();
				//���o�`�I���|���̫�@�Ӹ`�I

				DefaultMutableTreeNode dmtnNode = 
					new DefaultMutableTreeNode(
						strNode, cbAddChild.isSelected());
				//�إ߱��s�W���`�I

				dtmBook.insertNodeInto(
					dmtnNode, dmtnParent, dmtnParent.getChildCount());
				//���J�s�W�`�I

				updateNodeInfo(); //��s�`�I��ܸ�T
			}
		});

		//�H�ΦW���O�w�q�B�ŧi��ťActionEvent�ƥ󪺺�ť������
		btnRemove.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				TreePath tpSelNode = trBook.getSelectionPath();
				//���o�Q����`�I�����|

				if(tpSelNode != null){
					DefaultMutableTreeNode dmtnNode = 
						(DefaultMutableTreeNode) 
							tpSelNode.getLastPathComponent();
					//���o�`�I���|���̫�@�Ӹ`�I

					DefaultMutableTreeNode dmtnParent = 
						(DefaultMutableTreeNode)dmtnNode.getParent();
					//���o�Q����`�I�����`�I

					if(dmtnParent == null) //�Y�S�����`�I�h�פ�����k
						return;

					dtmBook.removeNodeFromParent(dmtnNode);
					//�����`�I

					updateNodeInfo(); //��s�`�I��ܸ�T
				}
			}
		});

		//�H�ΦW���O�w�q�B�ŧi��ťActionEvent�ƥ󪺺�ť������
		btnRemoveAllChildren.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				TreePath tpSelNode = trBook.getSelectionPath();
				//���o�Q����`�I�����|

				if(tpSelNode != null){

					DefaultMutableTreeNode dmtnNode = 
						(DefaultMutableTreeNode) 
							tpSelNode.getLastPathComponent();
					//���o�`�I���|���̫�@�Ӹ`�I

					dmtnNode.removeAllChildren(); //�����Ҧ��l�`�I
					dtmBook.reload(dmtnNode);
					//��sdmtnNode�`�I���𪬵��c

					updateNodeInfo(); //��s�`�I��ܸ�T
				}
			}
		});

		Box bxOper = new Box(BoxLayout.X_AXIS);
		bxOper.add(new JLabel("�`�I�W�� : "));
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(tfNode); //�[�J��J�`�I�W�٪���r��
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(cbAddChild);
		//�[�J��ܬO�_�i�[�J�l�`�I���֨����

		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(btnAdd); //�[�J�s�W�`�I�����s
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(btnRemove); //�[�J�����`�I�����s
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(btnRemoveAllChildren); //�[�J�����`�I�����s

		Box bxNodePath = new Box(BoxLayout.X_AXIS);
		bxNodePath.add(
			new JLabel("�`�I���|�g�L���`�I : ", JLabel.LEFT));
		bxNodePath.add(lbNodePath);

		JPanel jpNodeInfo = new JPanel(new GridLayout(3, 2, 5, 5));
		jpNodeInfo.add(new JLabel("�l�`�I�Ӽ� : ", JLabel.RIGHT));
		jpNodeInfo.add(lbChildNodeNum);
		jpNodeInfo.add(new JLabel("���`�I�Ӽ� : ", JLabel.RIGHT));
		jpNodeInfo.add(lbLeafNode);
		jpNodeInfo.add(
			new JLabel("�`�I�h��	: ", JLabel.RIGHT));
		jpNodeInfo.add(lbNodeLevel);
		
		Box bxMsg = new Box(BoxLayout.Y_AXIS);
		bxMsg.add(Box.createVerticalStrut(20));
		bxMsg.add(jpNodeInfo);
		bxMsg.add(Box.createVerticalStrut(250));
		
		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new BorderLayout(5, 5));

		cp.add(trBook); //�N����[�J�����ϰ�
		cp.add(bxOper, BorderLayout.NORTH);
		cp.add(bxNodePath, BorderLayout.SOUTH);
		cp.add(bxMsg, BorderLayout.EAST);

		getRootPane().setBorder(new EmptyBorder(3, 3, 3, 3));
		//�]�w�ڭ����ϥμe�׬�3���z���ؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(630, 430);
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
				String.valueOf(dtmBook.getChildCount(dmtnNode)));
			//�]�w������ܸ`�I���l�`�I�Ӽ�

			lbLeafNode.setText(
				String.valueOf((dtmBook.isLeaf(dmtnNode) 
										 ? "�O���`�I" : "���O���`�I")));
			//�]�w������ܸ`�I�]�t�����`�I�Ӽ�

			lbNodeLevel.setText(String.valueOf(
				dtmBook.getPathToRoot(dmtnNode).length));
			//�]�w������ܸ`�I���h��

			lbNodePath.setText(String.valueOf(
				Arrays.toString(dtmBook.getPathToRoot(dmtnNode))));
			//�]�w������ܸ`�I�ܮڸ`�I�����|�g�L���`�I
		}
	}

	public static void main(String args[]) {
		new TreeModelEX(); //�ŧi�����ج[����
	}
}