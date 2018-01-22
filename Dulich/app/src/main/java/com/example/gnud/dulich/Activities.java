package com.example.gnud.dulich;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.gnud.dulich.adapter.GridAdapter;
import com.example.gnud.dulich.database.DatabaseThings;
import com.example.gnud.dulich.item.EntityActivity;

import java.sql.SQLException;
import java.util.ArrayList;

public class Activities extends AppCompatActivity {

    public GridView gridView;
    private LinearLayout linearLayout;
    private ArrayList<EntityActivity> giaitri = new ArrayList<>();
    private ArrayList<EntityActivity> congtac = new ArrayList<>();
    private ArrayList<EntityActivity> both = new ArrayList<>();
    private ArrayList<EntityActivity> mylist = new ArrayList<>();
    private DatabaseThings database = new DatabaseThings(this);

    private ArrayList<String> listActivities = new ArrayList<>();
    private Intent intent;
    private int checkGiaitri, checkCongtac;
    Integer[] imageIDsGiaitri = {
            R.drawable.sel_act_swimming,
            R.drawable.sel_act_fancydinner,
            R.drawable.sel_act_running,
            R.drawable.sel_act_cycling,
            R.drawable.sel_act_hiking,
            R.drawable.sel_act_baby,
            R.drawable.sel_act_beach,
            R.drawable.sel_act_international,
            R.drawable.sel_act_snow,
            R.drawable.sel_act_camping,
            R.drawable.sel_act_gym,
            R.drawable.sel_act_photography,
            R.drawable.sel_act_motorcycling
    };

