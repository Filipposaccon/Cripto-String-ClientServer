import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class StringClient {
    static char[] alfabeto = new char[26]; //Array che contiene le lettere dell'alfabeto

    public static String cripta(String messaggio, String chiave) {
        int c = 0;//indice alfabeto
        int x = 0;//indice per scorrere la chiave
        int lettparola;
        int lettchiave;
        String cript = "";//messaggio criptato
        for (int i = 0; i < messaggio.length(); i++) {//cerco la lettera nell'alfabeto (messaggio utente)
            while (messaggio.charAt(i) != alfabeto[c]) {
                c++;
            }
            lettparola = c;//salvo la posizione
            c = 0;
            while (chiave.charAt(x) != alfabeto[c]) { //cerco la lettera nell'alfabeto (Chiave)
                c++;
            }
            lettchiave = c;//salvo posizione
            c = 0;
            x++;
            if (lettparola + lettchiave > alfabeto.length - 1) {// se l'indice ottenuto è superiore alla lunghezza dell'alfabeto sottraggo la dimensione dell'alfabeto
                int totale = (lettparola + lettchiave) - (alfabeto.length);
                cript = cript + alfabeto[totale];
            } else {
                int totale = (lettparola + lettchiave);
                cript = cript + alfabeto[totale];
            }
            if (x == chiave.length()) {//controllo per evitare di sfondare la lunghezza della chiave
                x = 0;
            }
        }
        return cript;
    }

    public static void caricaAlfabeto() {//metodo per caricare l'array con le lettere dell'alfabeto
        alfabeto[0] = 'a';
        alfabeto[1] = 'b';
        alfabeto[2] = 'c';
        alfabeto[3] = 'd';
        alfabeto[4] = 'e';
        alfabeto[5] = 'f';
        alfabeto[6] = 'g';
        alfabeto[7] = 'h';
        alfabeto[8] = 'i';
        alfabeto[9] = 'j';
        alfabeto[10] = 'k';
        alfabeto[11] = 'l';
        alfabeto[12] = 'm';
        alfabeto[13] = 'n';
        alfabeto[14] = 'o';
        alfabeto[15] = 'p';
        alfabeto[16] = 'q';
        alfabeto[17] = 'r';
        alfabeto[18] = 's';
        alfabeto[19] = 't';
        alfabeto[20] = 'u';
        alfabeto[21] = 'v';
        alfabeto[22] = 'w';
        alfabeto[23] = 'x';
        alfabeto[24] = 'y';
        alfabeto[25] = 'z';
    }

    public static String generaChiave(String chiave) {//metodo per generare una chiave random per ogni messaggio
        Random random = new Random();
        int lunghezza = random.nextInt(20);
        while (lunghezza == 0) {//per evitare che la chiave abbia lunghezza 0
            lunghezza = random.nextInt(20);
        }
        int rand;
        for (int i = 0; i < lunghezza; i++) {
            rand = random.nextInt(25);
            chiave = chiave + alfabeto[rand];
        }
        return chiave;
    }

    public static void main(String[] args) throws IOException {
        caricaAlfabeto();//carico l'alfabeto solo la prima volta
        while (true) {

            //Genero la chiave univoca per questo messaggio
            String chiave = "";
            chiave = generaChiave(chiave);
            System.out.println();
            //Instanzio il socket per connettermi al server
            Socket s = new Socket("localhost", 8080);
            //Creo un oggetto scanner per prendere il messaggio inserito dall''utente
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            System.out.print("Inserisci messaggio: ");
            String msg = myObj.nextLine();
            //Cripto il messaggio inserito dall'utente
            String messaggiocrip = cripta(msg.toLowerCase().replace(" ", ""), chiave);
            // invia dati al server
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println(messaggiocrip + "/" + chiave);
            // riceve una risposta dal server
            InputStreamReader isr = new InputStreamReader(s.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            System.out.println(in.readLine());
            s.close();
        }

    }
}
