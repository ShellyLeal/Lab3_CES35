package cliente;

import java.io.*;
import java.net.*;
public class GossipClient
{
  public static void main(String[] args) throws Exception
  {
     Socket sock = new Socket("192.168.1.151", 6666);
                               // reading from keyboard (keyRead object)
     BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
                              // sending to client (pwrite object)
     OutputStream ostream = sock.getOutputStream(); 
     PrintWriter pwrite = new PrintWriter(ostream, true);

                         // receiving from server ( receiveRead  object)
     InputStream istream = sock.getInputStream();
     BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

     System.out.println("Eu quero jogar um jogo, \n"
     		+ "Digite JOGO para começar ou QUIT para sair\n"
     		+ "Lembrando que ó pode errar 5 vezes");

     String receiveMessage = " ", sendMessage;               
     while(! receiveMessage.equals("Fim da conexão"))
     {
        sendMessage = keyRead.readLine();  // keyboard reading
        pwrite.println(sendMessage);       // sending to server
        pwrite.flush();                    // flush the data
        if((receiveMessage = receiveRead.readLine()) != null || !(receiveMessage = receiveRead.readLine()).equals(" ")) //receive from server
        {
        System.out.println(receiveMessage);
        }
      } 
     sock.close();
     keyRead.close();
     ostream.close();
     istream.close();
     pwrite.close();
     
    }                    
}            
