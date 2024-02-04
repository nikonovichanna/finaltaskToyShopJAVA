package finalWorkToys;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

class Toy {
    private int id;
    private String name;
    private int frequency;

    public Toy(int id, String name, int frequency) {
        this.id = id;
        this.name = name;
        this.frequency = frequency;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getFrequency() {
        return frequency;
    }
}

public class ToyShop {
    private static PriorityQueue<Toy> toyQueue = new PriorityQueue<>(Comparator.comparingInt(Toy::getFrequency));
    private static final String RESULT_FILE = "results.txt";

    public static void addToy(int id, String name, int frequency) {
        Toy toy = new Toy(id, name, frequency);
        toyQueue.add(toy);
    }   

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean addMoreToys = true;
            while (addMoreToys) {
                System.out.print("Выберите действие (1 - добавить игрушку, 2 - завершить): ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 
                switch (choice) {
                    case 1:
                        System.out.print("Введите id игрушки: ");
                        int id = scanner.nextInt();
                        scanner.nextLine(); 
                        System.out.print("Введите название игрушки: ");
                        String name = scanner.nextLine();
                        System.out.print("Введите частоту выпадения игрушки: ");
                        int frequency = scanner.nextInt();
                        scanner.nextLine(); 
                        addToy(id, name, frequency);
                        break;
                    case 2:
                        addMoreToys = false;
                        break;
                    default:
                        System.out.println("Некорректный выбор. Попробуйте снова.");
                        break;
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESULT_FILE))) {
            int count = 0; 
            while (!toyQueue.isEmpty() && count < 10) {
                Toy toy = toyQueue.poll();
                for (int i = 0; i < toy.getFrequency() && count < 10; i++) {
                    String result = "Получена игрушка: " + toy.getName() + ", id: " + toy.getId();
                    writer.write(result);
                    writer.newLine();
                    count++; 
                }
            }
            while (count < 10) {
                writer.write("Очередь пуста");
                writer.newLine();
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Результаты розыгрыша записаны в файл " + RESULT_FILE);
    }
}
