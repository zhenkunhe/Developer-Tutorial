import javax.swing.*;  //�ޥήM��
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import java.text.*; //����DateFormat���M��
import java.util.*; //����Locale���M��
import javax.swing.text.*;  //����DefaultFormatter, �Ψ�l���O���M��

import java.beans.*; //����PropertyChangeListener�������M��

public class FormattedEX extends JFrame {

	//�w�q�榡�Ƹ�ƿ�J���˦�
	static final String ID_PATTERN = "?#########"; //�����Ҹ��X���B�n�˦�
	static final String PHONE_PATTERN = "(##*)###*-###*"; //�q�ܪ��B�n�˦�

	JFormattedTextField ftfName = new JFormattedTextField(new String("���s�i")),
					   ftfID,
					   ftfBirthday = new JFormattedTextField(),
					   ftfPhone,
					   ftfSalary;
	//�ŧiJFormattedTextField����

	JLabel lbName = new JLabel("�m�W��쪺text�ݩ� :       value�ݩ� : ");
	JLabel lbMsg = new JLabel("���o���ƭ� : ");

	FormattedEX(){

		//��ť��в��ʨƥ�, �����text�ݩʻPvalue�ݩʤ��Ȫ��ܤ�
		ftfName.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e){
				JFormattedTextField ftfSource = (JFormattedTextField) e.getSource();

				lbName.setText("�m�W��쪺text�ݩ� : " 
											+ ftfSource.getText()
											+ "  value�ݩ� : " + ftfSource.getValue());
			}
		});

		//��ťAction�ƥ�, �����text�ݩʻPvalue�ݩʤ��ܤ�
		ftfName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				JFormattedTextField ftfSource = (JFormattedTextField) e.getSource();

				lbName.setText("�m�W��쪺text�ݩ� : " 
											+ ftfSource.getText()
											+ "  value�ݩ� : " + ftfSource.getValue());
			}
		});

		try{
			ftfID = new JFormattedTextField(
						new DefaultFormatterFactory(new MaskFormatter(ID_PATTERN)),
						new String("A154688887"));
			//�ŧiJFormattedTextField����,
			//�öǤJ�إ�MaskFormatter�榡����
			//DefaultFormatterFactory����P�w�]��

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

		NumberFormat nfMoney = NumberFormat.getCurrencyInstance(Locale.TAIWAN);
		//���o��F�x�W�ϳf����O��Fromat����

		ftfSalary = new JFormattedTextField(nfMoney);
		//�ŧiJFormattedTextField����,
		//�B�Ϊ��榡����N�ϥ�Format�榡

		NumberFormatter nfrSalary = (NumberFormatter)ftfSalary.getFormatter();
		//���oJFormattedTextField����ϥΪ��榡����

		nfrSalary.setMinimum(new Double(18500)); //�]�w���\���̤p��
		nfrSalary.setMaximum(new Double(70000)); //�]�w���\���̤j��

		ftfSalary.setValue(new Double(40000)); //�]�wvalue�ݩ�

		ftfSalary.addPropertyChangeListener("value", new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent evt){

				//Double value = (Double) evt.getNewValue();
				//�]���I�s�LsetMinimum()��k�PsetMaximum()��k,
				//�G�W�@��ԭz��i���`����

				Double value = ((Double)evt.getNewValue()).doubleValue();
				//���o����ƫ��O��Number, �Y�����૬��Double�N��X�ҥ~

				lbMsg.setText("���o���ƭ� : " + Double.toString(value));
			}
		});

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

		JPanel jpLabel = new JPanel(new GridLayout(2, 1,  5, 5));
		jpLabel.add(lbName);
		jpLabel.add(lbMsg);

		Container cp = getContentPane(); //���o���e����
		cp.add(jpMain); //�N�����[�J���e����
		cp.add(jpLabel, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���������N�w�]�����{��
		setSize(300, 250); //�]�w�����ج[����ܤj�p
		setVisible(true); //��ܵ����ج[
	}

	public static void main(String args[]) {
		new FormattedEX(); //�ŧi�����ج[����
	}
}