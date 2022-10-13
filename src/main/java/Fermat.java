import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class Fermat {

    public static void main(String[] args) {
        final long N = 99595193774911L;
        final long e = 1908299L;
        final List<Long> C = List.of(
                75790643190143L,
                36869061035180L,
                38422576553598L,
                68899435645717L,
                16193161920958L,
                98487458352335L,
                34167725433806L,
                96613844267045L,
                26583768908805L,
                73052827576371L,
                94695336463618L,
                69092596694070L
        );

        long n = (long) Math.sqrt(N) + 1;

        ResultPair resultPair = findTAndOmega(N, n);

        long p = resultPair.t + (long) Math.sqrt(resultPair.w);
        System.out.printf("p = %d\n", p);

        long q = resultPair.t - (long) Math.sqrt(resultPair.w);
        System.out.printf("q = %d\n", q);

        long phi = (p - 1) * (q - 1);
        System.out.printf("phi = %d\n", phi);

        long d = BigInteger.valueOf(e).modPow(BigInteger.valueOf(-1), BigInteger.valueOf(phi)).longValue();
        System.out.printf("d = %d\n\n", d);

        List<Long> messages = encryptList(C, d, N);
        messages.forEach(System.out::println);
    }

    static class ResultPair {
        long t;
        long w;

        public ResultPair(long t, long w) {
            this.t = t;
            this.w = w;
        }
    }

    public static ResultPair findTAndOmega(long N, Long n) {
        for (int i = 1; ; i++) {
            n++;

            System.out.printf("t%d = %d\n", i, n);

            long w = n * n - N;

            System.out.printf("t%d = %d\n", i, n * n);
            System.out.printf("d%d = %d\n\n", i, w);

            if (Math.abs(w - (long) Math.sqrt(w) * (long) Math.sqrt(w)) == 0) {
                System.out.printf("SUCCESS, i = %d\n\n", i);
                return new ResultPair(n, w);
            }
        }
    }

    public static List<Long> encryptList(List<Long> C, long d, long N) {
        return C.stream().map(elem -> encrypt(elem, d, N)).collect(Collectors.toList());
    }

    public static long encrypt(Long C, long d, long N) {
        return BigInteger.valueOf(C).modPow(BigInteger.valueOf(d), BigInteger.valueOf(N)).longValue();
    }
}
