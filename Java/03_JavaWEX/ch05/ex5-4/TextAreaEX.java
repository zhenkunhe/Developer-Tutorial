import javax.swing.*;
import javax.swing.text.*; //�ޥΥ]�tDocument�������M��
import javax.swing.event.*; //�ޥΥ]�tCaretListener�������M��
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class TextAreaEX extends JFrame{

	JTextArea taText = new JTextArea(5, 30); //�ŧi��r�Ϫ���, �éw�q��5�C30��
	JScrollPane spText = new JScrollPane(taText); //�H��r�Ϫ���إ߱��b����
	JLabel lbText = new JLabel("��r�� :");
	JLabel lbTextPos = new JLabel("��ܴ�Ц�m");
	JLabel lbDocAct = new JLabel("��ܤ�r�Ϥ��e���s��ʧ@");

	//�w�q��@CaretListener�����^��CaretEvent�ƥ󪺺�ť��
	CaretListener cl = new CaretListener(){
		public void caretUpdate(CaretEvent e){			
			lbTextPos.setText("��r�ϴ�Ц�m : " + e.getDot());

			if(e.getDot() != e.getMark()) //�P�_�O�_����d����
				lbTextPos.setText(lbTextPos.getText() 
					+ "  ����d��q " + e.getDot() + "��" + e.getMark());
		}
	};

	TextAreaEX(){
		taText.setLineWrap(true); //�]�w��r�Ϧ۰��_��
		taText.setFont(new Font("Times-Roman", Font.BOLD, 15)); //�]�w�ϥΪ��r��

		taText.addCaretListener(cl); //���U�^��CaretEvent�ƥ󪺺�ť��

		Document doc = taText.getDocument(); //���o��r�Ϫ�Document����

		//���UDocument���󤧨ƥ󪺺�ť��, �H�^����r���e���s�W�P�R���ʧ@,
		//�åܽd�b�I�saddDocumentListener()��, �w�q��ť�����O
		doc.addDocumentListener(new DocumentListener(){
			public void insertUpdate(DocumentEvent e) { //��Ʒs�W�ʧ@
				lbDocAct.setText("��Ʒs�W�ܤ�r��");
			}
			public void removeUpdate(DocumentEvent e) { //��ƧR���ʧ@
				lbDocAct.setText("������r�Ϥ��e");
			}
			public void changedUpdate(DocumentEvent e) { }
		});

		//�إߥ]�t��ܤ�r�Ϫ��A�����Ҫ�Box�e��
		Box bxShow = new Box(BoxLayout.Y_AXIS);
		bxShow.add(lbTextPos);
		bxShow.add(lbDocAct);

		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new FlowLayout(FlowLayout.LEFT)); //�]�w�ϥ�FlowLayout�t�m

		cp.add(lbText); //�N����[�J����
		cp.add(spText);
		cp.add(bxShow);

		//�]�w���������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 230);
		setVisible(true);
	}

	public static void main(String args[]) {
		new TextAreaEX(); //�إߵ����ج[
	}
}