/*
 * Copyright 2004 - 2012 Mirko Nasato and contributors
 *           2016 - 2017 Simon Braconnier and contributors
 *
 * This file is part of JODConverter - Java OpenDocument Converter.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jodconverter.filter;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.jodconverter.BaseOfficeITest;
import org.jodconverter.filter.text.TextReplacerFilter;

public class TextReplacerFilterITest extends BaseOfficeITest {

  private static final String SOURCE_FILE = DOCUMENTS_DIR + "test_replace.doc";
  private static final String OUTPUT_DIR =
      TEST_OUTPUT_DIR + TextReplacerFilterITest.class.getSimpleName() + "/";

  /** Ensures we start with a fresh output directory. */
  @BeforeClass
  public static void createOutputDir() {

    // Ensure we start with a fresh output directory
    final File outputDir = new File(OUTPUT_DIR);
    FileUtils.deleteQuietly(outputDir);
    outputDir.mkdirs();
  }

  /**  Deletes the output directory. */
  @AfterClass
  public static void deleteOutputDir() {

    // Delete the output directory
    FileUtils.deleteQuietly(new File(OUTPUT_DIR));
  }

  /**
   * Test that the creation of a TextReplacerFilter with a search list and replacement list of
   * different size throws a IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void create_WithArgumentsSizeNotEqual_ThrowsIllegalArgumentException() {

    new TextReplacerFilter(
        new String[] {"SEARCH_STRING", "ANOTHER_SEARCH_STRING"},
        new String[] {"REPLACEMENT_STRING"});
  }

  /**
   * Test that the creation of a TextReplacerFilter with an empty replacement list throws a
   * IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void create_WithEmptyReplacementList_ThrowsIllegalArgumentException() {

    new TextReplacerFilter(new String[] {"SEARCH_STRING"}, new String[0]);
  }

  /**
   * Test that the creation of a TextReplacerFilter with an empty search list throws a
   * IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void create_WithEmptySearchList_ThrowsIllegalArgumentException() {

    new TextReplacerFilter(new String[0], new String[] {"REPLACEMENT_STRING"});
  }

  /**
   * Test that the creation of a TextReplacerFilter with a null replacement list throws a
   * NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void create_WithNullReplacementList_ThrowsNullPointerException() {

    new TextReplacerFilter(new String[] {"SEARCH_STRING"}, null);
  }

  /**
   * Test that the creation of a TextReplacerFilter with a null search list throws a
   * NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void create_WithNullSearchList_ThrowsNullPointerException() {

    new TextReplacerFilter(null, new String[] {"REPLACEMENT_STRING"});
  }

  /**
   * Test the conversion of a document replacing text along the way.
   *
   * @throws Exception if an error occurs.
   */
  @Test
  public void doFilter_WithDefaultProperties() throws Exception {

    final File sourceFile = new File(SOURCE_FILE);
    final File testOutputDir = new File(OUTPUT_DIR);

    // Create the GraphicInserterFilter to test.
    final TextReplacerFilter filter =
        new TextReplacerFilter(
            new String[] {"SEARCH_WORD", "that", "have", "new common language will be more simple"},
            new String[] {
              "REPLACEMENT_STRING",
              "REPLACEMENT_THAT",
              "REPLACEMENT_HAVE",
              "most recent common language will be more basic"
            });

    // Test the filter
    convertFileToPdf(sourceFile, testOutputDir, "test", filter, RefreshFilter.REFRESH);
  }
}
