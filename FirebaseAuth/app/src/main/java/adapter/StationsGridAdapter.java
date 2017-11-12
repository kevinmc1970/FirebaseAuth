package adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import info.androidhive.model.Station;

public class StationsGridAdapter extends BaseAdapter {

        private Context mContext;
        private List<Station> stations;

        // 1
        public StationsGridAdapter(Context context, List<Station> stations) {
            this.mContext = context;
            this.stations = stations;
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
            Station station = getItem(position);
            TextView dummyTextView = new TextView(mContext);
            TextView name = new TextView(mContext);
            TextView available = new TextView(mContext);
            TextView working = new TextView(mContext);
            LinearLayout r = new LinearLayout(mContext);
            r.setOrientation(LinearLayout.VERTICAL);
            r.addView(name);
            r.addView(dummyTextView);
            r.addView(available);
            r.addView(working);
            dummyTextView.setText(station.getUserName());
            name.setText(station.getName());
            if (station.getAvailable() > 0) {
                available.setText(String.valueOf(new Date(getItem(position).getAvailable())));
            }
            if (station.getWorking()) {
                working.setText("charger working");
            }
            r.setBackgroundColor(Color.parseColor("#d9d5dc"));
            return r;
        }

    }
