package kenny;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        final String QUIT_CLIENT = "quit";
        final int DEFAULT_PORT = 8888;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            System.out.println("Server was started, serverSocket: " + DEFAULT_PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client: [" + socket.getPort() + "] is connected");
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())
                );
                String msg = null;
                while ((msg = reader.readLine()) != null) {
                    System.out.println("Client[" + socket.getPort() + "]: " + msg);
                    writer.write("Server: " + msg + "\n");
                    writer.flush();

                    if (QUIT_CLIENT.equals(msg)) {
                        System.out.println("Client[" + socket.getPort() + "] is disconnected");
                        break;
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                    System.out.println("serverSocket is closed");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
