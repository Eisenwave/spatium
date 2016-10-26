import net.grian.spatium.Spatium;
import net.grian.spatium.geo.Quaternion;

public class Main {

    public static void main(String[] args) {
        Quaternion a = Quaternion.create(1, 1, 1, Spatium.DEG_TO_RAD *  15);
        Quaternion b = Quaternion.create(1, 1, 1, Spatium.DEG_TO_RAD *  90);
        Quaternion c = Quaternion.product(a, b);
        System.out.println(a + " * " + b + " = " + c);
    }

}
