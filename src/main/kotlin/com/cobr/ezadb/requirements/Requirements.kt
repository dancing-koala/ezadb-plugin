package com.cobr.ezadb.requirements

import com.github.michaelbull.result.BindingScope
import com.github.michaelbull.result.Result

typealias RequirementsScope = BindingScope<RequirementError>
typealias RequirementFun = BindingScope<RequirementError>.() -> Unit

interface Requirements {
    fun check(): Result<Unit, RequirementError>
}
