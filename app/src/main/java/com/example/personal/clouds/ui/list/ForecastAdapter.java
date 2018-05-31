package com.example.personal.clouds.ui.list;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personal.clouds.R;
import com.example.personal.clouds.data.database.WeatherEntity;
import com.example.personal.clouds.utilities.CloudsDateUtils;
import com.example.personal.clouds.utilities.CloudsWeatherUtils;

import java.util.List;

/**
 * Created by personal on 1/5/2018.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {

    private static final int VIEW_TYPE_TODAY = 0;
    private static final int VIEW_TYPE_FUTURE_DAY = 1;

    private final Context mContext;

    private final ForecastAdapterOnItemClickHandler mClickHandler;

    private final boolean mUseTodayLayout;

    private List<WeatherEntity> mForecast;

    public ForecastAdapter(Context context, ForecastAdapterOnItemClickHandler clickHandler)
    {
        mContext = context;
        mClickHandler = clickHandler;
        mUseTodayLayout = mContext.getResources().getBoolean(R.bool.use_today_layout);
    }
    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutId = getLayoutIdByType(viewType);
        View view = LayoutInflater.from(mContext).inflate(layoutId, parent,false);
        view.setFocusable(true);

        return new ForecastAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder holder, int position) {

        WeatherEntity currentWeather = mForecast.get(position);

        /****************
         * Weather Icon *
         ****************/

        int weatherIconId = currentWeather.getWeatherIconId();
        int weatherImageResourceId = getImageResourceId(weatherIconId,position);
        holder.iconView.setImageResource(weatherImageResourceId);

        /****************
         * Weather Date *
         ****************/

        long dateInMillis = currentWeather.getDate();

        String dateString = CloudsDateUtils.getFriendlyDateString1(mContext,dateInMillis,false);

        holder.dateView.setText(dateString);

        /***********************
         * Weather Description *
         ***********************/
        String description = CloudsWeatherUtils.getStringForWeatherCondition(mContext,weatherIconId);
        String descriptionA11y = mContext.getString(R.string.a11y_forecast,description);

        holder.descriptionView.setText(description);
        holder.descriptionView.setContentDescription(descriptionA11y);

        /**************************
         * High (max) temperature *
         **************************/

        double highInCelsius = currentWeather.getMax();
         /*
          * If the user's preference for weather is fahrenheit, formatTemperature will convert
          * the temperature. This method will also append either °C or °F to the temperature
          * String.
          */
        String highString = CloudsWeatherUtils.formatTemperature(mContext, highInCelsius);
         /* Create the accessibility (a11y) String from the weather description */
        String highA11y = mContext.getString(R.string.a11y_high_temp, highString);

         /* Set the text and content description (for accessibility purposes) */
        holder.highTempView.setText(highString);
        holder.highTempView.setContentDescription(highA11y);

        /*************************
         * Low (min) temperature *
         *************************/
        double lowInCelsius = currentWeather.getMin();
         /*
          * If the user's preference for weather is fahrenheit, formatTemperature will convert
          * the temperature. This method will also append either °C or °F to the temperature
          * String.
          */
        String lowString = CloudsWeatherUtils.formatTemperature(mContext, lowInCelsius);
        String lowA11y = mContext.getString(R.string.a11y_low_temp, lowString);

         /* Set the text and content description (for accessibility purposes) */
        holder.lowTempView.setText(lowString);
        holder.lowTempView.setContentDescription(lowA11y);




    }

    @Override
    public int getItemCount() {

        if(null == mForecast) return 0;

        return mForecast.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (mUseTodayLayout && position == 0) {

            return VIEW_TYPE_TODAY;

        }
        else
            {
            return VIEW_TYPE_FUTURE_DAY;
        }
    }



    public interface ForecastAdapterOnItemClickHandler {
        void onItemClick(Long date);
    }

    private int getLayoutIdByType(int viewType)
    {
        switch (viewType)
        {
            case VIEW_TYPE_TODAY : {
                return R.layout.list_item_forecast_today;
            }

            case VIEW_TYPE_FUTURE_DAY: {
                return R.layout.forecast_list_item;
            }

            default:

                throw new IllegalArgumentException("invalid view type, value of " + viewType);

        }
    }

    class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView iconView;

        final TextView dateView;
        final TextView descriptionView;
        final TextView highTempView;
        final TextView lowTempView;

        ForecastAdapterViewHolder(View view)
        {
            super(view);

            iconView = view.findViewById(R.id.weather_icon);
            dateView = view.findViewById(R.id.date);
            descriptionView = view.findViewById(R.id.weather_description);
            highTempView = view.findViewById(R.id.high_temperature);
            lowTempView = view.findViewById(R.id.low_temperature);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Long date = mForecast.get(adapterPosition).getDate();
            mClickHandler.onItemClick(date);

        }
    }

    private int getImageResourceId(int weatherIconId, int position)
    {
        int viewType = getItemViewType(position);

        switch (viewType)
        {
            case VIEW_TYPE_TODAY:
                return CloudsWeatherUtils
                        .getLargeArtResourceIdForWeatherCondition(weatherIconId);

            case VIEW_TYPE_FUTURE_DAY:
                return CloudsWeatherUtils
                        .getSmallArtResourceIdForWeatherCondition(weatherIconId);
            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }
    }


    void swapForecast(final List<WeatherEntity> newForecast)
    {
        if(mForecast == null || mForecast.isEmpty())
        {
            mForecast = newForecast;
            notifyDataSetChanged();
        }
        else
        {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mForecast.size();
                }

                @Override
                public int getNewListSize() {
                    return newForecast.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mForecast.get(oldItemPosition).getId()
                            == newForecast.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    WeatherEntity newWeather = newForecast.get(newItemPosition);
                    WeatherEntity oldWeather = newForecast.get(oldItemPosition);

                    return newWeather.getId() == oldWeather.getId()
                            && newWeather.getDate().equals(oldWeather.getDate());
                }
            });
        }
    }
}
