package GenericUtils;

import java.io.*;
import java.util.*;

public class RandomUtils {
    static Random randObj = new Random();
    public static int Randomint(int max, int min){
        return randObj.nextInt(max - min + 1) + min;
    }
    public static boolean RandomTrueFromPercent(int percent) {//used to have something run X percent of the time // X should never be more than 100
        return Randomint(100, 0) <= percent;
    }
}
