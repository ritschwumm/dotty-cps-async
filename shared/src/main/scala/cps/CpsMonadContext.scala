package cps

import scala.compiletime.*
import scala.util.NotGiven



/**
 * Base for context operations inside monad
 **/
trait CpsMonadContext[F[_]] {

  type Monad[X] = F[X]

  type Direct = CpsDirect[F]


  /**
   *@return instance of cps-monad.
   */ 
   def monad: CpsMonad[F]
  
  /**
   * adopt external monadic value to the current context.
   **/
  //@deprecated("use wrapped monad operation instead", "0.17")
  //def adoptAwait[A](fa:F[A]):F[A]
 

}

trait CpsThrowMonadContext[F[_]] extends CpsMonadContext[F] {

  /**
   * @return instance of cps-monad which should supports throw operations.
   */
  override def monad: CpsThrowMonad[F]

}

trait CpsTryMonadContext[F[_]] extends CpsThrowMonadContext[F] {

  /**
   * @return instance of cps-monad which should supports try operations.
   */
  override def monad: CpsTryMonad[F]


}


object CpsMonadContext {

  given monadContext[F[_]](using direct:CpsDirect[F]): CpsMonadContext[F] = direct.context

}

/**
 * marker trait for context with NOOP intercaprAwait operation 
 **/
trait CpsMonadNoAdoptContext[F[_]] extends CpsMonadContext[F] {


  /**
   * If is it statically known, that monad is evaluated in this context, then
   * this call is completely eliminated by dotty-cps-async macro
   *@return fa
   **/
   //def adoptAwait[A](fa:F[A]):F[A] = fa

} 


class CpsPureMonadInstanceContextBody[F[_]](m: CpsPureMonadInstanceContext[F]) extends CpsMonadNoAdoptContext[F] {


   def monad: CpsMonad[F] = m

}


/**
 * Trait for minimal monad context, which provides an instance of CpsMonad.
 * Mixin this trait into your monad in cases, when you monad have no internal API and
 * not support try/catch operations.
 **/
trait CpsPureMonadInstanceContext[F[_]] extends CpsMonad[F] {


  type Context = CpsPureMonadInstanceContextBody[F]

  /**
  * run with this instance
  **/
  def apply[T](op: Context => F[T]): F[T] =
    op(CpsPureMonadInstanceContextBody(this))
  

   ///**
   //* If is it statically known, that monad is evaluated in this context, then
   //* this call is completely eliminated by dotty-cps-async macro
   //*@return fa
   //**/
   //def adoptAwait[A](fa:F[A]):F[A] = fa
    

}

class CpsThrowMonadInstanceContextBody[F[_]](val m: CpsThrowMonadInstanceContext[F]) extends CpsThrowMonadContext[F]  {

    override def monad: CpsThrowMonad[F] = m

}

trait CpsThrowMonadInstanceContext[F[_]] extends CpsThrowMonad[F] {

    override type Context = CpsThrowMonadInstanceContextBody[F]

    override def apply[T](op: Context => F[T]): F[T] =
      op(CpsThrowMonadInstanceContextBody(this))

}

class CpsTryMonadInstanceContextBody[F[_]](val m: CpsTryMonadInstanceContext[F]) extends CpsTryMonadContext[F]  {

    override def monad: CpsTryMonad[F] = m

}

trait CpsTryMonadInstanceContext[F[_]] extends CpsTryMonad[F] {

    override type Context = CpsTryMonadInstanceContextBody[F]

    override def apply[T](op: Context => F[T]): F[T] =
      op(CpsTryMonadInstanceContextBody(this))

}

/**
 * Base trait of CpsContextMonad which provide `Ctx` as a monad context 
 * Mixin this trait into your CosMonad in cases, when you monad have internal API
 * and you potentially want to use moand context as generic type.
 **/
trait CpsContextMonad[F[_],Ctx <: CpsMonadContext[F]]  extends CpsMonad[F] {

  type Context = Ctx

  /**
   * Evaluate operation in context. 
   **/
  def applyContext[T](op: Ctx =>F[T]): F[T]  

  /**
   * delegated to applyContext
   *@see applyContext
   **/
  def apply[T](op: Context => F[T]): F[T] =
    applyContext(c => op(c.asInstanceOf[Context]))
  
}

trait CpsTryContextMonad[F[_],Ctx <: CpsTryMonadContext[F]] extends CpsContextMonad[F, Ctx] with CpsTryMonad[F] {

  override type Context = Ctx

  override def apply[T](op: Context => F[T]): F[T] =
    applyContext(c => op(c.asInstanceOf[Context]))

}

trait CpsConcurrentContextMonad[F[_], Ctx <: CpsTryMonadContext[F]] extends CpsConcurrentMonad[F] with CpsTryContextMonad[F, Ctx] {

  type Context = Ctx

}


