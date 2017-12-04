import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.event.*;

//�s�������D�n���O
public class JBrowser extends JFrame {

	static final String HOME_URL = "http://www.yahoo.com";
	//�w�]���s���������|

	private URLLoader loader = null;
	//���JURL��}���w���������������

	private JEditorPane content = new JEditorPane();
	//��ܺ�������r�s�譱��

	private JPanel jpStatus =
		new JPanel(new FlowLayout(FlowLayout.LEFT));
	//���A�C������

	private JLabel lbLoading =
		new JLabel("Ready", JLabel.LEFT);
	//���A�C������������

	private URLComboBox urlcmb = new URLComboBox();
	//�ŧi�i�O�пﶵ�M�椧���e�����}�զX���

	private JToolBar tbStandard = new JToolBar("�з�"),
								 tbURL = new JToolBar("�s��");
	//�ŧi�u��C

	private ArrayList<String> pageList = new ArrayList<String>();
	//�ŧi�x�s�w�s�����W�s����ArrayList�e��
	
	private	JButton
		btnPrevious = new JButton(new BrowserAction("�W�@��",
							 new ImageIcon("image/previous.gif"))),
		btnNext = new JButton(new BrowserAction("�U�@��",
							new ImageIcon("image/next.gif"))),
		btnHome = 	new JButton(new BrowserAction("����",
							new ImageIcon("image/home.gif"))),
		btnGo = new JButton(new BrowserAction("����",
							new ImageIcon("image/go.gif")));
	//�ŧi�u��C�����s

	private Thread loadMsgThread = null;
	//�ŧi��ܸ��J�T�������������

