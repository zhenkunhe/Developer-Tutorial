import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*; //�ޥδ���File���O���M��

public class RendererEX extends JFrame {

	JList ltFile = new JList(); //�ŧi�M����

	DefaultListModel dlmFile = new DefaultListModel(),
								 dlmDir = new DefaultListModel(),
								 dlmAll = new DefaultListModel();
	//�ŧi�]�t�M�����ﶵ���e��Model����

	//�H�ΦW���O���覡�w�q�ëŧi��ť������
	ActionListener alOrientation = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			//�B�ΰʧ@�R�O�r��P�_���ϥΪ�Model����
			if(e.getActionCommand().equals("�ɮ�")){
				ltFile.setModel(dlmFile);
				//�]�w�ϥΥ]�t�ɮצW�٪�Model����
			}
			else if(e.getActionCommand().equals("��Ƨ�")){
				ltFile.setModel(dlmDir);
				//�]�w�ϥΥ]�t��Ƨ��W�٪�Model����
			}
			else{
				ltFile.setModel(dlmAll);
				//�]�w�ϥΥ]�t�ɮ׻P��Ƨ��W�٪�Model����
			}
		}
	};

	//�H�ΦW���O���覡�w�q�ëŧi��ť������
	ActionListener alRenderer = new ActionListener(){
		public void actionPerformed(ActionEvent e){

			//�B�ΰʧ@�R�O�r��P�_���ϥΪ���Ķ������
			if(e.getActionCommand().equals("��ܤ�r")){
				ltFile.setCellRenderer(new FileDirTextCellRenderer());
				//�]�w�ϥ���ܤ�r����Ķ������
			}
			else{
				ltFile.setCellRenderer(new FileDirIconCellRenderer());
				//�]�w�ϥΦP����ܹϥܻP��r����Ķ������
			}
		}
	};

	RendererEX(){
		File flCur = new File("C:\\"); //���C�X�ɮת����|
		File[] files = flCur.listFiles();
		//���o���|�U�Ҧ��ɮת�File����

		//�B�Υ[�j��for�j��إߦU��Model����
		for(File elm : files){
			dlmAll.addElement(elm);
			//�[�J�N���ɮשθ�Ƨ���File����

			if(elm.isDirectory()){
				dlmDir.addElement(elm);
				//�[�J�N���Ƨ���File����
			}
			else{
				dlmFile.addElement(elm);
				//�[�J�N���ɮת�File����
			}
		}

		ltFile.setModel(dlmFile); //�]�w�M��������ܤ��e
		ltFile.setLayoutOrientation(JList.VERTICAL_WRAP);
		//�]�w�ﶵ���]�w�覡

		ltFile.setVisibleRowCount(5); //�]�w�i���C�Ƭ�5�C
		ltFile.setSelectionForeground(Color.darkGray);
		ltFile.setSelectionBackground(Color.lightGray);

		JRadioButton rdFile = new JRadioButton("����ɮ�", true),
								rdDir = new JRadioButton("��ܸ�Ƨ�"),
								rdAll = new JRadioButton("�������");
		//�ŧi����M�������ﶵ�ƦC�覡����ܫ��s

		rdFile.setActionCommand("�ɮ�");
		rdDir.setActionCommand("��Ƨ�");
		rdAll.setActionCommand("����");
		//�]�w����M�������e���ʧ@�R�O�r��

		rdFile.addActionListener(alOrientation);
		rdDir.addActionListener(alOrientation);
		rdAll.addActionListener(alOrientation);
		//���U�^��ActionEvent�ƥ󪺺�ť������

		ButtonGroup bgOrientation = new ButtonGroup(); //�ŧi���s�s��
		bgOrientation.add(rdFile); //�N��ܫ��s�[�J���s�s��
		bgOrientation.add(rdDir);
		bgOrientation.add(rdAll);

		JToggleButton tbText = new JToggleButton("��ܤ�r", true),
				  tbIconText = new JToggleButton("��ܤ�r�P�ϥ�");

		ltFile.setCellRenderer(new FileDirTextCellRenderer());
		//�]�w�w�]��������Ķ������

		tbText.addActionListener(alRenderer);
		tbIconText.addActionListener(alRenderer);
		//���U��ťActionEvent�ƥ󪺺�ť������

		ButtonGroup bgRenderer = new ButtonGroup(); //�ŧi���s�s��
		bgRenderer.add(tbText); //�N��ܫ��s�[�J���s�s��
		bgRenderer.add(tbIconText);

		JPanel jpRenderer = new JPanel(new GridLayout(2, 1, 5, 10));
		jpRenderer.add(tbText);
		jpRenderer.add(tbIconText);

		JScrollPane spList = new JScrollPane(ltFile);
		//�H�M�����ŧi���b����

		JPanel jpLayout = new JPanel(
			new FlowLayout(FlowLayout.CENTER, 10, 5));
		jpLayout.add(rdFile); //�N��ܫ��s�[�J����
		jpLayout.add(rdDir);
		jpLayout.add(rdAll);
		
		Container cp = getContentPane(); //���o���e����
		cp.add(spList); //�N����[�J���e����
		cp.add(jpLayout,BorderLayout.NORTH);
		cp.add(jpRenderer, BorderLayout.EAST);

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(570, 220);
		setVisible(true);
	}

	public static void main(String args[]) {
		new RendererEX(); //�ŧi�����ج[����
	}
}

