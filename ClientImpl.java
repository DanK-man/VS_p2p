import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements ChatClient {

    public ClientImpl(String name, Registry reg) throws RemoteException {

        try {
            reg.rebind(name, this);
        }
        catch (Exception e) {
            System.out.println ("Exception: " + e.getMessage());
        }
    }

    @Override
    public void verteile_Textbeitrag(TextBeitrag tb) {
        System.out.println(tb.absender + "@" + tb.getTime() + "\n\t" + tb.getMsg());
    }

    @Override
    public void notify_Client_Change(String serverMsg) {
        System.out.println(serverMsg);
    }

}
