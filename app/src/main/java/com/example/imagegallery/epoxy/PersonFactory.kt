package com.example.imagegallery.epoxy

import android.R.drawable
import kotlin.random.Random


object PersonFactory {
    private const val ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm"
    private val imageResourceList: List<Int> by lazy {
        val cls = drawable::class.java
        val fields = cls.declaredFields.toList()
        val resourceList = mutableListOf<Int>()
        fields.forEach {
            resourceList.add(it.getInt(cls))
        }
        resourceList
    }
    val random = Random
    private fun getImageResource() = getRandomImageResource()

    private fun getName() = getRandomName(random.nextInt(4, 6))

    private fun getAge() = getRandomAge()

    fun getPersonList(count: Int): List<Person> {
        val personList = mutableListOf<Person>()
        repeat(count) {
            personList.add(Person(getImageResource(), getName(), getAge()))
        }
        return personList
    }

    private fun getRandomName(count: Int): String {
        return StringBuilder(count).apply {
            repeat(count) {
                append(ALLOWED_CHARACTERS[random.nextInt(10, ALLOWED_CHARACTERS.length)])
            }
        }.toString()
    }

    private fun getRandomAge(): Int {
        val ageDigits = 2
        return Integer.parseInt(StringBuilder(ageDigits).apply {
            repeat(ageDigits) {
                append(ALLOWED_CHARACTERS[random.nextInt(10)])
            }
        }.toString())
    }

    private fun getRandomImageResource(): Int {
        return imageResourceList.get(random.nextInt(imageResourceList.size))
    }
}

