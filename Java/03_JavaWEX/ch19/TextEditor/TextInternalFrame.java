import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;

import java.awt.*;
import java.io.*;

//�H�~��JInternalFrame���O���覡�w�q�s���r�������ج[���O
class TextInternalFrame extends JInternalFrame {

	JTextPane tpContent = new JTextPane();
	//�ŧi��r�s�譱��

	String strFilePath = "C:\\���R�W.txt"; //�s���ɮת��w�]�ɮ׸��|
	String strFileName = "���R�W.txt"; //�s���ɮת��w�]�ɮצW��

	boolean isNew = true, //�P�O�s���ɮ׬O�_���s�}���ɮת����L��
		changed = false; //�P�O�s���ɮפ��e�O�_���ܪ����L��

	JCheckBoxMenuItem cbmi;
	//�N�����ج[���֨�����ﶵ,
	//�N�[�J��MDIEditor�ҫإߵ����ج[�������\���

	int fontSize = 16; //�]�w�r�Ťj�p��16
	
	TextInternalFrame(){ //�غc�l
		super(null, true, true, true, true); //�I�s��¦���O���غc�l
		initInternalFrame(); //�_�l�]�w�����ج[
	}
				
	TextInternalFrame(String path, String name){ //�غc�l

		super(null, true, true, true, true); //�I�s��¦���O���غc�l

		strFilePath = path; //�]�w�ɮ׸��|�P�W��
		strFileName = name; //�]�w�ɮצW��
		isNew = false; //�]�w�����s�}�Ҫ��ɮ�

		try{
			tpContent.read(new FileReader(strFilePath), null);
			//�H�ǤJ���ɮ׸��|�W�٫ŧiFileReader����
		}
		catch (IOException ioe) {
			System.err.println("�ɮ�Ū���o�Ͱ��D");
		}

		initInternalFrame(); //�_�l�]�w�����ج[
	}

	//���椺���ج[���_�l�]�w
	private void initInternalFrame(){
		getContentPane().add(new JScrollPane(tpContent));
		//�H��r�s�譱���إ߱��b�����M��[�J�����ج[�����e����
			
		Document doc = tpContent.getDocument();
		//���o��r�s�譱����Document����

		doc.addDocumentListener(dl); //���UDocument�����ť��
			
		setTitle(strFileName); //�]�w�����ج[�����D

		cbmi = new JCheckBoxMenuItem(strFileName);
		//�H�����ج[�s�褧�ɮת��W�٫ŧi�֨�����ﶵ

		tpContent.setFont(
			new Font("Times-Roman", Font.BOLD, fontSize));
		//�]�w��r�s�譱���ϥΪ��ۦ�

		setSize(300, 200); //�]�w���������ج[���j�p

		setVisible(true); //�]�w��ܤ��������ج[
	}

	//���U�^��CaretEvent�ƥ󪺺�ť��
	public void addCaretListener(CaretListener cl){
		tpContent.addCaretListener(cl);
	}

	//���������ج[�e�ҩI�s����k
	public void doDefaultCloseAction(){
		//�P�_�����ج[�s�誺�ɮ׬O�_����, 
		//����ܽT�{����n�D�ϥΪ̨M�w�O�_�x�s
		if(changed == true &&
			JOptionPane.showConfirmDialog(
				this, "�ɮש|���x�s�n������?", "�T��",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE)
					== JOptionPane.NO_OPTION){
			return;				
		}

		super.doDefaultCloseAction(); //�I�s��¦���O����k
	}

	public boolean isNew(){ return isNew; }
	//���o�����ج[�s���ɮ׬O�_���s�W
	
	public boolean isChanged(){ return changed; }
	//���o�����ج[�s���ɮ׬O�_����

	public void setFileName(String name){
	//�]�w�����ج[�s���ɮת��W��

		setTitle(name); //��s�����ج[���s���ɮת��W��
		cbmi.setText(name); //��s�֨�����ﶵ��ܪ��ɮצW��
		strFileName = name; //��s�ݩʳ]�w
	}

	public void setFilePath(String path){ strFilePath = path; }
	//�]�w�ɮ׸��|

	public String getFileName(){ return strFileName; }
	//���o�ɮצW��

	public String getFilePath(){ return strFilePath; }
	//���o�ɮ׸��|

	public JTextPane getTextPane(){ return tpContent; }
	//���o��r�s�譱��

	//�]�w�r�Ťj�p
	public void setFontSize(int size){
		tpContent.setFont(new Font("Times-Roman", Font.BOLD, size));
		//�]�w��r�s�譱���ϥΪ��r�Ťj�p

		fontSize = size; //��s�ݩ�
	}

	public int getFontSize(){ return fontSize; }
	//���o�ϥΪ��r�Ťj�p

	public JCheckBoxMenuItem getMenuItem(){ return cbmi; }
	//���o�N�����ج[���֨�����ﶵ

	//�N�����ج[�s�誺���e��X��Writer
	public void write(Writer out) throws IOException {
		changed = false;
		//��s�ݩʪ�ܽs���ɮ��ܧ󪺤��e�w�g�x�s
		tpContent.write(out); //��X�s�褺�e
	}

	//�w�q�ëŧi�^��DocumentEvent�ƥ󪺺�ť��
	DocumentListener dl = new DocumentListener(){
		public void changedUpdate(DocumentEvent e){ }

		//�^����Ʒs�W�ʧ@
		public void insertUpdate(DocumentEvent e){ changed = true; }

		//�^����Ʋ����ʧ@
		public void removeUpdate(DocumentEvent e){ changed = true; }
	};
}