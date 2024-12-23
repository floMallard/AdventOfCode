package com.fmallard.adventofcode;

public class Position {
    int i;
    int j;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }


    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public Position(int i, int j) {
        this.i = i;
        this.j = j;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return i == position.i && j == position.j;
    }

    @Override
    public String toString() {
        return "Position{" +
                "i=" + i +
                ", j=" + j +
                '}';
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
