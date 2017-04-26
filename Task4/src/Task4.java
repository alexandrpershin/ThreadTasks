import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by apersin on 10-Apr-17.
 */
public class Task4 {
    public static void main(String[] args) {
        BookFactory book= new BookFactory();
        Thread customer = new Thread(new Customer(book));
        Thread creator = new Thread(new Creator(book));
        Thread accountant = new Thread(new Accountant(book));

        accountant.start();
        customer.start();
        creator.start();


    }
}

class BookFactory{
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

    public int getTotalPrice(){
        return  this.soldBookCount*this.price;
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

    public synchronized void  buyBook() throws InterruptedException {
        while (bookCount <1) {
            wait();
        }

        soldBookCount++;
        bookCount--;
//        System.out.println("Buy book,  books count = " + getBookCount());
        notify();
    }

    public   synchronized void  createBook() throws InterruptedException {

        while (bookCount >= 8) {
            wait();
        }
        bookCount++;
        createdBookCount++;
//        System.out.println("Create book,  books count = " + getBookCount());
        notify();

    }
}









class  Customer implements Runnable{  //Class creator, here we'll buy new book
    BookFactory book;
    Customer(BookFactory book){
        this.book=book;
    }

    @Override
    public void run() {
        while (true) {
            try {
                book.buyBook();
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class  Creator implements Runnable{  //Class creator, here we'll add new book
    BookFactory book;
    Creator(BookFactory book){
        this.book=book;
    }

    @Override
    public void run() {
        while (true) {
            try {
                book.createBook();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class  Accountant implements Runnable{  //Class creator, here we'll print some information about our buisness
    BookFactory book;
    Accountant(BookFactory book){
        this.book=book;
    }

    @Override
    public void run() {
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
    }
}
