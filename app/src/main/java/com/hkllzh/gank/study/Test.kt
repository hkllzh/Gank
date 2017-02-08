package com.hkllzh.gank.study

/**
 * Created by lizheng on 2017/2/7.
 */
fun String.hello(name: String): String {
    return "Welcome " + this + " and $name"
}

fun testGetString(text: String): String {
    return text.hello(text)
}

fun testDataClass(age: Int, name: String): String {
    val p = Person(age, name);
    return p.toString()
}

data class Person(val age: Int, val name: String)