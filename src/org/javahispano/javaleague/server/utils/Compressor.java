package org.javahispano.javaleague.server.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Compressor {
	public static byte[] compress(byte[] b, String name) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		ZipEntry entry = new ZipEntry(name);
		entry.setSize(b.length);
		zos.putNextEntry(entry);
		zos.write(b);
		zos.closeEntry();
		zos.close();

		return baos.toByteArray();
	}

	public static byte[] decompress(byte[] b) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            ZipInputStream zis = new ZipInputStream(bais);
            ZipEntry entry;
            ByteArrayOutputStream baos =
                    new ByteArrayOutputStream();
 
            //
            // Read each entry from the ZipInputStream until no
            // more entry found indicated by a null return value
            // of the getNextEntry() method.
            //
            while ((entry = zis.getNextEntry()) != null) {
                //System.out.println("Unzipping: " + entry.getName());
 
                int size;
                byte[] buffer = new byte[2048];
 
                BufferedOutputStream bos =
                        new BufferedOutputStream(baos, buffer.length);
 
                while ((size = zis.read(buffer, 0, buffer.length)) != -1) {
                    bos.write(buffer, 0, size);
                }
                bos.flush();
                bos.close();
            }
 
            zis.close();
            bais.close();
            
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            
            return null;
        }
    }
}
