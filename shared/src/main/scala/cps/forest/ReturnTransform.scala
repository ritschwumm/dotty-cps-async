package cps.forest

import scala.quoted._

import cps._
import cps.misc._


class ReturnTransform[F[_]:Type,T:Type](cpsCtx: TransformationContext[F,T]):

  import cpsCtx._

  def run(using Quotes)(returnTerm: quotes.reflect.Return, from: quotes.reflect.Symbol): CpsExpr[F,T] =
      throw MacroError("return inside asyn block is not supported",cpsCtx.patternCode)



  
