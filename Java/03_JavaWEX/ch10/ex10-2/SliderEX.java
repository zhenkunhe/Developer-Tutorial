import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;

public class SliderEX extends JFrame{

	JLabel lbColor = new JLabel("RGB(0, 0, 0)", JLabel.CENTER);
	//��ܹϤ�������

	JSlider slRed = new JSlider(JSlider.VERTICAL, 0, 255, 0),
				slGreen = new JSlider(JSlider.HORIZONTAL, 0, 255, 0),
				slBlue = new JSlider(JSlider.VERTICAL, 0, 255, 0);
	//�ŧi���������B�����P�Ŧ�����ưʱ�

	SliderEX(){

		slRed.setLabelTable(slRed.createStandardLabels(50));
		//�]�w�ϥμзǪ���׼Ʀr����

		slRed.setPaintLabels(true); //�]�w��ܨ�ת��Ʀr����
		slRed.setInverted(true); //�]�w�ưʱ쪺��V�O�_�A��
		
		slGreen.setMajorTickSpacing(50); //�]�w�D�n��׶��Z
		slGreen.setMinorTickSpacing(5); //�]�w���n����׶��Z
		slGreen.setPaintLabels(true); //�]�w��ܨ�ת��Ʀr����
		slGreen.setPaintTicks(true); //�]�w��ܨ��

		slBlue.setMajorTickSpacing(50); //�]�w�D�n��ת����Z
		slBlue.setMinorTickSpacing(5); //�]�w���n��׶��Z
		slBlue.setPaintTicks(true); //�]�w��ܨ��
		slBlue.setSnapToTicks(true);
		//�]�w�ưʱ첾�ʫ�, �O�_�w��ܳ̾a�񪺨��

		lbColor.setOpaque(true); //�]�w�����z��
		lbColor.setBackground(Color.black); //�]�w�I���C��

		//�H�ΦW���O���覡�ŧi�^��ChangeEvent�ƥ󪺺�ť������
		ChangeListener cl = new ChangeListener(){

			public void stateChanged(ChangeEvent e){

				JSlider sl = (JSlider)e.getSource();
				//���o�޵o�ƥ󪺱��b

				Color bgColor = lbColor.getBackground();
				//���o���Ҫ��I���C��
				
				//�P�_���b���覡, �H�N���ʭȳ]�w����ܪ�����
				if(sl == slRed){
					lbColor.setBackground(new Color(sl.getValue(), 
								bgColor.getGreen(), bgColor.getBlue()));
					//�վ���ҭI���C�⪺������j��
				}
				else if(sl == slGreen){
					lbColor.setBackground(new Color(bgColor.getRed(),
								sl.getValue(), bgColor.getBlue()));
					//�վ���ҭI���C�⪺�����j��
				}
				else{
					lbColor.setBackground(new Color(bgColor.getRed(), 
								bgColor.getGreen(), sl.getValue()));
					//�վ���ҭI���C�⪺�Ŧ���j��
				}

				bgColor = lbColor.getBackground();
				//���o���ҧ��᪺�I���C��

				lbColor.setText("RGB(" + bgColor.getRed() + ", "
									+ bgColor.getGreen() 
									+ ", " + bgColor.getBlue() + ")");
					//�]�w������ܭI���C�⪺RGB��
			}
		};

		slRed.addChangeListener(cl);
		slGreen.addChangeListener(cl);
		slBlue.addChangeListener(cl);
		//���U��ťChangeEvent�ƥ󪺺�ť������

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new BorderLayout(2, 2));
		//�]�w���e���������P�������Z����10��BorderLayout����޲z����

		add(slRed, BorderLayout.WEST); //�N����[�J
		add(lbColor);
		add(slGreen, BorderLayout.SOUTH);
		add(slBlue, BorderLayout.EAST);

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//�]�w�ڭ����ϥμe�׬�5���ťծؽu

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(400, 360);
		setVisible(true);

		slGreen.setBorder(
		new EmptyBorder(0, slRed.getWidth(), 0, slBlue.getWidth()));
		//�]�w�������b�ϥΪťծؽu, 
		//����N�X�{�P�������b�e�׬ۦP���ťն���
		//�Ъ`�N! �����b�I�ssetVisible()��k��ܵ��������
		//�~����o���T��
	}

	public static void main(String args[]) {
		new SliderEX(); //�ŧi�����ج[����
	}
}