package com.example.android.e_commerce.ui.ViewModel;

import com.example.android.e_commerce.ui.FirebaseClient.FireStoreClient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FirebaseAuthViewModel extends ViewModel {

    private CompositeDisposable         compositeDisposable      = new CompositeDisposable();
    private MutableLiveData<Boolean>    loginBoolean             = new MutableLiveData<>();
    private MutableLiveData<Boolean>    createBoolean            = new MutableLiveData<>();
    private MutableLiveData<String>     error                    = new MutableLiveData<>();
    private FireStoreClient             fireStoreClient          = new FireStoreClient();


    public void login(String email, String password) {
        Observable<Boolean> login = fireStoreClient.login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        compositeDisposable.add(login.subscribe(aBoolean -> loginBoolean.postValue(aBoolean),
                        throwable -> error.postValue(throwable.getMessage()))
        );
    }

    public void create(String email, String password) {
        Observable<Boolean> create = fireStoreClient.createAccount(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        compositeDisposable.add(create.subscribe(aBoolean -> createBoolean.postValue(aBoolean),
                        throwable -> error.postValue(throwable.getMessage()))
        );
    }



    public LiveData<Boolean> getLogin() {
        return loginBoolean;
    }

    public LiveData<Boolean> setAccountCreate() {
        return createBoolean;
    }

    public LiveData<String> error() {
        return error;
    }
}
