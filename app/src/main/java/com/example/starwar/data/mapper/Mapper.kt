package com.example.starwar.data.mapper

import com.example.starwar.data.model.People
import com.example.starwar.domain.model.PeopleEntity
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


fun People.toPeopleEntity(next:Int):PeopleEntity{
    return PeopleEntity(
        birth_year = this.birth_year,
        created = convertStringToTimestamp(this.created),
        edited = convertStringToTimestamp(this.edited),
        gender = this.gender,
        hair_color = this.hair_color,
        height = this.height,
        homeworld = this.homeworld,
        mass = this.mass,
        name = this.name,
        skin_color = this.skin_color,
        films = this.films,
        url = this.url,
        nextKey = next
    )
}
fun mapToEntityList(next:Int,peopleList: List<People>): List<PeopleEntity> {
    return peopleList.map { it.toPeopleEntity(next)}
}
fun convertStringToTimestamp(dateString: String): Long {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'000'X", Locale.getDefault())
    val zonedDateTime = ZonedDateTime.parse(dateString, formatter)
    return zonedDateTime.toInstant().toEpochMilli()
}