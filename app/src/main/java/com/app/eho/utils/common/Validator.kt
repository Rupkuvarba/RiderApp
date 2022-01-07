package com.app.eho.utils.common

import com.app.eho.R
import java.util.regex.Pattern

object Validator {

    private val EMAIL_ADDRESS = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    private const val MIN_PASSWORD_LENGTH = 6

    fun validateLoginFields(email: String?, password: String?): List<Validation> =
        ArrayList<Validation>().apply {
            when {
                email.isNullOrBlank() ->
                    add(Validation(Validation.Field.EMAIL, Resource.error(R.string.email_field_empty)))
                !EMAIL_ADDRESS.matcher(email).matches() ->
                    add(Validation(Validation.Field.EMAIL, Resource.error(R.string.email_field_invalid)))
                else ->
                    add(Validation(Validation.Field.EMAIL, Resource.success()))
            }
            when {
                password.isNullOrBlank() ->
                    add(Validation(Validation.Field.PASSWORD, Resource.error(R.string.password_field_empty)))
                password.length < MIN_PASSWORD_LENGTH ->
                    add(Validation(Validation.Field.PASSWORD, Resource.error(R.string.password_field_small_length)))
                else -> add(Validation(Validation.Field.PASSWORD, Resource.success()))
            }
        }

    fun validateRegistrationFields(name : String?, email: String?, password: String?, emailConfirm: String?, passwordConfirm: String?, phone : String?): List<Validation> =
        ArrayList<Validation>().apply {
            when {
                name.isNullOrBlank() ->
                    add(Validation(Validation.Field.NAME, Resource.error(R.string.name_field_empty)))
                else ->
                    add(Validation(Validation.Field.NAME, Resource.success()))
            }
            when {
                email.isNullOrBlank() ->
                    add(Validation(Validation.Field.EMAIL, Resource.error(R.string.email_field_empty)))
                !EMAIL_ADDRESS.matcher(email).matches() ->
                    add(Validation(Validation.Field.EMAIL, Resource.error(R.string.email_field_invalid)))
                else ->
                    add(Validation(Validation.Field.EMAIL, Resource.success()))
            }
            when {
                emailConfirm.isNullOrBlank() ->
                    add(Validation(Validation.Field.EMAIL, Resource.error(R.string.email_field_empty)))
                !EMAIL_ADDRESS.matcher(emailConfirm).matches() ->
                    add(Validation(Validation.Field.EMAIL, Resource.error(R.string.email_field_invalid)))
                emailConfirm.equals(email, false) ->
                    add(Validation(Validation.Field.EMAIL, Resource.error(R.string.msg_mismatch_email)))
                else ->
                    add(Validation(Validation.Field.EMAIL, Resource.success()))
            }
            when {
                password.isNullOrBlank() ->
                    add(Validation(Validation.Field.PASSWORD, Resource.error(R.string.password_field_empty)))
                password.length < MIN_PASSWORD_LENGTH ->
                    add(Validation(Validation.Field.PASSWORD, Resource.error(R.string.password_field_small_length)))
                else -> add(Validation(Validation.Field.PASSWORD, Resource.success()))
            }
            when {
                passwordConfirm.isNullOrBlank() ->
                    add(Validation(Validation.Field.PASSWORD, Resource.error(R.string.password_field_empty)))
                passwordConfirm.length < MIN_PASSWORD_LENGTH ->
                    add(Validation(Validation.Field.PASSWORD, Resource.error(R.string.password_field_small_length)))
                passwordConfirm.equals(password, false) ->
                    add(Validation(Validation.Field.PASSWORD, Resource.error(R.string.msg_mismatch_password)))
                else -> add(Validation(Validation.Field.PASSWORD, Resource.success()))
            }
            when {
                phone.isNullOrBlank() ->
                    add(Validation(Validation.Field.PHONE, Resource.error(R.string.phone_field_empty)))
                else ->
                    add(Validation(Validation.Field.PHONE, Resource.success()))
            }
        }

    fun validateForgotPasswordFields(email: String?): List<Validation> =
        ArrayList<Validation>().apply {
            when {
                email.isNullOrBlank() ->
                    add(Validation(Validation.Field.EMAIL, Resource.error(R.string.email_field_empty)))
                !EMAIL_ADDRESS.matcher(email).matches() ->
                    add(Validation(Validation.Field.EMAIL, Resource.error(R.string.email_field_invalid)))
                else ->
                    add(Validation(Validation.Field.EMAIL, Resource.success()))
            }
        }
}

data class Validation(val field: Field, val resource: Resource<Int>) {

    enum class Field {
        EMAIL,
        PASSWORD,
        NAME,
        PHONE
    }
}
