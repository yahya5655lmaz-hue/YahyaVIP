package com.ismaeldivita.chipnavigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class ChipNavigationBar extends View {
    public ChipNavigationBar(Context c) { super(c); }
    public ChipNavigationBar(Context c, AttributeSet a) { super(c,a); }
    public void setItemSelected(int id) {}
    public void setOnItemSelectedListener(OnItemSelectedListener l) {}
    public interface OnItemSelectedListener { void onItemSelected(int id); }
}