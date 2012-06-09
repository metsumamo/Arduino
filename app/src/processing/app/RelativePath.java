/*
 * by Shigeru KANEMOTO at SWITCHSCIENCE.
 */

package processing.app;

import java.io.File;
import java.io.IOException;

class RelativePath {
  public static String relativePath(String origin, String target) {
    try {
      origin = (new File(origin)).getCanonicalPath();
      target = (new File(target)).getCanonicalPath();
    }
    catch (IOException e) {
      return null;
    }

    if (origin.equals(target)) {
      // origin and target is identical.
      return ".";
    }

    if (origin.equals(File.separator)) {
      // origin is root.
      return "." + target;
    }

    if (System.getProperty("os.name").indexOf("Windows") != -1) {
      if (origin.startsWith("\\\\") || target.startsWith("\\\\")) {
	// Windows UNC path not supported.
	return null;
      }

      char originLetter = origin.charAt(0);
      char targetLetter = target.charAt(0);
      if (Character.isLetter(originLetter) && Character.isLetter(targetLetter)) {
	// Windows only
	if (originLetter != targetLetter) {
	  // Drive letters differ
	  return null;
	}
      }
    }

    String relative = "";
    while (!target.startsWith(origin + File.separator)) {
      origin = (new File(origin)).getParent();
      if (origin.equals(File.separator))
	origin = "";
      relative += "..";
      relative += File.separator;
    }

    return relative + target.substring(origin.length() + 1);
  }
}
