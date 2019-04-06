/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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

/**
 *
 * @author usuario
 */
public class Envio {
    
    public void EnviarCorreo(String remitente) throws MessagingException{
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
            String correoRemitente = "larcroco@gmail.com";
            String passwordRemitente = "Marcelo44";
            String correoReceptor = remitente;
            String asunto = "Mi primer correo en java";
            String mensaje = "Se ha unido a Zurita SAC! Felicidades!!";
            
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
    
}
