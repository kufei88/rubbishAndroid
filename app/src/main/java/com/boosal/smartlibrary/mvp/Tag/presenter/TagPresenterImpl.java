package com.boosal.smartlibrary.mvp.Tag.presenter;

import com.boosal.smartlibrary.mvp.Tag.contract.TagContract;
import com.boosal.smartlibrary.mvp.Tag.model.TagModelImpl;
import com.boosal.smartlibrary.utils.CollectionUtil;
import com.boosal.smartlibrary.utils.Logger;
import com.rfid.api.ADReaderInterface;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TagPresenterImpl implements TagContract.Presenter{

    private TagContract.View mView;
    private TagContract.Model mModel;

    private CompositeDisposable mCompositeDisposable;
    private Set<String> localTags;

    public TagPresenterImpl(TagContract.View view) {
        this.mView = view;
        mModel=new TagModelImpl();
        localTags = new HashSet<>();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void cancelLoop() {
        if (mCompositeDisposable != null ) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    public void clearlocalSet() {
        localTags.clear();
    }

    @Override
    public void loopQueryTag(final ADReaderInterface reader) {
        if(mCompositeDisposable == null){
            mCompositeDisposable = new CompositeDisposable();
        }

        mCompositeDisposable.add(
                Observable.create(new ObservableOnSubscribe<Set<String>>() {
                    @Override
                    public void subscribe(ObservableEmitter<Set<String>> e) throws Exception {
                        e.onNext(mModel.loopQueryTag(reader));
                        e.onComplete();
                    }
                }).repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                        return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Object o) throws Exception {
                                return Observable.just(1).delay(200, TimeUnit.MILLISECONDS);
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Set<String>>() {
                    @Override
                    public void accept(Set<String> tags) throws Exception {

                        //本地记录和当次读取结果不同
                        if(CollectionUtil.isSetEqual(tags,localTags)){
                            return;
                        }
                        localTags.clear();
                        localTags.addAll(tags);
                        mView.setTags(tags);
                    }
                })
        );
    }


}
