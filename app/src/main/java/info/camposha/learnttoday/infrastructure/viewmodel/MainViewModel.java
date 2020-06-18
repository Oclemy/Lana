package info.camposha.learnttoday.infrastructure.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import info.camposha.learnttoday.domain.entity.Lesson;
import info.camposha.learnttoday.domain.usecase.ICallbacks;
import info.camposha.learnttoday.domain.usecase.IManager;
import info.camposha.learnttoday.infrastructure.data.repository.LessonsRepository;
import info.camposha.learnttoday.infrastructure.view.MainActivity;

/**
 * ANDROID: http://www.camposha.info : Oclemy.
 */
public class MainViewModel extends AndroidViewModel {
    private LessonsRepository lessonsRepository;
    public String SELECTED_DATE = "";
    public List<Lesson> ALL_LESSONS = new ArrayList<>();
    public List<Lesson> TODAY_LESSONS = new ArrayList<>();
    public boolean IS_DAILY_VIEW = true;

    public MainViewModel(@NonNull Application application) {
        super(application);
        lessonsRepository=new LessonsRepository(application);
    }

    /**
     * This method will:
     * 1. Insert our lesson into our SQLite database
     * @param learnt
     * @param date
     */
    public void insert(ICallbacks.ISaveListener iSaveListener,String learnt, String date) {
        Lesson lesson = new Lesson();
        lesson.setLesson(learnt);
        lesson.setDate(date);
        lessonsRepository.insert(iSaveListener,lesson);
    }
    /**
     * This method will:
     * 1. Update a Lesson
     * @param lesson
     */
    public void update(ICallbacks.ISaveListener iSaveListener,Lesson lesson) {
        lessonsRepository.update(iSaveListener,lesson);
    }
    /**
     * This method will:
     * 1. Delete a Lesson
     * @param lesson
     */
    public void delete(ICallbacks.ISaveListener iSaveListener,Lesson lesson) {
        lessonsRepository.delete(iSaveListener,lesson);
    }
    public void fetchAll(ICallbacks.IFetchListener iFetchListener){
        lessonsRepository.retrieve(iFetchListener);
    }
    public List<Lesson> getForSelectedDate(){
        return IManager.filterByDate(ALL_LESSONS,SELECTED_DATE);
    }
}
