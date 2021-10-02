
/**
 *
 * @author colegilbert
 */
public class TitanicStatistics {

    // A package to compute various statistics for a set of passengers.
    // The table passed to these methods may be a subset of the passengers.

    public static enum Relation { 
        LESS,
        EQUAL,
        GREATER,
        NOT_EQUAL,
        LESS_EQUAL,
        GREATER_EQUAL
    }

    private static boolean compare(double left, double right, Relation relation) {
        switch (relation) {
            case LESS:          return left < right;
            case EQUAL:         return left == right;
            case GREATER:       return left > right;
            case NOT_EQUAL:     return left != right;
            case LESS_EQUAL:    return left <= right;
            case GREATER_EQUAL: return left >= right;
        }
        throw new IllegalArgumentException("Invalid relation: " + relation);
    }

    public static int survivors(Titanic.Passenger[] passengers) {
        // Number of passengers in this set that survived.
        int count = 0;
        for (Titanic.Passenger p : passengers) {
            if (p.survived()) count++;
        }
        return count;
    }

    public static int missing(Titanic.Passenger[] passengers, Titanic.Attribute attribute) {
        // Number of passengers in this set missing a value for this attribute.
        int count = 0;
        for (Titanic.Passenger p : passengers) {
            if (p.missing(attribute)) count++;
        }
        return count;
    }
    
    public static Titanic.Passenger[] notMissing(Titanic.Passenger[] passengers, Titanic.Attribute attribute){
            int n = TitanicStatistics.count(passengers, attribute);
            Titanic.Passenger[] result = new Titanic.Passenger[n];
        int count = 0;
        for (Titanic.Passenger p : passengers) {
            if (!p.missing(attribute)) {
                result[count++] =  p;
            }
        }
        return result;
}

    public static int count(Titanic.Passenger[] passengers, Titanic.Attribute attribute) {
        // Number of passengers in this set having a valid value for this attribute.
        int count = 0;
        for (Titanic.Passenger p : passengers) {
            if (!p.missing(attribute)) count++;
        }
        return count;
    }

    public static double min(Titanic.Passenger[] passengers, Titanic.Attribute attribute) {
        // Minimum value for this attribute for the passengers in this set.
        double min = Double.MAX_VALUE;
        for (Titanic.Passenger p : passengers) {
            if (!p.missing(attribute)) {
                min = Math.min(min, p.get(attribute));
            }
        }
        return min;
    }

    public static double max(Titanic.Passenger[] passengers, Titanic.Attribute attribute) {
        // Maximum value for this attribute for the passengers in this set.
        double max = Double.MIN_VALUE;
        for (Titanic.Passenger p : passengers) {
            if (!p.missing(attribute)) {
                max = Math.max(max, p.get(attribute));
            }
        }
        return max;
    }

    public static double average(Titanic.Passenger[] passengers, Titanic.Attribute attribute) {
        // Average value for this attribute for the passengers in this set.
        double total = 0.0;
        int count = 0;
        for (Titanic.Passenger p : passengers) {
            if (!p.missing(attribute)) {
                total += p.get(attribute);
                count++;
            }
        }
        return (count > 0) ? total / count : 0.0;
    }

    public static double standardDeviation(Titanic.Passenger[] passengers, Titanic.Attribute attribute) {
        // Variance for this attribute for the passengers in this set.
        double average = TitanicStatistics.average(passengers, attribute);
        double sumOfSquaredDifferences = 0.0;
        int count = 0;
        for (Titanic.Passenger p : passengers) {
            if (!p.missing(attribute)) {
                double difference = p.get(attribute) - average;
                sumOfSquaredDifferences += difference * difference;
                count++;
            }
        }
        return (count > 0) ? Math.sqrt(sumOfSquaredDifferences/count) : 0.0;
    }


    public static int count(
		Titanic.Passenger[] passengers,
		Titanic.Attribute   attribute,
		double              value)
	{
        // Number of passengers in this set having a specified value for this attribute.
        int count = 0;
        for (Titanic.Passenger p : passengers) {
            if (!p.missing(attribute) && p.get(attribute) == value) count++;
        }
        return count;
    }

    public static int count(
		Titanic.Passenger[] passengers,
		Titanic.Attribute   attribute,
		double              value,
		Relation            relation)
	{
        // Number of passengers in this set having a value satisfying a given relation for this attribute.
        // For example find the number of passengers in this set older than 50.
        int count = 0;
        for (Titanic.Passenger p : passengers) {
            if (!p.missing(attribute) && compare(p.get(attribute), value, relation)) count++;
        }
        return count;
    }

    public static Titanic.Passenger[] select(
		Titanic.Passenger[] passengers,
		Titanic.Attribute   attribute,
		double              value)
	{
        // Returns the subset of the passengers in this set having a specified value for an attribute.
        int n = TitanicStatistics.count(passengers, attribute, value);
        Titanic.Passenger[] result = new Titanic.Passenger[n];
        int count = 0;
        for (Titanic.Passenger p : passengers) {
            if (!p.missing(attribute) && p.get(attribute) == value) {
                result[count++] =  p;
            }
        }
        return result;
    }

    public static Titanic.Passenger[] select(Titanic.Passenger[] passengers, Titanic.Attribute   attribute, double              value, Relation            relation)
	{
        // Returns the subset of the passengers in this set having a value satisfying a relation for this attribute.
        // For example, return the subset of passengers in the set who are older than 50.
        int n = TitanicStatistics.count(passengers, attribute, value, relation);
        Titanic.Passenger[] result = new Titanic.Passenger[n];
        int count = 0;
        for (Titanic.Passenger p : passengers) {
            if (!p.missing(attribute) && compare(p.get(attribute), value, relation)) {
                result[count++] =  p;
            }
        }
        return result;
    }

    public static double entropy(Titanic.Passenger[] passengers) {
        // Determine the entropy for a given subset of the passengers.
        double survivors = TitanicStatistics.survivors(passengers);
        double p = survivors / passengers.length;
        double q = 1.0 - p;
        return -(p * Math.log(p) + q * Math.log(q)) / Math.log(2.0);
    }
}