    Integer[] imageIDsCongtac = {
            R.drawable.sel_act_business_casual,
            R.drawable.sel_act_business_formal,
            R.drawable.sel_act_fancydinner,
            R.drawable.sel_act_international,
            R.drawable.sel_act_working_fix
    };
    String[] titleGiaitri = {
            "Bơi",
            "Ăn tối ngoài trời",
            "Chạy",
            "Đạp xe",
            "Leo núi",
            "Đi cùng trẻ",
            "Tắm biển",
            "Quốc tế",
            "Thể thao tuyết",
            "Cắm trại",
            "Thể hình",
            "Chụp ảnh",
            "Đi xe máy"
    };
    String[] titleCongtac = {
            "Kinh doanh thường",
            "Kinh doanh lớn",
            "Ăn uống",
            "Quốc tế",
            "Làm việc"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
 //       setTitle("Chọn hoạt động");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar_activities);
        View view = getSupportActionBar().getCustomView();
        Toolbar parent = (Toolbar) getSupportActionBar().getCustomView().getParent();
        parent.setContentInsetsAbsolute(0,0);
        TextView txtCenter = (TextView) view.findViewById(R.id.txtActionbarActivities);
        txtCenter.setText("Chọn hoạt động");

        ImageView imgView = (ImageView) view.findViewById(R.id.imgBackBtn);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        intent = getIntent();
        gridView = (GridView) findViewById(R.id.gridview);
        linearLayout = (LinearLayout) findViewById(R.id.linearBatdau);


        for(int i = 0; i <titleGiaitri.length; i++)
            giaitri.add(new EntityActivity(imageIDsGiaitri[i], titleGiaitri[i], false));

        for(int i = 0; i < titleCongtac.length; i++){
            congtac.add(new EntityActivity(imageIDsCongtac[i], titleCongtac[i], false));
        }
        ArrayList<EntityActivity> congtaccheck = new ArrayList<>();
        congtaccheck.addAll(congtac);
        for(int i = 0; i <giaitri.size(); i++){
            for(int j = 0; j <congtaccheck.size(); j++){
                if(giaitri.get(i).getTitle().equals(congtaccheck.get(j).getTitle())){
                    congtaccheck.remove(j);
                }
            }
        }
        both.addAll(giaitri);
        both.addAll(congtaccheck);
        checkCongtac = Integer.parseInt(intent.getStringExtra("checkCongtac"));
        checkGiaitri = Integer.parseInt(intent.getStringExtra("checkGiaitri"));
        if(checkCongtac == 1 && checkGiaitri == 0){
            mylist.addAll(congtac);
        } else if (checkGiaitri == 1 && checkCongtac == 0){
            mylist.addAll(giaitri);
        } else mylist.addAll(both);


        final GridAdapter gridAdapter = new GridAdapter(this, R.layout.custom_gridview, mylist);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mylist.get(position).isCheck()) {
                    mylist.get(position).setCheck(false);
                } else mylist.get(position).setCheck(true);
                gridAdapter.notifyDataSetChanged();
            }
        });
        try {
            database.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(!database.ischeckData()){
            database.addThings(getResources().getString(R.string.dodungcanhan).substring(2,getResources().getString(R.string.dodungcanhan).indexOf(",")),getResources().getString(R.string.dodungcanhan));
            database.addThings(getResources().getString(R.string.thietyeu).substring(2, getResources().getString(R.string.thietyeu).indexOf(",")), getResources().getString(R.string.thietyeu));
            database.addThings(getResources().getString(R.string.boi).substring(2, getResources().getString(R.string.boi).indexOf(",")), getResources().getString(R.string.boi));
            database.addThings(getResources().getString(R.string.antoingoaitroi).substring(2, getResources().getString(R.string.antoingoaitroi).indexOf(",")), getResources().getString(R.string.antoingoaitroi));
            database.addThings(getResources().getString(R.string.chay).substring(2, getResources().getString(R.string.chay).indexOf(",")), getResources().getString(R.string.chay));
            database.addThings(getResources().getString(R.string.dapxe).substring(2, getResources().getString(R.string.dapxe).indexOf(",")), getResources().getString(R.string.dapxe));
            database.addThings(getResources().getString(R.string.leonui).substring(2, getResources().getString(R.string.leonui).indexOf(",")), getResources().getString(R.string.leonui));
            database.addThings(getResources().getString(R.string.dicungtre).substring(2, getResources().getString(R.string.dicungtre).indexOf(",")), getResources().getString(R.string.dicungtre));
            database.addThings(getResources().getString(R.string.tambien).substring(2, getResources().getString(R.string.tambien).indexOf(",")), getResources().getString(R.string.tambien));
            database.addThings(getResources().getString(R.string.quocte).substring(2, getResources().getString(R.string.quocte).indexOf(",")), getResources().getString(R.string.quocte));
            database.addThings(getResources().getString(R.string.thethaotuyet).substring(2, getResources().getString(R.string.thethaotuyet).indexOf(",")), getResources().getString(R.string.thethaotuyet));
            database.addThings(getResources().getString(R.string.camtrai).substring(2, getResources().getString(R.string.camtrai).indexOf(",")), getResources().getString(R.string.camtrai));
            database.addThings(getResources().getString(R.string.thehinh).substring(2,getResources().getString(R.string.thehinh).indexOf(",")),getResources().getString(R.string.thehinh));
            database.addThings(getResources().getString(R.string.chupanh).substring(2, getResources().getString(R.string.chupanh).indexOf(",")), getResources().getString(R.string.chupanh));
            database.addThings(getResources().getString(R.string.dixemay).substring(2, getResources().getString(R.string.dixemay).indexOf(",")), getResources().getString(R.string.dixemay));



            database.addThings(getResources().getString(R.string.lamviec).substring(2,getResources().getString(R.string.lamviec).indexOf(",")),getResources().getString(R.string.lamviec));
        }

       // gridView.getOnItemClickListener();
    }

    public void TSnackbarToast(String toast){
        TSnackbar snackbar = TSnackbar.make(findViewById(android.R.id.content), toast , TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundResource(R.color.btn_yellow_normal);
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(15);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        textView.setTypeface(font);
        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listActivities.add("Đồ dùng cá nhân");
        listActivities.add("Thiết yếu");
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mylist.size(); i++) {
                    if (mylist.get(i).isCheck()) {
                        listActivities.add(mylist.get(i).getTitle());
                    }
                }
             //   TSnackbarToast("Khởi tạo kế hoạch chuyến đi");
//                final ProgressDialog progress = new ProgressDialog(Activities.this);
//                progress.setTitle("Vui lòng chờ");
//                progress.setMessage("Đang khởi tạo kế hoạch..");
//                progress.setCancelable(false);
//                progress.show();
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                },3000);
//                progress.dismiss();
                Intent i = new Intent(Activities.this, DetailTrip.class);
                i.putExtra("check", "0");
                i.putExtra("location", intent.getStringExtra("location"));
                i.putExtra("datemonth", intent.getStringExtra("datemonth"));
                i.putStringArrayListExtra("listActivities", listActivities);
                startActivityForResult(i, 3);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 3){
            setResult(2);
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        database.close();
    }
}
