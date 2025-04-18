package com.purkt.mindexpense.core.domain.expense.di

import com.purkt.mindexpense.core.data.expense.di.coreDataExpenseModule
import com.purkt.mindexpense.core.domain.expense.usecase.CreateExpenseUseCase
import com.purkt.mindexpense.core.domain.expense.usecase.CreateExpenseUseCaseImpl
import com.purkt.mindexpense.core.domain.expense.usecase.GetExpenseByIdUseCase
import com.purkt.mindexpense.core.domain.expense.usecase.GetExpenseByIdUseCaseImpl
import com.purkt.mindexpense.core.domain.expense.usecase.UpdateExpenseUseCase
import com.purkt.mindexpense.core.domain.expense.usecase.UpdateExpenseUseCaseImpl
import com.purkt.mindexpense.core.domain.expense.usecase.ValidateExpenseAmountUseCase
import com.purkt.mindexpense.core.domain.expense.usecase.ValidateExpenseAmountUseCaseImpl
import com.purkt.mindexpense.core.domain.expense.usecase.ValidateExpenseNoteUseCase
import com.purkt.mindexpense.core.domain.expense.usecase.ValidateExpenseNoteUseCaseImpl
import com.purkt.mindexpense.core.domain.expense.usecase.ValidateExpenseRecipientUseCase
import com.purkt.mindexpense.core.domain.expense.usecase.ValidateExpenseRecipientUseCaseImpl
import com.purkt.mindexpense.core.domain.expense.usecase.ValidateExpenseTitleUseCase
import com.purkt.mindexpense.core.domain.expense.usecase.ValidateExpenseTitleUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val coreDomainExpenseModule = module {
    includes(coreDataExpenseModule)
    factoryOf(::CreateExpenseUseCaseImpl) { bind<CreateExpenseUseCase>() }
    factoryOf(::GetExpenseByIdUseCaseImpl) { bind<GetExpenseByIdUseCase>() }
    factoryOf(::UpdateExpenseUseCaseImpl) { bind<UpdateExpenseUseCase>() }
    factoryOf(::ValidateExpenseTitleUseCaseImpl) { bind<ValidateExpenseTitleUseCase>() }
    factoryOf(::ValidateExpenseRecipientUseCaseImpl) { bind<ValidateExpenseRecipientUseCase>() }
    factoryOf(::ValidateExpenseAmountUseCaseImpl) { bind<ValidateExpenseAmountUseCase>() }
    factoryOf(::ValidateExpenseNoteUseCaseImpl) { bind<ValidateExpenseNoteUseCase>() }
}