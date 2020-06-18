package info.camposha.learnttoday.domain.usecase;

import java.util.ArrayList;
import java.util.List;

import info.camposha.learnttoday.domain.entity.Lesson;

public class IManager {
    public static List<Lesson> filterByDate(List<Lesson> allLessons, String date){
        ArrayList<Lesson> filteredLessons =new ArrayList<>();

        if(allLessons == null){
            return filteredLessons;
        }
        for (Lesson lesson : allLessons){
            if (lesson != null && lesson.getDate().equalsIgnoreCase(date)){
                filteredLessons.add(lesson);
            }
        }
        return filteredLessons;

    }

}
