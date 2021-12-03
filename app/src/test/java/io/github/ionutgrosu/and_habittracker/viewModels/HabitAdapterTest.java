package io.github.ionutgrosu.and_habittracker.viewModels;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class HabitAdapterTest extends TestCase {

    HabitAdapter habitAdapter;

    @Before
    public void setup() {
        habitAdapter = new HabitAdapter(new ArrayList<>());
    }

    @Test
    public void testZeroPercentage() {
        //  arrange
        int total = 10, progress = 3;
        //  act
        int result = new HabitAdapter(new ArrayList<>()).getProgressPercentage(progress, total);
        //  assert
        assertEquals(30, result);
    }

    @Test
    public void testOnePercentage() {
        //  arrange
        int total = 100, progress = 1;
        //  act
        int result = new HabitAdapter(new ArrayList<>()).getProgressPercentage(progress, total);
        //  assert
        assertEquals(1, result);
    }

    @Test
    public void test33Percentage() {
        //  arrange
        int total = 3, progress = 1;
        //  act
        int result = new HabitAdapter(new ArrayList<>()).getProgressPercentage(progress, total);
        //  assert
        assertEquals(33, result);
    }

    @Test
    public void test50Percentage() {
        //  arrange
        int total = 50, progress = 25;
        //  act
        int result = new HabitAdapter(new ArrayList<>()).getProgressPercentage(progress, total);
        //  assert
        assertEquals(50, result);
    }

    @Test
    public void test100Percentage() {
        //  arrange
        int total = 20, progress = 20;
        //  act
        int result = new HabitAdapter(new ArrayList<>()).getProgressPercentage(progress, total);
        //  assert
        assertEquals(100, result);
    }


}