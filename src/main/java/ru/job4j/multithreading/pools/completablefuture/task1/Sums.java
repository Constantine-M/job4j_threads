package ru.job4j.multithreading.pools.completablefuture.task1;

import java.util.Objects;

public class Sums {

    private int rowSum;

    private int colSum;

    /**
     * Данный класс описывает
     * суммы по строкам и столбцам.
     */
    public Sums(int rowSum, int colSum) {
        this.rowSum = rowSum;
        this.colSum = colSum;
    }

    public int getRowSum() {
        return rowSum;
    }

    public void setRowSum(int rowSum) {
        this.rowSum = rowSum;
    }

    public int getColSum() {
        return colSum;
    }

    public void setColSum(int colSum) {
        this.colSum = colSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sums sums)) {
            return false;
        }
        return getRowSum() == sums.getRowSum() && getColSum() == sums.getColSum();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRowSum(), getColSum());
    }

    @Override
    public String
    toString() {
        return "Sums{"
                + "rowSum=" + rowSum
                + ", colSum=" + colSum
                + '}';
    }
}
