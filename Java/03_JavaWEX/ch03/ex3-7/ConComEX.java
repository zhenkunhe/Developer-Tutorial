import javax.swing.*; //�ޥ�Swing�M��
import java.awt.*;
import java.awt.event.*; //�ޥγB�z�ƥ�event�M��

public class ConComEX extends JFrame {

	JLabel lbMsg = new JLabel("ComponentEvent�ƥ󪺦^���ܽd",
													JLabel.CENTER);

	JButton btnAction = new JButton("��������");
	
	//�H�ΦW���O��@ComponentListener����, 
	//�ëŧi��ťComponentEvent�ƥ󪺺�ť������
	ComponentListener clFrame = new ComponentListener(){
		//�^�����ä���欰����k
		public void componentHidden(ComponentEvent e){
			lbMsg.setText("ComponentEvent : ���ä���");
		}
		//�^�����ʤ���欰����k
		public void componentMoved(ComponentEvent e){
			lbMsg.setText("ComponentEvent : ���ʤ���");
		}
		//�^���վ㤸��j�p�欰����k
		public void componentResized(ComponentEvent e){
			lbMsg.setText("ComponentEvent : �վ㤸��j�p");
		}
		//�^�����ä���欰����k
		public void componentShown(ComponentEvent e){
			lbMsg.setText("ComponentEvent : ��ܤ���");
		}
	};

	//�H�ΦW���O��@ContainerListener����, �ëŧi��ť������
	ContainerListener conlFrame = new ContainerListener(){
		//�^������[�J�欰����k
		public void componentAdded(ContainerEvent e){
			 btnAction.setText("��������"); //�]�w���s��ܪ���r
		}
		//�^�����󲾰��欰����k
		public void componentRemoved(ContainerEvent e){
			 btnAction.setText("�[�J����");
		}
	};

	ConComEX(){

		//�H�ΦW���O���覡�ŧi�^��ActionEvent�ƥ󪺺�ť��,
		//�éI�saddActionListener()��k���U��ť������
		btnAction.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){
				JButton source = (JButton) e.getSource();
				//���o�ƥ�ӷ������s����

				if(source.getText().equals("�[�J����")){
					ConComEX.this.add(lbMsg);
					//�I�s�~�����O��add()��k�[�J���Ҥ���
				}
				else{
					ConComEX.this.remove(lbMsg);
					//�I�s�~�����O��add()��k�������Ҥ���
				}

				ConComEX.this.repaint();
				//�I�s�~�����O��repaint()��k��ø�����e��
			}
		});

		add(lbMsg); //�N��ܰT�������ҥ[�J���e����
		add(btnAction, BorderLayout.SOUTH); //�N���s�[�J���e����
		
		addComponentListener(clFrame);

		getContentPane().addContainerListener(conlFrame);
		//���U��conlFrame��ť������ContainerEvent

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250, 150);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ConComEX(); //���͵����ج[����
	}
}