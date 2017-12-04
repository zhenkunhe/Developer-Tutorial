import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

import cz.dhl.io.*;
import cz.dhl.ftp.*;

//�w�q��ܦ��A���ɮת�����
public class RemoteFileDisplayPane extends JPanel {

	private static String UNCONNECT = "�|���s�u"; //��ܩ|���s�u���T��
	private Ftp cl; //�B�z�s�u��FTP����
	private JLabel lbPath; //��ܳs�u���|������
	private RemoteFileList rflFileList; //���A���ɮצC���M����
	private JButton btnUp = new JButton("�W�@�h");
	//���ܤW�@�h��Ƨ������s

	public RemoteFileDisplayPane(String systemName){ //�غc�l
		
		JPanel jpComboBox = new JPanel();
		//�e�ǲզX���������

		jpComboBox.setLayout(new BorderLayout(10, 10));
		//�]�w�ϥΪ������޲z��

		jpComboBox.add(new JLabel("���| : "), BorderLayout.WEST);
		jpComboBox.add(getPathLabel()); //���o��ܸ��|������
		jpComboBox.add(btnUp, BorderLayout.EAST);		
		//�N����[�J����

		lbPath.setBorder(LineBorder.createGrayLineBorder());
		//�]�w���|���ҨϥΪ��ؽu

		setLayout(new BorderLayout(10, 10));

		add(jpComboBox,  BorderLayout.NORTH);
		//�N��m�զX�����Box�e���[�J����

		JScrollPane spFileList = new JScrollPane(getRemoteFileList());	
		spFileList.setPreferredSize(new Dimension(150, 50));
		//�إ߱��b����, �ó]�w�A��j�p

		add(spFileList); //�[�J���b����

		Border border = BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(Color.gray, 2),
			systemName, TitledBorder.LEFT, TitledBorder.TOP);
		//�ŧi���Үؽu

		setBorder(border); //�]�w�����ϥμ��Үؽu

		btnUp.addActionListener(alUp);
		//���U��ť�W�@�h���sActionEvent�ƥ󪺺�ť������

		rflFileList.setCellRenderer(new DirListCellRenderer());
		//�]�w�C�X�ɮפ��M�����ϥΪ���Ķ������

		rflFileList.addMouseListener(maFileList);
		//���U��ťMouseEvent�ƥ󪺺�ť������
	}

	public void setFtpConnection(Ftp ftp){ cl = ftp; }
	//�]�w�B�zFTP�s�u������
	
	public JLabel getPathLabel(){ //���o��ܸ��|������
		if(lbPath == null){
			lbPath = new JLabel(); //�ŧi��ܸ��|������
		}

		return lbPath;
	}

	//���o��ܦ��A���ɮצC���M����
	public RemoteFileList getRemoteFileList(){

		if(rflFileList == null){
			rflFileList = new RemoteFileList();
			//�ŧi��ܦ��A���ɮצC���M����
		}

		return rflFileList;
	}

	public void update(){ rflFileList.update();}
	//��s��ܦ��A�ݪ��ɮײM��

	public void reset(){ //���]���A�ݭ�������ܤ��e
		lbPath.setText(null);
		rflFileList.clear();
	}

	//�ŧi��ť����W�ǰʧ@�����sActionEvent�ƥ󪺺�ť������
	private ActionListener alUp = new ActionListener(){

		public void actionPerformed(ActionEvent e){

			if(cl ==  null) return; //�Y���s�u�h�פ��k������

			FtpFile curPath  = rflFileList.getCurrentPath();
			//���o���A�ݥثe�����|

			if(curPath == null) return; //�Y�L�k���o���|�h�פ�����k

			String parent = curPath.getParent(); //���o�W�@�h��Ƨ�

			if(parent == null)	return;
			//�Y�L�k���o�W�@�h��Ƨ��h�פ�����k
			
			lbPath.setText(parent); //�]�w��ܤW�@�h���|

			rflFileList.setPath(new FtpFile(parent, cl));
			//�]�w��ܦ��A���ɮײM�椧�M���������|
		}
	};

	//�ŧi�^���M������MouseEvent�ƥ󪺺�ť������
	private MouseAdapter maFileList = new MouseAdapter(){

		public void mouseClicked(MouseEvent e){ //�^���ƹ����U�ƥ�

			if(e.getClickCount() == 2){ //�P�_�ƹ����U���ƬO�_��2��

				RemoteFileList fl = (RemoteFileList) e.getSource();
				//���o�޵o�ƥ󪺲M����

				FtpFile file = (FtpFile)fl.getSelectedValue();
				//���o�M�������������

				if(file.isDirectory()){ //�P�_���o�����جO�_����Ƨ�
					lbPath.setText(file.toString());
					//�]�w������ܦ��A�ݪ����|

					fl.setPath(file); //�]�w��ܪ����|
				}
			}
		}
	};
}

//�w�q�~��JList���O��ܦ��A���ɮצC���M����
class RemoteFileList extends JList {

	private FtpFile curPath; 	//�x�s�ثe��ܪ����|

	public RemoteFileList(){ initialize(); } //�غc�l

	public void clear(){
		setModel(new DefaultListModel());
		//�M���M������ܤ��e��Model����

		curPath = null; //�M���ثe����ɮצC�����|
	}

	private void initialize(){ //�_�l�M����
		setModel(new DefaultListModel());
		//�]�w�M������ܪ�Model����

		setSelectionMode(
			ListSelectionModel.SINGLE_INTERVAL_SELECTION );
		//�]�w�M����������Ҧ�
	}

	//�]�w�M������ܪ����|
	public void setPath(FtpFile directory){

		curPath = directory; //�]�w���A�ݱ��s�������|

		CoFile[] files = directory.listCoFiles();
		//�C�X���A�ݪ��ɮ�

		if(files !=  null){ //�P�_�O�_���o�ɮ�
			setListData(files); //�]�w�M��������ܪ��ﶵ
		}
		else{
			setModel(new DefaultListModel());
			//�M���M�������e
		}
	}
	
	public FtpFile getCurrentPath(){ return curPath; }
	//���o�ثe���A�ݪ����|

	public void update(){ setPath(curPath);}
	//��s�ثe��ܤ����A�ݸ��|
}