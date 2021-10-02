
/**
 *
 * @author colegilbert
 */
public class Tester {

    //run from command line
    public static void main(String[] args) {
        //prep data for missing values (eliminate all missing data)
        Titanic.Passenger[] trainData = Titanic.trainingData(0.8);
        System.out.println("Train b4: " + trainData.length);
        trainData = TitanicStatistics.notMissing(trainData, Titanic.Attribute.PORT);
        trainData = TitanicStatistics.notMissing(trainData, Titanic.Attribute.AGE);
        trainData = TitanicStatistics.notMissing(trainData, Titanic.Attribute.CLASS);
        trainData = TitanicStatistics.notMissing(trainData, Titanic.Attribute.FARE);
        trainData = TitanicStatistics.notMissing(trainData, Titanic.Attribute.PARENTS);
        trainData = TitanicStatistics.notMissing(trainData, Titanic.Attribute.SEX);
        trainData = TitanicStatistics.notMissing(trainData, Titanic.Attribute.SIBLINGS);
        System.out.println("Train after: " + trainData.length);

        Titanic.Passenger[] testData = Titanic.testingData(0.8);
        System.out.println("Test b4: " + testData.length);
        testData = TitanicStatistics.notMissing(testData, Titanic.Attribute.PORT);
        testData = TitanicStatistics.notMissing(testData, Titanic.Attribute.AGE);
        testData = TitanicStatistics.notMissing(testData, Titanic.Attribute.CLASS);
        testData = TitanicStatistics.notMissing(testData, Titanic.Attribute.FARE);
        testData = TitanicStatistics.notMissing(testData, Titanic.Attribute.PARENTS);
        testData = TitanicStatistics.notMissing(testData, Titanic.Attribute.SEX);
        testData = TitanicStatistics.notMissing(testData, Titanic.Attribute.SIBLINGS);
        System.out.println("Test after: " + testData.length);

        //separating into the data for testing and the data for training
        Vector[] vTraining = new Vector[trainData.length];
        for (int i = 0; i < vTraining.length; i++) {
            vTraining[i] = new Vector(trainData[i]);
        }

        Regression.gradientDescent(vTraining);

        Vector[] vTesting = new Vector[testData.length];
        for (int i = 0; i < vTesting.length; i++) {
            vTesting[i] = new Vector(testData[i]);
        }

        int count = 0;

        //testing and calculating percent right
        for (int i = 0; i < vTesting.length; i++) {
            if ((Regression.hypothesis(vTesting[i])) > 0) {
                System.out.println("lived");
                //survived
                if (testData[i].survived()) { //if the correct answer is survived...
                    count++;

                }
            } else {
                System.out.println("died");
                //died
                if (!testData[i].survived()) {
                    count++;
                }
            }
        }

        double percentCorrect = ((double) (count) / testData.length) * 100;

        System.out.println("\nVertical Shift: " + Regression.weights[0]);
        System.out.println("Port: " + Regression.weights[1]);
        System.out.println("Class: " + Regression.weights[2]);
        System.out.println("Sex: " + Regression.weights[3]);
        System.out.println("Age: " + Regression.weights[4]);
        System.out.println("Siblings: " + Regression.weights[5]);
        System.out.println("Parents: " + Regression.weights[6]);
        System.out.println("Fare: " + Regression.weights[7]);

        System.out.println("Percent Correct: " + percentCorrect + "%");
    }
}
