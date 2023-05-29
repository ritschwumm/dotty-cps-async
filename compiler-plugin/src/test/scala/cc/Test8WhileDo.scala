package cc

import org.junit.Test

class Test8WhileDo {

  // sync condition + sync body
  @Test
  def testCompileAndRunWhileDom1(): Unit = {
      DotcInvocations.compileAndRunFilesInDirAndCheckResult(
        "testdata/set8WhileDo/m1",
        "cpstest.Test8m1",
        "myurl:3\n"
      )
  }

  /*
  // sync condition + async body
  @Test
  def testCompileAndRunWhileDom2(): Unit = {
    val dotcInvocations = new DotcInvocations()
    try {
      val (code, output) = dotcInvocations.compileAndRunFilesInDir(
        "testdata/set8WhileDo/m2",
        "testdata/set8WhileDo/m2",
        "cpstest.Test8m2"
      )
    } catch {
      case ex: cps.plugin.CpsTransformException =>
        val reporter = dotcInvocations.reporter
        println("summary: " + reporter.summary)

        assert(reporter.allErrors.nonEmpty, "There should be errors")
      case _ => assert(false, "Expected a CpsTransformException error")
    }
  }

  // async condition + sync body
  @Test
  def testCompileAndRunWhileDom3(): Unit = {
    val dotcInvocations = new DotcInvocations()
    try {
      val (code, output) = dotcInvocations.compileAndRunFilesInDir(
        "testdata/set8WhileDo/m3",
        "testdata/set8WhileDo/m3",
        "cpstest.Test8m3"
      )
    } catch {
      case ex: cps.plugin.CpsTransformException =>
        val reporter = dotcInvocations.reporter
        println("summary: " + reporter.summary)

        assert(reporter.allErrors.nonEmpty, "There should be errors")
      case _ => assert(false, "Expected a CpsTransformException error")
    }
  }

  // async condition + async body
  @Test
  def testCompileAndRunWhileDom4(): Unit = {
    val dotcInvocations = new DotcInvocations()
    try {
      val (code, output) = dotcInvocations.compileAndRunFilesInDir(
        "testdata/set8WhileDo/m4",
        "testdata/set8WhileDo/m4",
        "cpstest.Test8m4"
      )
    } catch {
      case ex: cps.plugin.CpsTransformException =>
        val reporter = dotcInvocations.reporter
        println("summary: " + reporter.summary)

        assert(reporter.allErrors.nonEmpty, "There should be errors")
      case _ => assert(false, "Expected a CpsTransformException error")
    }
  }
  */

}
