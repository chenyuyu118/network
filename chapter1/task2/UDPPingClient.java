import java.io.IOException;
import java.lang.reflect.Array;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;

public class UDPPingClient {
    public static final int SERVER_PORT = 12000;
    // public static final String HOST = "localhost";
    public static final byte[] TEST_BYTE = "Hello1231flashcards231231231".getBytes(StandardCharsets.UTF_8);
    public static final String HOST = "175.178.166.84";

    public static void main(String[] args) throws SocketException, UnknownHostException {
        DatagramSocket client = new DatagramSocket();
        client.setSoTimeout(5);
        InetSocketAddress addr;
        if (HOST == "localhost")
            addr = new InetSocketAddress(HOST, SERVER_PORT);
        else
            addr = new InetSocketAddress(InetAddress.getByName(HOST), SERVER_PORT);
        DatagramPacket packet = new DatagramPacket(TEST_BYTE, TEST_BYTE.length);
        byte[] data = new byte[1024];
        packet.setSocketAddress(addr);
        for (int i = 0; i < 10; ++i) {
            try {
                Instant start = Instant.now();
                client.send(packet);
                DatagramPacket recv = new DatagramPacket(data, 1024);
                client.receive(recv);
                System.out.println(new String(recv.getData(), 0, recv.getData().length));
                Duration duration = Duration.between(start, Instant.now());
                System.out.println("RTT(ms):" + duration.toString());
            } catch (SocketTimeoutException e) {
                System.out.println("Time out!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
