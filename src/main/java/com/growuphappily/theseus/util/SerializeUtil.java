package com.growuphappily.theseus.util;

import java.io.*;

public class SerializeUtil {
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream in = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(in);
        out.writeObject(obj);
        return in.toByteArray();
    }
    public static Object unserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(bin);
        return in.readObject();
    }
}
