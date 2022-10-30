package ru.ac.uniyar.web.handlers

import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.FormField
import org.http4k.lens.Validator
import org.http4k.lens.localDate
import org.http4k.lens.nonEmptyString
import org.http4k.lens.webForm
import ru.ac.uniyar.domain.storage.AddQuery
import ru.ac.uniyar.web.models.ShowEquipmentFormVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class AddingEquipmentHandler (
        private val addNewEquipmentQuery: AddQuery,
        private val htmlView: ContextAwareViewRender,
) : HttpHandler {
    companion object {
        private val nameFormLens = FormField.nonEmptyString().required("name")
        private val productIdFormLens = FormField.nonEmptyString().required("productId")
        private val descriptionFormLens = FormField.nonEmptyString().required("description")
        private val submissionDateFormLens = FormField.localDate().required("submissionDate")
                private val equipmentFormLens = Body.webForm(
                Validator.Feedback,
                nameFormLens,
                productIdFormLens,
                descriptionFormLens,
                submissionDateFormLens
        ).toLens()
    }

    override fun invoke(request: Request): Response {
        var toReturn: Response? = null
        var webForm = equipmentFormLens(request)
        if (webForm.errors.isEmpty()){
                val a = addNewEquipmentQuery.invoke(
                        nameFormLens(webForm),
                        productIdFormLens(webForm),
                        descriptionFormLens(webForm),
                        submissionDateFormLens(webForm)
                )
                toReturn = Response(Status.FOUND).header("Location", "/equipment/"+a.toString())
        }

        return toReturn ?: Response(Status.OK).with(htmlView(request) of ShowEquipmentFormVM(webForm))
    }
}