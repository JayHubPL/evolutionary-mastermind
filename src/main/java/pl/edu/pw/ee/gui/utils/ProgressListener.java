package pl.edu.pw.ee.gui.utils;

public interface ProgressListener {

    void update(double progress);

    default void notifyFinished() {}

}
