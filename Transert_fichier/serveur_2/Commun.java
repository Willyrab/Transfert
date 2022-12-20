package infra;
import java.io.*;
import java.net.*;
import java.nio.*;

public class Commun {

    public  Socket  Creation_socket(int port) throws IOException {
    
        Socket socket = new Socket("localhost", port);
        return socket;
    }
    public OutputStream GetOutputStream(Socket socket) throws IOException {
        //sortie
        return socket.getOutputStream();
    }
  /* DataInputStream permet de lire des type primitifs java.
  argument ilay tiny ho vakiana */
    public DataOutputStream CreateOutputStream(Socket socket) throws IOException {
        OutputStream output=this.GetOutputStream(socket);
        return new DataOutputStream(output);
    }



}

