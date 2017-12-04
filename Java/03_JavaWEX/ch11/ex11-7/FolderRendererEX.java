import javax.swing.*;  //�ޥήM��
import javax.swing.event.*;
//�ޥΥ]�tJTree���O�i�޵o�ƥ����O���M��
import javax.swing.tree.*;
//�]�t��UJTree���O�إ߾𪬵��c�����O���M��
import javax.swing.border.*; //�ޥΥ]�t�ؽu���O���M��

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class FolderRendererEX extends JFrame {

	JLabel lbChildCount = new JLabel(), lbParent = new JLabel();
	//�ŧi��ܸ�Ƨ��T��������

	static final File DEFAULT_PATH = new File("C:\\");
	//�w�]��ܪ��Ϻи��|

	FolderTreeModel dtmDir = new FolderTreeModel(DEFAULT_PATH);
	//�ŧi�C�X�ϺФ���Ƨ���Model����

	JTree trDir = new JTree(dtmDir);
	//�ŧi�إ߾𪬵��c�����JTree����

	JComboBox cmbHardDrive = new JComboBox(); //�ŧi�զX���

	FolderRendererEX() {

		File[] roots = File.listRoots(); //���o�q�������Ϻи��|

		for(File file: roots)
			cmbHardDrive.addItem(file);
			//�N�Ϻи��|��File����[�J�զX���

		cmbHardDrive.setSelectedItem(DEFAULT_PATH);
		//�]�w�w�]������Ϻи��|

		trDir.setCellRenderer(new FolderTreeCellRenderer());
		//�]�w�ϥΦۭq��Ķ������

		//���U�^��ItemEvent�ƥ󪺺�ť������
		cmbHardDrive.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent e){
				//�P�_�O�_������ʧ@
				if(e.getStateChange() == e.SELECTED){
					dtmDir.setRoot((File)e.getItem());
					//���o������`�I

					trDir.updateUI(); //��s�𪬵��c���e��
				}
			}
		});

		//�B�ΰΦW���O�w�q�B�ŧi��ťTreeSelectionEvent�ƥ�
		//����ť������
		trDir.addTreeSelectionListener(
						new TreeSelectionListener(){

			public void valueChanged(TreeSelectionEvent e){

				File fileSel = 
					(File)trDir.getLastSelectedPathComponent();
				//���o������|�̫�@�Ӹ`�I�N����Ƨ�

				if(fileSel == null) //�Y������`�I�h�פ�����k
					return;

				File fileParent = fileSel.getParentFile();
				//���o����ɮת��W�@�h��Ƨ�

				if(fileParent == null) //�P�_�O�_�S���W�@�h��Ƨ�
					lbParent.setText("�L");
				else{
					lbParent.setText("".equals(fileParent.getName()) 
												? fileParent.getPath() 
												: fileParent.getName());
					//�P�_�W�@�h��Ƨ����W�٬O�_���Ŧr��, 
					//�O�h��ܤW�@�h��Ƨ������|,
					//�Ϥ�, �h��ܸ�Ƨ����W��
				}

				int childCount = dtmDir.getChildCount(fileSel);
				//���o�����Ƨ��U���l��Ƨ��Ӽ�

				lbChildCount.setText(String.valueOf(childCount));
				//�]�w��ܤl��Ƨ��Ӽ�
			}
		});

		Box bxOper = new Box(BoxLayout.X_AXIS);
		bxOper.add(new JLabel("�Ϻ� : "));
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(cmbHardDrive); //�[�J�զX���
		bxOper.setBorder(new EmptyBorder(0, 0, 3, 0));
		//�]�w�ϥΤU��e�׬�3���ťծؽu

		JPanel jpMsg = new JPanel(new GridLayout(1, 4, 10, 10));
		jpMsg.add(new JLabel("�l��Ƨ��Ӽ� : ", JLabel.RIGHT));
		jpMsg.add(lbChildCount); //��ܤ޵o�ƥ�`�I������
		jpMsg.add(new JLabel("�W�@�h��Ƨ� : ", JLabel.RIGHT));
		jpMsg.add(lbParent); //��ܤ޵o�ƥ�`�I������
		jpMsg.setBorder(new EmptyBorder(3, 3, 3,  3));

		Container cp = getContentPane(); //���o���e����

		cp.add(new JScrollPane(trDir)); //�N����[�J�����ϰ�
		cp.add(jpMsg, BorderLayout.SOUTH);
		cp.add(bxOper, BorderLayout.NORTH);

		getRootPane().setBorder(new EmptyBorder(3, 3, 3, 3));
		//�]�w�ڭ����ϥμe�׬�3���z���ؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 355);
		setVisible(true);
	}

	public static void main(String args[]) {
		new FolderRendererEX(); //�ŧi�����ج[����
	}
}

