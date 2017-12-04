import javax.media.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

import javax.swing.filechooser.FileFilter;

//�ŧi�~��JList���O��ܼ���M�檺����M����
class PlayList extends JList {

	protected DefaultListModel dlm = new DefaultListModel();
	//�ŧi�x�s�M�����ﶵ���e��Model����

	public PlayList(){	
		dlm.addElement("�̳߷R���q��");
		//�[�J�w�]������M��
	}

	//�x�s�M������Model����
	public void save(String fileName){
		try{
			FileOutputStream fos = new FileOutputStream(fileName);
			//�ŧi�x�sModel�����ɮצ�y

			ObjectOutputStream oos = new ObjectOutputStream(fos);
			//�ŧi��XModel���󪺪����y

			oos.writeObject(getModel());
			//�N�M������Model����g�J�����y

			oos.flush(); //�M���w�İ�
			oos.close(); //���������y
			fos.close(); //�����ɮצ�y
		}
		catch(Exception ex){
				ex.printStackTrace();
				System.err.println(
					"�s����}�����x�s���~ : " + ex.getMessage());
		}
	}

	public void load(String fileName){ //�q�ɮ׸��JModel����
		try{
			File flModel = new File(fileName);
			//�ŧi�x�sModel�����ɮ�

			if(!flModel.exists()){ //�P�_�ɮ׬O�_�s�b
				setModel(dlm); //�]�w�M�����ϥΪ�Model����
				return;
			}			

			FileInputStream fis = new FileInputStream(flModel);
			//�ŧi�ɮ׿�J��y

			ObjectInputStream ois = new ObjectInputStream(fis);
			//�ŧi�����J��y

			Object obj = ois.readObject(); //�q��yŪ�J����

			//�P�_���󫬧O�O�_��DefaultListModel
			if(obj instanceof DefaultListModel){
				setModel((DefaultListModel)obj);
				//�]�w�M�����x�s�ﶵ��Model����
			}
			ois.close(); //���������y
			fis.close(); //�����ɮצ�y
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.err.println(
				"�s����}����Ū�����~ : " +  ex.getMessage());
		}
	}
}

//�~��PlayList���O�w�q�x�s����q�����M����
class SongList extends PlayList {

	private int curPlay = -1; //�]�w�ثe����q�������ޭ�
	private Player player = null; //�]�w���񾹪���
	private JPanel jpControl = null; //�x�s���񾹱�����e��
	private Component controlPane = null; //�ŧi���񾹪������

	static final int ALL = 1,ONE = 2, AUTO = 3;
	//�]�w�N����覡���`��

	private int playStatus = SongList.ALL; //�]�w���񪬺A

