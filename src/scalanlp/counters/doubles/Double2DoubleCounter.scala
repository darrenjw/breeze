// THIS IS AN AUTO-GENERATED FILE. DO NOT MODIFY.    
// generated by GenCounter on Wed Oct 15 13:57:07 PDT 2008
package scalanlp.counters.doubles;

import scala.collection.mutable.Map;
import scala.collection.mutable.HashMap;

/**
 * Count objects of type Double with type Double.
 * This trait is a wraooer around Scala's Map trait
 * and can work with any scala Map. 
 *
 * @author dlwh
 */
@serializable 
trait Double2DoubleCounter extends DoubleCounter[Double] {


  abstract override def update(k : Double, v : Double) = {

    super.update(k,v);
  }

  // this isn't necessary, except that the jcl MapWrapper overrides put to call Java's put directly.
  override def put(k : Double, v : Double) :Option[Double] = { val old = get(k); update(k,v); old}

  abstract override def -=(key : Double) = {

    super.-=(key);
  }

  /**
   * Increments the count by the given parameter.
   */
   override  def incrementCount(t : Double, v : Double) = {
     update(t,(this(t) + v).asInstanceOf[Double]);
   }

  /**
   * Increments the count associated with Double by Double.
   * Note that this is different from the default Map behavior.
  */
  override def +=(kv: (Double,Double)) = incrementCount(kv._1,kv._2);

  override def default(k : Double) : Double = 0;

  override def apply(k : Double) : Double = super.apply(k);

  // TODO: clone doesn't seem to work. I think this is a JCL bug.
  override def clone(): Double2DoubleCounter  = super.clone().asInstanceOf[Double2DoubleCounter]

  /**
   * Return the Double with the largest count
   */
  override  def argmax() : Double = (elements reduceLeft ((p1:(Double,Double),p2:(Double,Double)) => if (p1._2 > p2._2) p1 else p2))._1

  /**
   * Return the Double with the smallest count
   */
  override  def argmin() : Double = (elements reduceLeft ((p1:(Double,Double),p2:(Double,Double)) => if (p1._2 < p2._2) p1 else p2))._1

  /**
   * Return the largest count
   */
  override  def max : Double = values reduceLeft ((p1:Double,p2:Double) => if (p1 > p2) p1 else p2)
  /**
   * Return the smallest count
   */
  override  def min : Double = values reduceLeft ((p1:Double,p2:Double) => if (p1 < p2) p1 else p2)

  // TODO: decide is this is the interface we want?
  /**
   * compares two objects by their counts
   */ 
  override  def comparator(a : Double, b :Double) = apply(a) compare apply(b);

  /**
   * Return a new Double2DoubleCounter with each Double divided by the total;
   */
  override  def normalized() : Double2DoubleCounter = {
    val normalized = new HashMap[Double,Double]() with Double2DoubleCounter;
    val total : Double = this.total
    if(total != 0.0)
      for (pair <- elements) {
        normalized.put(pair._1,pair._2 / total)
      }
    normalized
  }

  /**
   * Return the sum of the squares of the values
   */
  override  def l2norm() : Double = {
    var norm = 0.0
    for (val v <- values) {
      norm += (v * v)
    }
    return Math.sqrt(norm)
  }

  /**
   * Return a List the top k elements, along with their counts
   */
  override  def topK(k : Int) = Counters.topK[(Double,Double)](k,(x,y) => if(x._2 < y._2) -1 else if (x._2 == y._2) 0 else 1)(this);

  /**
   * Return \sum_(t) C1(t) * C2(t). 
   */
  def dot(that : Double2DoubleCounter) : Double = {
    var total = 0.0
    for (val (k,v) <- that.elements) {
      total += get(k).asInstanceOf[Double] * v
    }
    return total
  }

  def +=(that : Double2DoubleCounter) {
    for(val (k,v) <- that.elements) {
      update(k,(this(k) + v).asInstanceOf[Double]);
    }
  }

  def -=(that : Double2DoubleCounter) {
    for(val (k,v) <- that.elements) {
      update(k,(this(k) - v).asInstanceOf[Double]);
    }
  }

  override  def *=(scale : Double) {
    transform { (k,v) => (v * scale).asInstanceOf[Double]}
  }

  override  def /=(scale : Double) {
    transform { (k,v) => (v / scale).asInstanceOf[Double]}
  }
}


object Double2DoubleCounter {
  import it.unimi.dsi.fastutil.objects._
  import it.unimi.dsi.fastutil.ints._
  import it.unimi.dsi.fastutil.shorts._
  import it.unimi.dsi.fastutil.longs._
  import it.unimi.dsi.fastutil.floats._
  import it.unimi.dsi.fastutil.doubles._


  import scala.collection.jcl.MapWrapper;
  @serializable
  @SerialVersionUID(1L)
  class FastMapCounter extends MapWrapper[Double,Double] with Double2DoubleCounter {
    private val under = new Double2DoubleOpenHashMap;
    def underlying() = under.asInstanceOf[java.util.Map[Double,Double]];
    override def apply(x : Double) = under.get(x);
    override def update(x : Double, v : Double) {
      val oldV = this(x);
      updateTotal(v-oldV);
      under.put(x,v);
    }
  }

  def apply() = new FastMapCounter();

  
}

