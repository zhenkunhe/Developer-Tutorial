import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileFilter;

import java.io.*;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

import java.util.*;

public class MDIEditor extends JFrame{

	JDesktopPane dpPane = new JDesktopPane();
	//�e�Ǥ����ج[�������ୱ

	TextInternalFrame tifCurrent; //�ثe�I�諸��r�s�褺���ج[

	WindowMenu wmWindow = 
		new WindowMenu("����(W)", KeyEvent.VK_W);
	//����������e���������\���

	Action acCut, acCopy, acPaste; //����s��ʧ@��Action����

	JToggleButton tbnSize16, tbnSize18, tbnSize20;
	//����r�Ťj�p���u��C���s

	JCheckBoxMenuItem cbmiSize16, cbmiSize18, cbmiSize20;
	//����r�Ťj�p���֨�����ﶵ

	JLabel lbStatus; //��ܴ�Ц�m�P����r��������

	MDIEditor(){

		createInternalFrame(); //�إ߲Ĥ@�Ӥ����ج[
		
		JTextPane tpCurrent = tifCurrent.getTextPane();
		//���o�����ج[�ϥΪ���r�s�譱��

		JMenu mnFile = new JMenu("�ɮ�(F)"); //�ŧi�ɮץ\���
		mnFile.setMnemonic(KeyEvent.VK_F);
		//�]�w�ɮץ\���ϥΪ��U�вŸ�

		JMenuItem
			miNew = new JMenuItem("�s�W(N)", KeyEvent.VK_N),
			miOpen = new JMenuItem("�}������(O)", KeyEvent.VK_O),
			miSave = new JMenuItem("�x�s�ɮ�(S)", KeyEvent.VK_S),
			miSaveAn = new JMenuItem("�t�s�s��(A)", KeyEvent.VK_A),
			miExit = new JMenuItem("����(E)", KeyEvent.VK_E);
		//�ŧi�ɮץ\����ﶵ

		miNew.addActionListener(alFile); //���\���ﶵ�[�W��ť��
		miOpen.addActionListener(alFile);
		miSave.addActionListener(alFile);
		miSaveAn.addActionListener(alFile);
		miExit.addActionListener(alFile);

		mnFile.add(miNew); //�N�ﶵ�[�J�ɮץ\���
		mnFile.add(miOpen);
		mnFile.add(miSave);
		mnFile.add(miSaveAn);
		mnFile.addSeparator();
		mnFile.add(miExit);

		JMenu mnEdit = new JMenu("�s��(E)"); //�ŧi�s��\���
		mnEdit.setMnemonic(KeyEvent.VK_E);
		//�]�w�s��\����U�вŸ�

		acCut = tifCurrent.getTextPane().getActionMap().get(
							DefaultEditorKit.cutAction);
		acCopy = tifCurrent.getTextPane().getActionMap().get(
							DefaultEditorKit.copyAction);
		acPaste = tifCurrent.getTextPane().getActionMap().get(
							DefaultEditorKit.pasteAction);
		//���oJTextPane���󴣨Ѱ���ŤU�B�ƻs�B�K�W�ʧ@��Action����

		acCut.putValue(Action.NAME, "�ŤU(T)");
		acCopy.putValue(Action.NAME, "�ƻs(C)");
		acPaste.putValue(Action.NAME, "�K�W(P)");
		//�]�wAction����ϥΪ��W��

		acCut.putValue(Action.MNEMONIC_KEY , KeyEvent.VK_T);
		acCopy.putValue(Action.MNEMONIC_KEY , KeyEvent.VK_C);
		acPaste.putValue(Action.MNEMONIC_KEY , KeyEvent.VK_P);
		//�]�wAction����ϥΪ��U�вŸ�

		acCut.putValue(Action.ACCELERATOR_KEY, 
			KeyStroke.getKeyStroke(
				'X', java.awt.event.InputEvent.CTRL_MASK, false));
		acCopy.putValue(Action.ACCELERATOR_KEY,
			KeyStroke.getKeyStroke(
				'C', java.awt.event.InputEvent.CTRL_MASK, false));
		acPaste.putValue(Action.ACCELERATOR_KEY, 	
			KeyStroke.getKeyStroke(
				'V', java.awt.event.InputEvent.CTRL_MASK, false));
		//�]�wAction����ϥΪ��[�t��	

		acCut.setEnabled(false); //�]�wAction����L��
		acCopy.setEnabled(false);

		mnEdit.add(acCut); //�NAction����[�J�\������ﶵ
		mnEdit.add(acCopy);
		mnEdit.add(acPaste);

		JMenu mnFontSize = new JMenu("�r��(S)"); //�ŧi�r�ť\���
		mnFontSize.setMnemonic(KeyEvent.VK_S);
		//�]�w�r�ť\����U�вŸ�

		FontSizeAction 
			fsaSize16 = new FontSizeAction(
				"16(S)", new ImageIcon("icon/size16.gif"),
				"�]�w�ϥ�16�Ŧr", KeyEvent.VK_S),
			fsaSize18 = new FontSizeAction(
				"18(M)", new ImageIcon("icon/size18.gif"),
				"�]�w�ϥ�18�Ŧr", KeyEvent.VK_M),
	       fsaSize20 = new FontSizeAction(
				"20(L)", new ImageIcon("icon/size20.gif"),
				"�]�w�ϥ�20�Ŧr", KeyEvent.VK_L);
		//�ŧi����r�Ťj�p�]�w�ʧ@��Action����

		cbmiSize16 = new JCheckBoxMenuItem(fsaSize16);
		cbmiSize18 = new JCheckBoxMenuItem(fsaSize18);
		cbmiSize20 = new JCheckBoxMenuItem(fsaSize20);
		//�H����r�Ťj�p�]�w��Action����إ߮֨�����ﶵ

		cbmiSize16.setIcon(null); //�]�w�֨�����ﶵ���ϥιϥ�
		cbmiSize18.setIcon(null);
		cbmiSize20.setIcon(null);

		cbmiSize16.setState(true);
		//�]�w����N��16�r�Ū��֨�����ﶵ

		mnFontSize.add(cbmiSize16); //�N�֨�����ﶵ�[�J�\���
		mnFontSize.add(cbmiSize18);
		mnFontSize.add(cbmiSize20);

		ButtonGroup bgSize = new ButtonGroup(); //�ŧi���s�s��
		bgSize.add(cbmiSize16); //�N�֨�����ﶵ�[�J���s�s��
		bgSize.add(cbmiSize18);
		bgSize.add(cbmiSize20);

		JMenuBar jmb = new JMenuBar(); //�ŧi�\���C����
		setJMenuBar(jmb); //�]�w�����ج[�ϥΪ��\���C
		jmb.add(mnFile); //�N�\���[�J�\���C
		jmb.add(mnEdit);
		jmb.add(mnFontSize);
		jmb.add(wmWindow);

		JToolBar tbFontSize = new JToolBar(); //�s�W�u��C

		tbnSize16 = new JToggleButton(fsaSize16);
		tbnSize18 = new JToggleButton(fsaSize18);
		tbnSize20 = new JToggleButton(fsaSize20);
		//�H����r�Ťj�p�]�w��Action����, 
		//�ŧi�u��C��JToggleButton���s

		tbFontSize.add(tbnSize16); //�NJToggleButton���s�[�J�u��C
		tbFontSize.add(tbnSize18);
		tbFontSize.add(tbnSize20);

		tbnSize16.setActionCommand("16(S)");
		tbnSize18.setActionCommand("18(M)");
		tbnSize20.setActionCommand("20(L)");
		//�]�����s����ܦr��,�G�����]�w�ʧ@�R�O�r��,
		//�H�K��^���ƥ�ɧP�O

		tbnSize16.setText(null);
		tbnSize18.setText(null);
		tbnSize20.setText(null);
		//�]�wJToggleButton���s����ܦr��

		tbnSize16.setSelected(true);
		//�]�w����N��16�r�Ū�JToggleButton���s

		ButtonGroup bgToolBar = new ButtonGroup(); //�ŧi���s�s��
		bgToolBar.add(tbnSize16); //�NJToggleButton���s�[�J���s�s��
		bgToolBar.add(tbnSize18);
		bgToolBar.add(tbnSize20);

		JPanel plStatus = new JPanel(new GridLayout(1, 1));
		//�ŧi�������A�C��JPanel

		lbStatus = new JLabel("��Ц�m : �� 0 �Ӧr��");
		//�ŧi��ܰT��������

		plStatus.add(lbStatus);	//�N���ҥ[�JJPanel�e��

		Container cp = getContentPane(); //���o���e����
		cp.add(tbFontSize, BorderLayout.NORTH);
		//�N�u��C�[�J���e����

		cp.add(dpPane); //�N�����ୱ�[�J���e����
		cp.add(plStatus, BorderLayout.SOUTH); //�N���A�C�[�J���e����

		addWindowListener(wa); //���U�^��WindowEvent�ƥ󪺺�ť��

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(
			WindowConstants.DO_NOTHING_ON_CLOSE );
		setSize(800, 600);
		setVisible(true);
	}

