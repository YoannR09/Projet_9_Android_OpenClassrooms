package com.openclassrooms.realestatemanager.data.dao.listener;

public interface DaoListener<T> {
    void onSuccess(T data);
    void onFailure(String err);
}
