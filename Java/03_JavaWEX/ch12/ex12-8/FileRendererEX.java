import javax.swing.*;  //�ޥήM��
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
//�ޥΩw�q��UJTable����B�z��椧�����B���O���M��

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import java.io.*;
import java.text.*; //�ޥΥ]�tSimpleDateFormat���O���M��

public class FileRendererEX extends JFrame {

	static final File DEFAULT_PATH = new File("C:\\");
	//�w�]��ܪ��Ϻи��|

	JComboBox cmbHardDrive = new JComboBox();
	//�ŧi������ܺϺи��|���զX���

	FileTableModel ftmDir = new FileTableModel(DEFAULT_PATH);
	//�ŧi�C�X���w���|����Ƨ����ɮפ�TabelModel����

	JTable tbDir = new JTable(ftmDir);
	//�HModel����ŧi�إߪ�檺JTable����

	JLabel lbCurPath = new JLabel(DEFAULT_PATH.getPath());
	//��ܥثe�˵������|

	public FileRendererEX() {
		
		tbDir.setDefaultRenderer(String.class, new FileNameRenderer());
		tbDir.setDefaultRenderer(Boolean.class, new BasicRenderer());
		tbDir.setDefaultRenderer(Long.class, new BasicRenderer());
		//�]�w�B�z�U������ƨϥΪ���Ķ������

		File[] roots = File.listRoots(); //���o�q�������Ϻи��|

		for(File file: roots)
			cmbHardDrive.addItem(file);
			//�N�Ϻи��|��File����[�J�զX���

		cmbHardDrive.setSelectedItem(DEFAULT_PATH);
		//�]�w�w�]������Ϻи��|

		//���U�^��ItemEvent�ƥ󪺺�ť������
		cmbHardDrive.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent e){
				//�P�_�O�_������ʧ@
				if(e.getStateChange() == e.SELECTED){
					ftmDir.setCurrentPath((File)e.getItem());
					//���o������`�I, 
					//�ó]�w����������ɮפ��e����Ƨ����|

					tbDir.updateUI(); //��s��檺�e��
				}
			}
		});

		//�H�ΦW���O���覡, �w�q�B���U��ťTableModel����
		//TableModelEvent�ƥ󪺺�ť������
		ftmDir.addTableModelListener(new TableModelListener(){
			public void tableChanged(TableModelEvent e){
				File curDir = 
					((FileTableModel) e.getSource()).getCurrentPath();
				//���o�ثe�s������Ƨ����|

				lbCurPath.setText(curDir.getPath());
				//�]�w������ܥثe��Ƨ������|
			}
		});

		//���U�^��JTable����MouseEvent�ƥ󪺺�ť������
		tbDir.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){

				if(e.getClickCount() == 2){ //�P�_�ƹ��O�_�s���G�U

					//�Y�ƹ��I�諸���O��1��h�פ��k������
					if(tbDir.columnAtPoint(e.getPoint()) != 0)
						return;

					int selRow = tbDir.rowAtPoint(e.getPoint());
					//���o�ƹ��I���m�Ҧb����ƪ��C����

					String strFile = (String) ftmDir.getValueAt(selRow, 0);
					//���o�Q�I���ƦC����1�檺��, �䤺�e���ɮצW��

					File selFile = new File(ftmDir.getCurrentPath(), strFile);
					//�إ߳Q�����ƩҥN���ɮ�

					if(selFile.isDirectory()){ //�P�_�ɮ׬O�_����Ƨ�
						ftmDir.setCurrentPath(selFile);
						//�]�wFileTableModel����N��ܦ���Ƨ������ɮ�

						tbDir.updateUI(); //��s��檺�e��
					}
				}
			}
		});

		JButton btnUp = new JButton("�^�W�@�h");
		//�ŧi�����ܥثe�˵����|�W�@�h��Ƨ������O���s

		//�ŧi�B���U�^�����O���sActionEvent�ƥ󪺺�ť������
		btnUp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				File curPath = ftmDir.getCurrentPath();
				//���o�ثeFileTableModel������ܪ����|

				//�P�_�ثe�˵����|�O�_���W�@�h��Ƨ�
				if(curPath.getParentFile() != null){
					ftmDir.setCurrentPath(curPath.getParentFile());
					//�]�wFileTableModel����N��ܤW�@�h��Ƨ������ɮ�

					tbDir.updateUI(); //��s��檺�e��
				}
			}
		});

		TableColumn colTitle = tbDir.getColumnModel().getColumn(0);
		//���oJTable����ϥΪ�TableColumnModel����,
		//�A���o����Ĥ@�檺TableColumn����

		colTitle.setPreferredWidth(200); //�]�w��쪺�ߦn�e�׬�200

		TableColumn colDate = tbDir.getColumnModel().getColumn(3);
		colDate.setPreferredWidth(150); //�]�w��쪺�ߦn�e�׬�150

		Box bxOper = new Box(BoxLayout.X_AXIS);
		bxOper.add(new JLabel("�Ϻ� : "));
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(cmbHardDrive); //�[�J�զX���

		Box bxPath = new Box(BoxLayout.X_AXIS);
		bxPath.add(new JLabel("�ثe�˵����| : "));
		bxPath.add(Box.createHorizontalStrut(5));
		bxPath.add(lbCurPath);
		bxPath.add(Box.createHorizontalGlue());
		bxPath.add(btnUp);

		Box bxNorth = new Box(BoxLayout.Y_AXIS);
		bxNorth.add(bxOper);
		bxNorth.add(Box.createVerticalStrut(5));
		bxNorth.add(bxPath);

		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new BorderLayout(10, 10));
		//�إߦU�ϰ�����B�������Z��10��BorderLayout����

		cp.add(bxNorth, BorderLayout.NORTH);	
		cp.add(new JScrollPane(tbDir));		
		//�N����[�J���e����

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����|�P�N�ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 250);
		setVisible(true);
	}

	public static void main(String args[]) {
		new FileRendererEX(); //�ŧi�����ج[����	 
	}
}

