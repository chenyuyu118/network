import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class UDPPingServer {
    public static final int SERVER_PORT = 12000;

    public static void main(String[] args) throws SocketException {
        DatagramSocket server = new DatagramSocket(SERVER_PORT);
        System.out.println("UDP�������Ѿ�����");
        byte[] data = new byte[1024];
        while (!server.isClosed()) {
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                server.receive(packet);
                int random = (new Random()).nextInt() % 10;
                if (random < 4)
                    continue;
                byte[] realData = packet.getData();
                String str = new String(realData, 0, realData.length);
                System.out.println("receive from" + packet.getSocketAddress() + ": " + str);
                data = str.toUpperCase().getBytes(StandardCharsets.UTF_8);
                packet.setData(data);
                server.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
