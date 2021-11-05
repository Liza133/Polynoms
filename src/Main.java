public class Main {
    public static void main(String[] args) {
        System.out.println(new Polynom(new double[]{-1,-1,-1,-1,1,-1,-1,1}));
        double[] x = new double[]{-1,-1,0,-2};
        double[] y = new double[]{1,0,1};
        try{Polynom с = Polynom.Lagrange(x,y);}
        catch (WrongXException e){
            System.out.println("wrong x");
        }
        catch (WrongDemensionException e){
            System.out.println("wrong demensions");
        }
        //System.out.println(с);
    }
}