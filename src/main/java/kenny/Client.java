package kenny;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        final String QUIT_CLIENT = "quit";

        final String DEFAULT_SERVER_HOST = "127.0.0.1";
        final int DEFAULT_SERVER_PORT = 8888;
        Socket socket = null;
        BufferedWriter writer = null;

        try {
            socket = new Socket(DEFAULT_SERVER_HOST, DEFAULT_SERVER_PORT);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );

            BufferedReader consoleReader = new BufferedReader(
                    new InputStreamReader(System.in)
            );

            while (true) {
                String input = consoleReader.readLine();

                writer.write(input + "\n");
                writer.flush();

                String msg = reader.readLine();
                System.out.println(msg);

                // check is user quit
                if (QUIT_CLIENT.equals(input)) {
                    break;
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                    System.out.println("socket is closed");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
