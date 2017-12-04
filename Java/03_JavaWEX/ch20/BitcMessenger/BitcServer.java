import java.net.*; //���J�M��
import java.io.*;
import java.util.*;

//�B�z�s�u��BitcServer���A�����ϥΪ̪���������O
class UserThread extends Thread {
	private static HashMap<UserThread, String> 
		hmUser = new HashMap<UserThread, String>();
	//�x�s��������O�P�b���W�٪�Map�e��

	private Socket socket; //�x�s�B�z�s�u��Socket����
	private DataInputStream in;
	private DataOutputStream out;
	//�z�L�s�uŪ���ο�X�T������y����

	public UserThread(Socket socket) throws IOException {

		this.socket = socket; //�]�w�B�z�Ȥ�ݳs�u��Socket����

		in = new DataInputStream(
			new BufferedInputStream(socket.getInputStream()));
		out = new DataOutputStream(
			new BufferedOutputStream(socket.getOutputStream()));
		//�ŧi�z�L�s�u�B�z�T��Ū���P��X����y����
	}

	public String toString(){
		return socket.getInetAddress().getHostAddress();
		//�w�q��XUserThread��, �N�ǥX�s�u�q����IP��}
	}

	public void run() { //������������k

		String address = socket.getInetAddress().getHostAddress();
		//�H���A���W�٨��oIP��}

		String user = ""; //�ŧi�x�s�ϥΪ̦W�٪��r��

		try {
			user = in.readUTF(); //Ū���ϥ�UTF-8�s�X���T��
			hmUser.put(this, user);
			//�N���������P�������ϥΪ̱b���BIP�P��ͳq�T���J�e��

			System.out.println("�ثe�n�JBitcServer���A�����b����: " + hmUser.size());
			//��ܳs�u�Ӽ�

			Notify(hmUser.toString());
			//�I�sNotify()��k�N�n�J�b����Ƶo�e���Ҧ��s�u�b��

			System.out.println(in.readUTF());
			//Ū���Ȥ�ݶǰe�L�Ӫ��T��, �T���N�ϥ�UTF-8�s�X
			//�p�G���ݭn, �i�H�B��Client�ǹL�Ӫ��T��, �Ҧp�[�J���A�P�_

			System.out.println(user + "���u");
			//��ܨϥΪ����u�T��
		}
		catch (IOException e) {
			System.out.println(user + "���s�u�o�� [" + e.toString() + "] "
				 + "���~, �����s�u!");
		}
		finally{
			hmUser.remove(this); //�������������
			Notify(hmUser.toString());
			//�I�sNotify()��k�N�n�J�b����Ƶo�e���Ҧ��s�u�b��

			System.out.println("�ثe�n�JBitcServer���A�����b����: " + hmUser.size());
			try{
				socket.close();  //����Socket����
			}
			catch(IOException e) { }
		}
	}

	//�N�b���T���ǰe���Ҧ��s�u��BitcServer���A�����b��
	public static void Notify(String userData){
		synchronized(hmUser){ //�P�BhmUser�ݩ�

			Set<Map.Entry<UserThread, String>> stEntry = hmUser.entrySet();
			//�I�sentrySet()��k���o�]�tMap�e����������Set�e��

			//�H�[�j��for�j��t�X�]�tMap�e����������Set�e��,
			//�B�zMap�e�����Ҧ�����
			for(Map.Entry<UserThread, String> elm: stEntry){
        			try{
					UserThread currentUser = (UserThread)elm.getKey();
					//�I�sgetKey()��k�q�e�������o�B�z�n�JBitcServer���A�����ϥΪ̪���������O

					synchronized(currentUser.out){ 
						currentUser.out.writeUTF(userData);
						//�e�X�HUTF-8�s�X���T��
					}
					currentUser.out.flush();
				}
        			catch(IOException e) { }
			}
		}
	}
}

//�w�qBitcServer���A���D�{�����O
public class BitcServer {

	static final int port = 2004; //����BitcServer�Ȥ�ݵ{���s�u���q�T��

	public static void main(String args[]){
		try{
			ServerSocket server = new ServerSocket(port);
			//�ŧi��ť�q�T��ServerSocket����

			System.out.println("�Ұ�BitcServer���A�� (���U Ctrl + C ����)...");
			System.out.println("�ϥγq�T��: " + port);
			System.out.println("���ݥΤ�ݳs�u��......");

			while (true){  
				Socket client = server.accept(); //���o�s�u���Ȥ��Socket����
				System.out.println("�Ȥ�ݳs�u��IP��}: " 
						+ client.getInetAddress().getHostAddress());

				UserThread currentUser = new UserThread(client);
				//�إ߳B�z�n�JBitcServer���A�����ϥΪ̰T�������������

				currentUser.start();  //�Ұʰ����
			}
		}
		catch (Exception e){
			e.printStackTrace();
			return;
		}
	}
}