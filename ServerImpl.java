import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerImpl extends UnicastRemoteObject implements ChatServer{

    private ArrayList<String> teilnehmerListe;
    private Registry reg;

    public ServerImpl(String name, Registry reg) throws RemoteException {

        try {
            reg.rebind(name, this);
            this.reg = reg;
            teilnehmerListe = new ArrayList<>();
        }
        catch (Exception e) {
            System.out.println ("Server construction failed: " + e.getMessage());
        }
    }

    @Override
    public void register_Client(String name) throws RemoteException, ChatException {
        if (!this.teilnehmerListe.contains(name)) {
            // Neuen Teilnehmer hinzufügen
            teilnehmerListe.add(name);
            System.out.println("Neuer Teilnehmer: " + name);

            // Den anderen Chatteilnehmern den Neuzugang mitteilen
            for (String n: teilnehmerListe) {
                if (!n.equals(name)) {
                    try {
                        ChatClient client = (ChatClient) reg.lookup(n);
                        client.notify_Client_Change(name + " betritt den Chat.");
                    } catch (NotBoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            throw new ChatException("Name bereits vergeben: " + name);
        }
    }

    @Override
    public void deregister_Client(String name) throws RemoteException, ChatException {
        if (this.teilnehmerListe.contains(name)) {
            // Teilnehmer entfernen
            teilnehmerListe.remove(name);
            System.out.println("Teilnehmer wird entfernt: " + name);

            // Anderen Teilnehmern das Verlassen von name mitteilen
            for (String n: teilnehmerListe) {
                try {
                    ChatClient client = (ChatClient) reg.lookup(n);
                    client.notify_Client_Change(name + " hat den Chat verlassen.");
                } catch (NotBoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            throw new ChatException("Teilnehmer nicht vorhanden. " + name);
        }
    }

    @Override
    public void sende_Textbeitrag(TextBeitrag tb) throws RemoteException {
        for (String name: teilnehmerListe) {
            try {
                ChatClient client = (ChatClient) reg.lookup(name);
                client.verteile_Textbeitrag(tb);
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<String> erfrage_Clients() {
        return new ArrayList<>(teilnehmerListe);
    }
}
