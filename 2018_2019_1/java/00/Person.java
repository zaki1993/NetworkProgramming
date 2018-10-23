public class Person extends Thread {

    private String name;
    private int sleepTime;
    
    public Person(String name, int sleepTime) {
        this.name = name;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("My name is " + this.name);
    }
}
