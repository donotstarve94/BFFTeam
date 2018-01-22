package com.example.gnud.dulich;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.gnud.dulich.adapter.PlacesAutoCompleteAdapter;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewTrip extends AppCompatActivity {

    private EditText edtDate;
    private TextView txtNoibanden, txtNgaydi, txtsoNgaySeekBar, txtToast, txtBackground;
    private Calendar calendar;
    private int year, month , day;

    private PlacesAutoCompleteAdapter mPlacesAdapter;
    private AutoCompleteTextView location;
    private PlacePicker.IntentBuilder builder;
    private static final int PLACE_PICKER_FLAG = 1;

    protected GoogleApiClient mGoogleApiClient;

    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));

    private LinearLayout linearChonHoatDong, linearLayoutCongtac, linearLayoutGiaitri, linearSongayolai;

    private ImageView imgCongtac, imgGiaitri;
    private TextView txtCongtac, txtGiaitri;

    private int checkCongtac = 0;
    private int checkGiaitri = 0;

    private DiscreteSeekBar discreteSeekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Places.GEO_DATA_API).build();
        setContentView(R.layout.activity_new_trip);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar_activities);
        View view = getSupportActionBar().getCustomView();
        Toolbar parent = (Toolbar) getSupportActionBar().getCustomView().getParent();
        parent.setContentInsetsAbsolute(0,0);
        TextView txtCenter = (TextView) view.findViewById(R.id.txtActionbarActivities);
        txtCenter.setText("Chuyến đi mới");

        ImageView imgView = (ImageView) view.findViewById(R.id.imgBackBtn);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        location = (AutoCompleteTextView) findViewById(R.id.location);

        builder = new PlacePicker.IntentBuilder();
        mPlacesAdapter = new PlacesAutoCompleteAdapter(this, android.R.layout.simple_list_item_1,
                mGoogleApiClient, BOUNDS_GREATER_SYDNEY, null);
        if(isOnline()){
//            location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    final PlacesAutoCompleteAdapter.PlaceAutocomplete item = mPlacesAdapter.getItem(position);
//                    final String placeId = String.valueOf(item.placeId);
//                    PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
//                            .getPlaceById(mGoogleApiClient, placeId);
//                    placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
////            getWindow().setSoftInputMode(
////                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
////            );
//                }
//            });
            location.setAdapter(mPlacesAdapter);
            location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                }
            });

        }


//        txtToast = (TextView) findViewById(R.id.txtToast);
//        txtBackground = (TextView) findViewById(R.id.txtBackground);




        txtNoibanden = (TextView) findViewById(R.id.txtNoibanden);
        txtNgaydi = (TextView) findViewById(R.id.txtNgayDi);
        linearSongayolai = (LinearLayout) findViewById(R.id.linearSongayolai);
        txtNoibanden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location.clearFocus();
                hideKeyboard(v);
            }
        });

//        Typeface RB_bold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
////        Typeface RB_italic = Typeface.createFromAsset()
//        txtNoibanden.setTypeface(RB_bold);
        txtNgaydi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
            }
        });
        linearSongayolai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
            }
        });

        discreteSeekBar = (DiscreteSeekBar) findViewById(R.id.seekBar);
        txtsoNgaySeekBar = (TextView) findViewById(R.id.txtsoNgaySeekbar);
        txtsoNgaySeekBar.setText(String.valueOf(discreteSeekBar.getProgress()));

        discreteSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                txtsoNgaySeekBar.setText(String.valueOf(discreteSeekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });
