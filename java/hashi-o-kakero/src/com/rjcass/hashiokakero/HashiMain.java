package com.rjcass.hashiokakero;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class HashiMain
{
    public static void main(String[] args)
    {
        new HashiMain().go();
    }

    public HashiMain()
    {
    }

    public void go()
    {
        Board board = new Board(9, 9);

        board.addIsland(0, 1, 2);
        board.addIsland(0, 4, 3);
        board.addIsland(0, 6, 4);
        board.addIsland(0, 8, 4);
        board.addIsland(1, 0, 3);
        board.addIsland(1, 2, 4);
        board.addIsland(1, 5, 2);
        board.addIsland(1, 7, 2);
        board.addIsland(2, 1, 2);
        board.addIsland(2, 3, 5);
        board.addIsland(2, 6, 4);
        board.addIsland(3, 0, 5);
        board.addIsland(3, 2, 4);
        board.addIsland(3, 7, 3);
        board.addIsland(4, 3, 2);
        board.addIsland(4, 5, 3);
        board.addIsland(5, 1, 2);
        board.addIsland(5, 6, 2);
        board.addIsland(5, 8, 4);
        board.addIsland(6, 2, 3);
        board.addIsland(6, 5, 3);
        board.addIsland(6, 7, 2);
        board.addIsland(7, 1, 4);
        board.addIsland(7, 3, 3);
        board.addIsland(7, 6, 2);
        board.addIsland(7, 8, 2);
        board.addIsland(8, 0, 3);
        board.addIsland(8, 2, 2);
        board.addIsland(8, 4, 2);
        board.addIsland(8, 7, 2);

        board.dump(new PrintWriter(new OutputStreamWriter(System.out)));

        board.solve();

        board.dump(new PrintWriter(new OutputStreamWriter(System.out)));
    }
}
