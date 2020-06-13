import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class StringServer {
    static char[] alfabeto = new char[26];//Array che contiene le lettere dell'alfabeto

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

    public static String decript(String messaggio, String chiave) {//Questa funzione serve a decriptare i messaggi
        int c = 0;//indice alfabeto
        int x = 0;
        int lettparola;
        int lettchiave;
        String decript = "";
        for (int i = 0; i < messaggio.length(); i++) {
            while (messaggio.charAt(i) != alfabeto[c]) {
                c++;
            }
            lettparola = c;
            c = 0;
            while (chiave.charAt(x) != alfabeto[c]) {
                c++;
            }
            lettchiave = c;
            c = 0;
            x++;
            if (lettparola - lettchiave < 0) {//In questo al posto di sommare gli indici come nel client, vado a sottrarre
                int totale = (lettparola - lettchiave) + (alfabeto.length);
                decript = decript + alfabeto[totale];
            } else {
                int totale = (lettparola - lettchiave);
                decript = decript + alfabeto[totale];
            }
            if (x == chiave.length()) {
                x = 0;
            }
        }
        return decript;
    }

    public static void main(String[] args) throws IOException {
        // instanzia un server socket per la connessione
        ServerSocket ss = new ServerSocket(8080);
        caricaAlfabeto();//carico alfabeto

        while (true) {
            // riceve una connessione
            Socket s = ss.accept();
            // riceve del testo
            InputStreamReader isr = new InputStreamReader(s.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String msg = in.readLine();
            System.out.println("Il server riceve: " + msg);
            //eseguo lo split per ottenere la chiave e il messaggio (alla prima posizione si trova il messaggio e poi la chiave)
            String[] elem = msg.split("/");
            //decripto il messaggio
            String ris = decript(elem[0], elem[1]);
            System.out.println(ris);
            // invia del testo
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println("Frase in chiaro: " + ris);

        }

    }
}
