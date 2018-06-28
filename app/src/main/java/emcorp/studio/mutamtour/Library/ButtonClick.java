package emcorp.studio.mutamtour.Library;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

public class ButtonClick implements OnTouchListener {

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction()== MotionEvent.ACTION_DOWN){
			((ImageButton)v).setColorFilter(Color.argb(100, 0, 0, 185));
		} else if(event.getAction()== MotionEvent.ACTION_UP){
			((ImageButton)v).setColorFilter(Color.argb(0, 0, 0, 185));
		}
		return false;
	} 
}