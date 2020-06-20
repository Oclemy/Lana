package info.camposha.learnttoday.domain.usecase;

import java.util.List;

import info.camposha.learnttoday.domain.entity.Lesson;

public class ICallbacks {
    /**
     * Our CRUD Methods
     */
    public interface ICrud {
        void insert(final ISaveListener iSaveListener, final Lesson lesson);
        void update(final ISaveListener iSaveListener, final Lesson lesson);
        void delete(final ISaveListener iSaveListener, final Lesson lesson);
        void retrieve(final IFetchListener iFetchListener);
    }
    /**
     * Our DataSave Listener
     * Will be raised once our data is saved.
     */
    public interface ISaveListener {
        void onSuccess(String message);
        void onError(String error);
    }

    /**
     * Our DataLoad Listener
     * Will be raised once our data is loaded. A List of loaded items will
     * passed to us
     */
    public interface IFetchListener {
        void onDataFetched(List<Lesson> lessons);
    }
}
//end