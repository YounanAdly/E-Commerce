package com.example.android.e_commerce.ui.ViewModel;

import android.util.Log;

import com.example.android.e_commerce.ui.FirebaseClient.FireStoreClient;
import com.example.android.e_commerce.ui.data.Product;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ProductViewModel extends ViewModel {
    private FireStoreClient                     fireStoreClient              = new FireStoreClient();
    private CompositeDisposable                 compositeDisposable          = new CompositeDisposable();
    private MutableLiveData<List<Product>>      mutableLiveData              = new MutableLiveData<>();
    private MutableLiveData<String>             bookmarkLiveData             = new MutableLiveData<>();
    private MutableLiveData<String>             cardLiveData                 = new MutableLiveData<>();
    private MutableLiveData<Integer>            cardSize                     = new MutableLiveData<>();
    private MutableLiveData<Double>             totalPrice                   = new MutableLiveData<>();
    private MutableLiveData<List<Product>>      searchMutableLiveData        = new MutableLiveData<>();

    public static int productsPageSize = 3;
    private boolean isLoading = false;

    public void getProducts() {
        if (isLoading) {
            return;
        }
        isLoading = true;
        Observable<List<Product>> productObservable = fireStoreClient.getProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        compositeDisposable
                .add(productObservable
                        .subscribe(products -> {
                                    mutableLiveData.setValue(products);
                                    isLoading = false;
                                },
                                throwable -> {
                                    Log.d("TEST", "getProducts ERROR: " + throwable);
                                    isLoading = false;
                                }));
    }

    public MutableLiveData<List<Product>> getProducts2() {
        return mutableLiveData;
    }

    public void setBookMarkV() {
        Observable<String> stringObservable = fireStoreClient.setBookMark()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(s -> bookmarkLiveData.setValue(s));
        compositeDisposable.add(stringObservable.subscribe());
    }

    public LiveData<List<Product>> getBookMark() {
        Observable<List<Product>> bookMarkObservable = fireStoreClient.getBookMark()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(products -> mutableLiveData.setValue(products));
        compositeDisposable.add(bookMarkObservable.subscribe());
        return mutableLiveData;
    }

    public void setCardV() {
        Observable<String> stringObservable = fireStoreClient.setCard()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(s -> cardLiveData.setValue(s));
        compositeDisposable.add(stringObservable.subscribe());
    }

    public LiveData<List<Product>> getCard() {
        Observable<List<Product>> cardObservable = fireStoreClient.getCard()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(products -> mutableLiveData.setValue(products));
        compositeDisposable.add(cardObservable.subscribe());
        return mutableLiveData;
    }

    public LiveData<Integer> getCardSize() {
        Observable<Integer> cardObservable = fireStoreClient.getCardSize()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(products -> cardSize.postValue(products));
        compositeDisposable.add(cardObservable.subscribe());
        return cardSize;
    }

    public void getSearchResult(CharSequence searchText) {
        Observable<List<Product>> searchObservable = fireStoreClient.getSearch(searchText)
                .subscribeOn(Schedulers.io())
                .distinctUntilChanged()
                .debounce(2,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread());
        compositeDisposable.add(searchObservable.subscribe(
                products -> searchMutableLiveData.postValue(products),
                throwable -> Log.d("TEST", "getSearchResult: " + throwable)));
    }

    public MutableLiveData<List<Product>> getSearchMutableLiveData() {
        return searchMutableLiveData;
    }

    public LiveData<Double> getTotal() {
        Observable<Double> cardObservable = fireStoreClient.getTotalPrice()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(products -> totalPrice.postValue(products));
        compositeDisposable.add(cardObservable.subscribe());
        return totalPrice;
    }

}
