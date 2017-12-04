import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class InternalEX extends JFrame{

	JDesktopPane dpPane = new JDesktopPane();
	//STEP 1�B�]�t�h�����������������ୱ�e��

	//�ŧi���A�C����ܵ������A������
	JLabel lbCount = new JLabel("0", JLabel.LEFT),
	       lbAlive = new JLabel("0", JLabel.LEFT),
	       lbCur = new JLabel("�ثe�L��ܵ���");

	InternalEX(){

		JButton btnNew = 
			new JButton(new ImageIcon("images/new.gif"));
		//�ŧi���[�J�u��C�����s����, �åHnew.gif����ܹϤ�

		//�ŧi�éw�q�^�����sMouseEvenet�ƥ󪺺�ť��
		btnNew.addMouseListener(new MouseAdapter(){

			int count = 0;

			public void mouseClicked(MouseEvent e){
	
				JInternalFrame ifObj =
					 new JInternalFrame(null, true, true, true, true);
				//STEP 2�B�إߥi�����B�i�ܧ�j�p�B�L���D
				//				   �B�i�̤j/�p�ƪ������ج[

				dpPane.add(ifObj);
				//STEP 3�B�N�����ج[�[�J�����ୱ

				count = dpPane.getAllFrames().length;
				//���o�����ج[���Ӽ�

				lbAlive.setText(String.valueOf(count));
				//�]�w�ثe�}�ҵ������Ӽ�

				ifObj.setSize(200, 200); //�]�w�����ج[���j�p

				ifObj.setLocation(30*count, 30*count);
				//�]�w�����ج[�b�����ୱ����m

				ifObj.setTitle("�� " + count + " �ӵ���");
				//�]�w�����ج[�����D

				try{
					ifObj.setVisible(true);
					ifObj.setSelected(true);
					//STEP 4�B��ܤ����ج[�ó]�w�������
					
					lbCur.setText("[" + ifObj.getTitle() + "] ���o�J�I");
					//�]�w���A�C��ܥثe��ܤ����������D
				}
				catch(java.beans.PropertyVetoException pve){
					System.out.println(pve.toString());
				}

				//�ŧi�éw�q�^�������ج[InternalFrameEvenet�ƥ󪺺�ť��
				ifObj.addInternalFrameListener(
					new InternalFrameAdapter(){

					//�^�����������ج[�ƥ�
					public void
						internalFrameClosed(InternalFrameEvent e){

						int num = dpPane.getAllFrames().length;
						//���o�����ج[���Ӽ�

						lbAlive.setText(String.valueOf(num));
						//�]�w�ثe�}�Ҥ����ج[���Ӽ�

						if(num == 0)
							lbCur.setText("�ثe�L��ܵ���");
						//�]�w���A�C��ܥثe��ܤ����������D
					}

					//�^�������ج[���o�J�I���ƥ�
					public void 
						internalFrameActivated(InternalFrameEvent e){

						lbCur.setText(
							"[" + e.getInternalFrame().getTitle() +  "] ���o�J�I");
						//�]�w���A�C��ܥثe��ܤ����������D
					}
				});
			}
		});

		JToolBar tbTop = new JToolBar(); //�ŧi�u��C����
		tbTop.add(btnNew); //�N���s����[�J�u��C

		Box bxStatus = new Box(BoxLayout.X_AXIS);
		//�ŧi�]�t��ܪ��A�T����JPanel�e��

		//�N��ܤ����ج[��T�����ҥ[�JBox�e��
		bxStatus.add(Box.createHorizontalStrut(20));
		bxStatus.add(new JLabel("�ثe�}�ҭӼ� : ", JLabel.RIGHT));
		bxStatus.add(lbAlive);
		bxStatus.add(Box.createHorizontalGlue());
		bxStatus.add(lbCur);
		bxStatus.add(Box.createHorizontalStrut(20));

		Container cp = getContentPane(); //���o���e����
		cp.add(tbTop, BorderLayout.NORTH); //�N�u��C�[�J�����ج[

		cp.add(dpPane);
		//STEP 5�B�N�����ୱ�[�J���e����

		cp.add(bxStatus, BorderLayout.SOUTH); //�N���A�C�[�J�����ج[
		
		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 400);
		setVisible(true);
	}

	public static void main(String args[]) {
		new InternalEX(); //�ŧi�����ج[����
	}
}