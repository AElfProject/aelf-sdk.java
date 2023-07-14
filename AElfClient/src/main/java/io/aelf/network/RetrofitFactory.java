package io.aelf.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitFactory {

    private static RetrofitFactory ins;

    public static void init(String host) {
        ins = new RetrofitFactory(host);
    }

    public static APIService getAPIService() {
        if (ins == null) throw new RuntimeException("Please init RetrofitFactory first.");
        return ins.apiService;
    }

    private final APIService apiService;

    private RetrofitFactory(String host) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(NetworkConnector.getIns().getClient())
                .build();
        apiService = retrofit.create(APIService.class);
    }
}

