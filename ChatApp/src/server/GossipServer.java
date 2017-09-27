package server;

import java.io.*;
import java.net.*;



public class GossipServer{
	
	private static String palavraDaForca;
	private static int numTraco = 0;
	
  public static void main(String[] args) throws Exception{
	  
      ServerSocket sersock = new ServerSocket(6666);
      System.out.println("Server  ready for chatting");
      Socket sock = sersock.accept( );                          
                              // reading from keyboard (keyRead object)
      BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
	                      // sending to client (pwrite object)
      OutputStream ostream = sock.getOutputStream(); 
      PrintWriter pwrite = new PrintWriter(ostream, true);
      
                              // receiving from server ( receiveRead  object)
      InputStream istream = sock.getInputStream();
      BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
      String receiveMessage, sendMessage;               
      String palavra = "";
      String aux = "";
      boolean flag = false;
      boolean comecou = false;
      int tenta = 0;
      
      while(true) {
    	  
    	  if((receiveMessage = receiveRead.readLine()) != null)  {
    		  
           System.out.println(receiveMessage); 
           if(receiveMessage.equals("QUIT")) {   
        	   sendMessage = "Fim da conexão";
        	   pwrite.println(sendMessage);
        	   keyRead.close();
        	   sersock.close();
        	   sock.close();
        	   break;
           }
           
           if(comecou == true){ // comecou o jogo
        	   if(receiveMessage.length()!=1) // se nao manda letra
        	   {
        		   pwrite.println("Uma letra so, sua poc poc ");
        		   pwrite.flush();
        		   continue;
        	   }
        	   else
        		   {
        		     
        		     boolean acertou = false;
        		     for(int i=0;i<palavra.length();i++)
        		      if(Character.toString(palavra.charAt(i)).equals(receiveMessage))
        		    	     acertou = true;
        		     if(acertou){
        		    	 atualizaPalavraForca(receiveMessage, palavra);
        		    	 if (numTraco == 0){
        		    		 pwrite.println("Parabens, você acertou a palavra " + palavra 
        		    				 + ". Quer jogar de novo? JOGO: sim; QUIT: não");
        		    		 comecou = false;
        		    		 palavra = "";
        		    		 tenta = 0;
        		    		 aux = "";
        		    		 numTraco = 0;
        		    	 }
        		    	 else{
        		    		 pwrite.println(palavraDaForca + " Digite próxima letra");
        		    	 }
        		     }
        		     else {
        		    	 
        		    	 if (tenta == 4){
        		    		 pwrite.println("Fim das tentativas, você perdeu. A paravra era: " 
        		    				 		+  palavra + ". Jogar noamente? JOGO: sim, QUIT: não");
        		    		 comecou = false;
        		    		 palavra = "";
        		    		 tenta = 0;
        		    		 aux = "";
        		    		 numTraco = 0;
        		    	 }
        		    	 else{
        		    		 tenta = tenta+1;
        		    		 String msg = "Errou letra:" + receiveMessage + ". Tentativa "+ tenta+ ". " + palavraDaForca;
        		    		 pwrite.println(msg + " .Digite próxima palavra");
        		    		 
        		    	 }
        		     }
        		     
        		     pwrite.flush();
        		     continue;
        		   }
           }
           
          if(receiveMessage.equals("JOGO")&&palavra.equals("")) //pede pra comecar o jogo
          {   if (palavra.equals(""))
              	{flag = true; comecou = true;}
              
          }
        }  
  
        sendMessage = keyRead.readLine(); 
        
        if(flag == true) //guarda a palavra secreta
        {   palavra = sendMessage;
        	for(int i = 0;i<palavra.length();i++){
        		aux = aux + "- ";
        		numTraco ++;
        	}
        	palavraDaForca = aux;
            pwrite.println(palavraDaForca + " .Digite uma letra");             
            pwrite.flush();
        }
        else
        {
        pwrite.println(sendMessage);             
        pwrite.flush();
        }
        flag = false;
      
      
      }
      
    }

  private static void atualizaPalavraForca(String receiveMessage, String palavra) {
	StringBuilder aux = new StringBuilder (palavraDaForca);
	for(int i = 0; i < palavra.length(); i++){
		if (Character.toString(palavra.charAt(i)).equals(receiveMessage)){
			aux.setCharAt(2*i, receiveMessage.charAt(0));
			numTraco --;
		}
	}
	palavraDaForca = aux.toString();
	
  }                    
}    