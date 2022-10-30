package ru.ac.uniyar.domain.storage

import java.time.LocalDate
import java.util.UUID

class FetchEquipmentQuery(private val equipmentRepository: EquipmentRepository){
    operator fun invoke(id: UUID): Equipment? = equipmentRepository.fetch(id)
}

class AddQuery(
        private val equipmentRepository: EquipmentRepository) {
    operator fun invoke(name: String?, productId: String?, description: String?, submissionDate: LocalDate?): UUID {
        return equipmentRepository.add(
                Equipment(EMPTY_UUID, name!!, productId!!, description!!, submissionDate!!)
        )
    }
}

class EditEquipmentQuery(
        private val equipmentRepository: EquipmentRepository
){
    operator fun invoke(
            id: UUID,
            name: String?,
            productId: String?,
            description: String?,
            submissionDate: LocalDate?,
            ){
        equipmentRepository.edit(
                id, name!!, productId!!, description!!, submissionDate!!
        )
    }
}