	public JBrowser(){

		JMenuItem miExit = 
			new JMenuItem("����(E)", KeyEvent.VK_E);
		//�ŧi�����{�����ﶵ

		miExit.addActionListener(alExit);

		JMenu mnFile = new JMenu("�ɮ�");
		//�ŧi�ɮץ\���

		mnFile.add(miExit); //�N�����ﶵ�[�J�\���
		
		JMenuBar mbMain = new JMenuBar(); //�ŧi�\���C
		mbMain.add(mnFile); //�N�\���[�J�\���C

		setJMenuBar(mbMain); //�]�w�����{���ϥΪ��\���C

		btnNext.setEnabled(false); //�]�w�U�@�����s���L��
		btnPrevious.setEnabled(false); //�]�w�W�@�����s���L��

		btnHome.setText(""); //�]�w�������s�L��ܤ�r

		tbStandard.add(btnPrevious);  //�N���s�[�J�u��C
		tbStandard.add(btnNext);
		tbStandard.add(btnHome);

		tbStandard.setFloatable(false); //�]�w�u��C���B��

		content.setContentType("text/html");
		//�]�w��r�s�譱�����e�ϥΪ��榡

		content.setEditable(false); //�]�w��r�s�譱�����i����s��

		ComboBoxAgent cma = new ComboBoxAgent(urlcmb);
		//�ŧi��ť���}�զX����N�z����

		content.addHyperlinkListener(hl);
		//���U��ťHyperlinkEvent�ƥ󪺺�ť������

		urlcmb.setEditable(true); //�]�w���}�զX����O�_�i�s��

		urlcmb.addItemListener(il);
		//���U��ťItemEvent�ƥ󪺺�ť������

		addWindowListener(wa);
		//���U��ťWindowEvent�ƥ󪺺�ť������

		tbURL.setLayout(new BorderLayout());
		tbURL.add(new JLabel("���}: "),BorderLayout.WEST );
		tbURL.add(urlcmb);
		tbURL.add(btnGo,BorderLayout.EAST);
		//�N���ҡB���}�զX����P���s�[�J�u��C

		tbURL.setFloatable(false);
		//�]�w�u��C���B��

		JPanel jpToolbar = new JPanel(new BorderLayout());
		jpToolbar.add(tbStandard,  BorderLayout.NORTH);
		jpToolbar.add(tbURL,  BorderLayout.CENTER);
		//�ŧi�e�Ǥu��C��JPanel�e��

		jpStatus.add(lbLoading);
		//�N��ܳs�u���A�����ҥ[�J���A�C����

		Container cp = getContentPane();
		cp.add(jpToolbar,  BorderLayout.NORTH);
		cp.add(new JScrollPane(content));
		cp.add(jpStatus, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(800, 600);
		setVisible(true);
	}

	//�]�w��r�s�譱����ܪ�����
	public void showPage(String strUrl, boolean addToList)
														throws IOException {

		//�P�_�O�_�NURL���|�[�JArrayList�e��
		if (addToList){

			int size = pageList.size(); //���oArrayList�e�����j�p

			if(size > 0){

				JTextField editor = 
					(JTextField)urlcmb.getEditor().getEditorComponent();
				//���o���}�զX������s���r�����

				String currentUrl = editor.getText();
				//���o���}�զX�����r�s����쪺URL���|

				int curIndex = pageList.indexOf(currentUrl);
				//���o�ثe�s��������}�����ޭ�

				//�P�_���ޭȥ[1���j�p�O�_����size
				if((curIndex+1) < size){

					//�����ثe���ޭȤ��᪺�Ҧ��M��ﶵ
					for(int i=(size-1); i>curIndex; i--){
						pageList.remove(i);
						//�����ﶵ
					}
				}
			}

			pageList.add(strUrl); //�NURL���|�[�JArrayList����
		}

		loader = new URLLoader(strUrl);
		//�ŧi���JURL���|���w���������������

		loader.start(); //�Ұʰ����

		loadMsgThread = new URLLoadMsg();
		//�ŧi�ʬݺ������J�ʧ@�����������

		loadMsgThread.start();
		//�Ұʰ����

		//�P�_ArrayList���󤺪�URL���|�ӼƬO�_���p��2
		if(pageList.size() < 2 ){
			btnPrevious.setEnabled(false);
			btnNext.setEnabled(false);
			//�]�w�e�@��/�U�@�����s���L��
		}
		else{
			int newIndex = pageList.lastIndexOf(strUrl);
			//���o������s����URL���|
			//�bArrayList�e���������ޭȦ�m

			btnPrevious.setEnabled(newIndex > 0);
			//�]�w�W�@�����s������, �H�W�ԭz�۷��
			/*if(newIndex > 1)
				btnPrevious.setEnabled(true);
			*/

			btnNext.setEnabled(newIndex <  (pageList.size()-1));
			//�YArrayList�e�����������Ӽƴ� 1, 
			//�j�������s����URL���|�����ޭ�, 
			//�h�]�w���s������
		}
	}

	//�ŧi��ť�����ﶵ��ActionEvent�ƥ󪺺�ť������
	private ActionListener alExit = new ActionListener(){

		public void actionPerformed(ActionEvent e){

			JBrowser.this.processWindowEvent(
				new WindowEvent(
					JBrowser.this, WindowEvent.WINDOW_CLOSING));
			//Ĳ�o���������ƥ�
		}
	};

	//�ŧi��ťHyperlinkEvent�ƥ󪺺�ť������
	private HyperlinkListener hl = new HyperlinkListener(){
		//�^����s�W�s������k
		public void hyperlinkUpdate(HyperlinkEvent event) {

			HyperlinkEvent.EventType eventType = event.getEventType();
			//���o�W�s���ƥ�����

			//�P�_�W�s���������O�_��ACTIVATED
			if(eventType == HyperlinkEvent.EventType.ACTIVATED) {

				URL url = event.getURL(); //���o�޵o�ƥ󤧶W�s����URL��}

				//�P�_�ƥ󪺫��A�O�_��HTMLFrameHyperlinkEvent
				if (event instanceof HTMLFrameHyperlinkEvent) {
					HTMLFrameHyperlinkEvent 
						linkEvent = (HTMLFrameHyperlinkEvent) event;
					//�N���O�ഫ��HTMLFrameHyperlinkEvent

					HTMLDocument 
						document = (HTMLDocument) content.getDocument();
					//���o��r�s�譱���ϥΪ�Document����

					document.processHTMLFrameHyperlinkEvent(linkEvent);
					//����W�s���ƥ�
				}
				else {
					try{
						String strUrl = url.toString();
						//�N�W�s����URL�ন�r��
						showPage(strUrl, true);	//��ܺ���

						urlcmb.addItem(strUrl);
						//�N�W�s���r��[�J���}�զX���

						urlcmb.setSelectedItem(strUrl);
						//�]�w���}�զX������������
					}
					catch(IOException ioe){
						System.err.println(ioe.getMessage());
					}
				}
			}
			else if(eventType == HyperlinkEvent.EventType.ENTERED) {
			//�P�_�W�s�������A�O�_��ENTERED
				lbLoading.setText(	event.getURL().toString());
				//�]�wlbLoading������ܶW�s�����r��
			}
			else if(eventType == HyperlinkEvent.EventType.EXITED) {
			//�P�_�W�s�������A�O�_��EXITED
				lbLoading.setText(	"Ready");
				//�]�wlbLoading�������"Ready"���r��
			}
		}
	};

	//�ŧi��ť�����ج[WindowEvent�ƥ󪺺�ť������
	private WindowAdapter wa = new WindowAdapter(){
		//�^�������ج[���}�Ұʧ@
		public void windowOpened(WindowEvent e){
			urlcmb.load("url.dat");
			//�q�ɮ׸��J�x�s���}�զX������ﶵ�M��
		}

		//�^�������ج[�������ʧ@
		public void windowClosing(WindowEvent e){
			urlcmb.save("url.dat");
			//�N���}�զX������ﶵ�M���x�s���ɮ�

			System.exit(0);
		}
	};

	//�ŧi��ť�զX���ItemEvent�ƥ󪺺�ť������
	private ItemListener il = new ItemListener(){

		//�^�����ا��ܰʧ@����k
		public void itemStateChanged(ItemEvent e) {

			URLComboBox source = (URLComboBox) e.getSource();
			//���o�޵o�ƥ󪺲զX���

			//�P�_�ƥ�O�_�����ؿ���ƥ�
			if(e.getStateChange() == ItemEvent.SELECTED){

				String url = (String) source.getSelectedItem();
				//���o������ت����e

				try{
					Object selObj = e.getItem();
					//���o�������

					if (selObj == null) return;
					//�P�_������جO�_��null

					String selStr = (String)selObj;
					//�N��������૬���r��

					if("".equals(selStr)) return;
					//�P�_������ئr��O�_���Ŧr��

					source.addItem(selStr);
					//�N���إ[�J�զX������M��

					//�P�_������ت����ޭȬO�_��-1
					if(pageList.indexOf(selStr) != -1){
						showPage(selStr,  false);
						//�]�w����ܺ�����URL��}
					}
					else{
						showPage(selStr,  true);
						//�]�w����ܺ�����URL��}
					}
				}
				catch(IOException ioe){
					ioe.printStackTrace();
					System.err.println("���Ū�����~ : " + ioe.getMessage());
				}
			}
		}
	};

	//�w�q�B�z�s���ʧ@��Action����
	class BrowserAction extends AbstractAction {

		//�غc�l
		public BrowserAction(String name, Icon icon){
			super(name, icon);
		}

		//�^��ActionEvent�ƥ󪺤�k
		public void actionPerformed(ActionEvent e){

			String name = (String)getValue(Action.NAME);
			//���oAction����NAME�ʽ褧�]�w��

			String strUrl = (String)urlcmb.getSelectedItem();
			//���o�ثe���}�զX�����������ؤ��r��

			int curIndex = pageList.lastIndexOf(strUrl);
			//�j�M�ثe�����URL�����ޭ�

			try{
				//�̷Ӥ޵o�ƥ󤧤���W�ٰ���������ʧ@
				if(name.equals("����")){
				//�P�_�޵oActionEvent�ƥ󤧪���W�٬O�_������

					showPage(HOME_URL, true);
					//��ܭ�������

					urlcmb.setSelectedItem(HOME_URL);
					//�]�w������ت����|
				}
				else if(name.equals("�W�@��")){
				//�P�_�޵oActionEvent�ƥ󤧪���W�٬O�_���W�@��

					String prevUrl = (String)pageList.get(curIndex -1);
					//���o�W�@�ӳs�����|

					showPage(prevUrl ,false);
					//�]�w��ܫ��wURL���|������

					urlcmb.setSelectedItem(prevUrl);
					//�]�w���}�զX�������W�@�ӳs�����|
				}
				else if(name.equals("�U�@��")){
				//�P�_�޵oActionEvent�ƥ󤧪���W�٬O�_���U�@��

					String nextUrl = (String)pageList.get(curIndex + 1);
					//���o�U�@�ӳs�����|

					showPage(nextUrl ,false);
					//�]�w��ܫ��wURL���|������

					urlcmb.setSelectedItem(nextUrl);
					//�]�w���}�զX�������U�@�ӳs�����|
				}
				else if(name.equals("����")){
				//�P�_�޵oActionEvent�ƥ󤧪���W�٬O�_������

					JTextField editor = 
						(JTextField) urlcmb.getEditor().getEditorComponent();
					//���o���}���}�զX�������r��

					String url = editor.getText();
					//���o��r�檺���e

					showPage(url , true); //�]�w��ܫ��w

					urlcmb.setSelectedItem(url); //���}���}�զX���
				}
			}
			catch(MalformedURLException me){
				me.printStackTrace();
				System.err.println("��}�榡���~ : " + me.getMessage());
			}
			catch(IOException ioe){
				ioe.printStackTrace();
				System.err.println("�����s�����~ : " + ioe.getMessage());
			}
		}
	}

	//������JURL���w�������������
	class URLLoader extends Thread {

		protected String strUrl;
		//�x�s����������|

		public URLLoader(String strUrl){ //�غc�l
			this.strUrl = strUrl; //�]�w�����J������URL���|			
		}

		public void run(){ //���J����
			try{
				content.setPage(new URL(strUrl));
				//�]�w��ܸ��JURL���w������
			}
			catch(Exception ex){
				ex.printStackTrace();
				System.err.println("��}�s�����~ : " +  ex.getMessage());
			}
		}
	}

	//��ܰ�����J�T���������
	class URLLoadMsg extends Thread {

		public void run(){ //���J����

			setCursor(
				Cursor.getPredefinedCursor(
					Cursor.WAIT_CURSOR));
			//�]�w���ݸ��J��, �ϥΪ��ƹ����

			int i = 0;

			//�P�_���JURL���|���w�������O�_�s�b
			while(loader.isAlive()){

				StringBuffer msg = new StringBuffer("loading .");
				//���J�ʧ@���T��

				//��ܸ��J�ʧ@���r��
				for(int j = 0; j < (i%5); j++){
					msg.append(".");
				}

				lbLoading.setText(msg.toString());
				//�]�w������ܸ��J�ʧ@���r��

				i++;

				try{
					sleep(500); //��v0.5��
				}
				catch(Exception ex){
				}
			}

			lbLoading.setText("Ready");
			//�]�w�������J��, ���Ready�T��

			setCursor(
				Cursor.getPredefinedCursor(
					Cursor.DEFAULT_CURSOR));
			//�]�w��ܹw�]���ƹ����
		}
	};

	public static void main(String args[]) {
		new JBrowser();
	}
}

//�ŧi�ѨϥΪ̿�J���s�����}���զX���, 
//���զX����N�i�O�Фw�g��J�����}
class URLComboBox extends JComboBox {

