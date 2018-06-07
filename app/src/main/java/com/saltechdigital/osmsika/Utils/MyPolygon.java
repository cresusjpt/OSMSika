package com.saltechdigital.osmsika.Utils;

import android.view.MotionEvent;
import android.widget.Toast;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polygon;

public class MyPolygon extends Polygon {
    private Object tag;

    private OnPolygonClickListener onPolygonClickListener;
    private MyPolygon polygon;

    public void setPolygon(MyPolygon polygon) {
        this.polygon = polygon;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public interface OnPolygonClickListener {
        void onPolygonClick(MyPolygon polygon);
    }

    public void setOnPolygonClickListener(OnPolygonClickListener onPolygonClickListener) {
        this.onPolygonClickListener = onPolygonClickListener;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event, MapView mapView) {
        if (event.getAction() == MotionEvent.ACTION_UP && contains(event)) {
            onPolygonClickListener.onPolygonClick(this);
            return true;
        }
        return super.onSingleTapUp(event, mapView);
    }
}
