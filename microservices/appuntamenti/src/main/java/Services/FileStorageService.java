package Services;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;  // Google Drive File
import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.google.api.client.http.HttpResponse;

import org.springframework.stereotype.Service;

//@Service
public class FileStorageService {
	/*
    private static final String APPLICATION_NAME = "Gestionale Dentista";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
  //  private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_FILE);
    private static final String CREDENTIALS_FILE_PATH = "C:\\gestionaleDentista\\client_secret_505490338887-9khnvie8pg7cmj1koehpupph8gc0qjr4.apps.googleusercontent.com.json";


    private Drive getDriveService() throws Exception {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = getCredentials(HTTP_TRANSPORT);
        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws Exception {
    	InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("File delle credenziali non trovato: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets,Arrays.asList(DriveScopes.DRIVE_FILE))
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

     // Dopo aver ottenuto il Credential
        System.out.println("Access Token: " + credential.getAccessToken());
        System.out.println("Refresh Token: " + credential.getRefreshToken());
        //System.out.println("LIST FILES");
       // listFiles();  
     // Fai una richiesta per verificare gli scope effettivi
        HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
        HttpRequest request = requestFactory.buildGetRequest(
                new GenericUrl("https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=" + credential.getAccessToken())
        );
        HttpResponse response = request.execute();
        System.out.println("Token Info: " + response.parseAsString());
        // Log degli scope
        System.out.println("Scope attivi: " + credential.getAccessToken());

              
        return credential;

    }

    public String uploadToPublicServer(String filePath) throws Exception {
    	 try {
    		 // Usa java.io.File per il file locale
        java.io.File localFile = new java.io.File(filePath);

        // File metadata per Google Drive
        com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
        fileMetadata.setName(localFile.getName());

        // Contenuto del file
        FileContent mediaContent = new FileContent("application/pdf", localFile);

        // Inizializza il servizio Google Drive
        Drive driveService = getDriveService();

        // Carica il file su Google Drive
        com.google.api.services.drive.model.File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id, webViewLink, webContentLink")
                .execute();

        // Rendi il file pubblico
        driveService.permissions().create(uploadedFile.getId(),
                new com.google.api.services.drive.model.Permission()
                        .setRole("reader")
                        .setType("anyone"))
                .execute();

        return uploadedFile.getWebViewLink(); // URL pubblico del file
    	 } catch (IOException e) {
        System.err.println("Errore durante il caricamento o la condivisione del file: " + e.getMessage());
        throw e;
    }
}
    
    public void listFiles() throws Exception {
        Drive driveService = getDriveService(); // Usa il metodo esistente per ottenere il servizio
        Drive.Files.List request = driveService.files().list();
        request.setFields("nextPageToken, files(id, name)");
        com.google.api.services.drive.model.FileList result = request.execute();
        for (com.google.api.services.drive.model.File file : result.getFiles()) {
            System.out.printf("Found file: %s (%s)\n", file.getName(), file.getId());
        }
    }
*/
}
