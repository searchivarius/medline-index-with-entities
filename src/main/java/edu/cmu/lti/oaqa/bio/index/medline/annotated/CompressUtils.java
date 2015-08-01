/*
 *  Copyright 2015 Carnegie Mellon University
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package edu.cmu.lti.oaqa.bio.index.medline.annotated;

import java.io.*;
import java.util.zip.*;

import org.apache.tools.bzip2.CBZip2InputStream;

/**
 *   Creates an input/output stream for a potentially compressed file;
 *   Determines a compression format by file extension.
 *   <p>
 *   Supports the following formats:
 *   </p>
 *   <ul>
 *   <li>For reading: .gz and bz2
 *   <li>For writing: only .gz
 *   </ul>
 *
 */
public class CompressUtils {
  /**
   * Creates an input stream to read from a regular or compressed file.
   * 
   * @param fileName a file name with an extension (.gz or .bz2) or without it;
   *                   if the user specifies an extension .gz or .bz2,
   *                   we assume that the input
   *                   file is compressed.
   * @return an input stream to read from the file. 
   * @throws IOException
   */
  public static InputStream createInputStream(String fileName) throws IOException {
    InputStream finp = new FileInputStream(fileName);
    if (fileName.endsWith(".gz")) return new GZIPInputStream(finp);
    if (fileName.endsWith(".bz2")) {
      finp.read(new byte[2]); // skip the mark

      return new CBZip2InputStream(finp);
    }
    return finp;
  }
  
  /**
   * Creates an output stream to write to a regular or compressed file.
   * 
   * @param fileName    a file name with an extension .gz or without it;
   *                    if the user specifies an extension .gz, we assume
   *                    that the output file should be compressed.
   * @return an output stream to write to a file.
   * @throws IOException
   */
  public static OutputStream createOutputStream(String fileName) throws IOException {
    OutputStream foutp = new FileOutputStream(fileName);
    if (fileName.endsWith(".gz")) return new GZIPOutputStream(foutp);
    if (fileName.endsWith(".bz2")) {
      throw new IOException("bz2 is not supported for writing");      
    }
    return foutp;
  }
}
