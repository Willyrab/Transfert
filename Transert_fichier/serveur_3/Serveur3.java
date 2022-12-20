package Server_three;
import infra.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.*;
import static java.nio.file.StandardOpenOption.*;
public class Serveur3 {

    public void Evoie_fichier(int port) throws IOException{

        Commun c = new Commun();
        Socket socket=c.Creation_socket(port);
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
        }while(message!=null);

        input.close();
        dataOutputStream.writeUTF(contenu);
        dataOutputStream.flush();
        dataOutputStream.close();
        socket.close();

    }
    private void Envoie_autre_Server(int port) throws IOException{
        Serveur3 client=new Serveur3();
        client.Evoie_fichier(port);
    }
    public void Ecrire_fichier(String s){

        Path chemin = Paths.get("fichier_client.txt");
        byte[] data = s.getBytes();
        OutputStream output = null;
        try {
            
            output = new BufferedOutputStream(Files.newOutputStream(chemin, CREATE));
            output.write(data);
            output.flush();
            output.close();

        } catch (Exception e) {
            System.out.println("Message " + e);
        }
    }

    public static void main(String[] args) throws IOException {

        Serveur3 serveur=new Serveur3();
        ServerSocket server_socket = new ServerSocket(5553);
        System.out.println("connection...");
        Socket socket = server_socket.accept(); 
        System.out.println("Connection venant Serveur2 "+" "+ socket);

        InputStream inputStream = socket.getInputStream();

        DataInputStream dataInputStream = new DataInputStream(inputStream);
        String message = dataInputStream.readUTF();
        serveur.Ecrire_fichier(message);
        System.out.println("Fichier bien recue...");
        server_socket.close();

    }
}

 
 
