import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*; //�ޥΥ]�tItemListener�������M��

import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

import java.io.*; //�ޥΩw�qFile���O���M��

public class MemComboEX extends JFrame{

	final static String FILE_PATH = "data\\item.dat";
	//�x�s�զX����M��ﶵ���e���ɮ�

	MemComboBox mcbBank = new MemComboBox(FILE_PATH);
	//�H�]�t�ﶵ���e���r��إ߲զX���

	JLabel lbSelBank = new JLabel("��ܿ�����G");
	//��ܲզX���������G������

	String strMsg = "����ο�J�Ȧ�W��";

	//�w�q�ëŧi�^���b�զX�����
	//���U Enter ��Ĳ�o��ActionEvent����ť������
	ActionListener al = new ActionListener(){
		public void actionPerformed(ActionEvent e){

			MemComboBox source = (MemComboBox)e.getSource();
			//���o�޵o�ƥ�MemComboBox����

			String strInp = (String) source.getSelectedItem();
			//���o�Q�������

			//�Y�Q��������ؤ��e�P�զX�����ܪ��r��ۦP,
			//�h�פ�ƥ�^��
			if(strInp.equals(strMsg))
				return;

			source.addItem(strInp);
			//���o�Q�������
		}
	};

	MemComboEX(){

		mcbBank.setSelectedIndex(-1); //�]�w�w�]���������ﶵ

		ComboBoxEditor cmbeBank = mcbBank.getEditor();
		//���o�զX������e�s�褸��

		mcbBank.configureEditor(cmbeBank, strMsg);
		//�]�w���e�s�褸�󪺹w�]��

		mcbBank.addItemListener(new ItemListener(){

			//�^�����ܿﶵ���A���ʧ@
			public void itemStateChanged(ItemEvent e){

				if(e.getStateChange() == ItemEvent.SELECTED)
					lbSelBank.setText(mcbBank.getSelectedItem()
						+ "[" + mcbBank.getSelectedIndex() + "]");
			}
		});

		//���U�^��mcbBank����ActionEvent�ƥ󪺺�ť��
		mcbBank.addActionListener(al);

		//���U��ťWindowEvent�ƥ󪺺�ť������,
		//�ùB���������������|�x�s�զX�����Model����
		addWindowListener(new WindowAdapter(){

			//�^�����������ƥ�
			public void windowClosing(WindowEvent e){
				mcbBank.save(FILE_PATH);
				System.exit(0);
			}
		});

		//�إߥ]�t�U����Box�e��, �ñN����[�J
		Box bxBank = new Box(BoxLayout.X_AXIS);
		bxBank.add(Box.createHorizontalStrut(10));
		bxBank.add(new JLabel("�פJ�Ȧ� : ", JLabel.RIGHT));
		bxBank.add(mcbBank);
		bxBank.add(Box.createHorizontalStrut(10));
		bxBank.add(new JLabel("������G : ", JLabel.RIGHT));
		bxBank.add(lbSelBank);
		bxBank.add(Box.createHorizontalGlue());

		bxBank.setBorder(new EmptyBorder(5, 5,  5, 5));
		//�]�w�e�׬�5���ťծؽu

		getContentPane().add(bxBank); //�NBox�e���[�J���e����

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(450, 80);
		setVisible(true);
	}

	public static void main(String args[]) {
		new MemComboEX(); //�ŧi�����ج[����
	}
}

//�w�q�i�N�ﶵ����x�s���ɮ�,
//�P�q�ɮר��o�ﶵ���, �i�O�пﶵ���զX���
class MemComboBox extends JComboBox {

	public MemComboBox(Object[] items){
		super(items); //�I�s��¦���O���غc�l

		initial(); //�_�l�զX���
	}

	public MemComboBox(){
		initial(); //�_�l�զX���
	}

	public MemComboBox(String fileName){
		initial(); //�_�l�զX���

		load(fileName); //���J�x�s�ﶵ���e���ɮ�
	}

	private void initial(){
		setMaximumRowCount(20);
		//�]�w�զX������M��i�C�X�ﶵ���̤j����

		setEditable(true); //�]�w��r����i����s��
	}

	//�P�_�M�椺�O�_�����ƪ��ﶵ,
	//�Y�L�h�s�W�ﶵ
	public void addItem(Object item){

		//�Y�M�椺�S���ﶵ�h�����N�ﶵ�[�J
		if(getItemCount() == 0){
			super.addItem(item);
			return;
		}

		//�P�_�M�椺�O�_�w�g�s�b�ۦP���ﶵ,
		//�O�h�������
		for(int i=0; i<getItemCount(); i++)	{
			if((getItemAt(i)).equals(item)){
				return;
			}
		}

		super.addItem(item); //�N�M�椺�S�����ﶵ�[�J�M��
	}

	//�N�x�s�զX������M��ﶵ��Model����g�J���w�ɮ�
	public void save(String fileName){
		try{			
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			//�ŧi�NModel�����X�ܫ��w�ɮת���X��y����

			oos.writeObject(getModel());
			//���o�զX����ϥΪ�Model����, �üg�J�ɮ�

			oos.flush(); //�M�ſ�X��y, �ñN��Ƽg�J�w�İ�
			oos.close(); //���������X��y
			fos.close(); //�����ɮ׿�X��y
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	//�q���w�ɮ׸��J�x�s�զX����M�椺�e��Model����
	public void load(String fileName){
		try {
			File file = new File(fileName);
			//�ŧi�N���x�sModel�����ɮת�File

			if(!file.exists()){ //�P�_�O�_�i���T���o�ɮ�
				String[] strBank = {"�l��", "�صػȦ�", "���ذӻ�", "�X�@���w"};
				//�ŧi�إ߲զX����ﶵ���e���r��}�C

				setModel(new DefaultComboBoxModel(strBank));
				//�HString����}�C�ŧiModel����

				return;
			}

			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			//�ŧiŪ��Model���󪺿�J��y����

			Object obj = ois.readObject(); //�q��yŪ������

			//�P�_���o���󤧫��O�O�_��ComboBoxModel
			if(obj instanceof ComboBoxModel){
				removeAllItems(); //�����Ҧ��ﶵ
				setModel((ComboBoxModel)obj);
				//�]�w�զX����ϥ�Model���󪺤��e�����ﶵ
			}
			ois.close(); //���������J��y
			fis.close();  //�����ɮ׿�J��y
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}