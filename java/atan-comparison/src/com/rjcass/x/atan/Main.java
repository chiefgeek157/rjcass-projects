package com.rjcass.x.atan;

public class Main {

    private static double ATAN_C1 = 48.70107004404898384;

    private static double ATAN_C2 = 49.5326263772254345;

    private static double ATAN_C3 = 9.40604244231624;

    private static double ATAN_C4 = 48.70107004404996166;

    private static double ATAN_C5 = 65.7663163908956299;

    private static double ATAN_C6 = 21.587934067020262;

    public static void main(String[] args) {
        new Main().compare();
    }

    public Main() {
    }

    public void compare() {
        for(int i = -180; i<=180; i++) {
            double angle = i/180.0*Math.PI;
            double tan = Math.tan(angle);
            double atan = Math.atan(tan);
            double atanA = atanA(tan);
            double atanB = atanB(tan);
            System.out.println("Angle: "+i+" ATAN: "+atan+" atanA diff: "
                    +(atanA-atan)+" atanB diff: "+(atanB-atan));
        }
    }

    private double atanA(double x) {
        double x2 = x*x;
        return(x*(ATAN_C1+x2*(ATAN_C2+x2*ATAN_C3))/(ATAN_C4+x2
                *(ATAN_C5+x2*(ATAN_C6+x2))));
    }

    private double atanB(double x) {
        return (x+0.043157974*x*x*x)/(1.0+0.76443945*x*x+0.05831938*x*x*x*x);
    }

//    private double atanC(double x) {
//        double w, s1, s2, z;
//        int ix, hx, id;
//
//        hx = __HI(x);
//        ix = hx&0x7fffffff;
//        if(ix>=0x44100000) {
//            /* if |x| >= 2^66 */
//            if(ix>0x7ff00000||(ix==0x7ff00000&&(__LO(x)!=0)))
//                return x+x; /* NaN */
//            if(hx>0)
//                return atanhi[3]+atanlo[3];
//            else
//                return -atanhi[3]-atanlo[3];
//        }
//        if(ix<0x3fdc0000) { /* |x| < 0.4375 */
//            if(ix<0x3e200000) { /* |x| < 2^-29 */
//                if(huge+x>one)
//                    return x; /* raise inexact */
//            }
//            id = -1;
//        } else {
//            x = fabs(x);
//            if(ix<0x3ff30000) { /* |x| < 1.1875 */
//                if(ix<0x3fe60000) { /* 7/16 <=|x|<11/16 */
//                    id = 0;
//                    x = (2.0*x-one)/(2.0+x);
//                } else { /* 11/16<=|x|< 19/16 */
//                    id = 1;
//                    x = (x-one)/(x+one);
//                }
//            } else {
//                if(ix<0x40038000) { /* |x| < 2.4375 */
//                    id = 2;
//                    x = (x-1.5)/(one+1.5*x);
//                } else { /* 2.4375 <= |x| < 2^66 */
//                    id = 3;
//                    x = -1.0/x;
//                }
//            }
//        }
//        /* end of argument reduction */
//        z = x*x;
//        w = z*z;
//        /* break sum from i=0 to 10 aT[i]z**(i+1) into odd and even poly */
//        s1 = z*(aT[0]+w*(aT[2]+w*(aT[4]+w*(aT[6]+w*(aT[8]+w*aT[10])))));
//        s2 = w*(aT[1]+w*(aT[3]+w*(aT[5]+w*(aT[7]+w*aT[9]))));
//        if(id<0)
//            return x-x*(s1+s2);
//        else {
//            z = atanhi[id]-((x*(s1+s2)-atanlo[id])-x);
//            return (hx<0) ? -z : z;
//        }
//    }
}
