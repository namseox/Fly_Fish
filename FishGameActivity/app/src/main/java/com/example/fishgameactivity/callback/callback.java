package com.example.fishgameactivity.callback;

public class callback {
    public interface CallBackDirect {
        void OnItemDirect(String s, String score, String gameOver);
    }

    public interface CallBackDelete {
        void OnItemDirect(String name, int position);
    }
}
