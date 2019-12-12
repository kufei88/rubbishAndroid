package com.boosal.smartlibrary.mvp.Tag.model;

import com.boosal.smartlibrary.mvp.Tag.contract.TagContract;
import com.boosal.smartlibrary.rfid.AdReaderApi;
import com.rfid.api.ADReaderInterface;

import java.util.Set;

public class TagModelImpl implements TagContract.Model{

    private AdReaderApi adReaderApi;

    public TagModelImpl() {
        adReaderApi = new AdReaderApi();
    }

    @Override
    public Set<String> loopQueryTag(ADReaderInterface reader) {
        return adReaderApi.invertoryTag(reader);
    }
}
