import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ChatClient extends Remote {

    void verteile_Textbeitrag(TextBeitrag tb) throws RemoteException;
    void notify_Client_Change(String sMsg) throws RemoteException;
}
