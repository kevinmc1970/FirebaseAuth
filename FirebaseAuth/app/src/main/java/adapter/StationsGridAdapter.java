package adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import info.androidhive.firebase.R;
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
            // green if working and not in use
            r.setBackgroundColor(Color.parseColor("#64FFDA"));
            available.setText("Charger Available for Use");
            if (station.getAvailable() > 0) {
                available.setText(String.valueOf(new Date(station.getAvailable())));
                // blue if in use
                r.setBackgroundColor(Color.parseColor("#FF9100"));
            }
            if (!station.getWorking()) {
                // grey if in broken
                r.setBackgroundColor(Color.parseColor("#BDBDBD"));
                available.setText("Charger Broken");
            }
            return r;
        }

    }
