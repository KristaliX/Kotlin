package ru.ac.uniyar.web.handlers

import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.FormField
import org.http4k.lens.LensFailure
import org.http4k.lens.Path
import org.http4k.lens.Validator
import org.http4k.lens.localDate
import org.http4k.lens.nonEmptyString
import org.http4k.lens.uuid
import org.http4k.lens.webForm
import ru.ac.uniyar.domain.storage.EditEquipmentQuery
import ru.ac.uniyar.web.models.EditEquipmentFormVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender
import java.util.UUID

class ChangesEquipmentHandler(
        private val editEquipmentQuery: EditEquipmentQuery,
        private val htmlView: ContextAwareViewRender,
) : HttpHandler {
    companion object {
        val idForm = Path.uuid().of("id")
        private val nameFormLens = FormField.nonEmptyString().required("name")
        private val productIdFormLens = FormField.nonEmptyString().required("productId")
        private val descriptionFormLens = FormField.nonEmptyString().required("description")
        private val submissionDateFormLens = FormField.localDate().required("submissionDate")
        private val equipmentFormLens = Body.webForm(
                Validator.Feedback,
                nameFormLens,
                productIdFormLens,
                descriptionFormLens,
                submissionDateFormLens,
        ).toLens()
    }

    override fun invoke(request: Request): Response {
        var toReturn: Response? = null
        var webForm = equipmentFormLens(request)

        val index = try {
            idForm(request)
        } catch (error: LensFailure) {
            toReturn = Response(Status.BAD_REQUEST).header("Error", error.toString())
        }

        if (webForm.errors.isEmpty()) {
            editEquipmentQuery.invoke(
                   index as UUID,
                   nameFormLens(webForm),
                   productIdFormLens(webForm),
                   descriptionFormLens(webForm),
                   submissionDateFormLens(webForm)
            )
            toReturn = Response(Status.FOUND).header("Location", "/equipment/$index")
        }
        return toReturn ?: Response(Status.OK).with(
                htmlView(request) of EditEquipmentFormVM(
                        webForm,
                        index as UUID
                )
        )
    }
}