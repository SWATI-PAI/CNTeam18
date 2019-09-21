Question A9:
This is a program for A9 problem.

import com.sun.mail.imap.protocol.FLAGS;
import javax.mail.*;
import java.io.*;
import java.util.*;

public class Pop3Client {
    final static String Protocol = "pop3";
    static Store store;
    static Folder inbox;

    public static void main(String args[]) throws Exception {
        String host, username, password;
        int port;

        Scanner input = new Scanner(System.in);

        // Getting float input
        System.out.println("============ POP3 Client ================");
        System.out.println("POP3 Connection Details");

        // Read Hostname
        System.out.print("Enter Server Hostname: ");
        host = input.next();

        // Read Port
        System.out.print("Enter Server Port: ");
        port = input.nextInt();

        // Read Username
        System.out.print("Enter Server Username: ");
        username = input.next();

        // Read Password
        System.out.print("Enter Server Password: ");
        password = input.next();

        System.out.println("==========================================");

        // Read Mails
        Message[] mails = getMessages(host, port, username, password);

        if(mails.length > 0) {
            // Print Mails
            printMessages(mails);

            // Save First and Last Email
            saveMessage("firstemail.txt", mails[0]);
            saveMessage("lastemail.txt", mails[mails.length - 1]);

            // Delete First and Last Email
            mails[0].setFlag(FLAGS.Flag.DELETED, true);
            mails[mails.length - 1].setFlag(FLAGS.Flag.DELETED, true);
            System.out.println("Successfully deleted first and last emails");

            // Save Inbox and Close
            inbox.close(true);
            store.close();
        } else {
            System.out.println("No mails found!");
        }
    }

    private static Message[] getMessages(String host, int port, String username, String password) throws  Exception {
        Properties properties = System.getProperties();
        Session session = Session.getDefaultInstance(properties);
        store = session.getStore(Protocol);
        store.connect(host, port, username, password);
        inbox = store.getFolder("Inbox");
        inbox.open(Folder.READ_WRITE);
        return inbox.getMessages();
    }

    private static void printMessages(Message[] mails) throws  Exception {
        for (int i = 0; i < mails.length; i++) {
            System.out.println("Message " + (i + 1));
            System.out.println("From : " + mails[i].getFrom()[0]);
            System.out.println("Subject : " + mails[i].getSubject());
            System.out.println("Sent Date : " + mails[i].getSentDate());
            System.out.println();
        }
    }

    private static void saveMessage( String name, Message mail) throws  Exception {
        File file = new File(name);
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(mail.toString());
        bw.close();
    }
}

