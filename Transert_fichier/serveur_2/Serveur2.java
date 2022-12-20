package Server_twoo;
import infra.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.*;
import static java.nio.file.StandardOpenOption.*;
public class Serveur2 {

    public void Evoie_fichier(int port) throws IOException{

        Commun c = new Commun();
        Socket socket=c.Creation_socket(port);
        System.out.println("fichier pret a envoyer au serveur3...\n");
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
        socket.close();

    }
    public void Envoie_autre_Server(int port) throws IOException{
        Serveur2 client=new Serveur2();
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

        Serveur2 serveur=new Serveur2();
        ServerSocket server_socket = new ServerSocket(5552);
        System.out.println("connection...");
        Socket socket = server_socket.accept(); 
        System.out.println("Connection venant du Serveur1 "+" " + socket);

        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        String message = dataInputStream.readUTF();
        serveur.Ecrire_fichier(message);
        server_socket.close();
        serveur.Envoie_autre_Server(5553);

    }
}

 
