import java.net.*; //���J�����M��
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

//�w�q�P��L�ϥΪ̥�ͪ���ѵ���
class TalkFrame extends JFrame implements Runnable {

	private JTextArea taMsg = new JTextArea(20, 40);
	private JTextField input = new JTextField(30);
	private JButton btnSend = new JButton("�e�X");

	private Socket talkSocket = null;
	private String name, talkTo;
	private Thread talkThread; //�����ͪ������

	DataInputStream in; //���o�q�s�uŪ����ƪ�InputStream����
	DataOutputStream out; //���o��X��Ʀܳs�u��OutputStream����

	//��Ѩ�L�q���D�ʶǰT�L�Ӷi���ͮ�, �ŧi��͵������غc�l,
	//�åH�ǤJ�N��s�u��Socket����P�������b���W�٫إߵ���
	TalkFrame(Socket socket, String name){
		talkSocket = socket; //�]�w�B�z�i���ͤ��s�u��Socket����		
		this.name = name;		

		try{
			in = new DataInputStream(
				new BufferedInputStream(talkSocket.getInputStream()));
			//���o�q�s�uŪ����ƪ�InputStream����, �ëإ߬�DataInputStream����

			out = new DataOutputStream(
				new BufferedOutputStream(talkSocket.getOutputStream()));
			//���o��X��Ʀܳs�u��OutputStream����, �ëإ߬�DataOutputStream����

			talkTo = in.readUTF();  //�HUTF-8�s�XŪ���r��
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}

		setTitle(talkTo + " �Q�n�P�z���"); //�]�w��͵������D

		initPane(); //�_�l��͵���

		talkThread = new Thread(this); //�إ߰����ͪ������
		talkThread.start(); //�Ұʰ����
	}

	//���D�ʱN�T���ǻ�����L�q���i���ͮ�, �ŧi��͵������غc�l,
	//�N�H��͹�H�W�١B��}�P�������b���W�٦W�٫إߥ�͵���
	TalkFrame(String talkTo, String add, String name, int talkPort){
		super("�P " + talkTo + " ���");

		this.name = name; //�]�w�������W��
		this.talkTo = talkTo; //�]�w��͹�H���W��		

		try{
			talkSocket = new Socket(add, talkPort);
			//�ŧi�B�z�����ͤ��s�u��Socket����

			in = new DataInputStream(
				new BufferedInputStream(talkSocket.getInputStream()));
			//���o�q�s�uŪ����ƪ�InputStream����, �ëإ߬�DataInputStream����

			out = new DataOutputStream(
				new BufferedOutputStream(talkSocket.getOutputStream()));
			//���o�q�s�uŪ����ƪ�OutputStream����, �ëإ߬�DataOutputStream����

			out.writeUTF(name); //�N�ۤv���b���W�ٿ�X����͹�H
			out.flush();
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}

		initPane(); //�_�l��͵���

		talkThread = new Thread(this);  //�إ߰����ͪ������
		talkThread.start(); //�Ұʰ����
	}

	private void initPane(){ //�_�l��͵���

		Box bxInput = new Box(BoxLayout.X_AXIS);
		bxInput.add(input); //�[�J��J��ͰT������r���
		bxInput.add(Box.createHorizontalStrut(10));
		bxInput.add(btnSend); //�[�J�ǥX�T�������s

		//�w�q�^��ActionEvent�ƥ󪺺�ť��
		btnSend.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String send = input.getText();
				//���o�q��r������o��ͰT��

				if(send.length() == 0)
					return;

				taMsg.append(name + " �� : " + send + "\n");
				//�N�T���[�J��r��

				input.setText(null); //���]��r��������e
				try{
					out.writeUTF(send); //�N�T���Ǧܥ�͹�H
					out.flush();
				}
				catch(Exception ex){
					System.out.println(ex.toString());
				}
			}
		});

		taMsg.setEditable(false);  //�]�w��r�Ϥ��i����s��

		Container cp  = getContentPane();
		cp.add(taMsg); //�N��r�ϥ[�J��͵���
		cp.add(bxInput, BorderLayout.SOUTH);
		//�N��J�T������r����[�J��͵���

		addWindowListener(wa);

		//�]�w��������m�B�����j�p, ����ܵ���
		setLocation(100, 100);		
		setSize(400, 300);
		setVisible(true);
	}

	//�w�q�å[�J�B�z�����T������ť��
	WindowAdapter wa = new WindowAdapter() {

		public void windowClosing(WindowEvent e){ //�^�����������T������k
			try{
				talkThread.interrupt();	//�j��_�����

				talkSocket.close(); //�����s�u
			}
			catch(Exception ex){
				System.out.println(ex.toString());
			}
		}
	};

	public void run(){ //�B�z��ͰT���������
		try{
			while(talkSocket.isConnected()){ //�P�_�O�_����s�u
				String msg = in.readUTF(); //�HUTF-8�s�XŪ���T��

				taMsg.append(talkTo + " �� : " + msg + "\n");
				//�N�T���[���r��
			}
		}
		catch(SocketException se){
			System.out.println("�D�ʤ��_�s�u");
		}
		catch(EOFException ee){
			btnSend.setEnabled(false);
			taMsg.append("���s�u�w�g���_......");
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}
	}
}

