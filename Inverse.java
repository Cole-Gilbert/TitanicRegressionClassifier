
/**
 *
 * @author colegilbert
 */

public class Inverse {

    private int n;
    private Matrix original;
    private Matrix inverse;
    private boolean[] pivoted;

    private Inverse(Matrix matrix) {
        this.n = matrix.size();
        this.original = new Matrix(matrix);
        this.inverse = Matrix.identity(n);
        this.pivoted = new boolean[n];
    }

    private void swapRows(int row1, int row2) {
        double value1, value2;
        for (int column = 0; column < n; column++) {
            value1 = original.get(row1, column);
            value2 = original.get(row2, column);
            original.set(row1, column, value2);
            original.set(row2, column, value1);

            value1 = inverse.get(row1, column);
            value2 = inverse.get(row2, column);
            inverse.set(row1, column, value2);
            inverse.set(row2, column, value1);
        }
    }

    private void swapColumns(int column1, int column2) {
        double value1, value2;
        for (int row = 0; row < n; row++) {
            value1 = original.get(row, column1);
            value2 = original.get(row, column2);
            original.set(row, column1, value2);
            original.set(row, column2, value1);

            value1 = inverse.get(row, column1);
            value2 = inverse.get(row, column2);
            inverse.set(row, column1, value2);
            inverse.set(row, column2, value1);
        }
    }

    private void scaleRow(int row, double scale) {
        double value;
        for (int column = 0; column < n; column++) {
            value = original.get(row, column);
            original.set(row, column, scale * value);
            value = inverse.get(row, column);
            inverse.set(row, column, scale * value);
        }
    }

    private void scaleColumn(int column, double scale) {
        double value;
        for (int row = 0; row < n; row++) {
            value = original.get(row, column);
            original.set(row, column, scale * value);
            value = inverse.get(row, column);
            inverse.set(row, column, scale * value);
        }
    }

    private void subtractFromRow(int row, int from) {
        double source, target;
        for (int column = 0; column < n; column++) {
            source = original.get(row, column);
            target = original.get(from, column);
            original.set(from, column, target - source);
            source = inverse.get(row, column);
            target = inverse.get(from, column);
            inverse.set(from, column, target - source);
        }
    }

    private void subtractFromColumn(int column, int from) {
        double source, target;
        for (int row = 0; row < n; row++) {
            source = original.get(row, column);
            target = original.get(row, from);
            original.set(row, from, target - source);
            source = inverse.get(row, column);
            target = inverse.get(row, from);
            inverse.set(row, from, target - source);
        }
    }

    private static boolean isCloserToOne(double x, double y) {
        return Math.abs(1.0 - x) < Math.abs(1.0 - y);
    }

    private int findPivotRow(int column) {
        double pivotValue = 0.0;
        int pivotRow = -1;
        for (int row = 0; row < n; row++) {
            double value = original.get(row, column);
            if (value != 0.0 && !pivoted[row]) {
                if (pivotValue == 0.0 || isCloserToOne(value, pivotValue)) {
                    pivotValue = value;
                    pivotRow = row;
                } else if (isCloserToOne(value, pivotValue)) {
                    pivotValue = value;
                    pivotRow = row;
                }
            }
        }
        this.pivoted[pivotRow] = true;
        return pivotRow;
    }

    private void reduceRow(int row, int pivotRow, int pivotColumn) {
        double pivot = this.original.get(pivotRow, pivotColumn);
        double scale = this.original.get(row, pivotColumn) / pivot;
        double thisRowValue, pivotRowValue, updatedValue;

        for (int column = 0; column < n; column++) {
            pivotRowValue = this.original.get(pivotRow, column);
            thisRowValue = this.original.get(row, column);
            updatedValue = thisRowValue - scale * pivotRowValue;
            if (column == pivotColumn) {
                this.original.set(row, column, 0.0);
            } else {
                this.original.set(row, column, updatedValue);
            }
            pivotRowValue = this.inverse.get(pivotRow, column);
            thisRowValue = this.inverse.get(row, column);
            updatedValue = thisRowValue - scale * pivotRowValue;
            this.inverse.set(row, column, updatedValue);
        }
    }

    public Matrix inverse() {
        for (int column = 0; column < n; column++) {
            int pivotColumn = column;
            int pivotRow = findPivotRow(column);
            double pivotValue = original.get(pivotRow, pivotColumn);
            scaleRow(pivotRow, 1.0 / pivotValue);
            original.set(pivotRow, pivotColumn, 1.0);
            for (int row = 0; row < n; row++) {
                if (row != pivotRow) {
                    reduceRow(row, pivotRow, pivotColumn);
                }
            }
        }
        return this.inverse;
    }

    public static Matrix inverse(Matrix matrix) {
        Inverse inverse = new Inverse(matrix);
        return inverse.inverse();
    }
}
