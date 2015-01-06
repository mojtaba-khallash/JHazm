package iust.ac.ir.nlp.jhazm.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * Appends output of print writer to a string builder.
 * Created by majid on 12/20/14.
 */
public class StringBuilderWriter extends PrintWriter {

    public StringBuilderWriter(final StringBuilder builder) {
        super(new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                builder.append(cbuf, off, len);
            }

            @Override
            public void flush() throws IOException {

            }

            @Override
            public void close() throws IOException {

            }
        });
    }
}