//�w�q��ܵn�JBitcServer���A��, �i�z�L������ͪ��ϥΪ�
public class BitcClient extends JFrame implements Runnable{

	static final int port = 2004; //BitcServer���ѳs�u���q�T��s��
	static int talkPort; //�����ͮɹB�Ϊ��q�T��s��
	String serverName; //�n�J���A�����W��

	private DataInputStream in; //�q�s�uŪ����ƪ�InputStream����
	private DataOutputStream out; //��X��Ʀܳs�u��OutputStream����
	private String userAddPort; //�H�ϥΪ̦W�ٻP��}�إߪ��b���W��
	private String userName; //�ϥΪ̦W��
	private Thread client; //�P���A���s�u�������

	DefaultListModel dlmUser = new DefaultListModel();
	//�B�z�M������ܤ��e��DefaultListModel����

	JList lsUser = new JList(dlmUser);
	JButton btnLogin = new JButton("�n�J");
	JButton btnLogout = new JButton("�n�X");

	Socket socket = null; //�B�z�PBitcServer���A���s�u��Socket����

	public BitcClient(String server, String user){
	//�ŧiBitcServer�Y�ɳs�u�Ȥ�ݵ{�����غc�l

		super("Bitc Messenger : �ڬO" + user);

		userName = user;
		Random rand = new Random(); //�ŧi���Ͷüƪ�Random����
		talkPort = rand.nextInt(100) + 1024;
		//�H�üƤ覡���w��ͨϥΪ��q�T��

		try{
			this.userAddPort = user + "@" 
				+ InetAddress.getLocalHost().getHostAddress() 
				+ ":" + talkPort;  //�b���W��
		}
		catch(UnknownHostException uhe){ System.out.println(uhe.toString());}

		serverName = server;  //�]�w�n�J�����A��

		lsUser.addMouseListener(ma);
		//���U�^���M������MouseEvent�ƥ󪺺�ť��

		btnLogin.addActionListener(alLogin);
		//���U�^�� �n�J ���s��ActionEvent�ƥ󪺺�ť��

		btnLogout.addActionListener(alLogout);
		//���U�^�� �n�X ���s��ActionEvent�ƥ󪺺�ť��

		btnLogout.setEnabled(false); //�]�w �n�X ���s�L��

		Box bxBtn = new Box(BoxLayout.X_AXIS);
		bxBtn.add(btnLogin); //�N �n�J ���s�[�JBox�e����
		bxBtn.add(Box.createHorizontalStrut(10));
		bxBtn.add(btnLogout); //�N �n�X ���s�[�JBox�e����

		Container cp = getContentPane(); //���o���e����
		cp.add(lsUser); //�N�ϥΪ̲M�����[�J���e����

		cp.add(bxBtn, BorderLayout.SOUTH);
		//�N�]�t �n�J �P �n�X ���s��Box�e���[�J���e����

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
	}

