import java.util.Random;

class RandomNumberGenerator {
    RandomNumberGenerator(){}
    int generate(int minNumber, int maxNumber)
    {
        int difference = maxNumber - minNumber;
        Random random = new Random();
        int randomNumber = random.nextInt(difference + 1);
        randomNumber += minNumber;
        return (randomNumber);
    }
}
