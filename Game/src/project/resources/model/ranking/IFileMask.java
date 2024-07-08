package project.resources.model.ranking;

import java.io.File;

// interfaccia per la gestione del file

public interface IFileMask {
    public void writeInFile(final String result);

    public void sortFile();

    public File getFile();
}
