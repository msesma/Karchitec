package com.paradigmadigital.karchitect.platform


fun <E : Any, T : Collection<E>> T?.isNullOrEmpty(): Boolean {
    return this == null || this.isEmpty()
}


