import javax.media.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

//�ŧi�~��JFrame���OMP3���񾹪������ج[�{��
class MP3Player extends JFrame {

	String[][] strFilter = 
		{{"mp3", "MPEG-3 ������ (*.mp3)"},
		 {"swf", "Macromedia Flash (*.swf)"},
		 {"wav", "Windows Waveform ������ (*.wav)"},
		 {"mid", "Musical Instrument Digital Interface  (*.mid)"}};
	//�]�w�z���ɮת����ɦW�P�y�z�r��

	PlayList ltList = null; //��ܼ���M�檺�M����
	SongList ltDet = null; //��ܺq���M�檺�M����

	public MP3Player(){

		JPanel controlPane = new JPanel(); //�e�Ǽ��񾹱�����e��
		JToolBar tbControl = new JToolBar("����"); //�ŧi�u��C

		Player player = null; //�ŧi���񾹪���null

		JRadioButton 
			rbAll =  new JRadioButton("�����ӲM��", true),
			rbOne =  new JRadioButton("���Ƽ���@��"),
			rbAuto =  new JRadioButton("���Ƽ����ӲM��");
		//�ŧi�]�w�M�漽��覡����ܶs

		Manager.setHint(
			Manager.LIGHTWEIGHT_RENDERER, 
			new Boolean(true));
		//�]�w���񾹱���ϥΪ��~�[

		Container cp = getContentPane(); //���o���e����

		rbAll.addActionListener(alPlay);
		rbOne.addActionListener(alPlay);
		rbAuto.addActionListener(alPlay);
		//���U��ťActionEvent�ƥ󪺺�ť������

		ButtonGroup bgRepeat = new ButtonGroup();
		//�ŧi���s�s��

		bgRepeat.add(rbAll);
		bgRepeat.add(rbAuto);
		bgRepeat.add(rbOne);
		//�N��ܶs�[�J���s�s��

		tbControl.add(rbAll);
		tbControl.add(rbAuto);
		tbControl.add(rbOne);
		tbControl.add(controlPane);
		//�N��ܶs�[�J�u��C

		cp.add(tbControl, BorderLayout.NORTH);
		//�N�u��C�[�J���e����

		ltList = new PlayList(); //��ܼ���M�檺�M����
		ltList.setFixedCellWidth(150); //�]�w�M�������T�w�e��
		ltList.setSelectionMode(
			ListSelectionModel.SINGLE_SELECTION);
		//�]�w�M����������Ҧ������

		//��ť��ܼ���M��M����ListSelectionEvent�ƥ󪺺�ť������
		ltList.addListSelectionListener(lslList);

		ltDet = new SongList(controlPane);
		//�ŧi��ܼ���q�����M����

		//���U�^��MouseEvent�ƥ󪺺�ť������
		ltDet.addMouseListener(new MouseAdapter(){

			//�^���ƹ����U�ƥ�
			public void mouseClicked(MouseEvent e) {

				if(e.getClickCount() != 2) return;
				//�P�_�ƹ����U���ƬO�_������2

				SongList sl = (SongList) e.getSource();
				//���o�q������M��

				int selIndex = sl.getSelectedIndex();
				//���o������ت����ޭ�

				if(selIndex == -1) return;
				//�Y���������ﶵ�h�פ�����k

				sl.setCurrentPlay(selIndex);
				//�]�w�ثe���񪺺q��

				sl.play(); //����q��
			}
		});	

		JMenu mnFile = new JMenu("�ɮ�(F)"), 
					mnEdit = new JMenu("�s��M��(E)"),
					mnList = new JMenu("����M��(L)");
		//�ŧi�\���

		JMenuItem
			miExit = new JMenuItem("����(E)", KeyEvent.VK_E),
			miNew = new JMenuItem("�s�W(N)", KeyEvent.VK_N),
			miDel = new JMenuItem("�R��(D)", KeyEvent.VK_D),
			miMod = new JMenuItem("�ק�(M)", KeyEvent.VK_M),
			miNewItem  = new JMenuItem("�s�W�q��(N)", KeyEvent.VK_N),
			miDelItem  = new JMenuItem("�R���q��(D)", KeyEvent.VK_D);
		//�ŧi�\���ﶵ

		mnFile.add(miExit); //�s�W�\���ﶵ

		mnList.add(miNew);
		mnList.add(miMod);
		mnList.add(miDel);

		mnEdit.add(miNewItem);
		mnEdit.add(miDelItem);

		JMenuBar jmb = new JMenuBar();
		jmb.add(mnFile);
		jmb.add(mnList);
		jmb.add(mnEdit);
		//�إߵ����ج[���\���C

		miExit.addActionListener(alExit);
		miNew.addActionListener(alNew);
		miDel.addActionListener(alDel);
		miMod.addActionListener(alMod);
		miNewItem.addActionListener(alNewItem);
		miDelItem.addActionListener(alDelItem);
		//���U��ťActionEvent�ƥ󪺺�ť������

		addWindowListener(waWindow);
		//���U��ťWindowEvent�ƥ󪺺�ť������

		setJMenuBar(jmb);	 //�]�w�����ج[���\���C

		cp.add(new JScrollPane(ltList), BorderLayout.WEST);
		cp.add(new JScrollPane(ltDet));
		//�N�M�����[�J���e����

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 600);
		setVisible(true);
	}

	//�ŧi��ťWindowEvent�ƥ󪺺�ť������
	WindowAdapter waWindow = new WindowAdapter(){

		//�^�������}�Ұʧ@����k
		public void windowOpened(WindowEvent e){
			ltList.load("PlayList/main.dat");
			//�q�ɮ׸��J�M�������ﶵ���e

			ltList.setSelectedIndex(0);
			//�]�w����Ĥ@�Ӷ���
		}

		//�^�������}�Ұʧ@����k
		public void windowClosing(WindowEvent e){
			ltList.save("PlayList/main.dat");
			//�x�s�M�������ﶵ���e

			System.exit(0); //��������t��
		}
	};

	//���U�^�������ﶵ��ActionEvent�ƥ󪺺�ť������
	ActionListener alExit = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			System.exit(0); //�����t�ΰ���
		}
	};

	//�s�W����M��
	ActionListener alNew = new ActionListener(){

		//�^���ʧ@����k
		public void actionPerformed(ActionEvent e){

			String listName = (String)JOptionPane.showInputDialog(
				MP3Player.this, "�п�J����M��W��", "��ƿ�J",
				JOptionPane.QUESTION_MESSAGE, null, null, null);
			//��ܿ�J����M�����W�٪���ܲ�

			if("".equals(listName)){
				JOptionPane.showMessageDialog(
					MP3Player.this, "���i��J�ťզr��", "���~",
					JOptionPane.ERROR_MESSAGE);
				//��ܰT����ܲ�

				return;
			}

			DefaultListModel dlmList = 
				(DefaultListModel) ltList.getModel();
			//�ŧi�x�s����M�椺�e��Model����

			//�P�_��J������M��W�٬O�_�w�s�b��M������
			if(dlmList.contains(listName)){
				JOptionPane.showMessageDialog(
					MP3Player.this, "�w�s�b�P�W���M��", "���~",
					JOptionPane.ERROR_MESSAGE);
				//��ܰT����ܲ�
				return;
			}

			int curIndex = ltList.getSelectedIndex();
			//���o�M����������ت����ޭ�

			dlmList.add(
				(curIndex==0 ? curIndex=1: curIndex), listName);
			//�N�s�W����M�檺�W�٥[�J�M����

			ltList.setSelectedIndex(curIndex); //�]�w�������
		}
	};

	//�R������M��
	ActionListener alDel = new ActionListener(){

		//�^���ʧ@����k
		public void actionPerformed(ActionEvent e){

			DefaultListModel dlmList = 
				(DefaultListModel) ltList.getModel();
			//���o�x�s�M�������ﶵ���e��Model����

			File fileDel = 
				new File("PlayList/" + 
				(String)ltList.getSelectedValue() + ".dat");
			//�ŧi�N����R���ɮת�File����

			int curIndex = ltList.getSelectedIndex();
			//���o�M��������ﶵ�����ޭ�

			if(curIndex == 0){
				JOptionPane.showMessageDialog(MP3Player.this, 
					"���i�R��'�̳߷R���q��'�M��", "���~",
					JOptionPane.ERROR_MESSAGE);
				return;
			}

			fileDel.delete(); //�R���ɮ�

			dlmList.remove(curIndex);
			//�����M�����ثe���������M��

			ltList.setSelectedIndex(
				(curIndex<dlmList.getSize() ? curIndex : curIndex-1));
			//�]�w�s���������
		}
	};

	//�^���ק］��M��W�٪���ť������
	ActionListener alMod = new ActionListener(){

		//�^���ʧ@����k
		public void actionPerformed(ActionEvent e){

			int index = ltList.getSelectedIndex();
			//���o������ت����ޭ�

			if(index == -1){
				JOptionPane.showMessageDialog(
					MP3Player.this, "�п�����ק�W�٪��M�� !");
				//��ܰT������ܲ�
				return;
			}
	
			String listName = (String)JOptionPane.showInputDialog(
				MP3Player.this, "�Эק］��M��W��", "��ƭק�",
				JOptionPane.QUESTION_MESSAGE, null, null,
				(String) ltList.getSelectedValue());
			//��ܿ�J�M�����W�٪���ܲ�

			if("".equals(listName)){ //�P�_�O�_��J�ťզr��
				JOptionPane.showMessageDialog(
					MP3Player.this, "���i��J�ťզr��", 
					"���~", JOptionPane.ERROR_MESSAGE);
				//��ܿ��~�T��

				return;
			}

			DefaultListModel dlmList = 
				(DefaultListModel) ltList.getModel();
			//���o�x�s��ܼ���M��C���M������Model����

			//�P�_��J������M��W�٬O�_�s�b��Model����
			if(dlmList.contains(listName)){

				JOptionPane.showMessageDialog(
					MP3Player.this, "�w�s�b�P�W���M��", "���~", 
					JOptionPane.ERROR_MESSAGE);
				//��ܿ��~�T��

				return;
			}

			dlmList.setElementAt(listName, index);
			//�]�w�M����������ﶵ
		}
	};

	//�N�q���s�W�ܼ���M��
	ActionListener alNewItem = new ActionListener(){

		//�^���ʧ@����k
		public void actionPerformed(ActionEvent e){
			
			MediaFileChooser mcObj = new MediaFileChooser(
				MP3Player.class.getResource("/").getPath() + "mp3",
				strFilter);
			//�ŧi����h�C���ɮ׿�ܾ�

			mcObj.setMultiSelectionEnabled(true);
			//�]�w���\�h�����

			mcObj.setDialogTitle("�}������");
			//�]�w�ɮ׿�ܾ���ܲ������D�W��

			int result = mcObj.showOpenDialog(MP3Player.this);
			//���o�}�����ɹ�ܲ����U���s���`��

			//�P�_�O�_�����U �T�w ���s
			if(result == MediaFileChooser.APPROVE_OPTION){

				DefaultListModel dlmDet = 
					(DefaultListModel) ltDet.getModel();
				//���o�q���M�椧�M������Model����

				File[] selFiles = mcObj.getSelectedFiles();
				//�ɮ׹�ܲ�������Ҧ��ɮ�

				for (File elm : selFiles) dlmDet.addElement(elm);
				//�N����ɮץ[�J�q������M������Model����

				ltDet.save(
					"PlayList/" + ((String)ltList.getSelectedValue()) + ".dat");
				//�x�s�q������M���������e
			}
		}	
	};

	//�R���M�������q������
	ActionListener alDelItem = new ActionListener(){

		//�^���ʧ@����k
		public void actionPerformed(ActionEvent e){

			DefaultListModel dlmDet =
				(DefaultListModel) ltDet.getModel();
			//���o�x�s����M�椺�e��Model����

			int[] selIndex = ltDet.getSelectedIndices();
			//���o�Q����ﶵ�����ޭ�

			if(selIndex == null | selIndex.length == 0) return;

			int newIndex = 
				selIndex[selIndex.length-1] - selIndex.length + 1;
			//���o�̫�@�ӿ���ﶵ�����ޭ�

			//��������M�椺���������
			for(int i=(selIndex.length-1); i>=0; i--){
				dlmDet.remove(selIndex[i]);
			}

			int size = dlmDet.getSize(); //���o�M��ﶵ���Ӽ�

			ltDet.setSelectedIndex(
				(newIndex<size? newIndex: size-1));
			//�]�w�s���������

			ltDet.save("PlayList/" + 
				((String)ltList.getSelectedValue()) + ".dat");
			//�x�s�M�������e
		}
	};

	//�ŧi��ť�M��������ƥ󪺺�ť������
	ListSelectionListener lslList = new ListSelectionListener(){

		//�^���ﶵ�o���ܤƪ���k
		public void valueChanged(ListSelectionEvent e){

			if(e.getValueIsAdjusting()) return;
			//�P�_�O�_���s��ƥ�, �O�h�פ�����k

			PlayList pl = (PlayList) e.getSource();
			//���o�޵o�ƥ󤧨ӷ�����

			ltDet.load("PlayList/" + pl.getSelectedValue() + ".dat");
			//���J�M�����Q���������M�椧���e
		}
	};

	//�ŧi��ť�]�w����覡����ܶsActionEvent�ƥ󪺺�ť������
	ActionListener alPlay = new ActionListener(){

		//�^���ƥ󪺤�k
		public void actionPerformed(ActionEvent e){

			String command = e.getActionCommand();
			//���o�ʧ@�R�O�r��

			//�̷Ӱʧ@�R�O�r��]�w����覡
			if("�����ӲM��".equals(command))
				ltDet.setPlayStatus(SongList.ALL); //�]�w�����ӲM��
			else if("���Ƽ���@��".equals(command))
				ltDet.setPlayStatus(SongList.ONE); //�]�w����@���q
			else if("���Ƽ����ӲM��".equals(command))
				ltDet.setPlayStatus(SongList.AUTO);
				//�]�w���Ƽ����ӲM��
		}
	};

	public static void main(String[] args) {
		new MP3Player(); //�ŧi�����ج[
	}
}