package com.irimedas.notifyme.interfaces;

import java.util.ArrayList;

public interface models {
    public void save();
    public void update(String id);
    public void delete(String id);
    public Object find(String id);
    public ArrayList<Object> all();
    public Object show();

}
