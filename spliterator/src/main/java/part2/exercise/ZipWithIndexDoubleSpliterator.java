package part2.exercise;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

public class ZipWithIndexDoubleSpliterator extends Spliterators.AbstractSpliterator<IndexedDoublePair> {


    private final OfDouble inner;
    private int currentIndex;

    public ZipWithIndexDoubleSpliterator(OfDouble inner) {
        this(0, inner);
    }

    private ZipWithIndexDoubleSpliterator(int firstIndex, OfDouble inner) {
        super(inner.estimateSize(), inner.characteristics());
        currentIndex = firstIndex;
        this.inner = inner;
    }

    @Override
    public int characteristics() {
        return inner.characteristics();
    }

    @Override
    public boolean tryAdvance(Consumer<? super IndexedDoublePair> action) {
        if (inner.tryAdvance((double s) -> action.accept(new IndexedDoublePair(currentIndex, s)))) {
            currentIndex++;
            return true;
        }
        return false;
    }

    @Override
    public void forEachRemaining(Consumer<? super IndexedDoublePair> action) {
        inner.forEachRemaining((double s) -> {
            action.accept(new IndexedDoublePair(currentIndex, s));
            currentIndex++;
        });
    }

    @Override
    public Spliterator<IndexedDoublePair> trySplit() {
        if (inner.hasCharacteristics(SIZED & SUBSIZED)) {
            Spliterator<IndexedDoublePair> spliterator = new ZipWithIndexDoubleSpliterator(currentIndex, inner.trySplit());
            currentIndex += estimateSize();
            return spliterator;
        } else {
            return super.trySplit();
        }
    }

    @Override
    public long estimateSize() {
        return inner.estimateSize();
    }
}
