package com.healthycoderapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class ActivityCalculatorTest {

    @Test
    void should_return_bad_when_age_below_20(){
        int weeklyCardioMins =40;
        int weeklyWorkouts =1;

        String actual = ActivityCalculator.rateActivityLevel(weeklyCardioMins,weeklyWorkouts);

        assertEquals("bad",actual);
    }

    @Test
    void should_return_average_when_age_between_20_and_40(){
        int weeklyCardioMins =40;
        int weeklyWorkouts =3;

        String actual = ActivityCalculator.rateActivityLevel(weeklyCardioMins,weeklyWorkouts);

        assertEquals("average",actual);
    }

    @Test
    void should_return_good_when_age_below_40(){
        int weeklyCardioMins =40;
        int weeklyWorkouts =7;

        String actual = ActivityCalculator.rateActivityLevel(weeklyCardioMins,weeklyWorkouts);

        assertEquals("good",actual);
    }

    @Test
    void should_throw_exception(){
        int weeklyCardioMins =-40;
        int weeklyWorkouts =7;

        Executable executable = ()->ActivityCalculator.rateActivityLevel(weeklyCardioMins,weeklyWorkouts);

        assertThrows(RuntimeException.class,executable);
    }

}