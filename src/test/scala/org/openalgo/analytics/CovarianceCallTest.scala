package org.openalgo.analytics

import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfterEach, FunSuite}

import scala.collection.JavaConverters._
import scala.collection.mutable

class CovarianceCallTest extends FunSuite with BeforeAndAfterEach with MockFactory {
  val list1: mutable.Buffer[java.lang.Double] = mutable.Buffer()
  val list2: mutable.Buffer[java.lang.Double] = mutable.Buffer()


  override def beforeEach() {
    list1 += new java.lang.Double(1)
    list1 += new java.lang.Double(2)
    list1 += new java.lang.Double(3)
    list2 += new java.lang.Double(-1)
    list2 += new java.lang.Double(-2)
    list2 += new java.lang.Double(-3)
  }

  override def afterEach() {

  }

  test("testGetCovariance") {
    assert(CovarianceCall.getCovariance(list1.asJava, list1.asJava).round == 1)
    assert(CovarianceCall.getCovariance(list1.asJava, list2.asJava).round == -1)
  }

}
