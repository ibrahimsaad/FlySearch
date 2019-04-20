package io.github.mohamedisoliman.flysearch.presentation.mvi

import io.reactivex.Observable

/**
 *
 * Created by Mohamed Ibrahim on 4/20/19.
 */

interface MviIntent

interface MviAction

interface MviResult

interface MviViewState

interface MviView<I : MviIntent, in S : MviViewState> {
  /**
   * Unique [Observable] used by the [MviViewModel]
   * to listen to the [MviView].
   * All the [MviView]'s [MviIntent]s must go through this [Observable].
   */
  fun intents(): Observable<I>

  /**
   * Entry point for the [MviView] to render itself based on a [MviViewState].
   */
  fun render(state: S)
}

interface MviViewModel<I : MviIntent, S : MviViewState> {
  fun processIntents(intents: Observable<I>)

  fun states(): Observable<S>
}