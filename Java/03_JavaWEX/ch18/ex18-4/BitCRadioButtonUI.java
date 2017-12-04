import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//�~��BasicRadioButtonUI���O�ù�@��ťItemEvent�ƥ󪺺�ť������
public class BitCRadioButtonUI 
	extends javax.swing.plaf.basic.BasicRadioButtonUI
	implements ItemListener {

	Icon check = new ImageIcon("image/select.gif"),
			uncheck = new ImageIcon("image/unselect.gif");
	//�ŧi��ܿ�ܶs����P������������A�ϥ�
	
	private static BitCRadioButtonUI instance = new BitCRadioButtonUI();
	//�ŧi�B�z��ܶs�~�[��UI�N�z����

	public BitCRadioButtonUI(){} //�غc�l

	//�إ�UI�N�z���󪺤��}�R�A��k
	public static 
		javax.swing.plaf.ComponentUI createUI(JComponent c){

		return instance;
	}

	//�w��UI�N�z����
	public void installUI(JComponent c){

		super.installUI(c); //�I�s��¦���O��installUI()��k

		JRadioButton cb = (JRadioButton) c;
		//���o���w��UI�N�z���󪺿�ܶs
		
		cb.setIcon(cb.isSelected()? check: uncheck);
		//�̷ӿ�ܶs�O�_�Q����]�w��ܿ�����A���ϥ�

		cb.addItemListener(this);
		//���U�^��ItemEvent�ƥ󪺺�ť������
	}

	//����UI�N�z����
	public void uninstallUI(JComponent c){

		super.uninstallUI(c); //�I�s��¦���O��uninstallUI()��k

		JRadioButton cb = (JRadioButton) c;
		//���o���w��UI�N�z���󪺿�ܶs

		cb.removeItemListener(this);
		//�����^��ItemEvent�ƥ󪺺�ť������
	}

	//�^����ܶs������A���ܪ���k
	public void itemStateChanged(ItemEvent e){

		JRadioButton cb = (JRadioButton) e.getSource();
		//���o�޵o�ƥ󪺿�ܶs

		cb.setIcon(
			e.getStateChange() == ItemEvent.SELECTED ?
			check: uncheck);
		//�̷ӿ�ܶs�O�_�Q����]�w��ܿ�����A���ϥ�
	}
}