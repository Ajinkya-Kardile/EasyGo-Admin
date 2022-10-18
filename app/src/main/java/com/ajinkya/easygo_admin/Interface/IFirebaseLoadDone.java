package com.ajinkya.easygo_admin.Interface;
import com.ajinkya.easygo_admin.Model.IDs;

import java.util.List;

public interface IFirebaseLoadDone {
    void onFirebaseLoadSuccess(List<IDs> LocationList);
    void onFirebaseLoadFailed(String Message);
}


