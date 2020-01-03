package cps

import org.junit.{Test,Ignore}
import org.junit.Assert._

import scala.quoted._
import scala.util.Success


class TestBS1If

  @Test def tIfC1_000(): Unit = 
     val c = Async.transform[ComputationBound,Int]{
        if (true) 1 else 2
     }
     assert(c.run() == Success(1))

  @Test def tIfC1_001(): Unit = 
     val c = Async.transform[ComputationBound,Int]{
        if (true) 1 else await(T1.cbi(2))
     }
     assert(c.run() == Success(1))

  @Test def tIfC1_001f(): Unit = 
     val c = Async.transform[ComputationBound,Int]{
        if (false) 1 else await(T1.cbi(2))
     }
     assert(c.run() == Success(2))

  @Test def tIfC1_010(): Unit = 
     val c = Async.transform[ComputationBound,Int]{
        if (true) await(T1.cbi(1)) else 2
     }
     assert(c.run() == Success(1))

  @Test def tIfC1_011(): Unit = 
     val c = Async.transform[ComputationBound,Int]{
        if (true) await(T1.cbi(1)) else await(T1.cbi(2))
     }
     assert(c.run() == Success(1))


  @Test def tIfC1_100(): Unit = 
     val c = Async.transform[ComputationBound,Int]{
        if (await(T1.cbBool(true)))
            2 
        else 
            3
     }
     assert(c.run() == Success(2))

  @Test def tIfC1_100f(): Unit = 
     val c = Async.transform[ComputationBound,Int]{
        if (await(T1.cbBool(false)))
            2 
        else 
            3
     }
     assert(c.run() == Success(3))
  

  @Test def tIfC1_111(): Unit = 
     val c = Async.transform[ComputationBound,Int]{
        if (await(T1.cbBool(true)))
            await(T1.cbi(2))
        else 
            await(T1.cbi(3))
     }
     assert(c.run() == Success(2))


