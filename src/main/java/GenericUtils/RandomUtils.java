package GenericUtils;

import java.io.*;
import java.util.*;

public class RandomUtils {
    static Random rand = new Random();
    public static int Randomint(int max, int min){
        return rand.nextInt(max - min + 1) + min;
    }
}
