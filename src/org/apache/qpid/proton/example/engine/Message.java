package org.apache.qpid.proton.example.engine;


/**
 * Message
 *
 */

public class Message
{

    private final byte[] bytes;

    public Message(byte[] bytes) {
        this.bytes = bytes;
    }

    public Message(String string) {
        this.bytes = string.getBytes();
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String toString() {
        return new String(bytes);
    }

}
