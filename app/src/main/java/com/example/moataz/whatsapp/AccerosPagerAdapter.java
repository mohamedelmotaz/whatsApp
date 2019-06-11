package com.example.moataz.whatsapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AccerosPagerAdapter extends FragmentPagerAdapter {
    public AccerosPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                ChatsFragment chatsFragment=new ChatsFragment();
                return  chatsFragment;
            case 1:
                GroupsFragment groupsFragment=new GroupsFragment();
                return  groupsFragment;
            case 2:
                ContactsFragment contactsFragment=new ContactsFragment();
                return contactsFragment;
                default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:

                return  "CHATS";
            case 1:
           return "GROUPS";
            case 2:

                return "CONTACTS";
            default:
                return null;
        }
    }
}
