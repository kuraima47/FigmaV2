package thibault.kuraima.core.utils.Memento;

import thibault.kuraima.core.utils.Command.Command;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class History implements Serializable {

    private List<Pair> history = new ArrayList<Pair>();
    private int virtualSize = 0;

    public void push(Command c, Memento m) {
        System.out.println("Push : " + c.getName());
        if (virtualSize != history.size() && virtualSize > 0) {
            history = history.subList(0, virtualSize - 1);
        }
        history.add(new Pair(c, m));
        virtualSize = history.size();
    }

    public boolean undo() throws IOException {
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
        virtualSize = Math.max(0, virtualSize - 2);
        System.out.println("Undo : " + history.get(virtualSize).command.getName());
        return history.get(virtualSize).memento;
    }

    public boolean redo() throws IOException {
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
        virtualSize = Math.min(history.size(), virtualSize + 2);
        System.out.println("Redo : " + history.get(virtualSize - 1).command.getName());
        return history.get(virtualSize - 1).memento;
    }
}
