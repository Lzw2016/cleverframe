package org.activiti.explorer;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/12/3 22:20 <br/>
 */
@SuppressWarnings("ALL")
public class FilterServletOutputStream extends ServletOutputStream {

    private DataOutputStream stream;

    private WriteListener writeListener;

    public FilterServletOutputStream(OutputStream output) {
        stream = new DataOutputStream(output);
    }

    public void write(int b) throws IOException {
        stream.write(b);
    }

    public void write(byte[] b) throws IOException {
        stream.write(b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        stream.write(b, off, len);
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        this.writeListener = writeListener;
    }

    @Override
    public boolean isReady() {
        return true;
    }
}