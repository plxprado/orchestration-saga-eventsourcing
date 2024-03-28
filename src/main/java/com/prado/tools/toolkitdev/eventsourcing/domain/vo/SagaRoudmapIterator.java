package com.prado.tools.toolkitdev.eventsourcing.domain.vo;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;


public class SagaRoudmapIterator implements Iterator<SagaRoudmapItem> {

    private List<SagaRoudmapItem> sagaRoudmapItems;
    private int currentIndex;

    public SagaRoudmapIterator(List<SagaRoudmapItem> sagaRoudmapItems) {
        this.sagaRoudmapItems = sagaRoudmapItems;
        this.currentIndex = 0;
    }




    @Override
    public boolean hasNext() {
        while (currentIndex < sagaRoudmapItems.size()) {
            SagaRoudmapItem currentItem = sagaRoudmapItems.get(currentIndex);
            if (Optional.ofNullable(currentItem).isPresent()) {
                return true;
            }
            currentIndex++;
        }
        return false;
    }

    @Override
    public SagaRoudmapItem next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return sagaRoudmapItems.get(currentIndex++);    }

    @Override
    public void remove() {
        Iterator.super.remove();
    }

    @Override
    public void forEachRemaining(Consumer<? super SagaRoudmapItem> action) {
        Iterator.super.forEachRemaining(action);
    }

    public SagaRoudmapItem findNextItem(SagaRoudmapItem sagaRoudmapItem) {
        while (this.hasNext()) {
            SagaRoudmapItem item = this.next();
            if (sagaRoudmapItem.getId().equals(item.getId())) {
                return this.next();
            }
        }
        throw new NoSuchElementException();
    }
}



