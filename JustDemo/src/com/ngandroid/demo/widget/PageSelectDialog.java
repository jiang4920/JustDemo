/**
 * PageSelectDialog.java[V 1.0.0]
 * classes : com.ngandroid.demo.widget.PageSelectDialog
 * jiangyuchen Create at 2013-11-1 上午11:27:07
 */
package com.ngandroid.demo.widget;

import com.ngandroid.demo.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

/**
 * com.ngandroid.demo.widget.PageSelectDialog
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-11-1 上午11:27:07
 */
public class PageSelectDialog {

    public interface OnSelectedListener {
        public void onSelected(boolean isSelected, int page);
    }

    public static AlertDialog create(Context context, final int count,
            final OnSelectedListener listener) {
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        String title = context.getString(R.string.jump_to_page);
        title = String.format(title, count);
        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(input)
                .setPositiveButton(R.string.confirm,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                    int which) {
                                int page = Integer.parseInt(input.getText()
                                        .toString());
                                listener.onSelected(page <= count, page);
                            }
                        }).setNegativeButton(R.string.cancel, null).create();
    }

}
