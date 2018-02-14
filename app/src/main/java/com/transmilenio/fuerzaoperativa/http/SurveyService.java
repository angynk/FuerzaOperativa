package com.transmilenio.fuerzaoperativa.http;

import com.transmilenio.fuerzaoperativa.models.db.Aforador;
import com.transmilenio.fuerzaoperativa.models.db.Resultado;
import com.transmilenio.fuerzaoperativa.models.json.Config;
import com.transmilenio.fuerzaoperativa.models.json.EncuestasTerminadas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by nataly on 16/10/2017.
 */

public interface SurveyService {

    @POST("survey/new/")
    Call<List<Resultado>> sendSurvey(@Body EncuestasTerminadas res);

    @GET("config/serviciosEstaciones/")
    Call<Config> getServicios();

    @POST("user/login/")
    Call<Boolean> login(@Body Aforador aforador);
}
