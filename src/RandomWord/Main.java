package RandomWord;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

class WordPrinter implements Runnable {
    private List<String> words;
    private Random random;

    public WordPrinter(List<String> words) {
        this.words = words;
        this.random = new Random();
    }

    @Override
    public void run() {
        for (int i = 0; i < 12; i++) {  // Ejecutar durante 1 minuto (12 iteraciones de 5 segundos)
            String randomWord = getRandomWord();
            System.out.println(randomWord);

            try {
                Thread.sleep(5000);  // Esperar 5 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String getRandomWord() {
        int index = random.nextInt(words.size());
        return words.get(index);
    }
}

public class Main {
    public static void main(String[] args) {
        List<String> words = List.of("Hola", "Adiós", "Hola mundo", "Programación", "Java");

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Runnable wordPrinter = new WordPrinter(words);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(wordPrinter, 0, 5, TimeUnit.SECONDS);

        try {
            Thread.sleep(60000);  // Esperar 1 minuto
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scheduledExecutorService.shutdown();
        executorService.shutdown();
    }
}

