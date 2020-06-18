package info.camposha.learnttoday.infrastructure.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import org.joda.time.DateTime;

import java.util.List;

import easyadapter.dc.com.library.EasyAdapter;
import info.camposha.learnttoday.R;

import info.camposha.learnttoday.domain.entity.Lesson;
import info.camposha.learnttoday.domain.usecase.ICallbacks;
import info.camposha.learnttoday.databinding.ActivityMainBinding;
import info.camposha.learnttoday.databinding.ModelGridBinding;
import info.camposha.learnttoday.infrastructure.viewmodel.MainViewModel;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends AppCompatActivity implements DatePickerListener,
        ICallbacks.ISaveListener, ICallbacks.IFetchListener {

    private ActivityMainBinding b;
    private EasyAdapter<Lesson, ModelGridBinding> adapter;
    private MainViewModel mv;

    private void init(){
        this.setupDatePicker();
        this.setupAdapter();
        this.handleEvents();
        this.mv =new ViewModelProvider(this).get(MainViewModel.class);
    }
    public void show(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    /**
     * This method will:
     * 1. Setup our horizontal datepicker
     */
    private void setupDatePicker(){
        b.datePicker.setListener(this)
                .setDays(360)
                .setOffset(7)
                .setDateSelectedColor(Color.DKGRAY)
                .setDateSelectedTextColor(Color.WHITE)
                .setMonthAndYearTextColor(Color.DKGRAY)
                .setTodayButtonTextColor(getResources().getColor(R.color.colorPrimary))
                .setTodayDateTextColor(getResources().getColor(R.color.colorPrimary))
                .setTodayDateBackgroundColor(Color.GRAY)
                .setUnselectedDayTextColor(Color.DKGRAY)
                .setDayOfWeekTextColor(Color.DKGRAY)
                .setUnselectedDayTextColor(getResources().getColor(R.color.primaryTextColor))
                .showTodayButton(true)
                .init();
        b.datePicker.setBackgroundColor(Color.LTGRAY);
        b.datePicker.setDate(new DateTime());
    }

    /**
     * This method will:
     * 1. setup our adapter for us
     * 2.Setup our recyclerview
     */
    private void setupAdapter() {
        adapter = new EasyAdapter<Lesson, ModelGridBinding>(R.layout.model_grid) {
            @Override
            public void onBind(@NonNull ModelGridBinding mb, @NonNull Lesson l) {
                if(mv.IS_DAILY_VIEW){
                    mb.headerTV.setText("LESSON "+(mv.TODAY_LESSONS.indexOf(l)+1));
                }else{
                    mb.headerTV.setText("LESSON "+(mv.ALL_LESSONS.indexOf(l)+1));
                }
                mb.contentTV.setText(l.getLesson());

                if(mb.doubleLift.isExpanded()){
                    mb.toggleBtn.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
                }else{
                    mb.toggleBtn.setImageResource(R.drawable.ic_keyboard_arrow_up_white_24dp);
                }

                mb.headerTV.setOnClickListener(view -> {
                    if(mb.doubleLift.isExpanded()){
                        mb.doubleLift.collapse();
                        mb.toggleBtn.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
                    }else{
                        mb.doubleLift.expand();
                        mb.toggleBtn.setImageResource(R.drawable.ic_keyboard_arrow_up_white_24dp);
                    }
                });
                mb.toggleBtn.setOnClickListener(view -> {
                    if(mb.doubleLift.isExpanded()){
                        mb.doubleLift.collapse();
                        mb.toggleBtn.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
                    }else{
                        mb.doubleLift.expand();
                        mb.toggleBtn.setImageResource(R.drawable.ic_keyboard_arrow_up_white_24dp);
                    }
                });
                mb.editBtn.setOnClickListener(v -> showInputDialog(true, l));
                mb.deleteBtn.setOnClickListener(v -> mv.delete( MainActivity.this,l));

            }

        };
        GridLayoutManager glm = new GridLayoutManager(this, 1);
        b.rv.setLayoutManager(glm);
        b.rv.setAdapter(adapter);
    }

    /**
     * This method will:
     * 1. Show our input dialog. We can use this dialog for either update or delete
     * @param forEdit
     * @param lesson
     */
    private void showInputDialog(Boolean forEdit, Lesson lesson) {
        String button2 = "SAVE";
        if (forEdit) {
            button2 = "UPDATE";
        } else {
            button2 = "CREATE";
        }
        new LovelyTextInputDialog(this, R.style.EditTextTintTheme)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle("What did you learn Today?")
                .setIcon(R.drawable.flip_page)
                .setConfirmButton(button2, text ->
                {
                    if (forEdit) {
                        lesson.setLesson(text);
                        lesson.setDate(mv.SELECTED_DATE);
                        mv.update(this,lesson);
                    } else {
                        mv.insert(this,text, mv.SELECTED_DATE);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .configureEditText(editText -> {
                    editText.setMinLines(5);
                    if (lesson != null && lesson.getLesson() != null) {
                        editText.setText(lesson.getLesson());
                    }
                })
                .show();
    }

    /**
     * When our datepicker is selected, we obtain the selected date and
     * filter our data
     * @param dateSelected
     */
    @Override
    public void onDateSelected(DateTime dateSelected) {
        String year = String.valueOf(dateSelected.getYear());
        String month = String.valueOf(dateSelected.getMonthOfYear());
        String day = String.valueOf(dateSelected.getDayOfMonth());
        if(dateSelected.getMonthOfYear() < 10){
            mv.SELECTED_DATE = year + "-0" + month + "-" + day;
        }else{
            mv.SELECTED_DATE = year + "-" + month + "-" + day;
        }
        mv.IS_DAILY_VIEW=true;
        mv.fetchAll(this);

    }


    /**
     * When our data is saved
     * @param message
     */
    @Override
    public void onSuccess(String message) {
        show(message);
        mv.fetchAll(this);
    }

    /**
     * When an error occurs
     * @param error
     */
    @Override
    public void onError(String error) {
        show(error);
    }

    /**
     * When our data is loaded
     * @param lessons
     */
    @Override
    public void onDataFetched(List<Lesson> lessons) {
        adapter.clear(true);
        mv.ALL_LESSONS = lessons;
        mv.TODAY_LESSONS = mv.getForSelectedDate();
        if(mv.IS_DAILY_VIEW){
            adapter.addAll(mv.TODAY_LESSONS,true);
        }else{
            adapter.addAll(mv.ALL_LESSONS,true);
        }
        adapter.notifyDataSetChanged();
    }
    /**
     * Let's handle our events
     */
    private void handleEvents(){
        b.closeBtn.setOnClickListener(view ->finish());
        b.addBtn.setOnClickListener(view -> showInputDialog(false,null));
        b.switchBtn.setOnClickListener(view -> {
            mv.IS_DAILY_VIEW=!mv.IS_DAILY_VIEW;

            if(mv.IS_DAILY_VIEW){
                b.switchBtn.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
                adapter.clear(true);
                adapter.addAll(mv.TODAY_LESSONS,true);
                b.datePicker.setVisibility(View.VISIBLE);

            }else{
                b.switchBtn.setImageResource(R.drawable.ic_keyboard_arrow_up_white_24dp);
                adapter.clear(true);
                adapter.addAll(mv.ALL_LESSONS,true);
                b.datePicker.setVisibility(View.GONE);
            }
        });
    }
    /**
     * Attach Base Context to allow us inject custom fonts
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    /**
     * Our onCreate callback
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_main);

        this.init();

    }
}
