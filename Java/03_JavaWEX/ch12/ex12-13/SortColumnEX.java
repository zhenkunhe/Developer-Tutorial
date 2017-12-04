import javax.swing.*;  //�ޥήM��
import javax.swing.border.EmptyBorder; //�ޥ�EmptyBorder���O
import javax.swing.event.*;
import javax.swing.table.*;
//�ޥΩw�q��UJTable����B�z��椧�����B���O���M��

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat; //�ޥ�SimpleDateFormat���O

public class SortColumnEX extends JFrame {

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

	public SortColumnEX() {

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

					//�Y�ƹ��I�諸���O��1��h�N�פ��k������
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

		DefaultTableColumnModel dtcmDir = 
			(DefaultTableColumnModel) tbDir.getColumnModel();
		//���oTableColumnModel����

		TableColumn colTitle = dtcmDir.getColumn(0);
		//���o�����1�檺TableColumn����

		colTitle.setPreferredWidth(220); //�]�w��쪺�ߦn�e�׬�220

		TableColumn colDate = dtcmDir.getColumn(3);
		colDate.setPreferredWidth(150); //�]�w��쪺�ߦn�e�׬�150

		//�Hfor�j��]�w�U�����D�ϥΪ���Ķ������
		for(int i=0; i < dtcmDir.getColumnCount(); i++){
			TableColumn tc = dtcmDir.getColumn(i);
			//���o�N����U��쪺TableColumn����

			tc.setHeaderRenderer(createHeaderCellRenderer());
			//�I�screateHeaderCellRenderer()��k
			//���ͼ��D�ϥΪ���Ķ������
		}

		JTableHeader header = tbDir.getTableHeader();
		//���oJTable����ϥΪ����D�C

		//���U�^�����D�CMouseEvent�ƥ󪺺�ť������
		header.addMouseListener(new MouseAdapter(){

			//�^���ƹ����U�ʧ@
			public void mouseClicked(MouseEvent e){

				TableColumnModel  tcmDir = tbDir.getColumnModel();
				//���oJTable����TableColumnModel����

				int viewIndex = 
							tcmDir.getColumnIndexAtX(e.getX());
				//�N�ƹ���Ф� x �y���ഫ��JTable���󪺵e�������ޭ�

				TableColumn col = 
							tcmDir.getColumn(viewIndex);
				//�HJTable��������ޭȨ��o���檺TableColumn����

				int modelIndex = col.getModelIndex();
				//���oTableColumn����bTableModel���󤺪����ޭ�

				if(modelIndex < 0) //�Y���ޭȤp��0, �h�פ��k������
					return;

				boolean asc = false;
				//�w�]�Ȭ�false, �w�]�H����覡�Ƨ�

				//�P�_�ƹ��I�������ƧǪ����
				//�O�_�쥻�N���b�Ƨ�
				//�O�h�ܧ������ƧǤ覡
				if(ftmDir.getSortColumn() == modelIndex){
					asc = !ftmDir.isSortAscend();
					//�ܧ�ƧǤ覡, 
					//����O����h�אּ���W, ���W�h�令����
				}

				ftmDir.sortData(modelIndex, asc);
				//������w���Ҧ����ު��Ƨ�
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
					//�]�wFileTableModel����
					//�N��ܤW�@�h��Ƨ������ɮ�

					tbDir.updateUI(); //��s��檺�e��
				}
			}
		});

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

	//�إ߼��D�x�s�檺��Ķ��
	protected TableCellRenderer createHeaderCellRenderer(){

		//�H�ΦW���O���覡�w�q�~��DefaultTableCellRenderer���O
		//����Ķ�����O, �öǦ^����
		return new DefaultTableCellRenderer(){

			private final ImageIcon ICON_ASCEND = 
				new ImageIcon("icon/asc.jpg");
			//�w�q��ܩ������Y, �N����滼��ƧǪ��ϥ�

			private final ImageIcon ICON_DESCEND = 
				new ImageIcon("icon/dec.jpg");
			//�w�q��ܩ������Y, �N����滼��ƧǪ��ϥ�

			public Component getTableCellRendererComponent(
				JTable table, Object value, boolean isSelected, 
				boolean hasFocus, int row, int col){

				super.getTableCellRendererComponent(
					table, value, isSelected, hasFocus, row, col);
				//�I�s��¦���O��getTableCellRendererComponent()��k

				if(table != null){ //table�ѼƤ��i��null
					JTableHeader header = table.getTableHeader();
					//���o���D�C����

					if(header != null){ //�P�_���D�C���󤣥i��null
						setForeground(header.getForeground());
						setBackground(header.getBackground());
						//�]�w��Ķ������ǥX����
						//�ϥμ��D�C���󪺭I���C��P�e���C��

						setFont(header.getFont());
						//�]�w�ǥX����ϥμ��D�C���r��
					}

					TableColumn tc = tbDir.getColumnModel().getColumn(col);
					//�HJTable��������ޭȨ��o�N����檺TableColumn����

					int modelIndex = tc.getModelIndex();
					//���oTableColumn����bModel���󤺪����ޭ�

					//�]�w������ƧǪ������Y����ܹϥ�
					if(ftmDir.getSortColumn() != modelIndex){
						setIcon(null); //�]�w����ܹϥ�
					}
					else{
						setIcon((ftmDir.isSortAscend() 
									? ICON_ASCEND: ICON_DESCEND));
						//�̷ӱƧǤ覡�]�w��ܱƧǤ覡���ϥ�
						setHorizontalTextPosition(JLabel.LEFT);
						//�]�w�ϥܻP��r������覡
					}
				}

				setBorder(UIManager.getBorder("TableHeader.cellBorder"));
				//�]�w�ϥ�UIManager�w�]�����D�C�x�s��ؽu
				return this;
			}
		};		
	}

