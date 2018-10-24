import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	private enum Operation {
		UPLOAD,
		DOWNLOAD
	}

	private static final int PORT = 3333;
	private static final int BACK_LOG_SIZE = 127;
	private static final int BUFFER_SIZE = 4097;
	private Socket clientSocket;
	
	public Client() throws IOException {
		this.clientSocket = new Socket("10.0.242.18", PORT);
	}
	
	public static void main(String[] args) throws IOException {
		Client client = new Client();
		client.doOperation();
	}

	private void doOperation() throws IOException {
		
		DataOutputStream serverOut = new DataOutputStream(clientSocket.getOutputStream());
		DataInputStream serverIn = new DataInputStream(clientSocket.getInputStream());
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter operation: ");
		Operation operation = Operation.valueOf(sc.nextLine());
		String fileName = sc.nextLine();
		serverOut.write((operation.toString() + "\n").getBytes());
		serverOut.write((fileName + "\n").getBytes());
		try (FileInputStream fis = new FileInputStream(new File(fileName))) {
			final byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = 0;
			while ((bytesRead = fis.read(buffer, 0, BUFFER_SIZE)) >= 0) {
				serverOut.write(buffer, 0, BUFFER_SIZE);
				if (bytesRead < BUFFER_SIZE) {
					break;
				}
			}
			System.out.println("Operation done..!");
			serverOut.flush();
			String response = serverIn.readLine();
			System.out.println("Server response: " + response);
		} finally {
			//clientSocket.close();
		}
	}
}
