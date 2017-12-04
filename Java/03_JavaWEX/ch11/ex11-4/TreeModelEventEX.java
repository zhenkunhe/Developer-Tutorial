import javax.swing.*;  //�ޥήM��
import javax.swing.event.*;
//�ޥΥ]�tJTree���O�i�޵o�ƥ����O���M��

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.tree.*; //�]�t��UJTree���O�إ߾𪬵��c�����O���M��
import javax.swing.border.*; //�ޥΥ]�t�ؽu���O���M��

public class TreeModelEventEX extends JFrame {

	JLabel lbType = new JLabel();
	//�ŧi��ܸ`�I�P�ƥ󫬺A��ƪ�����

	JLabel lbChildNodeIndices = new JLabel(), 
				lbChildNode = new JLabel(),
				lbPath = new JLabel(), 
				lbTreePath = new JLabel();

	JTextField tfNode = new JTextField(); //��J�`�I�W�٪���r��

	JButton  btnAdd = new JButton("�s�W"),
		           btnRemove = new JButton("����"),
				   btnRemoveAllChild = new JButton("�����Ҧ��`�I");
	//�ŧi����s�W�B�����`�I�����O���s

	JTree trBook; //�ŧi�إ߾𪬵��c�����JTree����

	DefaultTreeModel dtmBook;
	//JTree����ϥΪ�Model����

	JCheckBox cbAddChild = new JCheckBox("�i�s�W�l�`�I");
	//�]�w�O�_�s�W�l�`�I���֨����

	TreeModelEventEX() {

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

		trBook = new JTree(dmtnRoot); //�ŧiJTree����

		dtmBook = (DefaultTreeModel) trBook.getModel();
		//���oJTree����ϥΪ�Model����

		//�H�ΦW���O�w�q�B�ŧi
		//�^��TreeModelEvent�ƥ󪺺�ť������
		dtmBook.addTreeModelListener(new TreeModelListener(){

			//�^���`�I���ܰʧ@
			public void treeNodesChanged(TreeModelEvent e){
				lbType.setText("���ܸ`�I"); //�]�w��ܤ޵o�ƥ󪺦W��
				updateNodeInfo(e); //��s�`�I�T��
			}

			//�^���`�I���J�ʧ@
			public void treeNodesInserted(TreeModelEvent e){
				lbType.setText("�s�W�`�I");
				updateNodeInfo(e); //��s�`�I�T��
			}

			//�^���`�I�����ʧ@
			public void treeNodesRemoved(TreeModelEvent e){
				lbType.setText("�����`�I");
				updateNodeInfo(e); //��s�`�I�T��
			}

			//�^���𪬵��c����
			public void treeStructureChanged(TreeModelEvent e){
			
				System.out.println("�ƥ󫬺A : ���c����");

				System.out.println("�޵o�ƥ󪺸`�I���| : "
								+ e.getTreePath().toString());
				//���o�`�I���|

				System.out.println("�l�`�I���ޭ� : " 
								+ Arrays.toString(e.getChildIndices()));
				//�]�w������ܤl�`�I���ޭ�

				System.out.println("�l�`�I : " 
								+ Arrays.toString(e.getChildren()));
				//�]�w������ܤl�`�I

				System.out.println("�`�I���|�g�L������ : " 
								+ Arrays.toString(e.getPath()));
				//�]�w������ܸ`�I���|�g�L������

				System.out.println("---------------------------------"
								+ "------------------------\n");
			}

			private void updateNodeInfo(TreeModelEvent e){

				lbChildNodeIndices.setText(Arrays.toString(e.getChildIndices()));
				//�]�w������ܸ`�I���l�`�I�Ӽ�

				lbChildNode.setText(Arrays.toString(e.getChildren()));
				//�]�w������ܸ`�I�]�t�����`�I�Ӽ�

				lbPath.setText(Arrays.toString(e.getPath()));
				//�]�w������ܸ`�I���h��

				lbTreePath.setText(e.getTreePath().toString());
				//���o�`�I���|���r��
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

				dtmBook.reload(dmtnNode);
				//��sdmtnNode�`�I���𪬵��c
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

					dtmBook.removeNodeFromParent(dmtnNode);
					//�����`�I

					if(dmtnParent == null) //�Y�S�����`�I�h�פ�����k
						return;

					dtmBook.reload(dmtnParent);
					//��sdmtnParent�`�I���𪬵��c
				}
			}
		});

		//�H�ΦW���O�w�q�B�ŧi��ťActionEvent�ƥ󪺺�ť������
		btnRemoveAllChild.addActionListener(new ActionListener(){
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

					dmtnNode.removeAllChildren(); //�����Ҧ��l�`�I
					dtmBook.reload(dmtnNode);
					//��sdmtnNode�`�I���𪬵��c
				}
			}
		});

		Box bxOper = new Box(BoxLayout.X_AXIS);
		bxOper.add(new JLabel("�`�I�W�� : "));
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(tfNode); //�[�J��J�`�I�W�٪���r��
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(cbAddChild); //�[�J��ܬO�_�i�[�J�l�`�I���֨����
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(btnAdd); //�[�J�s�W�`�I�����s
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(btnRemove); //�[�J�����`�I�����s
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(btnRemoveAllChild); //�[�J�����`�I�����s

		Box bxType = new Box(BoxLayout.X_AXIS);
		bxType.add(new JLabel("�ƥ󫬺A : ", JLabel.RIGHT));
		bxType.add(Box.createHorizontalStrut(5));
		bxType.add(lbType); //��ܨƥ󫬺A���`�I

		Box bxTreePath = new Box(BoxLayout.X_AXIS);
		bxTreePath.add(new JLabel("�޵o�ƥ󪺸`�I : ", JLabel.RIGHT));
		bxTreePath.add(Box.createHorizontalStrut(5));
		bxTreePath.add(lbTreePath); //��ܤ޵o�ƥ󪺸`�I

		Box bxChildNodeIndices = new Box(BoxLayout.X_AXIS);
		bxChildNodeIndices.add(new JLabel("�l�`�I���ޭ� : ", JLabel.RIGHT));
		bxChildNodeIndices.add(Box.createHorizontalStrut(5));
		bxChildNodeIndices.add(lbChildNodeIndices);

		Box bxChildNode = new Box(BoxLayout.X_AXIS);
		bxChildNode.add(new JLabel("�l�`�I : ", JLabel.RIGHT));
		bxChildNode.add(Box.createHorizontalStrut(5));
		bxChildNode.add(lbChildNode);

		Box bxPath = new Box(BoxLayout.X_AXIS);
		bxPath.add(new JLabel("�`�I���|�g�L������ : ", JLabel.RIGHT));
		bxPath.add(Box.createHorizontalStrut(5));
		bxPath.add(lbPath);

		JPanel jpNodeInfo = new JPanel(new GridLayout(5, 1, 5, 5));
		jpNodeInfo.add(bxTreePath);
		jpNodeInfo.add(bxType); 
		jpNodeInfo.add(bxChildNodeIndices);
		jpNodeInfo.add(bxChildNode);
		jpNodeInfo.add(bxPath);

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new BorderLayout(10, 10));

		cp.add(trBook); //�N����[�J�����ϰ�
		cp.add(bxOper, BorderLayout.NORTH);
		cp.add(jpNodeInfo, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(3, 3, 3, 3));
		//�]�w�ڭ����ϥμe�׬�3���z���ؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(550, 460);
		setVisible(true);
	}

	public static void main(String args[]) {
		new TreeModelEventEX(); //�ŧi�����ج[����
	}
}