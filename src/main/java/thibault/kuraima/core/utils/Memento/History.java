package thibault.kuraima.core.utils.Memento;

import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class History implements Serializable {

    private List<Memento> history = new ArrayList<Memento>();
    private int virtualSize = 0;

    public void push(Memento m) {
        if (virtualSize != history.size() && virtualSize > 0) {
            history = history.subList(0, virtualSize - 1);
        }
        history.add(m);
        virtualSize = history.size();
        System.out.println("History size: " + history.size());
    }

    public boolean undo() {
        Memento m = getUndo();
        if (m == null) {
            return false;
        }
        m.restore();
        return true;
    }

    private Memento getUndo() {
        if (virtualSize == 0) {
            return null;
        }
        virtualSize = Math.max(0, virtualSize - 1);
        return history.get(virtualSize);
    }

    public boolean redo() {
        Memento m = getRedo();
        if (m == null) {
            return false;
        }
        m.restore();
        return true;
    }

    private Memento getRedo() {
        if (virtualSize == history.size()) {
            return null;
        }
        virtualSize = Math.min(history.size(), virtualSize + 1);
        return history.get(virtualSize - 1);
    }

}
