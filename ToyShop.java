package finalWorkToys;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
    private static Queue<Toy> toyQueue = new PriorityQueue<>((t1, t2) -> t2.getFrequency() - t1.getFrequency());
    private static final String RESULT_FILE = "results.txt";

    public static void addToy(int id, String name, int frequency) {
        Toy toy = new Toy(id, name, frequency);
        toyQueue.add(toy);
    }

    public static Toy getToy() {
        Toy toy = toyQueue.poll();
        if (toy != null) {
            toyQueue.add(toy);
        }
        return toy;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESULT_FILE))) {
            for (int i = 0; i < 10; i++) {
                Toy toy = getToy();
                if (toy != null) {
                    String result = "Получена игрушка: " + toy.getName() + ", id: " + toy.getId();
                    writer.write(result);
                    writer.newLine();
                } else {
                    String result = "Нет доступных игрушек";
                    writer.write(result);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Результаты розыгрыша записаны в файл " + RESULT_FILE);
    }
}