	public URLComboBox(){	//�غc�l
		setMaximumRowCount(20);
		//�]�w�M��i��ܪ��ﶵ�Ӽ�
	}

	//�NURL���|�s�W�ܲզX������M��
	public void addItem(String strUrl){

		//���o�M��ﶵ���Ӽ�
		if(getItemCount() == 0){
			super.addItem(strUrl);
			//�N�ﶵ�[�J�M��

			return;
		}

		for(int i=0; i<getItemCount(); i++)	{
			if(((String)getItemAt(i)).equals(strUrl)) return;
			//�B�ΰj��P�_���[�JURL���|
			//�O�_�w�s�b��զX������M�椺
			//�Y�s�b�h�������j��
		}

		super.addItem(strUrl);
		//�NURL���|�[�J�զX������M�椺
	}

	public void save(String fileName){

		try{
			FileOutputStream fos = new FileOutputStream(fileName);
			//�إ߳B�z�N��ƿ�X�ܫ��w�ɮת�FileOutputStream����

			ObjectOutputStream oos = new ObjectOutputStream(fos);
			//�إ߰��檫���X��ObjectOutputStream����

			oos.writeObject(getModel());
			//�N�զX�����Model�����X���ɮ�

			oos.flush(); //�M��ObjectOutputStream�����y
			oos.close(); //���������X��y
			fos.close(); //�����ɮ׿�X��y
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.err.println(
				"�s����}�����x�s���~ : " + ex.getMessage());
		}
	}

