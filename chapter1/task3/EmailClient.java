import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class EmailClient {
    public static final int SERVER_PORT = 25; // simple smtp server's port
    public static final String HOST = "smtp.163.com"; // use 163's smtp server
    public static final String USER_NAME = "Y15859139708@163.com"; // username
    public static final String USER_PWD = "UBXHXJBWSMTEMMSF"; // user's relative password (for me, it's server's authority key)
    public static final String FROM = "<Y15859139708@163.com>"; // email from account('<>' is essential)
    public static final String TO = "<2458862813@qq.com>"; // email to account
    public static final String SUBJECT = "TEST"; // mail's subject
    public static final String CONTENT_TYPE = "text/plain"; // mail's content type
    public static final String CONTENT = "I love computer network"; // content to send
    public static Base64.Decoder decoder = Base64.getDecoder();
    public static Base64.Encoder encoder = Base64.getEncoder();

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        SocketAddress addr = new InetSocketAddress(HOST, SERVER_PORT);
        socket.connect(addr);
        try (var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             var out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {
            // start to communicate
            System.out.println(in.readLine());
            out.writeBytes("HELO 163.com\r\n");
            out.flush();
            System.out.println(in.readLine());
            out.writeBytes("AUTH LOGIN\r\n");
            out.flush();
            char[] statusCode = new char[3];
            if (in.read(statusCode, 0, 3) == -1) // once receive code can't be read, exit
                System.exit(-1);
            System.out.print(new String(statusCode, 0, 3) + " ");
            System.out.println(decodeBase64(in.readLine()));
            // send your own infos
            // 1.send username
            out.writeBytes(encodeBase64(USER_NAME) + "\r\n"); // '\r\n' is essential, for it's a mark of a meg's end.
            out.flush();
            if (in.read(statusCode, 0, 3) == -1)
                System.exit(-1);
            System.out.print(new String(statusCode, 0, 3) + " ");
            System.out.println(decodeBase64(in.readLine()));
            // 2.send pwd
            out.writeBytes(encodeBase64(USER_PWD) + "\r\n");
            out.flush();
            System.out.println(in.readLine());
            // authority id done, next is content.
            // 1.mail from
            out.writeBytes("MAIL FROM: " + FROM + "\r\n");
            out.flush();
            System.out.println(in.readLine());
            // 2.mail to
            out.writeBytes("RCPT TO: " + TO + "\r\n");
            out.flush();
            System.out.println(in.readLine());
            // 3.content
            // 3.1 content head
            out.writeBytes("DATA\r\n");
            out.flush();
            System.out.println(in.readLine());
            StringBuilder message = new StringBuilder("from:");
            message.append(FROM);message.append("\r\nto:");
            message.append(TO.replace("<", "").replace(">", ""));message.append("\r\nsubject:");
            message.append(SUBJECT);message.append("\r\nContent-Type:");
            message.append(CONTENT_TYPE);message.append("\t\n\r\n");
            // 3.2 real content
            message.append(CONTENT);
            // 3.3 mail end
            message.append("\r\n.\r\n");
            out.writeBytes(message.toString());
            out.flush();
            System.out.println(in.readLine());
            // close mail
            out.writeBytes("QUIT\r\n");
            out.flush();
            System.out.println(in.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close(); // close socket in the end no matter what happened
        }
    }

    public static String decodeBase64(String s) {
        s = s.trim();  // 这个trim很重要，因为空格无法解码
        byte []data = decoder.decode(s);
        return new String(data, 0, data.length);
    }

    /**
     * encode a sending meg to base64
     * @param s source str
     * @return a string encoded
     */
    public static String encodeBase64(String s) {
        byte[] data = encoder.encode(s.getBytes(StandardCharsets.UTF_8));
        return new String(data, 0, data.length);
    }
}