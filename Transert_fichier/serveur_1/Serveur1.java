package Server_one;
import infra.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.*;
import static java.nio.file.StandardOpenOption.*;


public class Serveur1 {
    public void Evoie_fichier(int port) throws IOException{
        Commun c = new Commun();
        Socket socket=c.Creation_socket(port);
       
        System.out.println("chargement du transfert fichier au serveur2 ...\n");
       //DataOutputStream vous permet d'écrire des primitives Java dans OutputStream au lieu de seulement des octets. Vous encapsulez un OutputStream dans un DataOutputStream, puis vous pouvez y écrire des primitives. C'est pourquoi on l'appelle un DataOutputStream - parce que vous pouvez écrire des valeurs int, long, float et double dans le OutputStream, et pas seulement des octets bruts.
        DataOutputStream dataOutputStream=c.CreateOutputStream(socket);
        //initialisation de donnée
        Path chemin = Paths.get("fichier_client.txt");
        InputStream input = null;
        input = Files.newInputStream(chemin);
        String message = null;
        String contenu="";
        input = Files.newInputStream(chemin);
        //BufferedReader est permet de lire le texte d’un flux d’entrée (comme un fichier) en mettant sur le buffer de façon transparente des caractères, des tableaux, etc.
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
        //idina ilay io
        dataOutputStream.close();
        socket.close();
    }


    public void Envoie_autre_Server(int port) throws IOException{

        Serveur1 client=new Serveur1();

        client.Evoie_fichier(port);

    }

    public void Ecrire_fichier(String s){

        Path chemin = Paths.get("fichier_client.txt");

        //le string avadika tab octet
        byte[] data = s.getBytes();

        OutputStream output = null;
        try {
            // Un objet BufferedOutputStream est affecté à la référence OutputStream.
            output = new BufferedOutputStream(Files.newOutputStream(chemin, CREATE));
            //manoratra anaty fichier
            //La méthode write(byte) est utilisée pour écrire un seul octet dans le Java OutputStream. La méthode write() d'un OutputStream prend un int qui contient la valeur d'octet de l'octet à écrire. Seul le premier octet de la valeur int est écrit.
            output.write(data);

         // La méthode flush()  vide toutes les données écrites dans OutputStream vers la destination de données sous-jacente.
            output.flush();

            // idina le fichier
            output.close();

        } catch (Exception e) {
            System.out.println("Message  " + e);
        }

    }

    public static void main(String[] args) throws IOException {

        Serveur1 serveur=new Serveur1();
        ServerSocket server_socket = new ServerSocket(5551);
        System.out.println("Bienvenue sur taptap Send...");
        System.out.println("En attente du client...");
        Socket socket = server_socket.accept(); 
        System.out.println("Connection venant du client"+" "+ socket + "");

        InputStream inputStream = socket.getInputStream();

        DataInputStream dataInputStream = new DataInputStream(inputStream);

        // lire le message venant du socket
        //lit une chaîne qui a été encodée à l'aide d'un format UTF-8 modifié. La chaîne de caractères est décodée à partir de l'UTF et renvoyée sous forme de chaîne
        String message = dataInputStream.readUTF();
        serveur.Ecrire_fichier(message);
        System.out.println("fermenture...");
        server_socket.close();
        serveur.Envoie_autre_Server(5552);

    }
}

