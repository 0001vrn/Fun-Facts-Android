package com.studio.varun.funfacts;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Varun on 01-10-2017.
 */

public interface IFactRequest {

    @GET
    Call<String> retrieveFact(@Url String url);
}
