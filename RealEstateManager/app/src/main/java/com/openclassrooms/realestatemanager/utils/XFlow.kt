package com.openclassrooms.realestatemanager.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

fun <T> Flow<T>.observe(owner: LifecycleOwner, action: suspend (T) -> Unit = {}) {
    owner.lifecycleScope.launchWhenStarted {
        collect {
            action(it)
        }
    }
}

fun <T1, T2, R> combineStateFlows(
    scope: CoroutineScope,
    a: StateFlow<T1>,
    b: StateFlow<T2>,
    transform: (a: T1, b: T2) -> R,
): StateFlow<R> = combine(a, b) { a, b ->
    transform(a, b)
}.asState(scope, transform(a.value, b.value))

fun <T1, T2, T3, R> combineStateFlows(
    scope: CoroutineScope,
    a: StateFlow<T1>,
    b: StateFlow<T2>,
    c: StateFlow<T3>,
    transform: (a: T1, b: T2, c: T3) -> R,
): StateFlow<R> = combine(a, b, c) { a, b, c ->
    transform(a, b, c)
}.asState(scope, transform(a.value, b.value, c.value))

fun <T1, T2, T3, T4, R> combineStateFlows(
    scope: CoroutineScope,
    a: StateFlow<T1>,
    b: StateFlow<T2>,
    c: StateFlow<T3>,
    d: StateFlow<T4>,
    transform: (a: T1, b: T2, c: T3, d: T4) -> R,
): StateFlow<R> = combine(a, b, c, d) { a, b, c, d ->
    transform(a, b, c, d)
}.asState(scope, transform(a.value, b.value, c.value, d.value))

fun <T1, T2, T3, T4, T5, R> combineStateFlows(
    scope: CoroutineScope,
    a: StateFlow<T1>,
    b: StateFlow<T2>,
    c: StateFlow<T3>,
    d: StateFlow<T4>,
    e: StateFlow<T5>,
    transform: (a: T1, b: T2, c: T3, d: T4, e: T5) -> R,
): StateFlow<R> = combine(a, b, c, d, e) { a, b, c, d, e ->
    transform(a, b, c, d, e)
}.asState(scope, transform(a.value, b.value, c.value, d.value, e.value))

fun <T> stateFlow(): MutableStateFlow<T?> = MutableStateFlow(null)
fun <T> stateFlowOf(value: T): MutableStateFlow<T> = MutableStateFlow(value)

fun <T, R> StateFlow<T>.mapState(scope: CoroutineScope, transform: (value: T) -> R): StateFlow<R> =
    mapLatest {
        transform(it)
    }.asState(scope, transform(value))

fun <T, R> StateFlow<T>.flatMapState(scope: CoroutineScope, transform: (value: T) -> StateFlow<R>): StateFlow<R> =
    flatMapLatest {
        transform(it)
    }.asState(scope, transform(value).value)

fun <T> StateFlow<T>.onEachState(scope: CoroutineScope, onEach: suspend (T) -> Unit): StateFlow<T> =
    onEach(onEach).asState(scope, value)

fun <R> Flow<R>.asState(scope: CoroutineScope, value: R): StateFlow<R> =
    when (this) {
        is StateFlow -> this
        else -> stateIn(scope, SharingStarted.Eagerly, value)
    }