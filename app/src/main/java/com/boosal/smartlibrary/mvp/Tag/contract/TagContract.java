package com.boosal.smartlibrary.mvp.Tag.contract;

import com.boosal.smartlibrary.base.baseMVP.model.IBaseModel;
import com.boosal.smartlibrary.base.baseMVP.presenter.IBasePresenter;
import com.boosal.smartlibrary.base.baseMVP.view.IBaseView;
import com.rfid.api.ADReaderInterface;

import java.util.Set;

public class TagContract {

    public interface View extends IBaseView {
        void setTags(Set<String>tags);
    }

    public interface Presenter extends IBasePresenter {
        void loopQueryTag(ADReaderInterface reader);
        void cancelLoop();//取消注册
        void clearlocalSet();
    }

    public interface Model extends IBaseModel {
        Set<String> loopQueryTag(ADReaderInterface reader);
    }

}
