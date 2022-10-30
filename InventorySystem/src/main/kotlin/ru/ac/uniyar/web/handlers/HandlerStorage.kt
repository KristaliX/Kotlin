package ru.ac.uniyar.web.handlers

import org.http4k.core.HttpHandler
import ru.ac.uniyar.domain.operations.OperationStorage
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class HandlerStorage(
    operationStorage: OperationStorage,
    htmlView: ContextAwareViewRender,
) {
    val showEmployeesHandler: HttpHandler = ShowEmployeesHandler(
        operationStorage.listEmployeesOperation,
        htmlView,
    )

    val showEmployeeHandler: HttpHandler = ShowEmployeeHandler(
        operationStorage.fetchEmployeeOperation,
        htmlView,
    )

    val showEquipmentHandler: HttpHandler = ShowEquipmentHandler(
        operationStorage.fetchEquipmentOperation,
        htmlView,
    )

    val showEquipmentListHandler: HttpHandler = ShowEquipmentListHandler(
        operationStorage.listEquipmentOperation,
        htmlView,
    )

    val showStartPageHandler: HttpHandler = ShowStartPageHandler(
        htmlView,
    )

    val showNewEquipmentFormHandler: HttpHandler = ShowEquipmentFormHandler(
            htmlView
    )

    val addNewEquipmentHandler: HttpHandler = AddingEquipmentHandler(
            operationStorage.addEquipmentQuery,
            htmlView,
    )

    val editEquipmentFormHandler = EditingEquipmentFormHandler(
            operationStorage.fetchEquipmentQuery,
            htmlView
    )

    val submitChangesInEquipmentHandler = ChangesEquipmentHandler(
            operationStorage.editEquipmentQuery,
            htmlView
    )
}
