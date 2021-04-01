package com.letrix.miranime.data.mapper

interface DomainMapper <T, DomainModel>{

    fun mapFromEntity(model: T): DomainModel

    fun mapToEntity(domainModel: DomainModel): T
}