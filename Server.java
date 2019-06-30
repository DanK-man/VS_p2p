import java.rmi.registry.*;

public class Server {
    public static void main (String args[]) {
        String serverName = args[0];
        try {
            //Registry reg = LocateRegistry.createRegistry(1099);
            Registry reg = LocateRegistry.getRegistry(1099);

            ServerImpl server = new ServerImpl(serverName, reg);

            System.out.println ("Chat-Server " + serverName + "  ready.");
        } catch (Exception e) {
            System.out.println("ServerException" + e.getMessage());
        }
    }
}
