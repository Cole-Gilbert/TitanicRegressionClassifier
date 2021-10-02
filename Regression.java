
/**
 *
 * @author colegilbert
 */
public class Regression {

    private static final double alpha = 0.1;
    public static double[] weights = {1, 1, 1, 1, 1, 1, 1, 1};

    public static double hypothesis(Vector v) {
        Vector w = new Vector(weights);
        double value = w.dotProduct(v);
        if (value > 0.0) {
            return 1.0;
        } else {
            return 0.0;
        }
    }

    public static void weightUpdate(Vector v) {
        double[] temp = Regression.weights.clone();
        for (int i = 0; i < Regression.weights.length; i++) {
            temp[i] = Regression.weights[i] + (alpha * v.get(i) * Regression.loss(v.getSurvived(), hypothesis(v)));
        }
        Regression.weights = temp; //check out if problematic
    }

    public static double loss(double actual, double guess) {
        return (actual - guess);
    }

    public static void gradientDescent(Vector[] v) {
        int index = (int) ((Math.random() + 1) * v.length / 2);
        for (int i = index; i < v.length- 1; i++) {
            Regression.weightUpdate(v[i]);
        }
    }
}
