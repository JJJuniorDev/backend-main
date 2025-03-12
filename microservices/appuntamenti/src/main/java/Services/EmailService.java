package Services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage.RecipientType;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

//@Service
public class EmailService {

  /*  private static final String APPLICATION_NAME = "gestionale-dentista";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private Gmail gmailService;

    public EmailService() throws Exception {
        // Autorizzazione con OAuth2
        Credential credential = authorize(); 
        
        // Costruzione del servizio Gmail
        gmailService = new Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    // Funzione di autenticazione OAuth2
    private Credential authorize() throws Exception {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JSON_FACTORY, 
new InputStreamReader(new FileInputStream("C:\\gestionaleDentista\\client_secret_505490338887-9khnvie8pg7cmj1koehpupph8gc0qjr4.apps.googleusercontent.com.json"))
        	    );
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets,
                Arrays.asList(
                        "https://www.googleapis.com/auth/gmail.send", // per Gmail
                        "https://www.googleapis.com/auth/drive.file" // per Google Drive
                    ))
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    // Metodo per inviare un'email con allegato
    public void sendEmail(String toEmail, String subject, String body, String attachmentPath) throws MessagingException, IOException {
        try {
			MimeMessage emailContent = createEmailWithAttachment(toEmail, subject, body, attachmentPath);
			sendMessage(gmailService, "me", emailContent);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // Crea un'email con allegato
    private MimeMessage createEmailWithAttachment(String to, String subject, String bodyText, String attachmentPath) throws MessagingException {
    	Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		MimeMessage email = new MimeMessage(session);
    	try {
			
			email.setFrom(new InternetAddress("me"));
			email.addRecipient(RecipientType.TO, new InternetAddress(to));
			email.setSubject(subject);

			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setText(bodyText);

			MimeBodyPart attachmentPart = new MimeBodyPart();
			try {
			    attachmentPart.attachFile(attachmentPath);
			} catch (IOException e) {
			    e.printStackTrace();
			} catch (MessagingException e) {
			    e.printStackTrace();
			}

			MimeMultipart multipart = new MimeMultipart();
			multipart.addBodyPart(textPart);
			multipart.addBodyPart(attachmentPart);

			email.setContent(multipart);

			
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        catch (Exception e) {
        	e.printStackTrace();
        }
        return email;
    }

    // Invia il messaggio tramite l'API Gmail
    private void sendMessage(Gmail service, String userId, MimeMessage emailContent) throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = com.google.api.client.util.Base64.encodeBase64URLSafeString(rawMessageBytes);

        Message message = new Message();
        message.setRaw(encodedEmail);
        service.users().messages().send(userId, message).execute();
    }*/
}
