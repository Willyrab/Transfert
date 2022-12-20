import infra.*;
import java.net.Socket;
import java.io.*;
import java.nio.file.*;
import static java.nio.file.StandardOpenOption.*;

public class Client {

    public void Evoie_fichier(int port) throws IOException{
        Commun c = new Commun();
        Socket socket=c.Creation_socket(port);
        System.out.println("Envoie de fichier au serveur1 en cours ...\n");
        DataOutputStream dataOutputStream=c.CreateOutputStream(socket);
        Path chemin = Paths.get("fichier_client.txt");
        InputStream input = null;
        input = Files.newInputStream(chemin);
        String message = null;
        String contenu="";
        input = Files.newInputStream(chemin);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        do{
            message = reader.readLine();
            if(message!=null){
                contenu+=message;
                contenu+="\n";
            }
        }
        while(message!=null);
        input.close();
        dataOutputStream.writeUTF(contenu);
        dataOutputStream.flush();
        dataOutputStream.close();
    }
    public static void main(String[] args) throws IOException {
        Client client=new Client();
        client.Evoie_fichier(5551);
    }
}

