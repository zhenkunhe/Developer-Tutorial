import javax.swing.*;
import javax.swing.event.*; //�ޥΥ]�tCaretListener�������M��
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class ListDataEventEX extends JFrame{

	String[] strTech = {"PHP", "JSP", "ASP", "ASP.NET",
								   "Perl", "Java", "C/C++", "C#"};
	//�]�w�ﶵ���r��}�C

	DefaultListModel dlmTech = new DefaultListModel();
	DefaultListModel dlmSelTech = new DefaultListModel();
	//�ŧi�B�z�M�������ﶵ��Model����

	JList ltTech = new JList(dlmTech);
	JList ltSelTech = new JList(dlmSelTech);
	//�HModel����ŧi�M����

	JLabel lbTech = new JLabel();
	JLabel lbSelTech = new JLabel("�^���ƥ󪺰T��");
	//�ŧi��ܲM�������ﶵ�Ӽƪ��T��

	JButton btnToLeft = new JButton("<");
	JButton btnToRight = new JButton(">");
	//�ŧi���s����

	//�N�ﶵ�ѥ��䪺�M�������ܥk��
	private void toRight(){

		dlmSelTech.addElement(ltTech.getSelectedValue());
		//�N����M����������ﶵ�[�J�k��M������Model����

		//�Y�k��M����������ﶵ�h�]�w����Ĥ@�ӿﶵ
		if(ltSelTech.getSelectedIndex() == -1)
			ltSelTech.setSelectedIndex(0); //�]�w����Ĥ@�ӿﶵ	

		int index = ltTech.getSelectedIndex(); //���o����ﶵ������
		dlmTech.remove(index); //�qModel���󲾰��ﶵ

		int size = dlmTech.getSize(); //���oModel���󪺤j�p

		//�����ﶵ��, �]�w�s������ﶵ
		if(index != size){
			ltTech.setSelectedIndex(index);
			//�]�w����M���������ӯ��ޭȪ��ﶵ
		}
		else{
			ltTech.setSelectedIndex(index-1);
			//�]�w����M���������ӯ��ޭȴ�1���ﶵ
		}

		if(size == 0){ //size����0��ܥ���M�������S���ﶵ
			btnToRight.setEnabled(false);
			//�]�w�N�ﶵ���ܥk�䪺���s���L��
		}

		if(!btnToLeft.isEnabled()){
		//�P�_�N�ﶵ���ܥ���M���������s�O�_����
			btnToLeft.setEnabled(true);	
			//�]�w�N�ﶵ���ܥ��䪺���s������
		}
	}

	//�N�ﶵ�ѥk�䪺�M�������ܥ���
	private void toLeft(){

		dlmTech.addElement(ltSelTech.getSelectedValue());
		//�N�k��M����������ﶵ�[�J����M������Model����

		//�Y����M����������ﶵ�h�]�w����Ĥ@�ӿﶵ
		if(ltTech.getSelectedIndex() == -1)
			ltTech.setSelectedIndex(0); //�]�w����Ĥ@�ӿﶵ

		int index = ltSelTech.getSelectedIndex(); //���o����ﶵ������
		dlmSelTech.remove(index); //�qModel���󲾰��ﶵ

		int size = dlmSelTech.getSize(); //���oModel���󪺤j�p

		//�����ﶵ��, �]�w�s������ﶵ
		if(index != size){
			ltSelTech.setSelectedIndex(index);
			//�]�w�k��M���������ӯ��ޭȪ��ﶵ
		}
		else{
			ltSelTech.setSelectedIndex(index-1);
			//�]�w�k��M����������ﶵ
		}

		if(size == 0){ //size����0��ܥk��M�������S���ﶵ
			btnToLeft.setEnabled(false);
			//�]�w�N�ﶵ���ܥ��䪺���s���L��
		}

		if(!btnToRight.isEnabled()){
		//�P�_�N�ﶵ���ܥk��M���������s�O�_����
			btnToRight.setEnabled(true);
			//�]�w�N�ﶵ���ܥk�䪺���s������
		}
	}

	//�H�ΦW���O���覡��@ActionListener����,
	//�w�q�ëŧi�^��ActionEvent�ƥ󪺺�ť������
	ActionListener al = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(e.getActionCommand().equals("toRight")){
				toRight(); //�N����ﶵ�q���䲾�ܥk��
			}
			else{
				toLeft(); //�N����ﶵ�q�k�䲾�ܥ���
			}
		}
	};

	//�H�ΦW���O���覡�~��MouseAdapter���O,
	//�w�q�ëŧi�^��MouseEvent�ƥ󪺺�ť������
	MouseAdapter ma = new MouseAdapter(){
		public void mouseClicked(MouseEvent e){
			if(e.getClickCount() == 2){ //�P�_�ƹ��O�_�s���G�U
				if((JList)e.getSource() == ltTech)
					toRight(); //�N����ﶵ�q���䲾�ܥk��
				else
					toLeft(); //�N����ﶵ�q�k�䲾�ܥ���
			}
		}
	};

	ListDataEventEX(){

		//�ŧi��ťListDataEvent�ƥ󪺺�ť������
		dlmSelTech.addListDataListener(new ListDataListener(){

			//�^���ﶵ���[�J�ƥ�
			public void intervalAdded(ListDataEvent e){
				lbSelTech.setText("�s�W��" + e.getIndex0() + "�ӿﶵ");
			}

			//�^���ﶵ�������ƥ�
			public void intervalRemoved(ListDataEvent e){
				lbSelTech.setText("������" + e.getIndex0() + "�ӿﶵ");
			}

			//�^���ﶵ���e�ק�ƥ�
			public void contentsChanged(ListDataEvent e){ }
		});

		//�N�r��}�C�����r��[�JModel����
		for(String item: strTech)
			dlmTech.addElement(item);

		btnToLeft.setEnabled(false); //�]�w�V�����s���L��

		ltTech.setVisibleRowCount(5); //�]�w����M�������i�����ج�5�C
		ltTech.setSelectedIndex(0); //�]�w����M�����w�]�����1�ӿﶵ

		ltSelTech.setVisibleRowCount(5);
		//�]�w�k��M�������i�O���ج�5�C

		btnToLeft.setActionCommand("toLeft"); //�]�w�ʧ@�R�O�r��
		btnToRight.setActionCommand("toRight");

		btnToLeft.addActionListener(al);
		btnToRight.addActionListener(al);
		//���U�^��ActionEvent�ƥ󪺺�ť������

		ltTech.addMouseListener(ma);
		ltSelTech.addMouseListener(ma);
		//���U�^��MouseEvent�ƥ󪺺�ť������

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new GridBagLayout());
		//�]�w���e�����ϥ�GridBagLayout�޲z�G��

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.BOTH;

		gbc.gridwidth = 2; gbc.gridheight = 1;
		gbc.gridx = 0; gbc.gridy = 0;
		cp.add(new JLabel("�i������޳N���� : "), gbc);

		gbc.gridx = 3; gbc.gridy = 0;
		cp.add(new JLabel("�Q������޳N���� : "), gbc);

		gbc.gridwidth = 2; gbc.gridheight = 5;
		gbc.gridx = 0; gbc.gridy = 1;
		cp.add(new JScrollPane(ltTech), gbc); //�[�J���䪺�M����

		gbc.gridx = 3; gbc.gridy = 1;
		cp.add(new JScrollPane(ltSelTech), gbc); //�[�J�k�䪺�M����

		gbc.gridx = 3; gbc.gridy = 7;
		gbc.gridwidth = 1; gbc.gridheight = 1;
		cp.add(lbSelTech, gbc); //�N���ҥ[�J�e��
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(10, 10, 10 ,10);

		gbc.gridx = 2; gbc.gridy = 2;
		cp.add(btnToRight, gbc); //�[�J�N�ﶵ���ܥk�䪺���O���s

		gbc.gridx = 2; gbc.gridy = 5;
		cp.add(btnToLeft, gbc); //�[�J�N�ﶵ���ܥ��䪺���O���s
	
		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(350, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ListDataEventEX();
	}
}