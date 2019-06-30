import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.rmi.registry.*;

// java Client <clientName> <serverName>

public class Client {
    public static void main (String args[]) {
        String clientName = args[0];
        String serverName = args[1];

        try {
            Registry reg = LocateRegistry.getRegistry(1099);

            // Server holen
            ChatServer server = (ChatServer) reg.lookup(serverName);

            System.out.println ("Betrete den Chat: " + serverName);
            System.out.println("Im Chat befinden sich bereits: " + server.erfrage_Clients().toString());

            server.register_Client(clientName);

            ClientImpl client = new ClientImpl(clientName, reg);

            // Strings lesen und als Chatnachrichten versenden
            try(Reader reader = new InputStreamReader(System.in);
                BufferedReader input = new BufferedReader(reader)) {
                for (String line = input.readLine(); line != null; line = input.readLine()) {

                    server.sende_Textbeitrag(new TextBeitrag(line, clientName));

                }
                System.out.println("Verlasse den Chat: " + serverName);
            }
            server.deregister_Client(clientName);

        } catch (Exception e) {
            System.out.println("ServerException" + e.getMessage());
        }
    }
}