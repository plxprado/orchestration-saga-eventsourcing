package com.prado.tools.toolkitdev.eventsourcing.domain.vo;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;


public class SagaWorkflowIterator implements Iterator<SagaWorkflowItem> {

    private List<SagaWorkflowItem> sagaWorkflowItems;
    private int currentIndex;

    public SagaWorkflowIterator(List<SagaWorkflowItem> sagaWorkflowItems) {
        this.sagaWorkflowItems = sagaWorkflowItems;
        this.currentIndex = 0;
    }




    @Override
    public boolean hasNext() {
        while (currentIndex < sagaWorkflowItems.size()) {
            SagaWorkflowItem currentItem = sagaWorkflowItems.get(currentIndex);
            if (Optional.ofNullable(currentItem).isPresent()) {
                return true;
            }
            currentIndex++;
        }
        return false;
    }

    @Override
    public SagaWorkflowItem next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return sagaWorkflowItems.get(currentIndex++);    }

    @Override
    public void remove() {
        Iterator.super.remove();
    }

    @Override
    public void forEachRemaining(Consumer<? super SagaWorkflowItem> action) {
        Iterator.super.forEachRemaining(action);
    }

    public SagaWorkflowItem findNextItem(SagaWorkflowItem sagaWorkflowItem) {
        while (this.hasNext()) {
            SagaWorkflowItem item = this.next();
            if (sagaWorkflowItem.getId().equals(item.getId())) {
                return this.next();
            }
        }
        throw new NoSuchElementException();
    }
}



