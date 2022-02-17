package id.xxx.module.di.module;

import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import id.xxx.module.di.multibinding.ViewModelFactory;

@Module
public abstract class AbstractViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory viewModelFactory);
}