	public void run(){ //�B�z�q���A���Ǩӵn�J�b��
		try{
			out.writeUTF(userAddPort);
			//�HUTF-8�s�X�e�X�ϥΪ̦W�٦r��

			out.flush();

			while(true){  //�Hwhile�j�鵥��Ū���T��
				String temp = in.readUTF();
				//Ū��BitcServer���A���ǰe�L�ӥ]�t�n�J�ϥΪ̱b�����T��
				//�T���N�ϥ�UTF-8�s�X

				dlmUser.clear(); //�M����ܵn�J�ϥΪ̪��M����
				String userData = temp.substring(1, temp.length()-1);
				//�NŪ���쪺�T���h���Y�����L�Φr��

				String[] aryTempItem = userData.split(", ");
				//�H", "�����j�r��N�T�����ά��r��}�C

				for(String tempItem: aryTempItem){
					String[] aryTemp = tempItem.split("=");
					//�H"="�����j�r��N�T�����ά��r��}�C

					//�P�_�r��ä��O�N��ۤv���b��, �M��~�N�r��[�J�M����
					if(!userAddPort.equals(aryTemp[1])){
						dlmUser.addElement(aryTemp[1]);
						//�N��ܱb�����r��[�J�M����
					}
				}

				dlmUser.addElement("�ثe�u�W�� " + dlmUser.getSize() + " ��B��");
			}
		}
		catch(SocketException se){ }
		catch(IOException e){
			System.out.println(e.toString());			
		}
		finally{
			try{
				in.close(); //������y
				out.close(); 
			}
			catch (IOException e2) { }
		}
	}

	MouseAdapter ma = new MouseAdapter(){ //�^��MouseEvent�ƥ󪺺�ť��
		public void mouseClicked(MouseEvent e){
			if (e.getClickCount() == 2) { //�P�_�ƹ�������U������
				JList source = (JList)e.getSource();
				//���o�M�����Q�I�諸����

				String[] selUser = 
					((String)source.getSelectedValue()).split("@");
				//�N�Q�I�ﶵ�ت����e, �̷� @ �r�����j����ӳ���

				String[] IP_Port = selUser[1].split(":");

				new TalkFrame(selUser[0], IP_Port[0], 
											userName, Integer.parseInt(IP_Port[1]));
				//�ŧi�����ͪ�����
			}
		}
	};

	//�w�q�ëŧi�^�� �n�J ���s��ActionEvent�ƥ󪺺�ť��
	ActionListener alLogin = new ActionListener(){
		public void actionPerformed(ActionEvent e){

			try{
				socket = new Socket(serverName, port);
				//�إ߻PBitcServer���A���s�u��Socket����

				in = new DataInputStream(
					new BufferedInputStream(socket.getInputStream()));
				out = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));
				//�إ߳B�z�s�u��ƿ�X�P��J����y����

				btnLogin.setEnabled(false); //�]�w �n�J ���s����
				btnLogout.setEnabled(true); //�]�w �n�X ���s����

				client = new Thread(BitcClient.this);
				//�ŧi�PBitcServer���A���s�u�������

				client.start(); //�Ұʰ����

				(new WaitTalk()).start();
				//�ŧi�Ұʵ��ݳB�z�T����ͪ������
			}
			catch(Exception ex){
				System.out.println(ex.toString());
			}
		}
	};

	//�w�q�ëŧi�^�� �n�X ���s��ActionEvent�ƥ󪺺�ť��
	ActionListener alLogout = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			try{
				out.writeUTF("Bye!"); //��X�����T����BitcServer���A��
				out.flush();
				client.interrupt(); //�j��_�����
				socket.close(); //�����s�u

				dlmUser.clear(); //�M����ܵn�J�ϥΪ̱b�����M����

				btnLogin.setEnabled(true); //�]�w �n�J ���s����
				btnLogout.setEnabled(false); //�]�w �n�X ���s����
			}
			catch(Exception ex){
				System.out.println(ex.toString());
			}
		}
	};

	//���ݨ�L�ϥΪ̳s�u��ͪ������
	class WaitTalk extends Thread {

		public void run(){			
			try{
				ServerSocket server = new ServerSocket(talkPort);
				//�H���w���q�T��ŧi���ݳs�u��ServerSocket����

				System.out.println("��ť " + talkPort + " �q�T�𵥫ݳs�u...");

				while(true){
					Socket client = server.accept(); //���o�s�u���Ȥ��Socket����
					new TalkFrame(client, userName); //�ŧi��͵���
				}
			}
			catch(IOException e){
				System.out.println(e.toString());			
			}
		}
	}

	public static void main (String... args) throws Exception {
		if (args.length != 2) {
			System.out.println("�ϥλy�k: BitcClient <���A���W��> <�ϥΪ̱b��>");
			return;
		}

		new BitcClient(args[0], args[1]); //�ŧiBitcServer�Y�ɳs�u�Ȥ�ݪ���
	}
}