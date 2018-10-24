import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
	
	private enum Operation {
		UPLOAD,
		DOWNLOAD
	}
	
	private enum OverwriteFile {
		YES("Yes"),
		NO("No"),
		Y("Y"),
		N("N");
		
		private String value;
		private OverwriteFile(String value) {
			this.value = value;
		}
	}

	private static final int PORT = 3333;
	private static final int BACK_LOG_SIZE = 127;
	private static final int BUFFER_SIZE = 4096;
	
	private ServerSocket serverSocket;
	private boolean serverRunning;

	public Server() throws IOException {
		this(null);
	}

	public Server(String host) throws IOException {

		if (host != null) {
			InetAddress ipv4Address = InetAddress.getByName("10.0.242.18"); 
			this.serverSocket = new ServerSocket(PORT, BACK_LOG_SIZE, ipv4Address);
		} else {

			InetAddress ipv4Address = InetAddress.getByName("10.0.242.18"); 
			this.serverSocket = new ServerSocket(PORT, BACK_LOG_SIZE, ipv4Address);
		}
		this.serverRunning = true;
	}

	public static void main(String args[]) throws IOException {

		Server server = new Server();
		server.listen();
	}

	public void listen() throws IOException {

		System.out.println("Server started running on port " + PORT);
		while (serverRunning) {
			Socket client = serverSocket.accept();
			new Thread(() -> processClient(client)).start();
		}
		System.out.println("Server stopped running..!");
		if (serverSocket != null) {
			serverSocket.close();
		}
	}

	public void processClient(final Socket client) {
		
		try {
			doOperation(client);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void doOperation(Socket client) throws IOException {

		BufferedReader clientInput = null;
		PrintWriter clientOutput = null;
		String clientOperation = null;
		try {
			clientInput = new BufferedReader(new InputStreamReader(client.getInputStream()));
			clientOutput = new PrintWriter(client.getOutputStream(), true);
			clientOperation = getClientOperation(clientInput);
			Operation operation = Operation.valueOf(clientOperation);
			System.out.println("Operation is: " + operation.toString());
			if (operation == Operation.UPLOAD) {
				uploadFileToServer(clientInput, clientOutput);
			} else {
				downloadFileFromServer(clientInput, clientOutput);
			}
		} catch (IllegalArgumentException e) {
			clientOutput.println("Invalid file operation: " + clientOperation);
		} catch (IOException e) {
			clientOutput.println(e.getMessage());
		}
	}

	private String getClientOperation(BufferedReader clientInput) throws IOException {
		return clientInput.readLine();
	}

	private void downloadFileFromServer(BufferedReader clientInput, PrintWriter clientOutput) throws IOException {
		
		String fileName = clientInput.readLine();

		File fileToSend = new File("D:\\Programming\\Eclipse\\SocketFileTransfer\\" + fileName);
		if (!fileToSend.exists()) {
			clientOutput.println("File " + fileName + " does not exist");
			return;
		}
		try (FileInputStream fis = new FileInputStream(fileToSend)) {
			clientOutput.println(fis.readAllBytes());
			clientOutput.println(("File " + fileName + " has been send..!\n").getBytes());
		} catch (IOException e) {
			clientOutput.println(("File " + fileName + " failed to send..!\n").getBytes());
		} finally {
			clientOutput.flush();
		}
	}

	private void uploadFileToServer(BufferedReader clientInput, PrintWriter clientOutput) throws IOException {
		
		String fileName = null;
		File fileToUpload = null;
		fileName = clientInput.readLine();
		System.out.println("FileName is: " + fileName);
		fileToUpload = new File("upload.jpg");
		if (fileToUpload.exists() && isFileOverwriten(clientOutput, clientInput)) {
			fileToUpload.createNewFile();
		} else {
			clientOutput.println("Reading file..!");
			uploadFile(clientInput, fileToUpload, clientOutput);
		}
	}

	private void uploadFile(BufferedReader clientInput, File fileToUpload, PrintWriter clientOutput) {
		
		if (fileToUpload != null) {
			char[] buffer = new char[BUFFER_SIZE];
			String fileName = fileToUpload.getName();
			int bytesRead = 0;
			try (FileOutputStream fileOut = new FileOutputStream(fileToUpload)) {
				while ((bytesRead = clientInput.read(buffer, 0, BUFFER_SIZE)) > 0) {
					fileOut.write(new String(buffer, 0, bytesRead).getBytes());
					if (bytesRead < BUFFER_SIZE) {
						break;
					}
				}
				clientOutput.println("File " + fileName + " has been uploaded..!\n");
			} catch (IOException e) {
				clientOutput.println("File " + fileName + " failed to upload..!\n");
			} finally {
				clientOutput.flush();
			}
		}
	}

	private boolean isFileOverwriten(PrintWriter clientOutput, BufferedReader clientInput) throws IOException {
		
		boolean result = false;
		
		clientOutput.println("Overwrite file? Y/N");
		clientOutput.flush();
		String answer = clientInput.readLine();
		try {
			OverwriteFile overwriteFile = OverwriteFile.valueOf(answer);
			result = overwriteFile == OverwriteFile.YES || overwriteFile == OverwriteFile.Y;
		} catch (IllegalArgumentException e) {
			clientOutput.println("Invalid answer for overwriting file..!");
			throw new IOException(e);
		}
		
		return result;
	}
}