	public static void main(String args[]) {
		new SortColumnEX(); //�ŧi�����ج[����	 
	}
}

//�w�q�B�z�ɮ׸�ƪ�TableModel����
class FileTableModel extends AbstractTableModel {

	private File dir;
	private ArrayList<File> files = new ArrayList<File>();
	//�x�s��Ƨ����ɮת��}�C

	String[] colName = {"�W��", "�j�p", "�O�_���ɮ�", 
									 "�̫�ק���", "�iŪ��", "�i�ק�"};
	//�ŧi�x�s���W�٪��r��

	Class[] types = new Class[] { 
		String.class, Long.class, Boolean.class, 
		String.class, //�Ъ`�N! Date���O��Ƹg�L�榡�ƫ�, ���O��String
		Boolean.class, Boolean.class};
	//���t�XTableCellRenderer����, 
	//�����ŧi�x�s�ɮ׸�Ƥ����O��Class�}�C

	SimpleDateFormat sdf = 
		new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	//�w�q�̫�ק����ϥΪ�����榡

	private int sortCol = -1; //����ƧǪ����e������
	private boolean sortAsc = false; //�x�s�ƧǤ覡

	FileTableModel(){
		this(new File(".")); //�I�sFileTableModel(String path)�غc�l
	}

	FileTableModel(File path){
		setCurrentPath(path); //�]�w���˵�����Ƨ�
	}

	public int getColumnCount(){ return colName.length; }
	//���o��쪺�Ӽ�

	public int getRowCount(){ return files.size(); }
	//���o��ƪ��C��

	 public Class getColumnClass(int col) { return types[col]; }
	//���o�ɮ׬Y����Ƥ����O��Class����,
	//����k�N��TableCellRenderer����B�@�ϥ�

	//���o���w��B�C��m���x�s����
	public Object getValueAt(int row, int col){
		
		//�P�_�C���ޭȬO�_���T
		if(row < 0 || row >= getRowCount())
			return "";

		//�P�_����ޭȬO�_���T
		if(col < 0 || col >= getColumnCount())
			return "";

		File file = (File) files.get(row);
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

		//�Y�x�s�ɮת�ArrayList�e�����O�Ū�, �h�M��
		if(!files.isEmpty()){
			files.clear(); //�M��ArrayList�e��
			sortCol = -1; //���]�������ƧǪ��ݩ�
			sortAsc = false;
		}

		//�C�X��Ƨ������Ҧ��ɮ�, �å[�JArrayList�e��
		for(File elm : dir.listFiles())
			files.add(elm);

		fireTableChanged(new TableModelEvent(this));
		//Ĳ�o���o�ͧ��ܪ��ƥ�, �å�XTableModelEvent����
	}

	//���o�ثe�˵�����Ƨ������|
	public File getCurrentPath(){
		return dir;
	}

	//���o����ƧǪ����
	public int getSortColumn(){
		return sortCol;
	}

	//���o�O�_���滼�W�Ƨ�
	public boolean isSortAscend(){
		return sortAsc;
	}

	//�����ƱƧ�
	public void sortData(int col, boolean asc){
		sortCol = col;
		sortAsc = asc;

		Collections.sort(files, new FileComparator<File>(col, asc));
		//�HFileComparator�����������ɮ׸�ƪ��Ƨ�
	}
}

//�w�q�B�z�ɮצU�ظ�ƱƧǪ�������O
class FileComparator<T extends File> 
						implements Comparator<T> {
	private int col; //������Ƨ���쪺����
	private boolean asc; //���W�λ���

	public FileComparator(int col, boolean asc){ //�غc�l
		this.col = col;
		this.asc = asc;
	}

	public int compare(T one, T another){ //�����諸compare()��k

		int result = 0;

		switch(col){
		case 0 :
			result = one.getName().compareTo(another.getName());
			//����ɮצW��
			break;
		case 1 :
			result = new Long(one.length() 
							- another.length()).intValue();
			//����ɮת���
			break;
		case 2 :
			result = (one.isFile() ? -1 : 1);
			//���O�_���ɮ�
			break;
		case 3 :
			result = new Long(one.lastModified()
							- another.lastModified()).intValue();
			//���̫�ק���
			break;
		case 4 :
			result = (one.canRead() ? -1 : 1);
			//���O�_�iŪ��
			break;
		case 5 :
			result = (one.canWrite() ? -1 : 1);
			//���O�_�i�g�J
			break;
		}

		if(asc)	//�P�_�O�_�����W, �O�h�Ǧ^���浲�G
			return result;

		return -result; //�Y�������, �h�Ǧ^�ۤϪ����浲�G
	}
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
		//�I�s��¦���O��getTreeCellRendererComponent()��k

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
	//�w�q�N���Ƨ��ϥܪ��R�A�`��

	private final static ImageIcon ICON_FILE = 
		new ImageIcon("icon/File.gif");
	//�w�q�N���ɮ׹ϥܪ��R�A�`��

	public Component getTableCellRendererComponent(
							JTable table, //�ϥ���Ķ�����󪺪�椸��
							Object value, //��Ķ�����B�z������x�s�檺��
							boolean isSelected, //�Q�B�z�x�s��O�_�Q���
							boolean hasFocus, //�Q����x�s��O�_�֦��J�I
							int row, //�Q����x�s��Ҧb��m���C��
							int column){ //�Q����x�s��Ҧb��m�����

		super.getTableCellRendererComponent(
			table, value, isSelected, hasFocus, row, column);
		//�I�s��¦���O��getTreeCellRendererComponent()��k

		if(column ==  0){
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