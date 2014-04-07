package org.javahispano.javaleague.server.classloader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;

/**
 * Classloader that fetches its class definitions from zip files stored in the
 * GAE DataStore.
 * 
 * @author bruce (modified by adou)
 */
public class MyDataStoreClassLoader extends ClassLoader {
	/**
	 * Cached copy of the byte streams of every class in the zip files. Not
	 * efficient, but good for demonstration purposes
	 */
	private Map<String, byte[]> byteStreams = new HashMap<String, byte[]>();

	Logger logger = Logger.getLogger(MyDataStoreClassLoader.class.getName());

	/**
	 * 
	 * @param parent
	 *            Parent class loader.
	 * @param keys
	 *            varargs array of the Blob keys that specify the zips that will
	 *            be scanned.
	 * @throws EntityNotFoundException
	 * @throws IOException
	 */
	public MyDataStoreClassLoader(ClassLoader parent)
			throws EntityNotFoundException, IOException {
		super(parent);
	}

	/**
	 * Provides the ability to add another ZIP in after the classloader has been
	 * constructed. This may be useful if you have a core WAR looking thing, but
	 * want to add additional JARs based upon configuration etc...
	 * 
	 * @param key
	 * @throws IOException
	 * @throws EntityNotFoundException
	 */
	public void addClassJar(ZipInputStream zin) throws IOException {
		ZipScanner.scan(zin, new ZipEntryHandler() {
			@Override
			public void readZipEntry(ZipEntry entry, ZipInputStream in)
					throws IOException {
				String name = entry.getName();
				if (byteStreams.containsKey(name)) {
					logger.warning("duplicate defintion of class/resource "
							+ name + ". It will be ignored");
				} else {
					addClass(name, ZipScanner.readZipBytes(entry, in));
				}
			}
		});
	}

	public Map<String, byte[]> addClassJar(BlobKey key) throws IOException {
		final Map<String, byte[]> tempByteStreams = new HashMap<String, byte[]>();
		InputStream in = new ByteArrayInputStream(readBlobFully(key));
		ZipInputStream zin = new ZipInputStream(in);
		ZipScanner.scan(zin, new ZipEntryHandler() {
			@Override
			public void readZipEntry(ZipEntry entry, ZipInputStream in)
					throws IOException {
				String name = entry.getName();
				if (name.contains(".class")) {
					name = name.substring(0, name.indexOf(".class"))
							.replaceAll("/", ".");
					//logger.warning("addClassJar :: class name: " + name);
					if (tempByteStreams.containsKey(name)) {
						logger.warning("duplicate defintion of class/resource "
								+ name + ". It will be ignored");
					} else {
						tempByteStreams.put(name,
								ZipScanner.readZipBytes(entry, in));
						// addClass(name, ZipScanner.readZipBytes(entry, in));
					}
				}
			}

		});

		return tempByteStreams;
	}

	public Map<String, byte[]> addClassJar(byte[] bytes) throws IOException {
		final Map<String, byte[]> tempByteStreams = new HashMap<String, byte[]>();
		InputStream in = new ByteArrayInputStream(bytes);
		ZipInputStream zin = new ZipInputStream(in);
		ZipScanner.scan(zin, new ZipEntryHandler() {
			@Override
			public void readZipEntry(ZipEntry entry, ZipInputStream in)
					throws IOException {
				String name = entry.getName();
				if (name.contains(".class")) {
					name = name.substring(0, name.indexOf(".class"))
							.replaceAll("/", ".");
					//logger.warning("addClassJar :: class name: " + name);
					if (tempByteStreams.containsKey(name)) {
						logger.warning("duplicate defintion of class/resource "
								+ name + ". It will be ignored");
					} else {
						tempByteStreams.put(name,
								ZipScanner.readZipBytes(entry, in));
						// addClass(name, ZipScanner.readZipBytes(entry, in));
					}
				}
			}

		});

		return tempByteStreams;
	}


