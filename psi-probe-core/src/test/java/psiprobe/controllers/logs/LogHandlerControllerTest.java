/**
 * Licensed under the GPL License. You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE.
 */
package psiprobe.controllers.logs;

import com.codebox.bean.JavaBeanTester;

import org.junit.jupiter.api.Test;

/**
 * The Class LogHandlerControllerTest.
 */
class LogHandlerControllerTest {

  /**
   * Javabean tester change log level.
   */
  @Test
  void javabeanTesterChangeLogLevel() {
    JavaBeanTester.builder(ChangeLogLevelController.class)
        .skip("applicationContext", "supportedMethods").test();
  }

  /**
   * Javabean tester download log.
   */
  @Test
  void javabeanTesterDownloadLog() {
    JavaBeanTester.builder(DownloadLogController.class)
        .skip("applicationContext", "supportedMethods").test();
  }

  /**
   * Javabean tester follow.
   */
  @Test
  void javabeanTesterFollow() {
    JavaBeanTester.builder(FollowController.class).skip("applicationContext", "supportedMethods")
        .test();
  }

  /**
   * Javabean tester followed file info.
   */
  @Test
  void javabeanTesterFollowedFileInfo() {
    JavaBeanTester.builder(FollowedFileInfoController.class)
        .skip("applicationContext", "supportedMethods").test();
  }

  /**
   * Javabean tester setup follow.
   */
  @Test
  void javabeanTesterSetupFollow() {
    JavaBeanTester.builder(SetupFollowController.class)
        .skip("applicationContext", "supportedMethods").test();
  }

}