//���o���w�ϺФ�����Ƨ�
class FolderTreeModel implements TreeModel {

	private File root; //�ڸ`�I
	private Vector listeners = new Vector();
	//�x�s��ť�����󪺮e��

	//�w�q�P�ŧi�Ω�z���Ƨ���FileFilter����
	FileFilter ffDir = new FileFilter(){
		public boolean accept(File file){
			return file.isDirectory();
			//�P�_File����O�_�N���Ƨ�
		}
	};

	//�ǤJ���w���|���غc�l
	public FolderTreeModel(File rootDirectory){

		//�P�_�ڸ`�I�U����Ƨ��~�]�wroot�ݩ�
		if(rootDirectory.listFiles(ffDir) != null)
			root = rootDirectory;
	}

	public void setRoot(File rootDirectory){

		//�P�_�ڸ`�I�U�O�_����Ƨ�
		//�Y�S����Ƨ�, �h�]�w�ڸ`�I��null
		if(rootDirectory.listFiles(ffDir) != null)
			root = rootDirectory;
		else
			root = null;
	}
   
	//���o�ڸ`�I
	public Object getRoot() {
		return root;
	}

	//���o�Y�`�I�U���w���ޭȩҥN���l�`�I
	public Object getChild(Object parent, int index) {

		File directory = (File) parent; //���o�`�I

		File[] files = directory.listFiles(ffDir);
		//���o�`�I���ŦXffDir�z�磌��File����

		return new File(directory, files[index].getName());
		//�^�Ǩ��o��File����
	}

	//���o�`�I�ҥ]�t�l�`�I���Ӽ�
	public int getChildCount(Object parent){
		File[] files = ((File)parent).listFiles(ffDir);
		//���o�`�I���ŦXffDir�z�磌��File����
      
		return files.length; //�^�Ǥl�`�I�Ӽ�
	}

	//�P�_�`�I�O�_�����]�t�l�`�I�����`�I
	public boolean isLeaf(Object node) {

		File[] files = ((File)node).listFiles(ffDir);
		//���o�`�I���ŦXffDir�z�磌��File����

		//�Yfiles��null�Ϊ̨S���ɮ׫h�Ǧ^true
		if(files == null || files.length == 0)
			return true;

		return false;
	}

	//���o�`�I���Y�l�`�I�����ޭ�
	public int getIndexOfChild(Object parent, Object child) {

		File directory = (File)parent; //�ǤJ���`�I
		File file = (File)child; //�ǤJ���l�`�I

		File[] children = directory.listFiles(ffDir);
		//���o�`�I���ŦXffDir�z�磌��File����
     
		if(children == null) //�Y�L�l�`�I�Ǧ^-1
			return -1;

		//�B��for�j��M��l�`�I���ۦP���`�I
		//���h�Ǧ^���ޭ�
		for(int i = 0; i < children.length; i++ ) {
			if(file.equals(children[i])) { //�P�_�`�I�O�_�ۦP
				return i; //�Ǧ^���ޭ�
			}
		}
      
		return -1; //�S�����h�Ǧ^-1
	}
 
	//�����Ѹ`�I�ק�\��
	public void valueForPathChanged(TreePath path, Object value) {
	}

	//���U��ťTreeModelEvent�ƥ󪺺�ť������
	public void addTreeModelListener(TreeModelListener listener){

		if(listener == null)
			return;

		listeners.add(listener); //�N��ť������[�J�e��
	}
   
	//������ťTreeModelEvent�ƥ󪺺�ť������
	public void removeTreeModelListener(TreeModelListener listener){
		listeners.remove(listener); //�q�e��������ť������
	}
}

//�w�q�𪬵��c������ܵe���ϥΪ���Ķ��
class FolderTreeCellRenderer 
	extends DefaultTreeCellRenderer {

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

		File item = (File)value;
		//���o�Q����`�I������

		if(((File)tree.getModel().getRoot()).equals(item)){
			//�P�_�O�_���ڸ`�I

			setText(item.getPath());
			//�O�h�]�w��ܤ�r��File���󪺸��|
		}
		else{
			setText(item.getName());
			//�]�w�`�I��ܪ���r��File����ҥN���ɮת��W��
		}

		setIcon(
			(expanded ? ICON_OPEN_FOLDER : ICON_CLOSED_FOLDER));
		//�̷Ӹ`�I�O�_�i�}�]�w�ϥΪ��ϥ�

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