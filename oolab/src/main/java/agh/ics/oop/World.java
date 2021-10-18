package agh.ics.oop;

public class World {
    static void run(String[] args){
        System.out.println("Zwierzak idzie do przodu");
        for (int i = 0; i < args.length; i++){
            System.out.print(args[i]);
            if(i < args.length-1){
                System.out.print(", ");
            }
            else{
                System.out.println();
            }
        }
    }
    public static void main(String[] args) {
        System.out.println("Start");
        run(new String[]{"f", "f"});
        System.out.println("Stop");
    }
}
