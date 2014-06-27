package com.pengyifan.commons.io;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.Validate;

/**
 * 
 * If the path is a filtered file, then process the file. If the path is a
 * directory, then process all filtered files. Otherwise throw Exception
 * 
 * @author Yifan Peng
 * 
 */
public abstract class BatchProcessor {

  protected String               path;
  protected File                 dir;
  protected List<String>         basenames;
  protected boolean              debug;

  public BatchProcessor(File path, FileFilter filter) {
    this(path.toString(), filter);
  }

  public BatchProcessor(String path, FileFilter filter) {
    this.path = path;
    debug = false;

    dir = new File(path);
    if (!dir.exists()) {
      throw new IllegalArgumentException("Cannot find file/dir: " + dir);
    } else if (dir.isFile()) {
      // check if it is a filtered file
      Validate.isTrue(filter.accept(dir), "Not a %s file: %s", filter, dir);
      String basename = FilenameUtils.getBaseName(dir.getAbsolutePath());
      basenames = Collections.singletonList(basename);
      dir = new File(FilenameUtils.getFullPath(dir.getAbsolutePath()));
    } else {
      // filter
      File[] files = dir.listFiles(filter);
      Arrays.sort(files);
      basenames = new ArrayList<String>();
      for (File f : files) {
        basenames.add(FilenameUtils.getBaseName(f.getAbsolutePath()));
      }
    }
  }

  public final void process()
      throws IOException {
    preprocess();
    for (String basename : basenames) {
      readResource(basename);
      preprocessFile(basename);
      processFile(basename);
      postprocessFile(basename);
    }
    postprocess();
  }

  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  /**
   * Pre-process and initiates the given directory.
   */
  protected void preprocess()
      throws IOException {

  }

  /**
   * Post-processes and completes the given directory.
   */
  protected void postprocess()
      throws IOException {

  }

  protected void processFile(String basename)
      throws IOException {

  }

  /**
   * read resources according to the basename
   * 
   * @deprecated replaced by {@link #preprocessFile(String)}.
   * @param basename
   * @throws IOException
   */
  @Deprecated
  protected void readResource(String basename)
      throws IOException {
    //
  }

  protected void preprocessFile(String basename)
      throws IOException {

  }

  protected void postprocessFile(String basename)
      throws IOException {

  }
}
