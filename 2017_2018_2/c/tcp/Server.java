import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {

	private static final int PORT = 3333;
	private static final int BACK_LOG_SIZE = 127;
	private static final int BUFFER_SIZE = 4096;
	
	private final byte[] BUFFER = new byte[BUFFER_SIZE];
	private ServerSocket serverSocket;
	private boolean serverRunning;

	public Server() throws IOException {
		this(null);
	}

	public Server(String host) throws IOException {

		if (host != null) {
			InetAddress ipv4Address = InetAddress.getByName(host); 
			this.serverSocket = new ServerSocket(PORT, BACK_LOG_SIZE, ipv4Address);
		} else {
			this.serverSocket = new ServerSocket(PORT, BACK_LOG_SIZE);
		}
		this.serverRunning = true;
	}

	public static void main(String args[]) throws IOException {

		Server server = null;
		if (args.length < 1) {
			server = new Server();
		} else {
			server = new Server(args[0]);
		}
		server.listen();
	}

	public void listen() throws IOException {

		System.out.println("Server started running on port " + PORT);
		while (serverRunning) {
			Socket client = serverSocket.accept();
			processClient(client);
		}
		System.out.println("Server stopped running..!");
		if (serverSocket != null) {
			serverSocket.close();
		}
	}

	public void processClient(final Socket client) {

		try (BufferedReader clientInput = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
			String line = null;
			long totalBytesRead = 0L;
			StringBuilder message = new StringBuilder();
			clientInput
			while ((line = clientInput.readLine()) != null) {
				totalBytesRead += line.length();
				message.append(line);
			}
			if (client != null) {
				System.out.println("Client: " + client.getInetAddress().getHostAddress());
				System.out.println("Message size: " + totalBytesRead);
				System.out.println("Message content: " + message);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) { }
			}
		}
	}
}
