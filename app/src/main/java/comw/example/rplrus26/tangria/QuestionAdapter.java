package comw.example.rplrus26.tangria;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class QuestionAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<Fragment> fragments = new ArrayList<>();

    public QuestionAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void initFragment(Fragment fragment) {
        fragments.add(fragment);
    }
}
