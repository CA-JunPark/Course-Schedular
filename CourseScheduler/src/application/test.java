package application;

public class test {
    public static void main(String[] args){
        System.out.println(getTimeDifference("1815", "2100"));
    }
    public static int getTimeDifference(String start, String end){
        int H = Integer.parseInt(end.substring(0,2))-Integer.parseInt(start.substring(0,2));
        int M = Integer.parseInt(end.substring(2))-Integer.parseInt(start.substring(2));
        return H*60+M;
    }
}
