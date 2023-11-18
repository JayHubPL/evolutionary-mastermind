package pl.edu.pw.ee.evo.utils;

import lombok.Value;

@Value(staticConstructor = "of")
public class Pair<T1, T2> {

    public T1 first;
    public T2 second;

}
