import javax.swing.*;
import javax.swing.event.*; //�ޥΥ]�tItemListener�������M��
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class ComboBoxEX extends JFrame{

	String[] strPay = {"�H�Υd", "�{��", "��d", "��b", "�䲼"};
	String[] strBank = {"�l��", "�صػȦ�", "���ذӻ�", "�X�@���w"};
	//�ŧi�إ߲զX����ﶵ���e���r��

	JComboBox cmbPay = new JComboBox(strPay),
						  cmbBank = new JComboBox(strBank);
	//�H�]�t�ﶵ���e���r��}�C�إ߲զX���

	JLabel lbSelPay = new JLabel(), //��ܲզX���������G������
				lbSelBank = new JLabel();

	String strMsg = "����ο�J�Ȧ�W��";

	//�w�q�ëŧi�^���b�զX�����
	//���U Enter ��Ĳ�o��ActionEvent����ť������
	ActionListener al = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			boolean addItem = true;
			String strInp = 
				(String) ((JComboBox)e.getSource()).getSelectedItem();
			//���o�Q�������

			//�Y�Q��������ؤ��e�P�զX�����ܪ��r��ۦP,
			//�h�פ�ƥ�^��
			if(strInp.equals(strMsg))
				return;

			//�Hfor�j������o�ﶵ�O�_���զX������ﶵ
			for(int i=0; i<cmbBank.getItemCount(); i++){

				//���ﶵ�r��				
				if(cmbBank.getItemAt(i).equals(strInp)){
					addItem = false; //�]�w���N�r��[�J
					break;
				}
			}

			//�Y���o�ﶵ���O�զX������ﶵ�h�N�ﶵ���J���������
			if(addItem)
				cmbBank.insertItemAt(strInp, 0); //���J�Ĥ@�ӿﶵ
		}
	};

	ComboBoxEX(){
		cmbPay.setSelectedIndex(0); //�]�w�ﶵ������
		cmbBank.setSelectedIndex(-1); //�]�w�w�]���������ﶵ

		cmbBank.setEditable(true); //�]�w��J���e�i�s��

		ComboBoxEditor cmbeBank = cmbBank.getEditor();
		//���o�զX������e�s�褸��

		cmbBank.configureEditor(cmbeBank, strMsg);
		//�]�w���e�s�褸�󪺹w�]��	

		//�w�q�ëŧi�^��ItemEvent�ƥ󪺺�ť������
		cmbPay.addItemListener(new ItemListener(){

			//�^�����ܿﶵ���A���ʧ@
			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange() == ItemEvent.SELECTED)
					lbSelPay.setText(cmbPay.getSelectedItem()
						+ "[" + cmbPay.getSelectedIndex() + "]");
			}
		});

		cmbBank.addItemListener(new ItemListener(){

			//�^�����ܿﶵ���A���ʧ@
			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange() == ItemEvent.SELECTED)
					lbSelBank.setText(cmbBank.getSelectedItem()
						+ "[" + cmbBank.getSelectedIndex() + "]");
			}
		});

		//���U�^��cmbBank����ActionEvent�ƥ󪺺�ť��
		cmbBank.addActionListener(al);

		//�إߥ]�t�U����Box�e��, �ñN����[�J
		Box bxPay = new Box(BoxLayout.X_AXIS);
		bxPay.add(Box.createHorizontalStrut(10));
		bxPay.add(new JLabel("�I�ڤ覡 : ", JLabel.RIGHT));
		bxPay.add(cmbPay);
		bxPay.add(Box.createHorizontalStrut(10));
		bxPay.add(new JLabel("������G : ", JLabel.RIGHT));
		bxPay.add(lbSelPay);
		bxPay.add(Box.createHorizontalGlue());

		//�إߥ]�t�U����Box�e��, �ñN����[�J
		Box bxBank = new Box(BoxLayout.X_AXIS);
		bxBank.add(Box.createHorizontalStrut(10));
		bxBank.add(new JLabel("�פJ�Ȧ� : ", JLabel.RIGHT));
		bxBank.add(cmbBank);
		bxBank.add(Box.createHorizontalStrut(10));
		bxBank.add(new JLabel("������G : ", JLabel.RIGHT));
		bxBank.add(lbSelBank);
		bxBank.add(Box.createHorizontalGlue());

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new GridLayout(2, 1, 10, 10));
		//�]�w���e�����ϥ�GridLayout�޲z����

		cp.add(bxPay); //�NBox�e���[�J���e����
		cp.add(bxBank);

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(400, 100);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ComboBoxEX(); //�ŧi�����ج[����
	}
}