import java.io.*;
import java.util.*;

import cz.dhl.io.*;
import cz.dhl.ftp.*;

//�~��Observable���O�N�Ω�B�zFTP�s�u
//�����O�N�i�Q��@Observer���������O��ť
class FtpAgent extends Observable {

	public static final int DOWNLOAD = 0;
	public static final int UPLOAD = 1;
	public static final int CONNECT = 2;
	public static final int DISCONNECT = 3;
	//�ŧi�N��s�u���A���`��

	private int status = DISCONNECT;
	//�ŧi�x�s�s�u���A���ݩ�, �w�]�Ȭ������s�u

	private Ftp cl; //�ŧi�N��FTP�s�u���ݩ�
	private FtpFile curPath; //�ŧi�N��s�u���|���ݩ�
	private String host, user, password;
	//�ŧiFTP�s�u�����A����}�B�ϥΪ̱b���P�K�X

	//�غc�l
	public FtpAgent(String host, String user, String password){
		this.host = host;
		this.user = user;
		this.password  = password;
		//�]�w�s�u�����A����}�B�ϥΪ̱b���P�K�X
	}

	public void connect() throws IOException {

		FtpConnect cn = new FtpConnect();
		//�ŧi�x�sFTP�s�u��ƪ�����

		cn.setHostName(host); //�]�w����s�u��FTP���A����}
		cn.setUserName(user); //�]�wFTP�s�u���ϥΪ̱b��
		cn.setPassWord(password); //�]�wFTP�s�u���K�X

		cl = new Ftp(); //�ŧiFTP�s�u�B�z����
		cl.connect(cn); //�إ߳s�u�õn�J���w��FTP���A��

		curPath = new FtpFile(cl.pwd(), cl);
		//���o�ثe��FTP�s�u���A�����ɮ׸��|

		status = CONNECT; //�]�w���A���s�u
		stateChanged(); //��s�s�u���A
	}

	public void disconnect(){ //���o�s�u
		cl.disconnect(); //�פ�FTP�s�u
		cl = null; //�]�wFTP�s�u��null
		status = DISCONNECT; //�]�w�s�u���A���_�u
		stateChanged(); //��s�s�u���A
	}

	public int getStatus(){ return status; }
	//���o�s�u���A

	public Ftp getConnect(){ return cl; }
	//���oFTP�s�u

	public FtpFile getCurrentPath(){ return curPath; }
	//���o�ثe���s�u���|

	public String getHost(){ return host; }
	//���oFTP���A������}

	public String getUser(){ 	return user; }
	//���o�ϥΪ̱b��

	public String  getPassword(){ return password; }
	//���o�ϥΪ̱b�����K�X

	public void upload(File from) throws IOException {
	
		CoFile source = new LocalFile(from.getPath());
		//�Ȥ�ݱ�����W�Ǫ��ɮ�

		CoFile to = new FtpFile(cl.pwd() + from.getName(), cl);
		//���A�ݥN��ѫȤ�ݤW�Ǫ��ɮ�

		CoLoad.copy(to, source);
		//�N�Ȥ���ɮ׽ƻs�ܦ��A��

		status = UPLOAD; //�]�w�s�u���A���W��

		stateChanged(); //��s�s�u���A
	}

	public void download(FtpFile from, File dest)
		throws IOException {

		CoFile to = new LocalFile(dest.getPath());
		//�x�s�q���A�ݤU���ɮפ��Ȥ���ɮ�

	   CoLoad.copy(to, from); //�q���A�ݤU���ɮצܫȤ��

	   status = DOWNLOAD; //�]�w�s�u���A

	   stateChanged(); //��s�s�u���A
	}

	private void stateChanged(){ //��s�s�u���A
		setChanged(); //�]�w����w�g���ͧ���
		notifyObservers(); //�q���w���ͪ��󪺧���
	}
}