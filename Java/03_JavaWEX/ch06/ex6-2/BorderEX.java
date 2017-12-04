import javax.swing.*;  //�ޥήM��
import java.awt.*;

import javax.swing.border.*; //�w�q�ؽu���O���M��

public class BorderEX extends JFrame{

	BorderEX(){

		BevelBorder bevelraised = new BevelBorder(BevelBorder.RAISED),
			bevellowered = (BevelBorder)BorderFactory.createBevelBorder(
										BevelBorder.LOWERED, Color.CYAN, Color.BLUE);
		//�ŧi�ר��ؽu

		JLabel lbBevelRaised = new JLabel(
														"�ר�(Bevel) : �Y�_", JLabel.CENTER),
					lbBevelLowered = new JLabel(
														"�ר�(Bevel) : �W�U", JLabel.CENTER);

		lbBevelRaised.setBorder(bevelraised); //�]�w���Ҩϥαר��ؽu
		lbBevelLowered.setBorder(bevellowered);

		JPanel jpBevel = new JPanel(new GridLayout(1, 2, 10, 10));
		jpBevel.add(lbBevelRaised);
		jpBevel.add(lbBevelLowered);

		SoftBevelBorder softbevelraised = new SoftBevelBorder(
										BevelBorder.RAISED),
									 softbevellowered = new SoftBevelBorder(
										BevelBorder.LOWERED, Color.CYAN, Color.BLUE);
		//�ŧi�X���ؽu

		JLabel	lbSoftBevelRaised = new JLabel(
									"�X��(Soft Bevel) : �Y�_", JLabel.CENTER),
					lbSoftBevelLowered = new JLabel(
									"�X��(Soft Bevel) : �W�U", JLabel.CENTER);

		lbSoftBevelRaised.setBorder(softbevelraised); //�]�w���ҨϥάX���ؽu
		lbSoftBevelLowered.setBorder(softbevellowered);

		JPanel jpSoftBevel = new JPanel(new GridLayout(1, 2, 10, 10));
		jpSoftBevel.add(lbSoftBevelRaised);
		jpSoftBevel.add(lbSoftBevelLowered);

		EtchedBorder etchedborderraised = new EtchedBorder(
					EtchedBorder.RAISED, Color.gray, Color.DARK_GRAY),
			etchedborderlowered = 
					(EtchedBorder) BorderFactory.createEtchedBorder(
					EtchedBorder.LOWERED, Color.gray, Color.DARK_GRAY);
		//�ŧi�貪�ؽu

		JLabel lbEtchedRaised = new JLabel(
														"�貪(Etched) : �Y�_", JLabel.CENTER),
					lbEtchedLowered = new JLabel(
														"�貪(Etched) : �W�U", JLabel.CENTER);

		lbEtchedRaised.setBorder(etchedborderraised); //�]�w�ϥΨ貪�ؽu
		lbEtchedLowered.setBorder(etchedborderlowered);

		JPanel jpEtchedBevel = new JPanel(new GridLayout(1, 2, 10, 10));
		jpEtchedBevel.add(lbEtchedRaised);
		jpEtchedBevel.add(lbEtchedLowered);
		
		LineBorder lineborderround = new LineBorder(
																Color.DARK_GRAY, 5, true),
			linebordersquare = (LineBorder)BorderFactory.createLineBorder(
																Color.DARK_GRAY, 3);
		//�ŧi�u���ؽu

		JLabel lbLineSquare = new JLabel(
												"�u��(Line) : �訤, �e 5 ����", JLabel.CENTER),
					lbLineRound = new JLabel(
												"�u��(Line) : �ꨤ, �e 3 ����", JLabel.CENTER);

		lbLineSquare.setBorder(linebordersquare); //�]�w�ϥνu���ؽu
		lbLineRound.setBorder(lineborderround);

		JPanel jpLineBevel = new JPanel(new GridLayout(1, 2, 10, 10));
		jpLineBevel.add(lbLineSquare);
		jpLineBevel.add(lbLineRound);

		MatteBorder matteborderimage = 
					new MatteBorder(20, 20, 20, 20, new ImageIcon(".\\Icon\\Bitc.gif")),
				mattebordercolor = (MatteBorder) BorderFactory.createMatteBorder(
					20, 20, 20, 20, Color.GRAY);
		//�ŧiŨ��ؽu

		JLabel	lbMatteImage = new JLabel("Ũ��(Matte) : �ϥ�", JLabel.CENTER),
					lbMatteColor = new JLabel("Ũ��(Matte) : �C��", JLabel.CENTER);

		lbMatteImage.setBorder(matteborderimage); //�]�wŨ���ؽu
		lbMatteColor.setBorder(mattebordercolor);

		JPanel jpMatteBevel = new JPanel(new GridLayout(1, 2, 10, 10));
		jpMatteBevel.add(lbMatteImage);
		jpMatteBevel.add(lbMatteColor);

		EmptyBorder empty = new EmptyBorder(5, 5, 5, 5);
		//�ŧi�ťծؽu

		JLabel lbEmpty = new JLabel("�ť�(Empty)", JLabel.CENTER);

		lbEmpty.setBorder(empty); //�]�w�ϥΪťծؽu

		JLabel	lbCompound = 
			new JLabel("�X��(Compound)", JLabel.CENTER);

		lbCompound.setBorder(BorderFactory.createCompoundBorder(
				new CompoundBorder(new EtchedBorder(), 
														new EmptyBorder(5, 5, 5, 5)),
				new MatteBorder(10, 10, 10, 10, Color.red)));
		//�HBorderFactory���O���R�A��k�إߦX���ؽu
		//�N�H�k��ؽu�B�ťծؽu�PŨ��ؽu�զX�X�s���ؽu

		JPanel jpOtherBevel = new JPanel(new GridLayout(1, 2, 10, 10));
		jpOtherBevel.add(lbEmpty);
		jpOtherBevel.add(lbCompound);

		JPanel jpMain =  new JPanel(new GridLayout(6, 1, 10, 10));
		jpMain.add(jpBevel); //�[�J�e�ǦU���Ҫ������e��
		jpMain.add(jpSoftBevel);
		jpMain.add(jpEtchedBevel);
		jpMain.add(jpLineBevel);
		jpMain.add(jpMatteBevel);
		jpMain.add(jpOtherBevel);

		Box bxTop = new Box(BoxLayout.Y_AXIS);
		bxTop.add(Box.createVerticalStrut(10));

		bxTop.add(jpMain); //�N�D�����[�J���e����

		bxTop.add(Box.createVerticalStrut(10));

		Container cp = getContentPane(); //���o���e����

		cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));
		//�]�w���e�������G���޲z������

		cp.add(Box.createHorizontalStrut(10));
		cp.add(bxTop);
		cp.add(Box.createHorizontalStrut(10));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�]�w���������N�w�]�����{��

		pack(); //�H�̾A�j�p��ܵ���
		setVisible(true); //��ܵ����ج[
	}

	public static void main(String args[]) {
		new BorderEX(); //�ŧi�����ج[����
	}
}