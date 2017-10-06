package com.elkusnandi.bakingapp.common;

import android.os.Bundle;

/**
 * Interface to handle action and data from fragment to Activity
 * <p>
 * Created by Taruna 98 on 19/09/2017.
 */

public interface FragmentDataListener {

    /**
     * Send data to any class that implements this interface
     *
     * @param action id
     * @param data   extra data in bundle
     */
    void onDataReceived(String action, Bundle data);
}
