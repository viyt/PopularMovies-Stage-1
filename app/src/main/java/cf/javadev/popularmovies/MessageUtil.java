package cf.javadev.popularmovies;

import android.content.Context;
import android.widget.Toast;


class MessageUtil {

    static void showLongErrorMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
