import javax.swing.*;  //�ޥήM��
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.table.*;
//�ޥΩw�q��UJTable����B�z��椧�����B���O���M��

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class DialogEX extends JFrame {

	Object[][] bookData = {
		{"Java 2 �J���i�� �V �A��JDK6.0", "����T", 590.0,
		 "P4137",	"2005-5-1"},
		{"Visual C++.NET �J���i��", "����T", 750.0, 
		 "P3237", "2003-9-1"},
		{"Access 2003 ������s", "����T", 590.0, 
		 "A4023", "2004-3-1"},
		{"Access 2003 �{���]�p", "����T", 660.0, 
		 "A4033",	"2004-5-1"},
		{"JSP �ʺA�����J�����", "����T", 720.0, 
		 "W3135", "2003-12-1"},
		{"ASP �ʺA�����J�����", "����T", 580.0, 
		 "W4075", "2004-7-1"}};
	//�ŧi�x�s���y��ƪ��G���}�C

	String[] colName = {"�ѦW", "�X����", "���",
									 "�Ѹ�", "�X�����"};
	//�ŧi�x�s���W�٪��r��

	JTextField tfTitle = new JTextField(),
						tfPublisher = new JTextField(),
						tfPrice = new JTextField(),
						tfID = new JTextField(),
						tfPDate = new JTextField();
	//�ŧi��ܡB�s���椺��ƪ���r��

	JTextField[] tfInput = 
		{tfTitle, tfPublisher, tfPrice, tfID, tfPDate};
	//�z�L�ާ@�Ҧ��s�W�B�s���ƪ���r��

	JButton btnNew = new JButton("�s�W"),
				  btnDel = new JButton("�R��"),
				  btnMod = new JButton("�ק�");
	//�ŧi�ާ@��ƪ����O���s

	//�ŧi�إߪ�檺JTable����,
	//�åH�ΦW���O���覡�л\valueChanged()��k,
	//�^���C����d���ܧ�ʧ@
	JTable tbBook = new JTable(){
		public void valueChanged(ListSelectionEvent e){
			super.valueChanged(e);
			//�I�s��¦���O��valueChanged()��k, 
			//�_�h����ʧ@�L�k���`����

			int selRow = tbBook.getSelectedRow();
			//���o���ثe������C

			//�Y�S������C�h�פ�����k
			if(selRow == -1) return;

			//�N����C�����, �̧ǳ]�w����r��
			for(int i = 0; i < tfInput.length; i++){
				tfInput[i].setText(
					dtmBook.getValueAt(selRow, i).toString());
			}
		}
	};
	
	DefaultTableModel dtmBook;
	//�ŧi�B�z����ƪ�TableModel����

	public DialogEX() {

		//�N�Ҧ���r��]�w�����i�s��
		for(JTextField elm : tfInput)
			elm.setEditable(false);
			
		dtmBook = (DefaultTableModel) tbBook.getModel();
		//���o�B�z����ƪ�TableModel����

		for(String elm : colName) //�H���W�ٷs�W���
			dtmBook.addColumn(elm);

		for(Object[] elm :  bookData)
			dtmBook.addRow(elm); //�N��Ʒs�W�ܪ��

		tbBook.setRowSelectionInterval(0, 0);
		//������b����Ʒs�W��, �_�h�N��X�ҥ~

		TableColumn colTitle = 
			tbBook.getColumnModel().getColumn(0);
		//���oJTable����ϥΪ�TableColumnModel����,
		//�A���o����Ĥ@�檺TableColumn����

		colTitle.setPreferredWidth(200);
		//�]�w��쪺�ߦn�e�׬�200

		//�H�ΦW���O���覡�w�q�B�ŧi��ť������,
		//�óz�L�^�����O���sActionEvent�ƥ󪺾��|,
		//�����ƪ��s�W�P�x�s
		btnNew.addActionListener(new ActionListener(){

			//�^�����s���U�ƥ�
			public void actionPerformed(ActionEvent e){

				Object[] newRowData = BookDataDialog.showDialog(
											DialogEX.this, "�s�W���");
				//��ܷs�W��ƹ�ܲ�

				//�Y�Ǧ^����ư}�C���׬�0, �h�פ�����k
				if(newRowData.length == 0)
					return;

				dtmBook.addRow(newRowData);
				//�N��Ʒs�W��TableModel����

				int selRow = dtmBook.getRowCount()-1;
				//�]�w�s����C�����ޭ�

				tbBook.setRowSelectionInterval(selRow, selRow);
				//�]�w�s������C
			}
		});

		//�H�ΦW���O���覡�w�q�B�ŧi��ť������,
		//�óz�L�^�����O���sActionEvent�ƥ󪺾��|,
		//�����ƪ��ק�P�x�s
		btnMod.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){
				int selRow = tbBook.getSelectedRow();
				//���o���ثe������C

				Vector vetBookData =  (Vector)dtmBook.getDataVector();
				//���o�x�sTableModel���󤺩Ҧ���ƪ�Vector����

				Vector vetSelRow = 
					(Vector)vetBookData.elementAt(selRow);
				//���o�x�s���w�C����ƪ�Vector����

				Object[] selRowData = vetSelRow.toArray();
				//�N�x�s����C��ƪ�Vector�e���������ഫ��Object�}�C

				Object[] newRowData = BookDataDialog.showDialog(
						DialogEX.this, "�ק���" , selRowData);
				//��ܭק��ƹ�ܲ�

				//�Y�Ǧ^����ư}�C���׬�0, �h�פ�����k
				if(newRowData.length == 0)
					return;

				//�H�ק��ƹ�ܲ��ק�᪺���
				//��sTableModel���󪺤��e
				for(int i=0; i<newRowData.length; i++)
					dtmBook.setValueAt(newRowData[i], selRow, i);

				tbBook.clearSelection(); //�M����檺���
				tbBook.setRowSelectionInterval(selRow, selRow);
				//���s����C, �H�K��s�����W���r����ܪ����
			}
		});

		//�H�ΦW���O���覡�w�q�B�ŧi��ť������,
		//�óz�L�^�����O���sActionEvent�ƥ󪺾��|,
		//�����ƪ��R��
		btnDel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				int[] selRows = tbBook.getSelectedRows();
				//���o����C�����ޭ�

				if(selRows.length == 0) return;
				//�Y���������C�h�פ�����k

				//�̷ӿ���C�����ޭȲ������
				for(int i = selRows.length-1; i>=0; i--){
					dtmBook.removeRow(selRows[i]);
					//�����q�᭱�}�l����, 
					//�]�������@�C��Ʈ�, �᭱����C�����ޭȱN����
					//�q�̫�@�C�}�l�����h�i�ѨM�����D
				}

				int rowCount = dtmBook.getRowCount();
				//���o��������C��, ��檺�C��

				//�Y���Ѿl�C�Ƭ�0 , �h�פ�����k
				if(rowCount == 0){

					//�M����r�椺�e
					for(JTextField elm : tfInput){
						elm.setText(null);
					}
					return;
				}

				int afterSelIndex = 
					selRows[selRows.length - 1] - selRows.length + 1;
				//�p��s����C�����ޭ�

				//�Y�s����C�����ޭȤj����Ѿl�C�ƴ�1,
				//�h�N���ޭȳ]�w���Ѿl�C�ƴ�1
				if(afterSelIndex > (rowCount-1))
					afterSelIndex = (rowCount-1);

				tbBook.setRowSelectionInterval(
					afterSelIndex, afterSelIndex);
				//�]�w���s������C
			}
		});

		JPanel jpInput = new JPanel(new GridBagLayout());
		//�ŧi�e�Ƿs�W�P�s���Ƥ���r�檺�����e��

		GridBagConstraints gbc = new GridBagConstraints();
		//�ŧi����󪩭��t�m��GridBagConstraints����

		gbc.insets = new Insets(2, 5 , 2, 5);
		gbc.ipadx = gbc.ipady = 2;
		//�]�w���󤺤�r�P�|�P��ɪ����Z����15
		gbc.gridwidth = 1; gbc.gridheight = 1;
		//�]�w�e�׬�1�氪�׬�1�C
		gbc.fill = GridBagConstraints.HORIZONTAL;
		//�]�w�������ܪŶ��������Ŷ�

		/***�[�J�Ĥ@�C������***/		
		gbc.gridx = 0; gbc.gridy = 0; //�]�w���󪺦�C��m
		jpInput.add(new JLabel("�W�� : "), gbc);

		gbc.gridx = 1; gbc.gridy = 0; //�]�w���󪺦�C��m
		gbc.gridwidth = 4; //�]�w��쪺�e��
		gbc.weightx = 1.0; //�]�w���t�B�~�����Ŷ������
		jpInput.add(tfTitle, gbc);

		gbc.weightx = 0.0; //��_�]�w��
		gbc.gridwidth = 1;

		/***�[�J�ĤG�C������***/		
		gbc.gridx = 0; gbc.gridy = 1; //�]�w���󪺦�C��m
		jpInput.add(new JLabel("�X���� : "), gbc);

		gbc.gridx = 1; gbc.gridy = 1; //�]�w���󪺦�C��m
		gbc.weightx = 1.0; //�]�w���t�B�~�����Ŷ������
		jpInput.add(tfPublisher, gbc);

		gbc.weightx = 0.0; //��_�]�w��

		gbc.gridx = 2; gbc.gridy = 1; //�]�w���󪺦�C��m
		jpInput.add(new JLabel("��� : "), gbc);

		gbc.gridx = 3; gbc.gridy = 1; //�]�w���󪺦�C��m
		gbc.weightx = 1.0; //�]�w���t�B�~�����Ŷ������
		jpInput.add(tfPrice, gbc);

		gbc.weightx = 0.0; //��_�]�w��

		/***�[�J�ĤT�C������***/
		gbc.gridx = 0; gbc.gridy = 2; //�]�w���󪺦�C��m
		jpInput.add(new JLabel("�Ѹ� : "), gbc);

		gbc.gridx = 1; gbc.gridy = 2; //�]�w���󪺦�C��m
		gbc.weightx = 1.0; //�]�w���t�B�~�����Ŷ������
		jpInput.add(tfID, gbc);

		gbc.weightx = 0.0; //��_�]�w��

		gbc.gridx = 2; gbc.gridy = 2; //�]�w���󪺦�C��m
		jpInput.add(new JLabel("�X����� : "), gbc);

		gbc.gridx = 3; gbc.gridy = 2; //�]�w���󪺦�C��m
		gbc.weightx = 1.0; //�]�w���t�B�~�����Ŷ������
		jpInput.add(tfPDate, gbc);

		//�إߥ]�t���O���s��Box�e��
		Box bxButton = new Box(BoxLayout.X_AXIS);
		bxButton.add(Box.createHorizontalGlue());
		bxButton.add(btnNew);
		bxButton.add(Box.createHorizontalStrut(10));
		bxButton.add(btnDel);
		bxButton.add(Box.createHorizontalStrut(10));
		bxButton.add(btnMod);

		//�ŧi�m�󤺮e�����_�䪺Box�e��
		Box bxNorth = new Box(BoxLayout.Y_AXIS);
		bxNorth.add(jpInput);
		bxNorth.add(Box.createVerticalStrut(5));
		bxNorth.add(bxButton);

		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new BorderLayout(15, 15));
		//�إߦU�ϰ�����B�������Z��15��BorderLayout����

		cp.add(bxNorth, BorderLayout.NORTH);
		cp.add(new JScrollPane(tbBook));
		//�N����[�J�����ϰ�

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����|�P�N�ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 300);
		setVisible(true);
	}

	public static void main(String args[]) {
		new DialogEX(); //�ŧi�����ج[����	 
	}
}

