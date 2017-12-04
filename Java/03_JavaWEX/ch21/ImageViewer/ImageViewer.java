import com.BitC.Controls.*;
//�ޤJ�ۭq�M��

import javax.swing.*;  //�ޥ�Swing�M��
import javax.swing.event.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

//�w�q�~��JFrame���O���Ϥ��s���������ج[
public class ImageViewer extends JFrame {

	private ImageCanvas pad; //��ܼv�����e��
	private int maxZoom = 100; //�̤j��j��v

	private JButton
		zoomIn = new JButton(new ZoomAction("��j")), //��j
		zoomOut = new JButton(new ZoomAction("�Y�p")), //�Y�p
		normal = new JButton(new ZoomAction("�A��j�p")), //���`
		fitWindow = new JButton(new ZoomAction("��l�j�p")),
		//�վ�ܵ����j�p
		center = new JButton(new ZoomAction("�m��")),
		//�վ�ܵ�������
		fitWidth = new JButton(new ZoomAction("�A��e��")),
		//�վ�ܵ����e��
		fitHeight = new JButton(new ZoomAction("�A����"));
		//�վ�ܵ�������

	File defaultPath = new File("C:\\"); //�𪬵��c���w�]�s�����|

	FolderTreeViewer ftv = new FolderTreeViewer(defaultPath);
	//��Ƨ��𪬵��c�˵���

	JComboBox cmbDevice = new JComboBox(File.listRoots());
	//����ɮ׸��|���զX���

	File flListPath = new File(
		ImageViewer.class.getResource("/").getPath() + "pic");
	//�ŧi�ɮײM�����w�]�s�������|

	//�ŧi�z���ɮצW�٪�����
	FilenameFilter nameFilter = new FilenameFilter(){

		//�P�_�ɮ׬O�_�Q����
		public boolean accept(File dir, String name){

			name = name.toLowerCase();
			//�N�ɮצW���ഫ���^��p�g

			//�P�_�ɮצW�٪����ɦW�O�_��gif��jpg
			if(name.endsWith(".gif") || name.endsWith(".jpg"))
				return true; //�Ǧ^true

			return false; //�Ǧ^false
		}
	};

	FileList fl = new FileList(flListPath, nameFilter);
	//�ŧi����ɮצC���M����

	ImageViewer() {

		addWindowListener(wa); //���U��ťWindowEvent�ƥ󪺺�ť������

		BorderLayout blObj = new BorderLayout(10,  10);
		setLayout(blObj); //�]�w�����t�m
			
		Container cp = getContentPane(); //���o���e����

		Dimension d = size(); //���o�����ج[���j�p
		pad = new ImageCanvas(d.width, d.height, maxZoom);
		//�إ���ܹ��ɪ��e��

		cp.add(pad); //�N�����[�J���e����
		
		JPanel jpButton = new JPanel(); //�ŧi�e�ǫ��O���s���e��

		jpButton.add(zoomIn);
		jpButton.add(zoomOut);
		jpButton.add(fitWindow);
		jpButton.add(normal);
		jpButton.add(center);
		jpButton.add(fitWidth);
		jpButton.add(fitHeight);
		//�إ߱�������s�������

		cp.add(jpButton, BorderLayout.NORTH);
		//�N�e���[�J���e����

		//���U��ťTreeSelectionEvent�ƥ󪺺�ť������
		ftv.addTreeSelectionListener(tslTree);

		Box bxFolderTree = new Box(BoxLayout.Y_AXIS);
		//�ŧi��m�𪬵��c��Box�e��

		bxFolderTree.add(cmbDevice); //�N����ϺЪ��զX���
		bxFolderTree.add(Box.createVerticalStrut(10));
		bxFolderTree.add(new JScrollPane(ftv));
		//�H�𪬵��c�إ߱��b�����å[�JBox�e��

		cmbDevice.setSelectedItem(defaultPath);
		//�]�w�զX�����������|

		cmbDevice.addItemListener(ilCMB);
		//���U��ťItemEvent�ƥ󪺺�ť������

		JPanel jpList = new JPanel(new BorderLayout());
		jpList.add(fl);
		//�N����ɮצC��[�J�M����

		fl.addListSelectionListener(lsl);
		//���U��ťListSelectionEvent�ƥ󪺺�ť������

		JPanel jpBrowser = 
			new JPanel(new GridLayout(1, 2, 10, 0), true);
		jpBrowser.add(bxFolderTree, BorderLayout.WEST);
		jpBrowser.add(new JScrollPane(jpList));
		//�N�M�����[�JJPanel�e��

		cp.add(jpBrowser, BorderLayout.SOUTH);
		//�N�s�����������[�J�����ج[�����e����

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����ϥμe�׬�5���ťծؽu

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);

		bxFolderTree.setPreferredSize(new Dimension(
			new Double(getPreferredSize().width).intValue(),
			new Double(getPreferredSize().height/3).intValue()));
		//�]�w�e�Ǹ�Ƨ��𪬵��c���Box�e�����j�p