//        location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    builder = new PlacePicker.IntentBuilder();
//                    Intent intent = builder.build(MainActivity.this);
//                    // Start the Intent by requesting a result, identified by a request code.
//                    startActivityForResult(intent, PLACE_PICKER_FLAG);
//
//                } catch (GooglePlayServicesRepairableException e) {
//                    GooglePlayServicesUtil
//                            .getErrorDialog(e.getConnectionStatusCode(), MainActivity.this, 0);
//                } catch (GooglePlayServicesNotAvailableException e) {
//                    Toast.makeText(MainActivity.this, "Google Play Services is not available.",
//                            Toast.LENGTH_LONG)
//                            .show();
//                }
//            }
//        });
//        setTitle("Chuyến đi mới");
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        edtDate = (EditText) findViewById(R.id.edtDate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                showDialog(999);

            }
        });

        linearLayoutCongtac = (LinearLayout) findViewById(R.id.linearlayoutCongtac);
        linearLayoutGiaitri = (LinearLayout) findViewById(R.id.linearlayoutGiaitri);
        txtCongtac = (TextView) findViewById(R.id.txtCongtac);
        txtGiaitri = (TextView) findViewById(R.id.txtGiaitri);
        imgCongtac = (ImageView) findViewById(R.id.imgCongtac);
        imgGiaitri = (ImageView) findViewById(R.id.imgGiaitri);




        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    public String plusDate(){
        String dt = edtDate.getText().toString();
        SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DATE, discreteSeekBar.getProgress());
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        String output = sdf1.format(calendar.getTime());
        return dt + " - " + output;
    }

    @Override
    protected void onResume() {
        super.onResume();
        linearLayoutCongtac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCongtac == 0) {
                    linearLayoutCongtac.setBackgroundResource(R.color.actionbar_color);
                    txtCongtac.setTextColor(getResources().getColor(R.color.white));
                    imgCongtac.setColorFilter(Color.parseColor("#ffffff"));
                    checkCongtac = 1;
                } else {
                    linearLayoutCongtac.setBackgroundResource(R.color.white);
                    txtCongtac.setTextColor(getResources().getColor(R.color.actionbar_color));
                    imgCongtac.setColorFilter(getResources().getColor(R.color.actionbar_color));
                    checkCongtac = 0;
                }

            }
        });
        linearLayoutGiaitri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkGiaitri == 0) {
                    linearLayoutGiaitri.setBackgroundResource(R.color.actionbar_color);
                    txtGiaitri.setTextColor(getResources().getColor(R.color.white));
                    imgGiaitri.setColorFilter(Color.parseColor("#ffffff"));
                    checkGiaitri = 1;
                } else {
                    linearLayoutGiaitri.setBackgroundResource(R.color.white);
                    txtGiaitri.setTextColor(getResources().getColor(R.color.actionbar_color));
                    imgGiaitri.setColorFilter(getResources().getColor(R.color.actionbar_color));
                    checkGiaitri = 0;
                }
            }
        });

        linearChonHoatDong = (LinearLayout) findViewById(R.id.linearChonHoatDong);
        linearChonHoatDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(location.getText().toString().equals("")) {
                    TSnackbarToast("Hãy chọn địa điểm đến..");
                }else if( edtDate.getText().toString().equals("")){
                    TSnackbarToast("Hãy chọn ngày khởi hành..");
                }else if((checkGiaitri == 0&& checkCongtac == 0)){
                    TSnackbarToast("Hãy chọn kiểu chuyến đi..");
                }else{
                    Intent i = new Intent(NewTrip.this, Activities.class);
                    i.putExtra("location", location.getText().toString());
                    i.putExtra("datemonth", plusDate());
                    i.putExtra("checkGiaitri", String.valueOf(checkGiaitri));
                    i.putExtra("checkCongtac", String.valueOf(checkCongtac));
                    Log.i("testdate", location.getText().toString() + "     " + plusDate());
                    startActivityForResult(i, 2);
                }

            }
        });
    }


    public void TSnackbarToast(String toast){
        TSnackbar snackbar = TSnackbar.make(findViewById(android.R.id.content), toast , TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundResource(R.color.red);
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextColor(Color.WHITE);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        textView.setTypeface(font);
        snackbar.show();
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            showDate(year, monthOfYear + 1, dayOfMonth);
        }
    };
    private void showDate(int year, int month, int day) {
        edtDate.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
    @Override
    protected Dialog onCreateDialog(int id){
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PLACE_PICKER_FLAG:
                    Place place = PlacePicker.getPlace(data, this);
                    location.setText(place.getName() + ", " + place.getAddress());
                    break;
            }
        }
        if(resultCode == 2){
            setResult(1);
            finish();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e("place", "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
        }
    };
}
