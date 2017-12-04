import java.io.*;
import java.net.*;
 
// 1. ���{�������P UdpServer.java �{���f�t����A������ UdpServer �A���楻�{���C
// 2. ���{���������@�ӰѼơA���w���A���� IP�C
// �Ϊk�d�ҡG java UdpClient 127.0.0.1
 
public class UdpClient extends Thread {
    int port;            // port : �s����
    InetAddress server; // InetAddress �O IP, ���B�� server �����O���A�� IP
    String msg;            // ���ǰe���T���A�C�� UdpClient �u��ǰe�@�ӰT���C
 
    public static void main(String args[]) throws Exception {
        for (int i=0; i<100; i++) {
            // �إ� UdpClient�A�]�w�ǰe��H�P�ǰe�T���C
            UdpClient client = new UdpClient(args[0], 5555, "UdpClient : "+i+"th message");
            client.run(); // �Ұ� UdpClient �}�l�ǰe�C
        }
    }
 
    public UdpClient(String pServer, int pPort, String pMsg) throws Exception {
        port = pPort;                             // �]�w�s����
        server = InetAddress.getByName(pServer); // �N���A�����}�ഫ�� IP�C
        msg = pMsg;                                 // �]�w�ǰe�T���C
    }
 
    public void run() {
      try {
        byte buffer[] = msg.getBytes();                 // �N�T���r�� msg �ഫ���줸��C
        // �ʸ˸Ӧ줸�ꦨ���ʥ] DatagramPacket�A�P�ɫ��w�ǰe��H�C
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, server, port); 
        DatagramSocket socket = new DatagramSocket();    // �إ߶ǰe�� UDP Socket�C
        socket.send(packet);                             // �ǰe
        socket.close();                                 // ���� UDP socket.
      } catch (Exception e) { e.printStackTrace(); }    // �Y�����~���͡A�C�L��ƩI�s���|�C
    }
}