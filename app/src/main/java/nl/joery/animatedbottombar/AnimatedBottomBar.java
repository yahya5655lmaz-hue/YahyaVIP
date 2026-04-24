package nl.joery.animatedbottombar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class AnimatedBottomBar extends View {
    public AnimatedBottomBar(Context c) { super(c); }
    public AnimatedBottomBar(Context c, AttributeSet a) { super(c,a); }
    public void setSelected(int id, boolean anim) {}
    public void setOnItemSelectedListener(OnItemSelectedListener l) {}
    public interface OnItemSelectedListener { void onItemSelected(int id, boolean b); }
}