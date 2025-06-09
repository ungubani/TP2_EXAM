// Написать фрагмент кода, отвечающий за получение пакета для протокола TCP

package taskThree;

import java.net.*;
import java.io.*;

public class TaskThree {
    public static void main(String[] args) {
        int portNumber = Integer.parseInt(args[0]);

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;

            MyProtocol myProt = new MyProtocol();
            outputLine = myProt.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = myProt.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye.")) break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


class MyProtocol {
    public MyProtocol() {}

    public String processInput(String inputLine) {
        if (inputLine == null) return "Hi!";  // Приветствие в начале
        if (inputLine.equals("ByeBye!")) return "Bye.";  // Прощание в конце (но только если правильная специальная фраза)

        return inputLine + "\n[NOTE: For quit send 'ByeBye!']";  // Иначе эхо + напоминалка, как выйти
    }
}