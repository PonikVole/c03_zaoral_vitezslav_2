package model;

public class Edge {
    private int x1, y1, x2, y2;

    public Edge(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public boolean isHorizontal() {
        return y1 == y2;
    }

    public void orientate() {
        if (y1 > y2){
            int temp = y2;
            y2 = y1;
            y1 = temp;

            temp = x2;
            x2 = x1;
            x1 = temp;
        }
    }

    public boolean isIntersection(int y) {
        return Integer.min(y1, y2-1) <= y && Integer.max(y1, y2-1) >= y;
    }


    public int getIntersection(int y) {
        if (!isIntersection(y)) return -1;
        double k;
        if (x1 != x2)
            k = (y2 - y1) / (double) (x2 - x1);
        else
            k = Integer.MAX_VALUE;
        double q = y1 - k * x1;

        // y = k*x + q
        // x = (y - q) / k
        double x = (y - q) / k;

        return (int) Math.round(x);
    }
}
