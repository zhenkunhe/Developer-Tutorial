import javax.swing.*;  //�ޥήM��
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import java.text.*; //����DateFormat���M��
import java.util.*; //����Locale���M��
import javax.swing.text.*;  //����DefaultFormatter, �Ψ�l���O���M��

import java.beans.*; //����PropertyChangeListener�������M��

public class VerifierEX extends JFrame {

	//�w�q�榡�Ƹ�ƿ�J���˦�
	static final String ID_PATTERN = "?#########"; //�����Ҹ��X���B�n�˦�
	static final String PHONE_PATTERN = "(##*)###*-###*"; //�q�ܪ��B�n�˦�

	JFormattedTextField ftfName = new JFormattedTextField(new String("���s�i")),
					   ftfID,
					   ftfBirthday = new JFormattedTextField(),
					   ftfPhone,
					   ftfSalary;
	//�ŧiJFormattedTextField����

	JLabel lbMsg = new JLabel("������ҰT��");

	VerifierEX(){

		try{
			ftfID = new JFormattedTextField(
						new DefaultFormatterFactory(new MaskFormatter(ID_PATTERN)),
						new String("A154688882"));
			//�ŧiJFormattedTextField����,
			//�öǤJ�إ�MaskFormatter�榡����
			//DefaultFormatterFactory����P�w�]��

			ftfID.setInputVerifier(new IDVerifier()); //�]�w���Ҩ����Ҹ��X������

			MaskFormatter mfPhone = new MaskFormatter(PHONE_PATTERN);
			//�ŧiMaskFormatter����

			mfPhone.setPlaceholderCharacter('_'); //�]�w�����ܿ�J��m���Х�
			mfPhone.setValidCharacters("0123456789 ");
			//�]�w�i�������r����0��9���Ʀr�P�ť�

			ftfPhone = new JFormattedTextField(mfPhone);
			//�ŧiJFormattedTextField����,
			//�����ǤJ���ϥΪ�MaskFormatter�榡����
		}
		catch(ParseException pe){
			pe.printStackTrace();
		}

		ftfPhone.setValue(new String("(07 )557 -5586"));
		//�]�wvalue�ݩ�

		ftfPhone.setInputVerifier(new PhoneFormatVerifier(ID_PATTERN)); //�]�w���ҹq�ܸ��X�����Ҫ���
		DateFormat dfDate = 
						new SimpleDateFormat("yyyy/MM/dd", Locale.US);
		//�ŧi����榡

		ftfBirthday.setFormatterFactory(
						new DefaultFormatterFactory(new DateFormatter(dfDate)));
		//�]�w���ͮ榡���󪺫إ߾�

		Calendar date = Calendar.getInstance(Locale.TAIWAN);
		//���o��ܤ��Calendar����

		date.set(1976, 5, 16);
		//�]�w����Ȭ�1976/6/16, ����ȱq0�}�l, �G�H5�N��6��

		ftfBirthday.setValue(date.getTime()); //�]�wvalue�ݩ�

		ftfBirthday.setInputVerifier(new DateVerifier(new Date()));
		//�]�w�����ƪ����Ҫ���

		NumberFormat nfMoney = NumberFormat.getCurrencyInstance(Locale.TAIWAN);
		//���o��F�x�W�ϳf����O��Fromat����

		ftfSalary = new JFormattedTextField(nfMoney);
		//�ŧiJFormattedTextField����,
		//�B�Ϊ��榡����N�ϥ�Format�榡

		NumberFormatter nfrSalary = 
							(NumberFormatter)ftfSalary.getFormatter();
		//���oJFormattedTextField����ϥΪ��榡����

		nfrSalary.setMinimum(new Double(18500)); //�]�w���\���̤p��
		nfrSalary.setMaximum(new Double(70000)); //�]�w���\���̤j��

		ftfSalary.setValue(new Double(40000)); //�]�wvalue�ݩ�

		JPanel jpMain = new JPanel(new GridLayout(5, 2,  5, 5));
		jpMain.add(new JLabel("�m�W : ", JLabel.RIGHT)); //�N����[�JJPanel�l�e��
		jpMain.add(ftfName);
		jpMain.add(new JLabel("�����Ҧr�� : ", JLabel.RIGHT));
		jpMain.add(ftfID);
		jpMain.add(new JLabel("�ͤ� : ", JLabel.RIGHT));
		jpMain.add(ftfBirthday);
		jpMain.add(new JLabel("�p���q�� : ", JLabel.RIGHT));
		jpMain.add(ftfPhone);
		jpMain.add(new JLabel("�~�� : ", JLabel.RIGHT));
		jpMain.add(ftfSalary);

		JPanel jpLabel = new JPanel(new GridLayout(1, 1,  5, 5));
		jpLabel.add(lbMsg);

		Container cp = getContentPane(); //���o���e����
		cp.add(jpMain); //�N�����[�J���e����
		cp.add(jpLabel, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���������N�w�]�����{��
		setSize(300, 250); //�]�w�����ج[����ܤj�p
		setVisible(true); //��ܵ����ج[
	}

	//���ҿ�J�������Ҹ��X�O�_���T
	class IDVerifier extends InputVerifier {

		public boolean verify(JComponent input){
		
			//�P�_���������Ҫ�����O�_��JFormattedTextField
			if(!(input instanceof JFormattedTextField))
				return true;

			JFormattedTextField ftf = (JFormattedTextField) input; //�ഫ���O

			String ID = (String) ftf.getText(); //���o��쪺�ݩʭ�

			if (ID.length() != 10) //�P�_��J�r�ꪺ����
				return false;
			
			ID = ID.toUpperCase(); //�N�^��r�����ഫ���j�g
			byte s[] = ID.getBytes();
			//�N�Ҧ��r���ഫ��byte���O, byte�ȧY���r����ASCII�X

			if (s[0] >= 65 && s[0] <= 90) { //�P�_�Ĥ@�Ӧr���O�_�^��r��

				int a[] = {10, 11, 12, 13, 14, 15, 16, 17, 34, 18, 19, 20, 21,
					          22, 35, 23, 24, 25, 26, 27, 28, 29, 32, 30, 31, 33};
				//�x�s�P�^��r����������ƭ�

				int count = (a[ s[0] - 65] / 10)  + (a[ s[0] - 65] % 10) * 9;
				//�N������Ĥ@�Ӧr������ƭ�, �Q�쪺�Ʀr�[�W�Ӧ쪺�Ʀr���H9

				for (int i = 1; i <= 8; i++) {
					count += (s[i] - 48) * (9 - i); //�N�Ʀr�̧ǭ��W1��8���Ʀr, �M��֥[
				}

				//�H10��h�֥[���G���Ӧ�Ʀr, �M��P�ˬd�X���
				if ((10 - (count % 10)) == (s[9] - 48)) {
					lbMsg.setText(" [" + ID + "] �q�L����! ");
					return true;
				}
			}

			lbMsg.setText(" [" + ID + "] �L�k�q�L����! ");
			return false;
		}
	}

	//�w�q���ҹq�ܸ��X�榡�O�_���T�����O
	class PhoneFormatVerifier extends InputVerifier {

		String pattern;

		PhoneFormatVerifier(String phonePattern) { //�غc�l
			pattern = phonePattern;
		}

		public boolean verify(JComponent input) {

			//�P�_���������Ҫ�����O�_��JFormattedTextField
			if(!(input instanceof JFormattedTextField))
				return true;

			JFormattedTextField ftf = (JFormattedTextField) input; //�ഫ���O

			JFormattedTextField.AbstractFormatter
							formatter = ftf.getFormatter(); //���o�榡����

			if(formatter == null){
				lbMsg.setText("�q�ܸ��X�榡�q�L����");
				return true;
			}

			try{
				formatter.stringToValue((String)ftf.getValue()); //�ഫ��줺����
				lbMsg.setText("�q�ܸ��X�榡�q�L����");
				return true;
			}
			catch(ParseException pe){
				lbMsg.setText("�榡���~, ���T���榡��" + pattern);
				return false;
			}
		}
	}

	//�ˬd��J����ȬO�_���T���������O
	class DateVerifier extends InputVerifier {

		Date d;
	
		public DateVerifier(Date d){
			this.d = d;
		}

		public boolean verify(JComponent input) {

			//�P�_���������Ҫ�����O�_��JFormattedTextField
			if(!(input instanceof JFormattedTextField))
				return true;

			JFormattedTextField ftf = (JFormattedTextField) input; //�ഫ���O

			if(d.after((Date)ftf.getValue())){ //�ˬd��J����ȬO�_�b���w�����
				lbMsg.setText("�����J�ȳq�L����");
				return true;
			}

			lbMsg.setText("����ߩ�" + d + ", ��J�Ȥ����T! ");
			return false;
		}
	}

	public static void main(String args[]) {
		new VerifierEX(); //�ŧi�����ج[����
	}
}