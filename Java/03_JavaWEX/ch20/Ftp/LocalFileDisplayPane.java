import com.BitC.Controls.*;

import java.io.*;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

//�w�q��ܫȤ���ɮת�����
class LocalFileDisplayPane extends JPanel {

	private JComboBox cmbDevice; //�ŧi����ϺЪ��զX���
	private JLabel lbPath; //�ŧi��ܫȤ�ݥثe���|������
	private FileList flFileList; //�ŧi����ɮצC���M����
	private JButton btnUp = new JButton("�W�@�h");
	//���ܥثe���|���W�@�h��Ƨ�

	private File defPath; //�w�]�s�������|

	//�غc�l
	public LocalFileDisplayPane(String systemName, File defPath) {
		
		this.defPath = defPath; //�]�w�w�]�s�������|

		JPanel jpPath = new JPanel(new BorderLayout(10, 10));
		jpPath.add(new JLabel("���| : "), BorderLayout.WEST);

		jpPath.add(getPathLabel()); //�N���|���ҥ[�J�e��
		lbPath.setBorder(LineBorder.createGrayLineBorder());
		jpPath.add(btnUp, BorderLayout.EAST);

		btnUp.addActionListener(alUp);
		//���U��ť�W�ǫ��sActionEvent�ƥ󪺺�ť������

		JPanel jpDevice = new JPanel(new BorderLayout(10, 10));
		jpDevice.add(new JLabel("�Ϻ� : "), BorderLayout.WEST);
		jpDevice.add(getDeviceComboBox());
		//�N����ϺЪ��զX����[�J�e��

		JPanel jpComboBox = new JPanel(new BorderLayout(10, 10));
		jpComboBox.add(jpPath);
		jpComboBox.add(jpDevice, BorderLayout.SOUTH);
		//�إ߮e�ǿ���P��ܺϺи��|���e��

		setLayout(new BorderLayout(10, 10));
		//�]�w�����޲z����

		JScrollPane spFileList = new JScrollPane(getFileList());
		 spFileList.setPreferredSize(new Dimension(150, 50));

		add(spFileList);
		add(jpComboBox,  BorderLayout.NORTH);

		Border border = BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(Color.gray, 2),
			systemName, TitledBorder.LEFT, TitledBorder.TOP);
		//�ŧi���D�ؽu

		setBorder(border); //�]�w�ϥμ��D�ؽu
	}

	//���o�ѨϥΪ̿���ϺЪ��զX���
	public JComboBox getDeviceComboBox(){
		
		if(cmbDevice == null){

			cmbDevice = new JComboBox();
			//�ŧi�զX���

			cmbDevice.setModel(
				new DefaultComboBoxModel(File.listRoots()));
			//�]�w�զX����M��ϥΪ�Model����

			cmbDevice.setSelectedItem(defPath);
			//�]�w������w�]���|

			cmbDevice.addItemListener(ilDevice);
			//���U��ťItemEvent�ƥ󪺺�ť������
		}

		return cmbDevice; //�ǥX�զX���
	}

	//���o���|����
	public JLabel getPathLabel(){

		if(lbPath == null){  //�P�_���|���ҬO�_��null
			lbPath = new JLabel(); //�ŧi����
			lbPath.setText(defPath.getPath());
			//�]�w������ܹw�]���|���r��
		}

		return lbPath; //�ǥX�w�]���|
	}

	public FileList getFileList(){ //���o����ɮצC���M����

		if(flFileList == null){
			flFileList = new FileList();
			//�ŧi����ɮצC���ɮײM����

			flFileList.setPath(defPath);
			//�]�w�ɮײM�������C�X�ɮת����|

			flFileList.setCellRenderer(new DirListCellRenderer());
			//�]�w�ɮײM�������ﶵ����Ķ��

			flFileList.setLayoutOrientation(JList.VERTICAL);
			//�]�w�ɮײM�������t�m�覡

			flFileList.setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
			//�]�w�ɮײM����������Ҧ����i���s�򪺽d����

			flFileList.addMouseListener(maFileList);
			//���U��ťMouseEvent�ƥ󪺺�ť������
		}

		return flFileList;
	}

	public void update(){ //��s�������e��
		flFileList.setPath(flFileList.getCurrentPath());
		//�]�w�ɮײM�����ثe�C�X�ɮת���Ƨ����|
	}

	//�w�q�ŧi��ťActionEvent�ƥ󪺺�ť������
	ActionListener alUp = new ActionListener(){

		//�^��ActionEvent�ƥ󪺤�k
		public void actionPerformed(ActionEvent e){

			File curPath = flFileList.getCurrentPath();
			//���o�ɮײM�����ثe�����|

			if(curPath == null) return;
			//�P�_���|�O�_��null, �O�h�פ�����k

			String parent = curPath.getParent();
			//���o�ثe���|���W�@�h���|

			if(parent == null)	return;
			//�Y�W�@�h���|��null, �h�פ�����k
				
			lbPath.setText(parent);
			//�]�w���|������ܤW�@�h���|

			flFileList.setPath(new File(parent));
			//�]�w�ɮײM������ܤW�@�h���|�����ɮ�
		}
	};

	//�^���զX�������ﶵ�޵o��ItemEvent�ƥ󪺺�ť������
	ItemListener ilDevice = new ItemListener(){

		File file = null;

		//�^���զX������ت��A������
		public void itemStateChanged(ItemEvent e){

			//�P�_���A�O�_���ܬ����
			if(e.getStateChange() == ItemEvent.SELECTED){

				file = (File) e.getItem();
				//���o������ت��x�s��, ���૬��File����

				lbPath.setText(file.getPath());
				//�]�w������ܿ�����ؤ��x�s�Ȫ��ɮ׸��|

				FileListLoader flLoader = new FileListLoader(file);
				//�ŧi�]�w�M������ܸ��|�������

				flLoader.start();  //�Ұʰ����
			}
		}
	};

	//��ť����ɮצC��MouseEvent�ƥ󪺺�ť������
	MouseAdapter maFileList = new MouseAdapter(){

		//�^���ƹ��I��ʧ@
		public void mouseClicked(MouseEvent e){

			//�P�_�ƹ����U���ƬO�_��2��
			if(e.getClickCount() == 2){
				FileList fl = (FileList) e.getSource();
				//���o�޵o�ƥ󪺤���

				File file = (File)fl.getCurrentPath();
				//���o�ɮײM�����ثe�����|
			
				if(file.isDirectory()) //�P�_�ثe���|�O�_����Ƨ�
					lbPath.setText(file.getPath());
					//�]�w������ܥثe�����|
			}
		}
	};

	//�~��Thread���O����M�����˵����|���]�w
	class FileListLoader extends Thread {

		File path; //�ŧi���U���ɮת����|

		FileListLoader(File path){ this.path = path; }
		//�غc�l

		public void run() {
			if(path != null)	flFileList.setPath(path);
			//�]�w�ɮײM������ܪ����w���|�����ɮצC��
		}
	}
}