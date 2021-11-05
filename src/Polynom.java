import org.jetbrains.annotations.NotNull;

public class Polynom {
    double[] coeff;

    Polynom() {
    }

    //Конструктор
    Polynom(double @NotNull [] coeff) {
        while (coeff[coeff.length - 1] == 0 && coeff.length != 1) {
            double[] result = new double[coeff.length - 1];
            System.arraycopy(coeff, 0, result, 0, coeff.length - 1);
            coeff = result;
        }
        this.coeff = coeff;
    }

    //Вывод
    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < coeff.length; i++) {
            if (i == 0) {
                if (coeff[i] < 0) {
                    res = " - " + Math.abs(coeff[i]);
                } else if (coeff[i] >= 0) {
                    if (coeff.length == 1) {
                        res = Double.toString(coeff[i]);
                    } else if (coeff[i] > 0) {
                        res = " + " + coeff[i];
                    }
                }
            } else if (i == coeff.length - 1) {
                if (i == 1) {
                    if (coeff[i] == 1) {
                        res = "x" + res;
                    } else if (coeff[i] == -1) {
                        res = "-x" + res;
                    } else {
                        res = coeff[i] + "x" + res;
                    }
                } else {
                    if (coeff[i] == 1) {
                        res = "x^" + i + res;
                    } else if (coeff[i] == -1) {
                        res = "-x^" + i + res;
                    } else {
                        res = coeff[i] + "x^" + i + res;
                    }
                }
            } else if (i == 1) {
                if (coeff[i] > 0) {
                    if (coeff[i] == 1) {
                        res = " + x" + res;
                    } else {
                        res = " + " + coeff[i] + "x" + res;
                    }
                } else if (coeff[i] < 0) {
                    if (coeff[i] == -1) {
                        res = " - x" + res;
                    } else {
                        res = " - " + Math.abs(coeff[i]) + "x" + res;
                    }
                }
            } else {
                if (coeff[i] > 0) {
                    if (coeff[i] == 1) {
                        res = " + x^" + i + res;
                    } else {
                        res = " + " + coeff[i] + "x^" + i + res;
                    }
                } else if (coeff[i] < 0) {
                    if (coeff[i] == -1) {
                        res = " - x^" + i + res;
                    } else {
                        res = " - " + Math.abs(coeff[i]) + "x^" + i + res;
                    }
                }
            }
        }
        return res;
    }

    //Сложение полиномов
    public Polynom add(@NotNull Polynom b) {
        double[] res = new double[Math.max(this.coeff.length, b.coeff.length)];
        if (this.coeff.length > b.coeff.length) {
            res = this.coeff;
            for (int i = 0; i < b.coeff.length; i++) {
                res[i] += b.coeff[i];
            }
        } else {
            res = b.coeff;
            for (int i = 0; i < this.coeff.length; i++) {
                res[i] += this.coeff[i];
            }
        }
        return new Polynom(res);
    }

    //Умножение полинома на число
    public Polynom numMult(double k) {
        double[] res = new double[coeff.length];
        for (int i = 0; i < coeff.length; i++) {
            res[i] = k * coeff[i];
        }
        return new Polynom(res);
    }

    //Умножение полинома на полином
    public Polynom mult(@NotNull Polynom b) {
        double[] res = new double[this.coeff.length + b.coeff.length - 1];
        for (int i = 0; i < this.coeff.length; i++) {
            for (int j = 0; j < b.coeff.length; j++) {
                res[i + j] += coeff[i] * b.coeff[j];
            }
        }
        return new Polynom(res);
    }

    //Полином в точке
    public double polynomValueAt(double x) {
        double res = 0;
        for (int i = 0; i < coeff.length; i++) {
            res += coeff[i] * Math.pow(x, i);
        }
        return res;
    }

    //Полином Лагранжа
    //По узлам создадим массив полиномов, из которых состоят произведения в числителе l k-ых
    private static Polynom @NotNull [] numPolynoms(double @NotNull [] nodalPoints) {
        Polynom[] res = new Polynom[nodalPoints.length];
        for (int i = 0; i < nodalPoints.length; i++) {
            double[] с = new double[]{-nodalPoints[i], 1};
            res[i] = new Polynom(с);
        }
        return res;
    }

    //метод, считающий числитель l k-ого
    private static Polynom numerator(double[] nodalPoints, int k) {
        Polynom res = new Polynom(new double[]{1});
        Polynom[] numPols = numPolynoms(nodalPoints);
        for (int i = 0; i < numPols.length; i++) {
            if (i != k) {
                res = res.mult(numPols[i]);
            }
        }
        return res;
    }

    //метод, считающий знаменатель l k-го (по сути, значение числителя в точке x_k)
    private static double denominator(double @NotNull [] nodalPoints, int k) {
        double x_k = nodalPoints[k];
        Polynom plm = numerator(nodalPoints, k);
        return plm.polynomValueAt(x_k);
    }

    //создаем массив l k-ых, используя предыдущие методы
    private static Polynom @NotNull [] l(double @NotNull [] nodalPoints) {
        Polynom[] res = new Polynom[nodalPoints.length];
        for (int i = 0; i < nodalPoints.length; i++) {
            Polynom p = numerator(nodalPoints, i);
            double k = denominator(nodalPoints, i);
            res[i] = p.numMult(1 / k);
        }
        return res;
    }

    //вычисляем полином Лагранжа по заданным узловым точкам и значениям функции в этих точках
    public static Polynom Lagrange(double[] nodalPoints, double[] func) {
        xException(nodalPoints);
        checkDemensions(nodalPoints, func);
        Polynom res = new Polynom(new double[]{0});
        Polynom[] l = l(nodalPoints);
        for (int i = 0; i < nodalPoints.length; i++) {
            res = res.add(l[i].numMult(func[i]));
        }
        return res;
    }

    //Проверяем иксы на одинаковость
    public static void xException(double[] x) {
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x.length; j++) {
                if (i != j && x[i] == x[j]) {
                    System.out.println("+");
                    throw new WrongXException();
                }
            }
        }
    }

    //Проверяем размерности
    public static void checkDemensions(double[] x, double[] y) {
        if (x.length != y.length) {
            throw new WrongDemensionException();
        }
    }
}
