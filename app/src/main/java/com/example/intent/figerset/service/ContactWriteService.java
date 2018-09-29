package com.example.intent.figerset.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.example.intent.figerset.Fragment.EContactsFragment;


public class ContactWriteService extends IntentService {
    public static final String CONTACT_LIST = "contact_list";
    public ContactWriteService(String name) {
        super("ContactWriteService");
    }

    public static void setUpdateUI(EContactsFragment eContactsFragment) {
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
