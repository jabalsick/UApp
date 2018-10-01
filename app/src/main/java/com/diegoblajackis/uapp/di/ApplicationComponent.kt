package com.diegoblajackis.uapp.di


import com.diegoblajackis.uapp.ui.viewModels.UsersViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    ApiServiceModule::class,
    RepositoriesModule::class] )
interface ApplicationComponent {
    fun inject(usersViewModel: UsersViewModel)
}