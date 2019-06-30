import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ChatServer extends Remote {

    void register_Client(String name) throws RemoteException, ChatException;
    void deregister_Client(String name) throws RemoteException, ChatException;
    void sende_Textbeitrag(TextBeitrag tb) throws RemoteException, ChatException;
    ArrayList<String> erfrage_Clients() throws RemoteException, ChatException;

}
