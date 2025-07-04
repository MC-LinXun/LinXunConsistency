package org.LinXun.Coo;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LinXunGlobal {
    final Path FILERURL = Paths.get(
            "plugins",
            "LinXun"
    );

    public Path getPath(String... subPaths) {
        Path path = FILERURL;
        for (String subPath : subPaths) {
            path = path.resolve(subPath);
        }
        return path;
    }
}
