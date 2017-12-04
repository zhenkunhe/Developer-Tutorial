import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;

import java.net.URL;

public class EditorPaneEX extends JFrame {

	JEditorPane epEditor = new JEditorPane(); //���s�譱��
	JTextArea taMsg = new JTextArea(); //��ܰT������r��

	//�H������]�w���s�譱����ܪ����
	class LoadThread extends Thread{

		private URL url; //���URL���|
		private String contentType = null; //���榡�r��

		//�غc�l
		LoadThread(URL url, String contentType){
			this.url = url;
			this.contentType = contentType;
		}

		public void run(){	 //����{��

			epEditor.setContentType("text/" + contentType);
			//�]�w��r�s�譱���B�z��󪺮榡

			try{
				epEditor.setPage(url); //�]�w��r�s�譱����ܪ����
			}
			catch(java.io.IOException ioe){
				System.err.println("�L�k���`�}�Ҥ�� "
															+ ioe.toString());
			}

			taMsg.append("�ϥ�Document�������O : "
				+ epEditor.getDocument().getClass().getName() + "\n");
			//��ܤ�r�s�譱���ϥΤ�Document�������O
		}
	};

	EditorPaneEX(){

		taMsg.setRows(6); //�]�w��r�Ϫ��C��
		taMsg.setLineWrap(true); //�]�w�۰��_��
		taMsg.setWrapStyleWord(true); //���b��r���_��

		epEditor.setEditable(false);
		//�]�w�L�k����s��, �s��HTML�榡����, 
		//�~��޵oHyperlinkEvent�ƥ�

		//�w�q�B���U�^��HyperlinkEvent�ƥ󪺺�ť������
		epEditor.addHyperlinkListener(new HyperlinkListener(){

			public void hyperlinkUpdate(HyperlinkEvent e){

				HyperlinkEvent.EventType eventType = e.getEventType();
				//���oHyperlinkEvent�ƥ�����
				
				//�P�_HyperlinkEvent�ƥ������O�_���s��
				if(eventType 
					== HyperlinkEvent.EventType.ACTIVATED){

					taMsg.append("[ACTIVATED] �s���W�s����}�� ["
												+ e.getURL()  + "]\n");
					//����s����}

					LoadThread ltd = new LoadThread(e.getURL(), "html");
					//�ŧi�]�w��r�s�譱����ܤ�󪺰��������

					ltd.start();	 //�Ұʰ����
				}
				else if(eventType 
					== HyperlinkEvent.EventType.ENTERED){
				//�P�_HyperlinkEvent�ƥ�����
				//�O�_���ƹ���жi�J�W�s���d��

					taMsg.append(
						"[ENTERED] �ƹ���жi�J�W�s���d��\n");
				}
				else if(eventType == 
					HyperlinkEvent.EventType.EXITED){
				//�P�_HyperlinkEvent�ƥ�����
				//�O�_���ƹ�������}�W�s���d��

					taMsg.append("[EXITED] �ƹ�������}�W�s���d��\n");
				}
			}
		});

		LoadThread ltd = new LoadThread(
			EditorPaneEX.class.getResource("res/bitc.htm"), "html");
		//�ŧi�����r�s�譱�����J���ʧ@�����������

		ltd.start(); //�Ұʰ����

		JToggleButton
			btnHTML = new JToggleButton("���HTML���", true),
			btnRTF = new JToggleButton("���RTF���"),
			btnPlain = new JToggleButton("��ܯ¤�r���");
		//�ŧi�u��C��JToggleButton���s

		ButtonGroup bg = new ButtonGroup(); //�ŧi���s�s��
		bg.add(btnHTML); //�N���s�[�J�s��
		bg.add(btnRTF);
		bg.add(btnPlain);
		
		btnHTML.setActionCommand("html"); //�]�w�ʧ@�R�O�r��
		btnRTF.setActionCommand("rtf");
		btnPlain.setActionCommand("plain");

		//�ŧi��ťActionEvent�ƥ󪺺�ť������
		ActionListener alLoad = new ActionListener(){

			String strCommand; //�x�s�ʧ@�R�O�r��

			//�^��ActionEvent�ƥ󪺤�k
			public void actionPerformed(ActionEvent e){

				URL url = null;

				strCommand = e.getActionCommand();
				//���o�޵o�ƥ󤧫��s���ʧ@�R�O�r��

				//�P�_�ʧ@�R�O�r��, �ŧi�����J���URL���|
				if("html".equals(strCommand))
					url =
						EditorPaneEX.class.getResource("res/bitc.htm");
				else if("rtf".equals(strCommand))
					url =
						EditorPaneEX.class.getResource("res/bitc.rtf");
				else if("plain".equals(strCommand))
					url =
						EditorPaneEX.class.getResource("res/bitc.txt");

				LoadThread ltd = new LoadThread(url, strCommand);
				//�ŧi���J��󪺰��������

				ltd.start(); //�Ұʰ����
			}
		};

		btnHTML.addActionListener(alLoad);
		btnRTF.addActionListener(alLoad);
		btnPlain.addActionListener(alLoad);
		//���U�^��ActionEvent�ƥ󪺺�ť������

		JToolBar tbShow = new JToolBar();
		tbShow.add(btnHTML);
		tbShow.addSeparator(); //�[�J���j�Ÿ�
		tbShow.add(btnRTF);
		tbShow.addSeparator();
		tbShow.add(btnPlain);
		//�N���s�[�J�u��C

		Container cp = getContentPane(); //���o���e����
		cp.setLayout(new BorderLayout(5,  5));
		//�]�w�ϥζ��j�e�׬�5��BorderLayout����t�m����

		cp.add(new JScrollPane(epEditor));
		cp.add(tbShow, BorderLayout.NORTH);
		cp.add(new JScrollPane(taMsg), BorderLayout.SOUTH);

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));

		//�]�w�����w�]�������ʧ@�B�����j�p, ����ܵ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 400);
		setVisible(true);
	}

	public static void main(String args[]) {
		new EditorPaneEX(); //�ŧi�����ج[����
	}
}