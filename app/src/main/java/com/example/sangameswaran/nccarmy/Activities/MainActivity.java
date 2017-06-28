package com.example.sangameswaran.nccarmy.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sangameswaran.nccarmy.FragmentsAndAdapters.CreateNewAdminFragment;
import com.example.sangameswaran.nccarmy.Entities.AdminEntity;
import com.example.sangameswaran.nccarmy.Entities.CadetEntity;
import com.example.sangameswaran.nccarmy.FragmentsAndAdapters.DashboardFragment;
import com.example.sangameswaran.nccarmy.FragmentsAndAdapters.GrantRevokePermissionFragment;
import com.example.sangameswaran.nccarmy.FragmentsAndAdapters.MarkAttendanceFragment;
import com.example.sangameswaran.nccarmy.FragmentsAndAdapters.ParadeTaskReportFragment;
import com.example.sangameswaran.nccarmy.FragmentsAndAdapters.UnAuthFragment;
import com.example.sangameswaran.nccarmy.FragmentsAndAdapters.ViewCadetsDetailFragment;
import com.example.sangameswaran.nccarmy.FragmentsAndAdapters.ViewParadeOverallReportFragment;
import com.example.sangameswaran.nccarmy.R;
import com.example.sangameswaran.nccarmy.FragmentsAndAdapters.RegisterCadetFragment;
import com.example.sangameswaran.nccarmy.FragmentsAndAdapters.ViewPermissionChangesFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public CadetEntity register=new CadetEntity();
    private AdminEntity adminEntity=new AdminEntity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        changeLoginFlag("1");
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Gson gs=new Gson();
        try {
            SharedPreferences sp=getSharedPreferences("userDetails",MODE_PRIVATE);
            String jsonString=sp.getString("user","throwRuntimeException");
            adminEntity=gs.fromJson(jsonString,AdminEntity.class);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Cast Exception",Toast.LENGTH_LONG).show();
            finishAffinity();
        }
        if(adminEntity.getIsSuperAdmin().equals("1")){
            DashboardFragment fragment=new DashboardFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
        }else {
            UnAuthFragment fragment=new UnAuthFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            changeLoginFlag("0");
            killSession(adminEntity.getUser_name());
            finishAffinity();

        } else {
            drawer.openDrawer(GravityCompat.START);
            Toast.makeText(getApplicationContext(),"Press back oncemore to exit app",Toast.LENGTH_LONG).show();

        }
    }

    private void killSession(String user_name) {
        DatabaseReference killSession=FirebaseDatabase.getInstance().getReference("ACTIVE_SESSION");
        killSession.child(user_name).setValue(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_camera) {
            if(adminEntity.getIsAdmin().equals("1")) {
                CreateNewAdminFragment fragment = new CreateNewAdminFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
            }else {
                UnAuthFragment fragment=new UnAuthFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
            }

        } else if (id == R.id.nav_gallery) {
            if(adminEntity.getIsAdmin().equals("1")) {
                GrantRevokePermissionFragment fragment = new GrantRevokePermissionFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
            }else {
                UnAuthFragment fragment=new UnAuthFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
            }
        }
        else if (id == R.id.register_cadet) {
            if(adminEntity.getIsAdmin().equals("1")) {
                RegisterCadetFragment cadetFragment = new RegisterCadetFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, cadetFragment).commit();
            }else {
                UnAuthFragment fragment=new UnAuthFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
            }
        }
        else if (id==R.id.pchange)
        {   if (adminEntity.getIsSuperAdmin().equals("1")) {
            ViewPermissionChangesFragment fr = new ViewPermissionChangesFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fr).commit();
        }else {
            UnAuthFragment fragment=new UnAuthFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
        }
        }
        else if (id==R.id.viewCadetsDetail)
        {
            if (adminEntity.getIsSuperAdmin().equals("1")){
            ViewCadetsDetailFragment fragment=new ViewCadetsDetailFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
        }else {
                UnAuthFragment fragment=new UnAuthFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
            }
        }

        else if (id==R.id.attendance)
        {   if(adminEntity.getIsAdmin().equals("1")) {
            MarkAttendanceFragment fragment = new MarkAttendanceFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
        }else {
            UnAuthFragment fragment=new UnAuthFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
        }
        }
        else if (id==R.id.viewAttendanceReport)
        {
            if (adminEntity.getIsSuperAdmin().equals("1")) {
                ViewParadeOverallReportFragment fragment = new ViewParadeOverallReportFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
            }else {
                UnAuthFragment fragment=new UnAuthFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
            }
        }
        else if (id==R.id.paradeTask){
            if(adminEntity.getIsAdmin().equals("1")){
                ParadeTaskReportFragment fragment=new ParadeTaskReportFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
            }else {
                UnAuthFragment fragment=new UnAuthFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
            }
        }
        else if (id==R.id.nav_share){
        }
        else if (id==R.id.dashboard){
            if(adminEntity.getIsSuperAdmin().equals("1")){
                DashboardFragment fragment=new DashboardFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
            }else {
                UnAuthFragment fragment=new UnAuthFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void changeLoginFlag(final String flag)
    {
        SharedPreferences sp=getSharedPreferences("LoginCredentials",MODE_PRIVATE);
        final String user=sp.getString("MyloginID","Error");
        final DatabaseReference myref=FirebaseDatabase.getInstance().getReference("Admins/"+user);
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("tag","Admins/"+user);
                if (dataSnapshot.hasChildren())
                {
                    AdminEntity entity=new AdminEntity();
                    entity=dataSnapshot.getValue(AdminEntity.class);
                    entity.setLogin_flag(flag);
                    myref.setValue(entity);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Connectivity Error",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
