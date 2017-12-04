import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class ScrollEX extends JFrame{

	ImageIcon ii = new ImageIcon("image\\P4137All.jpg");
	//�ŧi�e�ǹϤ���ImageIcon����

	JLabel lbImage = new JLabel(ii);	//��ܹϤ�������

	DefaultBoundedRangeModel brmHor =
		new DefaultBoundedRangeModel(0, 0, 0, 0);
	//�������b�ϥΪ�Model����

	JScrollBar sbHor = new  JScrollBar(JScrollBar.HORIZONTAL),
					  sbVer = new JScrollBar(JScrollBar.VERTICAL, 0, 0, 0, 0);
	//�ŧi������V�P������V�����b


	JPanel jpImage =  new JPanel(); //�ŧi�e�ǹϤ����e������
	JViewport vp = new JViewport(); //�ŧi��ܹϤ����˵���

	JLabel lbHor = new JLabel("�������b (value, min, max) : "),
				lbVer = new JLabel("�������b (value, min, max) : "),
				lbHorPos = new JLabel("0", JLabel.LEFT),
				lbVerPos = new JLabel("0", JLabel.LEFT),
				lbVPP = new  JLabel("�i���ϭ��I : "),
				lbVPPos = new JLabel(),
				lbVPS = new JLabel("�i���Ϥj�p : "),
				lbVPSize = new JLabel();
	//�ŧi��ܿ���Ҥ���

	//��s�����W����ܪ���T
	private void updateMsg(){

		Point position = vp.getViewPosition();
		//���o�˵��ϭ��I�bView���󪺮y��

		lbVerPos.setText(" ("  + sbVer.getValue() + " ," 
									+ sbVer.getMinimum() + " ," 
									+ sbVer.getMaximum() + ") ");
		//�̧���ܫ������b���ȡB�̤p�ȻP�̤j��

		lbHorPos.setText(" ("  +  sbHor.getValue() + " ," 
									+ sbHor.getMinimum() + " ," 
									+ sbHor.getMaximum() + ") ");
		//�̧���ܤ������b���ȡB�̤p�ȻP�̤j��

		lbVPPos.setText(" (" + position.x + " ,"
									 +	position.y +  ") ");
		//����˵��ϭ��I�bView����y�Ъ���m

		lbVPPos.setText("(" + position.x + " ,"
									+ position.y +  ") ");
		//����˵��ϭ��I�bView����y�Ъ���m
	}

	ScrollEX(){

		sbHor.setModel(brmHor);
		//�]�w������V���ʶb�ϥΪ�Model����

		sbHor.setUnitIncrement(10); //�]�w���U�b�Y���s�����W�q
		sbHor.setBlockIncrement(5); //�]�w���U���b�϶������ʼW�q

		jpImage.add(lbImage); //�N��ܹϤ������ҥ[�J�Ϥ��e������
		vp.setView(jpImage); //�]�w�˵�����ܪ�View����

		//���U�^���������bChangeEvent�ƥ󪺺�ť������
		brmHor.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){

				DefaultBoundedRangeModel  source = 
							(DefaultBoundedRangeModel ) e.getSource();
				//���o�޵o�ƥ�Model����

				Point position = vp.getViewPosition();
				//���o�ثe�˵��ϥ��W�����I�bView���󤧮y�Ъ���m

				position.x = source.getValue();
				//���o�޵o�ƥ󤧱��b���]�w��

				updateMsg(); //��s�����W����ܪ���T

				vp.setViewPosition(position); //�]�w�˵��ϭ��I����m
				vp.repaint(); //��ø�e��
			}
		});

		//���U��ťAdjustmentEvent�ƥ󪺺�ť������
		sbVer.addAdjustmentListener(new AdjustmentListener(){

			public void adjustmentValueChanged(AdjustmentEvent e){

				Point position = vp.getViewPosition();
				//���o�ߵ��ϭ��I�bView���󪺦�m

				JScrollBar sb = (JScrollBar)e.getAdjustable();
				//���o�޵o�ƥ󪺱��b

				position.y = e.getValue();
				//�N���b���ȳ]�w���˵��ϭ��I��y�y��

				vp.setViewPosition(position);
				//�]�w�˵��ϭ��I���bView���󪺮y�Ц�m

				vp.repaint(); //��ø����

				updateMsg(); //��s�����W����ܪ���T
			}
		});

		addWindowListener(new WindowAdapter(){
			public void windowOpened(WindowEvent e){

				Dimension dm = vp.getExtentSize();
				//���o�˵��ϥi���d�򪺤j�p

				sbHor.setBorder(new EmptyBorder(0, 0, 0, sbVer.getWidth()));
				//�]�w�������b�ϥΪťծؽu, 
				//����N�X�{�P�������b�e�׬ۦP���ťն���

				lbVPSize.setText(" [ " + dm.getWidth() 
												+ " x " + dm.getHeight() + "] ");
				//�]�w�˵��Ϫ��j�p
				
				int intHorMax = ii.getIconWidth() 
								- new Double(dm.getWidth()).intValue();
				//�H�Ϥ��j�p��h�i���d��j�p�p��������b���̤j��

				sbHor.setMaximum(intHorMax > 0 ? intHorMax : 0);
				//�]�w�������b���̤j�Ȭ��Ϥ��e�״�h�Ϥ��e���ثe���e��

				int intVerMax = ii.getIconHeight() - new Double(dm.getHeight()).intValue();

				sbVer.setMaximum(intVerMax > 0 ? intVerMax : 0);
				//�]�w�������b���̤j�Ȭ��Ϥ����״�h�Ϥ��e���ثe������
			}
		});

		//���U�^��ComponentEvent�ƥ󪺺�ť������
		addComponentListener(new ComponentAdapter(){	

			//�^������j�p�վ�
			public void componentResized(ComponentEvent e){

				Dimension dm = vp.getExtentSize();
				//���o�˵��ϥi���d�򪺤j�p

				lbVPSize.setText(" [ " + dm.getWidth() 
												+ " x " + dm.getHeight() + "] ");
				//�]�w�˵��Ϫ��j�p
				
				int intHorMax = ii.getIconWidth() 
								- new Double(dm.getWidth()).intValue();
				//�H�Ϥ��j�p��h�i���d��j�p�p��������b���̤j��

				sbHor.setMaximum(intHorMax > 0 ? intHorMax : 0);
				//�]�w�������b���̤j�Ȭ��Ϥ��e�״�h�Ϥ��e���ثe���e��

				int intVerMax = ii.getIconHeight() - new Double(dm.getHeight()).intValue();

				sbVer.setMaximum(intVerMax > 0 ? intVerMax : 0);
				//�]�w�������b���̤j�Ȭ��Ϥ����״�h�Ϥ��e���ثe������
			}
		});

		MouseInputAdapter mia = new MouseInputAdapter(){

			private boolean firstTime  = true; //�P�_�O�_���Ĥ@������

			private Point lastPos = new Point();
			//�����ƹ��W�@�����즲�y��

			public void mouseReleased(MouseEvent e){

				firstTime = true; //���]firstTime��

				Point position = vp.getViewPosition();
				//���o�ߵ��ϭ��I�bView���󪺦�m

				sbVer.setValue(position.y);
				sbHor.setValue(position.x);
				//���o�޵o�ƥ󪺱��b
			}

			public void mouseDragged(MouseEvent e){
				Point position = vp.getViewPosition();
				//���o�ߵ��ϭ��I�bView���󪺦�m

				if(firstTime) {
					firstTime = false;
					lastPos.setLocation(e.getX(), e.getY());
				}
				else{
					double newX = position.getX() 
									- (e.getX() - lastPos.getX());
					//�p��s��X�b�y��

					//��X�y�Фp��0, �h�]�w��0
					//�YX�y�Фj�󱲶b�̤j��, �h�]�w�����b�̤j��
					if(newX < 0.0)
						newX = 0.0;
					else if(newX > sbHor.getMaximum())
						newX = sbHor.getMaximum();
	
					double newY = position.getY()
									- (e.getY() - lastPos.getY());
					//�p��s��Y�y��

					//��Y�y�Фp��0, �h�]�w��0
					//�YY�y�Фj�󱲶b�̤j��, �h�]�w�����b�̤j��
					if(newY < 0.0)
						newY = 0.0;
					else if(newY > sbVer.getMaximum())
						newY = sbVer.getMaximum();

					lastPos.setLocation(e.getX(), e.getY());
					//�N�ƹ��y�г]�w��lastPos����ѤU���ϥ�

					updateMsg();

					vp.setViewPosition(new Point(
							(new Double(newX)).intValue() ,
							(new Double(newY)).intValue()));
					//�]�w�˵��ϭ��I�bView���󪺮y��
					vp.repaint(); //��ø�˵���

				}
			}
		};

		//���U�^��ComponentEvent�ƥ󪺺�ť������
		vp.addMouseListener(mia);
		vp.addMouseMotionListener(mia);

		//�إ���ܱ��b��T��Box����
		Box bxSBMsg = new Box(BoxLayout.X_AXIS);
		bxSBMsg.add(lbHor);
		bxSBMsg.add(lbHorPos);
		bxSBMsg.add(Box.createHorizontalStrut(10));
		bxSBMsg.add(lbVer);
		bxSBMsg.add(lbVerPos);

		//�إ�����˵��ϸ�ƪ�Box����
		Box bxVPMsg = new Box(BoxLayout.X_AXIS);
		bxVPMsg.add(lbVPP);
		bxVPMsg.add(lbVPPos);
		bxVPMsg.add(Box.createHorizontalStrut(10));
		bxVPMsg.add(lbVPS);
		bxVPMsg.add(lbVPSize);

		JPanel jpMsg = new JPanel(new GridLayout(2, 1));
		jpMsg.add(bxSBMsg);
		jpMsg.add(bxVPMsg);

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new BorderLayout(2, 2));
		//�]�w���e���������P�������Z����10��BorderLayout����޲z����

		add(jpMsg, BorderLayout.NORTH); //�[�J��ܸ�T����������
		add(vp); //�[�J��ܰ�
		add(sbHor, BorderLayout.SOUTH); //�[�J�������b
		add(sbVer, BorderLayout.EAST); //�[�J�������b

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(600, 400);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ScrollEX(); //�ŧi�����ج[����
	}
}