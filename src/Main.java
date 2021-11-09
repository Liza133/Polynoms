import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        System.out.println(new Polynom(new double[]{-1, -1, -1, -1, 1, -1, -1, 1}));
        double[] x = new double[]{-1, 0, 1};
        double[] y = new double[]{1, 0, 1};
        try {
            Polynom w = Polynom.Lagrange(x, y);
            System.out.println(w);
        } catch (WrongXException e) {
            System.out.println("wrong x");
        } catch (WrongDemensionException e) {
            System.out.println("wrong demensions");
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String x_str = reader.readLine();
            String y_str = reader.readLine();
            String[] x_s = x_str.split(" ");
            String[] y_s = y_str.split(" ");
            double[] xs = new double[x_s.length];
            for (int i = 0; i < x_s.length; i++) {
                xs[i] = Double.parseDouble(x_s[i]);
            }
            double[] ys = new double[y_s.length];
            for (int i = 0; i < y_s.length; i++) {
                ys[i] = Double.parseDouble(y_s[i]);
            }
            Polynom q = Polynom.Lagrange(xs, ys);
            System.out.println(q);
        } catch (IOException e) {
            System.out.println("BufReadExeption");
        } catch (WrongXException e) {
            System.out.println("wrong x");
        } catch (WrongDemensionException e) {
            System.out.println("wrong demensions");
        } catch (NumberFormatException e){
            System.out.println("not a number");
        }
    }
}