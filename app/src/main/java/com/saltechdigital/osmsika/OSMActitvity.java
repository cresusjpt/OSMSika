package com.saltechdigital.osmsika;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.saltechdigital.osmsika.Utils.ColorUtils;
import com.saltechdigital.osmsika.Utils.MyPolygon;
import com.saltechdigital.osmsika.database.culture.TCulture;
import com.saltechdigital.osmsika.database.culture.TCultureDao;
import com.saltechdigital.osmsika.database.infocparsol.TInfoCParSol;
import com.saltechdigital.osmsika.database.infocparsol.TInfoCParSolDao;
import com.saltechdigital.osmsika.database.periodeculture.TPeriodeCulture;
import com.saltechdigital.osmsika.database.periodeculture.TPeriodeCultureDao;
import com.saltechdigital.osmsika.database.region.TRegion;
import com.saltechdigital.osmsika.database.region.TRegionDao;
import com.saltechdigital.osmsika.database.regsol.TRegSol;
import com.saltechdigital.osmsika.database.regsol.TRegSolDao;
import com.saltechdigital.osmsika.database.rendement.TRendement;
import com.saltechdigital.osmsika.database.rendement.TRendementDao;
import com.saltechdigital.osmsika.database.sol.TSol;
import com.saltechdigital.osmsika.database.sol.TSolDao;
import com.saltechdigital.osmsika.slidingTab.SlidingTabLayout;
import com.saltechdigital.osmsika.slidingTab.ViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class OSMActitvity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener,MyPolygon.OnPolygonClickListener{

    MapView map = null;

    private int regionsNumber;

    ViewPagerAdapter adapter;
    ViewPager pager;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"SOL", "CULTURE"};
    int Numboftabs = 2;

    List<JSONObject> regionFeatures;
    List<JSONObject> regionProperties;
    List<JSONObject> regionGeometry;
    List<JSONArray> regionCoordinates;
    List<JSONArray> coodonneesArrays;

    List<List<GeoPoint>> polygonOptions;
    List<Polygon> polygons;

    private List<Integer> region;
    private List<GeoPoint> lat;
    IMapController mapController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();

        setContentView(R.layout.activity_osmactitvity);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = findViewById(R.id.tabs);

        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
                                       @TargetApi(Build.VERSION_CODES.M)
                                       @Override
                                       public int getIndicatorColor(int position) {
                                           return getResources().getColor(R.color.colorPrimaryDark);
                                       }
                                   }
        );

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        insertDatabase(this);

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        onMapReady(map);
    }

    private void insertDatabase(Context context){
        TCultureDao cultureDao = new TCultureDao(context);
        TSolDao solDao = new TSolDao(context);
        TInfoCParSolDao infoCParSolDao = new TInfoCParSolDao(context);
        TPeriodeCultureDao periodeCultureDao = new TPeriodeCultureDao(context);
        TRendementDao rendementDao = new TRendementDao(context);
        TRegionDao regionDao = new TRegionDao(context);
        TRegSolDao regSolDao = new TRegSolDao(context);

        if (cultureDao.taille() == 0 && solDao.taille() == 0 && infoCParSolDao.taille() == 0 && periodeCultureDao.taille() == 0 && rendementDao.taille() == 0){
            for (int i = 0; i <10 ; i++) {
                TRegion region = new TRegion(i,"MARITIME",50000);
                TSol sol = new TSol(i,"Argileux");
                TRegSol regSol = new TRegSol(i,i);
                TPeriodeCulture periodeCulture = new TPeriodeCulture(i,"01/01/2018","01/03/2018");
                TCulture culture = new TCulture(i,"Maïs");
                TInfoCParSol infoCParSol = new TInfoCParSol(i,i);
                TRendement rendement = new TRendement(i,i,0.5);

                regionDao.ajouter(region);
                solDao.ajouter(sol);
                regSolDao.ajouter(regSol);
                periodeCultureDao.ajouter(periodeCulture);
                cultureDao.ajouter(culture);
                infoCParSolDao.ajouter(infoCParSol);
                rendementDao.ajouter(rendement);
            }
        }
    }

    private void initialize(){
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        region = new ArrayList<>();
        lat = new ArrayList<>();
        polygonOptions = new ArrayList<>(10000);
        polygons = new ArrayList<>();
    }

    private void populate() {

        for (int i = 0; i < regionsNumber; i++) {
            polygonOptions.add(new ArrayList<GeoPoint>());
        }

        Button bt1 = findViewById(R.id.bt1);
        Button bt2 = findViewById(R.id.bt2);

        bt1.setTag("Appuyer pour voir les types de sols");
        bt2.setTag("Appuyer pour voir les cultures déjà disponible");

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
    }

    private void fetchGeoJSON(InputStream inputStream) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            JSONObject jsonObject = new JSONObject(builder.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("features");

            regionFeatures = new ArrayList<>();
            regionProperties = new ArrayList<>();
            regionGeometry = new ArrayList<>();
            regionCoordinates = new ArrayList<>();
            coodonneesArrays = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject features = jsonArray.getJSONObject(i);
                JSONObject properties = features.getJSONObject("properties");
                JSONObject geometry = features.getJSONObject("geometry");
                JSONArray coordinates = geometry.getJSONArray("coordinates");
                regionFeatures.add(features);
                regionProperties.add(properties);
                regionGeometry.add(geometry);
                regionCoordinates.add(coordinates);
            }
            regionsNumber = regionGeometry.size();

            for (int i = 0; i < regionCoordinates.size(); i++) {
                coodonneesArrays.add(regionCoordinates.get(i).getJSONArray(0).getJSONArray(0));
            }

            for (int i = 0; i < coodonneesArrays.size(); i++) {
                JSONArray jsonArray1 = coodonneesArrays.get(i);
                for (int j = 0; j < jsonArray1.length(); j++) {
                    JSONArray jsonArray2 = jsonArray1.getJSONArray(j);
                    double v1, v2;
                    v1 = jsonArray2.getDouble(1);
                    v2 = jsonArray2.getDouble(0);
                    region.add(i);
                    lat.add(new GeoPoint(v1, v2));
                }
                Log.d("JEANPAUL", "fetchGeoJSON: lat = "+lat.size());
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void createPolygoneOptions() {
        for (int i = 0; i < region.size(); i++) {
            for (int j = 0; j < regionsNumber; j++) {
                if (region.get(i) == j) {
                    Log.d("JEANPAUL", "createPolygoneOptions: i ="+i+" j = "+j+" region.get(i) = "+region.get(i));
                    polygonOptions.get(j).add(lat.get(i));
                    Log.d("JEANPAUL", "createPolygoneOptions: plopt : "+polygonOptions.size());
                }
            }
        }
    }

    private void addPolygon() {
        Polyline polyline = new Polyline();
        polyline.setOnClickListener(new Polyline.OnClickListener() {
            @Override
            public boolean onClick(Polyline polyline, MapView mapView, GeoPoint eventPos) {
                return false;
            }
        });
        for (int i = 0; i < regionsNumber; i++) {
            MyPolygon polygon = new MyPolygon();
            polygon.setFillColor(getResources().getColor(ColorUtils.regionColor(i)));
            polygon.setPoints(polygonOptions.get(i));
            polygon.setVisible(true);
            polygon.setStrokeWidth(0);
            polygon.setStrokeColor(Color.BLACK);
            polygon.setTag(i);
            polygon.setPolygon(polygon);
            polygons.add(polygon);
            polygon.setOnPolygonClickListener(this);
            map.getOverlayManager().add(polygon);

        }
    }

    public void onMapReady(MapView mMap) {
        try {
            InputStream inputStream = getAssets().open("civ.json");
            fetchGeoJSON(inputStream);
            populate();
        } catch (IOException e) {
            e.printStackTrace();
        }

        map = mMap;
        map.setMultiTouchControls(true);
        map.setBuiltInZoomControls(false);

        mapController = map.getController();
        double pZoom = 7;
        mapController.setZoom(pZoom);
        GeoPoint startPoint = new GeoPoint(9.069551294233216, 0.98876953125);
        mapController.setCenter(startPoint);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        createPolygoneOptions();
        addPolygon();

    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.bt1:
                mapController.zoomIn();
                break;
            case R.id.bt2:
                mapController.zoomOut();
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    @Override
    public void onPolygonClick(MyPolygon polygon) {
        int i = (int) polygon.getTag();
        GeoPoint latLng;
        try {
            String regionName = regionProperties.get(i).getString("VARNAME_1");
            double v1 = regionProperties.get(i).optJSONArray("MIDDLE").optDouble(1);
            double v2 = regionProperties.get(i).optJSONArray("MIDDLE").optDouble(0);
            latLng = new GeoPoint(v1,v2);
            mapController.setCenter(latLng);
            fetchData(i);

            mapController.zoomTo(9.5,2000L);
            mapController.animateTo(latLng);

            Toast.makeText(OSMActitvity.this, regionName, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            Toast.makeText(this, "MIDDLE don't exist", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void fetchData(int tag){
        Sols.setIdRegion(tag);
        Culture.setIdRegion(tag);
    }
}