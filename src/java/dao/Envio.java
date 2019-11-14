
package dao;

import entidad.Ayudante;
import entidad.Conductor;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class Envio {
    
/*
public static void main(String[] args) throws Exception {
        Envio envio = new Envio();
        //String receptor = "1410782@utp.edu.pe";
        String receptor = "diego1996ur@gmail.com";
        
        envio.EnviarCorreo(receptor);
}
*/
    
    public void EnviarCorreo(String receptor, String asunto, String mensaje) throws MessagingException{
        try {
            Properties props = new Properties();
            //Propiedades para la conexión a nuestra cuenta
            //nombre de la propiedad que vamos a asignar , cuentas a utilizar
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            //se indica que se va a utilizar tls
            props.setProperty("mail.smtp.starttls.enable", "true");
            //puerto
            props.setProperty("mail.smtp.port", "587");
            //se va a autentificar directamente al servidor de gmail
            props.setProperty("mail.smtp.auth", "true");
            
            //Crear una sesion para enviar las propiedades
            Session session = Session.getDefaultInstance(props);
            
            //declaracion de variables string con nuestro datos
            String correoRemitente = "diego1996ur@gmail.com";
            String passwordRemitente = "durbayd21";
            String correoReceptor = receptor;
        //    String asunto = "Registro de cuenta";
          //  String mensaje = "Se ha unido a Encomiendas SAC! Felicidades!!";
            
            //Se envia la sesion
            MimeMessage message = new MimeMessage(session);
            //Se indica quien va a enviar el mensaje
            message.setFrom(new InternetAddress(correoRemitente));
            //Opciones de envio
            //Para el destinatario principal
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoReceptor));
            //Para enviar una copia
            //message.addRecipient(Message.RecipientType.CC, new InternetAddress(correoReceptor));
            //Enviar al destinatario una copia pero los demás no podrán verlo
            //message.addRecipient(Message.RecipientType.BCC, new InternetAddress(correoReceptor));
                        
            message.setSubject(asunto);
            message.setText(mensaje);
            
            //se elige el tipo de transporte
            Transport t = session.getTransport("smtp");
            //se envia el correo remitente al transporte
            t.connect(correoRemitente, passwordRemitente);
            //se envia todo el mensaje a transport y se indica que es a la persona principal
            t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            t.close();
            
            JOptionPane.showMessageDialog(null, "correo enviado");
            
        } catch (AddressException ex) {
            Logger.getLogger(Envio.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
    
    public void EnviarCodigo(int idcliente, int idencomienda, int idConductor, int idAyudante, int vehiculo, String placaVehiculo, String receptor, double precio) throws MessagingException, Exception{
                
       ConductorDAO conductorDAO = new ConductorDAO();        
       AyudanteDAO ayudanteDAO = new AyudanteDAO();        
                
        try {
            
           Conductor conductor = conductorDAO.BuscarPorId(idConductor);
           String mensaje_extra = "";
           if(idAyudante > 0){
               Ayudante ayudante = ayudanteDAO.BuscarPorId(idAyudante);
               mensaje_extra = "Identificador de Ayudante: " + ayudante.getId()+"\n"
                    + "Nombre de Ayudante: " + ayudante.getNom() +" "+ ayudante.getApe() +"\n"
                       ;
           }
            
            Properties props = new Properties();

            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");
            
            Session session = Session.getDefaultInstance(props);
            
            //declaracion de variables string con nuestro datos
            String correoRemitente = "diego1996ur@gmail.com";
            String passwordRemitente = "durbayd21";
            String correoReceptor = receptor;
            String asunto = "Registro de Encomienda";
            String mensaje = "Hola, "+receptor+"\n"
                    + "Número de cliente: " + idcliente +"\n"
                    + "Identificador de encomienda: " + idencomienda +"\n"
                    + "Precio S/. : " + precio+"\n"
                    + "Matricula de vehiculo: " + placaVehiculo +"\n"
                    + "Identificador de Conductor: " + conductor.getId()+"\n"                    
                    + "Nombre de conductor: " + conductor.getNom() +" "+ conductor.getApe() +"\n"+
                    mensaje_extra
                //    + "Identificador de Ayudante: " + ayudante.getId()+"\n"
                 //   + "Nombre de Ayudante: " + ayudante.getNom() +" "+ ayudante.getApe() +"\n"
                    ;                       
 
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(correoRemitente));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoReceptor));
            //Para enviar una copia
            message.addRecipient(Message.RecipientType.CC, new InternetAddress(correoRemitente));

            message.setSubject(asunto);
            message.setText(mensaje);
            
            //se elige el tipo de transporte
            Transport t = session.getTransport("smtp");

            t.connect(correoRemitente, passwordRemitente);
            t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            t.close();
            
            JOptionPane.showMessageDialog(null, "correo enviado");
            
        } catch (AddressException ex) {
            Logger.getLogger(Envio.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }    
    
    
    public void EdicionDeEncomienda(int idencomienda, String receptor) throws MessagingException{
        try {
            Properties props = new Properties();

            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");
            
            Session session = Session.getDefaultInstance(props);
            
            //declaracion de variables string con nuestro datos
            String correoRemitente = "diego1996ur@gmail.com";
            String passwordRemitente = "durbayd21";
            String correoReceptor = receptor;
            String asunto = "Edición de envío ";
            String mensaje = "Hola, "+receptor+"\n"
                    +"Se he editado su encomienda con el id :"+idencomienda+"\n";
            
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(correoRemitente));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoReceptor));
            //Para enviar una copia
            message.addRecipient(Message.RecipientType.CC, new InternetAddress(correoRemitente));

            message.setSubject(asunto);
            message.setText(mensaje);
            
            //se elige el tipo de transporte
            Transport t = session.getTransport("smtp");

            t.connect(correoRemitente, passwordRemitente);
            t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            t.close();
            
            JOptionPane.showMessageDialog(null, "correo enviado");
            
        } catch (AddressException ex) {
            Logger.getLogger(Envio.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }      
    /*
   public void RecuperarContraseña(String email, String contra) throws MessagingException{
        try {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");
           
            Session session = Session.getDefaultInstance(props);
            
            String correoRemitente = "diego1996ur@gmail.com";
            String passwordRemitente = "durbayd21";
            String correoReceptor = email;
            String asunto = "Recuperación de contraseña";
            String mensaje = "Hola, "+email+"\n"
                    + "Su contraseña es: " + contra;
                        
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correoRemitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoReceptor));
                        
            message.setSubject(asunto);
            message.setText(mensaje);
            
            Transport t = session.getTransport("smtp");
            t.connect(correoRemitente, passwordRemitente);
            t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            t.close();
            
            JOptionPane.showMessageDialog(null, "correo enviado");
            
        } catch (AddressException ex) {
            Logger.getLogger(Envio.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }    
*/    
}

