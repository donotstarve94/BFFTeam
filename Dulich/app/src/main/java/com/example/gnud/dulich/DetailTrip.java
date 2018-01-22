package com.example.gnud.dulich;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.gnud.dulich.adapter.ListStickAdapter;
import com.example.gnud.dulich.adapter.ListviewAdapter;
import com.example.gnud.dulich.database.DatabaseThings;
import com.example.gnud.dulich.database.DatabaseTrip;
import com.example.gnud.dulich.item.Trip;

import java.sql.SQLException;
import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class DetailTrip extends AppCompatActivity {

    private ListView listView;
    private DatabaseTrip database = new DatabaseTrip(this);
    private DatabaseThings databaseThings = new DatabaseThings(this);
    private TextView txtlocationheader, txtdatemonthheader;
    private int k, checkVisi = 0;
    private LinearLayout linearSpinnerheader;
    private ImageView imgViewSpinner;
    private String location, datemonth, things;
    private ArrayList<String> listActivities = null;
    private StickyListHeadersListView stickyListHeadersListView;
    private ListStickAdapter listStickAdapter = null;
    private ListviewAdapter listviewAdapter = null;
    private ArrayList<String> array = null;
    private ArrayList<Trip>  arrayList = null;
    private Intent intent;
    private int saveId = 0;
    private String prefname = "myData";
    private Trip trip;

    private boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_trip);
     //   setTitle("Hành trang du lịch");
        setResult(3);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar_detailtrip);
        View view = getSupportActionBar().getCustomView();
        Toolbar parent = (Toolbar) getSupportActionBar().getCustomView().getParent();
        parent.setContentInsetsAbsolute(0,0);
        TextView txtCenter = (TextView) view.findViewById(R.id.txtActionbarDetailTrip);

        txtCenter.setText("Hành trang du lịch");

        ImageView imgView = (ImageView) view.findViewById(R.id.imgCustomDetailTrip);
        imgView.setImageResource(R.drawable.ic_launcher);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DetailTrip.this, R.style.YourDialogStyle);
                dialogBuilder.setIcon(R.drawable.ic_launcher);
                dialogBuilder.setTitle("Hướng dẫn");
                dialogBuilder.setMessage("- Bấm vào một đồ dùng để đánh dấu.\n\n- Bấm và giữ để xóa một đồ dùng.\n\n- Bấm vào header mỗi hoạt động để thêm đồ dùng tương ứng.")
                        .setPositiveButton("Đã hiểu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }
        });

        ImageView imgViewOptionmenu = (ImageView) view.findViewById(R.id.imgmenuOption);
        imgViewOptionmenu.setOnCreateContextMenuListener(this);


        listView = (ListView) findViewById(R.id.listView);
        txtlocationheader = (TextView) findViewById(R.id.txtlocationheader);
        txtdatemonthheader = (TextView) findViewById(R.id.txtdateofmonthheader);
        linearSpinnerheader = (LinearLayout) findViewById(R.id.linearSpinnerheader);
        imgViewSpinner = (ImageView) findViewById(R.id.imgViewSpinner);
        imgViewSpinner.setImageResource(R.drawable.spinner_arrow_down);

        stickyListHeadersListView = (StickyListHeadersListView) findViewById(R.id.listStick);