		setVisible(true);
	}

	//�w�q�~��AbstractAction���O�վ�Ϥ��j�p��Action����
	class ZoomAction extends AbstractAction {
		
		ZoomAction(String name){ //�غc�l

			super(name);
			//�N�ʧ@Action����W�ٶǤJ��¦���O�غc�l

			putValue(Action.SHORT_DESCRIPTION, name);
			//�]�wAction����²�u�y�z��r
		}

		//�^��ActionEvent�ƥ󪺰ʧ@
		public void actionPerformed(ActionEvent e){

			String name = (String) getValue(Action.NAME);
			//���oAction����W�٪��]�w��

			//���Action����W�ٳ]�w��������r���Ҭ�����
			//�H�ά����ﶵ�����
			if(name.equals("��j"))
				pad.zoomIn();
			else if(name.equals("�Y�p"))
				pad.zoomOut();
			else if(name.equals("�A��j�p"))
				pad.fitWindow();
			else if(name.equals("��l�j�p"))
				pad.normal();
			else if(name.equals("�m��"))
				pad.imagecenter();
			else if(name.equals("�A��e��"))
				pad.fitWidth();
			else if(name.equals("�A����"))
				pad.fitHeight();
		}
	}

	//�ŧi��ťTreeSelectionEvent�ƥ󪺺�ť������
	TreeSelectionListener tslTree = new TreeSelectionListener(){

		//�^���𪬵��c������A���ܪ���k
		public void valueChanged(TreeSelectionEvent event){

			Object treenode = ftv.getTreeNode(event.getPath());
			//���o�𪬵��c���`�I

			File node = (File) treenode; //���A���c���`�I
			fl.setListFile(node, nameFilter); //�]�w�ɮײM��
		}

		//�^���i�}�`�I����k
		public void treeCollapsed(TreeExpansionEvent e)	{}
	};

	//�ŧi��ť�զX���ItemEvent�ƥ󪺺�ť������
	ItemListener ilCMB = new ItemListener(){

		File file = null;

		//�^�����ت��A���ܪ���k
		public void itemStateChanged(ItemEvent e){

			//�P�_���ܪ����A�O�_��������A
			if(e.getStateChange() == ItemEvent.SELECTED){

				file = (File)e.getItem();
				//���o������Ϻ�

				File[] files = file.listFiles();
				//�C�X�ӺϺФ����ɮ�

				//�P�_File����}�C�������ɮ�
				if(files != null && files.length !=0){

					//�ŧi�N�ɮ׵��c���J�𪬵��c�����������
					Thread runner = new Thread(){

						public void run() { //����������檺�ʧ@

							String node = "���J�ؿ���....";

							ftv.setModel(
								new DefaultTreeModel(
								new DefaultMutableTreeNode(node)));
							//�]�w�𪬵��c�|���������J,
							//��ܸ��J�T����Model����

							ftv.setModel(new FolderTreeModel(file));
							//�]�w�x�s�𪬵��c�`�I���e��Model����

							fl.setListFile(file, nameFilter);
							//�]�w�M������ܤ��ɮצC���M����
							//�P�ɮ׿z�磌��
						}
					};
					runner.start(); //�Ұʰ����
				}
				else
					((JComboBox)e.getSource()).setSelectedItem(defaultPath);
					//�]�w�զX������������
			}
		}
	};

	//�ŧi��ť�M����ListSelectionEvent�ƥ󪺺�ť������
	ListSelectionListener lsl = new ListSelectionListener(){

		File file = null; //�ŧiFile����

		//�^������M�������A���ܰʧ@
		public void valueChanged(ListSelectionEvent e){

			if(!e.getValueIsAdjusting()){ //�P�_����ȬO�_����

				JList source = (JList) e.getSource();
				//���o�޵o�ƥ󪺨ӷ�

				file = (File) source.getSelectedValue();
				//���o������ɮ�

				if(file != null){ //�P�_�O�_��null
					pad.setup(); //�]�w�e��
					pad.loadImage(file.getPath());
					//���J�Ϥ�

					pad.fitWindow(); //�վ�e�����A��j�p
				}
			}
		}
	};

	//�ŧi�^��WindowEvent�ƥ󪺺�ť������
	WindowAdapter wa = new WindowAdapter(){

		//�^�������}�Ұʧ@����k
		public void windowOpened(WindowEvent e){

			File selFile = new File(
				ImageViewer.class.getResource("/").getPath() + 
				"pic//people.jpg");
			//�ŧi�w�]����Ϥ���File����

			fl.setSelectedValue(selFile, true);
			//�]�w�ɮײM����������w��File����

			pad.setup(); //�]�w�e��
			pad.fitWindow(); //�N�Ϥ��վ㬰�����j�p
		}
	};

	public static void main(String args[]) {
		new ImageViewer(); //�إߵ����ج[����
	}
}