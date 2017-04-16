package ru.lanwen.heisenbug.codewrite;

import javax.annotation.processing.Filer;
import java.util.Map;

/**
 * @author lanwen (Merkushev Kirill)
 */
public abstract class CodeWriter {
    protected Map<String, String> consts;

    public CodeWriter(Map<String, String> consts) {
        this.consts = consts;
    }

    public abstract void writeTo(Filer filer);
}
