package org.herac.tuxguitar.io.gpx.v7;

import org.herac.tuxguitar.io.base.TGFileFormat;
import org.herac.tuxguitar.io.base.TGFileFormatException;
import org.herac.tuxguitar.io.base.TGSongReader;
import org.herac.tuxguitar.io.base.TGSongReaderHandle;
import org.herac.tuxguitar.io.gpx.GPXDocumentParser;
import org.herac.tuxguitar.io.gpx.GPXDocumentReader;

public class GPXInputStream implements TGSongReader {
	
	public static final TGFileFormat FILE_FORMAT = new TGFileFormat("Guitar Pro 7", "application/x-gtp", new String[]{"gp"});
	
	public TGFileFormat getFileFormat() {
		return FILE_FORMAT;
	}
	
	public void read(TGSongReaderHandle handle) throws TGFileFormatException {
		try {
			GPXFileSystem gpxFileSystem = new GPXFileSystem();
			gpxFileSystem.load(handle.getInputStream());
			
			GPXDocumentReader gpxReader = new GPXDocumentReader(gpxFileSystem.getFileContentsAsStream(GPXFileSystem.RESOURCE_SCORE), GPXDocumentReader.GP7);
			GPXDocumentParser gpxParser = new GPXDocumentParser(handle.getFactory(), gpxReader.read());
			
			handle.setSong(gpxParser.parse());
		} catch (Throwable throwable) {
			throw new TGFileFormatException( throwable );
		}
	}
}
