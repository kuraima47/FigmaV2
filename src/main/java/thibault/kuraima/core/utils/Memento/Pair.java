package thibault.kuraima.core.utils.Memento;

import thibault.kuraima.core.utils.Command.Command;

public class Pair {
    public Command command;
    public Memento memento;
    Pair(Command c, Memento m) {
        command = c;
        memento = m;
    }
}
