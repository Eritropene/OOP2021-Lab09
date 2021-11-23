package it.unibo.oop.lab.workers02;

public class MultiThreadedSumMatrix implements SumMatrix {

    private final int nthread;
    /**
     * 
     * @param nthread
     */
    public MultiThreadedSumMatrix(final int nthread) {
        this.nthread = nthread;
    }
    private class Worker extends Thread {
        @Override
        public void run() {
        }
    }
    @Override
    public double sum(final double[][] matrix) {
        return 0;
    }

}
