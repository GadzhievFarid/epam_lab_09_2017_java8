package part1.exercise;

import com.sun.deploy.util.ArrayUtil;

import javax.naming.OperationNotSupportedException;
import java.util.Arrays;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class RectangleSpliterator extends Spliterators.AbstractIntSpliterator {

    private final int[][] array;
    private int rowStartInclusive;
    private int rowEndExclusive;
    private int columnStartInclusive;

    RectangleSpliterator(int[][] array) {
        this(array, 0, array.length, 0);
    }

    private RectangleSpliterator(int[][] array, int rowStartInclusive, int rowEndExclusive, int columnStartInclusive) {
        super(checkArrayAndCalcEstimatedSize(array), Spliterator.IMMUTABLE
                | Spliterator.ORDERED
                | Spliterator.SIZED
                | Spliterator.SUBSIZED
                | Spliterator.NONNULL);
        this.array = array;
        this.rowStartInclusive = rowStartInclusive;
        this.rowEndExclusive = rowEndExclusive;
        this.columnStartInclusive = columnStartInclusive;
    }


    private static long checkArrayAndCalcEstimatedSize(int[][] array) {
        return array.length * array[0].length;
    }

    @Override
    public OfInt trySplit() {
        if (rowEndExclusive - rowStartInclusive < 2) {
            return null;
        }
        int middleRow = rowStartInclusive + (rowEndExclusive - rowStartInclusive) / 2;
        RectangleSpliterator leftSpliterator = new RectangleSpliterator(array, 0, middleRow, 0);
        rowStartInclusive = middleRow;
        columnStartInclusive = 0;
        return leftSpliterator;
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        if (rowStartInclusive < rowEndExclusive && columnStartInclusive < array[0].length) {
            action.accept(array[rowStartInclusive][columnStartInclusive]);
            ++columnStartInclusive;
            return true;
        }
        return false;
    }
}