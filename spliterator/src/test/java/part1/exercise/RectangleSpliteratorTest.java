package part1.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;


class RectangleSpliteratorTest {

    private int[][] arr;

    @BeforeEach
    void setup() {
        int temp = 1;
        arr = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                arr[i][j] = temp;
                temp++;
            }
        }
    }

    @Test
    void testParallelSeq() {
        assertEquals(45, StreamSupport.intStream(new RectangleSpliterator(arr), false).sum());
    }

    @Test
    void testParallelStream() {
        assertEquals(45, StreamSupport.intStream(new RectangleSpliterator(arr), true).sum());
    }

    @Test
    void testMaxMethodOfSteam() {
        assertEquals(9, StreamSupport.intStream(new RectangleSpliterator(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}), true)
                .max()
                .orElseThrow(IllegalStateException::new));
    }
}