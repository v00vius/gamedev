package types;

public class Triple<T1, T2, T3> {
    public T1 first;
    public T2 second;
    public T3 third;

    public Triple(T1 e1, T2 e2, T3 e3) {
        first = e1;
        second = e2;
        third = e3;
    }
}
