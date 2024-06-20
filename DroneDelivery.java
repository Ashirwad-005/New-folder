package Sol;

import java.util.*;

public class ClosestPairAlgorithm {
    static Scanner scan = new Scanner(System.in);

    static class Point {
        double x;
        double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static List<Point> generateRandomPoints(int numPoints) {
        List<Point> points = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numPoints; i++) {
            double x = random.nextDouble() * 1000;
            double y = random.nextDouble() * 1000;
            points.add(new Point(x, y));
        }

        return points;
    }

    public static double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }

    public static double bruteForceClosestPair(List<Point> points) {
        int n = points.size();
        double minDist = Double.POSITIVE_INFINITY;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double dist = distance(points.get(i), points.get(j));
                if (dist < minDist) {
                    minDist = dist;
                }
            }
        }

        return minDist;
    }

    public static double closestPair(List<Point> points) {
        List<Point> sortedPoints = new ArrayList<>(points);
        Collections.sort(sortedPoints, (p1, p2) -> Double.compare(p1.x, p2.x));

        return closestPairHelper(sortedPoints);
    }

    private static double closestPairHelper(List<Point> sortedPoints) {
        int n = sortedPoints.size();
        if (n <= 3) {
            return bruteForceClosestPair(sortedPoints);
        }

        int mid = n / 2;
        Point midPoint = sortedPoints.get(mid);

        List<Point> leftPoints = sortedPoints.subList(0, mid);
        List<Point> rightPoints = sortedPoints.subList(mid, n);

        double leftMinDist = closestPairHelper(leftPoints);
        double rightMinDist = closestPairHelper(rightPoints);

        double minDist = Math.min(leftMinDist, rightMinDist);

        List<Point> strip = new ArrayList<>();
        for (Point point : sortedPoints) {
            if (Math.abs(point.x - midPoint.x) < minDist) {
                strip.add(point);
            }
        }
        Collections.sort(strip, (p1, p2) -> Double.compare(p1.y, p2.y));

        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size() && (strip.get(j).y - strip.get(i).y) < minDist; j++) {
                double dist = distance(strip.get(i), strip.get(j));
                if (dist < minDist) {
                    minDist = dist;
                }
            }
        }

        return minDist;
    }

    public static void main(String[] args) {
        List<Point> points = null;
        System.out.println("Enter the number to generate random points:");
        int num = scan.nextInt();
        System.out.println("Select the option to perform action on taking inputs of points");
        System.out.println("Press 1 for taking input from user");
        System.out.println("Press 2 for generating random points");
        int choice=scan.nextInt();
        if(choice==1) {
        	points= inputstreampoints(num);
        }
        else if(choice==2) {
        	points = generateRandomPoints(num);
        }else {
        	System.out.print("invalid input");
        }
        long startTimeBruteForce = System.currentTimeMillis();
        double minDistBruteForce = bruteForceClosestPair(points);
        long endTimeBruteForce = System.currentTimeMillis();
        long totalTimeBruteForce = endTimeBruteForce - startTimeBruteForce;

        long startTimeDivideConquer = System.currentTimeMillis();
        double minDistDivideConquer = closestPair(points);
        long endTimeDivideConquer = System.currentTimeMillis();
        long totalTimeDivideConquer = endTimeDivideConquer - startTimeDivideConquer;

        System.out.println("Brute Force Closest Pair:");
        System.out.println("Total Distance: " + minDistBruteForce);
        System.out.println("Time taken: " + totalTimeBruteForce );

        System.out.println();

        System.out.println("Divide and Conquer Closest Pair:");
        System.out.println("Total Distance: " + minDistDivideConquer);
        System.out.println("Time taken: " + totalTimeDivideConquer );
    }
    private static List<Point> inputstreampoints(int num) {
    	List<Point> points = new ArrayList<>();
    	for (int i = 0; i < num; i++) {
            double x = scan.nextDouble() ; 
            double y = scan.nextDouble() ;
            points.add(new Point(x, y));
        }
		return points;
    }
}
