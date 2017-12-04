import javax.swing.*;
import java.awt.event.*;
import java.util.*;

//�H�l��JMenu���O��@ActionListener�������覡, 
//�w�q������ج[���\���WindowMenu���O
class WindowMenu extends JMenu implements ActionListener{

	ButtonGroup bgWindow = new ButtonGroup(); //�ŧi���s�s��

	HashMap<JCheckBoxMenuItem, TextInternalFrame> hmMenuItem
				 = new HashMap<JCheckBoxMenuItem, TextInternalFrame>();
	//�ŧi�x�s�֨�����ﶵ�P���������ج[��HashMap�e��

	WindowMenu(String text, int mnemonic){ //�غc�l
		super(text); //�I�s��¦���O�غc�l
		setMnemonic(KeyEvent.VK_W); //�]�w�O����
	}

	//�N�N�����ج[���֨�����ﶵ
	//�P�����������ج[�s�W�ܥ\���
	public JMenuItem add(
		JCheckBoxMenuItem cbmi, TextInternalFrame tif){
		cbmi.addActionListener(this);
		//���U�^��ActionEvent�ƥ󪺺�ť��

		cbmi.setState(true); //�]�w����֨�����ﶵ
		hmMenuItem.put(cbmi, tif);
		//�N�֨�����ﶵ�P�����ج[�s�W��HashMap�e��

		bgWindow.add(cbmi); //�N�֨�����ﶵ�s�W�ܫ��s�s��

		return super.add(cbmi);
		//�I�s��¦���O��add()��k�[�J�֨�����ﶵ
	}

	public void remove(JCheckBoxMenuItem cbmi){
		//�������w���֨�����ﶵ

		super.remove(cbmi);
		//�I�s��¦���O��remove()��k�����֨�����ﶵ

		cbmi.removeActionListener(this);
		//�����^��ActionEvent�ƥ󪺺�ť��

		hmMenuItem.remove(cbmi);
		//����HashMap�e�����x�s�֨�����ﶵ������

		bgWindow.remove(cbmi); //�q���s�s�ղ����֨�����ﶵ
	}

	//�^��ActionEvent�ƥ󪺤�k
	public void actionPerformed(ActionEvent e){

		JInternalFrame tifCurrent = 
			hmMenuItem.get((JCheckBoxMenuItem)e.getSource());
		//�B�ή֨�����ﶵ�qHashMap�e�����o������TextInternalFrame����

		try{
			tifCurrent.setSelected(true);
			//�]�w���o��TextInternalFrame���󬰿��
		}
		catch(java.beans.PropertyVetoException pve){
			System.out.println(pve.toString());
		}	
	}
}