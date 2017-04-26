import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by apersin on 10-Apr-17.
 */
public class Task5 {
    static BookFactory book;


    public static void main(String[] args) {

        Runnable customer = () -> {


            while (true) {
                try {
                    book.buyBook();
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };

        Runnable creator = () -> {

            while (true) {
                try {
                    book.createBook();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable accountant = () -> {

            while (true) {
                try {
                    Thread.currentThread().join(100);
                    System.out.println("Created books = " + book.getCreatedBookCount());
                    System.out.println("Sold books = " + book.getSoldBookCount());
                    System.out.println("Current books count = " + book.getBookCount());
                    System.out.println("Total price = " + book.getTotalPrice());
                    System.out.println("*************");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                try {
                    TimeUnit.SECONDS.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };


        book = new BookFactory();
        new Thread(customer).start();
        new Thread(creator).start();
        new Thread(accountant).start();
    }
}



class BookFactory {
    private int bookCount;
    private int price;
    private int soldBookCount;
    private int createdBookCount;

    public BookFactory() {
        this.bookCount = 0;
        this.price = 100;
        this.soldBookCount = 0;
        this.createdBookCount = 0;
    }

    public int getBookCount() {
        return bookCount;
    }

    public int getTotalPrice() {
        return this.soldBookCount * this.price;
    }

    public int getPrice() {
        return price;
    }

    public int getSoldBookCount() {
        return soldBookCount;
    }

    public int getCreatedBookCount() {
        return createdBookCount;
    }

    public synchronized void buyBook() throws InterruptedException {
        while (bookCount < 1) {
            wait();
        }

        soldBookCount++;
        bookCount--;
        notify();
    }

    public synchronized void createBook() throws InterruptedException {

        while (bookCount >= 8) {
            wait();
        }
        bookCount++;
        createdBookCount++;
        notify();

    }
}








