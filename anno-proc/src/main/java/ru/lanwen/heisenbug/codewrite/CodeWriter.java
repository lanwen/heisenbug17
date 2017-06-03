package ru.lanwen.heisenbug.codewrite;

import javax.annotation.processing.Filer;
import java.io.IOException;
import java.util.Map;

/**
 * @author lanwen (Merkushev Kirill)
 */
public abstract class CodeWriter {
    protected Map<String, String> consts;

    public CodeWriter(Map<String, String> consts) {
        this.consts = consts;
    }

    public void writeTo(Filer filer) {
        try {
            writeExceptionally(filer);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    protected abstract void writeExceptionally(Filer filer) throws IOException;
}