	public SongList(JPanel jpControl){ //�غc�l

		dlm.clear();

		setCellRenderer(new SongListCellRenderer());
		//�]�w�M�����ϥΪ���Ķ������

		setSelectionMode(
			ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
		//�]�w�M����������Ҧ�

		this.jpControl = jpControl; //�]�w�e�Ǽ��񾹱��������
	}

	public void setPlayStatus(int status){ //�]�w�q���M�檺����覡
		playStatus = status;
	}

	public int getCurrentPlay(){ //���o�ثe����q�������ޭ�
		return curPlay;
	}

	//�]�w�ثe����q�������ޭ�
	public void setCurrentPlay(int index){
		curPlay = index;
	}

	public void play(){ //����mp3
		try{
			if(player != null){ //�P�_���񾹪���O�_��null
				jpControl.remove(controlPane); //���������o�����
				player.removeControllerListener(ca);
				//������ťControllerEvent�ƥ󪺺�ť������

				player.stop(); //�����
				player.close(); //��������
				player = null; //���]����Ѧ�
			}

			Object selValue = 
				((DefaultListModel)getModel()).elementAt(curPlay);
			//���o�ثe���񤧺q�����ޭ�

			player = Manager.createPlayer(
				new URL(new String("file:" + ((File)selValue).getPath())));
			//�إ߼��񾹪���
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}

		player.addControllerListener(ca);
		//���U��ťControllerEvent�ƥ󪺺�ť������

		player.realize(); //�N��������A�]�w����{
	}

	//���U��ť���񾹱������ť������
	ControllerAdapter ca = new ControllerAdapter(){

		Player player = null;

		//�^���w�������J����k
		public void realizeComplete(RealizeCompleteEvent e){
			player = (Player) e.getSource(); //���o�޵o�ƥ󪺼��񾹪���
			player.prefetch();
		}

		//�^�������ǳƸ��J�ʧ@����k
		public void prefetchComplete(PrefetchCompleteEvent e){

			player = (Player) e.getSource(); //���o�޵o�ƥ󪺼��񾹪���

			controlPane = player.getControlPanelComponent();
			//���o���񾹪��󪺱��

			controlPane.setSize(new Dimension(300, 40));
			//�]�w������j�p

			jpControl.add(controlPane);
			//�N���񾹱���[�J�e�Ǫ��e��

			validate(); //����
			updateUI(); //��s�e��

			player.start(); //�Ұʼ���
		}

		//�����C�鼽�񪺤�k
		public void endOfMedia(EndOfMediaEvent e){

			player = (Player) e.getSource();
			//���o�޵o�ƥ󪺼��񾹪���

			//�̷Ӽ��񪬺A���漽��ʧ@
			if(playStatus == SongList.ONE){ //���Ƽ����@�q��			
				player.setMediaTime(new Time(0));
				//�]�w�}�l���񪺦�m

				player.start(); //�}�l����
			}
			else{

				DefaultListModel dlm = (DefaultListModel) getModel();
				//���o�x�s�q���M������Model����

				int size = dlm.getSize(); //���o�M�椺�ﶵ���Ӽ�
				int index = getCurrentPlay();	//���o�ثe����q�������ޭ�
				
				if(playStatus == SongList.ALL){
				//�P�_�O�_����M�椺���Ҧ��q��

					if(index < (size-1)){
					//�P�_����q�������ެO�_�p��ﶵ�Ӽƴ�1
						setCurrentPlay(index + 1);
						//�]�w�U�@��������q��
					}
					else
						return;
				}
				else if(playStatus == SongList.AUTO){
				//�P�_�O�_���Ƽ���q���M�檺�Ҧ��q��

					if(index < (size-1)){
					//�P�_����q�������ެO�_�p��ﶵ�Ӽƴ�1

						setCurrentPlay(index + 1);
						//�]�w�U�@��������q��
					}
					else{
						setCurrentPlay(0);
						//�]�w����Ĥ@���q��
					}
				}

				play(); //����q��
			}
		}
	};
}


//�~��DefaultListCellRenderer���O
//�ŧi�B�z�q���M��ﶵ�~�[����Ķ������
class SongListCellRenderer extends DefaultListCellRenderer {

	//���o�ﶵ����Ķ������
	public Component getListCellRendererComponent(
					JList list,
					Object value,
					int index,
					boolean isSelected,
					boolean cellHasFocus) {

		super.getListCellRendererComponent(
			list, value, index, isSelected, cellHasFocus);
		//�I�s��¦���O����k, ���o��Ķ������

		//�Y������ȫh�פ�����k
		if(value == null) return this;

		String text = ((File)value).getName();
		//���o�ﶵ�x�s�����ɮצW��

		setText(text); //�]�w�ﶵ���W��

		//�P�_�ثe����q�����ޭ�
		//�O�_������o��Ķ�����ﶵ�����ޭ�
		if(((SongList)list).getCurrentPlay() == index)
			setIcon(new ImageIcon("image/play.gif"));
			//�]�w���بϥΪ��ϥ�

		text = text.toLowerCase();	 //�ഫ���p�g�^��r��

		setBackground(
			isSelected ? Color.lightGray : Color.white);
		//�̷ӿ�����A�]�w���ت��I���C��

		setForeground(
			isSelected ? Color.darkGray : Color.black);
		//�̷ӿ�����A�]�w���ت��e���C��

		if(isSelected)
			setBorder(
				BorderFactory.createLineBorder(Color.darkGray, 1));
			//�̷ӿ�����A�]�w�ﶵ���ؽu

		return this;
	}
}