import javax.media.*; //�ޤJ�h�C��M��

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.filechooser.FileFilter;
//�ޥ��ɮ׿z�����O

//�w�q���񾹪��D�{��
class MPlayer extends JFrame {

	private Box bxScreen = null; //�ŧiBox�e��
	private double ratio = 1.0; //�]�w�e������v
	private Container cp = null; //�]�w�e������null
	private Component visualPane = null, controlPane = null;
	//�]�wComponent����null

	private Player player = null; //�ŧi���񾹪���

	private JCheckBoxMenuItem 
		cbmRepeat = new JCheckBoxMenuItem("���Ƽ���");
	//�ŧi����O�_���Ƽ�����T�ɪ��֨�����ﶵ

	public MPlayer(){ //�غc�l

		JMenu mnFile = new JMenu("�ɮ�(F)"),
					mnScreen = new JMenu("�e��(S)"),
					mnPlay = new JMenu("����(P)");
		//�ŧi�ɮ׻P�e���\���

		JMenuItem 
			miOpen = new JMenuItem("�}��(O)", KeyEvent.VK_O),
			miExit = new JMenuItem("����(E)", KeyEvent.VK_E);
		//�ŧi�}�һP�����ﶵ

		JCheckBoxMenuItem 
			cbmi100 = new JCheckBoxMenuItem(new ScreenSize("100%")),
			cbmi75 = new JCheckBoxMenuItem(new ScreenSize("75%")),
			cbmi50 = new JCheckBoxMenuItem(new ScreenSize("50%")),
			cbmi125 = new JCheckBoxMenuItem(new ScreenSize("125%")),
			cbmi150 = new JCheckBoxMenuItem(new ScreenSize("150%")),
			cbmi200 = new JCheckBoxMenuItem(new ScreenSize("200%"));
		//�ŧi�]�w�e���j�p���֨�����ﶵ

		mnFile.setMnemonic(KeyEvent.VK_F);
		mnScreen.setMnemonic(KeyEvent.VK_S);
		mnPlay.setMnemonic(KeyEvent.VK_P);
		//�]�w�U����

		miOpen.addActionListener(alOpen);
		miExit.addActionListener(alExit);
		//���U��ť�\���ﶵ��ActionEvent�ƥ�

		mnFile.add(miOpen); //�N�ﶵ�[�J�\���
		mnFile.add(miExit);

		ButtonGroup bg = new ButtonGroup();
		bg.add(cbmi100);
		bg.add(cbmi75);
		bg.add(cbmi50);
		bg.add(cbmi200);
		bg.add(cbmi150);
		bg.add(cbmi125);
		//�N�֨�����ﶵ�[�J���s�s��
		
		mnScreen.add(cbmi100);
		mnScreen.add(cbmi75);
		mnScreen.add(cbmi50);
		mnScreen.add(cbmi200);
		mnScreen.add(cbmi150);
		mnScreen.add(cbmi125);
		//�N�֨�����ﶵ�[�J�e���\���

		cbmi100.setSelected(true);
		//�w�]�����ܤ�Ҭ�100%���ù��e��

		mnPlay.add(cbmRepeat);	//�N�ﶵ�[�J�\���

		JMenuBar jmb = new JMenuBar();
		//�ŧi�\���C

		jmb.add(mnFile);
		jmb.add(mnScreen);
		jmb.add(mnPlay);
		//�N�ɮץ\���P�ù��\���[�J�\���C

		setJMenuBar(jmb);	 //�]�w�����ج[�ϥΪ��\���C

		cp = getContentPane();

		addComponentListener(cma);
		//���U��ťComponentEvent�ƥ󪺺�ť������

		addWindowListener(wa);
		//���U��ť�����ج[WindowEvent�ƥ󪺺�ť������

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 600);
		setVisible(true);

