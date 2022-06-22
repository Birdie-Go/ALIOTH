package parser;

/**
 * pair class like c++
 */
public class Pairs<A, B> {
    /** key */
    public final A first;
    /** value */
    public final B second;

    /**
     * ¹¹Ôìº¯Êı
     * @param a key
     * @param b value
     */
    public Pairs(A a, B b){
        first = a;
        second = b;
    }
}