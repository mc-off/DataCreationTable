package generators;

import java.util.Random;

public class RandomNumberGenerator {
    public RandomNumberGenerator(){}
    public int generate(int minNumber, int maxNumber)
    {
        int difference = maxNumber - minNumber;
        Random random = new Random();
        int randomNumber = random.nextInt(difference + 1);
        randomNumber += minNumber;
        return (randomNumber);
    }
}
