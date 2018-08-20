package ru.kaooak.android.goblinscursorloaderexample.data;

public class Goblin {

    private long mId;
    private String mName;
    private int mMaxHP;
    private int mCurrentHP;

    public Goblin(long id, String name, int maxHP, int currentHP) {
        mId = id;
        mName = name;
        mMaxHP = maxHP;
        mCurrentHP = currentHP;
    }

    public Goblin(String name, int maxHP, int currentHP) {
        mId = -1;
        mName = name;
        mMaxHP = maxHP;
        mCurrentHP = currentHP;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int getMaxHP() {
        return mMaxHP;
    }

    public int getCurrentHP() {
        return mCurrentHP;
    }
}