	public void load(String fileName){

		try{
			File urlFile = new File(fileName);
			//�ŧi���w�ɮת�File����

			if(!urlFile.exists()) return;
			//�Y�ɮפ��s�b�h�פ��k������

			FileInputStream fis = new FileInputStream(urlFile);
			//�إ߱N��ƿ�J�ܫ��w�ɮת�FileInputStream����

			ObjectInputStream ois = new ObjectInputStream(fis);
			//�إ߿�J����ObjectInputStream����

			Object obj = ois.readObject();
			//�q�����J��yŪ������

			//�P�_�զX�������Model����
			if(obj instanceof ComboBoxModel){
				removeAllItems(); //�����Ҧ�����

				setModel((ComboBoxModel)obj);
				//�]�w�զX����ϥΪ�Model����

				setSelectedItem(null);
				//�]�w�զX��������
			}

			ois.close(); //���������J��y
			fis.close(); //�����ɮ׿�J��y
		}
		catch(Exception ex){
				ex.printStackTrace();
				System.err.println(
					"�s����}����Ū�����~ : " +  ex.getMessage());
		}
	}
}

//�ŧi��ťKeyEvent�ƥ󪺺�ť������
class ComboBoxAgent extends KeyAdapter {

	protected JComboBox cmb; //�Q��ť���զX�������
	protected JTextField  editor; //�զX�������ϥΪ���r��

