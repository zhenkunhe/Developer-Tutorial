import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//�~��BasicCheckBoxUI���O�ù�@��ťItemEvent�ƥ󪺺�ť������
public class BitCCheckBoxUI 
	extends javax.swing.plaf.basic.BasicCheckBoxUI
	implements ItemListener {

	Icon check = new ImageIcon("image/check.gif"),
			uncheck = new ImageIcon("image/uncheck.gif");
	//�ŧi��ܮ֨��������P������������A�ϥ�
	
	private static BitCCheckBoxUI instance = new BitCCheckBoxUI();
	//�ŧi�B�z�֨�����~�[��UI�N�z����

	public BitCCheckBoxUI(){} //�غc�l

	//�إ�UI�N�z���󪺤��}�R�A��k
	public static 
		javax.swing.plaf.ComponentUI createUI(JComponent c){

		return instance;
	}

	//�w��UI�N�z����
	public void installUI(JComponent c){

		super.installUI(c); //�I�s��¦���O��installUI()��k

		JCheckBox cb = (JCheckBox) c;
		//���o���w��UI�N�z���󪺮֨����
		
		cb.setIcon(cb.isSelected()? check: uncheck);
		//�̷Ӯ֨�����O�_�Q����]�w��ܿ�����A���ϥ�

		cb.addItemListener(this);
		//���U�^��ItemEvent�ƥ󪺺�ť������
	}

	//����UI�N�z����
	public void uninstallUI(JComponent c){

		super.uninstallUI(c); //�I�s��¦���O��uninstallUI()��k

		JCheckBox cb = (JCheckBox) c;
		//���o���w��UI�N�z���󪺮֨����

		cb.removeItemListener(this);
		//�����^��ItemEvent�ƥ󪺺�ť������
	}

	//ø�s����
	public void paint(Graphics g, JComponent c){
		super.paint(g, c);
		//������ø�s�ʧ@�G�����I�s��¦���O��paint()��k
	}

	//��s����
	public void update(Graphics g, JComponent c){
		super.update(g, c);
		//�����歫ø�ʧ@�G�����I�s��¦���O��update()��k
	}

	//�^���֨����������A���ܪ���k
	public void itemStateChanged(ItemEvent e){

		JCheckBox cb = (JCheckBox) e.getSource();
		//���o�޵o�ƥ󪺮֨����

		cb.setIcon(
			e.getStateChange() == ItemEvent.SELECTED ?
			check: uncheck);
		//�̷Ӯ֨�����O�_�Q����]�w��ܿ�����A���ϥ�
	}
}