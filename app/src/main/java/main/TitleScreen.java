package main;

import util.ClearScreen;
import util.Input;

public class TitleScreen {
    Input scan = Input.getInstance();

    public TitleScreen() {
        show();
    }

    private void show() {
        System.out.println("1. Start game");
        System.out.println("2. Help");
        System.out.println("3. Exit");

        getInput();
    }

    private void getInput() {
        System.out.print("\nENTER COMMAND: ");
        String input = scan.nextLine();

        if (input.equals("1")) {
            // pass, quit from method
        }
        else if (input.equals("2")) {
            // TODO: HELP!
            printHelp();
            scan.enterUntukLanjut();
            ClearScreen.clear();
            show();
        }
        else if (input.equals("3")) {
            System.exit(0);
        }
        else {
            ClearScreen.clear();
            show();
        }
    }

    private void printHelp() {
        System.out.println(
                "\n[Game Description]\n" +
                "Sim-Plicity adalah game simulasi yang berfokus pada kehidupan orang virtual yang disebut sebagai Sims.\n" +
                "Pemain dapat mengontrol aktivitas keseharian Sims, seperti menyuruhnya tidur, makan, atau buang air besar.\n" +
                "\n[How to play?]\n" +
                "- Setiap Sim baru akan diberikan sebuah rumah dengan 1 ruangan berisi beberapa furniture yang dapat digunakan untuk melakukan berbagai aksi.\n" +
                "- Sim memiliki status kebutuhan (Kekenyangan, Mood, dan Kesehatan) yang harus dipenuhi agar Sim tidak mati.\n" +
                "- Sim dapat melakukan berbagai aksi untuk memenuhi kebutuhan tersebut.\n" +
                "- Sim dapat melakukan upgrade pada rumahnya.\n" +
                "- Sim memiliki pekerjaan yang dapat menghasilkan uang.\n" +
                "- Uang dapat digunakan untuk membeli item-item yang dibutuhkan dan melakukan upgrade rumah.\n" +
                "- Tujuan dari permainan ini adalah menjaga kesejahteraan Sim yang telah dibuat agar tidak depresi dan mati\n" +
                "- Jika Sim mati, permainan akan berakhir.\n" +
                "\n[How to start?]\n" +
                "1. Pilih opsi \"Start\" pada menu untuk memulai Permainan.\n" +
                "2. Masukkan nama Sim yang ingin dimainkan.\n" +
                "3. Permainan dimulai!\n"
        );
    }
}