//�H�~��JLabel���O�ù�@ListCellRenderer�������覡
//�w�q�B�z�M�����ﶵ��ܪ���Ķ�����O
class FileDirTextCellRenderer  extends JLabel
										implements ListCellRenderer {

	public FileDirTextCellRenderer(){
		setBorder(new EmptyBorder(1, 1, 1, 1));
		//�]�w�֦��|�g�e�׬�1���ťծؽu

		setOpaque(true); //�]�w���󬰤��z��, �I���C��~�|��{
	}

	//���o�M�����ﶵ��Ķ������
	public Component getListCellRendererComponent(
						JList list, //������ﶵ��Ķ�����M����
						Object value, //���o�ﶵ����
						int index, //������Ķ���ﶵ�����ޭ�
						boolean isSelected, //�P�_�ﶵ�O�_�Q���
						boolean cellHasFocus //�P�_�ﶵ�O�_���o�J�I
						){
		String name = ((File)value).getName();
		//���o�ﶵ����ñN���O�ഫ��File����, �A���o�ɮצW��

		setText(name); //�]�w������ܪ��r��

		setBackground(isSelected 
			? list.getSelectionBackground() : list.getBackground());
		//�]�w�ﶵ���Ҫ��I���C��

		setForeground(isSelected 
			? list.getSelectionForeground() : list.getForeground());
		//�]�w�ﶵ���Ҫ��e���C��

		setBorder(cellHasFocus 
			? new CompoundBorder(
					new LineBorder(Color.darkGray, 1), 
					new EmptyBorder(1, 1, 1, 1)) //�X���ؽu
			: new EmptyBorder(1, 1, 1, 1)); //�ťծؽu
		//�]�w���ҨϥΪ��ؽu

		return this;
	}
}

//�H�~��DefaultListCellRenderer���O���覡
//�w�q�B�z�M�����ﶵ��ܪ���Ķ�����O
class FileDirIconCellRenderer  
						extends DefaultListCellRenderer {

	private static ImageIcon ICON_FILE = 
								new ImageIcon("icon\\File.gif");
	private static ImageIcon ICON_FOLDER = 
								new ImageIcon("icon\\ClosedFolder.jpg");
	//�w�q�N��ϥܪ��`��

	public FileDirIconCellRenderer(){
		setBorder(new EmptyBorder(1, 1, 1, 1));
		//�]�w���ҨϥΪťծؽu
	}

	public Component getListCellRendererComponent(
										JList list,
										Object value,
										int index,
										boolean isSelected,
										boolean cellHasFocus){

		super.getListCellRendererComponent(
			list, value, index, isSelected, cellHasFocus);
		//�I�s��¦���O��getListCellRendererComponent()��k

		String name = ((File)value).getName();
		//���o�ﶵ����ñN���O�ഫ��File����, �A���o�ɮצW��

		setText(name); //�]�w������ܪ��r��

		setIcon(((File)value).isDirectory() ? ICON_FOLDER : ICON_FILE);
		//�P�_File����O�_�N���Ƨ�, �H�M�w�ϥΪ��ϥ�

		setBackground(isSelected 
			? list.getSelectionBackground() : list.getBackground());
		//�]�w�ﶵ���Ҫ��I���C��

		setForeground(isSelected 
			? list.getSelectionForeground() : list.getForeground());
		//�]�w�ﶵ���Ҫ��e���C��

		setBorder(cellHasFocus 
			? new CompoundBorder(
					new LineBorder(Color.darkGray, 1), 
					new EmptyBorder(1, 1, 1, 1)) //�X���ؽu
			: new EmptyBorder(1, 1, 1, 1)); //�ťծؽu
		//�]�w���ҨϥΪ��ؽu

		return this;
	}
}