//        Typeface RB_bold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
////        Typeface RB_italic = Typeface.createFromAsset()
        try {
            database.open();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        try {
            databaseThings.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        intent = getIntent();
        if(intent.getStringExtra("check").equals("0")){
            location = intent.getStringExtra("location");
            datemonth = intent.getStringExtra("datemonth");
            listActivities = intent.getStringArrayListExtra("listActivities");
            things = databaseThings.getActivities(listActivities);
            if(location.indexOf(",") == -1){
                txtlocationheader.setText(location);
            }
            else {
                txtlocationheader.setText(location.substring(0,location.indexOf(",")));
            }
            Log.i("tesst", "hahahaa");
            txtdatemonthheader.setText(datemonth);
            trip = new Trip(0,location,datemonth, things);
            database.addTrip(trip);

            trip = database.GetTripbyID(database.getIdMax());


            array = convertStringToArrayList(things);

            listStickAdapter = new ListStickAdapter(this,R.layout.liststick_row, R.layout.liststick_header, array);
            stickyListHeadersListView.setAdapter(listStickAdapter);
            saveId = database.getIdMax();
        }

        try {
            databaseThings.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.newtrip) {
            Intent intent = new Intent(DetailTrip.this, NewTrip.class);
            startActivityForResult(intent, 4);

        }
        if (id == R.id.deleteTrip) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DetailTrip.this, R.style.YourDialogStyle);
            dialogBuilder.setTitle("Bạn có chắc chắn muốn xóa ?")
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            database.deletebyId(trip.getId());
                            if(database.getIdMax() == 0){
                                txtlocationheader.setText("Hãy tạo mới những chuyến đi");
                                txtdatemonthheader.setTextSize(15);
                                txtdatemonthheader.setText("0 - 0");
                                arrayList.clear();
                                array.clear();
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DetailTrip.this, R.style.YourDialogStyle);
                                dialogBuilder.setTitle("Bạn chưa có kế hoạch của chuyến đi nào !")
                                        .setMessage("Hãy tạo mới").setPositiveButton("Tạo mới", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(DetailTrip.this, NewTrip.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog dialog1 = dialogBuilder.create();
                                dialog1.show();
                            } else {
                                arrayList = database.getAllTrip();

                                trip = database.GetTripbyID(database.getIdMax());
                                array = convertStringToArrayList(trip.getThings());

                                txtlocationheader.setText(trip.getLocation());
                                txtdatemonthheader.setText(trip.getDatemonth());
                            }
                            listviewAdapter = new ListviewAdapter(DetailTrip.this, R.layout.listview_row, arrayList);
                            listView.setAdapter(listviewAdapter);

                            listStickAdapter = new ListStickAdapter(DetailTrip.this,R.layout.liststick_row, R.layout.liststick_header, array);
                            stickyListHeadersListView.setAdapter(listStickAdapter);

                            saveId = trip.getId();
                        }
                    })
                    .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog dialog = dialogBuilder.create();
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 1){
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            database.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(intent.getStringExtra("check").equals("1")){

            trip = database.GetTripbyID(restoringPreferencesIdTrip());
            txtlocationheader.setText(trip.getLocation());
            txtdatemonthheader.setText(trip.getDatemonth());

            array = convertStringToArrayList(trip.getThings());
            listStickAdapter = new ListStickAdapter(this,R.layout.liststick_row, R.layout.liststick_header, array);
            stickyListHeadersListView.setAdapter(listStickAdapter);

        }

        stickyListHeadersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(array.get(position).charAt(array.get(position).length()-1) == '0'){
                    char[] ch = array.get(position).toCharArray();
                    ch[ch.length - 1] = '1';
                    String t = "";
                    array.add(positionLastItem(array,position),t.copyValueOf(ch));
                    array.remove(position);

                    listStickAdapter.notifyDataSetChanged();
                }
                else{
                    char[] ch = array.get(position).toCharArray();
                    ch[ch.length - 1] = '0';
                    String t = "";
                    array.add(positionFirstItem(array,position),t.copyValueOf(ch));
                    array.remove(position+1);

                    listStickAdapter.notifyDataSetChanged();
                }
                database.updatebyId(saveId,convertListtoString(array));
            }
        });


        stickyListHeadersListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DetailTrip.this, R.style.YourDialogStyle);
                dialogBuilder.setTitle("Xóa '"+ array.get(position).substring(array.get(position).indexOf(",")+1,array.get(position).length()-1) + "' ?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                array.remove(position);
                                listStickAdapter.notifyDataSetChanged();
                                database.updatebyId(saveId,convertListtoString(array));
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
                return false;
            }
        });

        stickyListHeadersListView.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(StickyListHeadersListView l, final View header, int itemPosition, final long headerId, boolean currentlySticky) {
                Log.d("checkID", String.valueOf(itemPosition) +"x " +  String.valueOf(headerId));

                AlertDialog.Builder alerDialog = new AlertDialog.Builder(DetailTrip.this, R.style.YourDialogStyle);
                alerDialog.setTitle("Nhập tên đồ dùng");

                final EditText input = new EditText(DetailTrip.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                alerDialog.setView(input);
                alerDialog.setIcon(R.drawable.ic_action_edit_green);

                alerDialog.setPositiveButton("Nhập", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String idheader = "";
                        if(headerId < 10){
                            idheader = "0" + String.valueOf(headerId);
                        } else {
                            idheader = String.valueOf(headerId);
                        }
                        String stringheader = "";
                        for(int i = 0 ; i < array.size(); i++){
                            if(array.get(i).substring(0,2).equals(idheader)){
                                stringheader = array.get(i).substring(2,array.get(i).indexOf(","));
                            }
                        }
                        String itemAdd = idheader + stringheader + "," + input.getText().toString() + "0";
                        for(int i = 0 ; i < array.size(); i++){
                            if(array.get(i).substring(0,2).equals(idheader)){
                                array.add(i, itemAdd);
                                break;
                            }
                        }
                        listStickAdapter.notifyDataSetChanged();
                        database.updatebyId(trip.getId(),convertListtoString(array));

                    }
                }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = alerDialog.create();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                dialog.show();
            }
        });


        arrayList = database.getAllTrip();
        listviewAdapter = new ListviewAdapter(this, R.layout.listview_row, arrayList);
        listView.setAdapter(listviewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                trip = database.GetTripbyID(arrayList.get(i).getId());
                array = convertStringToArrayList(trip.getThings());

                txtlocationheader.setText(trip.getLocation());
                txtdatemonthheader.setText(trip.getDatemonth());

                listStickAdapter = new ListStickAdapter(DetailTrip.this,R.layout.liststick_row, R.layout.liststick_header, array);
                stickyListHeadersListView.setAdapter(listStickAdapter);

                checkVisi = 0;
                stickyListHeadersListView.setAlpha(1f);
                listView.setVisibility(View.INVISIBLE);
                imgViewSpinner.setImageResource(R.drawable.spinner_arrow_down);
                saveId = trip.getId();
            }
        });

        linearSpinnerheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkVisi == 0) {
                    listView.setVisibility(View.VISIBLE);
                    imgViewSpinner.setImageResource(R.drawable.spinner_arrow_up);
                    checkVisi = 1;
                    stickyListHeadersListView.setEnabled(false);
                    stickyListHeadersListView.setAlpha(0.3f);
                } else {
                    listView.setVisibility(View.INVISIBLE);
                    imgViewSpinner.setImageResource(R.drawable.spinner_arrow_down);
                    checkVisi = 0;
                    stickyListHeadersListView.setEnabled(true);
                    stickyListHeadersListView.setAlpha(1f);
                }

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        savingPreferencesIdTrip();
    }

    public void savingPreferencesIdTrip(){
        SharedPreferences preferences = getSharedPreferences(prefname, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("IdSave", saveId);
        editor.commit();
    }
    public int restoringPreferencesIdTrip(){
        SharedPreferences preferences = getSharedPreferences(prefname, MODE_PRIVATE);
        saveId = preferences.getInt("IdSave", saveId);
        return saveId;
    }

    @Override
    protected void onStop() {
        super.onStop();
        database.close();
        databaseThings.close();
    }

    @Override
    public void onBackPressed() {
        if(doubleBackToExitPressedOnce){
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        TSnackbarToast("Ấn BACK lần nữa để thoát", R.color.btn_yellow_normal);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void TSnackbarToast(String toast, int mau){
        TSnackbar snackbar = TSnackbar.make(findViewById(android.R.id.content), toast , TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundResource(mau);
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(15);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        textView.setTypeface(font);
        snackbar.show();
    }

    public ArrayList<String> convertStringToArrayList(String inputString){
        String[] str = inputString.split("-");
        ArrayList<String> arrayList = new ArrayList<>();

        for(int i = 0; i < str.length; i++){
            arrayList.add(str[i]);
        }
        return arrayList;
    }


    public int positionLastItem(ArrayList<String> array, int position){
        int result = 0;
        int k = 0;
        for(int i = 0; i < array.size(); i++){
            if(array.get(position).substring(0,2).equals(array.get(i).substring(0,2))){
                result = result + 1;
            }
        }
        for(int i = 0; i < array.size(); i ++){
            if(array.get(position).substring(0,2).equals(array.get(i).substring(0,2))){
                k = i;
                break;
            }
        }

        return result + k;
    }
    public int positionFirstItem(ArrayList<String> array, int position){
        for(int i = 0; i < array.size(); i ++){
            if(array.get(position).substring(0,2).equals(array.get(i).substring(0,2))){
                return i;
            }
        }
        return 0;
    }

    public String convertListtoString(ArrayList<String> inputArray){
        String string = "";
        for(int i = 0; i < inputArray.size(); i++){
            string = string + inputArray.get(i) + "-";
        }
        return string;
    }
}

