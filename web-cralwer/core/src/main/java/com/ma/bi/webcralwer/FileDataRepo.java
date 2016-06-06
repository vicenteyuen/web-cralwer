/**
 * 
 */
package com.ma.bi.webcralwer;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author Vicente Yuen
 *
 */
public class FileDataRepo implements DataRepo {

	private File dataFile;

	public FileDataRepo(File dataFile) {
		this.dataFile = dataFile;

	}

	@Override
	public void open() {
		// TODO Auto-generated method stub

	}

	@Override
	public void commit(ResultCallback callbacks) {
		// TODO Auto-generated method stub

		try {

			if (!Files.exists(dataFile.toPath())) {
				Files.createFile(dataFile.toPath());
			}

			AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(
					dataFile.toPath(), StandardOpenOption.WRITE);
			
			long position = dataFile.length();
			ByteBuffer buffer = ByteBuffer.allocate(4096);
			// --- convert to handle ---
			StringBuilder lineContent = null;
			Iterator<String[]> contentIter = lines.iterator();
			while (contentIter.hasNext()) {
				String[] content = contentIter.next();
				lineContent = new StringBuilder();
				
				for (String colField : content) {
					if (lineContent.length() > 0) {
						lineContent.append(",");
					}
					lineContent.append( colField );
				}
				
				if (position > 0) {
					lineContent.insert(0, "\r\n");
				}
			}
			
			
			
			buffer.put(lineContent.toString().getBytes());
			buffer.flip();
			
			
			

			fileChannel.write(buffer, position, buffer,
					new CompletionHandler<Integer, ByteBuffer>() {

						@Override
						public void completed(Integer result,
								ByteBuffer attachment) {
							
							if (null != callbacks) {
								callbacks.completed();
							}
							
							System.out.println("bytes written: " + result);
						}

						@Override
						public void failed(Throwable exc, ByteBuffer attachment) {
							
							if (null != callbacks) {
								callbacks.fail();
							}							
							System.out.println("Write failed");
							exc.printStackTrace();
						}
					});
			
			fileChannel.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	Collection<String[]> lines = new Vector<String[]>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ma.bi.webcralwer.DataRepo#addOne(java.lang.String[])
	 */
	@Override
	public void addOne(String... record) {
		// TODO Auto-generated method stub

		lines.add(record);

	}

}
