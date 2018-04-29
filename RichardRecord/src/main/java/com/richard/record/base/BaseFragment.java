package com.richard.record.base;





import android.support.v4.app.Fragment;

import com.squareup.leakcanary.RefWatcher;

public class BaseFragment extends Fragment {

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = BaseApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
