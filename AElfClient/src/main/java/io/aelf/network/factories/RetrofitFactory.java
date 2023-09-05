package io.aelf.network.factories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import io.aelf.network.APIService;
import io.aelf.network.NetworkConnector;
import io.aelf.response.ResultCode;
import io.aelf.utils.AElfException;
import org.apache.http.util.TextUtils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.annotation.Nullable;
import java.io.IOException;

public class RetrofitFactory {

    private static RetrofitFactory ins;

    public static void init(@Nullable String url, @Nullable Retrofit.Builder injectedRetrofit) {
        if (TextUtils.isBlank(url) && injectedRetrofit == null)
            throw new AElfException(ResultCode.PARAM_ERROR,
                    "You can not provide both empty host url and null injectedRetrofit object.");
        ins = new RetrofitFactory(wrapRetrofitConfig(url, injectedRetrofit));
    }

    public static APIService getAPIService() {
        if (ins == null) throw new RuntimeException("Please init RetrofitFactory first.");
        return ins.apiService;
    }

    private final APIService apiService;

    private RetrofitFactory(Retrofit retrofit) {
        apiService = retrofit.create(APIService.class);
    }

    protected static Retrofit.Builder createBasicRetrofitBuilder() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create());
    }

    protected static Retrofit wrapRetrofitConfig(String host, Retrofit.Builder builder) {
        if (builder == null) {
            builder = createBasicRetrofitBuilder();
        }
        if (!TextUtils.isBlank(host)) {
            builder = builder.baseUrl(host);
        }
        return builder
                .client(NetworkConnector.getIns().getClient())
                .build();
    }

    /**
     * Start network connection and get the result.
     *
     * @param call Call object created by {@link APIService}
     * @param <R>  any type
     * @return string result
     * @throws AElfException when facing errors
     */
    public static <R extends JsonElement> String networkResult(Call<R> call) throws AElfException {
        try {
            Response<R> response = call.execute();
            if (!response.isSuccessful()) throw new AElfException(ResultCode.PEER_REJECTED,
                    "the code is ".concat(String.valueOf(response.code()))
            );
            R body = response.body();
            if (body == null || TextUtils.isBlank(body.toString()))
                throw new AElfException(ResultCode.PEER_REJECTED,
                        "The peer returns empty response body.",
                        true);
            return body.toString();
        } catch (IOException e) {
            throw new AElfException(e, ResultCode.NETWORK_DISCONNECTED);
        } catch (Exception e) {
            throw new AElfException(e);
        }
    }
}