//�w�q�B�z�ɮ׸�ƪ�Model����
class FileTableModel extends AbstractTableModel {

	private File dir;
	private File[] files;
	//�x�s��Ƨ����ɮת��}�C

	String[] colName = {"�W��", "�j�p", "�O�_���ɮ�", 
									 "�̫�ק���", "�iŪ��", "�i�ק�"};
	//�ŧi�x�s���W�٪��r��

	SimpleDateFormat sdf = 
		new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	//�w�q�̫�ק����ϥΪ�����榡

	Class[] types = new Class[] { 
		String.class, Long.class, Boolean.class, 
		String.class, //�Ъ`�N! Date�g�L�榡�ƫ�, ���O��String
		Boolean.class, Boolean.class};
	//���t�XTableCellRenderer����, 
	//�����ŧi�x�s�ɮ׸�Ƥ����O��Class�}�C

	FileTableModel(){
		this(new File(".")); //�I�sFileTableModel(String path)�غc�l
	}

	FileTableModel(File path){
		setCurrentPath(path); //�]�w���˵�����Ƨ�
	}

	public int getColumnCount(){ return colName.length; }
	//���o��쪺�Ӽ�

	public int getRowCount(){

		if(files == null) return 0;
		//�Y��Ƨ����S���ɮ׫h�Ǧ^0

		return files.length; //�Ǧ^���o�ɮת��Ӽ�
	}
	//���o��ƪ��C��

	//���o���w��B�C��m���x�s����
	public Object getValueAt(int row, int col){
		
		//�P�_�C���ޭȬO�_���T
		if(row < 0 || row >= getRowCount())
			return "";

		//�P�_����ޭȬO�_���T
		if(col < 0 || col >= getColumnCount())
			return "";

		File file = files[row];
		//�H�C�ư������ޭȨ��o�ɮת���

		switch(col){ //�H����ާP�_�����o���ɮ׸��
			case 0:
				return file.getName(); //���o�ɮצW��
			case 1:
				return file.length(); //���o�ɮפj�p
			case 2:
				return file.isFile(); //�P�_�O�_���ɮ�
			case 3:
				return sdf.format(new Date(file.lastModified()));
							//�̫�ק���
			case 4:
				return file.canRead(); //�O�_�iŪ��
			case 5:
				return file.canWrite(); //�O�_�i�g�J
		}

		return "";
	}

	//���o���W��, �i���w�q
	public String getColumnName(int column){
		return colName[column];
	}

	//�]�w�ثe�˵�����Ƨ������|
	public void setCurrentPath(File dir) {
		if(!dir.isDirectory()){
			System.err.println("���|�����N���Ƨ�");
			return;
		}

		this.dir  = dir; //�]�w���˵�����Ƨ�
		files = dir.listFiles(); //�C�X��Ƨ������Ҧ��ɮ�

		fireTableChanged(new TableModelEvent(this));
		//Ĳ�o���o�ͧ��ܪ��ƥ�, �å�XTableModelEvent����
	}

	//���o�ثe�˵�����Ƨ������|
	public File getCurrentPath(){
		return dir;
	}

	public Class getColumnClass(int col) { return types[col]; }
	//���o�ɮ׬Y����Ƥ����O��Class����,
	//����k�N��TableCellRenderer����B�@�ϥ�
}

//�w�q��Ķ�����O
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
		//�I�s��¦���O��getTableCellRendererComponent()��k

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

		return this;
	}
}

//�w�q�B�z�ɮצW�ٸ�ƪ���Ķ�����O
class FileNameRenderer extends BasicRenderer {

	private final static ImageIcon ICON_CLOSED_FOLDER = 
		new ImageIcon("icon/ClosedFolder.jpg");
	private final static ImageIcon ICON_FILE = 
		new ImageIcon("icon/File.gif");
	//�w�q�N��ϥܪ��R�A�`��

	public Component getTableCellRendererComponent(
							JTable table, //�ϥ���Ķ�����󪺪�椸��
							Object value, //��Ķ�����B�z������x�s�檺��
							boolean isSelected, //�Q�B�z�x�s��O�_�Q���
							boolean hasFocus, //�Q����x�s��O�_�֦��J�I
							int row, //�Q����x�s��Ҧb��m���C��
							int column){ //�Q����x�s��Ҧb��m�����

		super.getTableCellRendererComponent(
			table, value, isSelected, hasFocus, row, column);
		//�I�s��¦���O��getTableCellRendererComponent()��k

		if(table.getColumnName(column).equals("�W��")){
			TableModel tm = table.getModel();
			//���o��椸���ƪ�TableModel����

			setIcon("true".equals(tm.getValueAt(row, 2).toString()) 
								? ICON_FILE : ICON_CLOSED_FOLDER);
			//�P�_����C��3�檺��ƬO�_��true, 
			//�O�h�]�w�ϥ�ICON_FILE�ϥ�
			//�Y���O�h�]�w�ϥ�ICON_CLOSED_FOLDER�ϥ�
		}
		else
			setIcon(null); //�]�w���ϥΥ���ϥ�

		return this;
	}
}