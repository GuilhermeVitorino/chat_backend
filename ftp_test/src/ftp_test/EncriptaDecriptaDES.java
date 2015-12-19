package ftp_test;
  import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
   


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
   
  public class EncriptaDecriptaDES
  {
   
   
         public static void main(String[] argv) throws UnsupportedEncodingException {
   
               try{
            	   String chave = "bbbbbbbb";
                   SecretKey chaveDES = new SecretKeySpec(chave.getBytes("UTF-8"),"DES");
                   
                   String chave2 = "bbbbbbbb";
                   SecretKey chaveDES2 = new SecretKeySpec(chave2.getBytes("UTF-8"),"DES");
   
                   Cipher cifraDES;
                   Cipher cifraDES2;
   
                   // Cria a cifra 
                   cifraDES = Cipher.getInstance("DES/ECB/PKCS5Padding");
                   cifraDES2 = Cipher.getInstance("DES/ECB/PKCS5Padding");
   
                   // Inicializa a cifra para o processo de encriptação
                   cifraDES.init(Cipher.ENCRYPT_MODE, chaveDES);
   
                   // Texto puro
                   byte[] textoPuro = "Exemplo de texto puro".getBytes();
   
                   System.out.println("Texto [Formato de Byte] : " + textoPuro);
                   System.out.println("Texto Puro : " + new String(textoPuro));
   
                   // Texto encriptado
                   byte[] textoEncriptado = cifraDES.doFinal(textoPuro);
   
                   System.out.println("Texto Encriptado : " + textoEncriptado);
   
                   // Inicializa a cifra também para o processo de decriptação
                   cifraDES2.init(Cipher.DECRYPT_MODE, chaveDES2);
   
                   // Decriptografa o texto
                   byte[] textoDecriptografado = cifraDES2.doFinal(textoEncriptado);
   
                   System.out.println("Texto Decriptografado : " + new String(textoDecriptografado));
   
               }catch(NoSuchAlgorithmException e){
                      e.printStackTrace();
               }catch(NoSuchPaddingException e){
                      e.printStackTrace();
               }catch(InvalidKeyException e){
                      e.printStackTrace();
               }catch(IllegalBlockSizeException e){
                      e.printStackTrace();
               }catch(BadPaddingException e){
                      e.printStackTrace();
               } 
   
         }
         
  }

