package thibault.kuraima.core.utils.Memento;

import thibault.kuraima.core.applications.App;
import thibault.kuraima.core.awt.application.Singleton.AppContext;
import thibault.kuraima.core.utils.Command.BackupCommand;
import thibault.kuraima.core.utils.Command.RestoreCommand;

import java.io.IOException;
import java.io.Serializable;

public class Memento implements Serializable {

    private String _backup;

    public Memento() {
        BackupCommand backupCommand = new BackupCommand();
        backupCommand.execute();
        _backup = backupCommand.result();
    }

    public void restore() throws IOException {
        RestoreCommand restoreCommand = new RestoreCommand();
        restoreCommand.setParams(_backup, null, null);
        restoreCommand.execute();
    }
}



