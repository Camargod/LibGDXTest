package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundPlayer
{
    private static Sound rainSound1;

    public static void rain()
    {
        rainSound1 = Gdx.audio.newSound(Gdx.files.internal("rain.mp3"));
        rainSound1.loop();
    }
}