	//�ǥX�Ω�e�Ǥ����ج[�������ୱ
	public JDesktopPane getDesktopPane(){ return dpPane; }

	//�إߤ�r�s�褺���ج[
	private void createInternalFrame(){

		tifCurrent = new TextInternalFrame();
		//�ŧi�����ج[����

		intiInternalFrame(); //�_�l�]�w�����ج[
	}

	//�����w�ɮ׫إߤ�r�s�褺���ج[
	private void createInternalFrame(String path, String name){

		tifCurrent = new TextInternalFrame(path, name);
		//�ŧi�����ج[����

		intiInternalFrame(); //�_�l�]�w�����ج[						
	}

	private void intiInternalFrame(){

		tifCurrent.addCaretListener(cl);
		//���U�^�����CaretEvent�ƥ󪺺�ť��

		tifCurrent.addInternalFrameListener(ifl);
		//���U�^��InternalFrameEvent�ƥ󪺺�ť��

		JCheckBoxMenuItem
			cbmiWindow = tifCurrent.getMenuItem();
		//���o�N�����إߤ�TextInternalFrame���󪺮֨�����ﶵ

		wmWindow.add(cbmiWindow, tifCurrent);
		//�N�֨�����ﶵ�P������TextInternalFrame����
		//�s�W�ܵ����\���

		dpPane.add(tifCurrent);
		//�N�����إߪ�TextInternalFrame����[�J�����ୱ

		int FrameCount = dpPane.getAllFrames().length;
		//���o�����ୱ��TextInternalFrame���󪺭Ӽ�

		tifCurrent.setLocation(
			20*(FrameCount-1), 20*(FrameCount-1));
		//�]�wTextInternalFrame�������ܤ�r�s������ج[
		//���W���b�����ୱ���y��

		try{
			tifCurrent.setSelected(true);
			//�]�w��������إߪ�TextInternalFrame����
		}
		catch(java.beans.PropertyVetoException pve){
			System.out.println(pve.toString());
		}
	}


