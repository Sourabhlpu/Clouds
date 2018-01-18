package com.example.personal.clouds.utilities;

import com.example.personal.clouds.ui.detail.DetailActivityViewModelFactory;

import java.util.Date;

/**
 * Created by personal on 1/18/2018.
 */

public class InjectorUtils {

    public static DetailActivityViewModelFactory provideDetailViewModelFactory(Date date) {

        return new DetailActivityViewModelFactory(date);
    }
}
