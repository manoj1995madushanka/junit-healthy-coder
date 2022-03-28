package com.healthycoderapp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class BMICalculatorTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("before all");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

    private String environment = "dev";

    @Test
    void should_return_true_when_diet_recommended() {
        // given
        double height = 1.72;
        double weight = 89.0;

        //when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);

        //then
        assertTrue(recommended);
    }

    @Test
    void should_return_true_when_coder_listHave1000Elements() {

        assumeTrue(this.environment.equals("prod"));

        // given
        List<Coder> coders = new ArrayList<>();
        for (int i=0;i<10000;i++)
        {
            coders.add(new Coder(1.0+i,10.0+i));
        }

        //when
        Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);

        //then
        assertTimeout(Duration.ofMillis(500),executable);
    }

    @ParameterizedTest(name = "weight={0},height={1}")
    @CsvSource(value = {"89.0,1.72", "95.0,1.75", "110.0,1.78"})
    void should_return_true_when_diet_recommended_parameterized(Double codeWeight, Double coderHeight) {
        // given
        double height = coderHeight;
        double weight = codeWeight;

        //when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);

        //then
        assertTrue(recommended);
    }

    @ParameterizedTest(name = "weight={0},height={1}")
    @CsvFileSource(resources = "/diet-recommended-input-data.csv",numLinesToSkip = 1)
    void should_return_true_when_diet_recommended_parameterized_file(Double codeWeight, Double coderHeight) {
        // given
        double height = coderHeight;
        double weight = codeWeight;

        //when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);

        //then
        assertTrue(recommended);
    }

    @Test
    @DisabledOnOs(OS.WINDOWS) // THIS WILL SKIP WHEN OS IS WINDOWS
    void should_return_false_when_diet_not_recommended() {
        // given
        double height = 50.0;
        double weight = 1.92;

        //when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);

        //then
        assertFalse(recommended);
    }

    @Test
    @Disabled // this will skip test
    void should_trow_arithmetic_exception_when_height_zero() {
        // given
        double height = 0.0;
        double weight = 1.0;

        //when
        Executable executable = () -> BMICalculator.isDietRecommended(weight, height);

        //then
        assertThrows(ArithmeticException.class, executable);
    }

    @Test
    void should_returnWorstBMI_when_coder_inNot_empty() {
        // given
        List<Coder> coders = new ArrayList<>();
        coders.add(new Coder(1.80, 60.0));
        coders.add(new Coder(1.82, 98.0));
        coders.add(new Coder(1.82, 64.8));

        //when
        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

        //then
        assertAll(
                () -> assertEquals(1.82, coderWorstBMI.getHeight()),
                () -> assertEquals(98.0, coderWorstBMI.getWeight())
        );
    }

    @Test
    void should_returnNull_when_coder_list_empty() {
        // given
        List<Coder> coders = new ArrayList<>();

        //when
        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

        //then
        assertNull(coderWorstBMI);
    }



    @Nested
    @DisplayName("sample display name")
    class GetBMIScoresTest{
        @Test
        void should_returnCorrectBMIScoreList_when_coder_inNot_empty() {
            // given
            List<Coder> coders = new ArrayList<>();
            coders.add(new Coder(1.80, 60.0));
            coders.add(new Coder(1.82, 98.0));
            coders.add(new Coder(1.82, 64.8));

            double[] expected = {18.52, 29.59, 19.56};

            //when
            double[] bmiScores = BMICalculator.getBMIScores(coders);

            //then
            assertArrayEquals(expected, bmiScores);
        }
    }
}