	private void saveFile(String strPath) //�x�s�ɮ�
				throws IOException, BadLocationException{

		int pos = strPath.lastIndexOf("\\");

		String path = strPath.substring(0, pos+1);
		String name = strPath.substring(pos+1, strPath.length());

		JFileChooser fcSave = new JFileChooser(path);
		//�إ��ɮ׿����ܲ�

		fcSave.setSelectedFile(new File(name)); //�]�w������ɮ�

		fcSave.addChoosableFileFilter(new TxtFileFilter("txt"));
		//�]�w�z���ɮת�����

		fcSave.setDialogTitle("�t�s�s��"); //�]�w��ܲ����D

		int result = fcSave.showSaveDialog(MDIEditor.this);
		//����ɮ��x�s��ܲ�

		if(result == JFileChooser.APPROVE_OPTION){
		//�ϥΪ̫��U �T�{ ���s

			File file = fcSave.getSelectedFile(); //���o������ɮ�
			tifCurrent.write(new FileWriter(file));
			//�N��r�s�褺���ج[�����e��X��FileWriter����

			tifCurrent.setFileName(file.getName()); //�]�w�s���ɮצW��
			tifCurrent.setFilePath(file.getPath()); //�]�w�s���ɮ׸��|
		}
	}

	//�w�q�ëŧi�^��InternalFrameEvent�ƥ󪺺�ť��
	InternalFrameAdapter ifl = new InternalFrameAdapter(){

		//�����ج[���o��еJ�IĲ�o�ƥ�N�Ѧ���k�^��
		public void internalFrameActivated(InternalFrameEvent e){

			tifCurrent = (TextInternalFrame)e.getInternalFrame();
			//���oĲ�oInternalFrame�ƥ�TextInternalFrame����

			tifCurrent.getMenuItem().setSelected(true);
			//�]�w�����\����N��TextInternalFrame����
			//���֨�����ﶵ�����

			//���oTextInternalFrame������ܤ��e�ϥΪ��r�Ťj�p
			switch(tifCurrent.getFontSize()){
			case 16 :
				cbmiSize16.setSelected(true); //�]�w��������������
				tbnSize16.setSelected(true);
				break;
			case 18 :
				cbmiSize18.setSelected(true);
				tbnSize18.setSelected(true);
				break;
			case 20 :
				cbmiSize20.setSelected(true);
				tbnSize20.setSelected(true);
				break;			
			}
			
		}

		//�����ج[���b�����ɩ�Ĳ�o�ƥ�N�Ѧ���k�^��
		public void internalFrameClosing(InternalFrameEvent e) {
			wmWindow.remove(tifCurrent.getMenuItem());
			//���������\����N��ثe����s�褧
			//TextInternalFrame���󪺿ﶵ
		}
	};

