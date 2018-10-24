import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
	
	private static final int PORT = 3333;
	private Socket clientSocket;
	
	public Client(String host) throws IOException {
		InetAddress ipv4Address = InetAddress.getByName(host);
		this.clientSocket = new Socket(ipv4Address, PORT);
	}
	
	public static void main(String[] args) throws IOException {
		
		if (args.length != 2) {
			throw new IllegalArgumentException("Provide host and message");
		}
		String host = args[0];
		String message = args[1];
		Client client = new Client(host);
		client.sendMessage(message);
		client.closeSocket();
	}
	
	public void sendMessage(String message) throws IOException {
		
		try (BufferedOutputStream serverOut = new BufferedOutputStream(clientSocket.getOutputStream())) {
			serverOut.write(message.getBytes());
		}
		System.out.println("Message send..!");
	}
	
	public void closeSocket() {
		
		if (clientSocket != null) {
			try {
				clientSocket.close();
			} catch (IOException e) { }
		}
	}
}
