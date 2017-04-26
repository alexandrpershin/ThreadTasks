import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Task3 {
    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();

        for (int i = 50; i > 0; i--){
            final int num = i;
            if(i==49)
                System.out.println("**************");
            service.submit(()->{
                try {
                    System.out.println("Start thread " + num);
                    System.out.println("Hello from thread " + num );
                    Thread.sleep(1000);
                    System.out.println("Finish thread " + num);
                    System.out.println("**************");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        service.shutdown();
    }
}
