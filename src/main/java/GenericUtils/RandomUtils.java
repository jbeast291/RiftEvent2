package GenericUtils;

public class RandomUtils {
    public static int Randomint(int max, int min){
        int range = max - min + 1;
        int random = (int)(Math.random() * range) + min;
        return random;
    }
}
