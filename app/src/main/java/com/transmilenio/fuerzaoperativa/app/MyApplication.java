package com.transmilenio.fuerzaoperativa.app;

import android.app.Application;

import com.transmilenio.fuerzaoperativa.models.db.Aforador;
import com.transmilenio.fuerzaoperativa.models.db.ConteoDesEncuesta;
import com.transmilenio.fuerzaoperativa.models.db.Estacion;
import com.transmilenio.fuerzaoperativa.models.db.EstacionServicio;
import com.transmilenio.fuerzaoperativa.models.db.RegistroConteo;
import com.transmilenio.fuerzaoperativa.models.db.Serv;
import com.transmilenio.fuerzaoperativa.models.db.ServTemp;
import com.transmilenio.fuerzaoperativa.models.db.ServicioRutas;
import com.transmilenio.fuerzaoperativa.models.json.EncuestaTM;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by nataly on 04/10/2017.
 */

public class MyApplication extends Application {


    public static AtomicInteger servicioID = new AtomicInteger();
    public static AtomicInteger estacionID = new AtomicInteger();
    public static AtomicInteger aforadorID = new AtomicInteger();
    public static AtomicInteger encuestasTmID = new AtomicInteger();
    public static AtomicInteger conteoDespaID = new AtomicInteger();
    public static AtomicInteger regConteoDespaID = new AtomicInteger();
    public static AtomicInteger estacionServicioID = new AtomicInteger();
    public static AtomicInteger servID = new AtomicInteger();
    public static AtomicInteger servTempID = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();

        setUpRealmConfig();
        Realm realm = Realm.getDefaultInstance();
        servicioID = getIdByTable(realm, ServicioRutas.class);
        estacionID = getIdByTable(realm, Estacion.class);
        aforadorID = getIdByTable(realm, Aforador.class);

        encuestasTmID = getIdByTable(realm, EncuestaTM.class);

        conteoDespaID = getIdByTable(realm, ConteoDesEncuesta.class);
        regConteoDespaID = getIdByTable(realm, RegistroConteo.class);
        estacionServicioID = getIdByTable(realm, EstacionServicio.class);

        servID = getIdByTable(realm, Serv.class);
        servTempID = getIdByTable(realm, ServTemp.class);

        realm.close();
    }

    private void setUpRealmConfig(){
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("myrealm.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm,Class<T> anyClass){
        RealmResults<T> result = realm.where(anyClass).findAll();
        return (result.size()>0) ? new AtomicInteger(result.max("id").intValue()): new AtomicInteger()  ;
    }
}
