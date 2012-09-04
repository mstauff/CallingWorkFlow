package org.lds.community.CallingWorkFlow.domain;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created with IntelliJ IDEA.
 * User: matts
 * Date: 9/4/12
 * Time: 2:48 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BaseRecord {
    public ContentValues getContentValues();
      public void setContent(ContentValues values);
      public void setContent(Cursor cursor);
}
