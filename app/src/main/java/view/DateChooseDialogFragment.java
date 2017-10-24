package view;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dabing.learnandroid.R;

import java.util.ArrayList;
import java.util.Calendar;

import view.wheelview.OnWheelChangedListener;
import view.wheelview.OnWheelScrollListener;
import view.wheelview.WheelView;
import view.wheelview.adapter.AbstractWheelTextAdapter1;

/**
 * Created by dabing on 2017/10/6.
 */

public class DateChooseDialogFragment extends DialogFragment implements View.OnClickListener {
    private Context context;
    private WheelView wvYear;
    private WheelView wvMonth;
    private WheelView wvDay;

    private TextView btnSure;
    private TextView btnCancel;

    private ArrayList<String> arry_years = new ArrayList<String>();
    private ArrayList<String> arry_months = new ArrayList<String>();
    private ArrayList<String> arry_days = new ArrayList<String>();
    private CalendarTextAdapter mYearAdapter;
    private CalendarTextAdapter mMonthAdapter;
    private CalendarTextAdapter mDaydapter;

    private String month;//一年中的月份
    private String day;//月份中的天数

    private String currentYear = getYear();
    private String currentMonth = getMonth();
    private String currentDay = getDay();
    private String currentEndYear = getYear();
    private String currentEndMonth = getMonth();
    private String currentEndDay = getDay();

    private int maxTextSize = 24;
    private int minTextSize = 14;

    private boolean issetdata = false;

    private String selectYear;
    private String selectMonth;
    private String selectDay;

    private String selectEndYear;
    private String selectEndMonth;
    private String selectEndDay;
    private ChangeDatePopwindow.OnBirthListener onBirthListener;
    private LinearLayout startDateLinear;
    private LinearLayout endDateLinear;
    float downX = 0;
    float moveX = 0;
    private TextView nextStep;
    private TextView upStep;
    private WheelView endYear;
    private WheelView endMonth;
    private WheelView endDay;
    private CalendarTextAdapter endMonthAdapter;
    private CalendarTextAdapter endDaydapter;
    private CalendarTextAdapter endYearAdapter;
    private View view;
    public static int POSITION_LEFT = 0;//左侧
    public static int POSITION_RIGHT = 1;//右侧
    private int position = 0;//默认展示左侧还是右侧


