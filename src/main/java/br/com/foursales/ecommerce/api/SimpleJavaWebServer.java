package br.com.foursales.ecommerce.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleJavaWebServer {
    public static void main(String[] args) {
        int port = 8080;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            // Thread pool to handle multiple requests
            ExecutorService executor = Executors.newFixedThreadPool(10);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Accept incoming connection
                executor.submit(() -> handleRequest(clientSocket)); // Handle in a separate thread
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleRequest(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            OutputStream output = clientSocket.getOutputStream()) {

            // Read HTTP request line (e.g., "GET / HTTP/1.1")
            String requestLine = reader.readLine();
            System.out.println("Request: " + requestLine);

            // Parse the requested path
            String[] parts = requestLine.split(" ");
            String method = parts[0]; // GET, POST, etc.
            String path = parts[1];   // Requested resource ("/")

            // Generate a simple HTTP response
            String responseContent = "<html><body><h1>Hello from Java Web Server!</h1></body></html>";
            String response = """
                HTTP/1.1 200 OK\r
                Content-Type: text/html\r
                Content-Length: %d\r
                \r
                %s""".formatted(responseContent.length(), responseContent);


            output.write(response.getBytes()); // Send response to client
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
