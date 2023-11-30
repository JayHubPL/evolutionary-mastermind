package pl.edu.pw.ee.gui.utils;

public interface ProgressListener {

    default void update(double progress) {
    }

    default void done() {
    }

}
