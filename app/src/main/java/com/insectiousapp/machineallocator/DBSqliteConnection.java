package com.insectiousapp.machineallocator;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DELL on 9/24/2017.
 */

public class DBSqliteConnection extends SQLiteOpenHelper {
    static final String viewAssets = "ViewAssets";

    Context ctx;

    public static final String DB_NAME="machinecollector";
    public static final int DB_VERSION=1;

    public static final String EMPLOYEETABLE="Employee";
    public static final String ASSETTABLE="Asset";

    private static final String COL_1_EMPID = "EmployeeID";
    private static final String COL_2_EMNAME = "EmployeeName";
    private static final String COL_3_EMAILID = "Employee_emailid";
    private static final String COL_4_UNIT = "Unit";
    private static final String COL_5_PHONENO = "PhoneNumber";

    private static final String COL_1_ASSETID = "AssetID";
    private static final String COL_2_ASSETMAKE = "AssetMake";
    private static final String COL_3_YEAROFMAKING = "YearOfMake";
    private static final String COL_4_ALLOCATEDTO = "AllocatedTo";
    private static final String COL_5_ALLOCATEDTILL = "AllocatedTill";

    public DBSqliteConnection(Context ctx){

        super(ctx,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+EMPLOYEETABLE+" ("+COL_1_EMPID+ " INTEGER PRIMARY KEY AUTOINCREMENT , "+
                COL_2_EMNAME+ " TEXT,"+ COL_3_EMAILID + " TEXT," +COL_4_UNIT + "TEXT, " + COL_5_PHONENO+ " TEXT)");


        db.execSQL("CREATE TABLE "+ASSETTABLE+" ("+COL_1_ASSETID+ " INTEGER PRIMARY KEY AUTOINCREMENT , "+
                COL_2_ASSETMAKE+ " TEXT,"+ COL_3_YEAROFMAKING + " INTEGER," +COL_4_ALLOCATEDTO + " INTEGER, " + COL_5_ALLOCATEDTILL +
                " TEXT)" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+EMPLOYEETABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ASSETTABLE);
        db.execSQL("DROP VIEW IF EXISTS "+viewAssets);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }


    public boolean addAsset(String assetMake, int yearOfMaking, int allocatedTo, String allocatedTill)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(COL_2_ASSETMAKE, assetMake);
        cv.put(COL_3_YEAROFMAKING, yearOfMaking);
        cv.put(COL_4_ALLOCATEDTO, allocatedTo);
        cv.put(COL_5_ALLOCATEDTILL, allocatedTill);

        long result=db.insert(ASSETTABLE, null, cv);
        if(result==-1)
            return false;
        else
            return  true;

    }

    public Cursor readAllAssets()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * from "+ASSETTABLE, null);
        return  cursor;
    }

//    public boolean updateData(String id, String name, String surname, String marks)
//    {
//        SQLiteDatabase db=this.getWritableDatabase();
//
//        ContentValues cv=new ContentValues();
//        cv.put(COL_1_ID, id);
//        cv.put(COL_2_STUDENT_NAME, name);
//        cv.put(COL_3_STUDENT_SURNAME, surname);
//        cv.put(COL_4_STUDENT_MARKS, marks);
//
//        db.update(STUDENT_TABLE, cv, "ID = ?", new String[] {id});
//        return true;
//    }

//    public boolean removeAsset(String id)
//    {
//        SQLiteDatabase db=this.getWritableDatabase();
//        int resultRowsAffected=db.delete(STUDENT_TABLE, "ID = ?", new String[] {id});
//
//        if(resultRowsAffected==0)
//            return false;
//        else
//            return true;
//    }




}