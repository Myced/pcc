package com.pefscomsys.pcc_buea;

import android.util.Log;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ScriptureTextHandlerTest
{
    @Test
    public void formatVerse()
    {
        String cedric = "Ced.Drikc";
        String[] parts = cedric.split("\\.");

        String part1 = parts[0];
        String part2 = parts[1];

        assertEquals("Ced", part1);

    }
}
