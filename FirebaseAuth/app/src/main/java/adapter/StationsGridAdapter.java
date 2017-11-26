package adapter;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import info.androidhive.firebase.R;
import info.androidhive.model.Station;

public class StationsGridAdapter extends BaseAdapter {

    static final int TIME_DIALOG_ID = 1111;
    private DatabaseReference mPostReference;
    private Context mContext;
    private List<Station> stations;
    private TextView available;
    private int hr;
    private int min;
    private int selectedPosition;

        // 1
        public StationsGridAdapter(Context context, List<Station> stations) {
            this.mContext = context;
            this.stations = stations;

            // Must be logged in at Firebase so continue
            // Show all the chargers for the user's company
            mPostReference = FirebaseDatabase.getInstance().getReference("companies/MBNA Chester/stations/");
        }

        // 2
        @Override
        public int getCount() {
            return stations.size();
        }

        // 3
        @Override
        public long getItemId(int position) {
            return 0;
        }

        // 4
        @Override
        public Station getItem(int position) {
            return stations.get(position);
        }

        // 5
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // need to set this once along with the View I think
            LayoutInflater inflater = LayoutInflater.from(mContext);
            RelativeLayout r = (RelativeLayout) inflater.inflate(R.layout.station_layout, parent, false);

            Station station = getItem(position);
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//            params.addRule(RelativeLayout., RelativeLayout.TRUE);
            TextView name = (TextView) r.findViewById(R.id.station_name);
            TextView available = (TextView) r.findViewById(R.id.station_available);
            TextView user = (TextView) r.findViewById(R.id.station_user);

            name.setText(station.getName());
            user.setText(station.getUserName());
            available.setText(station.getAvailable());
            // green if working and not in use
            r.setBackgroundColor(Color.parseColor("#64FFDA"));
            if (null != station.getAvailable()) {
                // blue if in use
                r.setBackgroundColor(Color.parseColor("#FF9100"));
                //station.setAvailable("Available for use");
            }
            if (!station.getWorking()) {
                // grey if in broken
                r.setBackgroundColor(Color.parseColor("#BDBDBD"));
            }
            addAvailableClickListener(available, position);
            return r;
        }

    public void addAvailableClickListener(final TextView available, final int position) {
        available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog selectedTime = createdDialog(TIME_DIALOG_ID);
                selectedTime.show();
                selectedPosition = position;
            }
        });
    }
    protected Dialog createdDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(mContext, R.style.TimePicker, timePickerListener, hr, min, false);
        }
        return null;
    }
    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
// TODO Auto-generated method stub
            hr = hourOfDay;
            min = minutes;
            // get the currently selected station and update it in firebase with the time selected
            Station selectedItem = getItem(selectedPosition);
            selectedItem.setAvailable(updateTime(hr, min));
            mPostReference.getRef().child(selectedItem.getId()).setValue(selectedItem);
        }
    };

//    private static String utilTime(int value) {
//        if (value < 10) return "0" + String.valueOf(value); else return String.valueOf(value); }

    private String updateTime(int hours, int mins) {
        String timeSet = ""; if (hours > 12) {
        hours -= 12;
        timeSet = "PM";
    } else if (hours == 0) {
        hours += 12;
        timeSet = "AM";
    } else if (hours == 12)
        timeSet = "PM";
    else
        timeSet = "AM";
        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);
        return new StringBuilder().append(hours).append(':').append(minutes).append(" ").append(timeSet).toString();

    }

}
