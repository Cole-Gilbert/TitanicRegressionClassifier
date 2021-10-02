
/**
 *
 * @author colegilbert
 */
public class Matrix {

    private double[][] matrix;

    public Matrix(int n) {
        this.matrix = new double[n][n];
    }

    public Matrix(double[][] matrix) {
        this(matrix.length);
        checkSquare(matrix);
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }
    }

    public Matrix(Matrix matrix) {
        this(matrix.matrix);
    }

    public static Matrix identity(int n) {
        Matrix result = new Matrix(n);
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                result.set(row, column, row == column ? 1.0 : 0.0);
            }
        }
        return result;
    }

    public int size() {
        return this.matrix.length;
    }

    public double get(int row, int column) {
        return this.matrix[row][column];
    }

    public void set(int row, int column, double value) {
        this.matrix[row][column] = value;
    }

    public Vector row(int row) {
        int n = this.size();
        Vector result = new Vector(n);
        for (int column = 0; column < n; column++) {
            result.set(column, this.get(row, column));
        }
        return result;
    }

    public Vector column(int column) {
        int n = this.size();
        Vector result = new Vector(n);
        for (int row = 0; row < n; row++) {
            result.set(row, this.get(row, column));
        }
        return result;
    }

    public Matrix transpose() {
        int n = this.size();
        Matrix result = new Matrix(n);
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                result.set(column, row, this.get(row, column));
            }
        }
        return result;
    }

    public Matrix abs() {
        int n = this.size();
        Matrix result = new Matrix(n);
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                result.set(row, column, Math.abs(this.get(row, column)));
            }
        }
        return result;
    }

    public Matrix negate() {
        int n = this.size();
        Matrix result = new Matrix(n);
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                result.set(row, column, -this.get(row, column));
            }
        }
        return result;
    }

    public Matrix add(Matrix other) {
        checkSameSize(other);
        int n = this.size();
        Matrix result = new Matrix(n);
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                result.set(row, column, this.get(row, column) + other.get(row, column));
            }
        }
        return result;
    }

    public Matrix subtract(Matrix other) {
        checkSameSize(other);
        int n = this.size();
        Matrix result = new Matrix(n);
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                result.set(row, column, this.get(row, column) - other.get(row, column));
            }
        }
        return result;
    }

    public Matrix multiply(double scale) {
        int n = this.size();
        Matrix result = new Matrix(n);
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                result.set(row, column, scale * this.get(row, column));
            }
        }
        return result;
    }

    public Vector multiply(Vector vector) {
        int n = this.size();
        Vector product = new Vector(n);
        for (int row = 0; row < n; row++) {
            double sum = 0.0;
            for (int column = 0; column < n; column++) {
                sum += this.get(row, column) * vector.get(row);
            }
            product.set(row, sum);
        }
        return product;
    }

    public Matrix multiply(Matrix other) {
        int n = this.size();
        Matrix product = new Matrix(n);
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                double sum = 0.0;
                for (int k = 0; k < n; k++) {
                    sum += this.get(row, k) * other.get(k, column);
                }
                product.set(row, column, sum);
            }
        }
        return product;
    }

    public Matrix squared() {
        return this.multiply(this);
    }

    private boolean isOdd(int n) {
        return n % 2 != 0;
    }

    public Matrix power(int n) {
        Matrix result = new Matrix(n);
        Matrix square = this;
        while (n > 0) {
            if (isOdd(n)) {
                result = result.multiply(square);
            }
            square = square.squared();
            n /= 2;
        }
        return result;
    }

    public Matrix inverse() {
        return Inverse.inverse(this);
    }

    public boolean equals(Matrix other) {
        int n = this.size();
        if (other.size() != n) {
            return false;
        }
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                if (this.get(row, column) != other.get(row, column)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Matrix && this.equals((Matrix) other);
    }

    public String toString(boolean newlines) {
        String rowSeparator = "";
        String result = "{";
        int n = this.size();
        for (int row = 0; row < n; row++) {
            String separator = "";
            result += rowSeparator;
            result += "{";
            for (int column = 0; column < n; column++) {
                result += separator;
                result += String.format("%5.2f", this.get(row, column));
                separator = ", ";
            }
            result += "}";
            rowSeparator = newlines ? ",\n " : ", ";
        }
        result += "}";
        return result;
    }

    @Override
    public String toString() {
        return this.toString(false);
    }

    private void checkSameSize(Matrix other) {
        if (this.size() != other.size()) {
            throw new IllegalArgumentException("Size: " + other.size() + ", expecting " + this.size());
        }
    }

    private void checkSquare(double[][] matrix) {
        int n = this.matrix.length;
        for (int row = 0; row < n; row++) {
            int length = matrix[row].length;
            if (length != n) {
                throw new IllegalArgumentException("Not square: length of row " + row + " is " + length + ", expecting " + n);
            }
        }
    }
}
