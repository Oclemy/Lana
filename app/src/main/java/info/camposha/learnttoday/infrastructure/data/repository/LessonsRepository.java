package info.camposha.learnttoday.infrastructure.data.repository;

import android.content.Context;

import java.util.List;

import info.camposha.learnttoday.domain.usecase.ICallbacks;
import info.camposha.learnttoday.infrastructure.data.db.MyRoomDB;
import info.camposha.learnttoday.domain.entity.Lesson;
import info.camposha.learnttoday.infrastructure.data.db.LessonDAO;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LessonsRepository implements ICallbacks.ICrud {
    private LessonDAO lessonDAO;

    public LessonsRepository(Context context) {
        MyRoomDB myRoomDB = MyRoomDB.getInstance(context);
        lessonDAO = myRoomDB.lessonDAO();
    }

    /**
     * Insert into Room database
     * @param dataCallback
     * @param lesson
     */
    @Override
    public void insert(final ICallbacks.ISaveListener dataCallback, final Lesson lesson) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                lessonDAO.insert(lesson);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onComplete() {
                        dataCallback.onSuccess("INSERT SUCCESSFUL");
                    }
                    @Override
                    public void onError(Throwable e) {
                        dataCallback.onError(e.getMessage());
                    }
                });
    }

    /**
     * Update our data
     * @param dataCallback
     * @param lesson
     */
    @Override
    public void update(final ICallbacks.ISaveListener dataCallback, final Lesson lesson) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                lessonDAO.update(lesson);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onComplete() {
                        dataCallback.onSuccess("UPDATE SUCCESSFUL");
                    }
                    @Override
                    public void onError(Throwable e) {
                        dataCallback.onError(e.getMessage());
                    }
                });

    }

    /**
     * Delete from our Room database
     * @param dataCallback
     * @param lesson
     */
    @Override
    public void delete(final ICallbacks.ISaveListener dataCallback, final Lesson lesson) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                lessonDAO.delete(lesson);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onComplete() {
                        dataCallback.onSuccess("DELETE SUCCESSFUL");
                    }
                    @Override
                    public void onError(Throwable e) {
                        dataCallback.onError(e.getMessage());
                    }
                });
    }

    //next




























    /**
     * Select or retrieve our data
     * @param dataCallback
     */
    @Override
    public void retrieve(final ICallbacks.IFetchListener dataCallback) {
        lessonDAO.selectAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<Lesson>>() {
            @Override
            public void accept(List<Lesson> lessons) throws Exception {
                dataCallback.onDataFetched(lessons);
            }

        });
    }
}
//end