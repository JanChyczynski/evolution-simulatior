package agh.ics.oop;

public class World {
    static void run(String[] args){
        for(String arg : args){
            String message = "Zwierzak idzie "+ switch (arg) {
                case "f" -> "idzie do przodu";
                case "b" -> "idzie do tyłu";
                case "r" -> "skręca w prawo";
                case "l" -> "skręca w lewo";
                default -> "";
            };
            System.out.println(message);

//            System.out.print();

        }
    }
    public static void main(String[] args) {
        System.out.println("Start");
        run(new String[]{"f", "r", "l", "b"});
        System.out.println("Stop");
    }
}
