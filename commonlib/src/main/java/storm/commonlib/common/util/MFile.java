package storm.commonlib.common.util;

import java.io.File;
import java.net.URI;

/**
 * Created by gdq on 16/1/25.
 */
public class MFile extends File {

    public String filename;
    public int size;
    public String content_type;
    public String file_id;


    public MFile(File dir, String name) {
        super(dir, name);
    }

    public MFile(String path) {
        super(path);
    }

    public MFile(String dirPath, String name) {
        super(dirPath, name);
    }

    public MFile(URI uri) {
        super(uri);
    }
}
