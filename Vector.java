
/**
 *
 * @author colegilbert
 */
public class Vector {

    private double[] vector;
    private double survived; //0.0 if died, 1.0 if survived

    public Vector(int n) {
        this.vector = new double[n];
    }

    public Vector(double[] vector) {
        this(vector.length);
        for (int i = 0; i < vector.length; i++) {
            this.vector[i] = vector[i];
        }
    }

    public Vector(Titanic.Passenger passenger) {
        if (passenger.survived()) {
            this.survived = 1.0;
        } else {
            this.survived = 0.0;
        }
        this.vector = new double[8];
        vector[0] = 1;
        if (!passenger.missing(Titanic.Attribute.PORT)) {
            vector[1] = (double) (passenger.port().ordinal());
        } else {
            System.out.println("Passenger " + passenger.id() + "is missing PORT");
        }
        if (!passenger.missing(Titanic.Attribute.CLASS)) {
            vector[2] = (double) (passenger.pclass().ordinal());
        } else {
            System.out.println("Passenger " + passenger.id() + "is missing CLASS");
        }
        if (!passenger.missing(Titanic.Attribute.SEX)) {
            vector[3] = (double) (passenger.sex().ordinal());
        } else {
            System.out.println("Passenger " + passenger.id() + "is missing SEX");
        }
        if (!passenger.missing(Titanic.Attribute.AGE)) {
            vector[4] = (double) (passenger.age());
        } else {
            System.out.println("Passenger " + passenger.id() + "is missing AGE");
        }
        if (!passenger.missing(Titanic.Attribute.SIBLINGS)) {
            vector[5] = (double) (passenger.siblings());
        } else {
            System.out.println("Passenger " + passenger.id() + "is missing SIBLINGS");
        }
        if (!passenger.missing(Titanic.Attribute.PARENTS)) {
            vector[6] = (double) (passenger.parents());
        } else {
            System.out.println("Passenger " + passenger.id() + "is missing PARENTS");
        }
        if (!passenger.missing(Titanic.Attribute.FARE)) {
            vector[7] = (passenger.fare());
        } else {
            System.out.println("Passenger " + passenger.id() + "is missing FARE");
        }
    }

    public Vector(Vector vector) {
        this(vector.vector);
    }

    public int size() {
        return this.vector.length;
    }

    public double getSurvived() {
        return survived;
    }

    public double get(int index) {
        return this.vector[index];
    }

    public void set(int index, double value) {
        this.vector[index] = value;
    }

    public double norm() {
        return Math.sqrt(normSquared());
    }

    public double normSquared() {
        int n = this.size();
        double result = 0.0;
        for (int i = 0; i < n; i++) {
            double value = this.get(i);
            result += value * value;
        }
        return result;
    }

    public Vector unitVector() {
        int n = this.vector.length;
        double norm = this.norm();
        Vector result = new Vector(n);
        for (int i = 0; i < n; i++) {
            result.set(i, this.get(i) / norm);
        }
        return result;
    }

    public Vector abs() {
        int n = this.size();
        Vector result = new Vector(n);
        for (int i = 0; i < n; i++) {
            result.set(i, Math.abs(this.get(i)));
        }
        return result;
    }

    public Vector negate() {
        int n = this.size();
        Vector result = new Vector(n);
        for (int i = 0; i < n; i++) {
            result.set(i, -this.get(i));
        }
        return result;
    }

    public Vector add(Vector other) {
        checkSameSize(other);
        int n = this.size();
        Vector result = new Vector(n);
        for (int i = 0; i < n; i++) {
            result.set(i, this.get(i) + other.get(i));
        }
        return result;
    }

    public Vector subtract(Vector other) {
        checkSameSize(other);
        int n = this.vector.length;
        Vector result = new Vector(n);
        for (int i = 0; i < n; i++) {
            result.set(i, this.get(i) - other.get(i));
        }
        return result;
    }

    public Vector multiply(double value) {
        int n = this.size();
        Vector result = new Vector(n);
        for (int i = 0; i < n; i++) {
            result.set(i, value * this.get(i));
        }
        return result;
    }

    public double value() {
        double value = 0.0;

        for (int i = 0; i < this.size(); i++) {
            value += this.get(i);
        }
        return value;
    }

    public double dotProduct(Vector other) {
        checkSameSize(other);
        double result = 0.0;
        int n = this.size();
        for (int i = 0; i < n; i++) {
            result += this.get(i) * other.get(i);
        }
        return result;
    }

    public double dot(Vector other) {
        return this.dotProduct(other);
    }

    public boolean equals(Vector other) {
        int n = this.size();
        if (other.size() != n) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            if (this.get(i) != other.get(i)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Vector && this.equals((Vector) other);
    }

    @Override
    public String toString() {
        String result = "{";
        String separator = "";
        for (double x : this.vector) {
            result += separator;
            result += String.format("%5.2f", x);
            separator = ", ";
        }
        result += "}";
        return result;
    }

    private void checkSameSize(Vector other) {
        if (this.size() != other.size()) {
            throw new IllegalArgumentException("Size: " + other.size() + ", expecting " + this.size());
        }
    }
}
