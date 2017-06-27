package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sangameswaran.nccarmy.Entities.AttendanceReportEntity;
import com.example.sangameswaran.nccarmy.Entities.InitApiEntity;
import com.example.sangameswaran.nccarmy.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sangameswaran on 27-06-2017.
 */

public class DashboardFragment extends Fragment {
    RelativeLayout rlDashboard,DashboardLoader;
    BarChart overallAttendanceChart;
    PieChart induividualAttendance;
    TextView dbtvParadeName,dbtvAttendance,dbtvTotalHC;
    Map<String,Double> coordinateMap=new HashMap<>();
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.dashboard_fragment_layout,container,false);
        rlDashboard= (RelativeLayout) v.findViewById(R.id.rlDashboard);
        DashboardLoader=(RelativeLayout)v.findViewById(R.id.DashboardLoader);
        induividualAttendance=(PieChart)v.findViewById(R.id.induividualParadeOverView);
        dbtvAttendance=(TextView)v.findViewById(R.id.dbtvAttendance);
        dbtvParadeName=(TextView)v.findViewById(R.id.dbtvParadeName);
        dbtvTotalHC=(TextView)v.findViewById(R.id.dbtvTotalCount);
        overallAttendanceChart=(BarChart)v.findViewById(R.id.overallAttendanceChart);
        final String[] allDates=new String[1000];
        DatabaseReference getOverallAttendanceCoordinatesApi= FirebaseDatabase.getInstance().getReference("PARADE_REPORT");
        getOverallAttendanceCoordinatesApi.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    int i=0;
                    for(DataSnapshot iterator:dataSnapshot.getChildren()){
                        try{
                            AttendanceReportEntity entity=iterator.getValue(AttendanceReportEntity.class);
                            String replace = entity.getPercentage_of_present().replace("%", "");
                            double percent=Double.parseDouble(replace);
                            String paradeDate=iterator.getKey();
                            allDates[i]=paradeDate;
                            coordinateMap.put(paradeDate,percent);
                            i++;
                        }catch (Exception e){
                            Toast.makeText(getActivity(),"Parse Error in Reading Values",Toast.LENGTH_LONG).show();
                        }
                    }
                    List<Integer> colours=new ArrayList<Integer>();
                    List<BarEntry> coordinates=new ArrayList<BarEntry>();
                    for(int iter=0;iter<coordinateMap.size();iter++){
                        coordinates.add(new BarEntry((float) iter,coordinateMap.get(allDates[iter]).floatValue()));
                        if(coordinateMap.get(allDates[iter]).floatValue()>=80.0f)
                        colours.add(Color.parseColor("#60a844"));
                        else if(coordinateMap.get(allDates[iter]).floatValue()>=40.0f)
                            colours.add(Color.parseColor("#dcdc39"));
                        else
                            colours.add(Color.parseColor("#ff5050"));
                        Log.d("CoordinateTest","x="+iter+",y="+coordinateMap.get(allDates[iter]).floatValue());
                    }
                    BarDataSet set=new BarDataSet(coordinates,"Attendance");
                    set.setColors(colours);
                    BarData data=new BarData(set);
                    data.setBarWidth(0.9f);
                    IAxisValueFormatter formatter=new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return "D+"+(int)value;
                        }
                    };
                    XAxis xAxis=overallAttendanceChart.getXAxis();
                    xAxis.setValueFormatter(formatter);
                    Legend le=overallAttendanceChart.getLegend();
                    le.setEnabled(false);
                    overallAttendanceChart.setFitBars(false);
                    overallAttendanceChart.setData(data);
                    overallAttendanceChart.invalidate();
                    overallAttendanceChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                        @Override
                        public void onValueSelected(final Entry e, Highlight h) {
                            Toast.makeText(getActivity(),allDates[(int) (e.getX())],Toast.LENGTH_LONG).show();
                            DatabaseReference getParadeDetailsApi=FirebaseDatabase.getInstance().getReference("PARADE_REPORT/"+allDates[(int)(e.getX())]);
                            getParadeDetailsApi.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                  if(dataSnapshot.hasChildren()){
                                      try{
                                          AttendanceReportEntity ent=dataSnapshot.getValue(AttendanceReportEntity.class);
                                          int emePresent=Integer.parseInt(ent.getEMEpresentCount());
                                          int engPresent=Integer.parseInt(ent.getENGpresentCount());
                                          int sigPresent=Integer.parseInt(ent.getSIGpresentCount());
                                          float eme=((float) ((float)emePresent/(emePresent+engPresent+sigPresent)))*100;
                                          float eng=((float) ((float)engPresent/(emePresent+engPresent+sigPresent)))*100;
                                          float sig=((float) ((float)sigPresent/(emePresent+engPresent+sigPresent)))*100;
                                            List<PieEntry> pieEntry=new ArrayList<PieEntry>();
                                          pieEntry.add(new PieEntry(eme,"EME"));
                                          pieEntry.add(new PieEntry(eng,"ENGINEERS"));
                                          pieEntry.add(new PieEntry(sig,"SIGNALS"));
                                          PieDataSet s=new PieDataSet(pieEntry,"PRESENT PERCENT");
                                          List<Integer> col=new ArrayList<Integer>();
                                          col.add(Color.parseColor("#60a844"));
                                          col.add(Color.parseColor("#dcdc39"));
                                          col.add(Color.parseColor("#ff0000"));
                                          s.setColors(col);
                                          PieData d=new PieData(s);
                                          induividualAttendance.setData(d);
                                          dbtvAttendance.setText("ATTENDANCE : "+ent.getPercentage_of_present());
                                          dbtvTotalHC.setText("TOTAL HEAD COUNT : "+ent.getOverall_present_count());
                                          dbtvParadeName.setText("PARADE NAME : "+allDates[(int)(e.getX())]);
                                          Legend l=induividualAttendance.getLegend();
                                          l.setEnabled(false);
                                          induividualAttendance.invalidate();
                                      }catch (Exception e){
                                          Log.d("EXCEPTION",e.getMessage().toString());
                                      }
                                  }else {
                                      Toast.makeText(getActivity(),"Selected parade details doesnot exist",Toast.LENGTH_LONG).show();
                                  }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onNothingSelected() {
                            //inflate allDates[0] view.
                        }
                    });
                    DashboardLoader.setVisibility(View.GONE);
                    rlDashboard.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(getActivity(),"Entry empty",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),"Server Error",Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }
}