	public ComboBoxAgent(JComboBox cmbObj){ //�غc�l
		cmb = cmbObj; //�]�w����ťKeyEvent�ƥ󪺲զX���

		editor = (JTextField)cmb.getEditor().getEditorComponent();
		//���o�զX������s�褸��

		editor.addKeyListener(this);
		//���U��ComboBoxAgent�����ť�զX�����KeyEvent�ƥ�
	}

	//�^����L����ʧ@����k
	public void keyReleased(KeyEvent e){

		char ch = e.getKeyChar(); //���o���䪺�r��

		if(ch == KeyEvent.VK_ENTER){ //�P�_�O�_�� Enter  ��޵o�ƥ�

			String url = (String) cmb.getSelectedItem();
			//�P�_�զX���������ت�URL�r��

			editor.setCaretPosition(url.length());
			//�]�w��Ц�m

			return;
		}

		if(ch == KeyEvent.CHAR_UNDEFINED 
			|| Character.isISOControl(ch))
			return;
		//�P�_�޵o�ƥ󪺫��䤧�r���O�_���w�q�εLISO����r��, 
		//�O�h��������k

		int  pos = editor.getCaretPosition();
		//���o��Цb�s�褸�󪺦�m

		String input = editor.getText(); //���o�s�褸�󪺤��e

		if(input.length() == 0) return;
		//�Y�s�褸�󪺤��e���׬�0, �h�פ�����k

		//�B��for�j��H��J���|���M�椺���ﶵ
		for(int k=0; k<cmb.getItemCount(); k++){

			String item = cmb.getItemAt(k).toString();
			//���o�զX������M��ﶵ, ���ഫ���r��

			//�H��J���r����M�檺�ﶵ
			if(item.startsWith(input)){
				editor.setText(item); //�]�w�s�褸�󪺤��e
				editor.setCaretPosition(item.length());
				//�]�w��Цb�s�褸�󪺦�m

				editor.moveCaretPosition(pos);
				//�N��в��ܫ��w��m

				break;
			}
		}
	}
}