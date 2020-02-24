package com.bast.lamzone.di;

import com.bast.lamzone.db.ApiServiceReu;
import com.bast.lamzone.db.ReuApiServ;

public class Di {

    private static ApiServiceReu apiService = new ReuApiServ();

    public static ApiServiceReu getApiServiceReu() { return apiService;}

    public static void resetApiService() {
        apiService = new ReuApiServ();
    }
}
