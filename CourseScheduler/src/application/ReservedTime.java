package application;

public class ReservedTime {
    private final int DAY;
    /* each index indicates 5min from 00:00 to 24:00
        ex) fiveMinutes[13] indicates 01:10~01:14
    */
    public boolean[] fiveMinutes = new boolean[288];

    public ReservedTime(int day){
        this.DAY = day;
    }

    // need format HHMM for both string
    // use getStartTimeFromData() and getEndTimeFromData() at Controller
    public static int[] getStartEnd(String startTime, String endTime){
        int startIndex;
        int endIndex;
        String[] startHM = {startTime.substring(0,2), startTime.substring(2,4)};
        String[] endHM = {endTime.substring(0,2), endTime.substring(2,4)};

        //drop 0 at the front
        for (int i = 0; i < 2; i ++){
            if (startHM[i].charAt(0) == '0'){
                startHM[i] = "" + startHM[i].charAt(1);
            }
            if (endHM[i].charAt(0) == '0'){
                endHM[i] = "" + endHM[i].charAt(1);
            }
        }

        int startH = Integer.parseInt(startHM[0]);
        int startM = Integer.parseInt(startHM[1]);
        int endH = Integer.parseInt(endHM[0]);
        int endM = Integer.parseInt(endHM[1]);

        startIndex = (startH * 60 + startM) / 5 + 1;
        endIndex = (endH * 60 + endM) / 5 + 1;
        return new int[]{startIndex, endIndex};
    }

    public boolean conflictCheck(int[] startEnd){
        for (int i = startEnd[0]; i < startEnd[1] + 1; i++){
            if (fiveMinutes[i]){
                return true;
            }
        }
        return false;
    }

    public void reserve(int[] startEnd){
        for (int i = startEnd[0]; i < startEnd[1] + 1; i++){
            fiveMinutes[i] = true;
        }
    }

    public void drop(int[] startEnd){
        for (int i = startEnd[0]; i < startEnd[1] + 1; i++){
            fiveMinutes[i] = false;
        }
    }

    public int getDAY(){
        return DAY;
    }
}