	public void addClassJarFramework(BlobKey key) throws IOException {
		InputStream in = new ByteArrayInputStream(readBlobFully(key));
		ZipInputStream zin = new ZipInputStream(in);
		ZipScanner.scan(zin, new ZipEntryHandler() {
			@Override
			public void readZipEntry(ZipEntry entry, ZipInputStream in)
					throws IOException {
				String name = entry.getName();
				if (name.contains(".class")) {
					name = name.substring(0, name.indexOf(".class"))
							.replaceAll("/", ".");
					/*logger.warning("addClassJarFramework :: class name: "
							+ name);*/
					if (byteStreams.containsKey(name)) {
						logger.warning("duplicate defintion of class/resource "
								+ name + ". It will be ignored");
					} else {
						byteStreams.put(name,
								ZipScanner.readZipBytes(entry, in));
						// addClass(name, ZipScanner.readZipBytes(entry, in));
					}
				}
			}

		});
	}

	public void addClassBlob(String name, BlobKey key) throws IOException {
		if (byteStreams.containsKey(name)) {
			logger.warning("duplicate defintion of class/resource " + name
					+ ". It will be ignored");
		} else {
			addClass(name, readBlobFully(key));
		}
	}

	/**
	 * Adds an additional class into the byteStream cache directly. Used by
	 * AgentLoader to include classes in the WARish thing (hence why it is
	 * package protected)
	 * 
	 * @param className
	 *            The file name of the class to add with slashes (/) instead of
	 *            periods, and .class on the end
	 * @param data
	 *            The bytes that represent the class.
	 * @throws IOException
	 */
	public void addClass(String className, byte[] data) throws IOException {
		if (byteStreams.containsKey(className)) {
			logger.warning("duplicate defintion of class/resource " + className
					+ ". It will be ignored");
		} else {
			byteStreams.put(className, data);
		}

	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		if (name == null) {
			throw new NullPointerException();
		}

		// Since all support classes of loaded class use same class loader
		// must check subclass cache of classes for things like Object

		// Class loaded yet?
		Class<?> c = findLoadedClass(name);
		if (c == null) {
			try {
				c = getParent().loadClass(name);
			} catch (ClassNotFoundException ex) {
				// Load class data from file and save in byte array
				byte data[] = byteStreams.get(name);

				if (data == null)
					throw new ClassNotFoundException(name);

				// Convert byte array to Class
				c = defineClass(name, data, 0, data.length);

				// If failed, throw exception
				if (c == null)
					throw new ClassNotFoundException(name);
			}

		}

		// Resolve class definition if approrpriate
		if (resolve)
			resolveClass(c);

		// Return class just created

		return c;
	}

	@Override
	public InputStream getResourceAsStream(String name) {
		byte data[] = byteStreams.get(name);
		if (data == null)
			return null;
		return new ByteArrayInputStream(data);
	}

	public static byte[] readBlobFully(BlobKey blobKey) {

		BlobstoreService blobstoreService = BlobstoreServiceFactory
				.getBlobstoreService();
		BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobKey);

		if (blobInfo == null)
			return null;

		if (blobInfo.getSize() > Integer.MAX_VALUE)
			throw new RuntimeException(
					"This method can only process blobs up to "
							+ Integer.MAX_VALUE + " bytes");

		int blobSize = (int) blobInfo.getSize();
		int chunks = (int) Math
				.ceil(((double) blobSize / BlobstoreService.MAX_BLOB_FETCH_SIZE));
		int totalBytesRead = 0;
		int startPointer = 0;
		int endPointer;
		byte[] blobBytes = new byte[blobSize];

		for (int i = 0; i < chunks; i++) {

			endPointer = Math.min(blobSize - 1, startPointer
					+ BlobstoreService.MAX_BLOB_FETCH_SIZE - 1);

			byte[] theBytes = blobstoreService.fetchData(blobKey, startPointer,
					endPointer);

			for (int j = 0; j < theBytes.length; j++)
				blobBytes[j + totalBytesRead] = theBytes[j];

			startPointer = endPointer + 1;
			totalBytesRead += theBytes.length;
		}

		return blobBytes;
	}

	public Map<String, byte[]> getByteStreams() {
		return byteStreams;
	}

}
