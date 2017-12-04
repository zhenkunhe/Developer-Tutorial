import javax.swing.*;  //�ޥήM��
import javax.swing.border.*;
import javax.swing.table.*;
//�ޥΩw�q��UJTable����B�z��椧�����B���O���M��
import javax.swing.event.*; //�W�[�ޥΪ��M��
import java.awt.*;
import java.awt.event.*;

public class TableModelEventEX extends JFrame {

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

	String[] colName = {"�ѦW", "�X����", "���", "�Ѹ�", "�X�����"};
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
				  btnMod = new JButton("�ק�"),
				  btnCancel = new JButton("����");
	//�ŧi�ާ@��ƪ����O���s

	//�ŧi�إߪ�檺JTable����,
	//�åH�ΦW���O���覡�л\valueChanged()��k,
	//�^���C����d���ܧ�ʧ@
	JTable tbBook = new JTable(){
		public void valueChanged(ListSelectionEvent e){
			super.valueChanged(e); //�I�s��¦���O��valueChanged()��k, �_�h����ʧ@�L�k���`����

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

	public TableModelEventEX() {

		//�N�Ҧ���r��]�w�����i�s��
		for(JTextField elm : tfInput){
			elm.setEditable(false);
		}
	
		dtmBook = (DefaultTableModel)tbBook.getModel();
		//���o�B�z����ƪ�Model����

		for(String elm : colName) //�H���W�ٷs�W���
			dtmBook.addColumn(elm);

		for(Object [] elm :  bookData)
			dtmBook.addRow(elm); //�N��Ʒs�W�ܪ��

		tbBook.setRowSelectionInterval(0, 0);
		//������b����Ʒs�W��, �_�h�N��X�ҥ~

		TableColumn colTitle = tbBook.getColumnModel().getColumn(0);
		//���oJTable����ϥΪ�ColumnModel����,
		//�A���o����Ĥ@�檺TableColumn����

		colTitle.setPreferredWidth(200); //�]�w��쪺�ߦn�e�׬�200

		//�H�ΦW���O���覡�w�q�B�ŧi��ť������,
		//�óz�L�^�����O���sActionEvent�ƥ󪺾��|,
		//�����ƪ��s�W�P�x�s
		btnNew.addActionListener(new ActionListener(){

			//�^�����s���U�ƥ�
			public void actionPerformed(ActionEvent e){

				//�P�_���s��ܦr�ꬰ�s�W���x�s,
				//�H�K���椣�P�B�z
				if(e.getActionCommand().equals("�s�W")){

					//�N��X��ƪ���r��]�w���i�s��,
					//�ó]�w�S�����󤺮e
					for(JTextField elm : tfInput){
						elm.setEditable(true);
						elm.setText(null);
					}

					btnNew.setText("�x�s");
					//�N���O���s��r�]�w���x�s

					btnDel.setEnabled(false);
					btnMod.setEnabled(false);
					//�N�R���P�ק���s�]�w���L��

					btnCancel.setEnabled(true);
					//�N�������s�]�w������
				}
				else if(e.getActionCommand().equals("�x�s")){

					Object[] newRow = { 
								tfTitle.getText(),
								tfPublisher.getText(),
								Double.valueOf(tfPrice.getText()),
								tfID.getText(),
								tfPDate.getText()};
					//�ŧi�x�s�s��ƪ�����}�C

					dtmBook.addRow(newRow);
					//�N��Ʒs�W�ܪ�檺TableModel����

					int newRowIndex =  dtmBook.getRowCount() - 1;
					//���o�s�W��Ʀ�m�����ޭ�

					tbBook.setRowSelectionInterval(
						newRowIndex, newRowIndex);
					//�]�w������C

					//�N��r��]�w�����i�s��
					for(JTextField elm : tfInput){
						elm.setEditable(false);
					}

					btnNew.setText("�s�W");
					//�N���O���s��r�]�w���s�W

					btnDel.setEnabled(true);
					btnMod.setEnabled(true);
					//�N�s�W�B�ק���s�]�w������

					btnCancel.setEnabled(false);
					//�N�������s�]�w���L��
				}
			}
		});

		//�H�ΦW���O���覡�w�q�B�ŧi��ť������,
		//�óz�L�^�����O���sActionEvent�ƥ󪺾��|,
		//�����ƪ��ק�P�x�s
		btnMod.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				//�P�_���s��ܦr�ꬰ�ק���x�s,
				//�H�K���椣�P�B�z
				if(e.getActionCommand().equals("�ק�")){

					//�P�_�O�_�������@�C, �O�h�]�w�ȿ���Ĥ@�C
					if(tbBook.getSelectedRows().length != 1){
						int selRow = tbBook.getSelectedRow();
						//���o����d�򪺲Ĥ@�C

						tbBook.setRowSelectionInterval(
							selRow, selRow);
						//�]�w����d�򪺲Ĥ@�C
					}

					//�N��r��]�w���i�s��
					for(JTextField elm : tfInput){
						elm.setEditable(true);
					}

					btnMod.setText("�x�s");
					//�N���O���s��r�]�w���x�s

					btnNew.setEnabled(false);
					btnDel.setEnabled(false);	
					//�N�s�W�B�R�����s�]�w���L��

					btnCancel.setEnabled(true);
					//�N�������s�]�w���L��
				}
				else if(e.getActionCommand().equals("�x�s")){

					Object[] newData = {
									tfTitle.getText(),
									tfPublisher.getText(),
									Double.valueOf(tfPrice.getText()),
									tfID.getText(),
									tfPDate.getText()};
					//�ŧi�x�s�ק��s��ƪ�����}�C

					int selRow = tbBook.getSelectedRow();
					//���o���ثe������C

					for(int i=0; i < tfInput.length; i++){
						tfInput[i].setEditable(false);
						//�N��r��]�w�����i�s��

						dtmBook.setValueAt(newData[i], selRow, i);
						//�H��r�檺��ƭק��椺�����
					}

					btnMod.setText("�ק�");
					//�N���O���s��r�]�w���ק�

					btnNew.setEnabled(true);
					btnDel.setEnabled(true);
					//�N�s�W�B�R�����s�קאּ����

					btnCancel.setEnabled(false);
					//�N�������s�]�w���L��
				}
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

		//�H�ΦW���O���覡�w�q�B�ŧi��ť������,
		//�óz�L�^�����O���sActionEvent�ƥ󪺾��|,
		//������ƪ��ާ@
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){			

				int selRow = tbBook.getSelectedRow();
				//���o�ثe������C

				tbBook.clearSelection(); //�M�����

				tbBook.setRowSelectionInterval(selRow, selRow);
				//���]����d��, �H�K�޵oListSelectionEvent�ƥ�

				//�N��r��]�w�����i�s��
				for(JTextField elm : tfInput){
					elm.setEditable(false);
				}

				btnCancel.setEnabled(false);
				//�N�������s�]�w���L��

				btnNew.setText("�s�W");
				btnMod.setText("�ק�");
				//�N���O���s��r��_�]�w���s�W�P�ק�

				btnNew.setEnabled(true);
				btnDel.setEnabled(true);
				btnMod.setEnabled(true);
				//�N�s�W�B�R���B�ק���s�]�w������
			}
		});

		//�H�ΦW���O���覡, ���U�^��TableModelEvent�ƥ󪺺�ť������
		dtmBook.addTableModelListener(new TableModelListener(){

			public void tableChanged(TableModelEvent e){

				//��TableModelEvent�ƥ����ʧ@�����A, 
				//�M�w�����檺�ʧ@
				switch(e.getType()){
				case TableModelEvent.INSERT : //�s�W���
					System.out.println(
						"INSERT : firstRow = " + e.getFirstRow() +
						" lastRow = " + e.getLastRow());
					break;
				case TableModelEvent.DELETE : //�R�����
					System.out.println(
						"DELETE : firstRow = " + e.getFirstRow() +  
						" lastRow = " + e.getLastRow());
					break;
				case TableModelEvent.UPDATE : //�ק���
					System.out.println(
						"UPDATE : firstRow = " + e.getFirstRow() +  
						" lastRow = " + e.getLastRow());
					break;
				}
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
		bxButton.add(Box.createHorizontalStrut(10));
		bxButton.add(btnCancel);

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
		new TableModelEventEX(); //�ŧi�����ج[����	 
	}
}