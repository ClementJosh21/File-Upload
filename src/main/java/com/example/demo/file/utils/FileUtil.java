package com.example.demo.file.utils;

/*
 * $$HeadURL$$
 * $$Id$$
 *
 * CCopyright (c) 2015, P3Solutions . All Rights Reserved.
 * This code may not be used without the express written permission
 * of the copyright holder, P3Solutions.
 */

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;

@Component
public class FileUtil {
  public static File createFile(String path) {
    return new File(path);
  }

  /**
   * check if a directory exists
   *
   * @param fileDir the file path
   * @throws Exception an error has occured
   */
  public static boolean checkForDirectory(String fileDir) throws Exception {
    File f;
    // check for file existing
    f = new File(fileDir);
    return f.isDirectory();
  }

  /**
   * check if a file exists
   *
   * @param fileDir the file path
   * @throws Exception an error has occured
   */
  public static boolean checkForFile(String fileDir) throws Exception {
    File f;
    // check for file existing
    f = new File(fileDir);
    return f.isFile();
  }

  /**
   * Gets the File name from a String Path. Example: if 'C:\\Program Files\\Test.doc' is passed in,
   * 'Test.doc' is returned. Alsoe takes care of / slashes in the case of /Program Files/Test.doc'
   *
   * @param fullFileName The file name to strip the file name from
   * @return The file name
   */
  public static String getFileNameFromPath(String fullFileName) {
    File f = new File(fullFileName);
    String fileName = f.getName();
    return fileName;
  }

  /**
   * checks for and if does not exist - creates a directory
   *
   * @param fileDir the file path
   * @throws IOException an error has occurred
   */
  public static void checkCreateDirectory(String fileDir) throws Exception {
    System.out.println("CreateDirectory = " + fileDir);
    if (!checkForDirectory(fileDir)) createDir(fileDir);
  }

  /**
   * Create a directory based on parent path and name.
   *
   * @param dir File of parent directory.
   * @param name Name of new directory.
   * @return File
   * @throws IOException
   */
  public static File createDir(File dir, String name) throws IOException {
    return createDir(dir.getAbsolutePath() + File.separator + name);
  }

  /**
   * Create a directory based on dir String passed in
   *
   * @param dir File of parent directory.
   * @return File
   * @throws IOException
   */
  public static File createDir(String dir, String name) throws IOException {
    return createDir(dir + File.separator + name);
  }

  /**
   * Create a directory based on dir String passed in
   *
   * @param dir File of parent directory.
   * @return File
   * @throws IOException
   */
  public static synchronized File createDir(String dir) throws IOException {
    File tmpDir = new File(dir);
    if (!tmpDir.exists()) {
      if (!tmpDir.mkdirs()) {
        throw new IOException("Could not create temporary directory: " + tmpDir.getAbsolutePath());
      }
    } else {
      System.out.println("Not creating directory, " + dir + ", this directory already exists.");
    }
    return tmpDir;
  }

  /**
   * Copy a file to another dir
   *
   * @param fileToMove
   * @param destinationFilePath Name of new directory.
   */
  public static boolean moveFile(
      String fileToMove, String destinationFilePath, boolean haltIfFail) {
    // File (or directory) to be moved
    File file = new File(fileToMove);
    // Destination directory
    File dir = new File(destinationFilePath);
    // Move file to new directory
    boolean success = file.renameTo(new File(dir, file.getName()));
    if (!success) {
      System.err.println("The file " + fileToMove + " was not successfully moved");
      if (haltIfFail) System.exit(1);
    }
    return success;
  }

  /**
   * Delete the target directory and its contents.
   *
   * @param strTargetDir Target directory to be deleted.
   * @return <code>true</code> if all deletions successful, <code>false> otherwise
   */
  public static synchronized boolean deleteDirectory(String strTargetDir) {
    File fTargetDir = new File(strTargetDir);
    if (fTargetDir.exists() && fTargetDir.isDirectory()) {
      return deleteDirectory(fTargetDir);
    } else {
      return false;
    }
  }

  /**
   * Delete the target directory and its contents.
   *
   * @param dir Target directory to be deleted.
   * @return <code>true</code> if all deletions successful, <code>false> otherwise
   */
  public static boolean deleteDirectory(File dir) {
    if (dir == null) return true;
    if (dir.isDirectory()) {
      String[] children = dir.list();
      for (String element : children) {
        boolean success = deleteDirectory(new File(dir, element));
        if (!success) {
          System.err.println("Unable to delete file: " + new File(dir, element));
          return false;
        }
      }
    }
    // The directory is now empty so delete it
    return dir.delete();
  }

  /**
   * deleteFile
   *
   * @param filePath the file path
   * @throws Exception an error has occured
   */
  public static void deleteFile(String filePath) {
    File f;
    try {
      // check for file existing
      f = new File(filePath);
      if (f.isFile()) {
        f.delete();
      }
    } finally {
      f = null;
    }
  }

  /**
   * write file
   *
   * @param filePath the file path
   * @throws Exception an error has occured
   */
  public static void writeFile(String filePath, String txtToWrite) throws IOException {
    try (Writer out = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8")) {
      out.write(txtToWrite);
    }
  }

  public static void writeFileAppend(String filePath, String txtToWrite) throws IOException {
    try (Writer out = new OutputStreamWriter(new FileOutputStream(filePath, true), "UTF-8")) {
      out.write(txtToWrite);
    }
  }

  public File getFileByBytes(byte[] bytes, String path) {
    try {
      File file = new File(path);
      FileUtils.writeByteArrayToFile(file, bytes);
      return file;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public byte[] fileToByteArray(File file) throws IOException {
    return Files.readAllBytes(file.toPath());
  }
}
