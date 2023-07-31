package PrimeNumberServer;

import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Server extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    // Text area for displaying contents
    TextArea ta = new TextArea();

    // Create a scene and place it in the stage
    Scene scene = new Scene(new ScrollPane(ta), 450, 200);
    primaryStage.setTitle("Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    
    new Thread( () -> {
      try {
        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(8000);
        Platform.runLater(() ->
          ta.appendText("Server started at " + new Date() + '\n'));
  
        // Listen for a connection request
        Socket socket = serverSocket.accept();
  
        // Create data input and output streams
        DataInputStream inputFromClient = new DataInputStream(
          socket.getInputStream());
        DataOutputStream outputToClient = new DataOutputStream(
          socket.getOutputStream());
  
        while (true) {
          // Receive number to check for prime number from the client
          int numberToCheck = inputFromClient.readInt();

          if (isPrime(numberToCheck))
          {
            // Send "yes" result back the client
            outputToClient.writeUTF("yes");
          }
          else {
            // Send "no" result back the client
            outputToClient.writeUTF("no");
          }

          Platform.runLater(() -> {
            ta.appendText("Number received from client to check prime number is: "
              + numberToCheck + '\n');
          });
        }
      }
      catch(IOException ex) {
        ex.printStackTrace();
      }
    }).start();
  }

  private static boolean isPrime(int number) {
    // A prime number is greater than 1
    if (number <= 1) {
      return false;
    }

    // 2 and 3 are prime numbers
    if (number <= 3) {
      return true;
    }

    // If the number is divisible by 2 or 3, it's not a prime number
    if (number % 2 == 0 || number % 3 == 0) {
      return false;
    }

    // Check for divisors starting from 5 up to the square root of the number
    for (int i = 5; i * i <= number; i += 6) {
      if (number % i == 0 || number % (i + 2) == 0) {
        return false;
      }
    }

    // If no divisors are found, the number is prime
    return true;
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
