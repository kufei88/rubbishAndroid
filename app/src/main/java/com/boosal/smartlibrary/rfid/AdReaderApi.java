package com.boosal.smartlibrary.rfid;

import com.rfid.api.ADReaderInterface;
import com.rfid.api.GFunction;
import com.rfid.api.ISO15693Interface;
import com.rfid.api.ISO15693Tag;
import com.rfid.def.ApiErrDefinition;
import com.rfid.def.RfidDef;

import java.util.HashSet;
import java.util.Set;

public class AdReaderApi {

    public Set<String> invertoryTag(final ADReaderInterface reader) {


        Set<String> tags = new HashSet<>();

        Object hInvenParamSpecList = null;
        byte newAI = RfidDef.AI_TYPE_NEW;
        byte useAnt[] = null;

        int iret = reader.RDR_TagInventory(newAI, useAnt, 0,
                hInvenParamSpecList);
        if (iret == ApiErrDefinition.NO_ERROR
                || iret == -ApiErrDefinition.ERR_STOPTRRIGOCUR) {

            Object tagReport = reader
                    .RDR_GetTagDataReport(RfidDef.RFID_SEEK_FIRST);
            while (tagReport != null) {
                ISO15693Tag ISO15693TagData = new ISO15693Tag();
                iret = ISO15693Interface.ISO15693_ParseTagDataReport(
                        tagReport, ISO15693TagData);
                if (iret == ApiErrDefinition.NO_ERROR) {
                    // ISO15693 TAG
                    tags.add(GFunction.encodeHexStr(ISO15693TagData.uid));
                    tagReport = reader.RDR_GetTagDataReport(RfidDef.RFID_SEEK_NEXT);
                    continue;
                }
                break;
            }
        }
        reader.RDR_ResetCommuImmeTimeout();
        return tags;
    }

}
