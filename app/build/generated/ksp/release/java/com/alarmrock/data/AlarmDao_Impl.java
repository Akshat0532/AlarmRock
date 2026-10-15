package com.alarmrock.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AlarmDao_Impl implements AlarmDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AlarmEntity> __insertionAdapterOfAlarmEntity;

  private final EntityDeletionOrUpdateAdapter<AlarmEntity> __deletionAdapterOfAlarmEntity;

  private final EntityDeletionOrUpdateAdapter<AlarmEntity> __updateAdapterOfAlarmEntity;

  public AlarmDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAlarmEntity = new EntityInsertionAdapter<AlarmEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `alarms` (`id`,`label`,`hour`,`minute`,`enabled`,`repeatDays`,`challengeType`,`challengeCount`,`difficulty`,`snoozeMinutes`,`soundUri`,`vibration`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AlarmEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getLabel());
        statement.bindLong(3, entity.getHour());
        statement.bindLong(4, entity.getMinute());
        final int _tmp = entity.getEnabled() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindString(6, entity.getRepeatDays());
        statement.bindString(7, entity.getChallengeType());
        statement.bindLong(8, entity.getChallengeCount());
        statement.bindString(9, entity.getDifficulty());
        statement.bindLong(10, entity.getSnoozeMinutes());
        statement.bindString(11, entity.getSoundUri());
        final int _tmp_1 = entity.getVibration() ? 1 : 0;
        statement.bindLong(12, _tmp_1);
      }
    };
    this.__deletionAdapterOfAlarmEntity = new EntityDeletionOrUpdateAdapter<AlarmEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `alarms` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AlarmEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfAlarmEntity = new EntityDeletionOrUpdateAdapter<AlarmEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `alarms` SET `id` = ?,`label` = ?,`hour` = ?,`minute` = ?,`enabled` = ?,`repeatDays` = ?,`challengeType` = ?,`challengeCount` = ?,`difficulty` = ?,`snoozeMinutes` = ?,`soundUri` = ?,`vibration` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AlarmEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getLabel());
        statement.bindLong(3, entity.getHour());
        statement.bindLong(4, entity.getMinute());
        final int _tmp = entity.getEnabled() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindString(6, entity.getRepeatDays());
        statement.bindString(7, entity.getChallengeType());
        statement.bindLong(8, entity.getChallengeCount());
        statement.bindString(9, entity.getDifficulty());
        statement.bindLong(10, entity.getSnoozeMinutes());
        statement.bindString(11, entity.getSoundUri());
        final int _tmp_1 = entity.getVibration() ? 1 : 0;
        statement.bindLong(12, _tmp_1);
        statement.bindLong(13, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final AlarmEntity alarm, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfAlarmEntity.insertAndReturnId(alarm);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final AlarmEntity alarm, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfAlarmEntity.handle(alarm);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final AlarmEntity alarm, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfAlarmEntity.handle(alarm);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<AlarmEntity>> getAll() {
    final String _sql = "SELECT * FROM alarms ORDER BY hour, minute";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"alarms"}, new Callable<List<AlarmEntity>>() {
      @Override
      @NonNull
      public List<AlarmEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfHour = CursorUtil.getColumnIndexOrThrow(_cursor, "hour");
          final int _cursorIndexOfMinute = CursorUtil.getColumnIndexOrThrow(_cursor, "minute");
          final int _cursorIndexOfEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "enabled");
          final int _cursorIndexOfRepeatDays = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatDays");
          final int _cursorIndexOfChallengeType = CursorUtil.getColumnIndexOrThrow(_cursor, "challengeType");
          final int _cursorIndexOfChallengeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "challengeCount");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfSnoozeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "snoozeMinutes");
          final int _cursorIndexOfSoundUri = CursorUtil.getColumnIndexOrThrow(_cursor, "soundUri");
          final int _cursorIndexOfVibration = CursorUtil.getColumnIndexOrThrow(_cursor, "vibration");
          final List<AlarmEntity> _result = new ArrayList<AlarmEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AlarmEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpLabel;
            _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            final int _tmpHour;
            _tmpHour = _cursor.getInt(_cursorIndexOfHour);
            final int _tmpMinute;
            _tmpMinute = _cursor.getInt(_cursorIndexOfMinute);
            final boolean _tmpEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfEnabled);
            _tmpEnabled = _tmp != 0;
            final String _tmpRepeatDays;
            _tmpRepeatDays = _cursor.getString(_cursorIndexOfRepeatDays);
            final String _tmpChallengeType;
            _tmpChallengeType = _cursor.getString(_cursorIndexOfChallengeType);
            final int _tmpChallengeCount;
            _tmpChallengeCount = _cursor.getInt(_cursorIndexOfChallengeCount);
            final String _tmpDifficulty;
            _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            final int _tmpSnoozeMinutes;
            _tmpSnoozeMinutes = _cursor.getInt(_cursorIndexOfSnoozeMinutes);
            final String _tmpSoundUri;
            _tmpSoundUri = _cursor.getString(_cursorIndexOfSoundUri);
            final boolean _tmpVibration;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfVibration);
            _tmpVibration = _tmp_1 != 0;
            _item = new AlarmEntity(_tmpId,_tmpLabel,_tmpHour,_tmpMinute,_tmpEnabled,_tmpRepeatDays,_tmpChallengeType,_tmpChallengeCount,_tmpDifficulty,_tmpSnoozeMinutes,_tmpSoundUri,_tmpVibration);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getEnabledAlarms(final Continuation<? super List<AlarmEntity>> $completion) {
    final String _sql = "SELECT * FROM alarms WHERE enabled = 1 ORDER BY hour, minute";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AlarmEntity>>() {
      @Override
      @NonNull
      public List<AlarmEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfHour = CursorUtil.getColumnIndexOrThrow(_cursor, "hour");
          final int _cursorIndexOfMinute = CursorUtil.getColumnIndexOrThrow(_cursor, "minute");
          final int _cursorIndexOfEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "enabled");
          final int _cursorIndexOfRepeatDays = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatDays");
          final int _cursorIndexOfChallengeType = CursorUtil.getColumnIndexOrThrow(_cursor, "challengeType");
          final int _cursorIndexOfChallengeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "challengeCount");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfSnoozeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "snoozeMinutes");
          final int _cursorIndexOfSoundUri = CursorUtil.getColumnIndexOrThrow(_cursor, "soundUri");
          final int _cursorIndexOfVibration = CursorUtil.getColumnIndexOrThrow(_cursor, "vibration");
          final List<AlarmEntity> _result = new ArrayList<AlarmEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AlarmEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpLabel;
            _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            final int _tmpHour;
            _tmpHour = _cursor.getInt(_cursorIndexOfHour);
            final int _tmpMinute;
            _tmpMinute = _cursor.getInt(_cursorIndexOfMinute);
            final boolean _tmpEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfEnabled);
            _tmpEnabled = _tmp != 0;
            final String _tmpRepeatDays;
            _tmpRepeatDays = _cursor.getString(_cursorIndexOfRepeatDays);
            final String _tmpChallengeType;
            _tmpChallengeType = _cursor.getString(_cursorIndexOfChallengeType);
            final int _tmpChallengeCount;
            _tmpChallengeCount = _cursor.getInt(_cursorIndexOfChallengeCount);
            final String _tmpDifficulty;
            _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            final int _tmpSnoozeMinutes;
            _tmpSnoozeMinutes = _cursor.getInt(_cursorIndexOfSnoozeMinutes);
            final String _tmpSoundUri;
            _tmpSoundUri = _cursor.getString(_cursorIndexOfSoundUri);
            final boolean _tmpVibration;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfVibration);
            _tmpVibration = _tmp_1 != 0;
            _item = new AlarmEntity(_tmpId,_tmpLabel,_tmpHour,_tmpMinute,_tmpEnabled,_tmpRepeatDays,_tmpChallengeType,_tmpChallengeCount,_tmpDifficulty,_tmpSnoozeMinutes,_tmpSoundUri,_tmpVibration);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