	//�w�q�ëŧi�^��CaretEvent�ƥ󪺺�ť��
	CaretListener cl = new CaretListener(){

		//���ʴ�Ц�m��, �N�Ѧ���k�^��
		public void caretUpdate(CaretEvent e) {

			if(e.getDot() != e.getMark()){
				lbStatus.setText("��Ц�m : �� " + e.getDot() + 
					" �Ӧr��" + ", ����d�� : " + e.getDot() 
					+ "��" + e.getMark());
				//�]�w���A�C������r

				acCut.setEnabled(true);
				acCopy.setEnabled(true);
				//�]�w����ŤU�P�ƻs�ʦr��Action���󬰦���
			}
			else{
				lbStatus.setText(
					"��Ц�m : �� " + e.getDot() + " �Ӧr��");
				//�]�w���A�C������r

				acCut.setEnabled(false);
				acCopy.setEnabled(false);
				//�]�w����ŤU�P�ƻs�ʦr��Action���󬰵L��
			}
		}
	};

	//�w�q�ëŧi�^���ɮץ\����ﶵ�Q�����Ĳ�o�ƥ󪺺�ť��
	ActionListener alFile = new ActionListener(){

		public void actionPerformed(ActionEvent e){
			int result;

			try {
				//�����ɮ׶}�Ұʧ@
				if(e.getActionCommand().equals("�}������(O)")){
					JFileChooser fcOpen = new JFileChooser(
								tifCurrent.getFilePath());
					 //�ŧiJFileChooser����

					fcOpen.addChoosableFileFilter(new TxtFileFilter("txt"));
					//�]�w�z���ɮת�����

					fcOpen.setDialogTitle("�}������"); //�]�w�ɮ׿�ܹ�ܲ������D
					result = fcOpen.showOpenDialog(MDIEditor.this);
					//��ܶ}���ɮ׹�ܲ�
	
					if(result == JFileChooser.APPROVE_OPTION){ //�ϥΪ̫��U �T�{ ���s
						File file = fcOpen.getSelectedFile(); //���o������ɮ�

						createInternalFrame(file.getPath(), file.getName());
						//�H���o���ɮ׫إ�TextInternalFrame����
					}
				}
				else if(e.getActionCommand().equals("�s�W(N)")){ //�s�W���										
					createInternalFrame(); //�إߨS�����e��TextInternalFrame����
				}
				else if(e.getActionCommand().equals("�x�s�ɮ�(S)")){
				//�����x�s�ɮװʧ@

					String strPath = tifCurrent.getFilePath();
					//���o�ثeTextInternalFrame����}���ɮת����|�P�W��

					if(!tifCurrent.isNew()){
					//�P�_TextInternalFrame����}�Ҫ��O�_���s���ɮ�

						FileWriter fw = new FileWriter(strPath);
						//�إ߿�X�ɮת�FileWriter����

						tifCurrent.write(fw);
						//TextInternalFrame���󪺤��e��X��FileWriter����

						return;				
					}
					else
						saveFile(strPath); //�x�s�ɮ�
				}
				else if(e.getActionCommand().equals("�t�s�s��(A)")){					
					saveFile(tifCurrent.getFilePath()); //�x�s�ɮ�
				}
				else if(e.getActionCommand().equals("����(E)")){
					MDIEditor.this.processWindowEvent(
						new WindowEvent(MDIEditor.this,
						WindowEvent.WINDOW_CLOSING));
					//����WindowEvent�ƥ�, Ĳ�oMDIEditor�����ج[�����������ƥ�
				}
			}
			catch (IOException ioe) {
				System.err.println(ioe.toString());
			}
			catch (BadLocationException ble) {
				System.err.println("��m�����T");
			}
		}
	};