    public static DateChooseDialogFragment getInstance(int position){
        DateChooseDialogFragment dateChooseDialogFragment = new DateChooseDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        dateChooseDialogFragment.setArguments(bundle);
        return  dateChooseDialogFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Bundle arguments = getArguments();
        position = arguments.getInt("position", 0);
        view = inflater.inflate(R.layout.dialog_myinfo_changebirth3, null);
        context = getActivity();
        CustomerHorizontalScrollView csv = (CustomerHorizontalScrollView) view.findViewById(R.id.date_container);

        startDateLinear = (LinearLayout) view.findViewById(R.id.start_date_linear);
        endDateLinear = (LinearLayout) view.findViewById(R.id.end_date_linear);
        btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        nextStep = (TextView) view.findViewById(R.id.btn_next_step);
        upStep = (TextView) view.findViewById(R.id.btn_up_step);
        btnSure = (TextView) view.findViewById(R.id.btn_sure);

        wvYear = (WheelView) view.findViewById(R.id.wv_birth_year);
        wvMonth = (WheelView) view.findViewById(R.id.wv_birth_month);
        wvDay = (WheelView) view.findViewById(R.id.wv_birth_day);


        endYear = (WheelView) view.findViewById(R.id.wv_end_year);
        endMonth = (WheelView) view.findViewById(R.id.wv_end_month);
        endDay = (WheelView) view.findViewById(R.id.wv_end_day);


        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startDateLinear.animate().translationX(-startDateLinear.getMeasuredWidth());
                    endDateLinear.animate().translationX(-startDateLinear.getMeasuredWidth());
            }
        });
        upStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startDateLinear.animate().translationX(0);
                    endDateLinear.animate().translationX(0);

            }
        });

        btnSure.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        if (!issetdata) {
            initData();
        }
        initYears();
        handleStartDate();
        handleEndDate();
        return view;
    }

    private void handleEndDate() {
        endYearAdapter = new CalendarTextAdapter(context, arry_years, setYear(currentEndYear), maxTextSize, minTextSize);
        endYear.setVisibleItems(5);
        endYear.setViewAdapter(endYearAdapter);
        endYear.setCurrentItem(setYear(currentEndYear));

        initMonths(Integer.parseInt(month));
        endMonthAdapter = new CalendarTextAdapter(context, arry_months, setMonth(currentEndMonth), maxTextSize, minTextSize);
        endMonth.setVisibleItems(5);
        endMonth.setViewAdapter(endMonthAdapter);
        endMonth.setCurrentItem(setMonth(currentEndMonth));

        initDays(Integer.parseInt(day));
        endDaydapter = new CalendarTextAdapter(context, arry_days, Integer.parseInt(currentEndDay) - 1, maxTextSize, minTextSize);
        endDay.setVisibleItems(5);
        endDay.setViewAdapter(endDaydapter);
        endDay.setCurrentItem(Integer.parseInt(currentEndDay) - 1);

        endYear.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) endYearAdapter.getItemText(wheel.getCurrentItem());
                selectEndYear = currentText;
                setTextViewSize(currentText, endYearAdapter);
                currentEndYear = currentText.substring(0, currentText.length() - 1).toString();
                setYear(currentEndYear);
                initMonths(Integer.parseInt(month));
                endMonthAdapter = new CalendarTextAdapter(context, arry_months, 0, maxTextSize, minTextSize);
                endMonth.setVisibleItems(5);
                endMonth.setViewAdapter(endMonthAdapter);
                endMonth.setCurrentItem(0);

                calDays(currentEndYear, month);
            }
        });

        endYear.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) endYearAdapter.getItemText(wheel.getCurrentItem());
                setTextViewSize(currentText, endYearAdapter);
            }
        });

        endMonth.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) endMonthAdapter.getItemText(wheel.getCurrentItem());
                selectEndMonth = currentText;
                setTextViewSize(currentText, endMonthAdapter);
                setMonth(currentText.substring(0, 1));
                initDays(Integer.parseInt(day));
                endDaydapter = new CalendarTextAdapter(context, arry_days, 0, maxTextSize, minTextSize);
                endDay.setVisibleItems(5);
                endDay.setViewAdapter(endDaydapter);
                endDay.setCurrentItem(0);

                calDays(currentEndYear, month);
            }
        });

        endMonth.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) endMonthAdapter.getItemText(wheel.getCurrentItem());
                setTextViewSize(currentText, endMonthAdapter);
            }
        });

        endDay.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) endDaydapter.getItemText(wheel.getCurrentItem());
                setTextViewSize(currentText, endDaydapter);
                selectEndDay = currentText;
            }
        });

        endDay.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) endDaydapter.getItemText(wheel.getCurrentItem());
                setTextViewSize(currentText, endDaydapter);
            }
        });
    }

    private void handleStartDate() {
        mYearAdapter = new CalendarTextAdapter(context, arry_years, setYear(currentYear), maxTextSize, minTextSize);
        wvYear.setVisibleItems(5);
        wvYear.setViewAdapter(mYearAdapter);
        wvYear.setCurrentItem(setYear(currentYear));

        initMonths(Integer.parseInt(month));
        mMonthAdapter = new CalendarTextAdapter(context, arry_months, setMonth(currentMonth), maxTextSize, minTextSize);
        wvMonth.setVisibleItems(5);
        wvMonth.setViewAdapter(mMonthAdapter);
        wvMonth.setCurrentItem(setMonth(currentMonth));

        initDays(Integer.parseInt(day));
        mDaydapter = new CalendarTextAdapter(context, arry_days, Integer.parseInt(currentDay) - 1, maxTextSize, minTextSize);
        wvDay.setVisibleItems(5);
        wvDay.setViewAdapter(mDaydapter);
        wvDay.setCurrentItem(Integer.parseInt(currentDay) - 1);

        wvYear.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                selectYear = currentText;
                setTextViewSize(currentText, mYearAdapter);
                currentYear = currentText.substring(0, currentText.length() - 1).toString();
                Log.d("currentYear==", currentYear);
                setYear(currentYear);
                initMonths(Integer.parseInt(month));
                mMonthAdapter = new CalendarTextAdapter(context, arry_months, 0, maxTextSize, minTextSize);
                wvMonth.setVisibleItems(5);
                wvMonth.setViewAdapter(mMonthAdapter);
                wvMonth.setCurrentItem(0);

                calDays(currentYear, month);
            }
        });

        wvYear.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                setTextViewSize(currentText, mYearAdapter);
            }
        });

        wvMonth.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                selectMonth = currentText;
                setTextViewSize(currentText, mMonthAdapter);
                setMonth(currentText.substring(0, 1));
                initDays(Integer.parseInt(day));
                mDaydapter = new CalendarTextAdapter(context, arry_days, 0, maxTextSize, minTextSize);
                wvDay.setVisibleItems(5);
                wvDay.setViewAdapter(mDaydapter);
                wvDay.setCurrentItem(0);

                calDays(currentYear, month);
            }
        });

        wvMonth.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                setTextViewSize(currentText, mMonthAdapter);
            }
        });

        wvDay.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) mDaydapter.getItemText(wheel.getCurrentItem());
                setTextViewSize(currentText, mDaydapter);
                selectDay = currentText;
            }
        });

        wvDay.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mDaydapter.getItemText(wheel.getCurrentItem());
                setTextViewSize(currentText, mDaydapter);
            }
        });
    }


    public void initYears() {
        for (int i = Integer.parseInt(getYear()); i < Integer.parseInt(getYear()) + 50; i++) {
            arry_years.add(i + "年");
        }
    }

    public void initMonths(int months) {
        arry_months.clear();
        for (int i = 1; i <= months; i++) {
            arry_months.add(i + "月");
        }
    }

    public void initDays(int days) {
        arry_days.clear();
        for (int i = 1; i <= days; i++) {
            arry_days.add(i + "日");
        }
    }

    private class CalendarTextAdapter extends AbstractWheelTextAdapter1 {
        ArrayList<String> list;

        protected CalendarTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }

    public void setBirthdayListener(ChangeDatePopwindow.OnBirthListener onBirthListener) {
        this.onBirthListener = onBirthListener;
    }

    @Override
    public void onClick(View v) {

        if (v == btnSure) {
            if (onBirthListener != null) {
                onBirthListener.onClick(selectYear, selectMonth, selectDay, selectEndYear, selectEndMonth, selectEndDay);
            }
        } else {
            dismiss();
        }
        dismiss();

    }

    public interface OnBirthListener {
        public void onClick(String year, String month, String day, String endYear, String endMonth, String endDay);
    }

    /**
     * 设置字体大小
     *
     * @param currentItemText
     * @param adapter
     */
    public void setTextViewSize(String currentItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textView = (TextView) arrayList.get(i);
            currentText = textView.getText().toString();
            if (currentItemText.equals(currentText)) {
                textView.setTextSize(maxTextSize);
            } else {
                textView.setTextSize(minTextSize);
            }
        }
    }

    public String getYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR) + "";
    }

    public String getMonth() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH) + 1 + "";
    }

    public String getDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DATE) + "";
    }

    public void initData() {
        setDate(getYear(), getMonth(), getDay());
//		this.currentDay = 1+"";
//		this.currentMonth = 1+"";
    }

    /**
     * 设置年月日
     *
     * @param year
     * @param month
     * @param day
     */
    public void setDate(String year, String month, String day) {
        selectYear = year + "年";
        selectMonth = month + "月";
        selectDay = day + "日";
        issetdata = true;
        this.currentYear = year;
        this.currentMonth = month;
        this.currentDay = day;
//		if (year == getYear()) {
//			this.month = getMonth();
//		} else {
        this.month = 12 + "";
//		}
        calDays(year, month);
    }

    /**
     * 设置年份
     *
     * @param year
     */
    public int setYear(String year) {
        int yearIndex = 0;
//		if (!year.equals(getYear())) {
        this.month = 12 + "";
//		} else {
//			this.month = getMonth();
//		}
        for (int i = Integer.parseInt(getYear()); i < Integer.parseInt(getYear()) + 50; i++) {
            if (i == Integer.parseInt(year)) {
                return yearIndex;
            }
            yearIndex++;
        }
        return yearIndex;
    }

    /**
     * 设置月份
     *
     * @param month
     * @return
     */
    public int setMonth(String month) {
        int monthIndex = 0;
        calDays(currentYear, month);
        for (int i = 1; i < Integer.parseInt(this.month); i++) {
            if (Integer.parseInt(month) == i) {
                return monthIndex;
            } else {
                monthIndex++;
            }
        }
        return monthIndex;
    }

    /**
     * 计算每月多少天
     *
     * @param month
     * @param year
     */
    public void calDays(String year, String month) {
        boolean leayyear = false;
        if (Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0) {
            leayyear = true;
        } else {
            leayyear = false;
        }
        for (int i = 1; i <= 12; i++) {
            switch (Integer.parseInt(month)) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    this.day = 31 + "";
                    break;
                case 2:
                    if (leayyear) {
                        this.day = 29 + "";
                    } else {
                        this.day = 28 + "";
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    this.day = 30 + "";
                    break;
            }
        }
//		if (year.equals( getYear()) && month .equals( getMonth())) {
//			this.day = getDay();//为了显示到当前月的当前日
//		}
    }

    @Override
    public void onStart() {
        super.onStart();

        //得到dialog对应的window
        Window window = getDialog().getWindow();
        if (window != null) {
            //得到LayoutParams
            WindowManager.LayoutParams params = window.getAttributes();

            //修改gravity
            params.gravity = Gravity.BOTTOM;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(params);
        }
    }
}