//�w�q��ܰ����Ʒs�W�B�ק蠟��ܲ���BookDataDialog���O
//�N�~��BookDataDialog���O�ù�@ActionListener����
//�H�^����ܲ������O���s
class BookDataDialog extends JDialog
										implements ActionListener {

	private JTextField tfTitle = new JTextField(),
						tfPublisher = new JTextField(),
						tfPrice = new JTextField(),
						tfID = new JTextField(),
						tfPDate = new JTextField();
	//�ŧi��ܡB�s���椺��ƪ���r��

	private JButton btnSave = new JButton("�x�s"),
				  btnCancel = new JButton("����");
	//�ŧi���O���s

	private JTextField[] tfInput = 
		{tfTitle, tfPublisher, tfPrice, tfID, tfPDate};
	//�z�L�ާ@�Ҧ��s�W�B�s���ƪ���r��

	private Vector values = new Vector();
	//�x�s��r�檺�]�w��

	//�ŧi�ק��ƹ�ܲ�
	BookDataDialog(JFrame parent, String title, Object[] data){

		super(parent, title, true); //�I�s��¦���O���غc�l

		createMainPanel(); //�إߥD�n��ܲ����D�n����

		//�H�ǤJ��Ƴ]�w��ܲ�����r��
		for(int i = 0; i < tfInput.length; i++)
			tfInput[i].setText(data[i].toString());

		setSize(350, 170); //�]�w��ܲ����j�p
	}
	//�ŧi�s�W��ƹ�ܲ�
	BookDataDialog(JFrame parent, String title){

		super(parent, title, true); //�I�s��¦���O���غc�l

		createMainPanel(); //�إߥD�n��ܲ����D�n����

		setSize(350, 170); //�]�w��ܲ����j�p
	}
	
	//�^�����O���s���U�ʧ@����k
	public void actionPerformed(ActionEvent e){

		//�Y�ϥΪ̫��U �x�s ���s, 
		//�N���r�檺��ƥ[�Jvalues�e����
		if("�x�s".equals(e.getActionCommand())){
			values.add(tfTitle.getText());
			values.add(tfPublisher.getText());
			values.add(Double.valueOf(tfPrice.getText()));
			values.add(tfID.getText());
			values.add(tfPDate.getText());
		}

		setVisible(false); //�]�w����ܹ�ܲ�
	}

	//���o��ܲ�����r�檺�]�w��
	public Object[] getValues(){
		return values.toArray();
	}

	private void createMainPanel(){ //�إ߹�ܲ���������k

		btnSave.addActionListener(this);
		btnCancel.addActionListener(this);
		//���U��BookDataDialog�����ť���O���s���ʧ@

		JPanel jpDataInput = new JPanel(new GridBagLayout());
		//�ŧi�e�Ƿs�W�P�s���Ƥ���r�檺�����e��

		GridBagConstraints gbc = new GridBagConstraints();
		//�ŧi����󪩭��t�m��GridBagConstraints����

		gbc.insets = new Insets(2, 5 , 2, 5);
		gbc.ipadx = gbc.ipady = 2;
		//�]�w���󤺤�r�P�|�P��ɪ����Z����15
		gbc.gridwidth = 1; gbc.gridheight = 1;
		//�]�w�e�׬�1�氪�׬�1�C
		gbc.fill = GridBagConstraints.HORIZONTAL;
		//�]�w�������ܪŶ��������Ŷ�

		/***�[�J�Ĥ@�C������***/
		gbc.gridx = 0; gbc.gridy = 0; //�]�w���󪺦�C��m
		jpDataInput.add(new JLabel("�W�� : "), gbc);

		gbc.gridx = 1; gbc.gridy = 0; //�]�w���󪺦�C��m
		gbc.gridwidth = 4; //�]�w��쪺�e��
		gbc.weightx = 1.0; //�]�w���t�B�~�����Ŷ������
		jpDataInput.add(tfTitle, gbc);

		gbc.weightx = 0.0; //��_�]�w��
		gbc.gridwidth = 1;

		/***�[�J�ĤG�C������***/
		gbc.gridx = 0; gbc.gridy = 1; //�]�w���󪺦�C��m
		jpDataInput.add(new JLabel("�X���� : "), gbc);

		gbc.gridx = 1; gbc.gridy = 1; //�]�w���󪺦�C��m
		gbc.weightx = 1.0; //�]�w���t�B�~�����Ŷ������
		jpDataInput.add(tfPublisher, gbc);

		gbc.weightx = 0.0; //��_�]�w��

		gbc.gridx = 2; gbc.gridy = 1; //�]�w���󪺦�C��m
		jpDataInput.add(new JLabel("��� : "), gbc);

		gbc.gridx = 3; gbc.gridy = 1; //�]�w���󪺦�C��m
		gbc.weightx = 1.0; //�]�w���t�B�~�����Ŷ������
		jpDataInput.add(tfPrice, gbc);

		gbc.weightx = 0.0; //��_�]�w��

		/***�[�J�ĤT�C������***/
		gbc.gridx = 0; gbc.gridy = 2; //�]�w���󪺦�C��m
		jpDataInput.add(new JLabel("�Ѹ� : "), gbc);

		gbc.gridx = 1; gbc.gridy = 2; //�]�w���󪺦�C��m
		gbc.weightx = 1.0; //�]�w���t�B�~�����Ŷ������
		jpDataInput.add(tfID, gbc);

		gbc.weightx = 0.0; //��_�]�w��

		gbc.gridx = 2; gbc.gridy = 2; //�]�w���󪺦�C��m
		jpDataInput.add(new JLabel("�X����� : "), gbc);

		gbc.gridx = 3; gbc.gridy = 2; //�]�w���󪺦�C��m
		gbc.weightx = 1.0; //�]�w���t�B�~�����Ŷ������
		jpDataInput.add(tfPDate, gbc);

		Box bxButton = new Box(BoxLayout.X_AXIS);
		//�e�ǫ��O���s��Box�e��

		bxButton.add(Box.createHorizontalGlue());
		bxButton.add(btnSave);  //�[�J�x�s���O
		bxButton.add(Box.createHorizontalStrut(10));
		bxButton.add(btnCancel);  //�[�J�������O

		Container cp = getContentPane(); //���o���e����
		cp.add(bxButton, BorderLayout.SOUTH);
		cp.add(jpDataInput);
		//�N�]�t�U������e���[�J���e����

		getRootPane().setBorder(new EmptyBorder( 5, 5, 5, 5));
		//�]�w�ڭ����|�g���e�׬�5���ťծؽu
	}

	//���͡B��ܷs�W��ƹ�ܲ����R�A��k
	public static Object[] showDialog(JFrame parent, String title){

		BookDataDialog dialog = new BookDataDialog(parent, title);
		//�ŧi�s�W��ƹ�ܲ�

		dialog.setVisible(true); //�]�w��ܹ�ܲ�

		return dialog.getValues(); //�Ǧ^��ܲ�����r�檺�]�w��
	}

	//���͡B��ܭק��ƹ�ܲ����R�A��k
	public static Object[] showDialog(
		JFrame parent, String title, Object[] data){

		BookDataDialog dialog = new BookDataDialog(parent, title, data);
		//�ŧi�ק��ƹ�ܲ�

		dialog.setLocationRelativeTo(parent);
		//�]�w��ܲ���ܩ��������

		dialog.setVisible(true); //�]�w��ܹ�ܲ�

		return dialog.getValues(); //�Ǧ^��ܲ�����r�檺�]�w��
	}
}