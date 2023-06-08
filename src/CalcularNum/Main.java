package CalcularNum;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class MagicNumberCalculator implements Callable<Integer> {
    private List<Integer> numbers;

    public MagicNumberCalculator(List<Integer> numbers) {
        this.numbers = numbers;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(2);
        numbers.add(4);
        numbers.add(6);
        numbers.add(8);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Future<Integer>> results = new ArrayList<>();

        int batchSize = numbers.size() / 2;
        for (int i = 0; i < numbers.size(); i += batchSize) {
            List<Integer> batch = numbers.subList(i, Math.min(i + batchSize, numbers.size()));
            Callable<Integer> calculator = new MagicNumberCalculator(batch);
            Future<Integer> result = executorService.submit(calculator);
            results.add(result);
        }

        int totalSum = 0;
        for (Future<Integer> result : results) {
            try {
                int sum = result.get();
                totalSum += sum;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();

        System.out.println("Total sum: " + totalSum);
    }
}

