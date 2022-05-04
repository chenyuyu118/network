import java.io.*;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;


public class TCPServer {
    public static final int SOCKET_PORT = 12000;

    public static void main(String[] args) throws IOException {
        var s = new ServerSocket(SOCKET_PORT);
        while (true) {
            var connectSocket = s.accept();
        connectSocket.getOutputStream();
        try (var in = new Scanner(new BufferedInputStream(connectSocket.getInputStream()));
             var writer = connectSocket.getOutputStream();
             var printer = new PrintWriter(new BufferedOutputStream(writer))) {
            String fileName = in.nextLine().split(" ")[1];
            fileName = "." + fileName;
            var file = new File(fileName);
            if (file.isFile()) {
                byte[] data = Files.readAllBytes(Path.of(fileName));
                // printer.writeBytes("HTTP/1.0 200 OK\r\n");
                // printer.writeBytes("Connection: close\r\n");
                // printer.writeBytes("Content-Type: text/html\r\n");
                // printer.writeBytes("Content-Length: " + data.length + "\r\n");
                // printer.writeBytes("\r\n");
                printer.println("HTTP/1.0 200 OK");
                printer.println("Connection: close");
                printer.println("Content-Type: text/html");
                printer.println("Content-Length: " + data.length);
                printer.print("\r\n");
                printer.flush(); // 这个很重要！！,刷新缓冲流
                writer.write(data);
            } else {
                // printer.writeBytes("HTTP/1.0 404 Found\r\n");
                // printer.writeBytes("Content-Length: 0\r\n");
                // printer.writeBytes("\r\n");
                printer.println("HTTP/1.0 404 Found");
                printer.println("Connection: close");
                printer.println("Content-Length: 0");
                printer.println("\r\n");
            }
        }
        connectSocket.close();
        }
        
    }
}