		Manager.setHint(
			Manager.LIGHTWEIGHT_RENDERER, new Boolean(true));
		//�I�s�޲z����setHint()��k, �]�w���񾹤�����Ķ����
	}

	//�إ߿ù��e��
	private Box buildScreen(){

		Dimension 
			dmVisual = visualPane.getPreferredSize(),
			dmContent = cp.getSize(),
			dmControl = controlPane.getSize();
		//���o��ı�����B���e�����P��������ؤo

		int strutWidth = 
			(int)(dmContent.width - dmVisual.width * ratio) / 2;
		//�p���[���e��

		int strutHeight = 
			(int)(dmContent.height - 
			dmVisual.height * ratio - dmControl.height) / 2;
		//�p���[������

		Box bxInner = new Box(BoxLayout.X_AXIS);
		bxInner.add(Box.createHorizontalStrut(strutWidth));
		bxInner.add(visualPane);
		bxInner.add(Box.createHorizontalStrut(strutWidth));
		//�ŧiBox�e��, �m�J��ܼv���ɪ��e��, 
		//�ëإߤ�����V�ťն��Z����[

		Box bxOutter = new Box(BoxLayout.Y_AXIS);
		bxOutter.add(Box.createVerticalStrut(strutHeight));
		bxOutter.add(bxInner);
		bxOutter.add(Box.createVerticalStrut(strutHeight));
		//�ŧiBox�e��, �m�J��ܼv���ɪ��e��,
		//�ëإ߫�����V�ťն��Z����[

		return bxOutter;
	}

	//���U�^��WindowEvent�ƥ󪺺�ť������
	WindowAdapter wa = new WindowAdapter(){

		//�^�����������ʧ@
		public void windowClosing(WindowEvent e){
			System.exit(0); //�����{������
		}
	};

	//�ŧi��ť�\����}�ҿﶵActionEvent�ƥ󪺺�ť������
	ActionListener alOpen = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String[]  mpgFilter = {"mpg", "mpeg"},
						  aviFilter =	{"avi", "wmv"},
						  qtFilter = {"mov"};
			//�ŧi�ѨϥΪ̿���ɮ��������z�磌��

			MediaFileChooser mcObj = new MediaFileChooser(
					MPlayer.class.getResource("/").getPath() + "mpg");
			//�ŧi�ɮ׿����, �ó]�w�_�l�˵����|

			mcObj.addChoosableFileFilter(
				mpgFilter, "�q�v��(mpg) (*.mpg, *.mpeg)");
			mcObj.addChoosableFileFilter(
				aviFilter, "Windows ���T��(avi) (*.avi, *.wmv)");
			mcObj.addChoosableFileFilter(
				qtFilter, "QuickTime �v���� (*.mov)");
			//�[�J�ɮ׿z�磌��

			mcObj.setFileFilter(mcObj.getAcceptAllFileFilter());
			//�]�w��ܲ��w�]�ϥο���Ҧ��ɮת�FileFilter

			mcObj.setDialogTitle("�}�Ҽv��");
			//�]�w��ܲ������D

			int result = mcObj.showOpenDialog(MPlayer.this);
			//��ܹ�ܲ�, �è��o�N����U���s���`��

			//�P�_���U�����s
			if(result == MediaFileChooser.APPROVE_OPTION){

				if(player != null){ //�P�_���񪫥�O�_��null
						if(controlPane != null) 
							cp.remove(controlPane); //���������

						if(bxScreen != null)						
							cp.remove(bxScreen); //������ܵe��

						player.stop(); //����񪫥�
						player.close(); //�������񪫥�					
				}

				File selFile = mcObj.getSelectedFile();
				//���o������ɮ�
		
				try{
					player = Manager.createPlayer(
						new URL(new String("file:" + selFile.getPath())));
					//�I�s�޲z������o���񪫥�
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(
						MPlayer.this, ex.getMessage(), 
						"���~", JOptionPane.ERROR_MESSAGE);
					//��ܿ��~�T��
				}

				if(player != null){ //�P�_���񪫥�O�_��null
					player.addControllerListener(ca);
					//���U�^��ControllerEvent�ƥ󪺺�ť������

					player.realize(); //�غc���񪫥�
				}
			}
		}
	};

	//�ŧi��ť�h�C�鼽�񱱨�ƥ󪺺�ť������
	ControllerAdapter ca = new ControllerAdapter(){

		//����������
		public void realizeComplete(RealizeCompleteEvent e){

			Player player = (Player) e.getSource();
			//���o�޵o�ƥ󪺼��񾹪���

			player.prefetch(); //�ǳƸ��J
		}

		//����o�Ϳ��~
		public void controllerError(ControllerErrorEvent e){
			JOptionPane.showMessageDialog(
				MPlayer.this, e.getMessage(), "���~",
				JOptionPane.ERROR_MESSAGE);
		}

		//�h�C���ɧ������J�ǳ�
		public void prefetchComplete(PrefetchCompleteEvent e){

			Player player = (Player) e.getSource();
			//���o�޵o�ƥ󪺼��񾹪���

			controlPane = player.getControlPanelComponent();
			//���o�����

			if(controlPane != null){ //�P�_�O�_���T���o�����
				cp.add(controlPane, BorderLayout.SOUTH);
				//�N��������[�J���e����
			}

			visualPane = player.getVisualComponent();
			//���o�e����ܭ���

			if (visualPane != null) { //�P�_�O�_���T���o�e������
				bxScreen = buildScreen(); //�إߵe��
				cp.add(bxScreen, BorderLayout.CENTER);
				//�N�e���[�J���e����
			}

			validate(); //�i������
			player.start(); //�}�l���񪫥�
		}

		//��������
		public void endOfMedia(EndOfMediaEvent e){

			//�P�_���Ƽ���֨�����ﶵ�O�_���
			if(cbmRepeat.isSelected()){
				Player player = (Player) e.getSource();
				//���o�޵o�ƥ󪺼��񪫥�

				player.setMediaTime(new Time(0));
				//�]�w�}�l���񪺮ɶ�

				player.start(); //�}�l����
			}
		}
	};

	//�ŧi��ť�����ﶵActionEvent�ƥ󪺺�ť������
	ActionListener alExit = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			System.exit(0); //�����{������
		}
	};

	//�ŧi��ťComponentEvent�ƥ󪺺�ť������
	ComponentAdapter cma = new ComponentAdapter() {

		//���s�վ㤸��j�p
		public void componentResized(ComponentEvent e) {

			if(bxScreen != null){ //�P�_�O�_�����������󪺫إ�
				cp.remove(bxScreen); //������������
				bxScreen = buildScreen(); //�إߵ�������
				cp.add(bxScreen); //�N��������[�J���e����
				validate(); //����T�{
			}
		}
	};

		//�w�q�]�w�e���j�p�ʤ��񪺩�H�ʧ@���O
	class ScreenSize extends AbstractAction {

		public ScreenSize(String name){ //�ŧi�ù��j�p

			super(name);
			//�N�ʧ@Action����W�ٶǤJ��¦���O�غc�l

			putValue(Action.SHORT_DESCRIPTION, name);
			//�]�wAction����²�u�y�z��r
		}

		//�^��ActionEvent�ƥ�, �ó]�w��ܵe�����j�p
		public void actionPerformed(ActionEvent e){

			String name = (String) getValue(Action.NAME);
			//���oAction����W�٪��]�w��

			//�̷Ӱʧ@�R�O�r��]�w�e���j�p����v
			if(name.equals("100%"))	
				ratio = 1.0;
			else if(name.equals("75%"))
				ratio = 0.75;
			else if(name.equals("50%"))
				ratio = 0.5;
			else if(name.equals("200%"))
				ratio = 2.0;
			else if(name.equals("125%"))
				ratio = 1.25;
			else if(name.equals("150%"))
				ratio = 1.5;

			if(bxScreen != null){ //�P�_�e�ǵe����Box�e���O�_�����إ�
				cp.remove(bxScreen); //�����e�ǵe����Box�e��
				bxScreen = buildScreen(); //�إߵe��
				cp.add(bxScreen); //�N�e���[�J���e����
				validate(); //��������
			}
		}
	}

	public static void main(String[] args) {
		new MPlayer(); //�ŧi�����ج[����
	}
}