	//�w�q�ëŧi�^��WindowEvent�ƥ�WindowAdapter���O,
	//�b�������ε{���e, �B�κ�ť���P�O�{�����}�Ҫ��ɮ׬O�_�w�g�x�s
	WindowAdapter wa = new WindowAdapter(){

		//�^�����������ƥ󪺤�k
		public void windowClosing(WindowEvent e){

			JInternalFrame[] ifAll = getDesktopPane().getAllFrames();
			//���o�ثe�����ୱ���Ҧ��}�Ҫ�TextInternalFrame����

			TextInternalFrame tifCurrent = 
				(TextInternalFrame)getDesktopPane().getSelectedFrame();
			//���o�����ୱ�ثe�����TextInternalFrame����

			//�P�_�}�Ҫ�TextInternalFrame����O�_��0
			if(ifAll.length != 0){

				//�B�Υ[�j��for�j����o�����ୱ��
				//�Ҧ�TextInternalFrame����
				for(JInternalFrame elm: ifAll){
					try{
						if(!((TextInternalFrame) elm).isChanged()){
							elm.setClosed(true); //���������ج[
						}
						else{
							int result = 
								JOptionPane.showConfirmDialog(
									MDIEditor.this, "�O�_�x�s?", "�T��",
									JOptionPane.YES_NO_CANCEL_OPTION,
									JOptionPane.INFORMATION_MESSAGE);
							//��ܽT�{���

							if(result == JOptionPane.NO_OPTION){
							//�P�_�O�_���U �_ ���s
								elm.setClosed(true);
							}
							else if(
								result == JOptionPane.CANCEL_OPTION){
							//�P�_�O�_���U ���� ���s
								return;
							}
							else if(result == JOptionPane.YES_OPTION){
							//�P�_�O�_���U �O ���s

								String strPath = 
									((TextInternalFrame) elm).getFilePath();
								//���oTextInternalFrame�ثe�s���ɮת����|

								//�P�_TextInternalFrame�ثe�s���ɮ׬O�_���s��
								if(!tifCurrent.isNew()){
									tifCurrent.write(new FileWriter(strPath));
									//�NTextInternalFrame�����e�g�JFileWriter����
								}
								else
									saveFile(strPath); //�x�s�ɮ�

								elm.setClosed(true); //���������ج[
							}
						}
					}
					catch(java.beans.PropertyVetoException pve){
						System.out.println(pve.toString());
					}
					catch (IOException ioe) {
						System.err.println(ioe.toString());
					}
					catch (BadLocationException ble) {
						System.err.println("��m�����T");
					}
				}
			}

			ifAll = getDesktopPane().getAllFrames();
			//���o�����ୱ�ثe�}�Ҫ��Ҧ������ج[

			if(ifAll.length == 0) //�P�_�����ج[���ƥ�
				System.exit(0); //�������ε{��
		}
	};

	//�w�q�����r�r�ų]�w��Action����
	class FontSizeAction extends AbstractAction {

		public FontSizeAction(String text, ImageIcon icon,
				 String desc, Integer mnemonic) {
			super(text, icon); //�I�s��¦���O�غc�l
			putValue(SHORT_DESCRIPTION, desc); //�]�w���ܦr��
			putValue(MNEMONIC_KEY, mnemonic); //�]�w�U�вŸ�
		}

		//�^���ƥ󪺰���ʧ@		
		public void actionPerformed(ActionEvent e) {	

			//�̷Ӱʧ@�R�O�r��P�O�����檺�ʧ@
			if(e.getActionCommand().equals("20(L)")){
				tifCurrent.setFontSize(20);
				//�]�w��r�s�譱���ϥ�20�Ŧr

				cbmiSize20.setSelected(true);
				tbnSize20.setSelected(true);
				//�]�w��������������
			}
			else if(e.getActionCommand().equals("18(M)")){
				tifCurrent.setFontSize(18);
				cbmiSize18.setSelected(true);
				tbnSize18.setSelected(true);
			}
			else{
				tifCurrent.setFontSize(16);
				cbmiSize16.setSelected(true);
				tbnSize16.setSelected(true);
			}
		}
	}

	//�إ߹L�o�ɮ׿�ܹ�ܲ����ɮ�����������
	class TxtFileFilter extends FileFilter {
		String extension;

		public TxtFileFilter(String ext){ //�غc�l
			extension = ext;
		}

		public boolean accept(File f) {
			if (f.isDirectory()) //�Y����Ƨ��Ǧ^true
				return true;

			String ext = null;
			String s = f.getName(); //���o�ɮצW��
			int i = s.lastIndexOf('.'); //�M���ɮצW�٤���"."��

			if (i > 0 &&  i < s.length() - 1){
				ext = s.substring(i+1).toLowerCase();
				//�q�ɮצW�٤����o���ɦW�r

				//�P�_���ɦW�O�_�P�ɮ׿z�磌��extension�r��ۦP
				if(ext.equals(extension))
					return true;
			}

			return false;
		}

		//�Ǧ^�ɮ׿z�磌����z���ɮ��������y�z�r��
		public String getDescription() { return "Text File"; }	
	}

	public static void main(String args[]){
		MDIEditor api = new MDIEditor(); //�إߵ����ج[	
	}
}